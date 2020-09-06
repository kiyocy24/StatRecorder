package com.kiyocy24.statisticsnotifier

import org.bukkit.Bukkit.getPluginManager

val pluginInstance : StatisticsNotifierPlugin by lazy {
    val instance = getPluginManager().getPlugin("StatisticsNotifier")
    requireNotNull(instance) { warning("Plugin instance is null.") }
    return@lazy instance as StatisticsNotifierPlugin
}
internal fun info(message : String) = pluginInstance.logger.info(message)
internal fun warning(message : String) = pluginInstance.logger.warning(message)
