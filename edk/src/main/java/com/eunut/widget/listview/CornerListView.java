package com.eunut.widget.listview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eunut.sdk.R;

public class CornerListView extends ListView {
    public CornerListView(Context context) {
        super(context);
        init();
    }
    public CornerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public CornerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
    	this.setBackgroundResource(R.drawable.eunut_corner_list_bg);
    	this.setCacheColorHint(Color.TRANSPARENT);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	            int x = (int) ev.getX();
	            int y = (int) ev.getY();
	            int itemnum = pointToPosition(x, y);
	            if (itemnum == AdapterView.INVALID_POSITION)
	            	break;                 
	            else{
	            	if(itemnum==0){
	                    if(itemnum==(getAdapter().getCount()-1)){                                    
	                        setSelector(R.drawable.eunut_corner_list_round);
	                    }else{
	                        setSelector(R.drawable.eunut_corner_list_round_top);
	                    }
	                }else if(itemnum==(getAdapter().getCount()-1))
	                	setSelector(R.drawable.eunut_corner_list_round_bottom);
	                else{                            
	                    setSelector(R.drawable.eunut_corner_list_shape);
	                }
	            }
	        	break;
	        case MotionEvent.ACTION_UP:
	        	break;
        }
        return super.onInterceptTouchEvent(ev);
    }
    @Override 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
        int expandSpec = MeasureSpec.makeMeasureSpec( 
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST); 
        super.onMeasure(widthMeasureSpec, expandSpec); 
    } 
}