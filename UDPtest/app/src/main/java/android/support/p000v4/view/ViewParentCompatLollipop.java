package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.view.ViewParentCompatLollipop */
class ViewParentCompatLollipop {
    private static final String TAG = "ViewParentCompat";

    ViewParentCompatLollipop() {
    }

    public static boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int i) {
        ViewParent parent = viewParent;
        View child = view;
        View target = view2;
        int nestedScrollAxes = i;
        ViewParent viewParent2 = parent;
        View view3 = child;
        View view4 = target;
        int i2 = nestedScrollAxes;
        try {
            return parent.onStartNestedScroll(child, target, nestedScrollAxes);
        } catch (AbstractMethodError e) {
            AbstractMethodError abstractMethodError = e;
            int e2 = Log.e(TAG, "ViewParent " + parent + " does not implement interface " + "method onStartNestedScroll", e);
            return false;
        }
    }

    public static void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int i) {
        ViewParent parent = viewParent;
        View child = view;
        View target = view2;
        int nestedScrollAxes = i;
        ViewParent viewParent2 = parent;
        View view3 = child;
        View view4 = target;
        int i2 = nestedScrollAxes;
        try {
            parent.onNestedScrollAccepted(child, target, nestedScrollAxes);
        } catch (AbstractMethodError e) {
            AbstractMethodError abstractMethodError = e;
            int e2 = Log.e(TAG, "ViewParent " + parent + " does not implement interface " + "method onNestedScrollAccepted", e);
        }
    }

    public static void onStopNestedScroll(ViewParent viewParent, View view) {
        ViewParent parent = viewParent;
        View target = view;
        ViewParent viewParent2 = parent;
        View view2 = target;
        try {
            parent.onStopNestedScroll(target);
        } catch (AbstractMethodError e) {
            AbstractMethodError abstractMethodError = e;
            int e2 = Log.e(TAG, "ViewParent " + parent + " does not implement interface " + "method onStopNestedScroll", e);
        }
    }

    public static void onNestedScroll(ViewParent viewParent, View view, int i, int i2, int i3, int i4) {
        ViewParent parent = viewParent;
        View target = view;
        int dxConsumed = i;
        int dyConsumed = i2;
        int dxUnconsumed = i3;
        int dyUnconsumed = i4;
        ViewParent viewParent2 = parent;
        View view2 = target;
        int i5 = dxConsumed;
        int i6 = dyConsumed;
        int i7 = dxUnconsumed;
        int i8 = dyUnconsumed;
        try {
            parent.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        } catch (AbstractMethodError e) {
            AbstractMethodError abstractMethodError = e;
            int e2 = Log.e(TAG, "ViewParent " + parent + " does not implement interface " + "method onNestedScroll", e);
        }
    }

    public static void onNestedPreScroll(ViewParent viewParent, View view, int i, int i2, int[] iArr) {
        ViewParent parent = viewParent;
        View target = view;
        int dx = i;
        int dy = i2;
        int[] consumed = iArr;
        ViewParent viewParent2 = parent;
        View view2 = target;
        int i3 = dx;
        int i4 = dy;
        int[] iArr2 = consumed;
        try {
            parent.onNestedPreScroll(target, dx, dy, consumed);
        } catch (AbstractMethodError e) {
            AbstractMethodError abstractMethodError = e;
            int e2 = Log.e(TAG, "ViewParent " + parent + " does not implement interface " + "method onNestedPreScroll", e);
        }
    }

    public static boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean z) {
        ViewParent parent = viewParent;
        View target = view;
        float velocityX = f;
        float velocityY = f2;
        ViewParent viewParent2 = parent;
        View view2 = target;
        float f3 = velocityX;
        float f4 = velocityY;
        try {
            return parent.onNestedFling(target, velocityX, velocityY, z);
        } catch (AbstractMethodError e) {
            AbstractMethodError abstractMethodError = e;
            int e2 = Log.e(TAG, "ViewParent " + parent + " does not implement interface " + "method onNestedFling", e);
            return false;
        }
    }

    public static boolean onNestedPreFling(ViewParent viewParent, View view, float f, float f2) {
        ViewParent parent = viewParent;
        View target = view;
        float velocityX = f;
        float velocityY = f2;
        ViewParent viewParent2 = parent;
        View view2 = target;
        float f3 = velocityX;
        float f4 = velocityY;
        try {
            return parent.onNestedPreFling(target, velocityX, velocityY);
        } catch (AbstractMethodError e) {
            AbstractMethodError abstractMethodError = e;
            int e2 = Log.e(TAG, "ViewParent " + parent + " does not implement interface " + "method onNestedPreFling", e);
            return false;
        }
    }
}
