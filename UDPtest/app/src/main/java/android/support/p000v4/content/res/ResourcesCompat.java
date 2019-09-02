package android.support.p000v4.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* renamed from: android.support.v4.content.res.ResourcesCompat */
public final class ResourcesCompat {
    @Nullable
    public static Drawable getDrawable(@NonNull Resources resources, @DrawableRes int i, @Nullable Theme theme) throws NotFoundException {
        Resources res = resources;
        int id = i;
        Theme theme2 = theme;
        Resources resources2 = res;
        int i2 = id;
        Theme theme3 = theme2;
        if (VERSION.SDK_INT < 21) {
            return res.getDrawable(id);
        }
        return ResourcesCompatApi21.getDrawable(res, id, theme2);
    }

    @Nullable
    public static Drawable getDrawableForDensity(@NonNull Resources resources, @DrawableRes int i, int i2, @Nullable Theme theme) throws NotFoundException {
        Resources res = resources;
        int id = i;
        int density = i2;
        Theme theme2 = theme;
        Resources resources2 = res;
        int i3 = id;
        int i4 = density;
        Theme theme3 = theme2;
        if (VERSION.SDK_INT >= 21) {
            return ResourcesCompatApi21.getDrawableForDensity(res, id, density, theme2);
        }
        if (VERSION.SDK_INT < 15) {
            return res.getDrawable(id);
        }
        return ResourcesCompatIcsMr1.getDrawableForDensity(res, id, density);
    }

    @ColorInt
    public static int getColor(@NonNull Resources resources, @ColorRes int i, @Nullable Theme theme) throws NotFoundException {
        Resources res = resources;
        int id = i;
        Theme theme2 = theme;
        Resources resources2 = res;
        int i2 = id;
        Theme theme3 = theme2;
        if (VERSION.SDK_INT < 23) {
            return res.getColor(id);
        }
        return ResourcesCompatApi23.getColor(res, id, theme2);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Resources resources, @ColorRes int i, @Nullable Theme theme) throws NotFoundException {
        Resources res = resources;
        int id = i;
        Theme theme2 = theme;
        Resources resources2 = res;
        int i2 = id;
        Theme theme3 = theme2;
        if (VERSION.SDK_INT < 23) {
            return res.getColorStateList(id);
        }
        return ResourcesCompatApi23.getColorStateList(res, id, theme2);
    }

    private ResourcesCompat() {
    }
}
