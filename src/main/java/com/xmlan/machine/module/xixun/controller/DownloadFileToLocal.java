package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.module.xixun.util.ClearPlayListData;
import com.xmlan.machine.module.xixun.util.DownloadFileToLocalData;
import okhttp3.*;

import java.io.IOException;

/**
 * 媒体文件上传到led控制卡内存
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-10-20 09:55
 **/
public class DownloadFileToLocal {
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

    public  void DownloadFileToLocal(String call,String led_code) {
        Gson gson = new Gson();
        DownloadFileToLocalData data = new DownloadFileToLocalData();
        data.url = data.url + call;
        data.path = data.path + call;
        String jsonData = gson.toJson(data);
        DownloadFileToLocal test = new DownloadFileToLocal();
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
