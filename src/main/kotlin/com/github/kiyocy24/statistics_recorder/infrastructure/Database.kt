package com.github.kiyocy24.statistics_recorder.infrastructure

import com.github.kiyocy24.statistics_recorder.entity.db.ItemLog
import com.github.kiyocy24.statistics_recorder.warning
import com.github.kiyocy24.statistics_recorder.entity.db.User as dbUser
import java.sql.Connection
import java.sql.SQLException
import kotlin.math.log

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

    inner class Item {
        fun insert(userId: Int, logs: List<ItemLog>) {
            try {
                var sql =  "INSERT INTO item_logs (user_id, item_name, block_mined, item_broken, item_crafted, item_used, item_picked_up, item_dropped) VALUES "
                for (i in 0..logs.size) {
                    sql += "(?, ?, ?, ?, ?, ?, ?, ?, ?),"
                }
                sql.substring(sql.length - 1)

                val pstmt = conn.prepareStatement(sql)
                for (i in 0..logs.size) {
                    pstmt.setInt(i+1, logs[i].userId)
                    pstmt.setString(i+2, logs[i].name)
                    pstmt.setInt(i+3, logs[i].blockMined)
                    pstmt.setInt(i+4, logs[i].itemBroken)
                    pstmt.setInt(i+5, logs[i].itemCrafted)
                    pstmt.setInt(i+6, logs[i].itemUsed)
                    pstmt.setInt(i+7, logs[i].itemUsed)
                    pstmt.setInt(i+8, logs[i].itemPickedUp)
                }
                pstmt.executeUpdate()
                pstmt.close()
            } catch (e: SQLException) {
                warning(e.message)
            }
        }
    }
}