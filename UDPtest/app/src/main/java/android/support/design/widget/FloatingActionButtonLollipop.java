package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.util.Property;
import android.view.View;

@TargetApi(21)
@RequiresApi(21)
class FloatingActionButtonLollipop extends FloatingActionButtonIcs {
    private InsetDrawable mInsetDrawable;

    static class AlwaysStatefulGradientDrawable extends GradientDrawable {
        AlwaysStatefulGradientDrawable() {
        }

        public boolean isStateful() {
            return true;
        }
    }

    FloatingActionButtonLollipop(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate, Creator creator) {
        VisibilityAwareImageButton view = visibilityAwareImageButton;
        ShadowViewDelegate shadowViewDelegate2 = shadowViewDelegate;
        Creator animatorCreator = creator;
        VisibilityAwareImageButton visibilityAwareImageButton2 = view;
        ShadowViewDelegate shadowViewDelegate3 = shadowViewDelegate2;
        Creator creator2 = animatorCreator;
        super(view, shadowViewDelegate2, animatorCreator);
    }

    /* access modifiers changed from: 0000 */
    public void setBackgroundDrawable(ColorStateList colorStateList, Mode mode, int i, int i2) {
        Drawable rippleContent;
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
        if (borderWidth <= 0) {
            this.mBorderDrawable = null;
            rippleContent = this.mShapeDrawable;
        } else {
            this.mBorderDrawable = createBorderDrawable(borderWidth, backgroundTint);
            Drawable[] drawableArr = new Drawable[2];
            drawableArr[0] = this.mBorderDrawable;
            drawableArr[1] = this.mShapeDrawable;
            rippleContent = new LayerDrawable(drawableArr);
        }
        this.mRippleDrawable = new RippleDrawable(ColorStateList.valueOf(rippleColor), rippleContent, null);
        this.mContentBackground = this.mRippleDrawable;
        this.mShadowViewDelegate.setBackgroundDrawable(this.mRippleDrawable);
    }

    /* access modifiers changed from: 0000 */
    public void setRippleColor(int i) {
        int rippleColor = i;
        int i2 = rippleColor;
        if (!(this.mRippleDrawable instanceof RippleDrawable)) {
            super.setRippleColor(rippleColor);
        } else {
            ((RippleDrawable) this.mRippleDrawable).setColor(ColorStateList.valueOf(rippleColor));
        }
    }

    /* access modifiers changed from: 0000 */
    public void onElevationsChanged(float f, float f2) {
        float elevation = f;
        float pressedTranslationZ = f2;
        float f3 = elevation;
        float f4 = pressedTranslationZ;
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i != 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            AnimatorSet animatorSet = new AnimatorSet();
            AnimatorSet set = animatorSet;
            Builder with = animatorSet.play(ObjectAnimator.ofFloat(this.mView, "elevation", new float[]{elevation}).setDuration(0)).with(ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[]{pressedTranslationZ}).setDuration(100));
            set.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, set);
            AnimatorSet animatorSet2 = new AnimatorSet();
            AnimatorSet set2 = animatorSet2;
            Builder with2 = animatorSet2.play(ObjectAnimator.ofFloat(this.mView, "elevation", new float[]{elevation}).setDuration(0)).with(ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[]{pressedTranslationZ}).setDuration(100));
            set2.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, set2);
            AnimatorSet animatorSet3 = new AnimatorSet();
            AnimatorSet set3 = animatorSet3;
            AnimatorSet animatorSet4 = animatorSet3;
            Animator[] animatorArr = new Animator[3];
            Animator[] animatorArr2 = animatorArr;
            animatorArr2[0] = ObjectAnimator.ofFloat(this.mView, "elevation", new float[]{elevation}).setDuration(0);
            VisibilityAwareImageButton visibilityAwareImageButton = this.mView;
            Property property = View.TRANSLATION_Z;
            float[] fArr = new float[1];
            fArr[0] = this.mView.getTranslationZ();
            animatorArr[1] = ObjectAnimator.ofFloat(visibilityAwareImageButton, property, fArr).setDuration(100);
            Animator[] animatorArr3 = animatorArr;
            animatorArr3[2] = ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(100);
            animatorSet4.playSequentially(animatorArr);
            set3.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(ENABLED_STATE_SET, set3);
            AnimatorSet animatorSet5 = new AnimatorSet();
            AnimatorSet set4 = animatorSet5;
            Builder with3 = animatorSet5.play(ObjectAnimator.ofFloat(this.mView, "elevation", new float[]{0.0f}).setDuration(0)).with(ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(0));
            set4.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(EMPTY_STATE_SET, set4);
            this.mView.setStateListAnimator(stateListAnimator);
        } else if (!this.mView.isEnabled()) {
            this.mView.setElevation(0.0f);
            this.mView.setTranslationZ(0.0f);
        } else {
            this.mView.setElevation(elevation);
            if (!this.mView.isFocused() && !this.mView.isPressed()) {
                this.mView.setTranslationZ(0.0f);
            } else {
                this.mView.setTranslationZ(pressedTranslationZ);
            }
        }
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            updatePadding();
        }
    }

    public float getElevation() {
        return this.mView.getElevation();
    }

    /* access modifiers changed from: 0000 */
    public void onCompatShadowChanged() {
        updatePadding();
    }

    /* access modifiers changed from: 0000 */
    public void onPaddingUpdated(Rect rect) {
        Rect padding = rect;
        Rect rect2 = padding;
        if (!this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            this.mShadowViewDelegate.setBackgroundDrawable(this.mRippleDrawable);
            return;
        }
        InsetDrawable insetDrawable = new InsetDrawable(this.mRippleDrawable, padding.left, padding.top, padding.right, padding.bottom);
        this.mInsetDrawable = insetDrawable;
        this.mShadowViewDelegate.setBackgroundDrawable(this.mInsetDrawable);
    }

    /* access modifiers changed from: 0000 */
    public void onDrawableStateChanged(int[] iArr) {
        int[] iArr2 = iArr;
    }

    /* access modifiers changed from: 0000 */
    public void jumpDrawableToCurrentState() {
    }

    /* access modifiers changed from: 0000 */
    public boolean requirePreDrawListener() {
        return false;
    }

    /* access modifiers changed from: 0000 */
    public CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawableLollipop();
    }

    /* access modifiers changed from: 0000 */
    public GradientDrawable newGradientDrawableForShape() {
        return new AlwaysStatefulGradientDrawable();
    }

    /* access modifiers changed from: 0000 */
    public void getPadding(Rect rect) {
        Rect rect2 = rect;
        Rect rect3 = rect2;
        if (!this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            rect2.set(0, 0, 0, 0);
            return;
        }
        float radius = this.mShadowViewDelegate.getRadius();
        float elevation = getElevation() + this.mPressedTranslationZ;
        int hPadding = (int) Math.ceil((double) ShadowDrawableWrapper.calculateHorizontalPadding(elevation, radius, false));
        int ceil = (int) Math.ceil((double) ShadowDrawableWrapper.calculateVerticalPadding(elevation, radius, false));
        int i = ceil;
        rect2.set(hPadding, ceil, hPadding, ceil);
    }
}
