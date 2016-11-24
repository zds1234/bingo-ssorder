package com.eunut.widget.listview.pinned;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseExpandableListAdapter;

import com.eunut.widget.listview.pinned.PinnedExpandableListView.PinnedHeaderAdapter;

public abstract  class BasePinnedExpandableListAdapter extends BaseExpandableListAdapter implements PinnedHeaderAdapter, OnScrollListener {

	public BasePinnedExpandableListAdapter() {
	}

	// public abstract Object getChild(int groupPosition, int childPosition);
	//
	// public abstract long getChildId(int groupPosition, int childPosition) ;
	//
	// public abstract int getChildrenCount(int groupPosition) ;
	//
	// public abstract View getChildView(int groupPosition, int childPosition,
	// boolean isLastChild, View convertView, ViewGroup parent);
	//
	// public abstract Object getGroup(int groupPosition);
	//
	// public abstract int getGroupCount();
	//
	// public abstract long getGroupId(int groupPosition);
	//
	// public abstract View getGroupView(int groupPosition, boolean isExpanded,
	// View convertView, ViewGroup parent);
	//
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (view instanceof PinnedExpandableListView) {
			((PinnedExpandableListView) view).configureHeaderView(firstVisibleItem);
		}
	}
}