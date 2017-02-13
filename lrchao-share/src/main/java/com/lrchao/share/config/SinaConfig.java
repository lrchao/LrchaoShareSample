package com.lrchao.share.config;

import com.lrchao.share.ShareSDK;
import com.lrchao.share.utils.LogUtils;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;

/**
 * Description: 新浪分享的配置
 *
 * @author lrc19860926@gmail.com
 * @date 2016/10/11 上午11:19
 */

public class SinaConfig {

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * <p>
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * <p>
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * <p>
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * <p>
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    private static SinaConfig sInstance;

    private String mAppKey;

    private IWeiboShareAPI mWeiboShareAPI;

    private boolean mRegisterResult;

    private SinaConfig() {
    }

    public static SinaConfig getInstance() {
        synchronized (SinaConfig.class) {
            if (sInstance == null) {
                sInstance = new SinaConfig();
            }
        }
        return sInstance;
    }

    public void init(String appKey, String appSecret) {


        if (mWeiboShareAPI == null || !mRegisterResult) {
            mAppKey = appKey;
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(ShareSDK.getInstance().getContext(), appKey);
            mRegisterResult = mWeiboShareAPI.registerApp(); // 将应用注册到微博客户端
        }

        LogUtils.e("init==appKey==" + appKey + " mRegisterResult=" + mRegisterResult);
    }

    public IWeiboShareAPI getWeiboShareAPI() {
        return mWeiboShareAPI;
    }

    public String getAppKey() {
        return mAppKey;
    }
}
