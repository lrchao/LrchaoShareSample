package com.lrchao.share;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.lrchao.share.platform.SharePlatform;
import com.lrchao.share.ui.dialog.BaseDialogFragment;
import com.lrchao.share.ui.dialog.OnGridViewItemClickListener;
import com.lrchao.share.ui.dialog.SharePlatformDialog;
import com.lrchao.share.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 分享对象实体
 *
 * @author liuranchao
 * @date 16/7/4 上午9:45
 */
public class ShareClient implements OnGridViewItemClickListener {

    /**
     * 要分享的平台列表
     */
    private List<SharePlatform> mPlatformList;

    /**
     * Dialog的标题
     */
    private CharSequence mDialogTitle;

    private boolean mDialogBtnTextShown;

    private OnShareItemClickListener mOnShareItemClickListener;

    //================================================
    // public
    //================================================

    /**
     * 初始化
     *
     * @param builder Builder
     */
    private ShareClient(Builder builder) {
        mDialogBtnTextShown = builder.dialogBtnTextShown;
        mPlatformList = builder.platformList;
        mOnShareItemClickListener = builder.onShareItemClickListener;
        ShareCallbackManager.getInstance().add(builder.shareCallback);
        mDialogTitle = builder.dialogTitle;
        ShareSDK.getInstance().setShowToast(builder.showToast);
    }

    public void show(Fragment fragment) {
        showDialog(fragment.getFragmentManager(), createShareDialog());
    }

    //================================================
    // private
    //================================================

    public void show(FragmentActivity activity) {
        showDialog(activity.getSupportFragmentManager(),
                createShareDialog());

    }

    private SharePlatformDialog createShareDialog() {
        SharePlatformDialog dialog = SharePlatformDialog.newInstance();
        dialog.setData(mPlatformList);
        dialog.setTitle(mDialogTitle);
        dialog.setDialogBtnTextShown(mDialogBtnTextShown);
        dialog.setOnGridViewItemClickListener(this);
        return dialog;
    }

    /**
     * 显示Dialog
     *
     * @param dialogFragment BaseDialogFragment
     */
    private void showDialog(FragmentManager fragmentManager, BaseDialogFragment dialogFragment) {

        try {
            String tag = dialogFragment.getClass().getSimpleName();

            FragmentTransaction ft = fragmentManager.beginTransaction();
            Fragment prev = fragmentManager.findFragmentByTag(tag);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            // 检查 是否isAdded()
            if (prev == null || (
                    !prev.isAdded() &&
                            !prev.isVisible() &&
                            !prev.isRemoving())) {
                dialogFragment.show(ft, tag);
            }

        } catch (IllegalStateException e) {
            LogUtils.wtf(e);
        }
    }

    @Override
    public void onGridViewItemClick(View view, int position) {

        if (position < mPlatformList.size()) {
            SharePlatform sharePlatform = mPlatformList.get(position);
            if (mOnShareItemClickListener != null) {
                mOnShareItemClickListener.onShareItemClick(sharePlatform);
            }
        }
    }

    public static final class Builder {

        List<SharePlatform> platformList = new ArrayList<>();

        OnShareItemClickListener onShareItemClickListener;

        ShareCallback shareCallback;

        CharSequence dialogTitle;

        /**
         * 操作结果是否显示toast
         */
        boolean showToast;

        boolean dialogBtnTextShown;

        public Builder dialogBtnTextShown(boolean show) {
            this.dialogBtnTextShown = show;
            return this;
        }

        public Builder dialogTitle(CharSequence dialogTitle) {
            this.dialogTitle = dialogTitle;
            return this;
        }

        public Builder platform(SharePlatform sharePlatform) {
            platformList.add(sharePlatform);
            return this;
        }

        public Builder onShareItemClickListener(OnShareItemClickListener listener) {
            this.onShareItemClickListener = listener;
            return this;
        }

        public Builder callback(ShareCallback callback) {
            this.shareCallback = callback;
            return this;
        }

        public Builder showToast(boolean showToast) {
            this.showToast = showToast;
            return this;
        }

        public ShareClient build() {
            return new ShareClient(this);
        }


    }

}
