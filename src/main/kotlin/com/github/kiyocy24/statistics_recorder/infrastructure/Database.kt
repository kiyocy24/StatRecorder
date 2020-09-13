package com.github.kiyocy24.statistics_recorder.infrastructure

import com.github.kiyocy24.statistics_recorder.entity.db.User as dbUser
import java.sql.Connection
import java.sql.SQLException

class Database(private val conn: Connection) {
    fun create() {
        try {
            val sql = """
                CREATE TABLE `users` (
                  `id` int PRIMARY KEY AUTO_INCREMENT,
                  `uuid` varchar(36) NOT NULL,
                  `name` varchar(255) NOT NULL,
                  `last_login` timestamp NOT NULL,
                  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE
                );

                CREATE TABLE `statistic_logs` (
                  `id` int PRIMARY KEY AUTO_INCREMENT,
                  `user_id` varchar(32) NOT NULL,
                  `item_name` varchar(255) NOT NULL,
                  `block_mined` bigint NOT NULL,
                  `item_broken` bigint NOT NULL,
                  `item_created` bigint NOT NULL,
                  `item_used` bigint NOT NULL,
                  `item_picked_up` bigint NOT NULL,
                  `item_dropped` bigint NOT NULL,
                  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE
                );

                ALTER TABLE `statistic_logs` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
            """.trimIndent()
            val pstmt = conn.prepareStatement(sql)
            pstmt.executeUpdate()
            pstmt.close()
        } catch (e: SQLException) {
            e.stackTrace
        }
    }

    inner class User {
        fun searchByUuid(uuid: String) : dbUser {
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
                e.stackTrace
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
                e.stackTrace
            }
        }

        fun update(u: dbUser) {
            try {
                val sql = "UPDATE users (name, last_login, updated_at) values (?, ?, ?) WHERE uuid=?"
                val pstmt = conn.prepareStatement(sql)
                pstmt.setString(1, u.name)
                pstmt.setTimestamp(2, u.lastLogin)
                pstmt.setTimestamp(3, u.updatedAt)
                pstmt.setString(4, u.uuid)
                pstmt.executeUpdate()
                pstmt.close()
            } catch (e: SQLException) {
                e.stackTrace
            }
        }
    }
}