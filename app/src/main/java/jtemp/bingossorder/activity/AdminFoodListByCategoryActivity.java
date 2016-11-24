package jtemp.bingossorder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import jtemp.bingossorder.gui.adapter.FoodMultiChooseListAdapter;

public class AdminFoodListByCategoryActivity extends AppCompatActivity {

    public static final String PARAM_FOOD_CATEGORY = "food_category";

    private FoodMultiChooseListAdapter foodMultiChooseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food_list_by_category);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.food_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        foodMultiChooseListAdapter = new FoodMultiChooseListAdapter(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(foodMultiChooseListAdapter);
    }
}
