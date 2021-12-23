package com.baoyz.swipemenulistview;


import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.ScrollHelper;
import ohos.agp.render.Canvas;
import ohos.app.Context;
import ohos.multimodalinput.event.TouchEvent;
import utils.AttrUtils;
import utils.PointF;
import utils.State;

import static utils.State.CLOSE;

public class SwipeMenuLayout extends ComponentContainer implements Component.TouchEventListener,
        Component.BindStateChangedListener, Component.DrawTask, ComponentContainer.ArrangeListener, Component.ScrolledListener {

    private final String TAG = SwipeMenuLayout.class.getSimpleName();

    private SwipeMenuLayout mViewCache;
    private static SwipeMenuLayout viewCache;
    private State mStateCache;

    private final int XY_ARRAY_LENGTH = 2;
    private final int Y_INDEX = 1;
    private final int X_INDEX = 0;
    private final int VIEW_CONFIGURATION_TOUCH_SLOP = 24;

    private int mLeftViewResID;
    private int mRightViewResID;
    private int mContentViewResID;
    private boolean mCanRightSwipe;
    private boolean mCanLeftSwipe;
    private float mFraction = 0.3f;
    private int mScaledTouchSlop;
    private Component mLeftView;
    private Component mRightView;
    private Component mContentView;
    private LayoutConfig mContentViewLp;
    private boolean isSwipeing;
    private PointF mLastP;
    private PointF mFirstP;
    private ScrollHelper mScroller;
    private float distanceX;
    private float finalyDistanceX;

    private int mLeftViewLen;
    private int mRightViewLen;
    private int mContentViewLen;
    private int mContentViewLenPre;

    public SwipeMenuLayout(Context context) {
        this(context, null);
    }

    public SwipeMenuLayout(Context context, AttrSet attrSet) {
        this(context, attrSet, null);
    }

    public SwipeMenuLayout(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);

        init(attrSet);
    }

    /**
     * 初始化方法
     *
     * @param attrs passed xml values
     */
    private void init(AttrSet attrs) {
        //创建辅助对象
        /**
         * ---------------------------------------------------------------------------------------------------------
         * Distance a touch can wander before we think the user is scrolling in dips.
         * Note that this value defined here is only used as a fallback by legacy/misbehaving
         * applications that do not provide a Context for determining density/configuration-dependent
         * values.
         *
         * To alter this value, see the configuration resource config_viewConfigurationTouchSlop
         * in frameworks/base/core/res/res/values/config.xml or the appropriate device resource overlay.
         * It may be appropriate to tweak this on a device-specific basis in an overlay based on
         * the characteristics of the touch panel and firmware.
         * ---------------------------------------------------------------------------------------------------------
         * For LIO-AL00(Mate 30 Pro) we are getting this value 24. Depends on device this value could get changed.
         * */
        mScaledTouchSlop = VIEW_CONFIGURATION_TOUCH_SLOP;

        mScroller = new ScrollHelper();

        // Attr style params
        final String leftMenuView = "leftMenuView";
        final String rightMenuView = "rightMenuView";
        final String contentView = "contentView";
        final String canRightSwipe = "canRightSwipe";
        final String canLeftSwipe = "canLeftSwipe";
        final String fraction = "fraction";

        mLeftViewResID = AttrUtils.getIntFromAttr(attrs, leftMenuView, null);
        mRightViewResID = AttrUtils.getIntFromAttr(attrs, rightMenuView, null);
        mContentViewResID = AttrUtils.getIntFromAttr(attrs, contentView, null);
        mCanRightSwipe = AttrUtils.getBooleanFromAttr(attrs, canRightSwipe, false);
        mCanLeftSwipe = AttrUtils.getBooleanFromAttr(attrs, canLeftSwipe, false);
        mFraction = AttrUtils.getFloatFromAttr(attrs, fraction, 0.5f);

        setTouchEventListener(this);
        addDrawTask(this);
        setBindStateChangedListener(this);
        setArrangeListener(this);
        setScrolledListener(this);
    }

    // postLayout was not getting called so implemented onLayout and calling it manually from onMeasure
    public void onLayout() {
        int count = getChildCount();
        int left = 0 + getPaddingLeft();
        int top = 0 + getPaddingTop();

        setComponentIds(count);

        mContentViewLp = mContentView.getLayoutConfig();

        //布局contentView
        int cRight;
        if (mContentView != null) {
            mContentViewLp = (LayoutConfig) mContentView.getLayoutConfig();
            int cTop = top + mContentViewLp.getMarginTop();
            int cLeft = left + mContentViewLp.getMarginLeft();
            cRight = left + mContentViewLp.getMarginLeft() + mContentViewLen;
            int cBottom = cTop + mContentView.getHeight();
            mContentView.setComponentPosition(cLeft, cTop, cRight, cBottom);

            mContentViewLenPre = mContentView.getWidth();
        }
        if (mLeftView != null) {
            LayoutConfig leftViewLp = (LayoutConfig) mLeftView.getLayoutConfig();
            int lTop = top + leftViewLp.getMarginTop();
            int lLeft = 0 - mLeftViewLen + leftViewLp.getMarginLeft() + leftViewLp.getMarginRight();
            int lRight = 0 - leftViewLp.getMarginRight();
            int lBottom = lTop + mLeftView.getHeight();
            mLeftView.setComponentPosition(lLeft, lTop, lRight, lBottom);
        }
        if (mRightView != null) {
            LayoutConfig rightViewLp = (LayoutConfig) mRightView.getLayoutConfig();
            int lTop = top + rightViewLp.getMarginTop();
            int lLeft = mContentView.getRight() + mContentViewLp.getMarginRight() + rightViewLp.getMarginLeft();
            int lRight = lLeft + mRightViewLen;
            int lBottom = lTop + mRightView.getHeight();
            mRightView.setComponentPosition(lLeft, lTop, lRight, lBottom);
        }

        if (mStateCache == State.LEFTOPEN && mCanLeftSwipe) {
            if (mLeftView != null) {
                mLeftView.setComponentPosition(0, 0, mLeftViewLen, 0);
            }
            mContentView.setComponentPosition(mLeftViewLen, 0,
                    mContentViewLen + mLeftViewLen, 0);
            if (mRightView != null) {
                mRightView.setComponentPosition(mContentViewLen + mLeftViewLen, 0,
                        mContentViewLen + mLeftViewLen + mRightViewLen, 0);
            }
        } else if (mStateCache == State.RIGHTOPEN && mCanRightSwipe) {
            if (mLeftView != null) {
                mLeftView.setComponentPosition(
                        -mLeftViewLen - (mContentViewLen - mRightViewLen), 0, 0, 0);
            }
            mContentView.setComponentPosition(-mRightViewLen, 0,
                    mContentViewLen - mRightViewLen, 0);
            if (mRightView != null) {
                mRightView.setComponentPosition(mContentViewLen - mRightViewLen, 0, mContentViewLen, 0);
            }
        }
    }

    private void setComponentIds(int count) {
        for (int i = 0; i < count; i++) {
            Component child = getComponentAt(i);
            settingComponentsId(child);
        }
        if(mContentViewLenPre > 0) {
            if (mContentView != null) {
                mContentViewLen = mContentViewLenPre;
            }
        } else {
            if (mContentView != null) {
                mContentViewLen = mContentView.getWidth();
            }
        }
        if (mLeftView != null) {
            mLeftViewLen = mLeftView.getWidth();
        }
        if (mRightView != null) {
            mRightViewLen = mRightView.getWidth();
        }
    }

    private void settingComponentsId(Component child) {
        if (mLeftView == null && child.getId() == mLeftViewResID) {
            mLeftView = child;
            mLeftView.setClickable(true);
            mLeftView.getComponentPosition();
        } else if (mRightView == null && child.getId() == mRightViewResID) {
            mRightView = child;
            mRightView.setClickable(true);
        } else if (mContentView == null && child.getId() == mContentViewResID) {
            mContentView = child;
            mContentView.setClickable(true);
        }
    }

    @Override
    public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case TouchEvent.PRIMARY_POINT_DOWN: {
                touchDown(touchEvent);
                break;
            }
            case TouchEvent.POINT_MOVE: {
                touchUp(touchEvent);

                break;
            }
            case TouchEvent.PRIMARY_POINT_UP:
            case TouchEvent.CANCEL: {
                finalyDistanceX = mFirstP.x - getTouchX(touchEvent, 0);
                if (Math.abs(finalyDistanceX) > mScaledTouchSlop) {
                    isSwipeing = true;
                }
                State result = isShouldOpen();
                handlerSwipeMenu(result);

                break;
            }
            default: {
                break;
            }
        }

        onInterceptTouchEvent(touchEvent);
        return true;
    }

    private void touchDown(TouchEvent touchEvent){
        isSwipeing = false;
        if (mLastP == null) {
            mLastP = new PointF();
        }

        mLastP.set(getTouchX(touchEvent, 0), getTouchY(touchEvent, 0));
        if (mFirstP == null) {
            mFirstP = new PointF();
        }
        mFirstP.set(getTouchX(touchEvent, 0), getTouchY(touchEvent, 0));
        if (viewCache != null) {
            if (viewCache != this) {
                viewCache.handlerSwipeMenu(CLOSE);
            }
        }
    }

    private void touchUp(TouchEvent touchEvent){
        float distanceX = mLastP.x - getTouchX(touchEvent, 0);
        float distanceY = mLastP.y - getTouchY(touchEvent, 0);
        if (Math.abs(distanceY) > mScaledTouchSlop && Math.abs(distanceY) > Math.abs(distanceX)) {
            return;
        }

        scrollTo((int) (distanceX), 0);

        if (getScrollValue(Component.HORIZONTAL) > 0) {
            if (mRightView == null) {
                scrollTo(0, 0);
            } else {
                if (getScrollValue(Component.HORIZONTAL)
                        > mRightView.getRight() - mContentView.getRight() - mContentViewLp.getMarginRight()) {
                    scrollTo(mRightView.getRight() - mContentView.getRight() - mContentViewLp.getMarginRight(),
                            0);
                }
            }
        }
        mLastP.set(getTouchX(touchEvent, 0), getTouchY(touchEvent, 0));
    }

    public boolean onInterceptTouchEvent(TouchEvent event) {
        switch (event.getAction()) {
            case TouchEvent.PRIMARY_POINT_DOWN: {
                break;
            }
            case TouchEvent.POINT_MOVE: {
                if (Math.abs(finalyDistanceX) > mScaledTouchSlop) {
                    return true;
                }
                break;
            }
            case TouchEvent.PRIMARY_POINT_UP:
            case TouchEvent.CANCEL: {
                if (isSwipeing) {
                    isSwipeing = false;
                    finalyDistanceX = 0;
                    return true;
                }
            }
        }
        return false;
    }

    private void handlerSwipeMenu(State result) {
        if (result == null) {
            return;
        }
        if (result == State.LEFTOPEN) {
            int x = getScrollValue(AXIS_X);
            int dx = mLeftView.getLeft() - getScrollValue(AXIS_X);
            mScroller.startScroll(x, 0, dx, 0);
            mViewCache = this;
            mStateCache = result;
        } else if (result == State.RIGHTOPEN) {
            mViewCache = this;
            updateCache(mViewCache);
            mScroller.startScroll(getScrollValue(AXIS_X), 0,
                mRightView.getRight() - mContentView.getRight() - mContentViewLp.getMarginRight() - getScrollValue(
                    AXIS_X), 0);
            mStateCache = result;
        } else {
            mScroller.startScroll(getScrollValue(AXIS_X), 0, -getScrollValue(AXIS_X), 0);
            mViewCache = null;
            mStateCache = null;

        }
        invalidate();
    }

    private static void updateCache(SwipeMenuLayout mViewCache){
        viewCache = mViewCache;
    }


    private State isShouldOpen() {
        if (!(mScaledTouchSlop < Math.abs(finalyDistanceX))) {
            return mStateCache;
        }
        if (finalyDistanceX > 0) {
            if (mRightView != null) {
                if (Math.abs(mRightView.getWidth() * mFraction) - finalyDistanceX < Math.abs(getScrollValue(AXIS_X))) {
                    return State.RIGHTOPEN;
                }
            }
            if (getScrollValue(AXIS_X) < 0 && mLeftView != null) {
                return CLOSE;
            }
        }
        return CLOSE;
    }

    private State finalYDistanceXLessZero(){
        if (getScrollValue(AXIS_X) < 0 && mLeftView != null) {
            if (Math.abs(mLeftView.getWidth() * mFraction) < Math.abs(getScrollValue(AXIS_X))) {
                return State.LEFTOPEN;
            }
        }

        if (getScrollValue(AXIS_X) > 0 && mRightView != null) {
            return CLOSE;
        }
        return CLOSE;
    }

    private State finalYDistanceXGreaterZero(){
        if (mRightView != null) {
            if (Math.abs(mRightView.getWidth() * mFraction) - finalyDistanceX < Math.abs(getScrollValue(AXIS_X))) {
                return State.RIGHTOPEN;
            }
        }
        if (getScrollValue(AXIS_X) < 0 && mLeftView != null) {
            return CLOSE;
        }
        return CLOSE;
    }

    @Override
    public void onComponentBoundToWindow(Component component) {
        if (this == mViewCache) {
            mViewCache.handlerSwipeMenu(mStateCache);
        }
    }

    @Override
    public void onComponentUnboundFromWindow(Component component) {
        if (this == mViewCache) {
            mViewCache.handlerSwipeMenu(CLOSE);
        }
    }

    public void resetStatus() {
        if (mViewCache != null) {
            if (mStateCache != null && mStateCache != CLOSE && mScroller != null) {
                mScroller.startScroll(mViewCache.getScrollValue(AXIS_X), 0, -mViewCache.getScrollValue(AXIS_X), 0);
                mViewCache.invalidate();
                mViewCache = null;
                mStateCache = null;
            }
        }
    }

    public float getFraction() {
        return mFraction;
    }

    public void setFraction(float mFraction) {
        this.mFraction = mFraction;
    }

    public boolean isCanLeftSwipe() {
        return mCanLeftSwipe;
    }

    public void setCanLeftSwipe(boolean mCanLeftSwipe) {
        this.mCanLeftSwipe = mCanLeftSwipe;
    }

    public boolean isCanRightSwipe() {
        return mCanRightSwipe;
    }

    public void setCanRightSwipe(boolean mCanRightSwipe) {
        this.mCanRightSwipe = mCanRightSwipe;
    }

  /*  public SwipeMenuLayout getViewCache() {
        return mViewCache;
    }*/

    public State getStateCache() {
        return mStateCache;
    }

    private boolean isLeftToRight() {
        if (distanceX < 0) {
            return true;
        } else {
            return false;
        }
    }

    private float getTouchX(TouchEvent touchEvent, int index) {
        float x = 0;
        if (touchEvent.getPointerCount() > index) {
            int[] xy = getLocationOnScreen();
            if (xy != null && xy.length == XY_ARRAY_LENGTH) {
                x = touchEvent.getPointerScreenPosition(index).getX() - xy[X_INDEX];
            } else {
                x = touchEvent.getPointerPosition(index).getX();
            }
        }
        return x;
    }

    private float getTouchY(TouchEvent touchEvent, int index) {
        float y = 0;
        if (touchEvent.getPointerCount() > index) {
            int[] xy = getLocationOnScreen();
            if (xy != null && xy.length == XY_ARRAY_LENGTH) {
                y = touchEvent.getPointerScreenPosition(index).getY() - xy[Y_INDEX];
            } else {
                y = touchEvent.getPointerPosition(index).getY();
            }
        }
        return y;
    }

    protected void onMeasure() {
        setClickable(true);
        onLayout();
    }

    @Override
    public boolean onArrange(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        onMeasure();
    }

    @Override
    public void onContentScrolled(Component component, int i, int i1, int i2, int i3) {
        resetStatus();
    }
}