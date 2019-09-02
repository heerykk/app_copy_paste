package android.support.p003v7.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/* renamed from: android.support.v7.util.SortedList */
public class SortedList<T> {
    private static final int CAPACITY_GROWTH = 10;
    private static final int DELETION = 2;
    private static final int INSERTION = 1;
    public static final int INVALID_POSITION = -1;
    private static final int LOOKUP = 4;
    private static final int MIN_CAPACITY = 10;
    private BatchedCallback mBatchedCallback;
    private Callback mCallback;
    T[] mData;
    private int mMergedSize;
    private T[] mOldData;
    private int mOldDataSize;
    private int mOldDataStart;
    private int mSize;
    private final Class<T> mTClass;

    /* renamed from: android.support.v7.util.SortedList$BatchedCallback */
    public static class BatchedCallback<T2> extends Callback<T2> {
        private final BatchingListUpdateCallback mBatchingListUpdateCallback = new BatchingListUpdateCallback(this.mWrappedCallback);
        final Callback<T2> mWrappedCallback;

        public BatchedCallback(Callback<T2> callback) {
            Callback<T2> wrappedCallback = callback;
            Callback<T2> callback2 = wrappedCallback;
            this.mWrappedCallback = wrappedCallback;
        }

        public int compare(T2 t2, T2 t22) {
            T2 o1 = t2;
            T2 o2 = t22;
            T2 t23 = o1;
            T2 t24 = o2;
            return this.mWrappedCallback.compare(o1, o2);
        }

        public void onInserted(int i, int i2) {
            int position = i;
            int count = i2;
            int i3 = position;
            int i4 = count;
            this.mBatchingListUpdateCallback.onInserted(position, count);
        }

        public void onRemoved(int i, int i2) {
            int position = i;
            int count = i2;
            int i3 = position;
            int i4 = count;
            this.mBatchingListUpdateCallback.onRemoved(position, count);
        }

        public void onMoved(int i, int i2) {
            int fromPosition = i;
            int toPosition = i2;
            int i3 = fromPosition;
            int i4 = toPosition;
            this.mBatchingListUpdateCallback.onMoved(fromPosition, toPosition);
        }

        public void onChanged(int i, int i2) {
            int position = i;
            int count = i2;
            int i3 = position;
            int i4 = count;
            this.mBatchingListUpdateCallback.onChanged(position, count, null);
        }

        public boolean areContentsTheSame(T2 t2, T2 t22) {
            T2 oldItem = t2;
            T2 newItem = t22;
            T2 t23 = oldItem;
            T2 t24 = newItem;
            return this.mWrappedCallback.areContentsTheSame(oldItem, newItem);
        }

        public boolean areItemsTheSame(T2 t2, T2 t22) {
            T2 item1 = t2;
            T2 item2 = t22;
            T2 t23 = item1;
            T2 t24 = item2;
            return this.mWrappedCallback.areItemsTheSame(item1, item2);
        }

        public void dispatchLastEvent() {
            this.mBatchingListUpdateCallback.dispatchLastEvent();
        }
    }

    /* renamed from: android.support.v7.util.SortedList$Callback */
    public static abstract class Callback<T2> implements Comparator<T2>, ListUpdateCallback {
        public abstract boolean areContentsTheSame(T2 t2, T2 t22);

        public abstract boolean areItemsTheSame(T2 t2, T2 t22);

        public abstract int compare(T2 t2, T2 t22);

        public abstract void onChanged(int i, int i2);

        public Callback() {
        }

        public void onChanged(int i, int i2, Object obj) {
            int position = i;
            int count = i2;
            int i3 = position;
            int i4 = count;
            Object obj2 = obj;
            onChanged(position, count);
        }
    }

    public SortedList(Class<T> cls, Callback<T> callback) {
        Class<T> klass = cls;
        Callback<T> callback2 = callback;
        Class<T> cls2 = klass;
        Callback<T> callback3 = callback2;
        this(klass, callback2, 10);
    }

    public SortedList(Class<T> cls, Callback<T> callback, int i) {
        Class<T> klass = cls;
        Callback<T> callback2 = callback;
        int initialCapacity = i;
        Class<T> cls2 = klass;
        Callback<T> callback3 = callback2;
        int i2 = initialCapacity;
        this.mTClass = klass;
        this.mData = (Object[]) Array.newInstance(klass, initialCapacity);
        this.mCallback = callback2;
        this.mSize = 0;
    }

    public int size() {
        return this.mSize;
    }

    public int add(T t) {
        T item = t;
        T t2 = item;
        throwIfMerging();
        return add(item, true);
    }

    public void addAll(T[] tArr, boolean z) {
        T[] items = tArr;
        T[] tArr2 = items;
        boolean mayModifyInput = z;
        throwIfMerging();
        if (items.length != 0) {
            if (!mayModifyInput) {
                Object[] objArr = (Object[]) Array.newInstance(this.mTClass, items.length);
                System.arraycopy(items, 0, objArr, 0, items.length);
                addAllInternal(objArr);
            } else {
                addAllInternal(items);
            }
        }
    }

    public void addAll(T... tArr) {
        T[] items = tArr;
        T[] tArr2 = items;
        addAll(items, false);
    }

    public void addAll(Collection<T> collection) {
        Collection<T> items = collection;
        Collection<T> collection2 = items;
        addAll(items.toArray((Object[]) Array.newInstance(this.mTClass, items.size())), true);
    }

    private void addAllInternal(T[] tArr) {
        T[] newItems = tArr;
        T[] tArr2 = newItems;
        boolean forceBatchedUpdates = !(this.mCallback instanceof BatchedCallback);
        if (forceBatchedUpdates) {
            beginBatchedUpdates();
        }
        this.mOldData = this.mData;
        this.mOldDataStart = 0;
        this.mOldDataSize = this.mSize;
        Arrays.sort(newItems, this.mCallback);
        int newSize = deduplicate(newItems);
        if (this.mSize != 0) {
            merge(newItems, newSize);
        } else {
            this.mData = newItems;
            this.mSize = newSize;
            this.mMergedSize = newSize;
            this.mCallback.onInserted(0, newSize);
        }
        this.mOldData = null;
        if (forceBatchedUpdates) {
            endBatchedUpdates();
        }
    }

    private int deduplicate(T[] tArr) {
        T[] items = tArr;
        T[] tArr2 = items;
        if (items.length != 0) {
            int rangeStart = 0;
            int rangeEnd = 1;
            int i = 1;
            while (i < items.length) {
                T currentItem = items[i];
                int compare = this.mCallback.compare(items[rangeStart], currentItem);
                int compare2 = compare;
                if (compare <= 0) {
                    if (compare2 != 0) {
                        if (rangeEnd != i) {
                            items[rangeEnd] = currentItem;
                        }
                        int i2 = rangeEnd;
                        rangeEnd++;
                        rangeStart = i2;
                    } else {
                        int findSameItem = findSameItem(currentItem, items, rangeStart, rangeEnd);
                        int sameItemPos = findSameItem;
                        if (findSameItem == -1) {
                            if (rangeEnd != i) {
                                items[rangeEnd] = currentItem;
                            }
                            rangeEnd++;
                        } else {
                            items[sameItemPos] = currentItem;
                        }
                    }
                    i++;
                } else {
                    throw new IllegalArgumentException("Input must be sorted in ascending order.");
                }
            }
            return rangeEnd;
        }
        throw new IllegalArgumentException("Input array must be non-empty");
    }

    private int findSameItem(T t, T[] tArr, int i, int i2) {
        T item = t;
        T[] items = tArr;
        int from = i;
        int to = i2;
        T t2 = item;
        T[] tArr2 = items;
        int i3 = from;
        int i4 = to;
        for (int pos = from; pos < to; pos++) {
            if (this.mCallback.areItemsTheSame(items[pos], item)) {
                return pos;
            }
        }
        return -1;
    }

    private void merge(T[] tArr, int i) {
        T[] newData = tArr;
        int newDataSize = i;
        T[] tArr2 = newData;
        int i2 = newDataSize;
        int i3 = this.mSize + newDataSize + 10;
        int i4 = i3;
        this.mData = (Object[]) Array.newInstance(this.mTClass, i3);
        this.mMergedSize = 0;
        int newDataStart = 0;
        while (true) {
            if (this.mOldDataStart < this.mOldDataSize || newDataStart < newDataSize) {
                if (this.mOldDataStart == this.mOldDataSize) {
                    int i5 = newDataSize - newDataStart;
                    int i6 = i5;
                    System.arraycopy(newData, newDataStart, this.mData, this.mMergedSize, i5);
                    this.mMergedSize += i5;
                    this.mSize += i5;
                    this.mCallback.onInserted(this.mMergedSize - i5, i5);
                    return;
                } else if (newDataStart != newDataSize) {
                    T oldItem = this.mOldData[this.mOldDataStart];
                    T newItem = newData[newDataStart];
                    int compare = this.mCallback.compare(oldItem, newItem);
                    int compare2 = compare;
                    if (compare > 0) {
                        T[] tArr3 = this.mData;
                        int i7 = this.mMergedSize;
                        this.mMergedSize = i7 + 1;
                        tArr3[i7] = newItem;
                        this.mSize++;
                        newDataStart++;
                        this.mCallback.onInserted(this.mMergedSize - 1, 1);
                    } else if (compare2 == 0 && this.mCallback.areItemsTheSame(oldItem, newItem)) {
                        T[] tArr4 = this.mData;
                        int i8 = this.mMergedSize;
                        this.mMergedSize = i8 + 1;
                        tArr4[i8] = newItem;
                        newDataStart++;
                        this.mOldDataStart++;
                        if (!this.mCallback.areContentsTheSame(oldItem, newItem)) {
                            this.mCallback.onChanged(this.mMergedSize - 1, 1);
                        }
                    } else {
                        T[] tArr5 = this.mData;
                        int i9 = this.mMergedSize;
                        this.mMergedSize = i9 + 1;
                        tArr5[i9] = oldItem;
                        this.mOldDataStart++;
                    }
                } else {
                    int i10 = this.mOldDataSize - this.mOldDataStart;
                    int i11 = i10;
                    System.arraycopy(this.mOldData, this.mOldDataStart, this.mData, this.mMergedSize, i10);
                    this.mMergedSize += i10;
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void throwIfMerging() {
        if (this.mOldData != null) {
            throw new IllegalStateException("Cannot call this method from within addAll");
        }
    }

    public void beginBatchedUpdates() {
        throwIfMerging();
        if (!(this.mCallback instanceof BatchedCallback)) {
            if (this.mBatchedCallback == null) {
                this.mBatchedCallback = new BatchedCallback(this.mCallback);
            }
            this.mCallback = this.mBatchedCallback;
        }
    }

    public void endBatchedUpdates() {
        throwIfMerging();
        if (this.mCallback instanceof BatchedCallback) {
            ((BatchedCallback) this.mCallback).dispatchLastEvent();
        }
        if (this.mCallback == this.mBatchedCallback) {
            this.mCallback = this.mBatchedCallback.mWrappedCallback;
        }
    }

    private int add(T t, boolean z) {
        T item = t;
        T t2 = item;
        boolean notify = z;
        int findIndexOf = findIndexOf(item, this.mData, 0, this.mSize, 1);
        int index = findIndexOf;
        if (findIndexOf != -1) {
            if (index < this.mSize) {
                T existing = this.mData[index];
                if (this.mCallback.areItemsTheSame(existing, item)) {
                    if (!this.mCallback.areContentsTheSame(existing, item)) {
                        this.mData[index] = item;
                        this.mCallback.onChanged(index, 1);
                        return index;
                    }
                    this.mData[index] = item;
                    return index;
                }
            }
        } else {
            index = 0;
        }
        addToData(index, item);
        if (notify) {
            this.mCallback.onInserted(index, 1);
        }
        return index;
    }

    public boolean remove(T t) {
        T item = t;
        T t2 = item;
        throwIfMerging();
        return remove(item, true);
    }

    public T removeItemAt(int i) {
        int index = i;
        int i2 = index;
        throwIfMerging();
        Object obj = get(index);
        removeItemAtIndex(index, true);
        return obj;
    }

    private boolean remove(T t, boolean z) {
        T item = t;
        T t2 = item;
        boolean notify = z;
        int findIndexOf = findIndexOf(item, this.mData, 0, this.mSize, 2);
        int index = findIndexOf;
        if (findIndexOf == -1) {
            return false;
        }
        removeItemAtIndex(index, notify);
        return true;
    }

    private void removeItemAtIndex(int i, boolean z) {
        int index = i;
        int i2 = index;
        boolean notify = z;
        System.arraycopy(this.mData, index + 1, this.mData, index, (this.mSize - index) - 1);
        this.mSize--;
        this.mData[this.mSize] = null;
        if (notify) {
            this.mCallback.onRemoved(index, 1);
        }
    }

    public void updateItemAt(int i, T t) {
        int index = i;
        T item = t;
        int i2 = index;
        T t2 = item;
        throwIfMerging();
        T t3 = get(index);
        T existing = t3;
        boolean contentsChanged = t3 == item || !this.mCallback.areContentsTheSame(existing, item);
        if (existing != item) {
            int compare = this.mCallback.compare(existing, item);
            int i3 = compare;
            if (compare == 0) {
                this.mData[index] = item;
                if (contentsChanged) {
                    this.mCallback.onChanged(index, 1);
                }
                return;
            }
        }
        if (contentsChanged) {
            this.mCallback.onChanged(index, 1);
        }
        removeItemAtIndex(index, false);
        int newIndex = add(item, false);
        if (index != newIndex) {
            this.mCallback.onMoved(index, newIndex);
        }
    }

    public void recalculatePositionOfItemAt(int i) {
        int index = i;
        int i2 = index;
        throwIfMerging();
        Object obj = get(index);
        removeItemAtIndex(index, false);
        int newIndex = add(obj, false);
        if (index != newIndex) {
            this.mCallback.onMoved(index, newIndex);
        }
    }

    public T get(int i) throws IndexOutOfBoundsException {
        int index = i;
        int i2 = index;
        if (index >= this.mSize || index < 0) {
            throw new IndexOutOfBoundsException("Asked to get item at " + index + " but size is " + this.mSize);
        } else if (this.mOldData == null || index < this.mMergedSize) {
            return this.mData[index];
        } else {
            return this.mOldData[(index - this.mMergedSize) + this.mOldDataStart];
        }
    }

    public int indexOf(T t) {
        T item = t;
        T t2 = item;
        if (this.mOldData == null) {
            return findIndexOf(item, this.mData, 0, this.mSize, 4);
        }
        int findIndexOf = findIndexOf(item, this.mData, 0, this.mMergedSize, 4);
        int index = findIndexOf;
        if (findIndexOf != -1) {
            return index;
        }
        int findIndexOf2 = findIndexOf(item, this.mOldData, this.mOldDataStart, this.mOldDataSize, 4);
        int index2 = findIndexOf2;
        if (findIndexOf2 == -1) {
            return -1;
        }
        return (index2 - this.mOldDataStart) + this.mMergedSize;
    }

    private int findIndexOf(T t, T[] tArr, int i, int i2, int i3) {
        int i4;
        T item = t;
        T[] mData2 = tArr;
        int reason = i3;
        T t2 = item;
        T[] tArr2 = mData2;
        int left = i;
        int right = i2;
        int i5 = reason;
        while (left < right) {
            int i6 = (left + right) / 2;
            int middle = i6;
            T myItem = mData2[i6];
            int compare = this.mCallback.compare(myItem, item);
            int cmp = compare;
            if (compare < 0) {
                left = middle + 1;
            } else if (cmp != 0) {
                right = middle;
            } else if (this.mCallback.areItemsTheSame(myItem, item)) {
                return middle;
            } else {
                int exact = linearEqualitySearch(item, middle, left, right);
                if (reason != 1) {
                    return exact;
                }
                return exact != -1 ? exact : middle;
            }
        }
        if (reason != 1) {
            i4 = -1;
        } else {
            i4 = left;
        }
        return i4;
    }

    private int linearEqualitySearch(T t, int i, int i2, int i3) {
        T item = t;
        int middle = i;
        int left = i2;
        int right = i3;
        T t2 = item;
        int i4 = middle;
        int i5 = left;
        int i6 = right;
        int next = middle - 1;
        while (next >= left) {
            T nextItem = this.mData[next];
            int compare = this.mCallback.compare(nextItem, item);
            int i7 = compare;
            if (compare != 0) {
                break;
            } else if (this.mCallback.areItemsTheSame(nextItem, item)) {
                return next;
            } else {
                next--;
            }
        }
        int next2 = middle + 1;
        while (next2 < right) {
            T nextItem2 = this.mData[next2];
            int compare2 = this.mCallback.compare(nextItem2, item);
            int i8 = compare2;
            if (compare2 != 0) {
                break;
            } else if (this.mCallback.areItemsTheSame(nextItem2, item)) {
                return next2;
            } else {
                next2++;
            }
        }
        return -1;
    }

    private void addToData(int i, T t) {
        int index = i;
        T item = t;
        int i2 = index;
        T t2 = item;
        if (index <= this.mSize) {
            if (this.mSize != this.mData.length) {
                System.arraycopy(this.mData, index, this.mData, index + 1, this.mSize - index);
                this.mData[index] = item;
            } else {
                Object[] objArr = (Object[]) Array.newInstance(this.mTClass, this.mData.length + 10);
                System.arraycopy(this.mData, 0, objArr, 0, index);
                objArr[index] = item;
                System.arraycopy(this.mData, index, objArr, index + 1, this.mSize - index);
                this.mData = objArr;
            }
            this.mSize++;
            return;
        }
        throw new IndexOutOfBoundsException("cannot add item to " + index + " because size is " + this.mSize);
    }

    public void clear() {
        throwIfMerging();
        if (this.mSize != 0) {
            int i = this.mSize;
            int i2 = i;
            Arrays.fill(this.mData, 0, i, null);
            this.mSize = 0;
            this.mCallback.onRemoved(0, i);
        }
    }
}
