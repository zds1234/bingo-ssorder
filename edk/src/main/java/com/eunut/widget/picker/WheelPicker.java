package com.eunut.widget.picker;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eunut.base.BaseApplication;
import com.eunut.base.adapter.SmartRecyclerAdapter;
import com.eunut.sdk.R;
import com.eunut.util.PopupUtil;
import com.eunut.widget.WheelView;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Chaly on 16/9/30.
 */

public class WheelPicker extends LinearLayout implements View.OnClickListener, WheelView.OnSelectListener {

    private TextView pickerTitle;
    private WheelView dataSource;
    private RecyclerView resultView;

    private static WheelPicker instance;
    private int level = 1;
    private int currentLevel = 0;
    private List resultList = Lists.newArrayList();
    private ResultAdapter mResultAdapter;
    private Provider provider;
    private Callback callback;

    public WheelPicker(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
        mLayoutInflater.inflate(R.layout.eunut_wheel_picker, this);
        pickerTitle = (TextView) findViewById(R.id.picker_title);
        resultView = (RecyclerView) findViewById(R.id.result_view);
        resultView = (RecyclerView) findViewById(R.id.result_view);
        dataSource = (WheelView) findViewById(R.id.data_source);
        findViewById(R.id.picker_ok).setOnClickListener(this);
        findViewById(R.id.picker_cancel).setOnClickListener(this);
        //滚动监听
        dataSource.setOnSelectListener(this);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        resultView.setLayoutManager(layoutManager);
        mResultAdapter = new ResultAdapter(resultList);
        resultView.setAdapter(mResultAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.picker_ok) {
            PopupUtil.close();
            if (callback != null) {
                callback.onResult(resultList);
            }
        } else if (id == R.id.picker_cancel) {
            PopupUtil.close();
        }
    }

    public void setData(List list) {
        if (list == null || list.size() == 0) return;
        //设置原始数据
        dataSource.refreshData(list);
        int index = 2;
        if (resultList.size() > 0 && currentLevel < resultList.size()) {
            for (int i = 0; i < list.size(); i++) {
                Object item = list.get(i);
                if (item.toString().equals(resultList.get(currentLevel).toString())) {
                    index = i;
                    break;
                }
            }
        }
        dataSource.setDefault(index);
    }

    @Override
    public void endSelect(int id, String text) {
        Object father = dataSource.getData().get(id);
        if (currentLevel < resultList.size()) {
            resultList.set(currentLevel, father);
        } else {
            resultList.add(currentLevel, father);
        }
        while (resultList.size() - 1 > currentLevel) {
            resultList.remove(resultList.size() - 1);
        }
        mResultAdapter.notifyDataSetChanged();
        if (currentLevel + 1 < level) {
            provider.provide(++currentLevel, father, WheelPicker.this);
        }
    }

    @Override
    public void selecting(int id, String text) {

    }

    public WheelPicker setTitle(int resId) {
        this.pickerTitle.setText(resId);
        return instance;
    }

    public WheelPicker setTitle(String title) {
        this.pickerTitle.setText(title);
        return instance;
    }

    public WheelPicker setDefault(List list) {
        resultList.clear();
        if (list != null)
            resultList.addAll(list);
        currentLevel = Math.max(resultList.size() - 1, 0);
        return instance;
    }

    public WheelPicker setCallback(Callback callback) {
        this.callback = callback;
        return instance;
    }

    public static WheelPicker with(int level, Provider provider) {
        instance = new WheelPicker(BaseApplication.get().getCurrentActivity());
        instance.level = level;
        instance.provider = provider;
        if (level == 1) instance.resultView.setVisibility(GONE);
        return instance;
    }

    //显示选择器
    public void show() {
        PopupUtil.make(BaseApplication.get().getCurrentActivity())
            .setWidthFillType(ViewGroup.LayoutParams.MATCH_PARENT)
            .setContentView(this).show();
        //初始化默认数据
        if (resultList.size() > 0) {//有默认数据
            int size = resultList.size();
            provider.provide(currentLevel, size > 1 ? resultList.get(size - 2) : null, this);
        } else {//没有默认数据
            provider.provide(0, null, this);
        }
    }

    public interface Callback<P> {
        public void onResult(List<P> result);
    }

    public interface Provider {
        public void provide(int level, Object father, WheelPicker wheelPicker);
    }

    public class ResultAdapter extends SmartRecyclerAdapter {

        public ResultAdapter(List mList) {
            super(R.layout.eunut_item_wheel_picker_result, mList);
        }

        @Override
        public RecyclerView.ViewHolder onCreate(View convertView, int viewType) {
            return new ViewHolder(convertView);
        }

        @Override
        public void onBind(RecyclerView.ViewHolder viewHolder, int position, Object data) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.text.setText(data.toString());
            holder.position = position;
            if (position == level - 1) {
                holder.text.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                holder.text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.et_arrow, 0);
            }
            holder.text.setSelected(position == currentLevel);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
            int position;
            TextView text;

            public ViewHolder(View view) {
                super(view);
                text = (TextView) view.findViewById(R.id.text);
                text.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (position != currentLevel) {
                    currentLevel = position;
                    ResultAdapter.this.notifyDataSetChanged();
                    provider.provide(
                        currentLevel,
                        currentLevel > 0 ? resultList.get(currentLevel - 1) : null,
                        WheelPicker.this
                    );
                }
            }
        }
    }

}
