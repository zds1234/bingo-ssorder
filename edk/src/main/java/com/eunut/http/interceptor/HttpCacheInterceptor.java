package com.eunut.http.interceptor;

import java.io.IOException;

import com.eunut.FinalKit;
import com.eunut.util.NetworkUtil;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chaly on 16/9/29.
 */

public class HttpCacheInterceptor implements Interceptor {

    //set cahe times is 3 days
    private static final int maxStale = 60 * 60 * 24 * 3;
    // read from cache for 60 s
    private static final int maxOnlineStale = 60;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtil.isNetworkAvailable(FinalKit.getContext())) {
            Response response = chain.proceed(request);
            return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, " + String.format("max-stale=%d", maxStale))
                .build();
        } else {
//                ((Activity) FinalKit.getContext()).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(FinalKit.getContext(), "当前无网络! 为你智能加载缓存", Toast.LENGTH_SHORT).show();
//                    }
//                });
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .url(chain.request().url())
                .build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, only-if-cached, " + String.format("max-stale=%d", maxOnlineStale))
                .build();
        }
    }
}