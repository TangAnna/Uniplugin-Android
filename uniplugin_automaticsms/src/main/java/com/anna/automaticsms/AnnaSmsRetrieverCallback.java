package com.anna.automaticsms;

/**
 * description:注册回调
 * time: 2023/3/2 3:03 PM.
 *
 * @author TangAnna
 * email: 18201399976@163.com
 * copyright: https://github.com/TangAnna
 */
public interface AnnaSmsRetrieverCallback {
    /**
     * 短信提取成功
     *
     * @param sms
     */
    void onSmsSuccess(String sms);

    /**失败
     * @param msg 失败信息
     */
    void onSmsFail(String msg);
}
