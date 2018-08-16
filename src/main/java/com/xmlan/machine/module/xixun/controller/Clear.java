package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.module.xixun.util.ClearData;
import okhttp3.*;

import java.io.IOException;

/**
 * 清除
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-07-25 10:11
 **/
public class Clear {
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

    public  void clea(String led_code) {
        Gson gson = new Gson();
        ClearData data = new ClearData();
        String jsonData = gson.toJson(data);
        Clear test = new Clear();
//        String url = "http://192.168.6.104:8081/command/y10-518-00147"; //check this
        String url = BaseBean.URL +led_code; //check this
        String result;
        try {
            result = test.post(url, jsonData);
            System.out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
