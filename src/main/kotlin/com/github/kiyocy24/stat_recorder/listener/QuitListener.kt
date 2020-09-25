package com.github.kiyocy24.stat_recorder.listener

import com.github.kiyocy24.stat_recorder.entity.view.*
import com.github.kiyocy24.stat_recorder.info
import com.github.kiyocy24.stat_recorder.mysqlConn
import com.github.kiyocy24.stat_recorder.repository.*
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.sql.Timestamp

object QuitListener : Listener {
    private val userRepo = UserRepository(mysqlConn)
    private val userLogRepo = UserLogRepository(mysqlConn)
    private val itemLogRepo = ItemLogRepository(mysqlConn)
    private val customLogRepo = CustomLogRepository(mysqlConn)
    private val killLogRepo = KillLogRepository(mysqlConn)

    @EventHandler
    fun onPlayerQuitEvent(e: PlayerQuitEvent) {
        val user = userRepo.searchByUuid(e.player.uniqueId.toString())
        if (user.id == 0) {
            return
        }

        recordUser(e.player)
        recordUserLog(e.player, user)
        recordItemLog(e.player, user.id)
        recordCustomLog(e.player, user.id)
        recordKillLog(e.player, user.id)
        recordKilledLog(e.player, user.id)
    }

    private fun recordUser(player: Player) {
        userRepo.update(Util().newViewUser(player = player))
    }

    private fun recordUserLog(player: Player, previousUser: User) {
        val totalItemLog = Util().sumItemLog(player)
        val userLog = UserLog(
                userId = previousUser.id,
                leaveGame = player.getStatistic(Statistic.LEAVE_GAME),
                playOneMinute = player.getStatistic(Statistic.PLAY_ONE_MINUTE) - previousUser.playOneMinute,
                blockMined = totalItemLog.blockMined - previousUser.blockMined,
                itemBroken = totalItemLog.itemBroken - previousUser.itemBroken,
                itemCrafted = totalItemLog.itemCrafted - previousUser.itemCrafted,
                itemUsed = totalItemLog.itemUsed - previousUser.itemUsed,
                itemPickedUp = totalItemLog.itemPickedUp - previousUser.itemPickedUp,
                itemDropped = totalItemLog.itemDropped - previousUser.itemDropped,
        )
        userLogRepo.insert(userLog)
    }

    private fun recordItemLog(player: Player, userId: Int) {
        val userLoginNum = player.getStatistic(Statistic.LEAVE_GAME)
        val itemLogs = mutableListOf<ItemLog>()
        for (m in Material.values()) {
            val blockMined = player.getStatistic(Statistic.MINE_BLOCK, m)
            val itemBroken = player.getStatistic(Statistic.BREAK_ITEM, m)
            val itemCrafted = player.getStatistic(Statistic.CRAFT_ITEM, m)
            val itemUsed = player.getStatistic(Statistic.USE_ITEM, m)
            val itemPickedUp = player.getStatistic(Statistic.PICKUP, m)
            val itemDropped = player.getStatistic(Statistic.DROP, m)

            if (blockMined > 0 || itemBroken > 0 || itemCrafted > 0 ||
                    itemUsed > 0 || itemPickedUp > 0 || itemDropped > 0) {
                itemLogs.add(ItemLog(
                        userId = userId,
                        userLoginNum = userLoginNum,
                        itemId = m.ordinal,
                        name = m.name,
                        blockMined = blockMined,
                        itemBroken = itemBroken,
                        itemCrafted = itemCrafted,
                        itemUsed = itemUsed,
                        itemPickedUp = itemPickedUp,
                        itemDropped = itemDropped
                ))
            }
        }
        if (itemLogs.size > 0) {
            itemLogRepo.multiInsert(itemLogs)
        }
    }

    private fun recordCustomLog(player: Player, userId: Int) {
        val customLog = CustomLog(
                userId = userId,
                damageDealt = player.getStatistic(Statistic.DAMAGE_DEALT),
                damageTaken = player.getStatistic(Statistic.DAMAGE_TAKEN),
                deaths = player.getStatistic(Statistic.DEATHS),
                mobKills = player.getStatistic(Statistic.MOB_KILLS),
                playerKills = player.getStatistic(Statistic.PLAYER_KILLS),
                fishCaught = player.getStatistic(Statistic.FISH_CAUGHT),
                animalsBred = player.getStatistic(Statistic.ANIMALS_BRED),
                leaveGame = player.getStatistic(Statistic.LEAVE_GAME),
                jump = player.getStatistic(Statistic.JUMP),
                dropCount = player.getStatistic(Statistic.DROP_COUNT),
                playOneMinute = player.getStatistic(Statistic.PLAY_ONE_MINUTE),
                walkOneCm = player.getStatistic(Statistic.WALK_ONE_CM),
                walkOnWaterOneCm = player.getStatistic(Statistic.WALK_ON_WATER_ONE_CM),
                fallOneCm = player.getStatistic(Statistic.FALL_ONE_CM),
                sneakTime = player.getStatistic(Statistic.SNEAK_TIME),
                climbOneCm = player.getStatistic(Statistic.CLIMB_ONE_CM),
                flyOneCm = player.getStatistic(Statistic.FLY_ONE_CM),
                walkUnderWaterOneCm = player.getStatistic(Statistic.WALK_UNDER_WATER_ONE_CM),
                minecartOneCm = player.getStatistic(Statistic.MINECART_ONE_CM),
                boatOneCm = player.getStatistic(Statistic.BOAT_ONE_CM),
                pigOneCm = player.getStatistic(Statistic.PIG_ONE_CM),
                horseOneCm = player.getStatistic(Statistic.HORSE_ONE_CM),
                sprIntOneCm = player.getStatistic(Statistic.SPRINT_ONE_CM),
                crouchOneCm = player.getStatistic(Statistic.CROUCH_ONE_CM),
                aviateOneCm = player.getStatistic(Statistic.AVIATE_ONE_CM),
                timeSinceDeath = player.getStatistic(Statistic.TIME_SINCE_DEATH),
                talkedToVillager = player.getStatistic(Statistic.TALKED_TO_VILLAGER),
                tradedWithVillager = player.getStatistic(Statistic.TRADED_WITH_VILLAGER),
                cakeSlicesEaten = player.getStatistic(Statistic.CAKE_SLICES_EATEN),
                cauldronFilled = player.getStatistic(Statistic.CAULDRON_FILLED),
                cauldronUsed = player.getStatistic(Statistic.CAULDRON_USED),
                armorCleaned = player.getStatistic(Statistic.ARMOR_CLEANED),
                bannerCleaned = player.getStatistic(Statistic.BANNER_CLEANED),
                brewingstandInteraction = player.getStatistic(Statistic.BREWINGSTAND_INTERACTION),
                beaconInteraction = player.getStatistic(Statistic.BEACON_INTERACTION),
                dropperInspected = player.getStatistic(Statistic.DROPPER_INSPECTED),
                hopperInspected = player.getStatistic(Statistic.HOPPER_INSPECTED),
                dispenserInspected = player.getStatistic(Statistic.DISPENSER_INSPECTED),
                noteblockPlayed = player.getStatistic(Statistic.NOTEBLOCK_PLAYED),
                noteblockTuned = player.getStatistic(Statistic.NOTEBLOCK_TUNED),
                flowerPotted = player.getStatistic(Statistic.FLOWER_POTTED),
                trappedChestTriggered = player.getStatistic(Statistic.TRAPPED_CHEST_TRIGGERED),
                enderchestOpened = player.getStatistic(Statistic.ENDERCHEST_OPENED),
                itemEnchanted = player.getStatistic(Statistic.ITEM_ENCHANTED),
                recordPlayed = player.getStatistic(Statistic.RECORD_PLAYED),
                furnaceInteraction = player.getStatistic(Statistic.FURNACE_INTERACTION),
                craftingTableInteraction = player.getStatistic(Statistic.CRAFTING_TABLE_INTERACTION),
                chestOpened = player.getStatistic(Statistic.CHEST_OPENED),
                sleepInBed = player.getStatistic(Statistic.SLEEP_IN_BED),
                shulkerBoxOpened = player.getStatistic(Statistic.SHULKER_BOX_OPENED),
                timeSinceRest = player.getStatistic(Statistic.TIME_SINCE_REST),
                swimOneCm = player.getStatistic(Statistic.SWIM_ONE_CM),
                damageDealtAbsorbed = player.getStatistic(Statistic.DAMAGE_DEALT_ABSORBED),
                damageDealtResisted = player.getStatistic(Statistic.DAMAGE_DEALT_RESISTED),
                damageBlockedByShield = player.getStatistic(Statistic.DAMAGE_BLOCKED_BY_SHIELD),
                damageAbsorbed = player.getStatistic(Statistic.DAMAGE_ABSORBED),
                damageResisted = player.getStatistic(Statistic.DAMAGE_RESISTED),
                cleanShulkerBox = player.getStatistic(Statistic.CLEAN_SHULKER_BOX),
                openBarrel = player.getStatistic(Statistic.OPEN_BARREL),
                interactWithBlastFurnace = player.getStatistic(Statistic.INTERACT_WITH_BLAST_FURNACE),
                interactWithSmoker = player.getStatistic(Statistic.INTERACT_WITH_SMOKER),
                interactWithLectern = player.getStatistic(Statistic.INTERACT_WITH_LECTERN),
                interactWithCampfire = player.getStatistic(Statistic.INTERACT_WITH_CAMPFIRE),
                interactWithCartographyTable = player.getStatistic(Statistic.INTERACT_WITH_CARTOGRAPHY_TABLE),
                interactWithLoom = player.getStatistic(Statistic.INTERACT_WITH_LOOM),
                interactWithStonecutter = player.getStatistic(Statistic.INTERACT_WITH_STONECUTTER),
                bellRing = player.getStatistic(Statistic.BELL_RING),
                raidTrigger = player.getStatistic(Statistic.RAID_TRIGGER),
                raidWin = player.getStatistic(Statistic.RAID_WIN),
                interactWithAnvil = player.getStatistic(Statistic.INTERACT_WITH_ANVIL),
                interactWithGrindstone = player.getStatistic(Statistic.INTERACT_WITH_GRINDSTONE),
                targetHit = player.getStatistic(Statistic.TARGET_HIT),
                interactWithSmithingTable = player.getStatistic(Statistic.INTERACT_WITH_SMITHING_TABLE),
                striderOneCm = player.getStatistic(Statistic.STRIDER_ONE_CM)
        )
        customLogRepo.insert(customLog)
    }

    private fun recordKillLog(player: Player, userId: Int) {
        val killLog = KillLog(
                userId = userId,
                userLoginNum = player.getStatistic(Statistic.LEAVE_GAME),
                bat = player.getStatistic(Statistic.KILL_ENTITY, EntityType.BAT),
                blaze = player.getStatistic(Statistic.KILL_ENTITY, EntityType.BLAZE),
                caveSpider = player.getStatistic(Statistic.KILL_ENTITY, EntityType.CAVE_SPIDER),
                chicken = player.getStatistic(Statistic.KILL_ENTITY, EntityType.CHICKEN),
                cow = player.getStatistic(Statistic.KILL_ENTITY, EntityType.COW),
                creeper = player.getStatistic(Statistic.KILL_ENTITY, EntityType.CREEPER),
                drowned = player.getStatistic(Statistic.KILL_ENTITY, EntityType.DROWNED),
                enderman = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ENDERMAN),
                endermite = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ENDERMITE),
                ghast = player.getStatistic(Statistic.KILL_ENTITY, EntityType.GHAST),
                husk = player.getStatistic(Statistic.KILL_ENTITY, EntityType.HUSK),
                magmaCube = player.getStatistic(Statistic.KILL_ENTITY, EntityType.MAGMA_CUBE),
                phantom = player.getStatistic(Statistic.KILL_ENTITY, EntityType.PHANTOM),
                pig = player.getStatistic(Statistic.KILL_ENTITY, EntityType.PIG),
                piglin = player.getStatistic(Statistic.KILL_ENTITY, EntityType.PIGLIN),
                pillager = player.getStatistic(Statistic.KILL_ENTITY, EntityType.PILLAGER),
                sheep = player.getStatistic(Statistic.KILL_ENTITY, EntityType.SHEEP),
                shulker = player.getStatistic(Statistic.KILL_ENTITY, EntityType.SHULKER),
                silverfish = player.getStatistic(Statistic.KILL_ENTITY, EntityType.SILVERFISH),
                skeleton = player.getStatistic(Statistic.KILL_ENTITY, EntityType.SKELETON),
                slime = player.getStatistic(Statistic.KILL_ENTITY, EntityType.SLIME),
                snowman = player.getStatistic(Statistic.KILL_ENTITY, EntityType.SNOWMAN),
                spider = player.getStatistic(Statistic.KILL_ENTITY, EntityType.SPIDER),
                squid = player.getStatistic(Statistic.KILL_ENTITY, EntityType.SQUID),
                villager = player.getStatistic(Statistic.KILL_ENTITY, EntityType.VILLAGER),
                witch = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITCH),
                wither = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER),
                witherSkelton = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER_SKELETON),
                wolf = player.getStatistic(Statistic.KILL_ENTITY, EntityType.WOLF),
                zombie = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE),
                zombieVillager = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE_VILLAGER),
                zombiePiglin = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIFIED_PIGLIN)
        )
        killLogRepo.insert(killLog)
    }

    private fun recordKilledLog(player: Player, userId: Int) {
        val killedLog = KillLog(
                userId = userId,
                userLoginNum = player.getStatistic(Statistic.LEAVE_GAME),
                bat = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.BAT),
                blaze = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.BLAZE),
                caveSpider = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.CAVE_SPIDER),
                chicken = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.CHICKEN),
                cow = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.COW),
                creeper = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.CREEPER),
                drowned = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.DROWNED),
                enderman = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.ENDERMAN),
                endermite = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.ENDERMITE),
                ghast = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.GHAST),
                husk = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.HUSK),
                magmaCube = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.MAGMA_CUBE),
                phantom = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.PHANTOM),
                pig = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.PIG),
                piglin = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.PIGLIN),
                pillager = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.PILLAGER),
                sheep = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.SHEEP),
                shulker = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.SHULKER),
                silverfish = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.SILVERFISH),
                skeleton = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.SKELETON),
                slime = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.SLIME),
                snowman = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.SNOWMAN),
                spider = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.SPIDER),
                squid = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.SQUID),
                villager = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.VILLAGER),
                witch = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.WITCH),
                wither = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.WITHER),
                witherSkelton = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.WITHER_SKELETON),
                wolf = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.WOLF),
                zombie = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.ZOMBIE),
                zombieVillager = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.ZOMBIE_VILLAGER),
                zombiePiglin = player.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.ZOMBIFIED_PIGLIN)
        )
        killLogRepo.insert(killedLog, isKilledLog = true)
    }
}