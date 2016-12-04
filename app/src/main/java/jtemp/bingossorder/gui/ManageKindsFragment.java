package jtemp.bingossorder.gui;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;


import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.adapter.MyRecyclerAdapter;
import jtemp.bingossorder.utils.DividerGridItemDecoration;
import jtemp.bingossorder.utils.FullyGridLayoutManager;


public class ManageKindsFragment extends BaseFragment implements OnClickListener {
	
	private View view;
	private RecyclerView recyclerView;
	private TextView tv_fragment_kinds_compile,tv_confirm;
	private MyRecyclerAdapter recycleAdapter;
	private List<String> mDatas;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_kinds, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initData();
		init();
	}
	private void initData() {
		mDatas = new ArrayList<String>();
		for (int i=0; i < 10; i++) {
			mDatas.add("item"+i);
		}
	}
	private void init() {
		// TODO Auto-generated method stub
		tv_fragment_kinds_compile = (TextView) view.findViewById(R.id.tv_fragment_kinds_compile);
		tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
		tv_fragment_kinds_compile.setOnClickListener(this);
		tv_confirm.setOnClickListener(this);

		recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
		recycleAdapter=new MyRecyclerAdapter(getActivity(),mDatas);
		FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(getActivity(), 3);
		recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(recycleAdapter);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		recycleAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
			
			@Override
			public void onClick(int position) {
			}
		});
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.tv_fragment_kinds_compile:
			recycleAdapter.compileContent();
			recycleAdapter.notifyDataSetChanged();
			break;
		case R.id.tv_confirm:
			recycleAdapter.confirmContent();
			recycleAdapter.notifyDataSetChanged();
			break;
		}
	}
}
