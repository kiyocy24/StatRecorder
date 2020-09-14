package com.github.kiyocy24.statistics_recorder.listener

import com.github.kiyocy24.statistics_recorder.entity.view.User
import com.github.kiyocy24.statistics_recorder.mysqlConn
import com.github.kiyocy24.statistics_recorder.repository.UserRepository
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.sql.Timestamp

object QuitListener : Listener {
    @EventHandler
    fun onPlayerQuitEvent(e: PlayerQuitEvent) {
        val user = User(
                uuid = e.player.uniqueId.toString(),
                name = e.player.name,
                lastLogin = Timestamp(System.currentTimeMillis())
        )
        UserRepository(mysqlConn).update(user)
    }
}