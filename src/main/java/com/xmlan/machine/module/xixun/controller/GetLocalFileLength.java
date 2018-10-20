package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.module.xixun.util.ClearData;
import com.xmlan.machine.module.xixun.util.GetLocalFileLengthData;
import okhttp3.*;

import java.io.IOException;

/**
 * @program: admachine
 * @description: con
 * @author: YSS
 * @create: 2018-10-20 18:27
 **/
public class GetLocalFileLength {public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static void main(String[] arge) {
        Gson gson = new Gson();
        GetLocalFileLengthData data = new GetLocalFileLengthData();
        data.path = "/abc/2018-10-20-18-19-46.mp4";
        String jsonData = gson.toJson(data);
        GetLocalFileLength test = new GetLocalFileLength();
        String url = "http://192.168.0.218:8081/command/y10-518-00147"; //check this
//        String url = BaseBean.URL +led_code; //check this
        String result;
        try {
            result = test.post(url, jsonData);
            System.out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
