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
import jtemp.bingossorder.entity.EntityFoodSpec;
import jtemp.bingossorder.gui.adapter.FoodSpecListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFoodMgrSpecFragment extends Fragment {

    private FoodSpecListAdapter foodSpecListAdapter;

    private AdminFoodMgrSpecDialog dialog;


    public AdminFoodMgrSpecFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_food_mgr_spec, container, false);
        dialog = new AdminFoodMgrSpecDialog(this, getActivity());
        foodSpecListAdapter = new FoodSpecListAdapter(this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.food_spec_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(foodSpecListAdapter);
        loadSpecList();
        return view;
    }

    public void showAddSpecModel() {
        dialog.setTitle("菜品规格");
        dialog.setSpecData(null);
        dialog.show();
    }

    public void loadSpecList() {
        List<EntityFoodSpec> list = AdminFoodManager.findAllSpec();
        foodSpecListAdapter.setData(list);
    }

    public void showEditSpecModel(EntityFoodSpec entityFoodSpec) {
        dialog.setTitle("菜品规格");
        dialog.setSpecData(entityFoodSpec);
        dialog.show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dialog.dismiss();
        dialog = null;
    }
}
