package com.ppuser.client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by UPC on 2017/3/28.
 */

public class CommonAdapter<T> extends BaseAdapter {
    public static final int NORMAL = 0;
    public static final int HEADER = 1;
    public static final int BOTTOM = 2;
    private AdapterListener adapterListener;
    private List<T> list;
    private View mHeaderView, mBottomView;

    public CommonAdapter(Context context, AdapterListener adapterListener,List<T> list) {
        super(context);
        this.adapterListener = adapterListener;
        this.list=list;
    }

    public CommonAdapter(Context context, AdapterListener adapterListener) {
        super(context);
        this.adapterListener = adapterListener;
    }

    public void setDatas(List<T> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int count = (list == null ? 0 : list.size());
        if (mHeaderView != null) {
            count++;
        }
        if (mBottomView != null) {
            count++;
        }
        return count;
    }

    @Override
    public boolean getCache() {
        return adapterListener.getCache();
    }

    @Override
    public int getItemLayoutId() {
        return adapterListener.getItemLayoutId();
    }

    @Override
    public View getItemLayoutView(int viewType) {
        if (viewType==HEADER){
            return mHeaderView;
        }else {
            return mBottomView;
        }
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View itemView) {
        return adapterListener.getViewHolder(itemView);
    }

    @Override
    public void initItemData(RecyclerView.ViewHolder holder, int position) {
        if (!isHeaderView(position) && !isBottom(position)) {
            if (headerIsExit()) {
                position--;
            }
            adapterListener.initItemData(holder,position);
        }

    }

    public interface AdapterListener{
        public boolean getCache();
        public int getItemLayoutId();
        public RecyclerView.ViewHolder getViewHolder(View view);
        public void initItemData(RecyclerView.ViewHolder holder, int position);
    }

    public void setHeader(View headerView) {
        this.mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setBottomView(View bottomView) {
        this.mBottomView = bottomView;
        if (headerIsExit()) {
            notifyItemInserted(list.size() + 1);
        } else {
            notifyItemInserted(list.size());
        }
    }

    protected boolean headerIsExit() {
        return mHeaderView != null;
    }

    protected boolean haveBottom() {
        return mBottomView != null;
    }

    protected boolean isHeaderView(int pos) {
        return headerIsExit() && pos == 0;
    }

    protected boolean isBottom(int pos) {
        return haveBottom() && pos == getItemCount() - 1;
    }

    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return HEADER;
        } else if (isBottom(position)) {
            return BOTTOM;
        } else {
            return NORMAL;
        }
    }

}
