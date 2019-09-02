package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.view.ViewCompatKitKat */
class ViewCompatKitKat {
    ViewCompatKitKat() {
    }

    public static int getAccessibilityLiveRegion(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getAccessibilityLiveRegion();
    }

    public static void setAccessibilityLiveRegion(View view, int i) {
        View view2 = view;
        int mode = i;
        View view3 = view2;
        int i2 = mode;
        view2.setAccessibilityLiveRegion(mode);
    }

    public static boolean isLaidOut(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.isLaidOut();
    }

    public static boolean isAttachedToWindow(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.isAttachedToWindow();
    }

    public static boolean isLayoutDirectionResolved(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.isLayoutDirectionResolved();
    }
}
