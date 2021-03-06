Material Design
=====================================
- FloatingActionButton //浮动操作按钮
- NavigationView //侧边导航栏
- Snackbar //提示条，类似toast
- TextInputLayout // 主要作为EditText的容器
- Palette //调色板
- CardView //卡片视图
- AppBarLayout //配合Toolbar，跟随滚动
- CollapsingToolbarLayout //配合Toolbar，可伸缩的Toolbar布局
- NestedScrollView
- NavigationView // 侧边导航抽屉
- CoordinatorLayout //协调(Coordinate)其他组件, 实现联动

More 待完成
=====================================   
- BottomSheetDialog // 底部抽屉dialog
- BottomNavigationView // 底部导航tab

### Bottom Sheets
底部操作控件，用于在屏幕底部创建一个可滑动关闭的视图，可以替代对话框和菜单。其中包含BottomSheets、BottomSheetDialog和BottomSheetDialogFragment三种可以使用。

#### 1. BottomSheets
BottomSheets其实也是依赖于Behavior机制的使用。
先引用依赖,最新的design包已经到24了

	compile 'com.android.support:design:24.2.1'
	
	//三个属性
	app:behavior_hideable="true"
	app:behavior_peekHeight="0dp"
	app:layout_behavior="@string/bottom_sheet_behavior"
	
> peekHeight，是当Bottom Sheets关闭的时候，底部我们能看到的高度,默认是0不可见。  
> hideable，是当我们拖拽下拉的时候，bottom sheet是否能全部隐藏。  
> layout\_behavior指向bottom\_sheet_behavior,代表这是一个bottom Sheets

setBottomSheetCallback可以监听回调的状态,  
onStateChanged监听状态的改变,  
onSlide是拖拽的回调,  
onStateChanged可以监听到的回调一共有5种:

* STATE\_HIDDEN: 隐藏状态。默认是false，可通过app:behavior_hideable属性设置。
* STATE\_COLLAPSED: 折叠关闭状态。可通过app:behavior_peekHeight来设置显示的高度,peekHeight默认是0。
* STATE_DRAGGING: 被拖拽状态
* STATE_SETTLING: 拖拽松开之后到达终点位置（collapsed or expanded）前的状态。
* STATE_EXPANDED: 完全展开的状态。

BottomSheets控件配合NestedScrollView、RecyclerView使用效果会更好,合理的使用让APP逼格满满。

#### 2. BottomSheetDialog(实用)
BottomSheetDialog应该是最实用的控件,也是使用率非常高的控件。它可以替代大多数网格显示和列表展示的dialog和popupwindow，默认宽度撑满，并且在BottomSheetDialog 区域中向下滑动也让对话框消失。

* 首先写一个dialog展示的xml，dialog中xml可以不用被CoordinatorLayout包裹，但是还是推荐实用推荐的滑动控件NestedScrollView
* 自定义布局显示

		BottomSheetDialog dialog = new BottomSheetDialog(this);
		View view = getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
		dialog.setContentView(view);
		dialog.getWindow().setDimAmount(0f);//去除遮罩
		dialog.show();



### Snackbar
[Snackbar详解](https://blog.csdn.net/qq_19431333/article/details/52862348)

### CardView
1. cardElevation:设置阴影的大小
2. cardBackgroundColor:卡片布局的背景演示
3. cardCornerRadius：卡片布局的圆角的大小
4. conentPadding：卡片布局和内容之间的距离
5. android:clickable="true"
Android:foreground="?android:attr/selectableItemBackground"
设置点击的水波纹效果

### Palette 调色板
**Palette可以提取的颜色如下**:   
Vibrant （有活力的）  
Vibrant dark（有活力的 暗色）  
Vibrant light（有活力的 亮色）  
Muted （柔和的）  
Muted dark（柔和的 暗色）  
Muted light（柔和的 亮色）  

**使用方法**  
我们要想使用Palette，需要导入Palette的兼容库，Gradle 中添加下面依赖。  
compile 'com.android.support:palette-v7:21.0.0'  
	
* 第一步，我们需要通过一个Bitmap对象来生成一个对应的Palette对象。 Palette 提供了四个静态方法用来生成对象。
	
	```
	Palette generate(Bitmap bitmap)
	Palette generate(Bitmap bitmap, int numColors)
	generateAsync(Bitmap bitmap, PaletteAsyncListener listener)
	generateAsync(Bitmap bitmap, int numColors, final PaletteAsyncListener listener)
	```
不难看出，生成方法分为generate(同步)和generateAsync(异步)两种，如果图片过大使用generate方法，可能会阻塞主线程，我们更倾向于使用generateAsync的方法，其实内部就是创建了一个AsyncTask。generateAsync方法需要一个PaletteAsyncListener对象用于监听生成完毕的回调。除了必须的Bitmap参数外，还可以传入一个numColors参数指定颜色数，默认是 16。
* 第二步，得到Palette对象后，就可以拿到提取到的颜色值

	```
	Palette.getVibrantSwatch()   
	Palette.getDarkVibrantSwatch()   
	Palette.getLightVibrantSwatch()   
	Palette.getMutedSwatch()   
	Palette.getDarkMutedSwatch()   
	Palette.getLightMutedSwatch()   
	```
* 第三步，使用颜色，上面get方法中返回的是一个 Swatch 样本对象，这个样本对象是Palette的一个内部类，它提供了一些获取最终颜色的方法。
	
	```
	getPopulation(): 样本中的像素数量   
	getRgb(): 颜色的RBG值   
	getHsl(): 颜色的HSL值   
	getBodyTextColor(): 主体文字的颜色值   
	getTitleTextColor(): 标题文字的颜色值
	```
通过 getRgb() 可以得到最终的颜色值并应用到UI中。getBodyTextColor() 和 getTitleTextColor() 可以得到此颜色下文字适合的颜色，这样很方便我们设置文字的颜色，使文字看起来更加舒服。

**实例代码**

	```
	// 此方法可能会阻塞主线程，建议使用异步方法
	Palette palette = Palette.generate(bitmap);   
	// 异步提取Bitmap颜色
	Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
	    @Override
	    public void onGenerated(Palette palette) {
	        // 提取完毕
	        // 有活力的颜色
	        Palette.Swatch vibrant = palette.getVibrantSwatch();    
	        // 有活力的暗色
	        Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();    
	        // 有活力的亮色
	        Palette.Swatch lightVibrant = palette.getLightVibrantSwatch();    
	        // 柔和的颜色
	        Palette.Swatch muted = palette.getMutedSwatch();    
	        // 柔和的暗色
	        Palette.Swatch darkMuted = palette.getDarkMutedSwatch();    
	        // 柔和的亮色
	        Palette.Swatch lightMuted = palette.getLightMutedSwatch();
	        // 使用颜色
	        // 修改Actionbar背景颜色
	        getActionBar().setBackgroundDrawable(new ColorDrawable(vibrant.getRgb()));
	        // 修改文字的颜色
	        mTextView.setTextColor(vibrant.getTitleTextColor());
	        ...
	        // 根据需求选择不同效果的颜色应用
	    });
	```

### AppBarLayout
**app:layout_scrollFlags**

AppBarLayout的子布局有5种滚动标识(就是上面代码CollapsingToolbarLayout中配置的app:layout_scrollFlags属性)：

* scroll:将此布局和滚动时间关联。这个标识要设置在其他标识之前，没有这个标识则布局不会滚动且其他标识设置无效。
* enterAlways:任何向下滚动操作都会使此布局可见。这个标识通常被称为“快速返回”模式。
* enterAlwaysCollapsed：假设你定义了一个最小高度（minHeight）同时enterAlways也定义了，那么view将在到达这个最小高度的时候开始显示，并且从这个时候开始慢慢展开，当滚动到顶部的时候展开完。
* exitUntilCollapsed：当你定义了一个minHeight，此布局将在滚动到达这个最小高度的时候折叠。
* snap:当一个滚动事件结束，如果视图是部分可见的，那么它将被滚动到收缩或展开。例如，如果视图只有底部25%显示，它将折叠。相反，如果它的底部75%可见，那么它将完全展开。

### CollapsingToolbarLayout 可伸缩折叠的Toolbar
CollapsingToolbarLayout属性  
可以通过app:contentScrim设置折叠时工具栏布局的颜色，  
通过app:statusBarScrim设置折叠时状态栏的颜色。  
默认contentScrim是colorPrimary的色值，  
statusBarScrim是colorPrimaryDark的色值。  

CollapsingToolbarLayout的子布局有3种折叠模式（Toolbar中设置的app:layout_collapseMode）  

* none：这个是默认属性，布局将正常显示，没有折叠的行为。
* pin：CollapsingToolbarLayout折叠后，此布局将固定在顶部。
* parallax：CollapsingToolbarLayout折叠时，此布局也会有视差折叠效果。
当CollapsingToolbarLayout的子布局设置了parallax模式时，我们还可以通过app:layout_collapseParallaxMultiplier设置视差滚动因子，值为：0~1。

### CoordinatorLayout
协调(Coordinate)其他组件, 实现联动。  
通常和AppBarLayout一起使用。



@see  https://github.com/Bakumon/UGank.git
------------------------------------------


![](http://ww1.sinaimg.cn/mw690/628a632agy1fhj7ktx3slj20k00zkdjt.jpg)


Pre-requisites
--------------
    
- Android SDK v22
- Android Build Tools v22.0.1
- Android Support Repository r16 (for v22.2.1)

License
-------

Copyright 2014 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
