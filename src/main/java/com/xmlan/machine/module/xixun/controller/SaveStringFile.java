package com.xmlan.machine.module.xixun.controller;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import com.xmlan.machine.module.xixun.util.SaveStringFileData;

import java.io.IOException;

/**
 * 上传文件到led板卡的sd内存卡上
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-07-26 13:36
 **/
public class SaveStringFile{
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
            String[] base =args;
            Gson gson = new Gson();
            SaveStringFileData data = new SaveStringFileData();
            data.content= base[1];
            data.fileName = base[0];
            String jsonData = gson.toJson(data);
            SaveStringFile test = new SaveStringFile();
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
