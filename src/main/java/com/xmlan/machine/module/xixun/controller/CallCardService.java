package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import com.xmlan.machine.module.xixun.util.CallCardServiceData;
import com.xmlan.machine.module.xixun.util.SetPlayListData;

import java.io.IOException;

/**
 * 设置屏幕开关
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-07-26 14:49
 **/
public class CallCardService {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
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

    public static void main(String[] args) {
        Gson gson = new Gson();
        CallCardServiceData data = new CallCardServiceData();
        String jsonData = gson.toJson(data);
        CallCardService test = new CallCardService();
//        String url = "http://192.168.6.104:8081/command/y10-518-00147"; //check this
        String url = "http://192.168.0.218:8081/command/y10-518-00147"; //check this
        String result;
        try {
            result = test.post(url, jsonData);
            System.out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
