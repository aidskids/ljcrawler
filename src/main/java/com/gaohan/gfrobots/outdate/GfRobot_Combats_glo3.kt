package com.gaohan.gfrobots.outdate

import com.gaohan.gfrobots.*
import java.util.*


/**
 * Created by gaohan on 2018/2/5.
 */

object GfRobot_Combats_glo3 {

    var round = 999

    @JvmStatic
    fun main(args: Array<String>) {
        dosome()
    }

    private fun dosome() {
        FrameUtils.initFrame()
        FrameUtils.switchFrame()
        (1..round).forEach {
            val t = Date(System.currentTimeMillis()).toLocaleString()
            println("$t\t$it/${round}\tcombat begin")
            cycle()
            println("$t\t$it/${round}\tcombat end")
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
            val glory = makeButton(130, 783)
            val episode1 = makeButton(175, 748)
            val mission1 = makeButton(807, 630)
            val mission2 = makeButton(1587, 381)
            val mission3 = makeButton(1427, 803)
            val mission_go = makeButton(967, 868)

            val blank = makeButton(109, 262)
            val spot0 = makeButton(527, 493)
            val spot1 = makeButton(866, 402)
            val spot2 = makeButton(1117, 279)
            val spot3 = makeButton(1359, 360) //最右的点
            val spot4 = makeButton(1236, 540)
            val spot5 = makeButton(1009, 651)
            val spot6 = makeButton(760, 653)
            val spot7 = makeButton(534, 751)
            val spot8 = makeButton(294, 748)
        }

        fun go() {
            robot.click(GfButtons.main.combat).wait(3500)
            robot.click(B.glory).wait(3000)
            robot.click(B.episode1).wait(500)
            robot.click(B.mission3).wait(500)
            robot.click(B.mission_go).wait(4000)
        }

        fun battle() {
            setUnits()
            round1()
            round2()
            round3()
            round4()
            GfFunctions.end_battle()
        }

        fun setUnits() {
            GfFunctions.setUnit(B.spot0)
            robot.click(GfButtons.common.confirm).wait(3500)
        }

        fun round1() {
            GfFunctions.supply(B.spot0)
            robot.click(B.spot1).wait(2500)
            robot.click(B.blank).wait(300)
            GfFunctions.setUnit(B.spot0)
            GfFunctions.supply(B.spot0)

            robot.click(GfButtons.common.confirm).clickForSec(B.blank, 32)
        }

        fun round2() {
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot1).wait(300)
            robot.click(B.spot2).wait(300)
            robot.click(GfButtons.common.confirm).wait(3000)
            Thread.sleep(23 * 1000L)

            robot.click(B.spot0).wait(300)
            robot.click(B.spot1).wait(2500)
            robot.click(B.blank).wait(300)

            GfFunctions.setUnit(B.spot0)
            robot.click(B.spot1).wait(300)
            robot.click(B.spot0).wait(300)
            robot.click(GfFunctions.spotLeft(B.spot0)).wait(2500)

            robot.click(GfButtons.common.confirm).clickForSec(B.blank, 32)
        }

        fun round3() {
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot2).wait(300)
            robot.click(B.spot3).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(GfButtons.common.confirm).wait(3000)
            Thread.sleep(45 * 1000L)

            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot1).wait(300)
            robot.click(B.spot2).wait(300)
            robot.click(B.spot3).wait(300)
            robot.click(GfButtons.common.confirm).wait(3000)
            Thread.sleep(5 * 1000L)

            robot.click(GfButtons.common.confirm).clickForSec(B.blank, 50)
        }

        fun round4() {
            GfFunctions.supply(B.spot4)
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(B.spot5).wait(300)
            robot.click(B.spot6).wait(300)
            robot.click(B.spot7).wait(300)
            robot.click(B.spot8).wait(300)
            robot.click(GfButtons.common.confirm)
            Thread.sleep(75 * 1000L)

            robot.click(GfButtons.common.confirm)
        }
    }

}
