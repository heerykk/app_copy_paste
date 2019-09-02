package android.support.p000v4.view;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.Gravity;

/* renamed from: android.support.v4.view.GravityCompat */
public final class GravityCompat {
    public static final int END = 8388613;
    static final GravityCompatImpl IMPL;
    public static final int RELATIVE_HORIZONTAL_GRAVITY_MASK = 8388615;
    public static final int RELATIVE_LAYOUT_DIRECTION = 8388608;
    public static final int START = 8388611;

    /* renamed from: android.support.v4.view.GravityCompat$GravityCompatImpl */
    interface GravityCompatImpl {
        void apply(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6);

        void apply(int i, int i2, int i3, Rect rect, Rect rect2, int i4);

        void applyDisplay(int i, Rect rect, Rect rect2, int i2);

        int getAbsoluteGravity(int i, int i2);
    }

    /* renamed from: android.support.v4.view.GravityCompat$GravityCompatImplBase */
    static class GravityCompatImplBase implements GravityCompatImpl {
        GravityCompatImplBase() {
        }

        public int getAbsoluteGravity(int i, int i2) {
            int gravity = i;
            int i3 = gravity;
            int i4 = i2;
            return gravity & -8388609;
        }

        public void apply(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
            int gravity = i;
            int w = i2;
            int h = i3;
            Rect container = rect;
            Rect outRect = rect2;
            int i5 = gravity;
            int i6 = w;
            int i7 = h;
            Rect rect3 = container;
            Rect rect4 = outRect;
            int i8 = i4;
            Gravity.apply(gravity, w, h, container, outRect);
        }

        public void apply(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6) {
            int gravity = i;
            int w = i2;
            int h = i3;
            Rect container = rect;
            int xAdj = i4;
            int yAdj = i5;
            Rect outRect = rect2;
            int i7 = gravity;
            int i8 = w;
            int i9 = h;
            Rect rect3 = container;
            int i10 = xAdj;
            int i11 = yAdj;
            Rect rect4 = outRect;
            int i12 = i6;
            Gravity.apply(gravity, w, h, container, xAdj, yAdj, outRect);
        }

        public void applyDisplay(int i, Rect rect, Rect rect2, int i2) {
            int gravity = i;
            Rect display = rect;
            Rect inoutObj = rect2;
            int i3 = gravity;
            Rect rect3 = display;
            Rect rect4 = inoutObj;
            int i4 = i2;
            Gravity.applyDisplay(gravity, display, inoutObj);
        }
    }

    /* renamed from: android.support.v4.view.GravityCompat$GravityCompatImplJellybeanMr1 */
    static class GravityCompatImplJellybeanMr1 implements GravityCompatImpl {
        GravityCompatImplJellybeanMr1() {
        }

        public int getAbsoluteGravity(int i, int i2) {
            int gravity = i;
            int layoutDirection = i2;
            int i3 = gravity;
            int i4 = layoutDirection;
            return GravityCompatJellybeanMr1.getAbsoluteGravity(gravity, layoutDirection);
        }

        public void apply(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
            int gravity = i;
            int w = i2;
            int h = i3;
            Rect container = rect;
            Rect outRect = rect2;
            int layoutDirection = i4;
            int i5 = gravity;
            int i6 = w;
            int i7 = h;
            Rect rect3 = container;
            Rect rect4 = outRect;
            int i8 = layoutDirection;
            GravityCompatJellybeanMr1.apply(gravity, w, h, container, outRect, layoutDirection);
        }

        public void apply(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6) {
            int gravity = i;
            int w = i2;
            int h = i3;
            Rect container = rect;
            int xAdj = i4;
            int yAdj = i5;
            Rect outRect = rect2;
            int layoutDirection = i6;
            int i7 = gravity;
            int i8 = w;
            int i9 = h;
            Rect rect3 = container;
            int i10 = xAdj;
            int i11 = yAdj;
            Rect rect4 = outRect;
            int i12 = layoutDirection;
            GravityCompatJellybeanMr1.apply(gravity, w, h, container, xAdj, yAdj, outRect, layoutDirection);
        }

        public void applyDisplay(int i, Rect rect, Rect rect2, int i2) {
            int gravity = i;
            Rect display = rect;
            Rect inoutObj = rect2;
            int layoutDirection = i2;
            int i3 = gravity;
            Rect rect3 = display;
            Rect rect4 = inoutObj;
            int i4 = layoutDirection;
            GravityCompatJellybeanMr1.applyDisplay(gravity, display, inoutObj, layoutDirection);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 17) {
            IMPL = new GravityCompatImplBase();
        } else {
            IMPL = new GravityCompatImplJellybeanMr1();
        }
    }

    public static void apply(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
        int gravity = i;
        int w = i2;
        int h = i3;
        Rect container = rect;
        Rect outRect = rect2;
        int layoutDirection = i4;
        int i5 = gravity;
        int i6 = w;
        int i7 = h;
        Rect rect3 = container;
        Rect rect4 = outRect;
        int i8 = layoutDirection;
        IMPL.apply(gravity, w, h, container, outRect, layoutDirection);
    }

    public static void apply(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6) {
        int gravity = i;
        int w = i2;
        int h = i3;
        Rect container = rect;
        int xAdj = i4;
        int yAdj = i5;
        Rect outRect = rect2;
        int layoutDirection = i6;
        int i7 = gravity;
        int i8 = w;
        int i9 = h;
        Rect rect3 = container;
        int i10 = xAdj;
        int i11 = yAdj;
        Rect rect4 = outRect;
        int i12 = layoutDirection;
        IMPL.apply(gravity, w, h, container, xAdj, yAdj, outRect, layoutDirection);
    }

    public static void applyDisplay(int i, Rect rect, Rect rect2, int i2) {
        int gravity = i;
        Rect display = rect;
        Rect inoutObj = rect2;
        int layoutDirection = i2;
        int i3 = gravity;
        Rect rect3 = display;
        Rect rect4 = inoutObj;
        int i4 = layoutDirection;
        IMPL.applyDisplay(gravity, display, inoutObj, layoutDirection);
    }

    public static int getAbsoluteGravity(int i, int i2) {
        int gravity = i;
        int layoutDirection = i2;
        int i3 = gravity;
        int i4 = layoutDirection;
        return IMPL.getAbsoluteGravity(gravity, layoutDirection);
    }

    private GravityCompat() {
    }
}
