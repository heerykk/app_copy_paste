package android.support.p003v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.text.AllCapsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v7.widget.AppCompatTextHelper */
class AppCompatTextHelper {
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableTopTint;
    final TextView mView;

    static AppCompatTextHelper create(TextView textView) {
        TextView textView2 = textView;
        TextView textView3 = textView2;
        if (VERSION.SDK_INT < 17) {
            return new AppCompatTextHelper(textView2);
        }
        return new AppCompatTextHelperV17(textView2);
    }

    AppCompatTextHelper(TextView textView) {
        TextView view = textView;
        TextView textView2 = view;
        this.mView = view;
    }

    /* access modifiers changed from: 0000 */
    public void loadFromAttributes(AttributeSet attributeSet, int i) {
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        Context context = this.mView.getContext();
        AppCompatDrawableManager drawableManager = AppCompatDrawableManager.get();
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attrs, C0268R.styleable.AppCompatTextHelper, defStyleAttr, 0);
        TintTypedArray a = obtainStyledAttributes;
        int ap = obtainStyledAttributes.getResourceId(C0268R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (a.hasValue(C0268R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.mDrawableLeftTint = createTintInfo(context, drawableManager, a.getResourceId(C0268R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (a.hasValue(C0268R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.mDrawableTopTint = createTintInfo(context, drawableManager, a.getResourceId(C0268R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (a.hasValue(C0268R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.mDrawableRightTint = createTintInfo(context, drawableManager, a.getResourceId(C0268R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (a.hasValue(C0268R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.mDrawableBottomTint = createTintInfo(context, drawableManager, a.getResourceId(C0268R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        a.recycle();
        boolean hasPwdTm = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        boolean allCaps = false;
        boolean allCapsSet = false;
        ColorStateList textColor = null;
        ColorStateList textColorHint = null;
        if (ap != -1) {
            TintTypedArray obtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(context, ap, C0268R.styleable.TextAppearance);
            TintTypedArray a2 = obtainStyledAttributes2;
            if (!hasPwdTm && obtainStyledAttributes2.hasValue(C0268R.styleable.TextAppearance_textAllCaps)) {
                allCapsSet = true;
                allCaps = a2.getBoolean(C0268R.styleable.TextAppearance_textAllCaps, false);
            }
            if (VERSION.SDK_INT < 23) {
                if (a2.hasValue(C0268R.styleable.TextAppearance_android_textColor)) {
                    textColor = a2.getColorStateList(C0268R.styleable.TextAppearance_android_textColor);
                }
                if (a2.hasValue(C0268R.styleable.TextAppearance_android_textColorHint)) {
                    textColorHint = a2.getColorStateList(C0268R.styleable.TextAppearance_android_textColorHint);
                }
            }
            a2.recycle();
        }
        TintTypedArray obtainStyledAttributes3 = TintTypedArray.obtainStyledAttributes(context, attrs, C0268R.styleable.TextAppearance, defStyleAttr, 0);
        TintTypedArray a3 = obtainStyledAttributes3;
        if (!hasPwdTm && obtainStyledAttributes3.hasValue(C0268R.styleable.TextAppearance_textAllCaps)) {
            allCapsSet = true;
            allCaps = a3.getBoolean(C0268R.styleable.TextAppearance_textAllCaps, false);
        }
        if (VERSION.SDK_INT < 23) {
            if (a3.hasValue(C0268R.styleable.TextAppearance_android_textColor)) {
                textColor = a3.getColorStateList(C0268R.styleable.TextAppearance_android_textColor);
            }
            if (a3.hasValue(C0268R.styleable.TextAppearance_android_textColorHint)) {
                textColorHint = a3.getColorStateList(C0268R.styleable.TextAppearance_android_textColorHint);
            }
        }
        a3.recycle();
        if (textColor != null) {
            this.mView.setTextColor(textColor);
        }
        if (textColorHint != null) {
            this.mView.setHintTextColor(textColorHint);
        }
        if (!hasPwdTm && allCapsSet) {
            setAllCaps(allCaps);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onSetTextAppearance(Context context, int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, resId, C0268R.styleable.TextAppearance);
        TintTypedArray a = obtainStyledAttributes;
        if (obtainStyledAttributes.hasValue(C0268R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(a.getBoolean(C0268R.styleable.TextAppearance_textAllCaps, false));
        }
        if (VERSION.SDK_INT < 23 && a.hasValue(C0268R.styleable.TextAppearance_android_textColor)) {
            ColorStateList colorStateList = a.getColorStateList(C0268R.styleable.TextAppearance_android_textColor);
            ColorStateList textColor = colorStateList;
            if (colorStateList != null) {
                this.mView.setTextColor(textColor);
            }
        }
        a.recycle();
    }

    /* access modifiers changed from: 0000 */
    public void setAllCaps(boolean z) {
        this.mView.setTransformationMethod(!z ? null : new AllCapsTransformationMethod(this.mView.getContext()));
    }

    /* access modifiers changed from: 0000 */
    public void applyCompoundDrawablesTints() {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void applyCompoundDrawableTint(Drawable drawable, TintInfo tintInfo) {
        Drawable drawable2 = drawable;
        TintInfo info = tintInfo;
        Drawable drawable3 = drawable2;
        TintInfo tintInfo2 = info;
        if (drawable2 != null && info != null) {
            AppCompatDrawableManager.tintDrawable(drawable2, info, this.mView.getDrawableState());
        }
    }

    protected static TintInfo createTintInfo(Context context, AppCompatDrawableManager appCompatDrawableManager, int i) {
        Context context2 = context;
        AppCompatDrawableManager drawableManager = appCompatDrawableManager;
        int drawableId = i;
        Context context3 = context2;
        AppCompatDrawableManager appCompatDrawableManager2 = drawableManager;
        int i2 = drawableId;
        ColorStateList tintList = drawableManager.getTintList(context2, drawableId);
        ColorStateList tintList2 = tintList;
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        TintInfo tintInfo2 = tintInfo;
        tintInfo.mHasTintList = true;
        tintInfo2.mTintList = tintList2;
        return tintInfo2;
    }
}
