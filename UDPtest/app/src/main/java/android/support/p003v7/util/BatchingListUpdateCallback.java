package android.support.p003v7.util;

/* renamed from: android.support.v7.util.BatchingListUpdateCallback */
public class BatchingListUpdateCallback implements ListUpdateCallback {
    private static final int TYPE_ADD = 1;
    private static final int TYPE_CHANGE = 3;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_REMOVE = 2;
    int mLastEventCount = -1;
    Object mLastEventPayload = null;
    int mLastEventPosition = -1;
    int mLastEventType = 0;
    final ListUpdateCallback mWrapped;

    public BatchingListUpdateCallback(ListUpdateCallback listUpdateCallback) {
        ListUpdateCallback callback = listUpdateCallback;
        ListUpdateCallback listUpdateCallback2 = callback;
        this.mWrapped = callback;
    }

    public void dispatchLastEvent() {
        if (this.mLastEventType != 0) {
            switch (this.mLastEventType) {
                case 1:
                    this.mWrapped.onInserted(this.mLastEventPosition, this.mLastEventCount);
                    break;
                case 2:
                    this.mWrapped.onRemoved(this.mLastEventPosition, this.mLastEventCount);
                    break;
                case 3:
                    this.mWrapped.onChanged(this.mLastEventPosition, this.mLastEventCount, this.mLastEventPayload);
                    break;
            }
            this.mLastEventPayload = null;
            this.mLastEventType = 0;
        }
    }

    public void onInserted(int i, int i2) {
        int position = i;
        int count = i2;
        int i3 = position;
        int i4 = count;
        if (this.mLastEventType == 1 && position >= this.mLastEventPosition && position <= this.mLastEventPosition + this.mLastEventCount) {
            this.mLastEventCount += count;
            this.mLastEventPosition = Math.min(position, this.mLastEventPosition);
            return;
        }
        dispatchLastEvent();
        this.mLastEventPosition = position;
        this.mLastEventCount = count;
        this.mLastEventType = 1;
    }

    public void onRemoved(int i, int i2) {
        int position = i;
        int count = i2;
        int i3 = position;
        int i4 = count;
        if (this.mLastEventType == 2 && this.mLastEventPosition >= position && this.mLastEventPosition <= position + count) {
            this.mLastEventCount += count;
            this.mLastEventPosition = position;
            return;
        }
        dispatchLastEvent();
        this.mLastEventPosition = position;
        this.mLastEventCount = count;
        this.mLastEventType = 2;
    }

    public void onMoved(int i, int i2) {
        int fromPosition = i;
        int toPosition = i2;
        int i3 = fromPosition;
        int i4 = toPosition;
        dispatchLastEvent();
        this.mWrapped.onMoved(fromPosition, toPosition);
    }

    public void onChanged(int i, int i2, Object obj) {
        int position = i;
        int count = i2;
        Object payload = obj;
        int i3 = position;
        int i4 = count;
        Object obj2 = payload;
        if (this.mLastEventType == 3 && position <= this.mLastEventPosition + this.mLastEventCount && position + count >= this.mLastEventPosition && this.mLastEventPayload == payload) {
            int i5 = this.mLastEventPosition + this.mLastEventCount;
            int i6 = i5;
            this.mLastEventPosition = Math.min(position, this.mLastEventPosition);
            this.mLastEventCount = Math.max(i5, position + count) - this.mLastEventPosition;
            return;
        }
        dispatchLastEvent();
        this.mLastEventPosition = position;
        this.mLastEventCount = count;
        this.mLastEventPayload = payload;
        this.mLastEventType = 3;
    }
}
