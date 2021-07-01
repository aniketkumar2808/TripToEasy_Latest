package com.TripToEasy.main.adapter.common;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ptblr-1195 on 21/2/18.
 */

public abstract class CommonViewHolder<T> extends RecyclerView.ViewHolder
        implements View.OnClickListener  {

    public CommonViewHolder(ViewGroup parent, @LayoutRes int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        itemView.setOnClickListener(this);
    }

    public CommonViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {
        onItemSelected(getAdapterPosition());
    }

    public abstract void onItemSelected(int position);

    public View getView(){
        return itemView;
    }

}