package com.gaohan.gfrobots

import java.util.*

object GfRobot_Friends {

    var round = 11

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
        GfFuncs.stop_battle_no_exit()
    }

    object M {

        object B {
            val spot1 = makeButton(1221, 127, 10)
            val airport_core = makeButton(989, 324, 40)
            val airport_dog = makeButton(393, 309, 30)
        }

        fun go() {
            if (firstTime) {
                robot.click(GfButtons.main.combat).wait(3500)
                robot.click(GfButtons.mission.episode0).wait(500)
                robot.click(GfButtons.mission.mission2).wait(500)
                robot.click(GfButtons.mission.btnLeft).wait(4000)
                firstTime = !firstTime
            }
        }

        fun battle() {
            setUnits()
            setFriends()
        }

        fun setUnits() {
            GfFuncs.toBottom1()
            GfFuncs.setEchelon(B.airport_core, GfButtons.battle.ech6_2)
            robot.click(GfButtons.common.confirm).wait(4500)
        }

        fun setFriends() {
            robot.click(B.airport_core).wait(500)
            robot.click(B.spot1).wait(2500)
            GfFuncs.setEchelon(B.airport_dog, GfButtons.battle.ech5_fri, true)
            GfFuncs.setEchelon(B.airport_core, GfButtons.battle.ech5_fri, true)
        }

    }

}