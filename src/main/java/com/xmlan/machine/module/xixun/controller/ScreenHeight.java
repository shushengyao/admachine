package com.xmlan.machine.module.xixun.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.module.xixun.util.ScreenHeightData;
import okhttp3.*;

import java.io.IOException;

/**
 * @program: admachine
 * @description: con
 * @author: YSS
 * @create: 2018-12-15 13:45
 **/
public class ScreenHeight {
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

    public String getScreenHeight(String led_code) {
        Gson gson = new Gson();
        ScreenHeightData data = new ScreenHeightData();
        String jsonData = gson.toJson(data);
        Clear test = new Clear();
        String url = BaseBean.URL +led_code; //check this
        String result;
        try {
            result = test.post(url, jsonData);
            JSONObject jsStr = JSONObject.parseObject(result);
            String height = jsStr.get("result").toString();
            return height;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "64";
    }
}
