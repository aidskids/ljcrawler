package com.gaohan.gfrobots.outdate

import com.gaohan.gfrobots.ALT_TAB
import com.gaohan.gfrobots.press
import java.awt.Robot
import java.awt.event.KeyEvent

/**
 * Created by gaohan on 2018/2/5.
 */

/*
1 清理仓库
2 缩小地图m
 */
object GfRobot2 {

    val battleTime = 110
    var round: Int = 20

    var fairy: Boolean = false
//    var fairy: Boolean = true

    // 在下一场中，第一梯队，设置为第几预设的打手
    // true为m4a1，false为star
//    var first: Boolean = true
    var first: Boolean = false

    val robot = Robot()

    @JvmStatic
    fun main(args: Array<String>) {
        run()
    }

    fun run() {
        init()
        (1..round).forEach {
            println("$it/${round}\tcombat begin")
            cycle()
            println("$it/${round}\tcombat end")
        }
    }

    fun init() {
        robot.press(ALT_TAB)
    }

    fun cycle() {
        F.go_43e()
        F.formation()
        F.setUnits()
        F.supply()
        F.battle()
        F.end_battle()
        F.handleSupport()
        first = !first
    }

    object F {

        fun go_43e() {
            robot.press(KeyEvent.VK_R, 1500)
            robot.press(KeyEvent.VK_E, 500)
            robot.press(KeyEvent.VK_R, 500)
            robot.press(KeyEvent.VK_W, 2500)
        }

        fun formation() {
            robot.press(KeyEvent.VK_Y, 500)
            robot.press(KeyEvent.VK_F, 2000)

            robot.press(KeyEvent.VK_K, 500)
            robot.press(KeyEvent.VK_T, 500)
            robot.press(KeyEvent.VK_K, 500)
            robot.press(KeyEvent.VK_Z, 1000)
            robot.press(KeyEvent.VK_D, 500)
            if (first) {
                robot.press(KeyEvent.VK_2, 500)
            } else {
                robot.press(KeyEvent.VK_1, 500)
            }
            robot.press(KeyEvent.VK_Z, 500)
            robot.press(KeyEvent.VK_S, 500)
            robot.press(KeyEvent.VK_Z, 500)
            robot.press(KeyEvent.VK_Z, 500)

            robot.press(KeyEvent.VK_J, 500)
            robot.press(KeyEvent.VK_Z, 500)
            robot.press(KeyEvent.VK_D, 500)
            if (first) {
                robot.press(KeyEvent.VK_1, 500)
            } else {
                robot.press(KeyEvent.VK_2, 500)
            }
            robot.press(KeyEvent.VK_Z, 500)
            robot.press(KeyEvent.VK_S, 500)
            robot.press(KeyEvent.VK_Z, 500)
            robot.press(KeyEvent.VK_Z, 500)

            robot.press(KeyEvent.VK_X, 3000)
        }

        fun setUnits() {
            robot.press(KeyEvent.VK_Y, 500)
            robot.press(KeyEvent.VK_Z, 500)
            robot.press(KeyEvent.VK_T, 500)
            robot.press(KeyEvent.VK_Z, 500)
            robot.press(KeyEvent.VK_Z, 3500)
        }

        fun supply() {
            robot.press(KeyEvent.VK_T, 500)
            robot.press(KeyEvent.VK_T, 500)
            robot.press(KeyEvent.VK_5, 500)
        }

        fun battle() {
            if (fairy) {
                robot.press(KeyEvent.VK_Y, 500)
                robot.press(KeyEvent.VK_H, 500)
                robot.press(KeyEvent.VK_Y, 1500)
            } else {
                robot.press(KeyEvent.VK_B, 800)
            }
            robot.press(KeyEvent.VK_Q, 300)
            robot.press(KeyEvent.VK_3, 300)
            robot.press(KeyEvent.VK_4, 300)
            robot.press(KeyEvent.VK_G, 1000)
            robot.press(KeyEvent.VK_5, 300)
            robot.press(KeyEvent.VK_6, 300)
            robot.press(KeyEvent.VK_4, 300)
            robot.press(KeyEvent.VK_Z, battleTime * 1000)
        }

        fun end_battle() {
            (1..28).forEach { robot.press(KeyEvent.VK_Z, 300) }
        }

        fun handleSupport() {
            (1..7).forEach { robot.press(KeyEvent.VK_M, 300) }
        }

    }

}