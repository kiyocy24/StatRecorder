package com.github.com.kiyocy24.statistics_recorder

import org.bukkit.Bukkit.getPluginManager
import java.sql.*

val pluginInstance : StatisticsNotifierPlugin by lazy {
    val instance = getPluginManager().getPlugin("StatisticsNotifier")
    requireNotNull(instance) { warning("Plugin instance is null.") }
    return@lazy instance as StatisticsNotifierPlugin
}
internal fun info(message : String) = pluginInstance.logger.info(message)
internal fun warning(message : String) = pluginInstance.logger.warning(message)

val mysqlConn : Connection by lazy {
    pluginInstance.saveDefaultConfig()
    val host = pluginInstance.config.getString("mysql.host")
    val port = pluginInstance.config.getInt("mysql.port")
    val database = pluginInstance.config.getString("database")
    val  username = pluginInstance.config.getString("mysql.username")
    val password = pluginInstance.config.getString("mysql.password")

    val conn = DriverManager.getConnection(
            "jdbc:mysql://$host:$port/$database",
            username,
            password
    )
    requireNotNull(conn) { warning("Database connection failed.") }
    return@lazy conn as Connection
}