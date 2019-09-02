package android.support.p003v7.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.widget.CompoundButtonCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/* renamed from: android.support.v7.widget.AppCompatCompoundButtonHelper */
class AppCompatCompoundButtonHelper {
    private ColorStateList mButtonTintList = null;
    private Mode mButtonTintMode = null;
    private boolean mHasButtonTint = false;
    private boolean mHasButtonTintMode = false;
    private boolean mSkipNextApply;
    private final CompoundButton mView;

    /* renamed from: android.support.v7.widget.AppCompatCompoundButtonHelper$DirectSetButtonDrawableInterface */
    interface DirectSetButtonDrawableInterface {
        void setButtonDrawable(Drawable drawable);
    }

    AppCompatCompoundButtonHelper(CompoundButton compoundButton) {
        CompoundButton view = compoundButton;
        CompoundButton compoundButton2 = view;
        this.mView = view;
    }

    /* access modifiers changed from: 0000 */
    public void loadFromAttributes(AttributeSet attributeSet, int i) {
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        TypedArray obtainStyledAttributes = this.mView.getContext().obtainStyledAttributes(attrs, C0268R.styleable.CompoundButton, defStyleAttr, 0);
        TypedArray a = obtainStyledAttributes;
        try {
            if (obtainStyledAttributes.hasValue(C0268R.styleable.CompoundButton_android_button)) {
                int resourceId = a.getResourceId(C0268R.styleable.CompoundButton_android_button, 0);
                int resourceId2 = resourceId;
                if (resourceId != 0) {
                    this.mView.setButtonDrawable(AppCompatResources.getDrawable(this.mView.getContext(), resourceId2));
                }
            }
            if (a.hasValue(C0268R.styleable.CompoundButton_buttonTint)) {
                CompoundButtonCompat.setButtonTintList(this.mView, a.getColorStateList(C0268R.styleable.CompoundButton_buttonTint));
            }
            if (a.hasValue(C0268R.styleable.CompoundButton_buttonTintMode)) {
                CompoundButtonCompat.setButtonTintMode(this.mView, DrawableUtils.parseTintMode(a.getInt(C0268R.styleable.CompoundButton_buttonTintMode, -1), null));
            }
        } finally {
            a.recycle();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setSupportButtonTintList(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        this.mButtonTintList = tint;
        this.mHasButtonTint = true;
        applyButtonTint();
    }

    /* access modifiers changed from: 0000 */
    public ColorStateList getSupportButtonTintList() {
        return this.mButtonTintList;
    }

    /* access modifiers changed from: 0000 */
    public void setSupportButtonTintMode(@Nullable Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        this.mButtonTintMode = tintMode;
        this.mHasButtonTintMode = true;
        applyButtonTint();
    }

    /* access modifiers changed from: 0000 */
    public Mode getSupportButtonTintMode() {
        return this.mButtonTintMode;
    }

    /* access modifiers changed from: 0000 */
    public void onSetButtonDrawable() {
        if (!this.mSkipNextApply) {
            this.mSkipNextApply = true;
            applyButtonTint();
            return;
        }
        this.mSkipNextApply = false;
    }

    /* access modifiers changed from: 0000 */
    public void applyButtonTint() {
        Drawable buttonDrawable = CompoundButtonCompat.getButtonDrawable(this.mView);
        Drawable buttonDrawable2 = buttonDrawable;
        if (buttonDrawable != null) {
            if (this.mHasButtonTint || this.mHasButtonTintMode) {
                Drawable wrap = DrawableCompat.wrap(buttonDrawable2);
                Drawable buttonDrawable3 = wrap;
                Drawable buttonDrawable4 = wrap.mutate();
                if (this.mHasButtonTint) {
                    DrawableCompat.setTintList(buttonDrawable4, this.mButtonTintList);
                }
                if (this.mHasButtonTintMode) {
                    DrawableCompat.setTintMode(buttonDrawable4, this.mButtonTintMode);
                }
                if (buttonDrawable4.isStateful()) {
                    boolean state = buttonDrawable4.setState(this.mView.getDrawableState());
                }
                this.mView.setButtonDrawable(buttonDrawable4);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int getCompoundPaddingLeft(int i) {
        int superValue = i;
        int superValue2 = superValue;
        if (VERSION.SDK_INT < 17) {
            Drawable buttonDrawable = CompoundButtonCompat.getButtonDrawable(this.mView);
            Drawable buttonDrawable2 = buttonDrawable;
            if (buttonDrawable != null) {
                superValue2 = superValue + buttonDrawable2.getIntrinsicWidth();
            }
        }
        return superValue2;
    }
}
