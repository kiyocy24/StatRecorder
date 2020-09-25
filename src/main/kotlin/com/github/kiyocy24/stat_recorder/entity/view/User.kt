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
        val totalBlockMined: Int = 0,
        val totalItemBroken: Int = 0,
        val totalItemCrafted: Int = 0,
        val totalIemUsed: Int = 0,
        val totalItemPickedUp: Int = 0,
        val totalItemDropped: Int = 0,
) {
    fun toDBUser() : DBUser {
        return DBUser(
                id = this.id,
                uuid = this.uuid,
                name = this.name,
                lastLogin = this.lastLogin,
                leaveGame = this.leaveGame,
                playOneMinute = this.playOneMinute,
                totalBlockMined = this.totalBlockMined,
                totalItemBroken = this.totalItemBroken,
                totalItemCrafted = this.totalItemCrafted,
                totalItemUsed = this.totalIemUsed,
                totalItemPickedUp = this.totalItemPickedUp,
                totalItemDropped = this.totalItemDropped,
        )
    }
}