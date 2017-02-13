package com.lrchao.share.ui.view;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: ViewPager的adapter
 *
 * @author liuranchao
 * @date 16/7/4 下午4:35
 */
public class ViewPagerAdapter extends PagerAdapter {


    private List<View> mDataSource;

    public ViewPagerAdapter(List<View> listViews) {
        setDataSource(listViews);
    }

    /**
     * 设置数据源
     *
     * @param dataSource 数据源
     */
    private void setDataSource(List<View> dataSource) {
        if (dataSource == null) {
            mDataSource = new ArrayList<>();
        } else {
            mDataSource = dataSource;
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mDataSource.get(position);

        if (view != null) {
            container.addView(view, 0);
        }
        return view;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container != null && object != null) {
            container.removeView((View) object);
        }
    }

    /**
     * 清除view object的引用， 以防内存泄露；
     */
    @SuppressWarnings("unused")
    public void clearViews() {
        if (mDataSource != null) {
            mDataSource.clear();
        }
        mDataSource = null;
    }

}
