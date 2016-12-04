package jtemp.bingossorder.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class AddFoodsListView extends ListView {

	public AddFoodsListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public AddFoodsListView(Context context,AttributeSet attrs) {
		super(context,attrs);
		// TODO Auto-generated constructor stub
	}
	public AddFoodsListView(Context context,AttributeSet attrs,int defstyle) {
		super(context,attrs,defstyle);
		// TODO Auto-generated constructor stub
	}
	
	 @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	   // TODO Auto-generated method stub
	 int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	                MeasureSpec.AT_MOST);
	        super.onMeasure(widthMeasureSpec, expandSpec);
	 }

}
