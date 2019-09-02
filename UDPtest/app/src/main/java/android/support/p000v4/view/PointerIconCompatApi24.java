package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.RequiresApi;
import android.view.PointerIcon;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.view.PointerIconCompatApi24 */
class PointerIconCompatApi24 {
    PointerIconCompatApi24() {
    }

    public static Object getSystemIcon(Context context, int i) {
        Context context2 = context;
        int style = i;
        Context context3 = context2;
        int i2 = style;
        return PointerIcon.getSystemIcon(context2, style);
    }

    public static Object create(Bitmap bitmap, float f, float f2) {
        Bitmap bitmap2 = bitmap;
        float hotSpotX = f;
        float hotSpotY = f2;
        Bitmap bitmap3 = bitmap2;
        float f3 = hotSpotX;
        float f4 = hotSpotY;
        return PointerIcon.create(bitmap2, hotSpotX, hotSpotY);
    }

    public static Object load(Resources resources, int i) {
        Resources resources2 = resources;
        int resourceId = i;
        Resources resources3 = resources2;
        int i2 = resourceId;
        return PointerIcon.load(resources2, resourceId);
    }
}
