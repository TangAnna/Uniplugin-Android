package com.anna.automaticsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;


/**
 * description:短信监控广播
 * time: 2023/3/2 3:03 PM.
 *
 * @author TangAnna
 * email: 18201399976@163.com
 * copyright: https://github.com/TangAnna
 */
public class AnnaSmsBroadcastReceiver extends BroadcastReceiver {

    private AnnaSmsRetrieverCallback callback;

    @Override
    public void onReceive(Context context, Intent intent) {
        MsgLog.d("onReceive:" + intent.toString());
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    MsgLog.d(message);
                    if (callback != null) {
                        callback.onSmsSuccess(message);
                    }
                    break;
                default:
                    MsgLog.d("code:" + status.getStatusCode() + "--msg:" + CommonStatusCodes.getStatusCodeString(status.getStatusCode()));
                    if (callback != null) {
                        callback.onSmsFail(CommonStatusCodes.getStatusCodeString(status.getStatusCode()));
                    }
                    break;
            }
        }
    }

    public void setCallback(AnnaSmsRetrieverCallback callback) {
        this.callback = callback;
    }
}
