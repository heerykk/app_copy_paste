package android.support.p000v4.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.graphics.drawable.DrawableWrapperGingerbread */
class DrawableWrapperGingerbread extends Drawable implements Callback, DrawableWrapper, TintAwareDrawable {
    static final Mode DEFAULT_TINT_MODE = Mode.SRC_IN;
    private boolean mColorFilterSet;
    private int mCurrentColor;
    private Mode mCurrentMode;
    Drawable mDrawable;
    private boolean mMutated;
    DrawableWrapperState mState;

    /* renamed from: android.support.v4.graphics.drawable.DrawableWrapperGingerbread$DrawableWrapperState */
    protected static abstract class DrawableWrapperState extends ConstantState {
        int mChangingConfigurations;
        ConstantState mDrawableState;
        ColorStateList mTint = null;
        Mode mTintMode = DrawableWrapperGingerbread.DEFAULT_TINT_MODE;

        public abstract Drawable newDrawable(@Nullable Resources resources);

        DrawableWrapperState(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            DrawableWrapperState orig = drawableWrapperState;
            DrawableWrapperState drawableWrapperState2 = orig;
            Resources resources2 = resources;
            if (orig != null) {
                this.mChangingConfigurations = orig.mChangingConfigurations;
                this.mDrawableState = orig.mDrawableState;
                this.mTint = orig.mTint;
                this.mTintMode = orig.mTintMode;
            }
        }

        public Drawable newDrawable() {
            return newDrawable(null);
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations | (this.mDrawableState == null ? 0 : this.mDrawableState.getChangingConfigurations());
        }

        /* access modifiers changed from: 0000 */
        public boolean canConstantState() {
            return this.mDrawableState != null;
        }
    }

    /* renamed from: android.support.v4.graphics.drawable.DrawableWrapperGingerbread$DrawableWrapperStateBase */
    private static class DrawableWrapperStateBase extends DrawableWrapperState {
        DrawableWrapperStateBase(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            DrawableWrapperState orig = drawableWrapperState;
            Resources res = resources;
            DrawableWrapperState drawableWrapperState2 = orig;
            Resources resources2 = res;
            super(orig, res);
        }

        public Drawable newDrawable(@Nullable Resources resources) {
            Resources res = resources;
            Resources resources2 = res;
            return new DrawableWrapperGingerbread(this, res);
        }
    }

    DrawableWrapperGingerbread(@NonNull DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
        DrawableWrapperState state = drawableWrapperState;
        Resources res = resources;
        DrawableWrapperState drawableWrapperState2 = state;
        Resources resources2 = res;
        this.mState = state;
        updateLocalState(res);
    }

    DrawableWrapperGingerbread(@Nullable Drawable drawable) {
        Drawable dr = drawable;
        Drawable drawable2 = dr;
        this.mState = mutateConstantState();
        setWrappedDrawable(dr);
    }

    private void updateLocalState(@Nullable Resources resources) {
        Resources res = resources;
        Resources resources2 = res;
        if (this.mState != null && this.mState.mDrawableState != null) {
            setWrappedDrawable(newDrawableFromState(this.mState.mDrawableState, res));
        }
    }

    /* access modifiers changed from: protected */
    public Drawable newDrawableFromState(@NonNull ConstantState constantState, @Nullable Resources resources) {
        ConstantState state = constantState;
        Resources res = resources;
        ConstantState constantState2 = state;
        Resources resources2 = res;
        return state.newDrawable(res);
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
        if (this.mDrawable != null) {
            this.mDrawable.setBounds(bounds);
        }
    }

    public void setChangingConfigurations(int i) {
        int configs = i;
        int i2 = configs;
        this.mDrawable.setChangingConfigurations(configs);
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | (this.mState == null ? 0 : this.mState.getChangingConfigurations()) | this.mDrawable.getChangingConfigurations();
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
        ColorStateList tintList = (isCompatTintEnabled() && this.mState != null) ? this.mState.mTint : null;
        return (tintList != null && tintList.isStateful()) || this.mDrawable.isStateful();
    }

    public boolean setState(int[] iArr) {
        int[] stateSet = iArr;
        int[] iArr2 = stateSet;
        return updateTint(stateSet) || this.mDrawable.setState(stateSet);
    }

    public int[] getState() {
        return this.mDrawable.getState();
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

    @Nullable
    public ConstantState getConstantState() {
        if (this.mState == null || !this.mState.canConstantState()) {
            return null;
        }
        this.mState.mChangingConfigurations = getChangingConfigurations();
        return this.mState;
    }

    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = mutateConstantState();
            if (this.mDrawable != null) {
                Drawable mutate = this.mDrawable.mutate();
            }
            if (this.mState != null) {
                this.mState.mDrawableState = this.mDrawable == null ? null : this.mDrawable.getConstantState();
            }
            this.mMutated = true;
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateBase(this.mState, null);
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

    public void setTint(int i) {
        int tint = i;
        int i2 = tint;
        setTintList(ColorStateList.valueOf(tint));
    }

    public void setTintList(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        this.mState.mTint = tint;
        boolean updateTint = updateTint(getState());
    }

    public void setTintMode(Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        this.mState.mTintMode = tintMode;
        boolean updateTint = updateTint(getState());
    }

    private boolean updateTint(int[] iArr) {
        int[] state = iArr;
        int[] iArr2 = state;
        if (!isCompatTintEnabled()) {
            return false;
        }
        ColorStateList tintList = this.mState.mTint;
        Mode tintMode = this.mState.mTintMode;
        if (tintList == null || tintMode == null) {
            this.mColorFilterSet = false;
            clearColorFilter();
        } else {
            int color = tintList.getColorForState(state, tintList.getDefaultColor());
            if (!(this.mColorFilterSet && color == this.mCurrentColor && tintMode == this.mCurrentMode)) {
                setColorFilter(color, tintMode);
                this.mCurrentColor = color;
                this.mCurrentMode = tintMode;
                this.mColorFilterSet = true;
                return true;
            }
        }
        return false;
    }

    public final Drawable getWrappedDrawable() {
        return this.mDrawable;
    }

    public final void setWrappedDrawable(Drawable drawable) {
        Drawable dr = drawable;
        Drawable drawable2 = dr;
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        this.mDrawable = dr;
        if (dr != null) {
            dr.setCallback(this);
            boolean visible = setVisible(dr.isVisible(), true);
            boolean state = setState(dr.getState());
            boolean level = setLevel(dr.getLevel());
            setBounds(dr.getBounds());
            if (this.mState != null) {
                this.mState.mDrawableState = dr.getConstantState();
            }
        }
        invalidateSelf();
    }

    /* access modifiers changed from: protected */
    public boolean isCompatTintEnabled() {
        return true;
    }
}
