package com.kiyocy24.statisticsnotifier

import com.kiyocy24.statisticsnotifier.listener.LoginListener
import com.kiyocy24.statisticsnotifier.listener.LogoutListener
import org.bukkit.plugin.java.JavaPlugin

class StatisticsNotifierPlugin : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic

<<<<<<< Updated upstream:src/main/kotlin/com/tklcraft/statisticsnotifier/StatisticsNotifierPlugin.kt
=======
        // Save default config
        saveDefaultConfig()

        // Set listener
        server.pluginManager.registerEvents(LoginListener, pluginInstance)
        server.pluginManager.registerEvents(LogoutListener, pluginInstance)

        // Set command
>>>>>>> Stashed changes:src/main/kotlin/com/kiyocy24/statisticsnotifier/StatisticsNotifierPlugin.kt
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
