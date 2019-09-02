package android.support.p003v7.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p003v7.widget.RecyclerView.Adapter;
import android.support.p003v7.widget.RecyclerView.LayoutManager;
import android.support.p003v7.widget.RecyclerView.OnFlingListener;
import android.support.p003v7.widget.RecyclerView.OnScrollListener;
import android.support.p003v7.widget.RecyclerView.SmoothScroller.Action;
import android.support.p003v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.support.p003v7.widget.RecyclerView.State;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

/* renamed from: android.support.v7.widget.SnapHelper */
public abstract class SnapHelper extends OnFlingListener {
    static final float MILLISECONDS_PER_INCH = 100.0f;
    private Scroller mGravityScroller;
    RecyclerView mRecyclerView;
    private final OnScrollListener mScrollListener = new OnScrollListener(this) {
        boolean mScrolled = false;
        final /* synthetic */ SnapHelper this$0;

        {
            SnapHelper this$02 = r6;
            SnapHelper snapHelper = this$02;
            this.this$0 = this$02;
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            RecyclerView recyclerView2 = recyclerView;
            int newState = i;
            RecyclerView recyclerView3 = recyclerView2;
            int i2 = newState;
            super.onScrollStateChanged(recyclerView2, newState);
            if (newState == 0 && this.mScrolled) {
                this.mScrolled = false;
                this.this$0.snapToTargetExistingView();
            }
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            int dx = i;
            int dy = i2;
            RecyclerView recyclerView2 = recyclerView;
            int i3 = dx;
            int i4 = dy;
            if (dx != 0 || dy != 0) {
                this.mScrolled = true;
            }
        }
    };

    @Nullable
    public abstract int[] calculateDistanceToFinalSnap(@NonNull LayoutManager layoutManager, @NonNull View view);

    @Nullable
    public abstract View findSnapView(LayoutManager layoutManager);

    public abstract int findTargetSnapPosition(LayoutManager layoutManager, int i, int i2);

    public SnapHelper() {
    }

    public boolean onFling(int i, int i2) {
        boolean z;
        int velocityX = i;
        int velocityY = i2;
        int i3 = velocityX;
        int i4 = velocityY;
        LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        LayoutManager layoutManager2 = layoutManager;
        if (layoutManager == null) {
            return false;
        }
        Adapter adapter = this.mRecyclerView.getAdapter();
        Adapter adapter2 = adapter;
        if (adapter == null) {
            return false;
        }
        int minFlingVelocity = this.mRecyclerView.getMinFlingVelocity();
        if ((Math.abs(velocityY) <= minFlingVelocity && Math.abs(velocityX) <= minFlingVelocity) || !snapFromFling(layoutManager2, velocityX, velocityY)) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        RecyclerView recyclerView2 = recyclerView;
        RecyclerView recyclerView3 = recyclerView2;
        if (this.mRecyclerView != recyclerView2) {
            if (this.mRecyclerView != null) {
                destroyCallbacks();
            }
            this.mRecyclerView = recyclerView2;
            if (this.mRecyclerView != null) {
                setupCallbacks();
                this.mGravityScroller = new Scroller(this.mRecyclerView.getContext(), new DecelerateInterpolator());
                snapToTargetExistingView();
            }
        }
    }

    private void setupCallbacks() throws IllegalStateException {
        if (this.mRecyclerView.getOnFlingListener() == null) {
            this.mRecyclerView.addOnScrollListener(this.mScrollListener);
            this.mRecyclerView.setOnFlingListener(this);
            return;
        }
        throw new IllegalStateException("An instance of OnFlingListener already set.");
    }

    private void destroyCallbacks() {
        this.mRecyclerView.removeOnScrollListener(this.mScrollListener);
        this.mRecyclerView.setOnFlingListener(null);
    }

    public int[] calculateScrollDistance(int i, int i2) {
        int velocityX = i;
        int velocityY = i2;
        int i3 = velocityX;
        int i4 = velocityY;
        int[] outDist = new int[2];
        this.mGravityScroller.fling(0, 0, velocityX, velocityY, Integer.MIN_VALUE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, Integer.MIN_VALUE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        outDist[0] = this.mGravityScroller.getFinalX();
        outDist[1] = this.mGravityScroller.getFinalY();
        return outDist;
    }

    private boolean snapFromFling(@NonNull LayoutManager layoutManager, int i, int i2) {
        LayoutManager layoutManager2 = layoutManager;
        int velocityX = i;
        int velocityY = i2;
        LayoutManager layoutManager3 = layoutManager2;
        int i3 = velocityX;
        int i4 = velocityY;
        if (!(layoutManager2 instanceof ScrollVectorProvider)) {
            return false;
        }
        LinearSmoothScroller createSnapScroller = createSnapScroller(layoutManager2);
        LinearSmoothScroller linearSmoothScroller = createSnapScroller;
        if (createSnapScroller == null) {
            return false;
        }
        int findTargetSnapPosition = findTargetSnapPosition(layoutManager2, velocityX, velocityY);
        int targetPosition = findTargetSnapPosition;
        if (findTargetSnapPosition == -1) {
            return false;
        }
        linearSmoothScroller.setTargetPosition(targetPosition);
        layoutManager2.startSmoothScroll(linearSmoothScroller);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void snapToTargetExistingView() {
        if (this.mRecyclerView != null) {
            LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
            LayoutManager layoutManager2 = layoutManager;
            if (layoutManager != null) {
                View findSnapView = findSnapView(layoutManager2);
                View snapView = findSnapView;
                if (findSnapView != null) {
                    int[] calculateDistanceToFinalSnap = calculateDistanceToFinalSnap(layoutManager2, snapView);
                    int[] snapDistance = calculateDistanceToFinalSnap;
                    if (!(calculateDistanceToFinalSnap[0] == 0 && snapDistance[1] == 0)) {
                        this.mRecyclerView.smoothScrollBy(snapDistance[0], snapDistance[1]);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public LinearSmoothScroller createSnapScroller(LayoutManager layoutManager) {
        LayoutManager layoutManager2 = layoutManager;
        LayoutManager layoutManager3 = layoutManager2;
        if (layoutManager2 instanceof ScrollVectorProvider) {
            return new LinearSmoothScroller(this, this.mRecyclerView.getContext()) {
                final /* synthetic */ SnapHelper this$0;

                {
                    SnapHelper this$02 = r8;
                    Context context = r9;
                    SnapHelper snapHelper = this$02;
                    Context context2 = context;
                    this.this$0 = this$02;
                }

                /* access modifiers changed from: protected */
                public void onTargetFound(View view, State state, Action action) {
                    View targetView = view;
                    Action action2 = action;
                    View view2 = targetView;
                    State state2 = state;
                    Action action3 = action2;
                    int[] calculateDistanceToFinalSnap = this.this$0.calculateDistanceToFinalSnap(this.this$0.mRecyclerView.getLayoutManager(), targetView);
                    int[] snapDistances = calculateDistanceToFinalSnap;
                    int dx = calculateDistanceToFinalSnap[0];
                    int i = snapDistances[1];
                    int dy = i;
                    int calculateTimeForDeceleration = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(i)));
                    int time = calculateTimeForDeceleration;
                    if (calculateTimeForDeceleration > 0) {
                        action2.update(dx, dy, time, this.mDecelerateInterpolator);
                    }
                }

                /* access modifiers changed from: protected */
                public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    DisplayMetrics displayMetrics2 = displayMetrics;
                    DisplayMetrics displayMetrics3 = displayMetrics2;
                    return SnapHelper.MILLISECONDS_PER_INCH / ((float) displayMetrics2.densityDpi);
                }
            };
        }
        return null;
    }
}
