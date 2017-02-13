package com.lrchao.share.ui.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.lrchao.share.R;
import com.lrchao.share.platform.SharePlatform;
import com.lrchao.share.ui.dialog.OnGridViewItemClickListener;
import com.lrchao.share.ui.dialog.ShareGridAdapter;

import java.util.List;


/**
 * Description: ViewPager单页
 *
 * @author liuranchao
 * @date 16/7/4 下午5:42
 */
public class ViewPagerItemView extends LinearLayout {


    private RecyclerView mRecyclerView;

    private int mColumnCount;

    private OnGridViewItemClickListener mOnGridViewItemClickListener;

    private boolean mDialogBtnTextShown;

    public ViewPagerItemView(Context context) {
        super(context);
        init(context);
    }

    public ViewPagerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setOnGridViewItemClickListener(OnGridViewItemClickListener onGridViewItemClickListener) {
        mOnGridViewItemClickListener = onGridViewItemClickListener;
    }

    public void setColumnCount(int columnCount) {
        mColumnCount = columnCount;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_pager_item, this);
        setOrientation(VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
    }

    public void bindView(List<SharePlatform> pagerItemData) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        ShareGridAdapter gridAdapter = new ShareGridAdapter();
        gridAdapter.setDialogBtnTextShown(mDialogBtnTextShown);
        gridAdapter.setOnGridViewItemClickListener(mOnGridViewItemClickListener);
        gridAdapter.setData(pagerItemData);
        mRecyclerView.setAdapter(gridAdapter);
    }

    public void setDialogBtnTextShown(boolean dialogBtnTextShown) {
        mDialogBtnTextShown = dialogBtnTextShown;
    }
}
