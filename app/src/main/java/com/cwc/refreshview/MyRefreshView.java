package com.cwc.refreshview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author Cuiweicong
 */

public class MyRefreshView extends View {
    private int mLocalHeight;
    private int mHeight;
    private int mWidth;
    private int mMaxHeight;
    private Paint mPaint;
    private Path mPath;

    private int multiple = 2;

    private boolean mIsReseting = false;

    public MyRefreshView(Context context) {
        this(context, null);
    }

    public MyRefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mWidth = getResources().getDisplayMetrics().widthPixels;
        mMaxHeight = (int) getResources().getDimension(R.dimen.height);
        mLocalHeight = mHeight = mMaxHeight / 2;
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.blue));
        mPaint.setAntiAlias(true);
        mPath = new Path();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("lws", "ondraw");
        super.onDraw(canvas);
        int height = mHeight < mMaxHeight ? mHeight : mMaxHeight;
        canvas.drawRect(0, 0, mWidth, height, mPaint);
        mPath.reset();
        mPath.moveTo(0, height);
        mPath.quadTo(mWidth / 2, mHeight, mWidth, height);
        canvas.drawPath(mPath, mPaint);
        if (mIsReseting){
            resetView();
        }
    }

    public void addHeight(int height){
        mHeight = mLocalHeight;
        mHeight += height / multiple;
        requestLayout();
    }

    public void reset(){
        mIsReseting = true;
        invalidate();
    }

    public void resetView(){
        if (mHeight > (mMaxHeight + 5)) {
            mHeight -= 10;
            requestLayout();
        } else {
            mHeight = mMaxHeight;
            mLocalHeight = mMaxHeight;
            mIsReseting = false;
            requestLayout();
        }
    }

}
