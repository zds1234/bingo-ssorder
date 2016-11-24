package jtemp.bingossorder.gui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jtemp.bingossorder.activity.AdminFoodListByCategoryActivity;
import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.entity.EntityFood;

/**
 * Created by ZMS on 2016/11/24.
 */

public class FoodMultiChooseListAdapter extends RecyclerView.Adapter<FoodMultiChooseListAdapter.ViewHolder> {

    private List<EntityFood> data = new ArrayList<>();

    private AdminFoodListByCategoryActivity adminFoodListByCategoryActivity;

    public FoodMultiChooseListAdapter(AdminFoodListByCategoryActivity adminFoodListByCategoryActivity) {
        this.adminFoodListByCategoryActivity = adminFoodListByCategoryActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_mgr_multi_choose_item, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v);
            }
        });
        return new ViewHolder(v);
    }

    private void onItemClick(View v) {
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EntityFood food = data.get(position);
        holder.itemView.setTag(food);
        holder.setData(food);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(EntityFood food) {
        }
    }
}
