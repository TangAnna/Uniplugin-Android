package com.anna.systempicture;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;


/**
 * description:
 * time: 2022/11/10 10:31 AM.
 *
 * @author TangAnna
 * email: tang_an@murongtech.com
 * copyright: 北京沐融信息科技股份有限公司
 */
public class MediaStoreUtils {

    /**
     * 创建 download MediaStoreUri
     *
     * @param context
     * @param fileName 文件名字
     * @return
     */
    public static Uri createDownloadUri(Context context, String fileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Uri downloadsCollection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
            return context.getContentResolver().insert(downloadsCollection, contentValues);
        }
        return null;
    }

    /**
     * 拍照存储照片的名称
     *
     * @return
     */
    public static String cameraFileName() {
        return "camera-" + System.currentTimeMillis() + MimeType.JPG_MIME.getExtension();
    }

    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param ctx 上下文
     * @return
     */
    public static Uri createCameraImageUri(Context ctx) {
        return createCameraImageUri(ctx, cameraFileName());
    }

    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param ctx      上下文
     * @param fileName 自定义文件名字 需要带有后缀名
     * @return
     */
    public static Uri createCameraImageUri(Context ctx, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = cameraFileName();
        }
        Uri imageCollection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        return ctx.getContentResolver().insert(imageCollection, contentValues);
    }

    /**
     * 创建一条视频地址uri,用于保存录制的视频
     *
     * @param ctx            上下文
     * @param cameraFileName 资源名称
     * @param mimeType       资源类型
     * @return
     */
    public static Uri createVideoUri(final Context ctx, String cameraFileName, String mimeType) {
        Uri videoCollection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            videoCollection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            videoCollection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, cameraFileName);
        return ctx.getContentResolver().insert(videoCollection, contentValues);
    }
}
