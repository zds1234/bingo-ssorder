package jtemp.bingossorder.gui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.code.FoodSpecType;

/**
 * Created by ZMS on 2016/11/23.
 */

public class FoodSpecListAdapter extends RecyclerView.Adapter<FoodSpecListAdapter.ViewHolder> {

    private List<FoodSpec> data = new ArrayList<>();

    private AdminFoodMgrSpecFragment adminFoodMgrSpecFragment;

    public FoodSpecListAdapter(AdminFoodMgrSpecFragment adminFoodMgrSpecFragment) {
        this.adminFoodMgrSpecFragment = adminFoodMgrSpecFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_spec_list_item, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v);
            }
        });
        return new ViewHolder(v);
    }

    private void onItemClick(View v) {
        FoodSpec entityFoodSpec = (FoodSpec) v.getTag();
        adminFoodMgrSpecFragment.showEditSpecModel(entityFoodSpec);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodSpec entityFoodSpec = data.get(position);
        holder.itemView.setTag(entityFoodSpec);
        holder.setData(entityFoodSpec);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<FoodSpec> data) {
        this.data.clear();
        if (data != null) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(FoodSpec entityFoodSpec) {
            TextView textView = (TextView) this.itemView.findViewById(R.id.food_spec_info);
            FoodSpecType type = FoodSpecType.valueOf(entityFoodSpec.getSpecType());
            textView.setText(type.getName() + " : " + entityFoodSpec.getSpecName());
        }
    }
}
