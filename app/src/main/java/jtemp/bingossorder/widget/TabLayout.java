package jtemp.bingossorder.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jtemp.bingossorder.activity.R;

/**
 * Created by hasee on 2016/12/2.
 */

public class TabLayout extends LinearLayout {
    private View mRootView;
    private ImageView mIcon;
    private TextView mTabName;
    private View mDivider;
//    tab名称
    private String name ;
//    resource id
    private int resource=-1;

//    右侧分割线
    private boolean need_divider;
    public TabLayout(Context context) {
        this(context,null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }
    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TabLayout, defStyleAttr, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {

                case R.styleable.TabLayout_title_name:
                    name=a.getString(R.styleable.TabLayout_title_name);
                    break;
                case R.styleable.TabLayout_src:
                    resource=a.getResourceId(R.styleable.TabLayout_src,-1);
                    break;

                case R.styleable.TabLayout_need_divider:
                    need_divider=a.getBoolean(R.styleable.TabLayout_need_divider, true);
                    break;
            }
        }
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        mRootView=LayoutInflater.from(context).inflate(R.layout.layout_tab_item,null);
        mIcon = (ImageView) mRootView.findViewById(R.id.iv_thumb);
        mTabName = (TextView) mRootView.findViewById(R.id.tv_content);
        mDivider =mRootView.findViewById(R.id.divider);
        if(resource!=-1){
            mIcon.setImageResource(resource);
        }
        if(!TextUtils.isEmpty(name)){
            mTabName.setText(name);
        }
        if(need_divider){
            mDivider.setVisibility(View.VISIBLE);
        }else{
            mDivider.setVisibility(GONE);
        }
        addView(mRootView);
    }
}
