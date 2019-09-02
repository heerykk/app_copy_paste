package android.support.design.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import java.util.ArrayList;

class ValueAnimatorCompatImplGingerbread extends Impl {
    private static final int DEFAULT_DURATION = 200;
    private static final int HANDLER_DELAY = 10;
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private float mAnimatedFraction;
    private long mDuration = 200;
    private final float[] mFloatValues = new float[2];
    private final int[] mIntValues = new int[2];
    private Interpolator mInterpolator;
    private boolean mIsRunning;
    private ArrayList<AnimatorListenerProxy> mListeners;
    private final Runnable mRunnable = new Runnable(this) {
        final /* synthetic */ ValueAnimatorCompatImplGingerbread this$0;

        {
            ValueAnimatorCompatImplGingerbread this$02 = r5;
            ValueAnimatorCompatImplGingerbread valueAnimatorCompatImplGingerbread = this$02;
            this.this$0 = this$02;
        }

        public void run() {
            this.this$0.update();
        }
    };
    private long mStartTime;
    private ArrayList<AnimatorUpdateListenerProxy> mUpdateListeners;

    ValueAnimatorCompatImplGingerbread() {
    }

    public void start() {
        if (!this.mIsRunning) {
            if (this.mInterpolator == null) {
                this.mInterpolator = new AccelerateDecelerateInterpolator();
            }
            this.mIsRunning = true;
            this.mAnimatedFraction = 0.0f;
            startInternal();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void startInternal() {
        this.mStartTime = SystemClock.uptimeMillis();
        dispatchAnimationUpdate();
        dispatchAnimationStart();
        boolean postDelayed = sHandler.postDelayed(this.mRunnable, 10);
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public void setInterpolator(Interpolator interpolator) {
        Interpolator interpolator2 = interpolator;
        Interpolator interpolator3 = interpolator2;
        this.mInterpolator = interpolator2;
    }

    public void addListener(AnimatorListenerProxy animatorListenerProxy) {
        AnimatorListenerProxy listener = animatorListenerProxy;
        AnimatorListenerProxy animatorListenerProxy2 = listener;
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        boolean add = this.mListeners.add(listener);
    }

    public void addUpdateListener(AnimatorUpdateListenerProxy animatorUpdateListenerProxy) {
        AnimatorUpdateListenerProxy updateListener = animatorUpdateListenerProxy;
        AnimatorUpdateListenerProxy animatorUpdateListenerProxy2 = updateListener;
        if (this.mUpdateListeners == null) {
            this.mUpdateListeners = new ArrayList<>();
        }
        boolean add = this.mUpdateListeners.add(updateListener);
    }

    public void setIntValues(int i, int i2) {
        int from = i;
        int to = i2;
        int i3 = from;
        int i4 = to;
        this.mIntValues[0] = from;
        this.mIntValues[1] = to;
    }

    public int getAnimatedIntValue() {
        return AnimationUtils.lerp(this.mIntValues[0], this.mIntValues[1], getAnimatedFraction());
    }

    public void setFloatValues(float f, float f2) {
        float from = f;
        float to = f2;
        float f3 = from;
        float f4 = to;
        this.mFloatValues[0] = from;
        this.mFloatValues[1] = to;
    }

    public float getAnimatedFloatValue() {
        return AnimationUtils.lerp(this.mFloatValues[0], this.mFloatValues[1], getAnimatedFraction());
    }

    public void setDuration(long j) {
        long duration = j;
        long j2 = duration;
        this.mDuration = duration;
    }

    public void cancel() {
        this.mIsRunning = false;
        sHandler.removeCallbacks(this.mRunnable);
        dispatchAnimationCancel();
        dispatchAnimationEnd();
    }

    public float getAnimatedFraction() {
        return this.mAnimatedFraction;
    }

    public void end() {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            sHandler.removeCallbacks(this.mRunnable);
            this.mAnimatedFraction = 1.0f;
            dispatchAnimationUpdate();
            dispatchAnimationEnd();
        }
    }

    public long getDuration() {
        return this.mDuration;
    }

    /* access modifiers changed from: 0000 */
    public final void update() {
        if (this.mIsRunning) {
            long uptimeMillis = SystemClock.uptimeMillis() - this.mStartTime;
            long j = uptimeMillis;
            float constrain = MathUtils.constrain(((float) uptimeMillis) / ((float) this.mDuration), 0.0f, 1.0f);
            float f = constrain;
            this.mAnimatedFraction = this.mInterpolator == null ? constrain : this.mInterpolator.getInterpolation(constrain);
            dispatchAnimationUpdate();
            if (!(SystemClock.uptimeMillis() < this.mStartTime + this.mDuration)) {
                this.mIsRunning = false;
                dispatchAnimationEnd();
            }
        }
        if (this.mIsRunning) {
            boolean postDelayed = sHandler.postDelayed(this.mRunnable, 10);
        }
    }

    private void dispatchAnimationUpdate() {
        if (this.mUpdateListeners != null) {
            int count = this.mUpdateListeners.size();
            for (int i = 0; i < count; i++) {
                ((AnimatorUpdateListenerProxy) this.mUpdateListeners.get(i)).onAnimationUpdate();
            }
        }
    }

    private void dispatchAnimationStart() {
        if (this.mListeners != null) {
            int count = this.mListeners.size();
            for (int i = 0; i < count; i++) {
                ((AnimatorListenerProxy) this.mListeners.get(i)).onAnimationStart();
            }
        }
    }

    private void dispatchAnimationCancel() {
        if (this.mListeners != null) {
            int count = this.mListeners.size();
            for (int i = 0; i < count; i++) {
                ((AnimatorListenerProxy) this.mListeners.get(i)).onAnimationCancel();
            }
        }
    }

    private void dispatchAnimationEnd() {
        if (this.mListeners != null) {
            int count = this.mListeners.size();
            for (int i = 0; i < count; i++) {
                ((AnimatorListenerProxy) this.mListeners.get(i)).onAnimationEnd();
            }
        }
    }
}
