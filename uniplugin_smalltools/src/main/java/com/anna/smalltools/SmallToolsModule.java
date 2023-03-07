package com.anna.smalltools;

import android.app.Activity;
import android.provider.Settings;
import android.view.WindowManager;

import com.alibaba.fastjson.JSONObject;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

/**
 * description:工具类
 * time: 2023/3/6 3:23 PM.
 *
 * @author TangAnna
 * email: 18201399976@163.com
 * copyright: 北京沐融信息科技股份有限公司
 */
public class SmallToolsModule extends UniModule {


    /**
     * 获取当前屏幕亮度
     * 0-255
     *
     * @param callback
     * @return
     */
    @UniJSMethod(uiThread = true)
    public void getCurrentBrightness(UniJSCallback callback) {
        MsgLog.d("交互名称：getCurrentBrightness");
        int currentBrightness = 0;
        WindowManager.LayoutParams lp = ((Activity) mUniSDKInstance.getContext()).getWindow().getAttributes();
        float brightness = lp.screenBrightness;
        if (brightness < 0) {
            try {
                currentBrightness = Settings.System.getInt(mUniSDKInstance.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            currentBrightness = (int) (brightness * 255);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "S");
        jsonObject.put("value", currentBrightness);
        callback.invoke(jsonObject);
        MsgLog.d("交互名称：getCurrentBrightness 回调参数：" + jsonObject);
    }

    /**
     * 调整屏幕亮度
     * 取值范围：0-255
     *
     * @param callback
     */
    @UniJSMethod(uiThread = true)
    public void changeBrightness(JSONObject jsonObject, UniJSCallback callback) {
        MsgLog.d("交互名称：changeBrightness 参数：" + jsonObject);
        JSONObject object = new JSONObject();
        if (jsonObject == null || !jsonObject.containsKey("value")) {
            MsgLog.d("参数错误");
            object.put("result", "F");
            object.put("errorMsg", "参数错误");
            callback.invoke(object);
            MsgLog.d("交互名称：changeBrightness 回调参数：" + object);
            return;
        }
        Integer value = jsonObject.getInteger("value");
        WindowManager.LayoutParams lp = ((Activity) mUniSDKInstance.getContext()).getWindow().getAttributes();
        lp.screenBrightness = value / 255f;
        ((Activity) mUniSDKInstance.getContext()).getWindow().setAttributes(lp);
        object.put("result", "S");
        object.put("value", value);
        callback.invoke(object);
        MsgLog.d("交互名称：changeBrightness 回调参数：" + object);
    }

    /**
     * 调整屏幕高亮
     * 取值范围：0-255
     *
     * @param callback
     */
    @UniJSMethod(uiThread = true)
    public void highlight(UniJSCallback callback) {
        MsgLog.d("交互名称：highlight ");
        WindowManager.LayoutParams lp = ((Activity) mUniSDKInstance.getContext()).getWindow().getAttributes();
        lp.screenBrightness = 255 / 255f;
        ((Activity) mUniSDKInstance.getContext()).getWindow().setAttributes(lp);
        JSONObject object = new JSONObject();
        object.put("result", "S");
        object.put("value", 255);
        callback.invoke(object);
        MsgLog.d("交互名称：highlight 回调参数：" + object);
    }
}
