package jtemp.bingossorder.gui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.adapter.ManageTimeFragmentAdapter;
import jtemp.bingossorder.model.SaleTime;

public class ManageTimeFragment extends BaseFragment {
	private ListView orderFoodTime;
	private View view;
	private ManageTimeFragmentAdapter manageTimeAdapter;
	private List<SaleTime> timeList ;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_manage_time_add_foods, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		orderFoodTime = (ListView) view.findViewById(R.id.public_listview);
		loadData();
		manageTimeAdapter = new ManageTimeFragmentAdapter(getActivity(), timeList);
		orderFoodTime.setAdapter(manageTimeAdapter);
	}

	private void loadData() {
		timeList= new ArrayList<SaleTime>();
		// TODO Auto-generated method stub
		SaleTime sacTime = new SaleTime();
		sacTime .name = "早餐时间";
		sacTime.start = 5;
		sacTime.end = 10;
		timeList.add(sacTime);
		SaleTime sacTime1 = new SaleTime();
		sacTime1 .name = "午餐时间";
		sacTime1.start = 11;
		sacTime1.end = 2;
		timeList.add(sacTime1);
		SaleTime sacTime2 = new SaleTime();
		sacTime2 .name = "晚餐时间";
		sacTime2.start = 6;
		sacTime2.end = 10;
		timeList.add(sacTime2);
	}
	
}


