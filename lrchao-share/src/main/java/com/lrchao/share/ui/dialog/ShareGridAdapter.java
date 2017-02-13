package com.lrchao.share.ui.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lrchao.share.R;
import com.lrchao.share.platform.SharePlatform;

import java.util.List;


/**
 * Description: 分享的dialog的Gird Adapter
 *
 * @author liuranchao
 * @date 16/7/4 下午3:44
 */
public class ShareGridAdapter extends RecyclerView.Adapter<GridViewHolder> implements
        OnGridViewItemClickListener {

    private static final String TAG = "ShareGridAdapter";

    private List<SharePlatform> mData;

    private boolean mDialogBtnTextShown;
    private OnGridViewItemClickListener mOnGridViewItemClickListener;

    public void setData(List<SharePlatform> data) {
        mData = data;
    }

    public void setOnGridViewItemClickListener(OnGridViewItemClickListener onGridViewItemClickListener) {
        mOnGridViewItemClickListener = onGridViewItemClickListener;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_grid_item, parent, false);

        GridViewHolder vh = new GridViewHolder(v);
        vh.setOnGridViewItemClickListener(this);
        return vh;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        SharePlatform platform = mData.get(position);

        if (platform != null) {
            holder.getIvIcon().setImageResource(platform.getIcon());
            holder.getTvName().setText(platform.getTitle());

            if (mDialogBtnTextShown) {
                holder.getTvName().setVisibility(View.VISIBLE);
            } else {
                holder.getTvName().setVisibility(View.INVISIBLE);
            }
        }
    }


    @Override
    public void onGridViewItemClick(View view, int position) {
        if (mOnGridViewItemClickListener != null) {
            mOnGridViewItemClickListener.onGridViewItemClick(view, position);
        }
        SharePlatform sharePlatform = mData.get(position);
        if (sharePlatform != null) {
            sharePlatform.share();
        }
    }

    public void setDialogBtnTextShown(boolean dialogBtnTextShown) {
        mDialogBtnTextShown = dialogBtnTextShown;
    }
}
