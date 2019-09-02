package android.support.p000v4.graphics.drawable;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.graphics.drawable.DrawableCompatKitKat */
class DrawableCompatKitKat {
    DrawableCompatKitKat() {
    }

    public static void setAutoMirrored(Drawable drawable, boolean z) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        drawable2.setAutoMirrored(z);
    }

    public static boolean isAutoMirrored(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        return drawable2.isAutoMirrored();
    }

    public static Drawable wrapForTinting(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (drawable2 instanceof TintAwareDrawable) {
            return drawable2;
        }
        return new DrawableWrapperKitKat(drawable2);
    }

    public static int getAlpha(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        return drawable2.getAlpha();
    }
}
