# Uniplugin-Android
uni plugin Android 
适用于uniapp 的Android原生工具类插件

测试Demo使用uni 官方提供的Demo工程，每一个module都是一个插件，可以下载后自己修改或者直接使用，后期测试没问题后会提交到插件市场
1.屏幕亮度相关

1.1.获取当前屏幕亮度值:getCurrentBrightness

    回调：{"result", "S","value":"亮度值"}

1.2.修改屏幕亮度:changeBrightness
    入参：value:亮度值（取值范围0-255）
    回调：成功{"result", "S","value":"亮度值"} 失败 {"result", "F","errorMsg", "参数错误"}

1.3.高亮:highlight

    回调：{"result", "S","value":"255"}
2.屏幕截图相关
2.1.禁止截屏:disallowScreenshots
2.2.允许截屏:allowScreenshots
