package android.support.p000v4.graphics.drawable;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.graphics.drawable.DrawableCompatApi23 */
class DrawableCompatApi23 {
    DrawableCompatApi23() {
    }

    public static boolean setLayoutDirection(Drawable drawable, int i) {
        Drawable drawable2 = drawable;
        int layoutDirection = i;
        Drawable drawable3 = drawable2;
        int i2 = layoutDirection;
        return drawable2.setLayoutDirection(layoutDirection);
    }

    public static int getLayoutDirection(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        return drawable2.getLayoutDirection();
    }
}
