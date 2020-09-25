package com.github.kiyocy24.stat_recorder.entity.view

import java.sql.Timestamp
import com.github.kiyocy24.stat_recorder.entity.db.User as DBUser

class User(
        val id: Int = 0,
        val uuid: String = "",
        val name: String = "",
        val lastLogin: Timestamp = Timestamp(0),
        val leaveGame: Int = 0,
        val playOneMinute: Int = 0,

        // Total Item log
        val blockMined: Int = 0,
        val itemBroken: Int = 0,
        val itemCrafted: Int = 0,
        val iemUsed: Int = 0,
        val itemPickedUp: Int = 0,
        val itemDropped: Int = 0,
) {
    fun toDBUser() : DBUser {
        return DBUser(
                id = this.id,
                uuid = this.uuid,
                name = this.name,
                lastLogin = this.lastLogin,
                leaveGame = this.leaveGame,
                playOneMinute = this.playOneMinute,
                blockMined = this.blockMined,
                itemBroken = this.itemBroken,
                itemCrafted = this.itemCrafted,
                itemUsed = this.iemUsed,
                itemPickedUp = this.itemPickedUp,
                itemDropped = this.itemDropped,
        )
    }
}