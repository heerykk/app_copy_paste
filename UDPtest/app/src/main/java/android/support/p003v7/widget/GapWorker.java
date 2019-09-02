package android.support.p003v7.widget;

import android.support.annotation.Nullable;
import android.support.p000v4.p002os.TraceCompat;
import android.support.p003v7.widget.RecyclerView.LayoutManager;
import android.support.p003v7.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry;
import android.support.p003v7.widget.RecyclerView.Recycler;
import android.support.p003v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/* renamed from: android.support.v7.widget.GapWorker */
final class GapWorker implements Runnable {
    static final ThreadLocal<GapWorker> sGapWorker = new ThreadLocal<>();
    static Comparator<Task> sTaskComparator = new Comparator<Task>() {
        public /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            return compare((Task) obj, (Task) obj2);
        }

        public int compare(Task task, Task task2) {
            Task lhs = task;
            Task rhs = task2;
            Task task3 = lhs;
            Task task4 = rhs;
            if ((lhs.view == null) != (rhs.view == null)) {
                return lhs.view != null ? -1 : 1;
            } else if (lhs.immediate == rhs.immediate) {
                int i = rhs.viewVelocity - lhs.viewVelocity;
                int deltaViewVelocity = i;
                if (i != 0) {
                    return deltaViewVelocity;
                }
                int i2 = lhs.distanceToItem - rhs.distanceToItem;
                int deltaDistanceToItem = i2;
                if (i2 == 0) {
                    return 0;
                }
                return deltaDistanceToItem;
            } else {
                return !lhs.immediate ? 1 : -1;
            }
        }
    };
    long mFrameIntervalNs;
    long mPostTimeNs;
    ArrayList<RecyclerView> mRecyclerViews = new ArrayList<>();
    private ArrayList<Task> mTasks = new ArrayList<>();

    /* renamed from: android.support.v7.widget.GapWorker$LayoutPrefetchRegistryImpl */
    static class LayoutPrefetchRegistryImpl implements LayoutPrefetchRegistry {
        int mCount;
        int[] mPrefetchArray;
        int mPrefetchDx;
        int mPrefetchDy;

        LayoutPrefetchRegistryImpl() {
        }

        /* access modifiers changed from: 0000 */
        public void setPrefetchVector(int i, int i2) {
            int dx = i;
            int dy = i2;
            int i3 = dx;
            int i4 = dy;
            this.mPrefetchDx = dx;
            this.mPrefetchDy = dy;
        }

        /* access modifiers changed from: 0000 */
        public void collectPrefetchPositionsFromView(RecyclerView recyclerView, boolean z) {
            RecyclerView view = recyclerView;
            RecyclerView recyclerView2 = view;
            boolean nested = z;
            this.mCount = 0;
            if (this.mPrefetchArray != null) {
                Arrays.fill(this.mPrefetchArray, -1);
            }
            LayoutManager layout = view.mLayout;
            if (view.mAdapter != null && layout != null && layout.isItemPrefetchEnabled()) {
                if (!nested) {
                    if (!view.hasPendingAdapterUpdates()) {
                        layout.collectAdjacentPrefetchPositions(this.mPrefetchDx, this.mPrefetchDy, view.mState, this);
                    }
                } else if (!view.mAdapterHelper.hasPendingUpdates()) {
                    layout.collectInitialPrefetchPositions(view.mAdapter.getItemCount(), this);
                }
                if (this.mCount > layout.mPrefetchMaxCountObserved) {
                    layout.mPrefetchMaxCountObserved = this.mCount;
                    layout.mPrefetchMaxObservedInInitialPrefetch = nested;
                    view.mRecycler.updateViewCacheSize();
                }
            }
        }

        public void addPosition(int i, int i2) {
            int layoutPosition = i;
            int pixelDistance = i2;
            int i3 = layoutPosition;
            int i4 = pixelDistance;
            if (pixelDistance >= 0) {
                int i5 = this.mCount * 2;
                int storagePosition = i5;
                if (this.mPrefetchArray == null) {
                    this.mPrefetchArray = new int[4];
                    Arrays.fill(this.mPrefetchArray, -1);
                } else if (i5 >= this.mPrefetchArray.length) {
                    int[] oldArray = this.mPrefetchArray;
                    this.mPrefetchArray = new int[(storagePosition * 2)];
                    System.arraycopy(oldArray, 0, this.mPrefetchArray, 0, oldArray.length);
                }
                this.mPrefetchArray[storagePosition] = layoutPosition;
                this.mPrefetchArray[storagePosition + 1] = pixelDistance;
                this.mCount++;
                return;
            }
            throw new IllegalArgumentException("Pixel distance must be non-negative");
        }

        /* access modifiers changed from: 0000 */
        public boolean lastPrefetchIncludedPosition(int i) {
            int position = i;
            int i2 = position;
            if (this.mPrefetchArray != null) {
                int count = this.mCount * 2;
                for (int i3 = 0; i3 < count; i3 += 2) {
                    if (this.mPrefetchArray[i3] == position) {
                        return true;
                    }
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void clearPrefetchPositions() {
            if (this.mPrefetchArray != null) {
                Arrays.fill(this.mPrefetchArray, -1);
            }
        }
    }

    /* renamed from: android.support.v7.widget.GapWorker$Task */
    static class Task {
        public int distanceToItem;
        public boolean immediate;
        public int position;
        public RecyclerView view;
        public int viewVelocity;

        Task() {
        }

        public void clear() {
            this.immediate = false;
            this.viewVelocity = 0;
            this.distanceToItem = 0;
            this.view = null;
            this.position = 0;
        }
    }

    GapWorker() {
    }

    public void add(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = recyclerView;
        RecyclerView recyclerView3 = recyclerView2;
        boolean add = this.mRecyclerViews.add(recyclerView2);
    }

    public void remove(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = recyclerView;
        RecyclerView recyclerView3 = recyclerView2;
        boolean remove = this.mRecyclerViews.remove(recyclerView2);
    }

    /* access modifiers changed from: 0000 */
    public void postFromTraversal(RecyclerView recyclerView, int i, int i2) {
        RecyclerView recyclerView2 = recyclerView;
        int prefetchDx = i;
        int prefetchDy = i2;
        RecyclerView recyclerView3 = recyclerView2;
        int i3 = prefetchDx;
        int i4 = prefetchDy;
        if (recyclerView2.isAttachedToWindow() && this.mPostTimeNs == 0) {
            this.mPostTimeNs = recyclerView2.getNanoTime();
            boolean post = recyclerView2.post(this);
        }
        recyclerView2.mPrefetchRegistry.setPrefetchVector(prefetchDx, prefetchDy);
    }

    private void buildTaskList() {
        Task task;
        int viewCount = this.mRecyclerViews.size();
        int totalTaskCount = 0;
        for (int i = 0; i < viewCount; i++) {
            RecyclerView recyclerView = (RecyclerView) this.mRecyclerViews.get(i);
            RecyclerView view = recyclerView;
            recyclerView.mPrefetchRegistry.collectPrefetchPositionsFromView(view, false);
            totalTaskCount += view.mPrefetchRegistry.mCount;
        }
        this.mTasks.ensureCapacity(totalTaskCount);
        int totalTaskIndex = 0;
        for (int i2 = 0; i2 < viewCount; i2++) {
            RecyclerView recyclerView2 = (RecyclerView) this.mRecyclerViews.get(i2);
            RecyclerView view2 = recyclerView2;
            LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView2.mPrefetchRegistry;
            LayoutPrefetchRegistryImpl prefetchRegistry = layoutPrefetchRegistryImpl;
            int viewVelocity = Math.abs(layoutPrefetchRegistryImpl.mPrefetchDx) + Math.abs(prefetchRegistry.mPrefetchDy);
            for (int j = 0; j < prefetchRegistry.mCount * 2; j += 2) {
                if (totalTaskIndex < this.mTasks.size()) {
                    task = (Task) this.mTasks.get(totalTaskIndex);
                } else {
                    task = new Task();
                    boolean add = this.mTasks.add(task);
                }
                int i3 = prefetchRegistry.mPrefetchArray[j + 1];
                int i4 = i3;
                task.immediate = i3 <= viewVelocity;
                task.viewVelocity = viewVelocity;
                task.distanceToItem = i3;
                task.view = view2;
                task.position = prefetchRegistry.mPrefetchArray[j];
                totalTaskIndex++;
            }
        }
        Collections.sort(this.mTasks, sTaskComparator);
    }

    static boolean isPrefetchPositionAttached(RecyclerView recyclerView, int i) {
        RecyclerView view = recyclerView;
        int position = i;
        RecyclerView recyclerView2 = view;
        int i2 = position;
        int childCount = view.mChildHelper.getUnfilteredChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View unfilteredChildAt = view.mChildHelper.getUnfilteredChildAt(i3);
            View view2 = unfilteredChildAt;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(unfilteredChildAt);
            ViewHolder holder = childViewHolderInt;
            if (childViewHolderInt.mPosition == position && !holder.isInvalid()) {
                return true;
            }
        }
        return false;
    }

    private ViewHolder prefetchPositionWithDeadline(RecyclerView recyclerView, int i, long j) {
        RecyclerView view = recyclerView;
        int position = i;
        long deadlineNs = j;
        RecyclerView recyclerView2 = view;
        int i2 = position;
        long j2 = deadlineNs;
        if (isPrefetchPositionAttached(view, position)) {
            return null;
        }
        Recycler recycler = view.mRecycler;
        Recycler recycler2 = recycler;
        ViewHolder tryGetViewHolderForPositionByDeadline = recycler.tryGetViewHolderForPositionByDeadline(position, false, deadlineNs);
        ViewHolder holder = tryGetViewHolderForPositionByDeadline;
        if (tryGetViewHolderForPositionByDeadline != null) {
            if (!holder.isBound()) {
                recycler2.addViewHolderToRecycledViewPool(holder, false);
            } else {
                recycler2.recycleView(holder.itemView);
            }
        }
        return holder;
    }

    private void prefetchInnerRecyclerViewWithDeadline(@Nullable RecyclerView recyclerView, long j) {
        RecyclerView innerView = recyclerView;
        long deadlineNs = j;
        RecyclerView recyclerView2 = innerView;
        long j2 = deadlineNs;
        if (innerView != null) {
            if (innerView.mDataSetHasChangedAfterLayout && innerView.mChildHelper.getUnfilteredChildCount() != 0) {
                innerView.removeAndRecycleViews();
            }
            LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = innerView.mPrefetchRegistry;
            LayoutPrefetchRegistryImpl innerPrefetchRegistry = layoutPrefetchRegistryImpl;
            layoutPrefetchRegistryImpl.collectPrefetchPositionsFromView(innerView, true);
            if (innerPrefetchRegistry.mCount != 0) {
                try {
                    TraceCompat.beginSection("RV Nested Prefetch");
                    innerView.mState.prepareForNestedPrefetch(innerView.mAdapter);
                    for (int i = 0; i < innerPrefetchRegistry.mCount * 2; i += 2) {
                        int i2 = innerPrefetchRegistry.mPrefetchArray[i];
                        int i3 = i2;
                        ViewHolder prefetchPositionWithDeadline = prefetchPositionWithDeadline(innerView, i2, deadlineNs);
                    }
                } finally {
                    TraceCompat.endSection();
                }
            }
        }
    }

    private void flushTaskWithDeadline(Task task, long j) {
        Task task2 = task;
        long deadlineNs = j;
        Task task3 = task2;
        long j2 = deadlineNs;
        ViewHolder prefetchPositionWithDeadline = prefetchPositionWithDeadline(task2.view, task2.position, !task2.immediate ? deadlineNs : Long.MAX_VALUE);
        ViewHolder holder = prefetchPositionWithDeadline;
        if (prefetchPositionWithDeadline != null && holder.mNestedRecyclerView != null) {
            prefetchInnerRecyclerViewWithDeadline((RecyclerView) holder.mNestedRecyclerView.get(), deadlineNs);
        }
    }

    private void flushTasksWithDeadline(long j) {
        long deadlineNs = j;
        long j2 = deadlineNs;
        int i = 0;
        while (i < this.mTasks.size()) {
            Task task = (Task) this.mTasks.get(i);
            Task task2 = task;
            if (task.view != null) {
                flushTaskWithDeadline(task2, deadlineNs);
                task2.clear();
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void prefetch(long j) {
        long deadlineNs = j;
        long j2 = deadlineNs;
        buildTaskList();
        flushTasksWithDeadline(deadlineNs);
    }

    public void run() {
        try {
            TraceCompat.beginSection("RV Prefetch");
            if (!this.mRecyclerViews.isEmpty()) {
                long nanos = TimeUnit.MILLISECONDS.toNanos(((RecyclerView) this.mRecyclerViews.get(0)).getDrawingTime());
                long lastFrameVsyncNs = nanos;
                if (nanos != 0) {
                    long j = lastFrameVsyncNs + this.mFrameIntervalNs;
                    long j2 = j;
                    prefetch(j);
                    this.mPostTimeNs = 0;
                    TraceCompat.endSection();
                    return;
                }
                return;
            }
            this.mPostTimeNs = 0;
            TraceCompat.endSection();
        } finally {
            this.mPostTimeNs = 0;
            TraceCompat.endSection();
        }
    }
}
