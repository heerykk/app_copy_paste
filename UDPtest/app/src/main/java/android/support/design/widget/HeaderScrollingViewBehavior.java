package android.support.design.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import java.util.List;

abstract class HeaderScrollingViewBehavior extends ViewOffsetBehavior<View> {
    private int mOverlayTop;
    final Rect mTempRect1 = new Rect();
    final Rect mTempRect2 = new Rect();
    private int mVerticalLayoutGap = 0;

    /* access modifiers changed from: 0000 */
    public abstract View findFirstDependency(List<View> list);

    public HeaderScrollingViewBehavior() {
    }

    public HeaderScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
    }

    public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
        CoordinatorLayout parent = coordinatorLayout;
        View child = view;
        int parentWidthMeasureSpec = i;
        int widthUsed = i2;
        int parentHeightMeasureSpec = i3;
        int heightUsed = i4;
        CoordinatorLayout coordinatorLayout2 = parent;
        View view2 = child;
        int i5 = parentWidthMeasureSpec;
        int i6 = widthUsed;
        int i7 = parentHeightMeasureSpec;
        int i8 = heightUsed;
        int i9 = child.getLayoutParams().height;
        int childLpHeight = i9;
        if (i9 == -1 || childLpHeight == -2) {
            View findFirstDependency = findFirstDependency(parent.getDependencies(child));
            View header = findFirstDependency;
            if (findFirstDependency != null) {
                if (ViewCompat.getFitsSystemWindows(header) && !ViewCompat.getFitsSystemWindows(child)) {
                    ViewCompat.setFitsSystemWindows(child, true);
                    if (ViewCompat.getFitsSystemWindows(child)) {
                        child.requestLayout();
                        return true;
                    }
                }
                int size = MeasureSpec.getSize(parentHeightMeasureSpec);
                int availableHeight = size;
                if (size == 0) {
                    availableHeight = parent.getHeight();
                }
                int measuredHeight = (availableHeight - header.getMeasuredHeight()) + getScrollRange(header);
                int i10 = measuredHeight;
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, childLpHeight != -1 ? Integer.MIN_VALUE : 1073741824);
                int i11 = makeMeasureSpec;
                parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, makeMeasureSpec, heightUsed);
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void layoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        CoordinatorLayout parent = coordinatorLayout;
        View child = view;
        int layoutDirection = i;
        CoordinatorLayout coordinatorLayout2 = parent;
        View view2 = child;
        int i2 = layoutDirection;
        View findFirstDependency = findFirstDependency(parent.getDependencies(child));
        View header = findFirstDependency;
        if (findFirstDependency == null) {
            super.layoutChild(parent, child, layoutDirection);
            this.mVerticalLayoutGap = 0;
            return;
        }
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        Rect rect = this.mTempRect1;
        Rect available = rect;
        rect.set(parent.getPaddingLeft() + lp.leftMargin, header.getBottom() + lp.topMargin, (parent.getWidth() - parent.getPaddingRight()) - lp.rightMargin, ((parent.getHeight() + header.getBottom()) - parent.getPaddingBottom()) - lp.bottomMargin);
        WindowInsetsCompat lastWindowInsets = parent.getLastWindowInsets();
        WindowInsetsCompat parentInsets = lastWindowInsets;
        if (lastWindowInsets != null && ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            available.left += parentInsets.getSystemWindowInsetLeft();
            available.right -= parentInsets.getSystemWindowInsetRight();
        }
        Rect out = this.mTempRect2;
        GravityCompat.apply(resolveGravity(lp.gravity), child.getMeasuredWidth(), child.getMeasuredHeight(), available, out, layoutDirection);
        int overlapPixelsForOffset = getOverlapPixelsForOffset(header);
        int i3 = overlapPixelsForOffset;
        child.layout(out.left, out.top - overlapPixelsForOffset, out.right, out.bottom - overlapPixelsForOffset);
        this.mVerticalLayoutGap = out.top - header.getBottom();
    }

    /* access modifiers changed from: 0000 */
    public float getOverlapRatioForOffset(View view) {
        View view2 = view;
        return 1.0f;
    }

    /* access modifiers changed from: 0000 */
    public final int getOverlapPixelsForOffset(View view) {
        View header = view;
        View view2 = header;
        return this.mOverlayTop != 0 ? MathUtils.constrain((int) (getOverlapRatioForOffset(header) * ((float) this.mOverlayTop)), 0, this.mOverlayTop) : 0;
    }

    private static int resolveGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        return gravity != 0 ? gravity : 8388659;
    }

    /* access modifiers changed from: 0000 */
    public int getScrollRange(View view) {
        View v = view;
        View view2 = v;
        return v.getMeasuredHeight();
    }

    /* access modifiers changed from: 0000 */
    public final int getVerticalLayoutGap() {
        return this.mVerticalLayoutGap;
    }

    public final void setOverlayTop(int i) {
        int overlayTop = i;
        int i2 = overlayTop;
        this.mOverlayTop = overlayTop;
    }

    public final int getOverlayTop() {
        return this.mOverlayTop;
    }
}
