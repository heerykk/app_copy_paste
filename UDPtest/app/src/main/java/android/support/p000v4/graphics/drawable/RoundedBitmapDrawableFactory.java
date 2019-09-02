package android.support.p000v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.p000v4.graphics.BitmapCompat;
import android.support.p000v4.view.GravityCompat;
import android.util.Log;
import java.io.InputStream;

/* renamed from: android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory */
public final class RoundedBitmapDrawableFactory {
    private static final String TAG = "RoundedBitmapDrawableFactory";

    /* renamed from: android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory$DefaultRoundedBitmapDrawable */
    private static class DefaultRoundedBitmapDrawable extends RoundedBitmapDrawable {
        DefaultRoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
            Resources res = resources;
            Bitmap bitmap2 = bitmap;
            Resources resources2 = res;
            Bitmap bitmap3 = bitmap2;
            super(res, bitmap2);
        }

        public void setMipMap(boolean z) {
            boolean mipMap = z;
            if (this.mBitmap != null) {
                BitmapCompat.setHasMipMap(this.mBitmap, mipMap);
                invalidateSelf();
            }
        }

        public boolean hasMipMap() {
            return this.mBitmap != null && BitmapCompat.hasMipMap(this.mBitmap);
        }

        /* access modifiers changed from: 0000 */
        public void gravityCompatApply(int i, int i2, int i3, Rect rect, Rect rect2) {
            int gravity = i;
            int bitmapWidth = i2;
            int bitmapHeight = i3;
            Rect bounds = rect;
            Rect outRect = rect2;
            int i4 = gravity;
            int i5 = bitmapWidth;
            int i6 = bitmapHeight;
            Rect rect3 = bounds;
            Rect rect4 = outRect;
            GravityCompat.apply(gravity, bitmapWidth, bitmapHeight, bounds, outRect, 0);
        }
    }

    public static RoundedBitmapDrawable create(Resources resources, Bitmap bitmap) {
        Resources res = resources;
        Bitmap bitmap2 = bitmap;
        Resources resources2 = res;
        Bitmap bitmap3 = bitmap2;
        if (VERSION.SDK_INT < 21) {
            return new DefaultRoundedBitmapDrawable(res, bitmap2);
        }
        return new RoundedBitmapDrawable21(res, bitmap2);
    }

    public static RoundedBitmapDrawable create(Resources resources, String str) {
        Resources res = resources;
        String filepath = str;
        Resources resources2 = res;
        String str2 = filepath;
        RoundedBitmapDrawable create = create(res, BitmapFactory.decodeFile(filepath));
        RoundedBitmapDrawable drawable = create;
        if (create.getBitmap() == null) {
            int w = Log.w(TAG, "RoundedBitmapDrawable cannot decode " + filepath);
        }
        return drawable;
    }

    public static RoundedBitmapDrawable create(Resources resources, InputStream inputStream) {
        Resources res = resources;
        InputStream is = inputStream;
        Resources resources2 = res;
        InputStream inputStream2 = is;
        RoundedBitmapDrawable create = create(res, BitmapFactory.decodeStream(is));
        RoundedBitmapDrawable drawable = create;
        if (create.getBitmap() == null) {
            int w = Log.w(TAG, "RoundedBitmapDrawable cannot decode " + is);
        }
        return drawable;
    }

    private RoundedBitmapDrawableFactory() {
    }
}
