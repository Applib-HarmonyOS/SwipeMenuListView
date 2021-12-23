[![Build](https://github.com/applibgroup/SwipeMenuListView/actions/workflows/main.yml/badge.svg)](https://github.com/applibgroup/SwipeMenuListView/actions/workflows/main.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=applibgroup_SwipeMenuListView&metric=alert_status)](https://sonarcloud.io/dashboard?id=applibgroup_SwipeMenuListView)

# SwipeMenuListView

## Introduction:
A swipe menu for ListView.

# Source
This library has been inspired by [baoyongzhang//SwipeMenuListView](https://github.com/baoyongzhang/SwipeMenuListView).


## Usage
<image src = "/screenshot/swipemenu.gif" width = 320 height = 400/>

The usage is pretty straightforward. Add the tag into the XML layout:
```xml
<com.baoyz.swipemenulistview.SwipeMenuListView
        ohos:id="$+id:list_container"
        ohos:width="match_parent"
        ohos:height="match_content"/>

```

 Then use this snippet to populate it with contents:
```java
     SwipeMenu menu = new SwipeMenu(getContext());
     SwipeMenuItem openItem = new SwipeMenuItem(
                         getApplicationContext());
                 ShapeElement openItemShapeElement = new ShapeElement();
                 openItemShapeElement.setRgbColor(new RgbColor(201, 201, 206));
                 openItem.setBackground1(openItemShapeElement);
                 openItem.setMenuTitle("open");
                 openItem.setWidth(250);
                 openItem.setTitle("Application "+ i);
                 openItem.setTitleSize(60);
                 openItem.setIcon2(ResourceTable.Media_ic_delete);
                 openItem.setTitleColor(Color.BLACK);
                 menu.addMenuItem(openItem);

    SwipeMenuListView listContainer = (SwipeMenuListView) findComponentById(ResourceTable.Id_list_container);
    swipeItemProvider = new SwipeMenuAdapter(this, menu);
    swipeItemProvider.getCount();
    listContainer.setItemProvider(swipeItemProvider);
                      
```
## Installation instructions:

```
Method 1:
    Generate the .har package through the library and add the .har package to the libs folder.
    Add the following code to the entry gradle:
        implementation fileTree  (dir: 'libs', include: ['*.jar', '*.har'])

Method 2:
    allprojects{
        repositories{
            mavenCentral()
        }
    }
implementation 'dev.applibgroup:swipemenulistview:1.0.0'
```
## License

    Copyright (C) 2015 Angelo Marchesin.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
