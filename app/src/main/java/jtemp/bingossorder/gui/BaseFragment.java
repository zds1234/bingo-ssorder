package jtemp.bingossorder.gui;

import android.support.v4.app.Fragment;

/**
 * Created by hasee on 2016/12/3.
 */

public class BaseFragment extends Fragment {
    public final <T> T findSpecificViewById(int resId) {
        return (T) getView().findViewById(resId);
    }
}
