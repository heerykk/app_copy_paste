package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.Gravity;

@TargetApi(17)
@RequiresApi(17)
/* renamed from: android.support.v4.view.GravityCompatJellybeanMr1 */
class GravityCompatJellybeanMr1 {
    GravityCompatJellybeanMr1() {
    }

    public static int getAbsoluteGravity(int i, int i2) {
        int gravity = i;
        int layoutDirection = i2;
        int i3 = gravity;
        int i4 = layoutDirection;
        return Gravity.getAbsoluteGravity(gravity, layoutDirection);
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
        Gravity.apply(gravity, w, h, container, outRect, layoutDirection);
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
        Gravity.apply(gravity, w, h, container, xAdj, yAdj, outRect, layoutDirection);
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
        Gravity.applyDisplay(gravity, display, inoutObj, layoutDirection);
    }
}
