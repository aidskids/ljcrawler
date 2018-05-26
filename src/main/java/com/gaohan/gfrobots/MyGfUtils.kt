package com.gaohan.gfrobots

var firstTime = true

object GfFuncs {
    fun formation_5(battle: Boolean, airport: RobotButton, preset: RobotButton) {
        robot.click(airport).wait(500)
        robot.click(GfButtons.battle.formation).wait(4500)
        if (battle) {
            robot.click(GfButtons.common.confirm).wait(3000)
            robot.click(GfButtons.formation.preset_menu).wait(2500)
            robot.click(preset).wait(2500)
            robot.click(GfButtons.common.confirm).wait(3000)
            robot.click(GfButtons.common.confirm).wait(3000)
        } else {
            robot.click(GfButtons.formation.unit1).wait(500)
            robot.click(GfButtons.formation.remove_unit).wait(2000)
            robot.click(GfButtons.formation.unit2).wait(500)
            robot.click(GfButtons.formation.remove_unit).wait(2000)
            robot.click(GfButtons.formation.unit3).wait(500)
            robot.click(GfButtons.formation.remove_unit).wait(2000)
            robot.click(GfButtons.formation.unit4).wait(500)
            robot.click(GfButtons.formation.remove_unit).wait(2000)
            robot.drag(GfButtons.formation.unit5, GfButtons.formation.unit1).wait(2000)
        }
        robot.click(GfButtons.common.returnn).wait(5000)
    }

    fun stop_battle_yes_exit() {
        robot.click(GfButtons.battle.stop1).wait(300)
        robot.click(GfButtons.battle.stop_right).wait(4000)
    }

    fun stop_battle_no_exit() {
        robot.click(GfButtons.battle.stop1).wait(300)
        robot.click(GfButtons.battle.stop_left).wait(5500)
    }

    fun end_battle() {
        (1..14).forEach { robot.click(GfButtons.common.confirm).wait(300) }
        (1..14).forEach { robot.click(GfButtons.main.empty).wait(300) }
    }

    fun handleSupport() {
        (1..14).forEach { robot.click(GfButtons.main.houqin).wait(300) }
    }

    fun toTop1() {
        robot.drag(GfButtons.common.top, GfButtons.common.bottom).wait(300)
    }

    fun toTop2() {
        robot.drag(GfButtons.common.top, GfButtons.common.bottom).wait(300)
        robot.drag(GfButtons.common.top, GfButtons.common.bottom).wait(300)
    }

    fun toBottom1() {
        robot.drag(GfButtons.common.bottom, GfButtons.common.top).wait(300)
    }

    fun toBottom2() {
        robot.drag(GfButtons.common.bottom, GfButtons.common.top).wait(300)
        robot.drag(GfButtons.common.bottom, GfButtons.common.top).wait(300)
    }

    fun setUnit(btn: RobotButton) {
        robot.click(btn).wait(500)
        robot.click(GfButtons.common.confirm).wait(2500)
    }

    fun setEchelon(btn: RobotButton, ech: RobotButton, friend: Boolean = false) {
        robot.click(btn).wait(500)
        robot.click(ech).wait(500)
        if (friend) {
            robot.wait(4500)
            robot.click(makeButton(666, 291)).wait(2000)
        }
        robot.click(GfButtons.common.confirm).wait(2500)
    }

    fun supply(btn: RobotButton) {
        robot.click(btn).wait(300)
        robot.click(btn).wait(300)
        robot.click(GfButtons.battle.supply).wait(2200)
        robot.click(GfButtons.battle.blank).wait(300)
        robot.click(GfButtons.battle.blank).wait(300)
    }

    fun retreat(btn: RobotButton) {
        robot.click(btn).wait(300)
        robot.click(btn).wait(300)
        robot.click(GfButtons.battle.retreat).wait(500)
        robot.click(GfButtons.battle.retreat_sure).wait(3000)
    }

    fun fairy(btn: RobotButton) {
        robot.click(btn).wait(500)
        robot.click(GfButtons.battle.fairy).wait(500)
        robot.click(btn).wait(2000)
    }

    fun spotLeft(btn: RobotButton) = makeButton((btn.x2 + btn.x1) / 2 - 150, (btn.y2 + btn.y1) / 2)

    fun spotRight(btn: RobotButton) = makeButton((btn.x2 + btn.x1) / 2 + 150, (btn.y2 + btn.y1) / 2)
}

object GfButtons {

    object common {
        val confirm = RobotButton(1661, 939, 1841, 975)
        val returnn = RobotButton(30, 30, 164, 105)
        val top = RobotButton(1000, 110, 1100, 120)
        val bottom = RobotButton(1000, 990, 1100, 1000)
    }

    object main {
        val combat = RobotButton(1260, 666, 1500, 816)
        val empty = RobotButton(500, 300, 600, 400)
        val houqin = RobotButton(1013, 714, 1210, 768)
    }

    object mission {
        val emergency = RobotButton(1594, 239, 1702, 306)
        val midnight = makeButton(1822, 275, 35)
        val episode0 = makeButton(372, 218, 40)
        val episode1 = makeButton(372, 377, 40)
        val episode2 = makeButton(372, 533, 40)
        val episode3 = makeButton(372, 680, 40)
        val episode4 = RobotButton(330, 799, 434, 857)
        val episode5 = RobotButton(317, 953, 427, 1011)
        val episode6 = makeButton(375, 295, 40)
        val episode7 = makeButton(375, 450, 40)
        val episode8 = makeButton(375, 600, 40)
        val episode9 = makeButton(375, 755, 40)
        val episode10 = makeButton(375, 910, 40)
        val mission1 = makeButton(1199, 419, 40)
        val mission2 = RobotButton(722, 547, 1458, 637)
        val mission3 = RobotButton(680, 757, 1812, 783)
        val mission4 = makeButton(1199, 949, 40)
        val mission5 = makeButton(1199, 715, 40)
        val mission6 = makeButton(1199, 889, 40)
        val btnLeft = RobotButton(1021, 827, 1106, 901)
        val btnRight = makeButton(808, 868, 40)
        val confirm_stop = makeButton(1128, 742, 30)
    }

    object battle {
        val formation = RobotButton(259, 911, 470, 951)
        val ech6_1 = makeButton(85, 99)
        val ech6_2 = makeButton(85, 245)
        val ech6_3 = makeButton(85, 393)
        val ech6_4 = makeButton(85, 536)
        val ech6_5 = makeButton(85, 682)
        val ech6_6 = makeButton(85, 830)
        val ech6_fri = makeButton(85, 983)
        val ech5_1 = makeButton(85, 175)
        val ech5_2 = makeButton(85, 323)
        val ech5_3 = makeButton(85, 465)
        val ech5_4 = makeButton(85, 612)
        val ech5_5 = makeButton(85, 763)
        val ech5_fri = makeButton(85, 907)

        val supply = RobotButton(1640, 809, 1885, 867)
        val retreat = RobotButton(1437, 950, 1556, 987)
        val retreat_sure = RobotButton(1042, 716, 1181, 757)

        val fairy = RobotButton(1767, 594, 1875, 623)
        val plan = RobotButton(23, 865, 181, 900)

        val stop1 = RobotButton(419, 31, 522, 91)
        val stop_left = RobotButton(651, 707, 820, 746)
        val stop_right = RobotButton(1101, 707, 1260, 746)
        val back_to_select = makeButton(110, 65, 30)

        val blank = makeButton(109, 262)
    }

    object formation {
        val zhenxing = common.confirm
        val echelon1 = RobotButton(107, 190, 144, 237)
        val echelon2 = RobotButton(31, 305, 142, 371)
        val unit1 = RobotButton(243, 215, 439, 680)
        val unit2 = RobotButton(524, 215, 713, 680)
        val unit3 = RobotButton(811, 215, 973, 680)
        val unit4 = RobotButton(1150, 215, 1250, 680)
        val unit5 = RobotButton(1400, 215, 1500, 680)
        val remove_unit = RobotButton(34, 198, 224, 492)
        val preset_menu = RobotButton(1837, 360, 1907, 455)
        val preset_1 = RobotButton(1114, 54, 1836, 164)
        val preset_2 = RobotButton(1120, 239, 1843, 351)
        val gezi1 = makeButton(212, 210, 100)
        val gezi2 = makeButton(523, 210, 100)
        val gezi3 = makeButton(828, 210, 100)
        val gezi4 = makeButton(212, 507, 100)
        val gezi5 = makeButton(523, 507, 100)
        val gezi6 = makeButton(828, 507, 100)
        val gezi7 = makeButton(212, 820, 100)
        val gezi8 = makeButton(523, 820, 100)
        val gezi9 = makeButton(828, 820, 100)
    }

}