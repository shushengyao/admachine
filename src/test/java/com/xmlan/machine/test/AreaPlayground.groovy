package com.xmlan.machine.test

import org.junit.Test

/**
 * Created by ayakurayuki on 2018/1/12-18:04.
 */
class AreaPlayground {

    def static area = ["minLon": "1.00", "minLat": "2.10", "maxLon": "10.12", "maxLat": "11.23"]

    @Test
    void test() {
        if (isInArea("longitude", "1.324", area)) {
            if (isInArea("latitude", "10.46", area)) {
                println true
            } else {
                println false
            }
        } else {
            println false
        }
    }

    static boolean isInArea(String flag, String value, LinkedHashMap<String, String> area) {
        switch (flag) {
            case "longitude":
                return value.toDouble() >= area.minLon.toDouble() && value.toDouble() <= area.maxLon.toDouble()
            case "latitude":
                return value.toDouble() >= area.minLat.toDouble() && value.toDouble() <= area.maxLat.toDouble()
            default:
                return false
        }
    }

}
