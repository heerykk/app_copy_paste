package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.p000v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

/* renamed from: android.support.v7.widget.ThemeUtils */
class ThemeUtils {
    static final int[] ACTIVATED_STATE_SET;
    static final int[] CHECKED_STATE_SET;
    static final int[] DISABLED_STATE_SET;
    static final int[] EMPTY_STATE_SET = new int[0];
    static final int[] FOCUSED_STATE_SET;
    static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET;
    static final int[] PRESSED_STATE_SET;
    static final int[] SELECTED_STATE_SET;
    private static final int[] TEMP_ARRAY = new int[1];
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();

    ThemeUtils() {
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = -16842910;
        DISABLED_STATE_SET = iArr;
        int[] iArr2 = new int[1];
        iArr2[0] = 16842908;
        FOCUSED_STATE_SET = iArr2;
        int[] iArr3 = new int[1];
        iArr3[0] = 16843518;
        ACTIVATED_STATE_SET = iArr3;
        int[] iArr4 = new int[1];
        iArr4[0] = 16842919;
        PRESSED_STATE_SET = iArr4;
        int[] iArr5 = new int[1];
        iArr5[0] = 16842912;
        CHECKED_STATE_SET = iArr5;
        int[] iArr6 = new int[1];
        iArr6[0] = 16842913;
        SELECTED_STATE_SET = iArr6;
        int[] iArr7 = new int[2];
        iArr7[0] = -16842919;
        iArr7[1] = -16842908;
        NOT_PRESSED_OR_FOCUSED_STATE_SET = iArr7;
    }

    public static ColorStateList createDisabledStateList(int i, int i2) {
        int textColor = i;
        int disabledTextColor = i2;
        int i3 = textColor;
        int i4 = disabledTextColor;
        int[][] states = new int[2][];
        states[0] = DISABLED_STATE_SET;
        states[1] = EMPTY_STATE_SET;
        int i5 = 1 + 1;
        return new ColorStateList(states, new int[]{disabledTextColor, textColor});
    }

    /* JADX INFO: finally extract failed */
    public static int getThemeAttrColor(Context context, int i) {
        Context context2 = context;
        int attr = i;
        Context context3 = context2;
        int i2 = attr;
        TEMP_ARRAY[0] = attr;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, (AttributeSet) null, TEMP_ARRAY);
        TintTypedArray a = obtainStyledAttributes;
        try {
            int color = obtainStyledAttributes.getColor(0, 0);
            a.recycle();
            return color;
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public static ColorStateList getThemeAttrColorStateList(Context context, int i) {
        Context context2 = context;
        int attr = i;
        Context context3 = context2;
        int i2 = attr;
        TEMP_ARRAY[0] = attr;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, (AttributeSet) null, TEMP_ARRAY);
        TintTypedArray a = obtainStyledAttributes;
        try {
            ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(0);
            a.recycle();
            return colorStateList;
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    public static int getDisabledThemeAttrColor(Context context, int i) {
        Context context2 = context;
        int attr = i;
        Context context3 = context2;
        int i2 = attr;
        ColorStateList themeAttrColorStateList = getThemeAttrColorStateList(context2, attr);
        ColorStateList csl = themeAttrColorStateList;
        if (themeAttrColorStateList != null && csl.isStateful()) {
            return csl.getColorForState(DISABLED_STATE_SET, csl.getDefaultColor());
        }
        TypedValue tv = getTypedValue();
        boolean resolveAttribute = context2.getTheme().resolveAttribute(16842803, tv, true);
        float f = tv.getFloat();
        float f2 = f;
        return getThemeAttrColor(context2, attr, f);
    }

    private static TypedValue getTypedValue() {
        TypedValue typedValue = (TypedValue) TL_TYPED_VALUE.get();
        TypedValue typedValue2 = typedValue;
        if (typedValue == null) {
            typedValue2 = new TypedValue();
            TL_TYPED_VALUE.set(typedValue2);
        }
        return typedValue2;
    }

    static int getThemeAttrColor(Context context, int i, float f) {
        Context context2 = context;
        int attr = i;
        float alpha = f;
        Context context3 = context2;
        int i2 = attr;
        float f2 = alpha;
        int themeAttrColor = getThemeAttrColor(context2, attr);
        return ColorUtils.setAlphaComponent(themeAttrColor, Math.round(((float) Color.alpha(themeAttrColor)) * alpha));
    }
}
