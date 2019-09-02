package android.support.p003v7.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.p003v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

/* renamed from: android.support.v7.widget.AppCompatCheckedTextView */
public class AppCompatCheckedTextView extends CheckedTextView {
    private static final int[] TINT_ATTRS;
    private AppCompatTextHelper mTextHelper;

    static {
        int[] iArr = new int[1];
        iArr[0] = 16843016;
        TINT_ATTRS = iArr;
    }

    public AppCompatCheckedTextView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public AppCompatCheckedTextView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 16843720);
    }

    public AppCompatCheckedTextView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(TintContextWrapper.wrap(context2), attrs, defStyleAttr);
        this.mTextHelper = AppCompatTextHelper.create(this);
        this.mTextHelper.loadFromAttributes(attrs, defStyleAttr);
        this.mTextHelper.applyCompoundDrawablesTints();
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, TINT_ATTRS, defStyleAttr, 0);
        setCheckMarkDrawable(a.getDrawable(0));
        a.recycle();
    }

    public void setCheckMarkDrawable(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setCheckMarkDrawable(AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setTextAppearance(Context context, int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        super.setTextAppearance(context2, resId);
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance(context2, resId);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mTextHelper != null) {
            this.mTextHelper.applyCompoundDrawablesTints();
        }
    }
}
