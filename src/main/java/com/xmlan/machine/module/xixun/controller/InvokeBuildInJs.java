package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.module.xixun.util.ClearData;
import com.xmlan.machine.module.xixun.util.InvokeBuildInJsData;

import java.io.IOException;

/**
 * 滚动字幕
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-07-25 10:45
 **/
public class InvokeBuildInJs {
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

    public void invokeBuildInJs(InvokeBuildInJsData inJsData) {
        Gson gson = new Gson();
        InvokeBuildInJsData data = new InvokeBuildInJsData();
        String html = inJsData.html.substring(0,inJsData.html.indexOf("#"));
        String type = inJsData.type.substring(0,inJsData.type.indexOf("s")+1);
        String led = inJsData.type.substring(inJsData.type.indexOf("s")+1);
        String color ="#"+inJsData.html.substring(inJsData.html.indexOf("#")+1);
        data.html = "<head><style type=\"text/css\">body{background-color:"+color+";}</style></head><i>"+html+"</i>";
        data.align=inJsData.align;
        Number number1 =inJsData.interval.intValue();
        Number number2 =inJsData.num.intValue();
        Number number3 =inJsData.step.intValue();
        data.direction =inJsData.direction;
        data.type =type;
        data.interval = number1;
        data.num = number2;
        data.step=number3;
        String jsonData = gson.toJson(data);
        InvokeBuildInJs test = new InvokeBuildInJs();
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
