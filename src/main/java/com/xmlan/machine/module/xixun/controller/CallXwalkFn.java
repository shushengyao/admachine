package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.module.xixun.util.CallXwalkFnData;
import java.io.IOException;

/**
 * 设置播放背景
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-07-25 11:01
 **/
public class CallXwalkFn {
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

    public void callXwalkFn(String call,String led) {
        Gson gson = new Gson();
        CallXwalkFnData data = new CallXwalkFnData();
        data.getArg().url=call;
        String jsonData = gson.toJson(data);
//        System.out.print(jsonData);
        CallXwalkFn test = new CallXwalkFn();
        String url = BaseBean.URL +led; //check this
        String result;
        try {
            result = test.post(url, jsonData);
            System.out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
