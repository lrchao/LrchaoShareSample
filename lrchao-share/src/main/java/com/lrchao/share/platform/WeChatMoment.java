package com.lrchao.share.platform;

import com.lrchao.share.R;
import com.lrchao.share.ShareSDK;
import com.lrchao.share.content.ShareContent;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

/**
 * Description: 分享到微信朋友圈
 *
 * @author liuranchao
 * @date 16/7/4 上午10:27
 */
public class WeChatMoment extends WeChatPlatform {

    public WeChatMoment(ShareContent content) {
        super(content);
    }

    @Override
    public String getTitle() {
        return ShareSDK.getInstance().getContext().getResources().getString(R.string.platform_wechat_moment);
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_wechat_moment;
    }

    @Override
    protected int getWXScene() {
        return SendMessageToWX.Req.WXSceneTimeline;
    }
}
