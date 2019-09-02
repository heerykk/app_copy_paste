package android.support.p000v4.graphics;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.support.annotation.RequiresApi;

@TargetApi(18)
@RequiresApi(18)
/* renamed from: android.support.v4.graphics.BitmapCompatJellybeanMR2 */
class BitmapCompatJellybeanMR2 {
    BitmapCompatJellybeanMR2() {
    }

    public static boolean hasMipMap(Bitmap bitmap) {
        Bitmap bitmap2 = bitmap;
        Bitmap bitmap3 = bitmap2;
        return bitmap2.hasMipMap();
    }

    public static void setHasMipMap(Bitmap bitmap, boolean z) {
        Bitmap bitmap2 = bitmap;
        Bitmap bitmap3 = bitmap2;
        bitmap2.setHasMipMap(z);
    }
}
