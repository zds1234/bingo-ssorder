package jtemp.bingossorder.utils;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by ZMS on 2016/11/21.
 */

public final class AndroidUtils {

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 点击返回按钮
     */
    public static void pressBack() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Instrumentation inst = new Instrumentation();
                inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            }
        }).start();
    }
}
