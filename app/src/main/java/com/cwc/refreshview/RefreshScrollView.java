package com.cwc.refreshview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 这是个ScrollView，使用addNewView方法添加视图
 *
 * @author Cuiweicong
 */

public class RefreshScrollView extends ScrollView {
    private Context mContext;
    private MyRefreshView mMyRefreshView;
    private LinearLayout mMyRefreshLayout;
    private boolean mIsDraging;

    private int mStartY;
    private int mEndY;

    public RefreshScrollView(Context context) {
        super(context);
        init(context);
    }

    public RefreshScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mMyRefreshLayout = new LinearLayout(mContext);
        mMyRefreshLayout.setOrientation(LinearLayout.VERTICAL);
        mMyRefreshView = new MyRefreshView(mContext);
        //现在ScrollView中添加LinearLayout
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mMyRefreshLayout, lp);
        //在LinearLayout中添加RefreshView
        mMyRefreshLayout.addView(mMyRefreshView, lp);
    }

    public void newAddView(View view){
        mMyRefreshLayout.addView(view);
    }

    public void newAddView(View view, LinearLayout.LayoutParams layoutParams){
        mMyRefreshLayout.addView(view, layoutParams);
    }

    public ViewGroup getViewGroup(){
        return mMyRefreshLayout;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (getScrollY() == 0) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mStartY = (int) ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mEndY = (int) ev.getY();
                    int dis = mEndY - mStartY;
                    if (dis > 0) {
                        mMyRefreshView.addHeight(dis);
                        mIsDraging = true;
                    }
                    break;
                default:
                    mMyRefreshView.reset();
                    mIsDraging = false;
                    break;
            }
        }
        if (mIsDraging) {
            return true;
        }
        return super.onTouchEvent(ev);
    }
}
