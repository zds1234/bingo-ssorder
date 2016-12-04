package jtemp.bingossorder.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import jtemp.bingossorder.base.BaseActivity;

public class AddFoodNameActivity extends BaseActivity {
	private EditText foodName;
	private ListView addFoodListView;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setParams();
	}
	private void setParams() {
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
		p.width = (int) (d.getWidth()*0.8);
		p.height= (int) (d.getHeight()*0.4);
		getWindow().setAttributes(p);
	}
	@Override
	public int getContentLayoutResources() {
		return R.layout.add_food_name_dialog;
	}

	@Override
	public void initUtils() {

	}

	@Override
	public void initViews() {
		foodName = (EditText) findViewById(R.id.dialog_input_food_name_et);
		addFoodListView = (ListView) findViewById(R.id.add_food_dialog_listview);
	}

	@Override
	public void initListeners() {

	}

	@Override
	public void initData() {

	}


}





