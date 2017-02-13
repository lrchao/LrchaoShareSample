package com.lrchao.sharesample;

import android.app.Application;

import com.lrchao.share.PlatformConfig;
import com.lrchao.share.ShareSDK;


/**
 * Description: Application
 *
 * @author liuranchao
 * @date 16/7/6 下午5:46
 */
public class CustomApplication extends Application {

    public static final String WECHAT_APP_ID = "wxb52a2ff0b01b9f24";
    private static final String WECHAT_SECRET = "e436b53f39ac939c89976c88d1561968";

    public static final String QQ_APP_ID = "1105056617";
    public static final String QQ_APP_KEY = "CXUjVnj9yTPuoLpO";

    public static final String SINA_APP_KEY = "2090115943";
    public static final String SINA_APP_SECRET = "363eba96ff6ebedcfbd67c4b296db4c7";


    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.getInstance().init(this);
        PlatformConfig.initWeChat(WECHAT_APP_ID, WECHAT_SECRET);
        PlatformConfig.initQQ(QQ_APP_ID, QQ_APP_KEY);
        PlatformConfig.initSina(SINA_APP_KEY, SINA_APP_SECRET);
    }
}
