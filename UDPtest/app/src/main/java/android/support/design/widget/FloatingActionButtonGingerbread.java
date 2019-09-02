package android.support.design.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.C0001R;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

class FloatingActionButtonGingerbread extends FloatingActionButtonImpl {
    ShadowDrawableWrapper mShadowDrawable;
    private final StateListAnimator mStateListAnimator = new StateListAnimator();

    private class DisabledElevationAnimation extends ShadowAnimatorImpl {
        final /* synthetic */ FloatingActionButtonGingerbread this$0;

        DisabledElevationAnimation(FloatingActionButtonGingerbread floatingActionButtonGingerbread) {
            FloatingActionButtonGingerbread floatingActionButtonGingerbread2 = floatingActionButtonGingerbread;
            this.this$0 = floatingActionButtonGingerbread2;
            super(floatingActionButtonGingerbread2, null);
        }

        /* access modifiers changed from: protected */
        public float getTargetShadowSize() {
            return 0.0f;
        }
    }

    private class ElevateToTranslationZAnimation extends ShadowAnimatorImpl {
        final /* synthetic */ FloatingActionButtonGingerbread this$0;

        ElevateToTranslationZAnimation(FloatingActionButtonGingerbread floatingActionButtonGingerbread) {
            FloatingActionButtonGingerbread floatingActionButtonGingerbread2 = floatingActionButtonGingerbread;
            this.this$0 = floatingActionButtonGingerbread2;
            super(floatingActionButtonGingerbread2, null);
        }

        /* access modifiers changed from: protected */
        public float getTargetShadowSize() {
            return this.this$0.mElevation + this.this$0.mPressedTranslationZ;
        }
    }

    private class ResetElevationAnimation extends ShadowAnimatorImpl {
        final /* synthetic */ FloatingActionButtonGingerbread this$0;

        ResetElevationAnimation(FloatingActionButtonGingerbread floatingActionButtonGingerbread) {
            FloatingActionButtonGingerbread floatingActionButtonGingerbread2 = floatingActionButtonGingerbread;
            this.this$0 = floatingActionButtonGingerbread2;
            super(floatingActionButtonGingerbread2, null);
        }

        /* access modifiers changed from: protected */
        public float getTargetShadowSize() {
            return this.this$0.mElevation;
        }
    }

    private abstract class ShadowAnimatorImpl extends AnimatorListenerAdapter implements AnimatorUpdateListener {
        private float mShadowSizeEnd;
        private float mShadowSizeStart;
        private boolean mValidValues;
        final /* synthetic */ FloatingActionButtonGingerbread this$0;

        /* access modifiers changed from: protected */
        public abstract float getTargetShadowSize();

        private ShadowAnimatorImpl(FloatingActionButtonGingerbread floatingActionButtonGingerbread) {
            this.this$0 = floatingActionButtonGingerbread;
        }

        /* synthetic */ ShadowAnimatorImpl(FloatingActionButtonGingerbread floatingActionButtonGingerbread, C00371 r9) {
            FloatingActionButtonGingerbread x0 = floatingActionButtonGingerbread;
            FloatingActionButtonGingerbread floatingActionButtonGingerbread2 = x0;
            C00371 r5 = r9;
            this(x0);
        }

        public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
            ValueAnimatorCompat animator = valueAnimatorCompat;
            ValueAnimatorCompat valueAnimatorCompat2 = animator;
            if (!this.mValidValues) {
                this.mShadowSizeStart = this.this$0.mShadowDrawable.getShadowSize();
                this.mShadowSizeEnd = getTargetShadowSize();
                this.mValidValues = true;
            }
            this.this$0.mShadowDrawable.setShadowSize(this.mShadowSizeStart + ((this.mShadowSizeEnd - this.mShadowSizeStart) * animator.getAnimatedFraction()));
        }

        public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
            ValueAnimatorCompat valueAnimatorCompat2 = valueAnimatorCompat;
            this.this$0.mShadowDrawable.setShadowSize(this.mShadowSizeEnd);
            this.mValidValues = false;
        }
    }

    FloatingActionButtonGingerbread(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate, Creator creator) {
        VisibilityAwareImageButton view = visibilityAwareImageButton;
        ShadowViewDelegate shadowViewDelegate2 = shadowViewDelegate;
        Creator animatorCreator = creator;
        VisibilityAwareImageButton visibilityAwareImageButton2 = view;
        ShadowViewDelegate shadowViewDelegate3 = shadowViewDelegate2;
        Creator creator2 = animatorCreator;
        super(view, shadowViewDelegate2, animatorCreator);
        this.mStateListAnimator.addState(PRESSED_ENABLED_STATE_SET, createAnimator(new ElevateToTranslationZAnimation(this)));
        this.mStateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, createAnimator(new ElevateToTranslationZAnimation(this)));
        this.mStateListAnimator.addState(ENABLED_STATE_SET, createAnimator(new ResetElevationAnimation(this)));
        this.mStateListAnimator.addState(EMPTY_STATE_SET, createAnimator(new DisabledElevationAnimation(this)));
    }

    /* access modifiers changed from: 0000 */
    public void setBackgroundDrawable(ColorStateList colorStateList, Mode mode, int i, int i2) {
        Drawable[] layers;
        ColorStateList backgroundTint = colorStateList;
        Mode backgroundTintMode = mode;
        int rippleColor = i;
        int borderWidth = i2;
        ColorStateList colorStateList2 = backgroundTint;
        Mode mode2 = backgroundTintMode;
        int i3 = rippleColor;
        int i4 = borderWidth;
        this.mShapeDrawable = DrawableCompat.wrap(createShapeDrawable());
        DrawableCompat.setTintList(this.mShapeDrawable, backgroundTint);
        if (backgroundTintMode != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, backgroundTintMode);
        }
        this.mRippleDrawable = DrawableCompat.wrap(createShapeDrawable());
        DrawableCompat.setTintList(this.mRippleDrawable, createColorStateList(rippleColor));
        if (borderWidth <= 0) {
            this.mBorderDrawable = null;
            Drawable[] drawableArr = new Drawable[2];
            drawableArr[0] = this.mShapeDrawable;
            drawableArr[1] = this.mRippleDrawable;
            layers = drawableArr;
        } else {
            this.mBorderDrawable = createBorderDrawable(borderWidth, backgroundTint);
            Drawable[] drawableArr2 = new Drawable[3];
            drawableArr2[0] = this.mBorderDrawable;
            drawableArr2[1] = this.mShapeDrawable;
            drawableArr2[2] = this.mRippleDrawable;
            layers = drawableArr2;
        }
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        this.mContentBackground = layerDrawable;
        ShadowDrawableWrapper shadowDrawableWrapper = new ShadowDrawableWrapper(this.mView.getContext(), this.mContentBackground, this.mShadowViewDelegate.getRadius(), this.mElevation, this.mElevation + this.mPressedTranslationZ);
        this.mShadowDrawable = shadowDrawableWrapper;
        this.mShadowDrawable.setAddPaddingForCorners(false);
        this.mShadowViewDelegate.setBackgroundDrawable(this.mShadowDrawable);
    }

    /* access modifiers changed from: 0000 */
    public void setBackgroundTintList(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintList(this.mShapeDrawable, tint);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setBorderTint(tint);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setBackgroundTintMode(Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, tintMode);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setRippleColor(int i) {
        int rippleColor = i;
        int i2 = rippleColor;
        if (this.mRippleDrawable != null) {
            DrawableCompat.setTintList(this.mRippleDrawable, createColorStateList(rippleColor));
        }
    }

    /* access modifiers changed from: 0000 */
    public float getElevation() {
        return this.mElevation;
    }

    /* access modifiers changed from: 0000 */
    public void onElevationsChanged(float f, float f2) {
        float elevation = f;
        float f3 = elevation;
        float f4 = f2;
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setShadowSize(elevation, elevation + this.mPressedTranslationZ);
            updatePadding();
        }
    }

    /* access modifiers changed from: 0000 */
    public void onDrawableStateChanged(int[] iArr) {
        int[] state = iArr;
        int[] iArr2 = state;
        this.mStateListAnimator.setState(state);
    }

    /* access modifiers changed from: 0000 */
    public void jumpDrawableToCurrentState() {
        this.mStateListAnimator.jumpToCurrentState();
    }

    /* access modifiers changed from: 0000 */
    public void hide(@Nullable InternalVisibilityChangedListener internalVisibilityChangedListener, boolean z) {
        InternalVisibilityChangedListener listener = internalVisibilityChangedListener;
        InternalVisibilityChangedListener internalVisibilityChangedListener2 = listener;
        boolean fromUser = z;
        if (!isOrWillBeHidden()) {
            this.mAnimState = 1;
            Animation loadAnimation = AnimationUtils.loadAnimation(this.mView.getContext(), C0001R.anim.design_fab_out);
            Animation anim = loadAnimation;
            loadAnimation.setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
            anim.setDuration(200);
            final boolean z2 = fromUser;
            final InternalVisibilityChangedListener internalVisibilityChangedListener3 = listener;
            C00371 r0 = new AnimationListenerAdapter(this) {
                final /* synthetic */ FloatingActionButtonGingerbread this$0;

                {
                    FloatingActionButtonGingerbread this$02 = r9;
                    InternalVisibilityChangedListener internalVisibilityChangedListener = r11;
                    FloatingActionButtonGingerbread floatingActionButtonGingerbread = this$02;
                    boolean z = r10;
                    this.this$0 = this$02;
                }

                public void onAnimationEnd(Animation animation) {
                    Animation animation2 = animation;
                    this.this$0.mAnimState = 0;
                    this.this$0.mView.internalSetVisibility(!z2 ? 4 : 8, z2);
                    if (internalVisibilityChangedListener3 != null) {
                        internalVisibilityChangedListener3.onHidden();
                    }
                }
            };
            anim.setAnimationListener(r0);
            this.mView.startAnimation(anim);
        }
    }

    /* access modifiers changed from: 0000 */
    public void show(@Nullable InternalVisibilityChangedListener internalVisibilityChangedListener, boolean z) {
        InternalVisibilityChangedListener listener = internalVisibilityChangedListener;
        InternalVisibilityChangedListener internalVisibilityChangedListener2 = listener;
        boolean fromUser = z;
        if (!isOrWillBeShown()) {
            this.mAnimState = 2;
            this.mView.internalSetVisibility(0, fromUser);
            Animation loadAnimation = AnimationUtils.loadAnimation(this.mView.getContext(), C0001R.anim.design_fab_in);
            Animation anim = loadAnimation;
            loadAnimation.setDuration(200);
            anim.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
            final InternalVisibilityChangedListener internalVisibilityChangedListener3 = listener;
            C00382 r0 = new AnimationListenerAdapter(this) {
                final /* synthetic */ FloatingActionButtonGingerbread this$0;

                {
                    FloatingActionButtonGingerbread this$02 = r6;
                    InternalVisibilityChangedListener internalVisibilityChangedListener = r7;
                    FloatingActionButtonGingerbread floatingActionButtonGingerbread = this$02;
                    this.this$0 = this$02;
                }

                public void onAnimationEnd(Animation animation) {
                    Animation animation2 = animation;
                    this.this$0.mAnimState = 0;
                    if (internalVisibilityChangedListener3 != null) {
                        internalVisibilityChangedListener3.onShown();
                    }
                }
            };
            anim.setAnimationListener(r0);
            this.mView.startAnimation(anim);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onCompatShadowChanged() {
    }

    /* access modifiers changed from: 0000 */
    public void getPadding(Rect rect) {
        Rect rect2 = rect;
        Rect rect3 = rect2;
        boolean padding = this.mShadowDrawable.getPadding(rect2);
    }

    private ValueAnimatorCompat createAnimator(@NonNull ShadowAnimatorImpl shadowAnimatorImpl) {
        ShadowAnimatorImpl impl = shadowAnimatorImpl;
        ShadowAnimatorImpl shadowAnimatorImpl2 = impl;
        ValueAnimatorCompat createAnimator = this.mAnimatorCreator.createAnimator();
        ValueAnimatorCompat animator = createAnimator;
        createAnimator.setInterpolator(ANIM_INTERPOLATOR);
        animator.setDuration(100);
        animator.addListener(impl);
        animator.addUpdateListener(impl);
        animator.setFloatValues(0.0f, 1.0f);
        return animator;
    }

    private static ColorStateList createColorStateList(int i) {
        int selectedColor = i;
        int i2 = selectedColor;
        int[][] states = new int[3][];
        int[] colors = new int[3];
        states[0] = FOCUSED_ENABLED_STATE_SET;
        colors[0] = selectedColor;
        states[1] = PRESSED_ENABLED_STATE_SET;
        colors[1] = selectedColor;
        int i3 = 1 + 1;
        states[i3] = new int[0];
        colors[i3] = 0;
        int i4 = i3 + 1;
        return new ColorStateList(states, colors);
    }
}
