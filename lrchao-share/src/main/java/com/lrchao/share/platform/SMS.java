package com.lrchao.share.platform;

import android.content.Intent;
import android.net.Uri;

import com.lrchao.share.R;
import com.lrchao.share.ShareSDK;
import com.lrchao.share.content.SMSContent;
import com.lrchao.share.content.ShareContent;

/**
 * Description: 通过短信分享
 *
 * @author liuranchao
 * @date 16/9/19 下午5:13
 */
public class SMS extends SharePlatform {

    private SMSContent mSMSContent;

    public SMS(ShareContent shareContent) {
        super(shareContent);
        mSMSContent = (SMSContent) shareContent;
    }

    @Override
    public void share() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + mSMSContent.getPhoneNumber()));
        intent.putExtra("sms_body", mSMSContent.getText());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ShareSDK.getInstance().getContext().startActivity(intent);
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_sms;
    }

    @Override
    public String getTitle() {
        return ShareSDK.getInstance().getContext().getResources().getString(R.string.platform_sms);
    }
}
