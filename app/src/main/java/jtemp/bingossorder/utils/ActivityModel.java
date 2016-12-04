package jtemp.bingossorder.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;

import jtemp.bingossorder.activity.AddDurationActivity;
import jtemp.bingossorder.activity.AddFoodNameActivity;
import jtemp.bingossorder.activity.AddFoodsActivity;
import jtemp.bingossorder.activity.ManageTabActivity;
import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.base.BaseActivity;

/**
 * Created by hasee on 2016/12/4.
 */

public class ActivityModel {
    public static void toAddFoodsActivity(Context context){
        Intent intent =new Intent(context, AddFoodsActivity.class);
        goTo(context,intent);
    }
    public static void goTo(Context context, Intent intent) {
        if (context != null) {
            context.startActivity(intent);
            if (context instanceof BaseActivity) {
                ((Activity) (context)).overridePendingTransition(
                        R.anim.in_from_right, R.anim.out_to_left);
            }
        }
    }

    public static void toAddTimeDuringActivity(Context context) {
        Intent intent =new Intent(context,AddDurationActivity.class);
        goTo(context,intent);


    }

    public static void toAddFoodNameActivity(Context context) {
        Intent intent =new Intent(context,AddFoodNameActivity.class);
        goTo(context,intent);
    }
}
