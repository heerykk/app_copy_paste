package android.support.p003v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.p003v7.widget.RecyclerView.LayoutManager;
import android.support.p003v7.widget.RecyclerView.LayoutParams;
import android.support.p003v7.widget.RecyclerView.SmoothScroller;
import android.support.p003v7.widget.RecyclerView.SmoothScroller.Action;
import android.support.p003v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.support.p003v7.widget.RecyclerView.State;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/* renamed from: android.support.v7.widget.LinearSmoothScroller */
public class LinearSmoothScroller extends SmoothScroller {
    private static final boolean DEBUG = false;
    private static final float MILLISECONDS_PER_INCH = 25.0f;
    public static final int SNAP_TO_ANY = 0;
    public static final int SNAP_TO_END = 1;
    public static final int SNAP_TO_START = -1;
    private static final String TAG = "LinearSmoothScroller";
    private static final float TARGET_SEEK_EXTRA_SCROLL_RATIO = 1.2f;
    private static final int TARGET_SEEK_SCROLL_DISTANCE_PX = 10000;
    private final float MILLISECONDS_PER_PX;
    protected final DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
    protected int mInterimTargetDx = 0;
    protected int mInterimTargetDy = 0;
    protected final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
    protected PointF mTargetVector;

    public LinearSmoothScroller(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this.MILLISECONDS_PER_PX = calculateSpeedPerPixel(context2.getResources().getDisplayMetrics());
    }

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    /* access modifiers changed from: protected */
    public void onTargetFound(View view, State state, Action action) {
        View targetView = view;
        Action action2 = action;
        View view2 = targetView;
        State state2 = state;
        Action action3 = action2;
        int dx = calculateDxToMakeVisible(targetView, getHorizontalSnapPreference());
        int dy = calculateDyToMakeVisible(targetView, getVerticalSnapPreference());
        int sqrt = (int) Math.sqrt((double) ((dx * dx) + (dy * dy)));
        int i = sqrt;
        int calculateTimeForDeceleration = calculateTimeForDeceleration(sqrt);
        int time = calculateTimeForDeceleration;
        if (calculateTimeForDeceleration > 0) {
            action2.update(-dx, -dy, time, this.mDecelerateInterpolator);
        }
    }

    /* access modifiers changed from: protected */
    public void onSeekTargetStep(int i, int i2, State state, Action action) {
        int dx = i;
        int dy = i2;
        Action action2 = action;
        int i3 = dx;
        int i4 = dy;
        State state2 = state;
        Action action3 = action2;
        if (getChildCount() != 0) {
            this.mInterimTargetDx = clampApplyScroll(this.mInterimTargetDx, dx);
            this.mInterimTargetDy = clampApplyScroll(this.mInterimTargetDy, dy);
            if (this.mInterimTargetDx == 0 && this.mInterimTargetDy == 0) {
                updateActionForInterimTarget(action2);
            }
            return;
        }
        stop();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        this.mInterimTargetDy = 0;
        this.mInterimTargetDx = 0;
        this.mTargetVector = null;
    }

    /* access modifiers changed from: protected */
    public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        DisplayMetrics displayMetrics2 = displayMetrics;
        DisplayMetrics displayMetrics3 = displayMetrics2;
        return MILLISECONDS_PER_INCH / ((float) displayMetrics2.densityDpi);
    }

    /* access modifiers changed from: protected */
    public int calculateTimeForDeceleration(int i) {
        int dx = i;
        int i2 = dx;
        return (int) Math.ceil(((double) calculateTimeForScrolling(dx)) / 0.3356d);
    }

    /* access modifiers changed from: protected */
    public int calculateTimeForScrolling(int i) {
        int dx = i;
        int i2 = dx;
        return (int) Math.ceil((double) (((float) Math.abs(dx)) * this.MILLISECONDS_PER_PX));
    }

    /* access modifiers changed from: protected */
    public int getHorizontalSnapPreference() {
        int i = (this.mTargetVector == null || this.mTargetVector.x == 0.0f) ? 0 : this.mTargetVector.x > 0.0f ? 1 : -1;
        return i;
    }

    /* access modifiers changed from: protected */
    public int getVerticalSnapPreference() {
        int i = (this.mTargetVector == null || this.mTargetVector.y == 0.0f) ? 0 : this.mTargetVector.y > 0.0f ? 1 : -1;
        return i;
    }

    /* access modifiers changed from: protected */
    public void updateActionForInterimTarget(Action action) {
        Action action2 = action;
        Action action3 = action2;
        PointF computeScrollVectorForPosition = computeScrollVectorForPosition(getTargetPosition());
        PointF scrollVector = computeScrollVectorForPosition;
        if (computeScrollVectorForPosition == null || (scrollVector.x == 0.0f && scrollVector.y == 0.0f)) {
            int targetPosition = getTargetPosition();
            int i = targetPosition;
            action2.jumpTo(targetPosition);
            stop();
            return;
        }
        normalize(scrollVector);
        this.mTargetVector = scrollVector;
        this.mInterimTargetDx = (int) (10000.0f * scrollVector.x);
        this.mInterimTargetDy = (int) (10000.0f * scrollVector.y);
        int calculateTimeForScrolling = calculateTimeForScrolling(TARGET_SEEK_SCROLL_DISTANCE_PX);
        int i2 = calculateTimeForScrolling;
        action2.update((int) (((float) this.mInterimTargetDx) * TARGET_SEEK_EXTRA_SCROLL_RATIO), (int) (((float) this.mInterimTargetDy) * TARGET_SEEK_EXTRA_SCROLL_RATIO), (int) (((float) calculateTimeForScrolling) * TARGET_SEEK_EXTRA_SCROLL_RATIO), this.mLinearInterpolator);
    }

    private int clampApplyScroll(int i, int i2) {
        int tmpDt = i;
        int dt = i2;
        int i3 = tmpDt;
        int i4 = dt;
        int i5 = tmpDt;
        int tmpDt2 = tmpDt - dt;
        if (tmpDt * tmpDt2 > 0) {
            return tmpDt2;
        }
        return 0;
    }

    public int calculateDtToFit(int i, int i2, int i3, int i4, int i5) {
        int viewStart = i;
        int viewEnd = i2;
        int boxStart = i3;
        int boxEnd = i4;
        int snapPreference = i5;
        int i6 = viewStart;
        int i7 = viewEnd;
        int i8 = boxStart;
        int i9 = boxEnd;
        int i10 = snapPreference;
        switch (snapPreference) {
            case -1:
                return boxStart - viewStart;
            case 0:
                int i11 = boxStart - viewStart;
                int dtStart = i11;
                if (i11 > 0) {
                    return dtStart;
                }
                int i12 = boxEnd - viewEnd;
                int dtEnd = i12;
                if (i12 >= 0) {
                    return 0;
                }
                return dtEnd;
            case 1:
                return boxEnd - viewEnd;
            default:
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
                throw illegalArgumentException;
        }
    }

    public int calculateDyToMakeVisible(View view, int i) {
        View view2 = view;
        int snapPreference = i;
        View view3 = view2;
        int i2 = snapPreference;
        LayoutManager layoutManager = getLayoutManager();
        LayoutManager layoutManager2 = layoutManager;
        if (layoutManager == null || !layoutManager2.canScrollVertically()) {
            return 0;
        }
        LayoutParams params = (LayoutParams) view2.getLayoutParams();
        int height = layoutManager2.getHeight() - layoutManager2.getPaddingBottom();
        int i3 = height;
        return calculateDtToFit(layoutManager2.getDecoratedTop(view2) - params.topMargin, layoutManager2.getDecoratedBottom(view2) + params.bottomMargin, layoutManager2.getPaddingTop(), height, snapPreference);
    }

    public int calculateDxToMakeVisible(View view, int i) {
        View view2 = view;
        int snapPreference = i;
        View view3 = view2;
        int i2 = snapPreference;
        LayoutManager layoutManager = getLayoutManager();
        LayoutManager layoutManager2 = layoutManager;
        if (layoutManager == null || !layoutManager2.canScrollHorizontally()) {
            return 0;
        }
        LayoutParams params = (LayoutParams) view2.getLayoutParams();
        int width = layoutManager2.getWidth() - layoutManager2.getPaddingRight();
        int i3 = width;
        return calculateDtToFit(layoutManager2.getDecoratedLeft(view2) - params.leftMargin, layoutManager2.getDecoratedRight(view2) + params.rightMargin, layoutManager2.getPaddingLeft(), width, snapPreference);
    }

    @Nullable
    public PointF computeScrollVectorForPosition(int i) {
        int targetPosition = i;
        int i2 = targetPosition;
        LayoutManager layoutManager = getLayoutManager();
        LayoutManager layoutManager2 = layoutManager;
        if (layoutManager instanceof ScrollVectorProvider) {
            return ((ScrollVectorProvider) layoutManager2).computeScrollVectorForPosition(targetPosition);
        }
        int w = Log.w(TAG, "You should override computeScrollVectorForPosition when the LayoutManager does not implement " + ScrollVectorProvider.class.getCanonicalName());
        return null;
    }
}
