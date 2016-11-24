package com.eunut.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.eunut.sdk.R;

import java.util.ArrayList;
import java.util.List;

public class PopupUtil {

    private static List<PopupWindow> listCache = new ArrayList<PopupWindow>();
    private static PopupUtil instance;
    private Activity activity;
    private int fillType = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int widthFillType = fillType;
    private int heightFillType = fillType;
    private int animationStyle = R.style.Animations_PopUpMenu_Slide;
    private int gravity = Gravity.BOTTOM;
    private boolean dimBehind = true;
    private View anchor;
    private View contentView;
    private int xPosition = 0, yPosition = 0;
    private OnCloseListener onCloseListener;

    public static PopupUtil make(Activity activity) {
        instance = new PopupUtil();
        instance.setActivity(activity);
        return instance;
    }

    /**
     * 使用返回键模拟关闭弹出窗口
     */
    public static void close() {
        if (listCache.size() > 0) {
//			new Thread() {
//				public void run() {
//					try {
//						Instrumentation inst = new Instrumentation();
//						inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//					} catch (Exception e) {
//						Log.e("Exception when sendKeyDownUpSync", e.toString());
//					}
//				}
//			}.start();
            listCache.remove(0).dismiss();
        }
    }

    /**
     * 使用返回键模拟关闭弹出窗口
     */
    public static void close(final OnCloseListener listener) {
        if (listCache.size() > 0) {
            listCache.get(0).setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    listCache.remove(0);
                    if (listener != null) listener.onClose();
                }
            });
            close();
        }
    }

    public static void closeAll() {
        while (listCache.size() > 0) {
            close();
        }
        if (instance != null && instance.onCloseListener != null) {
            instance.onCloseListener.onClose();
        }
    }

    public PopupUtil setContentView(int resourceId) {
        contentView = LayoutInflater.from(activity).inflate(resourceId, null);
        return instance;
    }

    public PopupUtil setDimBehind(boolean dimBehind) {
        this.dimBehind = dimBehind;
        return instance;
    }

    public PopupUtil setContentView(View content) {
        contentView = content;
        return instance;
    }

    public PopupUtil setAnchor(View anchor) {
        this.anchor = anchor;
        return instance;
    }

    public PopupUtil setPosition(Integer x, Integer y) {
        this.xPosition = x;
        this.yPosition = y;
        return instance;
    }

    public PopupUtil setFillType(int fillType) {
        this.fillType = fillType;
        widthFillType = fillType;
        heightFillType = fillType;
        return instance;
    }

    public PopupUtil setActivity(Activity activity) {
        this.activity = activity;
        return instance;
    }

    public PopupUtil setGravity(int gravity) {
        this.gravity = gravity;
        return instance;
    }

    public PopupUtil setWidthFillType(int widthFillType) {
        this.widthFillType = widthFillType;
        return instance;
    }

    public PopupUtil setHeightFillType(int heightFillType) {
        this.heightFillType = heightFillType;
        return instance;
    }

    public PopupUtil setAnimationStyle(int animationStyle) {
        this.animationStyle = animationStyle;
        return instance;
    }

    public PopupUtil setOnCloseListener(OnCloseListener listener) {
        this.onCloseListener = listener;
        return instance;
    }

    public View show() {
        try {
            //关闭键盘
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
        PopupWindow window = new PopupWindow(contentView);
        window.setWidth(widthFillType);
        window.setHeight(heightFillType);
        window.setAnimationStyle(animationStyle);
        // 与window.setFocusable(true)配合使用监听返回键的监听
//        window.setOutsideTouchable(false);
        ColorDrawable bg = new ColorDrawable(Color.TRANSPARENT);
        window.setBackgroundDrawable(bg);
        window.setFocusable(true);
        //防止全屏幕输入时软键盘覆盖
        window.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Gravity.TOP == gravity || (Gravity.TOP | Gravity.RIGHT) == gravity) {
            yPosition += getStatusBarHeight();
        }
        if (anchor != null) {
            window.showAsDropDown(anchor, xPosition, yPosition, gravity);
        } else {
            if (heightFillType == ViewGroup.LayoutParams.FILL_PARENT) {
                window.setHeight(getScreenMetrics(activity).y - getStatusBarHeight());
            }
            window.showAtLocation(activity.getWindow().getDecorView(), gravity, xPosition, yPosition);
        }
        //设置背景半透明
        if (dimBehind) {
            View container = (View) window.getContentView().getRootView();
            WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.5f;
            wm.updateViewLayout(container, p);
        }
        //设置关闭的监听事件
        window.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onCloseListener != null) onCloseListener.onClose();
            }
        });
        listCache.add(0, window);
        return window.getContentView();
    }

    private int getStatusBarHeight() {
        int result = 0;
        Resources res = activity.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
    }

    public interface OnCloseListener {
        public void onClose();
    }
}
