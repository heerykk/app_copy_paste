package android.support.p003v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.media.TransportMediator;
import android.support.p000v4.view.accessibility.AccessibilityEventCompat;
import android.support.p000v4.view.accessibility.AccessibilityRecordCompat;
import android.support.p003v7.widget.RecyclerView.LayoutManager;
import android.support.p003v7.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry;
import android.support.p003v7.widget.RecyclerView.LayoutManager.Properties;
import android.support.p003v7.widget.RecyclerView.LayoutParams;
import android.support.p003v7.widget.RecyclerView.Recycler;
import android.support.p003v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.support.p003v7.widget.RecyclerView.State;
import android.support.p003v7.widget.RecyclerView.ViewHolder;
import android.support.p003v7.widget.helper.ItemTouchHelper.ViewDropHandler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

/* renamed from: android.support.v7.widget.LinearLayoutManager */
public class LinearLayoutManager extends LayoutManager implements ViewDropHandler, ScrollVectorProvider {
    static final boolean DEBUG = false;
    public static final int HORIZONTAL = 0;
    public static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "LinearLayoutManager";
    public static final int VERTICAL = 1;
    final AnchorInfo mAnchorInfo;
    private int mInitialItemPrefetchCount;
    private boolean mLastStackFromEnd;
    private final LayoutChunkResult mLayoutChunkResult;
    private LayoutState mLayoutState;
    int mOrientation;
    OrientationHelper mOrientationHelper;
    SavedState mPendingSavedState;
    int mPendingScrollPosition;
    int mPendingScrollPositionOffset;
    private boolean mRecycleChildrenOnDetach;
    private boolean mReverseLayout;
    boolean mShouldReverseLayout;
    private boolean mSmoothScrollbarEnabled;
    private boolean mStackFromEnd;

    /* renamed from: android.support.v7.widget.LinearLayoutManager$AnchorInfo */
    class AnchorInfo {
        int mCoordinate;
        boolean mLayoutFromEnd;
        int mPosition;
        boolean mValid;
        final /* synthetic */ LinearLayoutManager this$0;

        AnchorInfo(LinearLayoutManager linearLayoutManager) {
            LinearLayoutManager this$02 = linearLayoutManager;
            LinearLayoutManager linearLayoutManager2 = this$02;
            this.this$0 = this$02;
            reset();
        }

        /* access modifiers changed from: 0000 */
        public void reset() {
            this.mPosition = -1;
            this.mCoordinate = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mValid = false;
        }

        /* access modifiers changed from: 0000 */
        public void assignCoordinateFromPadding() {
            int endAfterPadding;
            if (!this.mLayoutFromEnd) {
                endAfterPadding = this.this$0.mOrientationHelper.getStartAfterPadding();
            } else {
                endAfterPadding = this.this$0.mOrientationHelper.getEndAfterPadding();
            }
            this.mCoordinate = endAfterPadding;
        }

        public String toString() {
            return "AnchorInfo{mPosition=" + this.mPosition + ", mCoordinate=" + this.mCoordinate + ", mLayoutFromEnd=" + this.mLayoutFromEnd + ", mValid=" + this.mValid + '}';
        }

        /* access modifiers changed from: 0000 */
        public boolean isViewValidAsAnchor(View view, State state) {
            View child = view;
            State state2 = state;
            View view2 = child;
            State state3 = state2;
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            LayoutParams lp = layoutParams;
            return !layoutParams.isItemRemoved() && lp.getViewLayoutPosition() >= 0 && lp.getViewLayoutPosition() < state2.getItemCount();
        }

        public void assignFromViewAndKeepVisibleRect(View view) {
            View child = view;
            View view2 = child;
            int totalSpaceChange = this.this$0.mOrientationHelper.getTotalSpaceChange();
            int spaceChange = totalSpaceChange;
            if (totalSpaceChange < 0) {
                this.mPosition = this.this$0.getPosition(child);
                if (!this.mLayoutFromEnd) {
                    int decoratedStart = this.this$0.mOrientationHelper.getDecoratedStart(child);
                    int childStart = decoratedStart;
                    int startAfterPadding = decoratedStart - this.this$0.mOrientationHelper.getStartAfterPadding();
                    int startMargin = startAfterPadding;
                    this.mCoordinate = childStart;
                    if (startAfterPadding > 0) {
                        int endAfterPadding = this.this$0.mOrientationHelper.getEndAfterPadding() - spaceChange;
                        int i = endAfterPadding;
                        int endAfterPadding2 = this.this$0.mOrientationHelper.getEndAfterPadding() - Math.min(0, endAfterPadding - this.this$0.mOrientationHelper.getDecoratedEnd(child));
                        int i2 = endAfterPadding2;
                        int decoratedMeasurement = endAfterPadding2 - (childStart + this.this$0.mOrientationHelper.getDecoratedMeasurement(child));
                        int endMargin = decoratedMeasurement;
                        if (decoratedMeasurement < 0) {
                            this.mCoordinate -= Math.min(startMargin, -endMargin);
                        }
                    }
                } else {
                    int endAfterPadding3 = (this.this$0.mOrientationHelper.getEndAfterPadding() - spaceChange) - this.this$0.mOrientationHelper.getDecoratedEnd(child);
                    int previousEndMargin = endAfterPadding3;
                    this.mCoordinate = this.this$0.mOrientationHelper.getEndAfterPadding() - endAfterPadding3;
                    if (endAfterPadding3 > 0) {
                        int estimatedChildStart = this.mCoordinate - this.this$0.mOrientationHelper.getDecoratedMeasurement(child);
                        int layoutStart = this.this$0.mOrientationHelper.getStartAfterPadding();
                        int min = estimatedChildStart - (layoutStart + Math.min(this.this$0.mOrientationHelper.getDecoratedStart(child) - layoutStart, 0));
                        int startMargin2 = min;
                        if (min < 0) {
                            this.mCoordinate += Math.min(previousEndMargin, -startMargin2);
                        }
                    }
                }
                return;
            }
            assignFromView(child);
        }

        public void assignFromView(View view) {
            View child = view;
            View view2 = child;
            if (!this.mLayoutFromEnd) {
                this.mCoordinate = this.this$0.mOrientationHelper.getDecoratedStart(child);
            } else {
                this.mCoordinate = this.this$0.mOrientationHelper.getDecoratedEnd(child) + this.this$0.mOrientationHelper.getTotalSpaceChange();
            }
            this.mPosition = this.this$0.getPosition(child);
        }
    }

    /* renamed from: android.support.v7.widget.LinearLayoutManager$LayoutChunkResult */
    protected static class LayoutChunkResult {
        public int mConsumed;
        public boolean mFinished;
        public boolean mFocusable;
        public boolean mIgnoreConsumed;

        protected LayoutChunkResult() {
        }

        /* access modifiers changed from: 0000 */
        public void resetInternal() {
            this.mConsumed = 0;
            this.mFinished = false;
            this.mIgnoreConsumed = false;
            this.mFocusable = false;
        }
    }

    /* renamed from: android.support.v7.widget.LinearLayoutManager$LayoutState */
    static class LayoutState {
        static final int INVALID_LAYOUT = Integer.MIN_VALUE;
        static final int ITEM_DIRECTION_HEAD = -1;
        static final int ITEM_DIRECTION_TAIL = 1;
        static final int LAYOUT_END = 1;
        static final int LAYOUT_START = -1;
        static final int SCROLLING_OFFSET_NaN = Integer.MIN_VALUE;
        static final String TAG = "LLM#LayoutState";
        int mAvailable;
        int mCurrentPosition;
        int mExtra = 0;
        boolean mInfinite;
        boolean mIsPreLayout = false;
        int mItemDirection;
        int mLastScrollDelta;
        int mLayoutDirection;
        int mOffset;
        boolean mRecycle = true;
        List<ViewHolder> mScrapList = null;
        int mScrollingOffset;

        LayoutState() {
        }

        /* access modifiers changed from: 0000 */
        public boolean hasMore(State state) {
            State state2 = state;
            State state3 = state2;
            return this.mCurrentPosition >= 0 && this.mCurrentPosition < state2.getItemCount();
        }

        /* access modifiers changed from: 0000 */
        public View next(Recycler recycler) {
            Recycler recycler2 = recycler;
            Recycler recycler3 = recycler2;
            if (this.mScrapList != null) {
                return nextViewFromScrapList();
            }
            View view = recycler2.getViewForPosition(this.mCurrentPosition);
            this.mCurrentPosition += this.mItemDirection;
            return view;
        }

        private View nextViewFromScrapList() {
            int size = this.mScrapList.size();
            int i = 0;
            while (i < size) {
                View view = ((ViewHolder) this.mScrapList.get(i)).itemView;
                View view2 = view;
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.isItemRemoved() || this.mCurrentPosition != lp.getViewLayoutPosition()) {
                    i++;
                } else {
                    assignPositionFromScrapList(view2);
                    return view2;
                }
            }
            return null;
        }

        public void assignPositionFromScrapList() {
            assignPositionFromScrapList(null);
        }

        public void assignPositionFromScrapList(View view) {
            View ignore = view;
            View view2 = ignore;
            View nextViewInLimitedList = nextViewInLimitedList(ignore);
            View closest = nextViewInLimitedList;
            if (nextViewInLimitedList != null) {
                this.mCurrentPosition = ((LayoutParams) closest.getLayoutParams()).getViewLayoutPosition();
            } else {
                this.mCurrentPosition = -1;
            }
        }

        public View nextViewInLimitedList(View view) {
            View ignore = view;
            View view2 = ignore;
            int size = this.mScrapList.size();
            View closest = null;
            int closestDistance = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            for (int i = 0; i < size; i++) {
                View view3 = ((ViewHolder) this.mScrapList.get(i)).itemView;
                View view4 = view3;
                LayoutParams lp = (LayoutParams) view3.getLayoutParams();
                if (view4 != ignore && !lp.isItemRemoved()) {
                    int viewLayoutPosition = (lp.getViewLayoutPosition() - this.mCurrentPosition) * this.mItemDirection;
                    int distance = viewLayoutPosition;
                    if (viewLayoutPosition >= 0 && distance < closestDistance) {
                        closest = view4;
                        closestDistance = distance;
                        if (distance == 0) {
                            break;
                        }
                    }
                }
            }
            return closest;
        }

        /* access modifiers changed from: 0000 */
        public void log() {
            int d = Log.d(TAG, "avail:" + this.mAvailable + ", ind:" + this.mCurrentPosition + ", dir:" + this.mItemDirection + ", offset:" + this.mOffset + ", layoutDir:" + this.mLayoutDirection);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.widget.LinearLayoutManager$SavedState */
    public static class SavedState implements Parcelable {
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
        boolean mAnchorLayoutFromEnd;
        int mAnchorOffset;
        int mAnchorPosition;

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            this.mAnchorPosition = in.readInt();
            this.mAnchorOffset = in.readInt();
            this.mAnchorLayoutFromEnd = in.readInt() == 1;
        }

        public SavedState(SavedState savedState) {
            SavedState other = savedState;
            SavedState savedState2 = other;
            this.mAnchorPosition = other.mAnchorPosition;
            this.mAnchorOffset = other.mAnchorOffset;
            this.mAnchorLayoutFromEnd = other.mAnchorLayoutFromEnd;
        }

        /* access modifiers changed from: 0000 */
        public boolean hasValidAnchor() {
            return this.mAnchorPosition >= 0;
        }

        /* access modifiers changed from: 0000 */
        public void invalidateAnchor() {
            this.mAnchorPosition = -1;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            Parcel parcel2 = dest;
            int i2 = i;
            dest.writeInt(this.mAnchorPosition);
            dest.writeInt(this.mAnchorOffset);
            dest.writeInt(!this.mAnchorLayoutFromEnd ? 0 : 1);
        }
    }

    public LinearLayoutManager(Context context, int i, boolean z) {
        int orientation = i;
        Context context2 = context;
        int i2 = orientation;
        boolean reverseLayout = z;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo(this);
        this.mLayoutChunkResult = new LayoutChunkResult();
        this.mInitialItemPrefetchCount = 2;
        setOrientation(orientation);
        setReverseLayout(reverseLayout);
        setAutoMeasureEnabled(true);
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        int defStyleRes = i2;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i3 = defStyleAttr;
        int i4 = defStyleRes;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo(this);
        this.mLayoutChunkResult = new LayoutChunkResult();
        this.mInitialItemPrefetchCount = 2;
        Properties properties = getProperties(context2, attrs, defStyleAttr, defStyleRes);
        setOrientation(properties.orientation);
        setReverseLayout(properties.reverseLayout);
        setStackFromEnd(properties.stackFromEnd);
        setAutoMeasureEnabled(true);
    }

    public LinearLayoutManager(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, 1, false);
    }

    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public boolean getRecycleChildrenOnDetach() {
        return this.mRecycleChildrenOnDetach;
    }

    public void setRecycleChildrenOnDetach(boolean z) {
        this.mRecycleChildrenOnDetach = z;
    }

    public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
        RecyclerView view = recyclerView;
        Recycler recycler2 = recycler;
        RecyclerView recyclerView2 = view;
        Recycler recycler3 = recycler2;
        super.onDetachedFromWindow(view, recycler2);
        if (this.mRecycleChildrenOnDetach) {
            removeAndRecycleAllViews(recycler2);
            recycler2.clear();
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        super.onInitializeAccessibilityEvent(event);
        if (getChildCount() > 0) {
            AccessibilityRecordCompat asRecord = AccessibilityEventCompat.asRecord(event);
            AccessibilityRecordCompat record = asRecord;
            asRecord.setFromIndex(findFirstVisibleItemPosition());
            record.setToIndex(findLastVisibleItemPosition());
        }
    }

    public Parcelable onSaveInstanceState() {
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState state = new SavedState();
        if (getChildCount() <= 0) {
            state.invalidateAnchor();
        } else {
            ensureLayoutState();
            boolean z = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
            boolean z2 = z;
            state.mAnchorLayoutFromEnd = z;
            if (!z) {
                View refChild = getChildClosestToStart();
                state.mAnchorPosition = getPosition(refChild);
                state.mAnchorOffset = this.mOrientationHelper.getDecoratedStart(refChild) - this.mOrientationHelper.getStartAfterPadding();
            } else {
                View refChild2 = getChildClosestToEnd();
                state.mAnchorOffset = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(refChild2);
                state.mAnchorPosition = getPosition(refChild2);
            }
        }
        return state;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            this.mPendingSavedState = (SavedState) state;
            requestLayout();
        }
    }

    public boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    public boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    public void setStackFromEnd(boolean z) {
        boolean stackFromEnd = z;
        assertNotInLayoutOrScroll(null);
        if (this.mStackFromEnd != stackFromEnd) {
            this.mStackFromEnd = stackFromEnd;
            requestLayout();
        }
    }

    public boolean getStackFromEnd() {
        return this.mStackFromEnd;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(int i) {
        int orientation = i;
        int i2 = orientation;
        if (orientation == 0 || orientation == 1) {
            assertNotInLayoutOrScroll(null);
            if (orientation != this.mOrientation) {
                this.mOrientation = orientation;
                this.mOrientationHelper = null;
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation:" + orientation);
    }

    private void resolveShouldLayoutReverse() {
        if (this.mOrientation != 1 && isLayoutRTL()) {
            this.mShouldReverseLayout = !this.mReverseLayout;
        } else {
            this.mShouldReverseLayout = this.mReverseLayout;
        }
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    public void setReverseLayout(boolean z) {
        boolean reverseLayout = z;
        assertNotInLayoutOrScroll(null);
        if (reverseLayout != this.mReverseLayout) {
            this.mReverseLayout = reverseLayout;
            requestLayout();
        }
    }

    public View findViewByPosition(int i) {
        int position = i;
        int i2 = position;
        int childCount = getChildCount();
        int childCount2 = childCount;
        if (childCount == 0) {
            return null;
        }
        int position2 = position - getPosition(getChildAt(0));
        int viewPosition = position2;
        if (position2 >= 0 && viewPosition < childCount2) {
            View child = getChildAt(viewPosition);
            if (getPosition(child) == position) {
                return child;
            }
        }
        return super.findViewByPosition(position);
    }

    /* access modifiers changed from: protected */
    public int getExtraLayoutSpace(State state) {
        State state2 = state;
        State state3 = state2;
        if (!state2.hasTargetScrollPosition()) {
            return 0;
        }
        return this.mOrientationHelper.getTotalSpace();
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
        RecyclerView recyclerView2 = recyclerView;
        int position = i;
        RecyclerView recyclerView3 = recyclerView2;
        State state2 = state;
        int i2 = position;
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView2.getContext());
        LinearSmoothScroller linearSmoothScroller2 = linearSmoothScroller;
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller2);
    }

    public PointF computeScrollVectorForPosition(int i) {
        int targetPosition = i;
        int i2 = targetPosition;
        if (getChildCount() == 0) {
            return null;
        }
        int direction = (targetPosition < getPosition(getChildAt(0))) == this.mShouldReverseLayout ? 1 : -1;
        if (this.mOrientation != 0) {
            return new PointF(0.0f, (float) direction);
        }
        return new PointF((float) direction, 0.0f);
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        int extraForEnd;
        int extraForStart;
        int upcomingOffset;
        int firstLayoutDirection;
        int startOffset;
        int endOffset;
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if ((this.mPendingSavedState == null && this.mPendingScrollPosition == -1) || state2.getItemCount() != 0) {
            if (this.mPendingSavedState != null && this.mPendingSavedState.hasValidAnchor()) {
                this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
            }
            ensureLayoutState();
            this.mLayoutState.mRecycle = false;
            resolveShouldLayoutReverse();
            if (!(this.mAnchorInfo.mValid && this.mPendingScrollPosition == -1 && this.mPendingSavedState == null)) {
                this.mAnchorInfo.reset();
                this.mAnchorInfo.mLayoutFromEnd = this.mShouldReverseLayout ^ this.mStackFromEnd;
                updateAnchorInfoForLayout(recycler2, state2, this.mAnchorInfo);
                this.mAnchorInfo.mValid = true;
            }
            int extra = getExtraLayoutSpace(state2);
            if (this.mLayoutState.mLastScrollDelta < 0) {
                extraForStart = extra;
                extraForEnd = 0;
            } else {
                extraForEnd = extra;
                extraForStart = 0;
            }
            int extraForStart2 = extraForStart + this.mOrientationHelper.getStartAfterPadding();
            int extraForEnd2 = extraForEnd + this.mOrientationHelper.getEndPadding();
            if (!(!state2.isPreLayout() || this.mPendingScrollPosition == -1 || this.mPendingScrollPositionOffset == Integer.MIN_VALUE)) {
                View findViewByPosition = findViewByPosition(this.mPendingScrollPosition);
                View existing = findViewByPosition;
                if (findViewByPosition != null) {
                    if (!this.mShouldReverseLayout) {
                        upcomingOffset = this.mPendingScrollPositionOffset - (this.mOrientationHelper.getDecoratedStart(existing) - this.mOrientationHelper.getStartAfterPadding());
                    } else {
                        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(existing);
                        int i = endAfterPadding;
                        upcomingOffset = endAfterPadding - this.mPendingScrollPositionOffset;
                    }
                    if (upcomingOffset <= 0) {
                        extraForEnd2 -= upcomingOffset;
                    } else {
                        extraForStart2 += upcomingOffset;
                    }
                }
            }
            if (!this.mAnchorInfo.mLayoutFromEnd) {
                firstLayoutDirection = !this.mShouldReverseLayout ? 1 : -1;
            } else {
                firstLayoutDirection = !this.mShouldReverseLayout ? -1 : 1;
            }
            onAnchorReady(recycler2, state2, this.mAnchorInfo, firstLayoutDirection);
            detachAndScrapAttachedViews(recycler2);
            this.mLayoutState.mInfinite = resolveIsInfinite();
            this.mLayoutState.mIsPreLayout = state2.isPreLayout();
            if (!this.mAnchorInfo.mLayoutFromEnd) {
                updateLayoutStateToFillEnd(this.mAnchorInfo);
                this.mLayoutState.mExtra = extraForEnd2;
                int fill = fill(recycler2, this.mLayoutState, state2, false);
                endOffset = this.mLayoutState.mOffset;
                int lastElement = this.mLayoutState.mCurrentPosition;
                if (this.mLayoutState.mAvailable > 0) {
                    extraForStart2 += this.mLayoutState.mAvailable;
                }
                updateLayoutStateToFillStart(this.mAnchorInfo);
                this.mLayoutState.mExtra = extraForStart2;
                this.mLayoutState.mCurrentPosition += this.mLayoutState.mItemDirection;
                int fill2 = fill(recycler2, this.mLayoutState, state2, false);
                startOffset = this.mLayoutState.mOffset;
                if (this.mLayoutState.mAvailable > 0) {
                    int i2 = this.mLayoutState.mAvailable;
                    int extraForEnd3 = i2;
                    updateLayoutStateToFillEnd(lastElement, endOffset);
                    this.mLayoutState.mExtra = i2;
                    int fill3 = fill(recycler2, this.mLayoutState, state2, false);
                    endOffset = this.mLayoutState.mOffset;
                }
            } else {
                updateLayoutStateToFillStart(this.mAnchorInfo);
                this.mLayoutState.mExtra = extraForStart2;
                int fill4 = fill(recycler2, this.mLayoutState, state2, false);
                startOffset = this.mLayoutState.mOffset;
                int firstElement = this.mLayoutState.mCurrentPosition;
                if (this.mLayoutState.mAvailable > 0) {
                    extraForEnd2 += this.mLayoutState.mAvailable;
                }
                updateLayoutStateToFillEnd(this.mAnchorInfo);
                this.mLayoutState.mExtra = extraForEnd2;
                this.mLayoutState.mCurrentPosition += this.mLayoutState.mItemDirection;
                int fill5 = fill(recycler2, this.mLayoutState, state2, false);
                endOffset = this.mLayoutState.mOffset;
                if (this.mLayoutState.mAvailable > 0) {
                    int i3 = this.mLayoutState.mAvailable;
                    int extraForStart3 = i3;
                    updateLayoutStateToFillStart(firstElement, startOffset);
                    this.mLayoutState.mExtra = i3;
                    int fill6 = fill(recycler2, this.mLayoutState, state2, false);
                    startOffset = this.mLayoutState.mOffset;
                }
            }
            if (getChildCount() > 0) {
                if (!(this.mShouldReverseLayout ^ this.mStackFromEnd)) {
                    int fixOffset = fixLayoutStartGap(startOffset, recycler2, state2, true);
                    int startOffset2 = startOffset + fixOffset;
                    int i4 = endOffset + fixOffset;
                    int endOffset2 = i4;
                    int fixOffset2 = fixLayoutEndGap(i4, recycler2, state2, false);
                    startOffset = startOffset2 + fixOffset2;
                    endOffset = endOffset2 + fixOffset2;
                } else {
                    int fixOffset3 = fixLayoutEndGap(endOffset, recycler2, state2, true);
                    int startOffset3 = startOffset + fixOffset3;
                    int endOffset3 = endOffset + fixOffset3;
                    int fixOffset4 = fixLayoutStartGap(startOffset3, recycler2, state2, false);
                    startOffset = startOffset3 + fixOffset4;
                    endOffset = endOffset3 + fixOffset4;
                }
            }
            layoutForPredictiveAnimations(recycler2, state2, startOffset, endOffset);
            if (state2.isPreLayout()) {
                this.mAnchorInfo.reset();
            } else {
                this.mOrientationHelper.onLayoutComplete();
            }
            this.mLastStackFromEnd = this.mStackFromEnd;
            return;
        }
        removeAndRecycleAllViews(recycler2);
    }

    public void onLayoutCompleted(State state) {
        State state2 = state;
        State state3 = state2;
        super.onLayoutCompleted(state2);
        this.mPendingSavedState = null;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mAnchorInfo.reset();
    }

    /* access modifiers changed from: 0000 */
    public void onAnchorReady(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
        Recycler recycler2 = recycler;
        State state2 = state;
        AnchorInfo anchorInfo2 = anchorInfo;
        int i2 = i;
    }

    private void layoutForPredictiveAnimations(Recycler recycler, State state, int i, int i2) {
        Recycler recycler2 = recycler;
        State state2 = state;
        int startOffset = i;
        int endOffset = i2;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        int i3 = startOffset;
        int i4 = endOffset;
        if (state2.willRunPredictiveAnimations() && getChildCount() != 0 && !state2.isPreLayout() && supportsPredictiveItemAnimations()) {
            int scrapExtraStart = 0;
            int scrapExtraEnd = 0;
            List scrapList = recycler2.getScrapList();
            List list = scrapList;
            int scrapSize = scrapList.size();
            int firstChildPos = getPosition(getChildAt(0));
            for (int i5 = 0; i5 < scrapSize; i5++) {
                ViewHolder viewHolder = (ViewHolder) list.get(i5);
                ViewHolder scrap = viewHolder;
                if (!viewHolder.isRemoved()) {
                    int layoutPosition = scrap.getLayoutPosition();
                    int i6 = layoutPosition;
                    if (((layoutPosition < firstChildPos) == this.mShouldReverseLayout ? (char) 1 : 65535) != 65535) {
                        scrapExtraEnd += this.mOrientationHelper.getDecoratedMeasurement(scrap.itemView);
                    } else {
                        scrapExtraStart += this.mOrientationHelper.getDecoratedMeasurement(scrap.itemView);
                    }
                }
            }
            this.mLayoutState.mScrapList = list;
            if (scrapExtraStart > 0) {
                updateLayoutStateToFillStart(getPosition(getChildClosestToStart()), startOffset);
                this.mLayoutState.mExtra = scrapExtraStart;
                this.mLayoutState.mAvailable = 0;
                this.mLayoutState.assignPositionFromScrapList();
                int fill = fill(recycler2, this.mLayoutState, state2, false);
            }
            if (scrapExtraEnd > 0) {
                updateLayoutStateToFillEnd(getPosition(getChildClosestToEnd()), endOffset);
                this.mLayoutState.mExtra = scrapExtraEnd;
                this.mLayoutState.mAvailable = 0;
                this.mLayoutState.assignPositionFromScrapList();
                int fill2 = fill(recycler2, this.mLayoutState, state2, false);
            }
            this.mLayoutState.mScrapList = null;
        }
    }

    private void updateAnchorInfoForLayout(Recycler recycler, State state, AnchorInfo anchorInfo) {
        Recycler recycler2 = recycler;
        State state2 = state;
        AnchorInfo anchorInfo2 = anchorInfo;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        AnchorInfo anchorInfo3 = anchorInfo2;
        if (!updateAnchorFromPendingData(state2, anchorInfo2) && !updateAnchorFromChildren(recycler2, state2, anchorInfo2)) {
            anchorInfo2.assignCoordinateFromPadding();
            anchorInfo2.mPosition = !this.mStackFromEnd ? 0 : state2.getItemCount() - 1;
        }
    }

    private boolean updateAnchorFromChildren(Recycler recycler, State state, AnchorInfo anchorInfo) {
        View findReferenceChildClosestToEnd;
        int endAfterPadding;
        Recycler recycler2 = recycler;
        State state2 = state;
        AnchorInfo anchorInfo2 = anchorInfo;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        AnchorInfo anchorInfo3 = anchorInfo2;
        if (getChildCount() == 0) {
            return false;
        }
        View focusedChild = getFocusedChild();
        View focused = focusedChild;
        if (focusedChild != null && anchorInfo2.isViewValidAsAnchor(focused, state2)) {
            anchorInfo2.assignFromViewAndKeepVisibleRect(focused);
            return true;
        } else if (this.mLastStackFromEnd != this.mStackFromEnd) {
            return false;
        } else {
            if (!anchorInfo2.mLayoutFromEnd) {
                findReferenceChildClosestToEnd = findReferenceChildClosestToStart(recycler2, state2);
            } else {
                findReferenceChildClosestToEnd = findReferenceChildClosestToEnd(recycler2, state2);
            }
            View referenceChild = findReferenceChildClosestToEnd;
            if (referenceChild == null) {
                return false;
            }
            anchorInfo2.assignFromView(referenceChild);
            if (!state2.isPreLayout() && supportsPredictiveItemAnimations()) {
                if (this.mOrientationHelper.getDecoratedStart(referenceChild) >= this.mOrientationHelper.getEndAfterPadding() || this.mOrientationHelper.getDecoratedEnd(referenceChild) < this.mOrientationHelper.getStartAfterPadding()) {
                    if (!anchorInfo2.mLayoutFromEnd) {
                        endAfterPadding = this.mOrientationHelper.getStartAfterPadding();
                    } else {
                        endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
                    }
                    anchorInfo2.mCoordinate = endAfterPadding;
                }
            }
            return true;
        }
    }

    private boolean updateAnchorFromPendingData(State state, AnchorInfo anchorInfo) {
        int decoratedEnd;
        State state2 = state;
        AnchorInfo anchorInfo2 = anchorInfo;
        State state3 = state2;
        AnchorInfo anchorInfo3 = anchorInfo2;
        if (state2.isPreLayout() || this.mPendingScrollPosition == -1) {
            return false;
        }
        if (this.mPendingScrollPosition >= 0 && this.mPendingScrollPosition < state2.getItemCount()) {
            anchorInfo2.mPosition = this.mPendingScrollPosition;
            if (this.mPendingSavedState != null && this.mPendingSavedState.hasValidAnchor()) {
                anchorInfo2.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
                if (!anchorInfo2.mLayoutFromEnd) {
                    anchorInfo2.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingSavedState.mAnchorOffset;
                } else {
                    anchorInfo2.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingSavedState.mAnchorOffset;
                }
                return true;
            } else if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                anchorInfo2.mLayoutFromEnd = this.mShouldReverseLayout;
                if (!this.mShouldReverseLayout) {
                    anchorInfo2.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingScrollPositionOffset;
                } else {
                    anchorInfo2.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingScrollPositionOffset;
                }
                return true;
            } else {
                View findViewByPosition = findViewByPosition(this.mPendingScrollPosition);
                View child = findViewByPosition;
                if (findViewByPosition == null) {
                    if (getChildCount() > 0) {
                        int position = getPosition(getChildAt(0));
                        int i = position;
                        anchorInfo2.mLayoutFromEnd = (this.mPendingScrollPosition < position) == this.mShouldReverseLayout;
                    }
                    anchorInfo2.assignCoordinateFromPadding();
                } else {
                    int decoratedMeasurement = this.mOrientationHelper.getDecoratedMeasurement(child);
                    int i2 = decoratedMeasurement;
                    if (decoratedMeasurement <= this.mOrientationHelper.getTotalSpace()) {
                        int decoratedStart = this.mOrientationHelper.getDecoratedStart(child) - this.mOrientationHelper.getStartAfterPadding();
                        int i3 = decoratedStart;
                        if (decoratedStart >= 0) {
                            int endAfterPadding = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(child);
                            int i4 = endAfterPadding;
                            if (endAfterPadding >= 0) {
                                if (!anchorInfo2.mLayoutFromEnd) {
                                    decoratedEnd = this.mOrientationHelper.getDecoratedStart(child);
                                } else {
                                    decoratedEnd = this.mOrientationHelper.getDecoratedEnd(child) + this.mOrientationHelper.getTotalSpaceChange();
                                }
                                anchorInfo2.mCoordinate = decoratedEnd;
                            } else {
                                anchorInfo2.mCoordinate = this.mOrientationHelper.getEndAfterPadding();
                                anchorInfo2.mLayoutFromEnd = true;
                                return true;
                            }
                        } else {
                            anchorInfo2.mCoordinate = this.mOrientationHelper.getStartAfterPadding();
                            anchorInfo2.mLayoutFromEnd = false;
                            return true;
                        }
                    } else {
                        anchorInfo2.assignCoordinateFromPadding();
                        return true;
                    }
                }
                return true;
            }
        } else {
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            return false;
        }
    }

    private int fixLayoutEndGap(int i, Recycler recycler, State state, boolean z) {
        int endOffset = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        int i2 = endOffset;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        boolean canOffsetChildren = z;
        int gap = this.mOrientationHelper.getEndAfterPadding() - endOffset;
        if (gap <= 0) {
            return 0;
        }
        int fixOffset = -scrollBy(-gap, recycler2, state2);
        int endOffset2 = endOffset + fixOffset;
        if (canOffsetChildren) {
            int endAfterPadding = this.mOrientationHelper.getEndAfterPadding() - endOffset2;
            int gap2 = endAfterPadding;
            if (endAfterPadding > 0) {
                this.mOrientationHelper.offsetChildren(gap2);
                return gap2 + fixOffset;
            }
        }
        return fixOffset;
    }

    private int fixLayoutStartGap(int i, Recycler recycler, State state, boolean z) {
        int startOffset = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        int i2 = startOffset;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        boolean canOffsetChildren = z;
        int gap = startOffset - this.mOrientationHelper.getStartAfterPadding();
        if (gap <= 0) {
            return 0;
        }
        int fixOffset = -scrollBy(gap, recycler2, state2);
        int startOffset2 = startOffset + fixOffset;
        if (canOffsetChildren) {
            int startAfterPadding = startOffset2 - this.mOrientationHelper.getStartAfterPadding();
            int gap2 = startAfterPadding;
            if (startAfterPadding > 0) {
                this.mOrientationHelper.offsetChildren(-gap2);
                return fixOffset - gap2;
            }
        }
        return fixOffset;
    }

    private void updateLayoutStateToFillEnd(AnchorInfo anchorInfo) {
        AnchorInfo anchorInfo2 = anchorInfo;
        AnchorInfo anchorInfo3 = anchorInfo2;
        updateLayoutStateToFillEnd(anchorInfo2.mPosition, anchorInfo2.mCoordinate);
    }

    private void updateLayoutStateToFillEnd(int i, int i2) {
        int itemPosition = i;
        int offset = i2;
        int i3 = itemPosition;
        int i4 = offset;
        this.mLayoutState.mAvailable = this.mOrientationHelper.getEndAfterPadding() - offset;
        this.mLayoutState.mItemDirection = !this.mShouldReverseLayout ? 1 : -1;
        this.mLayoutState.mCurrentPosition = itemPosition;
        this.mLayoutState.mLayoutDirection = 1;
        this.mLayoutState.mOffset = offset;
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillStart(AnchorInfo anchorInfo) {
        AnchorInfo anchorInfo2 = anchorInfo;
        AnchorInfo anchorInfo3 = anchorInfo2;
        updateLayoutStateToFillStart(anchorInfo2.mPosition, anchorInfo2.mCoordinate);
    }

    private void updateLayoutStateToFillStart(int i, int i2) {
        int itemPosition = i;
        int offset = i2;
        int i3 = itemPosition;
        int i4 = offset;
        this.mLayoutState.mAvailable = offset - this.mOrientationHelper.getStartAfterPadding();
        this.mLayoutState.mCurrentPosition = itemPosition;
        this.mLayoutState.mItemDirection = !this.mShouldReverseLayout ? -1 : 1;
        this.mLayoutState.mLayoutDirection = -1;
        this.mLayoutState.mOffset = offset;
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    /* access modifiers changed from: protected */
    public boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    /* access modifiers changed from: 0000 */
    public void ensureLayoutState() {
        if (this.mLayoutState == null) {
            this.mLayoutState = createLayoutState();
        }
        if (this.mOrientationHelper == null) {
            this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        }
    }

    /* access modifiers changed from: 0000 */
    public LayoutState createLayoutState() {
        return new LayoutState();
    }

    public void scrollToPosition(int i) {
        int position = i;
        int i2 = position;
        this.mPendingScrollPosition = position;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchor();
        }
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i, int i2) {
        int position = i;
        int offset = i2;
        int i3 = position;
        int i4 = offset;
        this.mPendingScrollPosition = position;
        this.mPendingScrollPositionOffset = offset;
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchor();
        }
        requestLayout();
    }

    public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
        int dx = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        int i2 = dx;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (this.mOrientation != 1) {
            return scrollBy(dx, recycler2, state2);
        }
        return 0;
    }

    public int scrollVerticallyBy(int i, Recycler recycler, State state) {
        int dy = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        int i2 = dy;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (this.mOrientation != 0) {
            return scrollBy(dy, recycler2, state2);
        }
        return 0;
    }

    public int computeHorizontalScrollOffset(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollOffset(state2);
    }

    public int computeVerticalScrollOffset(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollOffset(state2);
    }

    public int computeHorizontalScrollExtent(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollExtent(state2);
    }

    public int computeVerticalScrollExtent(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollExtent(state2);
    }

    public int computeHorizontalScrollRange(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollRange(state2);
    }

    public int computeVerticalScrollRange(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollRange(state2);
    }

    private int computeScrollOffset(State state) {
        State state2 = state;
        State state3 = state2;
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        return ScrollbarHelper.computeScrollOffset(state2, this.mOrientationHelper, findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true), findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    private int computeScrollExtent(State state) {
        State state2 = state;
        State state3 = state2;
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        return ScrollbarHelper.computeScrollExtent(state2, this.mOrientationHelper, findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true), findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled);
    }

    private int computeScrollRange(State state) {
        State state2 = state;
        State state3 = state2;
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        return ScrollbarHelper.computeScrollRange(state2, this.mOrientationHelper, findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true), findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled);
    }

    public void setSmoothScrollbarEnabled(boolean z) {
        this.mSmoothScrollbarEnabled = z;
    }

    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    private void updateLayoutState(int i, int i2, boolean z, State state) {
        int scrollingOffset;
        int layoutDirection = i;
        int requiredSpace = i2;
        State state2 = state;
        int i3 = layoutDirection;
        int i4 = requiredSpace;
        boolean canUseExistingSpace = z;
        State state3 = state2;
        this.mLayoutState.mInfinite = resolveIsInfinite();
        this.mLayoutState.mExtra = getExtraLayoutSpace(state2);
        this.mLayoutState.mLayoutDirection = layoutDirection;
        if (layoutDirection != 1) {
            View child = getChildClosestToStart();
            this.mLayoutState.mExtra += this.mOrientationHelper.getStartAfterPadding();
            this.mLayoutState.mItemDirection = !this.mShouldReverseLayout ? -1 : 1;
            this.mLayoutState.mCurrentPosition = getPosition(child) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedStart(child);
            scrollingOffset = (-this.mOrientationHelper.getDecoratedStart(child)) + this.mOrientationHelper.getStartAfterPadding();
        } else {
            this.mLayoutState.mExtra += this.mOrientationHelper.getEndPadding();
            View child2 = getChildClosestToEnd();
            this.mLayoutState.mItemDirection = !this.mShouldReverseLayout ? 1 : -1;
            this.mLayoutState.mCurrentPosition = getPosition(child2) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedEnd(child2);
            scrollingOffset = this.mOrientationHelper.getDecoratedEnd(child2) - this.mOrientationHelper.getEndAfterPadding();
        }
        this.mLayoutState.mAvailable = requiredSpace;
        if (canUseExistingSpace) {
            this.mLayoutState.mAvailable -= scrollingOffset;
        }
        this.mLayoutState.mScrollingOffset = scrollingOffset;
    }

    /* access modifiers changed from: 0000 */
    public boolean resolveIsInfinite() {
        return this.mOrientationHelper.getMode() == 0 && this.mOrientationHelper.getEnd() == 0;
    }

    /* access modifiers changed from: 0000 */
    public void collectPrefetchPositionsForLayoutState(State state, LayoutState layoutState, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        State state2 = state;
        LayoutState layoutState2 = layoutState;
        LayoutPrefetchRegistry layoutPrefetchRegistry2 = layoutPrefetchRegistry;
        State state3 = state2;
        LayoutState layoutState3 = layoutState2;
        LayoutPrefetchRegistry layoutPrefetchRegistry3 = layoutPrefetchRegistry2;
        int i = layoutState2.mCurrentPosition;
        int pos = i;
        if (i >= 0 && pos < state2.getItemCount()) {
            layoutPrefetchRegistry2.addPosition(pos, layoutState2.mScrollingOffset);
        }
    }

    public void collectInitialPrefetchPositions(int i, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        boolean fromEnd;
        int anchorPos;
        int adapterItemCount = i;
        LayoutPrefetchRegistry layoutPrefetchRegistry2 = layoutPrefetchRegistry;
        int i2 = adapterItemCount;
        LayoutPrefetchRegistry layoutPrefetchRegistry3 = layoutPrefetchRegistry2;
        if (this.mPendingSavedState != null && this.mPendingSavedState.hasValidAnchor()) {
            fromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
            anchorPos = this.mPendingSavedState.mAnchorPosition;
        } else {
            resolveShouldLayoutReverse();
            fromEnd = this.mShouldReverseLayout;
            if (this.mPendingScrollPosition != -1) {
                anchorPos = this.mPendingScrollPosition;
            } else {
                anchorPos = !fromEnd ? 0 : adapterItemCount - 1;
            }
        }
        int direction = !fromEnd ? 1 : -1;
        int targetPos = anchorPos;
        for (int i3 = 0; i3 < this.mInitialItemPrefetchCount && targetPos >= 0 && targetPos < adapterItemCount; i3++) {
            layoutPrefetchRegistry2.addPosition(targetPos, 0);
            targetPos += direction;
        }
    }

    public void setInitialPrefetchItemCount(int i) {
        int itemCount = i;
        int i2 = itemCount;
        this.mInitialItemPrefetchCount = itemCount;
    }

    public int getInitialItemPrefetchCount() {
        return this.mInitialItemPrefetchCount;
    }

    public void collectAdjacentPrefetchPositions(int i, int i2, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int dx = i;
        int dy = i2;
        State state2 = state;
        LayoutPrefetchRegistry layoutPrefetchRegistry2 = layoutPrefetchRegistry;
        int i3 = dx;
        int i4 = dy;
        State state3 = state2;
        LayoutPrefetchRegistry layoutPrefetchRegistry3 = layoutPrefetchRegistry2;
        int delta = this.mOrientation != 0 ? dy : dx;
        if (getChildCount() != 0 && delta != 0) {
            int layoutDirection = delta <= 0 ? -1 : 1;
            int abs = Math.abs(delta);
            int i5 = abs;
            updateLayoutState(layoutDirection, abs, true, state2);
            collectPrefetchPositionsForLayoutState(state2, this.mLayoutState, layoutPrefetchRegistry2);
        }
    }

    /* access modifiers changed from: 0000 */
    public int scrollBy(int i, Recycler recycler, State state) {
        int dy = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        int i2 = dy;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (getChildCount() == 0 || dy == 0) {
            return 0;
        }
        this.mLayoutState.mRecycle = true;
        ensureLayoutState();
        int layoutDirection = dy <= 0 ? -1 : 1;
        int abs = Math.abs(dy);
        int absDy = abs;
        updateLayoutState(layoutDirection, abs, true, state2);
        int fill = this.mLayoutState.mScrollingOffset + fill(recycler2, this.mLayoutState, state2, false);
        int consumed = fill;
        if (fill < 0) {
            return 0;
        }
        int scrolled = absDy <= consumed ? dy : layoutDirection * consumed;
        this.mOrientationHelper.offsetChildren(-scrolled);
        this.mLayoutState.mLastScrollDelta = scrolled;
        return scrolled;
    }

    public void assertNotInLayoutOrScroll(String str) {
        String message = str;
        String str2 = message;
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(message);
        }
    }

    private void recycleChildren(Recycler recycler, int i, int i2) {
        Recycler recycler2 = recycler;
        int startIndex = i;
        int endIndex = i2;
        Recycler recycler3 = recycler2;
        int i3 = startIndex;
        int i4 = endIndex;
        if (startIndex != endIndex) {
            if (endIndex <= startIndex) {
                for (int i5 = startIndex; i5 > endIndex; i5--) {
                    removeAndRecycleViewAt(i5, recycler2);
                }
            } else {
                for (int i6 = endIndex - 1; i6 >= startIndex; i6--) {
                    removeAndRecycleViewAt(i6, recycler2);
                }
            }
        }
    }

    private void recycleViewsFromStart(Recycler recycler, int i) {
        Recycler recycler2 = recycler;
        int dt = i;
        Recycler recycler3 = recycler2;
        int i2 = dt;
        if (dt >= 0) {
            int i3 = dt;
            int childCount = getChildCount();
            if (!this.mShouldReverseLayout) {
                int i4 = 0;
                while (i4 < childCount) {
                    View child = getChildAt(i4);
                    if (this.mOrientationHelper.getDecoratedEnd(child) <= dt && this.mOrientationHelper.getTransformedEndWithDecoration(child) <= dt) {
                        i4++;
                    } else {
                        recycleChildren(recycler2, 0, i4);
                        return;
                    }
                }
            } else {
                int i5 = childCount - 1;
                while (i5 >= 0) {
                    View child2 = getChildAt(i5);
                    if (this.mOrientationHelper.getDecoratedEnd(child2) <= dt && this.mOrientationHelper.getTransformedEndWithDecoration(child2) <= dt) {
                        i5--;
                    } else {
                        recycleChildren(recycler2, childCount - 1, i5);
                        return;
                    }
                }
            }
        }
    }

    private void recycleViewsFromEnd(Recycler recycler, int i) {
        Recycler recycler2 = recycler;
        int dt = i;
        Recycler recycler3 = recycler2;
        int i2 = dt;
        int childCount = getChildCount();
        if (dt >= 0) {
            int limit = this.mOrientationHelper.getEnd() - dt;
            if (!this.mShouldReverseLayout) {
                int i3 = childCount - 1;
                while (i3 >= 0) {
                    View child = getChildAt(i3);
                    if (this.mOrientationHelper.getDecoratedStart(child) >= limit && this.mOrientationHelper.getTransformedStartWithDecoration(child) >= limit) {
                        i3--;
                    } else {
                        recycleChildren(recycler2, childCount - 1, i3);
                        return;
                    }
                }
            } else {
                int i4 = 0;
                while (i4 < childCount) {
                    View child2 = getChildAt(i4);
                    if (this.mOrientationHelper.getDecoratedStart(child2) >= limit && this.mOrientationHelper.getTransformedStartWithDecoration(child2) >= limit) {
                        i4++;
                    } else {
                        recycleChildren(recycler2, 0, i4);
                        return;
                    }
                }
            }
        }
    }

    private void recycleByLayoutState(Recycler recycler, LayoutState layoutState) {
        Recycler recycler2 = recycler;
        LayoutState layoutState2 = layoutState;
        Recycler recycler3 = recycler2;
        LayoutState layoutState3 = layoutState2;
        if (layoutState2.mRecycle && !layoutState2.mInfinite) {
            if (layoutState2.mLayoutDirection != -1) {
                recycleViewsFromStart(recycler2, layoutState2.mScrollingOffset);
            } else {
                recycleViewsFromEnd(recycler2, layoutState2.mScrollingOffset);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int fill(Recycler recycler, LayoutState layoutState, State state, boolean z) {
        Recycler recycler2 = recycler;
        LayoutState layoutState2 = layoutState;
        State state2 = state;
        Recycler recycler3 = recycler2;
        LayoutState layoutState3 = layoutState2;
        State state3 = state2;
        boolean stopOnFocusable = z;
        int start = layoutState2.mAvailable;
        if (layoutState2.mScrollingOffset != Integer.MIN_VALUE) {
            if (layoutState2.mAvailable < 0) {
                layoutState2.mScrollingOffset += layoutState2.mAvailable;
            }
            recycleByLayoutState(recycler2, layoutState2);
        }
        int remainingSpace = layoutState2.mAvailable + layoutState2.mExtra;
        LayoutChunkResult layoutChunkResult = this.mLayoutChunkResult;
        while (true) {
            if ((!layoutState2.mInfinite && remainingSpace <= 0) || !layoutState2.hasMore(state2)) {
                break;
            }
            layoutChunkResult.resetInternal();
            layoutChunk(recycler2, state2, layoutState2, layoutChunkResult);
            if (layoutChunkResult.mFinished) {
                break;
            }
            layoutState2.mOffset += layoutChunkResult.mConsumed * layoutState2.mLayoutDirection;
            if (!layoutChunkResult.mIgnoreConsumed || this.mLayoutState.mScrapList != null || !state2.isPreLayout()) {
                layoutState2.mAvailable -= layoutChunkResult.mConsumed;
                remainingSpace -= layoutChunkResult.mConsumed;
            }
            if (layoutState2.mScrollingOffset != Integer.MIN_VALUE) {
                layoutState2.mScrollingOffset += layoutChunkResult.mConsumed;
                if (layoutState2.mAvailable < 0) {
                    layoutState2.mScrollingOffset += layoutState2.mAvailable;
                }
                recycleByLayoutState(recycler2, layoutState2);
            }
            if (stopOnFocusable && layoutChunkResult.mFocusable) {
                break;
            }
        }
        return start - layoutState2.mAvailable;
    }

    /* access modifiers changed from: 0000 */
    public void layoutChunk(Recycler recycler, State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int right;
        int left;
        int bottom;
        int top;
        Recycler recycler2 = recycler;
        LayoutState layoutState2 = layoutState;
        LayoutChunkResult result = layoutChunkResult;
        Recycler recycler3 = recycler2;
        State state2 = state;
        LayoutState layoutState3 = layoutState2;
        LayoutChunkResult layoutChunkResult2 = result;
        View next = layoutState2.next(recycler2);
        View view = next;
        if (next != null) {
            LayoutParams params = (LayoutParams) view.getLayoutParams();
            if (layoutState2.mScrapList != null) {
                if (this.mShouldReverseLayout != (layoutState2.mLayoutDirection == -1)) {
                    addDisappearingView(view, 0);
                } else {
                    addDisappearingView(view);
                }
            } else {
                if (this.mShouldReverseLayout != (layoutState2.mLayoutDirection == -1)) {
                    addView(view, 0);
                } else {
                    addView(view);
                }
            }
            measureChildWithMargins(view, 0, 0);
            result.mConsumed = this.mOrientationHelper.getDecoratedMeasurement(view);
            if (this.mOrientation != 1) {
                int paddingTop = getPaddingTop();
                top = paddingTop;
                bottom = paddingTop + this.mOrientationHelper.getDecoratedMeasurementInOther(view);
                if (layoutState2.mLayoutDirection != -1) {
                    left = layoutState2.mOffset;
                    right = layoutState2.mOffset + result.mConsumed;
                } else {
                    right = layoutState2.mOffset;
                    left = layoutState2.mOffset - result.mConsumed;
                }
            } else {
                if (!isLayoutRTL()) {
                    int paddingLeft = getPaddingLeft();
                    left = paddingLeft;
                    right = paddingLeft + this.mOrientationHelper.getDecoratedMeasurementInOther(view);
                } else {
                    int width = getWidth() - getPaddingRight();
                    right = width;
                    left = width - this.mOrientationHelper.getDecoratedMeasurementInOther(view);
                }
                if (layoutState2.mLayoutDirection != -1) {
                    top = layoutState2.mOffset;
                    bottom = layoutState2.mOffset + result.mConsumed;
                } else {
                    bottom = layoutState2.mOffset;
                    top = layoutState2.mOffset - result.mConsumed;
                }
            }
            layoutDecoratedWithMargins(view, left, top, right, bottom);
            if (params.isItemRemoved() || params.isItemChanged()) {
                result.mIgnoreConsumed = true;
            }
            result.mFocusable = view.isFocusable();
            return;
        }
        result.mFinished = true;
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldMeasureTwice() {
        return (getHeightMode() == 1073741824 || getWidthMode() == 1073741824 || !hasFlexibleChildInBothOrientations()) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public int convertFocusDirectionToLayoutDirection(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int focusDirection = i;
        int i6 = focusDirection;
        switch (focusDirection) {
            case 1:
                if (this.mOrientation == 1) {
                    return -1;
                }
                if (!isLayoutRTL()) {
                    return -1;
                }
                return 1;
            case 2:
                if (this.mOrientation == 1) {
                    return 1;
                }
                if (!isLayoutRTL()) {
                    return 1;
                }
                return -1;
            case 17:
                if (this.mOrientation != 0) {
                    i5 = Integer.MIN_VALUE;
                } else {
                    i5 = -1;
                }
                return i5;
            case 33:
                if (this.mOrientation != 1) {
                    i4 = Integer.MIN_VALUE;
                } else {
                    i4 = -1;
                }
                return i4;
            case 66:
                if (this.mOrientation != 0) {
                    i3 = Integer.MIN_VALUE;
                } else {
                    i3 = 1;
                }
                return i3;
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                if (this.mOrientation != 1) {
                    i2 = Integer.MIN_VALUE;
                } else {
                    i2 = 1;
                }
                return i2;
            default:
                return Integer.MIN_VALUE;
        }
    }

    private View getChildClosestToStart() {
        return getChildAt(!this.mShouldReverseLayout ? 0 : getChildCount() - 1);
    }

    private View getChildClosestToEnd() {
        return getChildAt(!this.mShouldReverseLayout ? getChildCount() - 1 : 0);
    }

    private View findFirstVisibleChildClosestToStart(boolean z, boolean z2) {
        boolean completelyVisible = z;
        boolean acceptPartiallyVisible = z2;
        if (!this.mShouldReverseLayout) {
            return findOneVisibleChild(0, getChildCount(), completelyVisible, acceptPartiallyVisible);
        }
        return findOneVisibleChild(getChildCount() - 1, -1, completelyVisible, acceptPartiallyVisible);
    }

    private View findFirstVisibleChildClosestToEnd(boolean z, boolean z2) {
        boolean completelyVisible = z;
        boolean acceptPartiallyVisible = z2;
        if (!this.mShouldReverseLayout) {
            return findOneVisibleChild(getChildCount() - 1, -1, completelyVisible, acceptPartiallyVisible);
        }
        return findOneVisibleChild(0, getChildCount(), completelyVisible, acceptPartiallyVisible);
    }

    private View findReferenceChildClosestToEnd(Recycler recycler, State state) {
        View findFirstReferenceChild;
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (!this.mShouldReverseLayout) {
            findFirstReferenceChild = findLastReferenceChild(recycler2, state2);
        } else {
            findFirstReferenceChild = findFirstReferenceChild(recycler2, state2);
        }
        return findFirstReferenceChild;
    }

    private View findReferenceChildClosestToStart(Recycler recycler, State state) {
        View findLastReferenceChild;
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (!this.mShouldReverseLayout) {
            findLastReferenceChild = findFirstReferenceChild(recycler2, state2);
        } else {
            findLastReferenceChild = findLastReferenceChild(recycler2, state2);
        }
        return findLastReferenceChild;
    }

    private View findFirstReferenceChild(Recycler recycler, State state) {
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        return findReferenceChild(recycler2, state2, 0, getChildCount(), state2.getItemCount());
    }

    private View findLastReferenceChild(Recycler recycler, State state) {
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        return findReferenceChild(recycler2, state2, getChildCount() - 1, -1, state2.getItemCount());
    }

    /* access modifiers changed from: 0000 */
    public View findReferenceChild(Recycler recycler, State state, int i, int i2, int i3) {
        View view;
        int start = i;
        int end = i2;
        int itemCount = i3;
        Recycler recycler2 = recycler;
        State state2 = state;
        int i4 = start;
        int i5 = end;
        int i6 = itemCount;
        ensureLayoutState();
        View invalidMatch = null;
        View outOfBoundsMatch = null;
        int boundsStart = this.mOrientationHelper.getStartAfterPadding();
        int boundsEnd = this.mOrientationHelper.getEndAfterPadding();
        int diff = end <= start ? -1 : 1;
        for (int i7 = start; i7 != end; i7 += diff) {
            View view2 = getChildAt(i7);
            int position = getPosition(view2);
            int position2 = position;
            if (position >= 0 && position2 < itemCount) {
                if (!((LayoutParams) view2.getLayoutParams()).isItemRemoved()) {
                    if (this.mOrientationHelper.getDecoratedStart(view2) < boundsEnd && this.mOrientationHelper.getDecoratedEnd(view2) >= boundsStart) {
                        return view2;
                    }
                    if (outOfBoundsMatch == null) {
                        outOfBoundsMatch = view2;
                    }
                } else if (invalidMatch == null) {
                    invalidMatch = view2;
                }
            }
        }
        if (outOfBoundsMatch == null) {
            view = invalidMatch;
        } else {
            view = outOfBoundsMatch;
        }
        return view;
    }

    public int findFirstVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(0, getChildCount(), false, true);
        return findOneVisibleChild != null ? getPosition(findOneVisibleChild) : -1;
    }

    public int findFirstCompletelyVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(0, getChildCount(), true, false);
        return findOneVisibleChild != null ? getPosition(findOneVisibleChild) : -1;
    }

    public int findLastVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1, false, true);
        return findOneVisibleChild != null ? getPosition(findOneVisibleChild) : -1;
    }

    public int findLastCompletelyVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1, true, false);
        return findOneVisibleChild != null ? getPosition(findOneVisibleChild) : -1;
    }

    /* access modifiers changed from: 0000 */
    public View findOneVisibleChild(int i, int i2, boolean z, boolean z2) {
        int fromIndex = i;
        int toIndex = i2;
        int i3 = fromIndex;
        int i4 = toIndex;
        boolean completelyVisible = z;
        boolean acceptPartiallyVisible = z2;
        ensureLayoutState();
        int start = this.mOrientationHelper.getStartAfterPadding();
        int end = this.mOrientationHelper.getEndAfterPadding();
        int next = toIndex <= fromIndex ? -1 : 1;
        View partiallyVisible = null;
        for (int i5 = fromIndex; i5 != toIndex; i5 += next) {
            View child = getChildAt(i5);
            int childStart = this.mOrientationHelper.getDecoratedStart(child);
            int childEnd = this.mOrientationHelper.getDecoratedEnd(child);
            if (childStart < end && childEnd > start) {
                if (!completelyVisible) {
                    return child;
                }
                if (childStart >= start && childEnd <= end) {
                    return child;
                }
                if (acceptPartiallyVisible && partiallyVisible == null) {
                    partiallyVisible = child;
                }
            }
        }
        return partiallyVisible;
    }

    public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
        View referenceChild;
        View nextFocus;
        int focusDirection = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        View view2 = view;
        int i2 = focusDirection;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        resolveShouldLayoutReverse();
        if (getChildCount() == 0) {
            return null;
        }
        int convertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(focusDirection);
        int layoutDir = convertFocusDirectionToLayoutDirection;
        if (convertFocusDirectionToLayoutDirection == Integer.MIN_VALUE) {
            return null;
        }
        ensureLayoutState();
        if (layoutDir != -1) {
            referenceChild = findReferenceChildClosestToEnd(recycler2, state2);
        } else {
            referenceChild = findReferenceChildClosestToStart(recycler2, state2);
        }
        if (referenceChild == null) {
            return null;
        }
        ensureLayoutState();
        int totalSpace = (int) (MAX_SCROLL_FACTOR * ((float) this.mOrientationHelper.getTotalSpace()));
        int i3 = totalSpace;
        updateLayoutState(layoutDir, totalSpace, false, state2);
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
        this.mLayoutState.mRecycle = false;
        int fill = fill(recycler2, this.mLayoutState, state2, true);
        if (layoutDir != -1) {
            nextFocus = getChildClosestToEnd();
        } else {
            nextFocus = getChildClosestToStart();
        }
        if (nextFocus != referenceChild && nextFocus.isFocusable()) {
            return nextFocus;
        }
        return null;
    }

    private void logChildren() {
        int d = Log.d(TAG, "internal representation of views on the screen");
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int d2 = Log.d(TAG, "item " + getPosition(child) + ", coord:" + this.mOrientationHelper.getDecoratedStart(child));
        }
        int d3 = Log.d(TAG, "==============");
    }

    /* access modifiers changed from: 0000 */
    public void validateChildOrder() {
        int d = Log.d(TAG, "validating child count " + getChildCount());
        if (getChildCount() >= 1) {
            int lastPos = getPosition(getChildAt(0));
            int lastScreenLoc = this.mOrientationHelper.getDecoratedStart(getChildAt(0));
            if (!this.mShouldReverseLayout) {
                int i = 1;
                while (i < getChildCount()) {
                    View child = getChildAt(i);
                    int pos = getPosition(child);
                    int screenLoc = this.mOrientationHelper.getDecoratedStart(child);
                    if (pos < lastPos) {
                        logChildren();
                        throw new RuntimeException("detected invalid position. loc invalid? " + (screenLoc < lastScreenLoc));
                    } else if (screenLoc >= lastScreenLoc) {
                        i++;
                    } else {
                        logChildren();
                        throw new RuntimeException("detected invalid location");
                    }
                }
            } else {
                int i2 = 1;
                while (i2 < getChildCount()) {
                    View child2 = getChildAt(i2);
                    int pos2 = getPosition(child2);
                    int screenLoc2 = this.mOrientationHelper.getDecoratedStart(child2);
                    if (pos2 < lastPos) {
                        logChildren();
                        throw new RuntimeException("detected invalid position. loc invalid? " + (screenLoc2 < lastScreenLoc));
                    } else if (screenLoc2 <= lastScreenLoc) {
                        i2++;
                    } else {
                        logChildren();
                        throw new RuntimeException("detected invalid location");
                    }
                }
            }
        }
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && this.mLastStackFromEnd == this.mStackFromEnd;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void prepareForDrop(View view, View view2, int i, int i2) {
        View view3 = view;
        View target = view2;
        View view4 = view3;
        View view5 = target;
        int i3 = i;
        int i4 = i2;
        assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        ensureLayoutState();
        resolveShouldLayoutReverse();
        int myPos = getPosition(view3);
        int targetPos = getPosition(target);
        char c = myPos >= targetPos ? (char) 65535 : 1;
        if (!this.mShouldReverseLayout) {
            if (c != 65535) {
                scrollToPositionWithOffset(targetPos, this.mOrientationHelper.getDecoratedEnd(target) - this.mOrientationHelper.getDecoratedMeasurement(view3));
            } else {
                scrollToPositionWithOffset(targetPos, this.mOrientationHelper.getDecoratedStart(target));
            }
        } else if (c != 1) {
            scrollToPositionWithOffset(targetPos, this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(target));
        } else {
            scrollToPositionWithOffset(targetPos, this.mOrientationHelper.getEndAfterPadding() - (this.mOrientationHelper.getDecoratedStart(target) + this.mOrientationHelper.getDecoratedMeasurement(view3)));
        }
    }
}
