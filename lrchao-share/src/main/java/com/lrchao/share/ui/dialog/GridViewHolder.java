package com.lrchao.share.ui.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrchao.share.R;

/**
 * Description: GridView的ViewHolder
 *
 * @author liuranchao
 * @date 16/7/4 下午3:47
 */
public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mIvIcon;

    private TextView mTvName;
    private OnGridViewItemClickListener mOnGridViewItemClickListener;

    public GridViewHolder(View itemView) {
        super(itemView);
        mIvIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        itemView.setOnClickListener(this);
    }

    public ImageView getIvIcon() {
        return mIvIcon;
    }

    public void setIvIcon(ImageView ivIcon) {
        mIvIcon = ivIcon;
    }

    public TextView getTvName() {
        return mTvName;
    }

    public void setTvName(TextView tvName) {
        mTvName = tvName;
    }

    @Override
    public void onClick(View v) {
        if (mOnGridViewItemClickListener != null) {
            mOnGridViewItemClickListener.onGridViewItemClick(v, getAdapterPosition());
        }
    }

    public void setOnGridViewItemClickListener(OnGridViewItemClickListener onGridViewItemClickListener) {
        mOnGridViewItemClickListener = onGridViewItemClickListener;
    }


}
