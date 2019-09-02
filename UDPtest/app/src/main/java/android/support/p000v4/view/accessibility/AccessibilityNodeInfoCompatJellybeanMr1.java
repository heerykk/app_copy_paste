package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(17)
@RequiresApi(17)
/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatJellybeanMr1 */
class AccessibilityNodeInfoCompatJellybeanMr1 {
    AccessibilityNodeInfoCompatJellybeanMr1() {
    }

    public static void setLabelFor(Object obj, View view) {
        Object info = obj;
        View labeled = view;
        Object obj2 = info;
        View view2 = labeled;
        ((AccessibilityNodeInfo) info).setLabelFor(labeled);
    }

    public static void setLabelFor(Object obj, View view, int i) {
        Object info = obj;
        View root = view;
        int virtualDescendantId = i;
        Object obj2 = info;
        View view2 = root;
        int i2 = virtualDescendantId;
        ((AccessibilityNodeInfo) info).setLabelFor(root, virtualDescendantId);
    }

    public static Object getLabelFor(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getLabelFor();
    }

    public static void setLabeledBy(Object obj, View view) {
        Object info = obj;
        View labeled = view;
        Object obj2 = info;
        View view2 = labeled;
        ((AccessibilityNodeInfo) info).setLabeledBy(labeled);
    }

    public static void setLabeledBy(Object obj, View view, int i) {
        Object info = obj;
        View root = view;
        int virtualDescendantId = i;
        Object obj2 = info;
        View view2 = root;
        int i2 = virtualDescendantId;
        ((AccessibilityNodeInfo) info).setLabeledBy(root, virtualDescendantId);
    }

    public static Object getLabeledBy(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getLabeledBy();
    }
}
