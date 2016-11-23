package jtemp.bingossorder.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.event.AppEvent;
import jtemp.bingossorder.event.AppEventHandler;
import jtemp.bingossorder.utils.AndroidUtils;

/**
 * 首页头
 */
public class TitleFragment extends Fragment {

    private AppEventHandler handler;

    private boolean backVisible;

    private boolean actionVisible;

    private View view;

    private String actionText;

    public void setHandler(AppEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        this.view = view;
        this.setBackVisible(this.backVisible);
        this.setActionVisible(this.actionVisible);
        this.setActionText(actionText);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setActionVisible(boolean visible) {
        this.actionVisible = visible;
        if (this.view != null) {
            this.view.findViewById(R.id.title_action).setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
    }


    public void setBackVisible(boolean visible) {
        this.backVisible = visible;
        if (this.view != null) {
            this.view.findViewById(R.id.title_back).setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @OnClick({R.id.title_action, R.id.title_back, R.id.title_bingo})
    public void onClick(View v) {
        if (v == null || !v.isShown() || handler == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.title_bingo:
                AndroidUtils.hideSoftKeyboard(getActivity());
                handler.sendMessage(AppEvent.EVENT_TITLE_BINGO_CLICKED.toMessage(handler));
                break;
            case R.id.title_back:
                handler.sendMessage(AppEvent.EVENT_TITLE_BACK_CLICKED.toMessage(handler));
                break;
            case R.id.title_action:
                handler.sendMessage(AppEvent.EVENT_TITLE_ACTION_CLICKED.toMessage(handler));
                break;
            default:
                break;
        }
    }

    public void setActionText(String text) {
        this.actionText = text;
        if (this.view != null) {
            Button button = (Button) this.view.findViewById(R.id.title_action);
            button.setText(text);
        }
    }
}
