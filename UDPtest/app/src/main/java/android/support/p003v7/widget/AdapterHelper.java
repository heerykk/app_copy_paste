package android.support.p003v7.widget;

import android.support.p000v4.util.Pools.Pool;
import android.support.p000v4.util.Pools.SimplePool;
import android.support.p003v7.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: android.support.v7.widget.AdapterHelper */
class AdapterHelper implements Callback {
    private static final boolean DEBUG = false;
    static final int POSITION_TYPE_INVISIBLE = 0;
    static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
    private static final String TAG = "AHT";
    final Callback mCallback;
    final boolean mDisableRecycler;
    private int mExistingUpdateTypes;
    Runnable mOnItemProcessedCallback;
    final OpReorderer mOpReorderer;
    final ArrayList<UpdateOp> mPendingUpdates;
    final ArrayList<UpdateOp> mPostponedList;
    private Pool<UpdateOp> mUpdateOpPool;

    /* renamed from: android.support.v7.widget.AdapterHelper$Callback */
    interface Callback {
        ViewHolder findViewHolder(int i);

        void markViewHoldersUpdated(int i, int i2, Object obj);

        void offsetPositionsForAdd(int i, int i2);

        void offsetPositionsForMove(int i, int i2);

        void offsetPositionsForRemovingInvisible(int i, int i2);

        void offsetPositionsForRemovingLaidOutOrNewView(int i, int i2);

        void onDispatchFirstPass(UpdateOp updateOp);

        void onDispatchSecondPass(UpdateOp updateOp);
    }

    /* renamed from: android.support.v7.widget.AdapterHelper$UpdateOp */
    static class UpdateOp {
        static final int ADD = 1;
        static final int MOVE = 8;
        static final int POOL_SIZE = 30;
        static final int REMOVE = 2;
        static final int UPDATE = 4;
        int cmd;
        int itemCount;
        Object payload;
        int positionStart;

        UpdateOp(int i, int i2, int i3, Object obj) {
            int cmd2 = i;
            int positionStart2 = i2;
            int itemCount2 = i3;
            Object payload2 = obj;
            int i4 = cmd2;
            int i5 = positionStart2;
            int i6 = itemCount2;
            Object obj2 = payload2;
            this.cmd = cmd2;
            this.positionStart = positionStart2;
            this.itemCount = itemCount2;
            this.payload = payload2;
        }

        /* access modifiers changed from: 0000 */
        public String cmdToString() {
            switch (this.cmd) {
                case 1:
                    return "add";
                case 2:
                    return "rm";
                case 4:
                    return "up";
                case 8:
                    return "mv";
                default:
                    return "??";
            }
        }

        public String toString() {
            return Integer.toHexString(System.identityHashCode(this)) + "[" + cmdToString() + ",s:" + this.positionStart + "c:" + this.itemCount + ",p:" + this.payload + "]";
        }

        public boolean equals(Object obj) {
            Object o = obj;
            Object obj2 = o;
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            UpdateOp op = (UpdateOp) o;
            if (this.cmd != op.cmd) {
                return false;
            }
            if (this.cmd == 8 && Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == op.positionStart && this.positionStart == op.itemCount) {
                return true;
            }
            if (this.itemCount != op.itemCount) {
                return false;
            }
            if (this.positionStart != op.positionStart) {
                return false;
            }
            if (this.payload == null) {
                if (op.payload != null) {
                    return false;
                }
            } else if (!this.payload.equals(op.payload)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = (31 * ((31 * this.cmd) + this.positionStart)) + this.itemCount;
            int result = i;
            return i;
        }
    }

    AdapterHelper(Callback callback, boolean z) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        boolean disableRecycler = z;
        this.mUpdateOpPool = new SimplePool(30);
        this.mPendingUpdates = new ArrayList<>();
        this.mPostponedList = new ArrayList<>();
        this.mExistingUpdateTypes = 0;
        this.mCallback = callback2;
        this.mDisableRecycler = disableRecycler;
        this.mOpReorderer = new OpReorderer(this);
    }

    AdapterHelper(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        this(callback2, false);
    }

    /* access modifiers changed from: 0000 */
    public AdapterHelper addUpdateOp(UpdateOp... updateOpArr) {
        UpdateOp[] ops = updateOpArr;
        UpdateOp[] updateOpArr2 = ops;
        boolean addAll = Collections.addAll(this.mPendingUpdates, ops);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void reset() {
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
        recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    /* access modifiers changed from: 0000 */
    public void preProcess() {
        this.mOpReorderer.reorderOps(this.mPendingUpdates);
        int count = this.mPendingUpdates.size();
        for (int i = 0; i < count; i++) {
            UpdateOp updateOp = (UpdateOp) this.mPendingUpdates.get(i);
            UpdateOp op = updateOp;
            switch (updateOp.cmd) {
                case 1:
                    applyAdd(op);
                    break;
                case 2:
                    applyRemove(op);
                    break;
                case 4:
                    applyUpdate(op);
                    break;
                case 8:
                    applyMove(op);
                    break;
            }
            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
        }
        this.mPendingUpdates.clear();
    }

    /* access modifiers changed from: 0000 */
    public void consumePostponedUpdates() {
        int count = this.mPostponedList.size();
        for (int i = 0; i < count; i++) {
            this.mCallback.onDispatchSecondPass((UpdateOp) this.mPostponedList.get(i));
        }
        recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    private void applyMove(UpdateOp updateOp) {
        UpdateOp op = updateOp;
        UpdateOp updateOp2 = op;
        postponeAndUpdateViewHolders(op);
    }

    private void applyRemove(UpdateOp updateOp) {
        UpdateOp op = updateOp;
        UpdateOp op2 = op;
        int tmpStart = op.positionStart;
        int tmpCount = 0;
        int tmpEnd = op.positionStart + op.itemCount;
        int type = -1;
        int position = op.positionStart;
        while (position < tmpEnd) {
            boolean typeChanged = false;
            ViewHolder findViewHolder = this.mCallback.findViewHolder(position);
            ViewHolder viewHolder = findViewHolder;
            if (findViewHolder == null && !canFindInPreLayout(position)) {
                if (type == 1) {
                    postponeAndUpdateViewHolders(obtainUpdateOp(2, tmpStart, tmpCount, null));
                    typeChanged = true;
                }
                type = 0;
            } else {
                if (type == 0) {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(2, tmpStart, tmpCount, null));
                    typeChanged = true;
                }
                type = 1;
            }
            if (!typeChanged) {
                tmpCount++;
            } else {
                position -= tmpCount;
                tmpEnd -= tmpCount;
                tmpCount = 1;
            }
            position++;
        }
        if (tmpCount != op.itemCount) {
            recycleUpdateOp(op);
            op2 = obtainUpdateOp(2, tmpStart, tmpCount, null);
        }
        if (type != 0) {
            postponeAndUpdateViewHolders(op2);
        } else {
            dispatchAndUpdateViewHolders(op2);
        }
    }

    private void applyUpdate(UpdateOp updateOp) {
        UpdateOp op = updateOp;
        UpdateOp op2 = op;
        int tmpStart = op.positionStart;
        int tmpCount = 0;
        int tmpEnd = op.positionStart + op.itemCount;
        int type = -1;
        for (int position = op.positionStart; position < tmpEnd; position++) {
            ViewHolder findViewHolder = this.mCallback.findViewHolder(position);
            ViewHolder viewHolder = findViewHolder;
            if (findViewHolder == null && !canFindInPreLayout(position)) {
                if (type == 1) {
                    postponeAndUpdateViewHolders(obtainUpdateOp(4, tmpStart, tmpCount, op.payload));
                    tmpCount = 0;
                    tmpStart = position;
                }
                type = 0;
            } else {
                if (type == 0) {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(4, tmpStart, tmpCount, op.payload));
                    tmpCount = 0;
                    tmpStart = position;
                }
                type = 1;
            }
            tmpCount++;
        }
        if (tmpCount != op.itemCount) {
            Object payload = op.payload;
            recycleUpdateOp(op);
            op2 = obtainUpdateOp(4, tmpStart, tmpCount, payload);
        }
        if (type != 0) {
            postponeAndUpdateViewHolders(op2);
        } else {
            dispatchAndUpdateViewHolders(op2);
        }
    }

    private void dispatchAndUpdateViewHolders(UpdateOp updateOp) {
        int positionMultiplier;
        boolean z;
        UpdateOp op = updateOp;
        UpdateOp updateOp2 = op;
        if (op.cmd == 1 || op.cmd == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int tmpStart = updatePositionWithPostponed(op.positionStart, op.cmd);
        int tmpCnt = 1;
        int offsetPositionForPartial = op.positionStart;
        switch (op.cmd) {
            case 2:
                positionMultiplier = 0;
                break;
            case 4:
                positionMultiplier = 1;
                break;
            default:
                throw new IllegalArgumentException("op should be remove or update." + op);
        }
        for (int p = 1; p < op.itemCount; p++) {
            int i = op.positionStart + (positionMultiplier * p);
            int i2 = i;
            int updatedPos = updatePositionWithPostponed(i, op.cmd);
            boolean continuous = false;
            switch (op.cmd) {
                case 2:
                    if (updatedPos != tmpStart) {
                        z = false;
                    } else {
                        z = true;
                    }
                    continuous = z;
                    break;
                case 4:
                    continuous = updatedPos == tmpStart + 1;
                    break;
            }
            if (!continuous) {
                UpdateOp tmp = obtainUpdateOp(op.cmd, tmpStart, tmpCnt, op.payload);
                dispatchFirstPassAndUpdateViewHolders(tmp, offsetPositionForPartial);
                recycleUpdateOp(tmp);
                if (op.cmd == 4) {
                    offsetPositionForPartial += tmpCnt;
                }
                tmpStart = updatedPos;
                tmpCnt = 1;
            } else {
                tmpCnt++;
            }
        }
        Object payload = op.payload;
        recycleUpdateOp(op);
        if (tmpCnt > 0) {
            UpdateOp tmp2 = obtainUpdateOp(op.cmd, tmpStart, tmpCnt, payload);
            dispatchFirstPassAndUpdateViewHolders(tmp2, offsetPositionForPartial);
            recycleUpdateOp(tmp2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchFirstPassAndUpdateViewHolders(UpdateOp updateOp, int i) {
        UpdateOp op = updateOp;
        int offsetStart = i;
        UpdateOp updateOp2 = op;
        int i2 = offsetStart;
        this.mCallback.onDispatchFirstPass(op);
        switch (op.cmd) {
            case 2:
                this.mCallback.offsetPositionsForRemovingInvisible(offsetStart, op.itemCount);
                return;
            case 4:
                this.mCallback.markViewHoldersUpdated(offsetStart, op.itemCount, op.payload);
                return;
            default:
                throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        }
    }

    private int updatePositionWithPostponed(int i, int i2) {
        int start;
        int end;
        int cmd = i2;
        int pos = i;
        int i3 = cmd;
        int size = this.mPostponedList.size();
        int i4 = size;
        for (int i5 = size - 1; i5 >= 0; i5--) {
            UpdateOp updateOp = (UpdateOp) this.mPostponedList.get(i5);
            UpdateOp postponed = updateOp;
            if (updateOp.cmd == 8) {
                if (postponed.positionStart >= postponed.itemCount) {
                    start = postponed.itemCount;
                    end = postponed.positionStart;
                } else {
                    start = postponed.positionStart;
                    end = postponed.itemCount;
                }
                if (pos >= start && pos <= end) {
                    if (start != postponed.positionStart) {
                        if (cmd == 1) {
                            postponed.positionStart++;
                        } else if (cmd == 2) {
                            postponed.positionStart--;
                        }
                        pos--;
                    } else {
                        if (cmd == 1) {
                            postponed.itemCount++;
                        } else if (cmd == 2) {
                            postponed.itemCount--;
                        }
                        pos++;
                    }
                } else if (pos < postponed.positionStart) {
                    if (cmd == 1) {
                        postponed.positionStart++;
                        postponed.itemCount++;
                    } else if (cmd == 2) {
                        postponed.positionStart--;
                        postponed.itemCount--;
                    }
                }
            } else if (postponed.positionStart > pos) {
                if (cmd == 1) {
                    postponed.positionStart++;
                } else if (cmd == 2) {
                    postponed.positionStart--;
                }
            } else if (postponed.cmd == 1) {
                pos -= postponed.itemCount;
            } else if (postponed.cmd == 2) {
                pos += postponed.itemCount;
            }
        }
        for (int i6 = this.mPostponedList.size() - 1; i6 >= 0; i6--) {
            UpdateOp updateOp2 = (UpdateOp) this.mPostponedList.get(i6);
            UpdateOp op = updateOp2;
            if (updateOp2.cmd != 8) {
                if (op.itemCount <= 0) {
                    Object remove = this.mPostponedList.remove(i6);
                    recycleUpdateOp(op);
                }
            } else if (op.itemCount == op.positionStart || op.itemCount < 0) {
                Object remove2 = this.mPostponedList.remove(i6);
                recycleUpdateOp(op);
            }
        }
        return pos;
    }

    private boolean canFindInPreLayout(int i) {
        int position = i;
        int i2 = position;
        int count = this.mPostponedList.size();
        for (int i3 = 0; i3 < count; i3++) {
            UpdateOp updateOp = (UpdateOp) this.mPostponedList.get(i3);
            UpdateOp op = updateOp;
            if (updateOp.cmd != 8) {
                if (op.cmd != 1) {
                    continue;
                } else {
                    int end = op.positionStart + op.itemCount;
                    for (int pos = op.positionStart; pos < end; pos++) {
                        if (findPositionOffset(pos, i3 + 1) == position) {
                            return true;
                        }
                    }
                    continue;
                }
            } else if (findPositionOffset(op.itemCount, i3 + 1) == position) {
                return true;
            }
        }
        return false;
    }

    private void applyAdd(UpdateOp updateOp) {
        UpdateOp op = updateOp;
        UpdateOp updateOp2 = op;
        postponeAndUpdateViewHolders(op);
    }

    private void postponeAndUpdateViewHolders(UpdateOp updateOp) {
        UpdateOp op = updateOp;
        UpdateOp updateOp2 = op;
        boolean add = this.mPostponedList.add(op);
        switch (op.cmd) {
            case 1:
                this.mCallback.offsetPositionsForAdd(op.positionStart, op.itemCount);
                return;
            case 2:
                this.mCallback.offsetPositionsForRemovingLaidOutOrNewView(op.positionStart, op.itemCount);
                return;
            case 4:
                this.mCallback.markViewHoldersUpdated(op.positionStart, op.itemCount, op.payload);
                return;
            case 8:
                this.mCallback.offsetPositionsForMove(op.positionStart, op.itemCount);
                return;
            default:
                throw new IllegalArgumentException("Unknown update op type for " + op);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean hasPendingUpdates() {
        return this.mPendingUpdates.size() > 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasAnyUpdateTypes(int i) {
        int updateTypes = i;
        int i2 = updateTypes;
        return (this.mExistingUpdateTypes & updateTypes) != 0;
    }

    /* access modifiers changed from: 0000 */
    public int findPositionOffset(int i) {
        int position = i;
        int i2 = position;
        return findPositionOffset(position, 0);
    }

    /* access modifiers changed from: 0000 */
    public int findPositionOffset(int i, int i2) {
        int firstPostponedItem = i2;
        int position = i;
        int i3 = firstPostponedItem;
        int count = this.mPostponedList.size();
        for (int i4 = firstPostponedItem; i4 < count; i4++) {
            UpdateOp updateOp = (UpdateOp) this.mPostponedList.get(i4);
            UpdateOp op = updateOp;
            if (updateOp.cmd != 8) {
                if (op.positionStart > position) {
                    continue;
                } else if (op.cmd != 2) {
                    if (op.cmd == 1) {
                        position += op.itemCount;
                    }
                } else if (position < op.positionStart + op.itemCount) {
                    return -1;
                } else {
                    position -= op.itemCount;
                }
            } else if (op.positionStart != position) {
                if (op.positionStart < position) {
                    position--;
                }
                if (op.itemCount <= position) {
                    position++;
                }
            } else {
                position = op.itemCount;
            }
        }
        return position;
    }

    /* access modifiers changed from: 0000 */
    public boolean onItemRangeChanged(int i, int i2, Object obj) {
        int positionStart = i;
        int itemCount = i2;
        Object payload = obj;
        int i3 = positionStart;
        int i4 = itemCount;
        Object obj2 = payload;
        if (itemCount < 1) {
            return false;
        }
        boolean add = this.mPendingUpdates.add(obtainUpdateOp(4, positionStart, itemCount, payload));
        this.mExistingUpdateTypes |= 4;
        return this.mPendingUpdates.size() == 1;
    }

    /* access modifiers changed from: 0000 */
    public boolean onItemRangeInserted(int i, int i2) {
        int positionStart = i;
        int itemCount = i2;
        int i3 = positionStart;
        int i4 = itemCount;
        if (itemCount < 1) {
            return false;
        }
        boolean add = this.mPendingUpdates.add(obtainUpdateOp(1, positionStart, itemCount, null));
        this.mExistingUpdateTypes |= 1;
        return this.mPendingUpdates.size() == 1;
    }

    /* access modifiers changed from: 0000 */
    public boolean onItemRangeRemoved(int i, int i2) {
        int positionStart = i;
        int itemCount = i2;
        int i3 = positionStart;
        int i4 = itemCount;
        if (itemCount < 1) {
            return false;
        }
        boolean add = this.mPendingUpdates.add(obtainUpdateOp(2, positionStart, itemCount, null));
        this.mExistingUpdateTypes |= 2;
        return this.mPendingUpdates.size() == 1;
    }

    /* access modifiers changed from: 0000 */
    public boolean onItemRangeMoved(int i, int i2, int i3) {
        boolean z;
        int from = i;
        int to = i2;
        int itemCount = i3;
        int i4 = from;
        int i5 = to;
        int i6 = itemCount;
        if (from == to) {
            return false;
        }
        if (itemCount == 1) {
            boolean add = this.mPendingUpdates.add(obtainUpdateOp(8, from, to, null));
            this.mExistingUpdateTypes |= 8;
            if (this.mPendingUpdates.size() != 1) {
                z = false;
            } else {
                z = true;
            }
            return z;
        }
        throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
    }

    /* access modifiers changed from: 0000 */
    public void consumeUpdatesInOnePass() {
        consumePostponedUpdates();
        int count = this.mPendingUpdates.size();
        for (int i = 0; i < count; i++) {
            UpdateOp updateOp = (UpdateOp) this.mPendingUpdates.get(i);
            UpdateOp op = updateOp;
            switch (updateOp.cmd) {
                case 1:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.offsetPositionsForAdd(op.positionStart, op.itemCount);
                    break;
                case 2:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.offsetPositionsForRemovingInvisible(op.positionStart, op.itemCount);
                    break;
                case 4:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.markViewHoldersUpdated(op.positionStart, op.itemCount, op.payload);
                    break;
                case 8:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.offsetPositionsForMove(op.positionStart, op.itemCount);
                    break;
            }
            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
        }
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.mExistingUpdateTypes = 0;
    }

    public int applyPendingUpdatesToPosition(int i) {
        int position = i;
        int size = this.mPendingUpdates.size();
        for (int i2 = 0; i2 < size; i2++) {
            UpdateOp updateOp = (UpdateOp) this.mPendingUpdates.get(i2);
            UpdateOp op = updateOp;
            switch (updateOp.cmd) {
                case 1:
                    if (op.positionStart <= position) {
                        position += op.itemCount;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (op.positionStart > position) {
                        continue;
                    } else {
                        int i3 = op.positionStart + op.itemCount;
                        int i4 = i3;
                        if (i3 <= position) {
                            position -= op.itemCount;
                            break;
                        } else {
                            return -1;
                        }
                    }
                case 8:
                    if (op.positionStart == position) {
                        position = op.itemCount;
                        break;
                    } else {
                        if (op.positionStart < position) {
                            position--;
                        }
                        if (op.itemCount <= position) {
                            position++;
                            break;
                        } else {
                            break;
                        }
                    }
            }
        }
        return position;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasUpdates() {
        return !this.mPostponedList.isEmpty() && !this.mPendingUpdates.isEmpty();
    }

    public UpdateOp obtainUpdateOp(int i, int i2, int i3, Object obj) {
        int cmd = i;
        int positionStart = i2;
        int itemCount = i3;
        Object payload = obj;
        int i4 = cmd;
        int i5 = positionStart;
        int i6 = itemCount;
        Object obj2 = payload;
        UpdateOp updateOp = (UpdateOp) this.mUpdateOpPool.acquire();
        UpdateOp op = updateOp;
        if (updateOp != null) {
            op.cmd = cmd;
            op.positionStart = positionStart;
            op.itemCount = itemCount;
            op.payload = payload;
        } else {
            op = new UpdateOp(cmd, positionStart, itemCount, payload);
        }
        return op;
    }

    public void recycleUpdateOp(UpdateOp updateOp) {
        UpdateOp op = updateOp;
        UpdateOp updateOp2 = op;
        if (!this.mDisableRecycler) {
            op.payload = null;
            boolean release = this.mUpdateOpPool.release(op);
        }
    }

    /* access modifiers changed from: 0000 */
    public void recycleUpdateOpsAndClearList(List<UpdateOp> list) {
        List<UpdateOp> ops = list;
        List<UpdateOp> list2 = ops;
        int count = ops.size();
        for (int i = 0; i < count; i++) {
            recycleUpdateOp((UpdateOp) ops.get(i));
        }
        ops.clear();
    }
}
