package jtemp.bingossorder.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.event.AppEventHandler;
import jtemp.hardware.HardwareTestActivity;

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

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(
            {
                    R.id.admin_main_button1,
                    R.id.admin_main_button2,
                    R.id.admin_main_button3,
                    R.id.admin_main_button4,
                    R.id.admin_main_button5,
                    R.id.admin_main_button6,
                    R.id.admin_main_button7,
                    R.id.admin_main_button8,
                    R.id.hardware_test
            }
    )
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
            case R.id.hardware_test:
                startHardwareTest();
                break;
            default:
                break;
        }
    }

    private void startHardwareTest() {
        Intent intent = new Intent(getActivity().getApplicationContext(), HardwareTestActivity.class);
        getActivity().startActivity(intent);
    }

    private void startFoodManager() {
    }
}
