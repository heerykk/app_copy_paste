package android.support.p000v4.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* renamed from: android.support.v4.widget.SwipeProgressBar */
final class SwipeProgressBar {
    private static final int ANIMATION_DURATION_MS = 2000;
    private static final int COLOR1 = -1291845632;
    private static final int COLOR2 = Integer.MIN_VALUE;
    private static final int COLOR3 = 1291845632;
    private static final int COLOR4 = 436207616;
    private static final int FINISH_ANIMATION_DURATION_MS = 1000;
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private Rect mBounds = new Rect();
    private final RectF mClipRect = new RectF();
    private int mColor1;
    private int mColor2;
    private int mColor3;
    private int mColor4;
    private long mFinishTime;
    private final Paint mPaint = new Paint();
    private View mParent;
    private boolean mRunning;
    private long mStartTime;
    private float mTriggerPercentage;

    SwipeProgressBar(View view) {
        View parent = view;
        View view2 = parent;
        this.mParent = parent;
        this.mColor1 = COLOR1;
        this.mColor2 = Integer.MIN_VALUE;
        this.mColor3 = COLOR3;
        this.mColor4 = COLOR4;
    }

    /* access modifiers changed from: 0000 */
    public void setColorScheme(int i, int i2, int i3, int i4) {
        int color1 = i;
        int color2 = i2;
        int color3 = i3;
        int color4 = i4;
        int i5 = color1;
        int i6 = color2;
        int i7 = color3;
        int i8 = color4;
        this.mColor1 = color1;
        this.mColor2 = color2;
        this.mColor3 = color3;
        this.mColor4 = color4;
    }

    /* access modifiers changed from: 0000 */
    public void setTriggerPercentage(float f) {
        float triggerPercentage = f;
        float f2 = triggerPercentage;
        this.mTriggerPercentage = triggerPercentage;
        this.mStartTime = 0;
        ViewCompat.postInvalidateOnAnimation(this.mParent, this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.bottom);
    }

    /* access modifiers changed from: 0000 */
    public void start() {
        if (!this.mRunning) {
            this.mTriggerPercentage = 0.0f;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mRunning = true;
            this.mParent.postInvalidate();
        }
    }

    /* access modifiers changed from: 0000 */
    public void stop() {
        if (this.mRunning) {
            this.mTriggerPercentage = 0.0f;
            this.mFinishTime = AnimationUtils.currentAnimationTimeMillis();
            this.mRunning = false;
            this.mParent.postInvalidate();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isRunning() {
        boolean z;
        if (!this.mRunning) {
            if (this.mFinishTime <= 0) {
                z = false;
                return z;
            }
        }
        z = true;
        return z;
    }

    /* access modifiers changed from: 0000 */
    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        int width = this.mBounds.width();
        int height = this.mBounds.height();
        int cx = width / 2;
        int cy = height / 2;
        boolean drawTriggerWhileFinishing = false;
        int restoreCount = canvas2.save();
        boolean clipRect = canvas2.clipRect(this.mBounds);
        if (!this.mRunning) {
            if (this.mFinishTime <= 0) {
                if (this.mTriggerPercentage > 0.0f && ((double) this.mTriggerPercentage) <= 1.0d) {
                    drawTrigger(canvas2, cx, cy);
                }
                canvas2.restoreToCount(restoreCount);
            }
        }
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        long now = currentAnimationTimeMillis;
        long iterations = (now - this.mStartTime) / 2000;
        float rawProgress = ((float) ((currentAnimationTimeMillis - this.mStartTime) % 2000)) / 20.0f;
        if (!this.mRunning) {
            if (!(now - this.mFinishTime < 1000)) {
                this.mFinishTime = 0;
                return;
            }
            long j = (now - this.mFinishTime) % 1000;
            long j2 = j;
            float f = ((float) j) / 10.0f;
            float f2 = f;
            float interpolation = ((float) (width / 2)) * INTERPOLATOR.getInterpolation(f / 100.0f);
            float f3 = interpolation;
            this.mClipRect.set(((float) cx) - interpolation, 0.0f, ((float) cx) + interpolation, (float) height);
            int saveLayerAlpha = canvas2.saveLayerAlpha(this.mClipRect, 0, 0);
            drawTriggerWhileFinishing = true;
        }
        if (iterations == 0) {
            canvas2.drawColor(this.mColor1);
        } else if (rawProgress >= 0.0f && rawProgress < 25.0f) {
            canvas2.drawColor(this.mColor4);
        } else if (rawProgress >= 25.0f && rawProgress < 50.0f) {
            canvas2.drawColor(this.mColor1);
        } else if (rawProgress < 50.0f || rawProgress >= 75.0f) {
            canvas2.drawColor(this.mColor3);
        } else {
            canvas2.drawColor(this.mColor2);
        }
        if (rawProgress >= 0.0f && rawProgress <= 25.0f) {
            float f4 = ((rawProgress + 25.0f) * 2.0f) / 100.0f;
            float f5 = f4;
            drawCircle(canvas2, (float) cx, (float) cy, this.mColor1, f4);
        }
        if (rawProgress >= 0.0f && rawProgress <= 50.0f) {
            float f6 = (rawProgress * 2.0f) / 100.0f;
            float f7 = f6;
            drawCircle(canvas2, (float) cx, (float) cy, this.mColor2, f6);
        }
        if (rawProgress >= 25.0f && rawProgress <= 75.0f) {
            float f8 = ((rawProgress - 25.0f) * 2.0f) / 100.0f;
            float f9 = f8;
            drawCircle(canvas2, (float) cx, (float) cy, this.mColor3, f8);
        }
        if (rawProgress >= 50.0f && rawProgress <= 100.0f) {
            float f10 = ((rawProgress - 50.0f) * 2.0f) / 100.0f;
            float f11 = f10;
            drawCircle(canvas2, (float) cx, (float) cy, this.mColor4, f10);
        }
        if (rawProgress >= 75.0f && rawProgress <= 100.0f) {
            float f12 = ((rawProgress - 75.0f) * 2.0f) / 100.0f;
            float f13 = f12;
            drawCircle(canvas2, (float) cx, (float) cy, this.mColor1, f12);
        }
        if (this.mTriggerPercentage > 0.0f && drawTriggerWhileFinishing) {
            canvas2.restoreToCount(restoreCount);
            restoreCount = canvas2.save();
            boolean clipRect2 = canvas2.clipRect(this.mBounds);
            drawTrigger(canvas2, cx, cy);
        }
        ViewCompat.postInvalidateOnAnimation(this.mParent, this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.bottom);
        canvas2.restoreToCount(restoreCount);
    }

    private void drawTrigger(Canvas canvas, int i, int i2) {
        Canvas canvas2 = canvas;
        int cx = i;
        int cy = i2;
        Canvas canvas3 = canvas2;
        int i3 = cx;
        int i4 = cy;
        this.mPaint.setColor(this.mColor1);
        canvas2.drawCircle((float) cx, (float) cy, ((float) cx) * this.mTriggerPercentage, this.mPaint);
    }

    private void drawCircle(Canvas canvas, float f, float f2, int i, float f3) {
        Canvas canvas2 = canvas;
        float cx = f;
        float cy = f2;
        int color = i;
        float pct = f3;
        Canvas canvas3 = canvas2;
        float f4 = cx;
        float f5 = cy;
        int i2 = color;
        float f6 = pct;
        this.mPaint.setColor(color);
        int save = canvas2.save();
        canvas2.translate(cx, cy);
        float interpolation = INTERPOLATOR.getInterpolation(pct);
        float f7 = interpolation;
        canvas2.scale(interpolation, interpolation);
        canvas2.drawCircle(0.0f, 0.0f, cx, this.mPaint);
        canvas2.restore();
    }

    /* access modifiers changed from: 0000 */
    public void setBounds(int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        this.mBounds.left = left;
        this.mBounds.top = top;
        this.mBounds.right = right;
        this.mBounds.bottom = bottom;
    }
}
