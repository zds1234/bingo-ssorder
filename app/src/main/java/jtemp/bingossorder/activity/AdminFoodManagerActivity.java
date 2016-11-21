package jtemp.bingossorder.activity;

import android.os.Bundle;
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
import jtemp.bingossorder.fragment.AdminFoodCategoryMgrFragment;
import jtemp.bingossorder.fragment.AdminFoodMgrFragment;
import jtemp.bingossorder.fragment.AdminFoodMgrPageBarFragment;
import jtemp.bingossorder.fragment.AdminFoodSpecMgrFragment;
import jtemp.bingossorder.fragment.TitleFragment;

public class AdminFoodManagerActivity extends AppCompatActivity implements AppEventListener {

    private TitleFragment titleFragment;
    private AdminFoodMgrFragment adminFoodMgrFragment;
    private AdminFoodCategoryMgrFragment adminFoodCategoryMgrFragment;
    private AdminFoodSpecMgrFragment adminFoodSpecMgrFragment;
    private AdminFoodMgrPageBarFragment adminFoodMgrPageBarFragment;

    private AppEventHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_food_manager);
        handler = new AppEventHandler(this);
        initFragment();

    }

    private void initFragment() {
        titleFragment = new TitleFragment();
        titleFragment.setBackVisible(true);
        adminFoodMgrFragment = new AdminFoodMgrFragment();
        adminFoodMgrPageBarFragment = new AdminFoodMgrPageBarFragment();
        adminFoodMgrPageBarFragment.setHandler(handler);
        adminFoodCategoryMgrFragment = new AdminFoodCategoryMgrFragment();
        adminFoodSpecMgrFragment = new AdminFoodSpecMgrFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.title_fragment, titleFragment);
        transaction.replace(R.id.content_fragment, adminFoodMgrFragment);
        transaction.replace(R.id.page_fragment, adminFoodMgrPageBarFragment);
        transaction.commit();
    }

    @Override
    public void handleEvent(Message message) {
        AppEvent event = AppEvent.values()[message.what];
        switch (event) {
            case EVENT_ADMIN_FOOD_MGR_DISPLAY:
                replaceContent(adminFoodMgrFragment);
                break;
            case EVENT_ADMIN_FOOD_CAT_MGR_DISPLAY:
                replaceContent(adminFoodCategoryMgrFragment);
                break;
            case EVENT_ADMIN_FOOD_SPEC_MGR_DISPLAY:
                replaceContent(adminFoodSpecMgrFragment);
                break;
            default:
                break;
        }
    }

    private void replaceContent(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();
    }
}
