package android.support.p000v4.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.InsetDrawable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.graphics.drawable.DrawableCompatLollipop */
class DrawableCompatLollipop {
    DrawableCompatLollipop() {
    }

    public static void setHotspot(Drawable drawable, float f, float f2) {
        Drawable drawable2 = drawable;
        float x = f;
        float y = f2;
        Drawable drawable3 = drawable2;
        float f3 = x;
        float f4 = y;
        drawable2.setHotspot(x, y);
    }

    public static void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4) {
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
        drawable2.setHotspotBounds(left, top, right, bottom);
    }

    public static void setTint(Drawable drawable, int i) {
        Drawable drawable2 = drawable;
        int tint = i;
        Drawable drawable3 = drawable2;
        int i2 = tint;
        drawable2.setTint(tint);
    }

    public static void setTintList(Drawable drawable, ColorStateList colorStateList) {
        Drawable drawable2 = drawable;
        ColorStateList tint = colorStateList;
        Drawable drawable3 = drawable2;
        ColorStateList colorStateList2 = tint;
        drawable2.setTintList(tint);
    }

    public static void setTintMode(Drawable drawable, Mode mode) {
        Drawable drawable2 = drawable;
        Mode tintMode = mode;
        Drawable drawable3 = drawable2;
        Mode mode2 = tintMode;
        drawable2.setTintMode(tintMode);
    }

    public static Drawable wrapForTinting(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (drawable2 instanceof TintAwareDrawable) {
            return drawable2;
        }
        return new DrawableWrapperLollipop(drawable2);
    }

    public static void applyTheme(Drawable drawable, Theme theme) {
        Drawable drawable2 = drawable;
        Theme t = theme;
        Drawable drawable3 = drawable2;
        Theme theme2 = t;
        drawable2.applyTheme(t);
    }

    public static boolean canApplyTheme(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        return drawable2.canApplyTheme();
    }

    public static ColorFilter getColorFilter(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        return drawable2.getColorFilter();
    }

    public static void clearColorFilter(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        drawable2.clearColorFilter();
        if (drawable2 instanceof InsetDrawable) {
            clearColorFilter(((InsetDrawable) drawable2).getDrawable());
        } else if (drawable2 instanceof DrawableWrapper) {
            clearColorFilter(((DrawableWrapper) drawable2).getWrappedDrawable());
        } else if (drawable2 instanceof DrawableContainer) {
            DrawableContainer drawableContainer = (DrawableContainer) drawable2;
            DrawableContainer drawableContainer2 = drawableContainer;
            DrawableContainerState drawableContainerState = (DrawableContainerState) drawableContainer.getConstantState();
            DrawableContainerState state = drawableContainerState;
            if (drawableContainerState != null) {
                int count = state.getChildCount();
                for (int i = 0; i < count; i++) {
                    Drawable child = state.getChild(i);
                    Drawable child2 = child;
                    if (child != null) {
                        clearColorFilter(child2);
                    }
                }
            }
        }
    }

    public static void inflate(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) throws IOException, XmlPullParserException {
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
        drawable2.inflate(res, parser, attrs, t);
    }
}
