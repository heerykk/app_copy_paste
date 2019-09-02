package android.support.design.widget;

import android.content.Context;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.VelocityTrackerCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = -1;
    private Runnable mFlingRunnable;
    private boolean mIsBeingDragged;
    private int mLastMotionY;
    ScrollerCompat mScroller;
    private int mTouchSlop = -1;
    private VelocityTracker mVelocityTracker;

    private class FlingRunnable implements Runnable {
        private final V mLayout;
        private final CoordinatorLayout mParent;
        final /* synthetic */ HeaderBehavior this$0;

        FlingRunnable(HeaderBehavior headerBehavior, CoordinatorLayout coordinatorLayout, V v) {
            CoordinatorLayout parent = coordinatorLayout;
            V layout = v;
            CoordinatorLayout coordinatorLayout2 = parent;
            V v2 = layout;
            this.this$0 = headerBehavior;
            this.mParent = parent;
            this.mLayout = layout;
        }

        public void run() {
            if (this.mLayout != null && this.this$0.mScroller != null) {
                if (!this.this$0.mScroller.computeScrollOffset()) {
                    this.this$0.onFlingFinished(this.mParent, this.mLayout);
                    return;
                }
                HeaderBehavior headerBehavior = this.this$0;
                CoordinatorLayout coordinatorLayout = this.mParent;
                int headerTopBottomOffset = headerBehavior.setHeaderTopBottomOffset(coordinatorLayout, this.mLayout, this.this$0.mScroller.getCurrY());
                ViewCompat.postOnAnimation(this.mLayout, this);
            }
        }
    }

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        CoordinatorLayout parent = coordinatorLayout;
        V child = v;
        MotionEvent ev = motionEvent;
        CoordinatorLayout coordinatorLayout2 = parent;
        V v2 = child;
        MotionEvent motionEvent2 = ev;
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        int action = ev.getAction();
        int i = action;
        if (action == 2 && this.mIsBeingDragged) {
            return true;
        }
        switch (MotionEventCompat.getActionMasked(ev)) {
            case 0:
                this.mIsBeingDragged = false;
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                if (canDragView(child) && parent.isPointInChildBounds(child, x, y)) {
                    this.mLastMotionY = y;
                    this.mActivePointerId = ev.getPointerId(0);
                    ensureVelocityTracker();
                    break;
                }
            case 1:
            case 3:
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                    break;
                }
                break;
            case 2:
                int i2 = this.mActivePointerId;
                int activePointerId = i2;
                if (i2 != -1) {
                    int findPointerIndex = ev.findPointerIndex(activePointerId);
                    int pointerIndex = findPointerIndex;
                    if (findPointerIndex != -1) {
                        int y2 = (int) ev.getY(pointerIndex);
                        int y3 = y2;
                        int abs = Math.abs(y2 - this.mLastMotionY);
                        int i3 = abs;
                        if (abs > this.mTouchSlop) {
                            this.mIsBeingDragged = true;
                            this.mLastMotionY = y3;
                            break;
                        }
                    }
                }
                break;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(ev);
        }
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        CoordinatorLayout parent = coordinatorLayout;
        V child = v;
        MotionEvent ev = motionEvent;
        CoordinatorLayout coordinatorLayout2 = parent;
        V v2 = child;
        MotionEvent motionEvent2 = ev;
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        switch (MotionEventCompat.getActionMasked(ev)) {
            case 0:
                int y = (int) ev.getY();
                int y2 = y;
                if (parent.isPointInChildBounds(child, (int) ev.getX(), y) && canDragView(child)) {
                    this.mLastMotionY = y2;
                    this.mActivePointerId = ev.getPointerId(0);
                    ensureVelocityTracker();
                    break;
                } else {
                    return false;
                }
            case 1:
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.addMovement(ev);
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    float yVelocity = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
                    float f = yVelocity;
                    boolean fling = fling(parent, child, -getScrollRangeForDragFling(child), 0, yVelocity);
                    break;
                }
                break;
            case 2:
                int findPointerIndex = ev.findPointerIndex(this.mActivePointerId);
                int activePointerIndex = findPointerIndex;
                if (findPointerIndex != -1) {
                    int y3 = (int) ev.getY(activePointerIndex);
                    int dy = this.mLastMotionY - y3;
                    if (!this.mIsBeingDragged && Math.abs(dy) > this.mTouchSlop) {
                        this.mIsBeingDragged = true;
                        dy = dy <= 0 ? dy + this.mTouchSlop : dy - this.mTouchSlop;
                    }
                    if (this.mIsBeingDragged) {
                        this.mLastMotionY = y3;
                        int scroll = scroll(parent, child, dy, getMaxDragOffset(child), 0);
                        break;
                    }
                } else {
                    return false;
                }
                break;
            case 3:
                break;
        }
        this.mIsBeingDragged = false;
        this.mActivePointerId = -1;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(ev);
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int i) {
        CoordinatorLayout parent = coordinatorLayout;
        V header = v;
        int newOffset = i;
        CoordinatorLayout coordinatorLayout2 = parent;
        V v2 = header;
        int i2 = newOffset;
        return setHeaderTopBottomOffset(parent, header, newOffset, Integer.MIN_VALUE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    /* access modifiers changed from: 0000 */
    public int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
        int newOffset = i;
        int minOffset = i2;
        int maxOffset = i3;
        CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
        V v2 = v;
        int i4 = newOffset;
        int i5 = minOffset;
        int i6 = maxOffset;
        int curOffset = getTopAndBottomOffset();
        int consumed = 0;
        if (minOffset != 0 && curOffset >= minOffset && curOffset <= maxOffset) {
            int newOffset2 = MathUtils.constrain(newOffset, minOffset, maxOffset);
            if (curOffset != newOffset2) {
                boolean topAndBottomOffset = setTopAndBottomOffset(newOffset2);
                consumed = curOffset - newOffset2;
            }
        }
        return consumed;
    }

    /* access modifiers changed from: 0000 */
    public int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset();
    }

    /* access modifiers changed from: 0000 */
    public final int scroll(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
        CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
        V header = v;
        int dy = i;
        int minOffset = i2;
        int maxOffset = i3;
        CoordinatorLayout coordinatorLayout3 = coordinatorLayout2;
        V v2 = header;
        int i4 = dy;
        int i5 = minOffset;
        int i6 = maxOffset;
        return setHeaderTopBottomOffset(coordinatorLayout2, header, getTopBottomOffsetForScrollingSibling() - dy, minOffset, maxOffset);
    }

    /* access modifiers changed from: 0000 */
    public final boolean fling(CoordinatorLayout coordinatorLayout, V v, int i, int i2, float f) {
        CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
        V layout = v;
        int minOffset = i;
        int maxOffset = i2;
        float velocityY = f;
        CoordinatorLayout coordinatorLayout3 = coordinatorLayout2;
        V v2 = layout;
        int i3 = minOffset;
        int i4 = maxOffset;
        float f2 = velocityY;
        if (this.mFlingRunnable != null) {
            boolean removeCallbacks = layout.removeCallbacks(this.mFlingRunnable);
            this.mFlingRunnable = null;
        }
        if (this.mScroller == null) {
            this.mScroller = ScrollerCompat.create(layout.getContext());
        }
        this.mScroller.fling(0, getTopAndBottomOffset(), 0, Math.round(velocityY), 0, 0, minOffset, maxOffset);
        if (!this.mScroller.computeScrollOffset()) {
            onFlingFinished(coordinatorLayout2, layout);
            return false;
        }
        FlingRunnable flingRunnable = new FlingRunnable(this, coordinatorLayout2, layout);
        this.mFlingRunnable = flingRunnable;
        ViewCompat.postOnAnimation(layout, this.mFlingRunnable);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void onFlingFinished(CoordinatorLayout coordinatorLayout, V v) {
        CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
        V v2 = v;
    }

    /* access modifiers changed from: 0000 */
    public boolean canDragView(V v) {
        V v2 = v;
        return false;
    }

    /* access modifiers changed from: 0000 */
    public int getMaxDragOffset(V v) {
        V view = v;
        V v2 = view;
        return -view.getHeight();
    }

    /* access modifiers changed from: 0000 */
    public int getScrollRangeForDragFling(V v) {
        V view = v;
        V v2 = view;
        return view.getHeight();
    }

    private void ensureVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }
}
