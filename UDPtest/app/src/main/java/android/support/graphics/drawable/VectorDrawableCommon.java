package android.support.graphics.drawable;

import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.graphics.drawable.TintAwareDrawable;
import android.util.AttributeSet;

abstract class VectorDrawableCommon extends Drawable implements TintAwareDrawable {
    Drawable mDelegateDrawable;

    VectorDrawableCommon() {
    }

    protected static TypedArray obtainAttributes(Resources resources, Theme theme, AttributeSet attributeSet, int[] iArr) {
        Resources res = resources;
        Theme theme2 = theme;
        AttributeSet set = attributeSet;
        int[] attrs = iArr;
        Resources resources2 = res;
        Theme theme3 = theme2;
        AttributeSet attributeSet2 = set;
        int[] iArr2 = attrs;
        if (theme2 != null) {
            return theme2.obtainStyledAttributes(set, attrs, 0, 0);
        }
        return res.obtainAttributes(set, attrs);
    }

    public void setColorFilter(int i, Mode mode) {
        int color = i;
        Mode mode2 = mode;
        int i2 = color;
        Mode mode3 = mode2;
        if (this.mDelegateDrawable == null) {
            super.setColorFilter(color, mode2);
        } else {
            this.mDelegateDrawable.setColorFilter(color, mode2);
        }
    }

    public ColorFilter getColorFilter() {
        if (this.mDelegateDrawable == null) {
            return null;
        }
        return DrawableCompat.getColorFilter(this.mDelegateDrawable);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i) {
        int level = i;
        int i2 = level;
        if (this.mDelegateDrawable == null) {
            return super.onLevelChange(level);
        }
        return this.mDelegateDrawable.setLevel(level);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Rect bounds = rect;
        Rect rect2 = bounds;
        if (this.mDelegateDrawable == null) {
            super.onBoundsChange(bounds);
        } else {
            this.mDelegateDrawable.setBounds(bounds);
        }
    }

    public void setHotspot(float f, float f2) {
        float x = f;
        float y = f2;
        float f3 = x;
        float f4 = y;
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setHotspot(this.mDelegateDrawable, x, y);
        }
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
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setHotspotBounds(this.mDelegateDrawable, left, top, right, bottom);
        }
    }

    public void setFilterBitmap(boolean z) {
        boolean filter = z;
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setFilterBitmap(filter);
        }
    }

    public void jumpToCurrentState() {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.jumpToCurrentState(this.mDelegateDrawable);
        }
    }

    public void applyTheme(Theme theme) {
        Theme t = theme;
        Theme theme2 = t;
        if (this.mDelegateDrawable != null) {
            DrawableCompat.applyTheme(this.mDelegateDrawable, t);
        }
    }

    public void clearColorFilter() {
        if (this.mDelegateDrawable == null) {
            super.clearColorFilter();
        } else {
            this.mDelegateDrawable.clearColorFilter();
        }
    }

    public Drawable getCurrent() {
        if (this.mDelegateDrawable == null) {
            return super.getCurrent();
        }
        return this.mDelegateDrawable.getCurrent();
    }

    public int getMinimumWidth() {
        if (this.mDelegateDrawable == null) {
            return super.getMinimumWidth();
        }
        return this.mDelegateDrawable.getMinimumWidth();
    }

    public int getMinimumHeight() {
        if (this.mDelegateDrawable == null) {
            return super.getMinimumHeight();
        }
        return this.mDelegateDrawable.getMinimumHeight();
    }

    public boolean getPadding(Rect rect) {
        Rect padding = rect;
        Rect rect2 = padding;
        if (this.mDelegateDrawable == null) {
            return super.getPadding(padding);
        }
        return this.mDelegateDrawable.getPadding(padding);
    }

    public int[] getState() {
        if (this.mDelegateDrawable == null) {
            return super.getState();
        }
        return this.mDelegateDrawable.getState();
    }

    public Region getTransparentRegion() {
        if (this.mDelegateDrawable == null) {
            return super.getTransparentRegion();
        }
        return this.mDelegateDrawable.getTransparentRegion();
    }

    public void setChangingConfigurations(int i) {
        int configs = i;
        int i2 = configs;
        if (this.mDelegateDrawable == null) {
            super.setChangingConfigurations(configs);
        } else {
            this.mDelegateDrawable.setChangingConfigurations(configs);
        }
    }

    public boolean setState(int[] iArr) {
        int[] stateSet = iArr;
        int[] iArr2 = stateSet;
        if (this.mDelegateDrawable == null) {
            return super.setState(stateSet);
        }
        return this.mDelegateDrawable.setState(stateSet);
    }
}
