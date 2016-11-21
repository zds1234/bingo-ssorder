package jtemp.bingossorder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.admin.AdminManager;
import jtemp.bingossorder.event.AppEvent;
import jtemp.bingossorder.event.AppEventHandler;
import jtemp.bingossorder.utils.AndroidUtils;

/**
 * 登录
 */
public class AdminLoginFragment extends Fragment {

    private AppEventHandler handler;

    private View view;

    public void setHandler(AppEventHandler handler) {
        this.handler = handler;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_login, container, false);
        this.view = view;
        EditText editText = (EditText) view.findViewById(R.id.password);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    adminLogin();
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    private void adminLogin() {
        EditText password = (EditText) view.findViewById(R.id.password);
        if (AdminManager.isAdminPasswordValidate(password.getText().toString().trim())) {
            handler.sendMessage(AppEvent.EVENT_ADMIN_LOGIN_SUCCESS.toMessage(handler));
            AndroidUtils.hideSoftKeyboard(getActivity());
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}
