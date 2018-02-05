package com.xmlan.machine.test

import com.xmlan.machine.common.util.DateUtils
import com.xmlan.machine.common.util.EncodeUtils
import com.xmlan.machine.common.util.EncryptUtils
import org.apache.commons.lang3.math.NumberUtils
import org.junit.Test

/**
 * Created by ayakurayuki on 2018/1/17-10:59. <br/>
 * Package:  <br/>
 */
class StringPlayground {

    @Test
    void test() {
//        println("http://www.zhxmlan.com/mob/advertisementMachine/environment/3000/eyJwYXNzd29yZCI6ImMxMTlkYzdhZDkyZTg4MmVkYWI1ZmYxNjczYmE3ODE5Yjk2ZTljMzE1YjI0NjQyYWVmMTVjYmIxYTdlNjU3YjUiLCJhdXRobmFtZSI6InVzZXIifQ==".size())

//        println NumberUtils.isDigits("1.22314214214")
//        println NumberUtils.isDigits("22314214214")
//        println NumberUtils.isNumber("1.22314214214")
//        println NumberUtils.isDigits("-31.22314214214")
//        println NumberUtils.isDigits("-22314214214")
//        println NumberUtils.isNumber("-52.22314214214")

        println EncryptUtils.SHA256ForTenTimes('zhxm2512209')
    }

}
