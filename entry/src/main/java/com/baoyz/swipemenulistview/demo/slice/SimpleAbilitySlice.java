package com.baoyz.swipemenulistview.demo.slice;

import com.baoyz.swipemenulistview.*;
import com.baoyz.swipemenulistview.demo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Component;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;

import java.util.ArrayList;

public class SimpleAbilitySlice extends AbilitySlice implements Component.ScrolledListener {
    private final int LIST_ITEM_COUNT = 20;

    private SwipeMenuListView listContainer;

    private SwipeMenuAdapter swipeItemProvider;

    private ArrayList<String> contentList;

    private SwipeMenuLayout mainSwipeMenuLayout;

    private SwipeMenu menu = new SwipeMenu(getContext());

    private String APPLICATION = "Application ";



    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_list);

        initComponents();
        initContactData();
        initProvider();
    }

    private void initComponents() {
        listContainer = (SwipeMenuListView) findComponentById(ResourceTable.Id_list_container);
    }

    private void initProvider() {
        swipeItemProvider = new SwipeMenuAdapter(this, menu);
        swipeItemProvider.getCount();
        listContainer.setItemProvider(swipeItemProvider);
        listContainer.setReboundEffect(true);
    }

    private void initContactData() {
        for (int i = 0; i< 20; i++) {
            SwipeMenuItem openItem = new SwipeMenuItem(
                    getApplicationContext());
            ShapeElement openItemShapeElement = new ShapeElement();
            openItemShapeElement.setRgbColor(new RgbColor(201, 201, 206));
            openItem.setBackground1(openItemShapeElement);
            openItem.setMenuTitle("open");
            openItem.setWidth(250);
            openItem.setTitle(APPLICATION + i);
            openItem.setTitleSize(60);
            openItem.setIcon2(ResourceTable.Media_ic_delete);
            openItem.setTitleColor(Color.BLACK);
            menu.addMenuItem(openItem);
        }
    }


    @Override
    protected void onInactive() {
        super.onInactive();
        swipeItemProvider.reset();
    }

    @Override
    public void onContentScrolled(Component component, int i, int i1, int i2, int i3) {
        SwipeMenuLayout easy = (SwipeMenuLayout) component;
        if(easy != null) swipeItemProvider.reset();
    }
}
