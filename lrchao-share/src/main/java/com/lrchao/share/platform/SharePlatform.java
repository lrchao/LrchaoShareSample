package com.lrchao.share.platform;


import com.lrchao.share.content.ShareContent;

/**
 * Description: 分享平台
 *
 * @author liuranchao
 * @date 16/7/4 上午11:12
 */
public abstract class SharePlatform {

    protected ShareContent mShareContent;
    /**
     * Dialog显示的标题文本
     */
    private String mTitle;
    /**
     * Dialog显示的图标
     */
    private int mIcon;

    public SharePlatform(ShareContent shareContent) {
        mShareContent = shareContent;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public abstract void share();

}
