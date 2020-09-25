package com.github.kiyocy24.stat_recorder.entity.db

import java.sql.Timestamp

class User(
        val id: Int = 0,
        val uuid: String = "",
        val name: String = "",
        val lastLogin: Timestamp = Timestamp(0),
        val leaveGame: Int = 0,
        val playOneMinute: Int = 0,

        // Total Item log
        val totalBlockMined: Int = 0,
        val totalItemBroken: Int = 0,
        val totalItemCrafted: Int = 0,
        val totalItemUsed: Int = 0,
        val totalItemPickedUp: Int = 0,
        val totalItemDropped: Int = 0,

        val createdAt: Timestamp = Timestamp(0),
        val updatedAt: Timestamp = Timestamp(0)
) {}