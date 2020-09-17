package com.github.kiyocy24.stat_recorder.entity.db

import java.sql.Timestamp

class CustomLog(
        val id: Int = 0,
        val userId: Int = 0,
        val damageDealt: Int = 0,
        val damageTaken: Int = 0,
        val deaths: Int = 0,
        val mobKills: Int = 0,
        val playerKills: Int = 0,
        val fishCaught: Int = 0,
        val animalsBred: Int = 0,
        val leaveGame: Int = 0,
        val jump: Int = 0,
        val dropCount: Int = 0,
        val playOneMinute: Int = 0,
        val walkOneCm: Int = 0,
        val walkOnWaterOneCm: Int = 0,
        val fallOneCm: Int = 0,
        val sneakTime: Int = 0,
        val climbOneCm: Int = 0,
        val flyOneCm: Int = 0,
        val walkUnderWaterOneCm: Int = 0,
        val minecartOneCm: Int = 0,
        val boatOneCm: Int = 0,
        val pigOneCm: Int = 0,
        val horseOneCm: Int = 0,
        val sprIntOneCm: Int = 0,
        val crouchOneCm: Int = 0,
        val aviateOneCm: Int = 0,
        val timeSinceDeath: Int = 0,
        val talkedToVillager: Int = 0,
        val tradedWithVillager: Int = 0,
        val cakeSlicesEaten: Int = 0,
        val cauldronFilled: Int = 0,
        val cauldronUsed: Int = 0,
        val armorCleaned: Int = 0,
        val bannerCleaned: Int = 0,
        val brewingstandInteraction: Int = 0,
        val beaconInteraction: Int = 0,
        val dropperInspected: Int = 0,
        val hopperInspected: Int = 0,
        val dispenserInspected: Int = 0,
        val noteblockPlayed: Int = 0,
        val noteblockTuned: Int = 0,
        val flowerPotted: Int = 0,
        val trappedChestTriggered: Int = 0,
        val enderchestOpened: Int = 0,
        val itemEnchanted: Int = 0,
        val recordPlayed: Int = 0,
        val furnaceInteraction: Int = 0,
        val craftingTableInteraction: Int = 0,
        val chestOpened: Int = 0,
        val sleepInBed: Int = 0,
        val shulkerBoxOpened: Int = 0,
        val timeSinceRest: Int = 0,
        val swimOneCm: Int = 0,
        val damageDealtAbsorbed: Int = 0,
        val damageDealtResisted: Int = 0,
        val damageBlockedByShield: Int = 0,
        val damageAbsorbed: Int = 0,
        val damageResisted: Int = 0,
        val cleanShulkerBox: Int = 0,
        val openBarrel: Int = 0,
        val interactWithBlastFurnace: Int = 0,
        val interactWithSmoker: Int = 0,
        val interactWithLectern: Int = 0,
        val interactWithCampfire: Int = 0,
        val interactWithCartographyTable: Int = 0,
        val interactWithLoom: Int = 0,
        val interactWithStonecutter: Int = 0,
        val bellRing: Int = 0,
        val raidTrigger: Int = 0,
        val raidWin: Int = 0,
        val interactWithAnvil: Int = 0,
        val interactWithGrindstone: Int = 0,
        val targetHit: Int = 0,
        val interactWithSmithingTable: Int = 0,
        val striderOneCm: Int = 0,

        val createdAt: Timestamp = Timestamp(0),
        val updatedAt: Timestamp = Timestamp(0)
) {}