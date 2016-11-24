package com.eunut.http.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chaly on 16/9/29.
 */

public class HttpHeaderInterceptor implements Interceptor {

    private Map<String, String> headers = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            builder.header(entry.getKey(), entry.getValue());
        }
        return chain.proceed(builder.build());
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}