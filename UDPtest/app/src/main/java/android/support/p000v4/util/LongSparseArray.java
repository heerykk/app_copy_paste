package android.support.p000v4.util;

/* renamed from: android.support.v4.util.LongSparseArray */
public class LongSparseArray<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private long[] mKeys;
    private int mSize;
    private Object[] mValues;

    public LongSparseArray(int i) {
        int initialCapacity = i;
        int i2 = initialCapacity;
        this.mGarbage = false;
        if (initialCapacity != 0) {
            int idealLongArraySize = ContainerHelpers.idealLongArraySize(initialCapacity);
            int initialCapacity2 = idealLongArraySize;
            this.mKeys = new long[idealLongArraySize];
            this.mValues = new Object[idealLongArraySize];
        } else {
            this.mKeys = ContainerHelpers.EMPTY_LONGS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
        }
        this.mSize = 0;
    }

    public LongSparseArray() {
        this(10);
    }

    public LongSparseArray<E> clone() {
        LongSparseArray longSparseArray = null;
        try {
            LongSparseArray longSparseArray2 = (LongSparseArray) super.clone();
            longSparseArray = longSparseArray2;
            longSparseArray2.mKeys = (long[]) this.mKeys.clone();
            longSparseArray.mValues = (Object[]) this.mValues.clone();
        } catch (CloneNotSupportedException e) {
        }
        return longSparseArray;
    }

    public E get(long j) {
        long key = j;
        long j2 = key;
        return get(key, null);
    }

    public E get(long j, E e) {
        long key = j;
        E valueIfKeyNotFound = e;
        long j2 = key;
        E e2 = valueIfKeyNotFound;
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        int i = binarySearch;
        if (binarySearch >= 0 && this.mValues[i] != DELETED) {
            return this.mValues[i];
        }
        return valueIfKeyNotFound;
    }

    public void delete(long j) {
        long key = j;
        long j2 = key;
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        int i = binarySearch;
        if (binarySearch >= 0 && this.mValues[i] != DELETED) {
            this.mValues[i] = DELETED;
            this.mGarbage = true;
        }
    }

    public void remove(long j) {
        long key = j;
        long j2 = key;
        delete(key);
    }

    public void removeAt(int i) {
        int index = i;
        int i2 = index;
        if (this.mValues[index] != DELETED) {
            this.mValues[index] = DELETED;
            this.mGarbage = true;
        }
    }

    /* renamed from: gc */
    private void m12gc() {
        int n = this.mSize;
        int o = 0;
        long[] keys = this.mKeys;
        Object[] values = this.mValues;
        for (int i = 0; i < n; i++) {
            Object obj = values[i];
            Object val = obj;
            if (obj != DELETED) {
                if (i != o) {
                    keys[o] = keys[i];
                    values[o] = val;
                    values[i] = null;
                }
                o++;
            }
        }
        this.mGarbage = false;
        this.mSize = o;
    }

    public void put(long j, E e) {
        long key = j;
        E value = e;
        long j2 = key;
        E e2 = value;
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        int i = binarySearch;
        if (binarySearch < 0) {
            int i2 = i ^ -1;
            int i3 = i2;
            if (i2 < this.mSize && this.mValues[i3] == DELETED) {
                this.mKeys[i3] = key;
                this.mValues[i3] = value;
                return;
            }
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                m12gc();
                i3 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key) ^ -1;
            }
            if (this.mSize >= this.mKeys.length) {
                int idealLongArraySize = ContainerHelpers.idealLongArraySize(this.mSize + 1);
                long[] nkeys = new long[idealLongArraySize];
                Object[] nvalues = new Object[idealLongArraySize];
                System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
                System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
                this.mKeys = nkeys;
                this.mValues = nvalues;
            }
            if (this.mSize - i3 != 0) {
                System.arraycopy(this.mKeys, i3, this.mKeys, i3 + 1, this.mSize - i3);
                System.arraycopy(this.mValues, i3, this.mValues, i3 + 1, this.mSize - i3);
            }
            this.mKeys[i3] = key;
            this.mValues[i3] = value;
            this.mSize++;
        } else {
            this.mValues[i] = value;
        }
    }

    public int size() {
        if (this.mGarbage) {
            m12gc();
        }
        return this.mSize;
    }

    public long keyAt(int i) {
        int index = i;
        int i2 = index;
        if (this.mGarbage) {
            m12gc();
        }
        return this.mKeys[index];
    }

    public E valueAt(int i) {
        int index = i;
        int i2 = index;
        if (this.mGarbage) {
            m12gc();
        }
        return this.mValues[index];
    }

    public void setValueAt(int i, E e) {
        int index = i;
        E value = e;
        int i2 = index;
        E e2 = value;
        if (this.mGarbage) {
            m12gc();
        }
        this.mValues[index] = value;
    }

    public int indexOfKey(long j) {
        long key = j;
        long j2 = key;
        if (this.mGarbage) {
            m12gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
    }

    public int indexOfValue(E e) {
        E value = e;
        E e2 = value;
        if (this.mGarbage) {
            m12gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        int n = this.mSize;
        Object[] values = this.mValues;
        for (int i = 0; i < n; i++) {
            values[i] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    public void append(long j, E e) {
        long key = j;
        E value = e;
        long j2 = key;
        E e2 = value;
        if (this.mSize != 0) {
            if (!(key > this.mKeys[this.mSize + -1])) {
                put(key, value);
                return;
            }
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            m12gc();
        }
        int i = this.mSize;
        int pos = i;
        if (i >= this.mKeys.length) {
            int idealLongArraySize = ContainerHelpers.idealLongArraySize(pos + 1);
            long[] nkeys = new long[idealLongArraySize];
            Object[] nvalues = new Object[idealLongArraySize];
            System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
            System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
            this.mKeys = nkeys;
            this.mValues = nvalues;
        }
        this.mKeys[pos] = key;
        this.mValues[pos] = value;
        this.mSize = pos + 1;
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        StringBuilder buffer = sb;
        StringBuilder append = sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                StringBuilder append2 = buffer.append(", ");
            }
            long keyAt = keyAt(i);
            long j = keyAt;
            StringBuilder append3 = buffer.append(keyAt);
            StringBuilder append4 = buffer.append('=');
            Object valueAt = valueAt(i);
            Object value = valueAt;
            if (valueAt == this) {
                StringBuilder append5 = buffer.append("(this Map)");
            } else {
                StringBuilder append6 = buffer.append(value);
            }
        }
        StringBuilder append7 = buffer.append('}');
        return buffer.toString();
    }
}
