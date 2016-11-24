package com.eunut.util;

import android.util.Log;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 自定义异常捕获类
 */
public class CrashUtil implements UncaughtExceptionHandler {
	public static final String TAG = "CrashHandler";
	/** 单例模式 **/
	private CrashUtil() {
	}
	/** 懒汉式 **/
	private static class CrashHolder {
		static final CrashUtil crashHandler = new CrashUtil();
	}
	public static CrashUtil getInstance() {
		return CrashHolder.crashHandler;
	}
	public void init() {
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		String threadName = thread.getName();
		if ("main".equals(threadName)) {
			Log.d(TAG, "在主线程的崩溃！");
		} else {
			// 这里我们根据thread name来进行区别对待：可以将异常信息写入文件供以后分析
			Log.d(TAG, "在子线程中崩溃!");
		}
		ex.printStackTrace();
		//重启
//		FinalKits.restartApplication(FinalKits.mContext);
		//杀死当前进程
		android.os.Process.killProcess(android.os.Process.myPid()); // 杀死该进程
		System.exit(0); // 退出
	}
}