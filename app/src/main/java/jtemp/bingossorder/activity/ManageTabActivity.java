package jtemp.bingossorder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jtemp.bingossorder.adapter.MyPagerAdapter;
import jtemp.bingossorder.base.BaseActivity;
import jtemp.bingossorder.controller.ManageTabController;
import jtemp.bingossorder.gui.ManageKindsFragment;
import jtemp.bingossorder.gui.ManageSizeFragment;
import jtemp.bingossorder.gui.ManageTabFragment;
import jtemp.bingossorder.gui.ManageTimeFragment;
import jtemp.bingossorder.utils.ActivityModel;
import jtemp.bingossorder.utils.FixViewPagerScroller;
import jtemp.bingossorder.utils.ViewUtils;
import jtemp.bingossorder.widget.TabLayout;

/**
 * Tab主界面
 * Created by hasee on 2016/12/2.
 */

public class ManageTabActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ManageTabController controller;

    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private FixViewPagerScroller scroller;

    private LinearLayout ll_to_add;

    private TabLayout tab_manage,tab_kinds,tab_size,tab_time;
    private List<TabLayout> tabs;
    private int currItem;
    private ManageTabFragment mManageTabFragment;
    private ManageSizeFragment mManageSizeFragment;
    private ManageKindsFragment mManageKindsFragment;
    private ManageTimeFragment mManageTimeFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentLayoutResources() {
        return R.layout.activity_managetab;
    }

    @Override
    public void initUtils() {
        controller =new ManageTabController(this);
        scroller =new FixViewPagerScroller(this);
    }

    @Override
    public void initViews() {
        ll_to_add=findSpecificViewById(R.id.ll_to_add);
        tab_manage=findSpecificViewById(R.id.tab_manage);
        tab_kinds=findSpecificViewById(R.id.tab_kinds);
        tab_size=findSpecificViewById(R.id.tab_size);
        tab_time=findSpecificViewById(R.id.tab_time);
        tabs =new ArrayList<>();
        tabs.add(tab_manage);
        tabs.add(tab_kinds);
        tabs.add(tab_size);
        tabs.add(tab_time);
        viewPager=findSpecificViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(this);
        if(currItem>0&&currItem< tabs.size()){
            viewPager.setCurrentItem(currItem);
            setBarBg(currItem);
        }else{
            ViewUtils.setBgColor(tab_manage,getResources().getColor(R.color.color_bg_yellow));
        }
        mManageTabFragment =new ManageTabFragment();
        mManageKindsFragment =new ManageKindsFragment();
        mManageTimeFragment =new ManageTimeFragment();
        mManageSizeFragment =new ManageSizeFragment();
        List<Fragment> fragments =new ArrayList<>();
        fragments.add(mManageTabFragment);
        fragments.add(mManageKindsFragment);
        fragments.add(mManageSizeFragment);
        fragments.add(mManageTimeFragment);
        myPagerAdapter =new MyPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(myPagerAdapter);
    }

    @Override
    public void initListeners() {
        tab_manage.setOnClickListener(this);
        tab_kinds.setOnClickListener(this);
        tab_size .setOnClickListener(this);
        tab_time.setOnClickListener(this);
        ll_to_add.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_manage:
                setViewPagerScrollSpeed(FixViewPagerScroller.DURATION_ZERO);
//                setBarBg(0);
                viewPager.setCurrentItem(0);
                setViewPagerScrollSpeed(FixViewPagerScroller.DURATION_DEFAULT);
                break;
            case R.id.tab_kinds:
                setViewPagerScrollSpeed(FixViewPagerScroller.DURATION_ZERO);
//                setBarBg(1);
                viewPager.setCurrentItem(1);
                setViewPagerScrollSpeed(FixViewPagerScroller.DURATION_DEFAULT);
                break;
            case R.id.tab_size:
                setViewPagerScrollSpeed(FixViewPagerScroller.DURATION_ZERO);
//                setBarBg(2);
                viewPager.setCurrentItem(2);
                setViewPagerScrollSpeed(FixViewPagerScroller.DURATION_DEFAULT);
                break;
            case R.id.tab_time:
                setViewPagerScrollSpeed(FixViewPagerScroller.DURATION_ZERO);
//                setBarBg(3);
                viewPager.setCurrentItem(3);
                setViewPagerScrollSpeed(FixViewPagerScroller.DURATION_DEFAULT);
                break;
            case R.id.ll_to_add:
                if(currItem==0){
                    ActivityModel.toAddFoodsActivity(this);
                }else if(currItem==1){
                    ActivityModel.toAddKindsActivity(this);
                }else if(currItem==2){
                    ActivityModel.toAddFoodNameActivity(this);
                }else if(currItem==3){
                    ActivityModel.toAddTimeDuringActivity(this);
                }

                break;
        }

    }

    private void setBarBg(int select) {
        for (int i = 0; i < tabs.size(); i++) {
            if (select == i) {
                ViewUtils.setBgColor(tabs.get(i),getResources().getColor(R.color.color_bg_yellow));
            } else {
                ViewUtils.setBgColor(tabs.get(i),getResources().getColor(R.color.color_app_background));
            }
        }
    }

    private void setViewPagerScrollSpeed(int during) {
        scroller.setDuring(during);
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);

            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currItem=position;
        setBarBg(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
