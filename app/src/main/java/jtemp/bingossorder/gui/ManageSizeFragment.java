package jtemp.bingossorder.gui;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.adapter.FoodSpecificationsAdapter;

public class ManageSizeFragment extends BaseFragment implements OnClickListener{

	private View view;
	private ListView lv_specifications;
	private FoodSpecificationsAdapter foodSpecificationsAdapter;
	private List<String> mDates;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_specifications, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
		initdate();
		foodSpecificationsAdapter = new FoodSpecificationsAdapter(mDates, getActivity());
		lv_specifications.setAdapter(foodSpecificationsAdapter);
	}
	
	
	private void initdate() {
		// TODO Auto-generated method stub
		mDates = new ArrayList<String>();
		for (int i=0; i < 10; i++) {
			mDates.add("item"+i);
		}
	}
	private void init() {
		// TODO Auto-generated method stub
		lv_specifications = (ListView) view.findViewById(R.id.lv_specifications);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
