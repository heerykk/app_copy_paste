package android.support.p003v7.widget;

import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.graphics.drawable.DrawableWrapper;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.DrawableUtils */
public class DrawableUtils {
    public static final Rect INSETS_NONE = new Rect();
    private static final String TAG = "DrawableUtils";
    private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
    private static Class<?> sInsetsClazz;

    static {
        if (VERSION.SDK_INT >= 18) {
            try {
                sInsetsClazz = Class.forName("android.graphics.Insets");
            } catch (ClassNotFoundException e) {
            }
        }
    }

    private DrawableUtils() {
    }

    public static Rect getOpticalBounds(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (sInsetsClazz != null) {
            try {
                Drawable unwrap = DrawableCompat.unwrap(drawable2);
                Drawable drawable4 = unwrap;
                Method method = unwrap.getClass().getMethod("getOpticalInsets", new Class[0]);
                Method method2 = method;
                Object invoke = method.invoke(drawable4, new Object[0]);
                Object insets = invoke;
                if (invoke != null) {
                    Rect result = new Rect();
                    Field[] fields = sInsetsClazz.getFields();
                    Field[] fieldArr = fields;
                    int length = fields.length;
                    for (int i = 0; i < length; i++) {
                        Field field = fieldArr[i];
                        Field field2 = field;
                        String name = field.getName();
                        String str = name;
                        char c = 65535;
                        switch (name.hashCode()) {
                            case -1383228885:
                                if (str.equals("bottom")) {
                                    c = 3;
                                    break;
                                } else {
                                    break;
                                }
                            case 115029:
                                if (str.equals("top")) {
                                    c = 1;
                                    break;
                                } else {
                                    break;
                                }
                            case 3317767:
                                if (str.equals("left")) {
                                    c = 0;
                                    break;
                                } else {
                                    break;
                                }
                            case 108511772:
                                if (str.equals("right")) {
                                    c = 2;
                                    break;
                                } else {
                                    break;
                                }
                        }
                        switch (c) {
                            case 0:
                                result.left = field2.getInt(insets);
                                break;
                            case 1:
                                result.top = field2.getInt(insets);
                                break;
                            case 2:
                                result.right = field2.getInt(insets);
                                break;
                            case 3:
                                result.bottom = field2.getInt(insets);
                                break;
                        }
                    }
                    return result;
                }
            } catch (Exception e) {
                Exception exc = e;
                int e2 = Log.e(TAG, "Couldn't obtain the optical insets. Ignoring.");
            }
        }
        return INSETS_NONE;
    }

    static void fixDrawable(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (VERSION.SDK_INT == 21 && VECTOR_DRAWABLE_CLAZZ_NAME.equals(drawable2.getClass().getName())) {
            fixVectorDrawableTinting(drawable2);
        }
    }

    public static boolean canSafelyMutateDrawable(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (VERSION.SDK_INT < 15 && (drawable2 instanceof InsetDrawable)) {
            return false;
        }
        if (VERSION.SDK_INT < 15 && (drawable2 instanceof GradientDrawable)) {
            return false;
        }
        if (VERSION.SDK_INT < 17 && (drawable2 instanceof LayerDrawable)) {
            return false;
        }
        if (drawable2 instanceof DrawableContainer) {
            ConstantState constantState = drawable2.getConstantState();
            ConstantState state = constantState;
            if (constantState instanceof DrawableContainerState) {
                DrawableContainerState drawableContainerState = (DrawableContainerState) state;
                DrawableContainerState drawableContainerState2 = drawableContainerState;
                Drawable[] children = drawableContainerState.getChildren();
                Drawable[] drawableArr = children;
                int length = children.length;
                for (int i = 0; i < length; i++) {
                    Drawable drawable4 = drawableArr[i];
                    Drawable drawable5 = drawable4;
                    if (!canSafelyMutateDrawable(drawable4)) {
                        return false;
                    }
                }
            }
        } else if (drawable2 instanceof DrawableWrapper) {
            return canSafelyMutateDrawable(((DrawableWrapper) drawable2).getWrappedDrawable());
        } else {
            if (drawable2 instanceof android.support.p003v7.graphics.drawable.DrawableWrapper) {
                return canSafelyMutateDrawable(((android.support.p003v7.graphics.drawable.DrawableWrapper) drawable2).getWrappedDrawable());
            }
            if (drawable2 instanceof ScaleDrawable) {
                return canSafelyMutateDrawable(((ScaleDrawable) drawable2).getDrawable());
            }
        }
        return true;
    }

    private static void fixVectorDrawableTinting(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        int[] state = drawable2.getState();
        int[] originalState = state;
        if (state == null || originalState.length == 0) {
            boolean state2 = drawable2.setState(ThemeUtils.CHECKED_STATE_SET);
        } else {
            boolean state3 = drawable2.setState(ThemeUtils.EMPTY_STATE_SET);
        }
        boolean state4 = drawable2.setState(originalState);
    }

    static Mode parseTintMode(int i, Mode mode) {
        Mode valueOf;
        int value = i;
        Mode defaultMode = mode;
        int i2 = value;
        Mode mode2 = defaultMode;
        switch (value) {
            case 3:
                return Mode.SRC_OVER;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            case 16:
                if (VERSION.SDK_INT < 11) {
                    valueOf = defaultMode;
                } else {
                    valueOf = Mode.valueOf("ADD");
                }
                return valueOf;
            default:
                return defaultMode;
        }
    }
}
