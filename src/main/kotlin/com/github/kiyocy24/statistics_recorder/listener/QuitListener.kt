package com.github.kiyocy24.statistics_recorder.listener

import com.github.kiyocy24.statistics_recorder.entity.view.ItemLog
import com.github.kiyocy24.statistics_recorder.entity.view.User
import com.github.kiyocy24.statistics_recorder.mysqlConn
import com.github.kiyocy24.statistics_recorder.repository.ItemLogRepository
import com.github.kiyocy24.statistics_recorder.repository.UserRepository
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.sql.Timestamp

object QuitListener : Listener {
    @EventHandler
    fun onPlayerQuitEvent(e: PlayerQuitEvent) {
        val userRepo = UserRepository(mysqlConn)
        val itemLogRepo = ItemLogRepository(mysqlConn)

        // Record user log
        val user = User(
                uuid = e.player.uniqueId.toString(),
                name = e.player.name,
                lastLogin = Timestamp(System.currentTimeMillis())
        )
        userRepo.update(user)

        // Record item log
        val userId = userRepo.searchByUuid(e.player.uniqueId.toString()).id
        val userLoginNum = e.player.getStatistic(Statistic.LEAVE_GAME)
        val itemLogs = mutableListOf<ItemLog>()
        for (m in Material.values()) {
            val blockMined = e.player.getStatistic(Statistic.MINE_BLOCK, m)
            val itemBroken = e.player.getStatistic(Statistic.BREAK_ITEM, m)
            val itemCrafted = e.player.getStatistic(Statistic.CRAFT_ITEM, m)
            val itemUsed = e.player.getStatistic(Statistic.USE_ITEM, m)
            val itemPickedUp = e.player.getStatistic(Statistic.PICKUP, m)
            val itemDropped = e.player.getStatistic(Statistic.DROP, m)

            if (blockMined > 0 || itemBroken > 0 || itemCrafted > 0 ||
                itemUsed > 0 || itemPickedUp > 0 || itemDropped > 0) {
                itemLogs.add(ItemLog(
                        userId = userId,
                        userLoginNum = userLoginNum,
                        itemId = m.ordinal,
                        name = m.name,
                        blockMined = blockMined,
                        itemBroken = itemBroken,
                        itemCrafted = itemCrafted,
                        itemUsed = itemUsed,
                        itemPickedUp = itemPickedUp,
                        itemDropped = itemDropped
                ))
            }
        }
        if (itemLogs.size > 0) {
            itemLogRepo.multiInsert(itemLogs)
        }
    }
}