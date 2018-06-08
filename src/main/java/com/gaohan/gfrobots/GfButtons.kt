package com.gaohan.gfrobots

object GfButtons {

    object main {
        val restore = makeButton(1379, 350)
        val dormitory = makeButton(1739, 317)
        val research = makeButton(1380, 537)
        val factory = makeButton(1738, 517)
        val combat = RobotButton(1260, 666, 1500, 816)
        val formation = makeButton(1740, 719)
        val empty = RobotButton(500, 300, 600, 400)
        val houqin = RobotButton(1013, 714, 1210, 768)
    }

    object restore {
        val pos6 = makeButton(1590, 469)
        val confirm = makeButton(1774, 820)
        val confirm2 = makeButton(1392, 756)
        val returnToBase = makeButton(95, 70)
    }

    object formation {
        val returnToBase = RobotButton(30, 30, 164, 105)
        val ech1 = makeButton(80, 210)
        val ech2 = makeButton(80, 340)
        val ech3 = makeButton(80, 470)
        val ech4 = makeButton(80, 600)
        val ech5 = makeButton(80, 730)
        val ech6 = makeButton(80, 860)
        val ech7 = makeButton(80, 990)
        val unit1 = RobotButton(243, 215, 439, 680)
        val unit2 = RobotButton(524, 215, 713, 680)
        val unit3 = RobotButton(811, 215, 973, 680)
        val unit4 = RobotButton(1150, 215, 1250, 680)
        val unit5 = RobotButton(1400, 215, 1500, 680)
        val preset = RobotButton(1661, 939, 1841, 975)
    }

    object preset {
        val usePreset = RobotButton(1661, 939, 1841, 975)
        val confirm = RobotButton(1661, 939, 1841, 975)
        val presets = RobotButton(1837, 360, 1907, 455)
        val preset1 = RobotButton(1114, 54, 1836, 164)
        val preset2 = RobotButton(1120, 239, 1843, 351)
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

    object combat {
        val returnToBase = RobotButton(30, 30, 164, 105)
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
    }

    object battle {
        val blank = makeButton(60, 351, 10)
        val plan = RobotButton(23, 865, 181, 900)
        val exePlan = RobotButton(1661, 939, 1841, 975)
        val endTurn = RobotButton(1661, 939, 1841, 975)
        val fairy = RobotButton(1767, 594, 1875, 623)
        val back_to_select = makeButton(110, 65, 30)
        val stop_battle = RobotButton(419, 31, 522, 91)
        val stop_left = RobotButton(651, 707, 820, 746)
        val stop_right = RobotButton(1101, 707, 1260, 746)

        val top = RobotButton(1000, 110, 1100, 120)
        val bottom = RobotButton(1000, 990, 1100, 1000)
        val middle = makeButton(1042, 561)

        val min_map_left = makeButton(20, 980, 10)
        val min_map_right = makeButton(742, 601, 10)
    }

    object unitInfo {
        val confirm = RobotButton(1661, 939, 1841, 975)
        val cancel = makeButton(1425, 971)
        val supply = RobotButton(1640, 809, 1885, 867)
        val retreat = RobotButton(1437, 950, 1556, 987)
        val retreat_sure = RobotButton(1042, 716, 1181, 757)
        val edit = RobotButton(259, 911, 470, 951)
        val ech6_no1 = makeButton(85, 99)
        val ech6_no2 = makeButton(85, 245)
        val ech6_no3 = makeButton(85, 393)
        val ech6_no4 = makeButton(85, 536)
        val ech6_no5 = makeButton(85, 682)
        val ech6_no6 = makeButton(85, 830)
        val ech6_friend = makeButton(85, 983)
        val ech5_no1 = makeButton(85, 175)
        val ech5_no2 = makeButton(85, 323)
        val ech5_no3 = makeButton(85, 465)
        val ech5_no4 = makeButton(85, 612)
        val ech5_no5 = makeButton(85, 763)
        val ech5_friend = makeButton(85, 907)
    }

    object itemSelect {
        private val x0 = 150
        private val y0 = 370
        private val xd = 270
        private val yd = 450
        val removeUnit = makeButton(x0, y0)
        fun byIndex(row: Int, col: Int) = makeButton(x0 + col * xd, y0 + row * yd)
    }

}