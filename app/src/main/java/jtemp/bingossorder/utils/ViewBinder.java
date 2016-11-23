package jtemp.bingossorder.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by ZMS on 2016/10/31.
 */

public final class ViewBinder {

    public static void bind(Object viewContainer, Object bindTo) {
        try {
            Method method = viewContainer.getClass().getMethod("findViewById", int.class);
            Field[] fields = bindTo.getClass().getDeclaredFields();
            if (fields == null || fields.length == 0) {
                return;
            }
            for (Field field : fields) {
                BindView bindView = field.getAnnotation(BindView.class);
                if (bindView == null) {
                    continue;
                }
                Object v = method.invoke(viewContainer, bindView.value());
                field.setAccessible(true);
                try {
                    field.set(bindTo, v);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
