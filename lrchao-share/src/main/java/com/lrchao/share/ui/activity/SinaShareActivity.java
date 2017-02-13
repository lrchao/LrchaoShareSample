package com.lrchao.share.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lrchao.share.Constants;
import com.lrchao.share.R;
import com.lrchao.share.ShareCallbackManager;
import com.lrchao.share.config.AccessTokenKeeper;
import com.lrchao.share.config.SinaConfig;
import com.lrchao.share.content.ShareContent;
import com.lrchao.share.content.WebContent;
import com.lrchao.share.result.ShareResult;
import com.lrchao.share.utils.LogUtils;
import com.lrchao.share.utils.ToastUtils;
import com.lrchao.share.utils.Utils;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseRequest;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;

/**
 * Description: 新浪分享的Activity
 *
 * @author lrc19860926@gmail.com
 * @date 2016/10/11 上午11:29
 */

public class SinaShareActivity extends Activity implements IWeiboHandler.Response {

    private static final String TAG = "SinaShareActivity";

    private ShareContent mShareContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        if (savedInstanceState != null && SinaConfig.getInstance().getWeiboShareAPI() != null) {
            SinaConfig.getInstance().getWeiboShareAPI().handleWeiboResponse(getIntent(), this);
        }

        mShareContent = (ShareContent) getIntent().
                getSerializableExtra(Constants.INTENT_EXTRA_SHARE_CONTENT);

        share();
    }


    /**
     * SendMultiMessageToWeiboRequest
     */
    private SendMultiMessageToWeiboRequest getMultiMessageToWeiboRequest() {

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        // 设置网页的信息
        if (mShareContent instanceof WebContent) {
            weiboMessage.mediaObject = getWebPageObj((WebContent) mShareContent);
        }

        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        return request;
    }


    /**
     * SendMessageToWeiboRequest
     */
    private SendMessageToWeiboRequest getSingleMessageToWeiboRequest() {

        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMessage weiboMessage = new WeiboMessage();

        // 设置网页的信息
        if (mShareContent instanceof WebContent) {
            weiboMessage.mediaObject = getWebPageObj((WebContent) mShareContent);
        }

        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;

        return request;
    }

    private void share() {


        if (SinaConfig.getInstance().getWeiboShareAPI() != null) {


            if (SinaConfig.getInstance().getWeiboShareAPI().isWeiboAppSupportAPI()) {

                int supportApi = SinaConfig.getInstance().getWeiboShareAPI().getWeiboAppSupportAPI();
                LogUtils.e(TAG, "supportApi==" + supportApi);

                BaseRequest request;
                if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
                    request = getMultiMessageToWeiboRequest();
                } else {
                    request = getSingleMessageToWeiboRequest();
                }
                shareByApp(request);

            } else {
                shareByWebView(getMultiMessageToWeiboRequest());
            }

        } else {
            finish();
        }


        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息


        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

    }


    /**
     * 不通过客户端分享
     */
    private void shareByWebView(SendMultiMessageToWeiboRequest request) {
        AuthInfo authInfo = new AuthInfo(this, SinaConfig.getInstance().getAppKey(),
                SinaConfig.REDIRECT_URL, SinaConfig.SCOPE);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }

        if (SinaConfig.getInstance().getWeiboShareAPI() != null) {
            boolean sendResult = SinaConfig.getInstance().getWeiboShareAPI().sendRequest(this, request, authInfo, token, new WeiboAuthListener() {

                @Override
                public void onWeiboException(WeiboException e) {
                    LogUtils.e(Utils.getString(R.string.log_share_failed, e.getMessage(), 0));
                }

                @Override
                public void onComplete(Bundle bundle) {
                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                    AccessTokenKeeper.writeAccessToken(getApplicationContext(), newToken);
                    LogUtils.e("onAuthorizeComplete token = " + newToken.getToken());
                }

                @Override
                public void onCancel() {
                    LogUtils.e("onCancel");
                }
            });

            LogUtils.e("shareByWebView  sendResult==" + sendResult);
            if (!sendResult) {
                finish();
            }

        } else {
            finish();
        }
    }

    /**
     * 通过app分享
     */
    private void shareByApp(BaseRequest request) {
        if (SinaConfig.getInstance().getWeiboShareAPI() != null) {
            //发送请求消息到微博，唤起微博分享界面
            boolean sendResult = SinaConfig.getInstance().getWeiboShareAPI().sendRequest(this, request);
            LogUtils.e("shareByApp  sendResult==" + sendResult);
            if (!sendResult) {
                finish();
            }
        } else {
            finish();
        }
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebPageObj(WebContent webContent) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = webContent.getTitle();
        mediaObject.description = webContent.getDesc();

        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(webContent.getThumbBitmap());
        mediaObject.actionUrl = webContent.getUrl();
        //mediaObject.defaultText = "Webpage123 默认文案"; 没啥用
        return mediaObject;
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        return textObject;
    }

    /**
     * @see {@link Activity#onNewIntent}
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        if (SinaConfig.getInstance().getWeiboShareAPI() != null) {
            SinaConfig.getInstance().getWeiboShareAPI().handleWeiboResponse(intent, this);
        }
    }


    @Override
    public void onResponse(BaseResponse baseResp) {

        ShareResult shareResult = new ShareResult();
        shareResult.setTitle(Utils.getString(R.string.platform_sina_micro_blog));
        if (baseResp != null) {
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    ToastUtils.showResultToast(R.string.toast_share_success);
                    if (ShareCallbackManager.getInstance().getShareCallback() != null) {
                        ShareCallbackManager.getInstance().getShareCallback().onShareSuccess(shareResult);
                    }
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    ToastUtils.showResultToast(R.string.toast_cancel_share);
                    if (ShareCallbackManager.getInstance().getShareCallback() != null) {
                        ShareCallbackManager.getInstance().getShareCallback().onShareCancel(shareResult);
                    }
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    ToastUtils.showResultToast(R.string.toast_share_failed);
                    if (ShareCallbackManager.getInstance().getShareCallback() != null) {
                        ShareCallbackManager.getInstance().getShareCallback().onShareFailed(shareResult);
                    }
                    LogUtils.e(Utils.getString(R.string.log_share_failed, baseResp.errMsg, baseResp.errCode));
                    break;
                default:
                    break;
            }
        }

        finish();
    }
}
