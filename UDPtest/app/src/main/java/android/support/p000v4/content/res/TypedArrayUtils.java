package android.support.p000v4.content.res;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AnyRes;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleableRes;
import android.util.TypedValue;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.content.res.TypedArrayUtils */
public class TypedArrayUtils {
    public TypedArrayUtils() {
    }

    public static boolean getBoolean(TypedArray typedArray, @StyleableRes int i, @StyleableRes int i2, boolean z) {
        TypedArray a = typedArray;
        int index = i;
        int fallbackIndex = i2;
        TypedArray typedArray2 = a;
        int i3 = index;
        int i4 = fallbackIndex;
        boolean z2 = a.getBoolean(fallbackIndex, z);
        boolean z3 = z2;
        return a.getBoolean(index, z2);
    }

    public static Drawable getDrawable(TypedArray typedArray, @StyleableRes int i, @StyleableRes int i2) {
        TypedArray a = typedArray;
        int index = i;
        int fallbackIndex = i2;
        TypedArray typedArray2 = a;
        int i3 = index;
        int i4 = fallbackIndex;
        Drawable drawable = a.getDrawable(index);
        Drawable val = drawable;
        if (drawable == null) {
            val = a.getDrawable(fallbackIndex);
        }
        return val;
    }

    public static int getInt(TypedArray typedArray, @StyleableRes int i, @StyleableRes int i2, int i3) {
        TypedArray a = typedArray;
        int index = i;
        int fallbackIndex = i2;
        int defaultValue = i3;
        TypedArray typedArray2 = a;
        int i4 = index;
        int i5 = fallbackIndex;
        int i6 = defaultValue;
        int i7 = a.getInt(fallbackIndex, defaultValue);
        int i8 = i7;
        return a.getInt(index, i7);
    }

    @AnyRes
    public static int getResourceId(TypedArray typedArray, @StyleableRes int i, @StyleableRes int i2, @AnyRes int i3) {
        TypedArray a = typedArray;
        int index = i;
        int fallbackIndex = i2;
        int defaultValue = i3;
        TypedArray typedArray2 = a;
        int i4 = index;
        int i5 = fallbackIndex;
        int i6 = defaultValue;
        int resourceId = a.getResourceId(fallbackIndex, defaultValue);
        int i7 = resourceId;
        return a.getResourceId(index, resourceId);
    }

    public static String getString(TypedArray typedArray, @StyleableRes int i, @StyleableRes int i2) {
        TypedArray a = typedArray;
        int index = i;
        int fallbackIndex = i2;
        TypedArray typedArray2 = a;
        int i3 = index;
        int i4 = fallbackIndex;
        String string = a.getString(index);
        String val = string;
        if (string == null) {
            val = a.getString(fallbackIndex);
        }
        return val;
    }

    public static CharSequence getText(TypedArray typedArray, @StyleableRes int i, @StyleableRes int i2) {
        TypedArray a = typedArray;
        int index = i;
        int fallbackIndex = i2;
        TypedArray typedArray2 = a;
        int i3 = index;
        int i4 = fallbackIndex;
        CharSequence text = a.getText(index);
        CharSequence val = text;
        if (text == null) {
            val = a.getText(fallbackIndex);
        }
        return val;
    }

    public static CharSequence[] getTextArray(TypedArray typedArray, @StyleableRes int i, @StyleableRes int i2) {
        TypedArray a = typedArray;
        int index = i;
        int fallbackIndex = i2;
        TypedArray typedArray2 = a;
        int i3 = index;
        int i4 = fallbackIndex;
        CharSequence[] textArray = a.getTextArray(index);
        CharSequence[] val = textArray;
        if (textArray == null) {
            val = a.getTextArray(fallbackIndex);
        }
        return val;
    }

    public static int getAttr(Context context, int i, int i2) {
        Context context2 = context;
        int attr = i;
        int fallbackAttr = i2;
        Context context3 = context2;
        int i3 = attr;
        int i4 = fallbackAttr;
        TypedValue value = new TypedValue();
        boolean resolveAttribute = context2.getTheme().resolveAttribute(attr, value, true);
        if (value.resourceId == 0) {
            return fallbackAttr;
        }
        return attr;
    }
}
