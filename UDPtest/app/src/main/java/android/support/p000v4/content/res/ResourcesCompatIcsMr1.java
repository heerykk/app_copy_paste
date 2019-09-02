package android.support.p000v4.content.res;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;

@TargetApi(15)
@RequiresApi(15)
/* renamed from: android.support.v4.content.res.ResourcesCompatIcsMr1 */
class ResourcesCompatIcsMr1 {
    ResourcesCompatIcsMr1() {
    }

    public static Drawable getDrawableForDensity(Resources resources, int i, int i2) throws NotFoundException {
        Resources res = resources;
        int id = i;
        int density = i2;
        Resources resources2 = res;
        int i3 = id;
        int i4 = density;
        return res.getDrawableForDensity(id, density);
    }
}
