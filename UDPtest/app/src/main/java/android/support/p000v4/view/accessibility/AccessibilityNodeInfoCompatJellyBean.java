package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatJellyBean */
class AccessibilityNodeInfoCompatJellyBean {
    AccessibilityNodeInfoCompatJellyBean() {
    }

    public static void addChild(Object obj, View view, int i) {
        Object info = obj;
        View child = view;
        int virtualDescendantId = i;
        Object obj2 = info;
        View view2 = child;
        int i2 = virtualDescendantId;
        ((AccessibilityNodeInfo) info).addChild(child, virtualDescendantId);
    }

    public static void setSource(Object obj, View view, int i) {
        Object info = obj;
        View root = view;
        int virtualDescendantId = i;
        Object obj2 = info;
        View view2 = root;
        int i2 = virtualDescendantId;
        ((AccessibilityNodeInfo) info).setSource(root, virtualDescendantId);
    }

    public static boolean isVisibleToUser(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isVisibleToUser();
    }

    public static void setVisibleToUser(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setVisibleToUser(z);
    }

    public static boolean performAction(Object obj, int i, Bundle bundle) {
        Object info = obj;
        int action = i;
        Bundle arguments = bundle;
        Object obj2 = info;
        int i2 = action;
        Bundle bundle2 = arguments;
        return ((AccessibilityNodeInfo) info).performAction(action, arguments);
    }

    public static void setMovementGranularities(Object obj, int i) {
        Object info = obj;
        int granularities = i;
        Object obj2 = info;
        int i2 = granularities;
        ((AccessibilityNodeInfo) info).setMovementGranularities(granularities);
    }

    public static int getMovementGranularities(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getMovementGranularities();
    }

    public static Object obtain(View view, int i) {
        View root = view;
        int virtualDescendantId = i;
        View view2 = root;
        int i2 = virtualDescendantId;
        return AccessibilityNodeInfo.obtain(root, virtualDescendantId);
    }

    public static Object findFocus(Object obj, int i) {
        Object info = obj;
        int focus = i;
        Object obj2 = info;
        int i2 = focus;
        return ((AccessibilityNodeInfo) info).findFocus(focus);
    }

    public static Object focusSearch(Object obj, int i) {
        Object info = obj;
        int direction = i;
        Object obj2 = info;
        int i2 = direction;
        return ((AccessibilityNodeInfo) info).focusSearch(direction);
    }

    public static void setParent(Object obj, View view, int i) {
        Object info = obj;
        View root = view;
        int virtualDescendantId = i;
        Object obj2 = info;
        View view2 = root;
        int i2 = virtualDescendantId;
        ((AccessibilityNodeInfo) info).setParent(root, virtualDescendantId);
    }

    public static boolean isAccessibilityFocused(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isAccessibilityFocused();
    }

    public static void setAccesibilityFocused(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setAccessibilityFocused(z);
    }
}
