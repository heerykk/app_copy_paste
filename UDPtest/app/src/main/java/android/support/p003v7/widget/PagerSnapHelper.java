package android.support.p003v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p003v7.widget.RecyclerView.LayoutManager;
import android.support.p003v7.widget.RecyclerView.SmoothScroller.Action;
import android.support.p003v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.support.p003v7.widget.RecyclerView.State;
import android.util.DisplayMetrics;
import android.view.View;

/* renamed from: android.support.v7.widget.PagerSnapHelper */
public class PagerSnapHelper extends SnapHelper {
    private static final int MAX_SCROLL_ON_FLING_DURATION = 100;
    @Nullable
    private OrientationHelper mHorizontalHelper;
    @Nullable
    private OrientationHelper mVerticalHelper;

    public PagerSnapHelper() {
    }

    @Nullable
    public int[] calculateDistanceToFinalSnap(@NonNull LayoutManager layoutManager, @NonNull View view) {
        LayoutManager layoutManager2 = layoutManager;
        View targetView = view;
        LayoutManager layoutManager3 = layoutManager2;
        View view2 = targetView;
        int[] out = new int[2];
        if (!layoutManager2.canScrollHorizontally()) {
            out[0] = 0;
        } else {
            out[0] = distanceToCenter(layoutManager2, targetView, getHorizontalHelper(layoutManager2));
        }
        if (!layoutManager2.canScrollVertically()) {
            out[1] = 0;
        } else {
            out[1] = distanceToCenter(layoutManager2, targetView, getVerticalHelper(layoutManager2));
        }
        return out;
    }

    @Nullable
    public View findSnapView(LayoutManager layoutManager) {
        LayoutManager layoutManager2 = layoutManager;
        LayoutManager layoutManager3 = layoutManager2;
        if (layoutManager2.canScrollVertically()) {
            return findCenterView(layoutManager2, getVerticalHelper(layoutManager2));
        }
        if (!layoutManager2.canScrollHorizontally()) {
            return null;
        }
        return findCenterView(layoutManager2, getHorizontalHelper(layoutManager2));
    }

    public int findTargetSnapPosition(LayoutManager layoutManager, int i, int i2) {
        boolean forwardDirection;
        LayoutManager layoutManager2 = layoutManager;
        int velocityX = i;
        int velocityY = i2;
        LayoutManager layoutManager3 = layoutManager2;
        int i3 = velocityX;
        int i4 = velocityY;
        int itemCount = layoutManager2.getItemCount();
        int itemCount2 = itemCount;
        if (itemCount == 0) {
            return -1;
        }
        View mStartMostChildView = null;
        if (layoutManager2.canScrollVertically()) {
            mStartMostChildView = findStartView(layoutManager2, getVerticalHelper(layoutManager2));
        } else if (layoutManager2.canScrollHorizontally()) {
            mStartMostChildView = findStartView(layoutManager2, getHorizontalHelper(layoutManager2));
        }
        if (mStartMostChildView == null) {
            return -1;
        }
        int position = layoutManager2.getPosition(mStartMostChildView);
        int centerPosition = position;
        if (position == -1) {
            return -1;
        }
        if (!layoutManager2.canScrollHorizontally()) {
            forwardDirection = velocityY > 0;
        } else {
            forwardDirection = velocityX > 0;
        }
        boolean reverseLayout = false;
        if (layoutManager2 instanceof ScrollVectorProvider) {
            ScrollVectorProvider scrollVectorProvider = (ScrollVectorProvider) layoutManager2;
            ScrollVectorProvider scrollVectorProvider2 = scrollVectorProvider;
            PointF computeScrollVectorForPosition = scrollVectorProvider.computeScrollVectorForPosition(itemCount2 - 1);
            PointF vectorForEnd = computeScrollVectorForPosition;
            if (computeScrollVectorForPosition != null) {
                reverseLayout = ((vectorForEnd.x > 0.0f ? 1 : (vectorForEnd.x == 0.0f ? 0 : -1)) < 0) || vectorForEnd.y < 0.0f;
            }
        }
        int i5 = !reverseLayout ? !forwardDirection ? centerPosition : centerPosition + 1 : !forwardDirection ? centerPosition : centerPosition - 1;
        return i5;
    }

    /* access modifiers changed from: protected */
    public LinearSmoothScroller createSnapScroller(LayoutManager layoutManager) {
        LayoutManager layoutManager2 = layoutManager;
        LayoutManager layoutManager3 = layoutManager2;
        if (layoutManager2 instanceof ScrollVectorProvider) {
            return new LinearSmoothScroller(this, this.mRecyclerView.getContext()) {
                final /* synthetic */ PagerSnapHelper this$0;

                {
                    PagerSnapHelper this$02 = r8;
                    Context context = r9;
                    PagerSnapHelper pagerSnapHelper = this$02;
                    Context context2 = context;
                    this.this$0 = this$02;
                }

                /* access modifiers changed from: protected */
                public void onTargetFound(View view, State state, Action action) {
                    View targetView = view;
                    Action action2 = action;
                    View view2 = targetView;
                    State state2 = state;
                    Action action3 = action2;
                    int[] calculateDistanceToFinalSnap = this.this$0.calculateDistanceToFinalSnap(this.this$0.mRecyclerView.getLayoutManager(), targetView);
                    int[] snapDistances = calculateDistanceToFinalSnap;
                    int dx = calculateDistanceToFinalSnap[0];
                    int i = snapDistances[1];
                    int dy = i;
                    int calculateTimeForDeceleration = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(i)));
                    int time = calculateTimeForDeceleration;
                    if (calculateTimeForDeceleration > 0) {
                        action2.update(dx, dy, time, this.mDecelerateInterpolator);
                    }
                }

                /* access modifiers changed from: protected */
                public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    DisplayMetrics displayMetrics2 = displayMetrics;
                    DisplayMetrics displayMetrics3 = displayMetrics2;
                    return 100.0f / ((float) displayMetrics2.densityDpi);
                }

                /* access modifiers changed from: protected */
                public int calculateTimeForScrolling(int i) {
                    int dx = i;
                    int i2 = dx;
                    return Math.min(100, super.calculateTimeForScrolling(dx));
                }
            };
        }
        return null;
    }

    private int distanceToCenter(@NonNull LayoutManager layoutManager, @NonNull View view, OrientationHelper orientationHelper) {
        int containerCenter;
        LayoutManager layoutManager2 = layoutManager;
        View targetView = view;
        OrientationHelper helper = orientationHelper;
        LayoutManager layoutManager3 = layoutManager2;
        View view2 = targetView;
        OrientationHelper orientationHelper2 = helper;
        int childCenter = helper.getDecoratedStart(targetView) + (helper.getDecoratedMeasurement(targetView) / 2);
        if (!layoutManager2.getClipToPadding()) {
            containerCenter = helper.getEnd() / 2;
        } else {
            containerCenter = helper.getStartAfterPadding() + (helper.getTotalSpace() / 2);
        }
        return childCenter - containerCenter;
    }

    @Nullable
    private View findCenterView(LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int center;
        LayoutManager layoutManager2 = layoutManager;
        OrientationHelper helper = orientationHelper;
        LayoutManager layoutManager3 = layoutManager2;
        OrientationHelper orientationHelper2 = helper;
        int childCount = layoutManager2.getChildCount();
        int childCount2 = childCount;
        if (childCount == 0) {
            return null;
        }
        View closestChild = null;
        if (!layoutManager2.getClipToPadding()) {
            center = helper.getEnd() / 2;
        } else {
            center = helper.getStartAfterPadding() + (helper.getTotalSpace() / 2);
        }
        int absClosest = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        for (int i = 0; i < childCount2; i++) {
            View child = layoutManager2.getChildAt(i);
            int decoratedStart = helper.getDecoratedStart(child) + (helper.getDecoratedMeasurement(child) / 2);
            int i2 = decoratedStart;
            int abs = Math.abs(decoratedStart - center);
            int absDistance = abs;
            if (abs < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        return closestChild;
    }

    @Nullable
    private View findStartView(LayoutManager layoutManager, OrientationHelper orientationHelper) {
        LayoutManager layoutManager2 = layoutManager;
        OrientationHelper helper = orientationHelper;
        LayoutManager layoutManager3 = layoutManager2;
        OrientationHelper orientationHelper2 = helper;
        int childCount = layoutManager2.getChildCount();
        int childCount2 = childCount;
        if (childCount == 0) {
            return null;
        }
        View closestChild = null;
        int startest = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        for (int i = 0; i < childCount2; i++) {
            View child = layoutManager2.getChildAt(i);
            int decoratedStart = helper.getDecoratedStart(child);
            int childStart = decoratedStart;
            if (decoratedStart < startest) {
                startest = childStart;
                closestChild = child;
            }
        }
        return closestChild;
    }

    @NonNull
    private OrientationHelper getVerticalHelper(@NonNull LayoutManager layoutManager) {
        LayoutManager layoutManager2 = layoutManager;
        LayoutManager layoutManager3 = layoutManager2;
        if (this.mVerticalHelper == null || this.mVerticalHelper.mLayoutManager != layoutManager2) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager2);
        }
        return this.mVerticalHelper;
    }

    @NonNull
    private OrientationHelper getHorizontalHelper(@NonNull LayoutManager layoutManager) {
        LayoutManager layoutManager2 = layoutManager;
        LayoutManager layoutManager3 = layoutManager2;
        if (this.mHorizontalHelper == null || this.mHorizontalHelper.mLayoutManager != layoutManager2) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager2);
        }
        return this.mHorizontalHelper;
    }
}
