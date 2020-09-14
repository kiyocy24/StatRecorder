package com.github.kiyocy24.statistics_recorder.entity.db

import java.sql.Timestamp

const val INVALID_USER_ID = 0

class User(
        val id: Int = INVALID_USER_ID,
        val uuid: String = "",
        val name: String = "",
        val lastLogin: Timestamp = Timestamp(0),
        val createdAt: Timestamp = Timestamp(0),
        val updatedAt: Timestamp = Timestamp(0)
) {}