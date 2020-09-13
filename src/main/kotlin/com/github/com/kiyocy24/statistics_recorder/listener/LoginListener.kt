package com.github.com.kiyocy24.statistics_recorder.listener

import com.github.com.kiyocy24.statistics_recorder.entity.db.INVALID_USER_ID
import com.github.com.kiyocy24.statistics_recorder.entity.view.User
import com.github.com.kiyocy24.statistics_recorder.mysqlConn
import com.github.com.kiyocy24.statistics_recorder.repository.UserRepository
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import java.sql.Timestamp

object LoginListener : Listener {
    @EventHandler
    fun onPlayerLoginEvent(e: PlayerLoginEvent) {
        val user = UserRepository().searchByUuid(mysqlConn, e.player.uniqueId.toString())
        val newUser = User(
                uuid = e.player.uniqueId.toString(),
                name = e.player.name,
                lastLogin = Timestamp(System.currentTimeMillis())
        )
        if (user.id == INVALID_USER_ID) {
            UserRepository().insert(mysqlConn, newUser)
        }
        else {
            UserRepository().update(mysqlConn, newUser)
        }
    }
}