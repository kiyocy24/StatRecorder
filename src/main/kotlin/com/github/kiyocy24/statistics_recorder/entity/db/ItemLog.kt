package com.github.kiyocy24.statistics_recorder.entity.db

import java.sql.Timestamp

class ItemLog(
        val id: Int = 0,
        val userId: Int = 0,
        val userLoginNum: Int = 0,
        val itemId: Int = 0,
        val name: String = "",
        val blockMined: Int = 0,
        val itemBroken: Int = 0,
        val itemCrafted: Int = 0,
        val itemUsed: Int = 0,
        val itemPickedUp: Int = 0,
        val itemDropped: Int = 0,
        val createdAt: Timestamp = Timestamp(0),
        val updatedAt: Timestamp = Timestamp(0)
) {}