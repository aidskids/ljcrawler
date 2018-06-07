package com.gaohan.gfrobots.boss

import com.gaohan.gfrobots.*
import java.util.*


/**
 * Created by gaohan on 2018/2/5.
 */

object GfRobot_16 {

    var round = 2

    @JvmStatic
    fun main(args: Array<String>) = GfFuncs.sameOldFuck(round) { M.go();M.battle();GfFuncs.handleSupport() }

    object M {
        object B {
            val spot0 = makeButton(407, 505)
            val spot1 = makeButton(726, 500)
            val spot2 = makeButton(559, 762)
            val spot3 = makeButton(789, 1009) // 分叉点
            val spot4 = makeButton(1190, 771)
            val spot5 = makeButton(884, 756)
            val spot6 = makeButton(1132, 507)
            val spot7 = makeButton(1395, 626)
            val spot8 = makeButton(1365, 915)
            val spot9 = makeButton(1200, 1050, 10)
        }

        fun go() {
            robot.click(GfButtons.main.combat).wait(4500)
            if (firstTime) {
                robot.click(GfButtons.mission.episode1).wait(500)
            }
            robot.drag(GfButtons.mission.mission4, GfButtons.mission.mission1).wait(800)
            robot.click(GfButtons.mission.mission6).wait(500)
            robot.click(GfButtons.mission.btnLeft).wait(4000)
            if (firstTime) GfFuncs.minMap()
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
            GfFuncs.setUnit(B.spot0)
            robot.click(GfButtons.common.confirm).wait(3500)
        }

        fun round1() {
            GfFuncs.supply(B.spot0)
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot0).wait(300)
            robot.click(B.spot1).wait(300)
            robot.click(B.spot2).wait(300)
            robot.click(GfButtons.common.confirm).wait(30000)

            robot.click(GfButtons.common.confirm).clickForSec(GfButtons.battle.blank, 22)
        }

        fun round2() {
            GfFuncs.toTop1()
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot2).wait(300)
            robot.click(B.spot3).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(GfButtons.common.confirm).wait(30000)

            robot.click(GfButtons.common.confirm).clickForSec(GfButtons.battle.blank, 28)
        }

        fun round3() {
            GfFuncs.toTop1()
            GfFuncs.supply(B.spot4)
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(B.spot5).wait(300)
            robot.click(B.spot6).wait(300)
            robot.click(B.spot7).wait(300)
            robot.click(GfButtons.common.confirm).wait(50000)

            robot.click(GfButtons.common.confirm).clickForSec(GfButtons.battle.blank, 35)
        }

        fun round4() {
            GfFuncs.toTop1()
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot7).wait(300)
            robot.click(B.spot8).wait(300)
            robot.click(B.spot9).wait(300)
            robot.click(GfButtons.common.confirm).wait(50000)

            robot.click(GfButtons.common.confirm)
        }
    }
}

