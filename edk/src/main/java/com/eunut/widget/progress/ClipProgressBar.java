package com.eunut.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.eunut.sdk.R;

public class ClipProgressBar extends ImageView {
    public static final int LEFT_TO_RIGHT = 0;
    public static final int RIGHT_TO_LEFT = 1;
    public static final int TOP_TO_BOTTOM = 2;
    public static final int BOTTOM_TO_TOP = 3;
    private double max = 100;
    private double progress = 60;
    private int width = 0;
    private int height = 0;
    private int towards = 0;
    //实例化一个paint对象，其可以设置canvas绘制图形的颜色等属性
    private Paint paint = new Paint();
    private Bitmap bitmap;
    private int resId = 0;

    public ClipProgressBar(Context context) {
        super(context);
        init();
    }

    public ClipProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ClipProgressBar);
        max = a.getFloat(R.styleable.ClipProgressBar_android_max, 100);
        progress = a.getFloat(R.styleable.ClipProgressBar_android_progress, 50);
        towards = a.getInt(R.styleable.ClipProgressBar_towards, 0);
        resId = a.getResourceId(R.styleable.ClipProgressBar_android_src, 0);
        a.recycle();
        init();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        this.resId = resId;
        init();
    }

    ;

    private void init() {
        paint.setAntiAlias(true);
        if (resId > 0) {
            bitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), resId));
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        }
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
        if (progress > max) {
            progress = max;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmap != null) {
            Rect rect = null;
            switch (towards) {
                case LEFT_TO_RIGHT:
                    rect = new Rect(0, 0, (int) (width * (progress / max)), height);
                    break;
                case RIGHT_TO_LEFT:
                    rect = new Rect((int) (width * ((max - progress) / max)), 0, width, height);
                    break;
                case TOP_TO_BOTTOM:
                    rect = new Rect(0, 0, width, (int) (height * (progress / max)));
                    break;
                case BOTTOM_TO_TOP:
                    rect = new Rect(0, (int) (height * ((max - progress) / max)), width, height);
                    break;
                default:
                    super.onDraw(canvas);
                    return;
            }
            canvas.drawBitmap(bitmap, rect, rect, paint);
        }
    }
}  