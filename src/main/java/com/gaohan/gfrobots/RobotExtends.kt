package com.gaohan.gfrobots

import java.awt.*
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import javax.swing.JFrame

/**
 * 使用awt实现的
 * Created by tend on 2017/5/17.
 */

val robot = Robot()

object FrameUtils {
    fun initFrame() {
        val kit = Toolkit.getDefaultToolkit()             //定义工具包
        val screenSize = kit.screenSize            //获取屏幕的尺寸
        val screenWidth = screenSize.width                    //获取屏幕的宽
        val screenHeight = screenSize.height                  //获取屏幕的高

        EventQueue.invokeLater {
            val frame = JFrame("Example")
            kit.addAWTEventListener({ e ->
                if (e.id == KeyEvent.KEY_PRESSED) {
                    val evt = e as KeyEvent
                    if (evt.keyCode == KeyEvent.VK_ESCAPE) {
                        frame.dispose()
                        System.exit(666)
                    }
                }
            }, AWTEvent.KEY_EVENT_MASK)
            frame.preferredSize = Dimension(screenWidth, screenHeight)
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.isLocationByPlatform = true
            frame.pack()
            frame.isVisible = true

            val windowWidth = frame.width                    //获得窗口宽
            val windowHeight = frame.height                  //获得窗口高
            frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2)//设置窗口居中显示
        }

        Thread.sleep(1000)
    }

    fun switchFrame() {
        robot.keyPress(KeyEvent.VK_ALT)
        robot.delay(100)
        robot.keyPress(KeyEvent.VK_TAB)
        robot.delay(100)
        robot.keyRelease(KeyEvent.VK_TAB)
        robot.delay(100)
        robot.keyPress(KeyEvent.VK_TAB)
        robot.delay(100)
        robot.keyRelease(KeyEvent.VK_TAB)
        robot.delay(100)
        robot.keyRelease(KeyEvent.VK_ALT)
        robot.delay(100)
    }

    fun beep() {
        java.awt.Toolkit.getDefaultToolkit().beep()
    }
}

fun Robot.drag(btn1: RobotButton, btn2: RobotButton): Robot {
    val x1 = btn1.x
    val y1 = btn1.y
    val x2 = btn2.x
    val y2 = btn2.y
    this.moveJump(x1, y1)
    this.hold()
    this.moveLine(x2, y2)
    this.release()
    return this
}

fun Robot.clickForSec(btn: RobotButton, sec: Int): Robot {
    (1..sec * 2).forEach { robot.click(btn).wait(300) }
    return this
}

//200ms + 移动时间
fun Robot.click(btn: RobotButton): Robot {
    val x = btn.x
    val y = btn.y
    this.moveJump(x, y)
    this.hold() //100ms
    this.release() //100ms
    return this
}

fun Robot.clickMid(): Robot {
    this.hold(InputEvent.BUTTON2_MASK)
    this.release(InputEvent.BUTTON2_MASK)
    return this
}

data class RobotButton(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    val x get() = Math.round((x2 - x1) * Math.random()).toInt() + x1
    val y get() = Math.round((y2 - y1) * Math.random()).toInt() + y1
}

fun makeButton(x: Int, y: Int, radius: Int = 20): RobotButton {
    return RobotButton(x - radius, y - radius, x + radius, y + radius)
}

fun Robot.hold(type: Int = InputEvent.BUTTON1_MASK) = this.wait(100).mousePress(type)

fun Robot.release(type: Int = InputEvent.BUTTON1_MASK) = this.wait(100).mouseRelease(type)

fun Double.ceil() = Math.ceil(this).toInt()
fun Int.pow() = Math.pow(this.toDouble(), 2.0).toInt()
fun Int.sqrt() = Math.sqrt(this.toDouble()).ceil()

fun Robot.moveJump(x: Int, y: Int) {
    this.mouseMove(x, y)
}

private fun Robot.moveLine(x2: Int, y2: Int) {
    val (x1, y1) = MouseInfo.getPointerInfo().location.let { Pair(it.x, it.y) }
    val direct_x = if (x2 - x1 > 0) 1 else -1
    val direct_y = if (y2 - y1 > 0) 1 else -1
    val d_x = x2 - x1
    val d_y = y2 - y1
    val d_x_2 = d_x.pow()
    val d_y_2 = d_y.pow()
    val d_z_2 = d_x_2 + d_y_2
    val d_z = d_z_2.sqrt()
    (1..d_z)
            .map { some_z ->
                val some_z_2 = some_z.pow()
                val some_x_2 = (some_z_2.toDouble() / (d_x_2 + d_y_2) * d_x_2).ceil()
                val some_y_2 = (some_z_2.toDouble() / (d_x_2 + d_y_2) * d_y_2).ceil()
                val some_x = some_x_2.sqrt()
                val some_y = some_y_2.sqrt()
                val tarx = x1 + some_x * direct_x
                val tary = y1 + some_y * direct_y
                Pair(tarx, tary)
            }
            .forEachIndexed { index, it ->
                this.mouseMove(it.first, it.second)
                if (index % 3 == 0) Thread.sleep(0, 1)
            }
}

fun Robot.moveCurl(x2: Int, y2: Int, shuffle_rate: Double = 0.5) {
    val (x1, y1) = MouseInfo.getPointerInfo().location.let { Pair(it.x, it.y) }
    val direct_x = if (x2 - x1 > 0) 1 else -1
    val direct_y = if (y2 - y1 > 0) 1 else -1
    val d_x = x2 - x1
    val d_y = y2 - y1
    val d_x_2 = d_x.pow()
    val d_y_2 = d_y.pow()
    val d_z_2 = d_x_2 + d_y_2
    val d_z = d_z_2.sqrt()
    val len = 10 + (Math.random() * 3).ceil()
    val slices = d_z / len + 1
    val points = (1..slices).map { slice ->
        val some_z = Math.min(slice * len, d_z)
        val some_z_2 = some_z.pow()
        val some_x_2 = (some_z_2.toDouble() / (d_x_2 + d_y_2) * d_x_2).ceil()
        val some_y_2 = (some_z_2.toDouble() / (d_x_2 + d_y_2) * d_y_2).ceil()
        val some_x = some_x_2.sqrt()
        val some_y = some_y_2.sqrt()
        val tarx = x1 + some_x * direct_x
        val tary = y1 + some_y * direct_y
        Pair(tarx, tary)
    }
    val pointsr = points.mapIndexed { index, pt ->
        if (index == points.size - 1) pt else {
            val rx = (Math.random() * len * shuffle_rate).ceil()
            val ry = (Math.random() * len * shuffle_rate).ceil()
            Pair(pt.first + rx, pt.second + ry)
        }
    }
    pointsr.forEach { this.moveLine(it.first, it.second) }
}

fun Robot.wait(ms: Int): Robot {
    this.delay(ms)
    return this
}

fun Robot.waitForSec(s: Int): Robot {
    Thread.sleep(s * 1000L)
    return this
}

//=============================================================================================

fun setClip(data: String?) {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val transferable = StringSelection(data)
    clipboard.setContents(transferable, null)
}

fun getClip(): String? {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val transferable = clipboard.getContents(null)
    return transferable.getTransferData(DataFlavor.stringFlavor) as String?
}

fun clearClip() = setClip(null)

val ALT_TAB = listOf(VK_ALT, VK_TAB)
val CTRL_A = listOf(VK_CONTROL, VK_A)
val CTRL_C = listOf(VK_CONTROL, VK_C)
val CTRL_V = listOf(VK_CONTROL, VK_V)
val CTRL_T = listOf(VK_CONTROL, VK_T) // 新建标签页
val CTRL_W = listOf(VK_CONTROL, VK_W) // 关闭标签页
val CTRL_L = listOf(VK_CONTROL, VK_L) // 地址栏
val CTRL_U = listOf(VK_CONTROL, VK_U) // 看html
val CTRL_F = listOf(VK_CONTROL, VK_F) // 查找

fun Robot.press(key: Int, millis: Int = 0) {
    this.press(listOf(key), millis)
}

fun Robot.press(keyGroup: List<Int>, millis: Int = 0) {
    println("\t${keyGroup.map { KeyEvent.getKeyText(it) }}")
    keyGroup.forEach { this.keyPress(it) }
    this.delay(100)
    keyGroup.forEach { this.keyRelease(it) }
    this.delay(300)
    Thread.sleep(millis.toLong())
}