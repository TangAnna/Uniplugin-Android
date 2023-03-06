package com.anna.automaticsms;

import android.content.IntentFilter;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

/**
 * description:自动读取短信验证码
 * time: 2023/3/2 3:03 PM.
 *
 * @author TangAnna
 * email: 18201399976@163.com
 * copyright: https://github.com/TangAnna
 */
public class AutomaticSmsModule extends UniModule {

    private AnnaSmsBroadcastReceiver mSmsBroadcastReceiver;

    /**
     * 注册短信监控，页面创建后开始注册
     *
     * @param callback 回调
     */
    @UniJSMethod(uiThread = true)
    public void registerSmsObserver(final UniJSCallback callback) {
        MsgLog.d("交互：registerOtpObserver ");

        if (mSmsBroadcastReceiver == null) {
            mSmsBroadcastReceiver = new AnnaSmsBroadcastReceiver();
            mSmsBroadcastReceiver.setCallback(new AnnaSmsRetrieverCallback() {
                @Override
                public void onSmsSuccess(String sms) {
                    MsgLog.d("onSmsSuccess:" + sms);
                    if (callback != null) {
                        JSONObject resultData = new JSONObject();
                        resultData.put("result", "S");
                        resultData.put("sms", "sms");
                        callback.invoke(resultData);
                        MsgLog.d("交互：registerOtpObserver 回调参数：" + resultData);
                    }
                }

                @Override
                public void onSmsFail(String msg) {
                    MsgLog.d("onSmsFail:" + msg);
                    if (callback != null) {
                        JSONObject resultData = new JSONObject();
                        resultData.put("result", "F");
                        resultData.put("error", msg);
                        callback.invoke(resultData);
                        MsgLog.d("交互：registerOtpObserver 回调参数：" + resultData);
                    }
                }
            });
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        mUniSDKInstance.getContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);
    }

    /**
     * 注销短信验证码自动填充
     */
    @UniJSMethod(uiThread = true)
    public void unRegisterSmsObserver() {
        MsgLog.d("交互：unRegisterOtpObserver");
        if (mSmsBroadcastReceiver != null) {
            mUniSDKInstance.getContext().unregisterReceiver(mSmsBroadcastReceiver);
        }
    }

    /**
     * 启动短信检索器，每次短信验证码发送成功后调用此方法检索
     * 5分钟内有效
     *
     * @param callback 回调
     */
    @UniJSMethod(uiThread = true)
    public void startSmsRetriever(final UniJSCallback callback) {
        MsgLog.d("交互：startSmsRetriever ");
        SmsRetrieverClient client = SmsRetriever.getClient(mUniSDKInstance.getContext());
        final Task<Void> task = client.startSmsRetriever();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                MsgLog.d("start Sms Retriever success");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                MsgLog.d("start Sms Retriever Failure:" + e);
            }
        });

        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                MsgLog.d("start Sms Retriever onComplete:");
                if (callback != null) {
                    JSONObject jsonObject = new JSONObject();
                    if (task.isSuccessful()) {
                        jsonObject.put("result", "S");
                    } else {
                        jsonObject.put("result", "F");
                        jsonObject.put("error", task.getException() != null ? task.getException().toString() : "");
                    }
                    callback.invoke(jsonObject);
                }
            }
        });
    }

    /**
     * 获取APP的签名 短信内容中使用
     *
     * @param callback 回调
     */
    @UniJSMethod(uiThread = true)
    public void getAppSignatures(final UniJSCallback callback) {
        MsgLog.d("交互 getAppSignatures");
        ArrayList<String> appSignatures = new AppSignatureHelper(mUniSDKInstance.getContext()).getAppSignatures();
        if (callback != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hash:", appSignatures != null ? appSignatures.toString() : "Get app hash error!");
            callback.invoke(jsonObject);
            MsgLog.d(jsonObject.toString());
        }
    }
}
