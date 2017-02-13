package com.lrchao.share.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.StringRes;

import com.lrchao.share.Constants;
import com.lrchao.share.ShareSDK;
import com.lrchao.share.content.ShareContent;
import com.lrchao.share.ui.activity.QQShareActivity;
import com.lrchao.share.ui.activity.SinaShareActivity;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description: 工具类
 *
 * @author liuranchao
 * @date 16/7/5 下午8:47
 */
public final class Utils {

    private static final String TAG = "Utils";

    private Utils() {
    }

    /**
     * 将Bitmap转换为byte[]
     *
     * @param bmp         Bitmap
     * @param needRecycle 是否要回收
     * @return byte[]
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] getBitmap(String srcPath, long length, float width, float height) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        LogUtils.d(TAG, "before===w:" + w + " h:" + h);
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = width;//这里设置高度为800f
        float ww = height;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        LogUtils.d(TAG, "after===w:" + bitmap.getWidth() + " h:" + bitmap.getHeight() + " bitmap:" + bitmap);
        return compressImage(bitmap, length);//压缩好比例大小后再进行质量压缩
    }

    /**
     * @param srcBmp
     * @param length KB
     * @return
     */
    public static byte[] compressImage(Bitmap srcBmp, long length) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        srcBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        int options = 80;
        while (baos.toByteArray().length / 1024 > length) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            srcBmp.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        LogUtils.d("baos.toByteArray()==" + baos.toByteArray());
        return baos.toByteArray();
    }

    /**
     * byte to bitmap
     *
     * @param b byte[]
     * @return bitmap
     */
    public static Bitmap bytesToBitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


    /**
     * 跳转到QQ初始化分享的页面
     */
    public static void navToQQShareActivity(Bundle bundle) {
        Intent i = new Intent(ShareSDK.getInstance().getContext(), QQShareActivity.class);
        i.putExtras(bundle);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ShareSDK.getInstance().getContext().startActivity(i);
    }

    /**
     * 跳转到新浪初始化分享的页面
     */
    public static void navToSinaShareActivity(ShareContent shareContent) {
        Intent i = new Intent(ShareSDK.getInstance().getContext(), SinaShareActivity.class);
        i.putExtra(Constants.INTENT_EXTRA_SHARE_CONTENT, shareContent);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ShareSDK.getInstance().getContext().startActivity(i);
    }

    /**
     * 写文件
     *
     * @param data 数据元
     */
    public static boolean writeFile(byte[] data, String filePath) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(filePath));
            bos.write(data);
            bos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    /**
     * /data/data/com.jia.zxpt.user/cache/fileName
     *
     * @param context  Context
     * @param fileName 文件名称
     * @author KevinLiu
     */
    public static File getCacheFile(Context context, String fileName) {
        File cacheDir = context.getCacheDir();
        return new File(cacheDir.getAbsolutePath() + File.separator + fileName);
    }

    /**
     * @param resId      strings.xml
     * @param formatArgs %1$s 替换字符串
     * @author Kevin Liu
     */
    public static String getString(@StringRes int resId, Object... formatArgs) {
        return ShareSDK.getInstance().getContext().getResources().getString(resId, formatArgs);
    }
}
