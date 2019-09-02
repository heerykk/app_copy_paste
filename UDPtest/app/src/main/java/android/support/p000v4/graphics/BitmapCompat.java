package android.support.p000v4.graphics;

import android.graphics.Bitmap;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.graphics.BitmapCompat */
public final class BitmapCompat {
    static final BitmapImpl IMPL;

    /* renamed from: android.support.v4.graphics.BitmapCompat$BaseBitmapImpl */
    static class BaseBitmapImpl implements BitmapImpl {
        BaseBitmapImpl() {
        }

        public boolean hasMipMap(Bitmap bitmap) {
            Bitmap bitmap2 = bitmap;
            return false;
        }

        public void setHasMipMap(Bitmap bitmap, boolean z) {
            Bitmap bitmap2 = bitmap;
            boolean z2 = z;
        }

        public int getAllocationByteCount(Bitmap bitmap) {
            Bitmap bitmap2 = bitmap;
            Bitmap bitmap3 = bitmap2;
            return bitmap2.getRowBytes() * bitmap2.getHeight();
        }
    }

    /* renamed from: android.support.v4.graphics.BitmapCompat$BitmapImpl */
    interface BitmapImpl {
        int getAllocationByteCount(Bitmap bitmap);

        boolean hasMipMap(Bitmap bitmap);

        void setHasMipMap(Bitmap bitmap, boolean z);
    }

    /* renamed from: android.support.v4.graphics.BitmapCompat$HcMr1BitmapCompatImpl */
    static class HcMr1BitmapCompatImpl extends BaseBitmapImpl {
        HcMr1BitmapCompatImpl() {
        }

        public int getAllocationByteCount(Bitmap bitmap) {
            Bitmap bitmap2 = bitmap;
            Bitmap bitmap3 = bitmap2;
            return BitmapCompatHoneycombMr1.getAllocationByteCount(bitmap2);
        }
    }

    /* renamed from: android.support.v4.graphics.BitmapCompat$JbMr2BitmapCompatImpl */
    static class JbMr2BitmapCompatImpl extends HcMr1BitmapCompatImpl {
        JbMr2BitmapCompatImpl() {
        }

        public boolean hasMipMap(Bitmap bitmap) {
            Bitmap bitmap2 = bitmap;
            Bitmap bitmap3 = bitmap2;
            return BitmapCompatJellybeanMR2.hasMipMap(bitmap2);
        }

        public void setHasMipMap(Bitmap bitmap, boolean z) {
            Bitmap bitmap2 = bitmap;
            Bitmap bitmap3 = bitmap2;
            BitmapCompatJellybeanMR2.setHasMipMap(bitmap2, z);
        }
    }

    /* renamed from: android.support.v4.graphics.BitmapCompat$KitKatBitmapCompatImpl */
    static class KitKatBitmapCompatImpl extends JbMr2BitmapCompatImpl {
        KitKatBitmapCompatImpl() {
        }

        public int getAllocationByteCount(Bitmap bitmap) {
            Bitmap bitmap2 = bitmap;
            Bitmap bitmap3 = bitmap2;
            return BitmapCompatKitKat.getAllocationByteCount(bitmap2);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 19) {
            IMPL = new KitKatBitmapCompatImpl();
        } else if (version >= 18) {
            IMPL = new JbMr2BitmapCompatImpl();
        } else if (version < 12) {
            IMPL = new BaseBitmapImpl();
        } else {
            IMPL = new HcMr1BitmapCompatImpl();
        }
    }

    public static boolean hasMipMap(Bitmap bitmap) {
        Bitmap bitmap2 = bitmap;
        Bitmap bitmap3 = bitmap2;
        return IMPL.hasMipMap(bitmap2);
    }

    public static void setHasMipMap(Bitmap bitmap, boolean z) {
        Bitmap bitmap2 = bitmap;
        Bitmap bitmap3 = bitmap2;
        IMPL.setHasMipMap(bitmap2, z);
    }

    public static int getAllocationByteCount(Bitmap bitmap) {
        Bitmap bitmap2 = bitmap;
        Bitmap bitmap3 = bitmap2;
        return IMPL.getAllocationByteCount(bitmap2);
    }

    private BitmapCompat() {
    }
}
