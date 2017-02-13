package com.lrchao.share;

import android.content.Context;

/**
 * Description: SDK基类
 *
 * @author liuranchao
 * @date 16/7/6 下午6:27
 */
public class ShareSDK {

    private static ShareSDK sInstance;
    private Context mContext;
    private boolean mShowToast;

    private ShareSDK() {
    }

    public static ShareSDK getInstance() {
        synchronized (ShareSDK.class) {
            if (sInstance == null) {
                sInstance = new ShareSDK();
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isShowToast() {
        return mShowToast;
    }

    public void setShowToast(boolean showToast) {
        mShowToast = showToast;
    }
}
