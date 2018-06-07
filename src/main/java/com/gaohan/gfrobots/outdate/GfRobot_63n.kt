package com.gaohan.gfrobots.outdate

import com.gaohan.gfrobots.*
import java.util.*


/**
 * Created by gaohan on 2018/2/5.
 */

object GfRobot_63n {

    // 奇数回补给
    var round = 999
    var supply = round % 2 == 1

    val fairy = true
//    val fairy = false

    @JvmStatic
    fun main(args: Array<String>) {
        dosome()
    }

    private fun dosome() {
        FrameUtils.initFrame()
        FrameUtils.switchFrame()
        (1..round).forEach {
            val t = Date(System.currentTimeMillis()).toLocaleString()
            println("$t\t$it/${round}\tcombat begin, supply=${supply}")
            cycle()
            println("$t\t$it/${round}\tcombat end")
        }
        FrameUtils.beep()
        System.exit(999)
    }

    fun cycle() {
        if (firstTime || !supply) M.go()
        M.setUnits()
        M.battle(fairy)
        if (supply) {
            M.supply_dog()
            GfFuncs.stop_battle_yes_exit()
            GfFuncs.handleSupport()
        } else {
            GfFuncs.stop_battle_no_exit()
        }
        supply = !supply
    }

    object M {
        object B {
            val airport_core = makeButton(599, 377, 30)
            val airport_dog = makeButton(1162, 539, 25)
            val spot1 = makeButton(985, 416, 30)
            val spot2 = makeButton(1295, 403, 30)
            val airport_dog_change = makeButton(1027, 539, 20)
        }

        val battleTime1 = 56
        fun go() {
            robot.click(GfButtons.main.combat).wait(3500)
            if (firstTime) {
                robot.drag(GfButtons.mission.episode5, GfButtons.mission.episode0).wait(800)
                robot.click(GfButtons.mission.episode6).wait(500)
            }
            robot.click(GfButtons.mission.midnight).wait(500)
            robot.click(GfButtons.mission.mission3).wait(500)
            robot.click(GfButtons.mission.btnLeft).wait(6000)
        }

        fun setUnits() {
            robot.click(B.airport_core).wait(500)
            robot.click(GfButtons.common.confirm).wait(2500)
            robot.click(GfButtons.common.confirm).wait(3500)
        }

        fun battle(fairy: Boolean) {
            round1(fairy)
            round2(fairy)
        }

        fun round1(fairy: Boolean) {
            if (fairy) {
                val btn = B.airport_core
                robot.click(btn).wait(500)
                robot.click(GfButtons.battle.fairy).wait(500)
                robot.click(btn).wait(1500)
            }
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.airport_core).wait(300)
            robot.click(B.spot1).wait(300)
            robot.click(B.spot2).wait(300)
            robot.click(GfButtons.common.confirm).wait(3000)
            Thread.sleep(battleTime1 * 1000L)
            robot.click(GfButtons.common.confirm).wait(2500)
            Thread.sleep(27 * 1000L)
        }

        fun round2(fairy: Boolean) {
            robot.click(B.airport_dog).wait(600)
            robot.click(GfButtons.common.confirm).wait(2300)
            robot.click(B.spot2).wait(300)
            robot.click(B.airport_dog).wait(300)
            robot.click(B.airport_dog_change).wait(4000)
            robot.click(B.airport_dog).wait(300)
            robot.click(GfButtons.battle.retreat).wait(500)
            robot.click(GfButtons.battle.retreat_sure).wait(2500)
        }

        fun supply_dog() {
            val airport = B.airport_dog
            val preset = GfButtons.formation.preset_2
            GfFuncs.formation_5(false, airport, preset)
            GfFuncs.setUnit(airport)
            GfFuncs.supply(airport)
            GfFuncs.retreat(airport)
            GfFuncs.formation_5(true, airport, preset)
        }
    }
}

