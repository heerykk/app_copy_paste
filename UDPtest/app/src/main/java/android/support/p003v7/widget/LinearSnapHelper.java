package android.support.p003v7.widget;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p003v7.widget.RecyclerView.LayoutManager;
import android.support.p003v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.view.View;

/* renamed from: android.support.v7.widget.LinearSnapHelper */
public class LinearSnapHelper extends SnapHelper {
    private static final float INVALID_DISTANCE = 1.0f;
    @Nullable
    private OrientationHelper mHorizontalHelper;
    @Nullable
    private OrientationHelper mVerticalHelper;

    public LinearSnapHelper() {
    }

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

    public int findTargetSnapPosition(LayoutManager layoutManager, int i, int i2) {
        int hDeltaJump;
        int vDeltaJump;
        LayoutManager layoutManager2 = layoutManager;
        int velocityX = i;
        int velocityY = i2;
        LayoutManager layoutManager3 = layoutManager2;
        int i3 = velocityX;
        int i4 = velocityY;
        if (!(layoutManager2 instanceof ScrollVectorProvider)) {
            return -1;
        }
        int itemCount = layoutManager2.getItemCount();
        int itemCount2 = itemCount;
        if (itemCount == 0) {
            return -1;
        }
        View findSnapView = findSnapView(layoutManager2);
        View currentView = findSnapView;
        if (findSnapView == null) {
            return -1;
        }
        int position = layoutManager2.getPosition(currentView);
        int currentPosition = position;
        if (position == -1) {
            return -1;
        }
        ScrollVectorProvider scrollVectorProvider = (ScrollVectorProvider) layoutManager2;
        ScrollVectorProvider scrollVectorProvider2 = scrollVectorProvider;
        PointF computeScrollVectorForPosition = scrollVectorProvider.computeScrollVectorForPosition(itemCount2 - 1);
        PointF vectorForEnd = computeScrollVectorForPosition;
        if (computeScrollVectorForPosition == null) {
            return -1;
        }
        if (!layoutManager2.canScrollHorizontally()) {
            hDeltaJump = 0;
        } else {
            int estimateNextPositionDiffForFling = estimateNextPositionDiffForFling(layoutManager2, getHorizontalHelper(layoutManager2), velocityX, 0);
            hDeltaJump = estimateNextPositionDiffForFling;
            if (vectorForEnd.x < 0.0f) {
                hDeltaJump = -estimateNextPositionDiffForFling;
            }
        }
        if (!layoutManager2.canScrollVertically()) {
            vDeltaJump = 0;
        } else {
            int estimateNextPositionDiffForFling2 = estimateNextPositionDiffForFling(layoutManager2, getVerticalHelper(layoutManager2), 0, velocityY);
            vDeltaJump = estimateNextPositionDiffForFling2;
            if (vectorForEnd.y < 0.0f) {
                vDeltaJump = -estimateNextPositionDiffForFling2;
            }
        }
        int deltaJump = !layoutManager2.canScrollVertically() ? hDeltaJump : vDeltaJump;
        if (deltaJump == 0) {
            return -1;
        }
        int i5 = currentPosition + deltaJump;
        int targetPos = i5;
        if (i5 < 0) {
            targetPos = 0;
        }
        if (targetPos >= itemCount2) {
            targetPos = itemCount2 - 1;
        }
        return targetPos;
    }

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

    private int estimateNextPositionDiffForFling(LayoutManager layoutManager, OrientationHelper orientationHelper, int i, int i2) {
        LayoutManager layoutManager2 = layoutManager;
        OrientationHelper helper = orientationHelper;
        int velocityX = i;
        int velocityY = i2;
        LayoutManager layoutManager3 = layoutManager2;
        OrientationHelper orientationHelper2 = helper;
        int i3 = velocityX;
        int i4 = velocityY;
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float computeDistancePerChild = computeDistancePerChild(layoutManager2, helper);
        float distancePerChild = computeDistancePerChild;
        if (computeDistancePerChild <= 0.0f) {
            return 0;
        }
        int distance = Math.abs(distances[0]) <= Math.abs(distances[1]) ? distances[1] : distances[0];
        if (distance <= 0) {
            return (int) Math.ceil((double) (((float) distance) / distancePerChild));
        }
        return (int) Math.floor((double) (((float) distance) / distancePerChild));
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

    private float computeDistancePerChild(LayoutManager layoutManager, OrientationHelper orientationHelper) {
        LayoutManager layoutManager2 = layoutManager;
        OrientationHelper helper = orientationHelper;
        LayoutManager layoutManager3 = layoutManager2;
        OrientationHelper orientationHelper2 = helper;
        View minPosView = null;
        View maxPosView = null;
        int minPos = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int maxPos = Integer.MIN_VALUE;
        int childCount = layoutManager2.getChildCount();
        int childCount2 = childCount;
        if (childCount == 0) {
            return INVALID_DISTANCE;
        }
        for (int i = 0; i < childCount2; i++) {
            View child = layoutManager2.getChildAt(i);
            int position = layoutManager2.getPosition(child);
            int pos = position;
            if (position != -1) {
                if (pos < minPos) {
                    minPos = pos;
                    minPosView = child;
                }
                if (pos > maxPos) {
                    maxPos = pos;
                    maxPosView = child;
                }
            }
        }
        if (minPosView == null || maxPosView == null) {
            return INVALID_DISTANCE;
        }
        int start = Math.min(helper.getDecoratedStart(minPosView), helper.getDecoratedStart(maxPosView));
        int max = Math.max(helper.getDecoratedEnd(minPosView), helper.getDecoratedEnd(maxPosView));
        int i2 = max;
        int i3 = max - start;
        int distance = i3;
        if (i3 != 0) {
            return (INVALID_DISTANCE * ((float) distance)) / ((float) ((maxPos - minPos) + 1));
        }
        return INVALID_DISTANCE;
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
