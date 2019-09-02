package android.support.p000v4.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewPropertyAnimator;

@TargetApi(12)
@RequiresApi(12)
/* renamed from: android.support.v4.animation.HoneycombMr1AnimatorCompatProvider */
class HoneycombMr1AnimatorCompatProvider implements AnimatorProvider {
    private TimeInterpolator mDefaultInterpolator;

    /* renamed from: android.support.v4.animation.HoneycombMr1AnimatorCompatProvider$AnimatorListenerCompatWrapper */
    static class AnimatorListenerCompatWrapper implements AnimatorListener {
        final ValueAnimatorCompat mValueAnimatorCompat;
        final AnimatorListenerCompat mWrapped;

        public AnimatorListenerCompatWrapper(AnimatorListenerCompat animatorListenerCompat, ValueAnimatorCompat valueAnimatorCompat) {
            AnimatorListenerCompat wrapped = animatorListenerCompat;
            ValueAnimatorCompat valueAnimatorCompat2 = valueAnimatorCompat;
            AnimatorListenerCompat animatorListenerCompat2 = wrapped;
            ValueAnimatorCompat valueAnimatorCompat3 = valueAnimatorCompat2;
            this.mWrapped = wrapped;
            this.mValueAnimatorCompat = valueAnimatorCompat2;
        }

        public void onAnimationStart(Animator animator) {
            Animator animator2 = animator;
            this.mWrapped.onAnimationStart(this.mValueAnimatorCompat);
        }

        public void onAnimationEnd(Animator animator) {
            Animator animator2 = animator;
            this.mWrapped.onAnimationEnd(this.mValueAnimatorCompat);
        }

        public void onAnimationCancel(Animator animator) {
            Animator animator2 = animator;
            this.mWrapped.onAnimationCancel(this.mValueAnimatorCompat);
        }

        public void onAnimationRepeat(Animator animator) {
            Animator animator2 = animator;
            this.mWrapped.onAnimationRepeat(this.mValueAnimatorCompat);
        }
    }

    /* renamed from: android.support.v4.animation.HoneycombMr1AnimatorCompatProvider$HoneycombValueAnimatorCompat */
    static class HoneycombValueAnimatorCompat implements ValueAnimatorCompat {
        final Animator mWrapped;

        public HoneycombValueAnimatorCompat(Animator animator) {
            Animator wrapped = animator;
            Animator animator2 = wrapped;
            this.mWrapped = wrapped;
        }

        public void setTarget(View view) {
            View view2 = view;
            View view3 = view2;
            this.mWrapped.setTarget(view2);
        }

        public void addListener(AnimatorListenerCompat animatorListenerCompat) {
            AnimatorListenerCompat listener = animatorListenerCompat;
            AnimatorListenerCompat animatorListenerCompat2 = listener;
            this.mWrapped.addListener(new AnimatorListenerCompatWrapper(listener, this));
        }

        public void setDuration(long j) {
            long duration = j;
            long j2 = duration;
            Animator duration2 = this.mWrapped.setDuration(duration);
        }

        public void start() {
            this.mWrapped.start();
        }

        public void cancel() {
            this.mWrapped.cancel();
        }

        public void addUpdateListener(AnimatorUpdateListenerCompat animatorUpdateListenerCompat) {
            AnimatorUpdateListenerCompat animatorUpdateListener = animatorUpdateListenerCompat;
            AnimatorUpdateListenerCompat animatorUpdateListenerCompat2 = animatorUpdateListener;
            if (this.mWrapped instanceof ValueAnimator) {
                final AnimatorUpdateListenerCompat animatorUpdateListenerCompat3 = animatorUpdateListener;
                ((ValueAnimator) this.mWrapped).addUpdateListener(new AnimatorUpdateListener(this) {
                    final /* synthetic */ HoneycombValueAnimatorCompat this$0;

                    {
                        HoneycombValueAnimatorCompat this$02 = r6;
                        AnimatorUpdateListenerCompat animatorUpdateListenerCompat = r7;
                        HoneycombValueAnimatorCompat honeycombValueAnimatorCompat = this$02;
                        this.this$0 = this$02;
                    }

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ValueAnimator valueAnimator2 = valueAnimator;
                        animatorUpdateListenerCompat3.onAnimationUpdate(this.this$0);
                    }
                });
            }
        }

        public float getAnimatedFraction() {
            return ((ValueAnimator) this.mWrapped).getAnimatedFraction();
        }
    }

    HoneycombMr1AnimatorCompatProvider() {
    }

    public ValueAnimatorCompat emptyValueAnimator() {
        return new HoneycombValueAnimatorCompat(ValueAnimator.ofFloat(new float[]{0.0f, 1.0f}));
    }

    public void clearInterpolator(View view) {
        View view2 = view;
        View view3 = view2;
        if (this.mDefaultInterpolator == null) {
            this.mDefaultInterpolator = new ValueAnimator().getInterpolator();
        }
        ViewPropertyAnimator interpolator = view2.animate().setInterpolator(this.mDefaultInterpolator);
    }
}
