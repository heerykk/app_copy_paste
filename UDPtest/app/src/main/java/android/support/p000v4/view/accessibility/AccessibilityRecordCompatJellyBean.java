package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.accessibility.AccessibilityRecord;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.view.accessibility.AccessibilityRecordCompatJellyBean */
class AccessibilityRecordCompatJellyBean {
    AccessibilityRecordCompatJellyBean() {
    }

    public static void setSource(Object obj, View view, int i) {
        Object record = obj;
        View root = view;
        int virtualDescendantId = i;
        Object obj2 = record;
        View view2 = root;
        int i2 = virtualDescendantId;
        ((AccessibilityRecord) record).setSource(root, virtualDescendantId);
    }
}
