package com.eunut.widget.textview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MultilineTextView extends View {

    private TextPaint mTextPaint;
    private String mText;
    private int mAscent;
    private String mStrEllipsis;
    private String mStrEllipsisMore;
    private int mMaxLines;
    private boolean mDrawEllipsizeMoreString;
    private int mColorEllipsizeMore;
    private boolean mRightAlignEllipsizeMoreString;
    private boolean mExpanded;
    private LineBreaker mBreakerExpanded;
    private LineBreaker mBreakerCollapsed;

    public MultilineTextView(Context context) {
        super(context);
        init();
    }

    public MultilineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mExpanded = false;
        mDrawEllipsizeMoreString = true;
        mRightAlignEllipsizeMoreString = false;
        mMaxLines = -1;
        mStrEllipsis = "...";
        mStrEllipsisMore = "更多";
        mColorEllipsizeMore = 0xFF0000FF;

        mBreakerExpanded = new LineBreaker();
        mBreakerCollapsed = new LineBreaker();

        // Default font size and color.
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(0xFF000000);
        mTextPaint.setTextAlign(Align.LEFT);
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (getIsExpanded()) {
                    collapse();
                } else {
                    expand();
                }
            }
        });
        setTextSize(14);
    }

    /**
     * Sets the text to display in this widget.
     * @param text The text to display.
     */
    public void setText(String text) {
        mText = text;
        requestLayout();
        invalidate();
    }

    /**
     * Sets the text size for this widget.
     * @param size Font size.
     */
    public void setTextSize(int size) {
        Context c = getContext();
        Resources r;
        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, r.getDisplayMetrics()));
        requestLayout();
        invalidate();
    }

    /**
     * Sets the text color for this widget.
     * @param color ARGB value for the text.
     */
    public void setTextColor(int color) {
        mTextPaint.setColor(color);
        invalidate();
    }

    /**
     * The string to append when ellipsizing. Must be shorter than the available
     * width for a single line!
     * @param ellipsis The ellipsis string to use, like "...", or "-----".
     */
    public void setEllipsis(String ellipsis) {
        mStrEllipsis = ellipsis;
    }

    /**
     * Optional extra ellipsize string. This
     */
    public void setEllipsisMore(String ellipsisMore) {
        mStrEllipsisMore = ellipsisMore;
    }

    /**
     * The maximum number of lines to allow, height-wise.
     */
    public void setMaxLines(int maxLines) {
        mMaxLines = maxLines;
    }

    /**
     * Turn drawing of the optional ellipsizeMore string on or off.
     * @param drawEllipsizeMoreString Yes or no.
     */
    public void setDrawEllipsizeMoreString(boolean drawEllipsizeMoreString) {
        mDrawEllipsizeMoreString = drawEllipsizeMoreString;
    }

    /**
     * Font color to use for the optional ellipsizeMore string.
     * @param color ARGB color.
     */
    public void setColorEllpsizeMore(int color) {
        mColorEllipsizeMore = color;
    }

    /**
     * When drawing the ellipsizeMore string, either draw it wherever ellipsizing
     * on the last line occurs, or always right align it. On by default.
     * @param rightAlignEllipsizeMoreString Yes or no.
     */
    public void setRightAlignEllipsizeMoreString(boolean rightAlignEllipsizeMoreString) {
        mRightAlignEllipsizeMoreString = rightAlignEllipsizeMoreString;
    }

    /**
     * @see View#measure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * Determines the width of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be.
            result = specSize;

            // Format the text using this exact width, and the current mode.
            breakWidth(specSize);
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                // Use the AT_MOST size - if we had very short text, we may need even
                // less
                // than the AT_MOST value, so return the minimum.
                result = breakWidth(specSize);
                result = Math.min(result, specSize);
            } else {
                // We're not given any width - so in this case we assume we have an
                // unlimited
                // width?
                breakWidth(specSize);
            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        mAscent = (int) mTextPaint.ascent();
        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be, so nothing to do.
            result = specSize;
        } else {
            // The lines should already be broken up. Calculate our max desired height
            // for our current mode.
            int numLines;
            if (mExpanded) {
                numLines = mBreakerExpanded.getLines().size();
            } else {
                numLines = mBreakerCollapsed.getLines().size();
            }
            result = numLines * (int) (-mAscent + mTextPaint.descent()) + getPaddingTop() + getPaddingBottom();

            // Respect AT_MOST value if that was what is called for by measureSpec.
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Render the text
     * @see View#onDraw(Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        List<int[]> lines;
        LineBreaker breaker;
        if (mExpanded) {
            breaker = mBreakerExpanded;
            lines = mBreakerExpanded.getLines();
        } else {
            breaker = mBreakerCollapsed;
            lines = mBreakerCollapsed.getLines();
        }

        float x = getPaddingLeft();
        float y = getPaddingTop() + (-mAscent);
        for (int i = 0; i < lines.size(); i++) {
            // Draw the current line.
            int[] pair = lines.get(i);
            canvas.drawText(mText, pair[0], pair[1] + 1, x, y, mTextPaint);

            // Draw the ellipsis if necessary.
            if (i == lines.size() - 1) {
                if (breaker.getRequiredEllipsis()) {
                    canvas.drawText(mStrEllipsis, x + breaker.getLengthLastEllipsizedLine(), y, mTextPaint);
                    if (mDrawEllipsizeMoreString) {
                        int lastColor = mTextPaint.getColor();
                        mTextPaint.setColor(mColorEllipsizeMore);
                        if (mRightAlignEllipsizeMoreString) {
                            // Seems to not be right...
                            canvas.drawText(mStrEllipsisMore, canvas.getWidth() - (breaker.getLengthEllipsisMore() + getPaddingRight() + getPaddingLeft()), y, mTextPaint);
                        } else {
                            canvas.drawText(mStrEllipsisMore, x + breaker.getLengthLastEllipsizedLinePlusEllipsis(), y, mTextPaint);
                        }
                        mTextPaint.setColor(lastColor);
                    }
                }
            }

            y += (-mAscent + mTextPaint.descent());
            if (y > canvas.getHeight()) {
                break;
            }
        }
    }

    public boolean getIsExpanded() {
        return mExpanded;
    }

    public void expand() {
        mExpanded = true;
        requestLayout();
        invalidate();
    }

    public void collapse() {
        mExpanded = false;
        requestLayout();
        invalidate();
    }

    private int breakWidth(int availableWidth) {
        int widthUsed = 0;
        if (mExpanded) {
            widthUsed = mBreakerExpanded.breakText(mText, availableWidth - getPaddingLeft() - getPaddingRight(), mTextPaint);
        } else {
            widthUsed = mBreakerCollapsed.breakTextFast(mText, mStrEllipsis, mStrEllipsisMore, mMaxLines, availableWidth - getPaddingLeft() - getPaddingRight(),
                mTextPaint);
        }

        return widthUsed + getPaddingLeft() + getPaddingRight();
    }

    /**
     * Used internally to break a string into a list of integer pairs. The pairs
     * are start and end locations for lines given the current available layout
     * width.
     */
    private static class LineBreaker {
        /** Was the input text long enough to need an ellipsis? */
        private boolean mRequiredEllipsis;

        /** Beginning and end indices for the input string. */
        private ArrayList<int[]> mLines;

        /**
         * The width in pixels of the last line, used to draw the ellipsis if
         * necessary.
         */
        private float mLengthLastLine;

        /**
         * The width of the ellipsis string, so we know where to draw the
         * ellipsisMore string if necessary.
         */
        private float mLengthEllipsis;

        /** The width of the ellipsizeMore string, same use as above. */
        private float mLengthEllipsisMore;

        public LineBreaker() {
            mRequiredEllipsis = false;
            mLines = new ArrayList<int[]>();
        }

        /**
         * Used for breaking text in 'expanded' mode, which needs no ellipse. Uses
         * as many lines as is necessary to accomodate the entire input string.
         * @param input String to be broken.
         * @param maxWidth Available layout width.
         * @param tp Current paint object with styles applied to it.
         */
        public int breakText(String input, int maxWidth, TextPaint tp) {
            // return breakText(input, null, null, -1, maxWidth, tp);
            return breakTextFast(input, maxWidth, tp);
        }

        public final int breakTextFast(String input, int maxWidth, TextPaint tp) {
            CharSequence textCharArray = input.subSequence(0, input.length());
            int inputLength = textCharArray.length();
            mLines.clear();
            int offset = 0;
            while (offset < inputLength) {
                int numOfChars = tp.breakText(textCharArray, offset, textCharArray.length(), true, maxWidth, null);
                mLines.add(new int[]{offset, (offset += numOfChars) - 1});
            }
            return maxWidth;
        }

        public final int breakTextFast(String input, String ellipsis, String ellipsisMore, int maxLines, int maxWidth, TextPaint tp) {
            CharSequence textCharArray = input.subSequence(0, input.length());
            int inputLength = textCharArray.length();
            mRequiredEllipsis = false;
            mLengthLastLine = 0.0f;
            mLengthEllipsis = 0.0f;
            mLengthEllipsisMore = 0.0f;
            if (ellipsis != null) {
                mLengthEllipsis = tp.measureText(ellipsis);
            }
            if (ellipsisMore != null) {
                mLengthEllipsis = tp.measureText(ellipsisMore);
            }
            float maxLineWidth = 0;
            float[] measuredWidth = new float[1];
            measuredWidth[0] = 0;
            mLines.clear();
            int offset = 0, k = 0;
            while (k++ < maxLines && offset < inputLength) {
                int numOfChars = tp.breakText(textCharArray, offset, textCharArray.length(), true, maxWidth, measuredWidth);
                maxLineWidth = maxLineWidth > measuredWidth[0] ? maxLineWidth : measuredWidth[0];
                mLines.add(new int[]{offset, (offset += numOfChars) - 1});
            }
            int[] location = mLines.get(mLines.size() - 1);
            if (k >= maxLines && location[1] != inputLength - 1) {
                mRequiredEllipsis = true;
                location[1] = location[0] + tp.breakText(textCharArray, location[0], location[1], true, maxWidth - (mLengthEllipsis + mLengthEllipsis), measuredWidth)
                    - 1;
                maxLineWidth = maxLineWidth > measuredWidth[0] ? maxLineWidth : measuredWidth[0];
            }
            mLengthLastLine = measuredWidth[0];
            return (int) maxLineWidth;
        }

        public boolean getRequiredEllipsis() {
            return mRequiredEllipsis;
        }

        public List<int[]> getLines() {
            return mLines;
        }

        public float getLengthLastEllipsizedLine() {
            return mLengthLastLine;
        }

        public float getLengthLastEllipsizedLinePlusEllipsis() {
            return mLengthLastLine + mLengthEllipsis;
        }

        public float getLengthEllipsis() {
            return mLengthEllipsis;
        }

        public float getLengthEllipsisMore() {
            return mLengthEllipsisMore;
        }

        /**
         * 判断文本中是否含有中文
         */
        private boolean hasChinese(String input) {
            return input.getBytes().length != input.length();
        }
    }

}