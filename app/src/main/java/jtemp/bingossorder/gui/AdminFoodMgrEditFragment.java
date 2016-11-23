package jtemp.bingossorder.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.utils.AndroidUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFoodMgrEditFragment extends Fragment {

    @BindView(R.id.food_edit_types)
    RadioGroup food_edit_types;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_food_mgr_edit, container, false);
        ButterKnife.bind(this, view);
        initFoodSpec(view);
        return view;
    }

    @OnClick({R.id.food_edit_scroll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.food_edit_scroll:
                AndroidUtils.hideSoftKeyboard(getActivity());
                break;
        }
    }

    private void initFoodSpec(View view) {

    }
}
