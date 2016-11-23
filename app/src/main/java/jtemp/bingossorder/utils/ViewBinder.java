package jtemp.bingossorder.utils;

import android.app.Dialog;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by ZMS on 2016/10/31.
 */

public final class ViewBinder {

    public static void bind(View view, Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return;
        }
        for (Field field : fields) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView == null) {
                continue;
            }
            View v = view.findViewById(bindView.value());
            field.setAccessible(true);
            try {
                field.set(obj, v);
            } catch (Exception e) {
            }
        }
    }

    public static void bind(Dialog view, Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return;
        }
        for (Field field : fields) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView == null) {
                continue;
            }
            View v = view.findViewById(bindView.value());
            field.setAccessible(true);
            try {
                field.set(obj, v);
            } catch (Exception e) {
            }
        }
    }
}
