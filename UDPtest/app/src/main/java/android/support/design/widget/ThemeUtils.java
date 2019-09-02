package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.p003v7.appcompat.C0268R;

class ThemeUtils {
    private static final int[] APPCOMPAT_CHECK_ATTRS;

    ThemeUtils() {
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = C0268R.attr.colorPrimary;
        APPCOMPAT_CHECK_ATTRS = iArr;
    }

    static void checkAppCompatTheme(Context context) {
        Context context2 = context;
        Context context3 = context2;
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        TypedArray a = obtainStyledAttributes;
        boolean failed = !obtainStyledAttributes.hasValue(0);
        if (a != null) {
            a.recycle();
        }
        if (failed) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme (or descendant) with the design library.");
        }
    }
}
