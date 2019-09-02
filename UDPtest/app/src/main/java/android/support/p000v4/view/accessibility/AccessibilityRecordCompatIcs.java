package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.accessibility.AccessibilityRecord;
import java.util.List;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.view.accessibility.AccessibilityRecordCompatIcs */
class AccessibilityRecordCompatIcs {
    AccessibilityRecordCompatIcs() {
    }

    public static Object obtain() {
        return AccessibilityRecord.obtain();
    }

    public static Object obtain(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return AccessibilityRecord.obtain((AccessibilityRecord) record);
    }

    public static int getAddedCount(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getAddedCount();
    }

    public static CharSequence getBeforeText(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getBeforeText();
    }

    public static CharSequence getClassName(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getClassName();
    }

    public static CharSequence getContentDescription(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getContentDescription();
    }

    public static int getCurrentItemIndex(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getCurrentItemIndex();
    }

    public static int getFromIndex(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getFromIndex();
    }

    public static int getItemCount(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getItemCount();
    }

    public static Parcelable getParcelableData(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getParcelableData();
    }

    public static int getRemovedCount(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getRemovedCount();
    }

    public static int getScrollX(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getScrollX();
    }

    public static int getScrollY(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getScrollY();
    }

    public static Object getSource(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getSource();
    }

    public static List<CharSequence> getText(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getText();
    }

    public static int getToIndex(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getToIndex();
    }

    public static int getWindowId(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).getWindowId();
    }

    public static boolean isChecked(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).isChecked();
    }

    public static boolean isEnabled(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).isEnabled();
    }

    public static boolean isFullScreen(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).isFullScreen();
    }

    public static boolean isPassword(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).isPassword();
    }

    public static boolean isScrollable(Object obj) {
        Object record = obj;
        Object obj2 = record;
        return ((AccessibilityRecord) record).isScrollable();
    }

    public static void recycle(Object obj) {
        Object record = obj;
        Object obj2 = record;
        ((AccessibilityRecord) record).recycle();
    }

    public static void setAddedCount(Object obj, int i) {
        Object record = obj;
        int addedCount = i;
        Object obj2 = record;
        int i2 = addedCount;
        ((AccessibilityRecord) record).setAddedCount(addedCount);
    }

    public static void setBeforeText(Object obj, CharSequence charSequence) {
        Object record = obj;
        CharSequence beforeText = charSequence;
        Object obj2 = record;
        CharSequence charSequence2 = beforeText;
        ((AccessibilityRecord) record).setBeforeText(beforeText);
    }

    public static void setChecked(Object obj, boolean z) {
        Object record = obj;
        Object obj2 = record;
        ((AccessibilityRecord) record).setChecked(z);
    }

    public static void setClassName(Object obj, CharSequence charSequence) {
        Object record = obj;
        CharSequence className = charSequence;
        Object obj2 = record;
        CharSequence charSequence2 = className;
        ((AccessibilityRecord) record).setClassName(className);
    }

    public static void setContentDescription(Object obj, CharSequence charSequence) {
        Object record = obj;
        CharSequence contentDescription = charSequence;
        Object obj2 = record;
        CharSequence charSequence2 = contentDescription;
        ((AccessibilityRecord) record).setContentDescription(contentDescription);
    }

    public static void setCurrentItemIndex(Object obj, int i) {
        Object record = obj;
        int currentItemIndex = i;
        Object obj2 = record;
        int i2 = currentItemIndex;
        ((AccessibilityRecord) record).setCurrentItemIndex(currentItemIndex);
    }

    public static void setEnabled(Object obj, boolean z) {
        Object record = obj;
        Object obj2 = record;
        ((AccessibilityRecord) record).setEnabled(z);
    }

    public static void setFromIndex(Object obj, int i) {
        Object record = obj;
        int fromIndex = i;
        Object obj2 = record;
        int i2 = fromIndex;
        ((AccessibilityRecord) record).setFromIndex(fromIndex);
    }

    public static void setFullScreen(Object obj, boolean z) {
        Object record = obj;
        Object obj2 = record;
        ((AccessibilityRecord) record).setFullScreen(z);
    }

    public static void setItemCount(Object obj, int i) {
        Object record = obj;
        int itemCount = i;
        Object obj2 = record;
        int i2 = itemCount;
        ((AccessibilityRecord) record).setItemCount(itemCount);
    }

    public static void setParcelableData(Object obj, Parcelable parcelable) {
        Object record = obj;
        Parcelable parcelableData = parcelable;
        Object obj2 = record;
        Parcelable parcelable2 = parcelableData;
        ((AccessibilityRecord) record).setParcelableData(parcelableData);
    }

    public static void setPassword(Object obj, boolean z) {
        Object record = obj;
        Object obj2 = record;
        ((AccessibilityRecord) record).setPassword(z);
    }

    public static void setRemovedCount(Object obj, int i) {
        Object record = obj;
        int removedCount = i;
        Object obj2 = record;
        int i2 = removedCount;
        ((AccessibilityRecord) record).setRemovedCount(removedCount);
    }

    public static void setScrollX(Object obj, int i) {
        Object record = obj;
        int scrollX = i;
        Object obj2 = record;
        int i2 = scrollX;
        ((AccessibilityRecord) record).setScrollX(scrollX);
    }

    public static void setScrollY(Object obj, int i) {
        Object record = obj;
        int scrollY = i;
        Object obj2 = record;
        int i2 = scrollY;
        ((AccessibilityRecord) record).setScrollY(scrollY);
    }

    public static void setScrollable(Object obj, boolean z) {
        Object record = obj;
        Object obj2 = record;
        ((AccessibilityRecord) record).setScrollable(z);
    }

    public static void setSource(Object obj, View view) {
        Object record = obj;
        View source = view;
        Object obj2 = record;
        View view2 = source;
        ((AccessibilityRecord) record).setSource(source);
    }

    public static void setToIndex(Object obj, int i) {
        Object record = obj;
        int toIndex = i;
        Object obj2 = record;
        int i2 = toIndex;
        ((AccessibilityRecord) record).setToIndex(toIndex);
    }
}
