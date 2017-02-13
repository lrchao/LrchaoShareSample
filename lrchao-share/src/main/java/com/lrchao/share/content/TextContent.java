package com.lrchao.share.content;

/**
 * Description: 分享文本内容
 *
 * @author liuranchao
 * @date 16/7/4 上午10:19
 */
public class TextContent implements ShareContent {

    private String mText;

    private String mDesc;

    public TextContent(String text, String desc) {
        mText = text;
        mDesc = desc;
    }

    public String getText() {
        return mText;
    }

    public String getDesc() {
        return mDesc;
    }
}
