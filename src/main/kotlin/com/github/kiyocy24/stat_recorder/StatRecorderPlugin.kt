package com.github.kiyocy24.stat_recorder

import com.github.kiyocy24.stat_recorder.listener.LoginListener
import com.github.kiyocy24.stat_recorder.listener.QuitListener
import com.github.kiyocy24.stat_recorder.repository.DatabaseRepository
import org.bukkit.plugin.java.JavaPlugin

class StatRecorderPlugin : JavaPlugin() {

    override fun onEnable() {
        // Save default config
        saveDefaultConfig()
        info("Save config.yml")

        // Setup database
        DatabaseRepository(mysqlConn).create()
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
