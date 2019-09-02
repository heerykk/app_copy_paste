package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.widget.CompoundButtonCompatGingerbread */
class CompoundButtonCompatGingerbread {
    private static final String TAG = "CompoundButtonCompatGingerbread";
    private static Field sButtonDrawableField;
    private static boolean sButtonDrawableFieldFetched;

    CompoundButtonCompatGingerbread() {
    }

    static void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList) {
        CompoundButton button = compoundButton;
        ColorStateList tint = colorStateList;
        CompoundButton compoundButton2 = button;
        ColorStateList colorStateList2 = tint;
        if (button instanceof TintableCompoundButton) {
            ((TintableCompoundButton) button).setSupportButtonTintList(tint);
        }
    }

    static ColorStateList getButtonTintList(CompoundButton compoundButton) {
        CompoundButton button = compoundButton;
        CompoundButton compoundButton2 = button;
        if (!(button instanceof TintableCompoundButton)) {
            return null;
        }
        return ((TintableCompoundButton) button).getSupportButtonTintList();
    }

    static void setButtonTintMode(CompoundButton compoundButton, Mode mode) {
        CompoundButton button = compoundButton;
        Mode tintMode = mode;
        CompoundButton compoundButton2 = button;
        Mode mode2 = tintMode;
        if (button instanceof TintableCompoundButton) {
            ((TintableCompoundButton) button).setSupportButtonTintMode(tintMode);
        }
    }

    static Mode getButtonTintMode(CompoundButton compoundButton) {
        CompoundButton button = compoundButton;
        CompoundButton compoundButton2 = button;
        if (!(button instanceof TintableCompoundButton)) {
            return null;
        }
        return ((TintableCompoundButton) button).getSupportButtonTintMode();
    }

    static Drawable getButtonDrawable(CompoundButton compoundButton) {
        CompoundButton button = compoundButton;
        CompoundButton compoundButton2 = button;
        if (!sButtonDrawableFieldFetched) {
            try {
                sButtonDrawableField = CompoundButton.class.getDeclaredField("mButtonDrawable");
                sButtonDrawableField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                NoSuchFieldException noSuchFieldException = e;
                int i = Log.i(TAG, "Failed to retrieve mButtonDrawable field", e);
            }
            sButtonDrawableFieldFetched = true;
        }
        if (sButtonDrawableField != null) {
            try {
                return (Drawable) sButtonDrawableField.get(button);
            } catch (IllegalAccessException e2) {
                IllegalAccessException illegalAccessException = e2;
                int i2 = Log.i(TAG, "Failed to get button drawable via reflection", e2);
                sButtonDrawableField = null;
            }
        }
        return null;
    }
}
