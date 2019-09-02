package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowInsets;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.widget.DrawerLayoutCompatApi21 */
class DrawerLayoutCompatApi21 {
    private static final int[] THEME_ATTRS;

    /* renamed from: android.support.v4.widget.DrawerLayoutCompatApi21$InsetsListener */
    static class InsetsListener implements OnApplyWindowInsetsListener {
        InsetsListener() {
        }

        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            View v = view;
            WindowInsets insets = windowInsets;
            View view2 = v;
            WindowInsets windowInsets2 = insets;
            DrawerLayoutImpl drawerLayoutImpl = (DrawerLayoutImpl) v;
            DrawerLayoutImpl drawerLayoutImpl2 = drawerLayoutImpl;
            drawerLayoutImpl.setChildInsets(insets, insets.getSystemWindowInsetTop() > 0);
            return insets.consumeSystemWindowInsets();
        }
    }

    DrawerLayoutCompatApi21() {
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 16843828;
        THEME_ATTRS = iArr;
    }

    public static void configureApplyInsets(View view) {
        View drawerLayout = view;
        View view2 = drawerLayout;
        if (drawerLayout instanceof DrawerLayoutImpl) {
            drawerLayout.setOnApplyWindowInsetsListener(new InsetsListener());
            drawerLayout.setSystemUiVisibility(1280);
        }
    }

    public static void dispatchChildInsets(View view, Object obj, int i) {
        View child = view;
        Object insets = obj;
        int gravity = i;
        View view2 = child;
        Object obj2 = insets;
        int i2 = gravity;
        WindowInsets windowInsets = (WindowInsets) insets;
        WindowInsets wi = windowInsets;
        if (gravity == 3) {
            wi = windowInsets.replaceSystemWindowInsets(wi.getSystemWindowInsetLeft(), wi.getSystemWindowInsetTop(), 0, wi.getSystemWindowInsetBottom());
        } else if (gravity == 5) {
            wi = windowInsets.replaceSystemWindowInsets(0, wi.getSystemWindowInsetTop(), wi.getSystemWindowInsetRight(), wi.getSystemWindowInsetBottom());
        }
        WindowInsets dispatchApplyWindowInsets = child.dispatchApplyWindowInsets(wi);
    }

    public static void applyMarginInsets(MarginLayoutParams marginLayoutParams, Object obj, int i) {
        MarginLayoutParams lp = marginLayoutParams;
        Object insets = obj;
        int gravity = i;
        MarginLayoutParams marginLayoutParams2 = lp;
        Object obj2 = insets;
        int i2 = gravity;
        WindowInsets windowInsets = (WindowInsets) insets;
        WindowInsets wi = windowInsets;
        if (gravity == 3) {
            wi = windowInsets.replaceSystemWindowInsets(wi.getSystemWindowInsetLeft(), wi.getSystemWindowInsetTop(), 0, wi.getSystemWindowInsetBottom());
        } else if (gravity == 5) {
            wi = windowInsets.replaceSystemWindowInsets(0, wi.getSystemWindowInsetTop(), wi.getSystemWindowInsetRight(), wi.getSystemWindowInsetBottom());
        }
        lp.leftMargin = wi.getSystemWindowInsetLeft();
        lp.topMargin = wi.getSystemWindowInsetTop();
        lp.rightMargin = wi.getSystemWindowInsetRight();
        lp.bottomMargin = wi.getSystemWindowInsetBottom();
    }

    public static int getTopInset(Object obj) {
        Object insets = obj;
        Object obj2 = insets;
        return insets == null ? 0 : ((WindowInsets) insets).getSystemWindowInsetTop();
    }

    /* JADX INFO: finally extract failed */
    public static Drawable getDefaultStatusBarBackground(Context context) {
        Context context2 = context;
        Context context3 = context2;
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(THEME_ATTRS);
        TypedArray a = obtainStyledAttributes;
        try {
            Drawable drawable = obtainStyledAttributes.getDrawable(0);
            a.recycle();
            return drawable;
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }
}
