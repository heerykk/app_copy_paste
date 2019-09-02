package android.support.design.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.p000v4.text.TextDirectionHeuristicsCompat;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.widget.TintTypedArray;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.animation.Interpolator;

final class CollapsingTextHelper {
    private static final boolean DEBUG_DRAW = false;
    private static final Paint DEBUG_DRAW_PAINT = null;
    private static final boolean USE_SCALING_TEXTURE = (VERSION.SDK_INT < 18);
    private boolean mBoundsChanged;
    private final Rect mCollapsedBounds;
    private float mCollapsedDrawX;
    private float mCollapsedDrawY;
    private int mCollapsedShadowColor;
    private float mCollapsedShadowDx;
    private float mCollapsedShadowDy;
    private float mCollapsedShadowRadius;
    private ColorStateList mCollapsedTextColor;
    private int mCollapsedTextGravity = 16;
    private float mCollapsedTextSize = 15.0f;
    private Typeface mCollapsedTypeface;
    private final RectF mCurrentBounds;
    private float mCurrentDrawX;
    private float mCurrentDrawY;
    private float mCurrentTextSize;
    private Typeface mCurrentTypeface;
    private boolean mDrawTitle;
    private final Rect mExpandedBounds;
    private float mExpandedDrawX;
    private float mExpandedDrawY;
    private float mExpandedFraction;
    private int mExpandedShadowColor;
    private float mExpandedShadowDx;
    private float mExpandedShadowDy;
    private float mExpandedShadowRadius;
    private ColorStateList mExpandedTextColor;
    private int mExpandedTextGravity = 16;
    private float mExpandedTextSize = 15.0f;
    private Bitmap mExpandedTitleTexture;
    private Typeface mExpandedTypeface;
    private boolean mIsRtl;
    private Interpolator mPositionInterpolator;
    private float mScale;
    private int[] mState;
    private CharSequence mText;
    private final TextPaint mTextPaint;
    private Interpolator mTextSizeInterpolator;
    private CharSequence mTextToDraw;
    private float mTextureAscent;
    private float mTextureDescent;
    private Paint mTexturePaint;
    private boolean mUseTexture;
    private final View mView;

    static {
        if (DEBUG_DRAW_PAINT != null) {
            DEBUG_DRAW_PAINT.setAntiAlias(true);
            DEBUG_DRAW_PAINT.setColor(-65281);
        }
    }

    public CollapsingTextHelper(View view) {
        View view2 = view;
        View view3 = view2;
        this.mView = view2;
        this.mTextPaint = new TextPaint(129);
        this.mCollapsedBounds = new Rect();
        this.mExpandedBounds = new Rect();
        this.mCurrentBounds = new RectF();
    }

    /* access modifiers changed from: 0000 */
    public void setTextSizeInterpolator(Interpolator interpolator) {
        Interpolator interpolator2 = interpolator;
        Interpolator interpolator3 = interpolator2;
        this.mTextSizeInterpolator = interpolator2;
        recalculate();
    }

    /* access modifiers changed from: 0000 */
    public void setPositionInterpolator(Interpolator interpolator) {
        Interpolator interpolator2 = interpolator;
        Interpolator interpolator3 = interpolator2;
        this.mPositionInterpolator = interpolator2;
        recalculate();
    }

    /* access modifiers changed from: 0000 */
    public void setExpandedTextSize(float f) {
        float textSize = f;
        float f2 = textSize;
        if (this.mExpandedTextSize != textSize) {
            this.mExpandedTextSize = textSize;
            recalculate();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setCollapsedTextSize(float f) {
        float textSize = f;
        float f2 = textSize;
        if (this.mCollapsedTextSize != textSize) {
            this.mCollapsedTextSize = textSize;
            recalculate();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setCollapsedTextColor(ColorStateList colorStateList) {
        ColorStateList textColor = colorStateList;
        ColorStateList colorStateList2 = textColor;
        if (this.mCollapsedTextColor != textColor) {
            this.mCollapsedTextColor = textColor;
            recalculate();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setExpandedTextColor(ColorStateList colorStateList) {
        ColorStateList textColor = colorStateList;
        ColorStateList colorStateList2 = textColor;
        if (this.mExpandedTextColor != textColor) {
            this.mExpandedTextColor = textColor;
            recalculate();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setExpandedBounds(int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        if (!rectEquals(this.mExpandedBounds, left, top, right, bottom)) {
            this.mExpandedBounds.set(left, top, right, bottom);
            this.mBoundsChanged = true;
            onBoundsChanged();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setCollapsedBounds(int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        if (!rectEquals(this.mCollapsedBounds, left, top, right, bottom)) {
            this.mCollapsedBounds.set(left, top, right, bottom);
            this.mBoundsChanged = true;
            onBoundsChanged();
        }
    }

    /* access modifiers changed from: 0000 */
    public void onBoundsChanged() {
        this.mDrawTitle = this.mCollapsedBounds.width() > 0 && this.mCollapsedBounds.height() > 0 && this.mExpandedBounds.width() > 0 && this.mExpandedBounds.height() > 0;
    }

    /* access modifiers changed from: 0000 */
    public void setExpandedTextGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        if (this.mExpandedTextGravity != gravity) {
            this.mExpandedTextGravity = gravity;
            recalculate();
        }
    }

    /* access modifiers changed from: 0000 */
    public int getExpandedTextGravity() {
        return this.mExpandedTextGravity;
    }

    /* access modifiers changed from: 0000 */
    public void setCollapsedTextGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        if (this.mCollapsedTextGravity != gravity) {
            this.mCollapsedTextGravity = gravity;
            recalculate();
        }
    }

    /* access modifiers changed from: 0000 */
    public int getCollapsedTextGravity() {
        return this.mCollapsedTextGravity;
    }

    /* access modifiers changed from: 0000 */
    public void setCollapsedTextAppearance(int i) {
        int resId = i;
        int i2 = resId;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), resId, C0268R.styleable.TextAppearance);
        TintTypedArray a = obtainStyledAttributes;
        if (obtainStyledAttributes.hasValue(C0268R.styleable.TextAppearance_android_textColor)) {
            this.mCollapsedTextColor = a.getColorStateList(C0268R.styleable.TextAppearance_android_textColor);
        }
        if (a.hasValue(C0268R.styleable.TextAppearance_android_textSize)) {
            this.mCollapsedTextSize = (float) a.getDimensionPixelSize(C0268R.styleable.TextAppearance_android_textSize, (int) this.mCollapsedTextSize);
        }
        this.mCollapsedShadowColor = a.getInt(C0268R.styleable.TextAppearance_android_shadowColor, 0);
        this.mCollapsedShadowDx = a.getFloat(C0268R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.mCollapsedShadowDy = a.getFloat(C0268R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.mCollapsedShadowRadius = a.getFloat(C0268R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        a.recycle();
        if (VERSION.SDK_INT >= 16) {
            this.mCollapsedTypeface = readFontFamilyTypeface(resId);
        }
        recalculate();
    }

    /* access modifiers changed from: 0000 */
    public void setExpandedTextAppearance(int i) {
        int resId = i;
        int i2 = resId;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), resId, C0268R.styleable.TextAppearance);
        TintTypedArray a = obtainStyledAttributes;
        if (obtainStyledAttributes.hasValue(C0268R.styleable.TextAppearance_android_textColor)) {
            this.mExpandedTextColor = a.getColorStateList(C0268R.styleable.TextAppearance_android_textColor);
        }
        if (a.hasValue(C0268R.styleable.TextAppearance_android_textSize)) {
            this.mExpandedTextSize = (float) a.getDimensionPixelSize(C0268R.styleable.TextAppearance_android_textSize, (int) this.mExpandedTextSize);
        }
        this.mExpandedShadowColor = a.getInt(C0268R.styleable.TextAppearance_android_shadowColor, 0);
        this.mExpandedShadowDx = a.getFloat(C0268R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.mExpandedShadowDy = a.getFloat(C0268R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.mExpandedShadowRadius = a.getFloat(C0268R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        a.recycle();
        if (VERSION.SDK_INT >= 16) {
            this.mExpandedTypeface = readFontFamilyTypeface(resId);
        }
        recalculate();
    }

    /* JADX INFO: finally extract failed */
    private Typeface readFontFamilyTypeface(int i) {
        int resId = i;
        int i2 = resId;
        TypedArray obtainStyledAttributes = this.mView.getContext().obtainStyledAttributes(resId, new int[]{16843692});
        TypedArray a = obtainStyledAttributes;
        try {
            String string = obtainStyledAttributes.getString(0);
            String family = string;
            if (string == null) {
                a.recycle();
                return null;
            }
            Typeface create = Typeface.create(family, 0);
            a.recycle();
            return create;
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public void setCollapsedTypeface(Typeface typeface) {
        Typeface typeface2 = typeface;
        Typeface typeface3 = typeface2;
        if (this.mCollapsedTypeface != typeface2) {
            this.mCollapsedTypeface = typeface2;
            recalculate();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setExpandedTypeface(Typeface typeface) {
        Typeface typeface2 = typeface;
        Typeface typeface3 = typeface2;
        if (this.mExpandedTypeface != typeface2) {
            this.mExpandedTypeface = typeface2;
            recalculate();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setTypefaces(Typeface typeface) {
        Typeface typeface2 = typeface;
        Typeface typeface3 = typeface2;
        this.mExpandedTypeface = typeface2;
        this.mCollapsedTypeface = typeface2;
        recalculate();
    }

    /* access modifiers changed from: 0000 */
    public Typeface getCollapsedTypeface() {
        return this.mCollapsedTypeface == null ? Typeface.DEFAULT : this.mCollapsedTypeface;
    }

    /* access modifiers changed from: 0000 */
    public Typeface getExpandedTypeface() {
        return this.mExpandedTypeface == null ? Typeface.DEFAULT : this.mExpandedTypeface;
    }

    /* access modifiers changed from: 0000 */
    public void setExpansionFraction(float f) {
        float fraction = f;
        float f2 = fraction;
        float constrain = MathUtils.constrain(fraction, 0.0f, 1.0f);
        float fraction2 = constrain;
        if (constrain != this.mExpandedFraction) {
            this.mExpandedFraction = fraction2;
            calculateCurrentOffsets();
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean setState(int[] iArr) {
        int[] state = iArr;
        int[] iArr2 = state;
        this.mState = state;
        if (!isStateful()) {
            return false;
        }
        recalculate();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final boolean isStateful() {
        return (this.mCollapsedTextColor != null && this.mCollapsedTextColor.isStateful()) || (this.mExpandedTextColor != null && this.mExpandedTextColor.isStateful());
    }

    /* access modifiers changed from: 0000 */
    public float getExpansionFraction() {
        return this.mExpandedFraction;
    }

    /* access modifiers changed from: 0000 */
    public float getCollapsedTextSize() {
        return this.mCollapsedTextSize;
    }

    /* access modifiers changed from: 0000 */
    public float getExpandedTextSize() {
        return this.mExpandedTextSize;
    }

    private void calculateCurrentOffsets() {
        calculateOffsets(this.mExpandedFraction);
    }

    private void calculateOffsets(float f) {
        float fraction = f;
        float f2 = fraction;
        interpolateBounds(fraction);
        this.mCurrentDrawX = lerp(this.mExpandedDrawX, this.mCollapsedDrawX, fraction, this.mPositionInterpolator);
        this.mCurrentDrawY = lerp(this.mExpandedDrawY, this.mCollapsedDrawY, fraction, this.mPositionInterpolator);
        setInterpolatedTextSize(lerp(this.mExpandedTextSize, this.mCollapsedTextSize, fraction, this.mTextSizeInterpolator));
        if (this.mCollapsedTextColor == this.mExpandedTextColor) {
            this.mTextPaint.setColor(getCurrentCollapsedTextColor());
        } else {
            this.mTextPaint.setColor(blendColors(getCurrentExpandedTextColor(), getCurrentCollapsedTextColor(), fraction));
        }
        this.mTextPaint.setShadowLayer(lerp(this.mExpandedShadowRadius, this.mCollapsedShadowRadius, fraction, null), lerp(this.mExpandedShadowDx, this.mCollapsedShadowDx, fraction, null), lerp(this.mExpandedShadowDy, this.mCollapsedShadowDy, fraction, null), blendColors(this.mExpandedShadowColor, this.mCollapsedShadowColor, fraction));
        ViewCompat.postInvalidateOnAnimation(this.mView);
    }

    @ColorInt
    private int getCurrentExpandedTextColor() {
        if (this.mState == null) {
            return this.mExpandedTextColor.getDefaultColor();
        }
        return this.mExpandedTextColor.getColorForState(this.mState, 0);
    }

    @ColorInt
    private int getCurrentCollapsedTextColor() {
        if (this.mState == null) {
            return this.mCollapsedTextColor.getDefaultColor();
        }
        return this.mCollapsedTextColor.getColorForState(this.mState, 0);
    }

    private void calculateBaseOffsets() {
        float currentTextSize = this.mCurrentTextSize;
        calculateUsingTextSize(this.mCollapsedTextSize);
        float width = this.mTextToDraw == null ? 0.0f : this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length());
        int absoluteGravity = GravityCompat.getAbsoluteGravity(this.mCollapsedTextGravity, !this.mIsRtl ? 0 : 1);
        int collapsedAbsGravity = absoluteGravity;
        switch (absoluteGravity & 112) {
            case 48:
                this.mCollapsedDrawY = ((float) this.mCollapsedBounds.top) - this.mTextPaint.ascent();
                break;
            case 80:
                this.mCollapsedDrawY = (float) this.mCollapsedBounds.bottom;
                break;
            default:
                float descent = this.mTextPaint.descent() - this.mTextPaint.ascent();
                float f = descent;
                float descent2 = (descent / 2.0f) - this.mTextPaint.descent();
                float f2 = descent2;
                this.mCollapsedDrawY = ((float) this.mCollapsedBounds.centerY()) + descent2;
                break;
        }
        switch (collapsedAbsGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
            case 1:
                this.mCollapsedDrawX = ((float) this.mCollapsedBounds.centerX()) - (width / 2.0f);
                break;
            case 5:
                this.mCollapsedDrawX = ((float) this.mCollapsedBounds.right) - width;
                break;
            default:
                this.mCollapsedDrawX = (float) this.mCollapsedBounds.left;
                break;
        }
        calculateUsingTextSize(this.mExpandedTextSize);
        float width2 = this.mTextToDraw == null ? 0.0f : this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length());
        int absoluteGravity2 = GravityCompat.getAbsoluteGravity(this.mExpandedTextGravity, !this.mIsRtl ? 0 : 1);
        int expandedAbsGravity = absoluteGravity2;
        switch (absoluteGravity2 & 112) {
            case 48:
                this.mExpandedDrawY = ((float) this.mExpandedBounds.top) - this.mTextPaint.ascent();
                break;
            case 80:
                this.mExpandedDrawY = (float) this.mExpandedBounds.bottom;
                break;
            default:
                float descent3 = this.mTextPaint.descent() - this.mTextPaint.ascent();
                float f3 = descent3;
                float descent4 = (descent3 / 2.0f) - this.mTextPaint.descent();
                float f4 = descent4;
                this.mExpandedDrawY = ((float) this.mExpandedBounds.centerY()) + descent4;
                break;
        }
        switch (expandedAbsGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
            case 1:
                this.mExpandedDrawX = ((float) this.mExpandedBounds.centerX()) - (width2 / 2.0f);
                break;
            case 5:
                this.mExpandedDrawX = ((float) this.mExpandedBounds.right) - width2;
                break;
            default:
                this.mExpandedDrawX = (float) this.mExpandedBounds.left;
                break;
        }
        clearTexture();
        setInterpolatedTextSize(currentTextSize);
    }

    private void interpolateBounds(float f) {
        float fraction = f;
        float f2 = fraction;
        this.mCurrentBounds.left = lerp((float) this.mExpandedBounds.left, (float) this.mCollapsedBounds.left, fraction, this.mPositionInterpolator);
        this.mCurrentBounds.top = lerp(this.mExpandedDrawY, this.mCollapsedDrawY, fraction, this.mPositionInterpolator);
        this.mCurrentBounds.right = lerp((float) this.mExpandedBounds.right, (float) this.mCollapsedBounds.right, fraction, this.mPositionInterpolator);
        this.mCurrentBounds.bottom = lerp((float) this.mExpandedBounds.bottom, (float) this.mCollapsedBounds.bottom, fraction, this.mPositionInterpolator);
    }

    public void draw(Canvas canvas) {
        float ascent;
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        int saveCount = canvas2.save();
        if (this.mTextToDraw != null && this.mDrawTitle) {
            float x = this.mCurrentDrawX;
            float y = this.mCurrentDrawY;
            boolean drawTexture = this.mUseTexture && this.mExpandedTitleTexture != null;
            if (!drawTexture) {
                ascent = this.mTextPaint.ascent() * this.mScale;
                float descent = this.mTextPaint.descent() * this.mScale;
            } else {
                ascent = this.mTextureAscent * this.mScale;
                float f = this.mTextureDescent * this.mScale;
            }
            if (drawTexture) {
                y += ascent;
            }
            if (this.mScale != 1.0f) {
                canvas2.scale(this.mScale, this.mScale, x, y);
            }
            if (!drawTexture) {
                canvas2.drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), x, y, this.mTextPaint);
            } else {
                canvas2.drawBitmap(this.mExpandedTitleTexture, x, y, this.mTexturePaint);
            }
        }
        canvas2.restoreToCount(saveCount);
    }

    private boolean calculateIsRtl(CharSequence charSequence) {
        CharSequence text = charSequence;
        CharSequence charSequence2 = text;
        return (!(ViewCompat.getLayoutDirection(this.mView) == 1) ? TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR : TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL).isRtl(text, 0, text.length());
    }

    private void setInterpolatedTextSize(float f) {
        float textSize = f;
        float f2 = textSize;
        calculateUsingTextSize(textSize);
        this.mUseTexture = USE_SCALING_TEXTURE && this.mScale != 1.0f;
        if (this.mUseTexture) {
            ensureExpandedTexture();
        }
        ViewCompat.postInvalidateOnAnimation(this.mView);
    }

    private void calculateUsingTextSize(float f) {
        float newTextSize;
        float availableWidth;
        float textSize = f;
        float f2 = textSize;
        if (this.mText != null) {
            float collapsedWidth = (float) this.mCollapsedBounds.width();
            float expandedWidth = (float) this.mExpandedBounds.width();
            boolean updateDrawText = false;
            if (!isClose(textSize, this.mCollapsedTextSize)) {
                newTextSize = this.mExpandedTextSize;
                if (this.mCurrentTypeface != this.mExpandedTypeface) {
                    this.mCurrentTypeface = this.mExpandedTypeface;
                    updateDrawText = true;
                }
                if (!isClose(textSize, this.mExpandedTextSize)) {
                    this.mScale = textSize / this.mExpandedTextSize;
                } else {
                    this.mScale = 1.0f;
                }
                float textSizeRatio = this.mCollapsedTextSize / this.mExpandedTextSize;
                float f3 = expandedWidth * textSizeRatio;
                float f4 = f3;
                if (f3 > collapsedWidth) {
                    availableWidth = Math.min(collapsedWidth / textSizeRatio, expandedWidth);
                } else {
                    availableWidth = expandedWidth;
                }
            } else {
                newTextSize = this.mCollapsedTextSize;
                this.mScale = 1.0f;
                if (this.mCurrentTypeface != this.mCollapsedTypeface) {
                    this.mCurrentTypeface = this.mCollapsedTypeface;
                    updateDrawText = true;
                }
                availableWidth = collapsedWidth;
            }
            if (availableWidth > 0.0f) {
                updateDrawText = this.mCurrentTextSize != newTextSize || this.mBoundsChanged || updateDrawText;
                this.mCurrentTextSize = newTextSize;
                this.mBoundsChanged = false;
            }
            if (this.mTextToDraw == null || updateDrawText) {
                this.mTextPaint.setTextSize(this.mCurrentTextSize);
                Typeface typeface = this.mTextPaint.setTypeface(this.mCurrentTypeface);
                this.mTextPaint.setLinearText(this.mScale != 1.0f);
                CharSequence ellipsize = TextUtils.ellipsize(this.mText, this.mTextPaint, availableWidth, TruncateAt.END);
                CharSequence title = ellipsize;
                if (!TextUtils.equals(ellipsize, this.mTextToDraw)) {
                    this.mTextToDraw = title;
                    this.mIsRtl = calculateIsRtl(this.mTextToDraw);
                }
            }
        }
    }

    private void ensureExpandedTexture() {
        if (this.mExpandedTitleTexture == null && !this.mExpandedBounds.isEmpty() && !TextUtils.isEmpty(this.mTextToDraw)) {
            calculateOffsets(0.0f);
            this.mTextureAscent = this.mTextPaint.ascent();
            this.mTextureDescent = this.mTextPaint.descent();
            int w = Math.round(this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length()));
            int h = Math.round(this.mTextureDescent - this.mTextureAscent);
            if (w > 0 && h > 0) {
                this.mExpandedTitleTexture = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(this.mExpandedTitleTexture);
                Canvas canvas2 = canvas;
                canvas.drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), 0.0f, ((float) h) - this.mTextPaint.descent(), this.mTextPaint);
                if (this.mTexturePaint == null) {
                    Paint paint = new Paint(3);
                    this.mTexturePaint = paint;
                }
            }
        }
    }

    public void recalculate() {
        if (this.mView.getHeight() > 0 && this.mView.getWidth() > 0) {
            calculateBaseOffsets();
            calculateCurrentOffsets();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setText(CharSequence charSequence) {
        CharSequence text = charSequence;
        CharSequence charSequence2 = text;
        if (text == null || !text.equals(this.mText)) {
            this.mText = text;
            this.mTextToDraw = null;
            clearTexture();
            recalculate();
        }
    }

    /* access modifiers changed from: 0000 */
    public CharSequence getText() {
        return this.mText;
    }

    private void clearTexture() {
        if (this.mExpandedTitleTexture != null) {
            this.mExpandedTitleTexture.recycle();
            this.mExpandedTitleTexture = null;
        }
    }

    private static boolean isClose(float f, float f2) {
        float value = f;
        float targetValue = f2;
        float f3 = value;
        float f4 = targetValue;
        return Math.abs(value - targetValue) < 0.001f;
    }

    /* access modifiers changed from: 0000 */
    public ColorStateList getExpandedTextColor() {
        return this.mExpandedTextColor;
    }

    /* access modifiers changed from: 0000 */
    public ColorStateList getCollapsedTextColor() {
        return this.mCollapsedTextColor;
    }

    private static int blendColors(int i, int i2, float f) {
        int color1 = i;
        int color2 = i2;
        float ratio = f;
        int i3 = color1;
        int i4 = color2;
        float f2 = ratio;
        float inverseRatio = 1.0f - ratio;
        return Color.argb((int) ((((float) Color.alpha(color1)) * inverseRatio) + (((float) Color.alpha(color2)) * ratio)), (int) ((((float) Color.red(color1)) * inverseRatio) + (((float) Color.red(color2)) * ratio)), (int) ((((float) Color.green(color1)) * inverseRatio) + (((float) Color.green(color2)) * ratio)), (int) ((((float) Color.blue(color1)) * inverseRatio) + (((float) Color.blue(color2)) * ratio)));
    }

    private static float lerp(float f, float f2, float f3, Interpolator interpolator) {
        float startValue = f;
        float endValue = f2;
        float fraction = f3;
        Interpolator interpolator2 = interpolator;
        float f4 = startValue;
        float f5 = endValue;
        float fraction2 = fraction;
        Interpolator interpolator3 = interpolator2;
        if (interpolator2 != null) {
            fraction2 = interpolator2.getInterpolation(fraction);
        }
        return AnimationUtils.lerp(startValue, endValue, fraction2);
    }

    private static boolean rectEquals(Rect rect, int i, int i2, int i3, int i4) {
        Rect r = rect;
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        Rect rect2 = r;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        return r.left == left && r.top == top && r.right == right && r.bottom == bottom;
    }
}
