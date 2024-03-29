package android.support.p003v7.widget;

import android.support.p003v7.widget.RecyclerView.LayoutManager;
import android.support.p003v7.widget.RecyclerView.State;
import android.view.View;

/* renamed from: android.support.v7.widget.ScrollbarHelper */
class ScrollbarHelper {
    ScrollbarHelper() {
    }

    static int computeScrollOffset(State state, OrientationHelper orientationHelper, View view, View view2, LayoutManager layoutManager, boolean z, boolean z2) {
        int max;
        State state2 = state;
        OrientationHelper orientation = orientationHelper;
        View startChild = view;
        View endChild = view2;
        LayoutManager lm = layoutManager;
        State state3 = state2;
        OrientationHelper orientationHelper2 = orientation;
        View view3 = startChild;
        View view4 = endChild;
        LayoutManager layoutManager2 = lm;
        boolean smoothScrollbarEnabled = z;
        boolean reverseLayout = z2;
        if (lm.getChildCount() == 0 || state2.getItemCount() == 0 || startChild == null || endChild == null) {
            return 0;
        }
        int minPosition = Math.min(lm.getPosition(startChild), lm.getPosition(endChild));
        int maxPosition = Math.max(lm.getPosition(startChild), lm.getPosition(endChild));
        if (!reverseLayout) {
            max = Math.max(0, minPosition);
        } else {
            max = Math.max(0, (state2.getItemCount() - maxPosition) - 1);
        }
        int itemsBefore = max;
        if (!smoothScrollbarEnabled) {
            return itemsBefore;
        }
        return Math.round((((float) itemsBefore) * (((float) Math.abs(orientation.getDecoratedEnd(endChild) - orientation.getDecoratedStart(startChild))) / ((float) (Math.abs(lm.getPosition(startChild) - lm.getPosition(endChild)) + 1)))) + ((float) (orientation.getStartAfterPadding() - orientation.getDecoratedStart(startChild))));
    }

    static int computeScrollExtent(State state, OrientationHelper orientationHelper, View view, View view2, LayoutManager layoutManager, boolean z) {
        State state2 = state;
        OrientationHelper orientation = orientationHelper;
        View startChild = view;
        View endChild = view2;
        LayoutManager lm = layoutManager;
        State state3 = state2;
        OrientationHelper orientationHelper2 = orientation;
        View view3 = startChild;
        View view4 = endChild;
        LayoutManager layoutManager2 = lm;
        boolean smoothScrollbarEnabled = z;
        if (lm.getChildCount() == 0 || state2.getItemCount() == 0 || startChild == null || endChild == null) {
            return 0;
        }
        if (!smoothScrollbarEnabled) {
            return Math.abs(lm.getPosition(startChild) - lm.getPosition(endChild)) + 1;
        }
        return Math.min(orientation.getTotalSpace(), orientation.getDecoratedEnd(endChild) - orientation.getDecoratedStart(startChild));
    }

    static int computeScrollRange(State state, OrientationHelper orientationHelper, View view, View view2, LayoutManager layoutManager, boolean z) {
        State state2 = state;
        OrientationHelper orientation = orientationHelper;
        View startChild = view;
        View endChild = view2;
        LayoutManager lm = layoutManager;
        State state3 = state2;
        OrientationHelper orientationHelper2 = orientation;
        View view3 = startChild;
        View view4 = endChild;
        LayoutManager layoutManager2 = lm;
        boolean smoothScrollbarEnabled = z;
        if (lm.getChildCount() == 0 || state2.getItemCount() == 0 || startChild == null || endChild == null) {
            return 0;
        }
        if (!smoothScrollbarEnabled) {
            return state2.getItemCount();
        }
        return (int) ((((float) (orientation.getDecoratedEnd(endChild) - orientation.getDecoratedStart(startChild))) / ((float) (Math.abs(lm.getPosition(startChild) - lm.getPosition(endChild)) + 1))) * ((float) state2.getItemCount()));
    }
}
