package jtemp.bingossorder.gui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.adapter.RecyclerAdapter;
import jtemp.bingossorder.interfaces.ItemDragHelperCallback;
import jtemp.bingossorder.model.Foods;
import jtemp.bingossorder.utils.JsonImp;

/**
 * Created by hasee on 2016/12/3.
 */

public class RecyclerViewFragment extends BaseFragment {
    public static RecyclerViewFragment newInstance(List<? extends JsonImp> data ){
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", (Serializable) data);
        fragment.setArguments(args);
        return fragment;
    }
    private RecyclerView rv;
    private List<?extends JsonImp> list;
    private RecyclerAdapter adapter;
    private GridLayoutManager gm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            list= (List<?extends JsonImp>) getArguments().getSerializable("data");
        }
        if(list==null) list =new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv=findSpecificViewById(R.id.recycler_view);
        Log.i("lxy123",list.size()+"----");
        adapter =new RecyclerAdapter(getActivity().getApplicationContext(), (List<JsonImp>) list);
        gm=new GridLayoutManager(getActivity(),3);
        ItemDragHelperCallback callback =
                new ItemDragHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rv);
        rv.setLayoutManager(gm);
        rv.setAdapter(adapter);

    }


}
