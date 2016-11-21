package jtemp.bingossorder.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.event.AppEventHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainContentFragment extends Fragment {

    private AppEventHandler handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_content, container, false);
    }

    public void setHandler(AppEventHandler handler) {
        this.handler = handler;
    }
}
