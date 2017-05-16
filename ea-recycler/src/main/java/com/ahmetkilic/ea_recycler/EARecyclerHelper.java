package com.ahmetkilic.ea_recycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.animation.Interpolator;

import com.ahmetkilic.ea_recycler.animation.adapters.AlphaInAnimationAdapter;
import com.ahmetkilic.ea_recycler.animation.adapters.AnimationAdapter;
import com.ahmetkilic.ea_recycler.animation.adapters.ScaleInAnimationAdapter;
import com.ahmetkilic.ea_recycler.animation.adapters.SlideInBottomAnimationAdapter;
import com.ahmetkilic.ea_recycler.animation.adapters.SlideInLeftAnimationAdapter;
import com.ahmetkilic.ea_recycler.animation.adapters.SlideInRightAnimationAdapter;
import com.ahmetkilic.ea_recycler.animation.animators.BaseItemAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.FadeInAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.FadeInDownAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.FadeInLeftAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.FadeInRightAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.FadeInUpAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.FlipInBottomXAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.FlipInLeftYAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.FlipInRightYAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.FlipInTopXAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.LandingAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.OvershootInLeftAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.OvershootInRightAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.ScaleInAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.ScaleInBottomAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.ScaleInLeftAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.ScaleInRightAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.ScaleInTopAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.SlideInDownAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.SlideInLeftAnimator;
import com.ahmetkilic.ea_recycler.animation.animators.SlideInRightAnimator;
import com.ahmetkilic.ea_recycler.holders.BaseHolder;
import com.ahmetkilic.ea_recycler.holders.ProgressViewHolder;
import com.ahmetkilic.ea_recycler.interfaces.RecyclerFunctionsListener;
import com.ahmetkilic.ea_recycler.objects.LayoutObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AhmetPC on 15.05.2017.
 */

public class EARecyclerHelper implements EARecyclerView.LoadMoreListener {

    private EARecyclerView recyclerView;
    private EABaseAdapter recycleAdapter;
    private List<LayoutObject> objectList;

    private boolean loadMoreEnabled;
    private Context context;
    private RecyclerFunctionsListener listener;

    private int page;

    static final int PROGRESS_HORIZONTAL = 98;
    static final int PROGRESS_VERTICAL = 99;

    public EARecyclerHelper(Context context, EARecyclerView myRecyclerView) {
        this.context = context;
        this.recyclerView = myRecyclerView;
        init(0);
    }

    public EARecyclerHelper(Context context, EARecyclerView myRecyclerView, int progressViewLayoutId) {
        this.context = context;
        this.recyclerView = myRecyclerView;

        init(progressViewLayoutId);
    }

    private void init(int progressViewLayoutId) {

        this.objectList = new ArrayList<>();
        this.recycleAdapter = new EABaseAdapter(context, this.objectList, progressViewLayoutId);

        setOrientation(true);
        setHasFixedSize(true);

        if (context instanceof RecyclerFunctionsListener)
            this.listener = (RecyclerFunctionsListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement RecycleFunctionsListener");

        loadMoreEnabled = true;
        setPage(0);

        this.recyclerView.setLoadingListener(this);
        this.recyclerView.setAdapter(this.recycleAdapter);

        addNewType(ProgressViewHolder.class, R.layout.ea_recycler_progress_row_horizontal, PROGRESS_HORIZONTAL);
        addNewType(ProgressViewHolder.class, R.layout.ea_recycler_progress_row, PROGRESS_VERTICAL);
    }

    public void addNewType(Class<? extends BaseHolder> clazz, int layout_id, int type) {
        recycleAdapter.getFactory().addType(clazz, type);
        recycleAdapter.addType(layout_id);
    }

    private void showProgress(boolean show) {
        if (listener != null)
            listener.onProgressRequired(show);
    }

    private void showNoDataMessage(boolean show) {
        if (listener != null)
            listener.onErrorViewRequired(show);
    }

    @Override
    public void onLoadMore() {
        if (loadMoreEnabled && listener != null) {
            recycleAdapter.showLoadProgress();
            listener.onLoadMore(getPage());
        }
    }

    public void setReadyForItems(boolean loadMore) {
        showNoDataMessage(false);
        if (!loadMore) {
            setPage(0);
            showProgress(true);
        }
    }

    public void insertItems(List<LayoutObject> objects, boolean loadMore) {
        recyclerView.setLoadingMoreEnabled(loadMoreEnabled);
        setPage(getPage() + 1);
        if (objects != null) {
            showProgress(false);

            if (recyclerView.isLoadingData())
                recycleAdapter.hideLoadProgress();

            if (!loadMore)
                objectList.clear();

            objectList.addAll(objects);
            recycleAdapter.notifyDataSetChanged();

            if (!loadMore) {
                recycleAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
            } else
                recycleAdapter.notifyItemRangeInserted(getObjectList().size() - objects.size(), objects.size());

            recyclerView.loadMoreComplete();
        } else
            emptyItems(loadMore);
    }

    private void emptyItems(boolean loadMore) {
        if (recyclerView.isLoadingData())
            recycleAdapter.hideLoadProgress();
        if (!loadMore) {
            getObjectList().clear();
            recycleAdapter.notifyDataSetChanged();
            showNoDataMessage(true);
        }
        recyclerView.loadMoreComplete();
        recyclerView.setLoadingMoreEnabled(false);
        showProgress(false);
    }

    public List<LayoutObject> getObjectList() {
        return objectList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void addSingleItem(int position, LayoutObject object) {
        objectList.add(position, object);
        recycleAdapter.notifyItemInserted(position);
        recyclerView.smoothScrollToPosition(position);
    }

    public void updateItem(int position, LayoutObject item) {
        objectList.set(position, item);
        validateItem(position);
    }

    public void deleteItem(int position) {
        objectList.remove(position);
        recycleAdapter.notifyItemRemoved(position);
        recycleAdapter.notifyItemRangeChanged(position, objectList.size());
    }

    public void validateItem(int position) {
        recycleAdapter.notifyItemChanged(position);
    }

    public void disableLoadMore() {
        this.loadMoreEnabled = false;
        recyclerView.setLoadingMoreEnabled(false);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        recyclerView.setHasFixedSize(hasFixedSize);
    }

    public void setOrientation(boolean vertical) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,
                vertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL,
                false);

        recyclerView.setLayoutManager(layoutManager);
        recycleAdapter.setOrientation(vertical);
    }

    public enum ADAPTER_ANIMATION {
        ALPHA_IN, SCALE_IN, SLIDE_IN_LEFT, SLIDE_IN_RIGHT, SLIDE_IN_BOTTOM
    }

    public enum ITEM_ANIMATION {
        FADE_IN, FADE_IN_DOWN, FADE_IN_LEFT, FADE_IN_RIGHT, FADE_IN_UP, FLIP_IN_RIGHT, FLIP_IN_LEFT, FLIP_IN_TOP, FLIP_IN_BOTTOM,
        LANDING, OVERSHOOT_IN_LEFT, OVERSHOOT_IN_RIGHT, SCALE_IN, SCALE_IN_LEFT, SCALE_IN_RIGHT, SCALE_IN_TOP, SCALE_IN_BOTTOM,
        SLIDE_IN_LEFT, SLIDE_IN_RIGHT, SLIDE_IN_TOP, SLIDE_IN_BOTTOM
    }

    public void setAdapterAnimation(EARecyclerHelper.ADAPTER_ANIMATION adapter_animation, Interpolator interpolator, boolean first_only) {
        AnimationAdapter animationAdapter;
        switch (adapter_animation) {
            case ALPHA_IN:
                animationAdapter = new AlphaInAnimationAdapter(this.recycleAdapter);
                break;
            case SCALE_IN:
                animationAdapter = new ScaleInAnimationAdapter(this.recycleAdapter);
                break;
            case SLIDE_IN_LEFT:
                animationAdapter = new SlideInLeftAnimationAdapter(this.recycleAdapter);
                break;
            case SLIDE_IN_RIGHT:
                animationAdapter = new SlideInRightAnimationAdapter(this.recycleAdapter);
                break;
            case SLIDE_IN_BOTTOM:
                animationAdapter = new SlideInBottomAnimationAdapter(this.recycleAdapter);
                break;
            default:
                animationAdapter = new AlphaInAnimationAdapter(this.recycleAdapter);
                break;
        }
        animationAdapter.setFirstOnly(first_only);
        if (interpolator != null)
            animationAdapter.setInterpolator(interpolator);
        this.recyclerView.setAdapter(animationAdapter);
    }

    public void setItemAnimation(EARecyclerHelper.ITEM_ANIMATION item_animation, Interpolator interpolator) {
        BaseItemAnimator animator;
        switch (item_animation) {
            case FADE_IN:
                animator = new FadeInAnimator();
                break;
            case FADE_IN_LEFT:
                animator = new FadeInLeftAnimator();
                break;
            case FADE_IN_RIGHT:
                animator = new FadeInRightAnimator();
                break;
            case FADE_IN_UP:
                animator = new FadeInUpAnimator();
                break;
            case FADE_IN_DOWN:
                animator = new FadeInDownAnimator();
                break;
            case FLIP_IN_BOTTOM:
                animator = new FlipInBottomXAnimator();
                break;
            case FLIP_IN_LEFT:
                animator = new FlipInLeftYAnimator();
                break;
            case FLIP_IN_RIGHT:
                animator = new FlipInRightYAnimator();
                break;
            case FLIP_IN_TOP:
                animator = new FlipInTopXAnimator();
                break;
            case LANDING:
                animator = new LandingAnimator();
                break;
            case OVERSHOOT_IN_LEFT:
                animator = new OvershootInLeftAnimator();
                break;
            case OVERSHOOT_IN_RIGHT:
                animator = new OvershootInRightAnimator();
                break;
            case SCALE_IN:
                animator = new ScaleInAnimator();
                break;
            case SCALE_IN_LEFT:
                animator = new ScaleInLeftAnimator();
                break;
            case SCALE_IN_RIGHT:
                animator = new ScaleInRightAnimator();
                break;
            case SCALE_IN_BOTTOM:
                animator = new ScaleInBottomAnimator();
                break;
            case SCALE_IN_TOP:
                animator = new ScaleInTopAnimator();
                break;
            case SLIDE_IN_LEFT:
                animator = new SlideInLeftAnimator();
                break;
            case SLIDE_IN_BOTTOM:
                animator = new SlideInDownAnimator();
                break;
            case SLIDE_IN_TOP:
                animator = new ScaleInTopAnimator();
                break;
            case SLIDE_IN_RIGHT:
                animator = new SlideInRightAnimator();
                break;
            default:
                animator = new FadeInAnimator();
                break;
        }

        if (interpolator != null)
            animator.setInterpolator(interpolator);

        this.recyclerView.setItemAnimator(animator);
    }
}
