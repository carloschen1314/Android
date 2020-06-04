package com.androidproject.util;

import okhttp3.*;
import okhttp3.Response;

import java.io.IOException;

public class HttpUtils {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    public static Response post(String url, String json) throws IOException {
        MediaType mediaType=MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create("body参数",mediaType);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

}