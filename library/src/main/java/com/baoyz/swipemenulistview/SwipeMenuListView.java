package com.baoyz.swipemenulistview;

import ohos.agp.animation.Animator;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.AttrSet;
import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.element.ShapeElement;
import ohos.app.Context;
import ohos.multimodalinput.event.TouchEvent;

import java.util.List;

/**
 * @author baoyz
 * @date 2014-8-18
 */
public class SwipeMenuListView extends ListContainer implements Component.TouchEventListener {

    private static final int TOUCH_STATE_NONE = 0;
    private static final int TOUCH_STATE_X = 1;
    private static final int TOUCH_STATE_Y = 2;

    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = -1;
    private int mDirection = 1;//swipe from right to left by default

    private int MAX_Y = 5;
    private int MAX_X = 3;
    private float mDownX;
    private float mDownY;
    private int mTouchState;
    private int mTouchPosition;
    private Context mContext;
    private SwipeMenuLayout mTouchView;
    private OnSwipeListener mOnSwipeListener;

    private SwipeMenuCreator mMenuCreator;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private OnMenuStateChangeListener mOnMenuStateChangeListener;
    private Animator.CurveType mCloseInterpolator;
    private Animator.CurveType mOpenInterpolator;
    private List<String> itemsList;
    SwipeMenu menu;

    public SwipeMenuListView(Context context) {
        super(context);
        mContext = context;
    }

    public SwipeMenuListView(Context context, AttrSet attrs, int defStyle) {
        super(context, attrs, "");
        mContext = context;
    }

    public SwipeMenuListView(Context context, AttrSet attrs) {
        super(context, attrs);
        mContext = context;
    }


    @Override
    public void setItemProvider(BaseItemProvider itemProvider) {
        super.setItemProvider(itemProvider);
    }

    public void createMenu(SwipeMenu menu) {
        this.menu = menu;
        // Test Code
        SwipeMenuItem item = new SwipeMenuItem(mContext);
        item.setTitle("Item 1");
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setRgbColor(new RgbColor(128,128,128));
        item.setBackground1(shapeElement);
        item.setWidth(300);
        menu.addMenuItem(item);

        item = new SwipeMenuItem(mContext);
        item.setTitle("Item 2");
        ShapeElement shapeElementRed = new ShapeElement();
        shapeElementRed.setRgbColor(new RgbColor(255,0,0));
        item.setBackground1(shapeElementRed);
        item.setWidth(300);
        menu.addMenuItem(item);


    }

    public void setMenuCreator(SwipeMenuCreator menuCreator) {
        this.mMenuCreator = menuCreator;
    }

    @Override
    public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
        return false;
    }

    public void setOnMenuItemClickListener(
            OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.mOnSwipeListener = onSwipeListener;
    }

    public void setOnMenuStateChangeListener(OnMenuStateChangeListener onMenuStateChangeListener) {
        mOnMenuStateChangeListener = onMenuStateChangeListener;
    }

    public static interface OnMenuItemClickListener {
        boolean onMenuItemClick(int position, SwipeMenu menu, int index);
    }

    public static interface OnSwipeListener {
        void onSwipeStart(int position);

        void onSwipeEnd(int position);
    }

    public static interface OnMenuStateChangeListener {
        void onMenuOpen(int position);

        void onMenuClose(int position);
    }

    public void setSwipeDirection(int direction) {
        mDirection = direction;
    }

    /**
     * 判断点击事件是否在某个view内
     *
     * @param view
     * @param ev
     * @return
     */
    public static boolean inRangeOfView(Component view, TouchEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen();
        int x = location[0];
        int y = location[1];
        if (ev.getPointerPosition(0).getX() < x || ev.getPointerPosition(0).getX() > (x + view.getWidth()) || ev.getPointerPosition(0).getY() < y || ev.getPointerPosition(0).getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }
}
