package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.content.res.ConfigurationHelper;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ButtonBarLayout */
public class ButtonBarLayout extends LinearLayout {
    private static final int ALLOW_STACKING_MIN_HEIGHT_DP = 320;
    private static final int PEEK_BUTTON_DP = 16;
    private boolean mAllowStacking;
    private int mLastWidthSize = -1;

    public ButtonBarLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        boolean allowStackingDefault = ConfigurationHelper.getScreenHeightDp(getResources()) >= ALLOW_STACKING_MIN_HEIGHT_DP;
        TypedArray ta = context2.obtainStyledAttributes(attrs, C0268R.styleable.ButtonBarLayout);
        this.mAllowStacking = ta.getBoolean(C0268R.styleable.ButtonBarLayout_allowStacking, allowStackingDefault);
        ta.recycle();
    }

    public void setAllowStacking(boolean z) {
        boolean allowStacking = z;
        if (this.mAllowStacking != allowStacking) {
            this.mAllowStacking = allowStacking;
            if (!this.mAllowStacking && getOrientation() == 1) {
                setStacked(false);
            }
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int initialWidthMeasureSpec;
        boolean stack;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (this.mAllowStacking) {
            if (widthSize > this.mLastWidthSize && isStacked()) {
                setStacked(false);
            }
            this.mLastWidthSize = widthSize;
        }
        boolean needsRemeasure = false;
        if (!isStacked() && MeasureSpec.getMode(widthMeasureSpec) == 1073741824) {
            initialWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, Integer.MIN_VALUE);
            needsRemeasure = true;
        } else {
            initialWidthMeasureSpec = widthMeasureSpec;
        }
        super.onMeasure(initialWidthMeasureSpec, heightMeasureSpec);
        if (this.mAllowStacking && !isStacked()) {
            if (VERSION.SDK_INT < 11) {
                int childWidthTotal = 0;
                for (int i5 = 0; i5 < getChildCount(); i5++) {
                    childWidthTotal += getChildAt(i5).getMeasuredWidth();
                }
                stack = (childWidthTotal + getPaddingLeft()) + getPaddingRight() > widthSize;
            } else {
                int measuredWidthAndState = ViewCompat.getMeasuredWidthAndState(this);
                int i6 = measuredWidthAndState;
                int i7 = measuredWidthAndState & ViewCompat.MEASURED_STATE_MASK;
                int i8 = i7;
                stack = i7 == 16777216;
            }
            if (stack) {
                setStacked(true);
                needsRemeasure = true;
            }
        }
        if (needsRemeasure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        int minHeight = 0;
        int nextVisibleChildIndex = getNextVisibleChildIndex(0);
        int firstVisible = nextVisibleChildIndex;
        if (nextVisibleChildIndex >= 0) {
            View childAt = getChildAt(firstVisible);
            View firstButton = childAt;
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            minHeight = 0 + getPaddingTop() + firstButton.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (!isStacked()) {
                minHeight += getPaddingBottom();
            } else {
                int nextVisibleChildIndex2 = getNextVisibleChildIndex(firstVisible + 1);
                int secondVisible = nextVisibleChildIndex2;
                if (nextVisibleChildIndex2 >= 0) {
                    minHeight = (int) (((float) minHeight) + ((float) getChildAt(secondVisible).getPaddingTop()) + (16.0f * getResources().getDisplayMetrics().density));
                }
            }
        }
        if (ViewCompat.getMinimumHeight(this) != minHeight) {
            setMinimumHeight(minHeight);
        }
    }

    private int getNextVisibleChildIndex(int i) {
        int index = i;
        int i2 = index;
        int count = getChildCount();
        for (int i3 = index; i3 < count; i3++) {
            if (getChildAt(i3).getVisibility() == 0) {
                return i3;
            }
        }
        return -1;
    }

    private void setStacked(boolean z) {
        boolean stacked = z;
        setOrientation(!stacked ? 0 : 1);
        setGravity(!stacked ? 80 : 5);
        View findViewById = findViewById(C0268R.C0270id.spacer);
        View spacer = findViewById;
        if (findViewById != null) {
            spacer.setVisibility(!stacked ? 4 : 8);
        }
        int childCount = getChildCount();
        int i = childCount;
        for (int i2 = childCount - 2; i2 >= 0; i2--) {
            bringChildToFront(getChildAt(i2));
        }
    }

    private boolean isStacked() {
        return getOrientation() == 1;
    }
}
