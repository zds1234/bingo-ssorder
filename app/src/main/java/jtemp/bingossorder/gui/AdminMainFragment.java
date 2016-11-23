package jtemp.bingossorder.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jtemp.bingossorder.activity.AdminFoodManagerActivity;
import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.event.AppEventHandler;

/**
 * 管理首页
 */
public class AdminMainFragment extends Fragment implements View.OnClickListener {

    private AppEventHandler handler;

    public void setHandler(AppEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_main, container, false);

        view.findViewById(R.id.admin_main_button1).setOnClickListener(this);
        view.findViewById(R.id.admin_main_button2).setOnClickListener(this);
        view.findViewById(R.id.admin_main_button3).setOnClickListener(this);
        view.findViewById(R.id.admin_main_button4).setOnClickListener(this);
        view.findViewById(R.id.admin_main_button5).setOnClickListener(this);
        view.findViewById(R.id.admin_main_button6).setOnClickListener(this);
        view.findViewById(R.id.admin_main_button7).setOnClickListener(this);
        view.findViewById(R.id.admin_main_button8).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admin_main_button1:
                break;
            case R.id.admin_main_button2:
                break;
            case R.id.admin_main_button3:
                break;
            case R.id.admin_main_button4:
                startFoodManager();
                break;
            case R.id.admin_main_button5:
                break;
            case R.id.admin_main_button6:
                break;
            case R.id.admin_main_button7:
                break;
            case R.id.admin_main_button8:
                break;
            default:
                break;
        }
    }

    private void startFoodManager() {
        Intent intent = new Intent(getActivity().getApplicationContext(), AdminFoodManagerActivity.class);
        getActivity().startActivity(intent);
    }
}
