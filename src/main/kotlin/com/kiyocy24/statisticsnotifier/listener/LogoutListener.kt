package com.kiyocy24.statisticsnotifier.listener

import com.kiyocy24.statisticsnotifier.info
import org.bukkit.Statistic
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

object LogoutListener : Listener {
    @EventHandler
    fun onPlayerLogoutEvent(e: PlayerLoginEvent) {
        // Write statistic info to database
    }
}