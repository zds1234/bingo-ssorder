package com.eunut.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Chaly on 15/4/18.
 */
public abstract class SmartAdapter<T> extends BaseAdapter {

    protected List<T> mList;
    protected Context mContext;
    private int mLayoutId;
    private LayoutInflater mLayoutInflater;

    public SmartAdapter(int layoutId,List<T> mList) {
        this.mLayoutId = layoutId;
        this.mList = mList;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList == null || position < 0 || position >= mList.size() ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            if(mContext == null){
                mContext = parent.getContext();
                mLayoutInflater = LayoutInflater.from(mContext);
            }
            convertView = mLayoutInflater.inflate(mLayoutId, parent, false);
        }
        convert(position,convertView,getItem(position));
        return convertView;
    }

    public abstract void convert(int position, View convertView,T bean);
}
