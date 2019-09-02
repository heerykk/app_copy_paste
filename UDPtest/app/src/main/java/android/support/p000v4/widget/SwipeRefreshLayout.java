package android.support.p000v4.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.NestedScrollingChild;
import android.support.p000v4.view.NestedScrollingChildHelper;
import android.support.p000v4.view.NestedScrollingParent;
import android.support.p000v4.view.NestedScrollingParentHelper;
import android.support.p000v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;

/* renamed from: android.support.v4.widget.SwipeRefreshLayout */
public class SwipeRefreshLayout extends ViewGroup implements NestedScrollingParent, NestedScrollingChild {
    private static final int ALPHA_ANIMATION_DURATION = 300;
    private static final int ANIMATE_TO_START_DURATION = 200;
    private static final int ANIMATE_TO_TRIGGER_DURATION = 200;
    private static final int CIRCLE_BG_LIGHT = -328966;
    @VisibleForTesting
    static final int CIRCLE_DIAMETER = 40;
    @VisibleForTesting
    static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0f;
    public static final int DEFAULT = 1;
    private static final int DEFAULT_CIRCLE_TARGET = 64;
    private static final float DRAG_RATE = 0.5f;
    private static final int INVALID_POINTER = -1;
    public static final int LARGE = 0;
    private static final int[] LAYOUT_ATTRS;
    private static final String LOG_TAG = SwipeRefreshLayout.class.getSimpleName();
    private static final int MAX_ALPHA = 255;
    private static final float MAX_PROGRESS_ANGLE = 0.8f;
    private static final int SCALE_DOWN_DURATION = 150;
    private static final int STARTING_PROGRESS_ALPHA = 76;
    private int mActivePointerId;
    private Animation mAlphaMaxAnimation;
    private Animation mAlphaStartAnimation;
    private final Animation mAnimateToCorrectPosition;
    private final Animation mAnimateToStartPosition;
    private OnChildScrollUpCallback mChildScrollUpCallback;
    private int mCircleDiameter;
    CircleImageView mCircleView;
    private int mCircleViewIndex;
    int mCurrentTargetOffsetTop;
    private final DecelerateInterpolator mDecelerateInterpolator;
    protected int mFrom;
    private float mInitialDownY;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    OnRefreshListener mListener;
    private int mMediumAnimationDuration;
    private boolean mNestedScrollInProgress;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    boolean mNotify;
    protected int mOriginalOffsetTop;
    private final int[] mParentOffsetInWindow;
    private final int[] mParentScrollConsumed;
    MaterialProgressDrawable mProgress;
    private AnimationListener mRefreshListener;
    boolean mRefreshing;
    private boolean mReturningToStart;
    boolean mScale;
    private Animation mScaleAnimation;
    private Animation mScaleDownAnimation;
    private Animation mScaleDownToStartAnimation;
    int mSpinnerOffsetEnd;
    float mStartingScale;
    private View mTarget;
    private float mTotalDragDistance;
    private float mTotalUnconsumed;
    private int mTouchSlop;
    boolean mUsingCustomStart;

    /* renamed from: android.support.v4.widget.SwipeRefreshLayout$OnChildScrollUpCallback */
    public interface OnChildScrollUpCallback {
        boolean canChildScrollUp(SwipeRefreshLayout swipeRefreshLayout, @Nullable View view);
    }

    /* renamed from: android.support.v4.widget.SwipeRefreshLayout$OnRefreshListener */
    public interface OnRefreshListener {
        void onRefresh();
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 16842766;
        LAYOUT_ATTRS = iArr;
    }

    public SwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        this.mRefreshing = false;
        this.mTotalDragDistance = -1.0f;
        this.mParentScrollConsumed = new int[2];
        this.mParentOffsetInWindow = new int[2];
        this.mActivePointerId = -1;
        this.mCircleViewIndex = -1;
        this.mRefreshListener = new AnimationListener(this) {
            final /* synthetic */ SwipeRefreshLayout this$0;

            {
                SwipeRefreshLayout this$02 = r5;
                SwipeRefreshLayout swipeRefreshLayout = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationStart(Animation animation) {
                Animation animation2 = animation;
            }

            public void onAnimationRepeat(Animation animation) {
                Animation animation2 = animation;
            }

            @SuppressLint({"NewApi"})
            public void onAnimationEnd(Animation animation) {
                Animation animation2 = animation;
                if (!this.this$0.mRefreshing) {
                    this.this$0.reset();
                    return;
                }
                this.this$0.mProgress.setAlpha(255);
                this.this$0.mProgress.start();
                if (this.this$0.mNotify && this.this$0.mListener != null) {
                    this.this$0.mListener.onRefresh();
                }
                this.this$0.mCurrentTargetOffsetTop = this.this$0.mCircleView.getTop();
            }
        };
        this.mAnimateToCorrectPosition = new Animation(this) {
            final /* synthetic */ SwipeRefreshLayout this$0;

            {
                SwipeRefreshLayout this$02 = r5;
                SwipeRefreshLayout swipeRefreshLayout = this$02;
                this.this$0 = this$02;
            }

            public void applyTransformation(float f, Transformation transformation) {
                int endTarget;
                float interpolatedTime = f;
                float f2 = interpolatedTime;
                Transformation transformation2 = transformation;
                if (this.this$0.mUsingCustomStart) {
                    endTarget = this.this$0.mSpinnerOffsetEnd;
                } else {
                    endTarget = this.this$0.mSpinnerOffsetEnd - Math.abs(this.this$0.mOriginalOffsetTop);
                }
                int i = this.this$0.mFrom + ((int) (((float) (endTarget - this.this$0.mFrom)) * interpolatedTime));
                int targetTop = i;
                int top = i - this.this$0.mCircleView.getTop();
                int i2 = top;
                this.this$0.setTargetOffsetTopAndBottom(top, false);
                this.this$0.mProgress.setArrowScale(1.0f - interpolatedTime);
            }
        };
        this.mAnimateToStartPosition = new Animation(this) {
            final /* synthetic */ SwipeRefreshLayout this$0;

            {
                SwipeRefreshLayout this$02 = r5;
                SwipeRefreshLayout swipeRefreshLayout = this$02;
                this.this$0 = this$02;
            }

            public void applyTransformation(float f, Transformation transformation) {
                float interpolatedTime = f;
                float f2 = interpolatedTime;
                Transformation transformation2 = transformation;
                this.this$0.moveToStart(interpolatedTime);
            }
        };
        this.mTouchSlop = ViewConfiguration.get(context2).getScaledTouchSlop();
        this.mMediumAnimationDuration = getResources().getInteger(17694721);
        setWillNotDraw(false);
        this.mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        this.mCircleDiameter = (int) (40.0f * metrics.density);
        createProgressView();
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        this.mSpinnerOffsetEnd = (int) (64.0f * metrics.density);
        this.mTotalDragDistance = (float) this.mSpinnerOffsetEnd;
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        this.mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        int i = -this.mCircleDiameter;
        this.mCurrentTargetOffsetTop = i;
        this.mOriginalOffsetTop = i;
        moveToStart(1.0f);
        TypedArray a = context2.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
        setEnabled(a.getBoolean(0, true));
        a.recycle();
    }

    /* access modifiers changed from: 0000 */
    public void reset() {
        this.mCircleView.clearAnimation();
        this.mProgress.stop();
        this.mCircleView.setVisibility(8);
        setColorViewAlpha(255);
        if (!this.mScale) {
            setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCurrentTargetOffsetTop, true);
        } else {
            setAnimationProgress(0.0f);
        }
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
    }

    public void setEnabled(boolean z) {
        boolean enabled = z;
        super.setEnabled(enabled);
        if (!enabled) {
            reset();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    @SuppressLint({"NewApi"})
    private void setColorViewAlpha(int i) {
        int targetAlpha = i;
        int i2 = targetAlpha;
        this.mCircleView.getBackground().setAlpha(targetAlpha);
        this.mProgress.setAlpha(targetAlpha);
    }

    public void setProgressViewOffset(boolean z, int i, int i2) {
        int start = i;
        int end = i2;
        int i3 = start;
        int i4 = end;
        this.mScale = z;
        this.mOriginalOffsetTop = start;
        this.mSpinnerOffsetEnd = end;
        this.mUsingCustomStart = true;
        reset();
        this.mRefreshing = false;
    }

    public int getProgressViewStartOffset() {
        return this.mOriginalOffsetTop;
    }

    public int getProgressViewEndOffset() {
        return this.mSpinnerOffsetEnd;
    }

    public void setProgressViewEndTarget(boolean z, int i) {
        int end = i;
        boolean scale = z;
        int i2 = end;
        this.mSpinnerOffsetEnd = end;
        this.mScale = scale;
        this.mCircleView.invalidate();
    }

    public void setSize(int i) {
        int size = i;
        int i2 = size;
        if (size == 0 || size == 1) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            if (size != 0) {
                this.mCircleDiameter = (int) (40.0f * metrics.density);
            } else {
                this.mCircleDiameter = (int) (56.0f * metrics.density);
            }
            this.mCircleView.setImageDrawable(null);
            this.mProgress.updateSizes(size);
            this.mCircleView.setImageDrawable(this.mProgress);
        }
    }

    public SwipeRefreshLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i, int i2) {
        int childCount = i;
        int i3 = i2;
        int i4 = childCount;
        int i5 = i3;
        if (this.mCircleViewIndex < 0) {
            return i3;
        }
        if (i3 == childCount - 1) {
            return this.mCircleViewIndex;
        }
        if (i3 < this.mCircleViewIndex) {
            return i3;
        }
        return i3 + 1;
    }

    private void createProgressView() {
        this.mCircleView = new CircleImageView(getContext(), CIRCLE_BG_LIGHT);
        this.mProgress = new MaterialProgressDrawable(getContext(), this);
        this.mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        this.mCircleView.setImageDrawable(this.mProgress);
        this.mCircleView.setVisibility(8);
        addView(this.mCircleView);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        OnRefreshListener listener = onRefreshListener;
        OnRefreshListener onRefreshListener2 = listener;
        this.mListener = listener;
    }

    private boolean isAlphaUsedForScale() {
        return VERSION.SDK_INT < 11;
    }

    public void setRefreshing(boolean z) {
        int endTarget;
        boolean refreshing = z;
        if (refreshing && this.mRefreshing != refreshing) {
            this.mRefreshing = refreshing;
            if (this.mUsingCustomStart) {
                endTarget = this.mSpinnerOffsetEnd;
            } else {
                endTarget = this.mSpinnerOffsetEnd + this.mOriginalOffsetTop;
            }
            setTargetOffsetTopAndBottom(endTarget - this.mCurrentTargetOffsetTop, true);
            this.mNotify = false;
            startScaleUpAnimation(this.mRefreshListener);
            return;
        }
        setRefreshing(refreshing, false);
    }

    @SuppressLint({"NewApi"})
    private void startScaleUpAnimation(AnimationListener animationListener) {
        AnimationListener listener = animationListener;
        AnimationListener animationListener2 = listener;
        this.mCircleView.setVisibility(0);
        if (VERSION.SDK_INT >= 11) {
            this.mProgress.setAlpha(255);
        }
        this.mScaleAnimation = new Animation(this) {
            final /* synthetic */ SwipeRefreshLayout this$0;

            {
                SwipeRefreshLayout this$02 = r5;
                SwipeRefreshLayout swipeRefreshLayout = this$02;
                this.this$0 = this$02;
            }

            public void applyTransformation(float f, Transformation transformation) {
                float interpolatedTime = f;
                float f2 = interpolatedTime;
                Transformation transformation2 = transformation;
                this.this$0.setAnimationProgress(interpolatedTime);
            }
        };
        this.mScaleAnimation.setDuration((long) this.mMediumAnimationDuration);
        if (listener != null) {
            this.mCircleView.setAnimationListener(listener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleAnimation);
    }

    /* access modifiers changed from: 0000 */
    public void setAnimationProgress(float f) {
        float progress = f;
        float f2 = progress;
        if (!isAlphaUsedForScale()) {
            ViewCompat.setScaleX(this.mCircleView, progress);
            ViewCompat.setScaleY(this.mCircleView, progress);
            return;
        }
        setColorViewAlpha((int) (progress * 255.0f));
    }

    private void setRefreshing(boolean z, boolean z2) {
        boolean refreshing = z;
        boolean notify = z2;
        if (this.mRefreshing != refreshing) {
            this.mNotify = notify;
            ensureTarget();
            this.mRefreshing = refreshing;
            if (!this.mRefreshing) {
                startScaleDownAnimation(this.mRefreshListener);
            } else {
                animateOffsetToCorrectPosition(this.mCurrentTargetOffsetTop, this.mRefreshListener);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void startScaleDownAnimation(AnimationListener animationListener) {
        AnimationListener listener = animationListener;
        AnimationListener animationListener2 = listener;
        this.mScaleDownAnimation = new Animation(this) {
            final /* synthetic */ SwipeRefreshLayout this$0;

            {
                SwipeRefreshLayout this$02 = r5;
                SwipeRefreshLayout swipeRefreshLayout = this$02;
                this.this$0 = this$02;
            }

            public void applyTransformation(float f, Transformation transformation) {
                float interpolatedTime = f;
                float f2 = interpolatedTime;
                Transformation transformation2 = transformation;
                this.this$0.setAnimationProgress(1.0f - interpolatedTime);
            }
        };
        this.mScaleDownAnimation.setDuration(150);
        this.mCircleView.setAnimationListener(listener);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownAnimation);
    }

    @SuppressLint({"NewApi"})
    private void startProgressAlphaStartAnimation() {
        this.mAlphaStartAnimation = startAlphaAnimation(this.mProgress.getAlpha(), 76);
    }

    @SuppressLint({"NewApi"})
    private void startProgressAlphaMaxAnimation() {
        this.mAlphaMaxAnimation = startAlphaAnimation(this.mProgress.getAlpha(), 255);
    }

    @SuppressLint({"NewApi"})
    private Animation startAlphaAnimation(int i, int i2) {
        final int startingAlpha = i;
        final int endingAlpha = i2;
        int i3 = startingAlpha;
        int i4 = endingAlpha;
        if (this.mScale && isAlphaUsedForScale()) {
            return null;
        }
        C02344 r13 = new Animation(this) {
            final /* synthetic */ SwipeRefreshLayout this$0;

            {
                SwipeRefreshLayout this$02 = r7;
                int i = r8;
                int i2 = r9;
                SwipeRefreshLayout swipeRefreshLayout = this$02;
                this.this$0 = this$02;
            }

            public void applyTransformation(float f, Transformation transformation) {
                float interpolatedTime = f;
                float f2 = interpolatedTime;
                Transformation transformation2 = transformation;
                this.this$0.mProgress.setAlpha((int) (((float) startingAlpha) + (((float) (endingAlpha - startingAlpha)) * interpolatedTime)));
            }
        };
        C02344 r17 = r13;
        r13.setDuration(300);
        this.mCircleView.setAnimationListener(null);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(r17);
        return r17;
    }

    @Deprecated
    public void setProgressBackgroundColor(int i) {
        int colorRes = i;
        int i2 = colorRes;
        setProgressBackgroundColorSchemeResource(colorRes);
    }

    public void setProgressBackgroundColorSchemeResource(@ColorRes int i) {
        int colorRes = i;
        int i2 = colorRes;
        setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), colorRes));
    }

    public void setProgressBackgroundColorSchemeColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        this.mCircleView.setBackgroundColor(color);
        this.mProgress.setBackgroundColor(color);
    }

    @Deprecated
    public void setColorScheme(@ColorInt int... iArr) {
        int[] colors = iArr;
        int[] iArr2 = colors;
        setColorSchemeResources(colors);
    }

    public void setColorSchemeResources(@ColorRes int... iArr) {
        int[] colorResIds = iArr;
        int[] iArr2 = colorResIds;
        Context context = getContext();
        int[] colorRes = new int[colorResIds.length];
        for (int i = 0; i < colorResIds.length; i++) {
            int[] iArr3 = colorRes;
            iArr3[i] = ContextCompat.getColor(context, colorResIds[i]);
        }
        setColorSchemeColors(colorRes);
    }

    public void setColorSchemeColors(@ColorInt int... iArr) {
        int[] colors = iArr;
        int[] iArr2 = colors;
        ensureTarget();
        this.mProgress.setColorSchemeColors(colors);
    }

    public boolean isRefreshing() {
        return this.mRefreshing;
    }

    private void ensureTarget() {
        if (this.mTarget == null) {
            int i = 0;
            while (i < getChildCount()) {
                View childAt = getChildAt(i);
                View child = childAt;
                if (childAt.equals(this.mCircleView)) {
                    i++;
                } else {
                    this.mTarget = child;
                    return;
                }
            }
        }
    }

    public void setDistanceToTriggerSync(int i) {
        int distance = i;
        int i2 = distance;
        this.mTotalDragDistance = (float) distance;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = z;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        int i8 = i4;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (getChildCount() != 0) {
            if (this.mTarget == null) {
                ensureTarget();
            }
            if (this.mTarget != null) {
                View child = this.mTarget;
                int childLeft = getPaddingLeft();
                int childTop = getPaddingTop();
                int paddingTop = (height - getPaddingTop()) - getPaddingBottom();
                int i9 = paddingTop;
                child.layout(childLeft, childTop, childLeft + ((width - getPaddingLeft()) - getPaddingRight()), childTop + paddingTop);
                int circleWidth = this.mCircleView.getMeasuredWidth();
                int measuredHeight = this.mCircleView.getMeasuredHeight();
                int i10 = measuredHeight;
                this.mCircleView.layout((width / 2) - (circleWidth / 2), this.mCurrentTargetOffsetTop, (width / 2) + (circleWidth / 2), this.mCurrentTargetOffsetTop + measuredHeight);
            }
        }
    }

    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mTarget == null) {
            ensureTarget();
        }
        if (this.mTarget != null) {
            this.mTarget.measure(MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
            this.mCircleView.measure(MeasureSpec.makeMeasureSpec(this.mCircleDiameter, 1073741824), MeasureSpec.makeMeasureSpec(this.mCircleDiameter, 1073741824));
            this.mCircleViewIndex = -1;
            int index = 0;
            while (true) {
                if (index < getChildCount()) {
                    if (getChildAt(index) == this.mCircleView) {
                        this.mCircleViewIndex = index;
                        break;
                    }
                    index++;
                } else {
                    break;
                }
            }
        }
    }

    public int getProgressCircleDiameter() {
        return this.mCircleDiameter;
    }

    public boolean canChildScrollUp() {
        boolean z;
        if (this.mChildScrollUpCallback != null) {
            return this.mChildScrollUpCallback.canChildScrollUp(this, this.mTarget);
        }
        if (VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTarget, -1);
        }
        if (!(this.mTarget instanceof AbsListView)) {
            if (!ViewCompat.canScrollVertically(this.mTarget, -1) && this.mTarget.getScrollY() <= 0) {
                z = false;
            } else {
                z = true;
            }
            return z;
        }
        AbsListView absListView = (AbsListView) this.mTarget;
        AbsListView absListView2 = absListView;
        return absListView.getChildCount() > 0 && (absListView2.getFirstVisiblePosition() > 0 || absListView2.getChildAt(0).getTop() < absListView2.getPaddingTop());
    }

    public void setOnChildScrollUpCallback(@Nullable OnChildScrollUpCallback onChildScrollUpCallback) {
        OnChildScrollUpCallback callback = onChildScrollUpCallback;
        OnChildScrollUpCallback onChildScrollUpCallback2 = callback;
        this.mChildScrollUpCallback = callback;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        ensureTarget();
        int action = MotionEventCompat.getActionMasked(ev);
        if (this.mReturningToStart && action == 0) {
            this.mReturningToStart = false;
        }
        if (!isEnabled() || this.mReturningToStart || canChildScrollUp() || this.mRefreshing || this.mNestedScrollInProgress) {
            return false;
        }
        switch (action) {
            case 0:
                setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCircleView.getTop(), true);
                this.mActivePointerId = ev.getPointerId(0);
                this.mIsBeingDragged = false;
                int findPointerIndex = ev.findPointerIndex(this.mActivePointerId);
                int pointerIndex = findPointerIndex;
                if (findPointerIndex >= 0) {
                    this.mInitialDownY = ev.getY(pointerIndex);
                    break;
                } else {
                    return false;
                }
            case 1:
            case 3:
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                break;
            case 2:
                if (this.mActivePointerId != -1) {
                    int findPointerIndex2 = ev.findPointerIndex(this.mActivePointerId);
                    int pointerIndex2 = findPointerIndex2;
                    if (findPointerIndex2 >= 0) {
                        float y = ev.getY(pointerIndex2);
                        float f = y;
                        startDragging(y);
                        break;
                    } else {
                        return false;
                    }
                } else {
                    int e = Log.e(LOG_TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }
            case 6:
                onSecondaryPointerUp(ev);
                break;
        }
        return this.mIsBeingDragged;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        boolean b = z;
        if (VERSION.SDK_INT >= 21 || !(this.mTarget instanceof AbsListView)) {
            if (this.mTarget == null || ViewCompat.isNestedScrollingEnabled(this.mTarget)) {
                super.requestDisallowInterceptTouchEvent(b);
            }
        }
    }

    public boolean onStartNestedScroll(View view, View view2, int i) {
        int nestedScrollAxes = i;
        View view3 = view;
        View view4 = view2;
        int i2 = nestedScrollAxes;
        return isEnabled() && !this.mReturningToStart && !this.mRefreshing && (nestedScrollAxes & 2) != 0;
    }

    public void onNestedScrollAccepted(View view, View view2, int i) {
        View child = view;
        View target = view2;
        int axes = i;
        View view3 = child;
        View view4 = target;
        int i2 = axes;
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        boolean startNestedScroll = startNestedScroll(axes & 2);
        this.mTotalUnconsumed = 0.0f;
        this.mNestedScrollInProgress = true;
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        int dx = i;
        int dy = i2;
        int[] consumed = iArr;
        View view2 = view;
        int i3 = dx;
        int i4 = dy;
        int[] iArr2 = consumed;
        if (dy > 0 && this.mTotalUnconsumed > 0.0f) {
            if (((float) dy) > this.mTotalUnconsumed) {
                consumed[1] = dy - ((int) this.mTotalUnconsumed);
                this.mTotalUnconsumed = 0.0f;
            } else {
                this.mTotalUnconsumed -= (float) dy;
                consumed[1] = dy;
            }
            moveSpinner(this.mTotalUnconsumed);
        }
        if (this.mUsingCustomStart && dy > 0 && this.mTotalUnconsumed == 0.0f && Math.abs(dy - consumed[1]) > 0) {
            this.mCircleView.setVisibility(8);
        }
        int[] parentConsumed = this.mParentScrollConsumed;
        if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
            consumed[0] = consumed[0] + parentConsumed[0];
            consumed[1] = consumed[1] + parentConsumed[1];
        }
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    public void onStopNestedScroll(View view) {
        View target = view;
        View view2 = target;
        this.mNestedScrollingParentHelper.onStopNestedScroll(target);
        this.mNestedScrollInProgress = false;
        if (this.mTotalUnconsumed > 0.0f) {
            finishSpinner(this.mTotalUnconsumed);
            this.mTotalUnconsumed = 0.0f;
        }
        stopNestedScroll();
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        int dxConsumed = i;
        int dyConsumed = i2;
        int dxUnconsumed = i3;
        int dyUnconsumed = i4;
        View view2 = view;
        int i5 = dxConsumed;
        int i6 = dyConsumed;
        int i7 = dxUnconsumed;
        int i8 = dyUnconsumed;
        boolean dispatchNestedScroll = dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, this.mParentOffsetInWindow);
        int i9 = dyUnconsumed + this.mParentOffsetInWindow[1];
        int dy = i9;
        if (i9 < 0 && !canChildScrollUp()) {
            this.mTotalUnconsumed += (float) Math.abs(dy);
            moveSpinner(this.mTotalUnconsumed);
        }
    }

    public void setNestedScrollingEnabled(boolean z) {
        this.mNestedScrollingChildHelper.setNestedScrollingEnabled(z);
    }

    public boolean isNestedScrollingEnabled() {
        return this.mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int i) {
        int axes = i;
        int i2 = axes;
        return this.mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    public void stopNestedScroll() {
        this.mNestedScrollingChildHelper.stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingChildHelper.hasNestedScrollingParent();
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
        return this.mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
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
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public boolean onNestedPreFling(View view, float f, float f2) {
        float velocityX = f;
        float velocityY = f2;
        View view2 = view;
        float f3 = velocityX;
        float f4 = velocityY;
        return dispatchNestedPreFling(velocityX, velocityY);
    }

    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        float velocityX = f;
        float velocityY = f2;
        View view2 = view;
        float f3 = velocityX;
        float f4 = velocityY;
        return dispatchNestedFling(velocityX, velocityY, z);
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        float velocityX = f;
        float velocityY = f2;
        float f3 = velocityX;
        float f4 = velocityY;
        return this.mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, z);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        float velocityX = f;
        float velocityY = f2;
        float f3 = velocityX;
        float f4 = velocityY;
        return this.mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    private boolean isAnimationRunning(Animation animation) {
        Animation animation2 = animation;
        Animation animation3 = animation2;
        return animation2 != null && animation2.hasStarted() && !animation2.hasEnded();
    }

    @SuppressLint({"NewApi"})
    private void moveSpinner(float f) {
        float f2;
        float overscrollTop = f;
        float f3 = overscrollTop;
        this.mProgress.showArrow(true);
        float min = Math.min(1.0f, Math.abs(overscrollTop / this.mTotalDragDistance));
        float dragPercent = min;
        float adjustedPercent = (((float) Math.max(((double) min) - 0.4d, 0.0d)) * 5.0f) / 3.0f;
        float extraOS = Math.abs(overscrollTop) - this.mTotalDragDistance;
        if (!this.mUsingCustomStart) {
            f2 = (float) this.mSpinnerOffsetEnd;
        } else {
            f2 = (float) (this.mSpinnerOffsetEnd - this.mOriginalOffsetTop);
        }
        float slingshotDist = f2;
        float max = Math.max(0.0f, Math.min(extraOS, slingshotDist * DECELERATE_INTERPOLATION_FACTOR) / slingshotDist);
        float tensionPercent = ((float) (((double) (max / 4.0f)) - Math.pow((double) (max / 4.0f), 2.0d))) * DECELERATE_INTERPOLATION_FACTOR;
        float f4 = slingshotDist * tensionPercent * DECELERATE_INTERPOLATION_FACTOR;
        float f5 = f4;
        int targetY = this.mOriginalOffsetTop + ((int) ((slingshotDist * dragPercent) + f4));
        if (this.mCircleView.getVisibility() != 0) {
            this.mCircleView.setVisibility(0);
        }
        if (!this.mScale) {
            ViewCompat.setScaleX(this.mCircleView, 1.0f);
            ViewCompat.setScaleY(this.mCircleView, 1.0f);
        }
        if (this.mScale) {
            setAnimationProgress(Math.min(1.0f, overscrollTop / this.mTotalDragDistance));
        }
        if (overscrollTop < this.mTotalDragDistance) {
            if (this.mProgress.getAlpha() > 76 && !isAnimationRunning(this.mAlphaStartAnimation)) {
                startProgressAlphaStartAnimation();
            }
        } else if (this.mProgress.getAlpha() < 255 && !isAnimationRunning(this.mAlphaMaxAnimation)) {
            startProgressAlphaMaxAnimation();
        }
        float f6 = adjustedPercent * MAX_PROGRESS_ANGLE;
        float f7 = f6;
        this.mProgress.setStartEndTrim(0.0f, Math.min(MAX_PROGRESS_ANGLE, f6));
        this.mProgress.setArrowScale(Math.min(1.0f, adjustedPercent));
        float f8 = (-0.25f + (0.4f * adjustedPercent) + (tensionPercent * DECELERATE_INTERPOLATION_FACTOR)) * DRAG_RATE;
        float f9 = f8;
        this.mProgress.setProgressRotation(f8);
        setTargetOffsetTopAndBottom(targetY - this.mCurrentTargetOffsetTop, true);
    }

    private void finishSpinner(float f) {
        float overscrollTop = f;
        float f2 = overscrollTop;
        if (overscrollTop > this.mTotalDragDistance) {
            setRefreshing(true, true);
            return;
        }
        this.mRefreshing = false;
        this.mProgress.setStartEndTrim(0.0f, 0.0f);
        C02355 r13 = null;
        if (!this.mScale) {
            r13 = new AnimationListener(this) {
                final /* synthetic */ SwipeRefreshLayout this$0;

                {
                    SwipeRefreshLayout this$02 = r5;
                    SwipeRefreshLayout swipeRefreshLayout = this$02;
                    this.this$0 = this$02;
                }

                public void onAnimationStart(Animation animation) {
                    Animation animation2 = animation;
                }

                public void onAnimationEnd(Animation animation) {
                    Animation animation2 = animation;
                    if (!this.this$0.mScale) {
                        this.this$0.startScaleDownAnimation(null);
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                    Animation animation2 = animation;
                }
            };
        }
        animateOffsetToStartPosition(this.mCurrentTargetOffsetTop, r13);
        this.mProgress.showArrow(false);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int action = MotionEventCompat.getActionMasked(ev);
        if (this.mReturningToStart && action == 0) {
            this.mReturningToStart = false;
        }
        if (!isEnabled() || this.mReturningToStart || canChildScrollUp() || this.mRefreshing || this.mNestedScrollInProgress) {
            return false;
        }
        switch (action) {
            case 0:
                this.mActivePointerId = ev.getPointerId(0);
                this.mIsBeingDragged = false;
                break;
            case 1:
                int findPointerIndex = ev.findPointerIndex(this.mActivePointerId);
                int pointerIndex = findPointerIndex;
                if (findPointerIndex >= 0) {
                    if (this.mIsBeingDragged) {
                        float y = ev.getY(pointerIndex);
                        float f = y;
                        float f2 = (y - this.mInitialMotionY) * DRAG_RATE;
                        float f3 = f2;
                        this.mIsBeingDragged = false;
                        finishSpinner(f2);
                    }
                    this.mActivePointerId = -1;
                    return false;
                }
                int e = Log.e(LOG_TAG, "Got ACTION_UP event but don't have an active pointer id.");
                return false;
            case 2:
                int findPointerIndex2 = ev.findPointerIndex(this.mActivePointerId);
                int pointerIndex2 = findPointerIndex2;
                if (findPointerIndex2 >= 0) {
                    float y2 = ev.getY(pointerIndex2);
                    float f4 = y2;
                    startDragging(y2);
                    if (this.mIsBeingDragged) {
                        float f5 = (y2 - this.mInitialMotionY) * DRAG_RATE;
                        float overscrollTop = f5;
                        if (f5 > 0.0f) {
                            moveSpinner(overscrollTop);
                            break;
                        } else {
                            return false;
                        }
                    }
                } else {
                    int e2 = Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                break;
            case 3:
                return false;
            case 5:
                int actionIndex = MotionEventCompat.getActionIndex(ev);
                int pointerIndex3 = actionIndex;
                if (actionIndex >= 0) {
                    this.mActivePointerId = ev.getPointerId(pointerIndex3);
                    break;
                } else {
                    int e3 = Log.e(LOG_TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
            case 6:
                onSecondaryPointerUp(ev);
                break;
        }
        return true;
    }

    @SuppressLint({"NewApi"})
    private void startDragging(float f) {
        float y = f;
        float f2 = y;
        float f3 = y - this.mInitialDownY;
        float f4 = f3;
        if (f3 > ((float) this.mTouchSlop) && !this.mIsBeingDragged) {
            this.mInitialMotionY = this.mInitialDownY + ((float) this.mTouchSlop);
            this.mIsBeingDragged = true;
            this.mProgress.setAlpha(76);
        }
    }

    private void animateOffsetToCorrectPosition(int i, AnimationListener animationListener) {
        int from = i;
        AnimationListener listener = animationListener;
        int i2 = from;
        AnimationListener animationListener2 = listener;
        this.mFrom = from;
        this.mAnimateToCorrectPosition.reset();
        this.mAnimateToCorrectPosition.setDuration(200);
        this.mAnimateToCorrectPosition.setInterpolator(this.mDecelerateInterpolator);
        if (listener != null) {
            this.mCircleView.setAnimationListener(listener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToCorrectPosition);
    }

    private void animateOffsetToStartPosition(int i, AnimationListener animationListener) {
        int from = i;
        AnimationListener listener = animationListener;
        int i2 = from;
        AnimationListener animationListener2 = listener;
        if (!this.mScale) {
            this.mFrom = from;
            this.mAnimateToStartPosition.reset();
            this.mAnimateToStartPosition.setDuration(200);
            this.mAnimateToStartPosition.setInterpolator(this.mDecelerateInterpolator);
            if (listener != null) {
                this.mCircleView.setAnimationListener(listener);
            }
            this.mCircleView.clearAnimation();
            this.mCircleView.startAnimation(this.mAnimateToStartPosition);
            return;
        }
        startScaleDownReturnToStartAnimation(from, listener);
    }

    /* access modifiers changed from: 0000 */
    public void moveToStart(float f) {
        float interpolatedTime = f;
        float f2 = interpolatedTime;
        int i = this.mFrom + ((int) (((float) (this.mOriginalOffsetTop - this.mFrom)) * interpolatedTime));
        int targetTop = i;
        int top = i - this.mCircleView.getTop();
        int i2 = top;
        setTargetOffsetTopAndBottom(top, false);
    }

    @SuppressLint({"NewApi"})
    private void startScaleDownReturnToStartAnimation(int i, AnimationListener animationListener) {
        int from = i;
        AnimationListener listener = animationListener;
        int i2 = from;
        AnimationListener animationListener2 = listener;
        this.mFrom = from;
        if (!isAlphaUsedForScale()) {
            this.mStartingScale = ViewCompat.getScaleX(this.mCircleView);
        } else {
            this.mStartingScale = (float) this.mProgress.getAlpha();
        }
        this.mScaleDownToStartAnimation = new Animation(this) {
            final /* synthetic */ SwipeRefreshLayout this$0;

            {
                SwipeRefreshLayout this$02 = r5;
                SwipeRefreshLayout swipeRefreshLayout = this$02;
                this.this$0 = this$02;
            }

            public void applyTransformation(float f, Transformation transformation) {
                float interpolatedTime = f;
                float f2 = interpolatedTime;
                Transformation transformation2 = transformation;
                float f3 = this.this$0.mStartingScale + ((-this.this$0.mStartingScale) * interpolatedTime);
                float f4 = f3;
                this.this$0.setAnimationProgress(f3);
                this.this$0.moveToStart(interpolatedTime);
            }
        };
        this.mScaleDownToStartAnimation.setDuration(150);
        if (listener != null) {
            this.mCircleView.setAnimationListener(listener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownToStartAnimation);
    }

    /* access modifiers changed from: 0000 */
    public void setTargetOffsetTopAndBottom(int i, boolean z) {
        int offset = i;
        int i2 = offset;
        boolean requiresUpdate = z;
        this.mCircleView.bringToFront();
        ViewCompat.offsetTopAndBottom(this.mCircleView, offset);
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
        if (requiresUpdate && VERSION.SDK_INT < 11) {
            invalidate();
        }
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int actionIndex = MotionEventCompat.getActionIndex(ev);
        int pointerIndex = actionIndex;
        int pointerId = ev.getPointerId(actionIndex);
        int i = pointerId;
        if (pointerId == this.mActivePointerId) {
            this.mActivePointerId = ev.getPointerId(pointerIndex != 0 ? 0 : 1);
        }
    }
}
