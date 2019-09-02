package android.support.p000v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.view.ViewPropertyAnimatorCompatICS */
class ViewPropertyAnimatorCompatICS {
    ViewPropertyAnimatorCompatICS() {
    }

    public static void setDuration(View view, long j) {
        View view2 = view;
        long value = j;
        View view3 = view2;
        long j2 = value;
        ViewPropertyAnimator duration = view2.animate().setDuration(value);
    }

    public static void alpha(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator alpha = view2.animate().alpha(value);
    }

    public static void translationX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator translationX = view2.animate().translationX(value);
    }

    public static void translationY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator translationY = view2.animate().translationY(value);
    }

    public static long getDuration(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.animate().getDuration();
    }

    public static void setInterpolator(View view, Interpolator interpolator) {
        View view2 = view;
        Interpolator value = interpolator;
        View view3 = view2;
        Interpolator interpolator2 = value;
        ViewPropertyAnimator interpolator3 = view2.animate().setInterpolator(value);
    }

    public static void setStartDelay(View view, long j) {
        View view2 = view;
        long value = j;
        View view3 = view2;
        long j2 = value;
        ViewPropertyAnimator startDelay = view2.animate().setStartDelay(value);
    }

    public static long getStartDelay(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.animate().getStartDelay();
    }

    public static void alphaBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator alphaBy = view2.animate().alphaBy(value);
    }

    public static void rotation(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator rotation = view2.animate().rotation(value);
    }

    public static void rotationBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator rotationBy = view2.animate().rotationBy(value);
    }

    public static void rotationX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator rotationX = view2.animate().rotationX(value);
    }

    public static void rotationXBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator rotationXBy = view2.animate().rotationXBy(value);
    }

    public static void rotationY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator rotationY = view2.animate().rotationY(value);
    }

    public static void rotationYBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator rotationYBy = view2.animate().rotationYBy(value);
    }

    public static void scaleX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator scaleX = view2.animate().scaleX(value);
    }

    public static void scaleXBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator scaleXBy = view2.animate().scaleXBy(value);
    }

    public static void scaleY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator scaleY = view2.animate().scaleY(value);
    }

    public static void scaleYBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator scaleYBy = view2.animate().scaleYBy(value);
    }

    public static void cancel(View view) {
        View view2 = view;
        View view3 = view2;
        view2.animate().cancel();
    }

    /* renamed from: x */
    public static void m26x(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator x = view2.animate().x(value);
    }

    public static void xBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator xBy = view2.animate().xBy(value);
    }

    /* renamed from: y */
    public static void m27y(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator y = view2.animate().y(value);
    }

    public static void yBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator yBy = view2.animate().yBy(value);
    }

    public static void translationXBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator translationXBy = view2.animate().translationXBy(value);
    }

    public static void translationYBy(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        ViewPropertyAnimator translationYBy = view2.animate().translationYBy(value);
    }

    public static void start(View view) {
        View view2 = view;
        View view3 = view2;
        view2.animate().start();
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
