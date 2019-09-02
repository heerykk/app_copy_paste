package android.support.p003v7.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.graphics.drawable.DrawableCompat;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.graphics.drawable.DrawableWrapper */
public class DrawableWrapper extends Drawable implements Callback {
    private Drawable mDrawable;

    public DrawableWrapper(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        setWrappedDrawable(drawable2);
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        this.mDrawable.draw(canvas2);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Rect bounds = rect;
        Rect rect2 = bounds;
        this.mDrawable.setBounds(bounds);
    }

    public void setChangingConfigurations(int i) {
        int configs = i;
        int i2 = configs;
        this.mDrawable.setChangingConfigurations(configs);
    }

    public int getChangingConfigurations() {
        return this.mDrawable.getChangingConfigurations();
    }

    public void setDither(boolean z) {
        this.mDrawable.setDither(z);
    }

    public void setFilterBitmap(boolean z) {
        this.mDrawable.setFilterBitmap(z);
    }

    public void setAlpha(int i) {
        int alpha = i;
        int i2 = alpha;
        this.mDrawable.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        ColorFilter cf = colorFilter;
        ColorFilter colorFilter2 = cf;
        this.mDrawable.setColorFilter(cf);
    }

    public boolean isStateful() {
        return this.mDrawable.isStateful();
    }

    public boolean setState(int[] iArr) {
        int[] stateSet = iArr;
        int[] iArr2 = stateSet;
        return this.mDrawable.setState(stateSet);
    }

    public int[] getState() {
        return this.mDrawable.getState();
    }

    public void jumpToCurrentState() {
        DrawableCompat.jumpToCurrentState(this.mDrawable);
    }

    public Drawable getCurrent() {
        return this.mDrawable.getCurrent();
    }

    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = z;
        boolean restart = z2;
        return super.setVisible(visible, restart) || this.mDrawable.setVisible(visible, restart);
    }

    public int getOpacity() {
        return this.mDrawable.getOpacity();
    }

    public Region getTransparentRegion() {
        return this.mDrawable.getTransparentRegion();
    }

    public int getIntrinsicWidth() {
        return this.mDrawable.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        return this.mDrawable.getIntrinsicHeight();
    }

    public int getMinimumWidth() {
        return this.mDrawable.getMinimumWidth();
    }

    public int getMinimumHeight() {
        return this.mDrawable.getMinimumHeight();
    }

    public boolean getPadding(Rect rect) {
        Rect padding = rect;
        Rect rect2 = padding;
        return this.mDrawable.getPadding(padding);
    }

    public void invalidateDrawable(Drawable drawable) {
        Drawable drawable2 = drawable;
        invalidateSelf();
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Runnable what = runnable;
        long when = j;
        Drawable drawable2 = drawable;
        Runnable runnable2 = what;
        long j2 = when;
        scheduleSelf(what, when);
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Runnable what = runnable;
        Drawable drawable2 = drawable;
        Runnable runnable2 = what;
        unscheduleSelf(what);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i) {
        int level = i;
        int i2 = level;
        return this.mDrawable.setLevel(level);
    }

    public void setAutoMirrored(boolean z) {
        DrawableCompat.setAutoMirrored(this.mDrawable, z);
    }

    public boolean isAutoMirrored() {
        return DrawableCompat.isAutoMirrored(this.mDrawable);
    }

    public void setTint(int i) {
        int tint = i;
        int i2 = tint;
        DrawableCompat.setTint(this.mDrawable, tint);
    }

    public void setTintList(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        DrawableCompat.setTintList(this.mDrawable, tint);
    }

    public void setTintMode(Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        DrawableCompat.setTintMode(this.mDrawable, tintMode);
    }

    public void setHotspot(float f, float f2) {
        float x = f;
        float y = f2;
        float f3 = x;
        float f4 = y;
        DrawableCompat.setHotspot(this.mDrawable, x, y);
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
        DrawableCompat.setHotspotBounds(this.mDrawable, left, top, right, bottom);
    }

    public Drawable getWrappedDrawable() {
        return this.mDrawable;
    }

    public void setWrappedDrawable(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        this.mDrawable = drawable2;
        if (drawable2 != null) {
            drawable2.setCallback(this);
        }
    }
}
