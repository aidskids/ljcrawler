package com.gaohan.gfrobots

import java.util.*

object GfFuncs {

    var firstTime = false

    fun fuck(round: Int, cycle: () -> Unit) {
        FrameUtils.initFrame()
        FrameUtils.switchFrame()

        firstTime = true
        (1..round).forEach {
            println("${Date(System.currentTimeMillis()).toLocaleString()}\t$it/$round")
            cycle().let { firstTime = false }
        }

        FrameUtils.beep()
        System.exit(999)
    }

    fun go(episode: Int, mission: Int, midnight: Boolean = false) {
        val episodeBtn = when (episode) {
            0 -> GfButtons.mission.episode0
            1 -> GfButtons.mission.episode1
            2 -> GfButtons.mission.episode2
            3 -> GfButtons.mission.episode3
            4 -> GfButtons.mission.episode4
            5 -> GfButtons.mission.episode5
            6 -> GfButtons.mission.episode6
            7 -> GfButtons.mission.episode7
            8 -> GfButtons.mission.episode8
            9 -> GfButtons.mission.episode9
            10 -> GfButtons.mission.episode10
            else -> throw Exception()
        }
        val missionBtn = when (episode) {
            1 -> GfButtons.mission.mission1
            2 -> GfButtons.mission.mission2
            3 -> GfButtons.mission.mission4
            4 -> GfButtons.mission.mission5
            5 -> GfButtons.mission.mission5
            6 -> GfButtons.mission.mission6
            else -> throw Exception()
        }
        val drag1 = when (episodeBtn) {
            GfButtons.mission.episode6,
            GfButtons.mission.episode7,
            GfButtons.mission.episode8,
            GfButtons.mission.episode9,
            GfButtons.mission.episode10 -> true
            else -> false
        }
        val drag2 = when (missionBtn) {
            GfButtons.mission.mission5,
            GfButtons.mission.mission6 -> true
            else -> false
        }
        robot.click(GfButtons.main.combat).wait(3500)
        if (firstTime && drag1) robot.drag(GfButtons.mission.episode5, GfButtons.mission.episode0).wait(800)
        if (firstTime) robot.click(episodeBtn).wait(500)
        if (midnight) robot.click(GfButtons.mission.midnight).wait(500)
        if (drag2) robot.drag(GfButtons.mission.mission4, GfButtons.mission.mission1).wait(800)
        robot.click(missionBtn).wait(500)
        robot.click(GfButtons.mission.btnLeft).wait(4000)
        if (firstTime) GfFuncs.minMap()
    }

    fun minMap() {
        robot.clickMid().wait(300)
        (1..10).forEach { robot.drag(GfButtons.battle.min_map_left, GfButtons.battle.min_map_right).wait(300) }
        robot.clickMid().wait(300)
    }

    fun formPreset(airport: RobotButton, preset: Int) {
        val presetBtn = when (preset) {
            1 -> GfButtons.unitDetail.preset_1
            2 -> GfButtons.unitDetail.preset_2
            else -> throw Exception()
        }
        robot.click(airport).wait(500)
        robot.click(GfButtons.unitSummary.detail).wait(4500)
        robot.click(GfButtons.common.confirm).wait(3000)
        robot.click(GfButtons.unitDetail.preset_menu).wait(2500)
        robot.click(presetBtn).wait(2500)
        robot.click(GfButtons.common.confirm).wait(3000)
        robot.click(GfButtons.common.confirm).wait(3000)
        robot.click(GfButtons.common.returnn).wait(5000)
    }

    fun form5to1(airport: RobotButton) {
        robot.click(airport).wait(500)
        robot.click(GfButtons.unitSummary.detail).wait(4500)
        robot.click(GfButtons.unitDetail.unit1).wait(500)
        robot.click(GfButtons.unitDetail.remove_unit).wait(2000)
        robot.click(GfButtons.unitDetail.unit2).wait(500)
        robot.click(GfButtons.unitDetail.remove_unit).wait(2000)
        robot.click(GfButtons.unitDetail.unit3).wait(500)
        robot.click(GfButtons.unitDetail.remove_unit).wait(2000)
        robot.click(GfButtons.unitDetail.unit4).wait(500)
        robot.click(GfButtons.unitDetail.remove_unit).wait(2000)
        robot.drag(GfButtons.unitDetail.unit5, GfButtons.unitDetail.unit1).wait(2000)
        robot.click(GfButtons.common.returnn).wait(5000)
    }

    fun prepUnits(vararg objs: Any) {
        objs.forEach {
            if (it is RobotButton) setUnit(it)
            if (it is Pair<*, *>) setUnit(it.first as RobotButton, it.second as RobotButton)
        }
        GfFuncs.nextTurn(5)
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
        (1..14).forEach { robot.click(GfButtons.common.confirm).wait(300) }
        (1..14).forEach { robot.click(GfButtons.main.empty).wait(300) }
    }

    fun handleSupport() = (1..14).forEach { robot.click(GfButtons.main.houqin).wait(300) }

    fun exePlan(sec: Int, vararg objs: Any) {
        robot.click(GfButtons.battle.plan).wait(300)
        objs.forEach {
            if (it is Function0<*>) it.invoke()
            if (it is RobotButton) robot.click(it).wait(300)
        }
        robot.click(GfButtons.common.confirm).waitForSec(sec)
    }

    fun nextTurn(sec: Int) {
        robot.click(GfButtons.common.confirm).clickForSec(GfButtons.battle.blank, sec)
    }

    fun setUnit(btn: RobotButton, ech: RobotButton? = null, friend: Boolean = false) {
        robot.click(btn).wait(500)
        if (ech != null) robot.click(ech).wait(500)
        if (friend) {
            robot.wait(4500)
            robot.click(makeButton(666, 291)).wait(2000)
        }
        robot.click(GfButtons.common.confirm).wait(2500)
    }

    fun supply(btn: RobotButton) {
        robot.click(btn).wait(300)
        robot.click(btn).wait(300)
        robot.click(GfButtons.unitSummary.supply).wait(2200)
        robot.click(GfButtons.battle.blank).wait(300)
        robot.click(GfButtons.battle.blank).wait(300)
    }

    fun retreat(btn: RobotButton) {
        robot.click(btn).wait(300)
        robot.click(btn).wait(300)
        robot.click(GfButtons.unitSummary.retreat).wait(500)
        robot.click(GfButtons.unitSummary.retreat_sure).wait(3000)
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

}