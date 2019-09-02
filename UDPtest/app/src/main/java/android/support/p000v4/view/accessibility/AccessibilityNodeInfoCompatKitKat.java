package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatKitKat */
class AccessibilityNodeInfoCompatKitKat {
    private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";
    private static final String TRAITS_KEY = "android.view.accessibility.AccessibilityNodeInfo.traits";
    private static final long TRAIT_HAS_IMAGE = 1;
    private static final byte TRAIT_UNSET = -1;

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatKitKat$CollectionInfo */
    static class CollectionInfo {
        CollectionInfo() {
        }

        static int getColumnCount(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) info).getColumnCount();
        }

        static int getRowCount(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) info).getRowCount();
        }

        static boolean isHierarchical(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) info).isHierarchical();
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatKitKat$CollectionItemInfo */
    static class CollectionItemInfo {
        CollectionItemInfo() {
        }

        static int getColumnIndex(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) info).getColumnIndex();
        }

        static int getColumnSpan(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) info).getColumnSpan();
        }

        static int getRowIndex(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) info).getRowIndex();
        }

        static int getRowSpan(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) info).getRowSpan();
        }

        static boolean isHeading(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) info).isHeading();
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompatKitKat$RangeInfo */
    static class RangeInfo {
        RangeInfo() {
        }

        static float getCurrent(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) info).getCurrent();
        }

        static float getMax(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) info).getMax();
        }

        static float getMin(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) info).getMin();
        }

        static int getType(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) info).getType();
        }
    }

    AccessibilityNodeInfoCompatKitKat() {
    }

    static int getLiveRegion(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getLiveRegion();
    }

    static void setLiveRegion(Object obj, int i) {
        Object info = obj;
        int mode = i;
        Object obj2 = info;
        int i2 = mode;
        ((AccessibilityNodeInfo) info).setLiveRegion(mode);
    }

    static Object getCollectionInfo(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getCollectionInfo();
    }

    static Object getCollectionItemInfo(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getCollectionItemInfo();
    }

    public static void setCollectionInfo(Object obj, Object obj2) {
        Object info = obj;
        Object collectionInfo = obj2;
        Object obj3 = info;
        Object obj4 = collectionInfo;
        ((AccessibilityNodeInfo) info).setCollectionInfo((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) collectionInfo);
    }

    public static void setCollectionItemInfo(Object obj, Object obj2) {
        Object info = obj;
        Object collectionItemInfo = obj2;
        Object obj3 = info;
        Object obj4 = collectionItemInfo;
        ((AccessibilityNodeInfo) info).setCollectionItemInfo((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) collectionItemInfo);
    }

    static Object getRangeInfo(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getRangeInfo();
    }

    public static void setRangeInfo(Object obj, Object obj2) {
        Object info = obj;
        Object rangeInfo = obj2;
        Object obj3 = info;
        Object obj4 = rangeInfo;
        ((AccessibilityNodeInfo) info).setRangeInfo((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) rangeInfo);
    }

    public static Object obtainCollectionInfo(int i, int i2, boolean z, int i3) {
        int rowCount = i;
        int columnCount = i2;
        int i4 = rowCount;
        int i5 = columnCount;
        int i6 = i3;
        return android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(rowCount, columnCount, z);
    }

    public static Object obtainCollectionInfo(int i, int i2, boolean z) {
        int rowCount = i;
        int columnCount = i2;
        int i3 = rowCount;
        int i4 = columnCount;
        return android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(rowCount, columnCount, z);
    }

    public static Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z) {
        int rowIndex = i;
        int rowSpan = i2;
        int columnIndex = i3;
        int columnSpan = i4;
        int i5 = rowIndex;
        int i6 = rowSpan;
        int i7 = columnIndex;
        int i8 = columnSpan;
        return android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, z);
    }

    public static void setContentInvalid(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setContentInvalid(z);
    }

    public static boolean isContentInvalid(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isContentInvalid();
    }

    public static boolean canOpenPopup(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).canOpenPopup();
    }

    public static void setCanOpenPopup(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setCanOpenPopup(z);
    }

    public static Bundle getExtras(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getExtras();
    }

    private static long getTraits(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return getExtras(info).getLong(TRAITS_KEY, -1);
    }

    private static void setTrait(Object obj, long j) {
        Object info = obj;
        long trait = j;
        Object obj2 = info;
        long j2 = trait;
        Bundle extras = getExtras(info);
        Bundle extras2 = extras;
        long j3 = extras.getLong(TRAITS_KEY, 0);
        long j4 = j3;
        extras2.putLong(TRAITS_KEY, j3 | trait);
    }

    public static int getInputType(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).getInputType();
    }

    public static void setInputType(Object obj, int i) {
        Object info = obj;
        int inputType = i;
        Object obj2 = info;
        int i2 = inputType;
        ((AccessibilityNodeInfo) info).setInputType(inputType);
    }

    public static boolean isDismissable(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isDismissable();
    }

    public static void setDismissable(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setDismissable(z);
    }

    public static boolean isMultiLine(Object obj) {
        Object info = obj;
        Object obj2 = info;
        return ((AccessibilityNodeInfo) info).isMultiLine();
    }

    public static void setMultiLine(Object obj, boolean z) {
        Object info = obj;
        Object obj2 = info;
        ((AccessibilityNodeInfo) info).setMultiLine(z);
    }

    public static CharSequence getRoleDescription(Object obj) {
        Object info = obj;
        Object obj2 = info;
        Bundle extras = getExtras(info);
        Bundle bundle = extras;
        return extras.getCharSequence(ROLE_DESCRIPTION_KEY);
    }

    public static void setRoleDescription(Object obj, CharSequence charSequence) {
        Object info = obj;
        CharSequence roleDescription = charSequence;
        Object obj2 = info;
        CharSequence charSequence2 = roleDescription;
        Bundle extras = getExtras(info);
        Bundle bundle = extras;
        extras.putCharSequence(ROLE_DESCRIPTION_KEY, roleDescription);
    }

    public static Object obtainRangeInfo(int i, float f, float f2, float f3) {
        int type = i;
        float min = f;
        float max = f2;
        float current = f3;
        int i2 = type;
        float f4 = min;
        float f5 = max;
        float f6 = current;
        return android.view.accessibility.AccessibilityNodeInfo.RangeInfo.obtain(type, min, max, current);
    }
}
