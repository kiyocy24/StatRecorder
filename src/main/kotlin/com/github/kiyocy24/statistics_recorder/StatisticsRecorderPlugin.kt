package com.github.kiyocy24.statistics_recorder

import com.github.kiyocy24.statistics_recorder.listener.LoginListener
import com.github.kiyocy24.statistics_recorder.listener.QuitListener
import org.bukkit.plugin.java.JavaPlugin

class StatisticsRecorderPlugin : JavaPlugin() {

    override fun onEnable() {
        // Save default config
        saveDefaultConfig()

        // Set listener
        server.pluginManager.registerEvents(LoginListener, pluginInstance)
        server.pluginManager.registerEvents(QuitListener, pluginInstance)

        // Set command
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
