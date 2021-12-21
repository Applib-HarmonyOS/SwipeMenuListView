package com.baoyz.swipemenulistview.demo;

import com.baoyz.swipemenulistview.demo.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.bundle.IBundleManager;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {

        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
    }

}
