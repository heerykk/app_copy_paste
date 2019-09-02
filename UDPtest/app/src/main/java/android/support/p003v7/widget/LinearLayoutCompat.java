package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.InputDeviceCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: android.support.v7.widget.LinearLayoutCompat */
public class LinearLayoutCompat extends ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v7.widget.LinearLayoutCompat$DividerMode */
    public @interface DividerMode {
    }

    /* renamed from: android.support.v7.widget.LinearLayoutCompat$LayoutParams */
    public static class LayoutParams extends MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            super(width, height);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int i, int i2, float f) {
            int width = i;
            int height = i2;
            float weight2 = f;
            int i3 = width;
            int i4 = height;
            float f2 = weight2;
            super(width, height);
            this.gravity = -1;
            this.weight = weight2;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            Context c = context;
            AttributeSet attrs = attributeSet;
            Context context2 = c;
            AttributeSet attributeSet2 = attrs;
            super(c, attrs);
            this.gravity = -1;
            TypedArray a = c.obtainStyledAttributes(attrs, C0268R.styleable.LinearLayoutCompat_Layout);
            this.weight = a.getFloat(C0268R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = a.getInt(C0268R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            a.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            LayoutParams source = layoutParams;
            LayoutParams layoutParams2 = source;
            super(source);
            this.gravity = -1;
            this.weight = source.weight;
            this.gravity = source.gravity;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.view.ViewGroup.LayoutParams p = layoutParams;
            android.view.ViewGroup.LayoutParams layoutParams2 = p;
            super(p);
            this.gravity = -1;
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams source = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = source;
            super(source);
            this.gravity = -1;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v7.widget.LinearLayoutCompat$OrientationMode */
    public @interface OrientationMode {
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, attrs, C0268R.styleable.LinearLayoutCompat, defStyleAttr, 0);
        TintTypedArray a = obtainStyledAttributes;
        int i3 = obtainStyledAttributes.getInt(C0268R.styleable.LinearLayoutCompat_android_orientation, -1);
        int index = i3;
        if (i3 >= 0) {
            setOrientation(index);
        }
        int i4 = a.getInt(C0268R.styleable.LinearLayoutCompat_android_gravity, -1);
        int index2 = i4;
        if (i4 >= 0) {
            setGravity(index2);
        }
        boolean z = a.getBoolean(C0268R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        boolean baselineAligned = z;
        if (!z) {
            setBaselineAligned(baselineAligned);
        }
        this.mWeightSum = a.getFloat(C0268R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = a.getInt(C0268R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = a.getBoolean(C0268R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(a.getDrawable(C0268R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = a.getInt(C0268R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = a.getDimensionPixelSize(C0268R.styleable.LinearLayoutCompat_dividerPadding, 0);
        a.recycle();
    }

    public LinearLayoutCompat(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void setShowDividers(int i) {
        int showDividers = i;
        int i2 = showDividers;
        if (showDividers != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = showDividers;
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public void setDividerDrawable(Drawable drawable) {
        Drawable divider = drawable;
        Drawable drawable2 = divider;
        if (divider != this.mDivider) {
            this.mDivider = divider;
            if (divider == null) {
                this.mDividerWidth = 0;
                this.mDividerHeight = 0;
            } else {
                this.mDividerWidth = divider.getIntrinsicWidth();
                this.mDividerHeight = divider.getIntrinsicHeight();
            }
            setWillNotDraw(divider == null);
            requestLayout();
        }
    }

    public void setDividerPadding(int i) {
        int padding = i;
        int i2 = padding;
        this.mDividerPadding = padding;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        if (this.mDivider != null) {
            if (this.mOrientation != 1) {
                drawDividersHorizontal(canvas2);
            } else {
                drawDividersVertical(canvas2);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void drawDividersVertical(Canvas canvas) {
        int bottom;
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        int count = getVirtualChildCount();
        for (int i = 0; i < count; i++) {
            View virtualChildAt = getVirtualChildAt(i);
            View child = virtualChildAt;
            if (!(virtualChildAt == null || child.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                int top = (child.getTop() - ((LayoutParams) child.getLayoutParams()).topMargin) - this.mDividerHeight;
                int i2 = top;
                drawHorizontalDivider(canvas2, top);
            }
        }
        if (hasDividerBeforeChildAt(count)) {
            View virtualChildAt2 = getVirtualChildAt(count - 1);
            View child2 = virtualChildAt2;
            if (virtualChildAt2 != null) {
                bottom = child2.getBottom() + ((LayoutParams) child2.getLayoutParams()).bottomMargin;
            } else {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            }
            drawHorizontalDivider(canvas2, bottom);
        }
    }

    /* access modifiers changed from: 0000 */
    public void drawDividersHorizontal(Canvas canvas) {
        int position;
        int position2;
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        int count = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i = 0; i < count; i++) {
            View virtualChildAt = getVirtualChildAt(i);
            View child = virtualChildAt;
            if (!(virtualChildAt == null || child.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (!isLayoutRtl) {
                    position2 = (child.getLeft() - lp.leftMargin) - this.mDividerWidth;
                } else {
                    position2 = child.getRight() + lp.rightMargin;
                }
                drawVerticalDivider(canvas2, position2);
            }
        }
        if (hasDividerBeforeChildAt(count)) {
            View virtualChildAt2 = getVirtualChildAt(count - 1);
            View child2 = virtualChildAt2;
            if (virtualChildAt2 != null) {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                if (!isLayoutRtl) {
                    position = child2.getRight() + lp2.rightMargin;
                } else {
                    position = (child2.getLeft() - lp2.leftMargin) - this.mDividerWidth;
                }
            } else if (!isLayoutRtl) {
                position = (getWidth() - getPaddingRight()) - this.mDividerWidth;
            } else {
                position = getPaddingLeft();
            }
            drawVerticalDivider(canvas2, position);
        }
    }

    /* access modifiers changed from: 0000 */
    public void drawHorizontalDivider(Canvas canvas, int i) {
        Canvas canvas2 = canvas;
        int top = i;
        Canvas canvas3 = canvas2;
        int i2 = top;
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, top, (getWidth() - getPaddingRight()) - this.mDividerPadding, top + this.mDividerHeight);
        this.mDivider.draw(canvas2);
    }

    /* access modifiers changed from: 0000 */
    public void drawVerticalDivider(Canvas canvas, int i) {
        Canvas canvas2 = canvas;
        int left = i;
        Canvas canvas3 = canvas2;
        int i2 = left;
        this.mDivider.setBounds(left, getPaddingTop() + this.mDividerPadding, left + this.mDividerWidth, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas2);
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public void setBaselineAligned(boolean z) {
        this.mBaselineAligned = z;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.mUseLargestChild = z;
    }

    public int getBaseline() {
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() > this.mBaselineAlignedChildIndex) {
            View childAt = getChildAt(this.mBaselineAlignedChildIndex);
            View child = childAt;
            int baseline = childAt.getBaseline();
            int childBaseline = baseline;
            if (baseline != -1) {
                int childTop = this.mBaselineChildTop;
                if (this.mOrientation == 1) {
                    int i = this.mGravity & 112;
                    int majorGravity = i;
                    if (i != 48) {
                        switch (majorGravity) {
                            case 16:
                                childTop += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
                                break;
                            case 80:
                                childTop = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
                                break;
                        }
                    }
                }
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams layoutParams2 = layoutParams;
                return childTop + layoutParams.topMargin + childBaseline;
            } else if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            } else {
                throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
            }
        } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public void setBaselineAlignedChildIndex(int i) {
        int i2 = i;
        int i3 = i2;
        if (i2 >= 0 && i2 < getChildCount()) {
            this.mBaselineAlignedChildIndex = i2;
            return;
        }
        throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
    }

    /* access modifiers changed from: 0000 */
    public View getVirtualChildAt(int i) {
        int index = i;
        int i2 = index;
        return getChildAt(index);
    }

    /* access modifiers changed from: 0000 */
    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    public void setWeightSum(float f) {
        float weightSum = f;
        float f2 = weightSum;
        this.mWeightSum = Math.max(0.0f, weightSum);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        if (this.mOrientation != 1) {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasDividerBeforeChildAt(int i) {
        int childIndex = i;
        int i2 = childIndex;
        if (childIndex == 0) {
            return (this.mShowDividers & 1) != 0;
        } else if (childIndex == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        } else if ((this.mShowDividers & 2) == 0) {
            return false;
        } else {
            boolean hasVisibleViewBefore = false;
            int i3 = childIndex - 1;
            while (true) {
                if (i3 >= 0) {
                    if (getChildAt(i3).getVisibility() != 8) {
                        hasVisibleViewBefore = true;
                        break;
                    }
                    i3--;
                } else {
                    break;
                }
            }
            return hasVisibleViewBefore;
        }
    }

    /* access modifiers changed from: 0000 */
    public void measureVertical(int i, int i2) {
        boolean z;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        this.mTotalLength = 0;
        int maxWidth = 0;
        int childState = 0;
        int alternativeMaxWidth = 0;
        int weightedMaxWidth = 0;
        boolean allFillParent = true;
        float totalWeight = 0.0f;
        int count = getVirtualChildCount();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        boolean matchWidth = false;
        boolean skippedMeasure = false;
        int baselineChildIndex = this.mBaselineAlignedChildIndex;
        boolean useLargestChild = this.mUseLargestChild;
        int largestChildHeight = Integer.MIN_VALUE;
        int i5 = 0;
        while (i5 < count) {
            View virtualChildAt = getVirtualChildAt(i5);
            View child = virtualChildAt;
            if (virtualChildAt == null) {
                this.mTotalLength += measureNullChild(i5);
            } else if (child.getVisibility() != 8) {
                if (hasDividerBeforeChildAt(i5)) {
                    this.mTotalLength += this.mDividerHeight;
                }
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                totalWeight += layoutParams.weight;
                if (heightMode == 1073741824 && layoutParams.height == 0 && lp.weight > 0.0f) {
                    int i6 = this.mTotalLength;
                    int i7 = i6;
                    this.mTotalLength = Math.max(i6, i6 + lp.topMargin + lp.bottomMargin);
                    skippedMeasure = true;
                } else {
                    int oldHeight = Integer.MIN_VALUE;
                    if (lp.height == 0 && lp.weight > 0.0f) {
                        oldHeight = 0;
                        lp.height = -2;
                    }
                    measureChildBeforeLayout(child, i5, widthMeasureSpec, 0, heightMeasureSpec, totalWeight == 0.0f ? this.mTotalLength : 0);
                    if (oldHeight != Integer.MIN_VALUE) {
                        lp.height = oldHeight;
                    }
                    int childHeight = child.getMeasuredHeight();
                    int i8 = this.mTotalLength;
                    int i9 = i8;
                    this.mTotalLength = Math.max(i8, i8 + childHeight + lp.topMargin + lp.bottomMargin + getNextLocationOffset(child));
                    if (useLargestChild) {
                        largestChildHeight = Math.max(childHeight, largestChildHeight);
                    }
                }
                if (baselineChildIndex >= 0 && baselineChildIndex == i5 + 1) {
                    this.mBaselineChildTop = this.mTotalLength;
                }
                if (i5 < baselineChildIndex && lp.weight > 0.0f) {
                    RuntimeException runtimeException = new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    throw runtimeException;
                }
                boolean matchWidthLocally = false;
                if (widthMode != 1073741824 && lp.width == -1) {
                    matchWidth = true;
                    matchWidthLocally = true;
                }
                int margin = lp.leftMargin + lp.rightMargin;
                int measuredWidth = child.getMeasuredWidth() + margin;
                maxWidth = Math.max(maxWidth, measuredWidth);
                childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(child));
                allFillParent = allFillParent && lp.width == -1;
                if (lp.weight > 0.0f) {
                    weightedMaxWidth = Math.max(weightedMaxWidth, !matchWidthLocally ? measuredWidth : margin);
                } else {
                    alternativeMaxWidth = Math.max(alternativeMaxWidth, !matchWidthLocally ? measuredWidth : margin);
                }
                i5 += getChildrenSkipCount(child, i5);
            } else {
                i5 += getChildrenSkipCount(child, i5);
            }
            i5++;
        }
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(count)) {
            this.mTotalLength += this.mDividerHeight;
        }
        if (useLargestChild && (heightMode == Integer.MIN_VALUE || heightMode == 0)) {
            this.mTotalLength = 0;
            int i10 = 0;
            while (i10 < count) {
                View virtualChildAt2 = getVirtualChildAt(i10);
                View child2 = virtualChildAt2;
                if (virtualChildAt2 == null) {
                    this.mTotalLength += measureNullChild(i10);
                } else if (child2.getVisibility() != 8) {
                    LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                    int i11 = this.mTotalLength;
                    int i12 = i11;
                    this.mTotalLength = Math.max(i11, i11 + largestChildHeight + lp2.topMargin + lp2.bottomMargin + getNextLocationOffset(child2));
                } else {
                    i10 += getChildrenSkipCount(child2, i10);
                }
                i10++;
            }
        }
        this.mTotalLength += getPaddingTop() + getPaddingBottom();
        int i13 = this.mTotalLength;
        int i14 = i13;
        int max = Math.max(i13, getSuggestedMinimumHeight());
        int heightSize = max;
        int resolveSizeAndState = ViewCompat.resolveSizeAndState(max, heightMeasureSpec, 0);
        int heightSizeAndState = resolveSizeAndState;
        int i15 = resolveSizeAndState & ViewCompat.MEASURED_SIZE_MASK;
        int heightSize2 = i15;
        int delta = i15 - this.mTotalLength;
        if (!skippedMeasure && (delta == 0 || totalWeight <= 0.0f)) {
            alternativeMaxWidth = Math.max(alternativeMaxWidth, weightedMaxWidth);
            if (useLargestChild && heightMode != 1073741824) {
                for (int i16 = 0; i16 < count; i16++) {
                    View virtualChildAt3 = getVirtualChildAt(i16);
                    View child3 = virtualChildAt3;
                    if (!(virtualChildAt3 == null || child3.getVisibility() == 8)) {
                        LayoutParams layoutParams2 = (LayoutParams) child3.getLayoutParams();
                        LayoutParams layoutParams3 = layoutParams2;
                        float f = layoutParams2.weight;
                        float f2 = f;
                        if (f > 0.0f) {
                            child3.measure(MeasureSpec.makeMeasureSpec(child3.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(largestChildHeight, 1073741824));
                        }
                    }
                }
            }
        } else {
            float weightSum = this.mWeightSum > 0.0f ? this.mWeightSum : totalWeight;
            this.mTotalLength = 0;
            for (int i17 = 0; i17 < count; i17++) {
                View virtualChildAt4 = getVirtualChildAt(i17);
                View child4 = virtualChildAt4;
                if (virtualChildAt4.getVisibility() != 8) {
                    LayoutParams layoutParams4 = (LayoutParams) child4.getLayoutParams();
                    LayoutParams lp3 = layoutParams4;
                    float f3 = layoutParams4.weight;
                    float childExtra = f3;
                    if (f3 > 0.0f) {
                        int share = (int) ((childExtra * ((float) delta)) / weightSum);
                        weightSum -= childExtra;
                        delta -= share;
                        int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight() + lp3.leftMargin + lp3.rightMargin, lp3.width);
                        if (lp3.height == 0 && heightMode == 1073741824) {
                            child4.measure(childWidthMeasureSpec, MeasureSpec.makeMeasureSpec(share <= 0 ? 0 : share, 1073741824));
                        } else {
                            int measuredHeight = child4.getMeasuredHeight() + share;
                            int childHeight2 = measuredHeight;
                            if (measuredHeight < 0) {
                                childHeight2 = 0;
                            }
                            child4.measure(childWidthMeasureSpec, MeasureSpec.makeMeasureSpec(childHeight2, 1073741824));
                        }
                        childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(child4) & InputDeviceCompat.SOURCE_ANY);
                    }
                    int margin2 = lp3.leftMargin + lp3.rightMargin;
                    int measuredWidth2 = child4.getMeasuredWidth() + margin2;
                    maxWidth = Math.max(maxWidth, measuredWidth2);
                    alternativeMaxWidth = Math.max(alternativeMaxWidth, !(widthMode != 1073741824 && lp3.width == -1) ? measuredWidth2 : margin2);
                    if (allFillParent && lp3.width == -1) {
                        z = true;
                    } else {
                        z = false;
                    }
                    allFillParent = z;
                    int i18 = this.mTotalLength;
                    int i19 = i18;
                    this.mTotalLength = Math.max(i18, i18 + child4.getMeasuredHeight() + lp3.topMargin + lp3.bottomMargin + getNextLocationOffset(child4));
                }
            }
            this.mTotalLength += getPaddingTop() + getPaddingBottom();
        }
        if (!allFillParent && widthMode != 1073741824) {
            maxWidth = alternativeMaxWidth;
        }
        int paddingLeft = maxWidth + getPaddingLeft() + getPaddingRight();
        int maxWidth2 = paddingLeft;
        int max2 = Math.max(paddingLeft, getSuggestedMinimumWidth());
        int maxWidth3 = max2;
        setMeasuredDimension(ViewCompat.resolveSizeAndState(max2, widthMeasureSpec, childState), heightSizeAndState);
        if (matchWidth) {
            forceUniformWidth(count, heightMeasureSpec);
        }
    }

    private void forceUniformWidth(int i, int i2) {
        int count = i;
        int heightMeasureSpec = i2;
        int i3 = count;
        int i4 = heightMeasureSpec;
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i5 = 0; i5 < count; i5++) {
            View virtualChildAt = getVirtualChildAt(i5);
            View child = virtualChildAt;
            if (virtualChildAt.getVisibility() != 8) {
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

    /* access modifiers changed from: 0000 */
    public void measureHorizontal(int i, int i2) {
        int i3;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i4 = widthMeasureSpec;
        int i5 = heightMeasureSpec;
        this.mTotalLength = 0;
        int maxHeight = 0;
        int childState = 0;
        int alternativeMaxHeight = 0;
        int weightedMaxHeight = 0;
        boolean allFillParent = true;
        float totalWeight = 0.0f;
        int count = getVirtualChildCount();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        boolean matchHeight = false;
        boolean skippedMeasure = false;
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] maxAscent = this.mMaxAscent;
        int[] maxDescent = this.mMaxDescent;
        maxAscent[3] = -1;
        maxAscent[2] = -1;
        maxAscent[1] = -1;
        maxAscent[0] = -1;
        maxDescent[3] = -1;
        maxDescent[2] = -1;
        maxDescent[1] = -1;
        maxDescent[0] = -1;
        boolean baselineAligned = this.mBaselineAligned;
        boolean useLargestChild = this.mUseLargestChild;
        boolean isExactly = widthMode == 1073741824;
        int largestChildWidth = Integer.MIN_VALUE;
        int i6 = 0;
        while (i6 < count) {
            View virtualChildAt = getVirtualChildAt(i6);
            View child = virtualChildAt;
            if (virtualChildAt == null) {
                this.mTotalLength += measureNullChild(i6);
            } else if (child.getVisibility() != 8) {
                if (hasDividerBeforeChildAt(i6)) {
                    this.mTotalLength += this.mDividerWidth;
                }
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                totalWeight += layoutParams.weight;
                if (widthMode == 1073741824 && layoutParams.width == 0 && lp.weight > 0.0f) {
                    if (!isExactly) {
                        int i7 = this.mTotalLength;
                        int i8 = i7;
                        this.mTotalLength = Math.max(i7, i7 + lp.leftMargin + lp.rightMargin);
                    } else {
                        this.mTotalLength += lp.leftMargin + lp.rightMargin;
                    }
                    if (!baselineAligned) {
                        skippedMeasure = true;
                    } else {
                        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
                        int i9 = makeMeasureSpec;
                        child.measure(makeMeasureSpec, makeMeasureSpec);
                    }
                } else {
                    int oldWidth = Integer.MIN_VALUE;
                    if (lp.width == 0 && lp.weight > 0.0f) {
                        oldWidth = 0;
                        lp.width = -2;
                    }
                    measureChildBeforeLayout(child, i6, widthMeasureSpec, totalWeight == 0.0f ? this.mTotalLength : 0, heightMeasureSpec, 0);
                    if (oldWidth != Integer.MIN_VALUE) {
                        lp.width = oldWidth;
                    }
                    int childWidth = child.getMeasuredWidth();
                    if (!isExactly) {
                        int i10 = this.mTotalLength;
                        int i11 = i10;
                        this.mTotalLength = Math.max(i10, i10 + childWidth + lp.leftMargin + lp.rightMargin + getNextLocationOffset(child));
                    } else {
                        this.mTotalLength += childWidth + lp.leftMargin + lp.rightMargin + getNextLocationOffset(child);
                    }
                    if (useLargestChild) {
                        largestChildWidth = Math.max(childWidth, largestChildWidth);
                    }
                }
                boolean matchHeightLocally = false;
                if (heightMode != 1073741824 && lp.height == -1) {
                    matchHeight = true;
                    matchHeightLocally = true;
                }
                int margin = lp.topMargin + lp.bottomMargin;
                int childHeight = child.getMeasuredHeight() + margin;
                childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(child));
                if (baselineAligned) {
                    int baseline = child.getBaseline();
                    int childBaseline = baseline;
                    if (baseline != -1) {
                        int i12 = (lp.gravity >= 0 ? lp.gravity : this.mGravity) & 112;
                        int i13 = i12;
                        int i14 = ((i12 >> 4) & -2) >> 1;
                        int i15 = i14;
                        maxAscent[i14] = Math.max(maxAscent[i14], childBaseline);
                        maxDescent[i14] = Math.max(maxDescent[i14], childHeight - childBaseline);
                    }
                }
                maxHeight = Math.max(maxHeight, childHeight);
                allFillParent = allFillParent && lp.height == -1;
                if (lp.weight > 0.0f) {
                    weightedMaxHeight = Math.max(weightedMaxHeight, !matchHeightLocally ? childHeight : margin);
                } else {
                    alternativeMaxHeight = Math.max(alternativeMaxHeight, !matchHeightLocally ? childHeight : margin);
                }
                i6 += getChildrenSkipCount(child, i6);
            } else {
                i6 += getChildrenSkipCount(child, i6);
            }
            i6++;
        }
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(count)) {
            this.mTotalLength += this.mDividerWidth;
        }
        if (!(maxAscent[1] == -1 && maxAscent[0] == -1 && maxAscent[2] == -1 && maxAscent[3] == -1)) {
            maxHeight = Math.max(maxHeight, Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2]))) + Math.max(maxDescent[3], Math.max(maxDescent[0], Math.max(maxDescent[1], maxDescent[2]))));
        }
        if (useLargestChild && (widthMode == Integer.MIN_VALUE || widthMode == 0)) {
            this.mTotalLength = 0;
            int i16 = 0;
            while (i16 < count) {
                View virtualChildAt2 = getVirtualChildAt(i16);
                View child2 = virtualChildAt2;
                if (virtualChildAt2 == null) {
                    this.mTotalLength += measureNullChild(i16);
                } else if (child2.getVisibility() != 8) {
                    LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                    if (!isExactly) {
                        int i17 = this.mTotalLength;
                        int i18 = i17;
                        this.mTotalLength = Math.max(i17, i17 + largestChildWidth + lp2.leftMargin + lp2.rightMargin + getNextLocationOffset(child2));
                    } else {
                        this.mTotalLength += largestChildWidth + lp2.leftMargin + lp2.rightMargin + getNextLocationOffset(child2);
                    }
                } else {
                    i16 += getChildrenSkipCount(child2, i16);
                }
                i16++;
            }
        }
        this.mTotalLength += getPaddingLeft() + getPaddingRight();
        int i19 = this.mTotalLength;
        int i20 = i19;
        int max = Math.max(i19, getSuggestedMinimumWidth());
        int widthSize = max;
        int resolveSizeAndState = ViewCompat.resolveSizeAndState(max, widthMeasureSpec, 0);
        int widthSizeAndState = resolveSizeAndState;
        int i21 = resolveSizeAndState & ViewCompat.MEASURED_SIZE_MASK;
        int widthSize2 = i21;
        int delta = i21 - this.mTotalLength;
        if (!skippedMeasure && (delta == 0 || totalWeight <= 0.0f)) {
            alternativeMaxHeight = Math.max(alternativeMaxHeight, weightedMaxHeight);
            if (useLargestChild && widthMode != 1073741824) {
                for (int i22 = 0; i22 < count; i22++) {
                    View virtualChildAt3 = getVirtualChildAt(i22);
                    View child3 = virtualChildAt3;
                    if (!(virtualChildAt3 == null || child3.getVisibility() == 8)) {
                        LayoutParams layoutParams2 = (LayoutParams) child3.getLayoutParams();
                        LayoutParams layoutParams3 = layoutParams2;
                        float f = layoutParams2.weight;
                        float f2 = f;
                        if (f > 0.0f) {
                            child3.measure(MeasureSpec.makeMeasureSpec(largestChildWidth, 1073741824), MeasureSpec.makeMeasureSpec(child3.getMeasuredHeight(), 1073741824));
                        }
                    }
                }
            }
        } else {
            float weightSum = this.mWeightSum > 0.0f ? this.mWeightSum : totalWeight;
            maxAscent[3] = -1;
            maxAscent[2] = -1;
            maxAscent[1] = -1;
            maxAscent[0] = -1;
            maxDescent[3] = -1;
            maxDescent[2] = -1;
            maxDescent[1] = -1;
            maxDescent[0] = -1;
            maxHeight = -1;
            this.mTotalLength = 0;
            for (int i23 = 0; i23 < count; i23++) {
                View virtualChildAt4 = getVirtualChildAt(i23);
                View child4 = virtualChildAt4;
                if (!(virtualChildAt4 == null || child4.getVisibility() == 8)) {
                    LayoutParams layoutParams4 = (LayoutParams) child4.getLayoutParams();
                    LayoutParams lp3 = layoutParams4;
                    float f3 = layoutParams4.weight;
                    float childExtra = f3;
                    if (f3 > 0.0f) {
                        int share = (int) ((childExtra * ((float) delta)) / weightSum);
                        weightSum -= childExtra;
                        delta -= share;
                        int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom() + lp3.topMargin + lp3.bottomMargin, lp3.height);
                        if (lp3.width == 0 && widthMode == 1073741824) {
                            if (share <= 0) {
                                i3 = 0;
                            } else {
                                i3 = share;
                            }
                            child4.measure(MeasureSpec.makeMeasureSpec(i3, 1073741824), childHeightMeasureSpec);
                        } else {
                            int measuredWidth = child4.getMeasuredWidth() + share;
                            int childWidth2 = measuredWidth;
                            if (measuredWidth < 0) {
                                childWidth2 = 0;
                            }
                            child4.measure(MeasureSpec.makeMeasureSpec(childWidth2, 1073741824), childHeightMeasureSpec);
                        }
                        childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(child4) & ViewCompat.MEASURED_STATE_MASK);
                    }
                    if (!isExactly) {
                        int i24 = this.mTotalLength;
                        int i25 = i24;
                        this.mTotalLength = Math.max(i24, i24 + child4.getMeasuredWidth() + lp3.leftMargin + lp3.rightMargin + getNextLocationOffset(child4));
                    } else {
                        this.mTotalLength += child4.getMeasuredWidth() + lp3.leftMargin + lp3.rightMargin + getNextLocationOffset(child4);
                    }
                    boolean matchHeightLocally2 = heightMode != 1073741824 && lp3.height == -1;
                    int margin2 = lp3.topMargin + lp3.bottomMargin;
                    int childHeight2 = child4.getMeasuredHeight() + margin2;
                    maxHeight = Math.max(maxHeight, childHeight2);
                    alternativeMaxHeight = Math.max(alternativeMaxHeight, !matchHeightLocally2 ? childHeight2 : margin2);
                    allFillParent = allFillParent && lp3.height == -1;
                    if (baselineAligned) {
                        int baseline2 = child4.getBaseline();
                        int childBaseline2 = baseline2;
                        if (baseline2 != -1) {
                            int i26 = (lp3.gravity >= 0 ? lp3.gravity : this.mGravity) & 112;
                            int i27 = i26;
                            int i28 = ((i26 >> 4) & -2) >> 1;
                            int i29 = i28;
                            maxAscent[i28] = Math.max(maxAscent[i28], childBaseline2);
                            maxDescent[i28] = Math.max(maxDescent[i28], childHeight2 - childBaseline2);
                        }
                    }
                }
            }
            this.mTotalLength += getPaddingLeft() + getPaddingRight();
            if (!(maxAscent[1] == -1 && maxAscent[0] == -1 && maxAscent[2] == -1 && maxAscent[3] == -1)) {
                maxHeight = Math.max(maxHeight, Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2]))) + Math.max(maxDescent[3], Math.max(maxDescent[0], Math.max(maxDescent[1], maxDescent[2]))));
            }
        }
        if (!allFillParent && heightMode != 1073741824) {
            maxHeight = alternativeMaxHeight;
        }
        int paddingTop = maxHeight + getPaddingTop() + getPaddingBottom();
        int maxHeight2 = paddingTop;
        int max2 = Math.max(paddingTop, getSuggestedMinimumHeight());
        int maxHeight3 = max2;
        setMeasuredDimension(widthSizeAndState | (childState & ViewCompat.MEASURED_STATE_MASK), ViewCompat.resolveSizeAndState(max2, heightMeasureSpec, childState << 16));
        if (matchHeight) {
            forceUniformHeight(count, widthMeasureSpec);
        }
    }

    private void forceUniformHeight(int i, int i2) {
        int count = i;
        int widthMeasureSpec = i2;
        int i3 = count;
        int i4 = widthMeasureSpec;
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i5 = 0; i5 < count; i5++) {
            View virtualChildAt = getVirtualChildAt(i5);
            View child = virtualChildAt;
            if (virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.height == -1) {
                    int i6 = lp.width;
                    int i7 = i6;
                    lp.width = child.getMeasuredWidth();
                    measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
                    lp.width = i6;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int getChildrenSkipCount(View view, int i) {
        View view2 = view;
        int i2 = i;
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public int measureNullChild(int i) {
        int i2 = i;
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public void measureChildBeforeLayout(View view, int i, int i2, int i3, int i4, int i5) {
        View child = view;
        int widthMeasureSpec = i2;
        int totalWidth = i3;
        int heightMeasureSpec = i4;
        int totalHeight = i5;
        View view2 = child;
        int i6 = i;
        int i7 = widthMeasureSpec;
        int i8 = totalWidth;
        int i9 = heightMeasureSpec;
        int i10 = totalHeight;
        measureChildWithMargins(child, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight);
    }

    /* access modifiers changed from: 0000 */
    public int getLocationOffset(View view) {
        View view2 = view;
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public int getNextLocationOffset(View view) {
        View view2 = view;
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int l = i;
        int t = i2;
        int r = i3;
        int b = i4;
        boolean z2 = z;
        int i5 = l;
        int i6 = t;
        int i7 = r;
        int i8 = b;
        if (this.mOrientation != 1) {
            layoutHorizontal(l, t, r, b);
        } else {
            layoutVertical(l, t, r, b);
        }
    }

    /* access modifiers changed from: 0000 */
    public void layoutVertical(int i, int i2, int i3, int i4) {
        int childTop;
        int childLeft;
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        int paddingLeft = getPaddingLeft();
        int i9 = right - left;
        int childRight = i9 - getPaddingRight();
        int childSpace = (i9 - paddingLeft) - getPaddingRight();
        int count = getVirtualChildCount();
        int minorGravity = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        switch (this.mGravity & 112) {
            case 16:
                childTop = getPaddingTop() + (((bottom - top) - this.mTotalLength) / 2);
                break;
            case 80:
                childTop = ((getPaddingTop() + bottom) - top) - this.mTotalLength;
                break;
            default:
                childTop = getPaddingTop();
                break;
        }
        int i10 = 0;
        while (i10 < count) {
            View virtualChildAt = getVirtualChildAt(i10);
            View child = virtualChildAt;
            if (virtualChildAt == null) {
                childTop += measureNullChild(i10);
            } else if (child.getVisibility() != 8) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                int i11 = layoutParams.gravity;
                int gravity = i11;
                if (i11 < 0) {
                    gravity = minorGravity;
                }
                int absoluteGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this));
                int i12 = absoluteGravity;
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
                if (hasDividerBeforeChildAt(i10)) {
                    childTop += this.mDividerHeight;
                }
                int i13 = childTop + lp.topMargin;
                int childTop2 = i13;
                setChildFrame(child, childLeft, i13 + getLocationOffset(child), childWidth, childHeight);
                childTop = i13 + childHeight + lp.bottomMargin + getNextLocationOffset(child);
                i10 += getChildrenSkipCount(child, i10);
            }
            i10++;
        }
    }

    /* access modifiers changed from: 0000 */
    public void layoutHorizontal(int i, int i2, int i3, int i4) {
        int childLeft;
        int childTop;
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop = getPaddingTop();
        int i9 = bottom - top;
        int childBottom = i9 - getPaddingBottom();
        int childSpace = (i9 - paddingTop) - getPaddingBottom();
        int count = getVirtualChildCount();
        int majorGravity = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int minorGravity = this.mGravity & 112;
        boolean baselineAligned = this.mBaselineAligned;
        int[] maxAscent = this.mMaxAscent;
        int[] maxDescent = this.mMaxDescent;
        switch (GravityCompat.getAbsoluteGravity(majorGravity, ViewCompat.getLayoutDirection(this))) {
            case 1:
                childLeft = getPaddingLeft() + (((right - left) - this.mTotalLength) / 2);
                break;
            case 5:
                childLeft = ((getPaddingLeft() + right) - left) - this.mTotalLength;
                break;
            default:
                childLeft = getPaddingLeft();
                break;
        }
        int start = 0;
        int dir = 1;
        if (isLayoutRtl) {
            start = count - 1;
            dir = -1;
        }
        int i10 = 0;
        while (i10 < count) {
            int i11 = start + (dir * i10);
            int childIndex = i11;
            View virtualChildAt = getVirtualChildAt(i11);
            View child = virtualChildAt;
            if (virtualChildAt == null) {
                childLeft += measureNullChild(childIndex);
            } else if (child.getVisibility() != 8) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                int childBaseline = -1;
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (baselineAligned && layoutParams.height != -1) {
                    childBaseline = child.getBaseline();
                }
                int i12 = lp.gravity;
                int gravity = i12;
                if (i12 < 0) {
                    gravity = minorGravity;
                }
                switch (gravity & 112) {
                    case 16:
                        childTop = ((paddingTop + ((childSpace - childHeight) / 2)) + lp.topMargin) - lp.bottomMargin;
                        break;
                    case 48:
                        childTop = paddingTop + lp.topMargin;
                        if (childBaseline != -1) {
                            childTop += maxAscent[1] - childBaseline;
                            break;
                        }
                        break;
                    case 80:
                        childTop = (childBottom - childHeight) - lp.bottomMargin;
                        if (childBaseline != -1) {
                            childTop -= maxDescent[2] - (child.getMeasuredHeight() - childBaseline);
                            break;
                        }
                        break;
                    default:
                        childTop = paddingTop;
                        break;
                }
                if (hasDividerBeforeChildAt(childIndex)) {
                    childLeft += this.mDividerWidth;
                }
                int i13 = childLeft + lp.leftMargin;
                int childLeft2 = i13;
                setChildFrame(child, i13 + getLocationOffset(child), childTop, childWidth, childHeight);
                childLeft = i13 + childWidth + lp.rightMargin + getNextLocationOffset(child);
                i10 += getChildrenSkipCount(child, childIndex);
            }
            i10++;
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

    public void setOrientation(int i) {
        int orientation = i;
        int i2 = orientation;
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setGravity(int i) {
        int gravity = i;
        int gravity2 = gravity;
        if (this.mGravity != gravity) {
            if ((gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
                gravity2 = gravity | GravityCompat.START;
            }
            if ((gravity2 & 112) == 0) {
                gravity2 |= 48;
            }
            this.mGravity = gravity2;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setHorizontalGravity(int i) {
        int horizontalGravity = i;
        int i2 = horizontalGravity;
        int gravity = horizontalGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if ((this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) != gravity) {
            this.mGravity = (this.mGravity & -8388616) | gravity;
            requestLayout();
        }
    }

    public void setVerticalGravity(int i) {
        int verticalGravity = i;
        int i2 = verticalGravity;
        int gravity = verticalGravity & 112;
        if ((this.mGravity & 112) != gravity) {
            this.mGravity = (this.mGravity & -113) | gravity;
            requestLayout();
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AttributeSet attrs = attributeSet;
        AttributeSet attributeSet2 = attrs;
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -2);
        }
        if (this.mOrientation != 1) {
            return null;
        }
        return new LayoutParams(-1, -2);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return new LayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return p instanceof LayoutParams;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityEvent(event);
            event.setClassName(LinearLayoutCompat.class.getName());
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo info = accessibilityNodeInfo;
        AccessibilityNodeInfo accessibilityNodeInfo2 = info;
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(info);
            info.setClassName(LinearLayoutCompat.class.getName());
        }
    }
}
