package android.support.p000v4.view;

import android.graphics.Rect;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.view.WindowInsetsCompat */
public class WindowInsetsCompat {
    private static final WindowInsetsCompatImpl IMPL;
    private final Object mInsets;

    /* renamed from: android.support.v4.view.WindowInsetsCompat$WindowInsetsCompatApi20Impl */
    private static class WindowInsetsCompatApi20Impl extends WindowInsetsCompatBaseImpl {
        WindowInsetsCompatApi20Impl() {
        }

        public WindowInsetsCompat consumeSystemWindowInsets(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return new WindowInsetsCompat(WindowInsetsCompatApi20.consumeSystemWindowInsets(insets));
        }

        public int getSystemWindowInsetBottom(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi20.getSystemWindowInsetBottom(insets);
        }

        public int getSystemWindowInsetLeft(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi20.getSystemWindowInsetLeft(insets);
        }

        public int getSystemWindowInsetRight(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi20.getSystemWindowInsetRight(insets);
        }

        public int getSystemWindowInsetTop(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi20.getSystemWindowInsetTop(insets);
        }

        public boolean hasInsets(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi20.hasInsets(insets);
        }

        public boolean hasSystemWindowInsets(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi20.hasSystemWindowInsets(insets);
        }

        public boolean isRound(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi20.isRound(insets);
        }

        public WindowInsetsCompat replaceSystemWindowInsets(Object obj, int i, int i2, int i3, int i4) {
            Object insets = obj;
            int left = i;
            int top = i2;
            int right = i3;
            int bottom = i4;
            Object obj2 = insets;
            int i5 = left;
            int i6 = top;
            int i7 = right;
            int i8 = bottom;
            return new WindowInsetsCompat(WindowInsetsCompatApi20.replaceSystemWindowInsets(insets, left, top, right, bottom));
        }

        public Object getSourceWindowInsets(Object obj) {
            Object src = obj;
            Object obj2 = src;
            return WindowInsetsCompatApi20.getSourceWindowInsets(src);
        }
    }

    /* renamed from: android.support.v4.view.WindowInsetsCompat$WindowInsetsCompatApi21Impl */
    private static class WindowInsetsCompatApi21Impl extends WindowInsetsCompatApi20Impl {
        WindowInsetsCompatApi21Impl() {
        }

        public WindowInsetsCompat consumeStableInsets(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return new WindowInsetsCompat(WindowInsetsCompatApi21.consumeStableInsets(insets));
        }

        public int getStableInsetBottom(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi21.getStableInsetBottom(insets);
        }

        public int getStableInsetLeft(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi21.getStableInsetLeft(insets);
        }

        public int getStableInsetRight(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi21.getStableInsetRight(insets);
        }

        public int getStableInsetTop(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi21.getStableInsetTop(insets);
        }

        public boolean hasStableInsets(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi21.hasStableInsets(insets);
        }

        public boolean isConsumed(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return WindowInsetsCompatApi21.isConsumed(insets);
        }

        public WindowInsetsCompat replaceSystemWindowInsets(Object obj, Rect rect) {
            Object insets = obj;
            Rect systemWindowInsets = rect;
            Object obj2 = insets;
            Rect rect2 = systemWindowInsets;
            return new WindowInsetsCompat(WindowInsetsCompatApi21.replaceSystemWindowInsets(insets, systemWindowInsets));
        }
    }

    /* renamed from: android.support.v4.view.WindowInsetsCompat$WindowInsetsCompatBaseImpl */
    private static class WindowInsetsCompatBaseImpl implements WindowInsetsCompatImpl {
        WindowInsetsCompatBaseImpl() {
        }

        public int getSystemWindowInsetLeft(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getSystemWindowInsetTop(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getSystemWindowInsetRight(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getSystemWindowInsetBottom(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public boolean hasSystemWindowInsets(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean hasInsets(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isConsumed(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public boolean isRound(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public WindowInsetsCompat consumeSystemWindowInsets(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public WindowInsetsCompat replaceSystemWindowInsets(Object obj, int i, int i2, int i3, int i4) {
            Object obj2 = obj;
            int i5 = i;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
            return null;
        }

        public WindowInsetsCompat replaceSystemWindowInsets(Object obj, Rect rect) {
            Object obj2 = obj;
            Rect rect2 = rect;
            return null;
        }

        public int getStableInsetTop(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getStableInsetLeft(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getStableInsetRight(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public int getStableInsetBottom(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public boolean hasStableInsets(Object obj) {
            Object obj2 = obj;
            return false;
        }

        public WindowInsetsCompat consumeStableInsets(Object obj) {
            Object obj2 = obj;
            return null;
        }

        public Object getSourceWindowInsets(Object obj) {
            Object obj2 = obj;
            return null;
        }
    }

    /* renamed from: android.support.v4.view.WindowInsetsCompat$WindowInsetsCompatImpl */
    private interface WindowInsetsCompatImpl {
        WindowInsetsCompat consumeStableInsets(Object obj);

        WindowInsetsCompat consumeSystemWindowInsets(Object obj);

        Object getSourceWindowInsets(Object obj);

        int getStableInsetBottom(Object obj);

        int getStableInsetLeft(Object obj);

        int getStableInsetRight(Object obj);

        int getStableInsetTop(Object obj);

        int getSystemWindowInsetBottom(Object obj);

        int getSystemWindowInsetLeft(Object obj);

        int getSystemWindowInsetRight(Object obj);

        int getSystemWindowInsetTop(Object obj);

        boolean hasInsets(Object obj);

        boolean hasStableInsets(Object obj);

        boolean hasSystemWindowInsets(Object obj);

        boolean isConsumed(Object obj);

        boolean isRound(Object obj);

        WindowInsetsCompat replaceSystemWindowInsets(Object obj, int i, int i2, int i3, int i4);

        WindowInsetsCompat replaceSystemWindowInsets(Object obj, Rect rect);
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 21) {
            IMPL = new WindowInsetsCompatApi21Impl();
        } else if (version < 20) {
            IMPL = new WindowInsetsCompatBaseImpl();
        } else {
            IMPL = new WindowInsetsCompatApi20Impl();
        }
    }

    WindowInsetsCompat(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        this.mInsets = insets;
    }

    public WindowInsetsCompat(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat src = windowInsetsCompat;
        WindowInsetsCompat windowInsetsCompat2 = src;
        this.mInsets = src != null ? IMPL.getSourceWindowInsets(src.mInsets) : null;
    }

    public int getSystemWindowInsetLeft() {
        return IMPL.getSystemWindowInsetLeft(this.mInsets);
    }

    public int getSystemWindowInsetTop() {
        return IMPL.getSystemWindowInsetTop(this.mInsets);
    }

    public int getSystemWindowInsetRight() {
        return IMPL.getSystemWindowInsetRight(this.mInsets);
    }

    public int getSystemWindowInsetBottom() {
        return IMPL.getSystemWindowInsetBottom(this.mInsets);
    }

    public boolean hasSystemWindowInsets() {
        return IMPL.hasSystemWindowInsets(this.mInsets);
    }

    public boolean hasInsets() {
        return IMPL.hasInsets(this.mInsets);
    }

    public boolean isConsumed() {
        return IMPL.isConsumed(this.mInsets);
    }

    public boolean isRound() {
        return IMPL.isRound(this.mInsets);
    }

    public WindowInsetsCompat consumeSystemWindowInsets() {
        return IMPL.consumeSystemWindowInsets(this.mInsets);
    }

    public WindowInsetsCompat replaceSystemWindowInsets(int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        return IMPL.replaceSystemWindowInsets(this.mInsets, left, top, right, bottom);
    }

    public WindowInsetsCompat replaceSystemWindowInsets(Rect rect) {
        Rect systemWindowInsets = rect;
        Rect rect2 = systemWindowInsets;
        return IMPL.replaceSystemWindowInsets(this.mInsets, systemWindowInsets);
    }

    public int getStableInsetTop() {
        return IMPL.getStableInsetTop(this.mInsets);
    }

    public int getStableInsetLeft() {
        return IMPL.getStableInsetLeft(this.mInsets);
    }

    public int getStableInsetRight() {
        return IMPL.getStableInsetRight(this.mInsets);
    }

    public int getStableInsetBottom() {
        return IMPL.getStableInsetBottom(this.mInsets);
    }

    public boolean hasStableInsets() {
        return IMPL.hasStableInsets(this.mInsets);
    }

    public WindowInsetsCompat consumeStableInsets() {
        return IMPL.consumeStableInsets(this.mInsets);
    }

    public boolean equals(Object obj) {
        Object o = obj;
        Object obj2 = o;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WindowInsetsCompat other = (WindowInsetsCompat) o;
        boolean z = this.mInsets != null ? this.mInsets.equals(other.mInsets) : other.mInsets == null;
        return z;
    }

    public int hashCode() {
        return this.mInsets != null ? this.mInsets.hashCode() : 0;
    }

    static WindowInsetsCompat wrap(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return insets != null ? new WindowInsetsCompat(insets) : null;
    }

    static Object unwrap(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat insets = windowInsetsCompat;
        WindowInsetsCompat windowInsetsCompat2 = insets;
        if (insets != null) {
            return insets.mInsets;
        }
        return null;
    }
}
