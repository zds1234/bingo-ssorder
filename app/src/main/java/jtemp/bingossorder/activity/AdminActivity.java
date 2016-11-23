package jtemp.bingossorder.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import jtemp.bingossorder.event.AppEvent;
import jtemp.bingossorder.event.AppEventHandler;
import jtemp.bingossorder.event.AppEventListener;
import jtemp.bingossorder.fragment.AdFragment;
import jtemp.bingossorder.fragment.AdminLoginFragment;
import jtemp.bingossorder.fragment.AdminMainFragment;
import jtemp.bingossorder.fragment.TitleFragment;
import jtemp.bingossorder.utils.AndroidUtils;

/**
 * 管理界面
 */
public class AdminActivity extends AppCompatActivity implements AppEventListener {

    private static final long COUNT_DOWN_TIME = 20 * 1000;

    private TitleFragment titleFragment;
    private AdFragment adFragment;
    private AdminLoginFragment adminLoginFragment;
    private AdminMainFragment adminMainFragment;

    private AppEventHandler eventHandler;

    private boolean adminLogged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin);
        eventHandler = new AppEventHandler(this);
        initFragment();
        countDownBegin();
    }

    /**
     * 开始计时20秒,20秒内未操作退回到主界面
     */
    private void countDownBegin() {

        CountDownTimer timer = new CountDownTimer(COUNT_DOWN_TIME, COUNT_DOWN_TIME) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                onCountDownFinish();
            }
        }.start();
    }

    private void onCountDownFinish() {
        if (this.isFinishing() || adminLogged) {
            return;
        }
        finish();
    }

    private void initFragment() {

        //title
        titleFragment = new TitleFragment();
        titleFragment.setHandler(eventHandler);
        titleFragment.setBackVisible(true);

        //ad
        adFragment = new AdFragment();
        adFragment.setHandler(eventHandler);


        //login
        adminLoginFragment = new AdminLoginFragment();
        adminLoginFragment.setHandler(eventHandler);

        //main
        adminMainFragment = new AdminMainFragment();
        adminMainFragment.setHandler(eventHandler);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.title_fragment, titleFragment);
        fragmentTransaction.replace(R.id.ad_fragment, adFragment);
        fragmentTransaction.replace(R.id.content_fragment, adminLoginFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void handleEvent(Message message) {
        AppEvent event = AppEvent.values()[message.what];
        switch (event) {
            case EVENT_ADMIN_LOGIN_SUCCESS:
                onAdminLoginSuccess();
                break;
            case EVENT_TITLE_BACK_CLICKED:
                AndroidUtils.pressBack();
                break;
            default:
                break;
        }
    }

    private void onAdminLoginSuccess() {
        replaceContent(adminMainFragment);
        this.adminLogged = true;
    }

    private void replaceContent(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();
    }
}
