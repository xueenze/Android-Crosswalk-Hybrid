### Android-Crosswalk

> 这是一个基于**Crosswalk内核**的Android开发框架，专门用于处理Android Hybird的开发模式，所谓的Hybird开发模式，也就是说你Native App应用内部通过嵌套一个webview，从而能够装载在线的H5网页，从而极大程度的减少开发成本

`使用该框架可以极大的加速Native App的开发效率，各位看官可以尝试！`

#### 如下模式
也就是小编所开发的**国泰基金6.0**，如今也已经在**华为应用市场**，**OPPO应用市场**， **腾讯应用宝**上架，供大家体验

<img src="https://img-blog.csdn.net/2018101017305419?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3h1ZXh1ZWVuZW4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70" width = "350" height = "750" />

> 该框架的DEMO十分简单，只有一个页面，其中装载了一个包含三个Fragment的viewPager，每个fragment采用lazyload的方式，即用户选中该tab才会加载其中的内容，如果已经显示过，则不再重复加载，每一个fragment中都装载了一个Crosswalk，用于加载不同的链接，不过这里面小编配置了三个同样的链接，都是*百度首页*，各位看官可以自行修改：

```java
fragmentTabMain = XWalkCrossFragment.createInstance("https://www.baidu.com/");
fragmentTabFunds = XWalkCrossFragment.createInstance("https://www.baidu.com/");
fragmentTabMy = XWalkCrossFragment.createInstance("https://www.baidu.com/");
```

@(权限相关)
```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
```

@(开启硬件加速)
```xml
android:hardwareAccelerated="true"
```

### 框架简介
各位看官可以关注**customcrosswalk**这个包中的内容，也是小编自己封装的CrossWalk组件，主要定制化了其中的**ResourceClient**，**UIClient**，**XWalkView**，以便于大家使用起来更加灵活，不过在小编的工程中集成了大量的基于当前业务的代码，这里小编也不方便暴露出来，望各位看官理解~

`代码Down下来编译就可以使用，更多在Crosswalk环境下Native如何和H5通信的细节，请关注小编的另一个博客：https://blog.csdn.net/kameleon2013/article/details/82999084`

### 如果有什么还不懂的话，请和小编共同讨论
<kameleon@126.com>
