package com.eunut;

import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.eunut.base.BaseApplication;
import com.eunut.ext.dialog.SystemAlert;
import com.eunut.http.cookie.CookieManger;
import com.eunut.http.interceptor.HttpCacheInterceptor;
import com.eunut.http.interceptor.HttpHeaderInterceptor;
import com.eunut.util.GsonUtil;
import com.eunut.util.T;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Chaly on 16/9/24.
 */
public class FinalHttp {

    private Callback callback;
    private Dialog dialog;

    //服务器地址
    private static String BASE_URL = "";
    private static Retrofit retrofit;
    private static HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
    private static HttpHeaderInterceptor headerInterceptor = new HttpHeaderInterceptor();
    private static HttpCacheInterceptor cacheInterceptor = new HttpCacheInterceptor();

    private static class Singleton {
        private static FinalHttp INSTANCE = new FinalHttp();
    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            Cache cache = null;
            File cacheDirectory = null;
            if (cacheDirectory == null)
                cacheDirectory = new File(FinalKit.getContext().getCacheDir(), "http_cache");
            try {
                if (cache == null) cache = new Cache(cacheDirectory, 10 * 1024 * 1024);
            } catch (Exception e) {
                Log.e("OKHttp", "Could not create http cache", e);
            }
            OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(logInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(cacheInterceptor)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .cookieJar(new CookieManger(FinalKit.getContext()))
                .cache(cache)
                .build();
            retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.get()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        }
        return retrofit;
    }

    public void closeDialog() {
        if (callback != null) {
            callback.unsubscribe();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showDialog(boolean show, String... msg) {
        if (!show) return;
        dialog = new SystemAlert(BaseApplication.get().getCurrentActivity());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        dialog.show();
        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        window.setContentView(com.eunut.sdk.R.layout.eunut_toast_loading);
        window.findViewById(com.eunut.sdk.R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        TextView title = (TextView) window.findViewById(com.eunut.sdk.R.id.message_textView);
        if (msg != null && msg.length > 0) title.setText(msg[0]);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                closeDialog();
            }
        });
    }

    public static <R> Observable.Transformer progress(final boolean show, final String... msg) {
        return new Observable.Transformer<R, R>() {
            @Override
            public Observable<R> call(Observable<R> observable) {
                return ((Observable) observable)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Singleton.INSTANCE.showDialog(show, msg);
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//指定loading在主线程
                    .observeOn(Schedulers.io())//指定平铺在IO线程
                    .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static FinalHttp setDebug(HttpLoggingInterceptor.Level level) {
        Singleton.INSTANCE.logInterceptor.setLevel(level);
        return Singleton.INSTANCE;
    }

    public static FinalHttp addHeader(String key, String value) {
        Singleton.INSTANCE.headerInterceptor.getHeaders().put(key, value);
        return Singleton.INSTANCE;
    }

    public static FinalHttp setBaseUrl(String baseUrl) {
        Singleton.INSTANCE.BASE_URL = baseUrl;
        return Singleton.INSTANCE;
    }

    public static FinalHttp get() {
        return Singleton.INSTANCE;
    }

    public static <R> R with(Class<R> clazz) {
        return Singleton.INSTANCE.getRetrofit().create(clazz);
    }

    public static abstract class Callback<R> extends Subscriber<R> {

        public Callback() {
            Singleton.INSTANCE.callback = this;
        }

        @Override
        public void onCompleted() {
            onFinish();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            T.showLong(BaseApplication.get().getBaseContext(), e.getMessage());
            onFinish();
        }

        @Override
        public void onNext(R result) {
            onSuccess(result);
            Singleton.INSTANCE.closeDialog();
            onFinish();
        }

        public void onFinish() {
            Singleton.INSTANCE.closeDialog();
        }

        public abstract void onSuccess(R r);
    }

}