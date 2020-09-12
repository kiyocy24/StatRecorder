package com.github.com.kiyocy24.statistics_recorder.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

object LoginListener : Listener {
    @EventHandler
    fun onPlayerLoginEvent(e: PlayerLoginEvent) {
        // Check user registered
    }
}