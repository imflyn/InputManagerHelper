博客地址：
[两行代码搞定Android输入法遮挡问题](http://www.jianshu.com/p/50c060edeaa8)

使用方法：
1. 使用监听布局大小改变的自定义Layout
```
InputManagerHelper inputManagerHelper = InputManagerHelper.attachToActivity(this);
inputManagerHelper.bindCustomLayout(layout, loginButton);
```
2. 使用任意布局
```
InputManagerHelper inputManagerHelper = InputManagerHelper.attachToActivity(this);
inputManagerHelper.bindLayout(layout, loginButton);
```
3. 使用ScrollView
```
InputManagerHelper inputManagerHelper = InputManagerHelper.attachToActivity(this);
inputManagerHelper.bindScrollView(scrollView);
```
4. 使用RecycleView
```
InputManagerHelper inputManagerHelper = InputManagerHelper.attachToActivity(this);
inputManagerHelper.bindRecycleView(recyclerView);
```
