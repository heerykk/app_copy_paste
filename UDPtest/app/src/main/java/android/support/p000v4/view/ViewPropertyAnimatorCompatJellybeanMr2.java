package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.animation.Interpolator;

@TargetApi(18)
@RequiresApi(18)
/* renamed from: android.support.v4.view.ViewPropertyAnimatorCompatJellybeanMr2 */
class ViewPropertyAnimatorCompatJellybeanMr2 {
    ViewPropertyAnimatorCompatJellybeanMr2() {
    }

    public static Interpolator getInterpolator(View view) {
        View view2 = view;
        View view3 = view2;
        return (Interpolator) view2.animate().getInterpolator();
    }
}
