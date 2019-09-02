package android.support.p000v4.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewPropertyAnimator;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.view.ViewPropertyAnimatorCompatKK */
class ViewPropertyAnimatorCompatKK {
    ViewPropertyAnimatorCompatKK() {
    }

    public static void setUpdateListener(View view, ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
        View view2 = view;
        ViewPropertyAnimatorUpdateListener listener = viewPropertyAnimatorUpdateListener;
        View view3 = view2;
        ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener2 = listener;
        C02081 r5 = null;
        if (listener != null) {
            final ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener3 = listener;
            final View view4 = view2;
            r5 = new AnimatorUpdateListener() {
                {
                    View view = r6;
                }

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ValueAnimator valueAnimator2 = valueAnimator;
                    viewPropertyAnimatorUpdateListener3.onAnimationUpdate(view4);
                }
            };
        }
        ViewPropertyAnimator updateListener = view2.animate().setUpdateListener(r5);
    }
}
