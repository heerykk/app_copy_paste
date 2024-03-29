package android.support.p000v4.animation;

import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.view.View;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.animation.AnimatorCompatHelper */
public final class AnimatorCompatHelper {
    private static final AnimatorProvider IMPL;

    static {
        if (VERSION.SDK_INT < 12) {
            IMPL = new GingerbreadAnimatorCompatProvider();
        } else {
            IMPL = new HoneycombMr1AnimatorCompatProvider();
        }
    }

    public static ValueAnimatorCompat emptyValueAnimator() {
        return IMPL.emptyValueAnimator();
    }

    private AnimatorCompatHelper() {
    }

    public static void clearInterpolator(View view) {
        View view2 = view;
        View view3 = view2;
        IMPL.clearInterpolator(view2);
    }
}
