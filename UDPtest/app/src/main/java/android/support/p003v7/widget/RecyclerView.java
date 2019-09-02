package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.p000v4.media.TransportMediator;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.p002os.TraceCompat;
import android.support.p000v4.view.AbsSavedState;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.NestedScrollingChild;
import android.support.p000v4.view.NestedScrollingChildHelper;
import android.support.p000v4.view.ScrollingView;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.accessibility.AccessibilityEventCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityRecordCompat;
import android.support.p000v4.widget.EdgeEffectCompat;
import android.support.p000v4.widget.ScrollerCompat;
import android.support.p003v7.recyclerview.C0271R;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: android.support.v7.widget.RecyclerView */
public class RecyclerView extends ViewGroup implements ScrollingView, NestedScrollingChild {
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC = (VERSION.SDK_INT >= 23);
    /* access modifiers changed from: private */
    public static final boolean ALLOW_THREAD_GAP_WORK = (VERSION.SDK_INT >= 21);
    private static final int[] CLIP_TO_PADDING_ATTR;
    static final boolean DEBUG = false;
    static final boolean DISPATCH_TEMP_DETACH = false;
    private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION = (VERSION.SDK_INT <= 15);
    static final boolean FORCE_INVALIDATE_DISPLAY_LIST = (VERSION.SDK_INT == 18 || VERSION.SDK_INT == 19 || VERSION.SDK_INT == 20);
    static final long FOREVER_NS = Long.MAX_VALUE;
    public static final int HORIZONTAL = 0;
    private static final boolean IGNORE_DETACHED_FOCUSED_CHILD;
    private static final int INVALID_POINTER = -1;
    public static final int INVALID_TYPE = -1;
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
    static final int MAX_SCROLL_DURATION = 2000;
    private static final int[] NESTED_SCROLLING_ATTRS;
    public static final long NO_ID = -1;
    public static final int NO_POSITION = -1;
    static final boolean POST_UPDATES_ON_ANIMATION = (VERSION.SDK_INT >= 16);
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    static final String TAG = "RecyclerView";
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
    static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
    private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
    static final String TRACE_NESTED_PREFETCH_TAG = "RV Nested Prefetch";
    private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
    private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
    static final String TRACE_PREFETCH_TAG = "RV Prefetch";
    static final String TRACE_SCROLL_TAG = "RV Scroll";
    public static final int VERTICAL = 1;
    static final Interpolator sQuinticInterpolator = new Interpolator() {
        public float getInterpolation(float f) {
            float t = f;
            float f2 = t;
            float f3 = t - 1.0f;
            float t2 = f3;
            return (f3 * t2 * t2 * t2 * t2) + 1.0f;
        }
    };
    RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    private final AccessibilityManager mAccessibilityManager;
    private OnItemTouchListener mActiveOnItemTouchListener;
    Adapter mAdapter;
    AdapterHelper mAdapterHelper;
    boolean mAdapterUpdateDuringMeasure;
    private EdgeEffectCompat mBottomGlow;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback;
    ChildHelper mChildHelper;
    boolean mClipToPadding;
    boolean mDataSetHasChangedAfterLayout;
    private int mDispatchScrollCounter;
    private int mEatRequestLayout;
    private int mEatenAccessibilityChangeFlags;
    @VisibleForTesting
    boolean mFirstLayoutComplete;
    GapWorker mGapWorker;
    boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    boolean mIsAttached;
    ItemAnimator mItemAnimator;
    private ItemAnimatorListener mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    final ArrayList<ItemDecoration> mItemDecorations;
    boolean mItemsAddedOrRemoved;
    boolean mItemsChanged;
    private int mLastTouchX;
    private int mLastTouchY;
    @VisibleForTesting
    LayoutManager mLayout;
    boolean mLayoutFrozen;
    private int mLayoutOrScrollCounter;
    boolean mLayoutRequestEaten;
    private EdgeEffectCompat mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final RecyclerViewDataObserver mObserver;
    private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
    private OnFlingListener mOnFlingListener;
    private final ArrayList<OnItemTouchListener> mOnItemTouchListeners;
    @VisibleForTesting
    final List<ViewHolder> mPendingAccessibilityImportanceChange;
    private SavedState mPendingSavedState;
    boolean mPostedAnimatorRunner;
    LayoutPrefetchRegistryImpl mPrefetchRegistry;
    private boolean mPreserveFocusAfterLayout;
    final Recycler mRecycler;
    RecyclerListener mRecyclerListener;
    private EdgeEffectCompat mRightGlow;
    private final int[] mScrollConsumed;
    private float mScrollFactor;
    private OnScrollListener mScrollListener;
    private List<OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId;
    private int mScrollState;
    private NestedScrollingChildHelper mScrollingChildHelper;
    final State mState;
    final Rect mTempRect;
    private final Rect mTempRect2;
    final RectF mTempRectF;
    private EdgeEffectCompat mTopGlow;
    private int mTouchSlop;
    final Runnable mUpdateChildViewsRunnable;
    private VelocityTracker mVelocityTracker;
    final ViewFlinger mViewFlinger;
    private final ProcessCallback mViewInfoProcessCallback;
    final ViewInfoStore mViewInfoStore;

    /* renamed from: android.support.v7.widget.RecyclerView$Adapter */
    public static abstract class Adapter<VH extends ViewHolder> {
        private boolean mHasStableIds = false;
        private final AdapterDataObservable mObservable = new AdapterDataObservable();

        public abstract int getItemCount();

        public abstract void onBindViewHolder(VH vh, int i);

        public abstract VH onCreateViewHolder(ViewGroup viewGroup, int i);

        public Adapter() {
        }

        public void onBindViewHolder(VH vh, int i, List<Object> list) {
            VH holder = vh;
            int position = i;
            VH vh2 = holder;
            int i2 = position;
            List<Object> list2 = list;
            onBindViewHolder(holder, position);
        }

        public final VH createViewHolder(ViewGroup viewGroup, int i) {
            ViewGroup parent = viewGroup;
            int viewType = i;
            ViewGroup viewGroup2 = parent;
            int i2 = viewType;
            TraceCompat.beginSection(RecyclerView.TRACE_CREATE_VIEW_TAG);
            ViewHolder onCreateViewHolder = onCreateViewHolder(parent, viewType);
            ViewHolder viewHolder = onCreateViewHolder;
            onCreateViewHolder.mItemViewType = viewType;
            TraceCompat.endSection();
            return viewHolder;
        }

        public final void bindViewHolder(VH vh, int i) {
            VH holder = vh;
            int position = i;
            VH vh2 = holder;
            int i2 = position;
            holder.mPosition = position;
            if (hasStableIds()) {
                holder.mItemId = getItemId(position);
            }
            holder.setFlags(1, 519);
            TraceCompat.beginSection(RecyclerView.TRACE_BIND_VIEW_TAG);
            onBindViewHolder(holder, position, holder.getUnmodifiedPayloads());
            holder.clearPayload();
            android.view.ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            android.view.ViewGroup.LayoutParams layoutParams2 = layoutParams;
            if (layoutParams instanceof LayoutParams) {
                ((LayoutParams) layoutParams2).mInsetsDirty = true;
            }
            TraceCompat.endSection();
        }

        public int getItemViewType(int i) {
            int i2 = i;
            return 0;
        }

        public void setHasStableIds(boolean z) {
            boolean hasStableIds = z;
            if (!hasObservers()) {
                this.mHasStableIds = hasStableIds;
                return;
            }
            throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
        }

        public long getItemId(int i) {
            int i2 = i;
            return -1;
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public void onViewRecycled(VH vh) {
            VH vh2 = vh;
        }

        public boolean onFailedToRecycleView(VH vh) {
            VH vh2 = vh;
            return false;
        }

        public void onViewAttachedToWindow(VH vh) {
            VH vh2 = vh;
        }

        public void onViewDetachedFromWindow(VH vh) {
            VH vh2 = vh;
        }

        public final boolean hasObservers() {
            return this.mObservable.hasObservers();
        }

        public void registerAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            AdapterDataObserver observer = adapterDataObserver;
            AdapterDataObserver adapterDataObserver2 = observer;
            this.mObservable.registerObserver(observer);
        }

        public void unregisterAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            AdapterDataObserver observer = adapterDataObserver;
            AdapterDataObserver adapterDataObserver2 = observer;
            this.mObservable.unregisterObserver(observer);
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            RecyclerView recyclerView2 = recyclerView;
        }

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            RecyclerView recyclerView2 = recyclerView;
        }

        public final void notifyDataSetChanged() {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int i) {
            int position = i;
            int i2 = position;
            this.mObservable.notifyItemRangeChanged(position, 1);
        }

        public final void notifyItemChanged(int i, Object obj) {
            int position = i;
            Object payload = obj;
            int i2 = position;
            Object obj2 = payload;
            this.mObservable.notifyItemRangeChanged(position, 1, payload);
        }

        public final void notifyItemRangeChanged(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            this.mObservable.notifyItemRangeChanged(positionStart, itemCount);
        }

        public final void notifyItemRangeChanged(int i, int i2, Object obj) {
            int positionStart = i;
            int itemCount = i2;
            Object payload = obj;
            int i3 = positionStart;
            int i4 = itemCount;
            Object obj2 = payload;
            this.mObservable.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        public final void notifyItemInserted(int i) {
            int position = i;
            int i2 = position;
            this.mObservable.notifyItemRangeInserted(position, 1);
        }

        public final void notifyItemMoved(int i, int i2) {
            int fromPosition = i;
            int toPosition = i2;
            int i3 = fromPosition;
            int i4 = toPosition;
            this.mObservable.notifyItemMoved(fromPosition, toPosition);
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            this.mObservable.notifyItemRangeInserted(positionStart, itemCount);
        }

        public final void notifyItemRemoved(int i) {
            int position = i;
            int i2 = position;
            this.mObservable.notifyItemRangeRemoved(position, 1);
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            this.mObservable.notifyItemRangeRemoved(positionStart, itemCount);
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$AdapterDataObservable */
    static class AdapterDataObservable extends Observable<AdapterDataObserver> {
        AdapterDataObservable() {
        }

        public boolean hasObservers() {
            return !this.mObservers.isEmpty();
        }

        public void notifyChanged() {
            for (int i = this.mObservers.size() - 1; i >= 0; i--) {
                ((AdapterDataObserver) this.mObservers.get(i)).onChanged();
            }
        }

        public void notifyItemRangeChanged(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            notifyItemRangeChanged(positionStart, itemCount, null);
        }

        public void notifyItemRangeChanged(int i, int i2, Object obj) {
            int positionStart = i;
            int itemCount = i2;
            Object payload = obj;
            int i3 = positionStart;
            int i4 = itemCount;
            Object obj2 = payload;
            for (int i5 = this.mObservers.size() - 1; i5 >= 0; i5--) {
                ((AdapterDataObserver) this.mObservers.get(i5)).onItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        public void notifyItemRangeInserted(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            for (int i5 = this.mObservers.size() - 1; i5 >= 0; i5--) {
                ((AdapterDataObserver) this.mObservers.get(i5)).onItemRangeInserted(positionStart, itemCount);
            }
        }

        public void notifyItemRangeRemoved(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            for (int i5 = this.mObservers.size() - 1; i5 >= 0; i5--) {
                ((AdapterDataObserver) this.mObservers.get(i5)).onItemRangeRemoved(positionStart, itemCount);
            }
        }

        public void notifyItemMoved(int i, int i2) {
            int fromPosition = i;
            int toPosition = i2;
            int i3 = fromPosition;
            int i4 = toPosition;
            for (int i5 = this.mObservers.size() - 1; i5 >= 0; i5--) {
                ((AdapterDataObserver) this.mObservers.get(i5)).onItemRangeMoved(fromPosition, toPosition, 1);
            }
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$AdapterDataObserver */
    public static abstract class AdapterDataObserver {
        public AdapterDataObserver() {
        }

        public void onChanged() {
        }

        public void onItemRangeChanged(int i, int i2) {
            int i3 = i;
            int i4 = i2;
        }

        public void onItemRangeChanged(int i, int i2, Object obj) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            Object obj2 = obj;
            onItemRangeChanged(positionStart, itemCount);
        }

        public void onItemRangeInserted(int i, int i2) {
            int i3 = i;
            int i4 = i2;
        }

        public void onItemRangeRemoved(int i, int i2) {
            int i3 = i;
            int i4 = i2;
        }

        public void onItemRangeMoved(int i, int i2, int i3) {
            int i4 = i;
            int i5 = i2;
            int i6 = i3;
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$ChildDrawingOrderCallback */
    public interface ChildDrawingOrderCallback {
        int onGetChildDrawingOrder(int i, int i2);
    }

    /* renamed from: android.support.v7.widget.RecyclerView$ItemAnimator */
    public static abstract class ItemAnimator {
        public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        public static final int FLAG_CHANGED = 2;
        public static final int FLAG_INVALIDATED = 4;
        public static final int FLAG_MOVED = 2048;
        public static final int FLAG_REMOVED = 8;
        private long mAddDuration = 120;
        private long mChangeDuration = 250;
        private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList<>();
        private ItemAnimatorListener mListener = null;
        private long mMoveDuration = 250;
        private long mRemoveDuration = 120;

        @Retention(RetentionPolicy.SOURCE)
        /* renamed from: android.support.v7.widget.RecyclerView$ItemAnimator$AdapterChanges */
        public @interface AdapterChanges {
        }

        /* renamed from: android.support.v7.widget.RecyclerView$ItemAnimator$ItemAnimatorFinishedListener */
        public interface ItemAnimatorFinishedListener {
            void onAnimationsFinished();
        }

        /* renamed from: android.support.v7.widget.RecyclerView$ItemAnimator$ItemAnimatorListener */
        interface ItemAnimatorListener {
            void onAnimationFinished(ViewHolder viewHolder);
        }

        /* renamed from: android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo */
        public static class ItemHolderInfo {
            public int bottom;
            public int changeFlags;
            public int left;
            public int right;
            public int top;

            public ItemHolderInfo() {
            }

            public ItemHolderInfo setFrom(ViewHolder viewHolder) {
                ViewHolder holder = viewHolder;
                ViewHolder viewHolder2 = holder;
                return setFrom(holder, 0);
            }

            public ItemHolderInfo setFrom(ViewHolder viewHolder, int i) {
                ViewHolder holder = viewHolder;
                ViewHolder viewHolder2 = holder;
                int i2 = i;
                View view = holder.itemView;
                this.left = view.getLeft();
                this.top = view.getTop();
                this.right = view.getRight();
                this.bottom = view.getBottom();
                return this;
            }
        }

        public abstract boolean animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateChange(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2);

        public abstract boolean animatePersistence(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public abstract void endAnimation(ViewHolder viewHolder);

        public abstract void endAnimations();

        public abstract boolean isRunning();

        public abstract void runPendingAnimations();

        public ItemAnimator() {
        }

        public long getMoveDuration() {
            return this.mMoveDuration;
        }

        public void setMoveDuration(long j) {
            long moveDuration = j;
            long j2 = moveDuration;
            this.mMoveDuration = moveDuration;
        }

        public long getAddDuration() {
            return this.mAddDuration;
        }

        public void setAddDuration(long j) {
            long addDuration = j;
            long j2 = addDuration;
            this.mAddDuration = addDuration;
        }

        public long getRemoveDuration() {
            return this.mRemoveDuration;
        }

        public void setRemoveDuration(long j) {
            long removeDuration = j;
            long j2 = removeDuration;
            this.mRemoveDuration = removeDuration;
        }

        public long getChangeDuration() {
            return this.mChangeDuration;
        }

        public void setChangeDuration(long j) {
            long changeDuration = j;
            long j2 = changeDuration;
            this.mChangeDuration = changeDuration;
        }

        /* access modifiers changed from: 0000 */
        public void setListener(ItemAnimatorListener itemAnimatorListener) {
            ItemAnimatorListener listener = itemAnimatorListener;
            ItemAnimatorListener itemAnimatorListener2 = listener;
            this.mListener = listener;
        }

        @NonNull
        public ItemHolderInfo recordPreLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder, int i, @NonNull List<Object> list) {
            ViewHolder viewHolder2 = viewHolder;
            State state2 = state;
            ViewHolder viewHolder3 = viewHolder2;
            int i2 = i;
            List<Object> list2 = list;
            return obtainHolderInfo().setFrom(viewHolder2);
        }

        @NonNull
        public ItemHolderInfo recordPostLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder) {
            ViewHolder viewHolder2 = viewHolder;
            State state2 = state;
            ViewHolder viewHolder3 = viewHolder2;
            return obtainHolderInfo().setFrom(viewHolder2);
        }

        static int buildAdapterChangeFlagsForAnimations(ViewHolder viewHolder) {
            ViewHolder viewHolder2 = viewHolder;
            ViewHolder viewHolder3 = viewHolder2;
            int flags = ViewHolder.access$1400(viewHolder2) & 14;
            if (viewHolder2.isInvalid()) {
                return 4;
            }
            if ((flags & 4) == 0) {
                int oldPos = viewHolder2.getOldPosition();
                int pos = viewHolder2.getAdapterPosition();
                if (!(oldPos == -1 || pos == -1 || oldPos == pos)) {
                    flags |= 2048;
                }
            }
            return flags;
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            ViewHolder viewHolder2 = viewHolder;
            ViewHolder viewHolder3 = viewHolder2;
            onAnimationFinished(viewHolder2);
            if (this.mListener != null) {
                this.mListener.onAnimationFinished(viewHolder2);
            }
        }

        public void onAnimationFinished(ViewHolder viewHolder) {
            ViewHolder viewHolder2 = viewHolder;
        }

        public final void dispatchAnimationStarted(ViewHolder viewHolder) {
            ViewHolder viewHolder2 = viewHolder;
            ViewHolder viewHolder3 = viewHolder2;
            onAnimationStarted(viewHolder2);
        }

        public void onAnimationStarted(ViewHolder viewHolder) {
            ViewHolder viewHolder2 = viewHolder;
        }

        public final boolean isRunning(ItemAnimatorFinishedListener itemAnimatorFinishedListener) {
            ItemAnimatorFinishedListener listener = itemAnimatorFinishedListener;
            ItemAnimatorFinishedListener itemAnimatorFinishedListener2 = listener;
            boolean isRunning = isRunning();
            boolean running = isRunning;
            if (listener != null) {
                if (isRunning) {
                    boolean add = this.mFinishedListeners.add(listener);
                } else {
                    listener.onAnimationsFinished();
                }
            }
            return running;
        }

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder) {
            ViewHolder viewHolder2 = viewHolder;
            return true;
        }

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> list) {
            ViewHolder viewHolder2 = viewHolder;
            ViewHolder viewHolder3 = viewHolder2;
            List<Object> list2 = list;
            return canReuseUpdatedViewHolder(viewHolder2);
        }

        public final void dispatchAnimationsFinished() {
            int count = this.mFinishedListeners.size();
            for (int i = 0; i < count; i++) {
                ((ItemAnimatorFinishedListener) this.mFinishedListeners.get(i)).onAnimationsFinished();
            }
            this.mFinishedListeners.clear();
        }

        public ItemHolderInfo obtainHolderInfo() {
            return new ItemHolderInfo();
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$ItemAnimatorRestoreListener */
    private class ItemAnimatorRestoreListener implements ItemAnimatorListener {
        final /* synthetic */ RecyclerView this$0;

        ItemAnimatorRestoreListener(RecyclerView recyclerView) {
            this.this$0 = recyclerView;
        }

        public void onAnimationFinished(ViewHolder viewHolder) {
            ViewHolder item = viewHolder;
            ViewHolder viewHolder2 = item;
            item.setIsRecyclable(true);
            if (item.mShadowedHolder != null && item.mShadowingHolder == null) {
                item.mShadowedHolder = null;
            }
            item.mShadowingHolder = null;
            if (!ViewHolder.access$1300(item) && !this.this$0.removeAnimatingView(item.itemView) && item.isTmpDetached()) {
                this.this$0.removeDetachedView(item.itemView, false);
            }
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$ItemDecoration */
    public static abstract class ItemDecoration {
        public ItemDecoration() {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
            Canvas c = canvas;
            RecyclerView parent = recyclerView;
            Canvas canvas2 = c;
            RecyclerView recyclerView2 = parent;
            State state2 = state;
            onDraw(c, parent);
        }

        @Deprecated
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
            Canvas canvas2 = canvas;
            RecyclerView recyclerView2 = recyclerView;
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
            Canvas c = canvas;
            RecyclerView parent = recyclerView;
            Canvas canvas2 = c;
            RecyclerView recyclerView2 = parent;
            State state2 = state;
            onDrawOver(c, parent);
        }

        @Deprecated
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
            Canvas canvas2 = canvas;
            RecyclerView recyclerView2 = recyclerView;
        }

        @Deprecated
        public void getItemOffsets(Rect rect, int i, RecyclerView recyclerView) {
            Rect outRect = rect;
            Rect rect2 = outRect;
            int i2 = i;
            RecyclerView recyclerView2 = recyclerView;
            outRect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            Rect outRect = rect;
            View view2 = view;
            RecyclerView parent = recyclerView;
            Rect rect2 = outRect;
            View view3 = view2;
            RecyclerView recyclerView2 = parent;
            State state2 = state;
            getItemOffsets(outRect, ((LayoutParams) view2.getLayoutParams()).getViewLayoutPosition(), parent);
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$LayoutManager */
    public static abstract class LayoutManager {
        boolean mAutoMeasure = false;
        ChildHelper mChildHelper;
        private int mHeight;
        private int mHeightMode;
        boolean mIsAttachedToWindow = false;
        private boolean mItemPrefetchEnabled = true;
        private boolean mMeasurementCacheEnabled = true;
        int mPrefetchMaxCountObserved;
        boolean mPrefetchMaxObservedInInitialPrefetch;
        RecyclerView mRecyclerView;
        boolean mRequestedSimpleAnimations = false;
        @Nullable
        SmoothScroller mSmoothScroller;
        private int mWidth;
        private int mWidthMode;

        /* renamed from: android.support.v7.widget.RecyclerView$LayoutManager$LayoutPrefetchRegistry */
        public interface LayoutPrefetchRegistry {
            void addPosition(int i, int i2);
        }

        /* renamed from: android.support.v7.widget.RecyclerView$LayoutManager$Properties */
        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;

            public Properties() {
            }
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public LayoutManager() {
        }

        static /* synthetic */ void access$1200(LayoutManager layoutManager, SmoothScroller smoothScroller) {
            LayoutManager x0 = layoutManager;
            SmoothScroller x1 = smoothScroller;
            LayoutManager layoutManager2 = x0;
            SmoothScroller smoothScroller2 = x1;
            x0.onSmoothScrollerStopped(x1);
        }

        /* access modifiers changed from: 0000 */
        public void setRecyclerView(RecyclerView recyclerView) {
            RecyclerView recyclerView2 = recyclerView;
            RecyclerView recyclerView3 = recyclerView2;
            if (recyclerView2 != null) {
                this.mRecyclerView = recyclerView2;
                this.mChildHelper = recyclerView2.mChildHelper;
                this.mWidth = recyclerView2.getWidth();
                this.mHeight = recyclerView2.getHeight();
            } else {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidth = 0;
                this.mHeight = 0;
            }
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        /* access modifiers changed from: 0000 */
        public void setMeasureSpecs(int i, int i2) {
            int wSpec = i;
            int hSpec = i2;
            int i3 = wSpec;
            int i4 = hSpec;
            this.mWidth = MeasureSpec.getSize(wSpec);
            this.mWidthMode = MeasureSpec.getMode(wSpec);
            if (this.mWidthMode == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mWidth = 0;
            }
            this.mHeight = MeasureSpec.getSize(hSpec);
            this.mHeightMode = MeasureSpec.getMode(hSpec);
            if (this.mHeightMode == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mHeight = 0;
            }
        }

        /* access modifiers changed from: 0000 */
        public void setMeasuredDimensionFromChildren(int i, int i2) {
            int widthSpec = i;
            int heightSpec = i2;
            int i3 = widthSpec;
            int i4 = heightSpec;
            int childCount = getChildCount();
            int count = childCount;
            if (childCount != 0) {
                int minX = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                int minY = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                int maxX = Integer.MIN_VALUE;
                int maxY = Integer.MIN_VALUE;
                for (int i5 = 0; i5 < count; i5++) {
                    View child = getChildAt(i5);
                    Rect bounds = this.mRecyclerView.mTempRect;
                    getDecoratedBoundsWithMargins(child, bounds);
                    if (bounds.left < minX) {
                        minX = bounds.left;
                    }
                    if (bounds.right > maxX) {
                        maxX = bounds.right;
                    }
                    if (bounds.top < minY) {
                        minY = bounds.top;
                    }
                    if (bounds.bottom > maxY) {
                        maxY = bounds.bottom;
                    }
                }
                this.mRecyclerView.mTempRect.set(minX, minY, maxX, maxY);
                setMeasuredDimension(this.mRecyclerView.mTempRect, widthSpec, heightSpec);
                return;
            }
            this.mRecyclerView.defaultOnMeasure(widthSpec, heightSpec);
        }

        public void setMeasuredDimension(Rect rect, int i, int i2) {
            Rect childrenBounds = rect;
            int wSpec = i;
            int hSpec = i2;
            Rect rect2 = childrenBounds;
            int i3 = wSpec;
            int i4 = hSpec;
            int usedHeight = childrenBounds.height() + getPaddingTop() + getPaddingBottom();
            int width = chooseSize(wSpec, childrenBounds.width() + getPaddingLeft() + getPaddingRight(), getMinimumWidth());
            int chooseSize = chooseSize(hSpec, usedHeight, getMinimumHeight());
            int i5 = chooseSize;
            setMeasuredDimension(width, chooseSize);
        }

        public void requestLayout() {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.requestLayout();
            }
        }

        public void assertInLayoutOrScroll(String str) {
            String message = str;
            String str2 = message;
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertInLayoutOrScroll(message);
            }
        }

        public static int chooseSize(int i, int i2, int i3) {
            int spec = i;
            int desired = i2;
            int min = i3;
            int i4 = spec;
            int i5 = desired;
            int i6 = min;
            int mode = MeasureSpec.getMode(spec);
            int size = MeasureSpec.getSize(spec);
            switch (mode) {
                case Integer.MIN_VALUE:
                    return Math.min(size, Math.max(desired, min));
                case 1073741824:
                    return size;
                default:
                    return Math.max(desired, min);
            }
        }

        public void assertNotInLayoutOrScroll(String str) {
            String message = str;
            String str2 = message;
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertNotInLayoutOrScroll(message);
            }
        }

        public void setAutoMeasureEnabled(boolean z) {
            this.mAutoMeasure = z;
        }

        public boolean isAutoMeasureEnabled() {
            return this.mAutoMeasure;
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public final void setItemPrefetchEnabled(boolean z) {
            boolean enabled = z;
            if (enabled != this.mItemPrefetchEnabled) {
                this.mItemPrefetchEnabled = enabled;
                this.mPrefetchMaxCountObserved = 0;
                if (this.mRecyclerView != null) {
                    this.mRecyclerView.mRecycler.updateViewCacheSize();
                }
            }
        }

        public final boolean isItemPrefetchEnabled() {
            return this.mItemPrefetchEnabled;
        }

        public void collectAdjacentPrefetchPositions(int i, int i2, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
            int i3 = i;
            int i4 = i2;
            State state2 = state;
            LayoutPrefetchRegistry layoutPrefetchRegistry2 = layoutPrefetchRegistry;
        }

        public void collectInitialPrefetchPositions(int i, LayoutPrefetchRegistry layoutPrefetchRegistry) {
            int i2 = i;
            LayoutPrefetchRegistry layoutPrefetchRegistry2 = layoutPrefetchRegistry;
        }

        /* access modifiers changed from: 0000 */
        public void dispatchAttachedToWindow(RecyclerView recyclerView) {
            RecyclerView view = recyclerView;
            RecyclerView recyclerView2 = view;
            this.mIsAttachedToWindow = true;
            onAttachedToWindow(view);
        }

        /* access modifiers changed from: 0000 */
        public void dispatchDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            RecyclerView view = recyclerView;
            Recycler recycler2 = recycler;
            RecyclerView recyclerView2 = view;
            Recycler recycler3 = recycler2;
            this.mIsAttachedToWindow = false;
            onDetachedFromWindow(view, recycler2);
        }

        public boolean isAttachedToWindow() {
            return this.mIsAttachedToWindow;
        }

        public void postOnAnimation(Runnable runnable) {
            Runnable action = runnable;
            Runnable runnable2 = action;
            if (this.mRecyclerView != null) {
                ViewCompat.postOnAnimation(this.mRecyclerView, action);
            }
        }

        public boolean removeCallbacks(Runnable runnable) {
            Runnable action = runnable;
            Runnable runnable2 = action;
            if (this.mRecyclerView == null) {
                return false;
            }
            return this.mRecyclerView.removeCallbacks(action);
        }

        @CallSuper
        public void onAttachedToWindow(RecyclerView recyclerView) {
            RecyclerView recyclerView2 = recyclerView;
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView recyclerView) {
            RecyclerView recyclerView2 = recyclerView;
        }

        @CallSuper
        public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            RecyclerView view = recyclerView;
            RecyclerView recyclerView2 = view;
            Recycler recycler2 = recycler;
            onDetachedFromWindow(view);
        }

        public boolean getClipToPadding() {
            return this.mRecyclerView != null && this.mRecyclerView.mClipToPadding;
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Recycler recycler2 = recycler;
            State state2 = state;
            int e = Log.e(RecyclerView.TAG, "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onLayoutCompleted(State state) {
            State state2 = state;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            LayoutParams lp = layoutParams;
            LayoutParams layoutParams2 = lp;
            return lp != null;
        }

        public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.view.ViewGroup.LayoutParams lp = layoutParams;
            android.view.ViewGroup.LayoutParams layoutParams2 = lp;
            if (lp instanceof LayoutParams) {
                return new LayoutParams((LayoutParams) lp);
            }
            if (!(lp instanceof MarginLayoutParams)) {
                return new LayoutParams(lp);
            }
            return new LayoutParams((MarginLayoutParams) lp);
        }

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            Context c = context;
            AttributeSet attrs = attributeSet;
            Context context2 = c;
            AttributeSet attributeSet2 = attrs;
            return new LayoutParams(c, attrs);
        }

        public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
            int i2 = i;
            Recycler recycler2 = recycler;
            State state2 = state;
            return 0;
        }

        public int scrollVerticallyBy(int i, Recycler recycler, State state) {
            int i2 = i;
            Recycler recycler2 = recycler;
            State state2 = state;
            return 0;
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public void scrollToPosition(int i) {
            int i2 = i;
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
            RecyclerView recyclerView2 = recyclerView;
            State state2 = state;
            int i2 = i;
            int e = Log.e(RecyclerView.TAG, "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            SmoothScroller smoothScroller2 = smoothScroller;
            SmoothScroller smoothScroller3 = smoothScroller2;
            if (!(this.mSmoothScroller == null || smoothScroller2 == this.mSmoothScroller || !this.mSmoothScroller.isRunning())) {
                this.mSmoothScroller.stop();
            }
            this.mSmoothScroller = smoothScroller2;
            this.mSmoothScroller.start(this.mRecyclerView, this);
        }

        public boolean isSmoothScrolling() {
            return this.mSmoothScroller != null && this.mSmoothScroller.isRunning();
        }

        public int getLayoutDirection() {
            return ViewCompat.getLayoutDirection(this.mRecyclerView);
        }

        public void endAnimation(View view) {
            View view2 = view;
            View view3 = view2;
            if (this.mRecyclerView.mItemAnimator != null) {
                this.mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt(view2));
            }
        }

        public void addDisappearingView(View view) {
            View child = view;
            View view2 = child;
            addDisappearingView(child, -1);
        }

        public void addDisappearingView(View view, int i) {
            View child = view;
            int index = i;
            View view2 = child;
            int i2 = index;
            addViewInt(child, index, true);
        }

        public void addView(View view) {
            View child = view;
            View view2 = child;
            addView(child, -1);
        }

        public void addView(View view, int i) {
            View child = view;
            int index = i;
            View view2 = child;
            int i2 = index;
            addViewInt(child, index, false);
        }

        private void addViewInt(View view, int i, boolean z) {
            View child = view;
            int index = i;
            View view2 = child;
            int index2 = index;
            boolean disappearing = z;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(child);
            ViewHolder holder = childViewHolderInt;
            if (!disappearing && !childViewHolderInt.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(holder);
            } else {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(holder);
            }
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (holder.wasReturnedFromScrap() || holder.isScrap()) {
                if (!holder.isScrap()) {
                    holder.clearReturnedFromScrapFlag();
                } else {
                    holder.unScrap();
                }
                this.mChildHelper.attachViewToParent(child, index, child.getLayoutParams(), false);
            } else if (child.getParent() != this.mRecyclerView) {
                this.mChildHelper.addView(child, index, false);
                lp.mInsetsDirty = true;
                if (this.mSmoothScroller != null && this.mSmoothScroller.isRunning()) {
                    this.mSmoothScroller.onChildAttachedToWindow(child);
                }
            } else {
                int currentIndex = this.mChildHelper.indexOfChild(child);
                if (index == -1) {
                    index2 = this.mChildHelper.getChildCount();
                }
                if (currentIndex == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.mRecyclerView.indexOfChild(child));
                } else if (currentIndex != index2) {
                    this.mRecyclerView.mLayout.moveView(currentIndex, index2);
                }
            }
            if (lp.mPendingInvalidate) {
                holder.itemView.invalidate();
                lp.mPendingInvalidate = false;
            }
        }

        public void removeView(View view) {
            View child = view;
            View view2 = child;
            this.mChildHelper.removeView(child);
        }

        public void removeViewAt(int i) {
            int index = i;
            int i2 = index;
            View childAt = getChildAt(index);
            View view = childAt;
            if (childAt != null) {
                this.mChildHelper.removeViewAt(index);
            }
        }

        public void removeAllViews() {
            int childCount = getChildCount();
            int i = childCount;
            for (int i2 = childCount - 1; i2 >= 0; i2--) {
                this.mChildHelper.removeViewAt(i2);
            }
        }

        public int getBaseline() {
            return -1;
        }

        public int getPosition(View view) {
            View view2 = view;
            View view3 = view2;
            return ((LayoutParams) view2.getLayoutParams()).getViewLayoutPosition();
        }

        public int getItemViewType(View view) {
            View view2 = view;
            View view3 = view2;
            return RecyclerView.getChildViewHolderInt(view2).getItemViewType();
        }

        @Nullable
        public View findContainingItemView(View view) {
            View view2 = view;
            View view3 = view2;
            if (this.mRecyclerView == null) {
                return null;
            }
            View findContainingItemView = this.mRecyclerView.findContainingItemView(view2);
            View found = findContainingItemView;
            if (findContainingItemView == null) {
                return null;
            }
            if (!this.mChildHelper.isHidden(found)) {
                return found;
            }
            return null;
        }

        public View findViewByPosition(int i) {
            int position = i;
            int i2 = position;
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                View child = childAt;
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(childAt);
                ViewHolder vh = childViewHolderInt;
                if (childViewHolderInt != null && vh.getLayoutPosition() == position && !vh.shouldIgnore() && (this.mRecyclerView.mState.isPreLayout() || !vh.isRemoved())) {
                    return child;
                }
            }
            return null;
        }

        public void detachView(View view) {
            View child = view;
            View view2 = child;
            int indexOfChild = this.mChildHelper.indexOfChild(child);
            int ind = indexOfChild;
            if (indexOfChild >= 0) {
                detachViewInternal(ind, child);
            }
        }

        public void detachViewAt(int i) {
            int index = i;
            int i2 = index;
            detachViewInternal(index, getChildAt(index));
        }

        private void detachViewInternal(int i, View view) {
            int index = i;
            int i2 = index;
            View view2 = view;
            this.mChildHelper.detachViewFromParent(index);
        }

        public void attachView(View view, int i, LayoutParams layoutParams) {
            View child = view;
            int index = i;
            LayoutParams lp = layoutParams;
            View view2 = child;
            int i2 = index;
            LayoutParams layoutParams2 = lp;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(child);
            ViewHolder vh = childViewHolderInt;
            if (!childViewHolderInt.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(vh);
            } else {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(vh);
            }
            this.mChildHelper.attachViewToParent(child, index, lp, vh.isRemoved());
        }

        public void attachView(View view, int i) {
            View child = view;
            int index = i;
            View view2 = child;
            int i2 = index;
            attachView(child, index, (LayoutParams) child.getLayoutParams());
        }

        public void attachView(View view) {
            View child = view;
            View view2 = child;
            attachView(child, -1);
        }

        public void removeDetachedView(View view) {
            View child = view;
            View view2 = child;
            this.mRecyclerView.removeDetachedView(child, false);
        }

        public void moveView(int i, int i2) {
            int fromIndex = i;
            int toIndex = i2;
            int i3 = fromIndex;
            int i4 = toIndex;
            View childAt = getChildAt(fromIndex);
            View view = childAt;
            if (childAt != null) {
                detachViewAt(fromIndex);
                attachView(view, toIndex);
                return;
            }
            throw new IllegalArgumentException("Cannot move a child from non-existing index:" + fromIndex);
        }

        public void detachAndScrapView(View view, Recycler recycler) {
            View child = view;
            Recycler recycler2 = recycler;
            View view2 = child;
            Recycler recycler3 = recycler2;
            int indexOfChild = this.mChildHelper.indexOfChild(child);
            int i = indexOfChild;
            scrapOrRecycleView(recycler2, indexOfChild, child);
        }

        public void detachAndScrapViewAt(int i, Recycler recycler) {
            int index = i;
            Recycler recycler2 = recycler;
            int i2 = index;
            Recycler recycler3 = recycler2;
            scrapOrRecycleView(recycler2, index, getChildAt(index));
        }

        public void removeAndRecycleView(View view, Recycler recycler) {
            View child = view;
            Recycler recycler2 = recycler;
            View view2 = child;
            Recycler recycler3 = recycler2;
            removeView(child);
            recycler2.recycleView(child);
        }

        public void removeAndRecycleViewAt(int i, Recycler recycler) {
            int index = i;
            Recycler recycler2 = recycler;
            int i2 = index;
            Recycler recycler3 = recycler2;
            View view = getChildAt(index);
            removeViewAt(index);
            recycler2.recycleView(view);
        }

        public int getChildCount() {
            return this.mChildHelper == null ? 0 : this.mChildHelper.getChildCount();
        }

        public View getChildAt(int i) {
            int index = i;
            int i2 = index;
            return this.mChildHelper == null ? null : this.mChildHelper.getChildAt(index);
        }

        public int getWidthMode() {
            return this.mWidthMode;
        }

        public int getHeightMode() {
            return this.mHeightMode;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getPaddingLeft() {
            return this.mRecyclerView == null ? 0 : this.mRecyclerView.getPaddingLeft();
        }

        public int getPaddingTop() {
            return this.mRecyclerView == null ? 0 : this.mRecyclerView.getPaddingTop();
        }

        public int getPaddingRight() {
            return this.mRecyclerView == null ? 0 : this.mRecyclerView.getPaddingRight();
        }

        public int getPaddingBottom() {
            return this.mRecyclerView == null ? 0 : this.mRecyclerView.getPaddingBottom();
        }

        public int getPaddingStart() {
            return this.mRecyclerView == null ? 0 : ViewCompat.getPaddingStart(this.mRecyclerView);
        }

        public int getPaddingEnd() {
            return this.mRecyclerView == null ? 0 : ViewCompat.getPaddingEnd(this.mRecyclerView);
        }

        public boolean isFocused() {
            return this.mRecyclerView != null && this.mRecyclerView.isFocused();
        }

        public boolean hasFocus() {
            return this.mRecyclerView != null && this.mRecyclerView.hasFocus();
        }

        public View getFocusedChild() {
            if (this.mRecyclerView == null) {
                return null;
            }
            View focusedChild = this.mRecyclerView.getFocusedChild();
            View focused = focusedChild;
            if (focusedChild != null && !this.mChildHelper.isHidden(focused)) {
                return focused;
            }
            return null;
        }

        public int getItemCount() {
            Adapter a = this.mRecyclerView == null ? null : this.mRecyclerView.getAdapter();
            return a == null ? 0 : a.getItemCount();
        }

        public void offsetChildrenHorizontal(int i) {
            int dx = i;
            int i2 = dx;
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenHorizontal(dx);
            }
        }

        public void offsetChildrenVertical(int i) {
            int dy = i;
            int i2 = dy;
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenVertical(dy);
            }
        }

        public void ignoreView(View view) {
            View view2 = view;
            View view3 = view2;
            if (view2.getParent() == this.mRecyclerView && this.mRecyclerView.indexOfChild(view2) != -1) {
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view2);
                ViewHolder vh = childViewHolderInt;
                childViewHolderInt.addFlags(128);
                this.mRecyclerView.mViewInfoStore.removeViewHolder(vh);
                return;
            }
            throw new IllegalArgumentException("View should be fully attached to be ignored");
        }

        public void stopIgnoringView(View view) {
            View view2 = view;
            View view3 = view2;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view2);
            ViewHolder vh = childViewHolderInt;
            childViewHolderInt.stopIgnoring();
            vh.resetInternal();
            vh.addFlags(4);
        }

        public void detachAndScrapAttachedViews(Recycler recycler) {
            Recycler recycler2 = recycler;
            Recycler recycler3 = recycler2;
            int childCount = getChildCount();
            int i = childCount;
            for (int i2 = childCount - 1; i2 >= 0; i2--) {
                scrapOrRecycleView(recycler2, i2, getChildAt(i2));
            }
        }

        private void scrapOrRecycleView(Recycler recycler, int i, View view) {
            Recycler recycler2 = recycler;
            int index = i;
            View view2 = view;
            Recycler recycler3 = recycler2;
            int i2 = index;
            View view3 = view2;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view2);
            ViewHolder viewHolder = childViewHolderInt;
            if (!childViewHolderInt.shouldIgnore()) {
                if (viewHolder.isInvalid() && !viewHolder.isRemoved() && !this.mRecyclerView.mAdapter.hasStableIds()) {
                    removeViewAt(index);
                    recycler2.recycleViewHolderInternal(viewHolder);
                } else {
                    detachViewAt(index);
                    recycler2.scrapView(view2);
                    this.mRecyclerView.mViewInfoStore.onViewDetached(viewHolder);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void removeAndRecycleScrapInt(Recycler recycler) {
            Recycler recycler2 = recycler;
            Recycler recycler3 = recycler2;
            int scrapCount = recycler2.getScrapCount();
            int scrapCount2 = scrapCount;
            for (int i = scrapCount - 1; i >= 0; i--) {
                View scrapViewAt = recycler2.getScrapViewAt(i);
                View scrap = scrapViewAt;
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(scrapViewAt);
                ViewHolder vh = childViewHolderInt;
                if (!childViewHolderInt.shouldIgnore()) {
                    vh.setIsRecyclable(false);
                    if (vh.isTmpDetached()) {
                        this.mRecyclerView.removeDetachedView(scrap, false);
                    }
                    if (this.mRecyclerView.mItemAnimator != null) {
                        this.mRecyclerView.mItemAnimator.endAnimation(vh);
                    }
                    vh.setIsRecyclable(true);
                    recycler2.quickRecycleScrapView(scrap);
                }
            }
            recycler2.clearScrap();
            if (scrapCount2 > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public void measureChild(View view, int i, int i2) {
            View child = view;
            int widthUsed = i;
            int heightUsed = i2;
            View view2 = child;
            int i3 = widthUsed;
            int i4 = heightUsed;
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            Rect itemDecorInsetsForChild = this.mRecyclerView.getItemDecorInsetsForChild(child);
            Rect rect = itemDecorInsetsForChild;
            int widthUsed2 = widthUsed + itemDecorInsetsForChild.left + itemDecorInsetsForChild.right;
            int heightUsed2 = heightUsed + itemDecorInsetsForChild.top + itemDecorInsetsForChild.bottom;
            int widthSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + widthUsed2, lp.width, canScrollHorizontally());
            int childMeasureSpec = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + heightUsed2, lp.height, canScrollVertically());
            int heightSpec = childMeasureSpec;
            if (shouldMeasureChild(child, widthSpec, childMeasureSpec, lp)) {
                child.measure(widthSpec, heightSpec);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean shouldReMeasureChild(View view, int i, int i2, LayoutParams layoutParams) {
            View child = view;
            int widthSpec = i;
            int heightSpec = i2;
            LayoutParams lp = layoutParams;
            View view2 = child;
            int i3 = widthSpec;
            int i4 = heightSpec;
            LayoutParams layoutParams2 = lp;
            return !this.mMeasurementCacheEnabled || !isMeasurementUpToDate(child.getMeasuredWidth(), widthSpec, lp.width) || !isMeasurementUpToDate(child.getMeasuredHeight(), heightSpec, lp.height);
        }

        /* access modifiers changed from: 0000 */
        public boolean shouldMeasureChild(View view, int i, int i2, LayoutParams layoutParams) {
            View child = view;
            int widthSpec = i;
            int heightSpec = i2;
            LayoutParams lp = layoutParams;
            View view2 = child;
            int i3 = widthSpec;
            int i4 = heightSpec;
            LayoutParams layoutParams2 = lp;
            return child.isLayoutRequested() || !this.mMeasurementCacheEnabled || !isMeasurementUpToDate(child.getWidth(), widthSpec, lp.width) || !isMeasurementUpToDate(child.getHeight(), heightSpec, lp.height);
        }

        public boolean isMeasurementCacheEnabled() {
            return this.mMeasurementCacheEnabled;
        }

        public void setMeasurementCacheEnabled(boolean z) {
            this.mMeasurementCacheEnabled = z;
        }

        private static boolean isMeasurementUpToDate(int i, int i2, int i3) {
            int childSize = i;
            int spec = i2;
            int dimension = i3;
            int i4 = childSize;
            int i5 = spec;
            int i6 = dimension;
            int specMode = MeasureSpec.getMode(spec);
            int specSize = MeasureSpec.getSize(spec);
            if (dimension > 0 && childSize != dimension) {
                return false;
            }
            switch (specMode) {
                case Integer.MIN_VALUE:
                    return specSize >= childSize;
                case 0:
                    return true;
                case 1073741824:
                    return specSize == childSize;
                default:
                    return false;
            }
        }

        public void measureChildWithMargins(View view, int i, int i2) {
            View child = view;
            int widthUsed = i;
            int heightUsed = i2;
            View view2 = child;
            int i3 = widthUsed;
            int i4 = heightUsed;
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            Rect itemDecorInsetsForChild = this.mRecyclerView.getItemDecorInsetsForChild(child);
            Rect rect = itemDecorInsetsForChild;
            int widthUsed2 = widthUsed + itemDecorInsetsForChild.left + itemDecorInsetsForChild.right;
            int heightUsed2 = heightUsed + itemDecorInsetsForChild.top + itemDecorInsetsForChild.bottom;
            int widthSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin + widthUsed2, lp.width, canScrollHorizontally());
            int childMeasureSpec = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin + heightUsed2, lp.height, canScrollVertically());
            int heightSpec = childMeasureSpec;
            if (shouldMeasureChild(child, widthSpec, childMeasureSpec, lp)) {
                child.measure(widthSpec, heightSpec);
            }
        }

        @Deprecated
        public static int getChildMeasureSpec(int i, int i2, int i3, boolean z) {
            int parentSize = i;
            int padding = i2;
            int childDimension = i3;
            int i4 = parentSize;
            int i5 = padding;
            int i6 = childDimension;
            boolean canScroll = z;
            int size = Math.max(0, parentSize - padding);
            int resultSize = 0;
            int resultMode = 0;
            if (!canScroll) {
                if (childDimension >= 0) {
                    resultSize = childDimension;
                    resultMode = 1073741824;
                } else if (childDimension == -1) {
                    resultSize = size;
                    resultMode = 1073741824;
                } else if (childDimension == -2) {
                    resultSize = size;
                    resultMode = Integer.MIN_VALUE;
                }
            } else if (childDimension < 0) {
                resultSize = 0;
                resultMode = 0;
            } else {
                resultSize = childDimension;
                resultMode = 1073741824;
            }
            return MeasureSpec.makeMeasureSpec(resultSize, resultMode);
        }

        public static int getChildMeasureSpec(int i, int i2, int i3, int i4, boolean z) {
            int parentSize = i;
            int parentMode = i2;
            int padding = i3;
            int childDimension = i4;
            int i5 = parentSize;
            int i6 = parentMode;
            int i7 = padding;
            int i8 = childDimension;
            boolean canScroll = z;
            int size = Math.max(0, parentSize - padding);
            int resultSize = 0;
            int resultMode = 0;
            if (!canScroll) {
                if (childDimension >= 0) {
                    resultSize = childDimension;
                    resultMode = 1073741824;
                } else if (childDimension == -1) {
                    resultSize = size;
                    resultMode = parentMode;
                } else if (childDimension == -2) {
                    resultSize = size;
                    resultMode = (parentMode == Integer.MIN_VALUE || parentMode == 1073741824) ? Integer.MIN_VALUE : 0;
                }
            } else if (childDimension < 0) {
                if (childDimension == -1) {
                    switch (parentMode) {
                        case Integer.MIN_VALUE:
                        case 1073741824:
                            resultSize = size;
                            resultMode = parentMode;
                            break;
                        case 0:
                            resultSize = 0;
                            resultMode = 0;
                            break;
                    }
                } else if (childDimension == -2) {
                    resultSize = 0;
                    resultMode = 0;
                }
            } else {
                resultSize = childDimension;
                resultMode = 1073741824;
            }
            return MeasureSpec.makeMeasureSpec(resultSize, resultMode);
        }

        public int getDecoratedMeasuredWidth(View view) {
            View child = view;
            View view2 = child;
            Rect insets = ((LayoutParams) child.getLayoutParams()).mDecorInsets;
            return child.getMeasuredWidth() + insets.left + insets.right;
        }

        public int getDecoratedMeasuredHeight(View view) {
            View child = view;
            View view2 = child;
            Rect insets = ((LayoutParams) child.getLayoutParams()).mDecorInsets;
            return child.getMeasuredHeight() + insets.top + insets.bottom;
        }

        public void layoutDecorated(View view, int i, int i2, int i3, int i4) {
            View child = view;
            int left = i;
            int top = i2;
            int right = i3;
            int bottom = i4;
            View view2 = child;
            int i5 = left;
            int i6 = top;
            int i7 = right;
            int i8 = bottom;
            Rect insets = ((LayoutParams) child.getLayoutParams()).mDecorInsets;
            child.layout(left + insets.left, top + insets.top, right - insets.right, bottom - insets.bottom);
        }

        public void layoutDecoratedWithMargins(View view, int i, int i2, int i3, int i4) {
            View child = view;
            int left = i;
            int top = i2;
            int right = i3;
            int bottom = i4;
            View view2 = child;
            int i5 = left;
            int i6 = top;
            int i7 = right;
            int i8 = bottom;
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            LayoutParams lp = layoutParams;
            Rect insets = layoutParams.mDecorInsets;
            child.layout(left + insets.left + lp.leftMargin, top + insets.top + lp.topMargin, (right - insets.right) - lp.rightMargin, (bottom - insets.bottom) - lp.bottomMargin);
        }

        public void getTransformedBoundingBox(View view, boolean z, Rect rect) {
            View child = view;
            Rect out = rect;
            View view2 = child;
            Rect rect2 = out;
            if (!z) {
                out.set(0, 0, child.getWidth(), child.getHeight());
            } else {
                Rect insets = ((LayoutParams) child.getLayoutParams()).mDecorInsets;
                out.set(-insets.left, -insets.top, child.getWidth() + insets.right, child.getHeight() + insets.bottom);
            }
            if (this.mRecyclerView != null) {
                Matrix matrix = ViewCompat.getMatrix(child);
                Matrix childMatrix = matrix;
                if (matrix != null && !childMatrix.isIdentity()) {
                    RectF rectF = this.mRecyclerView.mTempRectF;
                    RectF tempRectF = rectF;
                    rectF.set(out);
                    boolean mapRect = childMatrix.mapRect(tempRectF);
                    out.set((int) Math.floor((double) tempRectF.left), (int) Math.floor((double) tempRectF.top), (int) Math.ceil((double) tempRectF.right), (int) Math.ceil((double) tempRectF.bottom));
                }
            }
            out.offset(child.getLeft(), child.getTop());
        }

        public void getDecoratedBoundsWithMargins(View view, Rect rect) {
            View view2 = view;
            Rect outBounds = rect;
            View view3 = view2;
            Rect rect2 = outBounds;
            RecyclerView.getDecoratedBoundsWithMarginsInt(view2, outBounds);
        }

        public int getDecoratedLeft(View view) {
            View child = view;
            View view2 = child;
            return child.getLeft() - getLeftDecorationWidth(child);
        }

        public int getDecoratedTop(View view) {
            View child = view;
            View view2 = child;
            return child.getTop() - getTopDecorationHeight(child);
        }

        public int getDecoratedRight(View view) {
            View child = view;
            View view2 = child;
            return child.getRight() + getRightDecorationWidth(child);
        }

        public int getDecoratedBottom(View view) {
            View child = view;
            View view2 = child;
            return child.getBottom() + getBottomDecorationHeight(child);
        }

        public void calculateItemDecorationsForChild(View view, Rect rect) {
            View child = view;
            Rect outRect = rect;
            View view2 = child;
            Rect rect2 = outRect;
            if (this.mRecyclerView != null) {
                outRect.set(this.mRecyclerView.getItemDecorInsetsForChild(child));
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }

        public int getTopDecorationHeight(View view) {
            View child = view;
            View view2 = child;
            return ((LayoutParams) child.getLayoutParams()).mDecorInsets.top;
        }

        public int getBottomDecorationHeight(View view) {
            View child = view;
            View view2 = child;
            return ((LayoutParams) child.getLayoutParams()).mDecorInsets.bottom;
        }

        public int getLeftDecorationWidth(View view) {
            View child = view;
            View view2 = child;
            return ((LayoutParams) child.getLayoutParams()).mDecorInsets.left;
        }

        public int getRightDecorationWidth(View view) {
            View child = view;
            View view2 = child;
            return ((LayoutParams) child.getLayoutParams()).mDecorInsets.right;
        }

        @Nullable
        public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
            View view2 = view;
            int i2 = i;
            Recycler recycler2 = recycler;
            State state2 = state;
            return null;
        }

        public View onInterceptFocusSearch(View view, int i) {
            View view2 = view;
            int i2 = i;
            return null;
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            int i;
            int dx;
            int i2;
            int i3;
            RecyclerView parent = recyclerView;
            View child = view;
            Rect rect2 = rect;
            RecyclerView recyclerView2 = parent;
            View view2 = child;
            Rect rect3 = rect2;
            boolean immediate = z;
            int parentLeft = getPaddingLeft();
            int parentTop = getPaddingTop();
            int parentRight = getWidth() - getPaddingRight();
            int parentBottom = getHeight() - getPaddingBottom();
            int childLeft = (child.getLeft() + rect2.left) - child.getScrollX();
            int childTop = (child.getTop() + rect2.top) - child.getScrollY();
            int childRight = childLeft + rect2.width();
            int childBottom = childTop + rect2.height();
            int offScreenLeft = Math.min(0, childLeft - parentLeft);
            int offScreenTop = Math.min(0, childTop - parentTop);
            int offScreenRight = Math.max(0, childRight - parentRight);
            int offScreenBottom = Math.max(0, childBottom - parentBottom);
            if (getLayoutDirection() != 1) {
                if (offScreenLeft == 0) {
                    i3 = Math.min(childLeft - parentLeft, offScreenRight);
                } else {
                    i3 = offScreenLeft;
                }
                dx = i3;
            } else {
                if (offScreenRight == 0) {
                    i = Math.max(offScreenLeft, childRight - parentRight);
                } else {
                    i = offScreenRight;
                }
                dx = i;
            }
            if (offScreenTop == 0) {
                i2 = Math.min(childTop - parentTop, offScreenBottom);
            } else {
                i2 = offScreenTop;
            }
            int dy = i2;
            if (dx == 0 && dy == 0) {
                return false;
            }
            if (!immediate) {
                parent.smoothScrollBy(dx, dy);
            } else {
                parent.scrollBy(dx, dy);
            }
            return true;
        }

        @Deprecated
        public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
            RecyclerView parent = recyclerView;
            RecyclerView recyclerView2 = parent;
            View view3 = view;
            View view4 = view2;
            return isSmoothScrolling() || parent.isComputingLayout();
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, State state, View view, View view2) {
            RecyclerView parent = recyclerView;
            View child = view;
            View focused = view2;
            RecyclerView recyclerView2 = parent;
            State state2 = state;
            View view3 = child;
            View view4 = focused;
            return onRequestChildFocus(parent, child, focused);
        }

        public void onAdapterChanged(Adapter adapter, Adapter adapter2) {
            Adapter adapter3 = adapter;
            Adapter adapter4 = adapter2;
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int i, int i2) {
            RecyclerView recyclerView2 = recyclerView;
            ArrayList<View> arrayList2 = arrayList;
            int i3 = i;
            int i4 = i2;
            return false;
        }

        public void onItemsChanged(RecyclerView recyclerView) {
            RecyclerView recyclerView2 = recyclerView;
        }

        public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
            RecyclerView recyclerView2 = recyclerView;
            int i3 = i;
            int i4 = i2;
        }

        public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
            RecyclerView recyclerView2 = recyclerView;
            int i3 = i;
            int i4 = i2;
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
            RecyclerView recyclerView2 = recyclerView;
            int i3 = i;
            int i4 = i2;
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
            RecyclerView recyclerView2 = recyclerView;
            int positionStart = i;
            int itemCount = i2;
            RecyclerView recyclerView3 = recyclerView2;
            int i3 = positionStart;
            int i4 = itemCount;
            Object obj2 = obj;
            onItemsUpdated(recyclerView2, positionStart, itemCount);
        }

        public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
            RecyclerView recyclerView2 = recyclerView;
            int i4 = i;
            int i5 = i2;
            int i6 = i3;
        }

        public int computeHorizontalScrollExtent(State state) {
            State state2 = state;
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) {
            State state2 = state;
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            State state2 = state;
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            State state2 = state;
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            State state2 = state;
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            State state2 = state;
            return 0;
        }

        public void onMeasure(Recycler recycler, State state, int i, int i2) {
            int widthSpec = i;
            int heightSpec = i2;
            Recycler recycler2 = recycler;
            State state2 = state;
            int i3 = widthSpec;
            int i4 = heightSpec;
            this.mRecyclerView.defaultOnMeasure(widthSpec, heightSpec);
        }

        public void setMeasuredDimension(int i, int i2) {
            int widthSize = i;
            int heightSize = i2;
            int i3 = widthSize;
            int i4 = heightSize;
            RecyclerView.access$1000(this.mRecyclerView, widthSize, heightSize);
        }

        public int getMinimumWidth() {
            return ViewCompat.getMinimumWidth(this.mRecyclerView);
        }

        public int getMinimumHeight() {
            return ViewCompat.getMinimumHeight(this.mRecyclerView);
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
            Parcelable parcelable2 = parcelable;
        }

        /* access modifiers changed from: 0000 */
        public void stopSmoothScroller() {
            if (this.mSmoothScroller != null) {
                this.mSmoothScroller.stop();
            }
        }

        private void onSmoothScrollerStopped(SmoothScroller smoothScroller) {
            SmoothScroller smoothScroller2 = smoothScroller;
            SmoothScroller smoothScroller3 = smoothScroller2;
            if (this.mSmoothScroller == smoothScroller2) {
                this.mSmoothScroller = null;
            }
        }

        public void onScrollStateChanged(int i) {
            int i2 = i;
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            Recycler recycler2 = recycler;
            Recycler recycler3 = recycler2;
            for (int i = getChildCount() - 1; i >= 0; i--) {
                View childAt = getChildAt(i);
                View view = childAt;
                if (!RecyclerView.getChildViewHolderInt(childAt).shouldIgnore()) {
                    removeAndRecycleViewAt(i, recycler2);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, info);
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            Recycler recycler2 = recycler;
            State state2 = state;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            Recycler recycler3 = recycler2;
            State state3 = state2;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            if (ViewCompat.canScrollVertically(this.mRecyclerView, -1) || ViewCompat.canScrollHorizontally(this.mRecyclerView, -1)) {
                info.addAction(8192);
                info.setScrollable(true);
            }
            if (ViewCompat.canScrollVertically(this.mRecyclerView, 1) || ViewCompat.canScrollHorizontally(this.mRecyclerView, 1)) {
                info.addAction(4096);
                info.setScrollable(true);
            }
            info.setCollectionInfo(CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler2, state2), getColumnCountForAccessibility(recycler2, state2), isLayoutHierarchical(recycler2, state2), getSelectionModeForAccessibility(recycler2, state2)));
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent event = accessibilityEvent;
            AccessibilityEvent accessibilityEvent2 = event;
            onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, event);
        }

        public void onInitializeAccessibilityEvent(Recycler recycler, State state, AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent event = accessibilityEvent;
            Recycler recycler2 = recycler;
            State state2 = state;
            AccessibilityEvent accessibilityEvent2 = event;
            AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(event);
            if (this.mRecyclerView != null && record != null) {
                record.setScrollable(ViewCompat.canScrollVertically(this.mRecyclerView, 1) || ViewCompat.canScrollVertically(this.mRecyclerView, -1) || ViewCompat.canScrollHorizontally(this.mRecyclerView, -1) || ViewCompat.canScrollHorizontally(this.mRecyclerView, 1));
                if (this.mRecyclerView.mAdapter != null) {
                    record.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void onInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View host = view;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            View view2 = host;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(host);
            ViewHolder vh = childViewHolderInt;
            if (childViewHolderInt != null && !vh.isRemoved() && !this.mChildHelper.isHidden(vh.itemView)) {
                onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, host, info);
            }
        }

        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View host = view;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            Recycler recycler2 = recycler;
            State state2 = state;
            View view2 = host;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            info.setCollectionItemInfo(CollectionItemInfoCompat.obtain(!canScrollVertically() ? 0 : getPosition(host), 1, !canScrollHorizontally() ? 0 : getPosition(host), 1, false, false));
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.mRequestedSimpleAnimations = true;
        }

        public int getSelectionModeForAccessibility(Recycler recycler, State state) {
            Recycler recycler2 = recycler;
            State state2 = state;
            return 0;
        }

        public int getRowCountForAccessibility(Recycler recycler, State state) {
            Recycler recycler2 = recycler;
            State state2 = state;
            if (this.mRecyclerView == null || this.mRecyclerView.mAdapter == null) {
                return 1;
            }
            return !canScrollVertically() ? 1 : this.mRecyclerView.mAdapter.getItemCount();
        }

        public int getColumnCountForAccessibility(Recycler recycler, State state) {
            Recycler recycler2 = recycler;
            State state2 = state;
            if (this.mRecyclerView == null || this.mRecyclerView.mAdapter == null) {
                return 1;
            }
            return !canScrollHorizontally() ? 1 : this.mRecyclerView.mAdapter.getItemCount();
        }

        public boolean isLayoutHierarchical(Recycler recycler, State state) {
            Recycler recycler2 = recycler;
            State state2 = state;
            return false;
        }

        /* access modifiers changed from: 0000 */
        public boolean performAccessibilityAction(int i, Bundle bundle) {
            int action = i;
            Bundle args = bundle;
            int i2 = action;
            Bundle bundle2 = args;
            return performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, action, args);
        }

        public boolean performAccessibilityAction(Recycler recycler, State state, int i, Bundle bundle) {
            int action = i;
            Recycler recycler2 = recycler;
            State state2 = state;
            int i2 = action;
            Bundle bundle2 = bundle;
            if (this.mRecyclerView == null) {
                return false;
            }
            int vScroll = 0;
            int hScroll = 0;
            switch (action) {
                case 4096:
                    if (ViewCompat.canScrollVertically(this.mRecyclerView, 1)) {
                        vScroll = (getHeight() - getPaddingTop()) - getPaddingBottom();
                    }
                    if (ViewCompat.canScrollHorizontally(this.mRecyclerView, 1)) {
                        hScroll = (getWidth() - getPaddingLeft()) - getPaddingRight();
                        break;
                    }
                    break;
                case 8192:
                    if (ViewCompat.canScrollVertically(this.mRecyclerView, -1)) {
                        vScroll = -((getHeight() - getPaddingTop()) - getPaddingBottom());
                    }
                    if (ViewCompat.canScrollHorizontally(this.mRecyclerView, -1)) {
                        hScroll = -((getWidth() - getPaddingLeft()) - getPaddingRight());
                        break;
                    }
                    break;
            }
            if (vScroll == 0 && hScroll == 0) {
                return false;
            }
            this.mRecyclerView.scrollBy(hScroll, vScroll);
            return true;
        }

        /* access modifiers changed from: 0000 */
        public boolean performAccessibilityActionForItem(View view, int i, Bundle bundle) {
            View view2 = view;
            int action = i;
            Bundle args = bundle;
            View view3 = view2;
            int i2 = action;
            Bundle bundle2 = args;
            return performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view2, action, args);
        }

        public boolean performAccessibilityActionForItem(Recycler recycler, State state, View view, int i, Bundle bundle) {
            Recycler recycler2 = recycler;
            State state2 = state;
            View view2 = view;
            int i2 = i;
            Bundle bundle2 = bundle;
            return false;
        }

        public static Properties getProperties(Context context, AttributeSet attributeSet, int i, int i2) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            int defStyleAttr = i;
            int defStyleRes = i2;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            int i3 = defStyleAttr;
            int i4 = defStyleRes;
            Properties properties = new Properties();
            TypedArray a = context2.obtainStyledAttributes(attrs, C0271R.styleable.RecyclerView, defStyleAttr, defStyleRes);
            properties.orientation = a.getInt(C0271R.styleable.RecyclerView_android_orientation, 1);
            properties.spanCount = a.getInt(C0271R.styleable.RecyclerView_spanCount, 1);
            properties.reverseLayout = a.getBoolean(C0271R.styleable.RecyclerView_reverseLayout, false);
            properties.stackFromEnd = a.getBoolean(C0271R.styleable.RecyclerView_stackFromEnd, false);
            a.recycle();
            return properties;
        }

        /* access modifiers changed from: 0000 */
        public void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            RecyclerView recyclerView2 = recyclerView;
            RecyclerView recyclerView3 = recyclerView2;
            setMeasureSpecs(MeasureSpec.makeMeasureSpec(recyclerView2.getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(recyclerView2.getHeight(), 1073741824));
        }

        /* access modifiers changed from: 0000 */
        public boolean shouldMeasureTwice() {
            return false;
        }

        /* access modifiers changed from: 0000 */
        public boolean hasFlexibleChildInBothOrientations() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                View view = childAt;
                android.view.ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                android.view.ViewGroup.LayoutParams lp = layoutParams;
                if (layoutParams.width < 0 && lp.height < 0) {
                    return true;
                }
            }
            return false;
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$LayoutParams */
    public static class LayoutParams extends MarginLayoutParams {
        final Rect mDecorInsets = new Rect();
        boolean mInsetsDirty = true;
        boolean mPendingInvalidate = false;
        ViewHolder mViewHolder;

        public LayoutParams(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            super(width, height);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            Context c = context;
            AttributeSet attrs = attributeSet;
            Context context2 = c;
            AttributeSet attributeSet2 = attrs;
            super(c, attrs);
        }

        public LayoutParams(LayoutParams layoutParams) {
            LayoutParams source = layoutParams;
            LayoutParams layoutParams2 = source;
            super(source);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.view.ViewGroup.LayoutParams source = layoutParams;
            android.view.ViewGroup.LayoutParams layoutParams2 = source;
            super(source);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams source = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = source;
            super(source);
        }

        public boolean viewNeedsUpdate() {
            return this.mViewHolder.needsUpdate();
        }

        public boolean isViewInvalid() {
            return this.mViewHolder.isInvalid();
        }

        public boolean isItemRemoved() {
            return this.mViewHolder.isRemoved();
        }

        public boolean isItemChanged() {
            return this.mViewHolder.isUpdated();
        }

        @Deprecated
        public int getViewPosition() {
            return this.mViewHolder.getPosition();
        }

        public int getViewLayoutPosition() {
            return this.mViewHolder.getLayoutPosition();
        }

        public int getViewAdapterPosition() {
            return this.mViewHolder.getAdapterPosition();
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$OnChildAttachStateChangeListener */
    public interface OnChildAttachStateChangeListener {
        void onChildViewAttachedToWindow(View view);

        void onChildViewDetachedFromWindow(View view);
    }

    /* renamed from: android.support.v7.widget.RecyclerView$OnFlingListener */
    public static abstract class OnFlingListener {
        public abstract boolean onFling(int i, int i2);

        public OnFlingListener() {
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$OnItemTouchListener */
    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent(boolean z);

        void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);
    }

    /* renamed from: android.support.v7.widget.RecyclerView$OnScrollListener */
    public static abstract class OnScrollListener {
        public OnScrollListener() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            RecyclerView recyclerView2 = recyclerView;
            int i2 = i;
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            RecyclerView recyclerView2 = recyclerView;
            int i3 = i;
            int i4 = i2;
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$RecycledViewPool */
    public static class RecycledViewPool {
        private static final int DEFAULT_MAX_SCRAP = 5;
        private int mAttachCount = 0;
        SparseArray<ScrapData> mScrap = new SparseArray<>();

        /* renamed from: android.support.v7.widget.RecyclerView$RecycledViewPool$ScrapData */
        static class ScrapData {
            long mBindRunningAverageNs = 0;
            long mCreateRunningAverageNs = 0;
            int mMaxScrap = 5;
            ArrayList<ViewHolder> mScrapHeap = new ArrayList<>();

            ScrapData() {
            }
        }

        public RecycledViewPool() {
        }

        public void clear() {
            for (int i = 0; i < this.mScrap.size(); i++) {
                ScrapData scrapData = (ScrapData) this.mScrap.valueAt(i);
                ScrapData scrapData2 = scrapData;
                scrapData.mScrapHeap.clear();
            }
        }

        public void setMaxRecycledViews(int i, int i2) {
            int viewType = i;
            int max = i2;
            int i3 = viewType;
            int i4 = max;
            ScrapData scrapDataForType = getScrapDataForType(viewType);
            ScrapData scrapData = scrapDataForType;
            scrapDataForType.mMaxScrap = max;
            ArrayList<ViewHolder> arrayList = scrapData.mScrapHeap;
            ArrayList<ViewHolder> scrapHeap = arrayList;
            if (arrayList != null) {
                while (scrapHeap.size() > max) {
                    Object remove = scrapHeap.remove(scrapHeap.size() - 1);
                }
            }
        }

        public int getRecycledViewCount(int i) {
            int viewType = i;
            int i2 = viewType;
            return getScrapDataForType(viewType).mScrapHeap.size();
        }

        public ViewHolder getRecycledView(int i) {
            int viewType = i;
            int i2 = viewType;
            ScrapData scrapData = (ScrapData) this.mScrap.get(viewType);
            ScrapData scrapData2 = scrapData;
            if (scrapData == null || scrapData2.mScrapHeap.isEmpty()) {
                return null;
            }
            ArrayList<ViewHolder> arrayList = scrapData2.mScrapHeap;
            return (ViewHolder) arrayList.remove(arrayList.size() - 1);
        }

        /* access modifiers changed from: 0000 */
        public int size() {
            int count = 0;
            for (int i = 0; i < this.mScrap.size(); i++) {
                ArrayList<ViewHolder> arrayList = ((ScrapData) this.mScrap.valueAt(i)).mScrapHeap;
                ArrayList<ViewHolder> viewHolders = arrayList;
                if (arrayList != null) {
                    count += viewHolders.size();
                }
            }
            return count;
        }

        public void putRecycledView(ViewHolder viewHolder) {
            ViewHolder scrap = viewHolder;
            ViewHolder viewHolder2 = scrap;
            int itemViewType = scrap.getItemViewType();
            int i = itemViewType;
            ArrayList scrapHeap = getScrapDataForType(itemViewType).mScrapHeap;
            if (((ScrapData) this.mScrap.get(itemViewType)).mMaxScrap > scrapHeap.size()) {
                scrap.resetInternal();
                boolean add = scrapHeap.add(scrap);
            }
        }

        /* access modifiers changed from: 0000 */
        public long runningAverage(long j, long j2) {
            long oldAverage = j;
            long newValue = j2;
            long j3 = oldAverage;
            long j4 = newValue;
            if (oldAverage == 0) {
                return newValue;
            }
            return ((oldAverage / 4) * 3) + (newValue / 4);
        }

        /* access modifiers changed from: 0000 */
        public void factorInCreateTime(int i, long j) {
            int viewType = i;
            long createTimeNs = j;
            int i2 = viewType;
            long j2 = createTimeNs;
            ScrapData scrapDataForType = getScrapDataForType(viewType);
            ScrapData scrapData = scrapDataForType;
            scrapDataForType.mCreateRunningAverageNs = runningAverage(scrapData.mCreateRunningAverageNs, createTimeNs);
        }

        /* access modifiers changed from: 0000 */
        public void factorInBindTime(int i, long j) {
            int viewType = i;
            long bindTimeNs = j;
            int i2 = viewType;
            long j2 = bindTimeNs;
            ScrapData scrapDataForType = getScrapDataForType(viewType);
            ScrapData scrapData = scrapDataForType;
            scrapDataForType.mBindRunningAverageNs = runningAverage(scrapData.mBindRunningAverageNs, bindTimeNs);
        }

        /* access modifiers changed from: 0000 */
        public boolean willCreateInTime(int i, long j, long j2) {
            boolean z;
            int viewType = i;
            long approxCurrentNs = j;
            long deadlineNs = j2;
            int i2 = viewType;
            long j3 = approxCurrentNs;
            long j4 = deadlineNs;
            long j5 = getScrapDataForType(viewType).mCreateRunningAverageNs;
            long expectedDurationNs = j5;
            if (j5 != 0) {
                if (approxCurrentNs + expectedDurationNs >= deadlineNs) {
                    z = false;
                    return z;
                }
            }
            z = true;
            return z;
        }

        /* access modifiers changed from: 0000 */
        public boolean willBindInTime(int i, long j, long j2) {
            boolean z;
            int viewType = i;
            long approxCurrentNs = j;
            long deadlineNs = j2;
            int i2 = viewType;
            long j3 = approxCurrentNs;
            long j4 = deadlineNs;
            long j5 = getScrapDataForType(viewType).mBindRunningAverageNs;
            long expectedDurationNs = j5;
            if (j5 != 0) {
                if (approxCurrentNs + expectedDurationNs >= deadlineNs) {
                    z = false;
                    return z;
                }
            }
            z = true;
            return z;
        }

        /* access modifiers changed from: 0000 */
        public void attach(Adapter adapter) {
            Adapter adapter2 = adapter;
            this.mAttachCount++;
        }

        /* access modifiers changed from: 0000 */
        public void detach() {
            this.mAttachCount--;
        }

        /* access modifiers changed from: 0000 */
        public void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean z) {
            Adapter oldAdapter = adapter;
            Adapter newAdapter = adapter2;
            Adapter adapter3 = oldAdapter;
            Adapter adapter4 = newAdapter;
            boolean compatibleWithPrevious = z;
            if (oldAdapter != null) {
                detach();
            }
            if (!compatibleWithPrevious && this.mAttachCount == 0) {
                clear();
            }
            if (newAdapter != null) {
                attach(newAdapter);
            }
        }

        private ScrapData getScrapDataForType(int i) {
            int viewType = i;
            int i2 = viewType;
            ScrapData scrapData = (ScrapData) this.mScrap.get(viewType);
            ScrapData scrapData2 = scrapData;
            if (scrapData == null) {
                scrapData2 = new ScrapData();
                this.mScrap.put(viewType, scrapData2);
            }
            return scrapData2;
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$Recycler */
    public final class Recycler {
        static final int DEFAULT_CACHE_SIZE = 2;
        final ArrayList<ViewHolder> mAttachedScrap = new ArrayList<>();
        final ArrayList<ViewHolder> mCachedViews = new ArrayList<>();
        ArrayList<ViewHolder> mChangedScrap = null;
        RecycledViewPool mRecyclerPool;
        private int mRequestedCacheMax = 2;
        private final List<ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
        private ViewCacheExtension mViewCacheExtension;
        int mViewCacheMax = 2;
        final /* synthetic */ RecyclerView this$0;

        public Recycler(RecyclerView recyclerView) {
            RecyclerView this$02 = recyclerView;
            RecyclerView recyclerView2 = this$02;
            this.this$0 = this$02;
        }

        public void clear() {
            this.mAttachedScrap.clear();
            recycleAndClearCachedViews();
        }

        public void setViewCacheSize(int i) {
            int viewCount = i;
            int i2 = viewCount;
            this.mRequestedCacheMax = viewCount;
            updateViewCacheSize();
        }

        /* access modifiers changed from: 0000 */
        public void updateViewCacheSize() {
            this.mViewCacheMax = this.mRequestedCacheMax + (this.this$0.mLayout == null ? 0 : this.this$0.mLayout.mPrefetchMaxCountObserved);
            for (int i = this.mCachedViews.size() - 1; i >= 0 && this.mCachedViews.size() > this.mViewCacheMax; i--) {
                recycleCachedViewAt(i);
            }
        }

        public List<ViewHolder> getScrapList() {
            return this.mUnmodifiableAttachedScrap;
        }

        /* access modifiers changed from: 0000 */
        public boolean validateViewHolderForOffsetPosition(ViewHolder viewHolder) {
            ViewHolder holder = viewHolder;
            ViewHolder viewHolder2 = holder;
            if (holder.isRemoved()) {
                return this.this$0.mState.isPreLayout();
            }
            if (holder.mPosition >= 0 && holder.mPosition < this.this$0.mAdapter.getItemCount()) {
                if (!this.this$0.mState.isPreLayout()) {
                    int itemViewType = this.this$0.mAdapter.getItemViewType(holder.mPosition);
                    int i = itemViewType;
                    if (itemViewType != holder.getItemViewType()) {
                        return false;
                    }
                }
                if (!this.this$0.mAdapter.hasStableIds()) {
                    return true;
                }
                return holder.getItemId() == this.this$0.mAdapter.getItemId(holder.mPosition);
            }
            throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + holder);
        }

        private boolean tryBindViewHolderByDeadline(ViewHolder viewHolder, int i, int i2, long j) {
            ViewHolder holder = viewHolder;
            int offsetPosition = i;
            int position = i2;
            long deadlineNs = j;
            ViewHolder viewHolder2 = holder;
            int i3 = offsetPosition;
            int i4 = position;
            long j2 = deadlineNs;
            holder.mOwnerRecyclerView = this.this$0;
            int itemViewType = holder.getItemViewType();
            int i5 = itemViewType;
            long startBindNs = this.this$0.getNanoTime();
            if (deadlineNs != RecyclerView.FOREVER_NS && !this.mRecyclerPool.willBindInTime(itemViewType, startBindNs, deadlineNs)) {
                return false;
            }
            this.this$0.mAdapter.bindViewHolder(holder, offsetPosition);
            long nanoTime = this.this$0.getNanoTime();
            long j3 = nanoTime;
            this.mRecyclerPool.factorInBindTime(holder.getItemViewType(), nanoTime - startBindNs);
            attachAccessibilityDelegate(holder.itemView);
            if (this.this$0.mState.isPreLayout()) {
                holder.mPreLayoutPosition = position;
            }
            return true;
        }

        public void bindViewToPosition(View view, int i) {
            LayoutParams rvLayoutParams;
            View view2 = view;
            int position = i;
            View view3 = view2;
            int i2 = position;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view2);
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt != null) {
                int findPositionOffset = this.this$0.mAdapterHelper.findPositionOffset(position);
                int offsetPosition = findPositionOffset;
                if (findPositionOffset >= 0 && offsetPosition < this.this$0.mAdapter.getItemCount()) {
                    boolean tryBindViewHolderByDeadline = tryBindViewHolderByDeadline(holder, offsetPosition, position, RecyclerView.FOREVER_NS);
                    android.view.ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                    android.view.ViewGroup.LayoutParams lp = layoutParams;
                    if (layoutParams == null) {
                        rvLayoutParams = (LayoutParams) this.this$0.generateDefaultLayoutParams();
                        holder.itemView.setLayoutParams(rvLayoutParams);
                    } else if (this.this$0.checkLayoutParams(lp)) {
                        rvLayoutParams = (LayoutParams) lp;
                    } else {
                        rvLayoutParams = (LayoutParams) this.this$0.generateLayoutParams(lp);
                        holder.itemView.setLayoutParams(rvLayoutParams);
                    }
                    rvLayoutParams.mInsetsDirty = true;
                    rvLayoutParams.mViewHolder = holder;
                    rvLayoutParams.mPendingInvalidate = holder.itemView.getParent() == null;
                    return;
                }
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + position + "(offset:" + offsetPosition + ")." + "state:" + this.this$0.mState.getItemCount());
            }
            throw new IllegalArgumentException("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
        }

        public int convertPreLayoutPositionToPostLayout(int i) {
            int position = i;
            int i2 = position;
            if (position < 0 || position >= this.this$0.mState.getItemCount()) {
                throw new IndexOutOfBoundsException("invalid position " + position + ". State " + "item count is " + this.this$0.mState.getItemCount());
            } else if (this.this$0.mState.isPreLayout()) {
                return this.this$0.mAdapterHelper.findPositionOffset(position);
            } else {
                return position;
            }
        }

        public View getViewForPosition(int i) {
            int position = i;
            int i2 = position;
            return getViewForPosition(position, false);
        }

        /* access modifiers changed from: 0000 */
        public View getViewForPosition(int i, boolean z) {
            int position = i;
            int i2 = position;
            return tryGetViewHolderForPositionByDeadline(position, z, RecyclerView.FOREVER_NS).itemView;
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public ViewHolder tryGetViewHolderForPositionByDeadline(int i, boolean z, long j) {
            LayoutParams rvLayoutParams;
            int position = i;
            long deadlineNs = j;
            int i2 = position;
            boolean dryRun = z;
            long j2 = deadlineNs;
            if (position >= 0 && position < this.this$0.mState.getItemCount()) {
                boolean fromScrapOrHiddenOrCache = false;
                ViewHolder holder = null;
                if (this.this$0.mState.isPreLayout()) {
                    ViewHolder changedScrapViewForPosition = getChangedScrapViewForPosition(position);
                    holder = changedScrapViewForPosition;
                    fromScrapOrHiddenOrCache = changedScrapViewForPosition != null;
                }
                if (holder == null) {
                    ViewHolder scrapOrHiddenOrCachedHolderForPosition = getScrapOrHiddenOrCachedHolderForPosition(position, dryRun);
                    holder = scrapOrHiddenOrCachedHolderForPosition;
                    if (scrapOrHiddenOrCachedHolderForPosition != null) {
                        if (validateViewHolderForOffsetPosition(holder)) {
                            fromScrapOrHiddenOrCache = true;
                        } else {
                            if (!dryRun) {
                                holder.addFlags(4);
                                if (holder.isScrap()) {
                                    this.this$0.removeDetachedView(holder.itemView, false);
                                    holder.unScrap();
                                } else if (holder.wasReturnedFromScrap()) {
                                    holder.clearReturnedFromScrapFlag();
                                }
                                recycleViewHolderInternal(holder);
                            }
                            holder = null;
                        }
                    }
                }
                if (holder == null) {
                    int findPositionOffset = this.this$0.mAdapterHelper.findPositionOffset(position);
                    int offsetPosition = findPositionOffset;
                    if (findPositionOffset >= 0 && offsetPosition < this.this$0.mAdapter.getItemCount()) {
                        int type = this.this$0.mAdapter.getItemViewType(offsetPosition);
                        if (this.this$0.mAdapter.hasStableIds()) {
                            ViewHolder scrapOrCachedViewForId = getScrapOrCachedViewForId(this.this$0.mAdapter.getItemId(offsetPosition), type, dryRun);
                            holder = scrapOrCachedViewForId;
                            if (scrapOrCachedViewForId != null) {
                                holder.mPosition = offsetPosition;
                                fromScrapOrHiddenOrCache = true;
                            }
                        }
                        if (holder == null && this.mViewCacheExtension != null) {
                            View viewForPositionAndType = this.mViewCacheExtension.getViewForPositionAndType(this, position, type);
                            View view = viewForPositionAndType;
                            if (viewForPositionAndType != null) {
                                ViewHolder childViewHolder = this.this$0.getChildViewHolder(view);
                                holder = childViewHolder;
                                if (childViewHolder == null) {
                                    IllegalArgumentException illegalArgumentException = new IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder");
                                    throw illegalArgumentException;
                                } else if (holder.shouldIgnore()) {
                                    IllegalArgumentException illegalArgumentException2 = new IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
                                    throw illegalArgumentException2;
                                }
                            }
                        }
                        if (holder == null) {
                            ViewHolder recycledView = getRecycledViewPool().getRecycledView(type);
                            holder = recycledView;
                            if (recycledView != null) {
                                holder.resetInternal();
                                if (RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST) {
                                    invalidateDisplayListInt(holder);
                                }
                            }
                        }
                        if (holder == null) {
                            long start = this.this$0.getNanoTime();
                            if (deadlineNs != RecyclerView.FOREVER_NS && !this.mRecyclerPool.willCreateInTime(type, start, deadlineNs)) {
                                return null;
                            }
                            ViewHolder createViewHolder = this.this$0.mAdapter.createViewHolder(this.this$0, type);
                            holder = createViewHolder;
                            if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
                                RecyclerView findNestedRecyclerView = RecyclerView.findNestedRecyclerView(createViewHolder.itemView);
                                RecyclerView innerView = findNestedRecyclerView;
                                if (findNestedRecyclerView != null) {
                                    WeakReference weakReference = new WeakReference(innerView);
                                    holder.mNestedRecyclerView = weakReference;
                                }
                            }
                            long nanoTime = this.this$0.getNanoTime();
                            long j3 = nanoTime;
                            this.mRecyclerPool.factorInCreateTime(type, nanoTime - start);
                        }
                    } else {
                        IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + position + "(offset:" + offsetPosition + ")." + "state:" + this.this$0.mState.getItemCount());
                        throw indexOutOfBoundsException;
                    }
                }
                if (fromScrapOrHiddenOrCache && !this.this$0.mState.isPreLayout() && holder.hasAnyOfTheFlags(8192)) {
                    holder.setFlags(0, 8192);
                    if (this.this$0.mState.mRunSimpleAnimations) {
                        int buildAdapterChangeFlagsForAnimations = ItemAnimator.buildAdapterChangeFlagsForAnimations(holder);
                        int i3 = buildAdapterChangeFlagsForAnimations;
                        int i4 = buildAdapterChangeFlagsForAnimations | 4096;
                        int changeFlags = i4;
                        this.this$0.recordAnimationInfoIfBouncedHiddenView(holder, this.this$0.mItemAnimator.recordPreLayoutInformation(this.this$0.mState, holder, i4, holder.getUnmodifiedPayloads()));
                    }
                }
                boolean bound = false;
                if (this.this$0.mState.isPreLayout() && holder.isBound()) {
                    holder.mPreLayoutPosition = position;
                } else if (!holder.isBound() || holder.needsUpdate() || holder.isInvalid()) {
                    int findPositionOffset2 = this.this$0.mAdapterHelper.findPositionOffset(position);
                    int i5 = findPositionOffset2;
                    bound = tryBindViewHolderByDeadline(holder, findPositionOffset2, position, deadlineNs);
                }
                android.view.ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                android.view.ViewGroup.LayoutParams lp = layoutParams;
                if (layoutParams == null) {
                    rvLayoutParams = (LayoutParams) this.this$0.generateDefaultLayoutParams();
                    holder.itemView.setLayoutParams(rvLayoutParams);
                } else if (this.this$0.checkLayoutParams(lp)) {
                    rvLayoutParams = (LayoutParams) lp;
                } else {
                    rvLayoutParams = (LayoutParams) this.this$0.generateLayoutParams(lp);
                    holder.itemView.setLayoutParams(rvLayoutParams);
                }
                rvLayoutParams.mViewHolder = holder;
                rvLayoutParams.mPendingInvalidate = fromScrapOrHiddenOrCache && bound;
                return holder;
            }
            IndexOutOfBoundsException indexOutOfBoundsException2 = new IndexOutOfBoundsException("Invalid item position " + position + "(" + position + "). Item count:" + this.this$0.mState.getItemCount());
            throw indexOutOfBoundsException2;
        }

        private void attachAccessibilityDelegate(View view) {
            View itemView = view;
            View view2 = itemView;
            if (this.this$0.isAccessibilityEnabled()) {
                if (ViewCompat.getImportantForAccessibility(itemView) == 0) {
                    ViewCompat.setImportantForAccessibility(itemView, 1);
                }
                if (!ViewCompat.hasAccessibilityDelegate(itemView)) {
                    ViewCompat.setAccessibilityDelegate(itemView, this.this$0.mAccessibilityDelegate.getItemDelegate());
                }
            }
        }

        private void invalidateDisplayListInt(ViewHolder viewHolder) {
            ViewHolder holder = viewHolder;
            ViewHolder viewHolder2 = holder;
            if (holder.itemView instanceof ViewGroup) {
                invalidateDisplayListInt((ViewGroup) holder.itemView, false);
            }
        }

        private void invalidateDisplayListInt(ViewGroup viewGroup, boolean z) {
            ViewGroup viewGroup2 = viewGroup;
            ViewGroup viewGroup3 = viewGroup2;
            boolean invalidateThis = z;
            for (int i = viewGroup2.getChildCount() - 1; i >= 0; i--) {
                View childAt = viewGroup2.getChildAt(i);
                View view = childAt;
                if (childAt instanceof ViewGroup) {
                    invalidateDisplayListInt((ViewGroup) view, true);
                }
            }
            if (invalidateThis) {
                if (viewGroup2.getVisibility() != 4) {
                    int visibility = viewGroup2.getVisibility();
                    int i2 = visibility;
                    viewGroup2.setVisibility(4);
                    viewGroup2.setVisibility(visibility);
                } else {
                    viewGroup2.setVisibility(0);
                    viewGroup2.setVisibility(4);
                }
            }
        }

        public void recycleView(View view) {
            View view2 = view;
            View view3 = view2;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view2);
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt.isTmpDetached()) {
                this.this$0.removeDetachedView(view2, false);
            }
            if (holder.isScrap()) {
                holder.unScrap();
            } else if (holder.wasReturnedFromScrap()) {
                holder.clearReturnedFromScrapFlag();
            }
            recycleViewHolderInternal(holder);
        }

        /* access modifiers changed from: 0000 */
        public void recycleViewInternal(View view) {
            View view2 = view;
            View view3 = view2;
            recycleViewHolderInternal(RecyclerView.getChildViewHolderInt(view2));
        }

        /* access modifiers changed from: 0000 */
        public void recycleAndClearCachedViews() {
            int size = this.mCachedViews.size();
            int i = size;
            for (int i2 = size - 1; i2 >= 0; i2--) {
                recycleCachedViewAt(i2);
            }
            this.mCachedViews.clear();
            if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
                this.this$0.mPrefetchRegistry.clearPrefetchPositions();
            }
        }

        /* access modifiers changed from: 0000 */
        public void recycleCachedViewAt(int i) {
            int cachedViewIndex = i;
            int i2 = cachedViewIndex;
            addViewHolderToRecycledViewPool((ViewHolder) this.mCachedViews.get(cachedViewIndex), true);
            Object remove = this.mCachedViews.remove(cachedViewIndex);
        }

        /* access modifiers changed from: 0000 */
        public void recycleViewHolderInternal(ViewHolder viewHolder) {
            ViewHolder holder = viewHolder;
            ViewHolder viewHolder2 = holder;
            if (holder.isScrap() || holder.itemView.getParent() != null) {
                throw new IllegalArgumentException("Scrapped or attached views may not be recycled. isScrap:" + holder.isScrap() + " isAttached:" + (holder.itemView.getParent() != null));
            } else if (holder.isTmpDetached()) {
                throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + holder);
            } else if (!holder.shouldIgnore()) {
                boolean access$700 = ViewHolder.access$700(holder);
                boolean transientStatePreventsRecycling = access$700;
                boolean cached = false;
                boolean recycled = false;
                if ((this.this$0.mAdapter != null && access$700 && this.this$0.mAdapter.onFailedToRecycleView(holder)) || holder.isRecyclable()) {
                    if (this.mViewCacheMax > 0 && !holder.hasAnyOfTheFlags(526)) {
                        int size = this.mCachedViews.size();
                        int cachedViewSize = size;
                        if (size >= this.mViewCacheMax && cachedViewSize > 0) {
                            recycleCachedViewAt(0);
                            cachedViewSize--;
                        }
                        int targetCacheIndex = cachedViewSize;
                        if (RecyclerView.ALLOW_THREAD_GAP_WORK && cachedViewSize > 0 && !this.this$0.mPrefetchRegistry.lastPrefetchIncludedPosition(holder.mPosition)) {
                            int cacheIndex = cachedViewSize - 1;
                            while (cacheIndex >= 0) {
                                int i = ((ViewHolder) this.mCachedViews.get(cacheIndex)).mPosition;
                                int i2 = i;
                                if (!this.this$0.mPrefetchRegistry.lastPrefetchIncludedPosition(i)) {
                                    break;
                                }
                                cacheIndex--;
                            }
                            targetCacheIndex = cacheIndex + 1;
                        }
                        this.mCachedViews.add(targetCacheIndex, holder);
                        cached = true;
                    }
                    if (!cached) {
                        addViewHolderToRecycledViewPool(holder, true);
                        recycled = true;
                    }
                }
                this.this$0.mViewInfoStore.removeViewHolder(holder);
                if (!cached && !recycled && transientStatePreventsRecycling) {
                    holder.mOwnerRecyclerView = null;
                }
            } else {
                throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
            }
        }

        /* access modifiers changed from: 0000 */
        public void addViewHolderToRecycledViewPool(ViewHolder viewHolder, boolean z) {
            ViewHolder holder = viewHolder;
            ViewHolder viewHolder2 = holder;
            boolean dispatchRecycled = z;
            RecyclerView.clearNestedRecyclerViewIfNotNested(holder);
            ViewCompat.setAccessibilityDelegate(holder.itemView, null);
            if (dispatchRecycled) {
                dispatchViewRecycled(holder);
            }
            holder.mOwnerRecyclerView = null;
            getRecycledViewPool().putRecycledView(holder);
        }

        /* access modifiers changed from: 0000 */
        public void quickRecycleScrapView(View view) {
            View view2 = view;
            View view3 = view2;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view2);
            ViewHolder holder = childViewHolderInt;
            Recycler access$802 = ViewHolder.access$802(childViewHolderInt, null);
            boolean access$902 = ViewHolder.access$902(holder, false);
            holder.clearReturnedFromScrapFlag();
            recycleViewHolderInternal(holder);
        }

        /* access modifiers changed from: 0000 */
        public void scrapView(View view) {
            View view2 = view;
            View view3 = view2;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view2);
            ViewHolder holder = childViewHolderInt;
            if (!childViewHolderInt.hasAnyOfTheFlags(12) && holder.isUpdated() && !this.this$0.canReuseUpdatedViewHolder(holder)) {
                if (this.mChangedScrap == null) {
                    this.mChangedScrap = new ArrayList();
                }
                holder.setScrapContainer(this, true);
                boolean add = this.mChangedScrap.add(holder);
            } else if (holder.isInvalid() && !holder.isRemoved() && !this.this$0.mAdapter.hasStableIds()) {
                throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
            } else {
                holder.setScrapContainer(this, false);
                boolean add2 = this.mAttachedScrap.add(holder);
            }
        }

        /* access modifiers changed from: 0000 */
        public void unscrapView(ViewHolder viewHolder) {
            ViewHolder holder = viewHolder;
            ViewHolder viewHolder2 = holder;
            if (!ViewHolder.access$900(holder)) {
                boolean remove = this.mAttachedScrap.remove(holder);
            } else {
                boolean remove2 = this.mChangedScrap.remove(holder);
            }
            Recycler access$802 = ViewHolder.access$802(holder, null);
            boolean access$902 = ViewHolder.access$902(holder, false);
            holder.clearReturnedFromScrapFlag();
        }

        /* access modifiers changed from: 0000 */
        public int getScrapCount() {
            return this.mAttachedScrap.size();
        }

        /* access modifiers changed from: 0000 */
        public View getScrapViewAt(int i) {
            int index = i;
            int i2 = index;
            return ((ViewHolder) this.mAttachedScrap.get(index)).itemView;
        }

        /* access modifiers changed from: 0000 */
        public void clearScrap() {
            this.mAttachedScrap.clear();
            if (this.mChangedScrap != null) {
                this.mChangedScrap.clear();
            }
        }

        /* access modifiers changed from: 0000 */
        public ViewHolder getChangedScrapViewForPosition(int i) {
            int position = i;
            int i2 = position;
            if (this.mChangedScrap != null) {
                int size = this.mChangedScrap.size();
                int changedScrapSize = size;
                if (size != 0) {
                    for (int i3 = 0; i3 < changedScrapSize; i3++) {
                        ViewHolder viewHolder = (ViewHolder) this.mChangedScrap.get(i3);
                        ViewHolder holder = viewHolder;
                        if (!viewHolder.wasReturnedFromScrap() && holder.getLayoutPosition() == position) {
                            holder.addFlags(32);
                            return holder;
                        }
                    }
                    if (this.this$0.mAdapter.hasStableIds()) {
                        int findPositionOffset = this.this$0.mAdapterHelper.findPositionOffset(position);
                        int i4 = findPositionOffset;
                        if (findPositionOffset > 0 && i4 < this.this$0.mAdapter.getItemCount()) {
                            long id = this.this$0.mAdapter.getItemId(i4);
                            for (int i5 = 0; i5 < changedScrapSize; i5++) {
                                ViewHolder viewHolder2 = (ViewHolder) this.mChangedScrap.get(i5);
                                ViewHolder holder2 = viewHolder2;
                                if (!viewHolder2.wasReturnedFromScrap() && holder2.getItemId() == id) {
                                    holder2.addFlags(32);
                                    return holder2;
                                }
                            }
                        }
                    }
                    return null;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public ViewHolder getScrapOrHiddenOrCachedHolderForPosition(int i, boolean z) {
            int position = i;
            int i2 = position;
            boolean dryRun = z;
            int scrapCount = this.mAttachedScrap.size();
            for (int i3 = 0; i3 < scrapCount; i3++) {
                ViewHolder viewHolder = (ViewHolder) this.mAttachedScrap.get(i3);
                ViewHolder holder = viewHolder;
                if (!viewHolder.wasReturnedFromScrap() && holder.getLayoutPosition() == position && !holder.isInvalid() && (this.this$0.mState.mInPreLayout || !holder.isRemoved())) {
                    holder.addFlags(32);
                    return holder;
                }
            }
            if (!dryRun) {
                View findHiddenNonRemovedView = this.this$0.mChildHelper.findHiddenNonRemovedView(position);
                View view = findHiddenNonRemovedView;
                if (findHiddenNonRemovedView != null) {
                    ViewHolder vh = RecyclerView.getChildViewHolderInt(view);
                    this.this$0.mChildHelper.unhide(view);
                    int indexOfChild = this.this$0.mChildHelper.indexOfChild(view);
                    int layoutIndex = indexOfChild;
                    if (indexOfChild != -1) {
                        this.this$0.mChildHelper.detachViewFromParent(layoutIndex);
                        scrapView(view);
                        vh.addFlags(8224);
                        return vh;
                    }
                    throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + vh);
                }
            }
            int cacheSize = this.mCachedViews.size();
            for (int i4 = 0; i4 < cacheSize; i4++) {
                ViewHolder viewHolder2 = (ViewHolder) this.mCachedViews.get(i4);
                ViewHolder holder2 = viewHolder2;
                if (!viewHolder2.isInvalid() && holder2.getLayoutPosition() == position) {
                    if (!dryRun) {
                        Object remove = this.mCachedViews.remove(i4);
                    }
                    return holder2;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public ViewHolder getScrapOrCachedViewForId(long j, int i, boolean z) {
            long id = j;
            int type = i;
            long j2 = id;
            int i2 = type;
            boolean dryRun = z;
            int size = this.mAttachedScrap.size();
            int i3 = size;
            for (int i4 = size - 1; i4 >= 0; i4--) {
                ViewHolder viewHolder = (ViewHolder) this.mAttachedScrap.get(i4);
                ViewHolder holder = viewHolder;
                if (viewHolder.getItemId() == id && !holder.wasReturnedFromScrap()) {
                    if (type == holder.getItemViewType()) {
                        holder.addFlags(32);
                        if (holder.isRemoved() && !this.this$0.mState.isPreLayout()) {
                            holder.setFlags(2, 14);
                        }
                        return holder;
                    } else if (!dryRun) {
                        Object remove = this.mAttachedScrap.remove(i4);
                        this.this$0.removeDetachedView(holder.itemView, false);
                        quickRecycleScrapView(holder.itemView);
                    }
                }
            }
            int size2 = this.mCachedViews.size();
            int i5 = size2;
            for (int i6 = size2 - 1; i6 >= 0; i6--) {
                ViewHolder viewHolder2 = (ViewHolder) this.mCachedViews.get(i6);
                ViewHolder holder2 = viewHolder2;
                if (viewHolder2.getItemId() == id) {
                    if (type == holder2.getItemViewType()) {
                        if (!dryRun) {
                            Object remove2 = this.mCachedViews.remove(i6);
                        }
                        return holder2;
                    } else if (!dryRun) {
                        recycleCachedViewAt(i6);
                        return null;
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public void dispatchViewRecycled(ViewHolder viewHolder) {
            ViewHolder holder = viewHolder;
            ViewHolder viewHolder2 = holder;
            if (this.this$0.mRecyclerListener != null) {
                this.this$0.mRecyclerListener.onViewRecycled(holder);
            }
            if (this.this$0.mAdapter != null) {
                this.this$0.mAdapter.onViewRecycled(holder);
            }
            if (this.this$0.mState != null) {
                this.this$0.mViewInfoStore.removeViewHolder(holder);
            }
        }

        /* access modifiers changed from: 0000 */
        public void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean z) {
            Adapter oldAdapter = adapter;
            Adapter newAdapter = adapter2;
            Adapter adapter3 = oldAdapter;
            Adapter adapter4 = newAdapter;
            boolean compatibleWithPrevious = z;
            clear();
            getRecycledViewPool().onAdapterChanged(oldAdapter, newAdapter, compatibleWithPrevious);
        }

        /* access modifiers changed from: 0000 */
        public void offsetPositionRecordsForMove(int i, int i2) {
            int start;
            int end;
            int inBetweenOffset;
            int from = i;
            int to = i2;
            int i3 = from;
            int i4 = to;
            if (from >= to) {
                start = to;
                end = from;
                inBetweenOffset = 1;
            } else {
                start = from;
                end = to;
                inBetweenOffset = -1;
            }
            int cachedCount = this.mCachedViews.size();
            for (int i5 = 0; i5 < cachedCount; i5++) {
                ViewHolder viewHolder = (ViewHolder) this.mCachedViews.get(i5);
                ViewHolder holder = viewHolder;
                if (viewHolder != null && holder.mPosition >= start && holder.mPosition <= end) {
                    if (holder.mPosition != from) {
                        holder.offsetPosition(inBetweenOffset, false);
                    } else {
                        holder.offsetPosition(to - from, false);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void offsetPositionRecordsForInsert(int i, int i2) {
            int insertedAt = i;
            int count = i2;
            int i3 = insertedAt;
            int i4 = count;
            int cachedCount = this.mCachedViews.size();
            for (int i5 = 0; i5 < cachedCount; i5++) {
                ViewHolder viewHolder = (ViewHolder) this.mCachedViews.get(i5);
                ViewHolder holder = viewHolder;
                if (viewHolder != null && holder.mPosition >= insertedAt) {
                    holder.offsetPosition(count, true);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void offsetPositionRecordsForRemove(int i, int i2, boolean z) {
            int removedFrom = i;
            int count = i2;
            int i3 = removedFrom;
            int i4 = count;
            boolean applyToPreLayout = z;
            int removedEnd = removedFrom + count;
            int size = this.mCachedViews.size();
            int i5 = size;
            for (int i6 = size - 1; i6 >= 0; i6--) {
                ViewHolder viewHolder = (ViewHolder) this.mCachedViews.get(i6);
                ViewHolder holder = viewHolder;
                if (viewHolder != null) {
                    if (holder.mPosition >= removedEnd) {
                        holder.offsetPosition(-count, applyToPreLayout);
                    } else if (holder.mPosition >= removedFrom) {
                        holder.addFlags(8);
                        recycleCachedViewAt(i6);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
            ViewCacheExtension extension = viewCacheExtension;
            ViewCacheExtension viewCacheExtension2 = extension;
            this.mViewCacheExtension = extension;
        }

        /* access modifiers changed from: 0000 */
        public void setRecycledViewPool(RecycledViewPool recycledViewPool) {
            RecycledViewPool pool = recycledViewPool;
            RecycledViewPool recycledViewPool2 = pool;
            if (this.mRecyclerPool != null) {
                this.mRecyclerPool.detach();
            }
            this.mRecyclerPool = pool;
            if (pool != null) {
                this.mRecyclerPool.attach(this.this$0.getAdapter());
            }
        }

        /* access modifiers changed from: 0000 */
        public RecycledViewPool getRecycledViewPool() {
            if (this.mRecyclerPool == null) {
                this.mRecyclerPool = new RecycledViewPool();
            }
            return this.mRecyclerPool;
        }

        /* access modifiers changed from: 0000 */
        public void viewRangeUpdate(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            int positionEnd = positionStart + itemCount;
            int size = this.mCachedViews.size();
            int i5 = size;
            for (int i6 = size - 1; i6 >= 0; i6--) {
                ViewHolder viewHolder = (ViewHolder) this.mCachedViews.get(i6);
                ViewHolder holder = viewHolder;
                if (viewHolder != null) {
                    int layoutPosition = holder.getLayoutPosition();
                    int pos = layoutPosition;
                    if (layoutPosition >= positionStart && pos < positionEnd) {
                        holder.addFlags(2);
                        recycleCachedViewAt(i6);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void setAdapterPositionsAsUnknown() {
            int cachedCount = this.mCachedViews.size();
            for (int i = 0; i < cachedCount; i++) {
                ViewHolder viewHolder = (ViewHolder) this.mCachedViews.get(i);
                ViewHolder holder = viewHolder;
                if (viewHolder != null) {
                    holder.addFlags(512);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void markKnownViewsInvalid() {
            if (this.this$0.mAdapter != null && this.this$0.mAdapter.hasStableIds()) {
                int cachedCount = this.mCachedViews.size();
                for (int i = 0; i < cachedCount; i++) {
                    ViewHolder viewHolder = (ViewHolder) this.mCachedViews.get(i);
                    ViewHolder holder = viewHolder;
                    if (viewHolder != null) {
                        holder.addFlags(6);
                        holder.addChangePayload(null);
                    }
                }
                return;
            }
            recycleAndClearCachedViews();
        }

        /* access modifiers changed from: 0000 */
        public void clearOldPositions() {
            int cachedCount = this.mCachedViews.size();
            for (int i = 0; i < cachedCount; i++) {
                ViewHolder viewHolder = (ViewHolder) this.mCachedViews.get(i);
                ViewHolder viewHolder2 = viewHolder;
                viewHolder.clearOldPosition();
            }
            int scrapCount = this.mAttachedScrap.size();
            for (int i2 = 0; i2 < scrapCount; i2++) {
                ((ViewHolder) this.mAttachedScrap.get(i2)).clearOldPosition();
            }
            if (this.mChangedScrap != null) {
                int changedScrapCount = this.mChangedScrap.size();
                for (int i3 = 0; i3 < changedScrapCount; i3++) {
                    ((ViewHolder) this.mChangedScrap.get(i3)).clearOldPosition();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void markItemDecorInsetsDirty() {
            int cachedCount = this.mCachedViews.size();
            for (int i = 0; i < cachedCount; i++) {
                ViewHolder viewHolder = (ViewHolder) this.mCachedViews.get(i);
                ViewHolder viewHolder2 = viewHolder;
                LayoutParams layoutParams = (LayoutParams) viewHolder.itemView.getLayoutParams();
                LayoutParams layoutParams2 = layoutParams;
                if (layoutParams != null) {
                    layoutParams2.mInsetsDirty = true;
                }
            }
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$RecyclerListener */
    public interface RecyclerListener {
        void onViewRecycled(ViewHolder viewHolder);
    }

    /* renamed from: android.support.v7.widget.RecyclerView$RecyclerViewDataObserver */
    private class RecyclerViewDataObserver extends AdapterDataObserver {
        final /* synthetic */ RecyclerView this$0;

        RecyclerViewDataObserver(RecyclerView recyclerView) {
            this.this$0 = recyclerView;
        }

        public void onChanged() {
            this.this$0.assertNotInLayoutOrScroll(null);
            this.this$0.mState.mStructureChanged = true;
            this.this$0.setDataSetChangedAfterLayout();
            if (!this.this$0.mAdapterHelper.hasPendingUpdates()) {
                this.this$0.requestLayout();
            }
        }

        public void onItemRangeChanged(int i, int i2, Object obj) {
            int positionStart = i;
            int itemCount = i2;
            Object payload = obj;
            int i3 = positionStart;
            int i4 = itemCount;
            Object obj2 = payload;
            this.this$0.assertNotInLayoutOrScroll(null);
            if (this.this$0.mAdapterHelper.onItemRangeChanged(positionStart, itemCount, payload)) {
                triggerUpdateProcessor();
            }
        }

        public void onItemRangeInserted(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            this.this$0.assertNotInLayoutOrScroll(null);
            if (this.this$0.mAdapterHelper.onItemRangeInserted(positionStart, itemCount)) {
                triggerUpdateProcessor();
            }
        }

        public void onItemRangeRemoved(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            this.this$0.assertNotInLayoutOrScroll(null);
            if (this.this$0.mAdapterHelper.onItemRangeRemoved(positionStart, itemCount)) {
                triggerUpdateProcessor();
            }
        }

        public void onItemRangeMoved(int i, int i2, int i3) {
            int fromPosition = i;
            int toPosition = i2;
            int itemCount = i3;
            int i4 = fromPosition;
            int i5 = toPosition;
            int i6 = itemCount;
            this.this$0.assertNotInLayoutOrScroll(null);
            if (this.this$0.mAdapterHelper.onItemRangeMoved(fromPosition, toPosition, itemCount)) {
                triggerUpdateProcessor();
            }
        }

        /* access modifiers changed from: 0000 */
        public void triggerUpdateProcessor() {
            if (RecyclerView.POST_UPDATES_ON_ANIMATION && this.this$0.mHasFixedSize && this.this$0.mIsAttached) {
                ViewCompat.postOnAnimation(this.this$0, this.this$0.mUpdateChildViewsRunnable);
                return;
            }
            this.this$0.mAdapterUpdateDuringMeasure = true;
            this.this$0.requestLayout();
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.widget.RecyclerView$SavedState */
    public static class SavedState extends AbsSavedState {
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
        Parcelable mLayoutState;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            ClassLoader classLoader2;
            Parcel in = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = in;
            ClassLoader classLoader3 = loader;
            super(in, loader);
            if (loader == null) {
                classLoader2 = LayoutManager.class.getClassLoader();
            } else {
                classLoader2 = loader;
            }
            this.mLayoutState = in.readParcelable(classLoader2);
        }

        SavedState(Parcelable parcelable) {
            Parcelable superState = parcelable;
            Parcelable parcelable2 = superState;
            super(superState);
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            int flags = i;
            Parcel parcel2 = dest;
            int i2 = flags;
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.mLayoutState, 0);
        }

        /* access modifiers changed from: 0000 */
        public void copyFrom(SavedState savedState) {
            SavedState other = savedState;
            SavedState savedState2 = other;
            this.mLayoutState = other.mLayoutState;
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$SimpleOnItemTouchListener */
    public static class SimpleOnItemTouchListener implements OnItemTouchListener {
        public SimpleOnItemTouchListener() {
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            RecyclerView recyclerView2 = recyclerView;
            MotionEvent motionEvent2 = motionEvent;
            return false;
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            RecyclerView recyclerView2 = recyclerView;
            MotionEvent motionEvent2 = motionEvent;
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
            boolean z2 = z;
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$SmoothScroller */
    public static abstract class SmoothScroller {
        private LayoutManager mLayoutManager;
        private boolean mPendingInitialRun;
        private RecyclerView mRecyclerView;
        private final Action mRecyclingAction = new Action(0, 0);
        private boolean mRunning;
        private int mTargetPosition = -1;
        private View mTargetView;

        /* renamed from: android.support.v7.widget.RecyclerView$SmoothScroller$Action */
        public static class Action {
            public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
            private boolean changed;
            private int consecutiveUpdates;
            private int mDuration;
            private int mDx;
            private int mDy;
            private Interpolator mInterpolator;
            private int mJumpToPosition;

            public Action(int i, int i2, int i3, Interpolator interpolator) {
                int dx = i;
                int dy = i2;
                int duration = i3;
                Interpolator interpolator2 = interpolator;
                int i4 = dx;
                int i5 = dy;
                int i6 = duration;
                Interpolator interpolator3 = interpolator2;
                this.mJumpToPosition = -1;
                this.changed = false;
                this.consecutiveUpdates = 0;
                this.mDx = dx;
                this.mDy = dy;
                this.mDuration = duration;
                this.mInterpolator = interpolator2;
            }

            public Action(int i, int i2) {
                int dx = i;
                int dy = i2;
                int i3 = dx;
                int i4 = dy;
                this(dx, dy, Integer.MIN_VALUE, null);
            }

            public Action(int i, int i2, int i3) {
                int dx = i;
                int dy = i2;
                int duration = i3;
                int i4 = dx;
                int i5 = dy;
                int i6 = duration;
                this(dx, dy, duration, null);
            }

            public void jumpTo(int i) {
                int targetPosition = i;
                int i2 = targetPosition;
                this.mJumpToPosition = targetPosition;
            }

            /* access modifiers changed from: 0000 */
            public boolean hasJumpTarget() {
                return this.mJumpToPosition >= 0;
            }

            /* access modifiers changed from: 0000 */
            public void runIfNecessary(RecyclerView recyclerView) {
                RecyclerView recyclerView2 = recyclerView;
                RecyclerView recyclerView3 = recyclerView2;
                if (this.mJumpToPosition < 0) {
                    if (!this.changed) {
                        this.consecutiveUpdates = 0;
                    } else {
                        validate();
                        if (this.mInterpolator != null) {
                            recyclerView2.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
                        } else if (this.mDuration != Integer.MIN_VALUE) {
                            recyclerView2.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
                        } else {
                            recyclerView2.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
                        }
                        this.consecutiveUpdates++;
                        if (this.consecutiveUpdates > 10) {
                            int e = Log.e(RecyclerView.TAG, "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                        }
                        this.changed = false;
                    }
                    return;
                }
                int i = this.mJumpToPosition;
                int i2 = i;
                this.mJumpToPosition = -1;
                recyclerView2.jumpToPositionForSmoothScroller(i);
                this.changed = false;
            }

            private void validate() {
                if (this.mInterpolator != null && this.mDuration < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                } else if (this.mDuration < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
            }

            public int getDx() {
                return this.mDx;
            }

            public void setDx(int i) {
                int dx = i;
                int i2 = dx;
                this.changed = true;
                this.mDx = dx;
            }

            public int getDy() {
                return this.mDy;
            }

            public void setDy(int i) {
                int dy = i;
                int i2 = dy;
                this.changed = true;
                this.mDy = dy;
            }

            public int getDuration() {
                return this.mDuration;
            }

            public void setDuration(int i) {
                int duration = i;
                int i2 = duration;
                this.changed = true;
                this.mDuration = duration;
            }

            public Interpolator getInterpolator() {
                return this.mInterpolator;
            }

            public void setInterpolator(Interpolator interpolator) {
                Interpolator interpolator2 = interpolator;
                Interpolator interpolator3 = interpolator2;
                this.changed = true;
                this.mInterpolator = interpolator2;
            }

            public void update(int i, int i2, int i3, Interpolator interpolator) {
                int dx = i;
                int dy = i2;
                int duration = i3;
                Interpolator interpolator2 = interpolator;
                int i4 = dx;
                int i5 = dy;
                int i6 = duration;
                Interpolator interpolator3 = interpolator2;
                this.mDx = dx;
                this.mDy = dy;
                this.mDuration = duration;
                this.mInterpolator = interpolator2;
                this.changed = true;
            }
        }

        /* renamed from: android.support.v7.widget.RecyclerView$SmoothScroller$ScrollVectorProvider */
        public interface ScrollVectorProvider {
            PointF computeScrollVectorForPosition(int i);
        }

        /* access modifiers changed from: protected */
        public abstract void onSeekTargetStep(int i, int i2, State state, Action action);

        /* access modifiers changed from: protected */
        public abstract void onStart();

        /* access modifiers changed from: protected */
        public abstract void onStop();

        /* access modifiers changed from: protected */
        public abstract void onTargetFound(View view, State state, Action action);

        static /* synthetic */ void access$400(SmoothScroller smoothScroller, int i, int i2) {
            SmoothScroller x0 = smoothScroller;
            int x1 = i;
            int x2 = i2;
            SmoothScroller smoothScroller2 = x0;
            int i3 = x1;
            int i4 = x2;
            x0.onAnimation(x1, x2);
        }

        public SmoothScroller() {
        }

        /* access modifiers changed from: 0000 */
        public void start(RecyclerView recyclerView, LayoutManager layoutManager) {
            RecyclerView recyclerView2 = recyclerView;
            LayoutManager layoutManager2 = layoutManager;
            RecyclerView recyclerView3 = recyclerView2;
            LayoutManager layoutManager3 = layoutManager2;
            this.mRecyclerView = recyclerView2;
            this.mLayoutManager = layoutManager2;
            if (this.mTargetPosition != -1) {
                int access$1102 = State.access$1102(this.mRecyclerView.mState, this.mTargetPosition);
                this.mRunning = true;
                this.mPendingInitialRun = true;
                this.mTargetView = findViewByPosition(getTargetPosition());
                onStart();
                this.mRecyclerView.mViewFlinger.postOnAnimation();
                return;
            }
            throw new IllegalArgumentException("Invalid target position");
        }

        public void setTargetPosition(int i) {
            int targetPosition = i;
            int i2 = targetPosition;
            this.mTargetPosition = targetPosition;
        }

        @Nullable
        public LayoutManager getLayoutManager() {
            return this.mLayoutManager;
        }

        /* access modifiers changed from: protected */
        public final void stop() {
            if (this.mRunning) {
                onStop();
                int access$1102 = State.access$1102(this.mRecyclerView.mState, -1);
                this.mTargetView = null;
                this.mTargetPosition = -1;
                this.mPendingInitialRun = false;
                this.mRunning = false;
                LayoutManager.access$1200(this.mLayoutManager, this);
                this.mLayoutManager = null;
                this.mRecyclerView = null;
            }
        }

        public boolean isPendingInitialRun() {
            return this.mPendingInitialRun;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        public int getTargetPosition() {
            return this.mTargetPosition;
        }

        private void onAnimation(int i, int i2) {
            int dx = i;
            int dy = i2;
            int i3 = dx;
            int i4 = dy;
            RecyclerView recyclerView = this.mRecyclerView;
            if (!this.mRunning || this.mTargetPosition == -1 || recyclerView == null) {
                stop();
            }
            this.mPendingInitialRun = false;
            if (this.mTargetView != null) {
                if (getChildPosition(this.mTargetView) != this.mTargetPosition) {
                    int e = Log.e(RecyclerView.TAG, "Passed over target position while smooth scrolling.");
                    this.mTargetView = null;
                } else {
                    onTargetFound(this.mTargetView, recyclerView.mState, this.mRecyclingAction);
                    this.mRecyclingAction.runIfNecessary(recyclerView);
                    stop();
                }
            }
            if (this.mRunning) {
                onSeekTargetStep(dx, dy, recyclerView.mState, this.mRecyclingAction);
                boolean hasJumpTarget = this.mRecyclingAction.hasJumpTarget();
                boolean z = hasJumpTarget;
                this.mRecyclingAction.runIfNecessary(recyclerView);
                if (hasJumpTarget) {
                    if (!this.mRunning) {
                        stop();
                        return;
                    }
                    this.mPendingInitialRun = true;
                    recyclerView.mViewFlinger.postOnAnimation();
                }
            }
        }

        public int getChildPosition(View view) {
            View view2 = view;
            View view3 = view2;
            return this.mRecyclerView.getChildLayoutPosition(view2);
        }

        public int getChildCount() {
            return this.mRecyclerView.mLayout.getChildCount();
        }

        public View findViewByPosition(int i) {
            int position = i;
            int i2 = position;
            return this.mRecyclerView.mLayout.findViewByPosition(position);
        }

        @Deprecated
        public void instantScrollToPosition(int i) {
            int position = i;
            int i2 = position;
            this.mRecyclerView.scrollToPosition(position);
        }

        /* access modifiers changed from: protected */
        public void onChildAttachedToWindow(View view) {
            View child = view;
            View view2 = child;
            if (getChildPosition(child) == getTargetPosition()) {
                this.mTargetView = child;
            }
        }

        /* access modifiers changed from: protected */
        public void normalize(PointF pointF) {
            PointF scrollVector = pointF;
            PointF pointF2 = scrollVector;
            double sqrt = Math.sqrt((double) ((scrollVector.x * scrollVector.x) + (scrollVector.y * scrollVector.y)));
            double d = sqrt;
            scrollVector.x = (float) (((double) scrollVector.x) / sqrt);
            scrollVector.y = (float) (((double) scrollVector.y) / sqrt);
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$State */
    public static class State {
        static final int STEP_ANIMATIONS = 4;
        static final int STEP_LAYOUT = 2;
        static final int STEP_START = 1;
        private SparseArray<Object> mData;
        int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        long mFocusedItemId;
        int mFocusedItemPosition;
        int mFocusedSubChildId;
        boolean mInPreLayout = false;
        boolean mIsMeasuring = false;
        int mItemCount = 0;
        int mLayoutStep = 1;
        int mPreviousLayoutItemCount = 0;
        boolean mRunPredictiveAnimations = false;
        boolean mRunSimpleAnimations = false;
        boolean mStructureChanged = false;
        private int mTargetPosition = -1;
        boolean mTrackOldChangeHolders = false;

        public State() {
        }

        static /* synthetic */ int access$1102(State state, int i) {
            State x0 = state;
            int x1 = i;
            State state2 = x0;
            int i2 = x1;
            x0.mTargetPosition = x1;
            return x1;
        }

        /* access modifiers changed from: 0000 */
        public void assertLayoutStep(int i) {
            int accepted = i;
            int i2 = accepted;
            if ((accepted & this.mLayoutStep) == 0) {
                throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString(accepted) + " but it is " + Integer.toBinaryString(this.mLayoutStep));
            }
        }

        /* access modifiers changed from: 0000 */
        public State reset() {
            this.mTargetPosition = -1;
            if (this.mData != null) {
                this.mData.clear();
            }
            this.mItemCount = 0;
            this.mStructureChanged = false;
            this.mIsMeasuring = false;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public void prepareForNestedPrefetch(Adapter adapter) {
            Adapter adapter2 = adapter;
            Adapter adapter3 = adapter2;
            this.mLayoutStep = 1;
            this.mItemCount = adapter2.getItemCount();
            this.mStructureChanged = false;
            this.mInPreLayout = false;
            this.mTrackOldChangeHolders = false;
            this.mIsMeasuring = false;
        }

        public boolean isMeasuring() {
            return this.mIsMeasuring;
        }

        public boolean isPreLayout() {
            return this.mInPreLayout;
        }

        public boolean willRunPredictiveAnimations() {
            return this.mRunPredictiveAnimations;
        }

        public boolean willRunSimpleAnimations() {
            return this.mRunSimpleAnimations;
        }

        public void remove(int i) {
            int resourceId = i;
            int i2 = resourceId;
            if (this.mData != null) {
                this.mData.remove(resourceId);
            }
        }

        public <T> T get(int i) {
            int resourceId = i;
            int i2 = resourceId;
            if (this.mData != null) {
                return this.mData.get(resourceId);
            }
            return null;
        }

        public void put(int i, Object obj) {
            int resourceId = i;
            Object data = obj;
            int i2 = resourceId;
            Object obj2 = data;
            if (this.mData == null) {
                this.mData = new SparseArray<>();
            }
            this.mData.put(resourceId, data);
        }

        public int getTargetScrollPosition() {
            return this.mTargetPosition;
        }

        public boolean hasTargetScrollPosition() {
            return this.mTargetPosition != -1;
        }

        public boolean didStructureChange() {
            return this.mStructureChanged;
        }

        public int getItemCount() {
            return !this.mInPreLayout ? this.mItemCount : this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
        }

        public String toString() {
            return "State{mTargetPosition=" + this.mTargetPosition + ", mData=" + this.mData + ", mItemCount=" + this.mItemCount + ", mPreviousLayoutItemCount=" + this.mPreviousLayoutItemCount + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.mDeletedInvisibleItemCountSincePreviousLayout + ", mStructureChanged=" + this.mStructureChanged + ", mInPreLayout=" + this.mInPreLayout + ", mRunSimpleAnimations=" + this.mRunSimpleAnimations + ", mRunPredictiveAnimations=" + this.mRunPredictiveAnimations + '}';
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$ViewCacheExtension */
    public static abstract class ViewCacheExtension {
        public abstract View getViewForPositionAndType(Recycler recycler, int i, int i2);

        public ViewCacheExtension() {
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$ViewFlinger */
    class ViewFlinger implements Runnable {
        private boolean mEatRunOnAnimationRequest = false;
        Interpolator mInterpolator = RecyclerView.sQuinticInterpolator;
        private int mLastFlingX;
        private int mLastFlingY;
        private boolean mReSchedulePostAnimationCallback = false;
        private ScrollerCompat mScroller;
        final /* synthetic */ RecyclerView this$0;

        public ViewFlinger(RecyclerView recyclerView) {
            RecyclerView this$02 = recyclerView;
            RecyclerView recyclerView2 = this$02;
            this.this$0 = this$02;
            this.mScroller = ScrollerCompat.create(this$02.getContext(), RecyclerView.sQuinticInterpolator);
        }

        public void run() {
            if (this.this$0.mLayout != null) {
                disableRunOnAnimationRequests();
                this.this$0.consumePendingUpdateOperations();
                ScrollerCompat scroller = this.mScroller;
                SmoothScroller smoothScroller = this.this$0.mLayout.mSmoothScroller;
                if (scroller.computeScrollOffset()) {
                    int x = scroller.getCurrX();
                    int y = scroller.getCurrY();
                    int dx = x - this.mLastFlingX;
                    int dy = y - this.mLastFlingY;
                    int hresult = 0;
                    int vresult = 0;
                    this.mLastFlingX = x;
                    this.mLastFlingY = y;
                    int overscrollX = 0;
                    int overscrollY = 0;
                    if (this.this$0.mAdapter != null) {
                        this.this$0.eatRequestLayout();
                        this.this$0.onEnterLayoutOrScroll();
                        TraceCompat.beginSection(RecyclerView.TRACE_SCROLL_TAG);
                        if (dx != 0) {
                            hresult = this.this$0.mLayout.scrollHorizontallyBy(dx, this.this$0.mRecycler, this.this$0.mState);
                            overscrollX = dx - hresult;
                        }
                        if (dy != 0) {
                            vresult = this.this$0.mLayout.scrollVerticallyBy(dy, this.this$0.mRecycler, this.this$0.mState);
                            overscrollY = dy - vresult;
                        }
                        TraceCompat.endSection();
                        this.this$0.repositionShadowingViews();
                        this.this$0.onExitLayoutOrScroll();
                        this.this$0.resumeRequestLayout(false);
                        if (smoothScroller != null && !smoothScroller.isPendingInitialRun() && smoothScroller.isRunning()) {
                            int itemCount = this.this$0.mState.getItemCount();
                            int adapterSize = itemCount;
                            if (itemCount == 0) {
                                smoothScroller.stop();
                            } else if (smoothScroller.getTargetPosition() < adapterSize) {
                                SmoothScroller.access$400(smoothScroller, dx - overscrollX, dy - overscrollY);
                            } else {
                                smoothScroller.setTargetPosition(adapterSize - 1);
                                SmoothScroller.access$400(smoothScroller, dx - overscrollX, dy - overscrollY);
                            }
                        }
                    }
                    if (!this.this$0.mItemDecorations.isEmpty()) {
                        this.this$0.invalidate();
                    }
                    if (this.this$0.getOverScrollMode() != 2) {
                        this.this$0.considerReleasingGlowsOnScroll(dx, dy);
                    }
                    if (!(overscrollX == 0 && overscrollY == 0)) {
                        int vel = (int) scroller.getCurrVelocity();
                        int velX = 0;
                        if (overscrollX != x) {
                            int i = overscrollX >= 0 ? overscrollX <= 0 ? 0 : vel : -vel;
                            velX = i;
                        }
                        int velY = 0;
                        if (overscrollY != y) {
                            int i2 = overscrollY >= 0 ? overscrollY <= 0 ? 0 : vel : -vel;
                            velY = i2;
                        }
                        if (this.this$0.getOverScrollMode() != 2) {
                            this.this$0.absorbGlows(velX, velY);
                        }
                        if ((velX != 0 || overscrollX == x || scroller.getFinalX() == 0) && (velY != 0 || overscrollY == y || scroller.getFinalY() == 0)) {
                            scroller.abortAnimation();
                        }
                    }
                    if (!(hresult == 0 && vresult == 0)) {
                        this.this$0.dispatchOnScrolled(hresult, vresult);
                    }
                    if (!RecyclerView.access$500(this.this$0)) {
                        this.this$0.invalidate();
                    }
                    boolean fullyConsumedAny = (dx == 0 && dy == 0) || (dx != 0 && this.this$0.mLayout.canScrollHorizontally() && hresult == dx) || (dy != 0 && this.this$0.mLayout.canScrollVertically() && vresult == dy);
                    if (!scroller.isFinished() && fullyConsumedAny) {
                        postOnAnimation();
                        if (this.this$0.mGapWorker != null) {
                            this.this$0.mGapWorker.postFromTraversal(this.this$0, dx, dy);
                        }
                    } else {
                        this.this$0.setScrollState(0);
                        if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
                            this.this$0.mPrefetchRegistry.clearPrefetchPositions();
                        }
                    }
                }
                if (smoothScroller != null) {
                    if (smoothScroller.isPendingInitialRun()) {
                        SmoothScroller.access$400(smoothScroller, 0, 0);
                    }
                    if (!this.mReSchedulePostAnimationCallback) {
                        smoothScroller.stop();
                    }
                }
                enableRunOnAnimationRequests();
                return;
            }
            stop();
        }

        private void disableRunOnAnimationRequests() {
            this.mReSchedulePostAnimationCallback = false;
            this.mEatRunOnAnimationRequest = true;
        }

        private void enableRunOnAnimationRequests() {
            this.mEatRunOnAnimationRequest = false;
            if (this.mReSchedulePostAnimationCallback) {
                postOnAnimation();
            }
        }

        /* access modifiers changed from: 0000 */
        public void postOnAnimation() {
            if (!this.mEatRunOnAnimationRequest) {
                boolean removeCallbacks = this.this$0.removeCallbacks(this);
                ViewCompat.postOnAnimation(this.this$0, this);
                return;
            }
            this.mReSchedulePostAnimationCallback = true;
        }

        public void fling(int i, int i2) {
            int velocityX = i;
            int velocityY = i2;
            int i3 = velocityX;
            int i4 = velocityY;
            this.this$0.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.fling(0, 0, velocityX, velocityY, Integer.MIN_VALUE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, Integer.MIN_VALUE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
            postOnAnimation();
        }

        public void smoothScrollBy(int i, int i2) {
            int dx = i;
            int dy = i2;
            int i3 = dx;
            int i4 = dy;
            smoothScrollBy(dx, dy, 0, 0);
        }

        public void smoothScrollBy(int i, int i2, int i3, int i4) {
            int dx = i;
            int dy = i2;
            int vx = i3;
            int vy = i4;
            int i5 = dx;
            int i6 = dy;
            int i7 = vx;
            int i8 = vy;
            smoothScrollBy(dx, dy, computeScrollDuration(dx, dy, vx, vy));
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

        private int computeScrollDuration(int i, int i2, int i3, int i4) {
            int duration;
            int dx = i;
            int dy = i2;
            int vx = i3;
            int vy = i4;
            int i5 = dx;
            int i6 = dy;
            int i7 = vx;
            int i8 = vy;
            int absDx = Math.abs(dx);
            int absDy = Math.abs(dy);
            boolean horizontal = absDx > absDy;
            int velocity = (int) Math.sqrt((double) ((vx * vx) + (vy * vy)));
            int containerSize = !horizontal ? this.this$0.getHeight() : this.this$0.getWidth();
            int i9 = containerSize / 2;
            float distanceInfluenceForSnapDuration = ((float) i9) + (((float) i9) * distanceInfluenceForSnapDuration(Math.min(1.0f, (1.0f * ((float) ((int) Math.sqrt((double) ((dx * dx) + (dy * dy)))))) / ((float) containerSize))));
            float f = distanceInfluenceForSnapDuration;
            if (velocity <= 0) {
                float f2 = (float) (!horizontal ? absDy : absDx);
                float f3 = f2;
                duration = (int) (((f2 / ((float) containerSize)) + 1.0f) * 300.0f);
            } else {
                duration = 4 * Math.round(1000.0f * Math.abs(distanceInfluenceForSnapDuration / ((float) velocity)));
            }
            return Math.min(duration, RecyclerView.MAX_SCROLL_DURATION);
        }

        public void smoothScrollBy(int i, int i2, int i3) {
            int dx = i;
            int dy = i2;
            int duration = i3;
            int i4 = dx;
            int i5 = dy;
            int i6 = duration;
            smoothScrollBy(dx, dy, duration, RecyclerView.sQuinticInterpolator);
        }

        public void smoothScrollBy(int i, int i2, Interpolator interpolator) {
            int dx = i;
            int dy = i2;
            Interpolator interpolator2 = interpolator;
            int i3 = dx;
            int i4 = dy;
            Interpolator interpolator3 = interpolator2;
            smoothScrollBy(dx, dy, computeScrollDuration(dx, dy, 0, 0), interpolator2 != null ? interpolator2 : RecyclerView.sQuinticInterpolator);
        }

        public void smoothScrollBy(int i, int i2, int i3, Interpolator interpolator) {
            int dx = i;
            int dy = i2;
            int duration = i3;
            Interpolator interpolator2 = interpolator;
            int i4 = dx;
            int i5 = dy;
            int i6 = duration;
            Interpolator interpolator3 = interpolator2;
            if (this.mInterpolator != interpolator2) {
                this.mInterpolator = interpolator2;
                this.mScroller = ScrollerCompat.create(this.this$0.getContext(), interpolator2);
            }
            this.this$0.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.startScroll(0, 0, dx, dy, duration);
            postOnAnimation();
        }

        public void stop() {
            boolean removeCallbacks = this.this$0.removeCallbacks(this);
            this.mScroller.abortAnimation();
        }
    }

    /* renamed from: android.support.v7.widget.RecyclerView$ViewHolder */
    public static abstract class ViewHolder {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
        public final View itemView;
        private int mFlags;
        private boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1;
        int mItemViewType = -1;
        WeakReference<RecyclerView> mNestedRecyclerView;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        @VisibleForTesting
        int mPendingAccessibilityState = -1;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        private Recycler mScrapContainer = null;
        ViewHolder mShadowedHolder = null;
        ViewHolder mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        static /* synthetic */ boolean access$1300(ViewHolder viewHolder) {
            ViewHolder x0 = viewHolder;
            ViewHolder viewHolder2 = x0;
            return x0.shouldBeKeptAsChild();
        }

        static /* synthetic */ int access$1400(ViewHolder viewHolder) {
            ViewHolder x0 = viewHolder;
            ViewHolder viewHolder2 = x0;
            return x0.mFlags;
        }

        static /* synthetic */ void access$200(ViewHolder viewHolder, RecyclerView recyclerView) {
            ViewHolder x0 = viewHolder;
            RecyclerView x1 = recyclerView;
            ViewHolder viewHolder2 = x0;
            RecyclerView recyclerView2 = x1;
            x0.onEnteredHiddenState(x1);
        }

        static /* synthetic */ void access$300(ViewHolder viewHolder, RecyclerView recyclerView) {
            ViewHolder x0 = viewHolder;
            RecyclerView x1 = recyclerView;
            ViewHolder viewHolder2 = x0;
            RecyclerView recyclerView2 = x1;
            x0.onLeftHiddenState(x1);
        }

        static /* synthetic */ boolean access$700(ViewHolder viewHolder) {
            ViewHolder x0 = viewHolder;
            ViewHolder viewHolder2 = x0;
            return x0.doesTransientStatePreventRecycling();
        }

        static /* synthetic */ Recycler access$802(ViewHolder viewHolder, Recycler recycler) {
            ViewHolder x0 = viewHolder;
            Recycler x1 = recycler;
            ViewHolder viewHolder2 = x0;
            Recycler recycler2 = x1;
            x0.mScrapContainer = x1;
            return x1;
        }

        static /* synthetic */ boolean access$900(ViewHolder viewHolder) {
            ViewHolder x0 = viewHolder;
            ViewHolder viewHolder2 = x0;
            return x0.mInChangeScrap;
        }

        static /* synthetic */ boolean access$902(ViewHolder viewHolder, boolean z) {
            ViewHolder x0 = viewHolder;
            ViewHolder viewHolder2 = x0;
            boolean x1 = z;
            x0.mInChangeScrap = x1;
            return x1;
        }

        public ViewHolder(View view) {
            View itemView2 = view;
            View view2 = itemView2;
            if (itemView2 != null) {
                this.itemView = itemView2;
                return;
            }
            throw new IllegalArgumentException("itemView may not be null");
        }

        /* access modifiers changed from: 0000 */
        public void flagRemovedAndOffsetPosition(int i, int i2, boolean z) {
            int mNewPosition = i;
            int offset = i2;
            int i3 = mNewPosition;
            int i4 = offset;
            boolean applyToPreLayout = z;
            addFlags(8);
            offsetPosition(offset, applyToPreLayout);
            this.mPosition = mNewPosition;
        }

        /* access modifiers changed from: 0000 */
        public void offsetPosition(int i, boolean z) {
            int offset = i;
            int i2 = offset;
            boolean applyToPreLayout = z;
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (applyToPreLayout) {
                this.mPreLayoutPosition += offset;
            }
            this.mPosition += offset;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams) this.itemView.getLayoutParams()).mInsetsDirty = true;
            }
        }

        /* access modifiers changed from: 0000 */
        public void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        /* access modifiers changed from: 0000 */
        public void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean shouldIgnore() {
            return (this.mFlags & 128) != 0;
        }

        @Deprecated
        public final int getPosition() {
            return this.mPreLayoutPosition != -1 ? this.mPreLayoutPosition : this.mPosition;
        }

        public final int getLayoutPosition() {
            return this.mPreLayoutPosition != -1 ? this.mPreLayoutPosition : this.mPosition;
        }

        public final int getAdapterPosition() {
            if (this.mOwnerRecyclerView != null) {
                return this.mOwnerRecyclerView.getAdapterPositionFor(this);
            }
            return -1;
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        /* access modifiers changed from: 0000 */
        public boolean isScrap() {
            return this.mScrapContainer != null;
        }

        /* access modifiers changed from: 0000 */
        public void unScrap() {
            this.mScrapContainer.unscrapView(this);
        }

        /* access modifiers changed from: 0000 */
        public boolean wasReturnedFromScrap() {
            return (this.mFlags & 32) != 0;
        }

        /* access modifiers changed from: 0000 */
        public void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        /* access modifiers changed from: 0000 */
        public void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        /* access modifiers changed from: 0000 */
        public void stopIgnoring() {
            this.mFlags &= -129;
        }

        /* access modifiers changed from: 0000 */
        public void setScrapContainer(Recycler recycler, boolean z) {
            Recycler recycler2 = recycler;
            Recycler recycler3 = recycler2;
            boolean isChangeScrap = z;
            this.mScrapContainer = recycler2;
            this.mInChangeScrap = isChangeScrap;
        }

        /* access modifiers changed from: 0000 */
        public boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean needsUpdate() {
            return (this.mFlags & 2) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean hasAnyOfTheFlags(int i) {
            int flags = i;
            int i2 = flags;
            return (this.mFlags & flags) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean isTmpDetached() {
            return (this.mFlags & 256) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean isAdapterPositionUnknown() {
            return (this.mFlags & 512) != 0 || isInvalid();
        }

        /* access modifiers changed from: 0000 */
        public void setFlags(int i, int i2) {
            int flags = i;
            int mask = i2;
            int i3 = flags;
            int i4 = mask;
            this.mFlags = (this.mFlags & (mask ^ -1)) | (flags & mask);
        }

        /* access modifiers changed from: 0000 */
        public void addFlags(int i) {
            int flags = i;
            int i2 = flags;
            this.mFlags |= flags;
        }

        /* access modifiers changed from: 0000 */
        public void addChangePayload(Object obj) {
            Object payload = obj;
            Object obj2 = payload;
            if (payload == null) {
                addFlags(1024);
            } else if ((this.mFlags & 1024) == 0) {
                createPayloadsIfNeeded();
                boolean add = this.mPayloads.add(payload);
            }
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                this.mPayloads = new ArrayList();
                this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
            }
        }

        /* access modifiers changed from: 0000 */
        public void clearPayload() {
            if (this.mPayloads != null) {
                this.mPayloads.clear();
            }
            this.mFlags &= -1025;
        }

        /* access modifiers changed from: 0000 */
        public List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 1024) != 0) {
                return FULLUPDATE_PAYLOADS;
            }
            if (this.mPayloads == null || this.mPayloads.size() == 0) {
                return FULLUPDATE_PAYLOADS;
            }
            return this.mUnmodifiedPayloads;
        }

        /* access modifiers changed from: 0000 */
        public void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.clearNestedRecyclerViewIfNotNested(this);
        }

        private void onEnteredHiddenState(RecyclerView recyclerView) {
            RecyclerView parent = recyclerView;
            RecyclerView recyclerView2 = parent;
            this.mWasImportantForAccessibilityBeforeHidden = ViewCompat.getImportantForAccessibility(this.itemView);
            boolean childImportantForAccessibilityInternal = parent.setChildImportantForAccessibilityInternal(this, 4);
        }

        private void onLeftHiddenState(RecyclerView recyclerView) {
            RecyclerView parent = recyclerView;
            RecyclerView recyclerView2 = parent;
            boolean childImportantForAccessibilityInternal = parent.setChildImportantForAccessibilityInternal(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("ViewHolder{" + Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            if (isScrap()) {
                StringBuilder append = sb.append(" scrap ").append(!this.mInChangeScrap ? "[attachedScrap]" : "[changeScrap]");
            }
            if (isInvalid()) {
                StringBuilder append2 = sb.append(" invalid");
            }
            if (!isBound()) {
                StringBuilder append3 = sb.append(" unbound");
            }
            if (needsUpdate()) {
                StringBuilder append4 = sb.append(" update");
            }
            if (isRemoved()) {
                StringBuilder append5 = sb.append(" removed");
            }
            if (shouldIgnore()) {
                StringBuilder append6 = sb.append(" ignored");
            }
            if (isTmpDetached()) {
                StringBuilder append7 = sb.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                StringBuilder append8 = sb.append(" not recyclable(" + this.mIsRecyclableCount + ")");
            }
            if (isAdapterPositionUnknown()) {
                StringBuilder append9 = sb.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                StringBuilder append10 = sb.append(" no parent");
            }
            StringBuilder append11 = sb.append("}");
            return sb.toString();
        }

        public final void setIsRecyclable(boolean z) {
            boolean recyclable = z;
            this.mIsRecyclableCount = !recyclable ? this.mIsRecyclableCount + 1 : this.mIsRecyclableCount - 1;
            if (this.mIsRecyclableCount < 0) {
                this.mIsRecyclableCount = 0;
                int e = Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
            } else if (!recyclable && this.mIsRecyclableCount == 1) {
                this.mFlags |= 16;
            } else if (recyclable && this.mIsRecyclableCount == 0) {
                this.mFlags &= -17;
            }
        }

        public final boolean isRecyclable() {
            return (this.mFlags & 16) == 0 && !ViewCompat.hasTransientState(this.itemView);
        }

        private boolean shouldBeKeptAsChild() {
            return (this.mFlags & 16) != 0;
        }

        private boolean doesTransientStatePreventRecycling() {
            return (this.mFlags & 16) == 0 && ViewCompat.hasTransientState(this.itemView);
        }

        /* access modifiers changed from: 0000 */
        public boolean isUpdated() {
            return (this.mFlags & 2) != 0;
        }
    }

    static /* synthetic */ void access$000(RecyclerView recyclerView, View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        RecyclerView x0 = recyclerView;
        View x1 = view;
        int x2 = i;
        android.view.ViewGroup.LayoutParams x3 = layoutParams;
        RecyclerView recyclerView2 = x0;
        View view2 = x1;
        int i2 = x2;
        android.view.ViewGroup.LayoutParams layoutParams2 = x3;
        x0.attachViewToParent(x1, x2, x3);
    }

    static /* synthetic */ void access$100(RecyclerView recyclerView, int i) {
        RecyclerView x0 = recyclerView;
        int x1 = i;
        RecyclerView recyclerView2 = x0;
        int i2 = x1;
        x0.detachViewFromParent(x1);
    }

    static /* synthetic */ void access$1000(RecyclerView recyclerView, int i, int i2) {
        RecyclerView x0 = recyclerView;
        int x1 = i;
        int x2 = i2;
        RecyclerView recyclerView2 = x0;
        int i3 = x1;
        int i4 = x2;
        x0.setMeasuredDimension(x1, x2);
    }

    static /* synthetic */ boolean access$500(RecyclerView recyclerView) {
        RecyclerView x0 = recyclerView;
        RecyclerView recyclerView2 = x0;
        return x0.awakenScrollBars();
    }

    static {
        boolean z;
        int[] iArr = new int[1];
        iArr[0] = 16843830;
        NESTED_SCROLLING_ATTRS = iArr;
        int[] iArr2 = new int[1];
        iArr2[0] = 16842987;
        CLIP_TO_PADDING_ATTR = iArr2;
        if (VERSION.SDK_INT > 15) {
            z = false;
        } else {
            z = true;
        }
        IGNORE_DETACHED_FOCUSED_CHILD = z;
        Class[] clsArr = new Class[4];
        clsArr[0] = Context.class;
        clsArr[1] = AttributeSet.class;
        clsArr[2] = Integer.TYPE;
        clsArr[3] = Integer.TYPE;
        LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = clsArr;
    }

    public RecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyle = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyle;
        super(context2, attrs, defStyle);
        this.mObserver = new RecyclerViewDataObserver(this);
        Recycler recycler = new Recycler(this);
        this.mRecycler = recycler;
        this.mViewInfoStore = new ViewInfoStore();
        C03251 r0 = new Runnable(this) {
            final /* synthetic */ RecyclerView this$0;

            {
                RecyclerView this$02 = r5;
                RecyclerView recyclerView = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                if (!this.this$0.mFirstLayoutComplete || this.this$0.isLayoutRequested()) {
                    return;
                }
                if (!this.this$0.mIsAttached) {
                    this.this$0.requestLayout();
                } else if (!this.this$0.mLayoutFrozen) {
                    this.this$0.consumePendingUpdateOperations();
                } else {
                    this.this$0.mLayoutRequestEaten = true;
                }
            }
        };
        this.mUpdateChildViewsRunnable = r0;
        this.mTempRect = new Rect();
        this.mTempRect2 = new Rect();
        this.mTempRectF = new RectF();
        this.mItemDecorations = new ArrayList();
        this.mOnItemTouchListeners = new ArrayList();
        this.mEatRequestLayout = 0;
        this.mDataSetHasChangedAfterLayout = false;
        this.mLayoutOrScrollCounter = 0;
        this.mDispatchScrollCounter = 0;
        this.mItemAnimator = new DefaultItemAnimator();
        this.mScrollState = 0;
        this.mScrollPointerId = -1;
        this.mScrollFactor = Float.MIN_VALUE;
        this.mPreserveFocusAfterLayout = true;
        ViewFlinger viewFlinger = new ViewFlinger(this);
        this.mViewFlinger = viewFlinger;
        if (!ALLOW_THREAD_GAP_WORK) {
            layoutPrefetchRegistryImpl = null;
        } else {
            layoutPrefetchRegistryImpl = new LayoutPrefetchRegistryImpl();
        }
        this.mPrefetchRegistry = layoutPrefetchRegistryImpl;
        this.mState = new State();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        ItemAnimatorRestoreListener itemAnimatorRestoreListener = new ItemAnimatorRestoreListener(this);
        this.mItemAnimatorListener = itemAnimatorRestoreListener;
        this.mPostedAnimatorRunner = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mNestedOffsets = new int[2];
        this.mPendingAccessibilityImportanceChange = new ArrayList();
        C03262 r02 = new Runnable(this) {
            final /* synthetic */ RecyclerView this$0;

            {
                RecyclerView this$02 = r5;
                RecyclerView recyclerView = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                if (this.this$0.mItemAnimator != null) {
                    this.this$0.mItemAnimator.runPendingAnimations();
                }
                this.this$0.mPostedAnimatorRunner = false;
            }
        };
        this.mItemAnimatorRunner = r02;
        C03284 r03 = new ProcessCallback(this) {
            final /* synthetic */ RecyclerView this$0;

            {
                RecyclerView this$02 = r5;
                RecyclerView recyclerView = this$02;
                this.this$0 = this$02;
            }

            public void processDisappeared(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2) {
                ViewHolder viewHolder2 = viewHolder;
                ItemHolderInfo info = itemHolderInfo;
                ItemHolderInfo postInfo = itemHolderInfo2;
                ViewHolder viewHolder3 = viewHolder2;
                ItemHolderInfo itemHolderInfo3 = info;
                ItemHolderInfo itemHolderInfo4 = postInfo;
                this.this$0.mRecycler.unscrapView(viewHolder2);
                this.this$0.animateDisappearance(viewHolder2, info, postInfo);
            }

            public void processAppeared(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2) {
                ViewHolder viewHolder2 = viewHolder;
                ItemHolderInfo preInfo = itemHolderInfo;
                ItemHolderInfo info = itemHolderInfo2;
                ViewHolder viewHolder3 = viewHolder2;
                ItemHolderInfo itemHolderInfo3 = preInfo;
                ItemHolderInfo itemHolderInfo4 = info;
                this.this$0.animateAppearance(viewHolder2, preInfo, info);
            }

            public void processPersistent(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
                ViewHolder viewHolder2 = viewHolder;
                ItemHolderInfo preInfo = itemHolderInfo;
                ItemHolderInfo postInfo = itemHolderInfo2;
                ViewHolder viewHolder3 = viewHolder2;
                ItemHolderInfo itemHolderInfo3 = preInfo;
                ItemHolderInfo itemHolderInfo4 = postInfo;
                viewHolder2.setIsRecyclable(false);
                if (!this.this$0.mDataSetHasChangedAfterLayout) {
                    if (this.this$0.mItemAnimator.animatePersistence(viewHolder2, preInfo, postInfo)) {
                        this.this$0.postAnimationRunner();
                    }
                } else if (this.this$0.mItemAnimator.animateChange(viewHolder2, viewHolder2, preInfo, postInfo)) {
                    this.this$0.postAnimationRunner();
                }
            }

            public void unused(ViewHolder viewHolder) {
                ViewHolder viewHolder2 = viewHolder;
                ViewHolder viewHolder3 = viewHolder2;
                this.this$0.mLayout.removeAndRecycleView(viewHolder2.itemView, this.this$0.mRecycler);
            }
        };
        this.mViewInfoProcessCallback = r03;
        if (attrs == null) {
            this.mClipToPadding = true;
        } else {
            TypedArray a = context2.obtainStyledAttributes(attrs, CLIP_TO_PADDING_ATTR, defStyle, 0);
            this.mClipToPadding = a.getBoolean(0, true);
            a.recycle();
        }
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration vc = ViewConfiguration.get(context2);
        this.mTouchSlop = vc.getScaledTouchSlop();
        this.mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == 2);
        this.mItemAnimator.setListener(this.mItemAnimatorListener);
        initAdapterManager();
        initChildrenHelper();
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = new RecyclerViewAccessibilityDelegate(this);
        setAccessibilityDelegateCompat(recyclerViewAccessibilityDelegate);
        boolean nestedScrollingEnabled = true;
        if (attrs == null) {
            setDescendantFocusability(262144);
        } else {
            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attrs, C0271R.styleable.RecyclerView, defStyle, 0);
            TypedArray a2 = obtainStyledAttributes;
            String layoutManagerName = obtainStyledAttributes.getString(C0271R.styleable.RecyclerView_layoutManager);
            int i3 = a2.getInt(C0271R.styleable.RecyclerView_android_descendantFocusability, -1);
            int i4 = i3;
            if (i3 == -1) {
                setDescendantFocusability(262144);
            }
            a2.recycle();
            createLayoutManager(context2, layoutManagerName, attrs, defStyle, 0);
            if (VERSION.SDK_INT >= 21) {
                TypedArray obtainStyledAttributes2 = context2.obtainStyledAttributes(attrs, NESTED_SCROLLING_ATTRS, defStyle, 0);
                TypedArray a3 = obtainStyledAttributes2;
                nestedScrollingEnabled = obtainStyledAttributes2.getBoolean(0, true);
                a3.recycle();
            }
        }
        setNestedScrollingEnabled(nestedScrollingEnabled);
    }

    public RecyclerView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        RecyclerViewAccessibilityDelegate accessibilityDelegate = recyclerViewAccessibilityDelegate;
        RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate2 = accessibilityDelegate;
        this.mAccessibilityDelegate = accessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this, this.mAccessibilityDelegate);
    }

    private void createLayoutManager(Context context, String str, AttributeSet attributeSet, int i, int i2) {
        ClassLoader classLoader;
        Constructor constructor;
        Context context2 = context;
        String className = str;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        int defStyleRes = i2;
        Context context3 = context2;
        String str2 = className;
        AttributeSet attributeSet2 = attrs;
        int i3 = defStyleAttr;
        int i4 = defStyleRes;
        if (className != null) {
            String trim = className.trim();
            String className2 = trim;
            if (trim.length() != 0) {
                String className3 = getFullClassName(context2, className2);
                try {
                    if (!isInEditMode()) {
                        classLoader = context2.getClassLoader();
                    } else {
                        classLoader = getClass().getClassLoader();
                    }
                    Class asSubclass = classLoader.loadClass(className3).asSubclass(LayoutManager.class);
                    Object[] constructorArgs = null;
                    try {
                        constructor = asSubclass.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
                        Object[] objArr = new Object[4];
                        objArr[0] = context2;
                        objArr[1] = attrs;
                        objArr[2] = Integer.valueOf(defStyleAttr);
                        objArr[3] = Integer.valueOf(defStyleRes);
                        constructorArgs = objArr;
                    } catch (NoSuchMethodException e) {
                        NoSuchMethodException noSuchMethodException = e;
                        constructor = asSubclass.getConstructor(new Class[0]);
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((LayoutManager) constructor.newInstance(constructorArgs));
                } catch (NoSuchMethodException e2) {
                    NoSuchMethodException noSuchMethodException2 = e2;
                    Throwable initCause = e2.initCause(e);
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Error creating LayoutManager " + className3, e2);
                } catch (ClassNotFoundException e3) {
                    ClassNotFoundException classNotFoundException = e3;
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Unable to find LayoutManager " + className3, e3);
                } catch (InvocationTargetException e4) {
                    InvocationTargetException invocationTargetException = e4;
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Could not instantiate the LayoutManager: " + className3, e4);
                } catch (InstantiationException e5) {
                    InstantiationException instantiationException = e5;
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Could not instantiate the LayoutManager: " + className3, e5);
                } catch (IllegalAccessException e6) {
                    IllegalAccessException illegalAccessException = e6;
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Cannot access non-public constructor " + className3, e6);
                } catch (ClassCastException e7) {
                    ClassCastException classCastException = e7;
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Class is not a LayoutManager " + className3, e7);
                }
            }
        }
    }

    private String getFullClassName(Context context, String str) {
        Context context2 = context;
        String className = str;
        Context context3 = context2;
        String str2 = className;
        if (className.charAt(0) == '.') {
            return context2.getPackageName() + className;
        }
        if (!className.contains(".")) {
            return RecyclerView.class.getPackage().getName() + '.' + className;
        }
        return className;
    }

    private void initChildrenHelper() {
        this.mChildHelper = new ChildHelper(new Callback(this) {
            final /* synthetic */ RecyclerView this$0;

            {
                RecyclerView this$02 = r5;
                RecyclerView recyclerView = this$02;
                this.this$0 = this$02;
            }

            public int getChildCount() {
                return this.this$0.getChildCount();
            }

            public void addView(View view, int i) {
                View child = view;
                int index = i;
                View view2 = child;
                int i2 = index;
                this.this$0.addView(child, index);
                this.this$0.dispatchChildAttached(child);
            }

            public int indexOfChild(View view) {
                View view2 = view;
                View view3 = view2;
                return this.this$0.indexOfChild(view2);
            }

            public void removeViewAt(int i) {
                int index = i;
                int i2 = index;
                View childAt = this.this$0.getChildAt(index);
                View child = childAt;
                if (childAt != null) {
                    this.this$0.dispatchChildDetached(child);
                }
                this.this$0.removeViewAt(index);
            }

            public View getChildAt(int i) {
                int offset = i;
                int i2 = offset;
                return this.this$0.getChildAt(offset);
            }

            public void removeAllViews() {
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    this.this$0.dispatchChildDetached(getChildAt(i));
                }
                this.this$0.removeAllViews();
            }

            public ViewHolder getChildViewHolder(View view) {
                View view2 = view;
                View view3 = view2;
                return RecyclerView.getChildViewHolderInt(view2);
            }

            public void attachViewToParent(View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
                View child = view;
                int index = i;
                android.view.ViewGroup.LayoutParams layoutParams2 = layoutParams;
                View view2 = child;
                int i2 = index;
                android.view.ViewGroup.LayoutParams layoutParams3 = layoutParams2;
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(child);
                ViewHolder vh = childViewHolderInt;
                if (childViewHolderInt != null) {
                    if (!vh.isTmpDetached() && !vh.shouldIgnore()) {
                        throw new IllegalArgumentException("Called attach on a child which is not detached: " + vh);
                    }
                    vh.clearTmpDetachFlag();
                }
                RecyclerView.access$000(this.this$0, child, index, layoutParams2);
            }

            public void detachViewFromParent(int i) {
                int offset = i;
                int i2 = offset;
                View childAt = getChildAt(offset);
                View view = childAt;
                if (childAt != null) {
                    ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                    ViewHolder vh = childViewHolderInt;
                    if (childViewHolderInt != null) {
                        if (vh.isTmpDetached() && !vh.shouldIgnore()) {
                            throw new IllegalArgumentException("called detach on an already detached child " + vh);
                        }
                        vh.addFlags(256);
                    }
                }
                RecyclerView.access$100(this.this$0, offset);
            }

            public void onEnteredHiddenState(View view) {
                View child = view;
                View view2 = child;
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(child);
                ViewHolder vh = childViewHolderInt;
                if (childViewHolderInt != null) {
                    ViewHolder.access$200(vh, this.this$0);
                }
            }

            public void onLeftHiddenState(View view) {
                View child = view;
                View view2 = child;
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(child);
                ViewHolder vh = childViewHolderInt;
                if (childViewHolderInt != null) {
                    ViewHolder.access$300(vh, this.this$0);
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void initAdapterManager() {
        this.mAdapterHelper = new AdapterHelper(new Callback(this) {
            final /* synthetic */ RecyclerView this$0;

            {
                RecyclerView this$02 = r5;
                RecyclerView recyclerView = this$02;
                this.this$0 = this$02;
            }

            public ViewHolder findViewHolder(int i) {
                int position = i;
                int i2 = position;
                ViewHolder findViewHolderForPosition = this.this$0.findViewHolderForPosition(position, true);
                ViewHolder vh = findViewHolderForPosition;
                if (findViewHolderForPosition == null) {
                    return null;
                }
                if (!this.this$0.mChildHelper.isHidden(vh.itemView)) {
                    return vh;
                }
                return null;
            }

            public void offsetPositionsForRemovingInvisible(int i, int i2) {
                int start = i;
                int count = i2;
                int i3 = start;
                int i4 = count;
                this.this$0.offsetPositionRecordsForRemove(start, count, true);
                this.this$0.mItemsAddedOrRemoved = true;
                this.this$0.mState.mDeletedInvisibleItemCountSincePreviousLayout += count;
            }

            public void offsetPositionsForRemovingLaidOutOrNewView(int i, int i2) {
                int positionStart = i;
                int itemCount = i2;
                int i3 = positionStart;
                int i4 = itemCount;
                this.this$0.offsetPositionRecordsForRemove(positionStart, itemCount, false);
                this.this$0.mItemsAddedOrRemoved = true;
            }

            public void markViewHoldersUpdated(int i, int i2, Object obj) {
                int positionStart = i;
                int itemCount = i2;
                Object payload = obj;
                int i3 = positionStart;
                int i4 = itemCount;
                Object obj2 = payload;
                this.this$0.viewRangeUpdate(positionStart, itemCount, payload);
                this.this$0.mItemsChanged = true;
            }

            public void onDispatchFirstPass(UpdateOp updateOp) {
                UpdateOp op = updateOp;
                UpdateOp updateOp2 = op;
                dispatchUpdate(op);
            }

            /* access modifiers changed from: 0000 */
            public void dispatchUpdate(UpdateOp updateOp) {
                UpdateOp op = updateOp;
                UpdateOp updateOp2 = op;
                switch (op.cmd) {
                    case 1:
                        this.this$0.mLayout.onItemsAdded(this.this$0, op.positionStart, op.itemCount);
                        return;
                    case 2:
                        this.this$0.mLayout.onItemsRemoved(this.this$0, op.positionStart, op.itemCount);
                        return;
                    case 4:
                        this.this$0.mLayout.onItemsUpdated(this.this$0, op.positionStart, op.itemCount, op.payload);
                        return;
                    case 8:
                        this.this$0.mLayout.onItemsMoved(this.this$0, op.positionStart, op.itemCount, 1);
                        return;
                    default:
                        return;
                }
            }

            public void onDispatchSecondPass(UpdateOp updateOp) {
                UpdateOp op = updateOp;
                UpdateOp updateOp2 = op;
                dispatchUpdate(op);
            }

            public void offsetPositionsForAdd(int i, int i2) {
                int positionStart = i;
                int itemCount = i2;
                int i3 = positionStart;
                int i4 = itemCount;
                this.this$0.offsetPositionRecordsForInsert(positionStart, itemCount);
                this.this$0.mItemsAddedOrRemoved = true;
            }

            public void offsetPositionsForMove(int i, int i2) {
                int from = i;
                int to = i2;
                int i3 = from;
                int i4 = to;
                this.this$0.offsetPositionRecordsForMove(from, to);
                this.this$0.mItemsAddedOrRemoved = true;
            }
        });
    }

    public void setHasFixedSize(boolean z) {
        this.mHasFixedSize = z;
    }

    public boolean hasFixedSize() {
        return this.mHasFixedSize;
    }

    public void setClipToPadding(boolean z) {
        boolean clipToPadding = z;
        if (clipToPadding != this.mClipToPadding) {
            invalidateGlows();
        }
        this.mClipToPadding = clipToPadding;
        super.setClipToPadding(clipToPadding);
        if (this.mFirstLayoutComplete) {
            requestLayout();
        }
    }

    public boolean getClipToPadding() {
        return this.mClipToPadding;
    }

    public void setScrollingTouchSlop(int i) {
        int slopConstant = i;
        int i2 = slopConstant;
        ViewConfiguration vc = ViewConfiguration.get(getContext());
        switch (slopConstant) {
            case 0:
                break;
            case 1:
                this.mTouchSlop = vc.getScaledPagingTouchSlop();
                return;
            default:
                int w = Log.w(TAG, "setScrollingTouchSlop(): bad argument constant " + slopConstant + "; using default value");
                break;
        }
        this.mTouchSlop = vc.getScaledTouchSlop();
    }

    public void swapAdapter(Adapter adapter, boolean z) {
        Adapter adapter2 = adapter;
        Adapter adapter3 = adapter2;
        boolean removeAndRecycleExistingViews = z;
        setLayoutFrozen(false);
        setAdapterInternal(adapter2, true, removeAndRecycleExistingViews);
        setDataSetChangedAfterLayout();
        requestLayout();
    }

    public void setAdapter(Adapter adapter) {
        Adapter adapter2 = adapter;
        Adapter adapter3 = adapter2;
        setLayoutFrozen(false);
        setAdapterInternal(adapter2, false, true);
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void removeAndRecycleViews() {
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
        }
        if (this.mLayout != null) {
            this.mLayout.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        }
        this.mRecycler.clear();
    }

    private void setAdapterInternal(Adapter adapter, boolean z, boolean z2) {
        Adapter adapter2 = adapter;
        Adapter adapter3 = adapter2;
        boolean compatibleWithPrevious = z;
        boolean removeAndRecycleViews = z2;
        if (this.mAdapter != null) {
            this.mAdapter.unregisterAdapterDataObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        if (!compatibleWithPrevious || removeAndRecycleViews) {
            removeAndRecycleViews();
        }
        this.mAdapterHelper.reset();
        Adapter oldAdapter = this.mAdapter;
        this.mAdapter = adapter2;
        if (adapter2 != null) {
            adapter2.registerAdapterDataObserver(this.mObserver);
            adapter2.onAttachedToRecyclerView(this);
        }
        if (this.mLayout != null) {
            this.mLayout.onAdapterChanged(oldAdapter, this.mAdapter);
        }
        this.mRecycler.onAdapterChanged(oldAdapter, this.mAdapter, compatibleWithPrevious);
        this.mState.mStructureChanged = true;
        markKnownViewsInvalid();
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        RecyclerListener listener = recyclerListener;
        RecyclerListener recyclerListener2 = listener;
        this.mRecyclerListener = listener;
    }

    public int getBaseline() {
        if (this.mLayout == null) {
            return super.getBaseline();
        }
        return this.mLayout.getBaseline();
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        OnChildAttachStateChangeListener listener = onChildAttachStateChangeListener;
        OnChildAttachStateChangeListener onChildAttachStateChangeListener2 = listener;
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new ArrayList();
        }
        boolean add = this.mOnChildAttachStateListeners.add(listener);
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        OnChildAttachStateChangeListener listener = onChildAttachStateChangeListener;
        OnChildAttachStateChangeListener onChildAttachStateChangeListener2 = listener;
        if (this.mOnChildAttachStateListeners != null) {
            boolean remove = this.mOnChildAttachStateListeners.remove(listener);
        }
    }

    public void clearOnChildAttachStateChangeListeners() {
        if (this.mOnChildAttachStateListeners != null) {
            this.mOnChildAttachStateListeners.clear();
        }
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        LayoutManager layout = layoutManager;
        LayoutManager layoutManager2 = layout;
        if (layout != this.mLayout) {
            stopScroll();
            if (this.mLayout == null) {
                this.mRecycler.clear();
            } else {
                if (this.mItemAnimator != null) {
                    this.mItemAnimator.endAnimations();
                }
                this.mLayout.removeAndRecycleAllViews(this.mRecycler);
                this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
                this.mRecycler.clear();
                if (this.mIsAttached) {
                    this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
                }
                this.mLayout.setRecyclerView(null);
                this.mLayout = null;
            }
            this.mChildHelper.removeAllViewsUnfiltered();
            this.mLayout = layout;
            if (layout != null) {
                if (layout.mRecyclerView == null) {
                    this.mLayout.setRecyclerView(this);
                    if (this.mIsAttached) {
                        this.mLayout.dispatchAttachedToWindow(this);
                    }
                } else {
                    throw new IllegalArgumentException("LayoutManager " + layout + " is already attached to a RecyclerView: " + layout.mRecyclerView);
                }
            }
            this.mRecycler.updateViewCacheSize();
            requestLayout();
        }
    }

    public void setOnFlingListener(@Nullable OnFlingListener onFlingListener) {
        OnFlingListener onFlingListener2 = onFlingListener;
        OnFlingListener onFlingListener3 = onFlingListener2;
        this.mOnFlingListener = onFlingListener2;
    }

    @Nullable
    public OnFlingListener getOnFlingListener() {
        return this.mOnFlingListener;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        if (this.mPendingSavedState != null) {
            state.copyFrom(this.mPendingSavedState);
        } else if (this.mLayout == null) {
            state.mLayoutState = null;
        } else {
            state.mLayoutState = this.mLayout.onSaveInstanceState();
        }
        return state;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            this.mPendingSavedState = (SavedState) state;
            super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
            if (!(this.mLayout == null || this.mPendingSavedState.mLayoutState == null)) {
                this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /* access modifiers changed from: protected */
    public void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> container = sparseArray;
        SparseArray<Parcelable> sparseArray2 = container;
        dispatchFreezeSelfOnly(container);
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> container = sparseArray;
        SparseArray<Parcelable> sparseArray2 = container;
        dispatchThawSelfOnly(container);
    }

    private void addAnimatingView(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        View view = viewHolder2.itemView;
        View view2 = view;
        boolean alreadyParented = view.getParent() == this;
        this.mRecycler.unscrapView(getChildViewHolder(view2));
        if (viewHolder2.isTmpDetached()) {
            this.mChildHelper.attachViewToParent(view2, -1, view2.getLayoutParams(), true);
        } else if (alreadyParented) {
            this.mChildHelper.hide(view2);
        } else {
            this.mChildHelper.addView(view2, true);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean removeAnimatingView(View view) {
        View view2 = view;
        View view3 = view2;
        eatRequestLayout();
        boolean removeViewIfHidden = this.mChildHelper.removeViewIfHidden(view2);
        boolean removed = removeViewIfHidden;
        if (removeViewIfHidden) {
            ViewHolder viewHolder = getChildViewHolderInt(view2);
            this.mRecycler.unscrapView(viewHolder);
            this.mRecycler.recycleViewHolderInternal(viewHolder);
        }
        resumeRequestLayout(!removed);
        return removed;
    }

    public LayoutManager getLayoutManager() {
        return this.mLayout;
    }

    public RecycledViewPool getRecycledViewPool() {
        return this.mRecycler.getRecycledViewPool();
    }

    public void setRecycledViewPool(RecycledViewPool recycledViewPool) {
        RecycledViewPool pool = recycledViewPool;
        RecycledViewPool recycledViewPool2 = pool;
        this.mRecycler.setRecycledViewPool(pool);
    }

    public void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
        ViewCacheExtension extension = viewCacheExtension;
        ViewCacheExtension viewCacheExtension2 = extension;
        this.mRecycler.setViewCacheExtension(extension);
    }

    public void setItemViewCacheSize(int i) {
        int size = i;
        int i2 = size;
        this.mRecycler.setViewCacheSize(size);
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    /* access modifiers changed from: 0000 */
    public void setScrollState(int i) {
        int state = i;
        int i2 = state;
        if (state != this.mScrollState) {
            this.mScrollState = state;
            if (state != 2) {
                stopScrollersInternal();
            }
            dispatchOnScrollStateChanged(state);
        }
    }

    public void addItemDecoration(ItemDecoration itemDecoration, int i) {
        ItemDecoration decor = itemDecoration;
        int index = i;
        ItemDecoration itemDecoration2 = decor;
        int i2 = index;
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(false);
        }
        if (index >= 0) {
            this.mItemDecorations.add(index, decor);
        } else {
            boolean add = this.mItemDecorations.add(decor);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void addItemDecoration(ItemDecoration itemDecoration) {
        ItemDecoration decor = itemDecoration;
        ItemDecoration itemDecoration2 = decor;
        addItemDecoration(decor, -1);
    }

    public void removeItemDecoration(ItemDecoration itemDecoration) {
        ItemDecoration decor = itemDecoration;
        ItemDecoration itemDecoration2 = decor;
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        boolean remove = this.mItemDecorations.remove(decor);
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        ChildDrawingOrderCallback childDrawingOrderCallback2 = childDrawingOrderCallback;
        ChildDrawingOrderCallback childDrawingOrderCallback3 = childDrawingOrderCallback2;
        if (childDrawingOrderCallback2 != this.mChildDrawingOrderCallback) {
            this.mChildDrawingOrderCallback = childDrawingOrderCallback2;
            setChildrenDrawingOrderEnabled(this.mChildDrawingOrderCallback != null);
        }
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        OnScrollListener listener = onScrollListener;
        OnScrollListener onScrollListener2 = listener;
        this.mScrollListener = listener;
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        OnScrollListener listener = onScrollListener;
        OnScrollListener onScrollListener2 = listener;
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList();
        }
        boolean add = this.mScrollListeners.add(listener);
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        OnScrollListener listener = onScrollListener;
        OnScrollListener onScrollListener2 = listener;
        if (this.mScrollListeners != null) {
            boolean remove = this.mScrollListeners.remove(listener);
        }
    }

    public void clearOnScrollListeners() {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.clear();
        }
    }

    public void scrollToPosition(int i) {
        int position = i;
        int i2 = position;
        if (!this.mLayoutFrozen) {
            stopScroll();
            if (this.mLayout != null) {
                this.mLayout.scrollToPosition(position);
                boolean awakenScrollBars = awakenScrollBars();
                return;
            }
            int e = Log.e(TAG, "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
        }
    }

    /* access modifiers changed from: 0000 */
    public void jumpToPositionForSmoothScroller(int i) {
        int position = i;
        int i2 = position;
        if (this.mLayout != null) {
            this.mLayout.scrollToPosition(position);
            boolean awakenScrollBars = awakenScrollBars();
        }
    }

    public void smoothScrollToPosition(int i) {
        int position = i;
        int i2 = position;
        if (this.mLayoutFrozen) {
            return;
        }
        if (this.mLayout != null) {
            this.mLayout.smoothScrollToPosition(this, this.mState, position);
        } else {
            int e = Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        }
    }

    public void scrollTo(int i, int i2) {
        int i3 = i;
        int i4 = i2;
        int w = Log.w(TAG, "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollBy(int i, int i2) {
        int x = i;
        int y = i2;
        int i3 = x;
        int i4 = y;
        if (this.mLayout == null) {
            int e = Log.e(TAG, "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.mLayoutFrozen) {
            boolean canScrollHorizontal = this.mLayout.canScrollHorizontally();
            boolean canScrollVertical = this.mLayout.canScrollVertically();
            if (canScrollHorizontal || canScrollVertical) {
                boolean scrollByInternal = scrollByInternal(!canScrollHorizontal ? 0 : x, !canScrollVertical ? 0 : y, null);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void consumePendingUpdateOperations() {
        if (!this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout) {
            TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
            dispatchLayout();
            TraceCompat.endSection();
        } else if (this.mAdapterHelper.hasPendingUpdates()) {
            if (this.mAdapterHelper.hasAnyUpdateTypes(4) && !this.mAdapterHelper.hasAnyUpdateTypes(11)) {
                TraceCompat.beginSection(TRACE_HANDLE_ADAPTER_UPDATES_TAG);
                eatRequestLayout();
                onEnterLayoutOrScroll();
                this.mAdapterHelper.preProcess();
                if (!this.mLayoutRequestEaten) {
                    if (!hasUpdatedView()) {
                        this.mAdapterHelper.consumePostponedUpdates();
                    } else {
                        dispatchLayout();
                    }
                }
                resumeRequestLayout(true);
                onExitLayoutOrScroll();
                TraceCompat.endSection();
            } else if (this.mAdapterHelper.hasPendingUpdates()) {
                TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
                dispatchLayout();
                TraceCompat.endSection();
            }
        }
    }

    private boolean hasUpdatedView() {
        int childCount = this.mChildHelper.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt != null && !holder.shouldIgnore() && holder.isUpdated()) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean scrollByInternal(int i, int i2, MotionEvent motionEvent) {
        boolean z;
        int x = i;
        int y = i2;
        MotionEvent ev = motionEvent;
        int i3 = x;
        int i4 = y;
        MotionEvent motionEvent2 = ev;
        int unconsumedX = 0;
        int unconsumedY = 0;
        int consumedX = 0;
        int consumedY = 0;
        consumePendingUpdateOperations();
        if (this.mAdapter != null) {
            eatRequestLayout();
            onEnterLayoutOrScroll();
            TraceCompat.beginSection(TRACE_SCROLL_TAG);
            if (x != 0) {
                consumedX = this.mLayout.scrollHorizontallyBy(x, this.mRecycler, this.mState);
                unconsumedX = x - consumedX;
            }
            if (y != 0) {
                consumedY = this.mLayout.scrollVerticallyBy(y, this.mRecycler, this.mState);
                unconsumedY = y - consumedY;
            }
            TraceCompat.endSection();
            repositionShadowingViews();
            onExitLayoutOrScroll();
            resumeRequestLayout(false);
        }
        if (!this.mItemDecorations.isEmpty()) {
            invalidate();
        }
        if (dispatchNestedScroll(consumedX, consumedY, unconsumedX, unconsumedY, this.mScrollOffset)) {
            this.mLastTouchX -= this.mScrollOffset[0];
            this.mLastTouchY -= this.mScrollOffset[1];
            if (ev != null) {
                ev.offsetLocation((float) this.mScrollOffset[0], (float) this.mScrollOffset[1]);
            }
            int[] iArr = this.mNestedOffsets;
            iArr[0] = iArr[0] + this.mScrollOffset[0];
            int[] iArr2 = this.mNestedOffsets;
            iArr2[1] = iArr2[1] + this.mScrollOffset[1];
        } else if (getOverScrollMode() != 2) {
            if (ev != null) {
                pullGlows(ev.getX(), (float) unconsumedX, ev.getY(), (float) unconsumedY);
            }
            considerReleasingGlowsOnScroll(x, y);
        }
        if (!(consumedX == 0 && consumedY == 0)) {
            dispatchOnScrolled(consumedX, consumedY);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        if (consumedX == 0 && consumedY == 0) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public int computeHorizontalScrollOffset() {
        if (this.mLayout == null) {
            return 0;
        }
        return !this.mLayout.canScrollHorizontally() ? 0 : this.mLayout.computeHorizontalScrollOffset(this.mState);
    }

    public int computeHorizontalScrollExtent() {
        if (this.mLayout == null) {
            return 0;
        }
        return !this.mLayout.canScrollHorizontally() ? 0 : this.mLayout.computeHorizontalScrollExtent(this.mState);
    }

    public int computeHorizontalScrollRange() {
        if (this.mLayout == null) {
            return 0;
        }
        return !this.mLayout.canScrollHorizontally() ? 0 : this.mLayout.computeHorizontalScrollRange(this.mState);
    }

    public int computeVerticalScrollOffset() {
        if (this.mLayout == null) {
            return 0;
        }
        return !this.mLayout.canScrollVertically() ? 0 : this.mLayout.computeVerticalScrollOffset(this.mState);
    }

    public int computeVerticalScrollExtent() {
        if (this.mLayout == null) {
            return 0;
        }
        return !this.mLayout.canScrollVertically() ? 0 : this.mLayout.computeVerticalScrollExtent(this.mState);
    }

    public int computeVerticalScrollRange() {
        if (this.mLayout == null) {
            return 0;
        }
        return !this.mLayout.canScrollVertically() ? 0 : this.mLayout.computeVerticalScrollRange(this.mState);
    }

    /* access modifiers changed from: 0000 */
    public void eatRequestLayout() {
        this.mEatRequestLayout++;
        if (this.mEatRequestLayout == 1 && !this.mLayoutFrozen) {
            this.mLayoutRequestEaten = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void resumeRequestLayout(boolean z) {
        boolean performLayoutChildren = z;
        if (this.mEatRequestLayout < 1) {
            this.mEatRequestLayout = 1;
        }
        if (!performLayoutChildren) {
            this.mLayoutRequestEaten = false;
        }
        if (this.mEatRequestLayout == 1) {
            if (performLayoutChildren && this.mLayoutRequestEaten && !this.mLayoutFrozen && this.mLayout != null && this.mAdapter != null) {
                dispatchLayout();
            }
            if (!this.mLayoutFrozen) {
                this.mLayoutRequestEaten = false;
            }
        }
        this.mEatRequestLayout--;
    }

    public void setLayoutFrozen(boolean z) {
        boolean frozen = z;
        if (frozen != this.mLayoutFrozen) {
            assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
            if (frozen) {
                long uptimeMillis = SystemClock.uptimeMillis();
                boolean onTouchEvent = onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0));
                this.mLayoutFrozen = true;
                this.mIgnoreMotionEventTillDown = true;
                stopScroll();
                return;
            }
            this.mLayoutFrozen = false;
            if (!(!this.mLayoutRequestEaten || this.mLayout == null || this.mAdapter == null)) {
                requestLayout();
            }
            this.mLayoutRequestEaten = false;
        }
    }

    public boolean isLayoutFrozen() {
        return this.mLayoutFrozen;
    }

    public void smoothScrollBy(int i, int i2) {
        int dx = i;
        int dy = i2;
        int i3 = dx;
        int i4 = dy;
        smoothScrollBy(dx, dy, null);
    }

    public void smoothScrollBy(int i, int i2, Interpolator interpolator) {
        Interpolator interpolator2 = interpolator;
        int dx = i;
        int dy = i2;
        Interpolator interpolator3 = interpolator2;
        if (this.mLayout == null) {
            int e = Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.mLayoutFrozen) {
            if (!this.mLayout.canScrollHorizontally()) {
                dx = 0;
            }
            if (!this.mLayout.canScrollVertically()) {
                dy = 0;
            }
            if (!(dx == 0 && dy == 0)) {
                this.mViewFlinger.smoothScrollBy(dx, dy, interpolator2);
            }
        }
    }

    public boolean fling(int i, int i2) {
        int velocityX = i;
        int velocityY = i2;
        int velocityX2 = velocityX;
        int velocityY2 = velocityY;
        if (this.mLayout == null) {
            int e = Log.e(TAG, "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        } else if (this.mLayoutFrozen) {
            return false;
        } else {
            boolean canScrollHorizontal = this.mLayout.canScrollHorizontally();
            boolean canScrollVertical = this.mLayout.canScrollVertically();
            if (!canScrollHorizontal || Math.abs(velocityX) < this.mMinFlingVelocity) {
                velocityX2 = 0;
            }
            if (!canScrollVertical || Math.abs(velocityY) < this.mMinFlingVelocity) {
                velocityY2 = 0;
            }
            if (velocityX2 == 0 && velocityY2 == 0) {
                return false;
            }
            if (!dispatchNestedPreFling((float) velocityX2, (float) velocityY2)) {
                boolean canScroll = canScrollHorizontal || canScrollVertical;
                boolean dispatchNestedFling = dispatchNestedFling((float) velocityX2, (float) velocityY2, canScroll);
                if (this.mOnFlingListener != null && this.mOnFlingListener.onFling(velocityX2, velocityY2)) {
                    return true;
                }
                if (canScroll) {
                    int velocityX3 = Math.max(-this.mMaxFlingVelocity, Math.min(velocityX2, this.mMaxFlingVelocity));
                    int max = Math.max(-this.mMaxFlingVelocity, Math.min(velocityY2, this.mMaxFlingVelocity));
                    int velocityY3 = max;
                    this.mViewFlinger.fling(velocityX3, max);
                    return true;
                }
            }
            return false;
        }
    }

    public void stopScroll() {
        setScrollState(0);
        stopScrollersInternal();
    }

    private void stopScrollersInternal() {
        this.mViewFlinger.stop();
        if (this.mLayout != null) {
            this.mLayout.stopSmoothScroller();
        }
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    private void pullGlows(float f, float f2, float f3, float f4) {
        float x = f;
        float overscrollX = f2;
        float y = f3;
        float overscrollY = f4;
        float f5 = x;
        float f6 = overscrollX;
        float f7 = y;
        float f8 = overscrollY;
        boolean invalidate = false;
        if (overscrollX < 0.0f) {
            ensureLeftGlow();
            if (this.mLeftGlow.onPull((-overscrollX) / ((float) getWidth()), 1.0f - (y / ((float) getHeight())))) {
                invalidate = true;
            }
        } else if (overscrollX > 0.0f) {
            ensureRightGlow();
            if (this.mRightGlow.onPull(overscrollX / ((float) getWidth()), y / ((float) getHeight()))) {
                invalidate = true;
            }
        }
        if (overscrollY < 0.0f) {
            ensureTopGlow();
            if (this.mTopGlow.onPull((-overscrollY) / ((float) getHeight()), x / ((float) getWidth()))) {
                invalidate = true;
            }
        } else if (overscrollY > 0.0f) {
            ensureBottomGlow();
            if (this.mBottomGlow.onPull(overscrollY / ((float) getHeight()), 1.0f - (x / ((float) getWidth())))) {
                invalidate = true;
            }
        }
        if (invalidate || overscrollX != 0.0f || overscrollY != 0.0f) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void releaseGlows() {
        boolean needsInvalidate = false;
        if (this.mLeftGlow != null) {
            needsInvalidate = this.mLeftGlow.onRelease();
        }
        if (this.mTopGlow != null) {
            needsInvalidate |= this.mTopGlow.onRelease();
        }
        if (this.mRightGlow != null) {
            needsInvalidate |= this.mRightGlow.onRelease();
        }
        if (this.mBottomGlow != null) {
            needsInvalidate |= this.mBottomGlow.onRelease();
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void considerReleasingGlowsOnScroll(int i, int i2) {
        int dx = i;
        int dy = i2;
        int i3 = dx;
        int i4 = dy;
        boolean needsInvalidate = false;
        if (this.mLeftGlow != null && !this.mLeftGlow.isFinished() && dx > 0) {
            needsInvalidate = this.mLeftGlow.onRelease();
        }
        if (this.mRightGlow != null && !this.mRightGlow.isFinished() && dx < 0) {
            needsInvalidate |= this.mRightGlow.onRelease();
        }
        if (this.mTopGlow != null && !this.mTopGlow.isFinished() && dy > 0) {
            needsInvalidate |= this.mTopGlow.onRelease();
        }
        if (this.mBottomGlow != null && !this.mBottomGlow.isFinished() && dy < 0) {
            needsInvalidate |= this.mBottomGlow.onRelease();
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void absorbGlows(int i, int i2) {
        int velocityX = i;
        int velocityY = i2;
        int i3 = velocityX;
        int i4 = velocityY;
        if (velocityX < 0) {
            ensureLeftGlow();
            boolean onAbsorb = this.mLeftGlow.onAbsorb(-velocityX);
        } else if (velocityX > 0) {
            ensureRightGlow();
            boolean onAbsorb2 = this.mRightGlow.onAbsorb(velocityX);
        }
        if (velocityY < 0) {
            ensureTopGlow();
            boolean onAbsorb3 = this.mTopGlow.onAbsorb(-velocityY);
        } else if (velocityY > 0) {
            ensureBottomGlow();
            boolean onAbsorb4 = this.mBottomGlow.onAbsorb(velocityY);
        }
        if (velocityX != 0 || velocityY != 0) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void ensureLeftGlow() {
        if (this.mLeftGlow == null) {
            this.mLeftGlow = new EdgeEffectCompat(getContext());
            if (!this.mClipToPadding) {
                this.mLeftGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
            } else {
                this.mLeftGlow.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void ensureRightGlow() {
        if (this.mRightGlow == null) {
            this.mRightGlow = new EdgeEffectCompat(getContext());
            if (!this.mClipToPadding) {
                this.mRightGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
            } else {
                this.mRightGlow.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void ensureTopGlow() {
        if (this.mTopGlow == null) {
            this.mTopGlow = new EdgeEffectCompat(getContext());
            if (!this.mClipToPadding) {
                this.mTopGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
            } else {
                this.mTopGlow.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void ensureBottomGlow() {
        if (this.mBottomGlow == null) {
            this.mBottomGlow = new EdgeEffectCompat(getContext());
            if (!this.mClipToPadding) {
                this.mBottomGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
            } else {
                this.mBottomGlow.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void invalidateGlows() {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    public View focusSearch(View view, int i) {
        View result;
        View view2;
        View focused = view;
        int direction = i;
        View view3 = focused;
        int direction2 = direction;
        View onInterceptFocusSearch = this.mLayout.onInterceptFocusSearch(focused, direction);
        View result2 = onInterceptFocusSearch;
        if (onInterceptFocusSearch != null) {
            return result2;
        }
        boolean canRunFocusFailure = this.mAdapter != null && this.mLayout != null && !isComputingLayout() && !this.mLayoutFrozen;
        FocusFinder instance = FocusFinder.getInstance();
        FocusFinder ff = instance;
        if (canRunFocusFailure && (direction == 2 || direction == 1)) {
            boolean needsFocusFailureLayout = false;
            if (this.mLayout.canScrollVertically()) {
                int absDir = direction != 2 ? 33 : TransportMediator.KEYCODE_MEDIA_RECORD;
                View findNextFocus = ff.findNextFocus(this, focused, absDir);
                View view4 = findNextFocus;
                needsFocusFailureLayout = findNextFocus == null;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    direction2 = absDir;
                }
            }
            if (!needsFocusFailureLayout && this.mLayout.canScrollHorizontally()) {
                int absDir2 = !((direction2 == 2) ^ (this.mLayout.getLayoutDirection() == 1)) ? 17 : 66;
                View findNextFocus2 = ff.findNextFocus(this, focused, absDir2);
                View view5 = findNextFocus2;
                needsFocusFailureLayout = findNextFocus2 == null;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    direction2 = absDir2;
                }
            }
            if (needsFocusFailureLayout) {
                consumePendingUpdateOperations();
                View findContainingItemView = findContainingItemView(focused);
                View view6 = findContainingItemView;
                if (findContainingItemView == null) {
                    return null;
                }
                eatRequestLayout();
                View onFocusSearchFailed = this.mLayout.onFocusSearchFailed(focused, direction2, this.mRecycler, this.mState);
                resumeRequestLayout(false);
            }
            result = ff.findNextFocus(this, focused, direction2);
        } else {
            View findNextFocus3 = instance.findNextFocus(this, focused, direction);
            result = findNextFocus3;
            if (findNextFocus3 == null && canRunFocusFailure) {
                consumePendingUpdateOperations();
                View findContainingItemView2 = findContainingItemView(focused);
                View view7 = findContainingItemView2;
                if (findContainingItemView2 == null) {
                    return null;
                }
                eatRequestLayout();
                result = this.mLayout.onFocusSearchFailed(focused, direction, this.mRecycler, this.mState);
                resumeRequestLayout(false);
            }
        }
        if (!isPreferredNextFocus(focused, result, direction2)) {
            view2 = super.focusSearch(focused, direction2);
        } else {
            view2 = result;
        }
        return view2;
    }

    private boolean isPreferredNextFocus(View view, View view2, int i) {
        View focused = view;
        View next = view2;
        int direction = i;
        View view3 = focused;
        View view4 = next;
        int i2 = direction;
        if (next == null || next == this) {
            return false;
        }
        if (focused == null) {
            return true;
        }
        if (direction != 2 && direction != 1) {
            return isPreferredNextFocusAbsolute(focused, next, direction);
        }
        if (isPreferredNextFocusAbsolute(focused, next, !((direction == 2) ^ (this.mLayout.getLayoutDirection() == 1)) ? 17 : 66)) {
            return true;
        }
        if (direction != 2) {
            return isPreferredNextFocusAbsolute(focused, next, 33);
        }
        return isPreferredNextFocusAbsolute(focused, next, TransportMediator.KEYCODE_MEDIA_RECORD);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x010d, code lost:
        r20 = r2.mTempRect.left;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x007b, code lost:
        r20 = r2.mTempRect.left;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isPreferredNextFocusAbsolute(android.view.View r30, android.view.View r31, int r32) {
        /*
            r29 = this;
            r2 = r29
            r3 = r30
            r4 = r31
            r5 = r32
            r6 = r2
            r7 = r3
            r8 = r4
            r9 = r5
            android.graphics.Rect r10 = r2.mTempRect
            r11 = 0
            r12 = 0
            int r13 = r3.getWidth()
            int r14 = r3.getHeight()
            r10.set(r11, r12, r13, r14)
            android.graphics.Rect r10 = r2.mTempRect2
            r11 = 0
            r12 = 0
            int r13 = r4.getWidth()
            int r14 = r4.getHeight()
            r10.set(r11, r12, r13, r14)
            android.graphics.Rect r15 = r2.mTempRect
            r16 = r3
            r17 = r15
            r0 = r16
            r1 = r17
            r2.offsetDescendantRectToMyCoords(r0, r1)
            android.graphics.Rect r15 = r2.mTempRect2
            r18 = r4
            r19 = r15
            r0 = r18
            r1 = r19
            r2.offsetDescendantRectToMyCoords(r0, r1)
            switch(r5) {
                case 17: goto L_0x0069;
                case 33: goto L_0x0092;
                case 66: goto L_0x00bb;
                case 130: goto L_0x00e4;
                default: goto L_0x0047;
            }
        L_0x0047:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r26 = "direction must be absolute. received:"
            r27 = r26
            r0 = r27
            java.lang.StringBuilder r15 = r15.append(r0)
            java.lang.StringBuilder r15 = r15.append(r5)
            java.lang.String r15 = r15.toString()
            r28 = r15
            r0 = r28
            r10.<init>(r0)
            throw r10
        L_0x0069:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.right
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.right
            r0 = r20
            if (r0 <= r11) goto L_0x010d
        L_0x007b:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.left
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.left
            r0 = r20
            if (r0 > r11) goto L_0x0121
        L_0x008d:
            r20 = 0
        L_0x008f:
            r22 = r20
            return r22
        L_0x0092:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.bottom
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.bottom
            r0 = r20
            if (r0 <= r11) goto L_0x013b
        L_0x00a4:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.top
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.top
            r0 = r20
            if (r0 > r11) goto L_0x014f
        L_0x00b6:
            r20 = 0
        L_0x00b8:
            r24 = r20
            return r24
        L_0x00bb:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.left
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.left
            r0 = r20
            if (r0 >= r11) goto L_0x0125
        L_0x00cd:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.right
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.right
            r0 = r20
            if (r0 < r11) goto L_0x0138
        L_0x00df:
            r20 = 0
        L_0x00e1:
            r23 = r20
            return r23
        L_0x00e4:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.top
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.top
            r0 = r20
            if (r0 >= r11) goto L_0x0153
        L_0x00f6:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.bottom
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.bottom
            r0 = r20
            if (r0 < r11) goto L_0x0166
        L_0x0108:
            r20 = 0
        L_0x010a:
            r25 = r20
            return r25
        L_0x010d:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.left
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.right
            r0 = r20
            if (r0 >= r11) goto L_0x007b
            goto L_0x008d
        L_0x0121:
            r20 = 1
            goto L_0x008f
        L_0x0125:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.right
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.left
            r0 = r20
            if (r0 <= r11) goto L_0x00cd
            goto L_0x00df
        L_0x0138:
            r20 = 1
            goto L_0x00e1
        L_0x013b:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.top
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.bottom
            r0 = r20
            if (r0 >= r11) goto L_0x00a4
            goto L_0x00b6
        L_0x014f:
            r20 = 1
            goto L_0x00b8
        L_0x0153:
            android.graphics.Rect r10 = r2.mTempRect
            int r0 = r10.bottom
            r20 = r0
            android.graphics.Rect r0 = r2.mTempRect2
            r21 = r0
            r0 = r21
            int r11 = r0.top
            r0 = r20
            if (r0 <= r11) goto L_0x00f6
            goto L_0x0108
        L_0x0166:
            r20 = 1
            goto L_0x010a
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.widget.RecyclerView.isPreferredNextFocusAbsolute(android.view.View, android.view.View, int):boolean");
    }

    public void requestChildFocus(View view, View view2) {
        boolean z;
        View child = view;
        View focused = view2;
        View view3 = child;
        View view4 = focused;
        if (!this.mLayout.onRequestChildFocus(this, this.mState, child, focused) && focused != null) {
            this.mTempRect.set(0, 0, focused.getWidth(), focused.getHeight());
            android.view.ViewGroup.LayoutParams layoutParams = focused.getLayoutParams();
            android.view.ViewGroup.LayoutParams focusedLayoutParams = layoutParams;
            if (layoutParams instanceof LayoutParams) {
                LayoutParams layoutParams2 = (LayoutParams) focusedLayoutParams;
                LayoutParams lp = layoutParams2;
                if (!layoutParams2.mInsetsDirty) {
                    Rect insets = lp.mDecorInsets;
                    this.mTempRect.left -= insets.left;
                    this.mTempRect.right += insets.right;
                    this.mTempRect.top -= insets.top;
                    this.mTempRect.bottom += insets.bottom;
                }
            }
            offsetDescendantRectToMyCoords(focused, this.mTempRect);
            offsetRectIntoDescendantCoords(child, this.mTempRect);
            Rect rect = this.mTempRect;
            if (this.mFirstLayoutComplete) {
                z = false;
            } else {
                z = true;
            }
            boolean requestChildRectangleOnScreen = requestChildRectangleOnScreen(child, rect, z);
        }
        super.requestChildFocus(child, focused);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        View child = view;
        Rect rect2 = rect;
        View view2 = child;
        Rect rect3 = rect2;
        return this.mLayout.requestChildRectangleOnScreen(this, child, rect2, z);
    }

    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        ArrayList<View> views = arrayList;
        int direction = i;
        int focusableMode = i2;
        ArrayList<View> arrayList2 = views;
        int i3 = direction;
        int i4 = focusableMode;
        if (this.mLayout == null || !this.mLayout.onAddFocusables(this, views, direction, focusableMode)) {
            super.addFocusables(views, direction, focusableMode);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        int direction = i;
        Rect previouslyFocusedRect = rect;
        int i2 = direction;
        Rect rect2 = previouslyFocusedRect;
        if (!isComputingLayout()) {
            return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        this.mIsAttached = true;
        this.mFirstLayoutComplete = this.mFirstLayoutComplete && !isLayoutRequested();
        if (this.mLayout != null) {
            this.mLayout.dispatchAttachedToWindow(this);
        }
        this.mPostedAnimatorRunner = false;
        if (ALLOW_THREAD_GAP_WORK) {
            this.mGapWorker = (GapWorker) GapWorker.sGapWorker.get();
            if (this.mGapWorker == null) {
                this.mGapWorker = new GapWorker();
                Display display = ViewCompat.getDisplay(this);
                float refreshRate = 60.0f;
                if (!isInEditMode() && display != null) {
                    float refreshRate2 = display.getRefreshRate();
                    float displayRefreshRate = refreshRate2;
                    if (refreshRate2 >= 30.0f) {
                        refreshRate = displayRefreshRate;
                    }
                }
                this.mGapWorker.mFrameIntervalNs = (long) (1.0E9f / refreshRate);
                GapWorker.sGapWorker.set(this.mGapWorker);
            }
            this.mGapWorker.add(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
        }
        stopScroll();
        this.mIsAttached = false;
        if (this.mLayout != null) {
            this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
        }
        this.mPendingAccessibilityImportanceChange.clear();
        boolean removeCallbacks = removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.onDetach();
        if (ALLOW_THREAD_GAP_WORK) {
            this.mGapWorker.remove(this);
            this.mGapWorker = null;
        }
    }

    public boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    /* access modifiers changed from: 0000 */
    public void assertInLayoutOrScroll(String str) {
        String message = str;
        String str2 = message;
        if (!isComputingLayout()) {
            if (message != null) {
                throw new IllegalStateException(message);
            }
            throw new IllegalStateException("Cannot call this method unless RecyclerView is computing a layout or scrolling");
        }
    }

    /* access modifiers changed from: 0000 */
    public void assertNotInLayoutOrScroll(String str) {
        String message = str;
        String str2 = message;
        if (!isComputingLayout()) {
            if (this.mDispatchScrollCounter > 0) {
                int w = Log.w(TAG, "Cannot call this method in a scroll callback. Scroll callbacks might be run during a measure & layout pass where you cannot change the RecyclerView data. Any method call that might change the structure of the RecyclerView or the adapter contents should be postponed to the next frame.", new IllegalStateException(""));
            }
        } else if (message != null) {
            throw new IllegalStateException(message);
        } else {
            throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling");
        }
    }

    public void addOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        OnItemTouchListener listener = onItemTouchListener;
        OnItemTouchListener onItemTouchListener2 = listener;
        boolean add = this.mOnItemTouchListeners.add(listener);
    }

    public void removeOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        OnItemTouchListener listener = onItemTouchListener;
        OnItemTouchListener onItemTouchListener2 = listener;
        boolean remove = this.mOnItemTouchListeners.remove(listener);
        if (this.mActiveOnItemTouchListener == listener) {
            this.mActiveOnItemTouchListener = null;
        }
    }

    private boolean dispatchOnItemTouchIntercept(MotionEvent motionEvent) {
        MotionEvent e = motionEvent;
        MotionEvent motionEvent2 = e;
        int action = e.getAction();
        int action2 = action;
        if (action == 3 || action2 == 0) {
            this.mActiveOnItemTouchListener = null;
        }
        int listenerCount = this.mOnItemTouchListeners.size();
        for (int i = 0; i < listenerCount; i++) {
            OnItemTouchListener onItemTouchListener = (OnItemTouchListener) this.mOnItemTouchListeners.get(i);
            OnItemTouchListener listener = onItemTouchListener;
            if (onItemTouchListener.onInterceptTouchEvent(this, e) && action2 != 3) {
                this.mActiveOnItemTouchListener = listener;
                return true;
            }
        }
        return false;
    }

    private boolean dispatchOnItemTouch(MotionEvent motionEvent) {
        MotionEvent e = motionEvent;
        MotionEvent motionEvent2 = e;
        int action = e.getAction();
        int action2 = action;
        if (this.mActiveOnItemTouchListener != null) {
            if (action != 0) {
                this.mActiveOnItemTouchListener.onTouchEvent(this, e);
                if (action2 == 3 || action2 == 1) {
                    this.mActiveOnItemTouchListener = null;
                }
                return true;
            }
            this.mActiveOnItemTouchListener = null;
        }
        if (action2 != 0) {
            int listenerCount = this.mOnItemTouchListeners.size();
            int i = 0;
            while (i < listenerCount) {
                OnItemTouchListener onItemTouchListener = (OnItemTouchListener) this.mOnItemTouchListeners.get(i);
                OnItemTouchListener listener = onItemTouchListener;
                if (!onItemTouchListener.onInterceptTouchEvent(this, e)) {
                    i++;
                } else {
                    this.mActiveOnItemTouchListener = listener;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        MotionEvent e = motionEvent;
        MotionEvent motionEvent2 = e;
        if (this.mLayoutFrozen) {
            return false;
        }
        if (dispatchOnItemTouchIntercept(e)) {
            cancelTouch();
            return true;
        } else if (this.mLayout == null) {
            return false;
        } else {
            boolean canScrollHorizontally = this.mLayout.canScrollHorizontally();
            boolean canScrollVertically = this.mLayout.canScrollVertically();
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(e);
            int action = MotionEventCompat.getActionMasked(e);
            int actionIndex = MotionEventCompat.getActionIndex(e);
            switch (action) {
                case 0:
                    if (this.mIgnoreMotionEventTillDown) {
                        this.mIgnoreMotionEventTillDown = false;
                    }
                    this.mScrollPointerId = e.getPointerId(0);
                    int x = (int) (e.getX() + 0.5f);
                    this.mLastTouchX = x;
                    this.mInitialTouchX = x;
                    int y = (int) (e.getY() + 0.5f);
                    this.mLastTouchY = y;
                    this.mInitialTouchY = y;
                    if (this.mScrollState == 2) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        setScrollState(1);
                    }
                    int[] iArr = this.mNestedOffsets;
                    this.mNestedOffsets[1] = 0;
                    iArr[0] = 0;
                    int nestedScrollAxis = 0;
                    if (canScrollHorizontally) {
                        nestedScrollAxis = 1;
                    }
                    if (canScrollVertically) {
                        nestedScrollAxis |= 2;
                    }
                    boolean startNestedScroll = startNestedScroll(nestedScrollAxis);
                    break;
                case 1:
                    this.mVelocityTracker.clear();
                    stopNestedScroll();
                    break;
                case 2:
                    int findPointerIndex = e.findPointerIndex(this.mScrollPointerId);
                    int index = findPointerIndex;
                    if (findPointerIndex >= 0) {
                        int x2 = (int) (e.getX(index) + 0.5f);
                        int y2 = (int) (e.getY(index) + 0.5f);
                        if (this.mScrollState != 1) {
                            int dx = x2 - this.mInitialTouchX;
                            int dy = y2 - this.mInitialTouchY;
                            boolean startScroll = false;
                            if (canScrollHorizontally && Math.abs(dx) > this.mTouchSlop) {
                                this.mLastTouchX = this.mInitialTouchX + (this.mTouchSlop * (dx >= 0 ? 1 : -1));
                                startScroll = true;
                            }
                            if (canScrollVertically && Math.abs(dy) > this.mTouchSlop) {
                                this.mLastTouchY = this.mInitialTouchY + (this.mTouchSlop * (dy >= 0 ? 1 : -1));
                                startScroll = true;
                            }
                            if (startScroll) {
                                setScrollState(1);
                                break;
                            }
                        }
                    } else {
                        int e2 = Log.e(TAG, "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                        return false;
                    }
                    break;
                case 3:
                    cancelTouch();
                    break;
                case 5:
                    this.mScrollPointerId = e.getPointerId(actionIndex);
                    int x3 = (int) (e.getX(actionIndex) + 0.5f);
                    this.mLastTouchX = x3;
                    this.mInitialTouchX = x3;
                    int y3 = (int) (e.getY(actionIndex) + 0.5f);
                    this.mLastTouchY = y3;
                    this.mInitialTouchY = y3;
                    break;
                case 6:
                    onPointerUp(e);
                    break;
            }
            if (this.mScrollState != 1) {
                z = false;
            } else {
                z = true;
            }
            return z;
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        boolean disallowIntercept = z;
        int listenerCount = this.mOnItemTouchListeners.size();
        for (int i = 0; i < listenerCount; i++) {
            OnItemTouchListener onItemTouchListener = (OnItemTouchListener) this.mOnItemTouchListeners.get(i);
            OnItemTouchListener onItemTouchListener2 = onItemTouchListener;
            onItemTouchListener.onRequestDisallowInterceptTouchEvent(disallowIntercept);
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0156, code lost:
        if (fling((int) r79, (int) r81) != false) goto L_0x0158;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r88) {
        /*
            r87 = this;
            r3 = r87
            r4 = r88
            r5 = r3
            r6 = r4
            boolean r7 = r3.mLayoutFrozen
            r8 = r7
            r9 = 0
            if (r8 == r9) goto L_0x000e
        L_0x000c:
            r11 = 0
            return r11
        L_0x000e:
            boolean r10 = r3.mIgnoreMotionEventTillDown
            r8 = r10
            r9 = 0
            if (r8 != r9) goto L_0x000c
            r12 = r4
            boolean r13 = r3.dispatchOnItemTouch(r12)
            r8 = r13
            r9 = 0
            if (r8 != r9) goto L_0x0095
            android.support.v7.widget.RecyclerView$LayoutManager r15 = r3.mLayout
            r16 = 0
            r0 = r16
            if (r15 == r0) goto L_0x009a
            android.support.v7.widget.RecyclerView$LayoutManager r15 = r3.mLayout
            boolean r18 = r15.canScrollHorizontally()
            r8 = r18
            r19 = r8
            android.support.v7.widget.RecyclerView$LayoutManager r15 = r3.mLayout
            boolean r20 = r15.canScrollVertically()
            r8 = r20
            r21 = r8
            android.view.VelocityTracker r15 = r3.mVelocityTracker
            r16 = 0
            r0 = r16
            if (r15 == r0) goto L_0x009d
        L_0x0041:
            r23 = 0
            r24 = r4
            android.view.MotionEvent r15 = android.view.MotionEvent.obtain(r24)
            r25 = r15
            r26 = r4
            int r8 = android.support.p000v4.view.MotionEventCompat.getActionMasked(r26)
            r27 = r8
            r28 = r4
            int r8 = android.support.p000v4.view.MotionEventCompat.getActionIndex(r28)
            r29 = r8
            r9 = 0
            r0 = r27
            if (r0 == r9) goto L_0x00a6
        L_0x0060:
            int[] r0 = r3.mNestedOffsets
            r22 = r0
            r35 = 0
            r36 = r22
            r30 = r36[r35]
            r0 = r30
            float r0 = (float) r0
            r37 = r0
            int[] r0 = r3.mNestedOffsets
            r31 = r0
            r38 = 1
            r39 = r31
            r35 = r39[r38]
            r0 = r35
            float r0 = (float) r0
            r40 = r0
            r0 = r25
            r1 = r37
            r2 = r40
            r0.offsetLocation(r1, r2)
            switch(r27) {
                case 0: goto L_0x00bb;
                case 1: goto L_0x0104;
                case 2: goto L_0x015d;
                case 3: goto L_0x01bd;
                case 4: goto L_0x01c2;
                case 5: goto L_0x01c4;
                case 6: goto L_0x01fe;
                default: goto L_0x008a;
            }
        L_0x008a:
            r9 = 0
            r0 = r23
            if (r0 == r9) goto L_0x03e7
        L_0x008f:
            r25.recycle()
            r86 = 1
            return r86
        L_0x0095:
            r3.cancelTouch()
            r14 = 1
            return r14
        L_0x009a:
            r17 = 0
            return r17
        L_0x009d:
            android.view.VelocityTracker r22 = android.view.VelocityTracker.obtain()
            r0 = r22
            r3.mVelocityTracker = r0
            goto L_0x0041
        L_0x00a6:
            int[] r15 = r3.mNestedOffsets
            r30 = 0
            int[] r0 = r3.mNestedOffsets
            r31 = r0
            r32 = 1
            r33 = r31
            r9 = 0
            r33[r32] = r9
            r34 = r15
            r9 = 0
            r34[r30] = r9
            goto L_0x0060
        L_0x00bb:
            r35 = 0
            r0 = r35
            int r30 = r4.getPointerId(r0)
            r0 = r30
            r3.mScrollPointerId = r0
            float r40 = r4.getX()
            r41 = 1056964608(0x3f000000, float:0.5)
            float r40 = r40 + r41
            r0 = r40
            int r0 = (int) r0
            r35 = r0
            r0 = r35
            r3.mLastTouchX = r0
            r0 = r35
            r3.mInitialTouchX = r0
            float r40 = r4.getY()
            r41 = 1056964608(0x3f000000, float:0.5)
            float r40 = r40 + r41
            r0 = r40
            int r0 = (int) r0
            r35 = r0
            r0 = r35
            r3.mLastTouchY = r0
            r0 = r35
            r3.mInitialTouchY = r0
            r42 = 0
            r9 = 0
            r0 = r19
            if (r0 != r9) goto L_0x0207
        L_0x00f8:
            r9 = 0
            r0 = r21
            if (r0 != r9) goto L_0x020c
        L_0x00fd:
            r0 = r42
            boolean r43 = r3.startNestedScroll(r0)
            goto L_0x008a
        L_0x0104:
            android.view.VelocityTracker r15 = r3.mVelocityTracker
            r77 = r25
            r0 = r77
            r15.addMovement(r0)
            r23 = 1
            android.view.VelocityTracker r15 = r3.mVelocityTracker
            r30 = 1000(0x3e8, float:1.401E-42)
            int r0 = r3.mMaxFlingVelocity
            r35 = r0
            r0 = r35
            float r0 = (float) r0
            r40 = r0
            r0 = r30
            r1 = r40
            r15.computeCurrentVelocity(r0, r1)
            r9 = 0
            r0 = r19
            if (r0 != r9) goto L_0x03ae
            r49 = 0
        L_0x012a:
            r79 = r49
            r9 = 0
            r0 = r21
            if (r0 != r9) goto L_0x03c5
            r49 = 0
        L_0x0133:
            r81 = r49
            r41 = 0
            int r82 = (r79 > r41 ? 1 : (r79 == r41 ? 0 : -1))
            if (r82 != 0) goto L_0x03dc
            r41 = 0
            int r83 = (r81 > r41 ? 1 : (r81 == r41 ? 0 : -1))
            if (r83 == 0) goto L_0x03de
        L_0x0141:
            r0 = r79
            int r0 = (int) r0
            r30 = r0
            r0 = r81
            int r0 = (int) r0
            r35 = r0
            r0 = r30
            r1 = r35
            boolean r84 = r3.fling(r0, r1)
            r8 = r84
            r9 = 0
            if (r8 == r9) goto L_0x03de
        L_0x0158:
            r3.resetTouch()
            goto L_0x008a
        L_0x015d:
            int r0 = r3.mScrollPointerId
            r30 = r0
            r0 = r30
            int r8 = r4.findPointerIndex(r0)
            r42 = r8
            r9 = 0
            if (r8 < r9) goto L_0x0212
            r0 = r42
            float r49 = r4.getX(r0)
            r41 = 1056964608(0x3f000000, float:0.5)
            float r49 = r49 + r41
            r0 = r49
            int r8 = (int) r0
            r50 = r8
            r0 = r42
            float r49 = r4.getY(r0)
            r41 = 1056964608(0x3f000000, float:0.5)
            float r49 = r49 + r41
            r0 = r49
            int r8 = (int) r0
            r51 = r8
            int r8 = r3.mLastTouchX
            int r8 = r8 - r50
            r52 = r8
            int r8 = r3.mLastTouchY
            int r8 = r8 - r51
            r53 = r8
            int[] r0 = r3.mScrollConsumed
            r54 = r0
            int[] r0 = r3.mScrollOffset
            r55 = r0
            r56 = r54
            r57 = r55
            r0 = r52
            r1 = r56
            r2 = r57
            boolean r58 = r3.dispatchNestedPreScroll(r0, r8, r1, r2)
            r8 = r58
            r9 = 0
            if (r8 != r9) goto L_0x024f
        L_0x01b1:
            int r8 = r3.mScrollState
            r9 = 1
            if (r8 != r9) goto L_0x02c8
        L_0x01b6:
            int r8 = r3.mScrollState
            r9 = 1
            if (r8 == r9) goto L_0x0338
        L_0x01bb:
            goto L_0x008a
        L_0x01bd:
            r3.cancelTouch()
            goto L_0x008a
        L_0x01c2:
            goto L_0x008a
        L_0x01c4:
            r0 = r29
            int r30 = r4.getPointerId(r0)
            r0 = r30
            r3.mScrollPointerId = r0
            r0 = r29
            float r40 = r4.getX(r0)
            r41 = 1056964608(0x3f000000, float:0.5)
            float r40 = r40 + r41
            r0 = r40
            int r0 = (int) r0
            r35 = r0
            r0 = r35
            r3.mLastTouchX = r0
            r0 = r35
            r3.mInitialTouchX = r0
            r0 = r29
            float r40 = r4.getY(r0)
            r41 = 1056964608(0x3f000000, float:0.5)
            float r40 = r40 + r41
            r0 = r40
            int r0 = (int) r0
            r35 = r0
            r0 = r35
            r3.mLastTouchY = r0
            r0 = r35
            r3.mInitialTouchY = r0
            goto L_0x008a
        L_0x01fe:
            r76 = r4
            r0 = r76
            r3.onPointerUp(r0)
            goto L_0x008a
        L_0x0207:
            r8 = 1
            r42 = r8
            goto L_0x00f8
        L_0x020c:
            r8 = r42 | 2
            r42 = r8
            goto L_0x00fd
        L_0x0212:
            java.lang.String r15 = "RecyclerView"
            java.lang.StringBuilder r22 = new java.lang.StringBuilder
            r22.<init>()
            java.lang.String r31 = "Error processing scroll; pointer index for id "
            r44 = r31
            r0 = r22
            r1 = r44
            java.lang.StringBuilder r22 = r0.append(r1)
            int r0 = r3.mScrollPointerId
            r35 = r0
            r0 = r22
            r1 = r35
            java.lang.StringBuilder r22 = r0.append(r1)
            java.lang.String r31 = " not found. Did any MotionEvents get skipped?"
            r45 = r31
            r0 = r22
            r1 = r45
            java.lang.StringBuilder r22 = r0.append(r1)
            java.lang.String r22 = r22.toString()
            r46 = r15
            r47 = r22
            int r8 = android.util.Log.e(r46, r47)
            r48 = 0
            return r48
        L_0x024f:
            int[] r0 = r3.mScrollConsumed
            r22 = r0
            r35 = 0
            r59 = r22
            r30 = r59[r35]
            int r8 = r52 - r30
            r52 = r8
            int[] r0 = r3.mScrollConsumed
            r22 = r0
            r35 = 1
            r60 = r22
            r30 = r60[r35]
            int r8 = r53 - r30
            r53 = r8
            int[] r0 = r3.mScrollOffset
            r22 = r0
            r35 = 0
            r61 = r22
            r30 = r61[r35]
            r0 = r30
            float r0 = (float) r0
            r37 = r0
            int[] r0 = r3.mScrollOffset
            r31 = r0
            r38 = 1
            r62 = r31
            r35 = r62[r38]
            r0 = r35
            float r0 = (float) r0
            r40 = r0
            r0 = r25
            r1 = r37
            r2 = r40
            r0.offsetLocation(r1, r2)
            int[] r15 = r3.mNestedOffsets
            r30 = 0
            r38 = 0
            r63 = r15
            r35 = r63[r38]
            int[] r0 = r3.mScrollOffset
            r54 = r0
            r32 = 0
            r64 = r54
            r38 = r64[r32]
            int r35 = r35 + r38
            r65 = r15
            r65[r30] = r35
            int[] r15 = r3.mNestedOffsets
            r30 = 1
            r38 = 1
            r66 = r15
            r35 = r66[r38]
            int[] r0 = r3.mScrollOffset
            r54 = r0
            r32 = 1
            r67 = r54
            r38 = r67[r32]
            int r35 = r35 + r38
            r68 = r15
            r68[r30] = r35
            goto L_0x01b1
        L_0x02c8:
            r69 = 0
            r9 = 0
            r0 = r19
            if (r0 != r9) goto L_0x02db
        L_0x02cf:
            r9 = 0
            r0 = r21
            if (r0 != r9) goto L_0x0305
        L_0x02d4:
            r9 = 0
            r0 = r69
            if (r0 != r9) goto L_0x032f
            goto L_0x01b6
        L_0x02db:
            int r8 = java.lang.Math.abs(r52)
            int r0 = r3.mTouchSlop
            r30 = r0
            r0 = r30
            if (r8 > r0) goto L_0x02e8
            goto L_0x02cf
        L_0x02e8:
            r9 = 0
            r0 = r52
            if (r0 > r9) goto L_0x02fa
            r8 = r52
            int r0 = r3.mTouchSlop
            r30 = r0
            int r8 = r8 + r30
            r52 = r8
        L_0x02f7:
            r69 = 1
            goto L_0x02cf
        L_0x02fa:
            r8 = r52
            int r0 = r3.mTouchSlop
            r30 = r0
            int r8 = r8 - r30
            r52 = r8
            goto L_0x02f7
        L_0x0305:
            int r8 = java.lang.Math.abs(r53)
            int r0 = r3.mTouchSlop
            r30 = r0
            r0 = r30
            if (r8 > r0) goto L_0x0312
            goto L_0x02d4
        L_0x0312:
            r9 = 0
            r0 = r53
            if (r0 > r9) goto L_0x0324
            r8 = r53
            int r0 = r3.mTouchSlop
            r30 = r0
            int r8 = r8 + r30
            r53 = r8
        L_0x0321:
            r69 = 1
            goto L_0x02d4
        L_0x0324:
            r8 = r53
            int r0 = r3.mTouchSlop
            r30 = r0
            int r8 = r8 - r30
            r53 = r8
            goto L_0x0321
        L_0x032f:
            r30 = 1
            r0 = r30
            r3.setScrollState(r0)
            goto L_0x01b6
        L_0x0338:
            int[] r0 = r3.mScrollOffset
            r31 = r0
            r38 = 0
            r70 = r31
            r35 = r70[r38]
            int r30 = r50 - r35
            r0 = r30
            r3.mLastTouchX = r0
            int[] r0 = r3.mScrollOffset
            r31 = r0
            r38 = 1
            r71 = r31
            r35 = r71[r38]
            int r30 = r51 - r35
            r0 = r30
            r3.mLastTouchY = r0
            r9 = 0
            r0 = r19
            if (r0 != r9) goto L_0x0381
            r30 = 0
        L_0x035f:
            r9 = 0
            r0 = r21
            if (r0 != r9) goto L_0x0384
            r35 = 0
        L_0x0366:
            r72 = r25
            r0 = r30
            r1 = r35
            r2 = r72
            boolean r73 = r3.scrollByInternal(r0, r1, r2)
            r8 = r73
            r9 = 0
            if (r8 != r9) goto L_0x0387
        L_0x0377:
            android.support.v7.widget.GapWorker r15 = r3.mGapWorker
            r16 = 0
            r0 = r16
            if (r15 != r0) goto L_0x0393
            goto L_0x01bb
        L_0x0381:
            r30 = r52
            goto L_0x035f
        L_0x0384:
            r35 = r53
            goto L_0x0366
        L_0x0387:
            android.view.ViewParent r15 = r3.getParent()
            r74 = 1
            r0 = r74
            r15.requestDisallowInterceptTouchEvent(r0)
            goto L_0x0377
        L_0x0393:
            r9 = 0
            r0 = r52
            if (r0 == r9) goto L_0x03a7
        L_0x0398:
            android.support.v7.widget.GapWorker r15 = r3.mGapWorker
            r75 = r3
            r0 = r75
            r1 = r52
            r2 = r53
            r15.postFromTraversal(r0, r1, r2)
            goto L_0x01bb
        L_0x03a7:
            r9 = 0
            r0 = r53
            if (r0 != r9) goto L_0x0398
            goto L_0x01bb
        L_0x03ae:
            android.view.VelocityTracker r15 = r3.mVelocityTracker
            int r0 = r3.mScrollPointerId
            r30 = r0
            r78 = r15
            r0 = r78
            r1 = r30
            float r49 = android.support.p000v4.view.VelocityTrackerCompat.getXVelocity(r0, r1)
            r0 = r49
            float r0 = -r0
            r49 = r0
            goto L_0x012a
        L_0x03c5:
            android.view.VelocityTracker r15 = r3.mVelocityTracker
            int r0 = r3.mScrollPointerId
            r30 = r0
            r80 = r15
            r0 = r80
            r1 = r30
            float r49 = android.support.p000v4.view.VelocityTrackerCompat.getYVelocity(r0, r1)
            r0 = r49
            float r0 = -r0
            r49 = r0
            goto L_0x0133
        L_0x03dc:
            goto L_0x0141
        L_0x03de:
            r30 = 0
            r0 = r30
            r3.setScrollState(r0)
            goto L_0x0158
        L_0x03e7:
            android.view.VelocityTracker r15 = r3.mVelocityTracker
            r85 = r25
            r0 = r85
            r15.addMovement(r0)
            goto L_0x008f
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.widget.RecyclerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void resetTouch() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        }
        stopNestedScroll();
        releaseGlows();
    }

    private void cancelTouch() {
        resetTouch();
        setScrollState(0);
    }

    private void onPointerUp(MotionEvent motionEvent) {
        MotionEvent e = motionEvent;
        MotionEvent motionEvent2 = e;
        int actionIndex = MotionEventCompat.getActionIndex(e);
        int actionIndex2 = actionIndex;
        if (e.getPointerId(actionIndex) == this.mScrollPointerId) {
            int newIndex = actionIndex2 != 0 ? 0 : 1;
            this.mScrollPointerId = e.getPointerId(newIndex);
            int x = (int) (e.getX(newIndex) + 0.5f);
            this.mLastTouchX = x;
            this.mInitialTouchX = x;
            int y = (int) (e.getY(newIndex) + 0.5f);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float vScroll;
        float hScroll;
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        if (this.mLayout == null) {
            return false;
        }
        if (this.mLayoutFrozen) {
            return false;
        }
        if ((event.getSource() & 2) != 0 && event.getAction() == 8) {
            if (!this.mLayout.canScrollVertically()) {
                vScroll = 0.0f;
            } else {
                vScroll = -MotionEventCompat.getAxisValue(event, 9);
            }
            if (!this.mLayout.canScrollHorizontally()) {
                hScroll = 0.0f;
            } else {
                hScroll = MotionEventCompat.getAxisValue(event, 10);
            }
            if (!(vScroll == 0.0f && hScroll == 0.0f)) {
                float scrollFactor = getScrollFactor();
                float f = scrollFactor;
                boolean scrollByInternal = scrollByInternal((int) (hScroll * scrollFactor), (int) (vScroll * scrollFactor), event);
            }
        }
        return false;
    }

    private float getScrollFactor() {
        if (this.mScrollFactor == Float.MIN_VALUE) {
            TypedValue outValue = new TypedValue();
            if (!getContext().getTheme().resolveAttribute(16842829, outValue, true)) {
                return 0.0f;
            }
            this.mScrollFactor = outValue.getDimension(getContext().getResources().getDisplayMetrics());
        }
        return this.mScrollFactor;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthSpec = i;
        int heightSpec = i2;
        int i3 = widthSpec;
        int i4 = heightSpec;
        if (this.mLayout != null) {
            if (this.mLayout.mAutoMeasure) {
                boolean skipMeasure = MeasureSpec.getMode(widthSpec) == 1073741824 && MeasureSpec.getMode(heightSpec) == 1073741824;
                this.mLayout.onMeasure(this.mRecycler, this.mState, widthSpec, heightSpec);
                if (!skipMeasure && this.mAdapter != null) {
                    if (this.mState.mLayoutStep == 1) {
                        dispatchLayoutStep1();
                    }
                    this.mLayout.setMeasureSpecs(widthSpec, heightSpec);
                    this.mState.mIsMeasuring = true;
                    dispatchLayoutStep2();
                    this.mLayout.setMeasuredDimensionFromChildren(widthSpec, heightSpec);
                    if (this.mLayout.shouldMeasureTwice()) {
                        this.mLayout.setMeasureSpecs(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                        this.mState.mIsMeasuring = true;
                        dispatchLayoutStep2();
                        this.mLayout.setMeasuredDimensionFromChildren(widthSpec, heightSpec);
                    }
                } else {
                    return;
                }
            } else if (!this.mHasFixedSize) {
                if (this.mAdapterUpdateDuringMeasure) {
                    eatRequestLayout();
                    processAdapterUpdatesAndSetAnimationFlags();
                    if (!this.mState.mRunPredictiveAnimations) {
                        this.mAdapterHelper.consumeUpdatesInOnePass();
                        this.mState.mInPreLayout = false;
                    } else {
                        this.mState.mInPreLayout = true;
                    }
                    this.mAdapterUpdateDuringMeasure = false;
                    resumeRequestLayout(false);
                }
                if (this.mAdapter == null) {
                    this.mState.mItemCount = 0;
                } else {
                    this.mState.mItemCount = this.mAdapter.getItemCount();
                }
                eatRequestLayout();
                this.mLayout.onMeasure(this.mRecycler, this.mState, widthSpec, heightSpec);
                resumeRequestLayout(false);
                this.mState.mInPreLayout = false;
            } else {
                this.mLayout.onMeasure(this.mRecycler, this.mState, widthSpec, heightSpec);
                return;
            }
            return;
        }
        defaultOnMeasure(widthSpec, heightSpec);
    }

    /* access modifiers changed from: 0000 */
    public void defaultOnMeasure(int i, int i2) {
        int widthSpec = i;
        int heightSpec = i2;
        int i3 = widthSpec;
        int i4 = heightSpec;
        int width = LayoutManager.chooseSize(widthSpec, getPaddingLeft() + getPaddingRight(), ViewCompat.getMinimumWidth(this));
        int chooseSize = LayoutManager.chooseSize(heightSpec, getPaddingTop() + getPaddingBottom(), ViewCompat.getMinimumHeight(this));
        int i5 = chooseSize;
        setMeasuredDimension(width, chooseSize);
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
        if (w != oldw || h != oldh) {
            invalidateGlows();
        }
    }

    public void setItemAnimator(ItemAnimator itemAnimator) {
        ItemAnimator animator = itemAnimator;
        ItemAnimator itemAnimator2 = animator;
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
            this.mItemAnimator.setListener(null);
        }
        this.mItemAnimator = animator;
        if (this.mItemAnimator != null) {
            this.mItemAnimator.setListener(this.mItemAnimatorListener);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onEnterLayoutOrScroll() {
        this.mLayoutOrScrollCounter++;
    }

    /* access modifiers changed from: 0000 */
    public void onExitLayoutOrScroll() {
        this.mLayoutOrScrollCounter--;
        if (this.mLayoutOrScrollCounter < 1) {
            this.mLayoutOrScrollCounter = 0;
            dispatchContentChangedIfNecessary();
            dispatchPendingImportantForAccessibilityChanges();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isAccessibilityEnabled() {
        return this.mAccessibilityManager != null && this.mAccessibilityManager.isEnabled();
    }

    private void dispatchContentChangedIfNecessary() {
        int i = this.mEatenAccessibilityChangeFlags;
        int flags = i;
        this.mEatenAccessibilityChangeFlags = 0;
        if (i != 0 && isAccessibilityEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            AccessibilityEvent event = obtain;
            obtain.setEventType(2048);
            AccessibilityEventCompat.setContentChangeTypes(event, flags);
            sendAccessibilityEventUnchecked(event);
        }
    }

    public boolean isComputingLayout() {
        return this.mLayoutOrScrollCounter > 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldDeferAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        if (!isComputingLayout()) {
            return false;
        }
        int type = 0;
        if (event != null) {
            type = AccessibilityEventCompat.getContentChangeTypes(event);
        }
        if (type == 0) {
            type = 0;
        }
        this.mEatenAccessibilityChangeFlags |= type;
        return true;
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        if (!shouldDeferAccessibilityEvent(event)) {
            super.sendAccessibilityEventUnchecked(event);
        }
    }

    public ItemAnimator getItemAnimator() {
        return this.mItemAnimator;
    }

    /* access modifiers changed from: 0000 */
    public void postAnimationRunner() {
        if (!this.mPostedAnimatorRunner && this.mIsAttached) {
            ViewCompat.postOnAnimation(this, this.mItemAnimatorRunner);
            this.mPostedAnimatorRunner = true;
        }
    }

    private boolean predictiveItemAnimationsEnabled() {
        return this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations();
    }

    private void processAdapterUpdatesAndSetAnimationFlags() {
        if (this.mDataSetHasChangedAfterLayout) {
            this.mAdapterHelper.reset();
            this.mLayout.onItemsChanged(this);
        }
        if (!predictiveItemAnimationsEnabled()) {
            this.mAdapterHelper.consumeUpdatesInOnePass();
        } else {
            this.mAdapterHelper.preProcess();
        }
        boolean animationTypeSupported = this.mItemsAddedOrRemoved || this.mItemsChanged;
        this.mState.mRunSimpleAnimations = this.mFirstLayoutComplete && this.mItemAnimator != null && (this.mDataSetHasChangedAfterLayout || animationTypeSupported || this.mLayout.mRequestedSimpleAnimations) && (!this.mDataSetHasChangedAfterLayout || this.mAdapter.hasStableIds());
        this.mState.mRunPredictiveAnimations = this.mState.mRunSimpleAnimations && animationTypeSupported && !this.mDataSetHasChangedAfterLayout && predictiveItemAnimationsEnabled();
    }

    /* access modifiers changed from: 0000 */
    public void dispatchLayout() {
        if (this.mAdapter == null) {
            int e = Log.e(TAG, "No adapter attached; skipping layout");
        } else if (this.mLayout != null) {
            this.mState.mIsMeasuring = false;
            if (this.mState.mLayoutStep == 1) {
                dispatchLayoutStep1();
                this.mLayout.setExactMeasureSpecsFrom(this);
                dispatchLayoutStep2();
            } else if (!this.mAdapterHelper.hasUpdates() && this.mLayout.getWidth() == getWidth() && this.mLayout.getHeight() == getHeight()) {
                this.mLayout.setExactMeasureSpecsFrom(this);
            } else {
                this.mLayout.setExactMeasureSpecsFrom(this);
                dispatchLayoutStep2();
            }
            dispatchLayoutStep3();
        } else {
            int e2 = Log.e(TAG, "No layout manager attached; skipping layout");
        }
    }

    private void saveFocusInfo() {
        int i;
        View child = null;
        if (this.mPreserveFocusAfterLayout && hasFocus() && this.mAdapter != null) {
            child = getFocusedChild();
        }
        ViewHolder focusedVh = child != null ? findContainingViewHolder(child) : null;
        if (focusedVh != null) {
            this.mState.mFocusedItemId = !this.mAdapter.hasStableIds() ? -1 : focusedVh.getItemId();
            State state = this.mState;
            if (this.mDataSetHasChangedAfterLayout) {
                i = -1;
            } else if (!focusedVh.isRemoved()) {
                i = focusedVh.getAdapterPosition();
            } else {
                i = focusedVh.mOldPosition;
            }
            state.mFocusedItemPosition = i;
            this.mState.mFocusedSubChildId = getDeepestFocusedViewWithId(focusedVh.itemView);
            return;
        }
        resetFocusInfo();
    }

    private void resetFocusInfo() {
        this.mState.mFocusedItemId = -1;
        this.mState.mFocusedItemPosition = -1;
        this.mState.mFocusedSubChildId = -1;
    }

    @Nullable
    private View findNextViewToFocus() {
        int startFocusSearchIndex = this.mState.mFocusedItemPosition == -1 ? 0 : this.mState.mFocusedItemPosition;
        int itemCount = this.mState.getItemCount();
        int i = startFocusSearchIndex;
        while (i < itemCount) {
            ViewHolder findViewHolderForAdapterPosition = findViewHolderForAdapterPosition(i);
            ViewHolder nextFocus = findViewHolderForAdapterPosition;
            if (findViewHolderForAdapterPosition == null) {
                break;
            } else if (nextFocus.itemView.hasFocusable()) {
                return nextFocus.itemView;
            } else {
                i++;
            }
        }
        int min = Math.min(itemCount, startFocusSearchIndex);
        int i2 = min;
        for (int i3 = min - 1; i3 >= 0; i3--) {
            ViewHolder findViewHolderForAdapterPosition2 = findViewHolderForAdapterPosition(i3);
            ViewHolder nextFocus2 = findViewHolderForAdapterPosition2;
            if (findViewHolderForAdapterPosition2 == null) {
                return null;
            }
            if (nextFocus2.itemView.hasFocusable()) {
                return nextFocus2.itemView;
            }
        }
        return null;
    }

    private void recoverFocusFromState() {
        if (this.mPreserveFocusAfterLayout && this.mAdapter != null && hasFocus() && getDescendantFocusability() != 393216 && (getDescendantFocusability() != 131072 || !isFocused())) {
            if (!isFocused()) {
                View focusedChild = getFocusedChild();
                View focusedChild2 = focusedChild;
                if (IGNORE_DETACHED_FOCUSED_CHILD && (focusedChild.getParent() == null || !focusedChild2.hasFocus())) {
                    if (this.mChildHelper.getChildCount() == 0) {
                        boolean requestFocus = requestFocus();
                        return;
                    }
                } else if (!this.mChildHelper.isHidden(focusedChild2)) {
                    return;
                }
            }
            ViewHolder focusTarget = null;
            if (this.mState.mFocusedItemId != -1 && this.mAdapter.hasStableIds()) {
                focusTarget = findViewHolderForItemId(this.mState.mFocusedItemId);
            }
            View viewToFocus = null;
            if (focusTarget != null && !this.mChildHelper.isHidden(focusTarget.itemView) && focusTarget.itemView.hasFocusable()) {
                viewToFocus = focusTarget.itemView;
            } else if (this.mChildHelper.getChildCount() > 0) {
                viewToFocus = findNextViewToFocus();
            }
            if (viewToFocus != null) {
                if (((long) this.mState.mFocusedSubChildId) != -1) {
                    View findViewById = viewToFocus.findViewById(this.mState.mFocusedSubChildId);
                    View child = findViewById;
                    if (findViewById != null && child.isFocusable()) {
                        viewToFocus = child;
                    }
                }
                boolean requestFocus2 = viewToFocus.requestFocus();
            }
        }
    }

    private int getDeepestFocusedViewWithId(View view) {
        View view2 = view;
        View view3 = view2;
        int lastKnownId = view2.getId();
        while (!view3.isFocused() && (view3 instanceof ViewGroup) && view3.hasFocus()) {
            View focusedChild = ((ViewGroup) view3).getFocusedChild();
            view3 = focusedChild;
            int id = focusedChild.getId();
            int i = id;
            if (id != -1) {
                lastKnownId = view3.getId();
            }
        }
        return lastKnownId;
    }

    private void dispatchLayoutStep1() {
        this.mState.assertLayoutStep(1);
        this.mState.mIsMeasuring = false;
        eatRequestLayout();
        this.mViewInfoStore.clear();
        onEnterLayoutOrScroll();
        processAdapterUpdatesAndSetAnimationFlags();
        saveFocusInfo();
        this.mState.mTrackOldChangeHolders = this.mState.mRunSimpleAnimations && this.mItemsChanged;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        this.mState.mInPreLayout = this.mState.mRunPredictiveAnimations;
        this.mState.mItemCount = this.mAdapter.getItemCount();
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.mRunSimpleAnimations) {
            int count = this.mChildHelper.getChildCount();
            for (int i = 0; i < count; i++) {
                ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                ViewHolder holder = childViewHolderInt;
                if (!childViewHolderInt.shouldIgnore() && (!holder.isInvalid() || this.mAdapter.hasStableIds())) {
                    this.mViewInfoStore.addToPreLayout(holder, this.mItemAnimator.recordPreLayoutInformation(this.mState, holder, ItemAnimator.buildAdapterChangeFlagsForAnimations(holder), holder.getUnmodifiedPayloads()));
                    if (this.mState.mTrackOldChangeHolders && holder.isUpdated() && !holder.isRemoved() && !holder.shouldIgnore() && !holder.isInvalid()) {
                        long changedHolderKey = getChangedHolderKey(holder);
                        long j = changedHolderKey;
                        this.mViewInfoStore.addToOldChangeHolders(changedHolderKey, holder);
                    }
                }
            }
        }
        if (!this.mState.mRunPredictiveAnimations) {
            clearOldPositions();
        } else {
            saveOldPositions();
            boolean z = this.mState.mStructureChanged;
            boolean z2 = z;
            this.mState.mStructureChanged = false;
            this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
            this.mState.mStructureChanged = z;
            for (int i2 = 0; i2 < this.mChildHelper.getChildCount(); i2++) {
                View childAt = this.mChildHelper.getChildAt(i2);
                View view = childAt;
                ViewHolder childViewHolderInt2 = getChildViewHolderInt(childAt);
                ViewHolder viewHolder = childViewHolderInt2;
                if (!childViewHolderInt2.shouldIgnore() && !this.mViewInfoStore.isInPreLayout(viewHolder)) {
                    int flags = ItemAnimator.buildAdapterChangeFlagsForAnimations(viewHolder);
                    boolean hasAnyOfTheFlags = viewHolder.hasAnyOfTheFlags(8192);
                    boolean wasHidden = hasAnyOfTheFlags;
                    if (!hasAnyOfTheFlags) {
                        flags |= 4096;
                    }
                    ItemHolderInfo animationInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, viewHolder, flags, viewHolder.getUnmodifiedPayloads());
                    if (!wasHidden) {
                        this.mViewInfoStore.addToAppearedInPreLayoutHolders(viewHolder, animationInfo);
                    } else {
                        recordAnimationInfoIfBouncedHiddenView(viewHolder, animationInfo);
                    }
                }
            }
            clearOldPositions();
        }
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
        this.mState.mLayoutStep = 2;
    }

    private void dispatchLayoutStep2() {
        eatRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.assertLayoutStep(6);
        this.mAdapterHelper.consumeUpdatesInOnePass();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.mState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
        this.mState.mInPreLayout = false;
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        this.mState.mStructureChanged = false;
        this.mPendingSavedState = null;
        this.mState.mRunSimpleAnimations = this.mState.mRunSimpleAnimations && this.mItemAnimator != null;
        this.mState.mLayoutStep = 4;
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
    }

    private void dispatchLayoutStep3() {
        this.mState.assertLayoutStep(4);
        eatRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.mLayoutStep = 1;
        if (this.mState.mRunSimpleAnimations) {
            for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; i--) {
                ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                ViewHolder holder = childViewHolderInt;
                if (!childViewHolderInt.shouldIgnore()) {
                    long changedHolderKey = getChangedHolderKey(holder);
                    long j = changedHolderKey;
                    ItemHolderInfo animationInfo = this.mItemAnimator.recordPostLayoutInformation(this.mState, holder);
                    ViewHolder fromOldChangeHolders = this.mViewInfoStore.getFromOldChangeHolders(changedHolderKey);
                    ViewHolder oldChangeViewHolder = fromOldChangeHolders;
                    if (fromOldChangeHolders != null && !oldChangeViewHolder.shouldIgnore()) {
                        boolean oldDisappearing = this.mViewInfoStore.isDisappearing(oldChangeViewHolder);
                        boolean newDisappearing = this.mViewInfoStore.isDisappearing(holder);
                        if (oldDisappearing && oldChangeViewHolder == holder) {
                            this.mViewInfoStore.addToPostLayout(holder, animationInfo);
                        } else {
                            ItemHolderInfo preInfo = this.mViewInfoStore.popFromPreLayout(oldChangeViewHolder);
                            this.mViewInfoStore.addToPostLayout(holder, animationInfo);
                            ItemHolderInfo postInfo = this.mViewInfoStore.popFromPostLayout(holder);
                            if (preInfo != null) {
                                animateChange(oldChangeViewHolder, holder, preInfo, postInfo, oldDisappearing, newDisappearing);
                            } else {
                                handleMissingPreInfoForChangeError(changedHolderKey, holder, oldChangeViewHolder);
                            }
                        }
                    } else {
                        this.mViewInfoStore.addToPostLayout(holder, animationInfo);
                    }
                }
            }
            this.mViewInfoStore.process(this.mViewInfoProcessCallback);
        }
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        this.mState.mPreviousLayoutItemCount = this.mState.mItemCount;
        this.mDataSetHasChangedAfterLayout = false;
        this.mState.mRunSimpleAnimations = false;
        this.mState.mRunPredictiveAnimations = false;
        this.mLayout.mRequestedSimpleAnimations = false;
        if (this.mRecycler.mChangedScrap != null) {
            this.mRecycler.mChangedScrap.clear();
        }
        if (this.mLayout.mPrefetchMaxObservedInInitialPrefetch) {
            this.mLayout.mPrefetchMaxCountObserved = 0;
            this.mLayout.mPrefetchMaxObservedInInitialPrefetch = false;
            this.mRecycler.updateViewCacheSize();
        }
        this.mLayout.onLayoutCompleted(this.mState);
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
        this.mViewInfoStore.clear();
        if (didChildRangeChange(this.mMinMaxLayoutPositions[0], this.mMinMaxLayoutPositions[1])) {
            dispatchOnScrolled(0, 0);
        }
        recoverFocusFromState();
        resetFocusInfo();
    }

    private void handleMissingPreInfoForChangeError(long j, ViewHolder viewHolder, ViewHolder viewHolder2) {
        long key = j;
        ViewHolder holder = viewHolder;
        ViewHolder oldChangeViewHolder = viewHolder2;
        long j2 = key;
        ViewHolder viewHolder3 = holder;
        ViewHolder viewHolder4 = oldChangeViewHolder;
        int childCount = this.mChildHelper.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mChildHelper.getChildAt(i);
            View view = childAt;
            ViewHolder childViewHolderInt = getChildViewHolderInt(childAt);
            ViewHolder other = childViewHolderInt;
            if (childViewHolderInt != holder) {
                long changedHolderKey = getChangedHolderKey(other);
                long j3 = changedHolderKey;
                if (changedHolderKey == key) {
                    if (this.mAdapter != null && this.mAdapter.hasStableIds()) {
                        throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + other + " \n View Holder 2:" + holder);
                    }
                    throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + other + " \n View Holder 2:" + holder);
                }
            }
        }
        int e = Log.e(TAG, "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + oldChangeViewHolder + " cannot be found but it is necessary for " + holder);
    }

    /* access modifiers changed from: 0000 */
    public void recordAnimationInfoIfBouncedHiddenView(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        ViewHolder viewHolder2 = viewHolder;
        ItemHolderInfo animationInfo = itemHolderInfo;
        ViewHolder viewHolder3 = viewHolder2;
        ItemHolderInfo itemHolderInfo2 = animationInfo;
        viewHolder2.setFlags(0, 8192);
        if (this.mState.mTrackOldChangeHolders && viewHolder2.isUpdated() && !viewHolder2.isRemoved() && !viewHolder2.shouldIgnore()) {
            long changedHolderKey = getChangedHolderKey(viewHolder2);
            long j = changedHolderKey;
            this.mViewInfoStore.addToOldChangeHolders(changedHolderKey, viewHolder2);
        }
        this.mViewInfoStore.addToPreLayout(viewHolder2, animationInfo);
    }

    private void findMinMaxChildLayoutPositions(int[] iArr) {
        int[] into = iArr;
        int[] iArr2 = into;
        int childCount = this.mChildHelper.getChildCount();
        int count = childCount;
        if (childCount != 0) {
            int minPositionPreLayout = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            int maxPositionPreLayout = Integer.MIN_VALUE;
            for (int i = 0; i < count; i++) {
                ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                ViewHolder holder = childViewHolderInt;
                if (!childViewHolderInt.shouldIgnore()) {
                    int layoutPosition = holder.getLayoutPosition();
                    int pos = layoutPosition;
                    if (layoutPosition < minPositionPreLayout) {
                        minPositionPreLayout = pos;
                    }
                    if (pos > maxPositionPreLayout) {
                        maxPositionPreLayout = pos;
                    }
                }
            }
            into[0] = minPositionPreLayout;
            into[1] = maxPositionPreLayout;
            return;
        }
        into[0] = -1;
        into[1] = -1;
    }

    private boolean didChildRangeChange(int i, int i2) {
        int minPositionPreLayout = i;
        int maxPositionPreLayout = i2;
        int i3 = minPositionPreLayout;
        int i4 = maxPositionPreLayout;
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        return (this.mMinMaxLayoutPositions[0] == minPositionPreLayout && this.mMinMaxLayoutPositions[1] == maxPositionPreLayout) ? false : true;
    }

    /* access modifiers changed from: protected */
    public void removeDetachedView(View view, boolean z) {
        View child = view;
        View view2 = child;
        boolean animate = z;
        ViewHolder childViewHolderInt = getChildViewHolderInt(child);
        ViewHolder vh = childViewHolderInt;
        if (childViewHolderInt != null) {
            if (vh.isTmpDetached()) {
                vh.clearTmpDetachFlag();
            } else if (!vh.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + vh);
            }
        }
        dispatchChildDetached(child);
        super.removeDetachedView(child, animate);
    }

    /* access modifiers changed from: 0000 */
    public long getChangedHolderKey(ViewHolder viewHolder) {
        ViewHolder holder = viewHolder;
        ViewHolder viewHolder2 = holder;
        return !this.mAdapter.hasStableIds() ? (long) holder.mPosition : holder.getItemId();
    }

    /* access modifiers changed from: 0000 */
    public void animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
        ViewHolder itemHolder = viewHolder;
        ItemHolderInfo preLayoutInfo = itemHolderInfo;
        ItemHolderInfo postLayoutInfo = itemHolderInfo2;
        ViewHolder viewHolder2 = itemHolder;
        ItemHolderInfo itemHolderInfo3 = preLayoutInfo;
        ItemHolderInfo itemHolderInfo4 = postLayoutInfo;
        itemHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateAppearance(itemHolder, preLayoutInfo, postLayoutInfo)) {
            postAnimationRunner();
        }
    }

    /* access modifiers changed from: 0000 */
    public void animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2) {
        ViewHolder holder = viewHolder;
        ItemHolderInfo preLayoutInfo = itemHolderInfo;
        ItemHolderInfo postLayoutInfo = itemHolderInfo2;
        ViewHolder viewHolder2 = holder;
        ItemHolderInfo itemHolderInfo3 = preLayoutInfo;
        ItemHolderInfo itemHolderInfo4 = postLayoutInfo;
        addAnimatingView(holder);
        holder.setIsRecyclable(false);
        if (this.mItemAnimator.animateDisappearance(holder, preLayoutInfo, postLayoutInfo)) {
            postAnimationRunner();
        }
    }

    private void animateChange(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2, boolean z, boolean z2) {
        ViewHolder oldHolder = viewHolder;
        ViewHolder newHolder = viewHolder2;
        ItemHolderInfo preInfo = itemHolderInfo;
        ItemHolderInfo postInfo = itemHolderInfo2;
        ViewHolder viewHolder3 = oldHolder;
        ViewHolder viewHolder4 = newHolder;
        ItemHolderInfo itemHolderInfo3 = preInfo;
        ItemHolderInfo itemHolderInfo4 = postInfo;
        boolean oldHolderDisappearing = z;
        boolean newHolderDisappearing = z2;
        oldHolder.setIsRecyclable(false);
        if (oldHolderDisappearing) {
            addAnimatingView(oldHolder);
        }
        if (oldHolder != newHolder) {
            if (newHolderDisappearing) {
                addAnimatingView(newHolder);
            }
            oldHolder.mShadowedHolder = newHolder;
            addAnimatingView(oldHolder);
            this.mRecycler.unscrapView(oldHolder);
            newHolder.setIsRecyclable(false);
            newHolder.mShadowingHolder = oldHolder;
        }
        if (this.mItemAnimator.animateChange(oldHolder, newHolder, preInfo, postInfo)) {
            postAnimationRunner();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = z;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        int i8 = i4;
        TraceCompat.beginSection(TRACE_ON_LAYOUT_TAG);
        dispatchLayout();
        TraceCompat.endSection();
        this.mFirstLayoutComplete = true;
    }

    public void requestLayout() {
        if (this.mEatRequestLayout == 0 && !this.mLayoutFrozen) {
            super.requestLayout();
        } else {
            this.mLayoutRequestEaten = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public void markItemDecorInsetsDirty() {
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < childCount; i++) {
            View unfilteredChildAt = this.mChildHelper.getUnfilteredChildAt(i);
            View view = unfilteredChildAt;
            ((LayoutParams) unfilteredChildAt.getLayoutParams()).mInsetsDirty = true;
        }
        this.mRecycler.markItemDecorInsetsDirty();
    }

    public void draw(Canvas canvas) {
        Canvas c = canvas;
        Canvas canvas2 = c;
        super.draw(c);
        int count = this.mItemDecorations.size();
        for (int i = 0; i < count; i++) {
            ((ItemDecoration) this.mItemDecorations.get(i)).onDrawOver(c, this, this.mState);
        }
        boolean needsInvalidate = false;
        if (this.mLeftGlow != null && !this.mLeftGlow.isFinished()) {
            int restore = c.save();
            int padding = !this.mClipToPadding ? 0 : getPaddingBottom();
            c.rotate(270.0f);
            c.translate((float) ((-getHeight()) + padding), 0.0f);
            needsInvalidate = this.mLeftGlow != null && this.mLeftGlow.draw(c);
            c.restoreToCount(restore);
        }
        if (this.mTopGlow != null && !this.mTopGlow.isFinished()) {
            int restore2 = c.save();
            if (this.mClipToPadding) {
                c.translate((float) getPaddingLeft(), (float) getPaddingTop());
            }
            needsInvalidate |= this.mTopGlow != null && this.mTopGlow.draw(c);
            c.restoreToCount(restore2);
        }
        if (this.mRightGlow != null && !this.mRightGlow.isFinished()) {
            int restore3 = c.save();
            int width = getWidth();
            int padding2 = !this.mClipToPadding ? 0 : getPaddingTop();
            c.rotate(90.0f);
            c.translate((float) (-padding2), (float) (-width));
            needsInvalidate |= this.mRightGlow != null && this.mRightGlow.draw(c);
            c.restoreToCount(restore3);
        }
        if (this.mBottomGlow != null && !this.mBottomGlow.isFinished()) {
            int restore4 = c.save();
            c.rotate(180.0f);
            if (!this.mClipToPadding) {
                c.translate((float) (-getWidth()), (float) (-getHeight()));
            } else {
                c.translate((float) ((-getWidth()) + getPaddingRight()), (float) ((-getHeight()) + getPaddingBottom()));
            }
            needsInvalidate |= this.mBottomGlow != null && this.mBottomGlow.draw(c);
            c.restoreToCount(restore4);
        }
        if (!needsInvalidate && this.mItemAnimator != null && this.mItemDecorations.size() > 0 && this.mItemAnimator.isRunning()) {
            needsInvalidate = true;
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void onDraw(Canvas canvas) {
        Canvas c = canvas;
        Canvas canvas2 = c;
        super.onDraw(c);
        int count = this.mItemDecorations.size();
        for (int i = 0; i < count; i++) {
            ((ItemDecoration) this.mItemDecorations.get(i)).onDraw(c, this, this.mState);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return (p instanceof LayoutParams) && this.mLayout.checkLayoutParams((LayoutParams) p);
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        if (this.mLayout != null) {
            return this.mLayout.generateDefaultLayoutParams();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AttributeSet attrs = attributeSet;
        AttributeSet attributeSet2 = attrs;
        if (this.mLayout != null) {
            return this.mLayout.generateLayoutParams(getContext(), attrs);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        if (this.mLayout != null) {
            return this.mLayout.generateLayoutParams(p);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    public boolean isAnimating() {
        return this.mItemAnimator != null && this.mItemAnimator.isRunning();
    }

    /* access modifiers changed from: 0000 */
    public void saveOldPositions() {
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            ViewHolder holder = childViewHolderInt;
            if (!childViewHolderInt.shouldIgnore()) {
                holder.saveOldPosition();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void clearOldPositions() {
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            ViewHolder holder = childViewHolderInt;
            if (!childViewHolderInt.shouldIgnore()) {
                holder.clearOldPosition();
            }
        }
        this.mRecycler.clearOldPositions();
    }

    /* access modifiers changed from: 0000 */
    public void offsetPositionRecordsForMove(int i, int i2) {
        int start;
        int end;
        int inBetweenOffset;
        int from = i;
        int to = i2;
        int i3 = from;
        int i4 = to;
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        if (from >= to) {
            start = to;
            end = from;
            inBetweenOffset = 1;
        } else {
            start = from;
            end = to;
            inBetweenOffset = -1;
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i5));
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt != null && holder.mPosition >= start && holder.mPosition <= end) {
                if (holder.mPosition != from) {
                    holder.offsetPosition(inBetweenOffset, false);
                } else {
                    holder.offsetPosition(to - from, false);
                }
                this.mState.mStructureChanged = true;
            }
        }
        this.mRecycler.offsetPositionRecordsForMove(from, to);
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void offsetPositionRecordsForInsert(int i, int i2) {
        int positionStart = i;
        int itemCount = i2;
        int i3 = positionStart;
        int i4 = itemCount;
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i5));
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt != null && !holder.shouldIgnore() && holder.mPosition >= positionStart) {
                holder.offsetPosition(itemCount, false);
                this.mState.mStructureChanged = true;
            }
        }
        this.mRecycler.offsetPositionRecordsForInsert(positionStart, itemCount);
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void offsetPositionRecordsForRemove(int i, int i2, boolean z) {
        int positionStart = i;
        int itemCount = i2;
        int i3 = positionStart;
        int i4 = itemCount;
        boolean applyToPreLayout = z;
        int positionEnd = positionStart + itemCount;
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i5));
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt != null && !holder.shouldIgnore()) {
                if (holder.mPosition >= positionEnd) {
                    holder.offsetPosition(-itemCount, applyToPreLayout);
                    this.mState.mStructureChanged = true;
                } else if (holder.mPosition >= positionStart) {
                    holder.flagRemovedAndOffsetPosition(positionStart - 1, -itemCount, applyToPreLayout);
                    this.mState.mStructureChanged = true;
                }
            }
        }
        this.mRecycler.offsetPositionRecordsForRemove(positionStart, itemCount, applyToPreLayout);
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void viewRangeUpdate(int i, int i2, Object obj) {
        int positionStart = i;
        int itemCount = i2;
        Object payload = obj;
        int i3 = positionStart;
        int i4 = itemCount;
        Object obj2 = payload;
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        int positionEnd = positionStart + itemCount;
        for (int i5 = 0; i5 < childCount; i5++) {
            View unfilteredChildAt = this.mChildHelper.getUnfilteredChildAt(i5);
            View child = unfilteredChildAt;
            ViewHolder childViewHolderInt = getChildViewHolderInt(unfilteredChildAt);
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt != null && !holder.shouldIgnore() && holder.mPosition >= positionStart && holder.mPosition < positionEnd) {
                holder.addFlags(2);
                holder.addChangePayload(payload);
                ((LayoutParams) child.getLayoutParams()).mInsetsDirty = true;
            }
        }
        this.mRecycler.viewRangeUpdate(positionStart, itemCount);
    }

    /* access modifiers changed from: 0000 */
    public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        return this.mItemAnimator == null || this.mItemAnimator.canReuseUpdatedViewHolder(viewHolder2, viewHolder2.getUnmodifiedPayloads());
    }

    /* access modifiers changed from: 0000 */
    public void setDataSetChangedAfterLayout() {
        if (!this.mDataSetHasChangedAfterLayout) {
            this.mDataSetHasChangedAfterLayout = true;
            int childCount = this.mChildHelper.getUnfilteredChildCount();
            for (int i = 0; i < childCount; i++) {
                ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
                ViewHolder holder = childViewHolderInt;
                if (childViewHolderInt != null && !holder.shouldIgnore()) {
                    holder.addFlags(512);
                }
            }
            this.mRecycler.setAdapterPositionsAsUnknown();
            markKnownViewsInvalid();
        }
    }

    /* access modifiers changed from: 0000 */
    public void markKnownViewsInvalid() {
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt != null && !holder.shouldIgnore()) {
                holder.addFlags(6);
            }
        }
        markItemDecorInsetsDirty();
        this.mRecycler.markKnownViewsInvalid();
    }

    public void invalidateItemDecorations() {
        if (this.mItemDecorations.size() != 0) {
            if (this.mLayout != null) {
                this.mLayout.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
            }
            markItemDecorInsetsDirty();
            requestLayout();
        }
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.mPreserveFocusAfterLayout;
    }

    public void setPreserveFocusAfterLayout(boolean z) {
        this.mPreserveFocusAfterLayout = z;
    }

    public ViewHolder getChildViewHolder(View view) {
        View child = view;
        View view2 = child;
        ViewParent parent = child.getParent();
        ViewParent parent2 = parent;
        if (parent == null || parent2 == this) {
            return getChildViewHolderInt(child);
        }
        throw new IllegalArgumentException("View " + child + " is not a direct child of " + this);
    }

    @Nullable
    public View findContainingItemView(View view) {
        ViewParent parent;
        View view2 = view;
        View view3 = view2;
        ViewParent parent2 = view2.getParent();
        while (true) {
            parent = parent2;
            if (!(parent == null || parent == this || !(parent instanceof View))) {
                View view4 = (View) parent;
                view3 = view4;
                parent2 = view4.getParent();
            }
        }
        return parent != this ? null : view3;
    }

    @Nullable
    public ViewHolder findContainingViewHolder(View view) {
        View view2 = view;
        View view3 = view2;
        View findContainingItemView = findContainingItemView(view2);
        return findContainingItemView != null ? getChildViewHolder(findContainingItemView) : null;
    }

    static ViewHolder getChildViewHolderInt(View view) {
        View child = view;
        View view2 = child;
        if (child != null) {
            return ((LayoutParams) child.getLayoutParams()).mViewHolder;
        }
        return null;
    }

    @Deprecated
    public int getChildPosition(View view) {
        View child = view;
        View view2 = child;
        return getChildAdapterPosition(child);
    }

    public int getChildAdapterPosition(View view) {
        View child = view;
        View view2 = child;
        ViewHolder childViewHolderInt = getChildViewHolderInt(child);
        return childViewHolderInt == null ? -1 : childViewHolderInt.getAdapterPosition();
    }

    public int getChildLayoutPosition(View view) {
        View child = view;
        View view2 = child;
        ViewHolder childViewHolderInt = getChildViewHolderInt(child);
        return childViewHolderInt == null ? -1 : childViewHolderInt.getLayoutPosition();
    }

    public long getChildItemId(View view) {
        View child = view;
        View view2 = child;
        if (this.mAdapter == null || !this.mAdapter.hasStableIds()) {
            return -1;
        }
        ViewHolder childViewHolderInt = getChildViewHolderInt(child);
        return childViewHolderInt == null ? -1 : childViewHolderInt.getItemId();
    }

    @Deprecated
    public ViewHolder findViewHolderForPosition(int i) {
        int position = i;
        int i2 = position;
        return findViewHolderForPosition(position, false);
    }

    public ViewHolder findViewHolderForLayoutPosition(int i) {
        int position = i;
        int i2 = position;
        return findViewHolderForPosition(position, false);
    }

    public ViewHolder findViewHolderForAdapterPosition(int i) {
        int position = i;
        int i2 = position;
        if (this.mDataSetHasChangedAfterLayout) {
            return null;
        }
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder hidden = null;
        for (int i3 = 0; i3 < childCount; i3++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i3));
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt != null && !holder.isRemoved() && getAdapterPositionFor(holder) == position) {
                if (!this.mChildHelper.isHidden(holder.itemView)) {
                    return holder;
                }
                hidden = holder;
            }
        }
        return hidden;
    }

    /* access modifiers changed from: 0000 */
    public ViewHolder findViewHolderForPosition(int i, boolean z) {
        int position = i;
        int i2 = position;
        boolean checkNewPosition = z;
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder hidden = null;
        for (int i3 = 0; i3 < childCount; i3++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i3));
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt != null && !holder.isRemoved()) {
                if (!checkNewPosition) {
                    if (holder.getLayoutPosition() != position) {
                        continue;
                    }
                } else if (holder.mPosition != position) {
                    continue;
                }
                if (!this.mChildHelper.isHidden(holder.itemView)) {
                    return holder;
                }
                hidden = holder;
            }
        }
        return hidden;
    }

    public ViewHolder findViewHolderForItemId(long j) {
        long id = j;
        long j2 = id;
        if (this.mAdapter == null || !this.mAdapter.hasStableIds()) {
            return null;
        }
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder hidden = null;
        for (int i = 0; i < childCount; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt != null && !holder.isRemoved() && holder.getItemId() == id) {
                if (!this.mChildHelper.isHidden(holder.itemView)) {
                    return holder;
                }
                hidden = holder;
            }
        }
        return hidden;
    }

    public View findChildViewUnder(float f, float f2) {
        float x = f;
        float y = f2;
        float f3 = x;
        float f4 = y;
        int childCount = this.mChildHelper.getChildCount();
        int i = childCount;
        for (int i2 = childCount - 1; i2 >= 0; i2--) {
            View childAt = this.mChildHelper.getChildAt(i2);
            View child = childAt;
            float translationX = ViewCompat.getTranslationX(childAt);
            float translationY = ViewCompat.getTranslationY(child);
            if (x >= ((float) child.getLeft()) + translationX && x <= ((float) child.getRight()) + translationX && y >= ((float) child.getTop()) + translationY && y <= ((float) child.getBottom()) + translationY) {
                return child;
            }
        }
        return null;
    }

    public boolean drawChild(Canvas canvas, View view, long j) {
        Canvas canvas2 = canvas;
        View child = view;
        long drawingTime = j;
        Canvas canvas3 = canvas2;
        View view2 = child;
        long j2 = drawingTime;
        return super.drawChild(canvas2, child, drawingTime);
    }

    public void offsetChildrenVertical(int i) {
        int dy = i;
        int i2 = dy;
        int childCount = this.mChildHelper.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            this.mChildHelper.getChildAt(i3).offsetTopAndBottom(dy);
        }
    }

    public void onChildAttachedToWindow(View view) {
        View view2 = view;
    }

    public void onChildDetachedFromWindow(View view) {
        View view2 = view;
    }

    public void offsetChildrenHorizontal(int i) {
        int dx = i;
        int i2 = dx;
        int childCount = this.mChildHelper.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            this.mChildHelper.getChildAt(i3).offsetLeftAndRight(dx);
        }
    }

    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        View view2 = view;
        Rect outBounds = rect;
        View view3 = view2;
        Rect rect2 = outBounds;
        getDecoratedBoundsWithMarginsInt(view2, outBounds);
    }

    static void getDecoratedBoundsWithMarginsInt(View view, Rect rect) {
        View view2 = view;
        Rect outBounds = rect;
        View view3 = view2;
        Rect rect2 = outBounds;
        LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
        LayoutParams lp = layoutParams;
        Rect insets = layoutParams.mDecorInsets;
        outBounds.set((view2.getLeft() - insets.left) - lp.leftMargin, (view2.getTop() - insets.top) - lp.topMargin, view2.getRight() + insets.right + lp.rightMargin, view2.getBottom() + insets.bottom + lp.bottomMargin);
    }

    /* access modifiers changed from: 0000 */
    public Rect getItemDecorInsetsForChild(View view) {
        View child = view;
        View view2 = child;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        LayoutParams lp = layoutParams;
        if (!layoutParams.mInsetsDirty) {
            return lp.mDecorInsets;
        }
        if (this.mState.isPreLayout() && (lp.isItemChanged() || lp.isViewInvalid())) {
            return lp.mDecorInsets;
        }
        Rect rect = lp.mDecorInsets;
        Rect insets = rect;
        rect.set(0, 0, 0, 0);
        int decorCount = this.mItemDecorations.size();
        for (int i = 0; i < decorCount; i++) {
            this.mTempRect.set(0, 0, 0, 0);
            ((ItemDecoration) this.mItemDecorations.get(i)).getItemOffsets(this.mTempRect, child, this, this.mState);
            insets.left += this.mTempRect.left;
            insets.top += this.mTempRect.top;
            insets.right += this.mTempRect.right;
            insets.bottom += this.mTempRect.bottom;
        }
        lp.mInsetsDirty = false;
        return insets;
    }

    public void onScrolled(int i, int i2) {
        int i3 = i;
        int i4 = i2;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnScrolled(int i, int i2) {
        int hresult = i;
        int vresult = i2;
        int i3 = hresult;
        int i4 = vresult;
        this.mDispatchScrollCounter++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int i5 = scrollY;
        onScrollChanged(scrollX, scrollY, scrollX, scrollY);
        onScrolled(hresult, vresult);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrolled(this, hresult, vresult);
        }
        if (this.mScrollListeners != null) {
            for (int i6 = this.mScrollListeners.size() - 1; i6 >= 0; i6--) {
                ((OnScrollListener) this.mScrollListeners.get(i6)).onScrolled(this, hresult, vresult);
            }
        }
        this.mDispatchScrollCounter--;
    }

    public void onScrollStateChanged(int i) {
        int i2 = i;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnScrollStateChanged(int i) {
        int state = i;
        int i2 = state;
        if (this.mLayout != null) {
            this.mLayout.onScrollStateChanged(state);
        }
        onScrollStateChanged(state);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollStateChanged(this, state);
        }
        if (this.mScrollListeners != null) {
            for (int i3 = this.mScrollListeners.size() - 1; i3 >= 0; i3--) {
                ((OnScrollListener) this.mScrollListeners.get(i3)).onScrollStateChanged(this, state);
            }
        }
    }

    public boolean hasPendingAdapterUpdates() {
        return !this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout || this.mAdapterHelper.hasPendingUpdates();
    }

    /* access modifiers changed from: 0000 */
    public void repositionShadowingViews() {
        int count = this.mChildHelper.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = this.mChildHelper.getChildAt(i);
            ViewHolder childViewHolder = getChildViewHolder(view);
            ViewHolder holder = childViewHolder;
            if (!(childViewHolder == null || holder.mShadowingHolder == null)) {
                View shadowingView = holder.mShadowingHolder.itemView;
                int left = view.getLeft();
                int top = view.getTop();
                if (left != shadowingView.getLeft() || top != shadowingView.getTop()) {
                    shadowingView.layout(left, top, left + shadowingView.getWidth(), top + shadowingView.getHeight());
                }
            }
        }
    }

    @Nullable
    static RecyclerView findNestedRecyclerView(@NonNull View view) {
        View view2 = view;
        View view3 = view2;
        if (!(view2 instanceof ViewGroup)) {
            return null;
        }
        if (view2 instanceof RecyclerView) {
            return (RecyclerView) view2;
        }
        ViewGroup viewGroup = (ViewGroup) view2;
        ViewGroup parent = viewGroup;
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View childAt = parent.getChildAt(i);
            View view4 = childAt;
            RecyclerView findNestedRecyclerView = findNestedRecyclerView(childAt);
            RecyclerView descendant = findNestedRecyclerView;
            if (findNestedRecyclerView != null) {
                return descendant;
            }
        }
        return null;
    }

    static void clearNestedRecyclerViewIfNotNested(@NonNull ViewHolder viewHolder) {
        ViewHolder holder = viewHolder;
        ViewHolder viewHolder2 = holder;
        if (holder.mNestedRecyclerView != null) {
            View item = (View) holder.mNestedRecyclerView.get();
            while (item != null) {
                if (item != holder.itemView) {
                    ViewParent parent = item.getParent();
                    ViewParent parent2 = parent;
                    if (!(parent instanceof View)) {
                        item = null;
                    } else {
                        item = (View) parent2;
                    }
                } else {
                    return;
                }
            }
            holder.mNestedRecyclerView = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public long getNanoTime() {
        if (!ALLOW_THREAD_GAP_WORK) {
            return 0;
        }
        return System.nanoTime();
    }

    /* access modifiers changed from: 0000 */
    public void dispatchChildDetached(View view) {
        View child = view;
        View view2 = child;
        ViewHolder viewHolder = getChildViewHolderInt(child);
        onChildDetachedFromWindow(child);
        if (!(this.mAdapter == null || viewHolder == null)) {
            this.mAdapter.onViewDetachedFromWindow(viewHolder);
        }
        if (this.mOnChildAttachStateListeners != null) {
            int size = this.mOnChildAttachStateListeners.size();
            int i = size;
            for (int i2 = size - 1; i2 >= 0; i2--) {
                ((OnChildAttachStateChangeListener) this.mOnChildAttachStateListeners.get(i2)).onChildViewDetachedFromWindow(child);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchChildAttached(View view) {
        View child = view;
        View view2 = child;
        ViewHolder viewHolder = getChildViewHolderInt(child);
        onChildAttachedToWindow(child);
        if (!(this.mAdapter == null || viewHolder == null)) {
            this.mAdapter.onViewAttachedToWindow(viewHolder);
        }
        if (this.mOnChildAttachStateListeners != null) {
            int size = this.mOnChildAttachStateListeners.size();
            int i = size;
            for (int i2 = size - 1; i2 >= 0; i2--) {
                ((OnChildAttachStateChangeListener) this.mOnChildAttachStateListeners.get(i2)).onChildViewAttachedToWindow(child);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public boolean setChildImportantForAccessibilityInternal(ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = viewHolder;
        int importantForAccessibility = i;
        ViewHolder viewHolder3 = viewHolder2;
        int i2 = importantForAccessibility;
        if (!isComputingLayout()) {
            ViewCompat.setImportantForAccessibility(viewHolder2.itemView, importantForAccessibility);
            return true;
        }
        viewHolder2.mPendingAccessibilityState = importantForAccessibility;
        boolean add = this.mPendingAccessibilityImportanceChange.add(viewHolder2);
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchPendingImportantForAccessibilityChanges() {
        for (int i = this.mPendingAccessibilityImportanceChange.size() - 1; i >= 0; i--) {
            ViewHolder viewHolder = (ViewHolder) this.mPendingAccessibilityImportanceChange.get(i);
            ViewHolder viewHolder2 = viewHolder;
            if (viewHolder.itemView.getParent() == this && !viewHolder2.shouldIgnore()) {
                int i2 = viewHolder2.mPendingAccessibilityState;
                int state = i2;
                if (i2 != -1) {
                    ViewCompat.setImportantForAccessibility(viewHolder2.itemView, state);
                    viewHolder2.mPendingAccessibilityState = -1;
                }
            }
        }
        this.mPendingAccessibilityImportanceChange.clear();
    }

    /* access modifiers changed from: 0000 */
    public int getAdapterPositionFor(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        if (!viewHolder2.hasAnyOfTheFlags(524) && viewHolder2.isBound()) {
            return this.mAdapterHelper.applyPendingUpdatesToPosition(viewHolder2.mPosition);
        }
        return -1;
    }

    public void setNestedScrollingEnabled(boolean z) {
        getScrollingChildHelper().setNestedScrollingEnabled(z);
    }

    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int i) {
        int axes = i;
        int i2 = axes;
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
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
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
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
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        float velocityX = f;
        float velocityY = f2;
        float f3 = velocityX;
        float f4 = velocityY;
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, z);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        float velocityX = f;
        float velocityY = f2;
        float f3 = velocityX;
        float f4 = velocityY;
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i, int i2) {
        int childCount = i;
        int i3 = i2;
        int i4 = childCount;
        int i5 = i3;
        if (this.mChildDrawingOrderCallback != null) {
            return this.mChildDrawingOrderCallback.onGetChildDrawingOrder(childCount, i3);
        }
        return super.getChildDrawingOrder(childCount, i3);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return this.mScrollingChildHelper;
    }
}
