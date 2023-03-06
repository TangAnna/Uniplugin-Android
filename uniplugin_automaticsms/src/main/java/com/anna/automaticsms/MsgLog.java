package com.anna.automaticsms;

import android.util.Log;

/**
 * description:日志
 * time: 2023/3/1 2:50 PM.
 *
 * @author TangAnna
 * email: 18201399976@163.com
 * copyright: https://github.com/TangAnna
 */
public class MsgLog {
    private static final String TAG_PREFIX = "uni_plugin_automaticsms:";

    public static void d(String msg) {
        Log.d(TAG_PREFIX, "d: " + msg);
    }
}
