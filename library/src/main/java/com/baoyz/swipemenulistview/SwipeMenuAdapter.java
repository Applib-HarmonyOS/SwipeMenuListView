package com.baoyz.swipemenulistview;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;

import java.util.List;

/**
 * 
 * @author baoyz
 * @date 2014-8-24
 * 
 */
public class SwipeMenuAdapter extends BaseItemProvider  {
    private Context mContext;
    private List<SwipeMenuItem> mSwipeMenuItems;
    private SwipeMenuLayout swipeMenuLayout;
    private final int TOAST_DURATION = 1000;

    public SwipeMenuAdapter(Context context, SwipeMenu menu) {
        mContext = context;
        mSwipeMenuItems = menu.getMenuItems();
    }


    @Override
    public int getCount() {
        return mSwipeMenuItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mSwipeMenuItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mSwipeMenuItems.get(i).getId();
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        Component container = LayoutScatter.getInstance(mContext).parse(ResourceTable.Layout_list_item,
                null, false);

        Text contentText = (Text) container.findComponentById(ResourceTable.Id_contentText);
        contentText.setText(mSwipeMenuItems.get(i).getTitle());
        contentText.setTextSize(mSwipeMenuItems.get(i).getTitleSize());
        contentText.setTextColor(mSwipeMenuItems.get(i).getTitleColor());

        SwipeMenuLayout easySwipeMenuLayout = (SwipeMenuLayout) container.findComponentById(
                ResourceTable.Id_es);
        swipeMenuLayout = easySwipeMenuLayout;

        DirectionalLayout contentD = (DirectionalLayout) container.findComponentById(ResourceTable.Id_content);
        Text rightFirstText = (Text) container.findComponentById(ResourceTable.Id_right_menu);
        rightFirstText.setTextAlignment(TextAlignment.CENTER);
        rightFirstText.setText(mSwipeMenuItems.get(i).getMenuTitle());
        if (mSwipeMenuItems.get(i).getBackground1() != null) {
            rightFirstText.setBackground(mSwipeMenuItems.get(i).getBackground1());
        }
        rightFirstText.setWidth(mSwipeMenuItems.get(i).getWidth());

        Image rightFirstImage = (Image) container.findComponentById(ResourceTable.Id_right_menu_image);
        if (mSwipeMenuItems.get(i).getIcon1() != 0){
            rightFirstImage.setVisibility(Component.VISIBLE);
            rightFirstText.setVisibility(Component.HIDE);
            rightFirstImage.setBackground(mSwipeMenuItems.get(i).getBackground1());
            rightFirstImage.setImageAndDecodeBounds(mSwipeMenuItems.get(i).getIcon1());
            rightFirstImage.setWidth(mSwipeMenuItems.get(i).getWidth());
        }


        Image rightSecondText = (Image) container.findComponentById(ResourceTable.Id_right_menu_2);
        if (mSwipeMenuItems.get(i).getBackground2() != null) {
            rightSecondText.setBackground(mSwipeMenuItems.get(i).getBackground2());
        }
        rightSecondText.setImageAndDecodeBounds(mSwipeMenuItems.get(i).getIcon2());
        rightSecondText.setWidth(mSwipeMenuItems.get(i).getWidth());

        contentD.setClickedListener(
                componentClick -> swipeClicked(contentText.getText(), i, easySwipeMenuLayout));
        rightFirstText.setClickedListener(componentRight -> swipeClicked("Right first", i,
                easySwipeMenuLayout));
        rightSecondText.setClickedListener(componentRight2 -> swipeClicked("Right second", i,
                easySwipeMenuLayout));

        return container;
    }

    private void swipeClicked(String content, int position, SwipeMenuLayout easySwipeMenuLayout) {
        ToastDialog toast = new ToastDialog(mContext);
        toast.setText(position + " click " + content);
        toast.setDuration(TOAST_DURATION);
        toast.show();

        easySwipeMenuLayout.resetStatus();
    }
    public void reset(){
        if(swipeMenuLayout != null) {
            swipeMenuLayout.resetStatus();
        }
    }
}
