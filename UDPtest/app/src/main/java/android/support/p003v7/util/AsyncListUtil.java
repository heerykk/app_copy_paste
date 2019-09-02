package android.support.p003v7.util;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.support.p003v7.util.ThreadUtil.BackgroundCallback;
import android.support.p003v7.util.ThreadUtil.MainThreadCallback;
import android.support.p003v7.util.TileList.Tile;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

/* renamed from: android.support.v7.util.AsyncListUtil */
public class AsyncListUtil<T> {
    static final boolean DEBUG = false;
    static final String TAG = "AsyncListUtil";
    boolean mAllowScrollHints;
    private final BackgroundCallback<T> mBackgroundCallback = new BackgroundCallback<T>(this) {
        private int mFirstRequiredTileStart;
        private int mGeneration;
        private int mItemCount;
        private int mLastRequiredTileStart;
        final SparseBooleanArray mLoadedTiles = new SparseBooleanArray();
        private Tile<T> mRecycledRoot;
        final /* synthetic */ AsyncListUtil this$0;

        {
            AsyncListUtil this$02 = r6;
            AsyncListUtil asyncListUtil = this$02;
            this.this$0 = this$02;
        }

        public void refresh(int i) {
            int generation = i;
            int i2 = generation;
            this.mGeneration = generation;
            this.mLoadedTiles.clear();
            this.mItemCount = this.this$0.mDataCallback.refreshData();
            this.this$0.mMainThreadProxy.updateItemCount(this.mGeneration, this.mItemCount);
        }

        public void updateRange(int i, int i2, int i3, int i4, int i5) {
            int rangeStart = i;
            int rangeEnd = i2;
            int extRangeStart = i3;
            int extRangeEnd = i4;
            int scrollHint = i5;
            int i6 = rangeStart;
            int i7 = rangeEnd;
            int i8 = extRangeStart;
            int i9 = extRangeEnd;
            int i10 = scrollHint;
            if (rangeStart <= rangeEnd) {
                int firstVisibleTileStart = getTileStart(rangeStart);
                int lastVisibleTileStart = getTileStart(rangeEnd);
                this.mFirstRequiredTileStart = getTileStart(extRangeStart);
                this.mLastRequiredTileStart = getTileStart(extRangeEnd);
                if (scrollHint != 1) {
                    requestTiles(firstVisibleTileStart, this.mLastRequiredTileStart, scrollHint, false);
                    requestTiles(this.mFirstRequiredTileStart, firstVisibleTileStart - this.this$0.mTileSize, scrollHint, true);
                } else {
                    requestTiles(this.mFirstRequiredTileStart, lastVisibleTileStart, scrollHint, true);
                    requestTiles(lastVisibleTileStart + this.this$0.mTileSize, this.mLastRequiredTileStart, scrollHint, false);
                }
            }
        }

        private int getTileStart(int i) {
            int position = i;
            int i2 = position;
            return position - (position % this.this$0.mTileSize);
        }

        private void requestTiles(int i, int i2, int i3, boolean z) {
            int firstTileStart = i;
            int lastTileStart = i2;
            int scrollHint = i3;
            int i4 = firstTileStart;
            int i5 = lastTileStart;
            int i6 = scrollHint;
            boolean backwards = z;
            int i7 = firstTileStart;
            while (i7 <= lastTileStart) {
                this.this$0.mBackgroundProxy.loadTile(!backwards ? i7 : (lastTileStart + firstTileStart) - i7, scrollHint);
                i7 += this.this$0.mTileSize;
            }
        }

        public void loadTile(int i, int i2) {
            int position = i;
            int scrollHint = i2;
            int i3 = position;
            int i4 = scrollHint;
            if (!isTileLoaded(position)) {
                Tile acquireTile = acquireTile();
                Tile tile = acquireTile;
                acquireTile.mStartPosition = position;
                tile.mItemCount = Math.min(this.this$0.mTileSize, this.mItemCount - tile.mStartPosition);
                this.this$0.mDataCallback.fillData(tile.mItems, tile.mStartPosition, tile.mItemCount);
                flushTileCache(scrollHint);
                addTile(tile);
            }
        }

        public void recycleTile(Tile<T> tile) {
            Tile<T> tile2 = tile;
            Tile<T> tile3 = tile2;
            this.this$0.mDataCallback.recycleData(tile2.mItems, tile2.mItemCount);
            tile2.mNext = this.mRecycledRoot;
            this.mRecycledRoot = tile2;
        }

        private Tile<T> acquireTile() {
            if (this.mRecycledRoot == null) {
                return new Tile(this.this$0.mTClass, this.this$0.mTileSize);
            }
            Tile<T> result = this.mRecycledRoot;
            this.mRecycledRoot = this.mRecycledRoot.mNext;
            return result;
        }

        private boolean isTileLoaded(int i) {
            int position = i;
            int i2 = position;
            return this.mLoadedTiles.get(position);
        }

        private void addTile(Tile<T> tile) {
            Tile<T> tile2 = tile;
            Tile<T> tile3 = tile2;
            this.mLoadedTiles.put(tile2.mStartPosition, true);
            this.this$0.mMainThreadProxy.addTile(this.mGeneration, tile2);
        }

        private void removeTile(int i) {
            int position = i;
            int i2 = position;
            this.mLoadedTiles.delete(position);
            this.this$0.mMainThreadProxy.removeTile(this.mGeneration, position);
        }

        private void flushTileCache(int i) {
            int scrollHint = i;
            int i2 = scrollHint;
            int cacheSizeLimit = this.this$0.mDataCallback.getMaxCachedTiles();
            while (this.mLoadedTiles.size() >= cacheSizeLimit) {
                int firstLoadedTileStart = this.mLoadedTiles.keyAt(0);
                int lastLoadedTileStart = this.mLoadedTiles.keyAt(this.mLoadedTiles.size() - 1);
                int startMargin = this.mFirstRequiredTileStart - firstLoadedTileStart;
                int endMargin = lastLoadedTileStart - this.mLastRequiredTileStart;
                if (startMargin > 0 && (startMargin >= endMargin || scrollHint == 2)) {
                    removeTile(firstLoadedTileStart);
                } else if (endMargin > 0 && (startMargin < endMargin || scrollHint == 1)) {
                    removeTile(lastLoadedTileStart);
                } else {
                    return;
                }
            }
        }

        private void log(String str, Object... objArr) {
            String s = str;
            Object[] args = objArr;
            String str2 = s;
            Object[] objArr2 = args;
            int d = Log.d(AsyncListUtil.TAG, "[BKGR] " + String.format(s, args));
        }
    };
    final BackgroundCallback<T> mBackgroundProxy;
    final DataCallback<T> mDataCallback;
    int mDisplayedGeneration = 0;
    int mItemCount = 0;
    private final MainThreadCallback<T> mMainThreadCallback = new MainThreadCallback<T>(this) {
        final /* synthetic */ AsyncListUtil this$0;

        {
            AsyncListUtil this$02 = r5;
            AsyncListUtil asyncListUtil = this$02;
            this.this$0 = this$02;
        }

        public void updateItemCount(int i, int i2) {
            int generation = i;
            int itemCount = i2;
            int i3 = generation;
            int i4 = itemCount;
            if (isRequestedGeneration(generation)) {
                this.this$0.mItemCount = itemCount;
                this.this$0.mViewCallback.onDataRefresh();
                this.this$0.mDisplayedGeneration = this.this$0.mRequestedGeneration;
                recycleAllTiles();
                this.this$0.mAllowScrollHints = false;
                this.this$0.updateRange();
            }
        }

        public void addTile(int i, Tile<T> tile) {
            int generation = i;
            Tile<T> tile2 = tile;
            int i2 = generation;
            Tile<T> tile3 = tile2;
            if (isRequestedGeneration(generation)) {
                Tile addOrReplace = this.this$0.mTileList.addOrReplace(tile2);
                Tile tile4 = addOrReplace;
                if (addOrReplace != null) {
                    int e = Log.e(AsyncListUtil.TAG, "duplicate tile @" + tile4.mStartPosition);
                    this.this$0.mBackgroundProxy.recycleTile(tile4);
                }
                int endPosition = tile2.mStartPosition + tile2.mItemCount;
                int index = 0;
                while (index < this.this$0.mMissingPositions.size()) {
                    int position = this.this$0.mMissingPositions.keyAt(index);
                    if (tile2.mStartPosition <= position && position < endPosition) {
                        this.this$0.mMissingPositions.removeAt(index);
                        this.this$0.mViewCallback.onItemLoaded(position);
                    } else {
                        index++;
                    }
                }
                return;
            }
            this.this$0.mBackgroundProxy.recycleTile(tile2);
        }

        public void removeTile(int i, int i2) {
            int generation = i;
            int position = i2;
            int i3 = generation;
            int i4 = position;
            if (isRequestedGeneration(generation)) {
                Tile removeAtPos = this.this$0.mTileList.removeAtPos(position);
                Tile tile = removeAtPos;
                if (removeAtPos != null) {
                    this.this$0.mBackgroundProxy.recycleTile(tile);
                    return;
                }
                int e = Log.e(AsyncListUtil.TAG, "tile not found @" + position);
            }
        }

        private void recycleAllTiles() {
            for (int i = 0; i < this.this$0.mTileList.size(); i++) {
                this.this$0.mBackgroundProxy.recycleTile(this.this$0.mTileList.getAtIndex(i));
            }
            this.this$0.mTileList.clear();
        }

        private boolean isRequestedGeneration(int i) {
            int generation = i;
            int i2 = generation;
            return generation == this.this$0.mRequestedGeneration;
        }
    };
    final MainThreadCallback<T> mMainThreadProxy;
    final SparseIntArray mMissingPositions = new SparseIntArray();
    final int[] mPrevRange = new int[2];
    int mRequestedGeneration = this.mDisplayedGeneration;
    private int mScrollHint = 0;
    final Class<T> mTClass;
    final TileList<T> mTileList;
    final int mTileSize;
    final int[] mTmpRange = new int[2];
    final int[] mTmpRangeExtended = new int[2];
    final ViewCallback mViewCallback;

    /* renamed from: android.support.v7.util.AsyncListUtil$DataCallback */
    public static abstract class DataCallback<T> {
        @WorkerThread
        public abstract void fillData(T[] tArr, int i, int i2);

        @WorkerThread
        public abstract int refreshData();

        public DataCallback() {
        }

        @WorkerThread
        public void recycleData(T[] tArr, int i) {
            T[] tArr2 = tArr;
            int i2 = i;
        }

        @WorkerThread
        public int getMaxCachedTiles() {
            return 10;
        }
    }

    /* renamed from: android.support.v7.util.AsyncListUtil$ViewCallback */
    public static abstract class ViewCallback {
        public static final int HINT_SCROLL_ASC = 2;
        public static final int HINT_SCROLL_DESC = 1;
        public static final int HINT_SCROLL_NONE = 0;

        @UiThread
        public abstract void getItemRangeInto(int[] iArr);

        @UiThread
        public abstract void onDataRefresh();

        @UiThread
        public abstract void onItemLoaded(int i);

        public ViewCallback() {
        }

        @UiThread
        public void extendRangeInto(int[] iArr, int[] iArr2, int i) {
            int[] range = iArr;
            int[] outRange = iArr2;
            int scrollHint = i;
            int[] iArr3 = range;
            int[] iArr4 = outRange;
            int i2 = scrollHint;
            int i3 = (range[1] - range[0]) + 1;
            int fullRange = i3;
            int i4 = i3 / 2;
            int i5 = i4;
            outRange[0] = range[0] - (scrollHint != 1 ? i4 : fullRange);
            outRange[1] = range[1] + (scrollHint != 2 ? i4 : fullRange);
        }
    }

    public AsyncListUtil(Class<T> cls, int i, DataCallback<T> dataCallback, ViewCallback viewCallback) {
        Class<T> klass = cls;
        int tileSize = i;
        DataCallback<T> dataCallback2 = dataCallback;
        ViewCallback viewCallback2 = viewCallback;
        Class<T> cls2 = klass;
        int i2 = tileSize;
        DataCallback<T> dataCallback3 = dataCallback2;
        ViewCallback viewCallback3 = viewCallback2;
        this.mTClass = klass;
        this.mTileSize = tileSize;
        this.mDataCallback = dataCallback2;
        this.mViewCallback = viewCallback2;
        this.mTileList = new TileList<>(this.mTileSize);
        MessageThreadUtil messageThreadUtil = new MessageThreadUtil();
        this.mMainThreadProxy = messageThreadUtil.getMainThreadProxy(this.mMainThreadCallback);
        this.mBackgroundProxy = messageThreadUtil.getBackgroundProxy(this.mBackgroundCallback);
        refresh();
    }

    /* access modifiers changed from: 0000 */
    public void log(String str, Object... objArr) {
        String s = str;
        Object[] args = objArr;
        String str2 = s;
        Object[] objArr2 = args;
        int d = Log.d(TAG, "[MAIN] " + String.format(s, args));
    }

    private boolean isRefreshPending() {
        return this.mRequestedGeneration != this.mDisplayedGeneration;
    }

    public void onRangeChanged() {
        if (!isRefreshPending()) {
            updateRange();
            this.mAllowScrollHints = true;
        }
    }

    public void refresh() {
        this.mMissingPositions.clear();
        BackgroundCallback<T> backgroundCallback = this.mBackgroundProxy;
        int i = this.mRequestedGeneration + 1;
        this.mRequestedGeneration = i;
        backgroundCallback.refresh(i);
    }

    public T getItem(int i) {
        int position = i;
        int i2 = position;
        if (position >= 0 && position < this.mItemCount) {
            Object itemAt = this.mTileList.getItemAt(position);
            Object obj = itemAt;
            if (itemAt == null && !isRefreshPending()) {
                this.mMissingPositions.put(position, 0);
            }
            return obj;
        }
        throw new IndexOutOfBoundsException(position + " is not within 0 and " + this.mItemCount);
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    /* access modifiers changed from: 0000 */
    public void updateRange() {
        this.mViewCallback.getItemRangeInto(this.mTmpRange);
        if (this.mTmpRange[0] <= this.mTmpRange[1] && this.mTmpRange[0] >= 0 && this.mTmpRange[1] < this.mItemCount) {
            if (!this.mAllowScrollHints) {
                this.mScrollHint = 0;
            } else if (this.mTmpRange[0] > this.mPrevRange[1] || this.mPrevRange[0] > this.mTmpRange[1]) {
                this.mScrollHint = 0;
            } else if (this.mTmpRange[0] < this.mPrevRange[0]) {
                this.mScrollHint = 1;
            } else if (this.mTmpRange[0] > this.mPrevRange[0]) {
                this.mScrollHint = 2;
            }
            this.mPrevRange[0] = this.mTmpRange[0];
            this.mPrevRange[1] = this.mTmpRange[1];
            this.mViewCallback.extendRangeInto(this.mTmpRange, this.mTmpRangeExtended, this.mScrollHint);
            this.mTmpRangeExtended[0] = Math.min(this.mTmpRange[0], Math.max(this.mTmpRangeExtended[0], 0));
            int[] iArr = this.mTmpRangeExtended;
            int[] iArr2 = iArr;
            iArr2[1] = Math.max(this.mTmpRange[1], Math.min(this.mTmpRangeExtended[1], this.mItemCount - 1));
            this.mBackgroundProxy.updateRange(this.mTmpRange[0], this.mTmpRange[1], this.mTmpRangeExtended[0], this.mTmpRangeExtended[1], this.mScrollHint);
        }
    }
}
