package com.gaohan.gfrobots

import java.util.*

object GfRobot_02 {

    // 40 per round
    var round =2

    var fairy = true
//        var fairy = false

    var battle = round % 2 == 1     // 战斗/补给

    @JvmStatic
    fun main(args: Array<String>) {
        FrameUtils.initFrame()
        FrameUtils.switchFrame()
        (1..round).forEach {
            val t = Date(System.currentTimeMillis()).toLocaleString()
            println("$t\t$it/${round}\tcombat begin, battle=${battle}")
            cycle()
            println("$t\t$it/${round}\tcombat end")
        }
        FrameUtils.beep()
        System.exit(999)
    }

    fun cycle() {
        if (firstTime || !battle) M.go()
        GfFuncs.toBottom1()
        GfFuncs.formation_5(battle, M.B.cat, GfButtons.formation.preset_1)
        M.setUnits()
        if (battle) {
            M.battle(fairy)
            GfFuncs.end_battle()
            GfFuncs.handleSupport()
        } else {
            M.supply_core()
            M.retreat_core()
            GfFuncs.stop_battle_no_exit()
        }

        battle = !battle
    }

    object M {
        object B {
            val cat = makeButton(989, 324, 40)
            val dog = makeButton(393, 309, 30)
            val spot1 = makeButton(785, 210, 15)
            val spot2 = makeButton(804, 843, 30)
            val spot3 = makeButton(1005, 534, 30)
            val spot4 = makeButton(784, 391, 30)
            val spot5 = makeButton(1213, 387, 30)
            val spot6 = makeButton(1492, 448, 30)
        }

        val battleTime1 = 105
        val battleTime2 = 70
        fun go() {
            robot.click(GfButtons.main.combat).wait(3500)
            if (firstTime) {
                robot.click(GfButtons.mission.episode0).wait(500)
                firstTime = !firstTime
            }
            robot.click(GfButtons.mission.mission2).wait(500)
            robot.click(GfButtons.mission.btnLeft).wait(4000)

        }

        fun setUnits() {
            GfFuncs.toBottom1()
            robot.click(B.cat).wait(500)
            robot.click(GfButtons.common.confirm).wait(500)
            robot.click(B.dog).wait(500)
            robot.click(GfButtons.common.confirm).wait(500)
            robot.click(GfButtons.common.confirm).wait(4500)
        }

        fun supply_core() {
            GfFuncs.supply(B.cat)
        }

        fun retreat_core() {
            GfFuncs.retreat(B.cat)
        }

        fun battle(fairy: Boolean) {
            round1(fairy)
            round2(fairy)
        }

        fun round1(fairy: Boolean) {
            if (fairy) {
                if (fairy) GfFuncs.fairy(B.cat)
                GfFuncs.toBottom1()
            }
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.cat).wait(300)
            robot.click(B.spot1).wait(300)
            GfFuncs.toTop2()
            robot.click(B.spot2).wait(300)
            robot.click(B.spot3).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(GfButtons.common.confirm).wait(2000)
            Thread.sleep(battleTime1 * 1000L)
            robot.click(GfButtons.common.confirm).wait(500)
            Thread.sleep(13 * 1000L)
        }

        fun round2(fairy: Boolean) {
            if (fairy) GfFuncs.fairy(B.spot4)
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(B.spot5).wait(300)
            robot.click(B.spot6).wait(300)
            robot.click(GfButtons.common.confirm).wait(2000)
            Thread.sleep(battleTime2 * 1000L)
        }
    }


}