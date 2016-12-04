package jtemp.bingossorder.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by hasee on 2016/12/1.
 */

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getContentLayoutResources());
        initUtils();
        initViews();
        initListeners();
        initData();
    }


    public abstract int getContentLayoutResources();

    public abstract void initUtils();

    public abstract void initViews();

    public abstract void initListeners();

    public abstract void initData();

    public final <T> T findSpecificViewById(int resId) {
        return (T) this.findViewById(resId);
    }
}
