package com.lrchao.share.ui.dialog;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lrchao.share.R;
import com.lrchao.share.platform.SharePlatform;
import com.lrchao.share.ui.view.CircleIndicator;
import com.lrchao.share.ui.view.ViewPagerAdapter;
import com.lrchao.share.ui.view.ViewPagerItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 选择分享的dialog
 *
 * @author liuranchao
 * @date 16/7/4 上午10:48
 */
public class SharePlatformDialog extends BaseMenuDialogFragment implements View.OnClickListener,
        OnGridViewItemClickListener {

    private static final String TAG = "ShareChooseDialog";

    private List<SharePlatform> mData;

    private CharSequence mTitle;

    private boolean mDialogBtnTextShown;

    /**
     * 列数
     */
    private int mColumnCount = 3;

    /**
     * 行数
     */
    private int mRowCount = 2;

    private OnGridViewItemClickListener mOnGridViewItemClickListener;

    public static SharePlatformDialog newInstance() {
        return new SharePlatformDialog();
    }

    public void setOnGridViewItemClickListener(OnGridViewItemClickListener onGridViewItemClickListener) {
        mOnGridViewItemClickListener = onGridViewItemClickListener;
    }

    public void setData(List<SharePlatform> data) {
        mData = data;
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.dialog_share_choose;
    }

    @Override
    protected void initView(View parentView) {
        ViewPager viewpager = (ViewPager) parentView.findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) parentView.findViewById(R.id.indicator);

        // 每页多少个
        int countPerPage = getCountPerPage(mColumnCount, mRowCount);

        // 一共多少页
        int pageCount = getPagerCount(mData, countPerPage);

        List<View> pagerViewList = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            ViewPagerItemView itemView = new ViewPagerItemView(getContext());
            itemView.setDialogBtnTextShown(mDialogBtnTextShown);
            itemView.setOnGridViewItemClickListener(this);
            itemView.setColumnCount(mColumnCount);
            itemView.bindView(getPagerItemData(mData, i, countPerPage));
            pagerViewList.add(itemView);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(pagerViewList);
        viewpager.setAdapter(adapter);
        indicator.setViewPager(viewpager);

        parentView.findViewById(R.id.btn_cancel).setOnClickListener(this);

        TextView tvTitle = (TextView) parentView.findViewById(R.id.tv_title);

        if (TextUtils.isEmpty(mTitle)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(mTitle);
        }

        if (pageCount > 1) {
            indicator.setVisibility(View.VISIBLE);
        } else {
            indicator.setVisibility(View.GONE);
        }
    }


    /**
     * 获取 一共几页
     */
    private int getPagerCount(List<SharePlatform> data, int count) {

        if (data != null) {
            // 整除的页数
            int a = data.size() / count;
            // 余数
            int b = data.size() % count;
            return b > 0 ? a + 1 : a;
        }
        return 0;
    }

    /**
     * 获取每页多少个
     */
    private int getCountPerPage(int columnCount, int rowCount) {
        return columnCount * rowCount;
    }

    /**
     * 获取开始的索引
     *
     * @param page         取第几页, 从0开始
     * @param perPageCount 每页多少个
     */
    private int getBeginIndex(int page, int perPageCount) {
        return perPageCount * page;
    }

    /**
     * 获取当前页的数据
     *
     * @param sourceData   总数据
     * @param page         第几页
     * @param perPageCount 每页多少个
     */
    private List<SharePlatform> getPagerItemData(List<SharePlatform> sourceData, int page, int perPageCount) {
        List<SharePlatform> data = new ArrayList<>();

        int beginIndex = getBeginIndex(page, perPageCount);

        for (int i = beginIndex; i < sourceData.size() && i < beginIndex + perPageCount; i++) {
            data.add(sourceData.get(i));
        }
        return data;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancel) {
            dismissDialog();
        }
    }

    @Override
    public void onGridViewItemClick(View view, int position) {
        dismissDialog();
        if (mOnGridViewItemClickListener != null) {
            mOnGridViewItemClickListener.onGridViewItemClick(view, position);
        }
    }

    public void setTitle(CharSequence dialogTitle) {
        mTitle = dialogTitle;
    }

    public void setDialogBtnTextShown(boolean dialogBtnTextShown) {
        mDialogBtnTextShown = dialogBtnTextShown;
    }
}

