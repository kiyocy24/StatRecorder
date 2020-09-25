package com.github.kiyocy24.stat_recorder.repository

import com.github.kiyocy24.stat_recorder.entity.db.UserLog as DBUserLog
import com.github.kiyocy24.stat_recorder.entity.view.UserLog as ViewUserLog
import com.github.kiyocy24.stat_recorder.infrastructure.Database
import java.sql.Connection

class UserLogRepository(private val conn: Connection) {
    private val db = Database(conn).UserLog()
    fun insert(userLog: ViewUserLog) {
        db.insert(toDBUserLog(userLog))
    }

    private fun toDBUserLog(userLog: ViewUserLog) : DBUserLog {
        return DBUserLog(
                id = userLog.id,
                userId = userLog.userId,
                leaveGame = userLog.leaveGame,
                playOneMinute = userLog.playOneMinute,
                blockMined = userLog.blockMined,
                itemBroken = userLog.itemBroken,
                itemCrafted = userLog.itemCrafted,
                itemUsed = userLog.itemUsed,
                itemPickedUp = userLog.itemPickedUp,
                itemDropped = userLog.itemDropped,
        )
    }
}