package com.gaohan.gfrobots


/**
 * Created by gaohan on 2018/2/5.
 */

object GfRobot_Houqin {

    @JvmStatic
    fun main(args: Array<String>) {
        FrameUtils.initFrame()
        FrameUtils.switchFrame()
        while(true) {
            cycle()
        }
    }

    fun cycle() {
        GfFuncs.handleSupport()
        robot.click(GfButtons.main.combat).wait(4000)
        robot.click(GfButtons.common.returnn).wait(4000)
    }

}