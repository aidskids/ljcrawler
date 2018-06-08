package com.gaohan.gfrobots

import java.util.*

object GfFuncs {

    private var firstTime = false

    fun initFrame(block: () -> Unit) {
        FrameUtils.initFrame()
        FrameUtils.switchFrame()
        block()
        FrameUtils.beep()
        System.exit(999)
    }

    fun loop(round: Int, cycle: () -> Unit) {
        firstTime = true
        (1..round).forEach {
            println("${Date(System.currentTimeMillis()).toLocaleString()}\t$it/$round")
            cycle()
            firstTime = false
        }
    }

    fun go(episode: Int, mission: Int, midnight: Boolean = false) {
        val episodeBtn = when (episode) {
            0 -> GfButtons.combat.episode0
            1 -> GfButtons.combat.episode1
            2 -> GfButtons.combat.episode2
            3 -> GfButtons.combat.episode3
            4 -> GfButtons.combat.episode4
            5 -> GfButtons.combat.episode5
            6 -> GfButtons.combat.episode6
            7 -> GfButtons.combat.episode7
            8 -> GfButtons.combat.episode8
            9 -> GfButtons.combat.episode9
            10 -> GfButtons.combat.episode10
            else -> throw Exception()
        }
        val missionBtn = when (mission) {
            1 -> GfButtons.combat.mission1
            2 -> GfButtons.combat.mission2
            3 -> GfButtons.combat.mission4
            4 -> GfButtons.combat.mission5
            5 -> GfButtons.combat.mission5
            6 -> GfButtons.combat.mission6
            else -> throw Exception()
        }
        val drag1 = when (episodeBtn) {
            GfButtons.combat.episode6,
            GfButtons.combat.episode7,
            GfButtons.combat.episode8,
            GfButtons.combat.episode9,
            GfButtons.combat.episode10 -> true
            else -> false
        }
        val drag2 = when (missionBtn) {
            GfButtons.combat.mission5,
            GfButtons.combat.mission6 -> true
            else -> false
        }
        robot.click(GfButtons.main.combat).wait(3500)
        if (firstTime && drag1) robot.drag(GfButtons.combat.episode5, GfButtons.combat.episode0).wait(800)
        if (firstTime) robot.click(episodeBtn).wait(500)
        if (midnight) robot.click(GfButtons.combat.midnight).wait(500)
        if (drag2) robot.drag(GfButtons.combat.mission4, GfButtons.combat.mission1).wait(800)
        robot.click(missionBtn).wait(500)
        robot.click(GfButtons.combat.btnLeft).wait(4000)
        if (firstTime) GfFuncs.minMap()
    }

    fun minMap() {
        (1..3).forEach {
            robot.clickMid()
            robot.drag(GfButtons.battle.min_map_left, GfButtons.battle.min_map_right)
            robot.clickMid()
            robot.wait(400)
            robot.click(GfButtons.unitInfo.cancel)
            robot.wait(400)
            robot.click(GfButtons.battle.blank)
        }
    }

    fun formPreset(airport: RobotButton, preset: Int, battle: Boolean = true) {
        val presetBtn = when (preset) {
            1 -> GfButtons.preset.preset1
            2 -> GfButtons.preset.preset2
            else -> throw Exception()
        }
        robot.click(airport).wait(500)
        robot.click(GfButtons.unitInfo.edit).wait(4500)
        if (firstTime || battle) {
            robot.click(GfButtons.formation.ech1).wait(1000)
            robot.click(GfButtons.formation.preset).wait(3000)
            robot.click(GfButtons.preset.presets).wait(2500)
            robot.click(presetBtn).wait(2500)
            robot.click(GfButtons.preset.usePreset).wait(3000)
            robot.click(GfButtons.preset.confirm).wait(3000)
        }
        if (!battle) {
            robot.click(GfButtons.formation.unit1).wait(500)
            robot.click(GfButtons.itemSelect.removeUnit).wait(2000)
            robot.click(GfButtons.formation.unit2).wait(500)
            robot.click(GfButtons.itemSelect.removeUnit).wait(2000)
            robot.click(GfButtons.formation.unit3).wait(500)
            robot.click(GfButtons.itemSelect.removeUnit).wait(2000)
            robot.click(GfButtons.formation.unit4).wait(500)
            robot.click(GfButtons.itemSelect.removeUnit).wait(2000)
            robot.drag(GfButtons.formation.unit5, GfButtons.formation.unit1).wait(2000)
        }
        robot.click(GfButtons.formation.returnToBase).wait(5000)
    }

    fun prepUnits(vararg objs: Any) {
        objs.forEach {
            if (it is RobotButton) setUnit(it)
            if (it is Pair<*, *>) setUnit(it.first as RobotButton, it.second as RobotButton)
        }
        GfFuncs.endTurn(5)
    }

    fun battleRetreat() {
        robot.click(GfButtons.battle.stop_battle).wait(300)
        robot.click(GfButtons.battle.stop_right).wait(4000)
    }

    fun battleRestart() {
        robot.click(GfButtons.battle.stop_battle).wait(300)
        robot.click(GfButtons.battle.stop_left).wait(5500)
    }

    fun battleComplete() {
        (1..14).forEach { robot.click(GfButtons.battle.endTurn).wait(300) }
        (1..14).forEach { robot.click(GfButtons.main.empty).wait(300) }
    }

    fun handleSupport() = (1..14).forEach { robot.click(GfButtons.main.houqin).wait(300) }

    fun exePlan(sec: Int, vararg objs: Any) {
        robot.click(GfButtons.battle.plan).wait(300)
        objs.forEach {
            if (it is Function0<*>) it.invoke()
            if (it is RobotButton) robot.click(it).wait(300)
        }
        robot.click(GfButtons.battle.exePlan).waitForSec(sec)
    }

    fun endTurn(sec: Int) {
        robot.click(GfButtons.battle.endTurn).clickForSec(GfButtons.battle.blank, sec)
    }

    fun setUnit(airport: RobotButton, ech: RobotButton? = null, friend: Boolean = false) {
        robot.click(airport).wait(500)
        if (ech != null) robot.click(ech).wait(500)
        if (friend) {
            robot.wait(4500)
            robot.click(makeButton(666, 291)).wait(2000)
        }
        robot.click(GfButtons.unitInfo.confirm).wait(2500)
    }

    fun supply(btn: RobotButton) {
        robot.click(btn).wait(300)
        robot.click(btn).wait(300)
        robot.click(GfButtons.unitInfo.supply).wait(2200)
        robot.click(GfButtons.battle.blank).wait(300)
        robot.click(GfButtons.battle.blank).wait(300)
    }

    fun retreat(btn: RobotButton) {
        robot.click(btn).wait(300)
        robot.click(btn).wait(300)
        robot.click(GfButtons.unitInfo.retreat).wait(500)
        robot.click(GfButtons.unitInfo.retreat_sure).wait(3000)
    }

    fun fairy(btn: RobotButton) {
        robot.click(btn).wait(500)
        robot.click(GfButtons.battle.fairy).wait(500)
        robot.click(btn).wait(2000)
    }

    fun toTop(times: Int = 1) = (1..times).forEach { robot.drag(GfButtons.battle.top, GfButtons.battle.bottom).wait(300) }

    fun toBottom(times: Int = 1) = (1..times).forEach { robot.drag(GfButtons.battle.bottom, GfButtons.battle.top).wait(300) }

    fun leftOf(btn: RobotButton) = makeButton((btn.x2 + btn.x1) / 2 - 150, (btn.y2 + btn.y1) / 2)

    fun rightOf(btn: RobotButton) = makeButton((btn.x2 + btn.x1) / 2 + 150, (btn.y2 + btn.y1) / 2)

    fun refresh() {
        robot.click(GfButtons.main.combat).waitForSec(5)
        robot.click(GfButtons.combat.returnToBase).waitForSec(5)
    }

    fun fixFirt() {
        robot.click(GfButtons.main.restore).wait(3500)
        robot.click(GfButtons.restore.pos6).wait(1000)
        robot.click(GfButtons.itemSelect.byIndex(0, 0)).wait(300)
        robot.click(GfButtons.restore.confirm).wait(300)
        robot.click(GfButtons.restore.confirm2).wait(2000)
        robot.click(GfButtons.restore.returnToBase).wait(2000)
    }

}