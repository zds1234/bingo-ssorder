package jtemp.bingossorder.event;

import android.os.Handler;
import android.os.Message;

/**
 * Created by ZMS on 2016/11/21.
 */

public class AppEventHandler extends Handler {

    private AppEventListener listener;

    public AppEventHandler(AppEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        listener.handleEvent(msg);
    }
}
