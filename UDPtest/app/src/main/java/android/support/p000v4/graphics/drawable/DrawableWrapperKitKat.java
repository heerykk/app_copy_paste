package android.support.p000v4.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.graphics.drawable.DrawableWrapperKitKat */
class DrawableWrapperKitKat extends DrawableWrapperHoneycomb {

    /* renamed from: android.support.v4.graphics.drawable.DrawableWrapperKitKat$DrawableWrapperStateKitKat */
    private static class DrawableWrapperStateKitKat extends DrawableWrapperState {
        DrawableWrapperStateKitKat(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            DrawableWrapperState orig = drawableWrapperState;
            Resources res = resources;
            DrawableWrapperState drawableWrapperState2 = orig;
            Resources resources2 = res;
            super(orig, res);
        }

        public Drawable newDrawable(@Nullable Resources resources) {
            Resources res = resources;
            Resources resources2 = res;
            return new DrawableWrapperKitKat(this, res);
        }
    }

    DrawableWrapperKitKat(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        super(drawable2);
    }

    DrawableWrapperKitKat(DrawableWrapperState drawableWrapperState, Resources resources) {
        DrawableWrapperState state = drawableWrapperState;
        Resources resources2 = resources;
        DrawableWrapperState drawableWrapperState2 = state;
        Resources resources3 = resources2;
        super(state, resources2);
    }

    public void setAutoMirrored(boolean z) {
        this.mDrawable.setAutoMirrored(z);
    }

    public boolean isAutoMirrored() {
        return this.mDrawable.isAutoMirrored();
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateKitKat(this.mState, null);
    }
}
