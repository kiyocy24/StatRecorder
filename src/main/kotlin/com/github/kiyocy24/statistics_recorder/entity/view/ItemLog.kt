package com.github.kiyocy24.statistics_recorder.entity.view

class StatisticLog(
        val userId: Int,
        val itemName: String,
        val blockMined: Long,
        val itemBroken: Long,
        val itemCreated: Long,
        val itemUsed: Long,
        val itemPickedUp: Long,
        val itemDropped: Long
) {}