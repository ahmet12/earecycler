package com.ahmetkilic.ahmetrecyclerhelper.objects;

import com.ahmetkilic.ea_recycler.interfaces.EATypeInterface;

/**
 * Created by AhmetPC on 15.05.2017.
 */

public class MyObject2 implements EATypeInterface{
    private String string1;
    private int type;

    public MyObject2(String string1,int type) {
        this.string1 = string1;
        this.type = type;
    }

    public MyObject2(String string1) {
        this.string1 = string1;
        this.type = 2;
    }

    public String getString1() {
        return string1;
    }

    @Override
    public int getType() {
        return type;
    }
}
