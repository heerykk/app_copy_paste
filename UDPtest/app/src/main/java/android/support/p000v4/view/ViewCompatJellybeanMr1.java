package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.graphics.Paint;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.View;

@TargetApi(17)
@RequiresApi(17)
/* renamed from: android.support.v4.view.ViewCompatJellybeanMr1 */
class ViewCompatJellybeanMr1 {
    ViewCompatJellybeanMr1() {
    }

    public static int getLabelFor(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getLabelFor();
    }

    public static void setLabelFor(View view, int i) {
        View view2 = view;
        int id = i;
        View view3 = view2;
        int i2 = id;
        view2.setLabelFor(id);
    }

    public static void setLayerPaint(View view, Paint paint) {
        View view2 = view;
        Paint paint2 = paint;
        View view3 = view2;
        Paint paint3 = paint2;
        view2.setLayerPaint(paint2);
    }

    public static int getLayoutDirection(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getLayoutDirection();
    }

    public static void setLayoutDirection(View view, int i) {
        View view2 = view;
        int layoutDirection = i;
        View view3 = view2;
        int i2 = layoutDirection;
        view2.setLayoutDirection(layoutDirection);
    }

    public static int getPaddingStart(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getPaddingStart();
    }

    public static int getPaddingEnd(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getPaddingEnd();
    }

    public static void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
        View view2 = view;
        int start = i;
        int top = i2;
        int end = i3;
        int bottom = i4;
        View view3 = view2;
        int i5 = start;
        int i6 = top;
        int i7 = end;
        int i8 = bottom;
        view2.setPaddingRelative(start, top, end, bottom);
    }

    public static int getWindowSystemUiVisibility(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getWindowSystemUiVisibility();
    }

    public static boolean isPaddingRelative(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.isPaddingRelative();
    }

    public static Display getDisplay(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getDisplay();
    }
}
