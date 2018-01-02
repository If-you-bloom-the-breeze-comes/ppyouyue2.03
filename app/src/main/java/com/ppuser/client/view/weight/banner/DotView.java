package com.ppuser.client.view.weight.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.ppuser.client.R;


public class DotView extends LinearLayoutCompat implements PagerIndicator {

    private int mLittleDotSize = -2;
    private int mDotSpan = 36;
    private float mDotRadius = 6f;

    private int mCurrent = 0;
    private int mTotal = 0;

    private int mSelectedColor = 0xFF377BEE;
    private int mUnSelectedColor = 0xFFC5CEDB;

    public DotView(Context context) {
        super(context);
    }

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setGravity(Gravity.CENTER_HORIZONTAL);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.DotView, 0, 0);
        if (arr != null) {
            if (arr.hasValue(R.styleable.DotView_dot_radius)) {
                mDotRadius = arr.getDimension(R.styleable.DotView_dot_radius, mDotRadius);
            }

            if (arr.hasValue(R.styleable.DotView_dot_span)) {
                mDotSpan = (int) arr.getDimension(R.styleable.DotView_dot_span, mDotSpan);
            }

            mSelectedColor = arr.getColor(R.styleable.DotView_dot_selected_color, mSelectedColor);
            mUnSelectedColor = arr.getColor(R.styleable.DotView_dot_unselected_color, mUnSelectedColor);
            arr.recycle();
        }

        mLittleDotSize = (int) (mDotSpan / 2 + mDotRadius * 2);
    }

    @Override
    public final void setNum(int num) {
        if (num < 0)
            return;

        mTotal = num;

        removeAllViews();
        setOrientation(HORIZONTAL);
        for (int i = 0; i < num; i++) {
            LittleDot dot = new LittleDot(getContext(), i);
            if (i == 0) {
                dot.setColor(mSelectedColor);
            } else {
                dot.setColor(mUnSelectedColor);
            }
            dot.setLayoutParams(new LayoutParams(mLittleDotSize, (int) mDotRadius * 2, 1));
            addView(dot);
        }
    }

    @Override
    public int getTotal() {
        return mTotal;
    }

    @Override
    public int getCurrentIndex() {
        return mCurrent;
    }


    public final void setSelected(int index) {
        if (index >= getChildCount() || index < 0 || mCurrent == index) {
            return;
        }
        if (mCurrent < getChildCount() && mCurrent >= 0) {
            ((LittleDot) getChildAt(mCurrent)).setColor(mUnSelectedColor);
        }
        ((LittleDot) getChildAt(index)).setColor(mSelectedColor);
        mCurrent = index;
    }

    private class LittleDot extends View {

        private int mColor;
        private Paint mPaint;
        private int mIndex;

        public LittleDot(Context context, int index) {
            super(context);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mIndex = index;
        }

        public int getIndex() {
            return mIndex;
        }

        public void setColor(int color) {
            if (color == mColor)
                return;
            mColor = color;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            mPaint.setColor(mColor);
            canvas.drawCircle(mLittleDotSize / 2, mDotRadius, mDotRadius, mPaint);
        }
    }

    public void setSelectedColor(int color) {
        if (mSelectedColor != color) {
            mSelectedColor = color;
            invalidate();
        }
    }

    public void setColor(int selectedColor, int unSelectedColor) {
        if (mSelectedColor != selectedColor || mUnSelectedColor != unSelectedColor) {
            mSelectedColor = selectedColor;
            mUnSelectedColor = unSelectedColor;
            invalidate();
        }
    }

    public void setUnSelectedColor(int color) {
        if (mUnSelectedColor != color) {
            mSelectedColor = color;
            invalidate();
        }
    }
}
