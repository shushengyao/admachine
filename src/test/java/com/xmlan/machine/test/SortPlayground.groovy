package com.xmlan.machine.test

import org.junit.Test

/**
 * Created by ayakurayuki on 2018/1/12-18:13.
 * Package: 
 */
class SortPlayground {

    @Test
    void sort() {
        def list = [
                ["longitude": "1.0", "latitude": "3.0"],
                ["longitude": "1.0", "latitude": "4.0"],
                ["longitude": "2.0", "latitude": "2.0"],
                ["longitude": "1.0", "latitude": "2.0"]
        ]
        list.sort { l, r ->
            if (l.longitude.toDouble() < r.longitude.toDouble()) {
                return -1
            } else if (l.longitude.toDouble() == r.longitude.toDouble()) {
                l.latitude.toDouble() <=> r.latitude.toDouble()
            } else {
                return 1
            }
        }
        println list
    }

}
