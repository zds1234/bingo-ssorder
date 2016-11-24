package com.eunut.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.eunut.sdk.R;

import com.eunut.FinalKit;
import com.eunut.util.PopupUtil;

@SuppressWarnings("deprecation")
public class ActionSheet extends LinearLayout {

    public static enum FlagType {
        OK, MORE, CANCEL, ITEM
    }

    private String[] adapter;
    private View customView = null;// 自定义的子试图
    private OnSheetItemClickListener onSheetItemClickListener;
    private boolean canClose = true;
    //取消按钮背景&&颜色
    int cancelButtonBackground;
    int cancelButtonTextColor;
    //成组的操作按钮的上中下按钮背景
    int otherButtonTopBackground;
    int otherButtonMiddleBackground;
    int otherButtonBottomBackground;
    //独立的按钮背景
    int otherButtonSingleBackground;
    int otherButtonTextColor;
    //间距
    int otherButtonSpacing;
    int cancelButtonMarginTop;
    //字体大小
    int actionSheetTextSize;

    public ActionSheet(Context context) {
        super(context);
        init();
    }

    public ActionSheet(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ActionSheet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化组件布局以及标题栏
     */
    private void init() {
        TypedArray a = getContext()
            .obtainStyledAttributes(null, R.styleable.ActionSheet, R.attr.actionSheetStyle, R.style.ActionSheetStyleIOS7);
        /** 属性的获取 */
        //取消按钮背景&&颜色
        cancelButtonBackground = a
            .getResourceId(R.styleable.ActionSheet_cancelButtonBackground, R.drawable.eunut_as_ios7_cancel);
        cancelButtonTextColor = a.getColor(R.styleable.ActionSheet_cancelButtonTextColor, Color.parseColor("#1E82FF"));
        //成组的操作按钮的上中下按钮背景
        otherButtonTopBackground = a
            .getResourceId(R.styleable.ActionSheet_otherButtonTopBackground, R.drawable.eunut_as_ios7_other_top);
        otherButtonMiddleBackground = a
            .getResourceId(R.styleable.ActionSheet_otherButtonMiddleBackground, R.drawable.eunut_as_ios7_other_middle);
        otherButtonBottomBackground = a
            .getResourceId(R.styleable.ActionSheet_otherButtonBottomBackground, R.drawable.eunut_as_ios7_other_bottom);
        //独立的按钮背景
        otherButtonSingleBackground = a
            .getResourceId(R.styleable.ActionSheet_otherButtonSingleBackground, R.drawable.eunut_as_ios7_other_single);
        otherButtonTextColor = a.getColor(R.styleable.ActionSheet_otherButtonTextColor, Color.parseColor("#1E82FF"));
        //间距
        otherButtonSpacing = a.getDimensionPixelSize(R.styleable.ActionSheet_otherButtonSpacing, 0);
        cancelButtonMarginTop = a
            .getDimensionPixelSize(R.styleable.ActionSheet_cancelButtonMarginTop, FinalKit.dip2px(10));
        //字体大小
        actionSheetTextSize = a.getDimensionPixelSize(R.styleable.ActionSheet_actionSheetTextSize, FinalKit.dip2px(16));
        //初始化布局
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
        this.setOrientation(LinearLayout.VERTICAL);
        //背景
        this.setBackgroundDrawable(a.getDrawable(R.styleable.ActionSheet_actionSheetBackground));
        //内边距
        int actionSheetPadding = a
            .getDimensionPixelSize(R.styleable.ActionSheet_actionSheetPadding, FinalKit.dip2px(20));
        this.setPadding(actionSheetPadding, actionSheetPadding, actionSheetPadding, actionSheetPadding);
        a.recycle();
    }

    /**
     * 设置数据源适配器
     */
    public void setAdapter(String[] pAdapter) {
        removeAllViews();
        this.adapter = pAdapter;
        int length = adapter.length;
        // 添加普通按钮，最多是3项
        for (int i = 0; i < (length > 3 ? 2 : length); i++) {
            int bg = otherButtonSingleBackground;
            if (length == 1) {
                bg = otherButtonSingleBackground;
            } else if (i == 0) {
                bg = otherButtonTopBackground;
            } else if (i == length - 1) {
                bg = otherButtonBottomBackground;
            } else {
                bg = otherButtonMiddleBackground;
            }
            addView(createButton(i, adapter[i], bg, otherButtonTextColor, FlagType.ITEM));
        }
        // 如果按钮多余3项则添加一个更多按钮
        if (length > 3) {
            addView(createButton(2, getContext().getString(R.string.more),
                otherButtonBottomBackground, otherButtonTextColor, FlagType.MORE));
        }
        // 添加取消按钮
        Button cancel = createButton(length > 3 ? 3 : length,
            getContext().getString(android.R.string.cancel),
            cancelButtonBackground, cancelButtonTextColor,
            FlagType.CANCEL);
        LayoutParams layoutParam = (LayoutParams) cancel.getLayoutParams();
        layoutParam.setMargins(0, cancelButtonMarginTop, 0, 0);
        addView(cancel);
    }

    public void setView(View child) {
        customView = child;
        removeAllViews();
        // 添加自定义的子试图
        addView(child);
        // 添加分组的分割线
        View view = new View(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, 15);
        view.setLayoutParams(params);
        addView(view);
        // 添加确认按钮
        Button ok = createButton(0, "确定", otherButtonTopBackground, otherButtonTextColor, FlagType.OK);
        LayoutParams layoutParam = (LayoutParams) ok.getLayoutParams();
        layoutParam.setMargins(0, cancelButtonMarginTop, 0, 0);
        addView(ok);
        // 添加取消按钮
        addView(createButton(1, "取消", otherButtonBottomBackground, cancelButtonTextColor, FlagType.CANCEL));
    }

    public View getCustomView() {
        return customView;
    }

    /**
     * 生产一个列表操作项
     * @param text 按钮上的文字
     * @param resId 按钮的背景资源ID
     * @param color 按钮上的文字颜色
     * @param tag 按钮上的标识 现在支持的功能有 OK CANCEL MORE
     */
    private Button createButton(final int position, String text, int resId, int color, Object tag) {
        final Button button = new Button(getContext());
        button.setText(text);
        button.setTextColor(color);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, actionSheetTextSize);
        button.setBackgroundResource(resId);
        button.setTag(tag);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        params.bottomMargin = otherButtonSpacing;
        button.setLayoutParams(params);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onSheetItemClickListener != null) {
                    FlagType flag = (FlagType) v.getTag();
                    switch (flag) {
                        case OK:// 点击的是确认按钮
                            onSheetItemClickListener.onOkItemClick(ActionSheet.this);
                            break;
                        case CANCEL:// 点击的是取消按钮
                            onSheetItemClickListener.onCancelItemClick(ActionSheet.this);
                            break;
                        case MORE:// 点击的是更多按钮
                            onSheetItemClickListener.onMoreItemClick(ActionSheet.this);
                            break;
                        default:
                            onSheetItemClickListener.onItemClick(button, position);
                            break;
                    }
                }
                if (isCanClose()) {
                    PopupUtil.close();
                }
                setCanClose(true);
            }
        });
        return button;
    }

    public boolean isCanClose() {
        return canClose;
    }

    public void setCanClose(boolean canClose) {
        this.canClose = canClose;
    }

    public OnSheetItemClickListener getOnSheetItemClickListener() {
        return onSheetItemClickListener;
    }

    public void setOnSheetItemClickListener(OnSheetItemClickListener onSheetItemClickListener) {
        this.onSheetItemClickListener = onSheetItemClickListener;
    }

    /**
     * 显示弹出面板
     */
    public void show() {
        PopupUtil.make((Activity) getContext()).setWidthFillType(ViewGroup.LayoutParams.FILL_PARENT)
            .setContentView(this).show();
    }

    public abstract static class OnSheetItemClickListener {
        public abstract void onItemClick(View v, int position);

        public void onMoreItemClick(View v) {}

        public void onOkItemClick(View v) {}

        public void onCancelItemClick(View v) {}
    }
}
