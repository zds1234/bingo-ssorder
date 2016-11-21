package jtemp.bingossorder.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.event.AppEvent;
import jtemp.bingossorder.event.AppEventHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFoodMgrPageBarFragment extends Fragment {

    private AppEventHandler handler;

    public void setHandler(AppEventHandler handler) {
        this.handler = handler;
    }

    public AdminFoodMgrPageBarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_food_mgr_pagebar, container, false);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.tab);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tabChanged(checkedId);
            }
        });
        return view;
    }

    private void tabChanged(int id) {
        switch (id) {
            case R.id.radioButton1:
                handler.sendMessage(AppEvent.EVENT_ADMIN_FOOD_MGR_DISPLAY.toMessage(handler));
                break;
            case R.id.radioButton2:
                handler.sendMessage(AppEvent.EVENT_ADMIN_FOOD_CAT_MGR_DISPLAY.toMessage(handler));
                break;
            case R.id.radioButton3:
                handler.sendMessage(AppEvent.EVENT_ADMIN_FOOD_SPEC_MGR_DISPLAY.toMessage(handler));
                break;
            default:
                break;
        }
    }

}
