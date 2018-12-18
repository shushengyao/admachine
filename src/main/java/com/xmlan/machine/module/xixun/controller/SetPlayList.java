package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.module.xixun.util.SetPlayListData;
import okhttp3.*;

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

    public void setPlayList(String led,String fileName,int width,int height) {
        Gson gson = new Gson();
        SetPlayListData data = new SetPlayListData();
        data.list[0] = "/data/data/com.xixun.xy.conn/files/local/abc/"+fileName;
        data.width = width;
        data.height = height;
        String jsonData = gson.toJson(data);
        SetPlayList test = new SetPlayList();
//        String url = "http://192.168.0.218:8081/command/y10-518-00147"; //check this
        String url = BaseBean.URL+led; //check this
        String result;
        try {
            result = test.post(url, jsonData);
            System.out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
