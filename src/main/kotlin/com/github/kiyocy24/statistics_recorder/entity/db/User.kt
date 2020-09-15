package com.github.kiyocy24.statistics_recorder.entity.db

import java.sql.Timestamp

class User(
        val id: Int = 0,
        val uuid: String = "",
        val name: String = "",
        val lastLogin: Timestamp = Timestamp(0),
        val createdAt: Timestamp = Timestamp(0),
        val updatedAt: Timestamp = Timestamp(0)
) {}