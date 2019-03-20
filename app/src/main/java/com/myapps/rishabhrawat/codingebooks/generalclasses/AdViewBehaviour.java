package com.myapps.rishabhrawat.codingebooks.generalclasses;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdView;

public class AdViewBehaviour extends CoordinatorLayout.Behavior<AdView> {


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AdView child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       AdView child, @NonNull
                                               View directTargetChild, @NonNull View target,
                                       int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AdView child,
                               @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed,
                               @ViewCompat.NestedScrollType int type) {
        try {
            child.setTranslationY(Math.max(0f, Math.min(Float.parseFloat(String.valueOf(child.getHeight() / child.getHeight())), child.getTranslationY() + dyConsumed / 2)));
        } catch (Exception ex) {
            Log.d("Rishabh", "ERROR COME " + ex);
        }
    }
}
