package com.github.kiyocy24.stat_recorder.repository

import com.github.kiyocy24.stat_recorder.infrastructure.CREATE_CUSTOM_LOGS
import com.github.kiyocy24.stat_recorder.infrastructure.CREATE_ITEM_LOGS
import com.github.kiyocy24.stat_recorder.infrastructure.CREATE_USERS
import com.github.kiyocy24.stat_recorder.infrastructure.Database
import java.sql.Connection

class DatabaseRepository(private val conn: Connection) {
    private val db = Database(conn)
    fun create() {
        db.create(CREATE_USERS)
        db.create(CREATE_ITEM_LOGS)
        db.create(CREATE_CUSTOM_LOGS)
    }
}