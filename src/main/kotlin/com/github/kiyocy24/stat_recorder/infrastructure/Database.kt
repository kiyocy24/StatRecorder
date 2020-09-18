package com.github.kiyocy24.stat_recorder.infrastructure

import com.github.kiyocy24.stat_recorder.info
import com.github.kiyocy24.stat_recorder.warning
import com.github.kiyocy24.stat_recorder.entity.db.User as DBUser
import com.github.kiyocy24.stat_recorder.entity.db.ItemLog as DBItemLog
import com.github.kiyocy24.stat_recorder.entity.db.CustomLog as DBCustomLog
import com.github.kiyocy24.stat_recorder.entity.db.KillLog as DBKillLog
import java.sql.Connection
import java.sql.SQLException

class Database(private val conn: Connection) {
    fun create(sql: String) {
        try {
            conn.prepareStatement(sql).executeUpdate()
        } catch (e: SQLException) {
            warning(e.message)
        }
    }

    inner class User {
        fun searchByUuid(uuid: String): DBUser {
            var user = DBUser()
            try {
                val sql = "SELECT * from users WHERE uuid=?"
                val pstmt = conn.prepareStatement(sql)
                pstmt.setString(1, uuid)
                val rs = pstmt.executeQuery()

                if (rs.next()) {
                    user = DBUser(
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

        fun insert(u: DBUser) {
            try {
                val sql = "INSERT INTO users (uuid, name, last_login) VALUES (?, ?, ?)"
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

        fun update(u: DBUser) {
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
        fun multiInsert(itemLogs: List<DBItemLog>) {
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

    inner class CustomLog {
        fun insert(customLog: DBCustomLog) {
            try {
                val sql = """
                    INSERT INTO custom_logs (
                        user_id,
                        damage_dealt,
                        damage_taken,
                        deaths,
                        mob_kills,
                        player_kills,
                        fish_caught,
                        animals_bred,
                        leave_game,
                        jump,
                        drop_count,
                        play_one_minute,
                        walk_one_cm,
                        walk_on_water_one_cm,
                        fall_one_cm,
                        sneak_time,
                        climb_one_cm,
                        fly_one_cm,
                        walk_under_water_one_cm,
                        minecart_one_cm,
                        boat_one_cm,
                        pig_one_cm,
                        horse_one_cm,
                        spr_int_one_cm,
                        crouch_one_cm,
                        aviate_one_cm,
                        time_since_death,
                        talked_to_villager,
                        traded_with_villager,
                        cake_slices_eaten,
                        cauldron_filled,
                        cauldron_used,
                        armor_cleaned,
                        banner_cleaned,
                        brewingstand_interaction,
                        beacon_interaction,
                        dropper_inspected,
                        hopper_inspected,
                        dispenser_inspected,
                        noteblock_played,
                        noteblock_tuned,
                        flower_potted,
                        trapped_chest_triggered,
                        enderchest_opened,
                        item_enchanted,
                        record_played,
                        furnace_interaction,
                        crafting_table_interaction,
                        chest_opened,
                        sleep_in_bed,
                        shulker_box_opened,
                        time_since_rest,
                        swim_one_cm,
                        damage_dealt_absorbed,
                        damage_dealt_resisted,
                        damage_blocked_by_shield,
                        damage_absorbed,
                        damage_resisted,
                        clean_shulker_box,
                        open_barrel,
                        interact_with_blast_furnace,
                        interact_with_smoker,
                        interact_with_lectern,
                        interact_with_campfire,
                        interact_with_cartography_table,
                        interact_with_loom,
                        interact_with_stonecutter,
                        bell_ring,
                        raid_trigger,
                        raid_win,
                        interact_with_anvil,
                        interact_with_grindstone,
                        target_hit,
                        interact_with_smithing_table,
                        strider_one_cm
                    ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                """.trimIndent()
                val pstmt = conn.prepareStatement(sql)
                pstmt.setInt(1, customLog.userId)
                pstmt.setInt(2, customLog.damageDealt)
                pstmt.setInt(3, customLog.damageTaken)
                pstmt.setInt(4, customLog.deaths)
                pstmt.setInt(5, customLog.mobKills)
                pstmt.setInt(6, customLog.playerKills)
                pstmt.setInt(7, customLog.fishCaught)
                pstmt.setInt(8, customLog.animalsBred)
                pstmt.setInt(9, customLog.leaveGame)
                pstmt.setInt(10, customLog.jump)
                pstmt.setInt(11, customLog.dropCount)
                pstmt.setInt(12, customLog.playOneMinute)
                pstmt.setInt(13, customLog.walkOneCm)
                pstmt.setInt(14, customLog.walkOnWaterOneCm)
                pstmt.setInt(15, customLog.fallOneCm)
                pstmt.setInt(16, customLog.sneakTime)
                pstmt.setInt(17, customLog.climbOneCm)
                pstmt.setInt(18, customLog.flyOneCm)
                pstmt.setInt(19, customLog.walkUnderWaterOneCm)
                pstmt.setInt(20, customLog.minecartOneCm)
                pstmt.setInt(21, customLog.boatOneCm)
                pstmt.setInt(22, customLog.pigOneCm)
                pstmt.setInt(23, customLog.horseOneCm)
                pstmt.setInt(24, customLog.sprIntOneCm)
                pstmt.setInt(25, customLog.crouchOneCm)
                pstmt.setInt(26, customLog.aviateOneCm)
                pstmt.setInt(27, customLog.timeSinceDeath)
                pstmt.setInt(28, customLog.talkedToVillager)
                pstmt.setInt(29, customLog.tradedWithVillager)
                pstmt.setInt(30, customLog.cakeSlicesEaten)
                pstmt.setInt(31, customLog.cauldronFilled)
                pstmt.setInt(32, customLog.cauldronUsed)
                pstmt.setInt(33, customLog.armorCleaned)
                pstmt.setInt(34, customLog.bannerCleaned)
                pstmt.setInt(35, customLog.brewingstandInteraction)
                pstmt.setInt(36, customLog.beaconInteraction)
                pstmt.setInt(37, customLog.dropperInspected)
                pstmt.setInt(38, customLog.hopperInspected)
                pstmt.setInt(39, customLog.dispenserInspected)
                pstmt.setInt(40, customLog.noteblockPlayed)
                pstmt.setInt(41, customLog.noteblockTuned)
                pstmt.setInt(42, customLog.flowerPotted)
                pstmt.setInt(43, customLog.trappedChestTriggered)
                pstmt.setInt(44, customLog.enderchestOpened)
                pstmt.setInt(45, customLog.itemEnchanted)
                pstmt.setInt(46, customLog.recordPlayed)
                pstmt.setInt(47, customLog.furnaceInteraction)
                pstmt.setInt(48, customLog.craftingTableInteraction)
                pstmt.setInt(49, customLog.chestOpened)
                pstmt.setInt(50, customLog.sleepInBed)
                pstmt.setInt(51, customLog.shulkerBoxOpened)
                pstmt.setInt(52, customLog.timeSinceRest)
                pstmt.setInt(53, customLog.swimOneCm)
                pstmt.setInt(54, customLog.damageDealtAbsorbed)
                pstmt.setInt(55, customLog.damageDealtResisted)
                pstmt.setInt(56, customLog.damageBlockedByShield)
                pstmt.setInt(57, customLog.damageAbsorbed)
                pstmt.setInt(58, customLog.damageResisted)
                pstmt.setInt(59, customLog.cleanShulkerBox)
                pstmt.setInt(60, customLog.openBarrel)
                pstmt.setInt(61, customLog.interactWithBlastFurnace)
                pstmt.setInt(62, customLog.interactWithSmoker)
                pstmt.setInt(63, customLog.interactWithLectern)
                pstmt.setInt(64, customLog.interactWithCampfire)
                pstmt.setInt(65, customLog.interactWithCartographyTable)
                pstmt.setInt(66, customLog.interactWithLoom)
                pstmt.setInt(67, customLog.interactWithStonecutter)
                pstmt.setInt(68, customLog.bellRing)
                pstmt.setInt(69, customLog.raidTrigger)
                pstmt.setInt(70, customLog.raidWin)
                pstmt.setInt(71, customLog.interactWithAnvil)
                pstmt.setInt(72, customLog.interactWithGrindstone)
                pstmt.setInt(73, customLog.targetHit)
                pstmt.setInt(74, customLog.interactWithSmithingTable)
                pstmt.setInt(75, customLog.striderOneCm)
                pstmt.executeUpdate()
                pstmt.close()
            } catch (e: SQLException) {
                warning(e.message)
            }
        }
    }

    inner class KillLog {
        fun insert(killLog: DBKillLog) {
            try {
                val sql = """
                    INSERT INTO kill_logs (
                       user_id,
                       user_login_num,
                       bat,
                       blaze,
                       caveSpider,
                       chicken,
                       cow,
                       creeper,
                       drowned,
                       enderman,
                       endermite,
                       ghast,
                       husk,
                       MagmaCube,
                       phantom,
                       pig,
                       piglin,
                       pillager,
                       sheep,
                       shulker,
                       silverfish,
                       skeleton,
                       slime,
                       snowman,
                       spider,
                       squid,
                       villager,
                       witch,
                       wither,
                       wither_skelton,
                       wolf,
                       zombie,
                       zombieVillager,
                       zombie_piglin
                    ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                    """.trimIndent()
                val pstmt = conn.prepareStatement(sql)
                pstmt.setInt(1, killLog.userId)
                pstmt.setInt(2, killLog.userLoginNum)
                pstmt.setInt(3, killLog.bat)
                pstmt.setInt(4, killLog.blaze)
                pstmt.setInt(5, killLog.caveSpider)
                pstmt.setInt(6, killLog.chicken)
                pstmt.setInt(7, killLog.cow)
                pstmt.setInt(8, killLog.creeper)
                pstmt.setInt(9, killLog.drowned)
                pstmt.setInt(10, killLog.enderman)
                pstmt.setInt(11, killLog.endermite)
                pstmt.setInt(12, killLog.ghast)
                pstmt.setInt(13, killLog.husk)
                pstmt.setInt(14, killLog.magmaCube)
                pstmt.setInt(15, killLog.phantom)
                pstmt.setInt(16, killLog.pig)
                pstmt.setInt(17, killLog.piglin)
                pstmt.setInt(18, killLog.pillager)
                pstmt.setInt(19, killLog.sheep)
                pstmt.setInt(20, killLog.shulker)
                pstmt.setInt(21, killLog.silverfish)
                pstmt.setInt(22, killLog.skeleton)
                pstmt.setInt(23, killLog.slime)
                pstmt.setInt(24, killLog.snowman)
                pstmt.setInt(25, killLog.spider)
                pstmt.setInt(26, killLog.squid)
                pstmt.setInt(27, killLog.villager)
                pstmt.setInt(28, killLog.witch)
                pstmt.setInt(29, killLog.wither)
                pstmt.setInt(30, killLog.witherSkelton)
                pstmt.setInt(31, killLog.wolf)
                pstmt.setInt(32, killLog.zombie)
                pstmt.setInt(33, killLog.zombieVillager)
                pstmt.setInt(34, killLog.zombiePiglin)
                pstmt.executeUpdate()
                pstmt.close()
            } catch (e: SQLException) {
                warning(e.message)
            }
        }
    }
}