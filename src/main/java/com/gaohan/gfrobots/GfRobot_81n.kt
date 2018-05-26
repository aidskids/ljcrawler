package com.gaohan.gfrobots

import java.util.*

object GfRobot_81n {

    var round = 999

    var fairy = true
//        var fairy = false

    @JvmStatic
    fun main(args: Array<String>) {
        FrameUtils.initFrame()
        FrameUtils.switchFrame()
        (1..round).forEach {
            println("${Date(System.currentTimeMillis()).toLocaleString()}\t$it/${round}")
            cycle()
        }
        FrameUtils.beep()
        System.exit(999)
    }

    fun cycle() {
        M.go()
        M.battle()
        GfFunctions.handleSupport()
    }

    object M {
        object B {
            val cat = makeButton(361, 189)
            val dog = makeButton(1695, 304)
            val spot1 = makeButton(485, 384)
            val spot2 = makeButton(213, 438)
            val spot3 = makeButton(233, 725)
            val spot4 = makeButton(473, 549)
            val spot5 = makeButton(445, 789)
            val spot6 = makeButton(299, 928, 5)
        }

        fun go() {
            robot.click(GfButtons.main.combat).wait(3500)
            if (firstTime) {
                robot.drag(GfButtons.mission.episode5, GfButtons.mission.episode0).wait(800)
                robot.click(GfButtons.mission.episode8).wait(500)
                firstTime = !firstTime
            }
            robot.click(GfButtons.mission.midnight).wait(500)
            robot.click(GfButtons.mission.mission1).wait(500)
            robot.click(GfButtons.mission.btnLeft).wait(6000)
        }

        fun battle() {
            GfFunctions.toTop1()
            GfFunctions.formation_5(false, B.cat, GfButtons.formation.preset_2)
            setUnits()
            GfFunctions.supply(B.cat)
            GfFunctions.retreat(B.cat)
            GfFunctions.stop_battle_no_exit()

            GfFunctions.toTop1()
            GfFunctions.formation_5(true, B.cat, GfButtons.formation.preset_2)
            setUnits()
            round1()
            GfFunctions.stop_battle_yes_exit()
            GfFunctions.handleSupport()
        }

        fun setUnits() {
            GfFunctions.toTop1()
            GfFunctions.setUnit(B.cat)
            GfFunctions.setUnit(B.dog)
            robot.click(GfButtons.common.confirm).wait(4500)
        }

        fun round1() {
            if (fairy) GfFunctions.fairy(B.cat)
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.cat).wait(300)
            robot.click(B.spot1).wait(300)
            robot.click(B.spot2).wait(300)
            robot.click(B.spot3).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(B.spot5).wait(300)
            robot.click(B.spot6).wait(300)
            robot.click(GfButtons.common.confirm).waitForSec(145)
//            robot.click(GfButtons.common.confirm).clickForSec(GfButtons.battle.blank, 0)
            GfFunctions.toTop1()
            GfFunctions.retreat(B.spot6)
        }
    }
}