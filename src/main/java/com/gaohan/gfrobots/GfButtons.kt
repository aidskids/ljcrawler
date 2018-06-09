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
        val refresh = makeButton(20, 539, 10)
    }

    object restore {
        val pos6 = makeButton(1590, 469)
        val confirm = makeButton(1774, 820)
        val confirm2 = makeButton(1392, 756)
        val returnToBase = makeButton(95, 70)
    }

    object formation {
        val returnToBase = RobotButton(30, 30, 164, 105)
        val echs = listOf(makeButton(0, 0)) + (0..6).map { makeButton(80, 210 + it * 130) }
        val units = listOf(makeButton(0, 0)) + (0..4).map { makeButton(340 + 280 * it, 450, 30) }
        val preset = RobotButton(1661, 939, 1841, 975)
    }

    object preset {
        val usePreset = RobotButton(1661, 939, 1841, 975)
        val confirm = RobotButton(1661, 939, 1841, 975)
        val showPreset = RobotButton(1837, 360, 1907, 455)
        val presets = listOf(makeButton(0, 0)) + (0..2).map { makeButton(1540, 125 + it * 190, 30) }
        fun getGrid(row: Int, col: Int) = makeButton(200 + (col - 1) * 300, 200 + (row - 1) * 300, 30)
    }

    object combat {
        val returnToBase = RobotButton(30, 30, 164, 105)
        val combatMission = makeButton(130, 220)
        val logisticSupport = makeButton(130, 360)
        val combatSimulation = makeButton(130, 500)
        val campaign = makeButton(130, 640)
        val emergency = RobotButton(1594, 239, 1702, 306)
        val midnight = makeButton(1822, 275, 35)
        private val episode0 = makeButton(372, 218, 40)
        private val episode1 = makeButton(372, 377, 40)
        private val episode2 = makeButton(372, 533, 40)
        private val episode3 = makeButton(372, 680, 40)
        private val episode4 = RobotButton(330, 799, 434, 857)
        private val episode5 = RobotButton(317, 953, 427, 1011)
        private val episode6 = makeButton(375, 295, 40)
        private val episode7 = makeButton(375, 450, 40)
        private val episode8 = makeButton(375, 600, 40)
        private val episode9 = makeButton(375, 755, 40)
        private val episode10 = makeButton(375, 910, 40)
        private val mission1 = makeButton(1199, 419, 40)
        private val mission2 = RobotButton(722, 547, 1458, 637)
        private val mission3 = RobotButton(680, 757, 1812, 783)
        private val mission4 = makeButton(1199, 949, 40)
        private val mission5 = makeButton(1199, 715, 40)
        private val mission6 = makeButton(1199, 889, 40)
        val episodeDrag = (6..10)
        val episodes = listOf(episode0, episode1, episode2, episode3, episode4, episode5, episode6, episode7, episode8, episode9, episode10)
        val missionDrag = (5..6)
        val missions = listOf(makeButton(0, 0), mission1, mission2, mission3, mission4, mission5, mission6)
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
        fun getEch(echAll: Int, echNo: Int): RobotButton {
            val (ymid, ydelta) = Pair(540, 146.25)
            val y0 = ymid - echAll * 0.5 * ydelta
            val y = (y0 + (echNo - 1) * ydelta).toInt()
            return makeButton(85, y)
        }
    }

    object itemSelect {
        fun getItem(row: Int, col: Int) = makeButton(150 + (col - 1) * 270, 370 + (row - 1) * 450)
    }

}