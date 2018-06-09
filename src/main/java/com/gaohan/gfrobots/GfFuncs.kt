package com.gaohan.gfrobots

import java.text.SimpleDateFormat
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
            println("${SimpleDateFormat("HH:mm:ss").format(Date(System.currentTimeMillis()))}\t$it/$round")
            cycle()
            firstTime = false
        }
    }

    fun go(episode: Int, mission: Int, midnight: Boolean = false) {
        robot.click(GfButtons.main.combat).wait(3500)
        if (firstTime) robot.click(GfButtons.combat.combatMission).wait(1500)
        if (firstTime && GfButtons.combat.episodeDrag.contains(episode)) robot.drag(GfButtons.combat.episodes[5], GfButtons.combat.episodes[0]).wait(800)
        if (firstTime) robot.click(GfButtons.combat.episodes[episode]).wait(500)
        if (midnight) robot.click(GfButtons.combat.midnight).wait(500)
        if (GfButtons.combat.missionDrag.contains(mission)) robot.drag(GfButtons.combat.missions[4], GfButtons.combat.missions[1]).wait(800)
        robot.click(GfButtons.combat.missions[mission]).wait(500)
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

    fun form(airport: RobotButton, ech: Int, preset: Int = 0, removes: List<Int> = listOf(), drags: List<Pair<Int, Int>> = listOf()) {
        robot.click(airport).wait(500)
        robot.click(GfButtons.unitInfo.edit).wait(4500)
        if (ech != 0) {
            robot.click(GfButtons.formation.echs[ech]).wait(1000)
        }
        if (preset != 0) {
            robot.click(GfButtons.formation.preset).wait(3000)
            robot.click(GfButtons.preset.showPreset).wait(2500)
            robot.click(GfButtons.preset.presets[preset]).wait(2500)
            robot.click(GfButtons.preset.usePreset).wait(3000)
            robot.click(GfButtons.preset.confirm).wait(3000)
        }
        removes.forEach {
            robot.click(GfButtons.formation.units[it]).wait(500)
            robot.click(GfButtons.itemSelect.getItem(1, 1)).wait(2000)
        }
        drags.forEach {
            robot.drag(GfButtons.formation.units[it.first], GfButtons.formation.units[it.second]).wait(2000)
        }
        robot.click(GfButtons.formation.returnToBase).wait(5000)
    }

    fun fighterSwitch(airport: RobotButton, preset: Int, battle: Boolean) {
        if (!battle && firstTime) GfFuncs.form(airport, 1, preset, listOf(1, 2, 3, 4), listOf(Pair(5, 1)))
        if (battle) GfFuncs.form(airport, 0, preset)
        if (!battle && !firstTime) GfFuncs.form(airport, 0, 0, listOf(1, 2, 3, 4), listOf(Pair(5, 1)))
    }

    fun prepUnits(vararg objs: Any) {
        objs.forEach {
            if (it is RobotButton) setUnit(it)
            if (it is Triple<*, *, *>) setUnit(it.first as RobotButton, it.second as Int, it.third as Int)
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

    fun setUnit(airport: RobotButton, echAll: Int = 0, echNo: Int = 0) {
        robot.click(airport).wait(500)
        if (echNo != 0) robot.click(GfButtons.unitInfo.getEch(echAll, echNo)).wait(500)
        if (echNo == echAll + 1) {
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
        robot.click(GfButtons.main.refresh).waitForSec(5)
    }

    fun fixFirst() {
        robot.click(GfButtons.main.restore).wait(3500)
        robot.click(GfButtons.restore.pos6).wait(1000)
        robot.click(GfButtons.itemSelect.getItem(1, 1)).wait(300)
        robot.click(GfButtons.restore.confirm).wait(300)
        robot.click(GfButtons.restore.confirm2).wait(2000)
        robot.click(GfButtons.restore.returnToBase).wait(2000)
    }

}