package com.gaohan.gfrobots.outdate

import com.gaohan.gfrobots.*
import java.util.*


/**
 * Created by gaohan on 2018/2/5.
 */

object GfRobot_Combats_glo1 {

    var round = 1

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
        M.setUnits()
        M.battle(true)
        GfFuncs.end_battle()
        GfFuncs.handleSupport()
    }


    object M {
        object B {
            val glory = makeButton(130, 783)
            val episode1 = makeButton(175, 748)
            val mission1 = makeButton(807, 630)
            val mission2 = makeButton(1587, 381)
            val mission3 = makeButton(1427, 803)
            val mission_go = makeButton(967, 868)

            val airport = makeButton(1028, 316)
            val spot1 = makeButton(1002, 509)
            val spot2 = makeButton(909, 725)
            val spot3 = makeButton(795, 908)
            val spot4 = makeButton(539, 852)
            val spot5 = makeButton(852, 1040)
        }

        fun go() {
            robot.click(GfButtons.main.combat).wait(3500)
            robot.click(B.glory).wait(3000)
            robot.click(B.episode1).wait(500)
            robot.click(B.mission1).wait(500)
            robot.click(B.mission_go).wait(4000)
        }

        fun setUnits() {
            robot.click(B.airport).wait(500)
            robot.click(GfButtons.common.confirm).wait(2500)
            robot.click(GfButtons.common.confirm).wait(3500)
        }

        fun battle(fairy: Boolean) {
            supply()
            round1(fairy)
            round2(fairy)
            round3(fairy)
        }

        fun supply() {
            robot.click(B.airport).wait(300)
            robot.click(B.airport).wait(300)
            robot.click(GfButtons.battle.supply).wait(2000)
        }

        fun round1(fairy: Boolean) {
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.airport).wait(300)
            robot.click(B.spot1).wait(300)
            robot.click(B.spot2).wait(300)
            robot.click(GfButtons.common.confirm).wait(3000)
            Thread.sleep(50 * 1000L)
            robot.click(GfButtons.common.confirm).wait(2500)
            Thread.sleep(15 * 1000L)
        }

        fun round2(fairy: Boolean) {
            GfFuncs.toTop1()
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot2).wait(300)
            robot.click(B.spot3).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(GfButtons.common.confirm).wait(3000)
            Thread.sleep(50 * 1000L)
            robot.click(GfButtons.common.confirm).wait(2500)
            Thread.sleep(13 * 1000L)
        }

        fun round3(fairy: Boolean) {
            GfFuncs.toTop1()
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(B.spot3).wait(300)
            robot.click(B.spot5).wait(300)
            robot.click(GfButtons.common.confirm).wait(3000)
            Thread.sleep(25 * 1000L)
        }
    }

}
