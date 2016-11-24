package com.eunut.widget.viewpage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eunut.sdk.R;
import com.eunut.widget.viewpage.indicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;

public class BannerView extends RelativeLayout {

    public static long PERIOD = 5000;// 自动播放间隔周期

    private SubViewPager bannerViewPager;
    private TextView bannerTitle;
    private CirclePageIndicator bannerIndicator;
    private Timer bannerAutoPlayTimer;
    int titleBackGroud;
    private boolean drag = false;
    private boolean autoPlay = true;
    private OnPageChangeListener mListener;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (bannerViewPager.getAdapter().getCount() > 0)
                bannerViewPager.setCurrentItem((bannerViewPager.getCurrentItem() + 1) % bannerViewPager.getAdapter()
                    .getCount(), true);
            return true;
        }
    });

    /**
     * 下一个
     */
    public void next() {
        bannerViewPager.setCurrentItem((bannerViewPager.getCurrentItem() + 1) % bannerViewPager.getAdapter()
            .getCount(), true);
    }

    /**
     * 上一个
     */
    public void previous() {
        int toIndex = bannerViewPager.getCurrentItem() - 1;
        if (toIndex < 0) {
            toIndex = bannerViewPager.getAdapter().getCount() - 1;
        }
        bannerViewPager.setCurrentItem(toIndex, true);
    }

    public BannerView(Context context) {
        super(context);
        init(null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (isInEditMode()) return;
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator);
        LayoutInflater.from(getContext()).inflate(R.layout.eunut_banner, this);
        bannerTitle = (TextView) findViewById(R.id.banner_title);
        bannerViewPager = (SubViewPager) findViewById(R.id.banner_viewpager);
        titleBackGroud = a.getColor(R.styleable.CirclePageIndicator_android_background, Color.TRANSPARENT);
        bannerIndicator = (CirclePageIndicator) findViewById(R.id.banner_indicator);
        bannerIndicator.setBackgroundColor(titleBackGroud);
        bannerIndicator.setGravity(a.getInt(R.styleable.BannerView_android_gravity, Gravity.CENTER));
        bannerIndicator.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                bannerTitle.setText(bannerViewPager.getAdapter().getPageTitle(position));
                if (mListener != null) {
                    mListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                if (mListener != null) {
                    mListener.onPageScrolled(arg0, arg1, arg2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if (mListener != null) {
                    mListener.onPageScrollStateChanged(arg0);
                }
            }
        });
        bannerViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    drag = true;
                } else if (drag && event.getAction() == MotionEvent.ACTION_UP) {
                    startAutoPlay();
                    drag = false;
                }
                return false;
            }
        });
        a.recycle();
    }

    public void setIndicatorGravity(int gravity) {
        bannerIndicator.setGravity(gravity);
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        bannerViewPager.setAdapter(pagerAdapter);
        bannerIndicator.setViewPager(bannerViewPager);
        bannerTitle.setText(bannerViewPager.getAdapter().getPageTitle(0));
        startAutoPlay();
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    private void startAutoPlay() {
        if (autoPlay) {
            stop();
            bannerAutoPlayTimer = new Timer(true);
            bannerAutoPlayTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, PERIOD, PERIOD);
        }
    }

    public void stop() {
        if (bannerAutoPlayTimer != null)
            bannerAutoPlayTimer.cancel();
        bannerAutoPlayTimer = null;
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    public void setIndicatorVisibility(int visibility) {
        bannerIndicator.setVisibility(visibility);
    }

    public void removeAllViews() {
        bannerViewPager.removeAllViews();
    }
}
