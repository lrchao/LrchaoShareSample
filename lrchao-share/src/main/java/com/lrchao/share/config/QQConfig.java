package com.lrchao.share.config;

import com.lrchao.share.ShareSDK;
import com.tencent.tauth.Tencent;

/**
 * Description: QQ配置文件
 *
 * @author lrc19860926@gmail.com
 * @date 2016/10/10 上午10:41
 */

public class QQConfig {

    private static QQConfig sInstance;

    private Tencent mTencent;

    private QQConfig() {
    }

    public static QQConfig getInstance() {
        synchronized (QQConfig.class) {
            if (sInstance == null) {
                sInstance = new QQConfig();
            }
        }
        return sInstance;
    }

    public void init(String appId) {

        try {
            if (mTencent == null) {
                mTencent = Tencent.createInstance(appId, ShareSDK.getInstance().getContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error error) {
            error.printStackTrace();
        }

    }

    public Tencent getTencent() {
        return mTencent;
    }
}
