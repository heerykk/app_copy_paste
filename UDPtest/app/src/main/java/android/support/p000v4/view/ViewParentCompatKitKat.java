package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewParent;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.view.ViewParentCompatKitKat */
class ViewParentCompatKitKat {
    ViewParentCompatKitKat() {
    }

    public static void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int i) {
        ViewParent parent = viewParent;
        View child = view;
        View source = view2;
        int changeType = i;
        ViewParent viewParent2 = parent;
        View view3 = child;
        View view4 = source;
        int i2 = changeType;
        parent.notifySubtreeAccessibilityStateChanged(child, source, changeType);
    }
}
