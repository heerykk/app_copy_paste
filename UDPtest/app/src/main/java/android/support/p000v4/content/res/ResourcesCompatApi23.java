package android.support.p000v4.content.res;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.content.res.ResourcesCompatApi23 */
class ResourcesCompatApi23 {
    ResourcesCompatApi23() {
    }

    public static int getColor(Resources resources, int i, Theme theme) throws NotFoundException {
        Resources res = resources;
        int id = i;
        Theme theme2 = theme;
        Resources resources2 = res;
        int i2 = id;
        Theme theme3 = theme2;
        return res.getColor(id, theme2);
    }

    public static ColorStateList getColorStateList(Resources resources, int i, Theme theme) throws NotFoundException {
        Resources res = resources;
        int id = i;
        Theme theme2 = theme;
        Resources resources2 = res;
        int i2 = id;
        Theme theme3 = theme2;
        return res.getColorStateList(id, theme2);
    }
}
