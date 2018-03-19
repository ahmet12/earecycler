package com.ahmetkilic.ea_recycler.objects;

import com.ahmetkilic.ea_recycler.interfaces.EATypeInterface;

/**
 * Created by AhmetPC on 16.05.2017.
 */

public class ProgressObject implements EATypeInterface{
    private int progressViewLayoutId;
    private int type;

    public ProgressObject(int progressViewLayoutId,int type) {
        this.progressViewLayoutId = progressViewLayoutId;
        this.type = type;
    }

    public int getProgressViewLayoutId() {
        return progressViewLayoutId;
    }

    @Override
    public int getType() {
        return type;
    }
}
