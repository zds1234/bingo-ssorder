package jtemp.bingossorder.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.adapter.KindsAdapter;
import jtemp.bingossorder.adapter.MyPagerAdapter;
import jtemp.bingossorder.base.BaseActivity;
import jtemp.bingossorder.manager.FoodInfoManager;

/**
 * 菜品管理
 * Created by hasee on 2016/12/3.
 */

public class ManageTabFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView lv;
    private KindsAdapter kindsAdapter;
    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;
    private BaseAdapter mFoodAdapter;
    private List<Fragment> fragments;
    FoodInfoManager foodInfoManager = FoodInfoManager.getInstance();
    private TabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_tab,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv=findSpecificViewById(R.id.lv);
        Log.i("lxy123",lv+","+foodInfoManager.getFoodsByKinds(null).size()+","+getActivity());
        kindsAdapter=new KindsAdapter((BaseActivity) getActivity(),foodInfoManager.getFoodsByKinds(null));
        lv.setAdapter(kindsAdapter);
        lv.setOnItemClickListener(this);



        viewPager =findSpecificViewById(R.id.vp_foods);
        fragments =new ArrayList<>();
        Fragment fragment_all=RecyclerViewFragment.newInstance(foodInfoManager.getFoodsList());
        Fragment fragment_onSale =RecyclerViewFragment.newInstance(foodInfoManager.getFoodsList());
        Fragment fragment_offSale =RecyclerViewFragment.newInstance(foodInfoManager.getFoodsList());
        fragments.add(fragment_all);
        fragments.add(fragment_onSale);
        fragments.add(fragment_offSale);
        pagerAdapter =new MyPagerAdapter(getChildFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);


        tabs =findSpecificViewById(R.id.tabs);
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setupWithViewPager(viewPager);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        kindsAdapter.setSelected(position);
        kindsAdapter.notifyDataSetChanged();
        lv.setSelection(position);
    }
}
