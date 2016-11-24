package com.eunut.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class BaseApplication extends Application {

    private Activity activity;

    private ActivityLifecycleCallbacks lifecycleCallback = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            BaseApplication.this.activity = activity;
        }

        @Override
        public void onActivityStarted(Activity activity) {
            BaseApplication.this.activity = activity;
        }

        @Override
        public void onActivityResumed(Activity activity) {
            BaseApplication.this.activity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    protected static class SingletonHolder {
        public static BaseApplication INSTANCE;
    }

    public BaseApplication() {
        registerActivityLifecycleCallbacks(lifecycleCallback);
        SingletonHolder.INSTANCE = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //程序一启动，就将未捕获异常初始化
        //CrashUtil.getInstance().init();
        SingletonHolder.INSTANCE = this;
    }

    public void onLowMemory() {
        System.gc();
        System.runFinalization();
        System.gc();
        super.onLowMemory();
    }

    public Activity getCurrentActivity() {
        return activity;
    }

    public static BaseApplication get() {
        return SingletonHolder.INSTANCE;
    }
}