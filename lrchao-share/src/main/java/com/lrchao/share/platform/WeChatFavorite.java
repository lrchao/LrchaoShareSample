package com.lrchao.share.platform;

import com.lrchao.share.R;
import com.lrchao.share.ShareSDK;
import com.lrchao.share.content.ShareContent;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

/**
 * Description: 分享到微信收藏
 *
 * @author liuranchao
 * @date 16/7/4 上午10:28
 */
public class WeChatFavorite extends WeChatPlatform {

    public WeChatFavorite(ShareContent content) {
        super(content);
    }

    @Override
    protected int getWXScene() {
        return SendMessageToWX.Req.WXSceneFavorite;
    }

    @Override
    public String getTitle() {
        return ShareSDK.getInstance().getContext().getResources().getString(R.string.platform_wechat_favorite);
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_wechat_friend;
    }

}
