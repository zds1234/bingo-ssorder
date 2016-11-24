package com.eunut.widget;

import android.app.Instrumentation;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eunut.FinalKit;
import com.eunut.sdk.R;

import org.apache.commons.lang3.StringUtils;

/**
 * 顶部工具条通用类
 *
 * @author Chaly
 */
public class TopBar extends LinearLayout implements OnClickListener {
    private TextView bar_title;
    private TextView bar_back;
    private LinearLayout bar_action_group;
    private FrameLayout bar_title_view;
    private TypedArray actions;
    private ActionClickListener mActionClickListener;
    private float textSize = 14;
    private int textColor = Color.WHITE;

    public TopBar(Context context) {
        super(context);
        init(null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.eunut_top_bar, this);
        bar_title = (TextView) findViewById(R.id.bar_title);
        bar_back = (TextView) findViewById(R.id.bar_back);
        bar_action_group = (LinearLayout) findViewById(R.id.bar_action_group);
        bar_title_view = (FrameLayout) findViewById(R.id.bar_title_view);
        bar_back.setOnClickListener(this);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TopBar, R.attr.TopBarStyle, R.style.TopBarDefaultStyle);
            String title = a.getString(R.styleable.TopBar_android_title);
            textColor = a.getColor(R.styleable.TopBar_android_textColor, textColor);
            this.setBackgroundResource(a.getResourceId(R.styleable.TopBar_android_background, R.color.light_blue));
            // 设置标题名称
            if (title != null && title.trim().length() > 0) {
                bar_title.setText(title);
                bar_title.setTypeface(Typeface.defaultFromStyle(a.getInt(R.styleable.TopBar_android_textStyle, 0)));
            }
            //设置标题
            int titleViewId = a.getResourceId(R.styleable.TopBar_titleView, 0);
            if (titleViewId > 0) {
                bar_title.setVisibility(GONE);
                bar_title_view.removeAllViews();
                View view = LayoutInflater.from(getContext()).inflate(titleViewId, null);
                bar_title_view.addView(view);
            }
            //控制返回按钮的文字
            String backTitle = a.getString(R.styleable.TopBar_android_text);
            if (!isInEditMode()) {
                textSize = a.getDimension(R.styleable.TopBar_android_textSize, FinalKit.dip2px(textSize));
                bar_back.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            bar_back.setTextColor(textColor);
            bar_title.setTextColor(textColor);
            if (StringUtils.isNotBlank(backTitle)) {
                bar_back.setText(backTitle);
            }
            //控制返回按钮的drawable
            Drawable left, right;
            left = a.getDrawable(R.styleable.TopBar_android_drawableLeft);
            right = a.getDrawable(R.styleable.TopBar_android_drawableRight);
            bar_back.setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
            if (left == null && right == null && StringUtils.isBlank(bar_back.getText())) {
                bar_back.setVisibility(GONE);
            }
            //初始化右侧的操作按钮
            int actionResourceId = a.getResourceId(R.styleable.TopBar_action, R.array.action_null);
            if (!isInEditMode()) {
                actions = getResources().obtainTypedArray(actionResourceId);
            }
            initActionBars();
            a.recycle();
        }
    }

    private void initActionBars() {
        if (actions != null) {
            bar_action_group.removeAllViews();
            for (int i = 0; i < actions.length(); i++) {
                String string = actions.getString(i);
                View view = null;
                if (string.contains("drawable")) {//证明这个资源是图片
                    view = createImageView(actions.getResourceId(i, -1));
                } else {
                    view = createTextView(string);
                }
                view.setTag(i);
                LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 0, 0, 0);
                view.setLayoutParams(lp);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mActionClickListener != null) {
                            mActionClickListener.onActionClick(v, (Integer) v.getTag());
                        }
                    }
                });
                bar_action_group.addView(view);
            }
        }
    }

    private View createImageView(int resourceId) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(resourceId);
        return imageView;
    }

    private View createTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        return textView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bar_back) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 模拟返回键功能
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                }
            }).start();
        }
    }

    //设置标题文字
    public void setTitle(String title) {
        bar_title.setText(title);
    }

    //设置标题文字
    public void setTitle(int resId) {
        bar_title.setText(getResources().getText(resId));
    }

    //设置返回文字
    public void setText(String title) {
        bar_back.setText(title);
    }

    //设置返回文字
    public void setText(int resId) {
        bar_back.setText(getResources().getText(resId));
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        bar_back.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }


    public void setAction(int resId) {//设置操作按钮
        actions = getResources().obtainTypedArray(resId);
        initActionBars();
    }

    public void setOnActionClickListener(ActionClickListener listener) {
        mActionClickListener = listener;
    }

    public interface ActionClickListener {
        public void onActionClick(View v, int position);
    }

    public void setOnTitleClickListener(OnClickListener listener) {
        bar_title.setOnClickListener(listener);
    }

    public TextView getTitleView() {
        return bar_title;
    }

    public LinearLayout getActionView() {
        return bar_action_group;
    }

}
