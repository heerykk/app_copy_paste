package android.support.design.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.design.C0001R;
import android.support.p000v4.content.ContextCompat;
import android.support.p003v7.graphics.drawable.DrawableWrapper;

class ShadowDrawableWrapper extends DrawableWrapper {
    static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    static final float SHADOW_BOTTOM_SCALE = 1.0f;
    static final float SHADOW_HORIZ_SCALE = 0.5f;
    static final float SHADOW_MULTIPLIER = 1.5f;
    static final float SHADOW_TOP_SCALE = 0.25f;
    private boolean mAddPaddingForCorners = true;
    final RectF mContentBounds;
    float mCornerRadius;
    final Paint mCornerShadowPaint;
    Path mCornerShadowPath;
    private boolean mDirty = true;
    final Paint mEdgeShadowPaint;
    float mMaxShadowSize;
    private boolean mPrintedShadowClipWarning = false;
    float mRawMaxShadowSize;
    float mRawShadowSize;
    private float mRotation;
    private final int mShadowEndColor;
    private final int mShadowMiddleColor;
    float mShadowSize;
    private final int mShadowStartColor;

    public ShadowDrawableWrapper(Context context, Drawable drawable, float f, float f2, float f3) {
        Context context2 = context;
        Drawable content = drawable;
        float radius = f;
        float shadowSize = f2;
        float maxShadowSize = f3;
        Context context3 = context2;
        Drawable drawable2 = content;
        float f4 = radius;
        float f5 = shadowSize;
        float f6 = maxShadowSize;
        super(content);
        this.mShadowStartColor = ContextCompat.getColor(context2, C0001R.color.design_fab_shadow_start_color);
        this.mShadowMiddleColor = ContextCompat.getColor(context2, C0001R.color.design_fab_shadow_mid_color);
        this.mShadowEndColor = ContextCompat.getColor(context2, C0001R.color.design_fab_shadow_end_color);
        this.mCornerShadowPaint = new Paint(5);
        this.mCornerShadowPaint.setStyle(Style.FILL);
        this.mCornerRadius = (float) Math.round(radius);
        this.mContentBounds = new RectF();
        Paint paint = new Paint(this.mCornerShadowPaint);
        this.mEdgeShadowPaint = paint;
        this.mEdgeShadowPaint.setAntiAlias(false);
        setShadowSize(shadowSize, maxShadowSize);
    }

    private static int toEven(float f) {
        float value = f;
        float f2 = value;
        int round = Math.round(value);
        int i = round;
        return round % 2 != 1 ? i : i - 1;
    }

    public void setAddPaddingForCorners(boolean z) {
        this.mAddPaddingForCorners = z;
        invalidateSelf();
    }

    public void setAlpha(int i) {
        int alpha = i;
        int i2 = alpha;
        super.setAlpha(alpha);
        this.mCornerShadowPaint.setAlpha(alpha);
        this.mEdgeShadowPaint.setAlpha(alpha);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Rect rect2 = rect;
        this.mDirty = true;
    }

    /* access modifiers changed from: 0000 */
    public void setShadowSize(float f, float f2) {
        float shadowSize = f;
        float maxShadowSize = f2;
        float f3 = shadowSize;
        float f4 = maxShadowSize;
        if ((shadowSize < 0.0f) || maxShadowSize < 0.0f) {
            throw new IllegalArgumentException("invalid shadow size");
        }
        float shadowSize2 = (float) toEven(shadowSize);
        float maxShadowSize2 = (float) toEven(maxShadowSize);
        if (shadowSize2 > maxShadowSize2) {
            shadowSize2 = maxShadowSize2;
            if (!this.mPrintedShadowClipWarning) {
                this.mPrintedShadowClipWarning = true;
            }
        }
        if (this.mRawShadowSize != shadowSize2 || this.mRawMaxShadowSize != maxShadowSize2) {
            this.mRawShadowSize = shadowSize2;
            this.mRawMaxShadowSize = maxShadowSize2;
            this.mShadowSize = (float) Math.round(shadowSize2 * SHADOW_MULTIPLIER);
            this.mMaxShadowSize = maxShadowSize2;
            this.mDirty = true;
            invalidateSelf();
        }
    }

    public boolean getPadding(Rect rect) {
        Rect padding = rect;
        Rect rect2 = padding;
        int vOffset = (int) Math.ceil((double) calculateVerticalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
        int ceil = (int) Math.ceil((double) calculateHorizontalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
        int i = ceil;
        padding.set(ceil, vOffset, ceil, vOffset);
        return true;
    }

    public static float calculateVerticalPadding(float f, float f2, boolean z) {
        float maxShadowSize = f;
        float cornerRadius = f2;
        float f3 = maxShadowSize;
        float f4 = cornerRadius;
        if (!z) {
            return maxShadowSize * SHADOW_MULTIPLIER;
        }
        return (float) (((double) (maxShadowSize * SHADOW_MULTIPLIER)) + ((1.0d - COS_45) * ((double) cornerRadius)));
    }

    public static float calculateHorizontalPadding(float f, float f2, boolean z) {
        float maxShadowSize = f;
        float cornerRadius = f2;
        float f3 = maxShadowSize;
        float f4 = cornerRadius;
        if (!z) {
            return maxShadowSize;
        }
        return (float) (((double) maxShadowSize) + ((1.0d - COS_45) * ((double) cornerRadius)));
    }

    public int getOpacity() {
        return -3;
    }

    public void setCornerRadius(float f) {
        float radius = f;
        float f2 = radius;
        float radius2 = (float) Math.round(radius);
        if (this.mCornerRadius != radius2) {
            this.mCornerRadius = radius2;
            this.mDirty = true;
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        if (this.mDirty) {
            buildComponents(getBounds());
            this.mDirty = false;
        }
        drawShadow(canvas2);
        super.draw(canvas2);
    }

    /* access modifiers changed from: 0000 */
    public final void setRotation(float f) {
        float rotation = f;
        float f2 = rotation;
        if (this.mRotation != rotation) {
            this.mRotation = rotation;
            invalidateSelf();
        }
    }

    private void drawShadow(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        int rotateSaved = canvas2.save();
        canvas2.rotate(this.mRotation, this.mContentBounds.centerX(), this.mContentBounds.centerY());
        float edgeShadowTop = (-this.mCornerRadius) - this.mShadowSize;
        float shadowOffset = this.mCornerRadius;
        boolean drawHorizontalEdges = this.mContentBounds.width() - (2.0f * shadowOffset) > 0.0f;
        boolean drawVerticalEdges = this.mContentBounds.height() - (2.0f * shadowOffset) > 0.0f;
        float shadowScaleHorizontal = shadowOffset / (shadowOffset + (this.mRawShadowSize - (this.mRawShadowSize * SHADOW_HORIZ_SCALE)));
        float shadowScaleTop = shadowOffset / (shadowOffset + (this.mRawShadowSize - (this.mRawShadowSize * SHADOW_TOP_SCALE)));
        float f = shadowOffset / (shadowOffset + (this.mRawShadowSize - (this.mRawShadowSize * SHADOW_BOTTOM_SCALE)));
        float f2 = f;
        int saved = canvas2.save();
        canvas2.translate(this.mContentBounds.left + shadowOffset, this.mContentBounds.top + shadowOffset);
        canvas2.scale(shadowScaleHorizontal, shadowScaleTop);
        canvas2.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas2.scale(SHADOW_BOTTOM_SCALE / shadowScaleHorizontal, SHADOW_BOTTOM_SCALE);
            canvas2.drawRect(0.0f, edgeShadowTop, this.mContentBounds.width() - (2.0f * shadowOffset), -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas2.restoreToCount(saved);
        int saved2 = canvas2.save();
        canvas2.translate(this.mContentBounds.right - shadowOffset, this.mContentBounds.bottom - shadowOffset);
        canvas2.scale(shadowScaleHorizontal, f);
        canvas2.rotate(180.0f);
        canvas2.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas2.scale(SHADOW_BOTTOM_SCALE / shadowScaleHorizontal, SHADOW_BOTTOM_SCALE);
            canvas2.drawRect(0.0f, edgeShadowTop, this.mContentBounds.width() - (2.0f * shadowOffset), (-this.mCornerRadius) + this.mShadowSize, this.mEdgeShadowPaint);
        }
        canvas2.restoreToCount(saved2);
        int saved3 = canvas2.save();
        canvas2.translate(this.mContentBounds.left + shadowOffset, this.mContentBounds.bottom - shadowOffset);
        canvas2.scale(shadowScaleHorizontal, f);
        canvas2.rotate(270.0f);
        canvas2.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawVerticalEdges) {
            canvas2.scale(SHADOW_BOTTOM_SCALE / f, SHADOW_BOTTOM_SCALE);
            canvas2.drawRect(0.0f, edgeShadowTop, this.mContentBounds.height() - (2.0f * shadowOffset), -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas2.restoreToCount(saved3);
        int saved4 = canvas2.save();
        canvas2.translate(this.mContentBounds.right - shadowOffset, this.mContentBounds.top + shadowOffset);
        canvas2.scale(shadowScaleHorizontal, shadowScaleTop);
        canvas2.rotate(90.0f);
        canvas2.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawVerticalEdges) {
            canvas2.scale(SHADOW_BOTTOM_SCALE / shadowScaleTop, SHADOW_BOTTOM_SCALE);
            canvas2.drawRect(0.0f, edgeShadowTop, this.mContentBounds.height() - (2.0f * shadowOffset), -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas2.restoreToCount(saved4);
        canvas2.restoreToCount(rotateSaved);
    }

    private void buildShadowCorners() {
        RectF innerBounds = new RectF(-this.mCornerRadius, -this.mCornerRadius, this.mCornerRadius, this.mCornerRadius);
        RectF rectF = new RectF(innerBounds);
        RectF outerBounds = rectF;
        rectF.inset(-this.mShadowSize, -this.mShadowSize);
        if (this.mCornerShadowPath != null) {
            this.mCornerShadowPath.reset();
        } else {
            this.mCornerShadowPath = new Path();
        }
        this.mCornerShadowPath.setFillType(FillType.EVEN_ODD);
        this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0f);
        this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0f);
        this.mCornerShadowPath.arcTo(outerBounds, 180.0f, 90.0f, false);
        this.mCornerShadowPath.arcTo(innerBounds, 270.0f, -90.0f, false);
        this.mCornerShadowPath.close();
        float f = -outerBounds.top;
        float shadowRadius = f;
        if (f > 0.0f) {
            float f2 = this.mCornerRadius / shadowRadius;
            float startRatio = f2;
            float f3 = f2 + ((SHADOW_BOTTOM_SCALE - startRatio) / 2.0f);
            float f4 = f3;
            Paint paint = this.mCornerShadowPaint;
            int[] iArr = new int[4];
            iArr[0] = 0;
            iArr[1] = this.mShadowStartColor;
            iArr[2] = this.mShadowMiddleColor;
            iArr[3] = this.mShadowEndColor;
            RadialGradient radialGradient = new RadialGradient(0.0f, 0.0f, shadowRadius, iArr, new float[]{0.0f, startRatio, f3, 1.0f}, TileMode.CLAMP);
            Shader shader = paint.setShader(radialGradient);
        }
        Paint paint2 = this.mEdgeShadowPaint;
        float f5 = innerBounds.top;
        float f6 = outerBounds.top;
        int[] iArr2 = new int[3];
        iArr2[0] = this.mShadowStartColor;
        iArr2[1] = this.mShadowMiddleColor;
        iArr2[2] = this.mShadowEndColor;
        LinearGradient linearGradient = new LinearGradient(0.0f, f5, 0.0f, f6, iArr2, new float[]{0.0f, 0.5f, 1.0f}, TileMode.CLAMP);
        Shader shader2 = paint2.setShader(linearGradient);
        this.mEdgeShadowPaint.setAntiAlias(false);
    }

    private void buildComponents(Rect rect) {
        Rect bounds = rect;
        Rect rect2 = bounds;
        float f = this.mRawMaxShadowSize * SHADOW_MULTIPLIER;
        float f2 = f;
        this.mContentBounds.set(((float) bounds.left) + this.mRawMaxShadowSize, ((float) bounds.top) + f, ((float) bounds.right) - this.mRawMaxShadowSize, ((float) bounds.bottom) - f);
        getWrappedDrawable().setBounds((int) this.mContentBounds.left, (int) this.mContentBounds.top, (int) this.mContentBounds.right, (int) this.mContentBounds.bottom);
        buildShadowCorners();
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public void setShadowSize(float f) {
        float size = f;
        float f2 = size;
        setShadowSize(size, this.mRawMaxShadowSize);
    }

    public void setMaxShadowSize(float f) {
        float size = f;
        float f2 = size;
        setShadowSize(this.mRawShadowSize, size);
    }

    public float getShadowSize() {
        return this.mRawShadowSize;
    }

    public float getMaxShadowSize() {
        return this.mRawMaxShadowSize;
    }

    public float getMinWidth() {
        float max = 2.0f * Math.max(this.mRawMaxShadowSize, this.mCornerRadius + (this.mRawMaxShadowSize / 2.0f));
        float f = max;
        return max + (this.mRawMaxShadowSize * 2.0f);
    }

    public float getMinHeight() {
        float max = 2.0f * Math.max(this.mRawMaxShadowSize, this.mCornerRadius + ((this.mRawMaxShadowSize * SHADOW_MULTIPLIER) / 2.0f));
        float f = max;
        return max + (this.mRawMaxShadowSize * SHADOW_MULTIPLIER * 2.0f);
    }
}
