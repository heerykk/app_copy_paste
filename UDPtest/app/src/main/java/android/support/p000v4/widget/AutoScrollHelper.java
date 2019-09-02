package android.support.p000v4.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* renamed from: android.support.v4.widget.AutoScrollHelper */
public abstract class AutoScrollHelper implements OnTouchListener {
    private static final int DEFAULT_ACTIVATION_DELAY = ViewConfiguration.getTapTimeout();
    private static final int DEFAULT_EDGE_TYPE = 1;
    private static final float DEFAULT_MAXIMUM_EDGE = Float.MAX_VALUE;
    private static final int DEFAULT_MAXIMUM_VELOCITY_DIPS = 1575;
    private static final int DEFAULT_MINIMUM_VELOCITY_DIPS = 315;
    private static final int DEFAULT_RAMP_DOWN_DURATION = 500;
    private static final int DEFAULT_RAMP_UP_DURATION = 500;
    private static final float DEFAULT_RELATIVE_EDGE = 0.2f;
    private static final float DEFAULT_RELATIVE_VELOCITY = 1.0f;
    public static final int EDGE_TYPE_INSIDE = 0;
    public static final int EDGE_TYPE_INSIDE_EXTEND = 1;
    public static final int EDGE_TYPE_OUTSIDE = 2;
    private static final int HORIZONTAL = 0;
    public static final float NO_MAX = Float.MAX_VALUE;
    public static final float NO_MIN = 0.0f;
    public static final float RELATIVE_UNSPECIFIED = 0.0f;
    private static final int VERTICAL = 1;
    private int mActivationDelay;
    private boolean mAlreadyDelayed;
    boolean mAnimating;
    private final Interpolator mEdgeInterpolator = new AccelerateInterpolator();
    private int mEdgeType;
    private boolean mEnabled;
    private boolean mExclusive;
    private float[] mMaximumEdges;
    private float[] mMaximumVelocity;
    private float[] mMinimumVelocity;
    boolean mNeedsCancel;
    boolean mNeedsReset;
    private float[] mRelativeEdges;
    private float[] mRelativeVelocity;
    private Runnable mRunnable;
    final ClampedScroller mScroller = new ClampedScroller();
    final View mTarget;

    /* renamed from: android.support.v4.widget.AutoScrollHelper$ClampedScroller */
    private static class ClampedScroller {
        private long mDeltaTime = 0;
        private int mDeltaX = 0;
        private int mDeltaY = 0;
        private int mEffectiveRampDown;
        private int mRampDownDuration;
        private int mRampUpDuration;
        private long mStartTime = Long.MIN_VALUE;
        private long mStopTime = -1;
        private float mStopValue;
        private float mTargetVelocityX;
        private float mTargetVelocityY;

        ClampedScroller() {
        }

        public void setRampUpDuration(int i) {
            int durationMillis = i;
            int i2 = durationMillis;
            this.mRampUpDuration = durationMillis;
        }

        public void setRampDownDuration(int i) {
            int durationMillis = i;
            int i2 = durationMillis;
            this.mRampDownDuration = durationMillis;
        }

        public void start() {
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mStopTime = -1;
            this.mDeltaTime = this.mStartTime;
            this.mStopValue = 0.5f;
            this.mDeltaX = 0;
            this.mDeltaY = 0;
        }

        public void requestStop() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            long j = currentAnimationTimeMillis;
            this.mEffectiveRampDown = AutoScrollHelper.constrain((int) (currentAnimationTimeMillis - this.mStartTime), 0, this.mRampDownDuration);
            this.mStopValue = getValueAt(currentAnimationTimeMillis);
            this.mStopTime = currentAnimationTimeMillis;
        }

        public boolean isFinished() {
            boolean z;
            if (!(this.mStopTime <= 0)) {
                if (!(AnimationUtils.currentAnimationTimeMillis() <= this.mStopTime + ((long) this.mEffectiveRampDown))) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        }

        private float getValueAt(long j) {
            long currentTime = j;
            long j2 = currentTime;
            if (!(currentTime >= this.mStartTime)) {
                return 0.0f;
            }
            if (!(this.mStopTime < 0)) {
                if (currentTime >= this.mStopTime) {
                    long j3 = currentTime - this.mStopTime;
                    long j4 = j3;
                    return (AutoScrollHelper.DEFAULT_RELATIVE_VELOCITY - this.mStopValue) + (this.mStopValue * AutoScrollHelper.constrain(((float) j3) / ((float) this.mEffectiveRampDown), 0.0f, (float) AutoScrollHelper.DEFAULT_RELATIVE_VELOCITY));
                }
            }
            long j5 = currentTime - this.mStartTime;
            long j6 = j5;
            return 0.5f * AutoScrollHelper.constrain(((float) j5) / ((float) this.mRampUpDuration), 0.0f, (float) AutoScrollHelper.DEFAULT_RELATIVE_VELOCITY);
        }

        private float interpolateValue(float f) {
            float value = f;
            float f2 = value;
            return (-4.0f * value * value) + (4.0f * value);
        }

        public void computeScrollDelta() {
            if (this.mDeltaTime == 0) {
                throw new RuntimeException("Cannot compute scroll delta before calling start()");
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            long currentTime = currentAnimationTimeMillis;
            float valueAt = getValueAt(currentAnimationTimeMillis);
            float f = valueAt;
            float interpolateValue = interpolateValue(valueAt);
            float f2 = interpolateValue;
            long j = currentAnimationTimeMillis - this.mDeltaTime;
            long j2 = j;
            this.mDeltaTime = currentTime;
            this.mDeltaX = (int) (((float) j) * interpolateValue * this.mTargetVelocityX);
            this.mDeltaY = (int) (((float) j) * interpolateValue * this.mTargetVelocityY);
        }

        public void setTargetVelocity(float f, float f2) {
            float x = f;
            float y = f2;
            float f3 = x;
            float f4 = y;
            this.mTargetVelocityX = x;
            this.mTargetVelocityY = y;
        }

        public int getHorizontalDirection() {
            return (int) (this.mTargetVelocityX / Math.abs(this.mTargetVelocityX));
        }

        public int getVerticalDirection() {
            return (int) (this.mTargetVelocityY / Math.abs(this.mTargetVelocityY));
        }

        public int getDeltaX() {
            return this.mDeltaX;
        }

        public int getDeltaY() {
            return this.mDeltaY;
        }
    }

    /* renamed from: android.support.v4.widget.AutoScrollHelper$ScrollAnimationRunnable */
    private class ScrollAnimationRunnable implements Runnable {
        final /* synthetic */ AutoScrollHelper this$0;

        ScrollAnimationRunnable(AutoScrollHelper autoScrollHelper) {
            this.this$0 = autoScrollHelper;
        }

        public void run() {
            if (this.this$0.mAnimating) {
                if (this.this$0.mNeedsReset) {
                    this.this$0.mNeedsReset = false;
                    this.this$0.mScroller.start();
                }
                ClampedScroller clampedScroller = this.this$0.mScroller;
                ClampedScroller scroller = clampedScroller;
                if (!clampedScroller.isFinished() && this.this$0.shouldAnimate()) {
                    if (this.this$0.mNeedsCancel) {
                        this.this$0.mNeedsCancel = false;
                        this.this$0.cancelTargetTouch();
                    }
                    scroller.computeScrollDelta();
                    int deltaX = scroller.getDeltaX();
                    int deltaY = scroller.getDeltaY();
                    int i = deltaY;
                    this.this$0.scrollTargetBy(deltaX, deltaY);
                    ViewCompat.postOnAnimation(this.this$0.mTarget, this);
                    return;
                }
                this.this$0.mAnimating = false;
            }
        }
    }

    public abstract boolean canTargetScrollHorizontally(int i);

    public abstract boolean canTargetScrollVertically(int i);

    public abstract void scrollTargetBy(int i, int i2);

    public AutoScrollHelper(View view) {
        View target = view;
        View view2 = target;
        float[] fArr = new float[2];
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        this.mRelativeEdges = fArr;
        float[] fArr2 = new float[2];
        fArr2[0] = Float.MAX_VALUE;
        fArr2[1] = Float.MAX_VALUE;
        this.mMaximumEdges = fArr2;
        float[] fArr3 = new float[2];
        fArr3[0] = 0.0f;
        fArr3[1] = 0.0f;
        this.mRelativeVelocity = fArr3;
        float[] fArr4 = new float[2];
        fArr4[0] = 0.0f;
        fArr4[1] = 0.0f;
        this.mMinimumVelocity = fArr4;
        float[] fArr5 = new float[2];
        fArr5[0] = Float.MAX_VALUE;
        fArr5[1] = Float.MAX_VALUE;
        this.mMaximumVelocity = fArr5;
        this.mTarget = target;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        DisplayMetrics displayMetrics2 = displayMetrics;
        int maxVelocity = (int) ((1575.0f * displayMetrics.density) + 0.5f);
        int i = (int) ((315.0f * displayMetrics.density) + 0.5f);
        int i2 = i;
        AutoScrollHelper maximumVelocity = setMaximumVelocity((float) maxVelocity, (float) maxVelocity);
        AutoScrollHelper minimumVelocity = setMinimumVelocity((float) i, (float) i);
        AutoScrollHelper edgeType = setEdgeType(1);
        AutoScrollHelper maximumEdges = setMaximumEdges(Float.MAX_VALUE, Float.MAX_VALUE);
        AutoScrollHelper relativeEdges = setRelativeEdges(DEFAULT_RELATIVE_EDGE, DEFAULT_RELATIVE_EDGE);
        AutoScrollHelper relativeVelocity = setRelativeVelocity(DEFAULT_RELATIVE_VELOCITY, DEFAULT_RELATIVE_VELOCITY);
        AutoScrollHelper activationDelay = setActivationDelay(DEFAULT_ACTIVATION_DELAY);
        AutoScrollHelper rampUpDuration = setRampUpDuration(500);
        AutoScrollHelper rampDownDuration = setRampDownDuration(500);
    }

    public AutoScrollHelper setEnabled(boolean z) {
        boolean enabled = z;
        if (this.mEnabled && !enabled) {
            requestStop();
        }
        this.mEnabled = enabled;
        return this;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public AutoScrollHelper setExclusive(boolean z) {
        this.mExclusive = z;
        return this;
    }

    public boolean isExclusive() {
        return this.mExclusive;
    }

    public AutoScrollHelper setMaximumVelocity(float f, float f2) {
        float horizontalMax = f;
        float verticalMax = f2;
        float f3 = horizontalMax;
        float f4 = verticalMax;
        this.mMaximumVelocity[0] = horizontalMax / 1000.0f;
        this.mMaximumVelocity[1] = verticalMax / 1000.0f;
        return this;
    }

    public AutoScrollHelper setMinimumVelocity(float f, float f2) {
        float horizontalMin = f;
        float verticalMin = f2;
        float f3 = horizontalMin;
        float f4 = verticalMin;
        this.mMinimumVelocity[0] = horizontalMin / 1000.0f;
        this.mMinimumVelocity[1] = verticalMin / 1000.0f;
        return this;
    }

    public AutoScrollHelper setRelativeVelocity(float f, float f2) {
        float horizontal = f;
        float vertical = f2;
        float f3 = horizontal;
        float f4 = vertical;
        this.mRelativeVelocity[0] = horizontal / 1000.0f;
        this.mRelativeVelocity[1] = vertical / 1000.0f;
        return this;
    }

    public AutoScrollHelper setEdgeType(int i) {
        int type = i;
        int i2 = type;
        this.mEdgeType = type;
        return this;
    }

    public AutoScrollHelper setRelativeEdges(float f, float f2) {
        float horizontal = f;
        float vertical = f2;
        float f3 = horizontal;
        float f4 = vertical;
        this.mRelativeEdges[0] = horizontal;
        this.mRelativeEdges[1] = vertical;
        return this;
    }

    public AutoScrollHelper setMaximumEdges(float f, float f2) {
        float horizontalMax = f;
        float verticalMax = f2;
        float f3 = horizontalMax;
        float f4 = verticalMax;
        this.mMaximumEdges[0] = horizontalMax;
        this.mMaximumEdges[1] = verticalMax;
        return this;
    }

    public AutoScrollHelper setActivationDelay(int i) {
        int delayMillis = i;
        int i2 = delayMillis;
        this.mActivationDelay = delayMillis;
        return this;
    }

    public AutoScrollHelper setRampUpDuration(int i) {
        int durationMillis = i;
        int i2 = durationMillis;
        this.mScroller.setRampUpDuration(durationMillis);
        return this;
    }

    public AutoScrollHelper setRampDownDuration(int i) {
        int durationMillis = i;
        int i2 = durationMillis;
        this.mScroller.setRampDownDuration(durationMillis);
        return this;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z;
        View v = view;
        MotionEvent event = motionEvent;
        View view2 = v;
        MotionEvent motionEvent2 = event;
        if (!this.mEnabled) {
            return false;
        }
        int actionMasked = MotionEventCompat.getActionMasked(event);
        int i = actionMasked;
        switch (actionMasked) {
            case 0:
                this.mNeedsCancel = true;
                this.mAlreadyDelayed = false;
                break;
            case 1:
            case 3:
                requestStop();
                break;
            case 2:
                break;
        }
        float xTargetVelocity = computeTargetVelocity(0, event.getX(), (float) v.getWidth(), (float) this.mTarget.getWidth());
        float computeTargetVelocity = computeTargetVelocity(1, event.getY(), (float) v.getHeight(), (float) this.mTarget.getHeight());
        float f = computeTargetVelocity;
        this.mScroller.setTargetVelocity(xTargetVelocity, computeTargetVelocity);
        if (!this.mAnimating && shouldAnimate()) {
            startAnimating();
        }
        if (this.mExclusive && this.mAnimating) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldAnimate() {
        ClampedScroller clampedScroller = this.mScroller;
        ClampedScroller scroller = clampedScroller;
        int verticalDirection = clampedScroller.getVerticalDirection();
        int horizontalDirection = scroller.getHorizontalDirection();
        return (verticalDirection != 0 && canTargetScrollVertically(verticalDirection)) || (horizontalDirection != 0 && canTargetScrollHorizontally(horizontalDirection));
    }

    private void startAnimating() {
        if (this.mRunnable == null) {
            this.mRunnable = new ScrollAnimationRunnable(this);
        }
        this.mAnimating = true;
        this.mNeedsReset = true;
        if (!this.mAlreadyDelayed && this.mActivationDelay > 0) {
            View view = this.mTarget;
            ViewCompat.postOnAnimationDelayed(view, this.mRunnable, (long) this.mActivationDelay);
        } else {
            this.mRunnable.run();
        }
        this.mAlreadyDelayed = true;
    }

    private void requestStop() {
        if (!this.mNeedsReset) {
            this.mScroller.requestStop();
        } else {
            this.mAnimating = false;
        }
    }

    private float computeTargetVelocity(int i, float f, float f2, float f3) {
        int direction = i;
        float coordinate = f;
        float srcSize = f2;
        float dstSize = f3;
        int i2 = direction;
        float f4 = coordinate;
        float f5 = srcSize;
        float f6 = dstSize;
        float relativeEdge = this.mRelativeEdges[direction];
        float f7 = this.mMaximumEdges[direction];
        float f8 = f7;
        float edgeValue = getEdgeValue(relativeEdge, srcSize, f7, coordinate);
        float value = edgeValue;
        if (edgeValue == 0.0f) {
            return 0.0f;
        }
        float relativeVelocity = this.mRelativeVelocity[direction];
        float minimumVelocity = this.mMinimumVelocity[direction];
        float maximumVelocity = this.mMaximumVelocity[direction];
        float targetVelocity = relativeVelocity * dstSize;
        if (value > 0.0f) {
            return constrain(value * targetVelocity, minimumVelocity, maximumVelocity);
        }
        return -constrain((-value) * targetVelocity, minimumVelocity, maximumVelocity);
    }

    private float getEdgeValue(float f, float f2, float f3, float f4) {
        float interpolated;
        float relativeValue = f;
        float size = f2;
        float maxValue = f3;
        float current = f4;
        float f5 = relativeValue;
        float f6 = size;
        float f7 = maxValue;
        float f8 = current;
        float constrain = constrain(relativeValue * size, 0.0f, maxValue);
        float edgeSize = constrain;
        float valueLeading = constrainEdgeValue(current, constrain);
        float constrainEdgeValue = constrainEdgeValue(size - current, edgeSize);
        float f9 = constrainEdgeValue;
        float f10 = constrainEdgeValue - valueLeading;
        float value = f10;
        if (f10 < 0.0f) {
            interpolated = -this.mEdgeInterpolator.getInterpolation(-value);
        } else if (value <= 0.0f) {
            return 0.0f;
        } else {
            interpolated = this.mEdgeInterpolator.getInterpolation(value);
        }
        return constrain(interpolated, -1.0f, (float) DEFAULT_RELATIVE_VELOCITY);
    }

    private float constrainEdgeValue(float f, float f2) {
        float current = f;
        float leading = f2;
        float f3 = current;
        float f4 = leading;
        if (leading == 0.0f) {
            return 0.0f;
        }
        switch (this.mEdgeType) {
            case 0:
            case 1:
                if (current < leading) {
                    if (current >= 0.0f) {
                        return DEFAULT_RELATIVE_VELOCITY - (current / leading);
                    }
                    if (this.mAnimating && this.mEdgeType == 1) {
                        return DEFAULT_RELATIVE_VELOCITY;
                    }
                }
                break;
            case 2:
                if (current < 0.0f) {
                    return current / (-leading);
                }
                break;
        }
        return 0.0f;
    }

    static int constrain(int i, int i2, int i3) {
        int value = i;
        int min = i2;
        int max = i3;
        int i4 = value;
        int i5 = min;
        int i6 = max;
        if (value > max) {
            return max;
        }
        if (value >= min) {
            return value;
        }
        return min;
    }

    static float constrain(float f, float f2, float f3) {
        float value = f;
        float min = f2;
        float max = f3;
        float f4 = value;
        float f5 = min;
        float f6 = max;
        if (value > max) {
            return max;
        }
        if (value < min) {
            return min;
        }
        return value;
    }

    /* access modifiers changed from: 0000 */
    public void cancelTargetTouch() {
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent cancel = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
        boolean onTouchEvent = this.mTarget.onTouchEvent(cancel);
        cancel.recycle();
    }
}
