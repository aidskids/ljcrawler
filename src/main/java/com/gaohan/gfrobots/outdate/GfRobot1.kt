package com.gaohan.gfrobots.outdate

import com.gaohan.gfrobots.ALT_TAB
import com.gaohan.gfrobots.press
import java.awt.Robot
import java.awt.event.KeyEvent.*

/**
 * Created by gaohan on 2018/2/5.
 */

/*
1 清理仓库
2 缩小地图m
 */
object GfRobot {

    val battleTime = 120000
    var round: Int = 2
    var fairy: Boolean = false
//    var fairy: Boolean = true
    var first: String = "supply"
//    var first: String = "battle"

   private val robot = Robot()
    val long = 6000
    val middle = 3000
    val second = 2000
    val short = 1000
    val blink = 500
    val none = 300

    @JvmStatic
    fun main(args: Array<String>) {
        run()
    }

    fun run() {
        init()
        (1..round).forEach {
            when (first) {
                "battle" -> {
                    doSup(it)
                    doBat(it)
                }
                "mission" -> {
                    doBat(it)
                    doSup(it)
                }
            }
        }
    }

    fun doBat(it: Int) {
        println("$it/${round}\tcombat begin")
        battle()
        println("$it/${round}\tcombat end")
    }

    fun doSup(it: Int) {
        println("$it/${round}\tsupply begin")
        supply()
        println("$it/${round}\tsupply end")
    }

    fun init() {
        robot.press(ALT_TAB)
    }

    fun supply() {
        robot.press(VK_F, second)
        robot.press(VK_Z, blink)
        robot.press(VK_D, blink)
        robot.press(VK_2, blink)
        robot.press(VK_Z, blink)
        robot.press(VK_S, blink)
        robot.press(VK_Z, blink)
        robot.press(VK_Z, short)
        robot.press(VK_X, short)
        F.handleSupport()
        F.go_43e()
        F.setUnits()
        robot.press(VK_Y, blink)
        robot.press(VK_Y, blink)
        robot.press(VK_5, blink)
        robot.press(VK_Y, blink)
        robot.press(VK_U, blink)
        robot.press(VK_M, blink)
        robot.press(VK_I, none)
        robot.press(VK_M, second)
        F.handleSupport()
    }

    fun battle() {
        robot.press(VK_F, second)
        robot.press(VK_Z, blink)
        robot.press(VK_D, blink)
        robot.press(VK_1, blink)
        robot.press(VK_Z, blink)
        robot.press(VK_Z, short)
        robot.press(VK_X, short)
        F.handleSupport()
        F.go_43e()
        F.setUnits()
        if (fairy) {
            robot.press(VK_Y, blink)
            robot.press(VK_H, blink)
            robot.press(VK_Y, second)
        } else {
            robot.press(VK_B, short)
        }
        robot.press(VK_Q, none)
        robot.press(VK_3, none)
        robot.press(VK_4, none)
        robot.press(VK_G, second)
        robot.press(VK_5, none)
        robot.press(VK_6, none)
        robot.press(VK_4, none)
        robot.press(VK_Z, battleTime)
        F.end_battle()
        F.handleSupport()

    }

    object F {

        fun go_43e() {
            robot.press(VK_R, second)
            robot.press(VK_E, short)
            robot.press(VK_R, short)
            robot.press(VK_W, long)
        }

        fun setUnits() {
            robot.press(VK_Y, blink)
            robot.press(VK_Z, blink)
            robot.press(VK_T, blink)
            robot.press(VK_Z, blink)
            robot.press(VK_Z, long)
        }

        fun handleSupport() {
            (1..5).forEach { robot.press(VK_M, second /3) }
        }

        fun end_battle() {
            (1..18).forEach { robot.press(VK_Z, second / 3) }
        }
    }

}



