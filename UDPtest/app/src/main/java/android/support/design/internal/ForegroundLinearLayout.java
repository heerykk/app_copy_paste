package android.support.design.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.C0001R;
import android.support.p000v4.view.GravityCompat;
import android.support.p003v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ForegroundLinearLayout extends LinearLayoutCompat {
    private Drawable mForeground;
    boolean mForegroundBoundsChanged;
    private int mForegroundGravity;
    protected boolean mForegroundInPadding;
    private final Rect mOverlayBounds;
    private final Rect mSelfBounds;

    public ForegroundLinearLayout(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyle = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyle;
        super(context2, attrs, defStyle);
        this.mSelfBounds = new Rect();
        this.mOverlayBounds = new Rect();
        this.mForegroundGravity = 119;
        this.mForegroundInPadding = true;
        this.mForegroundBoundsChanged = false;
        TypedArray a = context2.obtainStyledAttributes(attrs, C0001R.styleable.ForegroundLinearLayout, defStyle, 0);
        this.mForegroundGravity = a.getInt(C0001R.styleable.ForegroundLinearLayout_android_foregroundGravity, this.mForegroundGravity);
        Drawable drawable = a.getDrawable(C0001R.styleable.ForegroundLinearLayout_android_foreground);
        Drawable d = drawable;
        if (drawable != null) {
            setForeground(d);
        }
        this.mForegroundInPadding = a.getBoolean(C0001R.styleable.ForegroundLinearLayout_foregroundInsidePadding, true);
        a.recycle();
    }

    public ForegroundLinearLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public ForegroundLinearLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public int getForegroundGravity() {
        return this.mForegroundGravity;
    }

    public void setForegroundGravity(int i) {
        int foregroundGravity = i;
        int foregroundGravity2 = foregroundGravity;
        if (this.mForegroundGravity != foregroundGravity) {
            if ((foregroundGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
                foregroundGravity2 = foregroundGravity | GravityCompat.START;
            }
            if ((foregroundGravity2 & 112) == 0) {
                foregroundGravity2 |= 48;
            }
            this.mForegroundGravity = foregroundGravity2;
            if (this.mForegroundGravity == 119 && this.mForeground != null) {
                boolean padding = this.mForeground.getPadding(new Rect());
            }
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        Drawable who = drawable;
        Drawable drawable2 = who;
        return super.verifyDrawable(who) || who == this.mForeground;
    }

    @TargetApi(11)
    @RequiresApi(11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mForeground != null) {
            this.mForeground.jumpToCurrentState();
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mForeground != null && this.mForeground.isStateful()) {
            boolean state = this.mForeground.setState(getDrawableState());
        }
    }

    public void setForeground(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (this.mForeground != drawable2) {
            if (this.mForeground != null) {
                this.mForeground.setCallback(null);
                unscheduleDrawable(this.mForeground);
            }
            this.mForeground = drawable2;
            if (drawable2 == null) {
                setWillNotDraw(true);
            } else {
                setWillNotDraw(false);
                drawable2.setCallback(this);
                if (drawable2.isStateful()) {
                    boolean state = drawable2.setState(getDrawableState());
                }
                if (this.mForegroundGravity == 119) {
                    boolean padding = drawable2.getPadding(new Rect());
                }
            }
            requestLayout();
            invalidate();
        }
    }

    public Drawable getForeground() {
        return this.mForeground;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        boolean changed = z;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        super.onLayout(changed, left, top, right, bottom);
        this.mForegroundBoundsChanged |= changed;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        int w = i;
        int h = i2;
        int oldw = i3;
        int oldh = i4;
        int i5 = w;
        int i6 = h;
        int i7 = oldw;
        int i8 = oldh;
        super.onSizeChanged(w, h, oldw, oldh);
        this.mForegroundBoundsChanged = true;
    }

    public void draw(@NonNull Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        super.draw(canvas2);
        if (this.mForeground != null) {
            Drawable foreground = this.mForeground;
            if (this.mForegroundBoundsChanged) {
                this.mForegroundBoundsChanged = false;
                Rect selfBounds = this.mSelfBounds;
                Rect overlayBounds = this.mOverlayBounds;
                int w = getRight() - getLeft();
                int h = getBottom() - getTop();
                if (!this.mForegroundInPadding) {
                    selfBounds.set(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());
                } else {
                    selfBounds.set(0, 0, w, h);
                }
                Gravity.apply(this.mForegroundGravity, foreground.getIntrinsicWidth(), foreground.getIntrinsicHeight(), selfBounds, overlayBounds);
                foreground.setBounds(overlayBounds);
            }
            foreground.draw(canvas2);
        }
    }

    @TargetApi(21)
    @RequiresApi(21)
    public void drawableHotspotChanged(float f, float f2) {
        float x = f;
        float y = f2;
        float f3 = x;
        float f4 = y;
        super.drawableHotspotChanged(x, y);
        if (this.mForeground != null) {
            this.mForeground.setHotspot(x, y);
        }
    }
}
