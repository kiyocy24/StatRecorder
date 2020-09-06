package com.kiyocy24.statisticsnotifier.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object QuitListener : Listener {
    @EventHandler
    fun onPlayerQuitEvent(e: PlayerQuitEvent) {
        // Date login log
    }
}