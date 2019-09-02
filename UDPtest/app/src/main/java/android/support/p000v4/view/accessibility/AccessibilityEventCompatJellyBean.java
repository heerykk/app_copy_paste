package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.view.accessibility.AccessibilityEventCompatJellyBean */
class AccessibilityEventCompatJellyBean {
    AccessibilityEventCompatJellyBean() {
    }

    public static void setMovementGranularity(AccessibilityEvent accessibilityEvent, int i) {
        AccessibilityEvent event = accessibilityEvent;
        int granularity = i;
        AccessibilityEvent accessibilityEvent2 = event;
        int i2 = granularity;
        event.setMovementGranularity(granularity);
    }

    public static int getMovementGranularity(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        return event.getMovementGranularity();
    }

    public static void setAction(AccessibilityEvent accessibilityEvent, int i) {
        AccessibilityEvent event = accessibilityEvent;
        int action = i;
        AccessibilityEvent accessibilityEvent2 = event;
        int i2 = action;
        event.setAction(action);
    }

    public static int getAction(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        return event.getAction();
    }
}
