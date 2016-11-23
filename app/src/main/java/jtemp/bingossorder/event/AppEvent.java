package jtemp.bingossorder.event;

import android.os.Handler;
import android.os.Message;

/**
 * Created by ZMS on 2016/11/21.
 */

public enum AppEvent {

    //title event
    EVENT_TITLE_BINGO_CLICKED,
    EVENT_TITLE_BACK_CLICKED,
    EVENT_TITLE_ACTION_CLICKED,

    //login
    EVENT_ADMIN_LOGIN_SUCCESS,//登录成功

    //菜品管理器切换TAB
    EVENT_ADMIN_FOOD_MGR_SWITCH_TAB,
    //
    ;


    public Message toMessage(Handler handler, Object... args) {
        return Message.obtain(handler, this.ordinal(), args);
    }
}
