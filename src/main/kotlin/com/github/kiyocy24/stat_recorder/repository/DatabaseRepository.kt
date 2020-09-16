package com.github.kiyocy24.stat_recorder.repository

import com.github.kiyocy24.stat_recorder.infrastructure.Database
import java.sql.Connection

class DatabaseRepository(private val conn: Connection) {
    private val db = Database(conn)
    fun create() {
        db.create()
    }
}