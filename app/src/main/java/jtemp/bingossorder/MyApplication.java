package jtemp.bingossorder;

import android.app.Application;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

/**
 * Created by ZMS on 2016/11/23.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Connector.getDatabase();
    }
}
