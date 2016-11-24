package com.eunut.widget.gridview.dragable;

import android.widget.BaseAdapter;

public abstract class DragableGridViewAdapter extends BaseAdapter {

	/**
	 * Removes the element at the specified position in the list
	 */
	public abstract void remove(int position);

	/**
	 * Replaces the element at the specified position in this list with the specified element.
	 */
	public abstract  void swapItems(int positionOne, int positionTwo);
}
