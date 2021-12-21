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

public class DifferentAbilitySlice extends AbilitySlice implements Component.ScrolledListener {
    private final int LIST_ITEM_COUNT = 20;

    private SwipeMenuListView listContainer;

    private SwipeMenuAdapter swipeItemProvider;

    private ArrayList<String> contentList;

    private SwipeMenuLayout mainSwipeMenuLayout;

    private SwipeMenu menu = new SwipeMenu(getContext());



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
        for (int i = 0; i<= 20; i++) {
            SwipeMenuItem item1 = new SwipeMenuItem(
                    getApplicationContext());
            ShapeElement item1ShapeElement1 = new ShapeElement();
            item1ShapeElement1.setRgbColor(new RgbColor(0xE5, 0x18,
                    0x5E));
            item1.setBackground1(item1ShapeElement1);
            ShapeElement item1ShapeElement2 = new ShapeElement();
            item1ShapeElement2.setRgbColor(new RgbColor(0xC9, 0xC9,
                    0xCE));
            item1.setBackground2(item1ShapeElement2);
            item1.setMenuTitle("open");
            item1.setWidth(300);
            item1.setTitle("Application "+ i);
            item1.setTitleSize(60);
            item1.setIcon1(ResourceTable.Media_ic_action_favorite);
            item1.setIcon2(ResourceTable.Media_ic_action_good);
            item1.setTitleColor(Color.BLACK);
            menu.addMenuItem(item1);

            i++;

            SwipeMenuItem item2 = new SwipeMenuItem(
                    getApplicationContext());
            ShapeElement item2ShapeElement1 = new ShapeElement();
            item2ShapeElement1.setRgbColor(new RgbColor(0xE5, 0xE0,
                    0x3F));
            item2.setBackground1(item2ShapeElement1);
            ShapeElement item2ShapeElement2 = new ShapeElement();
            item2ShapeElement2.setRgbColor(new RgbColor(0xF9,
                    0x3F, 0x25));
            item2.setBackground2(item2ShapeElement2);
            item2.setMenuTitle("open");
            item2.setWidth(250);
            item2.setTitle("Application "+ i);
            item2.setTitleSize(60);
            item2.setIcon1(ResourceTable.Media_ic_action_important);
            item2.setIcon2(ResourceTable.Media_ic_action_discard);
            item2.setTitleColor(Color.BLACK);
            menu.addMenuItem(item2);
            i++;

            SwipeMenuItem item3 = new SwipeMenuItem(
                    getApplicationContext());
            ShapeElement item3ShapeElement1 = new ShapeElement();
            item3ShapeElement1.setRgbColor(new RgbColor(0x30, 0xB1,
                    0xF5));
            item3.setBackground1(item3ShapeElement1);
            ShapeElement item3ShapeElement2 = new ShapeElement();
            item3ShapeElement2.setRgbColor(new RgbColor(0xC9, 0xC9,
                    0xCE));
            item3.setBackground2(item3ShapeElement2);
            item3.setMenuTitle("open");
            item3.setWidth(300);
            item3.setTitle("Application "+ i);
            item3.setTitleSize(60);
            item3.setIcon1(ResourceTable.Media_ic_action_about);
            item3.setIcon2(ResourceTable.Media_ic_action_share);
            item3.setTitleColor(Color.BLACK);
            menu.addMenuItem(item3);
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
