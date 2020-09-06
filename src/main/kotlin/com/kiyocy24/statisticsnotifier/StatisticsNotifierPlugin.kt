package com.kiyocy24.statisticsnotifier

import com.kiyocy24.statisticsnotifier.listener.LoginListener
import com.kiyocy24.statisticsnotifier.listener.QuitListener
import org.bukkit.plugin.java.JavaPlugin

class StatisticsNotifierPlugin : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic

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
