package android.support.p003v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.widget.LinearLayoutCompat.LayoutParams;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.AlertDialogLayout */
public class AlertDialogLayout extends LinearLayoutCompat {
    public AlertDialogLayout(@Nullable Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
    }

    public AlertDialogLayout(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        if (!tryOnMeasure(widthMeasureSpec, heightMeasureSpec)) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private boolean tryOnMeasure(int i, int i2) {
        int childHeightSpec;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        View topPanel = null;
        View buttonPanel = null;
        View middlePanel = null;
        int count = getChildCount();
        for (int i5 = 0; i5 < count; i5++) {
            View childAt = getChildAt(i5);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                int id = child.getId();
                int id2 = id;
                if (id == C0268R.C0270id.topPanel) {
                    topPanel = child;
                } else if (id2 == C0268R.C0270id.buttonPanel) {
                    buttonPanel = child;
                } else if (id2 != C0268R.C0270id.contentPanel && id2 != C0268R.C0270id.customPanel) {
                    return false;
                } else {
                    if (middlePanel != null) {
                        return false;
                    }
                    middlePanel = child;
                }
            }
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int childState = 0;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int usedHeight = paddingTop;
        if (topPanel != null) {
            topPanel.measure(widthMeasureSpec, 0);
            usedHeight = paddingTop + topPanel.getMeasuredHeight();
            childState = ViewCompat.combineMeasuredStates(0, ViewCompat.getMeasuredState(topPanel));
        }
        int buttonHeight = 0;
        int buttonWantsHeight = 0;
        if (buttonPanel != null) {
            buttonPanel.measure(widthMeasureSpec, 0);
            buttonHeight = resolveMinimumHeight(buttonPanel);
            buttonWantsHeight = buttonPanel.getMeasuredHeight() - buttonHeight;
            usedHeight += buttonHeight;
            childState = ViewCompat.combineMeasuredStates(childState, ViewCompat.getMeasuredState(buttonPanel));
        }
        int middleHeight = 0;
        if (middlePanel != null) {
            if (heightMode != 0) {
                childHeightSpec = MeasureSpec.makeMeasureSpec(Math.max(0, heightSize - usedHeight), heightMode);
            } else {
                childHeightSpec = 0;
            }
            middlePanel.measure(widthMeasureSpec, childHeightSpec);
            middleHeight = middlePanel.getMeasuredHeight();
            usedHeight += middleHeight;
            childState = ViewCompat.combineMeasuredStates(childState, ViewCompat.getMeasuredState(middlePanel));
        }
        int remainingHeight = heightSize - usedHeight;
        if (buttonPanel != null) {
            int usedHeight2 = usedHeight - buttonHeight;
            int min = Math.min(remainingHeight, buttonWantsHeight);
            int heightToGive = min;
            if (min > 0) {
                remainingHeight -= heightToGive;
                buttonHeight += heightToGive;
            }
            int makeMeasureSpec = MeasureSpec.makeMeasureSpec(buttonHeight, 1073741824);
            int i6 = makeMeasureSpec;
            buttonPanel.measure(widthMeasureSpec, makeMeasureSpec);
            usedHeight = usedHeight2 + buttonPanel.getMeasuredHeight();
            childState = ViewCompat.combineMeasuredStates(childState, ViewCompat.getMeasuredState(buttonPanel));
        }
        if (middlePanel != null && remainingHeight > 0) {
            int usedHeight3 = usedHeight - middleHeight;
            int heightToGive2 = remainingHeight;
            int remainingHeight2 = remainingHeight - heightToGive2;
            int i7 = middleHeight + heightToGive2;
            int middleHeight2 = i7;
            int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(i7, heightMode);
            int i8 = makeMeasureSpec2;
            middlePanel.measure(widthMeasureSpec, makeMeasureSpec2);
            usedHeight = usedHeight3 + middlePanel.getMeasuredHeight();
            childState = ViewCompat.combineMeasuredStates(childState, ViewCompat.getMeasuredState(middlePanel));
        }
        int maxWidth = 0;
        for (int i9 = 0; i9 < count; i9++) {
            View childAt2 = getChildAt(i9);
            View child2 = childAt2;
            if (childAt2.getVisibility() != 8) {
                maxWidth = Math.max(maxWidth, child2.getMeasuredWidth());
            }
        }
        int paddingLeft = maxWidth + getPaddingLeft() + getPaddingRight();
        int maxWidth2 = paddingLeft;
        int widthSizeAndState = ViewCompat.resolveSizeAndState(paddingLeft, widthMeasureSpec, childState);
        int resolveSizeAndState = ViewCompat.resolveSizeAndState(usedHeight, heightMeasureSpec, 0);
        int i10 = resolveSizeAndState;
        setMeasuredDimension(widthSizeAndState, resolveSizeAndState);
        if (widthMode != 1073741824) {
            forceUniformWidth(count, heightMeasureSpec);
        }
        return true;
    }

    private void forceUniformWidth(int i, int i2) {
        int count = i;
        int heightMeasureSpec = i2;
        int i3 = count;
        int i4 = heightMeasureSpec;
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i5 = 0; i5 < count; i5++) {
            View childAt = getChildAt(i5);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.width == -1) {
                    int i6 = lp.height;
                    int i7 = i6;
                    lp.height = child.getMeasuredHeight();
                    measureChildWithMargins(child, uniformMeasureSpec, 0, heightMeasureSpec, 0);
                    lp.height = i6;
                }
            }
        }
    }

    private static int resolveMinimumHeight(View view) {
        View v = view;
        View view2 = v;
        int minimumHeight = ViewCompat.getMinimumHeight(v);
        int minHeight = minimumHeight;
        if (minimumHeight > 0) {
            return minHeight;
        }
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            ViewGroup vg = viewGroup;
            if (viewGroup.getChildCount() == 1) {
                return resolveMinimumHeight(vg.getChildAt(0));
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childTop;
        int i5;
        int childLeft;
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        boolean z2 = z;
        int i6 = left;
        int i7 = top;
        int i8 = right;
        int i9 = bottom;
        int paddingLeft = getPaddingLeft();
        int i10 = right - left;
        int childRight = i10 - getPaddingRight();
        int childSpace = (i10 - paddingLeft) - getPaddingRight();
        int totalLength = getMeasuredHeight();
        int count = getChildCount();
        int gravity = getGravity();
        int minorGravity = gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        switch (gravity & 112) {
            case 16:
                childTop = getPaddingTop() + (((bottom - top) - totalLength) / 2);
                break;
            case 80:
                childTop = ((getPaddingTop() + bottom) - top) - totalLength;
                break;
            default:
                childTop = getPaddingTop();
                break;
        }
        Drawable dividerDrawable = getDividerDrawable();
        Drawable dividerDrawable2 = dividerDrawable;
        if (dividerDrawable != null) {
            i5 = dividerDrawable2.getIntrinsicHeight();
        } else {
            i5 = 0;
        }
        int dividerHeight = i5;
        for (int i11 = 0; i11 < count; i11++) {
            View childAt = getChildAt(i11);
            View child = childAt;
            if (!(childAt == null || child.getVisibility() == 8)) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                int i12 = layoutParams.gravity;
                int layoutGravity = i12;
                if (i12 < 0) {
                    layoutGravity = minorGravity;
                }
                int absoluteGravity = GravityCompat.getAbsoluteGravity(layoutGravity, ViewCompat.getLayoutDirection(this));
                int i13 = absoluteGravity;
                switch (absoluteGravity & 7) {
                    case 1:
                        childLeft = ((paddingLeft + ((childSpace - childWidth) / 2)) + lp.leftMargin) - lp.rightMargin;
                        break;
                    case 5:
                        childLeft = (childRight - childWidth) - lp.rightMargin;
                        break;
                    default:
                        childLeft = paddingLeft + lp.leftMargin;
                        break;
                }
                if (hasDividerBeforeChildAt(i11)) {
                    childTop += dividerHeight;
                }
                int i14 = childTop + lp.topMargin;
                int childTop2 = i14;
                setChildFrame(child, childLeft, i14, childWidth, childHeight);
                childTop = i14 + childHeight + lp.bottomMargin;
            }
        }
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) {
        View child = view;
        int left = i;
        int top = i2;
        int width = i3;
        int height = i4;
        View view2 = child;
        int i5 = left;
        int i6 = top;
        int i7 = width;
        int i8 = height;
        child.layout(left, top, left + width, top + height);
    }
}
