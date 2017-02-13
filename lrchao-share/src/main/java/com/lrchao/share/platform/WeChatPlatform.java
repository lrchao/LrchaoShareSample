package com.lrchao.share.platform;

import com.lrchao.share.config.WeChatConfig;
import com.lrchao.share.content.ImageContent;
import com.lrchao.share.content.MusicContent;
import com.lrchao.share.content.ShareContent;
import com.lrchao.share.content.TextContent;
import com.lrchao.share.content.VideoContent;
import com.lrchao.share.content.WebContent;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

/**
 * Description: 微信平台分享基类
 *
 * @author liuranchao
 * @date 16/7/5 下午7:38
 */
public abstract class WeChatPlatform extends SharePlatform {

    private SendMessageToWX.Req mReq;

    public WeChatPlatform(ShareContent content) {
        super(content);
        mReq = new SendMessageToWX.Req();
        WXMediaMessage msg = new WXMediaMessage();

        if (content instanceof TextContent) {
            bindText((TextContent) content, msg);
        } else if (content instanceof ImageContent) {
            bindImg((ImageContent) content, msg);
        } else if (content instanceof MusicContent) {
            bindMusic((MusicContent) content, msg);
        } else if (content instanceof VideoContent) {
            bindVideo((VideoContent) content, msg);
        } else if (content instanceof WebContent) {
            bindWeb((WebContent) content, msg);
        }

        mReq.message = msg;
        mReq.scene = getWXScene();
    }

    /**
     * 好友，朋友圈 or...
     */
    protected abstract int getWXScene();

    /**
     * 构建文本的Req
     *
     * @param content TextContent
     */
    private void bindText(TextContent content, WXMediaMessage msg) {
        // 初始化一个WXTextObject对象，填写分享的文本内容
        WXTextObject textObject = new WXTextObject();
        // 发送的默认文本
        textObject.text = content.getText();
        msg.mediaObject = textObject;
        msg.description = content.getDesc();
        mReq.transaction = buildTransaction("text");
    }


    /**
     * 构图片类型
     *
     * @param content ImageContent
     */
    private void bindImg(ImageContent content, WXMediaMessage msg) {
        WXImageObject imageObject = new WXImageObject(content.getImgBmp());
        msg.mediaObject = imageObject;
        // 设置缩略图
        msg.thumbData = content.getThumbByte();
        mReq.transaction = buildTransaction("img");
    }

    /**
     * 音乐类型
     */
    private void bindMusic(MusicContent content, WXMediaMessage msg) {
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = content.getUrl();

        msg.mediaObject = music;
        msg.title = content.getTitle();
        msg.description = content.getDesc();
        msg.thumbData = content.getThumbData();

        mReq.transaction = buildTransaction("music");
    }

    /**
     * 视频类型
     */
    private void bindVideo(VideoContent content, WXMediaMessage msg) {
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = content.getUrl();
        msg.mediaObject = video;
        msg.title = content.getTitle();
        msg.description = content.getDesc();
        msg.thumbData = content.getThumbData();
        mReq.transaction = buildTransaction("video");
    }

    /**
     * 网页
     */
    private void bindWeb(WebContent content, WXMediaMessage msg) {
        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = content.getUrl();
        msg.mediaObject = webPage;
        msg.title = content.getTitle();
        msg.description = content.getDesc();
        msg.thumbData = content.getThumbData();
        mReq.transaction = buildTransaction("webPage");
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis() + getTitle();
    }

    @Override
    public void share() {
        if (mReq != null && WeChatConfig.getInstance().getAPI() != null) {
            WeChatConfig.getInstance().getAPI().sendReq(mReq);
        }
    }
}
