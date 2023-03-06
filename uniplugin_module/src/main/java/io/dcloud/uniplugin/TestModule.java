package io.dcloud.uniplugin;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;


public class TestModule extends UniModule {

    String TAG = "TestModule";
    public static int REQUEST_CODE = 1000;

    /**
     * 选择照片
     *
     * @param options  参数
     * @param callback 回调对象
     */
    @UniJSMethod(uiThread = true)
    public void testMethod(JSONObject options, UniJSCallback callback) {
        MsgLog.d("交互名称：testMethod 参数：" + options.toString());
        Toast.makeText(mUniSDKInstance.getContext(), "testMethod", Toast.LENGTH_SHORT).show();
        if (callback != null) {
            JSONObject data = new JSONObject();
            data.put("code", "success");
            callback.invoke(data);
            MsgLog.d("交互名称：testMethod 回调参数：" + data);
        }
    }

    //run ui thread
    @UniJSMethod(uiThread = true)
    public void testAsyncFunc(JSONObject options, UniJSCallback callback) {
        Log.e(TAG, "testAsyncFunc--" + options);
        if (callback != null) {
            JSONObject data = new JSONObject();
            data.put("code", "success");
            callback.invoke(data);
            //callback.invokeAndKeepAlive(data);
        }
    }

    //run JS thread
    @UniJSMethod(uiThread = false)
    public JSONObject testSyncFunc() {
        JSONObject data = new JSONObject();
        data.put("code", "success");
        return data;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && data.hasExtra("respond")) {
            Log.e("TestModule", "原生页面返回----" + data.getStringExtra("respond"));
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @UniJSMethod(uiThread = true)
    public void gotoNativePage() {
        if (mUniSDKInstance != null && mUniSDKInstance.getContext() instanceof Activity) {
            Intent intent = new Intent(mUniSDKInstance.getContext(), NativePageActivity.class);
            ((Activity) mUniSDKInstance.getContext()).startActivityForResult(intent, REQUEST_CODE);
        }
    }
}
