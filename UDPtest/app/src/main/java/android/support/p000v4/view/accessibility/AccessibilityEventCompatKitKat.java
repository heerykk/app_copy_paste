package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.view.accessibility.AccessibilityEventCompatKitKat */
class AccessibilityEventCompatKitKat {
    AccessibilityEventCompatKitKat() {
    }

    public static void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int i) {
        AccessibilityEvent event = accessibilityEvent;
        int changeTypes = i;
        AccessibilityEvent accessibilityEvent2 = event;
        int i2 = changeTypes;
        event.setContentChangeTypes(changeTypes);
    }

    public static int getContentChangeTypes(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        return event.getContentChangeTypes();
    }
}
