package android.support.p000v4.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.graphics.drawable.RoundedBitmapDrawable */
public abstract class RoundedBitmapDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 3;
    private boolean mApplyGravity = true;
    final Bitmap mBitmap;
    private int mBitmapHeight;
    private final BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private float mCornerRadius;
    final Rect mDstRect = new Rect();
    private final RectF mDstRectF = new RectF();
    private int mGravity = 119;
    private boolean mIsCircular;
    private final Paint mPaint = new Paint(3);
    private final Matrix mShaderMatrix = new Matrix();
    private int mTargetDensity = 160;

    RoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        Resources res = resources;
        Bitmap bitmap2 = bitmap;
        Resources resources2 = res;
        Bitmap bitmap3 = bitmap2;
        if (res != null) {
            this.mTargetDensity = res.getDisplayMetrics().densityDpi;
        }
        this.mBitmap = bitmap2;
        if (this.mBitmap == null) {
            this.mBitmapHeight = -1;
            this.mBitmapWidth = -1;
            this.mBitmapShader = null;
            return;
        }
        computeBitmapSize();
        this.mBitmapShader = new BitmapShader(this.mBitmap, TileMode.CLAMP, TileMode.CLAMP);
    }

    public final Paint getPaint() {
        return this.mPaint;
    }

    public final Bitmap getBitmap() {
        return this.mBitmap;
    }

    private void computeBitmapSize() {
        this.mBitmapWidth = this.mBitmap.getScaledWidth(this.mTargetDensity);
        this.mBitmapHeight = this.mBitmap.getScaledHeight(this.mTargetDensity);
    }

    public void setTargetDensity(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        setTargetDensity(canvas2.getDensity());
    }

    public void setTargetDensity(DisplayMetrics displayMetrics) {
        DisplayMetrics metrics = displayMetrics;
        DisplayMetrics displayMetrics2 = metrics;
        setTargetDensity(metrics.densityDpi);
    }

    public void setTargetDensity(int i) {
        int density = i;
        int i2 = density;
        if (this.mTargetDensity != density) {
            this.mTargetDensity = density != 0 ? density : 160;
            if (this.mBitmap != null) {
                computeBitmapSize();
            }
            invalidateSelf();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        if (this.mGravity != gravity) {
            this.mGravity = gravity;
            this.mApplyGravity = true;
            invalidateSelf();
        }
    }

    public void setMipMap(boolean z) {
        boolean z2 = z;
        throw new UnsupportedOperationException();
    }

    public boolean hasMipMap() {
        throw new UnsupportedOperationException();
    }

    public void setAntiAlias(boolean z) {
        this.mPaint.setAntiAlias(z);
        invalidateSelf();
    }

    public boolean hasAntiAlias() {
        return this.mPaint.isAntiAlias();
    }

    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    public void setDither(boolean z) {
        this.mPaint.setDither(z);
        invalidateSelf();
    }

    /* access modifiers changed from: 0000 */
    public void gravityCompatApply(int i, int i2, int i3, Rect rect, Rect rect2) {
        int i4 = i;
        int i5 = i2;
        int i6 = i3;
        Rect rect3 = rect;
        Rect rect4 = rect2;
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public void updateDstRect() {
        if (this.mApplyGravity) {
            if (!this.mIsCircular) {
                gravityCompatApply(this.mGravity, this.mBitmapWidth, this.mBitmapHeight, getBounds(), this.mDstRect);
            } else {
                int min = Math.min(this.mBitmapWidth, this.mBitmapHeight);
                int i = min;
                gravityCompatApply(this.mGravity, min, min, getBounds(), this.mDstRect);
                int minDrawDimen = Math.min(this.mDstRect.width(), this.mDstRect.height());
                int insetX = Math.max(0, (this.mDstRect.width() - minDrawDimen) / 2);
                int max = Math.max(0, (this.mDstRect.height() - minDrawDimen) / 2);
                int i2 = max;
                this.mDstRect.inset(insetX, max);
                this.mCornerRadius = 0.5f * ((float) minDrawDimen);
            }
            this.mDstRectF.set(this.mDstRect);
            if (this.mBitmapShader != null) {
                this.mShaderMatrix.setTranslate(this.mDstRectF.left, this.mDstRectF.top);
                boolean preScale = this.mShaderMatrix.preScale(this.mDstRectF.width() / ((float) this.mBitmap.getWidth()), this.mDstRectF.height() / ((float) this.mBitmap.getHeight()));
                this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
                Shader shader = this.mPaint.setShader(this.mBitmapShader);
            }
            this.mApplyGravity = false;
        }
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        Bitmap bitmap = this.mBitmap;
        Bitmap bitmap2 = bitmap;
        if (bitmap != null) {
            updateDstRect();
            if (this.mPaint.getShader() != null) {
                canvas2.drawRoundRect(this.mDstRectF, this.mCornerRadius, this.mCornerRadius, this.mPaint);
            } else {
                canvas2.drawBitmap(bitmap2, null, this.mDstRect, this.mPaint);
            }
        }
    }

    public void setAlpha(int i) {
        int alpha = i;
        int i2 = alpha;
        if (alpha != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        ColorFilter cf = colorFilter;
        ColorFilter colorFilter2 = cf;
        ColorFilter colorFilter3 = this.mPaint.setColorFilter(cf);
        invalidateSelf();
    }

    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public void setCircular(boolean z) {
        boolean circular = z;
        this.mIsCircular = circular;
        this.mApplyGravity = true;
        if (!circular) {
            setCornerRadius(0.0f);
            return;
        }
        updateCircularCornerRadius();
        Shader shader = this.mPaint.setShader(this.mBitmapShader);
        invalidateSelf();
    }

    private void updateCircularCornerRadius() {
        int min = Math.min(this.mBitmapHeight, this.mBitmapWidth);
        int i = min;
        this.mCornerRadius = (float) (min / 2);
    }

    public boolean isCircular() {
        return this.mIsCircular;
    }

    public void setCornerRadius(float f) {
        float cornerRadius = f;
        float f2 = cornerRadius;
        if (this.mCornerRadius != cornerRadius) {
            this.mIsCircular = false;
            if (!isGreaterThanZero(cornerRadius)) {
                Shader shader = this.mPaint.setShader(null);
            } else {
                Shader shader2 = this.mPaint.setShader(this.mBitmapShader);
            }
            this.mCornerRadius = cornerRadius;
            invalidateSelf();
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Rect bounds = rect;
        Rect rect2 = bounds;
        super.onBoundsChange(bounds);
        if (this.mIsCircular) {
            updateCircularCornerRadius();
        }
        this.mApplyGravity = true;
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    public int getOpacity() {
        if (this.mGravity != 119 || this.mIsCircular) {
            return -3;
        }
        Bitmap bitmap = this.mBitmap;
        return (bitmap != null && !bitmap.hasAlpha() && this.mPaint.getAlpha() >= 255 && !isGreaterThanZero(this.mCornerRadius)) ? -1 : -3;
    }

    private static boolean isGreaterThanZero(float f) {
        float toCompare = f;
        float f2 = toCompare;
        return toCompare > 0.05f;
    }
}
