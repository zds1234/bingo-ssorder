package jtemp.bingossorder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.eunut.xutils.util.LogUtil;

import java.util.Collections;
import java.util.List;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.interfaces.ItemDragHelperCallback;
import jtemp.bingossorder.interfaces.RecycleViewListener;
import jtemp.bingossorder.model.Foods;
import jtemp.bingossorder.model.SaleTime;
import jtemp.bingossorder.utils.JsonImp;
import jtemp.bingossorder.widget.CircleImageView;

/**
 * Created by y66676 on 2016/4/19.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,ItemDragHelperCallback.ItemTouchHelperAdapter {
    private Context context;
    private List<? extends JsonImp> list;
    private RecycleViewListener listener;
    private int type;

    public void setRecycleListener(RecycleViewListener listener) {
        this.listener = listener;
    }

    public RecyclerAdapter(Context context, List<? extends JsonImp> list) {
        this.context = context;
        this.list = list;
    }
    public RecyclerAdapter(Context context, List<? extends JsonImp> list,int type) {
        this.context = context;
        this.list = list;
        this.type=type;
        Log.i("lxy123",list.size()+"---");
    }
    public RecyclerAdapter(Context context){
        this.context = context;
    }
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }
    @Override
    public void onItemDismiss(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (type){
            case 0:
            case 3:
                View v = LayoutInflater.from(context).inflate(R.layout.item_foods, null);
                return new ViewHolder(v);
            case 1:
            case 2:
                View v1=LayoutInflater.from(context).inflate(R.layout.layout_check_box,null);
                return new ViewHolder2(v1);
            default:
                View v2 = LayoutInflater.from(context).inflate(R.layout.item_foods, null);
                return new ViewHolder(v2);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        switch (type){
            case 1:
                RecyclerAdapter.ViewHolder2 viewHolder2 = (ViewHolder2) viewHolder;
                viewHolder2.root_layout.setTag(i+"");
                viewHolder2.root_layout.setOnClickListener(this);
                if(list.get(i) instanceof SaleTime){
                    viewHolder2.tv.setText(((SaleTime) list.get(i)).name);
                }
                if(list.get(i) instanceof Foods){
                    viewHolder2.tv.setText(((Foods) list.get(i)).kinds);
                }
                break;
            case 2:
                RecyclerAdapter.ViewHolder2 viewHolder3 = (ViewHolder2) viewHolder;
                viewHolder3.root_layout.setTag(i+"");
                viewHolder3.root_layout.setOnClickListener(this);
                viewHolder3.tv.setOnClickListener(this);
                if(list.get(i) instanceof Foods){
                    viewHolder3.tv.setText(((Foods) list.get(i)).kinds);
                }
                if(list.get(i) instanceof SaleTime){
                    viewHolder3.tv.setText(((SaleTime) list.get(i)).name);
                }
                break;
            case 3:
                RecyclerAdapter.ViewHolder vh4= (ViewHolder) viewHolder;
                //Todo: 加载数据
                vh4.root_layout.setTag(i+"");
                vh4.root_layout.setOnClickListener(this);
                if(list.get(i) instanceof Foods) {
                    vh4.tv.setText(((Foods) list.get(i)).name);
                    vh4.tv.setVisibility(View.VISIBLE);
                    vh4.tv.setTextSize(8);
                    vh4.iv.setImageResource(((Foods) list.get(i)).localSrc);
                    vh4.cb_select.setVisibility(View.VISIBLE);
                }
                break;
            default:
                RecyclerAdapter.ViewHolder vh= (ViewHolder) viewHolder;
                //Todo: 加载数据
                vh.root_layout.setTag(i+"");
                vh.root_layout.setOnClickListener(this);
                if(list.get(i) instanceof Foods){
                    vh.tv.setText(((Foods) list.get(i)).name);
                    vh.tv.setVisibility(View.VISIBLE);
                    vh.iv.setImageResource(((Foods) list.get(i)).localSrc);
                break;
        }

        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }




    public void setData(List<JsonImp> list) {
        this.list = list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.root_layout:
                if(listener!=null) listener.onItemClicked(v,Integer.parseInt((String) v.getTag()));
                break;

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public CircleImageView iv;
        public View root_layout;
        public CheckBox cb_select;

        public ViewHolder(View itemView) {
            super(itemView);
            cb_select= (CheckBox) itemView.findViewById(R.id.cb_select);
            root_layout=itemView.findViewById(R.id.root_layout);
            iv = (CircleImageView) itemView.findViewById(R.id.iv_food);
            tv = (TextView) itemView.findViewById(R.id.tv_food_name);

        }
    }
    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView tv;
        public CheckBox cb;
        public View root_layout;

        public ViewHolder2(View itemView) {
            super(itemView);
            root_layout=itemView.findViewById(R.id.root_layout);
            cb = (CheckBox) itemView.findViewById(R.id.item_standard_add_food_checkbox);
            tv = (TextView) itemView.findViewById(R.id.item_standard_add_food_tv);

        }
    }
}
