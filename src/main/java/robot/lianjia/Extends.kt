package com.gaohan.robot.lianjia

import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent.*

/**
 * 使用awt实现的
 * Created by tend on 2017/5/17.
 */

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

fun Robot.pressKey(vararg keyStream: List<Int>) {
    keyStream.forEach {
        it.forEach { this.keyPress(it) }
        this.delay(100)
        it.forEach { this.keyRelease(it) }
        this.delay(500)
    }
}

val ENTER = listOf(VK_ENTER)
val ESC = listOf(VK_ESCAPE)
val ALT_TAB = listOf(VK_ALT, VK_TAB)
val CTRL_A = listOf(VK_CONTROL, VK_A)
val CTRL_C = listOf(VK_CONTROL, VK_C)
val CTRL_V = listOf(VK_CONTROL, VK_V)
val CTRL_T = listOf(VK_CONTROL, VK_T) // 新建标签页
val CTRL_W = listOf(VK_CONTROL, VK_W) // 关闭标签页
val CTRL_L = listOf(VK_CONTROL, VK_L) // 地址栏
val CTRL_U = listOf(VK_CONTROL, VK_U) // 看html
val CTRL_F = listOf(VK_CONTROL, VK_F) // 查找

