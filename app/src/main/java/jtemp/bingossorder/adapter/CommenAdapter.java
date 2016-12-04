package jtemp.bingossorder.adapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public abstract class CommenAdapter<T> extends BaseAdapter {
	protected Context context;
	protected Activity activity;
	protected List<T> datas;
	protected int width, b;
	protected double a;
	private boolean isCanLoadMore = false;

	public CommenAdapter(Context context, List<T> datas) {
		this.context = context;
		this.datas = datas;
	}

	public CommenAdapter(Context context, List<T> datas, int width) {
		this.context = context;
		this.datas = datas;
		this.width = width;
	}

	public CommenAdapter(Context context, ArrayList<T> datas) {
		this.context = context;
		this.datas = datas;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		SparseArray<View> viewMap = null;
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					getLayoutResId(), null);
			int[] resIds = getViewsId();
			if (resIds != null && resIds.length != 0) {
				viewMap = new SparseArray<View>();
				for (int resid : resIds) {
					View v = convertView.findViewById(resid);
					viewMap.put(resid, v);
				}
				convertView.setTag(viewMap);
			}
		}
		viewMap = (SparseArray<View>) convertView.getTag();
		T t = datas.get(position);
		addData2View(viewMap, t, position);
		return convertView;
	}
	
	public void notifyDataSetChanged(List<T> datas) {
		this.datas.clear();
		this.datas = datas;
		super.notifyDataSetChanged();
	}

	// / ˢ��
	public void refresh(List<T> datas) {
		checkCanLoadMore(datas);
		notifyDataSetChanged(datas);
	}

	// ���ظ���
	public void more(List<T> datas) {
		checkCanLoadMore(datas);
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	public void checkCanLoadMore(List<T> datas) {
		// TODO Auto-generated method stub
		isCanLoadMore = false;
		if (datas.size() >= getPageMaxCount()) {
			isCanLoadMore = true;
		}
	}

	public boolean isCanLoadMore() {
		return isCanLoadMore;
	}

	protected int getPageMaxCount() {
		return 10;
	}

	public void clear() {
		if (null != datas) {
			datas.clear();
		}
	}

	/**
	 * listview�б����id
	 * 
	 * @return
	 */
	protected abstract int getLayoutResId();

	/**
	 * listview�б���ؼ�id
	 * 
	 * @return
	 */
	protected abstract int[] getViewsId();

	/**
	 * ������ݵ�view��
	 * 
	 * @return
	 */
	protected abstract void addData2View(SparseArray<View> viewMap, T t,
			int position);

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
