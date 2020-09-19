package com.github.kiyocy24.stat_recorder.repository

import com.github.kiyocy24.stat_recorder.entity.db.User as DBUser
import com.github.kiyocy24.stat_recorder.entity.view.User as ViewUser
import com.github.kiyocy24.stat_recorder.infrastructure.Database
import java.sql.Connection

class UserRepository(private val conn: Connection) {
    private val db = Database(conn).User()
    fun searchByUuid(uuid: String) : ViewUser {
        val dbUser = db.searchByUuid(uuid)
        return  ViewUser(
                id = dbUser.id,
                uuid = dbUser.uuid,
                name = dbUser.name,
                lastLogin = dbUser.lastLogin
        )
    }

    fun insert(viewUser: ViewUser) {
        val dbUser = DBUser(
                uuid = viewUser.uuid,
                name = viewUser.name,
                lastLogin = viewUser.lastLogin
        )
        db.insert(dbUser)
    }

    fun update(viewUser: ViewUser) {
        val dbUser = DBUser(
                uuid = viewUser.uuid,
                name = viewUser.name,
                lastLogin = viewUser.lastLogin
        )
        db.update(dbUser)
    }
}