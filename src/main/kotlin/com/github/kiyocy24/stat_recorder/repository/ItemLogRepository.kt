package com.github.kiyocy24.stat_recorder.repository

import com.github.kiyocy24.stat_recorder.entity.db.ItemLog as DBItemLog
import com.github.kiyocy24.stat_recorder.entity.view.ItemLog as ViewItemLog
import com.github.kiyocy24.stat_recorder.infrastructure.Database
import java.sql.Connection

class ItemLogRepository(private val conn: Connection) {
    private val db = Database(conn).ItemLog()
    fun multiInsert(viewItemLogs: List<ViewItemLog>) {
        val dbItemLogs = mutableListOf<DBItemLog>()
        for(vItemLog in viewItemLogs) {
            dbItemLogs.add(DBItemLog(
                    userId = vItemLog.userId,
                    userLoginNum = vItemLog.userLoginNum,
                    itemId = vItemLog.itemId,
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