package android.support.p003v7.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.ViewPropertyAnimatorCompat;
import android.support.p000v4.widget.AutoScrollHelper;
import android.support.p000v4.widget.ListViewAutoScrollHelper;
import android.support.p003v7.appcompat.C0268R;
import android.view.MotionEvent;
import android.view.View;

/* renamed from: android.support.v7.widget.DropDownListView */
class DropDownListView extends ListViewCompat {
    private ViewPropertyAnimatorCompat mClickAnimation;
    private boolean mDrawsInPressedState;
    private boolean mHijackFocus;
    private boolean mListSelectionHidden;
    private ListViewAutoScrollHelper mScrollHelper;

    public DropDownListView(Context context, boolean z) {
        Context context2 = context;
        Context context3 = context2;
        boolean hijackFocus = z;
        super(context2, null, C0268R.attr.dropDownListViewStyle);
        this.mHijackFocus = hijackFocus;
        setCacheColorHint(0);
    }

    public boolean onForwardedEvent(MotionEvent motionEvent, int i) {
        MotionEvent event = motionEvent;
        int activePointerId = i;
        MotionEvent motionEvent2 = event;
        int i2 = activePointerId;
        boolean handledEvent = true;
        boolean clearPressedItem = false;
        int actionMasked = MotionEventCompat.getActionMasked(event);
        int actionMasked2 = actionMasked;
        switch (actionMasked) {
            case 1:
                handledEvent = false;
                break;
            case 2:
                break;
            case 3:
                handledEvent = false;
                break;
        }
        int findPointerIndex = event.findPointerIndex(activePointerId);
        int activeIndex = findPointerIndex;
        if (findPointerIndex >= 0) {
            int x = (int) event.getX(activeIndex);
            int y = (int) event.getY(activeIndex);
            int y2 = y;
            int pointToPosition = pointToPosition(x, y);
            int position = pointToPosition;
            if (pointToPosition != -1) {
                View child = getChildAt(position - getFirstVisiblePosition());
                setPressedItem(child, position, (float) x, (float) y2);
                handledEvent = true;
                if (actionMasked2 == 1) {
                    clickPressedItem(child, position);
                }
            } else {
                clearPressedItem = true;
            }
        } else {
            handledEvent = false;
        }
        if (!handledEvent || clearPressedItem) {
            clearPressedItem();
        }
        if (handledEvent) {
            if (this.mScrollHelper == null) {
                this.mScrollHelper = new ListViewAutoScrollHelper(this);
            }
            AutoScrollHelper enabled = this.mScrollHelper.setEnabled(true);
            boolean onTouch = this.mScrollHelper.onTouch(this, event);
        } else if (this.mScrollHelper != null) {
            AutoScrollHelper enabled2 = this.mScrollHelper.setEnabled(false);
        }
        return handledEvent;
    }

    private void clickPressedItem(View view, int i) {
        View child = view;
        int position = i;
        View view2 = child;
        int i2 = position;
        long itemIdAtPosition = getItemIdAtPosition(position);
        long j = itemIdAtPosition;
        boolean performItemClick = performItemClick(child, position, itemIdAtPosition);
    }

    /* access modifiers changed from: 0000 */
    public void setListSelectionHidden(boolean z) {
        this.mListSelectionHidden = z;
    }

    private void clearPressedItem() {
        this.mDrawsInPressedState = false;
        setPressed(false);
        drawableStateChanged();
        View childAt = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
        View motionView = childAt;
        if (childAt != null) {
            motionView.setPressed(false);
        }
        if (this.mClickAnimation != null) {
            this.mClickAnimation.cancel();
            this.mClickAnimation = null;
        }
    }

    private void setPressedItem(View view, int i, float f, float f2) {
        View child = view;
        int position = i;
        float x = f;
        float y = f2;
        View view2 = child;
        int i2 = position;
        float f3 = x;
        float f4 = y;
        this.mDrawsInPressedState = true;
        if (VERSION.SDK_INT >= 21) {
            drawableHotspotChanged(x, y);
        }
        if (!isPressed()) {
            setPressed(true);
        }
        layoutChildren();
        if (this.mMotionPosition != -1) {
            View childAt = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
            View motionView = childAt;
            if (!(childAt == null || motionView == child || !motionView.isPressed())) {
                motionView.setPressed(false);
            }
        }
        this.mMotionPosition = position;
        float childX = x - ((float) child.getLeft());
        float top = y - ((float) child.getTop());
        float f5 = top;
        if (VERSION.SDK_INT >= 21) {
            child.drawableHotspotChanged(childX, top);
        }
        if (!child.isPressed()) {
            child.setPressed(true);
        }
        positionSelectorLikeTouchCompat(position, child, x, y);
        setSelectorEnabled(false);
        refreshDrawableState();
    }

    /* access modifiers changed from: protected */
    public boolean touchModeDrawsInPressedStateCompat() {
        return this.mDrawsInPressedState || super.touchModeDrawsInPressedStateCompat();
    }

    public boolean isInTouchMode() {
        return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode();
    }

    public boolean hasWindowFocus() {
        return this.mHijackFocus || super.hasWindowFocus();
    }

    public boolean isFocused() {
        return this.mHijackFocus || super.isFocused();
    }

    public boolean hasFocus() {
        return this.mHijackFocus || super.hasFocus();
    }
}
