package com.lrchao.share.ui.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lrchao.share.R;


/**
 * Description: GirdView的item view
 *
 * @author liuranchao
 * @date 16/7/4 下午3:15
 */
public class GridItemView extends LinearLayout {

    private ImageView mIvIcon;

    private TextView mTvName;

    public GridItemView(Context context) {
        super(context);
        init(context);
    }

    public GridItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_grid_item, this);
        setOrientation(VERTICAL);
        mIvIcon = (ImageView) findViewById(R.id.iv_icon);
        mTvName = (TextView) findViewById(R.id.tv_name);
    }

    public void bindView(@StringRes int stringId, @DrawableRes int drawableId) {
        mIvIcon.setImageResource(drawableId);
        mTvName.setText(stringId);
    }


}
