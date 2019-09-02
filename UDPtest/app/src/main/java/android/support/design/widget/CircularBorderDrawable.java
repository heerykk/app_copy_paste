package android.support.design.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.p000v4.graphics.ColorUtils;

class CircularBorderDrawable extends Drawable {
    private static final float DRAW_STROKE_WIDTH_MULTIPLE = 1.3333f;
    private ColorStateList mBorderTint;
    float mBorderWidth;
    private int mBottomInnerStrokeColor;
    private int mBottomOuterStrokeColor;
    private int mCurrentBorderTintColor;
    private boolean mInvalidateShader = true;
    final Paint mPaint = new Paint(1);
    final Rect mRect = new Rect();
    final RectF mRectF = new RectF();
    private float mRotation;
    private int mTopInnerStrokeColor;
    private int mTopOuterStrokeColor;

    public CircularBorderDrawable() {
        this.mPaint.setStyle(Style.STROKE);
    }

    /* access modifiers changed from: 0000 */
    public void setGradientColors(int i, int i2, int i3, int i4) {
        int topOuterStrokeColor = i;
        int topInnerStrokeColor = i2;
        int bottomOuterStrokeColor = i3;
        int bottomInnerStrokeColor = i4;
        int i5 = topOuterStrokeColor;
        int i6 = topInnerStrokeColor;
        int i7 = bottomOuterStrokeColor;
        int i8 = bottomInnerStrokeColor;
        this.mTopOuterStrokeColor = topOuterStrokeColor;
        this.mTopInnerStrokeColor = topInnerStrokeColor;
        this.mBottomOuterStrokeColor = bottomOuterStrokeColor;
        this.mBottomInnerStrokeColor = bottomInnerStrokeColor;
    }

    /* access modifiers changed from: 0000 */
    public void setBorderWidth(float f) {
        float width = f;
        float f2 = width;
        if (this.mBorderWidth != width) {
            this.mBorderWidth = width;
            this.mPaint.setStrokeWidth(width * DRAW_STROKE_WIDTH_MULTIPLE);
            this.mInvalidateShader = true;
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        if (this.mInvalidateShader) {
            Shader shader = this.mPaint.setShader(createGradientShader());
            this.mInvalidateShader = false;
        }
        float strokeWidth = this.mPaint.getStrokeWidth() / 2.0f;
        float f = strokeWidth;
        RectF rectF = this.mRectF;
        copyBounds(this.mRect);
        rectF.set(this.mRect);
        rectF.left += strokeWidth;
        rectF.top += strokeWidth;
        rectF.right -= strokeWidth;
        rectF.bottom -= strokeWidth;
        int save = canvas2.save();
        canvas2.rotate(this.mRotation, rectF.centerX(), rectF.centerY());
        canvas2.drawOval(rectF, this.mPaint);
        canvas2.restore();
    }

    public boolean getPadding(Rect rect) {
        Rect padding = rect;
        Rect rect2 = padding;
        int round = Math.round(this.mBorderWidth);
        int i = round;
        padding.set(round, round, round, round);
        return true;
    }

    public void setAlpha(int i) {
        int alpha = i;
        int i2 = alpha;
        this.mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    /* access modifiers changed from: 0000 */
    public void setBorderTint(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        if (tint != null) {
            this.mCurrentBorderTintColor = tint.getColorForState(getState(), this.mCurrentBorderTintColor);
        }
        this.mBorderTint = tint;
        this.mInvalidateShader = true;
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        ColorFilter colorFilter2 = colorFilter;
        ColorFilter colorFilter3 = colorFilter2;
        ColorFilter colorFilter4 = this.mPaint.setColorFilter(colorFilter2);
        invalidateSelf();
    }

    public int getOpacity() {
        return this.mBorderWidth > 0.0f ? -3 : -2;
    }

    /* access modifiers changed from: 0000 */
    public final void setRotation(float f) {
        float rotation = f;
        float f2 = rotation;
        if (rotation != this.mRotation) {
            this.mRotation = rotation;
            invalidateSelf();
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Rect rect2 = rect;
        this.mInvalidateShader = true;
    }

    public boolean isStateful() {
        return (this.mBorderTint != null && this.mBorderTint.isStateful()) || super.isStateful();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        int[] state = iArr;
        int[] iArr2 = state;
        if (this.mBorderTint != null) {
            int colorForState = this.mBorderTint.getColorForState(state, this.mCurrentBorderTintColor);
            int newColor = colorForState;
            if (colorForState != this.mCurrentBorderTintColor) {
                this.mInvalidateShader = true;
                this.mCurrentBorderTintColor = newColor;
            }
        }
        if (this.mInvalidateShader) {
            invalidateSelf();
        }
        return this.mInvalidateShader;
    }

    private Shader createGradientShader() {
        Rect rect = this.mRect;
        copyBounds(rect);
        float height = this.mBorderWidth / ((float) rect.height());
        float f = height;
        int[] iArr = new int[6];
        int[] colors = iArr;
        iArr[0] = ColorUtils.compositeColors(this.mTopOuterStrokeColor, this.mCurrentBorderTintColor);
        colors[1] = ColorUtils.compositeColors(this.mTopInnerStrokeColor, this.mCurrentBorderTintColor);
        colors[2] = ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.mTopInnerStrokeColor, 0), this.mCurrentBorderTintColor);
        colors[3] = ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.mBottomInnerStrokeColor, 0), this.mCurrentBorderTintColor);
        colors[4] = ColorUtils.compositeColors(this.mBottomInnerStrokeColor, this.mCurrentBorderTintColor);
        colors[5] = ColorUtils.compositeColors(this.mBottomOuterStrokeColor, this.mCurrentBorderTintColor);
        float[] fArr = new float[6];
        float[] positions = fArr;
        fArr[0] = 0.0f;
        positions[1] = height;
        positions[2] = 0.5f;
        positions[3] = 0.5f;
        positions[4] = 1.0f - height;
        positions[5] = 1.0f;
        LinearGradient linearGradient = new LinearGradient(0.0f, (float) rect.top, 0.0f, (float) rect.bottom, colors, positions, TileMode.CLAMP);
        return linearGradient;
    }
}
