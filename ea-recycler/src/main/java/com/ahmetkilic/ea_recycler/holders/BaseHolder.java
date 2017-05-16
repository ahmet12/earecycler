package com.ahmetkilic.ea_recycler.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/*
 * Created by AhmetPC on 7.11.2016.
 */
public abstract class BaseHolder extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
    }

    public abstract void loadItems(Context context, Object object, int position);
}
