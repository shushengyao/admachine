package com.xmlan.machine.test

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

//        println EncryptUtils.SHA256ForTenTimes('zhxm2512209')

        def string = 'ac-ca-12-13-4a-dc'
        if (!(string ==~ /([A-Fa-f0-9]{2}[:-]){5}[A-Fa-f0-9]{2}/)) {
            println 'Variable "string" is not a mac address.'
        } else {
            string = string.replaceAll('-', ':')
            println string
        }
    }

}
