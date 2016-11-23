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
import jtemp.bingossorder.gui.AdminFoodMgrCategoryFragment;
import jtemp.bingossorder.gui.AdminFoodMgrEditFragment;
import jtemp.bingossorder.gui.AdminFoodMgrFragment;
import jtemp.bingossorder.gui.AdminFoodMgrSpecFragment;
import jtemp.bingossorder.gui.AdminFoodMgrTabBarFragment;
import jtemp.bingossorder.gui.TitleFragment;
import jtemp.bingossorder.utils.AndroidUtils;

public class AdminFoodManagerActivity extends AppCompatActivity implements AppEventListener {

    public enum TAB_INDEX {
        FOOD_MGR,
        FOOD_MGR_CATEGORY,
        FOOD_MGR_SPEC,
        FOOD_EDIT
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
        titleFragment.setHandler(handler);
        titleFragment.setBackVisible(true);
        titleFragment.setActionVisible(true);
        titleFragment.setActionText("添加");

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

        //food edit
        AdminFoodMgrEditFragment adminFoodMgrEditFragment = new AdminFoodMgrEditFragment();
        tab[TAB_INDEX.FOOD_EDIT.ordinal()] = adminFoodMgrEditFragment;


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
                if (current == TAB_INDEX.FOOD_EDIT) {
                    replaceContent(TAB_INDEX.FOOD_MGR);
                } else {
                    AndroidUtils.pressBack();
                }
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
            case FOOD_MGR_SPEC:
                AdminFoodMgrSpecFragment foodMgrSpecFragment = (AdminFoodMgrSpecFragment) tab[current.ordinal()];
                foodMgrSpecFragment.showAddSpecModel();
                break;
            case FOOD_MGR:
                replaceContent(TAB_INDEX.FOOD_EDIT);
                break;
            default:
                break;
        }
    }

    private void replaceContent(TAB_INDEX tab_index) {
        Fragment fragment = tab[tab_index.ordinal()];
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();
        current = tab_index;
        switch (current) {
            case FOOD_EDIT:
                titleFragment.setActionVisible(false);
                break;
            default:
                titleFragment.setActionVisible(true);
                break;
        }
    }


}
