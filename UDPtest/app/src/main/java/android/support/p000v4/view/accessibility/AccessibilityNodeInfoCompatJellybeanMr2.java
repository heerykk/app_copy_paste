package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

@TargetApi(18)
@RequiresApi(18)
/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatJellybeanMr2 */
class AccessibilityNodeInfoCompatJellybeanMr2 {
    AccessibilityNodeInfoCompatJellybeanMr2() {
    }

    public static void setViewIdResourceName(Object obj, String str) {
        Object info = obj;
        String viewId = str;
        Object obj2 = info;
        String str2 = viewId;
        ((AccessibilityNodeInfo) info).setViewIdResourceName(viewId);
    }

    public static String getViewIdResourceName(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getViewIdResourceName();
    }

    public static List<Object> findAccessibilityNodeInfosByViewId(Object obj, String str) {
        Object info = obj;
        String viewId = str;
        Object obj2 = info;
        String str2 = viewId;
        List findAccessibilityNodeInfosByViewId = ((AccessibilityNodeInfo) info).findAccessibilityNodeInfosByViewId(viewId);
        List list = findAccessibilityNodeInfosByViewId;
        return findAccessibilityNodeInfosByViewId;
    }

    public static void setTextSelection(Object obj, int i, int i2) {
        Object info = obj;
        int start = i;
        int end = i2;
        Object obj2 = info;
        int i3 = start;
        int i4 = end;
        ((AccessibilityNodeInfo) info).setTextSelection(start, end);
    }

    public static int getTextSelectionStart(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getTextSelectionStart();
    }

    public static int getTextSelectionEnd(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getTextSelectionEnd();
    }

    public static boolean isEditable(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isEditable();
    }

    public static void setEditable(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setEditable(z);
    }

    public static boolean refresh(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).refresh();
    }
}
