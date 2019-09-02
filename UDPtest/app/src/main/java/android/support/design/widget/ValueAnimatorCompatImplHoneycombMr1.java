package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.animation.Interpolator;

@TargetApi(12)
@RequiresApi(12)
class ValueAnimatorCompatImplHoneycombMr1 extends Impl {
    private final ValueAnimator mValueAnimator = new ValueAnimator();

    ValueAnimatorCompatImplHoneycombMr1() {
    }

    public void start() {
        this.mValueAnimator.start();
    }

    public boolean isRunning() {
        return this.mValueAnimator.isRunning();
    }

    public void setInterpolator(Interpolator interpolator) {
        Interpolator interpolator2 = interpolator;
        Interpolator interpolator3 = interpolator2;
        this.mValueAnimator.setInterpolator(interpolator2);
    }

    public void addUpdateListener(AnimatorUpdateListenerProxy animatorUpdateListenerProxy) {
        AnimatorUpdateListenerProxy updateListener = animatorUpdateListenerProxy;
        AnimatorUpdateListenerProxy animatorUpdateListenerProxy2 = updateListener;
        final AnimatorUpdateListenerProxy animatorUpdateListenerProxy3 = updateListener;
        this.mValueAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ ValueAnimatorCompatImplHoneycombMr1 this$0;

            {
                ValueAnimatorCompatImplHoneycombMr1 this$02 = r6;
                AnimatorUpdateListenerProxy animatorUpdateListenerProxy = r7;
                ValueAnimatorCompatImplHoneycombMr1 valueAnimatorCompatImplHoneycombMr1 = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ValueAnimator valueAnimator2 = valueAnimator;
                animatorUpdateListenerProxy3.onAnimationUpdate();
            }
        });
    }

    public void addListener(AnimatorListenerProxy animatorListenerProxy) {
        AnimatorListenerProxy listener = animatorListenerProxy;
        AnimatorListenerProxy animatorListenerProxy2 = listener;
        final AnimatorListenerProxy animatorListenerProxy3 = listener;
        this.mValueAnimator.addListener(new AnimatorListenerAdapter(this) {
            final /* synthetic */ ValueAnimatorCompatImplHoneycombMr1 this$0;

            {
                ValueAnimatorCompatImplHoneycombMr1 this$02 = r6;
                AnimatorListenerProxy animatorListenerProxy = r7;
                ValueAnimatorCompatImplHoneycombMr1 valueAnimatorCompatImplHoneycombMr1 = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationStart(Animator animator) {
                Animator animator2 = animator;
                animatorListenerProxy3.onAnimationStart();
            }

            public void onAnimationEnd(Animator animator) {
                Animator animator2 = animator;
                animatorListenerProxy3.onAnimationEnd();
            }

            public void onAnimationCancel(Animator animator) {
                Animator animator2 = animator;
                animatorListenerProxy3.onAnimationCancel();
            }
        });
    }

    public void setIntValues(int i, int i2) {
        int from = i;
        int to = i2;
        int i3 = from;
        int i4 = to;
        this.mValueAnimator.setIntValues(new int[]{from, to});
    }

    public int getAnimatedIntValue() {
        return ((Integer) this.mValueAnimator.getAnimatedValue()).intValue();
    }

    public void setFloatValues(float f, float f2) {
        float from = f;
        float to = f2;
        float f3 = from;
        float f4 = to;
        this.mValueAnimator.setFloatValues(new float[]{from, to});
    }

    public float getAnimatedFloatValue() {
        return ((Float) this.mValueAnimator.getAnimatedValue()).floatValue();
    }

    public void setDuration(long j) {
        long duration = j;
        long j2 = duration;
        ValueAnimator duration2 = this.mValueAnimator.setDuration(duration);
    }

    public void cancel() {
        this.mValueAnimator.cancel();
    }

    public float getAnimatedFraction() {
        return this.mValueAnimator.getAnimatedFraction();
    }

    public void end() {
        this.mValueAnimator.end();
    }

    public long getDuration() {
        return this.mValueAnimator.getDuration();
    }
}
