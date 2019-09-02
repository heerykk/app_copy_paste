package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewPropertyAnimator;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.view.ViewPropertyAnimatorCompatLollipop */
class ViewPropertyAnimatorCompatLollipop {
    ViewPropertyAnimatorCompatLollipop() {
    }

    public static void translationZ(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator translationZ = view2.animate().translationZ(value);
    }

    public static void translationZBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator translationZBy = view2.animate().translationZBy(value);
    }

    /* renamed from: z */
    public static void m28z(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator z = view2.animate().z(value);
    }

    public static void zBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator zBy = view2.animate().zBy(value);
    }
}
