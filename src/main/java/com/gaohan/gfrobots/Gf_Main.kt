package com.gaohan.gfrobots

object Gf_Main {

    @JvmStatic
    fun main(args: Array<String>) = GfFuncs.initFrame {
        HotMix()
    }

    fun index() {
        HotMix()
        Houqin()
        Friends(10)
        M02(18, false)
        M81n(20, true)
        M16(1)
    }
}