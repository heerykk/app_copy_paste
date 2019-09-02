package android.support.p003v7.widget;

import android.graphics.Rect;
import android.support.p003v7.widget.RecyclerView.LayoutManager;
import android.support.p003v7.widget.RecyclerView.LayoutParams;
import android.view.View;

/* renamed from: android.support.v7.widget.OrientationHelper */
public abstract class OrientationHelper {
    public static final int HORIZONTAL = 0;
    private static final int INVALID_SIZE = Integer.MIN_VALUE;
    public static final int VERTICAL = 1;
    private int mLastTotalSpace;
    protected final LayoutManager mLayoutManager;
    final Rect mTmpRect;

    public abstract int getDecoratedEnd(View view);

    public abstract int getDecoratedMeasurement(View view);

    public abstract int getDecoratedMeasurementInOther(View view);

    public abstract int getDecoratedStart(View view);

    public abstract int getEnd();

    public abstract int getEndAfterPadding();

    public abstract int getEndPadding();

    public abstract int getMode();

    public abstract int getModeInOther();

    public abstract int getStartAfterPadding();

    public abstract int getTotalSpace();

    public abstract int getTransformedEndWithDecoration(View view);

    public abstract int getTransformedStartWithDecoration(View view);

    public abstract void offsetChild(View view, int i);

    public abstract void offsetChildren(int i);

    /* synthetic */ OrientationHelper(LayoutManager layoutManager, C03191 r9) {
        LayoutManager x0 = layoutManager;
        LayoutManager layoutManager2 = x0;
        C03191 r5 = r9;
        this(x0);
    }

    private OrientationHelper(LayoutManager layoutManager) {
        LayoutManager layoutManager2 = layoutManager;
        LayoutManager layoutManager3 = layoutManager2;
        this.mLastTotalSpace = Integer.MIN_VALUE;
        this.mTmpRect = new Rect();
        this.mLayoutManager = layoutManager2;
    }

    public void onLayoutComplete() {
        this.mLastTotalSpace = getTotalSpace();
    }

    public int getTotalSpaceChange() {
        return Integer.MIN_VALUE != this.mLastTotalSpace ? getTotalSpace() - this.mLastTotalSpace : 0;
    }

    public static OrientationHelper createOrientationHelper(LayoutManager layoutManager, int i) {
        LayoutManager layoutManager2 = layoutManager;
        int orientation = i;
        LayoutManager layoutManager3 = layoutManager2;
        int i2 = orientation;
        switch (orientation) {
            case 0:
                return createHorizontalHelper(layoutManager2);
            case 1:
                return createVerticalHelper(layoutManager2);
            default:
                throw new IllegalArgumentException("invalid orientation");
        }
    }

    public static OrientationHelper createHorizontalHelper(LayoutManager layoutManager) {
        LayoutManager layoutManager2 = layoutManager;
        LayoutManager layoutManager3 = layoutManager2;
        return new OrientationHelper(layoutManager2) {
            {
                LayoutManager layoutManager = r9;
                LayoutManager layoutManager2 = layoutManager;
            }

            public int getEndAfterPadding() {
                return this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingRight();
            }

            public int getEnd() {
                return this.mLayoutManager.getWidth();
            }

            public void offsetChildren(int i) {
                int amount = i;
                int i2 = amount;
                this.mLayoutManager.offsetChildrenHorizontal(amount);
            }

            public int getStartAfterPadding() {
                return this.mLayoutManager.getPaddingLeft();
            }

            public int getDecoratedMeasurement(View view) {
                View view2 = view;
                View view3 = view2;
                LayoutParams params = (LayoutParams) view2.getLayoutParams();
                return this.mLayoutManager.getDecoratedMeasuredWidth(view2) + params.leftMargin + params.rightMargin;
            }

            public int getDecoratedMeasurementInOther(View view) {
                View view2 = view;
                View view3 = view2;
                LayoutParams params = (LayoutParams) view2.getLayoutParams();
                return this.mLayoutManager.getDecoratedMeasuredHeight(view2) + params.topMargin + params.bottomMargin;
            }

            public int getDecoratedEnd(View view) {
                View view2 = view;
                View view3 = view2;
                return this.mLayoutManager.getDecoratedRight(view2) + ((LayoutParams) view2.getLayoutParams()).rightMargin;
            }

            public int getDecoratedStart(View view) {
                View view2 = view;
                View view3 = view2;
                return this.mLayoutManager.getDecoratedLeft(view2) - ((LayoutParams) view2.getLayoutParams()).leftMargin;
            }

            public int getTransformedEndWithDecoration(View view) {
                View view2 = view;
                View view3 = view2;
                this.mLayoutManager.getTransformedBoundingBox(view2, true, this.mTmpRect);
                return this.mTmpRect.right;
            }

            public int getTransformedStartWithDecoration(View view) {
                View view2 = view;
                View view3 = view2;
                this.mLayoutManager.getTransformedBoundingBox(view2, true, this.mTmpRect);
                return this.mTmpRect.left;
            }

            public int getTotalSpace() {
                return (this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingLeft()) - this.mLayoutManager.getPaddingRight();
            }

            public void offsetChild(View view, int i) {
                View view2 = view;
                int offset = i;
                View view3 = view2;
                int i2 = offset;
                view2.offsetLeftAndRight(offset);
            }

            public int getEndPadding() {
                return this.mLayoutManager.getPaddingRight();
            }

            public int getMode() {
                return this.mLayoutManager.getWidthMode();
            }

            public int getModeInOther() {
                return this.mLayoutManager.getHeightMode();
            }
        };
    }

    public static OrientationHelper createVerticalHelper(LayoutManager layoutManager) {
        LayoutManager layoutManager2 = layoutManager;
        LayoutManager layoutManager3 = layoutManager2;
        return new OrientationHelper(layoutManager2) {
            {
                LayoutManager layoutManager = r9;
                LayoutManager layoutManager2 = layoutManager;
            }

            public int getEndAfterPadding() {
                return this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingBottom();
            }

            public int getEnd() {
                return this.mLayoutManager.getHeight();
            }

            public void offsetChildren(int i) {
                int amount = i;
                int i2 = amount;
                this.mLayoutManager.offsetChildrenVertical(amount);
            }

            public int getStartAfterPadding() {
                return this.mLayoutManager.getPaddingTop();
            }

            public int getDecoratedMeasurement(View view) {
                View view2 = view;
                View view3 = view2;
                LayoutParams params = (LayoutParams) view2.getLayoutParams();
                return this.mLayoutManager.getDecoratedMeasuredHeight(view2) + params.topMargin + params.bottomMargin;
            }

            public int getDecoratedMeasurementInOther(View view) {
                View view2 = view;
                View view3 = view2;
                LayoutParams params = (LayoutParams) view2.getLayoutParams();
                return this.mLayoutManager.getDecoratedMeasuredWidth(view2) + params.leftMargin + params.rightMargin;
            }

            public int getDecoratedEnd(View view) {
                View view2 = view;
                View view3 = view2;
                return this.mLayoutManager.getDecoratedBottom(view2) + ((LayoutParams) view2.getLayoutParams()).bottomMargin;
            }

            public int getDecoratedStart(View view) {
                View view2 = view;
                View view3 = view2;
                return this.mLayoutManager.getDecoratedTop(view2) - ((LayoutParams) view2.getLayoutParams()).topMargin;
            }

            public int getTransformedEndWithDecoration(View view) {
                View view2 = view;
                View view3 = view2;
                this.mLayoutManager.getTransformedBoundingBox(view2, true, this.mTmpRect);
                return this.mTmpRect.bottom;
            }

            public int getTransformedStartWithDecoration(View view) {
                View view2 = view;
                View view3 = view2;
                this.mLayoutManager.getTransformedBoundingBox(view2, true, this.mTmpRect);
                return this.mTmpRect.top;
            }

            public int getTotalSpace() {
                return (this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingTop()) - this.mLayoutManager.getPaddingBottom();
            }

            public void offsetChild(View view, int i) {
                View view2 = view;
                int offset = i;
                View view3 = view2;
                int i2 = offset;
                view2.offsetTopAndBottom(offset);
            }

            public int getEndPadding() {
                return this.mLayoutManager.getPaddingBottom();
            }

            public int getMode() {
                return this.mLayoutManager.getHeightMode();
            }

            public int getModeInOther() {
                return this.mLayoutManager.getWidthMode();
            }
        };
    }
}
