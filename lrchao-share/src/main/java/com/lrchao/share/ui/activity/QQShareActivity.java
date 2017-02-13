package com.lrchao.share.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lrchao.share.R;
import com.lrchao.share.ShareCallbackManager;
import com.lrchao.share.config.QQConfig;
import com.lrchao.share.result.ShareResult;
import com.lrchao.share.utils.LogUtils;
import com.lrchao.share.utils.ToastUtils;
import com.lrchao.share.utils.Utils;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Description: 类似微信分享的准备页面，发起分享并处理结果
 * 透明的页面
 *
 * @author lrc19860926@gmail.com
 * @date 2016/10/10 下午5:26
 */

public class QQShareActivity extends Activity {

    private ShareResult mShareResult = new ShareResult();

    IUiListener mQQShareListener = new IUiListener() {
        @Override
        public void onComplete(Object response) {
            ToastUtils.showResultToast(R.string.toast_share_success);
            if (ShareCallbackManager.getInstance().getShareCallback() != null) {
                ShareCallbackManager.getInstance().getShareCallback().onShareSuccess(mShareResult);
            }
            finish();
        }

        @Override
        public void onError(UiError e) {
            ToastUtils.showResultToast(R.string.toast_share_failed);
            if (ShareCallbackManager.getInstance().getShareCallback() != null) {
                ShareCallbackManager.getInstance().getShareCallback().onShareFailed(mShareResult);
            }
            LogUtils.e(Utils.getString(R.string.log_share_failed, e.errorMessage, e.errorCode));
            finish();
        }

        @Override
        public void onCancel() {
            ToastUtils.showResultToast(R.string.toast_cancel_share);
            if (ShareCallbackManager.getInstance().getShareCallback() != null) {
                ShareCallbackManager.getInstance().getShareCallback().onShareCancel(mShareResult);
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle params = getIntent().getExtras();

        int sendType = params.getInt(QQShare.SHARE_TO_QQ_EXT_INT);

        // 分享到QQ
        if (QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE == sendType) {
            mShareResult.setTitle(Utils.getString(R.string.platform_qq));
        } else if (QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN == sendType) {
            // QZone
            mShareResult.setTitle(Utils.getString(R.string.platform_qzone));
        }


        if (QQConfig.getInstance().getTencent() != null) {
            QQConfig.getInstance().getTencent().shareToQQ(this, params, mQQShareListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.REQUEST_QQ_SHARE ||
                resultCode == Constants.ACTIVITY_OK) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mQQShareListener);
        }
        super.onActivityResult(requestCode, resultCode, data);


    }
}
