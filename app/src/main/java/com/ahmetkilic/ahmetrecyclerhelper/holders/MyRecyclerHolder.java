package com.ahmetkilic.ahmetrecyclerhelper.holders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ahmetkilic.ahmetrecyclerhelper.R;
import com.ahmetkilic.ahmetrecyclerhelper.objects.MyObject;
import com.ahmetkilic.ea_recycler.holders.BaseHolder;
import com.ahmetkilic.ea_recycler.interfaces.RecyclerClickListener;
import com.ahmetkilic.ea_recycler.objects.LayoutObject;

/**
 * Created by AhmetPC on 15.05.2017.
 */

public class MyRecyclerHolder extends BaseHolder {

    private View main;
    private TextView tv1, tv2;
    private RecyclerClickListener clickListener;

    public MyRecyclerHolder(View itemView) {
        super(itemView);
        main = itemView.findViewById(R.id.ly_main);
        tv1 = (TextView) itemView.findViewById(R.id.tv_1);
        tv2 = (TextView) itemView.findViewById(R.id.tv_2);
    }

    @Override
    public void loadItems(Context context, Object object, int position) {
        if (context instanceof RecyclerClickListener)
            clickListener = (RecyclerClickListener) context;

        MyObject myObject = (MyObject) ((LayoutObject) object).getObject();

        if (clickListener != null) {
            final int final_pos = position;
            getMain().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onRecycleItemClick(0, final_pos);
                }
            });
        }

        getTv1().setText(myObject.getString1());
        getTv2().setText(myObject.getString2());
    }

    public TextView getTv1() {
        return tv1;
    }

    public TextView getTv2() {
        return tv2;
    }

    public View getMain() {
        return main;
    }
}
