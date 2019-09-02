package android.support.p000v4.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

/* renamed from: android.support.v4.widget.ScrollerCompat */
public final class ScrollerCompat {
    private final boolean mIsIcsOrNewer;
    OverScroller mScroller;

    public static ScrollerCompat create(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return create(context2, null);
    }

    public static ScrollerCompat create(Context context, Interpolator interpolator) {
        Context context2 = context;
        Interpolator interpolator2 = interpolator;
        Context context3 = context2;
        Interpolator interpolator3 = interpolator2;
        return new ScrollerCompat(VERSION.SDK_INT >= 14, context2, interpolator2);
    }

    ScrollerCompat(boolean z, Context context, Interpolator interpolator) {
        Context context2 = context;
        Interpolator interpolator2 = interpolator;
        Context context3 = context2;
        Interpolator interpolator3 = interpolator2;
        this.mIsIcsOrNewer = z;
        this.mScroller = interpolator2 == null ? new OverScroller(context2) : new OverScroller(context2, interpolator2);
    }

    public boolean isFinished() {
        return this.mScroller.isFinished();
    }

    public int getCurrX() {
        return this.mScroller.getCurrX();
    }

    public int getCurrY() {
        return this.mScroller.getCurrY();
    }

    public int getFinalX() {
        return this.mScroller.getFinalX();
    }

    public int getFinalY() {
        return this.mScroller.getFinalY();
    }

    public float getCurrVelocity() {
        if (!this.mIsIcsOrNewer) {
            return 0.0f;
        }
        return ScrollerCompatIcs.getCurrVelocity(this.mScroller);
    }

    public boolean computeScrollOffset() {
        return this.mScroller.computeScrollOffset();
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        int startX = i;
        int startY = i2;
        int dx = i3;
        int dy = i4;
        int i5 = startX;
        int i6 = startY;
        int i7 = dx;
        int i8 = dy;
        this.mScroller.startScroll(startX, startY, dx, dy);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        int startX = i;
        int startY = i2;
        int dx = i3;
        int dy = i4;
        int duration = i5;
        int i6 = startX;
        int i7 = startY;
        int i8 = dx;
        int i9 = dy;
        int i10 = duration;
        this.mScroller.startScroll(startX, startY, dx, dy, duration);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int startX = i;
        int startY = i2;
        int velocityX = i3;
        int velocityY = i4;
        int minX = i5;
        int maxX = i6;
        int minY = i7;
        int maxY = i8;
        int i9 = startX;
        int i10 = startY;
        int i11 = velocityX;
        int i12 = velocityY;
        int i13 = minX;
        int i14 = maxX;
        int i15 = minY;
        int i16 = maxY;
        this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        int startX = i;
        int startY = i2;
        int velocityX = i3;
        int velocityY = i4;
        int minX = i5;
        int maxX = i6;
        int minY = i7;
        int maxY = i8;
        int overX = i9;
        int overY = i10;
        int i11 = startX;
        int i12 = startY;
        int i13 = velocityX;
        int i14 = velocityY;
        int i15 = minX;
        int i16 = maxX;
        int i17 = minY;
        int i18 = maxY;
        int i19 = overX;
        int i20 = overY;
        this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY);
    }

    public boolean springBack(int i, int i2, int i3, int i4, int i5, int i6) {
        int startX = i;
        int startY = i2;
        int minX = i3;
        int maxX = i4;
        int minY = i5;
        int maxY = i6;
        int i7 = startX;
        int i8 = startY;
        int i9 = minX;
        int i10 = maxX;
        int i11 = minY;
        int i12 = maxY;
        return this.mScroller.springBack(startX, startY, minX, maxX, minY, maxY);
    }

    public void abortAnimation() {
        this.mScroller.abortAnimation();
    }

    public void notifyHorizontalEdgeReached(int i, int i2, int i3) {
        int startX = i;
        int finalX = i2;
        int overX = i3;
        int i4 = startX;
        int i5 = finalX;
        int i6 = overX;
        this.mScroller.notifyHorizontalEdgeReached(startX, finalX, overX);
    }

    public void notifyVerticalEdgeReached(int i, int i2, int i3) {
        int startY = i;
        int finalY = i2;
        int overY = i3;
        int i4 = startY;
        int i5 = finalY;
        int i6 = overY;
        this.mScroller.notifyVerticalEdgeReached(startY, finalY, overY);
    }

    public boolean isOverScrolled() {
        return this.mScroller.isOverScrolled();
    }
}
