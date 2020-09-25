package com.github.kiyocy24.stat_recorder.entity.view

class TotalItemLog (
        val blockMined: Int,
        val itemBroken: Int,
        val itemCrafted: Int,
        val itemUsed: Int,
        val itemPickedUp: Int,
        val itemDropped: Int,
) {}