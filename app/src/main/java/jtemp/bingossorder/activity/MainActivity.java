package jtemp.bingossorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import jtemp.bingossorder.event.AppEvent;
import jtemp.bingossorder.event.AppEventHandler;
import jtemp.bingossorder.event.AppEventListener;
import jtemp.bingossorder.gui.AdFragment;
import jtemp.bingossorder.gui.MainContentFragment;
import jtemp.bingossorder.gui.TitleFragment;
import jtemp.hardware.hw.HardwareCheckThread;
import jtemp.hardware.hw.SerialPortHardwareManager;

/**
 * 首页
 */
public class MainActivity extends AppCompatActivity implements AppEventListener {

    private TitleFragment titleFragment;
    private AdFragment adFragment;
    private MainContentFragment contentFragment;

    private AppEventHandler eventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SerialPortHardwareManager.initHardware(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        eventHandler = new AppEventHandler(this);
        initFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HardwareCheckThread.startHardwareCheck();
    }

    private void initFragment() {
        titleFragment = new TitleFragment();
        titleFragment.setHandler(eventHandler);
        titleFragment.setBackVisible(false);
        adFragment = new AdFragment();
        adFragment.setHandler(eventHandler);
        contentFragment = new MainContentFragment();
        contentFragment.setHandler(eventHandler);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.title_fragment, titleFragment);
        fragmentTransaction.replace(R.id.ad_fragment, adFragment);
        fragmentTransaction.replace(R.id.content_fragment, contentFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void handleEvent(Message message) {
        AppEvent event = AppEvent.values()[message.what];
        switch (event) {
            case EVENT_TITLE_BINGO_CLICKED:
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HardwareCheckThread.shutdown();
    }
}
