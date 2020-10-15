package com.github.kiyocy24.stat_recorder.listener

import com.github.kiyocy24.stat_recorder.mysqlConn
import com.github.kiyocy24.stat_recorder.repository.UserRepository
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

object LoginListener : Listener {
    @EventHandler
    fun onPlayerLoginEvent(e: PlayerLoginEvent) {
        val user = UserRepository(mysqlConn).searchByUuid(e.player.uniqueId.toString())
        val updateUser = Util().newViewUser(player = e.player)
        if (user.id == 0) {
            UserRepository(mysqlConn).insert(updateUser)
        }
        else {
            UserRepository(mysqlConn).update(updateUser)
        }
    }
}