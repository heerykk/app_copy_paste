package android.support.p000v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;

/* renamed from: android.support.v4.widget.CompoundButtonCompat */
public final class CompoundButtonCompat {
    private static final CompoundButtonCompatImpl IMPL;

    /* renamed from: android.support.v4.widget.CompoundButtonCompat$Api23CompoundButtonImpl */
    static class Api23CompoundButtonImpl extends LollipopCompoundButtonImpl {
        Api23CompoundButtonImpl() {
        }

        public Drawable getButtonDrawable(CompoundButton compoundButton) {
            CompoundButton button = compoundButton;
            CompoundButton compoundButton2 = button;
            return CompoundButtonCompatApi23.getButtonDrawable(button);
        }
    }

    /* renamed from: android.support.v4.widget.CompoundButtonCompat$BaseCompoundButtonCompat */
    static class BaseCompoundButtonCompat implements CompoundButtonCompatImpl {
        BaseCompoundButtonCompat() {
        }

        public void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList) {
            CompoundButton button = compoundButton;
            ColorStateList tint = colorStateList;
            CompoundButton compoundButton2 = button;
            ColorStateList colorStateList2 = tint;
            CompoundButtonCompatGingerbread.setButtonTintList(button, tint);
        }

        public ColorStateList getButtonTintList(CompoundButton compoundButton) {
            CompoundButton button = compoundButton;
            CompoundButton compoundButton2 = button;
            return CompoundButtonCompatGingerbread.getButtonTintList(button);
        }

        public void setButtonTintMode(CompoundButton compoundButton, Mode mode) {
            CompoundButton button = compoundButton;
            Mode tintMode = mode;
            CompoundButton compoundButton2 = button;
            Mode mode2 = tintMode;
            CompoundButtonCompatGingerbread.setButtonTintMode(button, tintMode);
        }

        public Mode getButtonTintMode(CompoundButton compoundButton) {
            CompoundButton button = compoundButton;
            CompoundButton compoundButton2 = button;
            return CompoundButtonCompatGingerbread.getButtonTintMode(button);
        }

        public Drawable getButtonDrawable(CompoundButton compoundButton) {
            CompoundButton button = compoundButton;
            CompoundButton compoundButton2 = button;
            return CompoundButtonCompatGingerbread.getButtonDrawable(button);
        }
    }

    /* renamed from: android.support.v4.widget.CompoundButtonCompat$CompoundButtonCompatImpl */
    interface CompoundButtonCompatImpl {
        Drawable getButtonDrawable(CompoundButton compoundButton);

        ColorStateList getButtonTintList(CompoundButton compoundButton);

        Mode getButtonTintMode(CompoundButton compoundButton);

        void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList);

        void setButtonTintMode(CompoundButton compoundButton, Mode mode);
    }

    /* renamed from: android.support.v4.widget.CompoundButtonCompat$LollipopCompoundButtonImpl */
    static class LollipopCompoundButtonImpl extends BaseCompoundButtonCompat {
        LollipopCompoundButtonImpl() {
        }

        public void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList) {
            CompoundButton button = compoundButton;
            ColorStateList tint = colorStateList;
            CompoundButton compoundButton2 = button;
            ColorStateList colorStateList2 = tint;
            CompoundButtonCompatLollipop.setButtonTintList(button, tint);
        }

        public ColorStateList getButtonTintList(CompoundButton compoundButton) {
            CompoundButton button = compoundButton;
            CompoundButton compoundButton2 = button;
            return CompoundButtonCompatLollipop.getButtonTintList(button);
        }

        public void setButtonTintMode(CompoundButton compoundButton, Mode mode) {
            CompoundButton button = compoundButton;
            Mode tintMode = mode;
            CompoundButton compoundButton2 = button;
            Mode mode2 = tintMode;
            CompoundButtonCompatLollipop.setButtonTintMode(button, tintMode);
        }

        public Mode getButtonTintMode(CompoundButton compoundButton) {
            CompoundButton button = compoundButton;
            CompoundButton compoundButton2 = button;
            return CompoundButtonCompatLollipop.getButtonTintMode(button);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int sdk = i;
        if (i >= 23) {
            IMPL = new Api23CompoundButtonImpl();
        } else if (sdk < 21) {
            IMPL = new BaseCompoundButtonCompat();
        } else {
            IMPL = new LollipopCompoundButtonImpl();
        }
    }

    private CompoundButtonCompat() {
    }

    public static void setButtonTintList(@NonNull CompoundButton compoundButton, @Nullable ColorStateList colorStateList) {
        CompoundButton button = compoundButton;
        ColorStateList tint = colorStateList;
        CompoundButton compoundButton2 = button;
        ColorStateList colorStateList2 = tint;
        IMPL.setButtonTintList(button, tint);
    }

    @Nullable
    public static ColorStateList getButtonTintList(@NonNull CompoundButton compoundButton) {
        CompoundButton button = compoundButton;
        CompoundButton compoundButton2 = button;
        return IMPL.getButtonTintList(button);
    }

    public static void setButtonTintMode(@NonNull CompoundButton compoundButton, @Nullable Mode mode) {
        CompoundButton button = compoundButton;
        Mode tintMode = mode;
        CompoundButton compoundButton2 = button;
        Mode mode2 = tintMode;
        IMPL.setButtonTintMode(button, tintMode);
    }

    @Nullable
    public static Mode getButtonTintMode(@NonNull CompoundButton compoundButton) {
        CompoundButton button = compoundButton;
        CompoundButton compoundButton2 = button;
        return IMPL.getButtonTintMode(button);
    }

    @Nullable
    public static Drawable getButtonDrawable(@NonNull CompoundButton compoundButton) {
        CompoundButton button = compoundButton;
        CompoundButton compoundButton2 = button;
        return IMPL.getButtonDrawable(button);
    }
}
