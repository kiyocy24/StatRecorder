package com.github.kiyocy24.statistics_recorder

import com.github.kiyocy24.statistics_recorder.infrastructure.Database
import com.github.kiyocy24.statistics_recorder.listener.LoginListener
import com.github.kiyocy24.statistics_recorder.listener.QuitListener
import org.bukkit.plugin.java.JavaPlugin

class StatisticsRecorderPlugin : JavaPlugin() {

    override fun onEnable() {
        // Save default config
        saveDefaultConfig()
        info("Save config.yml")

        // Setup database
        Database(mysqlConn).create()
        info("Setup database")

        // Set listener
        server.pluginManager.registerEvents(LoginListener, pluginInstance)
        server.pluginManager.registerEvents(QuitListener, pluginInstance)
        info("Set listener")

        // Set command
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
