package com.github.kiyocy24.statistics_recorder.entity.view

import com.github.kiyocy24.statistics_recorder.entity.db.INVALID_USER_ID
import java.sql.Timestamp

class User(
        val id: Int = INVALID_USER_ID,
        val uuid: String = "",
        val name: String = "",
        val lastLogin: Timestamp = Timestamp(0)
) {}