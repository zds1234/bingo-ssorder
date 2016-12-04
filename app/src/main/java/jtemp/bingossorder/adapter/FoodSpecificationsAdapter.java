package jtemp.bingossorder.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.utils.DividerGridItemDecoration;
import jtemp.bingossorder.utils.FullyGridLayoutManager;


public class FoodSpecificationsAdapter extends BaseAdapter implements
		MyRecyclerAdapter.OnItemClickListener {

	private List<String> mDatas;
	private Context context;
	private LayoutInflater inflater;

	public FoodSpecificationsAdapter(List<String> mDatas, Context context) {
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
					R.layout.item_adapter_fragment_specifications, null);
			viewHolder.tv_specifications_name = (TextView) convertView
					.findViewById(R.id.tv_specifications_name);
			viewHolder.iv_specifications_add = (ImageView) convertView
					.findViewById(R.id.iv_specifications_add);
			viewHolder.recyclerView = (RecyclerView) convertView
					.findViewById(R.id.recyclerView);
			viewHolder.recyclerAdapter = new MyRecyclerAdapter(context, mDatas);
			viewHolder.layoutManager = new FullyGridLayoutManager(context, 3);
			viewHolder.recyclerView.setLayoutManager(viewHolder.layoutManager);
			viewHolder.decoration = new DividerGridItemDecoration(context);

			viewHolder.recyclerView.setAdapter(viewHolder.recyclerAdapter);
			viewHolder.recyclerView.addItemDecoration(viewHolder.decoration);
			viewHolder.recyclerAdapter.setOnItemClickListener(this);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.recyclerAdapter.isFood();
		viewHolder.recyclerAdapter.compileContent();
		viewHolder.recyclerAdapter.notifyDataSetChanged();
		return convertView;
	}

	class ViewHolder {
		TextView tv_specifications_name;
		ImageView iv_specifications_add;
		RecyclerView recyclerView;

		MyRecyclerAdapter recyclerAdapter;
		FullyGridLayoutManager layoutManager;
		DividerGridItemDecoration decoration;
	}

	@Override
	public void onClick(int position) {
		// TODO Auto-generated method stub
		Log.i("tag", "tag:position:" + position);
	}

}
