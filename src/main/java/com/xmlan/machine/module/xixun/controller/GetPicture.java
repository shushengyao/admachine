package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.module.xixun.util.GetPictureData;
//import scala.util.parsing.json.JSONObject;
import net.sf.json.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.net.URI;

/**
 * @program: admachine
 * @description: con
 * @author: YSS
 * @create: 2018-07-30 14:03
 **/
public class GetPicture {
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

    public String getStr(String led) {
        Gson gson = new Gson();
        GetPictureData data = new GetPictureData();
        String jsonData = gson.toJson(data);
        GetPicture test = new GetPicture();
//        String url = "http://192.168.6.104:8081/command/y10-518-00147"; //check this
        String url = BaseBean.URL+led; //check this
        String result;
        try {
            result = test.post(url, jsonData);
            JSONObject jsStr = JSONObject.fromObject(result);
            String re =jsStr.get("result").toString();
            re.replaceAll("\r|\n*","");
//            System.out.print(re);
            return re;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
