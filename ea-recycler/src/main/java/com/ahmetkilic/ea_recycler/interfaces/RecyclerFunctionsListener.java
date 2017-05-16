package com.ahmetkilic.ea_recycler.interfaces;

/*
 * Created by AhmetPC on 8.12.2016.
 */

public interface RecyclerFunctionsListener {
    void onProgressRequired(boolean show);

    void onErrorViewRequired(boolean show);

    void onLoadMore(int page);
}
