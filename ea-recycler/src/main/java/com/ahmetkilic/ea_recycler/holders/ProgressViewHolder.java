package com.ahmetkilic.ea_recycler.holders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ahmetkilic.ea_recycler.R;
import com.ahmetkilic.ea_recycler.objects.ProgressObject;

public class ProgressViewHolder extends BaseHolder {

    private LinearLayout holder;

    public ProgressViewHolder(View itemView) {
        super(itemView);
        holder = (LinearLayout) itemView;
    }

    @Override
    public void loadItems(Context context, Object object, int position) {
        int progressViewResId = 0;
        if (object != null) {
            ProgressObject progressObject = (ProgressObject) object;
            progressViewResId = progressObject.getProgressViewLayoutId();
        }

        View progress = LayoutInflater.from(context).inflate(
                progressViewResId == 0 ? R.layout.ea_progress_item : progressViewResId, holder, false);

        holder.removeAllViews();
        // if (holder.getChildCount() < 1)
        holder.addView(progress);
    }
}