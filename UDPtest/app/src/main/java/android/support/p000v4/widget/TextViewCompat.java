package android.support.p000v4.widget;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.widget.TextView;

/* renamed from: android.support.v4.widget.TextViewCompat */
public final class TextViewCompat {
    static final TextViewCompatImpl IMPL;

    /* renamed from: android.support.v4.widget.TextViewCompat$Api23TextViewCompatImpl */
    static class Api23TextViewCompatImpl extends JbMr2TextViewCompatImpl {
        Api23TextViewCompatImpl() {
        }

        public void setTextAppearance(@NonNull TextView textView, @StyleRes int i) {
            TextView textView2 = textView;
            int resId = i;
            TextView textView3 = textView2;
            int i2 = resId;
            TextViewCompatApi23.setTextAppearance(textView2, resId);
        }
    }

    /* renamed from: android.support.v4.widget.TextViewCompat$BaseTextViewCompatImpl */
    static class BaseTextViewCompatImpl implements TextViewCompatImpl {
        BaseTextViewCompatImpl() {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
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
            textView2.setCompoundDrawables(start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
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
            textView2.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
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
            textView2.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }

        public int getMaxLines(TextView textView) {
            TextView textView2 = textView;
            TextView textView3 = textView2;
            return TextViewCompatGingerbread.getMaxLines(textView2);
        }

        public int getMinLines(TextView textView) {
            TextView textView2 = textView;
            TextView textView3 = textView2;
            return TextViewCompatGingerbread.getMinLines(textView2);
        }

        public void setTextAppearance(TextView textView, @StyleRes int i) {
            TextView textView2 = textView;
            int resId = i;
            TextView textView3 = textView2;
            int i2 = resId;
            TextViewCompatGingerbread.setTextAppearance(textView2, resId);
        }

        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            TextView textView2 = textView;
            TextView textView3 = textView2;
            return TextViewCompatGingerbread.getCompoundDrawablesRelative(textView2);
        }
    }

    /* renamed from: android.support.v4.widget.TextViewCompat$JbMr1TextViewCompatImpl */
    static class JbMr1TextViewCompatImpl extends JbTextViewCompatImpl {
        JbMr1TextViewCompatImpl() {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
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
            TextViewCompatJbMr1.setCompoundDrawablesRelative(textView2, start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
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
            TextViewCompatJbMr1.setCompoundDrawablesRelativeWithIntrinsicBounds(textView2, start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
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
            TextViewCompatJbMr1.setCompoundDrawablesRelativeWithIntrinsicBounds(textView2, start, top, end, bottom);
        }

        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            TextView textView2 = textView;
            TextView textView3 = textView2;
            return TextViewCompatJbMr1.getCompoundDrawablesRelative(textView2);
        }
    }

    /* renamed from: android.support.v4.widget.TextViewCompat$JbMr2TextViewCompatImpl */
    static class JbMr2TextViewCompatImpl extends JbMr1TextViewCompatImpl {
        JbMr2TextViewCompatImpl() {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
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
            TextViewCompatJbMr2.setCompoundDrawablesRelative(textView2, start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
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
            TextViewCompatJbMr2.setCompoundDrawablesRelativeWithIntrinsicBounds(textView2, start, top, end, bottom);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
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
            TextViewCompatJbMr2.setCompoundDrawablesRelativeWithIntrinsicBounds(textView2, start, top, end, bottom);
        }

        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            TextView textView2 = textView;
            TextView textView3 = textView2;
            return TextViewCompatJbMr2.getCompoundDrawablesRelative(textView2);
        }
    }

    /* renamed from: android.support.v4.widget.TextViewCompat$JbTextViewCompatImpl */
    static class JbTextViewCompatImpl extends BaseTextViewCompatImpl {
        JbTextViewCompatImpl() {
        }

        public int getMaxLines(TextView textView) {
            TextView textView2 = textView;
            TextView textView3 = textView2;
            return TextViewCompatJb.getMaxLines(textView2);
        }

        public int getMinLines(TextView textView) {
            TextView textView2 = textView;
            TextView textView3 = textView2;
            return TextViewCompatJb.getMinLines(textView2);
        }
    }

    /* renamed from: android.support.v4.widget.TextViewCompat$TextViewCompatImpl */
    interface TextViewCompatImpl {
        Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView);

        int getMaxLines(TextView textView);

        int getMinLines(TextView textView);

        void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4);

        void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4);

        void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4);

        void setTextAppearance(@NonNull TextView textView, @StyleRes int i);
    }

    private TextViewCompat() {
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 23) {
            IMPL = new Api23TextViewCompatImpl();
        } else if (version >= 18) {
            IMPL = new JbMr2TextViewCompatImpl();
        } else if (version >= 17) {
            IMPL = new JbMr1TextViewCompatImpl();
        } else if (version < 16) {
            IMPL = new BaseTextViewCompatImpl();
        } else {
            IMPL = new JbTextViewCompatImpl();
        }
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
        IMPL.setCompoundDrawablesRelative(textView2, start, top, end, bottom);
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
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(textView2, start, top, end, bottom);
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
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(textView2, start, top, end, bottom);
    }

    public static int getMaxLines(@NonNull TextView textView) {
        TextView textView2 = textView;
        TextView textView3 = textView2;
        return IMPL.getMaxLines(textView2);
    }

    public static int getMinLines(@NonNull TextView textView) {
        TextView textView2 = textView;
        TextView textView3 = textView2;
        return IMPL.getMinLines(textView2);
    }

    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int i) {
        TextView textView2 = textView;
        int resId = i;
        TextView textView3 = textView2;
        int i2 = resId;
        IMPL.setTextAppearance(textView2, resId);
    }

    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        TextView textView2 = textView;
        TextView textView3 = textView2;
        return IMPL.getCompoundDrawablesRelative(textView2);
    }
}
