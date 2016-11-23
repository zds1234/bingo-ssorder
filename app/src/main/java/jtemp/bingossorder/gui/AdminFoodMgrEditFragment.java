package jtemp.bingossorder.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.utils.AndroidUtils;
import jtemp.bingossorder.utils.BindView;
import jtemp.bingossorder.utils.ViewBinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFoodMgrEditFragment extends Fragment {

    @BindView(R.id.food_edit_types)
    private RadioGroup food_edit_types;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_food_mgr_edit, container, false);
        view.findViewById(R.id.food_edit_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(getActivity());
            }
        });
        ViewBinder.bind(view, this);
        initFoodSpec(view);
        return view;
    }

    private void initFoodSpec(View view) {

    }
}
