package com.eunut.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 使所有的片段都要重新实现setMenuVisibility方法否则菜单切换的时候只会显示最后一个片段
 *
 * @Project: Giant
 * @Title: BaseFragment.java
 * @Package: com.eunut.base.BaseFragment
 * @Author: Chaly
 * @Email： i@eunut.com
 * @Date: 2014年10月12日上午5:11:58
 * @Version: V1.0
 */
public class BaseFragment extends Fragment {
    boolean loaded = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.getView() != null)
            this.getView().setVisibility(isVisibleToUser ? View.VISIBLE : View.GONE);
        if (this.isVisible() && isVisibleToUser && !loaded) {
            loaded = true;
            onFirstEnter();
        }
    }

    public void onFirstEnter() {

    }
}
