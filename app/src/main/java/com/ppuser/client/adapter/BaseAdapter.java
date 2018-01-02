package com.ppuser.client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by UPC on 2017/3/28.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater layoutInflater;

    public BaseAdapter(Context context){
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(getItemLayoutId(),parent,false);
        RecyclerView.ViewHolder commonHolder=null;
        if (viewType== CommonAdapter.HEADER){
            commonHolder=getViewHolder(getItemLayoutView(viewType));
        }else if (viewType== CommonAdapter.BOTTOM){
            getItemLayoutView(viewType);
            commonHolder=getViewHolder(getItemLayoutView(viewType));
        }else {
            commonHolder=getViewHolder(view);
        }
        commonHolder.setIsRecyclable(getCache());
        return commonHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initItemData(holder,position);
    }

    public abstract boolean getCache();

    public abstract int getItemLayoutId();

    public abstract View getItemLayoutView(int viewType);

    public abstract RecyclerView.ViewHolder getViewHolder(View itemView);

    public abstract void initItemData(RecyclerView.ViewHolder holder,int position);

}
