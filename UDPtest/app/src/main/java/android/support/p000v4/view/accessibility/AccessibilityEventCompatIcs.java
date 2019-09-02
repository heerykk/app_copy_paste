package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityRecord;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.view.accessibility.AccessibilityEventCompatIcs */
class AccessibilityEventCompatIcs {
    AccessibilityEventCompatIcs() {
    }

    public static int getRecordCount(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        return event.getRecordCount();
    }

    public static void appendRecord(AccessibilityEvent accessibilityEvent, Object obj) {
        AccessibilityEvent event = accessibilityEvent;
        Object record = obj;
        AccessibilityEvent accessibilityEvent2 = event;
        Object obj2 = record;
        event.appendRecord((AccessibilityRecord) record);
    }

    public static Object getRecord(AccessibilityEvent accessibilityEvent, int i) {
        AccessibilityEvent event = accessibilityEvent;
        int index = i;
        AccessibilityEvent accessibilityEvent2 = event;
        int i2 = index;
        return event.getRecord(index);
    }

    public static void setScrollable(AccessibilityEvent accessibilityEvent, boolean z) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        event.setScrollable(z);
    }
}
