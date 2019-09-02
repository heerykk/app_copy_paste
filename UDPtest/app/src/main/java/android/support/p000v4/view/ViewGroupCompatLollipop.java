package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.view.ViewGroupCompatLollipop */
class ViewGroupCompatLollipop {
    ViewGroupCompatLollipop() {
    }

    public static void setTransitionGroup(ViewGroup viewGroup, boolean z) {
        ViewGroup group = viewGroup;
        ViewGroup viewGroup2 = group;
        group.setTransitionGroup(z);
    }

    public static boolean isTransitionGroup(ViewGroup viewGroup) {
        ViewGroup group = viewGroup;
        ViewGroup viewGroup2 = group;
        return group.isTransitionGroup();
    }

    public static int getNestedScrollAxes(ViewGroup viewGroup) {
        ViewGroup group = viewGroup;
        ViewGroup viewGroup2 = group;
        return group.getNestedScrollAxes();
    }
}
