package android.support.p000v4.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.graphics.drawable.DrawableWrapperLollipop */
class DrawableWrapperLollipop extends DrawableWrapperKitKat {

    /* renamed from: android.support.v4.graphics.drawable.DrawableWrapperLollipop$DrawableWrapperStateLollipop */
    private static class DrawableWrapperStateLollipop extends DrawableWrapperState {
        DrawableWrapperStateLollipop(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            DrawableWrapperState orig = drawableWrapperState;
            Resources res = resources;
            DrawableWrapperState drawableWrapperState2 = orig;
            Resources resources2 = res;
            super(orig, res);
        }

        public Drawable newDrawable(@Nullable Resources resources) {
            Resources res = resources;
            Resources resources2 = res;
            return new DrawableWrapperLollipop(this, res);
        }
    }

    DrawableWrapperLollipop(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        super(drawable2);
    }

    DrawableWrapperLollipop(DrawableWrapperState drawableWrapperState, Resources resources) {
        DrawableWrapperState state = drawableWrapperState;
        Resources resources2 = resources;
        DrawableWrapperState drawableWrapperState2 = state;
        Resources resources3 = resources2;
        super(state, resources2);
    }

    public void setHotspot(float f, float f2) {
        float x = f;
        float y = f2;
        float f3 = x;
        float f4 = y;
        this.mDrawable.setHotspot(x, y);
    }

    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        this.mDrawable.setHotspotBounds(left, top, right, bottom);
    }

    public void getOutline(Outline outline) {
        Outline outline2 = outline;
        Outline outline3 = outline2;
        this.mDrawable.getOutline(outline2);
    }

    public Rect getDirtyBounds() {
        return this.mDrawable.getDirtyBounds();
    }

    public void setTintList(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        if (!isCompatTintEnabled()) {
            this.mDrawable.setTintList(tint);
        } else {
            super.setTintList(tint);
        }
    }

    public void setTint(int i) {
        int tintColor = i;
        int i2 = tintColor;
        if (!isCompatTintEnabled()) {
            this.mDrawable.setTint(tintColor);
        } else {
            super.setTint(tintColor);
        }
    }

    public void setTintMode(Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        if (!isCompatTintEnabled()) {
            this.mDrawable.setTintMode(tintMode);
        } else {
            super.setTintMode(tintMode);
        }
    }

    public boolean setState(int[] iArr) {
        int[] stateSet = iArr;
        int[] iArr2 = stateSet;
        if (!super.setState(stateSet)) {
            return false;
        }
        invalidateSelf();
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isCompatTintEnabled() {
        if (VERSION.SDK_INT != 21) {
            return false;
        }
        Drawable drawable = this.mDrawable;
        Drawable drawable2 = drawable;
        return (drawable instanceof GradientDrawable) || (drawable2 instanceof DrawableContainer) || (drawable2 instanceof InsetDrawable);
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateLollipop(this.mState, null);
    }
}
