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
package utils;

import ohos.agp.components.AttrSet;

public class AttrUtils {
    // Get int value from Attributes
    public static Integer getIntFromAttr(AttrSet attrs, String name, Integer defaultValue) {
        Integer value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent()) {
                value = attrs.getAttr(name).get().getIntegerValue();
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return value;
    }

    // Get float value from Attributes
    public static float getFloatFromAttr(AttrSet attrs, String name, float defaultValue) {
        float value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent()) {
                value = attrs.getAttr(name).get().getFloatValue();
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return value;
    }

    // Get int boolean from Attributes
    public static boolean getBooleanFromAttr(AttrSet attrs, String name, boolean defaultValue) {
        boolean value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getBoolValue();
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return value;
    }
}