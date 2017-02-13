package com.lrchao.share.platform;

import android.os.Bundle;

import com.lrchao.share.R;
import com.lrchao.share.ShareSDK;
import com.lrchao.share.content.ShareContent;
import com.lrchao.share.content.WebContent;
import com.lrchao.share.utils.Utils;
import com.tencent.connect.share.QQShare;

/**
 * Description: 分享到QQ IM
 *
 * @author liuranchao
 * @date 16/7/4 上午10:27
 */
public class QQ extends SharePlatform {

    private Bundle params;

    public QQ(ShareContent content) {
        super(content);

        if (content instanceof WebContent) {
            bindWeb((WebContent) content);
        }
    }

    @Override
    public void share() {
        Utils.navToQQShareActivity(params);
    }

    private void bindWeb(WebContent content) {
        params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, content.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content.getDesc());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, content.getUrl());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, content.getThumbFilePath());
        //params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试222应用 222222");  手 Q 客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
    }

    @Override
    public String getTitle() {
        return ShareSDK.getInstance().getContext().getResources().getString(R.string.platform_qq);
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_qq;
    }
}
