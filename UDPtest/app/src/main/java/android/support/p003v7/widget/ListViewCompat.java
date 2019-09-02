package android.support.p003v7.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p003v7.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.reflect.Field;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ListViewCompat */
public class ListViewCompat extends ListView {
    public static final int INVALID_POSITION = -1;
    public static final int NO_POSITION = -1;
    private static final int[] STATE_SET_NOTHING;
    private Field mIsChildViewEnabled;
    protected int mMotionPosition;
    int mSelectionBottomPadding;
    int mSelectionLeftPadding;
    int mSelectionRightPadding;
    int mSelectionTopPadding;
    private GateKeeperDrawable mSelector;
    final Rect mSelectorRect;

    /* renamed from: android.support.v7.widget.ListViewCompat$GateKeeperDrawable */
    private static class GateKeeperDrawable extends DrawableWrapper {
        private boolean mEnabled = true;

        public GateKeeperDrawable(Drawable drawable) {
            Drawable drawable2 = drawable;
            Drawable drawable3 = drawable2;
            super(drawable2);
        }

        /* access modifiers changed from: 0000 */
        public void setEnabled(boolean z) {
            this.mEnabled = z;
        }

        public boolean setState(int[] iArr) {
            int[] stateSet = iArr;
            int[] iArr2 = stateSet;
            if (!this.mEnabled) {
                return false;
            }
            return super.setState(stateSet);
        }

        public void draw(Canvas canvas) {
            Canvas canvas2 = canvas;
            Canvas canvas3 = canvas2;
            if (this.mEnabled) {
                super.draw(canvas2);
            }
        }

        public void setHotspot(float f, float f2) {
            float x = f;
            float y = f2;
            float f3 = x;
            float f4 = y;
            if (this.mEnabled) {
                super.setHotspot(x, y);
            }
        }

        public void setHotspotBounds(int i, int i2, int i3, int i4) {
            int left = i;
            int top = i2;
            int right = i3;
            int bottom = i4;
            int i5 = left;
            int i6 = top;
            int i7 = right;
            int i8 = bottom;
            if (this.mEnabled) {
                super.setHotspotBounds(left, top, right, bottom);
            }
        }

        public boolean setVisible(boolean z, boolean z2) {
            boolean visible = z;
            boolean restart = z2;
            if (!this.mEnabled) {
                return false;
            }
            return super.setVisible(visible, restart);
        }
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 0;
        STATE_SET_NOTHING = iArr;
    }

    public ListViewCompat(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mSelectorRect = new Rect();
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        try {
            this.mIsChildViewEnabled = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            this.mIsChildViewEnabled.setAccessible(true);
        } catch (NoSuchFieldException e) {
            NoSuchFieldException noSuchFieldException = e;
            e.printStackTrace();
        }
    }

    public ListViewCompat(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public ListViewCompat(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void setSelector(Drawable drawable) {
        Drawable sel = drawable;
        Drawable drawable2 = sel;
        this.mSelector = sel == null ? null : new GateKeeperDrawable(sel);
        super.setSelector(this.mSelector);
        Rect padding = new Rect();
        if (sel != null) {
            boolean padding2 = sel.getPadding(padding);
        }
        this.mSelectionLeftPadding = padding.left;
        this.mSelectionTopPadding = padding.top;
        this.mSelectionRightPadding = padding.right;
        this.mSelectionBottomPadding = padding.bottom;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        setSelectorEnabled(true);
        updateSelectorStateCompat();
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        drawSelectorCompat(canvas2);
        super.dispatchDraw(canvas2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        switch (ev.getAction()) {
            case 0:
                this.mMotionPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
                break;
        }
        return super.onTouchEvent(ev);
    }

    /* access modifiers changed from: protected */
    public void updateSelectorStateCompat() {
        Drawable selector = getSelector();
        Drawable selector2 = selector;
        if (selector != null && shouldShowSelectorCompat()) {
            boolean state = selector2.setState(getDrawableState());
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldShowSelectorCompat() {
        return touchModeDrawsInPressedStateCompat() && isPressed();
    }

    /* access modifiers changed from: protected */
    public boolean touchModeDrawsInPressedStateCompat() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void drawSelectorCompat(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        if (!this.mSelectorRect.isEmpty()) {
            Drawable selector = getSelector();
            Drawable selector2 = selector;
            if (selector != null) {
                selector2.setBounds(this.mSelectorRect);
                selector2.draw(canvas2);
            }
        }
    }

    public int lookForSelectablePosition(int i, boolean z) {
        int position;
        int position2 = i;
        int i2 = position2;
        boolean lookDown = z;
        ListAdapter adapter = getAdapter();
        ListAdapter adapter2 = adapter;
        if (adapter == null || isInTouchMode()) {
            return -1;
        }
        int count = adapter2.getCount();
        if (!getAdapter().areAllItemsEnabled()) {
            if (!lookDown) {
                position = Math.min(position2, count - 1);
                while (position >= 0 && !adapter2.isEnabled(position)) {
                    position--;
                }
            } else {
                int position3 = Math.max(0, position2);
                while (position < count && !adapter2.isEnabled(position)) {
                    position3 = position + 1;
                }
            }
            if (position >= 0 && position < count) {
                return position;
            }
            return -1;
        } else if (position2 >= 0 && position2 < count) {
            return position2;
        } else {
            return -1;
        }
    }

    /* access modifiers changed from: protected */
    public void positionSelectorLikeTouchCompat(int i, View view, float f, float f2) {
        int position = i;
        View sel = view;
        float x = f;
        float y = f2;
        int i2 = position;
        View view2 = sel;
        float f3 = x;
        float f4 = y;
        positionSelectorLikeFocusCompat(position, sel);
        Drawable selector = getSelector();
        Drawable selector2 = selector;
        if (selector != null && position != -1) {
            DrawableCompat.setHotspot(selector2, x, y);
        }
    }

    /* access modifiers changed from: protected */
    public void positionSelectorLikeFocusCompat(int i, View view) {
        int position = i;
        View sel = view;
        int i2 = position;
        View view2 = sel;
        Drawable selector = getSelector();
        Drawable selector2 = selector;
        boolean manageState = (selector == null || position == -1) ? false : true;
        if (manageState) {
            boolean visible = selector2.setVisible(false, false);
        }
        positionSelectorCompat(position, sel);
        if (manageState) {
            Rect rect = this.mSelectorRect;
            Rect bounds = rect;
            float x = rect.exactCenterX();
            float exactCenterY = bounds.exactCenterY();
            float f = exactCenterY;
            boolean visible2 = selector2.setVisible(getVisibility() == 0, false);
            DrawableCompat.setHotspot(selector2, x, exactCenterY);
        }
    }

    /* access modifiers changed from: protected */
    public void positionSelectorCompat(int i, View view) {
        int position = i;
        View sel = view;
        int i2 = position;
        View view2 = sel;
        Rect rect = this.mSelectorRect;
        Rect selectorRect = rect;
        rect.set(sel.getLeft(), sel.getTop(), sel.getRight(), sel.getBottom());
        selectorRect.left -= this.mSelectionLeftPadding;
        selectorRect.top -= this.mSelectionTopPadding;
        selectorRect.right += this.mSelectionRightPadding;
        selectorRect.bottom += this.mSelectionBottomPadding;
        try {
            boolean isChildViewEnabled = this.mIsChildViewEnabled.getBoolean(this);
            if (sel.isEnabled() != isChildViewEnabled) {
                this.mIsChildViewEnabled.set(this, Boolean.valueOf(!isChildViewEnabled));
                if (position != -1) {
                    refreshDrawableState();
                }
            }
        } catch (IllegalAccessException e) {
            IllegalAccessException illegalAccessException = e;
            e.printStackTrace();
        }
    }

    public int measureHeightOfChildrenCompat(int i, int i2, int i3, int i4, int i5) {
        int i6;
        int heightMeasureSpec;
        int widthMeasureSpec = i;
        int maxHeight = i4;
        int disallowPartialChildPosition = i5;
        int i7 = widthMeasureSpec;
        int i8 = i2;
        int i9 = i3;
        int i10 = maxHeight;
        int i11 = disallowPartialChildPosition;
        int paddingTop = getListPaddingTop();
        int paddingBottom = getListPaddingBottom();
        int listPaddingLeft = getListPaddingLeft();
        int listPaddingRight = getListPaddingRight();
        int reportedDividerHeight = getDividerHeight();
        Drawable divider = getDivider();
        ListAdapter adapter = getAdapter();
        ListAdapter adapter2 = adapter;
        if (adapter == null) {
            return paddingTop + paddingBottom;
        }
        int returnedHeight = paddingTop + paddingBottom;
        if (reportedDividerHeight > 0 && divider != null) {
            i6 = reportedDividerHeight;
        } else {
            i6 = 0;
        }
        int dividerHeight = i6;
        int prevHeightWithoutPartialChild = 0;
        View child = null;
        int viewType = 0;
        int count = adapter2.getCount();
        int i12 = 0;
        while (i12 < count) {
            int itemViewType = adapter2.getItemViewType(i12);
            int newType = itemViewType;
            if (itemViewType != viewType) {
                child = null;
                viewType = newType;
            }
            View view = adapter2.getView(i12, child, this);
            child = view;
            LayoutParams layoutParams = view.getLayoutParams();
            LayoutParams childLp = layoutParams;
            if (layoutParams == null) {
                childLp = generateDefaultLayoutParams();
                child.setLayoutParams(childLp);
            }
            if (childLp.height <= 0) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
            } else {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(childLp.height, 1073741824);
            }
            child.measure(widthMeasureSpec, heightMeasureSpec);
            child.forceLayout();
            if (i12 > 0) {
                returnedHeight += dividerHeight;
            }
            int measuredHeight = returnedHeight + child.getMeasuredHeight();
            returnedHeight = measuredHeight;
            if (measuredHeight < maxHeight) {
                if (disallowPartialChildPosition >= 0 && i12 >= disallowPartialChildPosition) {
                    prevHeightWithoutPartialChild = returnedHeight;
                }
                i12++;
            } else {
                return (disallowPartialChildPosition >= 0 && i12 > disallowPartialChildPosition && prevHeightWithoutPartialChild > 0 && returnedHeight != maxHeight) ? prevHeightWithoutPartialChild : maxHeight;
            }
        }
        return returnedHeight;
    }

    /* access modifiers changed from: protected */
    public void setSelectorEnabled(boolean z) {
        boolean enabled = z;
        if (this.mSelector != null) {
            this.mSelector.setEnabled(enabled);
        }
    }
}
