package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityRecord;

@TargetApi(15)
@RequiresApi(15)
/* renamed from: android.support.v4.view.accessibility.AccessibilityRecordCompatIcsMr1 */
class AccessibilityRecordCompatIcsMr1 {
    AccessibilityRecordCompatIcsMr1() {
    }

    public static int getMaxScrollX(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getMaxScrollX();
    }

    public static int getMaxScrollY(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getMaxScrollY();
    }

    public static void setMaxScrollX(Object obj, int i) {
        Object record = obj;
        int maxScrollX = i;
        Object obj2 = record;
        int i2 = maxScrollX;
        ((AccessibilityRecord) record).setMaxScrollX(maxScrollX);
    }

    public static void setMaxScrollY(Object obj, int i) {
        Object record = obj;
        int maxScrollY = i;
        Object obj2 = record;
        int i2 = maxScrollY;
        ((AccessibilityRecord) record).setMaxScrollY(maxScrollY);
    }
}
