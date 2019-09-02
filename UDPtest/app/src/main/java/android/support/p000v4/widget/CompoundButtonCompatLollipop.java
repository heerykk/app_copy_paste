package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.RequiresApi;
import android.widget.CompoundButton;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.widget.CompoundButtonCompatLollipop */
class CompoundButtonCompatLollipop {
    CompoundButtonCompatLollipop() {
    }

    static void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList) {
        CompoundButton button = compoundButton;
        ColorStateList tint = colorStateList;
        CompoundButton compoundButton2 = button;
        ColorStateList colorStateList2 = tint;
        button.setButtonTintList(tint);
    }

    static ColorStateList getButtonTintList(CompoundButton compoundButton) {
        CompoundButton button = compoundButton;
        CompoundButton compoundButton2 = button;
        return button.getButtonTintList();
    }

    static void setButtonTintMode(CompoundButton compoundButton, Mode mode) {
        CompoundButton button = compoundButton;
        Mode tintMode = mode;
        CompoundButton compoundButton2 = button;
        Mode mode2 = tintMode;
        button.setButtonTintMode(tintMode);
    }

    static Mode getButtonTintMode(CompoundButton compoundButton) {
        CompoundButton button = compoundButton;
        CompoundButton compoundButton2 = button;
        return button.getButtonTintMode();
    }
}
