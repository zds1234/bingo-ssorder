package jtemp.bingossorder.gui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.entity.FoodCategory;
import jtemp.bingossorder.gui.AdminFoodMgrCategoryFragment;

/**
 * Created by ZMS on 2016/11/23.
 */

public class FoodCategoryListAdapter extends RecyclerView.Adapter<FoodCategoryListAdapter.ViewHolder> {

    private AdminFoodMgrCategoryFragment adminFoodMgrCategoryFragment;

    private List<FoodCategory> data = new ArrayList<>();

    public FoodCategoryListAdapter(AdminFoodMgrCategoryFragment adminFoodMgrCategoryFragment) {
        this.adminFoodMgrCategoryFragment = adminFoodMgrCategoryFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_category_list_item, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v);
            }
        });
        return new ViewHolder(v);
    }

    private void onItemClick(View v) {
        Integer position = (Integer) v.getTag();
        FoodCategory category = data.get(position);
        adminFoodMgrCategoryFragment.showEditCategoryModel(category);
    }

    @Override
    public void onBindViewHolder(FoodCategoryListAdapter.ViewHolder holder, int position) {
        FoodCategory entity = data.get(position);
        holder.setData(entity);
        holder.itemView.setTag(new Integer(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<FoodCategory> list) {
        data.clear();
        if (list != null) {
            data.addAll(list);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(FoodCategory entity) {
//            TextView view = (TextView) itemView.findViewById(R.id.food_category_info);
//            StringBuilder sb = new StringBuilder();
//            sb.append(entity.getCategoryName());
//            sb.append("    ");
//            if (entity.getPurchaseLimit() > 0) {
//                sb.append("单点限量:").append(entity.getPurchaseLimit());
//            } else {
//                sb.append("无单点限量");
//            }
//            sb.append("    ");
//            if (entity.isTaocan()) {
//                sb.append("套餐类");
//            }
//            view.setText(sb.toString());
        }
    }
}
