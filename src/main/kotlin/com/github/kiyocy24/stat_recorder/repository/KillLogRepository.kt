package com.github.kiyocy24.stat_recorder.repository

import com.github.kiyocy24.stat_recorder.entity.db.KillLog as DBKillLog
import com.github.kiyocy24.stat_recorder.entity.view.KillLog as ViewKillLog
import com.github.kiyocy24.stat_recorder.infrastructure.Database
import java.sql.Connection

class KillLogRepository(private val conn: Connection) {
    private val db = Database(conn).KillLog()
    fun insert(viewKillLog: ViewKillLog) {
        val dbKillLog = DBKillLog(
                userId = viewKillLog.userId,
                userLoginNum = viewKillLog.userLoginNum,
                bat = viewKillLog.bat,
                blaze = viewKillLog.blaze,
                caveSpider = viewKillLog.caveSpider,
                chicken = viewKillLog.chicken,
                cow = viewKillLog.cow,
                creeper = viewKillLog.creeper,
                drowned = viewKillLog.drowned,
                enderman = viewKillLog.enderman,
                endermite = viewKillLog.endermite,
                ghast = viewKillLog.ghast,
                husk = viewKillLog.husk,
                magmaCube = viewKillLog.magmaCube,
                phantom = viewKillLog.phantom,
                pig = viewKillLog.pig,
                piglin = viewKillLog.piglin,
                pillager = viewKillLog.pillager,
                sheep = viewKillLog.sheep,
                shulker = viewKillLog.shulker,
                silverfish = viewKillLog.silverfish,
                skeleton = viewKillLog.skeleton,
                slime = viewKillLog.slime,
                snowman = viewKillLog.snowman,
                spider = viewKillLog.spider,
                squid = viewKillLog.squid,
                villager = viewKillLog.villager,
                witch = viewKillLog.witch,
                wither = viewKillLog.wither,
                witherSkelton = viewKillLog.witherSkelton,
                wolf = viewKillLog.wolf,
                zombie = viewKillLog.zombie,
                zombieVillager = viewKillLog.zombieVillager,
                zombiePiglin = viewKillLog.zombiePiglin
        )
        db.insert(dbKillLog)
    }
}