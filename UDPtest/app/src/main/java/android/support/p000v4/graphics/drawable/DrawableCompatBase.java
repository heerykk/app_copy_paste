package android.support.p000v4.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.graphics.drawable.DrawableCompatBase */
class DrawableCompatBase {
    DrawableCompatBase() {
    }

    public static void setTint(Drawable drawable, int i) {
        Drawable drawable2 = drawable;
        int tint = i;
        Drawable drawable3 = drawable2;
        int i2 = tint;
        if (drawable2 instanceof TintAwareDrawable) {
            ((TintAwareDrawable) drawable2).setTint(tint);
        }
    }

    public static void setTintList(Drawable drawable, ColorStateList colorStateList) {
        Drawable drawable2 = drawable;
        ColorStateList tint = colorStateList;
        Drawable drawable3 = drawable2;
        ColorStateList colorStateList2 = tint;
        if (drawable2 instanceof TintAwareDrawable) {
            ((TintAwareDrawable) drawable2).setTintList(tint);
        }
    }

    public static void setTintMode(Drawable drawable, Mode mode) {
        Drawable drawable2 = drawable;
        Mode tintMode = mode;
        Drawable drawable3 = drawable2;
        Mode mode2 = tintMode;
        if (drawable2 instanceof TintAwareDrawable) {
            ((TintAwareDrawable) drawable2).setTintMode(tintMode);
        }
    }

    public static Drawable wrapForTinting(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (drawable2 instanceof TintAwareDrawable) {
            return drawable2;
        }
        return new DrawableWrapperGingerbread(drawable2);
    }

    public static void inflate(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) throws IOException, XmlPullParserException {
        Drawable drawable2 = drawable;
        Resources res = resources;
        XmlPullParser parser = xmlPullParser;
        AttributeSet attrs = attributeSet;
        Drawable drawable3 = drawable2;
        Resources resources2 = res;
        XmlPullParser xmlPullParser2 = parser;
        AttributeSet attributeSet2 = attrs;
        Theme theme2 = theme;
        drawable2.inflate(res, parser, attrs);
    }
}
