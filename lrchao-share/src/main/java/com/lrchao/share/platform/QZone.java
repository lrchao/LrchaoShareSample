package com.lrchao.share.platform;

import android.os.Bundle;

import com.lrchao.share.R;
import com.lrchao.share.ShareSDK;
import com.lrchao.share.content.ShareContent;
import com.lrchao.share.content.WebContent;
import com.lrchao.share.utils.Utils;
import com.tencent.connect.share.QQShare;

/**
 * Description: 分享到QQ空间
 *
 * @author liuranchao
 * @date 16/7/4 上午10:26
 */
public class QZone extends SharePlatform {

    private Bundle params;

    public QZone(ShareContent shareContent) {
        super(shareContent);

        if (shareContent instanceof WebContent) {
            bindWeb((WebContent) shareContent);
        }
    }

    private void bindWeb(WebContent shareContent) {
        params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareContent.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent.getDesc());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.getUrl());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareContent.getThumbFilePath());
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
    }

    @Override
    public void share() {
        Utils.navToQQShareActivity(params);
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_qzone;
    }

    @Override
    public String getTitle() {
        return ShareSDK.getInstance().getContext().getResources().getString(R.string.platform_qzone);
    }
}
