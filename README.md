# Backl1ghtWeather
一款用java+okhttp开发的天气应用，是BacklightWeather的2.0版本，数据来源为和风天气。

由于BacklightWeather是初学的时候写的，而且是课程作业赶时间，导致有很多冗余的代码。

花了比较多的时间和力气去重构，将很多冗余的东西都清理掉了，由于改动比较大，就另外新建了这个仓库。

### 使用

1. Android Studio
2. 注册和风天气的key，替换掉MainActivity.java中的key值。
3. 注册高德地图的key，替换掉AndroidManifest.xml中的key值。

### 运行界面展示



### 更新日志

- 2020.9.10: 上传重构后的第一版

### TODO

- [x] 使用okhttp替换HttpURLConnection
- [ ] 完善支持华氏度单位的功能
- [ ] 保存离线数据，当无网络连接时就显示离线数据
- [ ] 多标签页显示多个城市的天气
  - [x] 城市列表(city picker)
  - [x] 城市选择(city picker)
  - [x] 多标签页(ViewPager + CircleIndicator)
  - [ ] 添加城市后主界面视图更新
- [ ] 修复定时推送的bug
  - [ ] 定时更新数据，然后再推送
- [ ] 改用最新的和风天气v7API
  - [ ] 用和风天气城市信息查询实现城市选择
  - [ ] 请求数据的逻辑换成v7的版本
- [ ] 美化界面
  - [ ] 修改界面配色或控件位置，现在CircleIndicator和背景色同色，显示不出来
  - [ ] 城市列表界面优化(item使用swipe view)
  - [ ] 尝试自定义view
- [ ] 优化性能
  - [ ] 现在切换标签页，ViewPager的回收机制会导致界面半初始化状态被现实
- [ ] 优化定位相关功能
- [ ] 优化设置相关功能

### 参考资料

1. [CityPickerX](https://github.com/zhuxu1/CityPickerX)
2. [和风天气](https://www.heweather.com/)
3. [CircleIndicator](https://github.com/ongakuer/CircleIndicator)