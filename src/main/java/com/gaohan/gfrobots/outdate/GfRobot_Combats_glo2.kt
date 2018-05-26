package com.gaohan.gfrobots.outdate

import com.gaohan.gfrobots.*
import java.util.*


/**
 * Created by gaohan on 2018/2/5.
 */

object GfRobot_Combats_glo2 {

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

            val spot0 = makeButton(245, 530)
            val spot1 = makeButton(450, 530)
            val spot2 = makeButton(645, 530)
            val spot3 = makeButton(645, 846) // 最左下
            val spot4 = makeButton(913, 846)
            val spot5 = makeButton(1145, 732)
            val spot6 = makeButton(1145, 530) // 十字路口
            val spot7 = makeButton(1405, 530)
            val spot8 = makeButton(1405, 762)
            val spot9 = makeButton(1649, 748) // 最右下
            val spot10 = makeButton(1649, 566)
            val spot11 = makeButton(1649, 339) // boss
        }

        fun go() {
            robot.click(GfButtons.main.combat).wait(3500)
            robot.click(B.glory).wait(3000)
            robot.click(B.episode1).wait(500)
            robot.click(B.mission2).wait(500)
            robot.click(B.mission_go).wait(4000)
        }

        fun battle() {
            setUnits()
            round1()
            round2()
            round3()
            round4()
            GfFuncs.end_battle()
        }

        fun setUnits() {
            GfFuncs.toBottom1()
            GfFuncs.setUnit(B.spot0)
            robot.click(GfButtons.common.confirm).wait(3500)
        }

        fun round1() {
            GfFuncs.supply(B.spot0)
            robot.click(B.spot1).wait(2500)
            robot.click(GfButtons.battle.blank).wait(300)
            GfFuncs.setUnit(B.spot0)
            GfFuncs.supply(B.spot0)

            robot.click(GfButtons.common.confirm).clickForSec(GfButtons.battle.blank, 32)
        }

        fun round2() {
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot1).wait(300)
            robot.click(B.spot2).wait(300)
            robot.click(B.spot3).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(GfButtons.common.confirm).waitForSec(30)

            robot.click(GfButtons.common.confirm).clickForSec(GfButtons.battle.blank, 15)
        }

        fun round3() {
            robot.click(B.spot0).wait(300)
            robot.click(B.spot1).wait(2500)

            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(B.spot5).wait(300)
            robot.click(B.spot6).wait(300)
            robot.click(B.spot7).wait(300)
            robot.click(GfButtons.common.confirm).waitForSec(31)
            robot.click(GfButtons.common.confirm).clickForSec(GfButtons.battle.blank, 29)
        }

        fun round4() {
            GfFuncs.toBottom1()
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot7).wait(300)
            robot.click(B.spot8).wait(300)
            robot.click(B.spot9).wait(300)
            robot.click(B.spot10).wait(300)
            robot.click(B.spot11).wait(300)
            robot.click(GfButtons.common.confirm).waitForSec(56)

            robot.click(GfButtons.common.confirm)
        }
    }

}
