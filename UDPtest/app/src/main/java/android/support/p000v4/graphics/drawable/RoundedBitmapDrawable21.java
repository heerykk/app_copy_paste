package android.support.p000v4.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.Gravity;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.graphics.drawable.RoundedBitmapDrawable21 */
class RoundedBitmapDrawable21 extends RoundedBitmapDrawable {
    protected RoundedBitmapDrawable21(Resources resources, Bitmap bitmap) {
        Resources res = resources;
        Bitmap bitmap2 = bitmap;
        Resources resources2 = res;
        Bitmap bitmap3 = bitmap2;
        super(res, bitmap2);
    }

    public void getOutline(Outline outline) {
        Outline outline2 = outline;
        Outline outline3 = outline2;
        updateDstRect();
        outline2.setRoundRect(this.mDstRect, getCornerRadius());
    }

    public void setMipMap(boolean z) {
        boolean mipMap = z;
        if (this.mBitmap != null) {
            this.mBitmap.setHasMipMap(mipMap);
            invalidateSelf();
        }
    }

    public boolean hasMipMap() {
        return this.mBitmap != null && this.mBitmap.hasMipMap();
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
        Gravity.apply(gravity, bitmapWidth, bitmapHeight, bounds, outRect, 0);
    }
}
