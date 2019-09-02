package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.WindowInsets;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.view.WindowInsetsCompatApi21 */
class WindowInsetsCompatApi21 {
    WindowInsetsCompatApi21() {
    }

    public static Object consumeStableInsets(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).consumeStableInsets();
    }

    public static int getStableInsetBottom(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).getStableInsetBottom();
    }

    public static int getStableInsetLeft(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).getStableInsetLeft();
    }

    public static int getStableInsetRight(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).getStableInsetRight();
    }

    public static int getStableInsetTop(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).getStableInsetTop();
    }

    public static boolean hasStableInsets(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).hasStableInsets();
    }

    public static boolean isConsumed(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return ((WindowInsets) insets).isConsumed();
    }

    public static Object replaceSystemWindowInsets(Object obj, Rect rect) {
        Object insets = obj;
        Rect systemWindowInsets = rect;
        Object obj2 = insets;
        Rect rect2 = systemWindowInsets;
        return ((WindowInsets) insets).replaceSystemWindowInsets(systemWindowInsets);
    }
}
