package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.view.ViewCompatICS */
class ViewCompatICS {
    ViewCompatICS() {
    }

    public static boolean canScrollHorizontally(View view, int i) {
        View v = view;
        int direction = i;
        View view2 = v;
        int i2 = direction;
        return v.canScrollHorizontally(direction);
    }

    public static boolean canScrollVertically(View view, int i) {
        View v = view;
        int direction = i;
        View view2 = v;
        int i2 = direction;
        return v.canScrollVertically(direction);
    }

    public static void setAccessibilityDelegate(View view, @Nullable Object obj) {
        View v = view;
        Object delegate = obj;
        View view2 = v;
        Object obj2 = delegate;
        v.setAccessibilityDelegate((AccessibilityDelegate) delegate);
    }

    public static void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        View v = view;
        AccessibilityEvent event = accessibilityEvent;
        View view2 = v;
        AccessibilityEvent accessibilityEvent2 = event;
        v.onPopulateAccessibilityEvent(event);
    }

    public static void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        View v = view;
        AccessibilityEvent event = accessibilityEvent;
        View view2 = v;
        AccessibilityEvent accessibilityEvent2 = event;
        v.onInitializeAccessibilityEvent(event);
    }

    public static void onInitializeAccessibilityNodeInfo(View view, Object obj) {
        View v = view;
        Object info = obj;
        View view2 = v;
        Object obj2 = info;
        v.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo) info);
    }

    public static void setFitsSystemWindows(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        view2.setFitsSystemWindows(z);
    }
}
