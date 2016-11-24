package com.eunut.widget.gridview.dragable;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class DragableGridViewItemLayout extends RelativeLayout implements DragSource, DropTarget {

	public DragableGridViewItemLayout(Context context) {
		super(context);
	}

	public DragableGridViewItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// interface DragTarget implementation: handle what to do when we are the
	// Drag TARGET our item is a target, and is receiving a new drop from somewhere
	/**
	 * the drop is released on our item
	 */
	public void onDrop(DragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo) {
	}

	public void onDragEnter(DragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo) {
	}

	/**
	 * Another item is being dragged and hovering on us, switch items animation
	 */
	public void onDragOver(DragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo) {
		// TODO: switch animation
	}

	public void onDragExit(DragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo) {
	}

	/**
	 * Another item has been dragged on us, accept or reject By default all
	 * gridview items accept any drop
	 */
	public boolean acceptDrop(DragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo) {
		return true;
	}

	public Rect estimateDropLocation(DragSource source, int x, int y, int xOffset, int yOffset, DragView dragView, Object dragInfo, Rect recycle) {
		return null;
	}

	// interface DragSource implementation: handle what to do when we are the Drag SOURCE our item is dragged away
	/**
	 * To accept to be dragged. By default all gridview items can be dragged!
	 */
	public boolean allowDrag() {
		 return true;
	}

	/**
	 * Get the drag controller object
	 */
	public void setDragController(DragController dragger) {
		// Do nothing. We do not need to know the controller object.
	}
	/**
	 * one of our gridviewitems is now a source and is being dragged and
	 * released on Target Target may accept of reject this drag
	 */
	public void onDropCompleted(View target, boolean success) {
		// Do nothing
	}
	public boolean onDown(MotionEvent e) {
		return true;
	}
	public boolean onSingleTapUp(MotionEvent e) {
		return true;
	}
}
