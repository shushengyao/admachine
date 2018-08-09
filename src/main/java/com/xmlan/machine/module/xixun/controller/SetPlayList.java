package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import com.xmlan.machine.module.xixun.util.SetPlayListData;

import java.io.IOException;

/**
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-07-26 14:39
 **/
public class SetPlayList {
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
        SetPlayListData data = new SetPlayListData();
        String jsonData = gson.toJson(data);
        SetPlayList test = new SetPlayList();
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
