package com.lrchao.share.content;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.lrchao.share.ShareSDK;
import com.lrchao.share.utils.Utils;

import java.io.File;

/**
 * Description: 分享音乐内容对象
 *
 * @author liuranchao
 * @date 16/7/4 上午10:23
 */
public class MusicContent implements ShareContent {

    private String mUrl;

    private String mTitle;

    private String mDesc;

    private int mThumbId;

    private String mThumbFilePath;


    public MusicContent(String url, String title, String desc, int thumbId, String thumbFilePath) {
        mUrl = url;
        mTitle = title;
        mDesc = desc;
        mThumbId = thumbId;
        mThumbFilePath = thumbFilePath;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDesc() {
        return mDesc;
    }

    public byte[] getThumbData() {
        byte[] data = null;
        Bitmap thumbBmp = getImgBmp();
        if (thumbBmp != null) {
            data = Utils.bmpToByteArray(thumbBmp, true);
        }
        return data;
    }

    /**
     * 获取图片的bitmap
     */
    public Bitmap getImgBmp() {

        Bitmap bitmap = null;
        if (mThumbId > 0) {
            bitmap = BitmapFactory.decodeResource(ShareSDK.getInstance().getContext().getResources(), mThumbId);
        } else if (!TextUtils.isEmpty(mThumbFilePath) && new File(mThumbFilePath).exists()) {
            bitmap = BitmapFactory.decodeFile(mThumbFilePath);
        }
        return bitmap;
    }
}
