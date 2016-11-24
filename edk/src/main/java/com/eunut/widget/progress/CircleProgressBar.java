package com.eunut.widget.progress;

import com.eunut.sdk.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义的圆环进度条
 * 
 * @author Steven.Yao
 * @date 2014-1-10
 */
public class CircleProgressBar extends View {
	// 画实心圆的画笔
	private Paint mCirclePaint;
	// 圆环背景颜色
	private Paint mRingBgPaint;
	// 画圆环的画笔
	private Paint mRingPaint;
	// 画字体的画笔
	private Paint mTextPaint;
	// 圆形颜色
	private int mCircleColor;
	// 圆环背景颜色
	private int mRingBgColor;
	// 圆环颜色
	private int mRingColor;
	// 半径
	private float mRadius;
	// 圆环半径
	private float mRingRadius;
	// 圆环宽度
	private float mStrokeWidth;
	// 圆心x坐标
	private int mXCenter;
	// 圆心y坐标
	private int mYCenter;
	// 字的长度
	private float mTxtWidth;
	// 字的高度
	private float mTxtHeight;
	// 总进度
	private int mTotalProgress = 100;
	// 当前进度
	private int mProgress;
	public CircleProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttrs(context, attrs); // 获取自定义的属性
		initVariable();
	}
	private void initAttrs(Context context, AttributeSet attrs) {
		TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, 0, 0);
		mRadius = typeArray.getDimension(R.styleable.CircleProgressBar_radiu, 80);
		mStrokeWidth = typeArray.getDimension(R.styleable.CircleProgressBar_strokeWidth, 10);
		mCircleColor = typeArray.getColor(R.styleable.CircleProgressBar_circleColor, 0xFFFFFFFF);
		mRingColor = typeArray.getColor(R.styleable.CircleProgressBar_ringColor, 0xFFFFFFFF);
		mRingBgColor = typeArray.getColor(R.styleable.CircleProgressBar_ringBgColor, 0xFFFFFFFF);
		mRingRadius = mRadius + mStrokeWidth / 2;
		typeArray.recycle();
	}
	private void initVariable() {
		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setColor(mCircleColor);
		mCirclePaint.setStyle(Paint.Style.FILL);
		mRingBgPaint = new Paint();
		mRingBgPaint.setAntiAlias(true);
		mRingBgPaint.setColor(mRingBgColor);
		mRingBgPaint.setStyle(Paint.Style.STROKE);
		mRingBgPaint.setStrokeWidth(mStrokeWidth);
		mRingPaint = new Paint();
		mRingPaint.setAntiAlias(true);
		mRingPaint.setColor(mRingColor);
		mRingPaint.setStyle(Paint.Style.STROKE);
		mRingPaint.setStrokeWidth(mStrokeWidth);
		mRingPaint.setStrokeCap(Paint.Cap.ROUND);
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setStyle(Paint.Style.FILL);
		mTextPaint.setARGB(255, 255, 255, 255);
		mTextPaint.setTextSize(mRadius / 2);
		FontMetrics fm = mTextPaint.getFontMetrics();
		mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		mXCenter = getWidth() / 2;
		mYCenter = getHeight() / 2;
		canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
		canvas.drawCircle(mXCenter, mYCenter, mRadius + mStrokeWidth / 2, mRingBgPaint);
		// canvas.drawCircle(mXCenter, mYCenter - mRingRadius, mStrokeWidth / 256, mRingPaint);
		if (mProgress > 0) {
			RectF oval = new RectF();
			oval.left = (mXCenter - mRingRadius);
			oval.top = (mYCenter - mRingRadius);
			oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
			oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
			canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint);
			String txt = mProgress + "%";
			mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
			canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);
		} else {
			// canvas.drawCircle(mXCenter, mYCenter, mRadius + mStrokeWidth / 2, mRingPaint);
		}
	}
	/**
	 * set or update progress
	 * @param progress
	 */
	public void setProgress(int progress) {
		mProgress = progress;
		postInvalidate();
	}
	public int getProgress() {
		return mProgress;
	}
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			final Bundle bundle = (Bundle) state;
			setProgress(bundle.getInt("progress", 0));
			super.onRestoreInstanceState(bundle.getParcelable("state"));
			return;
		}
		super.onRestoreInstanceState(state);
	}
	@Override
	protected Parcelable onSaveInstanceState() {
		final Bundle bundle = new Bundle();
		bundle.putParcelable("state", super.onSaveInstanceState());
		bundle.putInt("progress", mProgress);
		return bundle;
	}
}
