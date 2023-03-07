package com.anna.systempicture;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

/**
 * description:系统图片相关
 * time: 2023/3/6 11:07 AM.
 *
 * @author TangAnna
 * email: 18201399976@163.com
 * copyright:https://github.com/TangAnna
 */
public class SystemPictureModule extends UniModule {

    private static final int REQUEST_CODE_CAMERA = 100;
    private static final int REQUEST_CODE_CAMERA_PERMISSION = 101;
    private UniJSCallback mUniJSCallback;
    private Uri mCameraImageUri;

    /**
     * 系统相机拍照
     * 检查权限
     * 创建存储的Uri
     * 开始拍照
     *
     * @param callback 回调对象
     */
    @UniJSMethod(uiThread = true)
    public void systemCamera(UniJSCallback callback) {
        MsgLog.d("交互名称：systemCamera 参数：");
        mUniJSCallback = callback;
        if (ContextCompat.checkSelfPermission(mUniSDKInstance.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openSystemCamera();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ((Activity) mUniSDKInstance.getContext()).requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA_PERMISSION);
            }
        }
    }

    /**
     * 打开系统相机
     */
    private void openSystemCamera() {
        //有权限了
        mCameraImageUri = MediaStoreUtils.createCameraImageUri(mUniSDKInstance.getContext());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraImageUri);
        ((Activity) mUniSDKInstance.getContext()).startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 保存图片到相册
     *
     * @param jsonObject
     * @param callback   回调对象
     */
    @UniJSMethod(uiThread = true)
    public void saveSystemAlbum(JSONObject jsonObject, UniJSCallback callback) {
        MsgLog.d("交互名称：saveSystemAlbum 参数：" + jsonObject);
        JSONObject object = new JSONObject();
        if (jsonObject == null || TextUtils.isEmpty(jsonObject.getString("base64Data"))) {
            object.put("result", "F");
            object.put("errorMsg", "参数错误");
            callback.invoke(object);
            MsgLog.d("交互名称：saveSystemAlbum 回调参数：" + object);
            return;
        }
        String imgBase64 = jsonObject.getString("base64Data");
        byte[] decode = Base64.decode(imgBase64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        if (bitmap != null) {
            // 把文件插入到系统图库
            MediaStore.Images.Media.insertImage(mUniSDKInstance.getContext().getContentResolver(), bitmap, null, null);
            // 通知图库更新
            mUniSDKInstance.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard" +
                    "/namecard/")));
            object.put("result", "S");
            callback.invoke(object);
            MsgLog.d("交互名称：saveSystemAlbum 回调参数：" + object);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获得权限
                openSystemCamera();
            } else {
                //权限被拒绝
                if (mUniJSCallback !=null){
                    JSONObject object = new JSONObject();
                    object.put("result","F");
                    object.put("errorCode","10000");
                    object.put("errorMsg","Permission denied!");
                    mUniJSCallback.invoke(object);
                    MsgLog.d("交互名称：systemCamera 回调参数：" + object);
                }
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CAMERA) {
            //拍照回调
            JSONObject object = new JSONObject();
            object.put("result", "S");
            object.put("uri", mCameraImageUri);
            mUniJSCallback.invoke(object);
            MsgLog.d("交互名称：systemCamera 回调参数：" + object);
        }
    }
}
