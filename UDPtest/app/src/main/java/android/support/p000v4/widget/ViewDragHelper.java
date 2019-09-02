package android.support.p000v4.widget;

import android.content.Context;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.VelocityTrackerCompat;
import android.support.p000v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import java.util.Arrays;

/* renamed from: android.support.v4.widget.ViewDragHelper */
public class ViewDragHelper {
    private static final int BASE_SETTLE_DURATION = 256;
    public static final int DIRECTION_ALL = 3;
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_VERTICAL = 2;
    public static final int EDGE_ALL = 15;
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    private static final int EDGE_SIZE = 20;
    public static final int EDGE_TOP = 4;
    public static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "ViewDragHelper";
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float f) {
            float t = f;
            float f2 = t;
            float f3 = t - 1.0f;
            float t2 = f3;
            return (f3 * t2 * t2 * t2 * t2) + 1.0f;
        }
    };
    private int mActivePointerId = -1;
    private final Callback mCallback;
    private View mCapturedView;
    private int mDragState;
    private int[] mEdgeDragsInProgress;
    private int[] mEdgeDragsLocked;
    private int mEdgeSize;
    private int[] mInitialEdgesTouched;
    private float[] mInitialMotionX;
    private float[] mInitialMotionY;
    private float[] mLastMotionX;
    private float[] mLastMotionY;
    private float mMaxVelocity;
    private float mMinVelocity;
    private final ViewGroup mParentView;
    private int mPointersDown;
    private boolean mReleaseInProgress;
    private ScrollerCompat mScroller;
    private final Runnable mSetIdleRunnable = new Runnable(this) {
        final /* synthetic */ ViewDragHelper this$0;

        {
            ViewDragHelper this$02 = r5;
            ViewDragHelper viewDragHelper = this$02;
            this.this$0 = this$02;
        }

        public void run() {
            this.this$0.setDragState(0);
        }
    };
    private int mTouchSlop;
    private int mTrackingEdges;
    private VelocityTracker mVelocityTracker;

    /* renamed from: android.support.v4.widget.ViewDragHelper$Callback */
    public static abstract class Callback {
        public abstract boolean tryCaptureView(View view, int i);

        public Callback() {
        }

        public void onViewDragStateChanged(int i) {
            int i2 = i;
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            View view2 = view;
            int i5 = i;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
        }

        public void onViewCaptured(View view, int i) {
            View view2 = view;
            int i2 = i;
        }

        public void onViewReleased(View view, float f, float f2) {
            View view2 = view;
            float f3 = f;
            float f4 = f2;
        }

        public void onEdgeTouched(int i, int i2) {
            int i3 = i;
            int i4 = i2;
        }

        public boolean onEdgeLock(int i) {
            int i2 = i;
            return false;
        }

        public void onEdgeDragStarted(int i, int i2) {
            int i3 = i;
            int i4 = i2;
        }

        public int getOrderedChildIndex(int i) {
            int index = i;
            int i2 = index;
            return index;
        }

        public int getViewHorizontalDragRange(View view) {
            View view2 = view;
            return 0;
        }

        public int getViewVerticalDragRange(View view) {
            View view2 = view;
            return 0;
        }

        public int clampViewPositionHorizontal(View view, int i, int i2) {
            View view2 = view;
            int i3 = i;
            int i4 = i2;
            return 0;
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            View view2 = view;
            int i3 = i;
            int i4 = i2;
            return 0;
        }
    }

    private ViewDragHelper(Context context, ViewGroup viewGroup, Callback callback) {
        Context context2 = context;
        ViewGroup forParent = viewGroup;
        Callback cb = callback;
        Context context3 = context2;
        ViewGroup viewGroup2 = forParent;
        Callback callback2 = cb;
        if (forParent == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        } else if (cb != null) {
            this.mParentView = forParent;
            this.mCallback = cb;
            ViewConfiguration vc = ViewConfiguration.get(context2);
            float f = context2.getResources().getDisplayMetrics().density;
            float f2 = f;
            this.mEdgeSize = (int) ((20.0f * f) + 0.5f);
            this.mTouchSlop = vc.getScaledTouchSlop();
            this.mMaxVelocity = (float) vc.getScaledMaximumFlingVelocity();
            this.mMinVelocity = (float) vc.getScaledMinimumFlingVelocity();
            this.mScroller = ScrollerCompat.create(context2, sInterpolator);
        } else {
            throw new IllegalArgumentException("Callback may not be null");
        }
    }

    public static ViewDragHelper create(ViewGroup viewGroup, Callback callback) {
        ViewGroup forParent = viewGroup;
        Callback cb = callback;
        ViewGroup viewGroup2 = forParent;
        Callback callback2 = cb;
        return new ViewDragHelper(forParent.getContext(), forParent, cb);
    }

    public static ViewDragHelper create(ViewGroup viewGroup, float f, Callback callback) {
        ViewGroup forParent = viewGroup;
        float sensitivity = f;
        Callback cb = callback;
        ViewGroup viewGroup2 = forParent;
        float f2 = sensitivity;
        Callback callback2 = cb;
        ViewDragHelper create = create(forParent, cb);
        ViewDragHelper helper = create;
        create.mTouchSlop = (int) (((float) helper.mTouchSlop) * (1.0f / sensitivity));
        return helper;
    }

    public void setMinVelocity(float f) {
        float minVel = f;
        float f2 = minVel;
        this.mMinVelocity = minVel;
    }

    public float getMinVelocity() {
        return this.mMinVelocity;
    }

    public int getViewDragState() {
        return this.mDragState;
    }

    public void setEdgeTrackingEnabled(int i) {
        int edgeFlags = i;
        int i2 = edgeFlags;
        this.mTrackingEdges = edgeFlags;
    }

    public int getEdgeSize() {
        return this.mEdgeSize;
    }

    public void captureChildView(View view, int i) {
        View childView = view;
        int activePointerId = i;
        View view2 = childView;
        int i2 = activePointerId;
        if (childView.getParent() == this.mParentView) {
            this.mCapturedView = childView;
            this.mActivePointerId = activePointerId;
            this.mCallback.onViewCaptured(childView, activePointerId);
            setDragState(1);
            return;
        }
        throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.mParentView + ")");
    }

    public View getCapturedView() {
        return this.mCapturedView;
    }

    public int getActivePointerId() {
        return this.mActivePointerId;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public void cancel() {
        this.mActivePointerId = -1;
        clearMotionHistory();
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void abort() {
        cancel();
        if (this.mDragState == 2) {
            int oldX = this.mScroller.getCurrX();
            int oldY = this.mScroller.getCurrY();
            this.mScroller.abortAnimation();
            int newX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            int i = currY;
            this.mCallback.onViewPositionChanged(this.mCapturedView, newX, currY, newX - oldX, currY - oldY);
        }
        setDragState(0);
    }

    public boolean smoothSlideViewTo(View view, int i, int i2) {
        View child = view;
        int finalLeft = i;
        int finalTop = i2;
        View view2 = child;
        int i3 = finalLeft;
        int i4 = finalTop;
        this.mCapturedView = child;
        this.mActivePointerId = -1;
        boolean forceSettleCapturedViewAt = forceSettleCapturedViewAt(finalLeft, finalTop, 0, 0);
        boolean continueSliding = forceSettleCapturedViewAt;
        if (!forceSettleCapturedViewAt && this.mDragState == 0 && this.mCapturedView != null) {
            this.mCapturedView = null;
        }
        return continueSliding;
    }

    public boolean settleCapturedViewAt(int i, int i2) {
        int finalLeft = i;
        int finalTop = i2;
        int i3 = finalLeft;
        int i4 = finalTop;
        if (this.mReleaseInProgress) {
            return forceSettleCapturedViewAt(finalLeft, finalTop, (int) VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), (int) VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    private boolean forceSettleCapturedViewAt(int i, int i2, int i3, int i4) {
        int finalLeft = i;
        int finalTop = i2;
        int xvel = i3;
        int yvel = i4;
        int i5 = finalLeft;
        int i6 = finalTop;
        int i7 = xvel;
        int i8 = yvel;
        int startLeft = this.mCapturedView.getLeft();
        int startTop = this.mCapturedView.getTop();
        int dx = finalLeft - startLeft;
        int dy = finalTop - startTop;
        if (dx == 0 && dy == 0) {
            this.mScroller.abortAnimation();
            setDragState(0);
            return false;
        }
        int computeSettleDuration = computeSettleDuration(this.mCapturedView, dx, dy, xvel, yvel);
        int i9 = computeSettleDuration;
        this.mScroller.startScroll(startLeft, startTop, dx, dy, computeSettleDuration);
        setDragState(2);
        return true;
    }

    private int computeSettleDuration(View view, int i, int i2, int i3, int i4) {
        View child = view;
        int dx = i;
        int dy = i2;
        int xvel = i3;
        int yvel = i4;
        View view2 = child;
        int i5 = dx;
        int i6 = dy;
        int i7 = xvel;
        int i8 = yvel;
        int xvel2 = clampMag(xvel, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        int yvel2 = clampMag(yvel, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);
        int absXVel = Math.abs(xvel2);
        int absYVel = Math.abs(yvel2);
        int addedVel = absXVel + absYVel;
        int addedDistance = absDx + absDy;
        return (int) ((((float) computeAxisDuration(dx, xvel2, this.mCallback.getViewHorizontalDragRange(child))) * (xvel2 == 0 ? ((float) absDx) / ((float) addedDistance) : ((float) absXVel) / ((float) addedVel))) + (((float) computeAxisDuration(dy, yvel2, this.mCallback.getViewVerticalDragRange(child))) * (yvel2 == 0 ? ((float) absDy) / ((float) addedDistance) : ((float) absYVel) / ((float) addedVel))));
    }

    private int computeAxisDuration(int i, int i2, int i3) {
        int duration;
        int delta = i;
        int velocity = i2;
        int motionRange = i3;
        int i4 = delta;
        int i5 = velocity;
        int i6 = motionRange;
        if (delta == 0) {
            return 0;
        }
        int width = this.mParentView.getWidth();
        int width2 = width;
        int i7 = width / 2;
        float distanceInfluenceForSnapDuration = ((float) i7) + (((float) i7) * distanceInfluenceForSnapDuration(Math.min(1.0f, ((float) Math.abs(delta)) / ((float) width2))));
        float f = distanceInfluenceForSnapDuration;
        int abs = Math.abs(velocity);
        int velocity2 = abs;
        if (abs <= 0) {
            float abs2 = ((float) Math.abs(delta)) / ((float) motionRange);
            float f2 = abs2;
            duration = (int) ((abs2 + 1.0f) * 256.0f);
        } else {
            duration = 4 * Math.round(1000.0f * Math.abs(distanceInfluenceForSnapDuration / ((float) velocity2)));
        }
        return Math.min(duration, MAX_SETTLE_DURATION);
    }

    private int clampMag(int i, int i2, int i3) {
        int value = i;
        int absMin = i2;
        int absMax = i3;
        int i4 = value;
        int i5 = absMin;
        int i6 = absMax;
        int abs = Math.abs(value);
        int absValue = abs;
        if (abs < absMin) {
            return 0;
        }
        if (absValue <= absMax) {
            return value;
        }
        return value <= 0 ? -absMax : absMax;
    }

    private float clampMag(float f, float f2, float f3) {
        float value = f;
        float absMin = f2;
        float absMax = f3;
        float f4 = value;
        float f5 = absMin;
        float f6 = absMax;
        float abs = Math.abs(value);
        float absValue = abs;
        if (abs < absMin) {
            return 0.0f;
        }
        if (absValue <= absMax) {
            return value;
        }
        return value > 0.0f ? absMax : -absMax;
    }

    private float distanceInfluenceForSnapDuration(float f) {
        float f2 = f;
        float f3 = f2;
        float f4 = f2 - 0.5f;
        float f5 = f4;
        float f6 = (float) (((double) f4) * 0.4712389167638204d);
        float f7 = f6;
        return (float) Math.sin((double) f6);
    }

    public void flingCapturedView(int i, int i2, int i3, int i4) {
        int minLeft = i;
        int minTop = i2;
        int maxLeft = i3;
        int maxTop = i4;
        int i5 = minLeft;
        int i6 = minTop;
        int i7 = maxLeft;
        int i8 = maxTop;
        if (this.mReleaseInProgress) {
            this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int) VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), (int) VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId), minLeft, maxLeft, minTop, maxTop);
            setDragState(2);
            return;
        }
        IllegalStateException illegalStateException = new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
        throw illegalStateException;
    }

    public boolean continueSettling(boolean z) {
        boolean z2;
        boolean deferCallbacks = z;
        if (this.mDragState == 2) {
            boolean keepGoing = this.mScroller.computeScrollOffset();
            int x = this.mScroller.getCurrX();
            int y = this.mScroller.getCurrY();
            int dx = x - this.mCapturedView.getLeft();
            int dy = y - this.mCapturedView.getTop();
            if (dx != 0) {
                ViewCompat.offsetLeftAndRight(this.mCapturedView, dx);
            }
            if (dy != 0) {
                ViewCompat.offsetTopAndBottom(this.mCapturedView, dy);
            }
            if (!(dx == 0 && dy == 0)) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, x, y, dx, dy);
            }
            if (keepGoing && x == this.mScroller.getFinalX() && y == this.mScroller.getFinalY()) {
                this.mScroller.abortAnimation();
                keepGoing = false;
            }
            if (!keepGoing) {
                if (!deferCallbacks) {
                    setDragState(0);
                } else {
                    boolean post = this.mParentView.post(this.mSetIdleRunnable);
                }
            }
        }
        if (this.mDragState != 2) {
            z2 = false;
        } else {
            z2 = true;
        }
        return z2;
    }

    private void dispatchViewReleased(float f, float f2) {
        float xvel = f;
        float yvel = f2;
        float f3 = xvel;
        float f4 = yvel;
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, xvel, yvel);
        this.mReleaseInProgress = false;
        if (this.mDragState == 1) {
            setDragState(0);
        }
    }

    private void clearMotionHistory() {
        if (this.mInitialMotionX != null) {
            Arrays.fill(this.mInitialMotionX, 0.0f);
            Arrays.fill(this.mInitialMotionY, 0.0f);
            Arrays.fill(this.mLastMotionX, 0.0f);
            Arrays.fill(this.mLastMotionY, 0.0f);
            Arrays.fill(this.mInitialEdgesTouched, 0);
            Arrays.fill(this.mEdgeDragsInProgress, 0);
            Arrays.fill(this.mEdgeDragsLocked, 0);
            this.mPointersDown = 0;
        }
    }

    private void clearMotionHistory(int i) {
        int pointerId = i;
        int i2 = pointerId;
        if (this.mInitialMotionX != null && isPointerDown(pointerId)) {
            this.mInitialMotionX[pointerId] = 0.0f;
            this.mInitialMotionY[pointerId] = 0.0f;
            this.mLastMotionX[pointerId] = 0.0f;
            this.mLastMotionY[pointerId] = 0.0f;
            this.mInitialEdgesTouched[pointerId] = 0;
            this.mEdgeDragsInProgress[pointerId] = 0;
            this.mEdgeDragsLocked[pointerId] = 0;
            this.mPointersDown &= (1 << pointerId) ^ -1;
        }
    }

    private void ensureMotionHistorySizeForId(int i) {
        int pointerId = i;
        int i2 = pointerId;
        if (this.mInitialMotionX == null || this.mInitialMotionX.length <= pointerId) {
            float[] imx = new float[(pointerId + 1)];
            float[] imy = new float[(pointerId + 1)];
            float[] lmx = new float[(pointerId + 1)];
            float[] lmy = new float[(pointerId + 1)];
            int[] iit = new int[(pointerId + 1)];
            int[] edip = new int[(pointerId + 1)];
            int[] edl = new int[(pointerId + 1)];
            if (this.mInitialMotionX != null) {
                System.arraycopy(this.mInitialMotionX, 0, imx, 0, this.mInitialMotionX.length);
                System.arraycopy(this.mInitialMotionY, 0, imy, 0, this.mInitialMotionY.length);
                System.arraycopy(this.mLastMotionX, 0, lmx, 0, this.mLastMotionX.length);
                System.arraycopy(this.mLastMotionY, 0, lmy, 0, this.mLastMotionY.length);
                System.arraycopy(this.mInitialEdgesTouched, 0, iit, 0, this.mInitialEdgesTouched.length);
                System.arraycopy(this.mEdgeDragsInProgress, 0, edip, 0, this.mEdgeDragsInProgress.length);
                System.arraycopy(this.mEdgeDragsLocked, 0, edl, 0, this.mEdgeDragsLocked.length);
            }
            this.mInitialMotionX = imx;
            this.mInitialMotionY = imy;
            this.mLastMotionX = lmx;
            this.mLastMotionY = lmy;
            this.mInitialEdgesTouched = iit;
            this.mEdgeDragsInProgress = edip;
            this.mEdgeDragsLocked = edl;
        }
    }

    private void saveInitialMotion(float f, float f2, int i) {
        float x = f;
        float y = f2;
        int pointerId = i;
        float f3 = x;
        float f4 = y;
        int i2 = pointerId;
        ensureMotionHistorySizeForId(pointerId);
        float[] fArr = this.mInitialMotionX;
        this.mLastMotionX[pointerId] = x;
        fArr[pointerId] = x;
        float[] fArr2 = this.mInitialMotionY;
        this.mLastMotionY[pointerId] = y;
        fArr2[pointerId] = y;
        this.mInitialEdgesTouched[pointerId] = getEdgesTouched((int) x, (int) y);
        this.mPointersDown |= 1 << pointerId;
    }

    private void saveLastMotion(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int pointerCount = ev.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = ev.getPointerId(i);
            int pointerId2 = pointerId;
            if (isValidPointerForActionMove(pointerId)) {
                float x = ev.getX(i);
                float y = ev.getY(i);
                float f = y;
                this.mLastMotionX[pointerId2] = x;
                this.mLastMotionY[pointerId2] = y;
            }
        }
    }

    public boolean isPointerDown(int i) {
        int pointerId = i;
        int i2 = pointerId;
        return (this.mPointersDown & (1 << pointerId)) != 0;
    }

    /* access modifiers changed from: 0000 */
    public void setDragState(int i) {
        int state = i;
        int i2 = state;
        boolean removeCallbacks = this.mParentView.removeCallbacks(this.mSetIdleRunnable);
        if (this.mDragState != state) {
            this.mDragState = state;
            this.mCallback.onViewDragStateChanged(state);
            if (this.mDragState == 0) {
                this.mCapturedView = null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean tryCaptureViewForDrag(View view, int i) {
        View toCapture = view;
        int pointerId = i;
        View view2 = toCapture;
        int i2 = pointerId;
        if (toCapture == this.mCapturedView && this.mActivePointerId == pointerId) {
            return true;
        }
        if (toCapture == null || !this.mCallback.tryCaptureView(toCapture, pointerId)) {
            return false;
        }
        this.mActivePointerId = pointerId;
        captureChildView(toCapture, pointerId);
        return true;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00fe, code lost:
        if (android.support.p000v4.view.ViewCompat.canScrollVertically(r8, -r11) == false) goto L_0x0032;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean canScroll(android.view.View r46, boolean r47, int r48, int r49, int r50, int r51) {
        /*
            r45 = this;
            r7 = r45
            r8 = r46
            r9 = r47
            r10 = r48
            r11 = r49
            r12 = r50
            r13 = r51
            r14 = r7
            r15 = r8
            r16 = r9
            r17 = r10
            r18 = r11
            r19 = r12
            r20 = r13
            boolean r0 = r8 instanceof android.view.ViewGroup
            r21 = r0
            r22 = r21
            r23 = r22
            r24 = 0
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x0037
        L_0x002a:
            r24 = 0
            r0 = r16
            r1 = r24
            if (r0 != r1) goto L_0x00ce
        L_0x0032:
            r23 = 0
        L_0x0034:
            r44 = r23
            return r44
        L_0x0037:
            r0 = r8
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            r25 = r0
            r26 = r25
            int r23 = r8.getScrollX()
            r27 = r23
            int r23 = r8.getScrollY()
            r28 = r23
            int r23 = r26.getChildCount()
            r29 = r23
            int r23 = r23 + -1
            r30 = r23
        L_0x0054:
            r24 = 0
            r0 = r30
            r1 = r24
            if (r0 >= r1) goto L_0x005d
            goto L_0x002a
        L_0x005d:
            r0 = r26
            r1 = r30
            android.view.View r25 = r0.getChildAt(r1)
            r31 = r25
            int r23 = r12 + r27
            int r32 = r25.getLeft()
            r0 = r23
            r1 = r32
            if (r0 >= r1) goto L_0x0076
        L_0x0073:
            int r30 = r30 + -1
            goto L_0x0054
        L_0x0076:
            int r23 = r12 + r27
            int r32 = r25.getRight()
            r0 = r23
            r1 = r32
            if (r0 < r1) goto L_0x0083
            goto L_0x0073
        L_0x0083:
            int r23 = r13 + r28
            int r32 = r25.getTop()
            r0 = r23
            r1 = r32
            if (r0 >= r1) goto L_0x0090
            goto L_0x0073
        L_0x0090:
            int r23 = r13 + r28
            int r32 = r25.getBottom()
            r0 = r23
            r1 = r32
            if (r0 < r1) goto L_0x009d
            goto L_0x0073
        L_0x009d:
            int r33 = r12 + r27
            int r34 = r31.getLeft()
            int r33 = r33 - r34
            int r34 = r13 + r28
            int r35 = r31.getTop()
            int r34 = r34 - r35
            r36 = r31
            r37 = 1
            r0 = r7
            r1 = r36
            r2 = r37
            r3 = r10
            r4 = r11
            r5 = r33
            r6 = r34
            boolean r38 = r0.canScroll(r1, r2, r3, r4, r5, r6)
            r23 = r38
            r24 = 0
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x00cb
            goto L_0x0073
        L_0x00cb:
            r39 = 1
            return r39
        L_0x00ce:
            int r0 = -r10
            r32 = r0
            r40 = r8
            r0 = r40
            r1 = r32
            boolean r41 = android.support.p000v4.view.ViewCompat.canScrollHorizontally(r0, r1)
            r23 = r41
            r24 = 0
            r0 = r23
            r1 = r24
            if (r0 == r1) goto L_0x00e9
        L_0x00e5:
            r23 = 1
            goto L_0x0034
        L_0x00e9:
            int r0 = -r11
            r32 = r0
            r42 = r8
            r0 = r42
            r1 = r32
            boolean r43 = android.support.p000v4.view.ViewCompat.canScrollVertically(r0, r1)
            r23 = r43
            r24 = 0
            r0 = r23
            r1 = r24
            if (r0 != r1) goto L_0x00e5
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.widget.ViewDragHelper.canScroll(android.view.View, boolean, int, int, int, int):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:63:0x023b, code lost:
        if (r51 == r47) goto L_0x0229;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean shouldInterceptTouchEvent(android.view.MotionEvent r65) {
        /*
            r64 = this;
            r3 = r64
            r4 = r65
            r5 = r3
            r6 = r4
            r7 = r4
            int r8 = android.support.p000v4.view.MotionEventCompat.getActionMasked(r7)
            r9 = r8
            r10 = r4
            int r8 = android.support.p000v4.view.MotionEventCompat.getActionIndex(r10)
            r11 = r8
            r12 = 0
            if (r9 == r12) goto L_0x002f
        L_0x0015:
            android.view.VelocityTracker r13 = r3.mVelocityTracker
            r14 = 0
            if (r13 == r14) goto L_0x0033
        L_0x001a:
            android.view.VelocityTracker r13 = r3.mVelocityTracker
            r16 = r4
            r0 = r16
            r13.addMovement(r0)
            switch(r9) {
                case 0: goto L_0x003a;
                case 1: goto L_0x0083;
                case 2: goto L_0x0087;
                case 3: goto L_0x008d;
                case 4: goto L_0x008e;
                case 5: goto L_0x008f;
                case 6: goto L_0x00b4;
                default: goto L_0x0026;
            }
        L_0x0026:
            int r8 = r3.mDragState
            r12 = 1
            if (r8 == r12) goto L_0x0265
            r8 = 0
        L_0x002c:
            r63 = r8
            return r63
        L_0x002f:
            r3.cancel()
            goto L_0x0015
        L_0x0033:
            android.view.VelocityTracker r15 = android.view.VelocityTracker.obtain()
            r3.mVelocityTracker = r15
            goto L_0x001a
        L_0x003a:
            float r17 = r4.getX()
            r18 = r17
            float r17 = r4.getY()
            r19 = r17
            r20 = 0
            r0 = r20
            int r8 = r4.getPointerId(r0)
            r21 = r8
            r0 = r18
            r1 = r17
            r3.saveInitialMotion(r0, r1, r8)
            r0 = r18
            int r0 = (int) r0
            r20 = r0
            r0 = r17
            int r0 = (int) r0
            r22 = r0
            r0 = r20
            r1 = r22
            android.view.View r13 = r3.findTopChildUnder(r0, r1)
            r23 = r13
            r13 = r13
            android.view.View r15 = r3.mCapturedView
            if (r13 == r15) goto L_0x00bf
        L_0x0070:
            int[] r13 = r3.mInitialEdgesTouched
            r26 = r13
            r8 = r26[r21]
            r27 = r8
            r8 = r8
            int r0 = r3.mTrackingEdges
            r20 = r0
            r8 = r8 & r20
            r12 = 0
            if (r8 != r12) goto L_0x00d0
            goto L_0x0026
        L_0x0083:
            r3.cancel()
            goto L_0x0026
        L_0x0087:
            float[] r13 = r3.mInitialMotionX
            r14 = 0
            if (r13 != r14) goto L_0x012b
            goto L_0x0026
        L_0x008d:
            goto L_0x0083
        L_0x008e:
            goto L_0x0026
        L_0x008f:
            int r8 = r4.getPointerId(r11)
            r28 = r8
            float r17 = r4.getX(r11)
            r19 = r17
            float r17 = r4.getY(r11)
            r29 = r17
            r0 = r19
            r1 = r17
            r3.saveInitialMotion(r0, r1, r8)
            int r8 = r3.mDragState
            r12 = 0
            if (r8 == r12) goto L_0x00e1
            int r8 = r3.mDragState
            r12 = 2
            if (r8 == r12) goto L_0x0105
            goto L_0x0026
        L_0x00b4:
            int r8 = r4.getPointerId(r11)
            r28 = r8
            r3.clearMotionHistory(r8)
            goto L_0x0026
        L_0x00bf:
            int r8 = r3.mDragState
            r12 = 2
            if (r8 == r12) goto L_0x00c5
            goto L_0x0070
        L_0x00c5:
            r24 = r23
            r0 = r24
            r1 = r21
            boolean r25 = r3.tryCaptureViewForDrag(r0, r1)
            goto L_0x0070
        L_0x00d0:
            android.support.v4.widget.ViewDragHelper$Callback r13 = r3.mCallback
            int r0 = r3.mTrackingEdges
            r22 = r0
            r20 = r27 & r22
            r0 = r20
            r1 = r21
            r13.onEdgeTouched(r0, r1)
            goto L_0x0026
        L_0x00e1:
            int[] r13 = r3.mInitialEdgesTouched
            r30 = r13
            r8 = r30[r28]
            r31 = r8
            r8 = r8
            int r0 = r3.mTrackingEdges
            r20 = r0
            r8 = r8 & r20
            r12 = 0
            if (r8 != r12) goto L_0x00f5
        L_0x00f3:
            goto L_0x0026
        L_0x00f5:
            android.support.v4.widget.ViewDragHelper$Callback r13 = r3.mCallback
            int r0 = r3.mTrackingEdges
            r22 = r0
            r20 = r31 & r22
            r0 = r20
            r1 = r28
            r13.onEdgeTouched(r0, r1)
            goto L_0x00f3
        L_0x0105:
            r0 = r19
            int r0 = (int) r0
            r20 = r0
            r0 = r17
            int r0 = (int) r0
            r22 = r0
            r0 = r20
            r1 = r22
            android.view.View r13 = r3.findTopChildUnder(r0, r1)
            r23 = r13
            r13 = r13
            android.view.View r15 = r3.mCapturedView
            if (r13 == r15) goto L_0x0120
        L_0x011e:
            goto L_0x0026
        L_0x0120:
            r32 = r23
            r0 = r32
            r1 = r28
            boolean r33 = r3.tryCaptureViewForDrag(r0, r1)
            goto L_0x011e
        L_0x012b:
            float[] r13 = r3.mInitialMotionY
            r14 = 0
            if (r13 == r14) goto L_0x0147
            int r8 = r4.getPointerCount()
            r28 = r8
            r34 = 0
        L_0x0138:
            r0 = r34
            r1 = r28
            if (r0 < r1) goto L_0x0149
        L_0x013e:
            r62 = r4
            r0 = r62
            r3.saveLastMotion(r0)
            goto L_0x0026
        L_0x0147:
            goto L_0x0026
        L_0x0149:
            r0 = r34
            int r8 = r4.getPointerId(r0)
            r21 = r8
            boolean r35 = r3.isValidPointerForActionMove(r8)
            r8 = r35
            r12 = 0
            if (r8 == r12) goto L_0x01b3
            r0 = r34
            float r17 = r4.getX(r0)
            r36 = r17
            r0 = r34
            float r17 = r4.getY(r0)
            r37 = r17
            float[] r15 = r3.mInitialMotionX
            r38 = r15
            r39 = r38[r21]
            float r17 = r36 - r39
            r40 = r17
            float[] r15 = r3.mInitialMotionY
            r41 = r15
            r39 = r41[r21]
            float r17 = r37 - r39
            r42 = r17
            r0 = r36
            int r0 = (int) r0
            r20 = r0
            r0 = r37
            int r0 = (int) r0
            r22 = r0
            r0 = r20
            r1 = r22
            android.view.View r13 = r3.findTopChildUnder(r0, r1)
            r43 = r13
            r14 = 0
            if (r13 != r14) goto L_0x01b4
        L_0x0195:
            r8 = 0
        L_0x0196:
            r46 = r8
            r12 = 0
            r0 = r46
            if (r0 != r12) goto L_0x01c8
        L_0x019d:
            r0 = r40
            r1 = r17
            r2 = r21
            r3.reportNewEdgeDrags(r0, r1, r2)
            int r8 = r3.mDragState
            r12 = 1
            if (r8 == r12) goto L_0x0250
            r12 = 0
            r0 = r46
            if (r0 != r12) goto L_0x0252
        L_0x01b0:
            int r34 = r34 + 1
            goto L_0x0138
        L_0x01b3:
            goto L_0x01b0
        L_0x01b4:
            r44 = r43
            r0 = r44
            r1 = r40
            r2 = r17
            boolean r45 = r3.checkTouchSlop(r0, r1, r2)
            r8 = r45
            r12 = 0
            if (r8 != r12) goto L_0x01c6
            goto L_0x0195
        L_0x01c6:
            r8 = 1
            goto L_0x0196
        L_0x01c8:
            int r8 = r43.getLeft()
            r47 = r8
            r0 = r40
            int r0 = (int) r0
            r20 = r0
            int r8 = r8 + r20
            r48 = r8
            android.support.v4.widget.ViewDragHelper$Callback r13 = r3.mCallback
            r0 = r40
            int r0 = (int) r0
            r49 = r0
            r50 = r43
            r0 = r50
            r1 = r49
            int r8 = r13.clampViewPositionHorizontal(r0, r8, r1)
            r51 = r8
            int r8 = r43.getTop()
            r52 = r8
            r0 = r17
            int r0 = (int) r0
            r20 = r0
            int r8 = r8 + r20
            r53 = r8
            android.support.v4.widget.ViewDragHelper$Callback r13 = r3.mCallback
            r0 = r17
            int r0 = (int) r0
            r49 = r0
            r54 = r43
            r0 = r54
            r1 = r49
            int r8 = r13.clampViewPositionVertical(r0, r8, r1)
            r55 = r8
            android.support.v4.widget.ViewDragHelper$Callback r13 = r3.mCallback
            r56 = r43
            r0 = r56
            int r8 = r13.getViewHorizontalDragRange(r0)
            r57 = r8
            android.support.v4.widget.ViewDragHelper$Callback r13 = r3.mCallback
            r58 = r43
            r0 = r58
            int r8 = r13.getViewVerticalDragRange(r0)
            r59 = r8
            r12 = 0
            r0 = r57
            if (r0 != r12) goto L_0x0230
        L_0x0229:
            r12 = 0
            r0 = r59
            if (r0 != r12) goto L_0x023f
            goto L_0x013e
        L_0x0230:
            r12 = 0
            r0 = r57
            if (r0 > r12) goto L_0x0237
            goto L_0x019d
        L_0x0237:
            r0 = r51
            r1 = r47
            if (r0 == r1) goto L_0x0229
            goto L_0x019d
        L_0x023f:
            r12 = 0
            r0 = r59
            if (r0 > r12) goto L_0x0246
            goto L_0x019d
        L_0x0246:
            r0 = r55
            r1 = r52
            if (r0 == r1) goto L_0x024e
            goto L_0x019d
        L_0x024e:
            goto L_0x013e
        L_0x0250:
            goto L_0x013e
        L_0x0252:
            r60 = r43
            r0 = r60
            r1 = r21
            boolean r61 = r3.tryCaptureViewForDrag(r0, r1)
            r8 = r61
            r12 = 0
            if (r8 != r12) goto L_0x0263
            goto L_0x01b0
        L_0x0263:
            goto L_0x013e
        L_0x0265:
            r8 = 1
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.widget.ViewDragHelper.shouldInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public void processTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int action = MotionEventCompat.getActionMasked(ev);
        int actionIndex = MotionEventCompat.getActionIndex(ev);
        if (action == 0) {
            cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        switch (action) {
            case 0:
                float x = ev.getX();
                float y = ev.getY();
                float f = y;
                int pointerId = ev.getPointerId(0);
                int pointerId2 = pointerId;
                View toCapture = findTopChildUnder((int) x, (int) y);
                saveInitialMotion(x, y, pointerId);
                boolean tryCaptureViewForDrag = tryCaptureViewForDrag(toCapture, pointerId);
                int i = this.mInitialEdgesTouched[pointerId2];
                int edgesTouched = i;
                if ((i & this.mTrackingEdges) != 0) {
                    this.mCallback.onEdgeTouched(edgesTouched & this.mTrackingEdges, pointerId2);
                    return;
                }
                return;
            case 1:
                if (this.mDragState == 1) {
                    releaseViewForPointerUp();
                }
                cancel();
                return;
            case 2:
                if (this.mDragState != 1) {
                    int pointerCount = ev.getPointerCount();
                    for (int i2 = 0; i2 < pointerCount; i2++) {
                        int pointerId3 = ev.getPointerId(i2);
                        int pointerId4 = pointerId3;
                        if (isValidPointerForActionMove(pointerId3)) {
                            float x2 = ev.getX(i2);
                            float y2 = ev.getY(i2);
                            float dx = x2 - this.mInitialMotionX[pointerId4];
                            float f2 = y2 - this.mInitialMotionY[pointerId4];
                            float f3 = f2;
                            reportNewEdgeDrags(dx, f2, pointerId4);
                            if (this.mDragState != 1) {
                                View toCapture2 = findTopChildUnder((int) x2, (int) y2);
                                if (checkTouchSlop(toCapture2, dx, f2) && tryCaptureViewForDrag(toCapture2, pointerId4)) {
                                }
                            }
                            saveLastMotion(ev);
                            return;
                        }
                    }
                    saveLastMotion(ev);
                    return;
                } else if (isValidPointerForActionMove(this.mActivePointerId)) {
                    int findPointerIndex = ev.findPointerIndex(this.mActivePointerId);
                    int i3 = findPointerIndex;
                    float x3 = ev.getX(findPointerIndex);
                    float y3 = ev.getY(findPointerIndex);
                    int idx = (int) (x3 - this.mLastMotionX[this.mActivePointerId]);
                    int i4 = (int) (y3 - this.mLastMotionY[this.mActivePointerId]);
                    int i5 = i4;
                    dragTo(this.mCapturedView.getLeft() + idx, this.mCapturedView.getTop() + i4, idx, i4);
                    saveLastMotion(ev);
                    return;
                } else {
                    return;
                }
            case 3:
                if (this.mDragState == 1) {
                    dispatchViewReleased(0.0f, 0.0f);
                }
                cancel();
                return;
            case 5:
                int pointerId5 = ev.getPointerId(actionIndex);
                int pointerId6 = pointerId5;
                float x4 = ev.getX(actionIndex);
                float y4 = ev.getY(actionIndex);
                float f4 = y4;
                saveInitialMotion(x4, y4, pointerId5);
                if (this.mDragState != 0) {
                    if (isCapturedViewUnder((int) x4, (int) y4)) {
                        boolean tryCaptureViewForDrag2 = tryCaptureViewForDrag(this.mCapturedView, pointerId6);
                        return;
                    }
                    return;
                }
                boolean tryCaptureViewForDrag3 = tryCaptureViewForDrag(findTopChildUnder((int) x4, (int) y4), pointerId6);
                int i6 = this.mInitialEdgesTouched[pointerId6];
                int edgesTouched2 = i6;
                if ((i6 & this.mTrackingEdges) != 0) {
                    this.mCallback.onEdgeTouched(edgesTouched2 & this.mTrackingEdges, pointerId6);
                    return;
                }
                return;
            case 6:
                int pointerId7 = ev.getPointerId(actionIndex);
                if (this.mDragState == 1 && pointerId7 == this.mActivePointerId) {
                    int newActivePointer = -1;
                    int pointerCount2 = ev.getPointerCount();
                    int i7 = 0;
                    while (true) {
                        if (i7 < pointerCount2) {
                            int pointerId8 = ev.getPointerId(i7);
                            int id = pointerId8;
                            if (pointerId8 != this.mActivePointerId) {
                                float x5 = ev.getX(i7);
                                float y5 = ev.getY(i7);
                                float f5 = y5;
                                if (findTopChildUnder((int) x5, (int) y5) == this.mCapturedView && tryCaptureViewForDrag(this.mCapturedView, id)) {
                                    newActivePointer = this.mActivePointerId;
                                }
                            }
                            i7++;
                        }
                    }
                    if (newActivePointer == -1) {
                        releaseViewForPointerUp();
                    }
                }
                clearMotionHistory(pointerId7);
                return;
            default:
                return;
        }
    }

    private void reportNewEdgeDrags(float f, float f2, int i) {
        float dx = f;
        float dy = f2;
        int pointerId = i;
        float f3 = dx;
        float f4 = dy;
        int i2 = pointerId;
        int dragsStarted = 0;
        if (checkNewEdgeDrag(dx, dy, pointerId, 1)) {
            dragsStarted = 1;
        }
        if (checkNewEdgeDrag(dy, dx, pointerId, 4)) {
            dragsStarted |= 4;
        }
        if (checkNewEdgeDrag(dx, dy, pointerId, 2)) {
            dragsStarted |= 2;
        }
        if (checkNewEdgeDrag(dy, dx, pointerId, 8)) {
            dragsStarted |= 8;
        }
        if (dragsStarted != 0) {
            int[] iArr = this.mEdgeDragsInProgress;
            iArr[pointerId] = iArr[pointerId] | dragsStarted;
            this.mCallback.onEdgeDragStarted(dragsStarted, pointerId);
        }
    }

    private boolean checkNewEdgeDrag(float f, float f2, int i, int i2) {
        boolean z;
        float delta = f;
        float odelta = f2;
        int pointerId = i;
        int edge = i2;
        float f3 = delta;
        float f4 = odelta;
        int i3 = pointerId;
        int i4 = edge;
        float absDelta = Math.abs(delta);
        float absODelta = Math.abs(odelta);
        if ((this.mInitialEdgesTouched[pointerId] & edge) != edge || (this.mTrackingEdges & edge) == 0 || (this.mEdgeDragsLocked[pointerId] & edge) == edge || (this.mEdgeDragsInProgress[pointerId] & edge) == edge || (absDelta <= ((float) this.mTouchSlop) && absODelta <= ((float) this.mTouchSlop))) {
            return false;
        }
        if (absDelta >= absODelta * 0.5f || !this.mCallback.onEdgeLock(edge)) {
            if ((this.mEdgeDragsInProgress[pointerId] & edge) == 0 && absDelta > ((float) this.mTouchSlop)) {
                z = true;
            } else {
                z = false;
            }
            return z;
        }
        int[] iArr = this.mEdgeDragsLocked;
        iArr[pointerId] = iArr[pointerId] | edge;
        return false;
    }

    private boolean checkTouchSlop(View view, float f, float f2) {
        View child = view;
        float dx = f;
        float dy = f2;
        View view2 = child;
        float f3 = dx;
        float f4 = dy;
        if (child == null) {
            return false;
        }
        boolean checkHorizontal = this.mCallback.getViewHorizontalDragRange(child) > 0;
        boolean checkVertical = this.mCallback.getViewVerticalDragRange(child) > 0;
        if (checkHorizontal && checkVertical) {
            return (dx * dx) + (dy * dy) > ((float) (this.mTouchSlop * this.mTouchSlop));
        } else if (checkHorizontal) {
            return Math.abs(dx) > ((float) this.mTouchSlop);
        } else if (!checkVertical) {
            return false;
        } else {
            return Math.abs(dy) > ((float) this.mTouchSlop);
        }
    }

    public boolean checkTouchSlop(int i) {
        int directions = i;
        int i2 = directions;
        int count = this.mInitialMotionX.length;
        for (int i3 = 0; i3 < count; i3++) {
            if (checkTouchSlop(directions, i3)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTouchSlop(int i, int i2) {
        int directions = i;
        int pointerId = i2;
        int i3 = directions;
        int i4 = pointerId;
        if (!isPointerDown(pointerId)) {
            return false;
        }
        boolean checkHorizontal = (directions & 1) == 1;
        boolean checkVertical = (directions & 2) == 2;
        float dx = this.mLastMotionX[pointerId] - this.mInitialMotionX[pointerId];
        float f = this.mLastMotionY[pointerId] - this.mInitialMotionY[pointerId];
        float dy = f;
        if (checkHorizontal && checkVertical) {
            return (dx * dx) + (dy * dy) > ((float) (this.mTouchSlop * this.mTouchSlop));
        } else if (checkHorizontal) {
            return Math.abs(dx) > ((float) this.mTouchSlop);
        } else if (!checkVertical) {
            return false;
        } else {
            return Math.abs(f) > ((float) this.mTouchSlop);
        }
    }

    public boolean isEdgeTouched(int i) {
        int edges = i;
        int i2 = edges;
        int count = this.mInitialEdgesTouched.length;
        for (int i3 = 0; i3 < count; i3++) {
            if (isEdgeTouched(edges, i3)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEdgeTouched(int i, int i2) {
        int edges = i;
        int pointerId = i2;
        int i3 = edges;
        int i4 = pointerId;
        return isPointerDown(pointerId) && (this.mInitialEdgesTouched[pointerId] & edges) != 0;
    }

    private void releaseViewForPointerUp() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
        float xvel = clampMag(VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity);
        float clampMag = clampMag(VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity);
        float f = clampMag;
        dispatchViewReleased(xvel, clampMag);
    }

    private void dragTo(int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int dx = i3;
        int dy = i4;
        int i5 = left;
        int i6 = top;
        int i7 = dx;
        int i8 = dy;
        int clampedX = left;
        int clampedY = top;
        int oldLeft = this.mCapturedView.getLeft();
        int oldTop = this.mCapturedView.getTop();
        if (dx != 0) {
            int clampViewPositionHorizontal = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, left, dx);
            clampedX = clampViewPositionHorizontal;
            ViewCompat.offsetLeftAndRight(this.mCapturedView, clampViewPositionHorizontal - oldLeft);
        }
        if (dy != 0) {
            int clampViewPositionVertical = this.mCallback.clampViewPositionVertical(this.mCapturedView, top, dy);
            clampedY = clampViewPositionVertical;
            ViewCompat.offsetTopAndBottom(this.mCapturedView, clampViewPositionVertical - oldTop);
        }
        if (dx != 0 || dy != 0) {
            int clampedDx = clampedX - oldLeft;
            int i9 = clampedY - oldTop;
            int i10 = i9;
            this.mCallback.onViewPositionChanged(this.mCapturedView, clampedX, clampedY, clampedDx, i9);
        }
    }

    public boolean isCapturedViewUnder(int i, int i2) {
        int x = i;
        int y = i2;
        int i3 = x;
        int i4 = y;
        return isViewUnder(this.mCapturedView, x, y);
    }

    public boolean isViewUnder(View view, int i, int i2) {
        View view2 = view;
        int x = i;
        int y = i2;
        View view3 = view2;
        int i3 = x;
        int i4 = y;
        if (view2 == null) {
            return false;
        }
        return x >= view2.getLeft() && x < view2.getRight() && y >= view2.getTop() && y < view2.getBottom();
    }

    public View findTopChildUnder(int i, int i2) {
        int x = i;
        int y = i2;
        int i3 = x;
        int i4 = y;
        int childCount = this.mParentView.getChildCount();
        int i5 = childCount;
        for (int i6 = childCount - 1; i6 >= 0; i6--) {
            View childAt = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(i6));
            View view = childAt;
            if (x >= childAt.getLeft() && x < childAt.getRight() && y >= childAt.getTop() && y < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    private int getEdgesTouched(int i, int i2) {
        int x = i;
        int y = i2;
        int i3 = x;
        int i4 = y;
        int result = 0;
        if (x < this.mParentView.getLeft() + this.mEdgeSize) {
            result = 1;
        }
        if (y < this.mParentView.getTop() + this.mEdgeSize) {
            result |= 4;
        }
        if (x > this.mParentView.getRight() - this.mEdgeSize) {
            result |= 2;
        }
        if (y > this.mParentView.getBottom() - this.mEdgeSize) {
            result |= 8;
        }
        return result;
    }

    private boolean isValidPointerForActionMove(int i) {
        int pointerId = i;
        int i2 = pointerId;
        if (isPointerDown(pointerId)) {
            return true;
        }
        int e = Log.e(TAG, "Ignoring pointerId=" + pointerId + " because ACTION_DOWN was not received " + "for this pointer before ACTION_MOVE. It likely happened because " + " ViewDragHelper did not receive all the events in the event stream.");
        return false;
    }
}
