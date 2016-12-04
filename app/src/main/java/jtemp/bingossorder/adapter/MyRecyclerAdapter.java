package jtemp.bingossorder.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import jtemp.bingossorder.activity.R;


public class MyRecyclerAdapter extends
		RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

	private List<String> mDatas;
	private Context mContext;
	private LayoutInflater inflater;
	private OnItemClickListener mOnItemClickListener;
	private boolean isShow = false;
	private boolean isFood = false;
	
	public interface OnItemClickListener{
		void onClick(int position);
	}
	
	public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
		this.mOnItemClickListener=onItemClickListener;
	}
	
	public MyRecyclerAdapter(Context context, List<String> datas){
		this.mContext=context;
		this.mDatas=datas;
		inflater=LayoutInflater.from(mContext);
	}
	
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}
	
	public void compileContent(){
		isShow = true;
	}
	public void confirmContent(){
		isShow = false;
	}
	
	public void isFood(){
		isFood = true;
	}
	
    public void removeItem(int position){
    	mDatas.remove(position);
    }
    
    public void addItem(int position,String str){
    	mDatas.add(position,str);
    }

	@Override
	public void onBindViewHolder(MyViewHolder holder, final int position) {
		// TODO Auto-generated method stub
//		holder.tv.setText(mDatas.get(position));
//		holder.tv.setText(mDatas.get(position));
		if (isFood) {
			holder.tv_fragment_recycle_item.setVisibility(View.GONE);
		}else{
//			if (isFood) {
//			}
			holder.tv_fragment_recycle_item.setVisibility(View.GONE);
		}
		
		holder.et_fragment_recycle_item.setText((mDatas.get(position)));
		if (isShow) {
			holder.iv_delete.setVisibility(View.VISIBLE);
		}else{
			holder.iv_delete.setVisibility(View.GONE);
		}
		if (mOnItemClickListener != null) {
			
			holder.itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mOnItemClickListener.onClick(position);
				}
			});
			holder.iv_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					removeItem(position);
					notifyDataSetChanged();
				}
			});
		}
	}
	
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.item_fragment_recycle, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}
	
	class MyViewHolder extends ViewHolder {

		TextView tv_fragment_recycle_item;
		EditText et_fragment_recycle_item;
		ImageView iv_delete;

		public MyViewHolder(View view) {
			super(view);
			et_fragment_recycle_item = (EditText) view.findViewById(R.id.et_fragment_recycle_item);
			tv_fragment_recycle_item = (TextView) view.findViewById(R.id.tv_fragment_recycle_item);
			iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
		}

	}
	
}
