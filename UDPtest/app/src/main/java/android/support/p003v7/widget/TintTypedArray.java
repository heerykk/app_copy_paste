package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.TypedValue;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.TintTypedArray */
public class TintTypedArray {
    private final Context mContext;
    private TypedValue mTypedValue;
    private final TypedArray mWrapped;

    public static TintTypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] iArr) {
        Context context2 = context;
        AttributeSet set = attributeSet;
        int[] attrs = iArr;
        Context context3 = context2;
        AttributeSet attributeSet2 = set;
        int[] iArr2 = attrs;
        return new TintTypedArray(context2, context2.obtainStyledAttributes(set, attrs));
    }

    public static TintTypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2) {
        Context context2 = context;
        AttributeSet set = attributeSet;
        int[] attrs = iArr;
        int defStyleAttr = i;
        int defStyleRes = i2;
        Context context3 = context2;
        AttributeSet attributeSet2 = set;
        int[] iArr2 = attrs;
        int i3 = defStyleAttr;
        int i4 = defStyleRes;
        return new TintTypedArray(context2, context2.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes));
    }

    public static TintTypedArray obtainStyledAttributes(Context context, int i, int[] iArr) {
        Context context2 = context;
        int resid = i;
        int[] attrs = iArr;
        Context context3 = context2;
        int i2 = resid;
        int[] iArr2 = attrs;
        return new TintTypedArray(context2, context2.obtainStyledAttributes(resid, attrs));
    }

    private TintTypedArray(Context context, TypedArray typedArray) {
        Context context2 = context;
        TypedArray array = typedArray;
        Context context3 = context2;
        TypedArray typedArray2 = array;
        this.mContext = context2;
        this.mWrapped = array;
    }

    public Drawable getDrawable(int i) {
        int index = i;
        int i2 = index;
        if (this.mWrapped.hasValue(index)) {
            int resourceId = this.mWrapped.getResourceId(index, 0);
            int resourceId2 = resourceId;
            if (resourceId != 0) {
                return AppCompatResources.getDrawable(this.mContext, resourceId2);
            }
        }
        return this.mWrapped.getDrawable(index);
    }

    public Drawable getDrawableIfKnown(int i) {
        int index = i;
        int i2 = index;
        if (this.mWrapped.hasValue(index)) {
            int resourceId = this.mWrapped.getResourceId(index, 0);
            int resourceId2 = resourceId;
            if (resourceId != 0) {
                return AppCompatDrawableManager.get().getDrawable(this.mContext, resourceId2, true);
            }
        }
        return null;
    }

    public int length() {
        return this.mWrapped.length();
    }

    public int getIndexCount() {
        return this.mWrapped.getIndexCount();
    }

    public int getIndex(int i) {
        int at = i;
        int i2 = at;
        return this.mWrapped.getIndex(at);
    }

    public Resources getResources() {
        return this.mWrapped.getResources();
    }

    public CharSequence getText(int i) {
        int index = i;
        int i2 = index;
        return this.mWrapped.getText(index);
    }

    public String getString(int i) {
        int index = i;
        int i2 = index;
        return this.mWrapped.getString(index);
    }

    public String getNonResourceString(int i) {
        int index = i;
        int i2 = index;
        return this.mWrapped.getNonResourceString(index);
    }

    public boolean getBoolean(int i, boolean z) {
        int index = i;
        int i2 = index;
        return this.mWrapped.getBoolean(index, z);
    }

    public int getInt(int i, int i2) {
        int index = i;
        int defValue = i2;
        int i3 = index;
        int i4 = defValue;
        return this.mWrapped.getInt(index, defValue);
    }

    public float getFloat(int i, float f) {
        int index = i;
        float defValue = f;
        int i2 = index;
        float f2 = defValue;
        return this.mWrapped.getFloat(index, defValue);
    }

    public int getColor(int i, int i2) {
        int index = i;
        int defValue = i2;
        int i3 = index;
        int i4 = defValue;
        return this.mWrapped.getColor(index, defValue);
    }

    public ColorStateList getColorStateList(int i) {
        int index = i;
        int i2 = index;
        if (this.mWrapped.hasValue(index)) {
            int resourceId = this.mWrapped.getResourceId(index, 0);
            int resourceId2 = resourceId;
            if (resourceId != 0) {
                ColorStateList colorStateList = AppCompatResources.getColorStateList(this.mContext, resourceId2);
                ColorStateList value = colorStateList;
                if (colorStateList != null) {
                    return value;
                }
            }
        }
        return this.mWrapped.getColorStateList(index);
    }

    public int getInteger(int i, int i2) {
        int index = i;
        int defValue = i2;
        int i3 = index;
        int i4 = defValue;
        return this.mWrapped.getInteger(index, defValue);
    }

    public float getDimension(int i, float f) {
        int index = i;
        float defValue = f;
        int i2 = index;
        float f2 = defValue;
        return this.mWrapped.getDimension(index, defValue);
    }

    public int getDimensionPixelOffset(int i, int i2) {
        int index = i;
        int defValue = i2;
        int i3 = index;
        int i4 = defValue;
        return this.mWrapped.getDimensionPixelOffset(index, defValue);
    }

    public int getDimensionPixelSize(int i, int i2) {
        int index = i;
        int defValue = i2;
        int i3 = index;
        int i4 = defValue;
        return this.mWrapped.getDimensionPixelSize(index, defValue);
    }

    public int getLayoutDimension(int i, String str) {
        int index = i;
        String name = str;
        int i2 = index;
        String str2 = name;
        return this.mWrapped.getLayoutDimension(index, name);
    }

    public int getLayoutDimension(int i, int i2) {
        int index = i;
        int defValue = i2;
        int i3 = index;
        int i4 = defValue;
        return this.mWrapped.getLayoutDimension(index, defValue);
    }

    public float getFraction(int i, int i2, int i3, float f) {
        int index = i;
        int base = i2;
        int pbase = i3;
        float defValue = f;
        int i4 = index;
        int i5 = base;
        int i6 = pbase;
        float f2 = defValue;
        return this.mWrapped.getFraction(index, base, pbase, defValue);
    }

    public int getResourceId(int i, int i2) {
        int index = i;
        int defValue = i2;
        int i3 = index;
        int i4 = defValue;
        return this.mWrapped.getResourceId(index, defValue);
    }

    public CharSequence[] getTextArray(int i) {
        int index = i;
        int i2 = index;
        return this.mWrapped.getTextArray(index);
    }

    public boolean getValue(int i, TypedValue typedValue) {
        int index = i;
        TypedValue outValue = typedValue;
        int i2 = index;
        TypedValue typedValue2 = outValue;
        return this.mWrapped.getValue(index, outValue);
    }

    public int getType(int i) {
        int index = i;
        int i2 = index;
        if (VERSION.SDK_INT >= 21) {
            return this.mWrapped.getType(index);
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        boolean value = this.mWrapped.getValue(index, this.mTypedValue);
        return this.mTypedValue.type;
    }

    public boolean hasValue(int i) {
        int index = i;
        int i2 = index;
        return this.mWrapped.hasValue(index);
    }

    public TypedValue peekValue(int i) {
        int index = i;
        int i2 = index;
        return this.mWrapped.peekValue(index);
    }

    public String getPositionDescription() {
        return this.mWrapped.getPositionDescription();
    }

    public void recycle() {
        this.mWrapped.recycle();
    }

    public int getChangingConfigurations() {
        return this.mWrapped.getChangingConfigurations();
    }
}
