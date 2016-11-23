package jtemp.bingossorder.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.admin.AdminFoodManager;
import jtemp.bingossorder.entity.EntityFoodCategory;
import jtemp.bingossorder.gui.adapter.FoodCategoryListAdapter;
import jtemp.bingossorder.utils.AndroidUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFoodMgrCategoryFragment extends Fragment implements View.OnClickListener {

    private FoodCategoryListAdapter foodCategoryListAdapter;

    private AdminFoodMgrCategoryDialog dialog;


    public AdminFoodMgrCategoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_food_mgr_category, container, false);
        dialog = new AdminFoodMgrCategoryDialog(getActivity(), this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(getActivity());
            }
        });
        initContent(view);
        initEvent(view);
        loadCategoryList();
        return view;
    }

    private void initContent(View view) {
        foodCategoryListAdapter = new FoodCategoryListAdapter(this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.food_category_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(foodCategoryListAdapter);
    }

    private void initEvent(View view) {
    }

    @Override
    public void onClick(View v) {
    }

    public void showAddCategoryModel() {
        dialog.setTitle("菜品添加");
        dialog.initContent(null);
        dialog.show();
    }

    public void showEditCategoryModel(EntityFoodCategory foodCategory) {
        dialog.setTitle("菜品编辑");
        dialog.initContent(foodCategory);
        dialog.show();
    }

    public void loadCategoryList() {
        List<EntityFoodCategory> list = AdminFoodManager.findAllCategory();
        foodCategoryListAdapter.setData(list);
    }
}