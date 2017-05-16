package com.ahmetkilic.ea_recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ahmetkilic.ea_recycler.holders.BaseHolder;
import com.ahmetkilic.ea_recycler.interfaces.RecyclerClickListener;
import com.ahmetkilic.ea_recycler.objects.LayoutObject;
import com.ahmetkilic.ea_recycler.objects.ProgressObject;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by AhmetPC on 8.12.2016.
 */

class EABaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private boolean VERTICAL;
    private List<LayoutObject> items;
    private List<Integer> layout_ids;
    private EAHolderFactory factory;
    private int progressViewLayoutId;

    EABaseAdapter(Context context, List<LayoutObject> items, int progressViewLayoutId) {
        this.context = context;
        this.progressViewLayoutId = progressViewLayoutId;

        if (!(context instanceof RecyclerClickListener))
            throw new RuntimeException(context.toString() + " must implement RecycleClickListener");

        this.items = items;
        layout_ids = new ArrayList<>();
        this.factory = new EAHolderFactory();
        VERTICAL = true;
    }

    void addType(int layout_id) {
        layout_ids.add(layout_id);
    }

    void setOrientation(boolean vertical) {
        this.VERTICAL = vertical;
    }

    void showLoadProgress() {
        if (VERTICAL)
            items.add(new LayoutObject(EARecyclerHelper.PROGRESS_VERTICAL, new ProgressObject(progressViewLayoutId)));
        else
            items.add(new LayoutObject(EARecyclerHelper.PROGRESS_HORIZONTAL, new ProgressObject(progressViewLayoutId)));

        notifyItemInserted(items.size() - 1);
    }

    void hideLoadProgress() {
        items.remove(items.size() - 1);
        notifyItemRemoved(items.size());
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        for (int i = 0; i < factory.getTypes().size(); i++)
            if (viewType == factory.getTypes().get(i)) {
                View v2 = inflater.inflate(layout_ids.get(i), parent, false);
                return getFactory().getInstance(v2, viewType);
            }

        Log.e("recycler_type_error", "BASE ADAPTER--> View type is not exists : " + String.valueOf(viewType));
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseHolder baseHolder = (BaseHolder) holder;
        baseHolder.loadItems(context, items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    EAHolderFactory getFactory() {
        return factory;
    }
}
