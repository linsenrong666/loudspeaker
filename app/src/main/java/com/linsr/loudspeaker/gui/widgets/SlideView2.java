package com.linsr.loudspeaker.gui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Description
 *
 * @author linsenrong on 2017/7/13 17:25
 */

public class SlideView2 extends ViewGroup {

    private Scroller mScroller;
    private int mTouchSlop;
    private View mContent;
    private View mMenu;
    private int mMenuWidth;
    private int mContentWidth;

    private int leftBorder;
    private int rightBorder;
    private float mXDown, mXMove, mXLastMove;

    public SlideView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledPagingTouchSlop();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMenu = getChildAt(0);
        mContent = getChildAt(1);
        measureChild(mContent, widthMeasureSpec, heightMeasureSpec);
        measureChild(mMenu, widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        if (change) {
            mMenuWidth = mMenu.getMeasuredWidth();
            mContentWidth = mContent.getMeasuredWidth();

            mContent.layout(0, 0, mContentWidth, mContentWidth);
            mMenu.layout(mContentWidth, 0, mContentWidth + mMenuWidth, mMenuWidth);
            leftBorder = 0;
            rightBorder = mContentWidth + mMenuWidth;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrolledX = (int) (mXLastMove - mXMove);
                if (getScrollX() + scrolledX < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrolledX > rightBorder) {
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX() > mMenuWidth / 2) {
                    open();
                } else {
                    close();
                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void open() {
        mScroller.startScroll(getScrollX(), 0, mMenuWidth - getScrollX(), 0);
    }

    private void close() {
        mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public SlideView2(Context context) {
        this(context, null, 0);
    }

    public SlideView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
}
