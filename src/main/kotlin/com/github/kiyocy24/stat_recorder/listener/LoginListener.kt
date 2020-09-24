package com.github.kiyocy24.stat_recorder.listener

import com.github.kiyocy24.stat_recorder.entity.view.User
import com.github.kiyocy24.stat_recorder.mysqlConn
import com.github.kiyocy24.stat_recorder.repository.UserRepository
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import java.sql.Timestamp

object LoginListener : Listener {
    @EventHandler
    fun onPlayerLoginEvent(e: PlayerLoginEvent) {
        val newUser = User(
                uuid = e.player.uniqueId.toString(),
                name = e.player.name,
                lastLogin = Timestamp(System.currentTimeMillis())
        )
        var user = UserRepository(mysqlConn).searchByUuid(e.player.uniqueId.toString())
        if (user.id == 0) {
            UserRepository(mysqlConn).insert(newUser)
            user = UserRepository(mysqlConn).searchByUuid(e.player.toString())
        }
        else {
            UserRepository(mysqlConn).update(newUser)
        }
    }
}