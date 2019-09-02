package android.support.design.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.util.AttributeSet;
import android.view.View;

class ViewOffsetBehavior<V extends View> extends Behavior<V> {
    private int mTempLeftRightOffset = 0;
    private int mTempTopBottomOffset = 0;
    private ViewOffsetHelper mViewOffsetHelper;

    public ViewOffsetBehavior() {
    }

    public ViewOffsetBehavior(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
    }

    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
        CoordinatorLayout parent = coordinatorLayout;
        V child = v;
        int layoutDirection = i;
        CoordinatorLayout coordinatorLayout2 = parent;
        V v2 = child;
        int i2 = layoutDirection;
        layoutChild(parent, child, layoutDirection);
        if (this.mViewOffsetHelper == null) {
            this.mViewOffsetHelper = new ViewOffsetHelper(child);
        }
        this.mViewOffsetHelper.onViewLayout();
        if (this.mTempTopBottomOffset != 0) {
            boolean topAndBottomOffset = this.mViewOffsetHelper.setTopAndBottomOffset(this.mTempTopBottomOffset);
            this.mTempTopBottomOffset = 0;
        }
        if (this.mTempLeftRightOffset != 0) {
            boolean leftAndRightOffset = this.mViewOffsetHelper.setLeftAndRightOffset(this.mTempLeftRightOffset);
            this.mTempLeftRightOffset = 0;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void layoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
        CoordinatorLayout parent = coordinatorLayout;
        V child = v;
        int layoutDirection = i;
        CoordinatorLayout coordinatorLayout2 = parent;
        V v2 = child;
        int i2 = layoutDirection;
        parent.onLayoutChild(child, layoutDirection);
    }

    public boolean setTopAndBottomOffset(int i) {
        int offset = i;
        int i2 = offset;
        if (this.mViewOffsetHelper != null) {
            return this.mViewOffsetHelper.setTopAndBottomOffset(offset);
        }
        this.mTempTopBottomOffset = offset;
        return false;
    }

    public boolean setLeftAndRightOffset(int i) {
        int offset = i;
        int i2 = offset;
        if (this.mViewOffsetHelper != null) {
            return this.mViewOffsetHelper.setLeftAndRightOffset(offset);
        }
        this.mTempLeftRightOffset = offset;
        return false;
    }

    public int getTopAndBottomOffset() {
        return this.mViewOffsetHelper == null ? 0 : this.mViewOffsetHelper.getTopAndBottomOffset();
    }

    public int getLeftAndRightOffset() {
        return this.mViewOffsetHelper == null ? 0 : this.mViewOffsetHelper.getLeftAndRightOffset();
    }
}
