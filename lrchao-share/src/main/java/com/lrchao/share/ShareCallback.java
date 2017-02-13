package com.lrchao.share;

import com.lrchao.share.result.ShareResult;

/**
 * Description: 分享的成功 失败的回调
 *
 * @author liuranchao
 * @date 16/7/6 下午5:10
 */
public interface ShareCallback {

    /**
     * 分享成功后的回调
     *
     * @param shareResult ShareResult
     */
    void onShareSuccess(ShareResult shareResult);

    /**
     * 分享取消后的回调
     *
     * @param shareResult ShareResult
     */
    void onShareCancel(ShareResult shareResult);

    /**
     * 分享失败后的回调
     *
     * @param shareResult ShareResult
     */
    void onShareFailed(ShareResult shareResult);
}
