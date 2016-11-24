package com.eunut.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;
import com.eunut.sdk.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class T {
    private static Toast toast;
    private static TextView textView;
    private static Method showMethod, hideMethod;
    private static Field field;
    private static Object obj;

    private static void fillView(Context context) {
        textView = (TextView) LayoutInflater.from(context).inflate(R.layout.eunut_toast, null);
        toast.setView(textView);
    }

    private static void setText(CharSequence message) {
        textView.setText(message);
    }

    private static void setText(int message) {
        textView.setText(message);
    }

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, CharSequence message) {
        if (null == toast) {
            toast = new Toast(context);
            fillView(context);
            toast.setDuration(Toast.LENGTH_LONG);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        }
        setText(message);
        show();
    }

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, int message) {
        if (null == toast) {
            toast = new Toast(context);
            fillView(context);
            toast.setDuration(Toast.LENGTH_SHORT);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        }
        setText(message);
        show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, CharSequence message) {
        if (null == toast) {
            toast = new Toast(context);
            fillView(context);
            toast.setDuration(Toast.LENGTH_LONG);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        }
        setText(message);
        show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, int message) {
        if (null == toast) {
            toast = new Toast(context);
            fillView(context);
            toast.setDuration(Toast.LENGTH_LONG);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        }
        setText(message);
        show();
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (null == toast) {
            toast = new Toast(context);
            fillView(context);
            toast.setDuration(duration);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        }
        setText(message);
        show();
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(Context context, int message, int duration) {
        if (null == toast) {
            toast = new Toast(context);
            fillView(context);
            toast.setDuration(duration);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        }
        setText(message);
        show();
    }

    /**
     * Hide the toast, if any.
     */
    public static void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }

    private static void show() {
        String message = textView.getText().toString().trim();
        if (message == null || message.length() == 0) {
            return;
        }
        toast.show();
    }
}
