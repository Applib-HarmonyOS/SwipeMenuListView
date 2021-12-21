/* * Copyright (C) 2021 Huawei Device Co., Ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baoyz.swipemenulistview.demo.slice;

import com.baoyz.swipemenulistview.*;
import com.baoyz.swipemenulistview.demo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.colors.RgbPalette;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.ElementScatter;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.components.element.VectorElement;
import ohos.agp.utils.Color;

import java.util.ArrayList;

public class MainAbilitySlice extends AbilitySlice {


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button simpleDemo = (Button)findComponentById(ResourceTable.Id_simpledemo);
        simpleDemo.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent1 = new Intent();
                SimpleAbilitySlice abilitySlice = new SimpleAbilitySlice();
                present(abilitySlice,intent1);

            }
        });

        Button differentdemo = (Button)findComponentById(ResourceTable.Id_differentdemo);
        differentdemo.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent1 = new Intent();
                DifferentAbilitySlice abilitySlice = new DifferentAbilitySlice();
                present(abilitySlice,intent1);
            }
        });

    }


}