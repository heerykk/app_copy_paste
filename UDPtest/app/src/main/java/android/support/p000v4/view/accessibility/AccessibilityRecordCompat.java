package android.support.p000v4.view.accessibility;

import android.os.Build.VERSION;
import android.os.Parcelable;
import android.view.View;
import java.util.Collections;
import java.util.List;

/* renamed from: android.support.v4.view.accessibility.AccessibilityRecordCompat */
public class AccessibilityRecordCompat {
    private static final AccessibilityRecordImpl IMPL;
    private final Object mRecord;

    /* renamed from: android.support.v4.view.accessibility.AccessibilityRecordCompat$AccessibilityRecordIcsImpl */
    static class AccessibilityRecordIcsImpl extends AccessibilityRecordStubImpl {
        AccessibilityRecordIcsImpl() {
        }

        public Object obtain() {
            return AccessibilityRecordCompatIcs.obtain();
        }

        public Object obtain(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.obtain(record);
        }

        public int getAddedCount(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getAddedCount(record);
        }

        public CharSequence getBeforeText(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getBeforeText(record);
        }

        public CharSequence getClassName(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getClassName(record);
        }

        public CharSequence getContentDescription(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getContentDescription(record);
        }

        public int getCurrentItemIndex(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getCurrentItemIndex(record);
        }

        public int getFromIndex(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getFromIndex(record);
        }

        public int getItemCount(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getItemCount(record);
        }

        public Parcelable getParcelableData(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getParcelableData(record);
        }

        public int getRemovedCount(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getRemovedCount(record);
        }

        public int getScrollX(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getScrollX(record);
        }

        public int getScrollY(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getScrollY(record);
        }

        public AccessibilityNodeInfoCompat getSource(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityNodeInfoCompat.wrapNonNullInstance(AccessibilityRecordCompatIcs.getSource(record));
        }

        public List<CharSequence> getText(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getText(record);
        }

        public int getToIndex(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getToIndex(record);
        }

        public int getWindowId(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.getWindowId(record);
        }

        public boolean isChecked(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.isChecked(record);
        }

        public boolean isEnabled(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.isEnabled(record);
        }

        public boolean isFullScreen(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.isFullScreen(record);
        }

        public boolean isPassword(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.isPassword(record);
        }

        public boolean isScrollable(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcs.isScrollable(record);
        }

        public void recycle(Object obj) {
            Object record = obj;
            Object obj2 = record;
            AccessibilityRecordCompatIcs.recycle(record);
        }

        public void setAddedCount(Object obj, int i) {
            Object record = obj;
            int addedCount = i;
            Object obj2 = record;
            int i2 = addedCount;
            AccessibilityRecordCompatIcs.setAddedCount(record, addedCount);
        }

        public void setBeforeText(Object obj, CharSequence charSequence) {
            Object record = obj;
            CharSequence beforeText = charSequence;
            Object obj2 = record;
            CharSequence charSequence2 = beforeText;
            AccessibilityRecordCompatIcs.setBeforeText(record, beforeText);
        }

        public void setChecked(Object obj, boolean z) {
            Object record = obj;
            Object obj2 = record;
            AccessibilityRecordCompatIcs.setChecked(record, z);
        }

        public void setClassName(Object obj, CharSequence charSequence) {
            Object record = obj;
            CharSequence className = charSequence;
            Object obj2 = record;
            CharSequence charSequence2 = className;
            AccessibilityRecordCompatIcs.setClassName(record, className);
        }

        public void setContentDescription(Object obj, CharSequence charSequence) {
            Object record = obj;
            CharSequence contentDescription = charSequence;
            Object obj2 = record;
            CharSequence charSequence2 = contentDescription;
            AccessibilityRecordCompatIcs.setContentDescription(record, contentDescription);
        }

        public void setCurrentItemIndex(Object obj, int i) {
            Object record = obj;
            int currentItemIndex = i;
            Object obj2 = record;
            int i2 = currentItemIndex;
            AccessibilityRecordCompatIcs.setCurrentItemIndex(record, currentItemIndex);
        }

        public void setEnabled(Object obj, boolean z) {
            Object record = obj;
            Object obj2 = record;
            AccessibilityRecordCompatIcs.setEnabled(record, z);
        }

        public void setFromIndex(Object obj, int i) {
            Object record = obj;
            int fromIndex = i;
            Object obj2 = record;
            int i2 = fromIndex;
            AccessibilityRecordCompatIcs.setFromIndex(record, fromIndex);
        }

        public void setFullScreen(Object obj, boolean z) {
            Object record = obj;
            Object obj2 = record;
            AccessibilityRecordCompatIcs.setFullScreen(record, z);
        }

        public void setItemCount(Object obj, int i) {
            Object record = obj;
            int itemCount = i;
            Object obj2 = record;
            int i2 = itemCount;
            AccessibilityRecordCompatIcs.setItemCount(record, itemCount);
        }

        public void setParcelableData(Object obj, Parcelable parcelable) {
            Object record = obj;
            Parcelable parcelableData = parcelable;
            Object obj2 = record;
            Parcelable parcelable2 = parcelableData;
            AccessibilityRecordCompatIcs.setParcelableData(record, parcelableData);
        }

        public void setPassword(Object obj, boolean z) {
            Object record = obj;
            Object obj2 = record;
            AccessibilityRecordCompatIcs.setPassword(record, z);
        }

        public void setRemovedCount(Object obj, int i) {
            Object record = obj;
            int removedCount = i;
            Object obj2 = record;
            int i2 = removedCount;
            AccessibilityRecordCompatIcs.setRemovedCount(record, removedCount);
        }

        public void setScrollX(Object obj, int i) {
            Object record = obj;
            int scrollX = i;
            Object obj2 = record;
            int i2 = scrollX;
            AccessibilityRecordCompatIcs.setScrollX(record, scrollX);
        }

        public void setScrollY(Object obj, int i) {
            Object record = obj;
            int scrollY = i;
            Object obj2 = record;
            int i2 = scrollY;
            AccessibilityRecordCompatIcs.setScrollY(record, scrollY);
        }

        public void setScrollable(Object obj, boolean z) {
            Object record = obj;
            Object obj2 = record;
            AccessibilityRecordCompatIcs.setScrollable(record, z);
        }

        public void setSource(Object obj, View view) {
            Object record = obj;
            View source = view;
            Object obj2 = record;
            View view2 = source;
            AccessibilityRecordCompatIcs.setSource(record, source);
        }

        public void setToIndex(Object obj, int i) {
            Object record = obj;
            int toIndex = i;
            Object obj2 = record;
            int i2 = toIndex;
            AccessibilityRecordCompatIcs.setToIndex(record, toIndex);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityRecordCompat$AccessibilityRecordIcsMr1Impl */
    static class AccessibilityRecordIcsMr1Impl extends AccessibilityRecordIcsImpl {
        AccessibilityRecordIcsMr1Impl() {
        }

        public int getMaxScrollX(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcsMr1.getMaxScrollX(record);
        }

        public int getMaxScrollY(Object obj) {
            Object record = obj;
            Object obj2 = record;
            return AccessibilityRecordCompatIcsMr1.getMaxScrollY(record);
        }

        public void setMaxScrollX(Object obj, int i) {
            Object record = obj;
            int maxScrollX = i;
            Object obj2 = record;
            int i2 = maxScrollX;
            AccessibilityRecordCompatIcsMr1.setMaxScrollX(record, maxScrollX);
        }

        public void setMaxScrollY(Object obj, int i) {
            Object record = obj;
            int maxScrollY = i;
            Object obj2 = record;
            int i2 = maxScrollY;
            AccessibilityRecordCompatIcsMr1.setMaxScrollY(record, maxScrollY);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityRecordCompat$AccessibilityRecordImpl */
    interface AccessibilityRecordImpl {
        int getAddedCount(Object obj);

        CharSequence getBeforeText(Object obj);

        CharSequence getClassName(Object obj);

        CharSequence getContentDescription(Object obj);

        int getCurrentItemIndex(Object obj);

        int getFromIndex(Object obj);

        int getItemCount(Object obj);

        int getMaxScrollX(Object obj);

        int getMaxScrollY(Object obj);

        Parcelable getParcelableData(Object obj);

        int getRemovedCount(Object obj);

        int getScrollX(Object obj);

        int getScrollY(Object obj);

        AccessibilityNodeInfoCompat getSource(Object obj);

        List<CharSequence> getText(Object obj);

        int getToIndex(Object obj);

        int getWindowId(Object obj);

        boolean isChecked(Object obj);

        boolean isEnabled(Object obj);

        boolean isFullScreen(Object obj);

        boolean isPassword(Object obj);

        boolean isScrollable(Object obj);

        Object obtain();

        Object obtain(Object obj);

        void recycle(Object obj);

        void setAddedCount(Object obj, int i);

        void setBeforeText(Object obj, CharSequence charSequence);

        void setChecked(Object obj, boolean z);

        void setClassName(Object obj, CharSequence charSequence);

        void setContentDescription(Object obj, CharSequence charSequence);

        void setCurrentItemIndex(Object obj, int i);

        void setEnabled(Object obj, boolean z);

        void setFromIndex(Object obj, int i);

        void setFullScreen(Object obj, boolean z);

        void setItemCount(Object obj, int i);

        void setMaxScrollX(Object obj, int i);

        void setMaxScrollY(Object obj, int i);

        void setParcelableData(Object obj, Parcelable parcelable);

        void setPassword(Object obj, boolean z);

        void setRemovedCount(Object obj, int i);

        void setScrollX(Object obj, int i);

        void setScrollY(Object obj, int i);

        void setScrollable(Object obj, boolean z);

        void setSource(Object obj, View view);

        void setSource(Object obj, View view, int i);

        void setToIndex(Object obj, int i);
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityRecordCompat$AccessibilityRecordJellyBeanImpl */
    static class AccessibilityRecordJellyBeanImpl extends AccessibilityRecordIcsMr1Impl {
        AccessibilityRecordJellyBeanImpl() {
        }

        public void setSource(Object obj, View view, int i) {
            Object record = obj;
            View root = view;
            int virtualDescendantId = i;
            Object obj2 = record;
            View view2 = root;
            int i2 = virtualDescendantId;
            AccessibilityRecordCompatJellyBean.setSource(record, root, virtualDescendantId);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityRecordCompat$AccessibilityRecordStubImpl */
    static class AccessibilityRecordStubImpl implements AccessibilityRecordImpl {
        AccessibilityRecordStubImpl() {
        }

        public Object obtain() {
            return null;
        }

        public Object obtain(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public int getAddedCount(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public CharSequence getBeforeText(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public CharSequence getClassName(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public CharSequence getContentDescription(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public int getCurrentItemIndex(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getFromIndex(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getItemCount(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getMaxScrollX(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getMaxScrollY(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public Parcelable getParcelableData(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public int getRemovedCount(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getScrollX(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getScrollY(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public AccessibilityNodeInfoCompat getSource(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public List<CharSequence> getText(Object obj) {
            Object obj2 = obj;
            return Collections.emptyList();
        }

        public int getToIndex(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getWindowId(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public boolean isChecked(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isEnabled(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isFullScreen(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isPassword(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isScrollable(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public void recycle(Object obj) {
            Object obj2 = obj;
        }

        public void setAddedCount(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public void setBeforeText(Object obj, CharSequence charSequence) {
            Object obj2 = obj;
            CharSequence charSequence2 = charSequence;
        }

        public void setChecked(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setClassName(Object obj, CharSequence charSequence) {
            Object obj2 = obj;
            CharSequence charSequence2 = charSequence;
        }

        public void setContentDescription(Object obj, CharSequence charSequence) {
            Object obj2 = obj;
            CharSequence charSequence2 = charSequence;
        }

        public void setCurrentItemIndex(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public void setEnabled(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setFromIndex(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public void setFullScreen(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setItemCount(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public void setMaxScrollX(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public void setMaxScrollY(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public void setParcelableData(Object obj, Parcelable parcelable) {
            Object obj2 = obj;
            Parcelable parcelable2 = parcelable;
        }

        public void setPassword(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setRemovedCount(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public void setScrollX(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public void setScrollY(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }

        public void setScrollable(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public void setSource(Object obj, View view) {
            Object obj2 = obj;
            View view2 = view;
        }

        public void setSource(Object obj, View view, int i) {
            Object obj2 = obj;
            View view2 = view;
            int i2 = i;
        }

        public void setToIndex(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityRecordJellyBeanImpl();
        } else if (VERSION.SDK_INT >= 15) {
            IMPL = new AccessibilityRecordIcsMr1Impl();
        } else if (VERSION.SDK_INT < 14) {
            IMPL = new AccessibilityRecordStubImpl();
        } else {
            IMPL = new AccessibilityRecordIcsImpl();
        }
    }

    @Deprecated
    public AccessibilityRecordCompat(Object obj) {
        Object record = obj;
        Object obj2 = record;
        this.mRecord = record;
    }

    @Deprecated
    public Object getImpl() {
        return this.mRecord;
    }

    public static AccessibilityRecordCompat obtain(AccessibilityRecordCompat accessibilityRecordCompat) {
        AccessibilityRecordCompat record = accessibilityRecordCompat;
        AccessibilityRecordCompat accessibilityRecordCompat2 = record;
        return new AccessibilityRecordCompat(IMPL.obtain(record.mRecord));
    }

    public static AccessibilityRecordCompat obtain() {
        return new AccessibilityRecordCompat(IMPL.obtain());
    }

    public void setSource(View view) {
        View source = view;
        View view2 = source;
        IMPL.setSource(this.mRecord, source);
    }

    public void setSource(View view, int i) {
        View root = view;
        int virtualDescendantId = i;
        View view2 = root;
        int i2 = virtualDescendantId;
        IMPL.setSource(this.mRecord, root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat getSource() {
        return IMPL.getSource(this.mRecord);
    }

    public int getWindowId() {
        return IMPL.getWindowId(this.mRecord);
    }

    public boolean isChecked() {
        return IMPL.isChecked(this.mRecord);
    }

    public void setChecked(boolean z) {
        IMPL.setChecked(this.mRecord, z);
    }

    public boolean isEnabled() {
        return IMPL.isEnabled(this.mRecord);
    }

    public void setEnabled(boolean z) {
        IMPL.setEnabled(this.mRecord, z);
    }

    public boolean isPassword() {
        return IMPL.isPassword(this.mRecord);
    }

    public void setPassword(boolean z) {
        IMPL.setPassword(this.mRecord, z);
    }

    public boolean isFullScreen() {
        return IMPL.isFullScreen(this.mRecord);
    }

    public void setFullScreen(boolean z) {
        IMPL.setFullScreen(this.mRecord, z);
    }

    public boolean isScrollable() {
        return IMPL.isScrollable(this.mRecord);
    }

    public void setScrollable(boolean z) {
        IMPL.setScrollable(this.mRecord, z);
    }

    public int getItemCount() {
        return IMPL.getItemCount(this.mRecord);
    }

    public void setItemCount(int i) {
        int itemCount = i;
        int i2 = itemCount;
        IMPL.setItemCount(this.mRecord, itemCount);
    }

    public int getCurrentItemIndex() {
        return IMPL.getCurrentItemIndex(this.mRecord);
    }

    public void setCurrentItemIndex(int i) {
        int currentItemIndex = i;
        int i2 = currentItemIndex;
        IMPL.setCurrentItemIndex(this.mRecord, currentItemIndex);
    }

    public int getFromIndex() {
        return IMPL.getFromIndex(this.mRecord);
    }

    public void setFromIndex(int i) {
        int fromIndex = i;
        int i2 = fromIndex;
        IMPL.setFromIndex(this.mRecord, fromIndex);
    }

    public int getToIndex() {
        return IMPL.getToIndex(this.mRecord);
    }

    public void setToIndex(int i) {
        int toIndex = i;
        int i2 = toIndex;
        IMPL.setToIndex(this.mRecord, toIndex);
    }

    public int getScrollX() {
        return IMPL.getScrollX(this.mRecord);
    }

    public void setScrollX(int i) {
        int scrollX = i;
        int i2 = scrollX;
        IMPL.setScrollX(this.mRecord, scrollX);
    }

    public int getScrollY() {
        return IMPL.getScrollY(this.mRecord);
    }

    public void setScrollY(int i) {
        int scrollY = i;
        int i2 = scrollY;
        IMPL.setScrollY(this.mRecord, scrollY);
    }

    public int getMaxScrollX() {
        return IMPL.getMaxScrollX(this.mRecord);
    }

    public void setMaxScrollX(int i) {
        int maxScrollX = i;
        int i2 = maxScrollX;
        IMPL.setMaxScrollX(this.mRecord, maxScrollX);
    }

    public int getMaxScrollY() {
        return IMPL.getMaxScrollY(this.mRecord);
    }

    public void setMaxScrollY(int i) {
        int maxScrollY = i;
        int i2 = maxScrollY;
        IMPL.setMaxScrollY(this.mRecord, maxScrollY);
    }

    public int getAddedCount() {
        return IMPL.getAddedCount(this.mRecord);
    }

    public void setAddedCount(int i) {
        int addedCount = i;
        int i2 = addedCount;
        IMPL.setAddedCount(this.mRecord, addedCount);
    }

    public int getRemovedCount() {
        return IMPL.getRemovedCount(this.mRecord);
    }

    public void setRemovedCount(int i) {
        int removedCount = i;
        int i2 = removedCount;
        IMPL.setRemovedCount(this.mRecord, removedCount);
    }

    public CharSequence getClassName() {
        return IMPL.getClassName(this.mRecord);
    }

    public void setClassName(CharSequence charSequence) {
        CharSequence className = charSequence;
        CharSequence charSequence2 = className;
        IMPL.setClassName(this.mRecord, className);
    }

    public List<CharSequence> getText() {
        return IMPL.getText(this.mRecord);
    }

    public CharSequence getBeforeText() {
        return IMPL.getBeforeText(this.mRecord);
    }

    public void setBeforeText(CharSequence charSequence) {
        CharSequence beforeText = charSequence;
        CharSequence charSequence2 = beforeText;
        IMPL.setBeforeText(this.mRecord, beforeText);
    }

    public CharSequence getContentDescription() {
        return IMPL.getContentDescription(this.mRecord);
    }

    public void setContentDescription(CharSequence charSequence) {
        CharSequence contentDescription = charSequence;
        CharSequence charSequence2 = contentDescription;
        IMPL.setContentDescription(this.mRecord, contentDescription);
    }

    public Parcelable getParcelableData() {
        return IMPL.getParcelableData(this.mRecord);
    }

    public void setParcelableData(Parcelable parcelable) {
        Parcelable parcelableData = parcelable;
        Parcelable parcelable2 = parcelableData;
        IMPL.setParcelableData(this.mRecord, parcelableData);
    }

    public void recycle() {
        IMPL.recycle(this.mRecord);
    }

    public int hashCode() {
        return this.mRecord != null ? this.mRecord.hashCode() : 0;
    }

    public boolean equals(Object obj) {
        Object obj2 = obj;
        Object obj3 = obj2;
        if (this == obj2) {
            return true;
        }
        if (obj2 == null) {
            return false;
        }
        if (getClass() != obj2.getClass()) {
            return false;
        }
        AccessibilityRecordCompat other = (AccessibilityRecordCompat) obj2;
        if (this.mRecord != null) {
            if (!this.mRecord.equals(other.mRecord)) {
                return false;
            }
        } else if (other.mRecord != null) {
            return false;
        }
        return true;
    }
}
