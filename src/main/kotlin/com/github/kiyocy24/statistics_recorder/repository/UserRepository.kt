package com.github.kiyocy24.statistics_recorder.repository

import com.github.kiyocy24.statistics_recorder.entity.db.User as dbUser
import com.github.kiyocy24.statistics_recorder.entity.view.User as vUser
import com.github.kiyocy24.statistics_recorder.infrastructure.Database
import java.sql.Connection

class UserRepository() {
    fun searchByUuid(conn: Connection, uuid: String) : vUser {
        val dbUser = Database(conn).User().searchByUuid(uuid)
        return  vUser(
                id = dbUser.id,
                uuid = dbUser.uuid,
                name = dbUser.name,
                lastLogin = dbUser.lastLogin
        )
    }

    fun insert(conn: Connection, user: vUser) {
        val dbUser = dbUser(
                uuid = user.uuid,
                name = user.name,
                lastLogin = user.lastLogin
        )
        Database(conn).User().insert(dbUser)
    }

    fun update(conn: Connection, user: vUser) {
        val dbUser = dbUser(
                uuid = user.uuid,
                name = user.name,
                lastLogin = user.lastLogin
        )
        Database(conn).User().update(dbUser)
    }
}