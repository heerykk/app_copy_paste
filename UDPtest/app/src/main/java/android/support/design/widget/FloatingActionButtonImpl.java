package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.design.C0001R;
import android.support.p000v4.content.ContextCompat;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Interpolator;

abstract class FloatingActionButtonImpl {
    static final Interpolator ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
    static final int ANIM_STATE_HIDING = 1;
    static final int ANIM_STATE_NONE = 0;
    static final int ANIM_STATE_SHOWING = 2;
    static final int[] EMPTY_STATE_SET = new int[0];
    static final int[] ENABLED_STATE_SET;
    static final int[] FOCUSED_ENABLED_STATE_SET;
    static final long PRESSED_ANIM_DELAY = 100;
    static final long PRESSED_ANIM_DURATION = 100;
    static final int[] PRESSED_ENABLED_STATE_SET;
    static final int SHOW_HIDE_ANIM_DURATION = 200;
    int mAnimState = 0;
    final Creator mAnimatorCreator;
    CircularBorderDrawable mBorderDrawable;
    Drawable mContentBackground;
    float mElevation;
    private OnPreDrawListener mPreDrawListener;
    float mPressedTranslationZ;
    Drawable mRippleDrawable;
    final ShadowViewDelegate mShadowViewDelegate;
    Drawable mShapeDrawable;
    private final Rect mTmpRect = new Rect();
    final VisibilityAwareImageButton mView;

    interface InternalVisibilityChangedListener {
        void onHidden();

        void onShown();
    }

    /* access modifiers changed from: 0000 */
    public abstract float getElevation();

    /* access modifiers changed from: 0000 */
    public abstract void getPadding(Rect rect);

    /* access modifiers changed from: 0000 */
    public abstract void hide(@Nullable InternalVisibilityChangedListener internalVisibilityChangedListener, boolean z);

    /* access modifiers changed from: 0000 */
    public abstract void jumpDrawableToCurrentState();

    /* access modifiers changed from: 0000 */
    public abstract void onCompatShadowChanged();

    /* access modifiers changed from: 0000 */
    public abstract void onDrawableStateChanged(int[] iArr);

    /* access modifiers changed from: 0000 */
    public abstract void onElevationsChanged(float f, float f2);

    /* access modifiers changed from: 0000 */
    public abstract void setBackgroundDrawable(ColorStateList colorStateList, Mode mode, int i, int i2);

    /* access modifiers changed from: 0000 */
    public abstract void setBackgroundTintList(ColorStateList colorStateList);

    /* access modifiers changed from: 0000 */
    public abstract void setBackgroundTintMode(Mode mode);

    /* access modifiers changed from: 0000 */
    public abstract void setRippleColor(int i);

    /* access modifiers changed from: 0000 */
    public abstract void show(@Nullable InternalVisibilityChangedListener internalVisibilityChangedListener, boolean z);

    static {
        int[] iArr = new int[2];
        iArr[0] = 16842919;
        iArr[1] = 16842910;
        PRESSED_ENABLED_STATE_SET = iArr;
        int[] iArr2 = new int[2];
        iArr2[0] = 16842908;
        iArr2[1] = 16842910;
        FOCUSED_ENABLED_STATE_SET = iArr2;
        int[] iArr3 = new int[1];
        iArr3[0] = 16842910;
        ENABLED_STATE_SET = iArr3;
    }

    FloatingActionButtonImpl(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate, Creator creator) {
        VisibilityAwareImageButton view = visibilityAwareImageButton;
        ShadowViewDelegate shadowViewDelegate2 = shadowViewDelegate;
        Creator animatorCreator = creator;
        VisibilityAwareImageButton visibilityAwareImageButton2 = view;
        ShadowViewDelegate shadowViewDelegate3 = shadowViewDelegate2;
        Creator creator2 = animatorCreator;
        this.mView = view;
        this.mShadowViewDelegate = shadowViewDelegate2;
        this.mAnimatorCreator = animatorCreator;
    }

    /* access modifiers changed from: 0000 */
    public final void setElevation(float f) {
        float elevation = f;
        float f2 = elevation;
        if (this.mElevation != elevation) {
            this.mElevation = elevation;
            onElevationsChanged(elevation, this.mPressedTranslationZ);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void setPressedTranslationZ(float f) {
        float translationZ = f;
        float f2 = translationZ;
        if (this.mPressedTranslationZ != translationZ) {
            this.mPressedTranslationZ = translationZ;
            onElevationsChanged(this.mElevation, translationZ);
        }
    }

    /* access modifiers changed from: 0000 */
    public final Drawable getContentBackground() {
        return this.mContentBackground;
    }

    /* access modifiers changed from: 0000 */
    public final void updatePadding() {
        Rect rect = this.mTmpRect;
        getPadding(rect);
        onPaddingUpdated(rect);
        this.mShadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* access modifiers changed from: 0000 */
    public void onPaddingUpdated(Rect rect) {
        Rect rect2 = rect;
    }

    /* access modifiers changed from: 0000 */
    public void onAttachedToWindow() {
        if (requirePreDrawListener()) {
            ensurePreDrawListener();
            this.mView.getViewTreeObserver().addOnPreDrawListener(this.mPreDrawListener);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onDetachedFromWindow() {
        if (this.mPreDrawListener != null) {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mPreDrawListener);
            this.mPreDrawListener = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean requirePreDrawListener() {
        return false;
    }

    /* access modifiers changed from: 0000 */
    public CircularBorderDrawable createBorderDrawable(int i, ColorStateList colorStateList) {
        int borderWidth = i;
        ColorStateList backgroundTint = colorStateList;
        int i2 = borderWidth;
        ColorStateList colorStateList2 = backgroundTint;
        Context context = this.mView.getContext();
        CircularBorderDrawable newCircularDrawable = newCircularDrawable();
        CircularBorderDrawable borderDrawable = newCircularDrawable;
        newCircularDrawable.setGradientColors(ContextCompat.getColor(context, C0001R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, C0001R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, C0001R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, C0001R.color.design_fab_stroke_end_outer_color));
        borderDrawable.setBorderWidth((float) borderWidth);
        borderDrawable.setBorderTint(backgroundTint);
        return borderDrawable;
    }

    /* access modifiers changed from: 0000 */
    public CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawable();
    }

    /* access modifiers changed from: 0000 */
    public void onPreDraw() {
    }

    private void ensurePreDrawListener() {
        if (this.mPreDrawListener == null) {
            this.mPreDrawListener = new OnPreDrawListener(this) {
                final /* synthetic */ FloatingActionButtonImpl this$0;

                {
                    FloatingActionButtonImpl this$02 = r5;
                    FloatingActionButtonImpl floatingActionButtonImpl = this$02;
                    this.this$0 = this$02;
                }

                public boolean onPreDraw() {
                    this.this$0.onPreDraw();
                    return true;
                }
            };
        }
    }

    /* access modifiers changed from: 0000 */
    public GradientDrawable createShapeDrawable() {
        GradientDrawable newGradientDrawableForShape = newGradientDrawableForShape();
        GradientDrawable d = newGradientDrawableForShape;
        newGradientDrawableForShape.setShape(1);
        d.setColor(-1);
        return d;
    }

    /* access modifiers changed from: 0000 */
    public GradientDrawable newGradientDrawableForShape() {
        return new GradientDrawable();
    }

    /* access modifiers changed from: 0000 */
    public boolean isOrWillBeShown() {
        if (this.mView.getVisibility() == 0) {
            return this.mAnimState != 1;
        }
        return this.mAnimState == 2;
    }

    /* access modifiers changed from: 0000 */
    public boolean isOrWillBeHidden() {
        if (this.mView.getVisibility() != 0) {
            return this.mAnimState != 2;
        }
        return this.mAnimState == 1;
    }
}
