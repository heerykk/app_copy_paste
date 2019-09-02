package android.support.p003v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.util.AttributeSet;
import android.view.View;

/* renamed from: android.support.v7.widget.AppCompatBackgroundHelper */
class AppCompatBackgroundHelper {
    private int mBackgroundResId = -1;
    private TintInfo mBackgroundTint;
    private final AppCompatDrawableManager mDrawableManager;
    private TintInfo mInternalBackgroundTint;
    private TintInfo mTmpInfo;
    private final View mView;

    AppCompatBackgroundHelper(View view) {
        View view2 = view;
        View view3 = view2;
        this.mView = view2;
        this.mDrawableManager = AppCompatDrawableManager.get();
    }

    /* access modifiers changed from: 0000 */
    public void loadFromAttributes(AttributeSet attributeSet, int i) {
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attrs, C0268R.styleable.ViewBackgroundHelper, defStyleAttr, 0);
        TintTypedArray a = obtainStyledAttributes;
        try {
            if (obtainStyledAttributes.hasValue(C0268R.styleable.ViewBackgroundHelper_android_background)) {
                this.mBackgroundResId = a.getResourceId(C0268R.styleable.ViewBackgroundHelper_android_background, -1);
                ColorStateList tintList = this.mDrawableManager.getTintList(this.mView.getContext(), this.mBackgroundResId);
                ColorStateList tint = tintList;
                if (tintList != null) {
                    setInternalBackgroundTint(tint);
                }
            }
            if (a.hasValue(C0268R.styleable.ViewBackgroundHelper_backgroundTint)) {
                ViewCompat.setBackgroundTintList(this.mView, a.getColorStateList(C0268R.styleable.ViewBackgroundHelper_backgroundTint));
            }
            if (a.hasValue(C0268R.styleable.ViewBackgroundHelper_backgroundTintMode)) {
                ViewCompat.setBackgroundTintMode(this.mView, DrawableUtils.parseTintMode(a.getInt(C0268R.styleable.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
        } finally {
            a.recycle();
        }
    }

    /* access modifiers changed from: 0000 */
    public void onSetBackgroundResource(int i) {
        int resId = i;
        int i2 = resId;
        this.mBackgroundResId = resId;
        setInternalBackgroundTint(this.mDrawableManager == null ? null : this.mDrawableManager.getTintList(this.mView.getContext(), resId));
        applySupportBackgroundTint();
    }

    /* access modifiers changed from: 0000 */
    public void onSetBackgroundDrawable(Drawable drawable) {
        Drawable drawable2 = drawable;
        this.mBackgroundResId = -1;
        setInternalBackgroundTint(null);
        applySupportBackgroundTint();
    }

    /* access modifiers changed from: 0000 */
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintList = tint;
        this.mBackgroundTint.mHasTintList = true;
        applySupportBackgroundTint();
    }

    /* access modifiers changed from: 0000 */
    public ColorStateList getSupportBackgroundTintList() {
        return this.mBackgroundTint == null ? null : this.mBackgroundTint.mTintList;
    }

    /* access modifiers changed from: 0000 */
    public void setSupportBackgroundTintMode(Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintMode = tintMode;
        this.mBackgroundTint.mHasTintMode = true;
        applySupportBackgroundTint();
    }

    /* access modifiers changed from: 0000 */
    public Mode getSupportBackgroundTintMode() {
        return this.mBackgroundTint == null ? null : this.mBackgroundTint.mTintMode;
    }

    /* access modifiers changed from: 0000 */
    public void applySupportBackgroundTint() {
        Drawable background = this.mView.getBackground();
        Drawable background2 = background;
        if (background == null || (shouldApplyFrameworkTintUsingColorFilter() && applyFrameworkTintUsingColorFilter(background2))) {
            return;
        }
        if (this.mBackgroundTint != null) {
            AppCompatDrawableManager.tintDrawable(background2, this.mBackgroundTint, this.mView.getDrawableState());
        } else if (this.mInternalBackgroundTint != null) {
            AppCompatDrawableManager.tintDrawable(background2, this.mInternalBackgroundTint, this.mView.getDrawableState());
        }
    }

    /* access modifiers changed from: 0000 */
    public void setInternalBackgroundTint(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        if (tint == null) {
            this.mInternalBackgroundTint = null;
        } else {
            if (this.mInternalBackgroundTint == null) {
                this.mInternalBackgroundTint = new TintInfo();
            }
            this.mInternalBackgroundTint.mTintList = tint;
            this.mInternalBackgroundTint.mHasTintList = true;
        }
        applySupportBackgroundTint();
    }

    private boolean shouldApplyFrameworkTintUsingColorFilter() {
        boolean z;
        int i = VERSION.SDK_INT;
        int sdk = i;
        if (i < 21) {
            return false;
        }
        if (sdk == 21) {
            return true;
        }
        if (this.mInternalBackgroundTint == null) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    private boolean applyFrameworkTintUsingColorFilter(@NonNull Drawable drawable) {
        Drawable background = drawable;
        Drawable drawable2 = background;
        if (this.mTmpInfo == null) {
            this.mTmpInfo = new TintInfo();
        }
        TintInfo tintInfo = this.mTmpInfo;
        TintInfo info = tintInfo;
        tintInfo.clear();
        ColorStateList backgroundTintList = ViewCompat.getBackgroundTintList(this.mView);
        ColorStateList tintList = backgroundTintList;
        if (backgroundTintList != null) {
            info.mHasTintList = true;
            info.mTintList = tintList;
        }
        Mode backgroundTintMode = ViewCompat.getBackgroundTintMode(this.mView);
        Mode mode = backgroundTintMode;
        if (backgroundTintMode != null) {
            info.mHasTintMode = true;
            info.mTintMode = mode;
        }
        if (!info.mHasTintList && !info.mHasTintMode) {
            return false;
        }
        AppCompatDrawableManager.tintDrawable(background, info, this.mView.getDrawableState());
        return true;
    }
}
