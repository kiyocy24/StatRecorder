package com.github.kiyocy24.statistics_recorder.entity.db

import java.sql.Timestamp

const val INVALID_ITEM_LOG = 0

class ItemLog(
        val id: Int = INVALID_ITEM_LOG,
        val userId: Int = INVALID_USER_ID,
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