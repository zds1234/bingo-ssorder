package jtemp.bingossorder.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.base.BaseActivity;
import jtemp.bingossorder.model.Foods;
import jtemp.bingossorder.utils.DensityUtil;
import jtemp.bingossorder.utils.JsonImp;

/**
 * Created by hasee on 2016/12/3.
 */

public class KindsAdapter extends BasicAdapter {

    public KindsAdapter(BaseActivity context, List<? extends JsonImp>list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    private int selected;
   public void setSelected(int pos){
       selected=pos;
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_kinds,null);
        }
        TextView tv_kinds =ViewHolder.get(convertView,R.id.tv_kinds);
        tv_kinds.setText(((Foods)(list.get(position))).kinds);
        if(position==selected){
            tv_kinds.setBackgroundResource(R.mipmap.iv_bg_red);
            tv_kinds.setTextColor(context.getResources().getColor(R.color.color_app_background));
            tv_kinds.setTextSize(20);
        }else {
            tv_kinds.setBackgroundResource(R.mipmap.iv_bg_white_gray_border);
            tv_kinds.setTextColor(context.getResources().getColor(R.color.color_red));
            tv_kinds.setTextSize(14);
        }
        return convertView;
    }
}
