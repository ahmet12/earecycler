package com.ahmetkilic.ahmetrecyclerhelper;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.ahmetkilic.ahmetrecyclerhelper.holders.MyRecyclerHolder;
import com.ahmetkilic.ahmetrecyclerhelper.holders.MyRecyclerHolder2;
import com.ahmetkilic.ahmetrecyclerhelper.objects.MyObject;
import com.ahmetkilic.ahmetrecyclerhelper.objects.MyObject2;
import com.ahmetkilic.ea_recycler.EARecyclerHelper;
import com.ahmetkilic.ea_recycler.EARecyclerView;
import com.ahmetkilic.ea_recycler.interfaces.RecyclerClickListener;
import com.ahmetkilic.ea_recycler.interfaces.RecyclerFunctionsListener;
import com.ahmetkilic.ea_recycler.objects.LayoutObject;
import com.ahmetkilic.eaprogress.widgets.CrystalPreloader;
import com.ahmetkilic.eaprogress.widgets.EAProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerFunctionsListener, RecyclerClickListener, View.OnClickListener {

    private EARecyclerHelper helper;
    private EAProgressDialog progressDialog;

    //You need to implement the RecyclerFunctionsListener. RecyclerClickListener is optional.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new EAProgressDialog(this);
        progressDialog.addProgress(CrystalPreloader.Style.LINE_SCALE, CrystalPreloader.Size.SMALL);

        EARecyclerView recyclerView = (EARecyclerView) findViewById(R.id.ea_recycler);

        //3rd parameter is optional. ProgressView for loading more items animation.
        // helper = new EARecyclerHelper(this, recyclerView);
        helper = new EARecyclerHelper(this, recyclerView, R.layout.my_progress_item);
        //helper.disableLoadMore();

       // animations
        helper.setAdapterAnimation(EARecyclerHelper.ADAPTER_ANIMATION.SLIDE_IN_LEFT, new LinearInterpolator(), true);
       // helper.setItemAnimation(EARecyclerHelper.ITEM_ANIMATION.SCALE_IN, new AccelerateInterpolator());

        // You need an object , a viewHolder which extends the BaseHolder , an integer as type
        helper.addNewType(MyRecyclerHolder.class, R.layout.my_recycler_item, 1);
        helper.addNewType(MyRecyclerHolder2.class, R.layout.my_recycler_item_2, 2);

        addItems(0, true);
    }

    @Override
    public void onProgressRequired(boolean show) {
        showProgress(show);
    }

    @Override
    public void onErrorViewRequired(boolean show) {
        findViewById(R.id.error_view).setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoadMore(int page) {
        addItems(page, false);
    }

    private void addItems(final int page, boolean with_error) {
        //Use this before you pull data from the server or elsewhere
        helper.setReadyForItems(page != 0);

        if (with_error)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    helper.insertItems(null, page != 0);
                }
            }, 2000);
        else
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<LayoutObject> objectList = new ArrayList<>();

                    for (int i = helper.getObjectList().size(); i < helper.getObjectList().size() + 20; i++) {
                        // Put your objects into LayoutObject with the type code you defined while adding new type to helper
                        LayoutObject layoutObject = new LayoutObject(1, new MyObject("Title " + String.valueOf(i), " Content " + String.valueOf(i)));
                        LayoutObject layoutObject2 = new LayoutObject(2, new MyObject2(" Content -> " + String.valueOf(i)));
                        objectList.add(layoutObject);
                        objectList.add(layoutObject2);
                    }
                    // Insert the array of LayoutObjects to  helper. The second parameter is whether the data is the first data or more data
                    helper.insertItems(objectList, page != 0);
                }
            }, 2000);
    }

    private void showProgress(boolean show) {
        if (show)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }

    @Override
    public void onRecycleItemClick(int type, int position) {
        String string1 = ((MyObject) helper.getObjectList().get(position).getObject()).getString1();
        Toast.makeText(this, string1 + " Clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_retry) {
            addItems(0, false);
        }
    }
}
