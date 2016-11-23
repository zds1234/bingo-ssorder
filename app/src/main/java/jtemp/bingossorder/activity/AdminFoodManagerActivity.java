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
import jtemp.bingossorder.fragment.AdminFoodMgrCategoryFragment;
import jtemp.bingossorder.fragment.AdminFoodMgrFragment;
import jtemp.bingossorder.fragment.AdminFoodMgrSpecFragment;
import jtemp.bingossorder.fragment.AdminFoodMgrTabBarFragment;
import jtemp.bingossorder.fragment.TitleFragment;
import jtemp.bingossorder.utils.AndroidUtils;

public class AdminFoodManagerActivity extends AppCompatActivity implements AppEventListener {

    public enum TAB_INDEX {
        FOOD_MGR,
        FOOD_MGR_CATEGORY,
        FOOD_MGR_SPEC
    }

    private TitleFragment titleFragment;
    private AdminFoodMgrTabBarFragment adminFoodMgrTabBarFragment;

    private Fragment[] tab = new Fragment[TAB_INDEX.values().length];
    private TAB_INDEX current = TAB_INDEX.FOOD_MGR;

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

        //title
        titleFragment = new TitleFragment();
        titleFragment.setBackVisible(true);
        titleFragment.setHandler(handler);

        //tab bar
        adminFoodMgrTabBarFragment = new AdminFoodMgrTabBarFragment();
        adminFoodMgrTabBarFragment.setHandler(handler);

        //food mgr
        AdminFoodMgrFragment adminFoodMgrFragment = new AdminFoodMgrFragment();
        tab[TAB_INDEX.FOOD_MGR.ordinal()] = adminFoodMgrFragment;

        //food category mgr
        AdminFoodMgrCategoryFragment adminFoodMgrCategoryFragment = new AdminFoodMgrCategoryFragment();
        tab[TAB_INDEX.FOOD_MGR_CATEGORY.ordinal()] = adminFoodMgrCategoryFragment;

        //food spec mgr
        AdminFoodMgrSpecFragment adminFoodMgrSpecFragment = new AdminFoodMgrSpecFragment();
        tab[TAB_INDEX.FOOD_MGR_SPEC.ordinal()] = adminFoodMgrSpecFragment;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.title_fragment, titleFragment);
        transaction.replace(R.id.page_fragment, adminFoodMgrTabBarFragment);
        transaction.replace(R.id.content_fragment, adminFoodMgrFragment);
        transaction.commit();
    }

    @Override
    public void handleEvent(Message message) {
        AppEvent event = AppEvent.values()[message.what];
        switch (event) {
            case EVENT_ADMIN_FOOD_MGR_SWITCH_TAB: {
                Object[] params = (Object[]) message.obj;
                replaceContent((TAB_INDEX) params[0]);
                break;
            }
            case EVENT_TITLE_BACK_CLICKED: {
                AndroidUtils.pressBack();
                break;
            }
            case EVENT_TITLE_ACTION_CLICKED: {
                doTitleAction();
                break;
            }
            default:
                break;
        }
    }

    private void doTitleAction() {
        switch (current) {
            case FOOD_MGR_CATEGORY:
                AdminFoodMgrCategoryFragment foodMgrCategoryFragment = (AdminFoodMgrCategoryFragment) tab[current.ordinal()];
                foodMgrCategoryFragment.showAddCategoryModel();
                break;
            default:
                break;
        }
    }

    private void replaceContent(TAB_INDEX tab_index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_fragment, tab[tab_index.ordinal()]);
        transaction.commit();
        current = tab_index;
        switch (tab_index) {
            case FOOD_MGR_CATEGORY:
                titleFragment.setActionText("添加");
                titleFragment.setActionVisible(true);
                break;
            default:
                titleFragment.setActionVisible(false);
                break;
        }
    }

}
