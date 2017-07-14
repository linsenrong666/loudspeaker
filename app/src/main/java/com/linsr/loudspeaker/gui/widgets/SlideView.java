package com.linsr.loudspeaker.gui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Description
 *
 * @author linsenrong on 2017/7/13 17:09
 */

public class SlideView extends ViewGroup {
    /**
     * 整个ViewGroup 由一个menu， 一个content组成，都是一个简单的listView。
     */
    //屏幕宽度
    private int mScreenWidth;
    //屏幕高度
    private int mScreenHeight;
    //menu的宽度，左侧的菜单
    private int mMenuWidth;
    //左侧的menu
    private ViewGroup mMenu;
    //屏幕中默认显示的content
    private ViewGroup mContent;
    //上一次手指接触屏幕的X坐标
    private float mLastX;
    //上一次手指接触屏幕的Y坐标
    private float mLastY;
    //当手指点击屏幕的时候的X坐标，
    private float mDownX;
    //当menu完全显示的时候右侧content的显示的宽度
    private int mMenuMargin = 80;
    //帮助实现View滑动的类
    private Scroller mScroller;
    //当事件分发到ViewGroup的dispatchTouchEvent(MotionEvent ev)的时候，
    //因为onInterceptTouchEvent是在dispatchTouchEvent中被调用的
    //上次手指所处屏幕的X的坐标
    private int mLastInterceptX = 0;
    //上次手指所处屏幕的Y的坐标
    private int mLastInterceptY = 0;

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        mMenuMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mMenuMargin, context.getResources().getDisplayMetrics());
        mMenuWidth = mScreenWidth - mMenuMargin;
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMenu = (ViewGroup) getChildAt(0);
        mMenu.getLayoutParams().width = (int) mMenuWidth;
        mContent = (ViewGroup) getChildAt(1);
        mContent.getLayoutParams().width = (int) mScreenWidth;
        measureChild(mMenu, widthMeasureSpec, heightMeasureSpec);
        measureChild(mContent, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mMenuWidth + mScreenWidth, mScreenHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            //android的机制，View在显示到屏幕的时候，会进行最少2次的OnMeasure，OnLayout...
            //所以这里只在第一次的时候进行ViewGroup在手机屏幕中显示的位置的设定
            mMenu.layout(-mMenuWidth, 0, 0, mScreenHeight);
            mContent.layout(0, 0, mScreenWidth, mScreenHeight);
        }
    }

    //通过重写ViewGroup的事件拦截方法来解决ViewGroup与ListView的滑动冲突
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return super.onInterceptTouchEvent(ev);
        boolean intercept = false;
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercept = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                //判断滑动的时候，横向的距离与纵向的距离只差
                //如果横向的距离大于纵向的距离，那就拦截，ViewGroup滑动（消费点击事件）；
                //如果横向的距离小于纵向的距离，那就不拦截，ListView滑动（消费点击事件）；
                int deltaX = (int) ev.getRawX() - mLastInterceptX;
                int deltaY = (int) ev.getRawY() - mLastInterceptY;
                Log.d("deltaX", deltaX + "");
                Log.d("deltaY", deltaY + "");
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;

            case MotionEvent.ACTION_UP:
                intercept = false;
                break;

        }
        mLastX = x;
        mLastY = y;
        mLastInterceptX = x;
        mLastInterceptY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getRawX();
                mLastX = mDownX;
                break;
            case MotionEvent.ACTION_MOVE:
                float mCurrentX = event.getRawX();
                float mCurrentY = event.getRawY();
                //获取滑动时上一个点到现在手指所处的点的距离
                float scrolledX = mLastX - mCurrentX;

                //边界值判断（这里最好是自己手动画图，便于理解）
                if (-(getScrollX() + scrolledX) > mMenuWidth) {
                    scrollTo(-mMenuWidth, 0);
                    return true;
                } else if (getScrollX() + scrolledX > 0) {
                    scrollTo(0, 0);
                    return true;
                }

                scrollBy((int) scrolledX, 0);
                mLastX = mCurrentX;
                mLastY = mCurrentY;
                break;
            case MotionEvent.ACTION_UP:
                //当手指离开屏幕的时候，判断滑动的距离是否大于Menu宽度的1/2
                //如果大于则显示menu，如果小于则不显示
                if (-getScrollX() >= mMenuWidth / 2) {
                    mScroller.startScroll(getScrollX(), 0,
                            -(mMenuWidth + getScrollX()), 0);
                    invalidate();
                } else {
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);
                    invalidate();
                }
                break;

        }
        return super.onTouchEvent(event);
    }

    //这里是实现View缓慢滑动所要重写的方法
    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

}