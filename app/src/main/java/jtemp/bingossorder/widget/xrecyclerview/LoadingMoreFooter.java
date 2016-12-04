package jtemp.bingossorder.widget.xrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.utils.DensityUtil;
import jtemp.bingossorder.widget.xrecyclerview.progressindicator.AVLoadingIndicatorView;


public class LoadingMoreFooter extends LinearLayout {

    private SimpleViewSwitcher progressCon;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    private TextView mText;


	public LoadingMoreFooter(Context context) {
		super(context);
		initView();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LoadingMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
    public void initView(){
        setGravity(Gravity.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getContext(),40)));
        progressCon = new SimpleViewSwitcher(getContext());
        progressCon.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        progressCon.setView(progressView);

        addView(progressCon);
        progressCon.setVisibility(View.GONE);
        mText = new TextView(getContext());
        mText.setText("加载更多...");
        mText.setVisibility(INVISIBLE);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins( (int)getResources().getDimension(R.dimen.manger_kinds_40),0,0,0 );

        mText.setLayoutParams(layoutParams);
        addView(mText);
    }

    public void setProgressStyle(int style) {
        if(style != ProgressStyle.BallSpinFadeLoader){
            progressCon.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        }else{
            AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            progressCon.setView(progressView);
        }
    }

    public void  setState(int state) {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch(state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
                mText.setVisibility(View.VISIBLE);
                mText.setText(getContext().getText(R.string.recyclerheader_loading));
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mText.setText(getContext().getText(R.string.recyclerheader_loading_complete));
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_NOMORE:
                mText.setText(getContext().getText(R.string.recyclerheader_nomore_loading));
                mText.setVisibility(VISIBLE);
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }

    }
}
