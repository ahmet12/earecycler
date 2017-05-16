package com.ahmetkilic.ea_recycler.objects;

/**
 * Created by AhmetPC on 15.05.2017.
 */

public class LayoutObject {
    private int type;
    private Object object;

    public LayoutObject(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    public int getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }
}

