package android.support.design.widget;

import android.support.annotation.NonNull;
import android.view.animation.Interpolator;

class ValueAnimatorCompat {
    private final Impl mImpl;

    interface AnimatorListener {
        void onAnimationCancel(ValueAnimatorCompat valueAnimatorCompat);

        void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat);

        void onAnimationStart(ValueAnimatorCompat valueAnimatorCompat);
    }

    static class AnimatorListenerAdapter implements AnimatorListener {
        AnimatorListenerAdapter() {
        }

        public void onAnimationStart(ValueAnimatorCompat valueAnimatorCompat) {
            ValueAnimatorCompat valueAnimatorCompat2 = valueAnimatorCompat;
        }

        public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
            ValueAnimatorCompat valueAnimatorCompat2 = valueAnimatorCompat;
        }

        public void onAnimationCancel(ValueAnimatorCompat valueAnimatorCompat) {
            ValueAnimatorCompat valueAnimatorCompat2 = valueAnimatorCompat;
        }
    }

    interface AnimatorUpdateListener {
        void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat);
    }

    interface Creator {
        @NonNull
        ValueAnimatorCompat createAnimator();
    }

    static abstract class Impl {

        interface AnimatorListenerProxy {
            void onAnimationCancel();

            void onAnimationEnd();

            void onAnimationStart();
        }

        interface AnimatorUpdateListenerProxy {
            void onAnimationUpdate();
        }

        /* access modifiers changed from: 0000 */
        public abstract void addListener(AnimatorListenerProxy animatorListenerProxy);

        /* access modifiers changed from: 0000 */
        public abstract void addUpdateListener(AnimatorUpdateListenerProxy animatorUpdateListenerProxy);

        /* access modifiers changed from: 0000 */
        public abstract void cancel();

        /* access modifiers changed from: 0000 */
        public abstract void end();

        /* access modifiers changed from: 0000 */
        public abstract float getAnimatedFloatValue();

        /* access modifiers changed from: 0000 */
        public abstract float getAnimatedFraction();

        /* access modifiers changed from: 0000 */
        public abstract int getAnimatedIntValue();

        /* access modifiers changed from: 0000 */
        public abstract long getDuration();

        /* access modifiers changed from: 0000 */
        public abstract boolean isRunning();

        /* access modifiers changed from: 0000 */
        public abstract void setDuration(long j);

        /* access modifiers changed from: 0000 */
        public abstract void setFloatValues(float f, float f2);

        /* access modifiers changed from: 0000 */
        public abstract void setIntValues(int i, int i2);

        /* access modifiers changed from: 0000 */
        public abstract void setInterpolator(Interpolator interpolator);

        /* access modifiers changed from: 0000 */
        public abstract void start();

        Impl() {
        }
    }

    ValueAnimatorCompat(Impl impl) {
        Impl impl2 = impl;
        Impl impl3 = impl2;
        this.mImpl = impl2;
    }

    public void start() {
        this.mImpl.start();
    }

    public boolean isRunning() {
        return this.mImpl.isRunning();
    }

    public void setInterpolator(Interpolator interpolator) {
        Interpolator interpolator2 = interpolator;
        Interpolator interpolator3 = interpolator2;
        this.mImpl.setInterpolator(interpolator2);
    }

    public void addUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        AnimatorUpdateListener updateListener = animatorUpdateListener;
        AnimatorUpdateListener animatorUpdateListener2 = updateListener;
        if (updateListener == null) {
            this.mImpl.addUpdateListener(null);
            return;
        }
        final AnimatorUpdateListener animatorUpdateListener3 = updateListener;
        this.mImpl.addUpdateListener(new AnimatorUpdateListenerProxy(this) {
            final /* synthetic */ ValueAnimatorCompat this$0;

            {
                ValueAnimatorCompat this$02 = r6;
                AnimatorUpdateListener animatorUpdateListener = r7;
                ValueAnimatorCompat valueAnimatorCompat = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationUpdate() {
                animatorUpdateListener3.onAnimationUpdate(this.this$0);
            }
        });
    }

    public void addListener(AnimatorListener animatorListener) {
        AnimatorListener listener = animatorListener;
        AnimatorListener animatorListener2 = listener;
        if (listener == null) {
            this.mImpl.addListener(null);
            return;
        }
        final AnimatorListener animatorListener3 = listener;
        this.mImpl.addListener(new AnimatorListenerProxy(this) {
            final /* synthetic */ ValueAnimatorCompat this$0;

            {
                ValueAnimatorCompat this$02 = r6;
                AnimatorListener animatorListener = r7;
                ValueAnimatorCompat valueAnimatorCompat = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationStart() {
                animatorListener3.onAnimationStart(this.this$0);
            }

            public void onAnimationEnd() {
                animatorListener3.onAnimationEnd(this.this$0);
            }

            public void onAnimationCancel() {
                animatorListener3.onAnimationCancel(this.this$0);
            }
        });
    }

    public void setIntValues(int i, int i2) {
        int from = i;
        int to = i2;
        int i3 = from;
        int i4 = to;
        this.mImpl.setIntValues(from, to);
    }

    public int getAnimatedIntValue() {
        return this.mImpl.getAnimatedIntValue();
    }

    public void setFloatValues(float f, float f2) {
        float from = f;
        float to = f2;
        float f3 = from;
        float f4 = to;
        this.mImpl.setFloatValues(from, to);
    }

    public float getAnimatedFloatValue() {
        return this.mImpl.getAnimatedFloatValue();
    }

    public void setDuration(long j) {
        long duration = j;
        long j2 = duration;
        this.mImpl.setDuration(duration);
    }

    public void cancel() {
        this.mImpl.cancel();
    }

    public float getAnimatedFraction() {
        return this.mImpl.getAnimatedFraction();
    }

    public void end() {
        this.mImpl.end();
    }

    public long getDuration() {
        return this.mImpl.getDuration();
    }
}
