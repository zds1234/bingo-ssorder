package jtemp.bingossorder.activity;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jtemp.bingossorder.adapter.RecyclerAdapter;
import jtemp.bingossorder.base.BaseActivity;
import jtemp.bingossorder.manager.FoodInfoManager;
import jtemp.bingossorder.model.Foods;
import jtemp.bingossorder.model.SaleTime;

/**
 * Created by hasee on 2016/12/4.
 */

public class AddFoodsActivity extends BaseActivity implements View.OnClickListener {
    private FoodInfoManager manager;
    private RecyclerView rv_kinds,rl_on_sale_time,rv_single_kind_all;
    private EditText et_name,et_eng_name;
    private CheckBox cb_recommend ,cb_is_on_sale;
    private TextView tv_commit;
    private CheckBox cb_monday,cb_tuesday,cb_wednesday,cb_thursday,cb_friday,cb_saturday,cb_sunday;
    private List<Foods> list_kinds;
    private RecyclerAdapter adapterFor_rv_kinds;
    private RecyclerAdapter adapterFor_rv_on_sale_time;
    private List<SaleTime> list_day_time =new ArrayList<>();;
    private ImageView iv_add_food;
    private LinearLayout ll_bottom;
    private TextView tv_confirm,tv_cancel;
    private List<Foods> list_single_kinds;
    private RecyclerAdapter adapterFor_single_kind;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setParams();
    }

    private void setParams() {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth());
        getWindow().setAttributes(p);
    }

    @Override
    public int getContentLayoutResources() {
        return R.layout.activity_add_foods;
    }

    @Override
    public void initUtils() {
        manager =FoodInfoManager.getInstance();
    }

    @Override
    public void initViews() {
//        分类
        rv_kinds=findSpecificViewById(R.id.rv_kinds);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_kinds.setLayoutManager(llm);


//每日上架时间
        rl_on_sale_time =findSpecificViewById(R.id.rl_on_sale_time);
        LinearLayoutManager llm2=new LinearLayoutManager(this);
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rl_on_sale_time.setLayoutManager(llm2);

        et_name =findSpecificViewById(R.id.et_name);
        et_eng_name=findSpecificViewById(R.id.et_eng_name);
        cb_recommend=findSpecificViewById(R.id.cb_recommend);
        cb_is_on_sale =findSpecificViewById(R.id.cb_is_on_sale);

//        每周上架时间
        cb_monday=findSpecificViewById(R.id.cb_monday);
        cb_tuesday=findSpecificViewById(R.id.cb_tuesday);
        cb_wednesday =findSpecificViewById(R.id.cb_wednesday);
        cb_thursday=findSpecificViewById(R.id.cb_thursday);
        cb_friday =findSpecificViewById(R.id.cb_friday);
        cb_saturday=findSpecificViewById(R.id.cb_saturday);
        cb_sunday =findSpecificViewById(R.id.cb_sunday);

        iv_add_food =findSpecificViewById(R.id.iv_add_food);

//        添加按钮
        tv_commit =findSpecificViewById(R.id.tv_commit);

//        bottom
        ll_bottom=findSpecificViewById(R.id.ll_bottom);
//        某子类的item
        rv_single_kind_all=findSpecificViewById(R.id.rv_single_kind_all);
        tv_confirm =findSpecificViewById(R.id.tv_confirm);
        tv_cancel=findSpecificViewById(R.id.tv_cancel);
        LinearLayoutManager llm3 =new LinearLayoutManager(this);
        llm3.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_single_kind_all.setLayoutManager(llm3);

    }

    @Override
    public void initListeners() {
        iv_add_food.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void initData() {
        list_kinds=manager.getFoodsByKinds(null);
        if(list_kinds.size()>0){
            Log.i("lxy123",list_kinds.get(0).toJson());
        }
//        adapterFor_rv_kinds =new RecyclerAdapter(this,list_kinds,2);
//        rv_kinds.setAdapter(adapterFor_rv_kinds);
        SaleTime st=new SaleTime();
        st.name="早餐";
        list_day_time.add(st);
        SaleTime st2 =new SaleTime();
        st2.name="中餐";
        list_day_time.add(st2);
        SaleTime st3=new SaleTime();
        st3.name="晚餐";
        list_day_time.add(st3);
        adapterFor_rv_on_sale_time =new RecyclerAdapter(this,list_day_time,1);
        rl_on_sale_time.setAdapter(adapterFor_rv_on_sale_time);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_add_food:
                dispatchShow(true);
                break;
            case R.id.tv_cancel:
                break;
            case R.id.tv_confirm:
                break;
        }
    }
//    根据当前选中状态，决定图片数据源。
    private void dispatchShow(boolean isShow) {
        if(isShow){
            if(list_single_kinds==null){
                list_single_kinds=manager.getFoodsList();
                adapterFor_single_kind =new RecyclerAdapter(this,list_single_kinds,3);
                rv_single_kind_all.setAdapter(adapterFor_single_kind);
            }else{
                list_single_kinds.clear();
                list_single_kinds.addAll(manager.getFoodsList());
                adapterFor_single_kind.notifyDataSetChanged();
            }
            ll_bottom.setVisibility(View.VISIBLE);
        }else{
            ll_bottom.setVisibility(View.INVISIBLE);
        }

    }
}
