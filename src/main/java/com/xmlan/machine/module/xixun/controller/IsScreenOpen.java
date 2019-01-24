package com.xmlan.machine.module.xixun.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.module.xixun.util.IsScreenOpenData;
import com.xmlan.machine.module.xixun.util.ScreenHeightData;
import okhttp3.*;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * 监测led屏幕开关
 * @program: admachine
 * @description: con
 * @author: YSS
 * @create: 2019-01-15 16:05
 **/
public class IsScreenOpen {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        String resut;
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            resut = response.body().string();
        }catch (SocketTimeoutException e){
            resut = "timeOut";
        }catch (ConnectException e){
            resut = "timeOut";
        }
        return resut;
    }

    public String getIsScreenOpen(String led_code) {
        Gson gson = new Gson();
        IsScreenOpenData data = new IsScreenOpenData();
        String jsonData = gson.toJson(data);
        IsScreenOpen test = new IsScreenOpen();
        String url = BaseBean.URL +led_code; //check this
        String result;
        String resu = null;
        try {
            result = test.post(url, jsonData);
            boolean contain = result.contains("success");
            if (contain){
                JSONObject jsStr = JSONObject.parseObject(result);
                resu = jsStr.get("result").toString();
            }else {
                resu = result;
            }
            return resu;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resu;
    }
}
