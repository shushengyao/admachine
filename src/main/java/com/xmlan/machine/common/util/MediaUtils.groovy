package com.xmlan.machine.common.util

import com.xmlan.machine.common.config.Global

import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse

/**
 * Created by ayakurayuki on 2018/1/8-10:30.
 * Package: com.xmlan.machine.common.util
 */
class MediaUtils {

    static void mediaTransfer(String url, HttpServletResponse response) throws Exception{
        new File("${Global.mediaPath}/${url}".toString()).withInputStream { input ->
            ServletOutputStream output = response.outputStream
                try {
                    byte[] bytes = null
                    while (input.available() > 0) {
                        bytes = new byte[input.available()]
                        input.read(bytes, 0, bytes.length)
                        output.write(bytes, 0, bytes.length)
                    }
                    output.flush()
                    output.close()
                }catch (Exception e){
                    throw e
                }
                finally {
                    if (input !=null){
                        input.close()
                    }
                    if (output !=null){
                        output.close()
                    }
                }
        }
    }
}
