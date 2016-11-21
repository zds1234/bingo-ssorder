package jtemp.bingossorder.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jtemp.bingossorder.activity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFoodMgrFragment extends Fragment {


    public AdminFoodMgrFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_food_mgr, container, false);
    }

}
