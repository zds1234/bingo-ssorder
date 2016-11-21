package jtemp.bingossorder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.event.AppEventHandler;

/**
 * 广告
 */
public class AdFragment extends Fragment {

    private AppEventHandler handler;

    public void setHandler(AppEventHandler handler) {
        this.handler = handler;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ad, container, false);
    }
}
