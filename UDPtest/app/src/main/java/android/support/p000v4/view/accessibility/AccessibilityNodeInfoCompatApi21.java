package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import java.util.List;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatApi21 */
class AccessibilityNodeInfoCompatApi21 {

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatApi21$CollectionInfo */
    static class CollectionInfo {
        CollectionInfo() {
        }

        public static int getSelectionMode(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) info).getSelectionMode();
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatApi21$CollectionItemInfo */
    static class CollectionItemInfo {
        CollectionItemInfo() {
        }

        public static boolean isSelected(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) info).isSelected();
        }
    }

    AccessibilityNodeInfoCompatApi21() {
    }

    static List<Object> getActionList(Object obj) {
        Object info = obj;
        Object obj2 = info;
        List actionList = ((AccessibilityNodeInfo) info).getActionList();
        List list = actionList;
        return actionList;
    }

    static void addAction(Object obj, Object obj2) {
        Object info = obj;
        Object action = obj2;
        Object obj3 = info;
        Object obj4 = action;
        ((AccessibilityNodeInfo) info).addAction((AccessibilityAction) action);
    }

    public static boolean removeAction(Object obj, Object obj2) {
        Object info = obj;
        Object action = obj2;
        Object obj3 = info;
        Object obj4 = action;
        return ((AccessibilityNodeInfo) info).removeAction((AccessibilityAction) action);
    }

    public static Object obtainCollectionInfo(int i, int i2, boolean z, int i3) {
        int rowCount = i;
        int columnCount = i2;
        int selectionMode = i3;
        int i4 = rowCount;
        int i5 = columnCount;
        int i6 = selectionMode;
        return android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(rowCount, columnCount, z, selectionMode);
    }

    public static Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        int rowIndex = i;
        int rowSpan = i2;
        int columnIndex = i3;
        int columnSpan = i4;
        int i5 = rowIndex;
        int i6 = rowSpan;
        int i7 = columnIndex;
        int i8 = columnSpan;
        return android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, z, z2);
    }

    public static CharSequence getError(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getError();
    }

    public static void setError(Object obj, CharSequence charSequence) {
        Object info = obj;
        CharSequence error = charSequence;
        Object obj2 = info;
        CharSequence charSequence2 = error;
        ((AccessibilityNodeInfo) info).setError(error);
    }

    public static void setMaxTextLength(Object obj, int i) {
        Object info = obj;
        int max = i;
        Object obj2 = info;
        int i2 = max;
        ((AccessibilityNodeInfo) info).setMaxTextLength(max);
    }

    public static int getMaxTextLength(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getMaxTextLength();
    }

    public static Object getWindow(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getWindow();
    }

    public static boolean removeChild(Object obj, View view) {
        Object info = obj;
        View child = view;
        Object obj2 = info;
        View view2 = child;
        return ((AccessibilityNodeInfo) info).removeChild(child);
    }

    public static boolean removeChild(Object obj, View view, int i) {
        Object info = obj;
        View root = view;
        int virtualDescendantId = i;
        Object obj2 = info;
        View view2 = root;
        int i2 = virtualDescendantId;
        return ((AccessibilityNodeInfo) info).removeChild(root, virtualDescendantId);
    }

    static Object newAccessibilityAction(int i, CharSequence charSequence) {
        int actionId = i;
        CharSequence label = charSequence;
        int i2 = actionId;
        CharSequence charSequence2 = label;
        return new AccessibilityAction(actionId, label);
    }

    static int getAccessibilityActionId(Object obj) {
        Object action = obj;
        Object obj2 = action;
        return ((AccessibilityAction) action).getId();
    }

    static CharSequence getAccessibilityActionLabel(Object obj) {
        Object action = obj;
        Object obj2 = action;
        return ((AccessibilityAction) action).getLabel();
    }
}
