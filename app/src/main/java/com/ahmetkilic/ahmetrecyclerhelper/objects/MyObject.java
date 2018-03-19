package com.ahmetkilic.ahmetrecyclerhelper.objects;

import com.ahmetkilic.ea_recycler.interfaces.EATypeInterface;

/**
 * Created by AhmetPC on 15.05.2017.
 */

public class MyObject implements EATypeInterface {
    private String string1, string2;
    private int type;

    public MyObject(String string1, String string2, int type) {
        this.string1 = string1;
        this.string2 = string2;
        this.type = type;
    }

    public MyObject(String string1, String string2) {
        this.string1 = string1;
        this.string2 = string2;
        this.type = 1;
    }

    public String getString1() {
        return string1;
    }

    public String getString2() {
        return string2;
    }

    @Override
    public int getType() {
        return type;
    }
}
