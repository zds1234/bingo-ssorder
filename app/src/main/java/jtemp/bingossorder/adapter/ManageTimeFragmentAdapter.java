package jtemp.bingossorder.adapter;

import java.util.List;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.model.SaleTime;
import jtemp.bingossorder.utils.DateUtils;


public class ManageTimeFragmentAdapter extends CommenAdapter<SaleTime>{
	private TimePickerDialog timeChoose;
	public ManageTimeFragmentAdapter(Context context, List<SaleTime> datas) {
		super(context, datas);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int getLayoutResId() {
		// TODO Auto-generated method stub
		return R.layout.item_manage_time_fragment_adapter;
	}

	@Override
	protected int[] getViewsId() {
		// TODO Auto-generated method stub
		return new int[]{R.id.item_manage_time_adapter_tv,R.id.item_manage_start_time_adapter_tv,
				R.id.item_manage_end_time_adapter_tv};
	}

	@Override
	protected void addData2View(SparseArray<View> viewMap, SaleTime t,
			int position) {
		// TODO Auto-generated method stub
		TextView orderTime = (TextView) viewMap.get(R.id.item_manage_time_adapter_tv);
		final TextView startTime = (TextView) viewMap.get(R.id.item_manage_start_time_adapter_tv);
		final TextView endTime = (TextView) viewMap.get(R.id.item_manage_end_time_adapter_tv);
		orderTime.setText(t.name);
		startTime.setText(t.start+"");
		endTime.setText(t.end+"");
		startTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stuboi
				timeChoose = new TimePickerDialog(context,android.R.style.Theme_DeviceDefault_Light_Dialog,new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						startTime.setText(hourOfDay + ":" + minute);
					}
				}, DateUtils.getHourOfDay(), DateUtils.getMinute(), true);
//				view = LayoutInflater.from(context).inflate(R.layout.date_layout, null);
//				timeChoose.setView(view);l
				timeChoose.setCanceledOnTouchOutside(false);
				timeChoose.show();
			}
		});
		endTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timeChoose = new TimePickerDialog(context,android.R.style.Theme_DeviceDefault_Light_Dialog, new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						endTime.setText(hourOfDay + ":" + minute);
					}
				}, DateUtils.getHourOfDay(), DateUtils.getMinute(), true);
//				view = LayoutInflater.from(context).inflate(R.layout.date_layout, null);
//				timeChoose.setView(view);
				timeChoose.setCanceledOnTouchOutside(false);
				timeChoose.show();
			}
		});
	}

}



