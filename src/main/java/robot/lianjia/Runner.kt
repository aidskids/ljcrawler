package com.gaohan.robot.lianjia

import org.jsoup.Jsoup
import java.awt.Robot

/**
 * 使用awt实现的
 * Created by tend on 2017/5/17.
 */

// 1. 无法判断页面是否加载完成 ok
// 2. 不能记录浏览器的操作日志
// 3. 运行过程中没有办法停止

val robot = Robot()

fun main(args: Array<String>) {

//    val district = "dongcheng"
//    val pages = (1..2).toCollection(mutableListOf()).map { "pg$it" }
//    val seeds = pages.map { "http://bj.lianjia.com/xiaoqu/$district/$it/" }
//    init()
//    visit(seeds)

    init()
    val html = getHtml("http://www.google.com/")
    println(html)
}

fun init() {
    robot.pressKey(ALT_TAB)
}

fun visit(seeds: List<String>) {
    seeds.forEach {
        val html = getHtml(it)
        val body = Jsoup.parseBodyFragment(html).body()
        val names = body.select("div.info div.title a").map { Pair(it.attr("href").split("/").filter { it.matches("\\d+".toRegex()) }.first(), it.text()) }
        names.forEach {
            robot.pressKey(CTRL_F)
            setClip(it.second)
            robot.pressKey(CTRL_V, ESC, ENTER)
            val args = getArgs(it.first, getHtml())
            robot.pressKey(CTRL_W)
            println(args)
        }
    }
}

fun getHtml(url: String? = null, delay: Int = 2000): String {
    if (url != null) {
        robot.pressKey(CTRL_T, CTRL_L)
        setClip(url)
        robot.pressKey(CTRL_V, ENTER)
    }
    robot.delay(delay)
    clearClip()
    robot.pressKey(CTRL_U, CTRL_A, CTRL_C, CTRL_W, CTRL_W)
    val html = getClip()
    if (html == "view-source:chrome-search://local-ntp/local-ntp.html") {
        return getHtml(url, delay * 2)
    }
    if (html == null) {
        throw RuntimeException("404")
    }
    return html
}

fun getArgs(xiaoquId: String, html: String): List<String> {
    val body = Jsoup.parseBodyFragment(html).body()
    val args = mutableListOf<String>()
    args.add(xiaoquId)
    args.add(body.select(".detailTitle").first().text())
    args.add(body.select(".detailDesc").first().text())
    val xiaoquPrice = body.select("div.xiaoquPrice").select("div.fl").first().text()
    if (xiaoquPrice.contains("暂无参考均价")) {
        args.add("暂无参考均价")
        args.add("暂无参考均价")
    } else {
        args.add(body.select("span.xiaoquUnitPrice").first().text())
        args.add(body.select("span.xiaoquUnitPriceDesc").first().text())
    }
    val infoLabels = arrayOf("建筑年代", "建筑类型", "物业费用", "物业公司", "开发商", "楼栋总数", "房屋总数", "附近门店")
    infoLabels.map {
        body.select("div.xiaoquInfoItem")
                .filter { e -> e.select("span.xiaoquInfoLabel").first().text().contains(it) }
                .map { it.select("span.xiaoquInfoContent").first().text() }
                .first()
    }.mapTo(args) { it }
    return args
}