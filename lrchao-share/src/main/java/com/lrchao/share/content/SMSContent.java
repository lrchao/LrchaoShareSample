package com.lrchao.share.content;

/**
 * Description: 短信内容
 *
 * @author liuranchao
 * @date 16/9/19 下午5:25
 */
public class SMSContent extends TextContent {

    private String mPhoneNumber;

    public SMSContent(String text, String desc, String phoneNumber) {
        super(text, desc);
        mPhoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }
}
