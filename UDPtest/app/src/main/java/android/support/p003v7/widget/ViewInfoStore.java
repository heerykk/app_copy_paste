package android.support.p003v7.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p000v4.util.ArrayMap;
import android.support.p000v4.util.LongSparseArray;
import android.support.p000v4.util.Pools.Pool;
import android.support.p000v4.util.Pools.SimplePool;
import android.support.p003v7.widget.RecyclerView.ItemAnimator.ItemHolderInfo;
import android.support.p003v7.widget.RecyclerView.ViewHolder;

/* renamed from: android.support.v7.widget.ViewInfoStore */
class ViewInfoStore {
    private static final boolean DEBUG = false;
    @VisibleForTesting
    final ArrayMap<ViewHolder, InfoRecord> mLayoutHolderMap = new ArrayMap<>();
    @VisibleForTesting
    final LongSparseArray<ViewHolder> mOldChangedHolders = new LongSparseArray<>();

    /* renamed from: android.support.v7.widget.ViewInfoStore$InfoRecord */
    static class InfoRecord {
        static final int FLAG_APPEAR = 2;
        static final int FLAG_APPEAR_AND_DISAPPEAR = 3;
        static final int FLAG_APPEAR_PRE_AND_POST = 14;
        static final int FLAG_DISAPPEARED = 1;
        static final int FLAG_POST = 8;
        static final int FLAG_PRE = 4;
        static final int FLAG_PRE_AND_POST = 12;
        static Pool<InfoRecord> sPool = new SimplePool(20);
        int flags;
        @Nullable
        ItemHolderInfo postInfo;
        @Nullable
        ItemHolderInfo preInfo;

        private InfoRecord() {
        }

        static InfoRecord obtain() {
            InfoRecord infoRecord = (InfoRecord) sPool.acquire();
            return infoRecord != null ? infoRecord : new InfoRecord();
        }

        static void recycle(InfoRecord infoRecord) {
            InfoRecord record = infoRecord;
            InfoRecord infoRecord2 = record;
            record.flags = 0;
            record.preInfo = null;
            record.postInfo = null;
            boolean release = sPool.release(record);
        }

        static void drainCache() {
            do {
            } while (sPool.acquire() != null);
        }
    }

    /* renamed from: android.support.v7.widget.ViewInfoStore$ProcessCallback */
    interface ProcessCallback {
        void processAppeared(ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        void processDisappeared(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2);

        void processPersistent(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        void unused(ViewHolder viewHolder);
    }

    ViewInfoStore() {
    }

    /* access modifiers changed from: 0000 */
    public void clear() {
        this.mLayoutHolderMap.clear();
        this.mOldChangedHolders.clear();
    }

    /* access modifiers changed from: 0000 */
    public void addToPreLayout(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        ViewHolder holder = viewHolder;
        ItemHolderInfo info = itemHolderInfo;
        ViewHolder viewHolder2 = holder;
        ItemHolderInfo itemHolderInfo2 = info;
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(holder);
        InfoRecord record = infoRecord;
        if (infoRecord == null) {
            record = InfoRecord.obtain();
            Object put = this.mLayoutHolderMap.put(holder, record);
        }
        record.preInfo = info;
        record.flags |= 4;
    }

    /* access modifiers changed from: 0000 */
    public boolean isDisappearing(ViewHolder viewHolder) {
        ViewHolder holder = viewHolder;
        ViewHolder viewHolder2 = holder;
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(holder);
        return (infoRecord == null || (infoRecord.flags & 1) == 0) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public ItemHolderInfo popFromPreLayout(ViewHolder viewHolder) {
        ViewHolder vh = viewHolder;
        ViewHolder viewHolder2 = vh;
        return popFromLayoutStep(vh, 4);
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public ItemHolderInfo popFromPostLayout(ViewHolder viewHolder) {
        ViewHolder vh = viewHolder;
        ViewHolder viewHolder2 = vh;
        return popFromLayoutStep(vh, 8);
    }

    private ItemHolderInfo popFromLayoutStep(ViewHolder viewHolder, int i) {
        ItemHolderInfo info;
        ViewHolder vh = viewHolder;
        int flag = i;
        ViewHolder viewHolder2 = vh;
        int i2 = flag;
        int indexOfKey = this.mLayoutHolderMap.indexOfKey(vh);
        int index = indexOfKey;
        if (indexOfKey < 0) {
            return null;
        }
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.valueAt(index);
        InfoRecord record = infoRecord;
        if (infoRecord == null || (record.flags & flag) == 0) {
            return null;
        }
        record.flags &= flag ^ -1;
        if (flag == 4) {
            info = record.preInfo;
        } else if (flag != 8) {
            throw new IllegalArgumentException("Must provide flag PRE or POST");
        } else {
            info = record.postInfo;
        }
        if ((record.flags & 12) == 0) {
            Object removeAt = this.mLayoutHolderMap.removeAt(index);
            InfoRecord.recycle(record);
        }
        return info;
    }

    /* access modifiers changed from: 0000 */
    public void addToOldChangeHolders(long j, ViewHolder viewHolder) {
        long key = j;
        ViewHolder holder = viewHolder;
        long j2 = key;
        ViewHolder viewHolder2 = holder;
        this.mOldChangedHolders.put(key, holder);
    }

    /* access modifiers changed from: 0000 */
    public void addToAppearedInPreLayoutHolders(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        ViewHolder holder = viewHolder;
        ItemHolderInfo info = itemHolderInfo;
        ViewHolder viewHolder2 = holder;
        ItemHolderInfo itemHolderInfo2 = info;
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(holder);
        InfoRecord record = infoRecord;
        if (infoRecord == null) {
            record = InfoRecord.obtain();
            Object put = this.mLayoutHolderMap.put(holder, record);
        }
        record.flags |= 2;
        record.preInfo = info;
    }

    /* access modifiers changed from: 0000 */
    public boolean isInPreLayout(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(viewHolder2);
        return (infoRecord == null || (infoRecord.flags & 4) == 0) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public ViewHolder getFromOldChangeHolders(long j) {
        long key = j;
        long j2 = key;
        return (ViewHolder) this.mOldChangedHolders.get(key);
    }

    /* access modifiers changed from: 0000 */
    public void addToPostLayout(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        ViewHolder holder = viewHolder;
        ItemHolderInfo info = itemHolderInfo;
        ViewHolder viewHolder2 = holder;
        ItemHolderInfo itemHolderInfo2 = info;
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(holder);
        InfoRecord record = infoRecord;
        if (infoRecord == null) {
            record = InfoRecord.obtain();
            Object put = this.mLayoutHolderMap.put(holder, record);
        }
        record.postInfo = info;
        record.flags |= 8;
    }

    /* access modifiers changed from: 0000 */
    public void addToDisappearedInLayout(ViewHolder viewHolder) {
        ViewHolder holder = viewHolder;
        ViewHolder viewHolder2 = holder;
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(holder);
        InfoRecord record = infoRecord;
        if (infoRecord == null) {
            record = InfoRecord.obtain();
            Object put = this.mLayoutHolderMap.put(holder, record);
        }
        record.flags |= 1;
    }

    /* access modifiers changed from: 0000 */
    public void removeFromDisappearedInLayout(ViewHolder viewHolder) {
        ViewHolder holder = viewHolder;
        ViewHolder viewHolder2 = holder;
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.get(holder);
        InfoRecord record = infoRecord;
        if (infoRecord != null) {
            record.flags &= -2;
        }
    }

    /* access modifiers changed from: 0000 */
    public void process(ProcessCallback processCallback) {
        ProcessCallback callback = processCallback;
        ProcessCallback processCallback2 = callback;
        for (int index = this.mLayoutHolderMap.size() - 1; index >= 0; index--) {
            ViewHolder viewHolder = (ViewHolder) this.mLayoutHolderMap.keyAt(index);
            InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.removeAt(index);
            InfoRecord record = infoRecord;
            if ((infoRecord.flags & 3) == 3) {
                callback.unused(viewHolder);
            } else if ((record.flags & 1) == 0) {
                if ((record.flags & 14) == 14) {
                    callback.processAppeared(viewHolder, record.preInfo, record.postInfo);
                } else if ((record.flags & 12) == 12) {
                    callback.processPersistent(viewHolder, record.preInfo, record.postInfo);
                } else if ((record.flags & 4) != 0) {
                    callback.processDisappeared(viewHolder, record.preInfo, null);
                } else if ((record.flags & 8) != 0) {
                    callback.processAppeared(viewHolder, record.preInfo, record.postInfo);
                } else if ((record.flags & 2) == 0) {
                }
            } else if (record.preInfo != null) {
                callback.processDisappeared(viewHolder, record.preInfo, record.postInfo);
            } else {
                callback.unused(viewHolder);
            }
            InfoRecord.recycle(record);
        }
    }

    /* access modifiers changed from: 0000 */
    public void removeViewHolder(ViewHolder viewHolder) {
        ViewHolder holder = viewHolder;
        ViewHolder viewHolder2 = holder;
        int i = this.mOldChangedHolders.size() - 1;
        while (true) {
            if (i >= 0) {
                if (holder == this.mOldChangedHolders.valueAt(i)) {
                    this.mOldChangedHolders.removeAt(i);
                    break;
                }
                i--;
            } else {
                break;
            }
        }
        InfoRecord infoRecord = (InfoRecord) this.mLayoutHolderMap.remove(holder);
        InfoRecord info = infoRecord;
        if (infoRecord != null) {
            InfoRecord.recycle(info);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onDetach() {
        InfoRecord.drainCache();
    }

    public void onViewDetached(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        removeFromDisappearedInLayout(viewHolder2);
    }
}
