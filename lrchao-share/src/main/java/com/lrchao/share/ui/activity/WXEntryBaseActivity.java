package com.lrchao.share.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.lrchao.share.R;
import com.lrchao.share.ShareCallbackManager;
import com.lrchao.share.config.WeChatConfig;
import com.lrchao.share.result.ShareResult;
import com.lrchao.share.utils.LogUtils;
import com.lrchao.share.utils.ToastUtils;
import com.lrchao.share.utils.Utils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

/**
 * Description: 微信接受回调的Activity
 *
 * @author liuranchao
 * @date 16/7/5 上午10:32
 */
public class WXEntryBaseActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryBaseActivity";

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (WeChatConfig.getInstance().getAPI() != null) {
            WeChatConfig.getInstance().getAPI().handleIntent(getIntent(), this);
        }
    }

    /**
     * 微信发请求的回调
     *
     * @param baseReq BaseReq
     */
    @CallSuper
    @Override
    public void onReq(BaseReq baseReq) {
        LogUtils.d(TAG, "onReq===openId=" + baseReq.openId +
                " transaction=" + baseReq.transaction +
                " checkArgs=" + baseReq.checkArgs() +
                " getType=" + baseReq.getType());
    }

    /**
     * 请求微信的回调
     *
     * @param baseResp BaseResp
     */
    @CallSuper
    @Override
    public void onResp(BaseResp baseResp) {
        LogUtils.d(TAG, "onResp==" +
                " errorCode=" + baseResp.errCode +
                " errorStr=" + baseResp.errStr +
                " openId=" + baseResp.openId +
                " transaction=" + baseResp.transaction +
                " checkArgs=" + baseResp.checkArgs() +
                " getType=" + baseResp.getType());

        switch (baseResp.getType()) {
            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                onRespSendMsg(baseResp);
                break;
            default:
                break;
        }
    }

    @CallSuper
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (WeChatConfig.getInstance().getAPI() != null) {
            WeChatConfig.getInstance().getAPI().handleIntent(intent, this);
        }
    }

    /**
     * 发消息的回调
     *
     * @param baseResp BaseResp
     */
    private void onRespSendMsg(BaseResp baseResp) {

        ShareResult shareResult = new ShareResult();


        if (baseResp.transaction.contains(Utils.getString(R.string.platform_wechat_friend))) {
            shareResult.setTitle(Utils.getString(R.string.platform_wechat_friend));
        } else if (baseResp.transaction.contains(Utils.getString(R.string.platform_wechat_moment))) {
            shareResult.setTitle(Utils.getString(R.string.platform_wechat_moment));
        }

        switch (baseResp.errCode) {

            case BaseResp.ErrCode.ERR_USER_CANCEL:

                ToastUtils.showResultToast(R.string.toast_cancel_share);
                if (ShareCallbackManager.getInstance().getShareCallback() != null) {
                    ShareCallbackManager.getInstance().getShareCallback().onShareCancel(shareResult);
                }
                break;
            case BaseResp.ErrCode.ERR_OK:
                ToastUtils.showResultToast(R.string.toast_share_success);
                if (ShareCallbackManager.getInstance().getShareCallback() != null) {
                    ShareCallbackManager.getInstance().getShareCallback().onShareSuccess(shareResult);
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_COMM:
            case BaseResp.ErrCode.ERR_SENT_FAILED:
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                ToastUtils.showResultToast(R.string.toast_share_failed);
                if (ShareCallbackManager.getInstance().getShareCallback() != null) {
                    ShareCallbackManager.getInstance().getShareCallback().onShareFailed(shareResult);
                }
                LogUtils.e(Utils.getString(R.string.log_share_failed, baseResp.errStr, baseResp.errCode));
                break;
            default:
                break;
        }

        finish();
    }
}
