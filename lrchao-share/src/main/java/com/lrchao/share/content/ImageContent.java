package com.lrchao.share.content;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.lrchao.share.ShareSDK;
import com.lrchao.share.utils.Utils;

import java.io.File;

/**
 * Description: 分享图片内容
 *
 * @author liuranchao
 * @date 16/7/4 上午10:22
 */
public class ImageContent implements ShareContent {

    private static final int THUMB_SIZE = 48;

    /**
     * 图片的res id
     */
    private int mImgResId;

    /**
     * 图片的本地路径
     */
    private String mImgFilePath;

    public ImageContent(int imgResId, String imgFilePath) {
        mImgResId = imgResId;
        mImgFilePath = imgFilePath;
    }

    /**
     * 获取图片的bitmap
     */
    public Bitmap getImgBmp() {

        Bitmap bitmap = null;
        if (mImgResId > 0) {
            bitmap = BitmapFactory.decodeResource(ShareSDK.getInstance().getContext().getResources(), mImgResId);
        } else if (!TextUtils.isEmpty(mImgFilePath) && new File(mImgFilePath).exists()) {
            bitmap = BitmapFactory.decodeFile(mImgFilePath);
        }
        return bitmap;
    }

    /**
     * 缩略图的bitmap
     */
    private Bitmap getThumbBmp() {
        Bitmap thumbBmp = null;
        Bitmap bitmap = getImgBmp();
        if (bitmap != null) {
            thumbBmp = Bitmap.createScaledBitmap(getImgBmp(), THUMB_SIZE, THUMB_SIZE, true);
            bitmap.recycle();
        }
        return thumbBmp;
    }

    /**
     * 获取缩略图的byte
     */
    public byte[] getThumbByte() {

        byte[] data = null;
        Bitmap thumbBmp = getThumbBmp();
        if (thumbBmp != null) {
            data = Utils.bmpToByteArray(thumbBmp, true);
        }
        return data;
    }
}
