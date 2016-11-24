package jtemp.bingossorder;

import com.eunut.base.BaseApplication;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

/**
 * Created by ZMS on 2016/11/23.
 */

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Connector.getDatabase();
    }
}
