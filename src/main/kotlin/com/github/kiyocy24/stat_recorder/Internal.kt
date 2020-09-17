package com.github.kiyocy24.stat_recorder

import org.bukkit.Bukkit.getPluginManager
import java.sql.*

val pluginInstance : StatRecorderPlugin by lazy {
    val instance = getPluginManager().getPlugin("StatRecorder")
    requireNotNull(instance) { warning("Plugin instance is null.") }
    return@lazy instance as StatRecorderPlugin
}
internal fun info(message : String?) = pluginInstance.logger.info(message)
internal fun warning(message : String?) = pluginInstance.logger.warning(message)

val mysqlConn : Connection by lazy {
    pluginInstance.saveDefaultConfig()
    val host = pluginInstance.config.getString("mysql.host")
    val port = pluginInstance.config.getInt("mysql.port")
    val database = pluginInstance.config.getString("mysql.database")
    val username = pluginInstance.config.getString("mysql.username")
    val password = pluginInstance.config.getString("mysql.password")

    val conn = try {
        DriverManager.getConnection(
                "jdbc:mysql://$host:$port/$database?autoReconnect=true&useSSL=false",
                username,
                password
        )
    } catch (e: SQLException) {
        warning(e.message)
    }
    requireNotNull(conn) { warning("Database connection failed.") }
    return@lazy conn as Connection
}