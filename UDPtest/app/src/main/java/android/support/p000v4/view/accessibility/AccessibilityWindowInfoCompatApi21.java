package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityWindowInfo;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.view.accessibility.AccessibilityWindowInfoCompatApi21 */
class AccessibilityWindowInfoCompatApi21 {
    AccessibilityWindowInfoCompatApi21() {
    }

    public static Object obtain() {
        return AccessibilityWindowInfo.obtain();
    }

    public static Object obtain(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return AccessibilityWindowInfo.obtain((AccessibilityWindowInfo) info);
    }

    public static int getType(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityWindowInfo) info).getType();
    }

    public static int getLayer(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityWindowInfo) info).getLayer();
    }

    public static Object getRoot(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityWindowInfo) info).getRoot();
    }

    public static Object getParent(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityWindowInfo) info).getParent();
    }

    public static int getId(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityWindowInfo) info).getId();
    }

    public static void getBoundsInScreen(Object obj, Rect rect) {
        Object info = obj;
        Rect outBounds = rect;
        Object obj2 = info;
        Rect rect2 = outBounds;
        ((AccessibilityWindowInfo) info).getBoundsInScreen(outBounds);
    }

    public static boolean isActive(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityWindowInfo) info).isActive();
    }

    public static boolean isFocused(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityWindowInfo) info).isFocused();
    }

    public static boolean isAccessibilityFocused(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityWindowInfo) info).isAccessibilityFocused();
    }

    public static int getChildCount(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityWindowInfo) info).getChildCount();
    }

    public static Object getChild(Object obj, int i) {
        Object info = obj;
        int index = i;
        Object obj2 = info;
        int i2 = index;
        return ((AccessibilityWindowInfo) info).getChild(index);
    }

    public static void recycle(Object obj) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityWindowInfo) info).recycle();
    }
}
