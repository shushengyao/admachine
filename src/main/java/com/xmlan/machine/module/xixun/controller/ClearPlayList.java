package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.module.xixun.util.ClearPlayListData;
import okhttp3.*;

import java.io.IOException;

/**
 * 清除播放列表
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-07-26 15:01
 **/
public class ClearPlayList {
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

    public void clearPlayList(String led) {
        Gson gson = new Gson();
        ClearPlayListData data = new ClearPlayListData();
        String jsonData = gson.toJson(data);
        ClearPlayList test = new ClearPlayList();
//        String url = "http://192.168.6.104:8081/command/y10-518-00147"; //check this
        String url = BaseBean.URL + led; //check this
        String result;
        try {
            result = test.post(url, jsonData);
            System.out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
