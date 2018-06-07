package com.gaohan.gfrobots

object GfRobot_02 {

    var round = 6

    //    var fairy = true
    var fairy = false

    @JvmStatic
    fun main(args: Array<String>) = GfFuncs.sameOldFuck(round) { M.go();M.battle();GfFuncs.handleSupport() }

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

        fun go() {
            robot.click(GfButtons.main.combat).wait(3500)
            if (firstTime) {
                robot.click(GfButtons.mission.episode0).wait(500)
            }
            robot.click(GfButtons.mission.mission2).wait(500)
            robot.click(GfButtons.mission.btnLeft).wait(4000)
            if (firstTime) GfFuncs.minMap()
        }

        fun adjust() = GfFuncs.toBottom1()

        fun battle() {
            adjust()
            GfFuncs.formation_5(false, M.B.cat, GfButtons.formation.preset_1)
            setUnits()
            GfFuncs.supply(B.cat)
            GfFuncs.retreat(B.cat)
            GfFuncs.stop_battle_no_exit()

            adjust()
            GfFuncs.formation_5(false, M.B.cat, GfButtons.formation.preset_1)
            setUnits()
            round1()
            round2()
            GfFuncs.end_battle()
        }

        fun setUnits() {
            adjust()
            robot.click(B.cat).wait(500)
            robot.click(GfButtons.common.confirm).wait(500)
            robot.click(B.dog).wait(500)
            robot.click(GfButtons.common.confirm).wait(500)
            robot.click(GfButtons.common.confirm).wait(4500)
        }

        fun round1() {
            GfFuncs.fairy(B.cat, fairy) { adjust() }
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.cat).wait(300)
            robot.click(B.spot1).wait(300)
            GfFuncs.toTop2()
            robot.click(B.spot2).wait(300)
            robot.click(B.spot3).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(GfButtons.common.confirm).wait(2000)
            Thread.sleep(105 * 1000L)
            robot.click(GfButtons.common.confirm).wait(500)
            Thread.sleep(13 * 1000L)
        }

        fun round2() {
            GfFuncs.fairy(B.spot4, fairy)
            robot.click(GfButtons.battle.plan).wait(300)
            robot.click(B.spot4).wait(300)
            robot.click(B.spot5).wait(300)
            robot.click(B.spot6).wait(300)
            robot.click(GfButtons.common.confirm).wait(2000)
            Thread.sleep(70 * 1000L)
        }
    }


}