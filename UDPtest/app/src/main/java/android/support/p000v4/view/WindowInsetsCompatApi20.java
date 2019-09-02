package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.WindowInsets;

@TargetApi(20)
@RequiresApi(20)
/* renamed from: android.support.v4.view.WindowInsetsCompatApi20 */
class WindowInsetsCompatApi20 {
    WindowInsetsCompatApi20() {
    }

    public static Object consumeSystemWindowInsets(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).consumeSystemWindowInsets();
    }

    public static int getSystemWindowInsetBottom(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).getSystemWindowInsetBottom();
    }

    public static int getSystemWindowInsetLeft(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).getSystemWindowInsetLeft();
    }

    public static int getSystemWindowInsetRight(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).getSystemWindowInsetRight();
    }

    public static int getSystemWindowInsetTop(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).getSystemWindowInsetTop();
    }

    public static boolean hasInsets(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).hasInsets();
    }

    public static boolean hasSystemWindowInsets(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).hasSystemWindowInsets();
    }

    public static boolean isRound(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).isRound();
    }

    public static Object replaceSystemWindowInsets(Object obj, int i, int i2, int i3, int i4) {
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
        return ((WindowInsets) insets).replaceSystemWindowInsets(left, top, right, bottom);
    }

    public static Object getSourceWindowInsets(Object obj) {
        Object src = obj;
        Object obj2 = src;
        return new WindowInsets((WindowInsets) src);
    }
}
