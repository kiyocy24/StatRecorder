package com.github.kiyocy24.stat_recorder.entity.view

class UserLog (
        val id: Int = 0,
        val userId: Int = 0,
        val leaveGame: Int = 0,
        val playOneMinute: Int = 0,

        // Total Item log
        val blockMined: Int = 0,
        val itemBroken: Int = 0,
        val itemCrafted: Int = 0,
        val itemUsed: Int = 0,
        val itemPickedUp: Int = 0,
        val itemDropped: Int = 0
) {}