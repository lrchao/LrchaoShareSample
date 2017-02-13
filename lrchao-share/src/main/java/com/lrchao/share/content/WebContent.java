package com.lrchao.share.content;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.lrchao.share.ShareSDK;
import com.lrchao.share.utils.Utils;

import java.io.File;

/**
 * Description: 分享网页内容
 *
 * @author liuranchao
 * @date 16/7/4 上午10:23
 */
public class WebContent implements ShareContent {

    private static final String THUMB_FILE_NAME = "share_web_thumb.jpeg";

    // 最大32KB
    private static final int THUMB_SIZE = 32;

    private static final float THUMB_WIDTH = 120f;
    private static final float THUMB_HEIGHT = 120f;

    private String mUrl;

    private String mTitle;

    private String mDesc;

    private int mThumbResId;

    private String mThumbFilePath;

    public WebContent(String url, String title, String desc, int thumbResId, String thumbFilePath) {
        mUrl = url;
        mTitle = title;
        mDesc = desc;
        mThumbResId = thumbResId;
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

    /**
     * 获取缩略图的字节
     */
    public byte[] getThumbData() {
        byte[] data = null;
        if (mThumbResId > 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(ShareSDK.getInstance().getContext().getResources(), mThumbResId);
            data = Utils.compressImage(bitmap, THUMB_SIZE);

        } else if (!TextUtils.isEmpty(mThumbFilePath) && new File(mThumbFilePath).exists()) {
            data = Utils.getBitmap(mThumbFilePath, THUMB_SIZE, THUMB_WIDTH, THUMB_HEIGHT);
        }
        return data;
    }

    public String getThumbFilePath() {

        if (!TextUtils.isEmpty(mThumbFilePath)) {
            return mThumbFilePath;
        }

        String thumbFilePath = Utils.getCacheFile(ShareSDK.getInstance().getContext(), THUMB_FILE_NAME).getAbsolutePath();

        if (Utils.writeFile(getThumbData(), thumbFilePath)) {
            mThumbFilePath = thumbFilePath;
        }
        return mThumbFilePath;
    }

    public Bitmap getThumbBitmap() {
        byte[] data = getThumbData();
        if (data != null && data.length > 0) {
            return Utils.bytesToBitmap(data);
        }
        return null;
    }
}
