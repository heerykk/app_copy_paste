package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

@TargetApi(17)
@RequiresApi(17)
/* renamed from: android.support.v4.widget.TextViewCompatJbMr1 */
class TextViewCompatJbMr1 {
    TextViewCompatJbMr1() {
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        TextView textView2 = textView;
        Drawable start = drawable;
        Drawable top = drawable2;
        Drawable end = drawable3;
        Drawable bottom = drawable4;
        TextView textView3 = textView2;
        Drawable drawable5 = start;
        Drawable drawable6 = top;
        Drawable drawable7 = end;
        Drawable drawable8 = bottom;
        boolean rtl = textView2.getLayoutDirection() == 1;
        textView2.setCompoundDrawables(!rtl ? start : end, top, !rtl ? end : start, bottom);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        TextView textView2 = textView;
        Drawable start = drawable;
        Drawable top = drawable2;
        Drawable end = drawable3;
        Drawable bottom = drawable4;
        TextView textView3 = textView2;
        Drawable drawable5 = start;
        Drawable drawable6 = top;
        Drawable drawable7 = end;
        Drawable drawable8 = bottom;
        boolean rtl = textView2.getLayoutDirection() == 1;
        textView2.setCompoundDrawablesWithIntrinsicBounds(!rtl ? start : end, top, !rtl ? end : start, bottom);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, int i, int i2, int i3, int i4) {
        TextView textView2 = textView;
        int start = i;
        int top = i2;
        int end = i3;
        int bottom = i4;
        TextView textView3 = textView2;
        int i5 = start;
        int i6 = top;
        int i7 = end;
        int i8 = bottom;
        boolean rtl = textView2.getLayoutDirection() == 1;
        textView2.setCompoundDrawablesWithIntrinsicBounds(!rtl ? start : end, top, !rtl ? end : start, bottom);
    }

    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        TextView textView2 = textView;
        TextView textView3 = textView2;
        boolean rtl = textView2.getLayoutDirection() == 1;
        Drawable[] compoundDrawables = textView2.getCompoundDrawables();
        Drawable[] compounds = compoundDrawables;
        if (rtl) {
            Drawable start = compoundDrawables[2];
            Drawable end = compounds[0];
            compounds[0] = start;
            compounds[2] = end;
        }
        return compounds;
    }
}
