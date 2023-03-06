package io.dcloud.uniplugin;

import android.util.Log;

/**
 * description:
 * time: 2023/3/1 2:50 PM.
 *
 * @author TangAnna
 * email: tang_an@murongtech.com
 * copyright: 北京沐融信息科技股份有限公司
 */
public class MsgLog {
    private static final String TAG_PREFIX = "uni_plugin_picture:";

    public static void d(String msg) {
        Log.d(TAG_PREFIX, "d: " + msg);
    }
}
