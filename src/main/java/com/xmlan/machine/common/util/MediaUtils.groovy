package com.xmlan.machine.common.util

import com.xmlan.machine.common.config.Global

import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse

/**
 * Created by ayakurayuki on 2018/1/8-10:30.
 * Package: com.xmlan.machine.common.util
 */
class MediaUtils {

    static void mediaTransfer(String url, HttpServletResponse response) {
        new File("${Global.mediaPath}/${url}".toString()).withInputStream { input ->
            ServletOutputStream output = response.outputStream
            byte[] bytes = null
            while (input.available() > 0) {
                if (input.available() > 10240) {
                    bytes = new byte[10240]
                } else {
                    bytes = new byte[input.available()]
                }
                input.read(bytes, 0, bytes.length)
                output.write(bytes, 0, bytes.length)
            }
            output.flush()
            output.close()
        }
    }

}
