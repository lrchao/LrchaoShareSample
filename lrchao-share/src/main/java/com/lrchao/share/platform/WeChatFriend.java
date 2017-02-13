package com.lrchao.share.platform;

import com.lrchao.share.R;
import com.lrchao.share.ShareSDK;
import com.lrchao.share.content.ShareContent;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

/**
 * Description: 微信好友分享
 *
 * @author liuranchao
 * @date 16/7/4 上午9:53
 */
public class WeChatFriend extends WeChatPlatform {

    public WeChatFriend(ShareContent content) {
        super(content);
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_wechat_friend;
    }

    @Override
    public String getTitle() {
        return ShareSDK.getInstance().getContext().getResources().getString(R.string.platform_wechat_friend);
    }

    @Override
    protected int getWXScene() {
        return SendMessageToWX.Req.WXSceneSession;
    }
}
