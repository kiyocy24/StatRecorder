package com.github.kiyocy24.stat_recorder.infrastructure

import com.github.kiyocy24.stat_recorder.info
import com.github.kiyocy24.stat_recorder.warning
import com.github.kiyocy24.stat_recorder.entity.db.User as dbUser
import com.github.kiyocy24.stat_recorder.entity.db.ItemLog as dbItemLog
import java.sql.Connection
import java.sql.SQLException

class Database(private val conn: Connection) {
    fun create() {
        try {
            conn.prepareStatement(CREATE_USERS).executeUpdate()
            conn.prepareStatement(CREATE_ITEM_LOGS).executeUpdate()
        } catch (e: SQLException) {
            warning(e.message)
        }
    }

    inner class User {
        fun searchByUuid(uuid: String): dbUser {
            var user = dbUser()
            try {
                val sql = "SELECT * from users WHERE uuid=?"
                val pstmt = conn.prepareStatement(sql)
                pstmt.setString(1, uuid)
                val rs = pstmt.executeQuery()

                if (rs.next()) {
                    user = dbUser(
                            id = rs.getInt("id"),
                            uuid = rs.getString("uuid"),
                            name = rs.getString("name"),
                            lastLogin = rs.getTimestamp("last_login"),
                            createdAt = rs.getTimestamp("created_at"),
                            updatedAt = rs.getTimestamp("updated_at")
                    )
                }
                rs.close()
                pstmt.close()
            } catch (e: SQLException) {
                warning(e.message)
            }
            return user
        }

        fun insert(u: dbUser) {
            try {
                val sql = "INSERT INTO users (uuid, name, last_login) values (?, ?, ?)"
                val pstmt = conn.prepareStatement(sql)
                pstmt.setString(1, u.uuid)
                pstmt.setString(2, u.name)
                pstmt.setTimestamp(3, u.lastLogin)
                pstmt.executeUpdate()
                pstmt.close()
            } catch (e: SQLException) {
                warning(e.message)
            }
        }

        fun update(u: dbUser) {
            try {
                val sql = "UPDATE users SET name=?, last_login=? WHERE uuid = ?"
                val pstmt = conn.prepareStatement(sql)
                pstmt.setString(1, u.name)
                pstmt.setTimestamp(2, u.lastLogin)
                pstmt.setString(3, u.uuid)
                pstmt.executeUpdate()
                pstmt.close()
            } catch (e: SQLException) {
                warning(e.message)
            }
        }
    }

    inner class ItemLog {
        fun multiInsert(itemLogs: List<dbItemLog>) {
            try {
                var sql =  "INSERT INTO item_logs (user_id, user_login_num, item_id, item_name, block_mined, item_broken, item_crafted, item_used, item_picked_up, item_dropped) VALUES "
                for (i in itemLogs.indices) {
                    sql += "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?),"
                }
                sql = sql.removeSuffix(",")
                info("Item log size: ${itemLogs.size}")
                val pstmt = conn.prepareStatement(sql)
                for (i in itemLogs.indices) {
                    val j = i*10
                    pstmt.setInt(j + 1, itemLogs[i].userId)
                    pstmt.setInt(j + 2, itemLogs[i].userLoginNum)
                    pstmt.setInt(j + 3, itemLogs[i].itemId)
                    pstmt.setString(j + 4, itemLogs[i].name)
                    pstmt.setInt(j + 5, itemLogs[i].blockMined)
                    pstmt.setInt(j+ 6, itemLogs[i].itemBroken)
                    pstmt.setInt(j + 7, itemLogs[i].itemCrafted)
                    pstmt.setInt(j + 8, itemLogs[i].itemUsed)
                    pstmt.setInt(j + 9, itemLogs[i].itemPickedUp)
                    pstmt.setInt(j + 10, itemLogs[i].itemDropped)
                }
                pstmt.executeUpdate()
                pstmt.close()
            } catch (e: SQLException) {
                warning(e.message)
            }
        }
    }
}