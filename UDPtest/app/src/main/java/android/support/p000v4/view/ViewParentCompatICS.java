package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.view.ViewParentCompatICS */
class ViewParentCompatICS {
    ViewParentCompatICS() {
    }

    public static boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
        ViewParent parent = viewParent;
        View child = view;
        AccessibilityEvent event = accessibilityEvent;
        ViewParent viewParent2 = parent;
        View view2 = child;
        AccessibilityEvent accessibilityEvent2 = event;
        return parent.requestSendAccessibilityEvent(child, event);
    }
}
