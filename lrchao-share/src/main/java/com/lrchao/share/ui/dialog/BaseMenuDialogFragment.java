package com.lrchao.share.ui.dialog;

import com.lrchao.share.R;

/**
 * Description: Menu Dialog的基类
 *
 * @author liuranchao
 * @date 16/7/4 上午10:47
 */
public abstract class BaseMenuDialogFragment extends BaseDialogFragment {

    @Override
    protected boolean isBottom() {
        return true;
    }

    @Override
    protected int getAnimation() {
        return R.style.DialogMenuAnimation;
    }
}
