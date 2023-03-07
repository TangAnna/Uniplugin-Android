package com.anna.pdf;

import android.util.Log;

/**
 * description:
 * time: 2023/3/1 2:50 PM.
 *
 * @author TangAnna
 * email: 18201399976@163.com
 * copyright: https://github.com/TangAnna
 */
public class MsgLog {
    private static final String TAG_PREFIX = "uni_plugin_smalltools=======: ";

    public static void d(String msg) {
        Log.d(TAG_PREFIX,msg);
    }
}
