package com.github.kiyocy24.stat_recorder.listener

import com.github.kiyocy24.stat_recorder.entity.view.TotalItemLog
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.entity.Player
import java.sql.Timestamp
import com.github.kiyocy24.stat_recorder.entity.view.User as ViewUser

class Util {
    fun newViewUser(userId: Int = 0, player: Player) : ViewUser {
        val totalItemLog = sumItemLog(player)
        return ViewUser(
                id = userId,
                uuid = player.uniqueId.toString(),
                name = player.name,
                lastLogin = Timestamp(System.currentTimeMillis()),
                leaveGame = player.getStatistic(Statistic.LEAVE_GAME),
                playOneMinute = player.getStatistic(Statistic.PLAY_ONE_MINUTE),
                blockMined = totalItemLog.blockMined,
                itemBroken = totalItemLog.itemBroken,
                itemCrafted = totalItemLog.itemCrafted,
                iemUsed = totalItemLog.itemUsed,
                itemPickedUp = totalItemLog.itemPickedUp,
                itemDropped = totalItemLog.itemDropped,
        )
    }

    private fun sumItemLog(player: Player) : TotalItemLog {
        var blockMined = 0
        var itemBroken = 0
        var itemCrafted = 0
        var itemUsed = 0
        var itemPickedUp = 0
        var itemDropped = 0
        for ( m in Material.values() ) {
            blockMined += player.getStatistic(Statistic.MINE_BLOCK, m)
            itemBroken += player.getStatistic(Statistic.BREAK_ITEM, m)
            itemCrafted += player.getStatistic(Statistic.CRAFT_ITEM, m)
            itemUsed += player.getStatistic(Statistic.USE_ITEM, m)
            itemPickedUp += player.getStatistic(Statistic.PICKUP, m)
            itemDropped += player.getStatistic(Statistic.DROP, m)
        }
        return TotalItemLog(
                blockMined = blockMined,
                itemBroken = itemBroken,
                itemCrafted = itemCrafted,
                itemUsed = itemUsed,
                itemPickedUp = itemPickedUp,
                itemDropped = itemDropped,
        )
    }
}