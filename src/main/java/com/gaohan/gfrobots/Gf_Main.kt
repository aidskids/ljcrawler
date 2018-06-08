package com.gaohan.gfrobots

object Gf_Main {

    @JvmStatic
    fun main(args: Array<String>) = GfFuncs.initFrame {

        (1..28).forEach {
            M02(1, false)
            GfFuncs.fixFirt()
            M81n(1, true)
            GfFuncs.refresh()
        }

    }

    fun index() {
        Houqin()
        Friends(10)
        M02(18, false)
        M81n(20, true)
        M16(1)
    }
}