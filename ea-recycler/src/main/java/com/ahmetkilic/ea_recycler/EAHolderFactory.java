package com.ahmetkilic.ea_recycler;

import android.util.Log;
import android.view.View;

import com.ahmetkilic.ea_recycler.holders.BaseHolder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by AhmetPC on 8.12.2016.
 */

class EAHolderFactory {

    private List<Integer> types;
    private List<Class<? extends BaseHolder>> classes;

    EAHolderFactory() {
        this.classes = new ArrayList<>();
        this.types = new ArrayList<>();
    }

    BaseHolder getInstance(View v2, int view_type) {
        try {
            for (int i = 0; i < getTypes().size(); i++) {
                if (getTypes().get(i) == view_type) {
                    return classes.get(i).asSubclass(BaseHolder.class)
                            .getConstructor(View.class)
                            .newInstance(v2);
                }
            }
            Log.e("recycler_type_error", "HOLDER FACTORY--> View type is not exists : " + String.valueOf(view_type));
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    void addType(Class<? extends BaseHolder> clazz, int type) {
        getTypes().add(type);
        classes.add(clazz);
    }

    List<Integer> getTypes() {
        return types;
    }
}
