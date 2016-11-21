package jtemp.bingossorder.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.utils.AndroidUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFoodMgrCategoryFragment extends Fragment implements View.OnClickListener {


    public AdminFoodMgrCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_food_mgr_category, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(getActivity());
            }
        });

        initEvent(view);

        return view;
    }

    private void initEvent(View view) {
        view.findViewById(R.id.id_add).setOnClickListener(this);
        view.findViewById(R.id.id_reduce).setOnClickListener(this);
        view.findViewById(R.id.limit_add).setOnClickListener(this);
        view.findViewById(R.id.limit_reduce).setOnClickListener(this);
        view.findViewById(R.id.sort_add).setOnClickListener(this);
        view.findViewById(R.id.sort_reduce).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_add:
                addNumber(v.getRootView().findViewById(R.id.categoryID), 1);
                break;
            case R.id.id_reduce:
                addNumber(v.getRootView().findViewById(R.id.categoryID), -1);
                break;
            case R.id.limit_add:
                addNumber(v.getRootView().findViewById(R.id.limit), 1);
                break;
            case R.id.limit_reduce:
                addNumber(v.getRootView().findViewById(R.id.limit), -1);
                break;
            case R.id.sort_add:
                addNumber(v.getRootView().findViewById(R.id.sort), 1);
                break;
            case R.id.sort_reduce:
                addNumber(v.getRootView().findViewById(R.id.sort), -1);
                break;
            default:
                break;
        }
    }

    private void addNumber(View view, int number) {
        if (view instanceof EditText) {
            EditText text = (EditText) view;
            String str = text.getText().toString();
            int count = 0;
            try {
                count = Integer.parseInt(str);
            } catch (Exception e) {
            }
            count += number;
            if (count <= 0) {
                count = 0;
            }
            text.setText(String.valueOf(count));
        }
    }
}
