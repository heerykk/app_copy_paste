package android.support.design.internal;

import android.content.Context;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BaselineLayout extends ViewGroup {
    private int mBaseline = -1;

    public BaselineLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2, null, 0);
    }

    public BaselineLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs, 0);
    }

    public BaselineLayout(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        int count = getChildCount();
        int maxWidth = 0;
        int maxHeight = 0;
        int maxChildBaseline = -1;
        int maxChildDescent = -1;
        int childState = 0;
        for (int i5 = 0; i5 < count; i5++) {
            View childAt = getChildAt(i5);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                int baseline = child.getBaseline();
                int baseline2 = baseline;
                if (baseline != -1) {
                    maxChildBaseline = Math.max(maxChildBaseline, baseline2);
                    maxChildDescent = Math.max(maxChildDescent, child.getMeasuredHeight() - baseline2);
                }
                maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
                childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(child));
            }
        }
        if (maxChildBaseline != -1) {
            maxHeight = Math.max(maxHeight, maxChildBaseline + Math.max(maxChildDescent, getPaddingBottom()));
            this.mBaseline = maxChildBaseline;
        }
        int maxHeight2 = Math.max(maxHeight, getSuggestedMinimumHeight());
        int max = Math.max(maxWidth, getSuggestedMinimumWidth());
        int maxWidth2 = max;
        setMeasuredDimension(ViewCompat.resolveSizeAndState(max, widthMeasureSpec, childState), ViewCompat.resolveSizeAndState(maxHeight2, heightMeasureSpec, childState << 16));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childTop;
        int left = i;
        int right = i3;
        boolean z2 = z;
        int i5 = left;
        int i6 = i2;
        int i7 = right;
        int i8 = i4;
        int count = getChildCount();
        int parentLeft = getPaddingLeft();
        int paddingRight = (right - left) - getPaddingRight();
        int i9 = paddingRight;
        int parentContentWidth = paddingRight - parentLeft;
        int parentTop = getPaddingTop();
        for (int i10 = 0; i10 < count; i10++) {
            View childAt = getChildAt(i10);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                int childLeft = parentLeft + ((parentContentWidth - width) / 2);
                if (this.mBaseline == -1 || child.getBaseline() == -1) {
                    childTop = parentTop;
                } else {
                    childTop = (parentTop + this.mBaseline) - child.getBaseline();
                }
                child.layout(childLeft, childTop, childLeft + width, childTop + height);
            }
        }
    }

    public int getBaseline() {
        return this.mBaseline;
    }
}
