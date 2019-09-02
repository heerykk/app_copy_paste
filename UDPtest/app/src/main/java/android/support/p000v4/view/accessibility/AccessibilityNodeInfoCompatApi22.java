package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(22)
@RequiresApi(22)
/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatApi22 */
class AccessibilityNodeInfoCompatApi22 {
    AccessibilityNodeInfoCompatApi22() {
    }

    public static Object getTraversalBefore(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getTraversalBefore();
    }

    public static void setTraversalBefore(Object obj, View view) {
        Object info = obj;
        View view2 = view;
        Object obj2 = info;
        View view3 = view2;
        ((AccessibilityNodeInfo) info).setTraversalBefore(view2);
    }

    public static void setTraversalBefore(Object obj, View view, int i) {
        Object info = obj;
        View root = view;
        int virtualDescendantId = i;
        Object obj2 = info;
        View view2 = root;
        int i2 = virtualDescendantId;
        ((AccessibilityNodeInfo) info).setTraversalBefore(root, virtualDescendantId);
    }

    public static Object getTraversalAfter(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getTraversalAfter();
    }

    public static void setTraversalAfter(Object obj, View view) {
        Object info = obj;
        View view2 = view;
        Object obj2 = info;
        View view3 = view2;
        ((AccessibilityNodeInfo) info).setTraversalAfter(view2);
    }

    public static void setTraversalAfter(Object obj, View view, int i) {
        Object info = obj;
        View root = view;
        int virtualDescendantId = i;
        Object obj2 = info;
        View view2 = root;
        int i2 = virtualDescendantId;
        ((AccessibilityNodeInfo) info).setTraversalAfter(root, virtualDescendantId);
    }
}
