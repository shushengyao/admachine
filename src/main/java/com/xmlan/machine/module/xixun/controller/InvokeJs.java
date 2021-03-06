package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.xmlan.machine.module.xixun.util.InvokeJsData;
import com.xmlan.machine.module.xixun.util.LoadUrlData;
import okhttp3.*;

import java.io.IOException;

/**
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-07-25 10:10
 **/
public class InvokeJs {
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
        InvokeJsData data = new InvokeJsData();
        String jsonData = gson.toJson(data);
        InvokeJs test = new InvokeJs();
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
