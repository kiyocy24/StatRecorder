package com.github.kiyocy24.statistics_recorder.repository

import com.github.kiyocy24.statistics_recorder.entity.db.ItemLog as dbItemLog
import com.github.kiyocy24.statistics_recorder.entity.view.ItemLog as vItemLog
import com.github.kiyocy24.statistics_recorder.infrastructure.Database
import java.sql.Connection

class ItemLogRepository(private val conn: Connection) {
    private val db = Database(conn).ItemLog()
    fun multiInsert(vItemLogs: List<vItemLog>) {
        val dbItemLogs = mutableListOf<dbItemLog>()
        for(vItemLog in vItemLogs) {
            dbItemLogs.add(dbItemLog(
                    userId = vItemLog.userId,
                    name = vItemLog.name,
                    blockMined = vItemLog.blockMined,
                    itemBroken = vItemLog.itemBroken,
                    itemCrafted = vItemLog.itemCrafted,
                    itemUsed = vItemLog.itemUsed,
                    itemPickedUp = vItemLog.itemPickedUp,
                    itemDropped = vItemLog.itemDropped
            ))
        }
        db.multiInsert(dbItemLogs)
    }
}