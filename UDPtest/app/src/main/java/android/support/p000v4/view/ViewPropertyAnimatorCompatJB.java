package android.support.p000v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewPropertyAnimator;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.view.ViewPropertyAnimatorCompatJB */
class ViewPropertyAnimatorCompatJB {
    ViewPropertyAnimatorCompatJB() {
    }

    public static void withStartAction(View view, Runnable runnable) {
        View view2 = view;
        Runnable runnable2 = runnable;
        View view3 = view2;
        Runnable runnable3 = runnable2;
        ViewPropertyAnimator withStartAction = view2.animate().withStartAction(runnable2);
    }

    public static void withEndAction(View view, Runnable runnable) {
        View view2 = view;
        Runnable runnable2 = runnable;
        View view3 = view2;
        Runnable runnable3 = runnable2;
        ViewPropertyAnimator withEndAction = view2.animate().withEndAction(runnable2);
    }

    public static void withLayer(View view) {
        View view2 = view;
        View view3 = view2;
        ViewPropertyAnimator withLayer = view2.animate().withLayer();
    }

    public static void setListener(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        View view2 = view;
        ViewPropertyAnimatorListener listener = viewPropertyAnimatorListener;
        View view3 = view2;
        ViewPropertyAnimatorListener viewPropertyAnimatorListener2 = listener;
        if (listener == null) {
            ViewPropertyAnimator listener2 = view2.animate().setListener(null);
            return;
        }
        final ViewPropertyAnimatorListener viewPropertyAnimatorListener3 = listener;
        final View view4 = view2;
        ViewPropertyAnimator listener3 = view2.animate().setListener(new AnimatorListenerAdapter() {
            {
                View view = r6;
            }

            public void onAnimationCancel(Animator animator) {
                Animator animator2 = animator;
                viewPropertyAnimatorListener3.onAnimationCancel(view4);
            }

            public void onAnimationEnd(Animator animator) {
                Animator animator2 = animator;
                viewPropertyAnimatorListener3.onAnimationEnd(view4);
            }

            public void onAnimationStart(Animator animator) {
                Animator animator2 = animator;
                viewPropertyAnimatorListener3.onAnimationStart(view4);
            }
        });
    }
}
