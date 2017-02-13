package com.lrchao.share.config;

import com.lrchao.share.ShareSDK;
import com.lrchao.share.utils.LogUtils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Description: 微信分享配置
 *
 * @author liuranchao
 * @date 16/7/5 下午4:01
 */
public class WeChatConfig {

    private static WeChatConfig sInstance;

    private IWXAPI mAPI;

    private WeChatConfig() {
    }

    public static WeChatConfig getInstance() {
        synchronized (WeChatConfig.class) {
            if (sInstance == null) {
                sInstance = new WeChatConfig();
            }
        }
        return sInstance;
    }

    public void init(String appId, String appSecret) {
        if (mAPI == null) {
            mAPI = WXAPIFactory.createWXAPI(ShareSDK.getInstance().getContext(), appId, true);
            if (mAPI != null) {
                boolean isRegister = mAPI.registerApp(appId);
                LogUtils.d("wechat register result is " + isRegister);
            }
        }
    }

    public IWXAPI getAPI() {
        return mAPI;
    }
}
