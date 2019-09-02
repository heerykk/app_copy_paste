package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.design.C0001R;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.view.AbsSavedState;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.NestedScrollingChild;
import android.support.p000v4.view.VelocityTrackerCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.widget.ViewDragHelper;
import android.support.p000v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class BottomSheetBehavior<V extends View> extends Behavior<V> {
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    int mActivePointerId;
    private BottomSheetCallback mCallback;
    private final Callback mDragCallback = new Callback(this) {
        final /* synthetic */ BottomSheetBehavior this$0;

        {
            BottomSheetBehavior this$02 = r5;
            BottomSheetBehavior bottomSheetBehavior = this$02;
            this.this$0 = this$02;
        }

        public boolean tryCaptureView(View view, int i) {
            boolean z;
            View child = view;
            int pointerId = i;
            View view2 = child;
            int i2 = pointerId;
            if (this.this$0.mState == 1) {
                return false;
            }
            if (this.this$0.mTouchingScrollingChild) {
                return false;
            }
            if (this.this$0.mState == 3 && this.this$0.mActivePointerId == pointerId) {
                View view3 = (View) this.this$0.mNestedScrollingChildRef.get();
                View scroll = view3;
                if (view3 != null && ViewCompat.canScrollVertically(scroll, -1)) {
                    return false;
                }
            }
            if (this.this$0.mViewRef != null && this.this$0.mViewRef.get() == child) {
                z = true;
            } else {
                z = false;
            }
            return z;
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            int top = i2;
            View view2 = view;
            int i5 = i;
            int i6 = top;
            int i7 = i3;
            int i8 = i4;
            this.this$0.dispatchOnSlide(top);
        }

        public void onViewDragStateChanged(int i) {
            int state = i;
            int i2 = state;
            if (state == 1) {
                this.this$0.setStateInternal(1);
            }
        }

        public void onViewReleased(View view, float f, float f2) {
            int top;
            int targetState;
            View releasedChild = view;
            float yvel = f2;
            View view2 = releasedChild;
            float f3 = f;
            float f4 = yvel;
            if (yvel < 0.0f) {
                top = this.this$0.mMinOffset;
                targetState = 3;
            } else if (this.this$0.mHideable && this.this$0.shouldHide(releasedChild, yvel)) {
                top = this.this$0.mParentHeight;
                targetState = 5;
            } else if (yvel == 0.0f) {
                int top2 = releasedChild.getTop();
                if (Math.abs(top2 - this.this$0.mMinOffset) >= Math.abs(top2 - this.this$0.mMaxOffset)) {
                    top = this.this$0.mMaxOffset;
                    targetState = 4;
                } else {
                    top = this.this$0.mMinOffset;
                    targetState = 3;
                }
            } else {
                top = this.this$0.mMaxOffset;
                targetState = 4;
            }
            if (!this.this$0.mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top)) {
                this.this$0.setStateInternal(targetState);
                return;
            }
            this.this$0.setStateInternal(2);
            SettleRunnable settleRunnable = new SettleRunnable(this.this$0, releasedChild, targetState);
            ViewCompat.postOnAnimation(releasedChild, settleRunnable);
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            int top = i;
            View view2 = view;
            int i3 = top;
            int i4 = i2;
            return MathUtils.constrain(top, this.this$0.mMinOffset, !this.this$0.mHideable ? this.this$0.mMaxOffset : this.this$0.mParentHeight);
        }

        public int clampViewPositionHorizontal(View view, int i, int i2) {
            View child = view;
            View view2 = child;
            int i3 = i;
            int i4 = i2;
            return child.getLeft();
        }

        public int getViewVerticalDragRange(View view) {
            View view2 = view;
            if (!this.this$0.mHideable) {
                return this.this$0.mMaxOffset - this.this$0.mMinOffset;
            }
            return this.this$0.mParentHeight - this.this$0.mMinOffset;
        }
    };
    boolean mHideable;
    private boolean mIgnoreEvents;
    private int mInitialY;
    private int mLastNestedScrollDy;
    int mMaxOffset;
    private float mMaximumVelocity;
    int mMinOffset;
    private boolean mNestedScrolled;
    WeakReference<View> mNestedScrollingChildRef;
    int mParentHeight;
    private int mPeekHeight;
    private boolean mPeekHeightAuto;
    private int mPeekHeightMin;
    private boolean mSkipCollapsed;
    int mState = 4;
    boolean mTouchingScrollingChild;
    private VelocityTracker mVelocityTracker;
    ViewDragHelper mViewDragHelper;
    WeakReference<V> mViewRef;

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view, float f);

        public abstract void onStateChanged(@NonNull View view, int i);

        public BottomSheetCallback() {
        }
    }

    protected static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                Parcel in = parcel;
                ClassLoader loader = classLoader;
                Parcel parcel2 = in;
                ClassLoader classLoader2 = loader;
                return new SavedState(in, loader);
            }

            public SavedState[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new SavedState[size];
            }
        });
        final int state;

        public SavedState(Parcel parcel) {
            Parcel source = parcel;
            Parcel parcel2 = source;
            this(source, (ClassLoader) null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            Parcel source = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = source;
            ClassLoader classLoader2 = loader;
            super(source, loader);
            this.state = source.readInt();
        }

        public SavedState(Parcelable parcelable, int i) {
            Parcelable superState = parcelable;
            int state2 = i;
            Parcelable parcelable2 = superState;
            int i2 = state2;
            super(superState);
            this.state = state2;
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel out = parcel;
            int flags = i;
            Parcel parcel2 = out;
            int i2 = flags;
            super.writeToParcel(out, flags);
            out.writeInt(this.state);
        }
    }

    private class SettleRunnable implements Runnable {
        private final int mTargetState;
        private final View mView;
        final /* synthetic */ BottomSheetBehavior this$0;

        SettleRunnable(BottomSheetBehavior bottomSheetBehavior, View view, int i) {
            View view2 = view;
            int targetState = i;
            View view3 = view2;
            int i2 = targetState;
            this.this$0 = bottomSheetBehavior;
            this.mView = view2;
            this.mTargetState = targetState;
        }

        public void run() {
            if (this.this$0.mViewDragHelper != null && this.this$0.mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.mView, this);
            } else {
                this.this$0.setStateInternal(this.mTargetState);
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public BottomSheetBehavior() {
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attrs, C0001R.styleable.BottomSheetBehavior_Layout);
        TypedArray a = obtainStyledAttributes;
        TypedValue peekValue = obtainStyledAttributes.peekValue(C0001R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        TypedValue value = peekValue;
        if (peekValue != null && value.data == -1) {
            setPeekHeight(value.data);
        } else {
            setPeekHeight(a.getDimensionPixelSize(C0001R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        }
        setHideable(a.getBoolean(C0001R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setSkipCollapsed(a.getBoolean(C0001R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        a.recycle();
        this.mMaximumVelocity = (float) ViewConfiguration.get(context2).getScaledMaximumFlingVelocity();
    }

    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
        CoordinatorLayout parent = coordinatorLayout;
        V child = v;
        CoordinatorLayout coordinatorLayout2 = parent;
        V v2 = child;
        return new SavedState(super.onSaveInstanceState(parent, child), this.mState);
    }

    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        CoordinatorLayout parent = coordinatorLayout;
        V child = v;
        Parcelable state = parcelable;
        CoordinatorLayout coordinatorLayout2 = parent;
        V v2 = child;
        Parcelable parcelable2 = state;
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        if (ss.state == 1 || ss.state == 2) {
            this.mState = 4;
        } else {
            this.mState = ss.state;
        }
    }

    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
        int peekHeight;
        CoordinatorLayout parent = coordinatorLayout;
        V child = v;
        int layoutDirection = i;
        CoordinatorLayout coordinatorLayout2 = parent;
        V v2 = child;
        int i2 = layoutDirection;
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            ViewCompat.setFitsSystemWindows(child, true);
        }
        int savedTop = child.getTop();
        parent.onLayoutChild(child, layoutDirection);
        this.mParentHeight = parent.getHeight();
        if (!this.mPeekHeightAuto) {
            peekHeight = this.mPeekHeight;
        } else {
            if (this.mPeekHeightMin == 0) {
                this.mPeekHeightMin = parent.getResources().getDimensionPixelSize(C0001R.dimen.design_bottom_sheet_peek_height_min);
            }
            peekHeight = Math.max(this.mPeekHeightMin, this.mParentHeight - ((parent.getWidth() * 9) / 16));
        }
        this.mMinOffset = Math.max(0, this.mParentHeight - child.getHeight());
        this.mMaxOffset = Math.max(this.mParentHeight - peekHeight, this.mMinOffset);
        if (this.mState == 3) {
            ViewCompat.offsetTopAndBottom(child, this.mMinOffset);
        } else if (this.mHideable && this.mState == 5) {
            ViewCompat.offsetTopAndBottom(child, this.mParentHeight);
        } else if (this.mState == 4) {
            ViewCompat.offsetTopAndBottom(child, this.mMaxOffset);
        } else if (this.mState == 1 || this.mState == 2) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.getTop());
        }
        if (this.mViewDragHelper == null) {
            this.mViewDragHelper = ViewDragHelper.create(parent, this.mDragCallback);
        }
        WeakReference weakReference = new WeakReference(child);
        this.mViewRef = weakReference;
        WeakReference weakReference2 = new WeakReference(findScrollingChild(child));
        this.mNestedScrollingChildRef = weakReference2;
        return true;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        CoordinatorLayout parent = coordinatorLayout;
        V child = v;
        MotionEvent event = motionEvent;
        CoordinatorLayout coordinatorLayout2 = parent;
        V v2 = child;
        MotionEvent motionEvent2 = event;
        if (child.isShown()) {
            int actionMasked = MotionEventCompat.getActionMasked(event);
            int action = actionMasked;
            if (actionMasked == 0) {
                reset();
            }
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(event);
            switch (action) {
                case 0:
                    int x = (int) event.getX();
                    int initialX = x;
                    this.mInitialY = (int) event.getY();
                    View view = (View) this.mNestedScrollingChildRef.get();
                    View scroll = view;
                    if (view != null) {
                        if (parent.isPointInChildBounds(scroll, x, this.mInitialY)) {
                            this.mActivePointerId = event.getPointerId(event.getActionIndex());
                            this.mTouchingScrollingChild = true;
                        }
                    }
                    if (this.mActivePointerId == -1) {
                        if (!parent.isPointInChildBounds(child, initialX, this.mInitialY)) {
                            z = true;
                            this.mIgnoreEvents = z;
                            break;
                        }
                    }
                    z = false;
                    this.mIgnoreEvents = z;
                case 1:
                case 3:
                    this.mTouchingScrollingChild = false;
                    this.mActivePointerId = -1;
                    if (this.mIgnoreEvents) {
                        this.mIgnoreEvents = false;
                        return false;
                    }
                    break;
            }
            if (!this.mIgnoreEvents && this.mViewDragHelper.shouldInterceptTouchEvent(event)) {
                return true;
            }
            View view2 = (View) this.mNestedScrollingChildRef.get();
            View scroll2 = view2;
            if (action == 2 && view2 != null && !this.mIgnoreEvents && this.mState != 1) {
                if (!parent.isPointInChildBounds(scroll2, (int) event.getX(), (int) event.getY()) && Math.abs(((float) this.mInitialY) - event.getY()) > ((float) this.mViewDragHelper.getTouchSlop())) {
                    z2 = true;
                    return z2;
                }
            }
            z2 = false;
            return z2;
        }
        this.mIgnoreEvents = true;
        return false;
    }

    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        boolean z;
        V child = v;
        MotionEvent event = motionEvent;
        CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
        V v2 = child;
        MotionEvent motionEvent2 = event;
        if (!child.isShown()) {
            return false;
        }
        int action = MotionEventCompat.getActionMasked(event);
        if (this.mState == 1 && action == 0) {
            return true;
        }
        this.mViewDragHelper.processTouchEvent(event);
        if (action == 0) {
            reset();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(event);
        if (action == 2 && !this.mIgnoreEvents && Math.abs(((float) this.mInitialY) - event.getY()) > ((float) this.mViewDragHelper.getTouchSlop())) {
            this.mViewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
        }
        if (this.mIgnoreEvents) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
        int nestedScrollAxes = i;
        CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
        V v2 = v;
        View view3 = view;
        View view4 = view2;
        int i2 = nestedScrollAxes;
        this.mLastNestedScrollDy = 0;
        this.mNestedScrolled = false;
        return (nestedScrollAxes & 2) != 0;
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr) {
        V child = v;
        View target = view;
        int dy = i2;
        int[] consumed = iArr;
        CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
        V v2 = child;
        View view2 = target;
        int i3 = i;
        int i4 = dy;
        int[] iArr2 = consumed;
        if (target == ((View) this.mNestedScrollingChildRef.get())) {
            int top = child.getTop();
            int currentTop = top;
            int newTop = top - dy;
            if (dy > 0) {
                if (newTop >= this.mMinOffset) {
                    consumed[1] = dy;
                    ViewCompat.offsetTopAndBottom(child, -dy);
                    setStateInternal(1);
                } else {
                    consumed[1] = currentTop - this.mMinOffset;
                    ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                    setStateInternal(3);
                }
            } else if (dy < 0 && !ViewCompat.canScrollVertically(target, -1)) {
                if (newTop > this.mMaxOffset && !this.mHideable) {
                    consumed[1] = currentTop - this.mMaxOffset;
                    ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                    setStateInternal(4);
                } else {
                    consumed[1] = dy;
                    ViewCompat.offsetTopAndBottom(child, -dy);
                    setStateInternal(1);
                }
            }
            dispatchOnSlide(child.getTop());
            this.mLastNestedScrollDy = dy;
            this.mNestedScrolled = true;
        }
    }

    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view) {
        int top;
        int targetState;
        V child = v;
        View target = view;
        CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
        V v2 = child;
        View view2 = target;
        if (child.getTop() == this.mMinOffset) {
            setStateInternal(3);
        } else if (target == this.mNestedScrollingChildRef.get() && this.mNestedScrolled) {
            if (this.mLastNestedScrollDy > 0) {
                top = this.mMinOffset;
                targetState = 3;
            } else if (this.mHideable && shouldHide(child, getYVelocity())) {
                top = this.mParentHeight;
                targetState = 5;
            } else if (this.mLastNestedScrollDy != 0) {
                top = this.mMaxOffset;
                targetState = 4;
            } else {
                int top2 = child.getTop();
                if (Math.abs(top2 - this.mMinOffset) >= Math.abs(top2 - this.mMaxOffset)) {
                    top = this.mMaxOffset;
                    targetState = 4;
                } else {
                    top = this.mMinOffset;
                    targetState = 3;
                }
            }
            if (!this.mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
                setStateInternal(targetState);
            } else {
                setStateInternal(2);
                ViewCompat.postOnAnimation(child, new SettleRunnable(this, child, targetState));
            }
            this.mNestedScrolled = false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0052, code lost:
        if (super.onNestedPreFling(r7, r8, r9, r10, r11) == false) goto L_0x0020;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onNestedPreFling(android.support.design.widget.CoordinatorLayout r28, V r29, android.view.View r30, float r31, float r32) {
        /*
            r27 = this;
            r6 = r27
            r7 = r28
            r8 = r29
            r9 = r30
            r10 = r31
            r11 = r32
            r12 = r6
            r13 = r7
            r14 = r8
            r15 = r9
            r16 = r10
            r17 = r11
            java.lang.ref.WeakReference<android.view.View> r0 = r6.mNestedScrollingChildRef
            r18 = r0
            java.lang.Object r18 = r18.get()
            r0 = r18
            if (r9 == r0) goto L_0x0025
        L_0x0020:
            r19 = 0
        L_0x0022:
            r26 = r19
            return r26
        L_0x0025:
            int r0 = r6.mState
            r19 = r0
            r20 = 3
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x0034
        L_0x0031:
            r19 = 1
            goto L_0x0022
        L_0x0034:
            r21 = r6
            r22 = r7
            r23 = r8
            r24 = r9
            r0 = r21
            r1 = r22
            r2 = r23
            r3 = r24
            r4 = r10
            r5 = r11
            boolean r25 = super.onNestedPreFling(r1, r2, r3, r4, r5)
            r19 = r25
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0031
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.onNestedPreFling(android.support.design.widget.CoordinatorLayout, android.view.View, android.view.View, float, float):boolean");
    }

    public final void setPeekHeight(int i) {
        int peekHeight = i;
        int i2 = peekHeight;
        boolean layout = false;
        if (peekHeight != -1) {
            if (this.mPeekHeightAuto || this.mPeekHeight != peekHeight) {
                this.mPeekHeightAuto = false;
                this.mPeekHeight = Math.max(0, peekHeight);
                this.mMaxOffset = this.mParentHeight - peekHeight;
                layout = true;
            }
        } else if (!this.mPeekHeightAuto) {
            this.mPeekHeightAuto = true;
            layout = true;
        }
        if (layout && this.mState == 4 && this.mViewRef != null) {
            View view = (View) this.mViewRef.get();
            View view2 = view;
            if (view != null) {
                view2.requestLayout();
            }
        }
    }

    public final int getPeekHeight() {
        return !this.mPeekHeightAuto ? this.mPeekHeight : -1;
    }

    public void setHideable(boolean z) {
        this.mHideable = z;
    }

    public boolean isHideable() {
        return this.mHideable;
    }

    public void setSkipCollapsed(boolean z) {
        this.mSkipCollapsed = z;
    }

    public boolean getSkipCollapsed() {
        return this.mSkipCollapsed;
    }

    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        BottomSheetCallback callback = bottomSheetCallback;
        BottomSheetCallback bottomSheetCallback2 = callback;
        this.mCallback = callback;
    }

    public final void setState(int i) {
        final int state = i;
        int i2 = state;
        if (state == this.mState) {
            return;
        }
        if (this.mViewRef != null) {
            View view = (View) this.mViewRef.get();
            View view2 = view;
            if (view != null) {
                ViewParent parent = view2.getParent();
                ViewParent parent2 = parent;
                if (parent != null && parent2.isLayoutRequested() && ViewCompat.isAttachedToWindow(view2)) {
                    final View view3 = view2;
                    C00251 r0 = new Runnable(this) {
                        final /* synthetic */ BottomSheetBehavior this$0;

                        {
                            BottomSheetBehavior this$02 = r7;
                            View view = r8;
                            int i = r9;
                            BottomSheetBehavior bottomSheetBehavior = this$02;
                            this.this$0 = this$02;
                        }

                        public void run() {
                            this.this$0.startSettlingAnimation(view3, state);
                        }
                    };
                    boolean post = view2.post(r0);
                } else {
                    startSettlingAnimation(view2, state);
                }
                return;
            }
            return;
        }
        if (state == 4 || state == 3 || (this.mHideable && state == 5)) {
            this.mState = state;
        }
    }

    public final int getState() {
        return this.mState;
    }

    /* access modifiers changed from: 0000 */
    public void setStateInternal(int i) {
        int state = i;
        int i2 = state;
        if (this.mState != state) {
            this.mState = state;
            View view = (View) this.mViewRef.get();
            View bottomSheet = view;
            if (!(view == null || this.mCallback == null)) {
                this.mCallback.onStateChanged(bottomSheet, state);
            }
        }
    }

    private void reset() {
        this.mActivePointerId = -1;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldHide(View view, float f) {
        boolean z;
        View child = view;
        float yvel = f;
        View view2 = child;
        float f2 = yvel;
        if (this.mSkipCollapsed) {
            return true;
        }
        if (child.getTop() < this.mMaxOffset) {
            return false;
        }
        float top = ((float) child.getTop()) + (yvel * HIDE_FRICTION);
        float f3 = top;
        if (Math.abs(top - ((float) this.mMaxOffset)) / ((float) this.mPeekHeight) > HIDE_THRESHOLD) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private View findScrollingChild(View view) {
        View view2 = view;
        View view3 = view2;
        if (view2 instanceof NestedScrollingChild) {
            return view2;
        }
        if (view2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view2;
            ViewGroup group = viewGroup;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View findScrollingChild = findScrollingChild(group.getChildAt(i));
                View scrollingChild = findScrollingChild;
                if (findScrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }

    private float getYVelocity() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        return VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
    }

    /* access modifiers changed from: 0000 */
    public void startSettlingAnimation(View view, int i) {
        int top;
        View child = view;
        int state = i;
        View view2 = child;
        int i2 = state;
        if (state == 4) {
            top = this.mMaxOffset;
        } else if (state == 3) {
            top = this.mMinOffset;
        } else if (this.mHideable && state == 5) {
            top = this.mParentHeight;
        } else {
            throw new IllegalArgumentException("Illegal state argument: " + state);
        }
        setStateInternal(2);
        if (this.mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
            SettleRunnable settleRunnable = new SettleRunnable(this, child, state);
            ViewCompat.postOnAnimation(child, settleRunnable);
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnSlide(int i) {
        int top = i;
        int i2 = top;
        View view = (View) this.mViewRef.get();
        View bottomSheet = view;
        if (view != null && this.mCallback != null) {
            if (top <= this.mMaxOffset) {
                this.mCallback.onSlide(bottomSheet, ((float) (this.mMaxOffset - top)) / ((float) (this.mMaxOffset - this.mMinOffset)));
                return;
            }
            this.mCallback.onSlide(bottomSheet, ((float) (this.mMaxOffset - top)) / ((float) (this.mParentHeight - this.mMaxOffset)));
        }
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public int getPeekHeightMin() {
        return this.mPeekHeightMin;
    }

    public static <V extends View> BottomSheetBehavior<V> from(V v) {
        V view = v;
        V v2 = view;
        LayoutParams layoutParams = view.getLayoutParams();
        LayoutParams params = layoutParams;
        if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
            Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
            Behavior behavior2 = behavior;
            if (behavior instanceof BottomSheetBehavior) {
                return (BottomSheetBehavior) behavior2;
            }
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
    }
}
