package android.support.p000v4.view;

import android.view.View;
import android.view.ViewGroup;

/* renamed from: android.support.v4.view.NestedScrollingParentHelper */
public class NestedScrollingParentHelper {
    private int mNestedScrollAxes;
    private final ViewGroup mViewGroup;

    public NestedScrollingParentHelper(ViewGroup viewGroup) {
        ViewGroup viewGroup2 = viewGroup;
        ViewGroup viewGroup3 = viewGroup2;
        this.mViewGroup = viewGroup2;
    }

    public void onNestedScrollAccepted(View view, View view2, int i) {
        int axes = i;
        View view3 = view;
        View view4 = view2;
        int i2 = axes;
        this.mNestedScrollAxes = axes;
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollAxes;
    }

    public void onStopNestedScroll(View view) {
        View view2 = view;
        this.mNestedScrollAxes = 0;
    }
}
