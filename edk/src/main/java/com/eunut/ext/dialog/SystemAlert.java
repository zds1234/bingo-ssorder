package com.eunut.ext.dialog;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Chaly on 15/3/6.
 */
public class SystemAlert extends AlertDialog {
    private CloseSystemDialogsReceiver mCloseSystemDialogsReceiver;
    private Window mWindow;

    public SystemAlert(Context context) {
        super(context);
        mWindow = this.getWindow();
        mWindow.setDimAmount(0f);
        mWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void show() {
        if (mCloseSystemDialogsReceiver == null) {
            IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            mCloseSystemDialogsReceiver = new CloseSystemDialogsReceiver();
            mWindow.getContext().registerReceiver(mCloseSystemDialogsReceiver, filter);
        }
        super.show();
    }

    @Override
    public void dismiss() {
        if (mCloseSystemDialogsReceiver != null) {
            mWindow.getContext().unregisterReceiver(mCloseSystemDialogsReceiver);
            mCloseSystemDialogsReceiver = null;
        }
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        dismiss();
        return super.onKeyDown(keyCode, event);
    }

    private class CloseSystemDialogsReceiver extends BroadcastReceiver {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                    SystemAlert.this.dismiss();
                }
            }
        }
    }
}