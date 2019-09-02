package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.view.ViewGroupCompatIcs */
class ViewGroupCompatIcs {
    ViewGroupCompatIcs() {
    }

    public static boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        ViewGroup group = viewGroup;
        View child = view;
        AccessibilityEvent event = accessibilityEvent;
        ViewGroup viewGroup2 = group;
        View view2 = child;
        AccessibilityEvent accessibilityEvent2 = event;
        return group.onRequestSendAccessibilityEvent(child, event);
    }
}
