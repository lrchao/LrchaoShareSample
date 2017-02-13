package com.lrchao.share.platform;


import com.lrchao.share.R;
import com.lrchao.share.ShareSDK;
import com.lrchao.share.content.ShareContent;
import com.lrchao.share.utils.Utils;

/**
 * Description: 分享到新浪微博
 *
 * @author liuranchao
 * @date 16/7/4 上午10:24
 */
public class SinaMicroBlog extends SharePlatform {

    public SinaMicroBlog(ShareContent shareContent) {
        super(shareContent);
    }

    @Override
    public void share() {
        Utils.navToSinaShareActivity(mShareContent);
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_sina_microblog;
    }

    @Override
    public String getTitle() {
        return ShareSDK.getInstance().getContext().getResources().getString(R.string.platform_sina_micro_blog);
    }
}
