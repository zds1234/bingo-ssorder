package com.eunut.widget.viewpage;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SubViewPager extends ViewPager {

	public SubViewPager(Context context) {
		super(context);
	}

	public SubViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		super.dispatchTouchEvent(ev);
//		onTouchEvent(ev); // 进行子View手势的相应操作
		return true;
	}

	/**
	 * Hacky fix for Issue #4 and
	 * http://code.google.com/p/android/issues/detail?id=18990
	 * ScaleGestureDetector seems to mess up the touch events, which means that
	 * ViewGroups which make use of onInterceptTouchEvent throw a lot of
	 * IllegalArgumentException: pointerIndex out of range.
	 * There's not much I can do in my code for now, but we can mask the result by
	 * just catching the problem and ignoring it.
	 */
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		try {
//			return super.onInterceptTouchEvent(ev);
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			return false;
//		} catch (ArrayIndexOutOfBoundsException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
}
