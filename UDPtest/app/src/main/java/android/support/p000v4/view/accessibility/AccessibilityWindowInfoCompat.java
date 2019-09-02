package android.support.p000v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.view.accessibility.AccessibilityWindowInfoCompat */
public class AccessibilityWindowInfoCompat {
    private static final AccessibilityWindowInfoImpl IMPL;
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    private static final int UNDEFINED = -1;
    private Object mInfo;

    /* renamed from: android.support.v4.view.accessibility.AccessibilityWindowInfoCompat$AccessibilityWindowInfoApi21Impl */
    private static class AccessibilityWindowInfoApi21Impl extends AccessibilityWindowInfoStubImpl {
        AccessibilityWindowInfoApi21Impl() {
        }

        public Object obtain() {
            return AccessibilityWindowInfoCompatApi21.obtain();
        }

        public Object obtain(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi21.obtain(info);
        }

        public int getType(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi21.getType(info);
        }

        public int getLayer(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi21.getLayer(info);
        }

        public Object getRoot(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi21.getRoot(info);
        }

        public Object getParent(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi21.getParent(info);
        }

        public int getId(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi21.getId(info);
        }

        public void getBoundsInScreen(Object obj, Rect rect) {
            Object info = obj;
            Rect outBounds = rect;
            Object obj2 = info;
            Rect rect2 = outBounds;
            AccessibilityWindowInfoCompatApi21.getBoundsInScreen(info, outBounds);
        }

        public boolean isActive(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi21.isActive(info);
        }

        public boolean isFocused(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi21.isFocused(info);
        }

        public boolean isAccessibilityFocused(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi21.isAccessibilityFocused(info);
        }

        public int getChildCount(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi21.getChildCount(info);
        }

        public Object getChild(Object obj, int i) {
            Object info = obj;
            int index = i;
            Object obj2 = info;
            int i2 = index;
            return AccessibilityWindowInfoCompatApi21.getChild(info, index);
        }

        public void recycle(Object obj) {
            Object info = obj;
            Object obj2 = info;
            AccessibilityWindowInfoCompatApi21.recycle(info);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityWindowInfoCompat$AccessibilityWindowInfoApi24Impl */
    private static class AccessibilityWindowInfoApi24Impl extends AccessibilityWindowInfoApi21Impl {
        AccessibilityWindowInfoApi24Impl() {
        }

        public CharSequence getTitle(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi24.getTitle(info);
        }

        public Object getAnchor(Object obj) {
            Object info = obj;
            Object obj2 = info;
            return AccessibilityWindowInfoCompatApi24.getAnchor(info);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityWindowInfoCompat$AccessibilityWindowInfoImpl */
    private interface AccessibilityWindowInfoImpl {
        Object getAnchor(Object obj);

        void getBoundsInScreen(Object obj, Rect rect);

        Object getChild(Object obj, int i);

        int getChildCount(Object obj);

        int getId(Object obj);

        int getLayer(Object obj);

        Object getParent(Object obj);

        Object getRoot(Object obj);

        CharSequence getTitle(Object obj);

        int getType(Object obj);

        boolean isAccessibilityFocused(Object obj);

        boolean isActive(Object obj);

        boolean isFocused(Object obj);

        Object obtain();

        Object obtain(Object obj);

        void recycle(Object obj);
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityWindowInfoCompat$AccessibilityWindowInfoStubImpl */
    private static class AccessibilityWindowInfoStubImpl implements AccessibilityWindowInfoImpl {
        AccessibilityWindowInfoStubImpl() {
        }

        public Object obtain() {
            return null;
        }

        public Object obtain(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public int getType(Object obj) {
            Object obj2 = obj;
            return -1;
        }

        public int getLayer(Object obj) {
            Object obj2 = obj;
            return -1;
        }

        public Object getRoot(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public Object getParent(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public int getId(Object obj) {
            Object obj2 = obj;
            return -1;
        }

        public void getBoundsInScreen(Object obj, Rect rect) {
            Object obj2 = obj;
            Rect rect2 = rect;
        }

        public boolean isActive(Object obj) {
            Object obj2 = obj;
            return true;
        }

        public boolean isFocused(Object obj) {
            Object obj2 = obj;
            return true;
        }

        public boolean isAccessibilityFocused(Object obj) {
            Object obj2 = obj;
            return true;
        }

        public int getChildCount(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public Object getChild(Object obj, int i) {
            Object obj2 = obj;
            int i2 = i;
            return null;
        }

        public void recycle(Object obj) {
            Object obj2 = obj;
        }

        public CharSequence getTitle(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public Object getAnchor(Object obj) {
            Object obj2 = obj;
            return null;
        }
    }

    static {
        if (VERSION.SDK_INT >= 24) {
            IMPL = new AccessibilityWindowInfoApi24Impl();
        } else if (VERSION.SDK_INT < 21) {
            IMPL = new AccessibilityWindowInfoStubImpl();
        } else {
            IMPL = new AccessibilityWindowInfoApi21Impl();
        }
    }

    static AccessibilityWindowInfoCompat wrapNonNullInstance(Object obj) {
        Object object = obj;
        Object obj2 = object;
        if (object == null) {
            return null;
        }
        return new AccessibilityWindowInfoCompat(object);
    }

    private AccessibilityWindowInfoCompat(Object obj) {
        Object info = obj;
        Object obj2 = info;
        this.mInfo = info;
    }

    public int getType() {
        return IMPL.getType(this.mInfo);
    }

    public int getLayer() {
        return IMPL.getLayer(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getRoot() {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.getRoot(this.mInfo));
    }

    public AccessibilityWindowInfoCompat getParent() {
        return wrapNonNullInstance(IMPL.getParent(this.mInfo));
    }

    public int getId() {
        return IMPL.getId(this.mInfo);
    }

    public void getBoundsInScreen(Rect rect) {
        Rect outBounds = rect;
        Rect rect2 = outBounds;
        IMPL.getBoundsInScreen(this.mInfo, outBounds);
    }

    public boolean isActive() {
        return IMPL.isActive(this.mInfo);
    }

    public boolean isFocused() {
        return IMPL.isFocused(this.mInfo);
    }

    public boolean isAccessibilityFocused() {
        return IMPL.isAccessibilityFocused(this.mInfo);
    }

    public int getChildCount() {
        return IMPL.getChildCount(this.mInfo);
    }

    public AccessibilityWindowInfoCompat getChild(int i) {
        int index = i;
        int i2 = index;
        return wrapNonNullInstance(IMPL.getChild(this.mInfo, index));
    }

    public CharSequence getTitle() {
        return IMPL.getTitle(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getAnchor() {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.getAnchor(this.mInfo));
    }

    public static AccessibilityWindowInfoCompat obtain() {
        return wrapNonNullInstance(IMPL.obtain());
    }

    public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat accessibilityWindowInfoCompat) {
        AccessibilityWindowInfoCompat info = accessibilityWindowInfoCompat;
        AccessibilityWindowInfoCompat accessibilityWindowInfoCompat2 = info;
        return info != null ? wrapNonNullInstance(IMPL.obtain(info.mInfo)) : null;
    }

    public void recycle() {
        IMPL.recycle(this.mInfo);
    }

    public int hashCode() {
        return this.mInfo != null ? this.mInfo.hashCode() : 0;
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
        AccessibilityWindowInfoCompat other = (AccessibilityWindowInfoCompat) obj2;
        if (this.mInfo != null) {
            if (!this.mInfo.equals(other.mInfo)) {
                return false;
            }
        } else if (other.mInfo != null) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        Rect bounds = new Rect();
        getBoundsInScreen(bounds);
        StringBuilder append = builder.append("AccessibilityWindowInfo[");
        StringBuilder append2 = builder.append("id=").append(getId());
        StringBuilder append3 = builder.append(", type=").append(typeToString(getType()));
        StringBuilder append4 = builder.append(", layer=").append(getLayer());
        StringBuilder append5 = builder.append(", bounds=").append(bounds);
        StringBuilder append6 = builder.append(", focused=").append(isFocused());
        StringBuilder append7 = builder.append(", active=").append(isActive());
        StringBuilder append8 = builder.append(", hasParent=").append(getParent() != null);
        StringBuilder append9 = builder.append(", hasChildren=").append(getChildCount() > 0);
        StringBuilder append10 = builder.append(']');
        return builder.toString();
    }

    private static String typeToString(int i) {
        int type = i;
        int i2 = type;
        switch (type) {
            case 1:
                return "TYPE_APPLICATION";
            case 2:
                return "TYPE_INPUT_METHOD";
            case 3:
                return "TYPE_SYSTEM";
            case 4:
                return "TYPE_ACCESSIBILITY_OVERLAY";
            default:
                return "<UNKNOWN>";
        }
    }
}
