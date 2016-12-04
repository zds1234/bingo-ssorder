package jtemp.bingossorder.adapter;

import java.util.List;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import jtemp.bingossorder.activity.R;

public class AddFoodSpecificationsAdapter extends BaseAdapter {
	
	private List<String> mDatas;
	private Context context;
	private LayoutInflater inflater;

	public AddFoodSpecificationsAdapter(List<String> mDatas, Context context) {
		this.mDatas = mDatas;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(
					R.layout.item_adapter_food_add, null);
			viewHolder.tv_listview_item_content = (TextView) convertView
					.findViewById(R.id.tv_listview_item_content);
			viewHolder.iv_choose = (CheckBox) convertView
					.findViewById(R.id.iv_choose);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
//		viewHolder.tv_listview_item_content.setText(text);
		return convertView;
	}
	
	class ViewHolder {
		TextView tv_listview_item_content;
		CheckBox iv_choose;
	}

}
