package com.lrchao.share;

import java.lang.ref.SoftReference;

/**
 * Description: 回调
 *
 * @author liuranchao
 * @date 16/8/25 下午4:07
 */
public class ShareCallbackManager {

    private static ShareCallbackManager sInstance;

    private SoftReference<ShareCallback> mShareCallback;

    private ShareCallbackManager() {
    }

    public static ShareCallbackManager getInstance() {
        synchronized (ShareCallbackManager.class) {
            if (sInstance == null) {
                sInstance = new ShareCallbackManager();
            }
        }
        return sInstance;
    }

    public void add(ShareCallback shareCallback) {
        mShareCallback = new SoftReference<>(shareCallback);
    }

    public ShareCallback getShareCallback() {
        return mShareCallback.get();
    }
}
