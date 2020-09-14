package com.github.kiyocy24.statistics_recorder.repository

import com.github.kiyocy24.statistics_recorder.entity.db.User as dbUser
import com.github.kiyocy24.statistics_recorder.entity.view.User as vUser
import com.github.kiyocy24.statistics_recorder.infrastructure.Database
import java.sql.Connection

class UserRepository(private val conn: Connection) {
    private val db = Database(conn).User()
    fun searchByUuid(uuid: String) : vUser {
        val dbUser = db.searchByUuid(uuid)
        return  vUser(
                id = dbUser.id,
                uuid = dbUser.uuid,
                name = dbUser.name,
                lastLogin = dbUser.lastLogin
        )
    }

    fun insert(user: vUser) {
        val dbUser = dbUser(
                uuid = user.uuid,
                name = user.name,
                lastLogin = user.lastLogin
        )
        db.insert(dbUser)
    }

    fun update(user: vUser) {
        val dbUser = dbUser(
                uuid = user.uuid,
                name = user.name,
                lastLogin = user.lastLogin
        )
        db.update(dbUser)
    }
}