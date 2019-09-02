package android.support.p000v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: android.support.v4.graphics.drawable.DrawableCompat */
public final class DrawableCompat {
    static final DrawableImpl IMPL;

    /* renamed from: android.support.v4.graphics.drawable.DrawableCompat$BaseDrawableImpl */
    static class BaseDrawableImpl implements DrawableImpl {
        BaseDrawableImpl() {
        }

        public void jumpToCurrentState(Drawable drawable) {
            Drawable drawable2 = drawable;
        }

        public void setAutoMirrored(Drawable drawable, boolean z) {
            Drawable drawable2 = drawable;
            boolean z2 = z;
        }

        public boolean isAutoMirrored(Drawable drawable) {
            Drawable drawable2 = drawable;
            return false;
        }

        public void setHotspot(Drawable drawable, float f, float f2) {
            Drawable drawable2 = drawable;
            float f3 = f;
            float f4 = f2;
        }

        public void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4) {
            Drawable drawable2 = drawable;
            int i5 = i;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
        }

        public void setTint(Drawable drawable, int i) {
            Drawable drawable2 = drawable;
            int tint = i;
            Drawable drawable3 = drawable2;
            int i2 = tint;
            DrawableCompatBase.setTint(drawable2, tint);
        }

        public void setTintList(Drawable drawable, ColorStateList colorStateList) {
            Drawable drawable2 = drawable;
            ColorStateList tint = colorStateList;
            Drawable drawable3 = drawable2;
            ColorStateList colorStateList2 = tint;
            DrawableCompatBase.setTintList(drawable2, tint);
        }

        public void setTintMode(Drawable drawable, Mode mode) {
            Drawable drawable2 = drawable;
            Mode tintMode = mode;
            Drawable drawable3 = drawable2;
            Mode mode2 = tintMode;
            DrawableCompatBase.setTintMode(drawable2, tintMode);
        }

        public Drawable wrap(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            return DrawableCompatBase.wrapForTinting(drawable2);
        }

        public boolean setLayoutDirection(Drawable drawable, int i) {
            Drawable drawable2 = drawable;
            int i2 = i;
            return false;
        }

        public int getLayoutDirection(Drawable drawable) {
            Drawable drawable2 = drawable;
            return 0;
        }

        public int getAlpha(Drawable drawable) {
            Drawable drawable2 = drawable;
            return 0;
        }

        public void applyTheme(Drawable drawable, Theme theme) {
            Drawable drawable2 = drawable;
            Theme theme2 = theme;
        }

        public boolean canApplyTheme(Drawable drawable) {
            Drawable drawable2 = drawable;
            return false;
        }

        public ColorFilter getColorFilter(Drawable drawable) {
            Drawable drawable2 = drawable;
            return null;
        }

        public void clearColorFilter(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            drawable2.clearColorFilter();
        }

        public void inflate(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) throws IOException, XmlPullParserException {
            Drawable drawable2 = drawable;
            Resources res = resources;
            XmlPullParser parser = xmlPullParser;
            AttributeSet attrs = attributeSet;
            Theme t = theme;
            Drawable drawable3 = drawable2;
            Resources resources2 = res;
            XmlPullParser xmlPullParser2 = parser;
            AttributeSet attributeSet2 = attrs;
            Theme theme2 = t;
            DrawableCompatBase.inflate(drawable2, res, parser, attrs, t);
        }
    }

    /* renamed from: android.support.v4.graphics.drawable.DrawableCompat$DrawableImpl */
    interface DrawableImpl {
        void applyTheme(Drawable drawable, Theme theme);

        boolean canApplyTheme(Drawable drawable);

        void clearColorFilter(Drawable drawable);

        int getAlpha(Drawable drawable);

        ColorFilter getColorFilter(Drawable drawable);

        int getLayoutDirection(Drawable drawable);

        void inflate(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) throws IOException, XmlPullParserException;

        boolean isAutoMirrored(Drawable drawable);

        void jumpToCurrentState(Drawable drawable);

        void setAutoMirrored(Drawable drawable, boolean z);

        void setHotspot(Drawable drawable, float f, float f2);

        void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4);

        boolean setLayoutDirection(Drawable drawable, int i);

        void setTint(Drawable drawable, int i);

        void setTintList(Drawable drawable, ColorStateList colorStateList);

        void setTintMode(Drawable drawable, Mode mode);

        Drawable wrap(Drawable drawable);
    }

    /* renamed from: android.support.v4.graphics.drawable.DrawableCompat$HoneycombDrawableImpl */
    static class HoneycombDrawableImpl extends BaseDrawableImpl {
        HoneycombDrawableImpl() {
        }

        public void jumpToCurrentState(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            DrawableCompatHoneycomb.jumpToCurrentState(drawable2);
        }

        public Drawable wrap(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            return DrawableCompatHoneycomb.wrapForTinting(drawable2);
        }
    }

    /* renamed from: android.support.v4.graphics.drawable.DrawableCompat$JellybeanMr1DrawableImpl */
    static class JellybeanMr1DrawableImpl extends HoneycombDrawableImpl {
        JellybeanMr1DrawableImpl() {
        }

        public boolean setLayoutDirection(Drawable drawable, int i) {
            Drawable drawable2 = drawable;
            int layoutDirection = i;
            Drawable drawable3 = drawable2;
            int i2 = layoutDirection;
            return DrawableCompatJellybeanMr1.setLayoutDirection(drawable2, layoutDirection);
        }

        public int getLayoutDirection(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            int layoutDirection = DrawableCompatJellybeanMr1.getLayoutDirection(drawable2);
            return layoutDirection < 0 ? 0 : layoutDirection;
        }
    }

    /* renamed from: android.support.v4.graphics.drawable.DrawableCompat$KitKatDrawableImpl */
    static class KitKatDrawableImpl extends JellybeanMr1DrawableImpl {
        KitKatDrawableImpl() {
        }

        public void setAutoMirrored(Drawable drawable, boolean z) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            DrawableCompatKitKat.setAutoMirrored(drawable2, z);
        }

        public boolean isAutoMirrored(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            return DrawableCompatKitKat.isAutoMirrored(drawable2);
        }

        public Drawable wrap(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            return DrawableCompatKitKat.wrapForTinting(drawable2);
        }

        public int getAlpha(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            return DrawableCompatKitKat.getAlpha(drawable2);
        }
    }

    /* renamed from: android.support.v4.graphics.drawable.DrawableCompat$LollipopDrawableImpl */
    static class LollipopDrawableImpl extends KitKatDrawableImpl {
        LollipopDrawableImpl() {
        }

        public void setHotspot(Drawable drawable, float f, float f2) {
            Drawable drawable2 = drawable;
            float x = f;
            float y = f2;
            Drawable drawable3 = drawable2;
            float f3 = x;
            float f4 = y;
            DrawableCompatLollipop.setHotspot(drawable2, x, y);
        }

        public void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4) {
            Drawable drawable2 = drawable;
            int left = i;
            int top = i2;
            int right = i3;
            int bottom = i4;
            Drawable drawable3 = drawable2;
            int i5 = left;
            int i6 = top;
            int i7 = right;
            int i8 = bottom;
            DrawableCompatLollipop.setHotspotBounds(drawable2, left, top, right, bottom);
        }

        public void setTint(Drawable drawable, int i) {
            Drawable drawable2 = drawable;
            int tint = i;
            Drawable drawable3 = drawable2;
            int i2 = tint;
            DrawableCompatLollipop.setTint(drawable2, tint);
        }

        public void setTintList(Drawable drawable, ColorStateList colorStateList) {
            Drawable drawable2 = drawable;
            ColorStateList tint = colorStateList;
            Drawable drawable3 = drawable2;
            ColorStateList colorStateList2 = tint;
            DrawableCompatLollipop.setTintList(drawable2, tint);
        }

        public void setTintMode(Drawable drawable, Mode mode) {
            Drawable drawable2 = drawable;
            Mode tintMode = mode;
            Drawable drawable3 = drawable2;
            Mode mode2 = tintMode;
            DrawableCompatLollipop.setTintMode(drawable2, tintMode);
        }

        public Drawable wrap(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            return DrawableCompatLollipop.wrapForTinting(drawable2);
        }

        public void applyTheme(Drawable drawable, Theme theme) {
            Drawable drawable2 = drawable;
            Theme t = theme;
            Drawable drawable3 = drawable2;
            Theme theme2 = t;
            DrawableCompatLollipop.applyTheme(drawable2, t);
        }

        public boolean canApplyTheme(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            return DrawableCompatLollipop.canApplyTheme(drawable2);
        }

        public ColorFilter getColorFilter(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            return DrawableCompatLollipop.getColorFilter(drawable2);
        }

        public void clearColorFilter(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            DrawableCompatLollipop.clearColorFilter(drawable2);
        }

        public void inflate(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) throws IOException, XmlPullParserException {
            Drawable drawable2 = drawable;
            Resources res = resources;
            XmlPullParser parser = xmlPullParser;
            AttributeSet attrs = attributeSet;
            Theme t = theme;
            Drawable drawable3 = drawable2;
            Resources resources2 = res;
            XmlPullParser xmlPullParser2 = parser;
            AttributeSet attributeSet2 = attrs;
            Theme theme2 = t;
            DrawableCompatLollipop.inflate(drawable2, res, parser, attrs, t);
        }
    }

    /* renamed from: android.support.v4.graphics.drawable.DrawableCompat$MDrawableImpl */
    static class MDrawableImpl extends LollipopDrawableImpl {
        MDrawableImpl() {
        }

        public boolean setLayoutDirection(Drawable drawable, int i) {
            Drawable drawable2 = drawable;
            int layoutDirection = i;
            Drawable drawable3 = drawable2;
            int i2 = layoutDirection;
            return DrawableCompatApi23.setLayoutDirection(drawable2, layoutDirection);
        }

        public int getLayoutDirection(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            return DrawableCompatApi23.getLayoutDirection(drawable2);
        }

        public Drawable wrap(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            return drawable2;
        }

        public void clearColorFilter(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            drawable2.clearColorFilter();
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 23) {
            IMPL = new MDrawableImpl();
        } else if (version >= 21) {
            IMPL = new LollipopDrawableImpl();
        } else if (version >= 19) {
            IMPL = new KitKatDrawableImpl();
        } else if (version >= 17) {
            IMPL = new JellybeanMr1DrawableImpl();
        } else if (version < 11) {
            IMPL = new BaseDrawableImpl();
        } else {
            IMPL = new HoneycombDrawableImpl();
        }
    }

    public static void jumpToCurrentState(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        IMPL.jumpToCurrentState(drawable2);
    }

    public static void setAutoMirrored(@NonNull Drawable drawable, boolean z) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        IMPL.setAutoMirrored(drawable2, z);
    }

    public static boolean isAutoMirrored(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        return IMPL.isAutoMirrored(drawable2);
    }

    public static void setHotspot(@NonNull Drawable drawable, float f, float f2) {
        Drawable drawable2 = drawable;
        float x = f;
        float y = f2;
        Drawable drawable3 = drawable2;
        float f3 = x;
        float f4 = y;
        IMPL.setHotspot(drawable2, x, y);
    }

    public static void setHotspotBounds(@NonNull Drawable drawable, int i, int i2, int i3, int i4) {
        Drawable drawable2 = drawable;
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        Drawable drawable3 = drawable2;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        IMPL.setHotspotBounds(drawable2, left, top, right, bottom);
    }

    public static void setTint(@NonNull Drawable drawable, @ColorInt int i) {
        Drawable drawable2 = drawable;
        int tint = i;
        Drawable drawable3 = drawable2;
        int i2 = tint;
        IMPL.setTint(drawable2, tint);
    }

    public static void setTintList(@NonNull Drawable drawable, @Nullable ColorStateList colorStateList) {
        Drawable drawable2 = drawable;
        ColorStateList tint = colorStateList;
        Drawable drawable3 = drawable2;
        ColorStateList colorStateList2 = tint;
        IMPL.setTintList(drawable2, tint);
    }

    public static void setTintMode(@NonNull Drawable drawable, @Nullable Mode mode) {
        Drawable drawable2 = drawable;
        Mode tintMode = mode;
        Drawable drawable3 = drawable2;
        Mode mode2 = tintMode;
        IMPL.setTintMode(drawable2, tintMode);
    }

    public static int getAlpha(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        return IMPL.getAlpha(drawable2);
    }

    public static void applyTheme(@NonNull Drawable drawable, @NonNull Theme theme) {
        Drawable drawable2 = drawable;
        Theme t = theme;
        Drawable drawable3 = drawable2;
        Theme theme2 = t;
        IMPL.applyTheme(drawable2, t);
    }

    public static boolean canApplyTheme(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        return IMPL.canApplyTheme(drawable2);
    }

    public static ColorFilter getColorFilter(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        return IMPL.getColorFilter(drawable2);
    }

    public static void clearColorFilter(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        IMPL.clearColorFilter(drawable2);
    }

    public static void inflate(@NonNull Drawable drawable, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Theme theme) throws XmlPullParserException, IOException {
        Drawable drawable2 = drawable;
        Resources res = resources;
        XmlPullParser parser = xmlPullParser;
        AttributeSet attrs = attributeSet;
        Theme theme2 = theme;
        Drawable drawable3 = drawable2;
        Resources resources2 = res;
        XmlPullParser xmlPullParser2 = parser;
        AttributeSet attributeSet2 = attrs;
        Theme theme3 = theme2;
        IMPL.inflate(drawable2, res, parser, attrs, theme2);
    }

    public static Drawable wrap(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        return IMPL.wrap(drawable2);
    }

    public static <T extends Drawable> T unwrap(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (!(drawable2 instanceof DrawableWrapper)) {
            return drawable2;
        }
        return ((DrawableWrapper) drawable2).getWrappedDrawable();
    }

    public static boolean setLayoutDirection(@NonNull Drawable drawable, int i) {
        Drawable drawable2 = drawable;
        int layoutDirection = i;
        Drawable drawable3 = drawable2;
        int i2 = layoutDirection;
        return IMPL.setLayoutDirection(drawable2, layoutDirection);
    }

    public static int getLayoutDirection(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        return IMPL.getLayoutDirection(drawable2);
    }

    private DrawableCompat() {
    }
}
