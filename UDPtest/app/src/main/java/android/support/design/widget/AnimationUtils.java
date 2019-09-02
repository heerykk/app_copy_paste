package android.support.design.widget;

import android.support.p000v4.view.animation.FastOutLinearInInterpolator;
import android.support.p000v4.view.animation.FastOutSlowInInterpolator;
import android.support.p000v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

class AnimationUtils {
    static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    static final Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR = new FastOutLinearInInterpolator();
    static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();
    static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    static final Interpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();

    static class AnimationListenerAdapter implements AnimationListener {
        AnimationListenerAdapter() {
        }

        public void onAnimationStart(Animation animation) {
            Animation animation2 = animation;
        }

        public void onAnimationEnd(Animation animation) {
            Animation animation2 = animation;
        }

        public void onAnimationRepeat(Animation animation) {
            Animation animation2 = animation;
        }
    }

    AnimationUtils() {
    }

    static float lerp(float f, float f2, float f3) {
        float startValue = f;
        float endValue = f2;
        float fraction = f3;
        float f4 = startValue;
        float f5 = endValue;
        float f6 = fraction;
        return startValue + (fraction * (endValue - startValue));
    }

    static int lerp(int i, int i2, float f) {
        int startValue = i;
        int endValue = i2;
        float fraction = f;
        int i3 = startValue;
        int i4 = endValue;
        float f2 = fraction;
        return startValue + Math.round(fraction * ((float) (endValue - startValue)));
    }
}
