package jtemp.bingossorder.event;

import android.os.Handler;
import android.os.Message;

/**
 * Created by ZMS on 2016/11/21.
 */

public enum AppEvent {
    EVENT_ADMIN_LOGIN_DISPLAY,
    EVENT_ADMIN_LOGIN_SUCCESS,
    EVENT_ADMIN_FOOD_MGR_DISPLAY,
    EVENT_ADMIN_FOOD_CAT_MGR_DISPLAY,
    EVENT_ADMIN_FOOD_SPEC_MGR_DISPLAY,

    //
    ;


    public Message toMessage(Handler handler, Object... args) {
        return Message.obtain(handler, this.ordinal(), args);
    }
}
