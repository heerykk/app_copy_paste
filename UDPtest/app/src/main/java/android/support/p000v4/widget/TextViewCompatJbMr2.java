package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

@TargetApi(18)
@RequiresApi(18)
/* renamed from: android.support.v4.widget.TextViewCompatJbMr2 */
class TextViewCompatJbMr2 {
    TextViewCompatJbMr2() {
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
        textView2.setCompoundDrawablesRelative(start, top, end, bottom);
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
        textView2.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
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
        textView2.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }

    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        TextView textView2 = textView;
        TextView textView3 = textView2;
        return textView2.getCompoundDrawablesRelative();
    }
}
