package jtemp.bingossorder.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jtemp.bingossorder.base.BaseActivity;

/**
 * Created by y66676 on 2016/5/19.
 */
public abstract class BasicAdapter<T> extends BaseAdapter {
    public List<T> list;
    public List<T> list_all_cache;
    public BaseActivity context;

    public void setData(List<T> list){
        this.list_all_cache=new ArrayList<>();
        this.list_all_cache.addAll(list);
        this.list=list;

    }
    public List<T> getCacheData(){
        return list_all_cache;
    }
    public void restoreData(List<T> list){
        list.clear();

        list.addAll(list_all_cache);
    }
//    public void settings(EditText et) {
//        if(watcher==null) return;
//        et.addTextChangedListener(watcher);
//        if(!TextUtils.isEmpty(watcher.tempStr)){
//            et.setText(watcher.tempStr);
//            et.setSelection(watcher.tempStr.length());
//        }
//        et.requestFocus();
//    }
//    public class TextChangedWatcher implements TextWatcher {
//        public String tempStr="";
//        public List<T> temp;
//        private String[] fieldName;
//
//        public TextChangedWatcher(String... fieldNames){
//            this.fieldName=fieldNames;
//        }
//
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            if(s.toString().equals(tempStr)) return;
//            if(TextUtils.isEmpty(s.toString())){
//                restoreData(list);
//                context.notifyInfo(list,false);
//            }else{
//                try {
//                    for(int i=0;i<fieldName.length;i++){
//                        temp=  searchByField(list_all_cache,fieldName[i],s.toString());
//                        list.clear();
//                        list.addAll(temp);
//                    }
//                    context.notifyInfo(list,false);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            tempStr=s.toString();
//        }
//    }



    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
