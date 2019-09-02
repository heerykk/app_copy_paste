package android.support.p000v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.media.TransportMediator;
import android.support.p000v4.view.AccessibilityDelegateCompat;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.NestedScrollingChild;
import android.support.p000v4.view.NestedScrollingChildHelper;
import android.support.p000v4.view.NestedScrollingParent;
import android.support.p000v4.view.NestedScrollingParentHelper;
import android.support.p000v4.view.ScrollingView;
import android.support.p000v4.view.VelocityTrackerCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.accessibility.AccessibilityEventCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityRecordCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import java.util.ArrayList;

/* renamed from: android.support.v4.widget.NestedScrollView */
public class NestedScrollView extends FrameLayout implements NestedScrollingParent, NestedScrollingChild, ScrollingView {
    private static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
    static final int ANIMATED_SCROLL_GAP = 250;
    private static final int INVALID_POINTER = -1;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final int[] SCROLLVIEW_STYLEABLE;
    private static final String TAG = "NestedScrollView";
    private int mActivePointerId;
    private final NestedScrollingChildHelper mChildHelper;
    private View mChildToScrollTo;
    private EdgeEffectCompat mEdgeGlowBottom;
    private EdgeEffectCompat mEdgeGlowTop;
    private boolean mFillViewport;
    private boolean mIsBeingDragged;
    private boolean mIsLaidOut;
    private boolean mIsLayoutDirty;
    private int mLastMotionY;
    private long mLastScroll;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mNestedYOffset;
    private OnScrollChangeListener mOnScrollChangeListener;
    private final NestedScrollingParentHelper mParentHelper;
    private SavedState mSavedState;
    private final int[] mScrollConsumed;
    private final int[] mScrollOffset;
    private ScrollerCompat mScroller;
    private boolean mSmoothScrollingEnabled;
    private final Rect mTempRect;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;

    /* renamed from: android.support.v4.widget.NestedScrollView$AccessibilityDelegate */
    static class AccessibilityDelegate extends AccessibilityDelegateCompat {
        AccessibilityDelegate() {
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            View host = view;
            int action = i;
            Bundle arguments = bundle;
            View view2 = host;
            int i2 = action;
            Bundle bundle2 = arguments;
            if (super.performAccessibilityAction(host, action, arguments)) {
                return true;
            }
            NestedScrollView nestedScrollView = (NestedScrollView) host;
            NestedScrollView nsvHost = nestedScrollView;
            if (!nestedScrollView.isEnabled()) {
                return false;
            }
            switch (action) {
                case 4096:
                    int min = Math.min(nsvHost.getScrollY() + ((nsvHost.getHeight() - nsvHost.getPaddingBottom()) - nsvHost.getPaddingTop()), nsvHost.getScrollRange());
                    int targetScrollY = min;
                    if (min == nsvHost.getScrollY()) {
                        return false;
                    }
                    nsvHost.smoothScrollTo(0, targetScrollY);
                    return true;
                case 8192:
                    int max = Math.max(nsvHost.getScrollY() - ((nsvHost.getHeight() - nsvHost.getPaddingBottom()) - nsvHost.getPaddingTop()), 0);
                    int targetScrollY2 = max;
                    if (max == nsvHost.getScrollY()) {
                        return false;
                    }
                    nsvHost.smoothScrollTo(0, targetScrollY2);
                    return true;
                default:
                    return false;
            }
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View host = view;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            View view2 = host;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            super.onInitializeAccessibilityNodeInfo(host, info);
            NestedScrollView nsvHost = (NestedScrollView) host;
            info.setClassName(ScrollView.class.getName());
            if (nsvHost.isEnabled()) {
                int scrollRange = nsvHost.getScrollRange();
                int scrollRange2 = scrollRange;
                if (scrollRange > 0) {
                    info.setScrollable(true);
                    if (nsvHost.getScrollY() > 0) {
                        info.addAction(8192);
                    }
                    if (nsvHost.getScrollY() < scrollRange2) {
                        info.addAction(4096);
                    }
                }
            }
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            View host = view;
            AccessibilityEvent event = accessibilityEvent;
            View view2 = host;
            AccessibilityEvent accessibilityEvent2 = event;
            super.onInitializeAccessibilityEvent(host, event);
            NestedScrollView nsvHost = (NestedScrollView) host;
            event.setClassName(ScrollView.class.getName());
            AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(event);
            record.setScrollable(nsvHost.getScrollRange() > 0);
            record.setScrollX(nsvHost.getScrollX());
            record.setScrollY(nsvHost.getScrollY());
            record.setMaxScrollX(nsvHost.getScrollX());
            record.setMaxScrollY(nsvHost.getScrollRange());
        }
    }

    /* renamed from: android.support.v4.widget.NestedScrollView$OnScrollChangeListener */
    public interface OnScrollChangeListener {
        void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4);
    }

    /* renamed from: android.support.v4.widget.NestedScrollView$SavedState */
    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                Parcel in = parcel;
                Parcel parcel2 = in;
                return new SavedState(in);
            }

            public SavedState[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new SavedState[size];
            }
        };
        public int scrollPosition;

        SavedState(Parcelable parcelable) {
            Parcelable superState = parcelable;
            Parcelable parcelable2 = superState;
            super(superState);
        }

        SavedState(Parcel parcel) {
            Parcel source = parcel;
            Parcel parcel2 = source;
            super(source);
            this.scrollPosition = source.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            int flags = i;
            Parcel parcel2 = dest;
            int i2 = flags;
            super.writeToParcel(dest, flags);
            dest.writeInt(this.scrollPosition);
        }

        public String toString() {
            return "HorizontalScrollView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " scrollPosition=" + this.scrollPosition + "}";
        }
    }

    public NestedScrollView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mTempRect = new Rect();
        this.mIsLayoutDirty = true;
        this.mIsLaidOut = false;
        this.mChildToScrollTo = null;
        this.mIsBeingDragged = false;
        this.mSmoothScrollingEnabled = true;
        this.mActivePointerId = -1;
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        initScrollView();
        TypedArray a = context2.obtainStyledAttributes(attrs, SCROLLVIEW_STYLEABLE, defStyleAttr, 0);
        setFillViewport(a.getBoolean(0, false));
        a.recycle();
        this.mParentHelper = new NestedScrollingParentHelper(this);
        this.mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        ViewCompat.setAccessibilityDelegate(this, ACCESSIBILITY_DELEGATE);
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 16843130;
        SCROLLVIEW_STYLEABLE = iArr;
    }

    public NestedScrollView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void setNestedScrollingEnabled(boolean z) {
        this.mChildHelper.setNestedScrollingEnabled(z);
    }

    public boolean isNestedScrollingEnabled() {
        return this.mChildHelper.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int i) {
        int axes = i;
        int i2 = axes;
        return this.mChildHelper.startNestedScroll(axes);
    }

    public void stopNestedScroll() {
        this.mChildHelper.stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() {
        return this.mChildHelper.hasNestedScrollingParent();
    }

    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        int dxConsumed = i;
        int dyConsumed = i2;
        int dxUnconsumed = i3;
        int dyUnconsumed = i4;
        int[] offsetInWindow = iArr;
        int i5 = dxConsumed;
        int i6 = dyConsumed;
        int i7 = dxUnconsumed;
        int i8 = dyUnconsumed;
        int[] iArr2 = offsetInWindow;
        return this.mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        int dx = i;
        int dy = i2;
        int[] consumed = iArr;
        int[] offsetInWindow = iArr2;
        int i3 = dx;
        int i4 = dy;
        int[] iArr3 = consumed;
        int[] iArr4 = offsetInWindow;
        return this.mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        float velocityX = f;
        float velocityY = f2;
        float f3 = velocityX;
        float f4 = velocityY;
        return this.mChildHelper.dispatchNestedFling(velocityX, velocityY, z);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        float velocityX = f;
        float velocityY = f2;
        float f3 = velocityX;
        float f4 = velocityY;
        return this.mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    public boolean onStartNestedScroll(View view, View view2, int i) {
        int nestedScrollAxes = i;
        View view3 = view;
        View view4 = view2;
        int i2 = nestedScrollAxes;
        return (nestedScrollAxes & 2) != 0;
    }

    public void onNestedScrollAccepted(View view, View view2, int i) {
        View child = view;
        View target = view2;
        int nestedScrollAxes = i;
        View view3 = child;
        View view4 = target;
        int i2 = nestedScrollAxes;
        this.mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
        boolean startNestedScroll = startNestedScroll(2);
    }

    public void onStopNestedScroll(View view) {
        View target = view;
        View view2 = target;
        this.mParentHelper.onStopNestedScroll(target);
        stopNestedScroll();
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        int dyUnconsumed = i4;
        View view2 = view;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        int i8 = dyUnconsumed;
        int oldScrollY = getScrollY();
        scrollBy(0, dyUnconsumed);
        int myConsumed = getScrollY() - oldScrollY;
        int i9 = dyUnconsumed - myConsumed;
        int i10 = i9;
        boolean dispatchNestedScroll = dispatchNestedScroll(0, myConsumed, 0, i9, null);
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        int dx = i;
        int dy = i2;
        int[] consumed = iArr;
        View view2 = view;
        int i3 = dx;
        int i4 = dy;
        int[] iArr2 = consumed;
        boolean dispatchNestedPreScroll = dispatchNestedPreScroll(dx, dy, consumed, null);
    }

    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        float velocityY = f2;
        View view2 = view;
        float f3 = f;
        float f4 = velocityY;
        if (z) {
            return false;
        }
        flingWithNestedDispatch((int) velocityY);
        return true;
    }

    public boolean onNestedPreFling(View view, float f, float f2) {
        float velocityX = f;
        float velocityY = f2;
        View view2 = view;
        float f3 = velocityX;
        float f4 = velocityY;
        return dispatchNestedPreFling(velocityX, velocityY);
    }

    public int getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes();
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    /* access modifiers changed from: protected */
    public float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int length = getVerticalFadingEdgeLength();
        int scrollY = getScrollY();
        int scrollY2 = scrollY;
        if (scrollY >= length) {
            return 1.0f;
        }
        return ((float) scrollY2) / ((float) length);
    }

    /* access modifiers changed from: protected */
    public float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int length = getVerticalFadingEdgeLength();
        int bottom = (getChildAt(0).getBottom() - getScrollY()) - (getHeight() - getPaddingBottom());
        int span = bottom;
        if (bottom >= length) {
            return 1.0f;
        }
        return ((float) span) / ((float) length);
    }

    public int getMaxScrollAmount() {
        return (int) (MAX_SCROLL_FACTOR * ((float) getHeight()));
    }

    private void initScrollView() {
        this.mScroller = ScrollerCompat.create(getContext(), null);
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    public void addView(View view) {
        View child = view;
        View view2 = child;
        if (getChildCount() <= 0) {
            super.addView(child);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public void addView(View view, int i) {
        View child = view;
        int index = i;
        View view2 = child;
        int i2 = index;
        if (getChildCount() <= 0) {
            super.addView(child, index);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public void addView(View view, LayoutParams layoutParams) {
        View child = view;
        LayoutParams params = layoutParams;
        View view2 = child;
        LayoutParams layoutParams2 = params;
        if (getChildCount() <= 0) {
            super.addView(child, params);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        View child = view;
        int index = i;
        LayoutParams params = layoutParams;
        View view2 = child;
        int i2 = index;
        LayoutParams layoutParams2 = params;
        if (getChildCount() <= 0) {
            super.addView(child, index, params);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        OnScrollChangeListener l = onScrollChangeListener;
        OnScrollChangeListener onScrollChangeListener2 = l;
        this.mOnScrollChangeListener = l;
    }

    private boolean canScroll() {
        View childAt = getChildAt(0);
        View child = childAt;
        if (childAt == null) {
            return false;
        }
        return getHeight() < (child.getHeight() + getPaddingTop()) + getPaddingBottom();
    }

    public boolean isFillViewport() {
        return this.mFillViewport;
    }

    public void setFillViewport(boolean z) {
        boolean fillViewport = z;
        if (fillViewport != this.mFillViewport) {
            this.mFillViewport = fillViewport;
            requestLayout();
        }
    }

    public boolean isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled;
    }

    public void setSmoothScrollingEnabled(boolean z) {
        this.mSmoothScrollingEnabled = z;
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        int l = i;
        int t = i2;
        int oldl = i3;
        int oldt = i4;
        int i5 = l;
        int i6 = t;
        int i7 = oldl;
        int i8 = oldt;
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.mOnScrollChangeListener != null) {
            this.mOnScrollChangeListener.onScrollChange(this, l, t, oldl, oldt);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mFillViewport) {
            int mode = MeasureSpec.getMode(heightMeasureSpec);
            int i5 = mode;
            if (mode != 0 && getChildCount() > 0) {
                View child = getChildAt(0);
                int height = getMeasuredHeight();
                if (child.getMeasuredHeight() < height) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) child.getLayoutParams();
                    FrameLayout.LayoutParams layoutParams2 = layoutParams;
                    int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width);
                    int paddingTop = height - getPaddingTop();
                    int height2 = paddingTop;
                    int paddingBottom = paddingTop - getPaddingBottom();
                    int height3 = paddingBottom;
                    int makeMeasureSpec = MeasureSpec.makeMeasureSpec(paddingBottom, 1073741824);
                    int i6 = makeMeasureSpec;
                    child.measure(childWidthMeasureSpec, makeMeasureSpec);
                }
            }
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        return super.dispatchKeyEvent(event) || executeKeyEvent(event);
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        int i;
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        this.mTempRect.setEmpty();
        if (canScroll()) {
            boolean handled = false;
            if (event.getAction() == 0) {
                switch (event.getKeyCode()) {
                    case 19:
                        if (!event.isAltPressed()) {
                            handled = arrowScroll(33);
                            break;
                        } else {
                            handled = fullScroll(33);
                            break;
                        }
                    case 20:
                        if (!event.isAltPressed()) {
                            handled = arrowScroll(TransportMediator.KEYCODE_MEDIA_RECORD);
                            break;
                        } else {
                            handled = fullScroll(TransportMediator.KEYCODE_MEDIA_RECORD);
                            break;
                        }
                    case 62:
                        if (!event.isShiftPressed()) {
                            i = TransportMediator.KEYCODE_MEDIA_RECORD;
                        } else {
                            i = 33;
                        }
                        boolean pageScroll = pageScroll(i);
                        break;
                }
            }
            return handled;
        } else if (!isFocused() || event.getKeyCode() == 4) {
            return false;
        } else {
            View findFocus = findFocus();
            View currentFocused = findFocus;
            if (findFocus == this) {
                currentFocused = null;
            }
            View findNextFocus = FocusFinder.getInstance().findNextFocus(this, currentFocused, TransportMediator.KEYCODE_MEDIA_RECORD);
            View nextFocused = findNextFocus;
            return (findNextFocus == null || nextFocused == this || !nextFocused.requestFocus(TransportMediator.KEYCODE_MEDIA_RECORD)) ? false : true;
        }
    }

    private boolean inChild(int i, int i2) {
        int x = i;
        int y = i2;
        int i3 = x;
        int i4 = y;
        if (getChildCount() <= 0) {
            return false;
        }
        int scrollY = getScrollY();
        View childAt = getChildAt(0);
        View view = childAt;
        return y >= childAt.getTop() - scrollY && y < childAt.getBottom() - scrollY && x >= childAt.getLeft() && x < childAt.getRight();
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        } else {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        boolean disallowIntercept = z;
        if (disallowIntercept) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int action = ev.getAction();
        int action2 = action;
        if (action == 2 && this.mIsBeingDragged) {
            return true;
        }
        switch (action2 & 255) {
            case 0:
                int y = (int) ev.getY();
                int y2 = y;
                if (!inChild((int) ev.getX(), y)) {
                    this.mIsBeingDragged = false;
                    recycleVelocityTracker();
                    break;
                } else {
                    this.mLastMotionY = y2;
                    this.mActivePointerId = ev.getPointerId(0);
                    initOrResetVelocityTracker();
                    this.mVelocityTracker.addMovement(ev);
                    boolean computeScrollOffset = this.mScroller.computeScrollOffset();
                    this.mIsBeingDragged = !this.mScroller.isFinished();
                    boolean startNestedScroll = startNestedScroll(2);
                    break;
                }
            case 1:
            case 3:
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                recycleVelocityTracker();
                if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                stopNestedScroll();
                break;
            case 2:
                int i = this.mActivePointerId;
                int activePointerId = i;
                if (i != -1) {
                    int findPointerIndex = ev.findPointerIndex(activePointerId);
                    int pointerIndex = findPointerIndex;
                    if (findPointerIndex == -1) {
                        int e = Log.e(TAG, "Invalid pointerId=" + activePointerId + " in onInterceptTouchEvent");
                        break;
                    } else {
                        int y3 = (int) ev.getY(pointerIndex);
                        int y4 = y3;
                        int abs = Math.abs(y3 - this.mLastMotionY);
                        int i2 = abs;
                        if (abs > this.mTouchSlop && (getNestedScrollAxes() & 2) == 0) {
                            this.mIsBeingDragged = true;
                            this.mLastMotionY = y4;
                            initVelocityTrackerIfNotExists();
                            this.mVelocityTracker.addMovement(ev);
                            this.mNestedYOffset = 0;
                            ViewParent parent = getParent();
                            ViewParent parent2 = parent;
                            if (parent != null) {
                                parent2.requestDisallowInterceptTouchEvent(true);
                                break;
                            }
                        }
                    }
                }
                break;
            case 6:
                onSecondaryPointerUp(ev);
                break;
        }
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        initVelocityTrackerIfNotExists();
        MotionEvent vtev = MotionEvent.obtain(ev);
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        int actionMasked2 = actionMasked;
        if (actionMasked == 0) {
            this.mNestedYOffset = 0;
        }
        vtev.offsetLocation(0.0f, (float) this.mNestedYOffset);
        switch (actionMasked2) {
            case 0:
                if (getChildCount() != 0) {
                    if (this.mScroller.isFinished()) {
                        z = false;
                    } else {
                        z = true;
                    }
                    boolean z2 = z;
                    this.mIsBeingDragged = z;
                    if (z2) {
                        ViewParent parent = getParent();
                        ViewParent parent2 = parent;
                        if (parent != null) {
                            parent2.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    if (!this.mScroller.isFinished()) {
                        this.mScroller.abortAnimation();
                    }
                    this.mLastMotionY = (int) ev.getY();
                    this.mActivePointerId = ev.getPointerId(0);
                    boolean startNestedScroll = startNestedScroll(2);
                    break;
                } else {
                    return false;
                }
            case 1:
                if (this.mIsBeingDragged) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    VelocityTracker velocityTracker2 = velocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    int yVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker2, this.mActivePointerId);
                    int initialVelocity = yVelocity;
                    if (Math.abs(yVelocity) <= this.mMinimumVelocity) {
                        if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                            ViewCompat.postInvalidateOnAnimation(this);
                        }
                    } else {
                        flingWithNestedDispatch(-initialVelocity);
                    }
                }
                this.mActivePointerId = -1;
                endDrag();
                break;
            case 2:
                int findPointerIndex = ev.findPointerIndex(this.mActivePointerId);
                int activePointerIndex = findPointerIndex;
                if (findPointerIndex == -1) {
                    int e = Log.e(TAG, "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
                    break;
                } else {
                    int y = (int) ev.getY(activePointerIndex);
                    int i = this.mLastMotionY - y;
                    int deltaY = i;
                    if (dispatchNestedPreScroll(0, i, this.mScrollConsumed, this.mScrollOffset)) {
                        deltaY -= this.mScrollConsumed[1];
                        vtev.offsetLocation(0.0f, (float) this.mScrollOffset[1]);
                        this.mNestedYOffset += this.mScrollOffset[1];
                    }
                    if (!this.mIsBeingDragged && Math.abs(deltaY) > this.mTouchSlop) {
                        ViewParent parent3 = getParent();
                        ViewParent parent4 = parent3;
                        if (parent3 != null) {
                            parent4.requestDisallowInterceptTouchEvent(true);
                        }
                        this.mIsBeingDragged = true;
                        if (deltaY <= 0) {
                            deltaY += this.mTouchSlop;
                        } else {
                            deltaY -= this.mTouchSlop;
                        }
                    }
                    if (this.mIsBeingDragged) {
                        this.mLastMotionY = y - this.mScrollOffset[1];
                        int oldY = getScrollY();
                        int range = getScrollRange();
                        int overScrollMode = getOverScrollMode();
                        boolean canOverscroll = overScrollMode == 0 || (overScrollMode == 1 && range > 0);
                        if (overScrollByCompat(0, deltaY, 0, getScrollY(), 0, range, 0, 0, true) && !hasNestedScrollingParent()) {
                            this.mVelocityTracker.clear();
                        }
                        int scrolledDeltaY = getScrollY() - oldY;
                        int i2 = deltaY - scrolledDeltaY;
                        int i3 = i2;
                        if (!dispatchNestedScroll(0, scrolledDeltaY, 0, i2, this.mScrollOffset)) {
                            if (canOverscroll) {
                                ensureGlows();
                                int i4 = oldY + deltaY;
                                int pulledToY = i4;
                                if (i4 < 0) {
                                    boolean onPull = this.mEdgeGlowTop.onPull(((float) deltaY) / ((float) getHeight()), ev.getX(activePointerIndex) / ((float) getWidth()));
                                    if (!this.mEdgeGlowBottom.isFinished()) {
                                        boolean onRelease = this.mEdgeGlowBottom.onRelease();
                                    }
                                } else if (pulledToY > range) {
                                    boolean onPull2 = this.mEdgeGlowBottom.onPull(((float) deltaY) / ((float) getHeight()), 1.0f - (ev.getX(activePointerIndex) / ((float) getWidth())));
                                    if (!this.mEdgeGlowTop.isFinished()) {
                                        boolean onRelease2 = this.mEdgeGlowTop.onRelease();
                                    }
                                }
                                if (this.mEdgeGlowTop != null && (!this.mEdgeGlowTop.isFinished() || !this.mEdgeGlowBottom.isFinished())) {
                                    ViewCompat.postInvalidateOnAnimation(this);
                                    break;
                                }
                            }
                        } else {
                            this.mLastMotionY -= this.mScrollOffset[1];
                            vtev.offsetLocation(0.0f, (float) this.mScrollOffset[1]);
                            this.mNestedYOffset += this.mScrollOffset[1];
                            break;
                        }
                    }
                }
                break;
            case 3:
                if (this.mIsBeingDragged && getChildCount() > 0) {
                    if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                }
                this.mActivePointerId = -1;
                endDrag();
                break;
            case 5:
                int actionIndex = MotionEventCompat.getActionIndex(ev);
                int i5 = actionIndex;
                this.mLastMotionY = (int) ev.getY(actionIndex);
                this.mActivePointerId = ev.getPointerId(actionIndex);
                break;
            case 6:
                onSecondaryPointerUp(ev);
                this.mLastMotionY = (int) ev.getY(ev.findPointerIndex(this.mActivePointerId));
                break;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(vtev);
        }
        vtev.recycle();
        return true;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int action = (ev.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        int pointerIndex = action;
        int pointerId = ev.getPointerId(action);
        int i = pointerId;
        if (pointerId == this.mActivePointerId) {
            int newPointerIndex = pointerIndex != 0 ? 0 : 1;
            this.mLastMotionY = (int) ev.getY(newPointerIndex);
            this.mActivePointerId = ev.getPointerId(newPointerIndex);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        if ((event.getSource() & 2) != 0) {
            switch (event.getAction()) {
                case 8:
                    if (!this.mIsBeingDragged) {
                        float axisValue = MotionEventCompat.getAxisValue(event, 9);
                        float vscroll = axisValue;
                        if (axisValue != 0.0f) {
                            int delta = (int) (vscroll * getVerticalScrollFactorCompat());
                            int range = getScrollRange();
                            int scrollY = getScrollY();
                            int oldScrollY = scrollY;
                            int i = scrollY - delta;
                            int newScrollY = i;
                            if (i < 0) {
                                newScrollY = 0;
                            } else if (newScrollY > range) {
                                newScrollY = range;
                            }
                            if (newScrollY != oldScrollY) {
                                super.scrollTo(getScrollX(), newScrollY);
                                return true;
                            }
                        }
                    }
                    break;
            }
        }
        return false;
    }

    private float getVerticalScrollFactorCompat() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue outValue = new TypedValue();
            Context context = getContext();
            Context context2 = context;
            if (context.getTheme().resolveAttribute(16842829, outValue, true)) {
                this.mVerticalScrollFactor = outValue.getDimension(context2.getResources().getDisplayMetrics());
            } else {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
        }
        return this.mVerticalScrollFactor;
    }

    /* access modifiers changed from: protected */
    public void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        int scrollX = i;
        int scrollY = i2;
        int i3 = scrollX;
        int i4 = scrollY;
        boolean z3 = z;
        boolean z4 = z2;
        super.scrollTo(scrollX, scrollY);
    }

    /* access modifiers changed from: 0000 */
    public boolean overScrollByCompat(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        boolean z2;
        int deltaX = i;
        int deltaY = i2;
        int scrollX = i3;
        int scrollY = i4;
        int scrollRangeX = i5;
        int scrollRangeY = i6;
        int i9 = deltaX;
        int i10 = deltaY;
        int i11 = scrollX;
        int i12 = scrollY;
        int i13 = scrollRangeX;
        int i14 = scrollRangeY;
        int maxOverScrollX = i7;
        int maxOverScrollY = i8;
        boolean z3 = z;
        int overScrollMode = getOverScrollMode();
        boolean canScrollHorizontal = computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        boolean canScrollVertical = computeVerticalScrollRange() > computeVerticalScrollExtent();
        boolean overScrollHorizontal = overScrollMode == 0 || (overScrollMode == 1 && canScrollHorizontal);
        boolean overScrollVertical = overScrollMode == 0 || (overScrollMode == 1 && canScrollVertical);
        int newScrollX = scrollX + deltaX;
        if (!overScrollHorizontal) {
            maxOverScrollX = 0;
        }
        int newScrollY = scrollY + deltaY;
        if (!overScrollVertical) {
            maxOverScrollY = 0;
        }
        int left = -maxOverScrollX;
        int right = maxOverScrollX + scrollRangeX;
        int top = -maxOverScrollY;
        int bottom = maxOverScrollY + scrollRangeY;
        boolean clampedX = false;
        if (newScrollX > right) {
            newScrollX = right;
            clampedX = true;
        } else if (newScrollX < left) {
            newScrollX = left;
            clampedX = true;
        }
        boolean clampedY = false;
        if (newScrollY > bottom) {
            newScrollY = bottom;
            clampedY = true;
        } else if (newScrollY < top) {
            newScrollY = top;
            clampedY = true;
        }
        if (clampedY) {
            boolean springBack = this.mScroller.springBack(newScrollX, newScrollY, 0, 0, 0, getScrollRange());
        }
        onOverScrolled(newScrollX, newScrollY, clampedX, clampedY);
        if (!clampedX && !clampedY) {
            z2 = false;
        } else {
            z2 = true;
        }
        return z2;
    }

    /* access modifiers changed from: 0000 */
    public int getScrollRange() {
        int scrollRange = 0;
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            View view = childAt;
            scrollRange = Math.max(0, childAt.getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
        }
        return scrollRange;
    }

    private View findFocusableViewInBounds(boolean z, int i, int i2) {
        int top = i;
        int bottom = i2;
        boolean topFocus = z;
        int i3 = top;
        int i4 = bottom;
        ArrayList focusables = getFocusables(2);
        View focusCandidate = null;
        boolean foundFullyContainedFocusable = false;
        int count = focusables.size();
        for (int i5 = 0; i5 < count; i5++) {
            View view = (View) focusables.get(i5);
            View view2 = view;
            int viewTop = view.getTop();
            int viewBottom = view2.getBottom();
            if (top < viewBottom && viewTop < bottom) {
                boolean viewIsFullyContained = top < viewTop && viewBottom < bottom;
                if (focusCandidate != null) {
                    boolean viewIsCloserToBoundary = (topFocus && viewTop < focusCandidate.getTop()) || (!topFocus && viewBottom > focusCandidate.getBottom());
                    if (!foundFullyContainedFocusable) {
                        if (viewIsFullyContained) {
                            focusCandidate = view2;
                            foundFullyContainedFocusable = true;
                        } else if (viewIsCloserToBoundary) {
                            focusCandidate = view2;
                        }
                    } else if (viewIsFullyContained && viewIsCloserToBoundary) {
                        focusCandidate = view2;
                    }
                } else {
                    focusCandidate = view2;
                    foundFullyContainedFocusable = viewIsFullyContained;
                }
            }
        }
        return focusCandidate;
    }

    public boolean pageScroll(int i) {
        int direction = i;
        int i2 = direction;
        boolean down = direction == 130;
        int height = getHeight();
        if (!down) {
            this.mTempRect.top = getScrollY() - height;
            if (this.mTempRect.top < 0) {
                this.mTempRect.top = 0;
            }
        } else {
            this.mTempRect.top = getScrollY() + height;
            int childCount = getChildCount();
            int count = childCount;
            if (childCount > 0) {
                View view = getChildAt(count - 1);
                if (this.mTempRect.top + height > view.getBottom()) {
                    this.mTempRect.top = view.getBottom() - height;
                }
            }
        }
        this.mTempRect.bottom = this.mTempRect.top + height;
        return scrollAndFocus(direction, this.mTempRect.top, this.mTempRect.bottom);
    }

    public boolean fullScroll(int i) {
        int direction = i;
        int i2 = direction;
        boolean down = direction == 130;
        int height = getHeight();
        int height2 = height;
        this.mTempRect.top = 0;
        this.mTempRect.bottom = height;
        if (down) {
            int childCount = getChildCount();
            int count = childCount;
            if (childCount > 0) {
                this.mTempRect.bottom = getChildAt(count - 1).getBottom() + getPaddingBottom();
                this.mTempRect.top = this.mTempRect.bottom - height2;
            }
        }
        return scrollAndFocus(direction, this.mTempRect.top, this.mTempRect.bottom);
    }

    private boolean scrollAndFocus(int i, int i2, int i3) {
        int direction = i;
        int top = i2;
        int bottom = i3;
        int i4 = direction;
        int i5 = top;
        int i6 = bottom;
        boolean handled = true;
        int height = getHeight();
        int scrollY = getScrollY();
        int containerTop = scrollY;
        int containerBottom = scrollY + height;
        boolean up = direction == 33;
        View findFocusableViewInBounds = findFocusableViewInBounds(up, top, bottom);
        View newFocused = findFocusableViewInBounds;
        if (findFocusableViewInBounds == null) {
            newFocused = this;
        }
        if (top >= containerTop && bottom <= containerBottom) {
            handled = false;
        } else {
            doScrollY(!up ? bottom - containerBottom : top - containerTop);
        }
        if (newFocused != findFocus()) {
            boolean requestFocus = newFocused.requestFocus(direction);
        }
        return handled;
    }

    public boolean arrowScroll(int i) {
        int direction = i;
        int i2 = direction;
        View findFocus = findFocus();
        View currentFocused = findFocus;
        if (findFocus == this) {
            currentFocused = null;
        }
        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
        int maxScrollAmount = getMaxScrollAmount();
        int maxJump = maxScrollAmount;
        if (nextFocused != null) {
            if (isWithinDeltaOfScreen(nextFocused, maxScrollAmount, getHeight())) {
                nextFocused.getDrawingRect(this.mTempRect);
                offsetDescendantRectToMyCoords(nextFocused, this.mTempRect);
                int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
                int i3 = computeScrollDeltaToGetChildRectOnScreen;
                doScrollY(computeScrollDeltaToGetChildRectOnScreen);
                boolean requestFocus = nextFocused.requestFocus(direction);
                if (currentFocused != null && currentFocused.isFocused() && isOffScreen(currentFocused)) {
                    int descendantFocusability = getDescendantFocusability();
                    setDescendantFocusability(131072);
                    boolean requestFocus2 = requestFocus();
                    setDescendantFocusability(descendantFocusability);
                }
                return true;
            }
        }
        int scrollDelta = maxJump;
        if (direction == 33 && getScrollY() < maxJump) {
            scrollDelta = getScrollY();
        } else if (direction == 130 && getChildCount() > 0) {
            int daBottom = getChildAt(0).getBottom();
            int screenBottom = (getScrollY() + getHeight()) - getPaddingBottom();
            if (daBottom - screenBottom < maxJump) {
                scrollDelta = daBottom - screenBottom;
            }
        }
        if (scrollDelta == 0) {
            return false;
        }
        doScrollY(direction != 130 ? -scrollDelta : scrollDelta);
        int descendantFocusability2 = getDescendantFocusability();
        setDescendantFocusability(131072);
        boolean requestFocus22 = requestFocus();
        setDescendantFocusability(descendantFocusability2);
        return true;
    }

    private boolean isOffScreen(View view) {
        View descendant = view;
        View view2 = descendant;
        return !isWithinDeltaOfScreen(descendant, 0, getHeight());
    }

    private boolean isWithinDeltaOfScreen(View view, int i, int i2) {
        View descendant = view;
        int delta = i;
        int height = i2;
        View view2 = descendant;
        int i3 = delta;
        int i4 = height;
        descendant.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(descendant, this.mTempRect);
        return this.mTempRect.bottom + delta >= getScrollY() && this.mTempRect.top - delta <= getScrollY() + height;
    }

    private void doScrollY(int i) {
        int delta = i;
        int i2 = delta;
        if (delta != 0) {
            if (!this.mSmoothScrollingEnabled) {
                scrollBy(0, delta);
            } else {
                smoothScrollBy(0, delta);
            }
        }
    }

    public final void smoothScrollBy(int i, int i2) {
        int dx = i;
        int dy = i2;
        int i3 = dx;
        int i4 = dy;
        if (getChildCount() != 0) {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll;
            long j = currentAnimationTimeMillis;
            if (!(currentAnimationTimeMillis <= 250)) {
                int maxY = Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
                int scrollY = getScrollY();
                int max = Math.max(0, Math.min(scrollY + dy, maxY)) - scrollY;
                int dy2 = max;
                this.mScroller.startScroll(getScrollX(), scrollY, 0, max);
                ViewCompat.postInvalidateOnAnimation(this);
            } else {
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                scrollBy(dx, dy);
            }
            this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
        }
    }

    public final void smoothScrollTo(int i, int i2) {
        int x = i;
        int y = i2;
        int i3 = x;
        int i4 = y;
        smoothScrollBy(x - getScrollX(), y - getScrollY());
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeVerticalScrollRange() {
        int contentHeight = (getHeight() - getPaddingBottom()) - getPaddingTop();
        if (getChildCount() == 0) {
            return contentHeight;
        }
        int scrollRange = getChildAt(0).getBottom();
        int scrollY = getScrollY();
        int overscrollBottom = Math.max(0, scrollRange - contentHeight);
        if (scrollY < 0) {
            scrollRange -= scrollY;
        } else if (scrollY > overscrollBottom) {
            scrollRange += scrollY - overscrollBottom;
        }
        return scrollRange;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    /* access modifiers changed from: protected */
    public void measureChild(View view, int i, int i2) {
        View child = view;
        int parentWidthMeasureSpec = i;
        View view2 = child;
        int i3 = parentWidthMeasureSpec;
        int i4 = i2;
        LayoutParams layoutParams = child.getLayoutParams();
        LayoutParams layoutParams2 = layoutParams;
        int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width);
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        int i5 = makeMeasureSpec;
        child.measure(childWidthMeasureSpec, makeMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        View child = view;
        int parentWidthMeasureSpec = i;
        int widthUsed = i2;
        View view2 = child;
        int i5 = parentWidthMeasureSpec;
        int i6 = widthUsed;
        int i7 = i3;
        int i8 = i4;
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
        MarginLayoutParams lp = marginLayoutParams;
        int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + widthUsed, marginLayoutParams.width);
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(marginLayoutParams.topMargin + lp.bottomMargin, 0);
        int i9 = makeMeasureSpec;
        child.measure(childWidthMeasureSpec, makeMeasureSpec);
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = this.mScroller.getCurrX();
            int y = this.mScroller.getCurrY();
            if (oldX != x || oldY != y) {
                int range = getScrollRange();
                int overScrollMode = getOverScrollMode();
                boolean canOverscroll = overScrollMode == 0 || (overScrollMode == 1 && range > 0);
                boolean overScrollByCompat = overScrollByCompat(x - oldX, y - oldY, oldX, oldY, 0, range, 0, 0, false);
                if (canOverscroll) {
                    ensureGlows();
                    if (y <= 0 && oldY > 0) {
                        boolean onAbsorb = this.mEdgeGlowTop.onAbsorb((int) this.mScroller.getCurrVelocity());
                    } else if (y >= range && oldY < range) {
                        boolean onAbsorb2 = this.mEdgeGlowBottom.onAbsorb((int) this.mScroller.getCurrVelocity());
                    }
                }
            }
        }
    }

    private void scrollToChild(View view) {
        View child = view;
        View view2 = child;
        child.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(child, this.mTempRect);
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        int scrollDelta = computeScrollDeltaToGetChildRectOnScreen;
        if (computeScrollDeltaToGetChildRectOnScreen != 0) {
            scrollBy(0, scrollDelta);
        }
    }

    private boolean scrollToChildRect(Rect rect, boolean z) {
        Rect rect2 = rect;
        Rect rect3 = rect2;
        boolean immediate = z;
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect2);
        int delta = computeScrollDeltaToGetChildRectOnScreen;
        boolean scroll = computeScrollDeltaToGetChildRectOnScreen != 0;
        if (scroll) {
            if (!immediate) {
                smoothScrollBy(0, delta);
            } else {
                scrollBy(0, delta);
            }
        }
        return scroll;
    }

    /* access modifiers changed from: protected */
    public int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        int scrollYDelta;
        int scrollYDelta2;
        Rect rect2 = rect;
        Rect rect3 = rect2;
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int screenTop = scrollY;
        int screenBottom = scrollY + height;
        int fadingEdge = getVerticalFadingEdgeLength();
        if (rect2.top > 0) {
            screenTop += fadingEdge;
        }
        if (rect2.bottom < getChildAt(0).getHeight()) {
            screenBottom -= fadingEdge;
        }
        int scrollYDelta3 = 0;
        if (rect2.bottom > screenBottom && rect2.top > screenTop) {
            if (rect2.height() <= height) {
                scrollYDelta = 0 + (rect2.bottom - screenBottom);
            } else {
                scrollYDelta = 0 + (rect2.top - screenTop);
            }
            int bottom = getChildAt(0).getBottom();
            int i = bottom;
            scrollYDelta3 = Math.min(scrollYDelta, bottom - screenBottom);
        } else if (rect2.top < screenTop && rect2.bottom < screenBottom) {
            if (rect2.height() <= height) {
                scrollYDelta2 = 0 - (screenTop - rect2.top);
            } else {
                scrollYDelta2 = 0 - (screenBottom - rect2.bottom);
            }
            scrollYDelta3 = Math.max(scrollYDelta2, -getScrollY());
        }
        return scrollYDelta3;
    }

    public void requestChildFocus(View view, View view2) {
        View child = view;
        View focused = view2;
        View view3 = child;
        View view4 = focused;
        if (this.mIsLayoutDirty) {
            this.mChildToScrollTo = focused;
        } else {
            scrollToChild(focused);
        }
        super.requestChildFocus(child, focused);
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        View findNextFocus;
        int direction = i;
        Rect previouslyFocusedRect = rect;
        int direction2 = direction;
        Rect rect2 = previouslyFocusedRect;
        if (direction == 2) {
            direction2 = TransportMediator.KEYCODE_MEDIA_RECORD;
        } else if (direction == 1) {
            direction2 = 33;
        }
        if (previouslyFocusedRect != null) {
            findNextFocus = FocusFinder.getInstance().findNextFocusFromRect(this, previouslyFocusedRect, direction2);
        } else {
            findNextFocus = FocusFinder.getInstance().findNextFocus(this, null, direction2);
        }
        View nextFocus = findNextFocus;
        if (nextFocus == null) {
            return false;
        }
        if (!isOffScreen(nextFocus)) {
            return nextFocus.requestFocus(direction2, previouslyFocusedRect);
        }
        return false;
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        View child = view;
        Rect rectangle = rect;
        View view2 = child;
        Rect rect2 = rectangle;
        boolean immediate = z;
        rectangle.offset(child.getLeft() - child.getScrollX(), child.getTop() - child.getScrollY());
        return scrollToChildRect(rectangle, immediate);
    }

    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int l = i;
        int t = i2;
        int r = i3;
        int b = i4;
        int i5 = l;
        int i6 = t;
        int i7 = r;
        int i8 = b;
        super.onLayout(z, l, t, r, b);
        this.mIsLayoutDirty = false;
        if (this.mChildToScrollTo != null && isViewDescendantOf(this.mChildToScrollTo, this)) {
            scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!this.mIsLaidOut) {
            if (this.mSavedState != null) {
                scrollTo(getScrollX(), this.mSavedState.scrollPosition);
                this.mSavedState = null;
            }
            int scrollRange = Math.max(0, (getChildCount() <= 0 ? 0 : getChildAt(0).getMeasuredHeight()) - (((b - t) - getPaddingBottom()) - getPaddingTop()));
            if (getScrollY() > scrollRange) {
                scrollTo(getScrollX(), scrollRange);
            } else if (getScrollY() < 0) {
                scrollTo(getScrollX(), 0);
            }
        }
        scrollTo(getScrollX(), getScrollY());
        this.mIsLaidOut = true;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsLaidOut = false;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        int w = i;
        int h = i2;
        int oldw = i3;
        int oldh = i4;
        int i5 = w;
        int i6 = h;
        int i7 = oldw;
        int i8 = oldh;
        super.onSizeChanged(w, h, oldw, oldh);
        View currentFocused = findFocus();
        if (!(null == currentFocused || this == currentFocused || !isWithinDeltaOfScreen(currentFocused, 0, oldh))) {
            currentFocused.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(currentFocused, this.mTempRect);
            int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
            int i9 = computeScrollDeltaToGetChildRectOnScreen;
            doScrollY(computeScrollDeltaToGetChildRectOnScreen);
        }
    }

    private static boolean isViewDescendantOf(View view, View view2) {
        View child = view;
        View parent = view2;
        View view3 = child;
        View view4 = parent;
        if (child == parent) {
            return true;
        }
        ViewParent parent2 = child.getParent();
        return (parent2 instanceof ViewGroup) && isViewDescendantOf((View) parent2, parent);
    }

    public void fling(int i) {
        int velocityY = i;
        int i2 = velocityY;
        if (getChildCount() > 0) {
            int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
            int height2 = getChildAt(0).getHeight();
            int i3 = height2;
            this.mScroller.fling(getScrollX(), getScrollY(), 0, velocityY, 0, 0, 0, Math.max(0, height2 - height), 0, height / 2);
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void flingWithNestedDispatch(int i) {
        int velocityY = i;
        int i2 = velocityY;
        int scrollY = getScrollY();
        boolean canFling = (scrollY > 0 || velocityY > 0) && (scrollY < getScrollRange() || velocityY < 0);
        if (!dispatchNestedPreFling(0.0f, (float) velocityY)) {
            boolean dispatchNestedFling = dispatchNestedFling(0.0f, (float) velocityY, canFling);
            if (canFling) {
                fling(velocityY);
            }
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        recycleVelocityTracker();
        stopNestedScroll();
        if (this.mEdgeGlowTop != null) {
            boolean onRelease = this.mEdgeGlowTop.onRelease();
            boolean onRelease2 = this.mEdgeGlowBottom.onRelease();
        }
    }

    public void scrollTo(int i, int i2) {
        int x = i;
        int y = i2;
        int i3 = x;
        int i4 = y;
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            View view = childAt;
            int x2 = clamp(x, (getWidth() - getPaddingRight()) - getPaddingLeft(), childAt.getWidth());
            int y2 = clamp(y, (getHeight() - getPaddingBottom()) - getPaddingTop(), childAt.getHeight());
            if (x2 != getScrollX() || y2 != getScrollY()) {
                super.scrollTo(x2, y2);
            }
        }
    }

    private void ensureGlows() {
        if (getOverScrollMode() == 2) {
            this.mEdgeGlowTop = null;
            this.mEdgeGlowBottom = null;
        } else if (this.mEdgeGlowTop == null) {
            Context context = getContext();
            this.mEdgeGlowTop = new EdgeEffectCompat(context);
            this.mEdgeGlowBottom = new EdgeEffectCompat(context);
        }
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        super.draw(canvas2);
        if (this.mEdgeGlowTop != null) {
            int scrollY = getScrollY();
            if (!this.mEdgeGlowTop.isFinished()) {
                int restoreCount = canvas2.save();
                int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
                int i = width;
                canvas2.translate((float) getPaddingLeft(), (float) Math.min(0, scrollY));
                this.mEdgeGlowTop.setSize(width, getHeight());
                if (this.mEdgeGlowTop.draw(canvas2)) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                canvas2.restoreToCount(restoreCount);
            }
            if (!this.mEdgeGlowBottom.isFinished()) {
                int restoreCount2 = canvas2.save();
                int width2 = (getWidth() - getPaddingLeft()) - getPaddingRight();
                int height = getHeight();
                int i2 = height;
                canvas2.translate((float) ((-width2) + getPaddingLeft()), (float) (Math.max(getScrollRange(), scrollY) + height));
                canvas2.rotate(180.0f, (float) width2, 0.0f);
                this.mEdgeGlowBottom.setSize(width2, height);
                if (this.mEdgeGlowBottom.draw(canvas2)) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                canvas2.restoreToCount(restoreCount2);
            }
        }
    }

    private static int clamp(int i, int i2, int i3) {
        int n = i;
        int my = i2;
        int child = i3;
        int i4 = n;
        int i5 = my;
        int i6 = child;
        if (my >= child || n < 0) {
            return 0;
        }
        if (my + n <= child) {
            return n;
        }
        return child - my;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            this.mSavedState = ss;
            requestLayout();
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState ss = savedState;
        savedState.scrollPosition = getScrollY();
        return ss;
    }
}
