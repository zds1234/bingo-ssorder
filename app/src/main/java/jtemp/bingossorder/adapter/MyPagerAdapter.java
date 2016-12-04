package jtemp.bingossorder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zm on 16/3/24.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private  List<Fragment> viewList;
    private String[] titles={"全部","已上架","未上架"};

    public MyPagerAdapter(android.support.v4.app.FragmentManager fm, List<Fragment> viewList){
        super(fm);
        this.viewList = viewList;
    }

    @Override
    public Fragment getItem(int i) {
        return viewList.get(i);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position>titles.length-1)
            return"";
        return titles[position];
    }
}
