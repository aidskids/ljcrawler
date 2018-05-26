package com.gaohan.gfrobots

import java.util.*

object GfRobot_02 {

    // 40 per round
    var round = 2

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
        GfFunctions.toBottom1()
        GfFunctions.formation_5(battle, M.B.airport_core, GfButtons.formation.preset_1)
        M.setUnits()
        if (battle) {
            M.battle(fairy)
            GfFunctions.end_battle()
            GfFunctions.handleSupport()
        } else {
            M.supply_core()
            M.retreat_core()
            GfFunctions.stop_battle_no_exit()
        }

        battle = !battle
    }

    object M {
        object B {
            //拖动至底部
            val airport_core = makeButton(989, 324, 40)
            val airport_dog = makeButton(393, 309, 30)
            val spot1 = makeButton(785, 210, 15)
            //拖动至顶部
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
            GfFunctions.toBottom1()
            robot.click(B.airport_core).wait(500)
            robot.click(GfButtons.common.confirm).wait(500)
            robot.click(B.airport_dog).wait(500)
            robot.click(GfButtons.common.confirm).wait(500)
            robot.click(GfButtons.common.confirm).wait(4500)
        }

        fun supply_dog() {
            robot.click(B.airport_dog).wait(500)
            robot.click(B.airport_dog).wait(500)
            robot.click(GfButtons.battle.supply).wait(500)
        }

        fun supply_core() {
            GfFunctions.supply(B.airport_core)
        }

        fun retreat_core() {
            GfFunctions.retreat(B.airport_core)
        }

        fun battle(fairy: Boolean) {
            round1(fairy)
            round2(fairy)
        }

        fun round1(fairy: Boolean) {
            if (fairy) {
                robot.click(B.airport_core).wait(500)
                robot.click(GfButtons.battle.fairy).wait(500)
                robot.click(B.airport_core).wait(1500)
                GfFunctions.toBottom1()
            }
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.airport_core).wait(300)
            robot.click(B.spot1).wait(300)
            GfFunctions.toTop2()
            robot.click(B.spot2).wait(300)
            robot.click(B.spot3).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(GfButtons.common.confirm).wait(2000)
            Thread.sleep(battleTime1 * 1000L)
            robot.click(GfButtons.common.confirm).wait(500)
            Thread.sleep(13 * 1000L)
        }

        fun round2(fairy: Boolean) {
            // 停在左上角导弹车的位置时，不需要上拖
//        GfFunctions.toTop1()
            if (fairy) {
                robot.click(B.spot4).wait(500)
                robot.click(GfButtons.battle.fairy).wait(500)
                robot.click(B.spot4).wait(2500)
//            GfFunctions.toTop1()
            }
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(B.spot5).wait(300)
            robot.click(B.spot6).wait(300)
            robot.click(GfButtons.common.confirm).wait(2000)
            Thread.sleep(battleTime2 * 1000L)
        }
    }


}