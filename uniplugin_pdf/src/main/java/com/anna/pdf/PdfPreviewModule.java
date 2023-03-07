package com.anna.pdf;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONObject;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

/**
 * description:pdf 文件预览
 * time: 2023/3/7 4:20 PM.
 *
 * @author TangAnna
 * email: tang_an@murongtech.com
 * copyright: 北京沐融信息科技股份有限公司
 */
@Keep
public class PdfPreviewModule extends UniModule {
    /**
     * pdf 本地预览
     * path:本地地址
     * needNavigation:是否需要顶部导航 true:需要 false:不需要 （默认有）
     * navigationColor:导航背景颜色argb（默认白色）
     * blackBack:是否黑色返回按钮 true:是 false:不是 （默认黑色）
     * titleText:标题文案 （默认无）
     * titleColor:标题颜色argb （默认黑色）
     *
     * @param callback
     */
    @UniJSMethod(uiThread = true)
    public void pdfPreviewLocal(JSONObject jsonObject, UniJSCallback callback) {
        MsgLog.d("交互名称：pdfPreview 参数：" + jsonObject);
        JSONObject object = new JSONObject();
        if (jsonObject == null || !jsonObject.containsKey("path")) {
            MsgLog.d("参数错误");
            object.put("result", "F");
            object.put("errorMsg", "参数错误");
            callback.invoke(object);
            MsgLog.d("交互名称：pdfPreview 回调参数：" + object);
            return;
        }
        Bundle bundle = new Bundle();
        String path = jsonObject.getString("path");
        bundle.putString("path", path);
        String titleText = jsonObject.getString("titleText");
        bundle.putString("titleText", titleText);
        String titleColor = jsonObject.getString("titleColor");
        bundle.putString("titleColor", titleColor);

        boolean needNavigation = jsonObject.getBoolean("needNavigation");
        bundle.putBoolean("needNavigation", needNavigation);
        String navigationColor = jsonObject.getString("navigationColor");
        bundle.putString("navigationColor", navigationColor);
        boolean blackTheme = jsonObject.getBoolean("blackBack");
        bundle.putBoolean("blackBack", blackTheme);
        Intent intent = new Intent(mUniSDKInstance.getContext(), PdfPreviewActivity.class);
        intent.putExtras(bundle);
        mUniSDKInstance.getContext().startActivity(intent);
    }


    /**
     * @return pdf 关闭事件
     */
    @UniJSMethod(uiThread = false)
    public JSONObject pdfClosed() {
        JSONObject data = new JSONObject();
        data.put("code", "success");
        return data;
    }
}
