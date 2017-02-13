package com.lrchao.share;

import com.lrchao.share.config.QQConfig;
import com.lrchao.share.config.SinaConfig;
import com.lrchao.share.config.WeChatConfig;

/**
 * Description: 分享平台设置
 *
 * @author liuranchao
 * @date 16/7/5 下午2:45
 */
public class PlatformConfig {


    /**
     * 设置微信
     */
    public static void initWeChat(String appId, String appSecret) {
        try {
            WeChatConfig.getInstance().init(appId, appSecret);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置qq分享
     */
    public static void initQQ(String appId, String appKey) {
        try {
            QQConfig.getInstance().init(appId);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置新浪分享
     */
    public static void initSina(String appKey, String appSecret) {
        try {
            SinaConfig.getInstance().init(appKey, appSecret);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
