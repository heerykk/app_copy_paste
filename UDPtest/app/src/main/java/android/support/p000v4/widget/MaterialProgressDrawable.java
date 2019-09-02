package android.support.p000v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.NonNull;
import android.support.p000v4.view.animation.FastOutSlowInInterpolator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/* renamed from: android.support.v4.widget.MaterialProgressDrawable */
class MaterialProgressDrawable extends Drawable implements Animatable {
    private static final int ANIMATION_DURATION = 1332;
    private static final int ARROW_HEIGHT = 5;
    private static final int ARROW_HEIGHT_LARGE = 6;
    private static final float ARROW_OFFSET_ANGLE = 5.0f;
    private static final int ARROW_WIDTH = 10;
    private static final int ARROW_WIDTH_LARGE = 12;
    private static final float CENTER_RADIUS = 8.75f;
    private static final float CENTER_RADIUS_LARGE = 12.5f;
    private static final int CIRCLE_DIAMETER = 40;
    private static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final int[] COLORS;
    private static final float COLOR_START_DELAY_OFFSET = 0.75f;
    static final int DEFAULT = 1;
    private static final float END_TRIM_START_DELAY_OFFSET = 0.5f;
    private static final float FULL_ROTATION = 1080.0f;
    static final int LARGE = 0;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final float MAX_PROGRESS_ARC = 0.8f;
    private static final float NUM_POINTS = 5.0f;
    private static final float START_TRIM_DURATION_OFFSET = 0.5f;
    private static final float STROKE_WIDTH = 2.5f;
    private static final float STROKE_WIDTH_LARGE = 3.0f;
    private Animation mAnimation;
    private final ArrayList<Animation> mAnimators = new ArrayList<>();
    private final Callback mCallback = new Callback(this) {
        final /* synthetic */ MaterialProgressDrawable this$0;

        {
            MaterialProgressDrawable this$02 = r5;
            MaterialProgressDrawable materialProgressDrawable = this$02;
            this.this$0 = this$02;
        }

        public void invalidateDrawable(Drawable drawable) {
            Drawable drawable2 = drawable;
            this.this$0.invalidateSelf();
        }

        public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
            Runnable what = runnable;
            long when = j;
            Drawable drawable2 = drawable;
            Runnable runnable2 = what;
            long j2 = when;
            this.this$0.scheduleSelf(what, when);
        }

        public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            Runnable what = runnable;
            Drawable drawable2 = drawable;
            Runnable runnable2 = what;
            this.this$0.unscheduleSelf(what);
        }
    };
    boolean mFinishing;
    private double mHeight;
    private View mParent;
    private Resources mResources;
    private final Ring mRing;
    private float mRotation;
    float mRotationCount;
    private double mWidth;

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.widget.MaterialProgressDrawable$ProgressDrawableSize */
    public @interface ProgressDrawableSize {
    }

    /* renamed from: android.support.v4.widget.MaterialProgressDrawable$Ring */
    private static class Ring {
        private int mAlpha;
        private Path mArrow;
        private int mArrowHeight;
        private final Paint mArrowPaint = new Paint();
        private float mArrowScale;
        private int mArrowWidth;
        private int mBackgroundColor;
        private final Callback mCallback;
        private final Paint mCirclePaint = new Paint(1);
        private int mColorIndex;
        private int[] mColors;
        private int mCurrentColor;
        private float mEndTrim = 0.0f;
        private final Paint mPaint = new Paint();
        private double mRingCenterRadius;
        private float mRotation = 0.0f;
        private boolean mShowArrow;
        private float mStartTrim = 0.0f;
        private float mStartingEndTrim;
        private float mStartingRotation;
        private float mStartingStartTrim;
        private float mStrokeInset = MaterialProgressDrawable.STROKE_WIDTH;
        private float mStrokeWidth = 5.0f;
        private final RectF mTempBounds = new RectF();

        Ring(Callback callback) {
            Callback callback2 = callback;
            Callback callback3 = callback2;
            this.mCallback = callback2;
            this.mPaint.setStrokeCap(Cap.SQUARE);
            this.mPaint.setAntiAlias(true);
            this.mPaint.setStyle(Style.STROKE);
            this.mArrowPaint.setStyle(Style.FILL);
            this.mArrowPaint.setAntiAlias(true);
        }

        public void setBackgroundColor(int i) {
            int color = i;
            int i2 = color;
            this.mBackgroundColor = color;
        }

        public void setArrowDimensions(float f, float f2) {
            float width = f;
            float height = f2;
            float f3 = width;
            float f4 = height;
            this.mArrowWidth = (int) width;
            this.mArrowHeight = (int) height;
        }

        public void draw(Canvas canvas, Rect rect) {
            Canvas c = canvas;
            Rect bounds = rect;
            Canvas canvas2 = c;
            Rect rect2 = bounds;
            RectF rectF = this.mTempBounds;
            RectF arcBounds = rectF;
            rectF.set(bounds);
            arcBounds.inset(this.mStrokeInset, this.mStrokeInset);
            float startAngle = (this.mStartTrim + this.mRotation) * 360.0f;
            float f = (this.mEndTrim + this.mRotation) * 360.0f;
            float f2 = f;
            float f3 = f - startAngle;
            float f4 = f3;
            this.mPaint.setColor(this.mCurrentColor);
            c.drawArc(arcBounds, startAngle, f3, false, this.mPaint);
            drawTriangle(c, startAngle, f3, bounds);
            if (this.mAlpha < 255) {
                this.mCirclePaint.setColor(this.mBackgroundColor);
                this.mCirclePaint.setAlpha(255 - this.mAlpha);
                c.drawCircle(bounds.exactCenterX(), bounds.exactCenterY(), (float) (bounds.width() / 2), this.mCirclePaint);
            }
        }

        private void drawTriangle(Canvas canvas, float f, float f2, Rect rect) {
            Canvas c = canvas;
            float startAngle = f;
            float sweepAngle = f2;
            Rect bounds = rect;
            Canvas canvas2 = c;
            float f3 = startAngle;
            float f4 = sweepAngle;
            Rect rect2 = bounds;
            if (this.mShowArrow) {
                if (this.mArrow != null) {
                    this.mArrow.reset();
                } else {
                    this.mArrow = new Path();
                    this.mArrow.setFillType(FillType.EVEN_ODD);
                }
                float inset = ((float) (((int) this.mStrokeInset) / 2)) * this.mArrowScale;
                float x = (float) ((this.mRingCenterRadius * Math.cos(0.0d)) + ((double) bounds.exactCenterX()));
                float sin = (float) ((this.mRingCenterRadius * Math.sin(0.0d)) + ((double) bounds.exactCenterY()));
                float f5 = sin;
                this.mArrow.moveTo(0.0f, 0.0f);
                this.mArrow.lineTo(((float) this.mArrowWidth) * this.mArrowScale, 0.0f);
                this.mArrow.lineTo((((float) this.mArrowWidth) * this.mArrowScale) / 2.0f, ((float) this.mArrowHeight) * this.mArrowScale);
                this.mArrow.offset(x - inset, sin);
                this.mArrow.close();
                this.mArrowPaint.setColor(this.mCurrentColor);
                c.rotate((startAngle + sweepAngle) - 5.0f, bounds.exactCenterX(), bounds.exactCenterY());
                c.drawPath(this.mArrow, this.mArrowPaint);
            }
        }

        public void setColors(@NonNull int[] iArr) {
            int[] colors = iArr;
            int[] iArr2 = colors;
            this.mColors = colors;
            setColorIndex(0);
        }

        public void setColor(int i) {
            int color = i;
            int i2 = color;
            this.mCurrentColor = color;
        }

        public void setColorIndex(int i) {
            int index = i;
            int i2 = index;
            this.mColorIndex = index;
            this.mCurrentColor = this.mColors[this.mColorIndex];
        }

        public int getNextColor() {
            return this.mColors[getNextColorIndex()];
        }

        private int getNextColorIndex() {
            return (this.mColorIndex + 1) % this.mColors.length;
        }

        public void goToNextColor() {
            setColorIndex(getNextColorIndex());
        }

        public void setColorFilter(ColorFilter colorFilter) {
            ColorFilter filter = colorFilter;
            ColorFilter colorFilter2 = filter;
            ColorFilter colorFilter3 = this.mPaint.setColorFilter(filter);
            invalidateSelf();
        }

        public void setAlpha(int i) {
            int alpha = i;
            int i2 = alpha;
            this.mAlpha = alpha;
        }

        public int getAlpha() {
            return this.mAlpha;
        }

        public void setStrokeWidth(float f) {
            float strokeWidth = f;
            float f2 = strokeWidth;
            this.mStrokeWidth = strokeWidth;
            this.mPaint.setStrokeWidth(strokeWidth);
            invalidateSelf();
        }

        public float getStrokeWidth() {
            return this.mStrokeWidth;
        }

        public void setStartTrim(float f) {
            float startTrim = f;
            float f2 = startTrim;
            this.mStartTrim = startTrim;
            invalidateSelf();
        }

        public float getStartTrim() {
            return this.mStartTrim;
        }

        public float getStartingStartTrim() {
            return this.mStartingStartTrim;
        }

        public float getStartingEndTrim() {
            return this.mStartingEndTrim;
        }

        public int getStartingColor() {
            return this.mColors[this.mColorIndex];
        }

        public void setEndTrim(float f) {
            float endTrim = f;
            float f2 = endTrim;
            this.mEndTrim = endTrim;
            invalidateSelf();
        }

        public float getEndTrim() {
            return this.mEndTrim;
        }

        public void setRotation(float f) {
            float rotation = f;
            float f2 = rotation;
            this.mRotation = rotation;
            invalidateSelf();
        }

        public float getRotation() {
            return this.mRotation;
        }

        public void setInsets(int i, int i2) {
            float insets;
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            float min = (float) Math.min(width, height);
            float minEdge = min;
            if ((this.mRingCenterRadius <= 0.0d) || min < 0.0f) {
                insets = (float) Math.ceil((double) (this.mStrokeWidth / 2.0f));
            } else {
                insets = (float) (((double) (minEdge / 2.0f)) - this.mRingCenterRadius);
            }
            this.mStrokeInset = insets;
        }

        public float getInsets() {
            return this.mStrokeInset;
        }

        public void setCenterRadius(double d) {
            double centerRadius = d;
            double d2 = centerRadius;
            this.mRingCenterRadius = centerRadius;
        }

        public double getCenterRadius() {
            return this.mRingCenterRadius;
        }

        public void setShowArrow(boolean z) {
            boolean show = z;
            if (this.mShowArrow != show) {
                this.mShowArrow = show;
                invalidateSelf();
            }
        }

        public void setArrowScale(float f) {
            float scale = f;
            float f2 = scale;
            if (scale != this.mArrowScale) {
                this.mArrowScale = scale;
                invalidateSelf();
            }
        }

        public float getStartingRotation() {
            return this.mStartingRotation;
        }

        public void storeOriginals() {
            this.mStartingStartTrim = this.mStartTrim;
            this.mStartingEndTrim = this.mEndTrim;
            this.mStartingRotation = this.mRotation;
        }

        public void resetOriginals() {
            this.mStartingStartTrim = 0.0f;
            this.mStartingEndTrim = 0.0f;
            this.mStartingRotation = 0.0f;
            setStartTrim(0.0f);
            setEndTrim(0.0f);
            setRotation(0.0f);
        }

        private void invalidateSelf() {
            this.mCallback.invalidateDrawable(null);
        }
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = -16777216;
        COLORS = iArr;
    }

    MaterialProgressDrawable(Context context, View view) {
        Context context2 = context;
        View parent = view;
        Context context3 = context2;
        View view2 = parent;
        this.mParent = parent;
        this.mResources = context2.getResources();
        this.mRing = new Ring(this.mCallback);
        this.mRing.setColors(COLORS);
        updateSizes(1);
        setupAnimators();
    }

    private void setSizeParameters(double d, double d2, double d3, double d4, float f, float f2) {
        double progressCircleWidth = d;
        double progressCircleHeight = d2;
        double centerRadius = d3;
        double strokeWidth = d4;
        float arrowWidth = f;
        float arrowHeight = f2;
        double d5 = progressCircleWidth;
        double d6 = progressCircleHeight;
        double d7 = centerRadius;
        double d8 = strokeWidth;
        float f3 = arrowWidth;
        float f4 = arrowHeight;
        Ring ring = this.mRing;
        DisplayMetrics displayMetrics = this.mResources.getDisplayMetrics();
        DisplayMetrics displayMetrics2 = displayMetrics;
        float f5 = displayMetrics.density;
        float f6 = f5;
        this.mWidth = progressCircleWidth * ((double) f5);
        this.mHeight = progressCircleHeight * ((double) f5);
        ring.setStrokeWidth(((float) strokeWidth) * f5);
        ring.setCenterRadius(centerRadius * ((double) f5));
        ring.setColorIndex(0);
        ring.setArrowDimensions(arrowWidth * f5, arrowHeight * f5);
        ring.setInsets((int) this.mWidth, (int) this.mHeight);
    }

    public void updateSizes(int i) {
        int size = i;
        int i2 = size;
        if (size != 0) {
            setSizeParameters(40.0d, 40.0d, 8.75d, 2.5d, 10.0f, 5.0f);
        } else {
            setSizeParameters(56.0d, 56.0d, 12.5d, 3.0d, 12.0f, 6.0f);
        }
    }

    public void showArrow(boolean z) {
        this.mRing.setShowArrow(z);
    }

    public void setArrowScale(float f) {
        float scale = f;
        float f2 = scale;
        this.mRing.setArrowScale(scale);
    }

    public void setStartEndTrim(float f, float f2) {
        float startAngle = f;
        float endAngle = f2;
        float f3 = startAngle;
        float f4 = endAngle;
        this.mRing.setStartTrim(startAngle);
        this.mRing.setEndTrim(endAngle);
    }

    public void setProgressRotation(float f) {
        float rotation = f;
        float f2 = rotation;
        this.mRing.setRotation(rotation);
    }

    public void setBackgroundColor(int i) {
        int color = i;
        int i2 = color;
        this.mRing.setBackgroundColor(color);
    }

    public void setColorSchemeColors(int... iArr) {
        int[] colors = iArr;
        int[] iArr2 = colors;
        this.mRing.setColors(colors);
        this.mRing.setColorIndex(0);
    }

    public int getIntrinsicHeight() {
        return (int) this.mHeight;
    }

    public int getIntrinsicWidth() {
        return (int) this.mWidth;
    }

    public void draw(Canvas canvas) {
        Canvas c = canvas;
        Canvas canvas2 = c;
        Rect bounds = getBounds();
        int save = c.save();
        int i = save;
        c.rotate(this.mRotation, bounds.exactCenterX(), bounds.exactCenterY());
        this.mRing.draw(c, bounds);
        c.restoreToCount(save);
    }

    public void setAlpha(int i) {
        int alpha = i;
        int i2 = alpha;
        this.mRing.setAlpha(alpha);
    }

    public int getAlpha() {
        return this.mRing.getAlpha();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        ColorFilter colorFilter2 = colorFilter;
        ColorFilter colorFilter3 = colorFilter2;
        this.mRing.setColorFilter(colorFilter2);
    }

    /* access modifiers changed from: 0000 */
    public void setRotation(float f) {
        float rotation = f;
        float f2 = rotation;
        this.mRotation = rotation;
        invalidateSelf();
    }

    private float getRotation() {
        return this.mRotation;
    }

    public int getOpacity() {
        return -3;
    }

    public boolean isRunning() {
        ArrayList<Animation> arrayList = this.mAnimators;
        ArrayList<Animation> animators = arrayList;
        int N = arrayList.size();
        for (int i = 0; i < N; i++) {
            Animation animation = (Animation) animators.get(i);
            Animation animator = animation;
            if (animation.hasStarted() && !animator.hasEnded()) {
                return true;
            }
        }
        return false;
    }

    public void start() {
        this.mAnimation.reset();
        this.mRing.storeOriginals();
        if (this.mRing.getEndTrim() != this.mRing.getStartTrim()) {
            this.mFinishing = true;
            this.mAnimation.setDuration(666);
            this.mParent.startAnimation(this.mAnimation);
            return;
        }
        this.mRing.setColorIndex(0);
        this.mRing.resetOriginals();
        this.mAnimation.setDuration(1332);
        this.mParent.startAnimation(this.mAnimation);
    }

    public void stop() {
        this.mParent.clearAnimation();
        setRotation(0.0f);
        this.mRing.setShowArrow(false);
        this.mRing.setColorIndex(0);
        this.mRing.resetOriginals();
    }

    /* access modifiers changed from: 0000 */
    public float getMinProgressArc(Ring ring) {
        Ring ring2 = ring;
        Ring ring3 = ring2;
        return (float) Math.toRadians(((double) ring2.getStrokeWidth()) / (6.283185307179586d * ring2.getCenterRadius()));
    }

    private int evaluateColorChange(float f, int i, int i2) {
        float fraction = f;
        int startValue = i;
        int endValue = i2;
        float f2 = fraction;
        int i3 = startValue;
        int i4 = endValue;
        int intValue = Integer.valueOf(startValue).intValue();
        int startInt = intValue;
        int startA = (intValue >> 24) & 255;
        int startR = (startInt >> 16) & 255;
        int startG = (startInt >> 8) & 255;
        int startB = startInt & 255;
        int intValue2 = Integer.valueOf(endValue).intValue();
        int endInt = intValue2;
        return ((startA + ((int) (fraction * ((float) (((intValue2 >> 24) & 255) - startA))))) << 24) | ((startR + ((int) (fraction * ((float) (((endInt >> 16) & 255) - startR))))) << 16) | ((startG + ((int) (fraction * ((float) (((endInt >> 8) & 255) - startG))))) << 8) | (startB + ((int) (fraction * ((float) ((endInt & 255) - startB)))));
    }

    /* access modifiers changed from: 0000 */
    public void updateRingColor(float f, Ring ring) {
        float interpolatedTime = f;
        Ring ring2 = ring;
        float f2 = interpolatedTime;
        Ring ring3 = ring2;
        if (interpolatedTime > COLOR_START_DELAY_OFFSET) {
            ring2.setColor(evaluateColorChange((interpolatedTime - COLOR_START_DELAY_OFFSET) / 0.25f, ring2.getStartingColor(), ring2.getNextColor()));
        }
    }

    /* access modifiers changed from: 0000 */
    public void applyFinishTranslation(float f, Ring ring) {
        float interpolatedTime = f;
        Ring ring2 = ring;
        float f2 = interpolatedTime;
        Ring ring3 = ring2;
        updateRingColor(interpolatedTime, ring2);
        float targetRotation = (float) (Math.floor((double) (ring2.getStartingRotation() / MAX_PROGRESS_ARC)) + 1.0d);
        float startingStartTrim = ring2.getStartingStartTrim() + (((ring2.getStartingEndTrim() - getMinProgressArc(ring2)) - ring2.getStartingStartTrim()) * interpolatedTime);
        float f3 = startingStartTrim;
        ring2.setStartTrim(startingStartTrim);
        ring2.setEndTrim(ring2.getStartingEndTrim());
        float startingRotation = ring2.getStartingRotation() + ((targetRotation - ring2.getStartingRotation()) * interpolatedTime);
        float f4 = startingRotation;
        ring2.setRotation(startingRotation);
    }

    private void setupAnimators() {
        Ring ring = this.mRing;
        final Ring ring2 = ring;
        C02221 r2 = new Animation(this) {
            final /* synthetic */ MaterialProgressDrawable this$0;

            {
                MaterialProgressDrawable this$02 = r6;
                Ring ring = r7;
                MaterialProgressDrawable materialProgressDrawable = this$02;
                this.this$0 = this$02;
            }

            public void applyTransformation(float f, Transformation transformation) {
                float interpolatedTime = f;
                float f2 = interpolatedTime;
                Transformation transformation2 = transformation;
                if (!this.this$0.mFinishing) {
                    float minProgressArc = this.this$0.getMinProgressArc(ring2);
                    float startingEndTrim = ring2.getStartingEndTrim();
                    float startingTrim = ring2.getStartingStartTrim();
                    float startingRotation = ring2.getStartingRotation();
                    this.this$0.updateRingColor(interpolatedTime, ring2);
                    if (interpolatedTime <= 0.5f) {
                        float interpolation = startingTrim + ((MaterialProgressDrawable.MAX_PROGRESS_ARC - minProgressArc) * MaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation(interpolatedTime / 0.5f));
                        float f3 = interpolation;
                        ring2.setStartTrim(interpolation);
                    }
                    if (interpolatedTime > 0.5f) {
                        float interpolation2 = startingEndTrim + ((MaterialProgressDrawable.MAX_PROGRESS_ARC - minProgressArc) * MaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation((interpolatedTime - 0.5f) / 0.5f));
                        float f4 = interpolation2;
                        ring2.setEndTrim(interpolation2);
                    }
                    float f5 = startingRotation + (0.25f * interpolatedTime);
                    float f6 = f5;
                    ring2.setRotation(f5);
                    float f7 = (216.0f * interpolatedTime) + (MaterialProgressDrawable.FULL_ROTATION * (this.this$0.mRotationCount / 5.0f));
                    float f8 = f7;
                    this.this$0.setRotation(f7);
                    return;
                }
                this.this$0.applyFinishTranslation(interpolatedTime, ring2);
            }
        };
        C02221 r6 = r2;
        r2.setRepeatCount(-1);
        r6.setRepeatMode(1);
        r6.setInterpolator(LINEAR_INTERPOLATOR);
        final Ring ring3 = ring;
        r6.setAnimationListener(new AnimationListener(this) {
            final /* synthetic */ MaterialProgressDrawable this$0;

            {
                MaterialProgressDrawable this$02 = r6;
                Ring ring = r7;
                MaterialProgressDrawable materialProgressDrawable = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationStart(Animation animation) {
                Animation animation2 = animation;
                this.this$0.mRotationCount = 0.0f;
            }

            public void onAnimationEnd(Animation animation) {
                Animation animation2 = animation;
            }

            public void onAnimationRepeat(Animation animation) {
                Animation animation2 = animation;
                Animation animation3 = animation2;
                ring3.storeOriginals();
                ring3.goToNextColor();
                ring3.setStartTrim(ring3.getEndTrim());
                if (!this.this$0.mFinishing) {
                    this.this$0.mRotationCount = (this.this$0.mRotationCount + 1.0f) % 5.0f;
                    return;
                }
                this.this$0.mFinishing = false;
                animation2.setDuration(1332);
                ring3.setShowArrow(false);
            }
        });
        this.mAnimation = r6;
    }
}
