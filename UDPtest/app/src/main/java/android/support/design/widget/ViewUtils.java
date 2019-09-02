package android.support.design.widget;

import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;

class ViewUtils {
    static final Creator DEFAULT_ANIMATOR_CREATOR = new Creator() {
        public ValueAnimatorCompat createAnimator() {
            return new ValueAnimatorCompat(VERSION.SDK_INT < 12 ? new ValueAnimatorCompatImplGingerbread() : new ValueAnimatorCompatImplHoneycombMr1());
        }
    };

    ViewUtils() {
    }

    static ValueAnimatorCompat createAnimator() {
        return DEFAULT_ANIMATOR_CREATOR.createAnimator();
    }

    static boolean objectEquals(Object obj, Object obj2) {
        Object a = obj;
        Object b = obj2;
        Object obj3 = a;
        Object obj4 = b;
        return a == b || (a != null && a.equals(b));
    }

    static Mode parseTintMode(int i, Mode mode) {
        int value = i;
        Mode defaultMode = mode;
        int i2 = value;
        Mode mode2 = defaultMode;
        switch (value) {
            case 3:
                return Mode.SRC_OVER;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            default:
                return defaultMode;
        }
    }
}
