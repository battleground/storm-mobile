package com.baofeng.mobile.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可阻止滑动切屏
 */
public class NonSwipebleViewPager extends ViewPager {

    private boolean mBlockSwipe;

    public NonSwipebleViewPager(Context context) {
        super(context);
    }

    public NonSwipebleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 是否阻止滑动切屏
     * @return
     */
    public boolean isBlockSwipe() {
        return mBlockSwipe;
    }

    /**
     * 设置是否阻止滑动切屏
     * @param blockSwipe true阻止滑动切屏，false不阻止，即普通模式(默认false)
     */
    public void setBlockSwipe(boolean blockSwipe) {
        this.mBlockSwipe = blockSwipe;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mBlockSwipe)
            return false;
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mBlockSwipe)
            return false;
        return super.onTouchEvent(event);
    }
}