package com.github.kiyocy24.stat_recorder.repository

import com.github.kiyocy24.stat_recorder.infrastructure.*
import java.sql.Connection

class DatabaseRepository(private val conn: Connection) {
    private val db = Database(conn)
    fun create() {
        db.create(CREATE_USERS)
        db.create(CREATE_ITEM_LOGS)
        db.create(CREATE_CUSTOM_LOGS)
        db.create(CREATE_KILL_LOGS)
    }
}