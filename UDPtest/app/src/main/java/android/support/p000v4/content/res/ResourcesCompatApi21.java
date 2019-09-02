package android.support.p000v4.content.res;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.content.res.ResourcesCompatApi21 */
class ResourcesCompatApi21 {
    ResourcesCompatApi21() {
    }

    public static Drawable getDrawable(Resources resources, int i, Theme theme) throws NotFoundException {
        Resources res = resources;
        int id = i;
        Theme theme2 = theme;
        Resources resources2 = res;
        int i2 = id;
        Theme theme3 = theme2;
        return res.getDrawable(id, theme2);
    }

    public static Drawable getDrawableForDensity(Resources resources, int i, int i2, Theme theme) throws NotFoundException {
        Resources res = resources;
        int id = i;
        int density = i2;
        Theme theme2 = theme;
        Resources resources2 = res;
        int i3 = id;
        int i4 = density;
        Theme theme3 = theme2;
        return res.getDrawableForDensity(id, density, theme2);
    }
}
