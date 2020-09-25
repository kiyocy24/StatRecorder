package com.github.kiyocy24.stat_recorder.repository

import com.github.kiyocy24.stat_recorder.entity.db.User as DBUser
import com.github.kiyocy24.stat_recorder.entity.view.User as ViewUser
import com.github.kiyocy24.stat_recorder.infrastructure.Database
import java.sql.Connection

class UserRepository(private val conn: Connection) {
    private val db = Database(conn).User()
    fun searchByUuid(uuid: String) : ViewUser {
        val dbUser = db.searchByUuid(uuid)
        return  dbUserToViewUser(dbUser)
    }

    fun insert(viewUser: ViewUser) {
        db.insert(viewUser.toDBUser())
    }

    fun update(viewUser: ViewUser) {
        db.update(viewUser.toDBUser())
    }

    private fun dbUserToViewUser(user: DBUser) : ViewUser {
        return ViewUser(
                id = user.id,
                uuid = user.uuid,
                name = user.name,
                lastLogin = user.lastLogin,
                leaveGame = user.leaveGame,
                playOneMinute = user.playOneMinute,
                blockMined = user.blockMined,
                itemBroken = user.itemBroken,
                itemCrafted = user.itemCrafted,
                iemUsed = user.itemUsed,
                itemPickedUp = user.itemPickedUp,
                itemDropped = user.itemDropped,
        )
    }
}