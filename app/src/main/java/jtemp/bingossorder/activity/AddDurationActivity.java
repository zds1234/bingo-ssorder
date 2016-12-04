package jtemp.bingossorder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import jtemp.bingossorder.base.BaseActivity;

/**
 * Created by hasee on 2016/12/4.
 */

public class AddDurationActivity extends BaseActivity {
    private TextView tv_cancel,tv_confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setParams();
    }
    private void setParams() {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth()*0.8);
        p.height= (int) (d.getHeight()*0.4);
        getWindow().setAttributes(p);
    }

    @Override
    public int getContentLayoutResources() {
        return R.layout.activity_add_duration;
    }

    @Override
    public void initUtils() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
