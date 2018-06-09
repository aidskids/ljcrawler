package com.gaohan.gfrobots

class HotMix(round: Int = 28) {
    init {
        (1..round).forEach {
            M02(1, false)
            GfFuncs.fixFirst()
            GfFuncs.handleSupport()
            M81n(1, true)
            GfFuncs.refresh()
        }
    }
}

class Houqin {
    init {
        while (true) {
            (1..20).forEach {
                robot.waitForSec(5 + (5 * Math.random()).toInt())
                robot.click(GfButtons.main.houqin)
            }
            GfFuncs.refresh()
        }
    }
}

class Friends(val round: Int) {
    init {
        GfFuncs.loop(1) { battle().let { GfFuncs.handleSupport() } }
    }

    fun battle() {
        GfFuncs.go(0, 2).let { B.adjust() }
        (1..(round)).forEach {
            GfFuncs.prepUnits(Triple(B.cat, 6, 2))
            round1()
            if (it < round) GfFuncs.battleRestart().let { B.adjust() }
            if (it == round) GfFuncs.battleRetreat()
        }
    }

    fun round1() {
        robot.click(B.cat).wait(500)
        robot.click(B.spot1).wait(2500)
        GfFuncs.setUnit(B.dog, 5, 6)
        GfFuncs.setUnit(B.cat, 5, 6)
    }

    object B {
        fun adjust() = GfFuncs.toBottom()
        val spot1 = makeButton(1221, 127, 10)
        val cat = makeButton(989, 324, 40)
        val dog = makeButton(393, 309, 30)
    }
}


class M02(round: Int, val fairy: Boolean) {

    init {
        GfFuncs.loop(round) { battle().let { GfFuncs.handleSupport() } }
    }

    fun battle() {
        GfFuncs.go(0, 2).let { B.adjust() }
        GfFuncs.fighterSwitch(B.cat, 1, false).let { B.adjust() }
        GfFuncs.prepUnits(B.cat, B.dog)
        GfFuncs.supply(B.cat).let { GfFuncs.retreat(B.cat) }.let { GfFuncs.battleRestart() }.let { B.adjust() }
        GfFuncs.fighterSwitch(B.cat, 1, true).let { B.adjust() }
        GfFuncs.prepUnits(B.cat, B.dog)
        round1().let { GfFuncs.endTurn(14) }
        round2().let { GfFuncs.battleComplete() }
    }

    fun round1() {
        if (fairy) GfFuncs.fairy(B.cat).let { B.adjust() }
        GfFuncs.exePlan(107, B.cat, B.spot1, { GfFuncs.toTop(2) }, B.spot2, B.spot3, B.spot4)
    }

    fun round2() {
        if (fairy) GfFuncs.fairy(B.spot4)
        GfFuncs.exePlan(72, B.spot4, B.spot5, B.spot6)
    }

    object B {
        fun adjust() = GfFuncs.toBottom()
        val cat = makeButton(989, 324, 40)
        val dog = makeButton(393, 309, 30)
        val spot1 = makeButton(785, 210, 15)
        val spot2 = makeButton(804, 843, 30)
        val spot3 = makeButton(1005, 534, 30)
        val spot4 = makeButton(784, 391, 30)
        val spot5 = makeButton(1213, 387, 30)
        val spot6 = makeButton(1492, 448, 30)
    }
}


class M81n(round: Int, val fairy: Boolean) {

    init {
        GfFuncs.loop(round) { battle().let { GfFuncs.handleSupport() } }
    }

    fun battle() {
        GfFuncs.go(8, 1, true).let { B.adjust() }
        GfFuncs.fighterSwitch(B.cat, 2, false).let { B.adjust() }
        GfFuncs.prepUnits(B.cat, B.dog)
        GfFuncs.supply(B.cat).let { GfFuncs.retreat(B.cat) }.let { GfFuncs.battleRestart() }.let { B.adjust() }
        GfFuncs.fighterSwitch(B.cat, 2, true).let { B.adjust() }
        GfFuncs.prepUnits(B.cat, B.dog)
        round1().let { GfFuncs.battleRetreat() }
    }

    fun round1() {
        if (fairy) GfFuncs.fairy(B.cat)
        GfFuncs.exePlan(145, B.cat, B.spot1, B.spot2, B.spot3, B.spot4, B.spot5, B.spot6).let { B.adjust() }
        GfFuncs.retreat(B.spot6)
    }

    object B {
        fun adjust() = GfFuncs.toTop()
        val cat = makeButton(361, 189)
        val dog = makeButton(1695, 304)
        val spot1 = makeButton(485, 384)
        val spot2 = makeButton(213, 438)
        val spot3 = makeButton(233, 725)
        val spot4 = makeButton(473, 549)
        val spot5 = makeButton(445, 789)
        val spot6 = makeButton(299, 928, 5)
    }
}


class M16(round: Int) {

    init {
        GfFuncs.loop(round) { battle().let { GfFuncs.handleSupport() } }
    }

    fun battle() {
        GfFuncs.go(1, 6).let { B.adjust() }
        GfFuncs.prepUnits(B.spot0)
        round1().let { GfFuncs.endTurn(22) }.let { B.adjust() }
        round2().let { GfFuncs.endTurn(28) }.let { B.adjust() }
        round3().let { GfFuncs.endTurn(35) }.let { B.adjust() }
        round4().let { GfFuncs.battleComplete() }
    }

    fun round1() {
        GfFuncs.supply(B.spot0)
        GfFuncs.exePlan(30, B.spot0, B.spot1, B.spot2)
    }

    fun round2() {
        GfFuncs.exePlan(30, B.spot2, B.spot3, B.spot4)
    }

    fun round3() {
        GfFuncs.supply(B.spot4)
        GfFuncs.exePlan(50, B.spot4, B.spot5, B.spot6, B.spot7)
    }

    fun round4() {
        GfFuncs.exePlan(50, B.spot7, B.spot8, B.spot9)
    }

    object B {
        fun adjust() = GfFuncs.toTop()
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
}

