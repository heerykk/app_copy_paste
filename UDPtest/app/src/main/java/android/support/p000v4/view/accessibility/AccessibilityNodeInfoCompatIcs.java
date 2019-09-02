package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatIcs */
class AccessibilityNodeInfoCompatIcs {
    AccessibilityNodeInfoCompatIcs() {
    }

    public static Object obtain() {
        return AccessibilityNodeInfo.obtain();
    }

    public static Object obtain(View view) {
        View source = view;
        View view2 = source;
        return AccessibilityNodeInfo.obtain(source);
    }

    public static Object obtain(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return AccessibilityNodeInfo.obtain((AccessibilityNodeInfo) info);
    }

    public static void addAction(Object obj, int i) {
        Object info = obj;
        int action = i;
        Object obj2 = info;
        int i2 = action;
        ((AccessibilityNodeInfo) info).addAction(action);
    }

    public static void addChild(Object obj, View view) {
        Object info = obj;
        View child = view;
        Object obj2 = info;
        View view2 = child;
        ((AccessibilityNodeInfo) info).addChild(child);
    }

    public static List<Object> findAccessibilityNodeInfosByText(Object obj, String str) {
        Object info = obj;
        String text = str;
        Object obj2 = info;
        String str2 = text;
        List findAccessibilityNodeInfosByText = ((AccessibilityNodeInfo) info).findAccessibilityNodeInfosByText(text);
        List list = findAccessibilityNodeInfosByText;
        return findAccessibilityNodeInfosByText;
    }

    public static int getActions(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getActions();
    }

    public static void getBoundsInParent(Object obj, Rect rect) {
        Object info = obj;
        Rect outBounds = rect;
        Object obj2 = info;
        Rect rect2 = outBounds;
        ((AccessibilityNodeInfo) info).getBoundsInParent(outBounds);
    }

    public static void getBoundsInScreen(Object obj, Rect rect) {
        Object info = obj;
        Rect outBounds = rect;
        Object obj2 = info;
        Rect rect2 = outBounds;
        ((AccessibilityNodeInfo) info).getBoundsInScreen(outBounds);
    }

    public static Object getChild(Object obj, int i) {
        Object info = obj;
        int index = i;
        Object obj2 = info;
        int i2 = index;
        return ((AccessibilityNodeInfo) info).getChild(index);
    }

    public static int getChildCount(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getChildCount();
    }

    public static CharSequence getClassName(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getClassName();
    }

    public static CharSequence getContentDescription(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getContentDescription();
    }

    public static CharSequence getPackageName(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getPackageName();
    }

    public static Object getParent(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getParent();
    }

    public static CharSequence getText(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getText();
    }

    public static int getWindowId(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getWindowId();
    }

    public static boolean isCheckable(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isCheckable();
    }

    public static boolean isChecked(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isChecked();
    }

    public static boolean isClickable(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isClickable();
    }

    public static boolean isEnabled(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isEnabled();
    }

    public static boolean isFocusable(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isFocusable();
    }

    public static boolean isFocused(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isFocused();
    }

    public static boolean isLongClickable(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isLongClickable();
    }

    public static boolean isPassword(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isPassword();
    }

    public static boolean isScrollable(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isScrollable();
    }

    public static boolean isSelected(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isSelected();
    }

    public static boolean performAction(Object obj, int i) {
        Object info = obj;
        int action = i;
        Object obj2 = info;
        int i2 = action;
        return ((AccessibilityNodeInfo) info).performAction(action);
    }

    public static void setBoundsInParent(Object obj, Rect rect) {
        Object info = obj;
        Rect bounds = rect;
        Object obj2 = info;
        Rect rect2 = bounds;
        ((AccessibilityNodeInfo) info).setBoundsInParent(bounds);
    }

    public static void setBoundsInScreen(Object obj, Rect rect) {
        Object info = obj;
        Rect bounds = rect;
        Object obj2 = info;
        Rect rect2 = bounds;
        ((AccessibilityNodeInfo) info).setBoundsInScreen(bounds);
    }

    public static void setCheckable(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setCheckable(z);
    }

    public static void setChecked(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setChecked(z);
    }

    public static void setClassName(Object obj, CharSequence charSequence) {
        Object info = obj;
        CharSequence className = charSequence;
        Object obj2 = info;
        CharSequence charSequence2 = className;
        ((AccessibilityNodeInfo) info).setClassName(className);
    }

    public static void setClickable(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setClickable(z);
    }

    public static void setContentDescription(Object obj, CharSequence charSequence) {
        Object info = obj;
        CharSequence contentDescription = charSequence;
        Object obj2 = info;
        CharSequence charSequence2 = contentDescription;
        ((AccessibilityNodeInfo) info).setContentDescription(contentDescription);
    }

    public static void setEnabled(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setEnabled(z);
    }

    public static void setFocusable(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setFocusable(z);
    }

    public static void setFocused(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setFocused(z);
    }

    public static void setLongClickable(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setLongClickable(z);
    }

    public static void setPackageName(Object obj, CharSequence charSequence) {
        Object info = obj;
        CharSequence packageName = charSequence;
        Object obj2 = info;
        CharSequence charSequence2 = packageName;
        ((AccessibilityNodeInfo) info).setPackageName(packageName);
    }

    public static void setParent(Object obj, View view) {
        Object info = obj;
        View parent = view;
        Object obj2 = info;
        View view2 = parent;
        ((AccessibilityNodeInfo) info).setParent(parent);
    }

    public static void setPassword(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setPassword(z);
    }

    public static void setScrollable(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setScrollable(z);
    }

    public static void setSelected(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setSelected(z);
    }

    public static void setSource(Object obj, View view) {
        Object info = obj;
        View source = view;
        Object obj2 = info;
        View view2 = source;
        ((AccessibilityNodeInfo) info).setSource(source);
    }

    public static void setText(Object obj, CharSequence charSequence) {
        Object info = obj;
        CharSequence text = charSequence;
        Object obj2 = info;
        CharSequence charSequence2 = text;
        ((AccessibilityNodeInfo) info).setText(text);
    }

    public static void recycle(Object obj) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).recycle();
    }
}
