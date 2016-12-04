package jtemp.bingossorder.utils;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by y66676 on 2016/4/16.
 */
public class FixViewPagerScroller extends Scroller {

    public static int DURATION_ZERO=0;
    public static int DURATION_DEFAULT=600;
    private int mDuration=0;
    public FixViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }
    public FixViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }
    public FixViewPagerScroller(Context context) {
        super(context);
    }
    public void setDuring(int mDuration){
        this.mDuration=mDuration;
    }


    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }



}
