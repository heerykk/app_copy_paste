package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.widget.TintableCompoundButton;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.RadioButton;

/* renamed from: android.support.v7.widget.AppCompatRadioButton */
public class AppCompatRadioButton extends RadioButton implements TintableCompoundButton {
    private AppCompatCompoundButtonHelper mCompoundButtonHelper;

    public AppCompatRadioButton(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public AppCompatRadioButton(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, C0268R.attr.radioButtonStyle);
    }

    public AppCompatRadioButton(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(TintContextWrapper.wrap(context2), attrs, defStyleAttr);
        this.mCompoundButtonHelper = new AppCompatCompoundButtonHelper(this);
        this.mCompoundButtonHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    public void setButtonDrawable(Drawable drawable) {
        Drawable buttonDrawable = drawable;
        Drawable drawable2 = buttonDrawable;
        super.setButtonDrawable(buttonDrawable);
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.onSetButtonDrawable();
        }
    }

    public void setButtonDrawable(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setButtonDrawable(AppCompatResources.getDrawable(getContext(), resId));
    }

    public int getCompoundPaddingLeft() {
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        int i = compoundPaddingLeft;
        return this.mCompoundButtonHelper == null ? compoundPaddingLeft : this.mCompoundButtonHelper.getCompoundPaddingLeft(compoundPaddingLeft);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setSupportButtonTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.setSupportButtonTintList(tint);
        }
    }

    @Nullable
    @RestrictTo({Scope.LIBRARY_GROUP})
    public ColorStateList getSupportButtonTintList() {
        return this.mCompoundButtonHelper == null ? null : this.mCompoundButtonHelper.getSupportButtonTintList();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setSupportButtonTintMode(@Nullable Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.setSupportButtonTintMode(tintMode);
        }
    }

    @Nullable
    @RestrictTo({Scope.LIBRARY_GROUP})
    public Mode getSupportButtonTintMode() {
        return this.mCompoundButtonHelper == null ? null : this.mCompoundButtonHelper.getSupportButtonTintMode();
    }
}
