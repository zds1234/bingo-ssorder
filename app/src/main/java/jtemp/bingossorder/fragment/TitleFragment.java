package jtemp.bingossorder.fragment;


import android.app.Instrumentation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.event.AppEvent;
import jtemp.bingossorder.event.AppEventHandler;

/**
 * 首页头
 */
public class TitleFragment extends Fragment {

    private AppEventHandler handler;

    private boolean backVisible;

    private View view;

    public void setHandler(AppEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdmin();
            }
        });
        this.view = view;
        this.setBackVisible(this.backVisible);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        return view;
    }

    private void back() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Instrumentation inst = new Instrumentation();
                inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            }
        });
    }

    private void showAdmin() {
        if (!backVisible) {
            handler.sendMessage(AppEvent.EVENT_ADMIN_LOGIN_DISPLAY.toMessage(handler));
        }
    }

    public void setBackVisible(boolean visible) {
        this.backVisible = visible;
        if (this.view != null) {
            this.view.findViewById(R.id.back).setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
