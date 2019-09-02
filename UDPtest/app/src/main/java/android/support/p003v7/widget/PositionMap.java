package android.support.p003v7.widget;

import java.util.ArrayList;

/* renamed from: android.support.v7.widget.PositionMap */
class PositionMap<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    /* renamed from: android.support.v7.widget.PositionMap$ContainerHelpers */
    static class ContainerHelpers {
        static final boolean[] EMPTY_BOOLEANS = new boolean[0];
        static final int[] EMPTY_INTS = new int[0];
        static final long[] EMPTY_LONGS = new long[0];
        static final Object[] EMPTY_OBJECTS = new Object[0];

        ContainerHelpers() {
        }

        static int binarySearch(int[] iArr, int i, int i2) {
            int[] array = iArr;
            int size = i;
            int value = i2;
            int[] iArr2 = array;
            int i3 = size;
            int i4 = value;
            int lo = 0;
            int hi = size - 1;
            while (lo <= hi) {
                int i5 = (lo + hi) >>> 1;
                int mid = i5;
                int i6 = array[i5];
                int midVal = i6;
                if (i6 < value) {
                    lo = mid + 1;
                } else if (midVal <= value) {
                    return mid;
                } else {
                    hi = mid - 1;
                }
            }
            return lo ^ -1;
        }
    }

    public PositionMap(int i) {
        int initialCapacity = i;
        int i2 = initialCapacity;
        this.mGarbage = false;
        if (initialCapacity != 0) {
            int idealIntArraySize = idealIntArraySize(initialCapacity);
            int initialCapacity2 = idealIntArraySize;
            this.mKeys = new int[idealIntArraySize];
            this.mValues = new Object[idealIntArraySize];
        } else {
            this.mKeys = ContainerHelpers.EMPTY_INTS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
        }
        this.mSize = 0;
    }

    public PositionMap() {
        this(10);
    }

    public PositionMap<E> clone() {
        PositionMap positionMap = null;
        try {
            PositionMap positionMap2 = (PositionMap) super.clone();
            positionMap = positionMap2;
            positionMap2.mKeys = (int[]) this.mKeys.clone();
            positionMap.mValues = (Object[]) this.mValues.clone();
        } catch (CloneNotSupportedException e) {
        }
        return positionMap;
    }

    public E get(int i) {
        int key = i;
        int i2 = key;
        return get(key, null);
    }

    public E get(int i, E e) {
        int key = i;
        E valueIfKeyNotFound = e;
        int i2 = key;
        E e2 = valueIfKeyNotFound;
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        int i3 = binarySearch;
        if (binarySearch >= 0 && this.mValues[i3] != DELETED) {
            return this.mValues[i3];
        }
        return valueIfKeyNotFound;
    }

    public void delete(int i) {
        int key = i;
        int i2 = key;
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        int i3 = binarySearch;
        if (binarySearch >= 0 && this.mValues[i3] != DELETED) {
            this.mValues[i3] = DELETED;
            this.mGarbage = true;
        }
    }

    public void remove(int i) {
        int key = i;
        int i2 = key;
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

    public void removeAtRange(int i, int i2) {
        int index = i;
        int size = i2;
        int i3 = index;
        int i4 = size;
        int end = Math.min(this.mSize, index + size);
        for (int i5 = index; i5 < end; i5++) {
            removeAt(i5);
        }
    }

    public void insertKeyRange(int i, int i2) {
        int i3 = i;
        int i4 = i2;
    }

    public void removeKeyRange(ArrayList<E> arrayList, int i, int i2) {
        ArrayList<E> arrayList2 = arrayList;
        int i3 = i;
        int i4 = i2;
    }

    /* renamed from: gc */
    private void m29gc() {
        int n = this.mSize;
        int o = 0;
        int[] keys = this.mKeys;
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

    public void put(int i, E e) {
        int key = i;
        E value = e;
        int i2 = key;
        E e2 = value;
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        int i3 = binarySearch;
        if (binarySearch < 0) {
            int i4 = i3 ^ -1;
            int i5 = i4;
            if (i4 < this.mSize && this.mValues[i5] == DELETED) {
                this.mKeys[i5] = key;
                this.mValues[i5] = value;
                return;
            }
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                m29gc();
                i5 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key) ^ -1;
            }
            if (this.mSize >= this.mKeys.length) {
                int idealIntArraySize = idealIntArraySize(this.mSize + 1);
                int[] nkeys = new int[idealIntArraySize];
                Object[] nvalues = new Object[idealIntArraySize];
                System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
                System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
                this.mKeys = nkeys;
                this.mValues = nvalues;
            }
            if (this.mSize - i5 != 0) {
                System.arraycopy(this.mKeys, i5, this.mKeys, i5 + 1, this.mSize - i5);
                System.arraycopy(this.mValues, i5, this.mValues, i5 + 1, this.mSize - i5);
            }
            this.mKeys[i5] = key;
            this.mValues[i5] = value;
            this.mSize++;
        } else {
            this.mValues[i3] = value;
        }
    }

    public int size() {
        if (this.mGarbage) {
            m29gc();
        }
        return this.mSize;
    }

    public int keyAt(int i) {
        int index = i;
        int i2 = index;
        if (this.mGarbage) {
            m29gc();
        }
        return this.mKeys[index];
    }

    public E valueAt(int i) {
        int index = i;
        int i2 = index;
        if (this.mGarbage) {
            m29gc();
        }
        return this.mValues[index];
    }

    public void setValueAt(int i, E e) {
        int index = i;
        E value = e;
        int i2 = index;
        E e2 = value;
        if (this.mGarbage) {
            m29gc();
        }
        this.mValues[index] = value;
    }

    public int indexOfKey(int i) {
        int key = i;
        int i2 = key;
        if (this.mGarbage) {
            m29gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
    }

    public int indexOfValue(E e) {
        E value = e;
        E e2 = value;
        if (this.mGarbage) {
            m29gc();
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

    public void append(int i, E e) {
        int key = i;
        E value = e;
        int i2 = key;
        E e2 = value;
        if (this.mSize != 0) {
            if (key <= this.mKeys[this.mSize - 1]) {
                put(key, value);
                return;
            }
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            m29gc();
        }
        int i3 = this.mSize;
        int pos = i3;
        if (i3 >= this.mKeys.length) {
            int idealIntArraySize = idealIntArraySize(pos + 1);
            int[] nkeys = new int[idealIntArraySize];
            Object[] nvalues = new Object[idealIntArraySize];
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
            int keyAt = keyAt(i);
            int i2 = keyAt;
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

    static int idealByteArraySize(int i) {
        int need = i;
        int i2 = need;
        for (int i3 = 4; i3 < 32; i3++) {
            if (need <= (1 << i3) - 12) {
                return (1 << i3) - 12;
            }
        }
        return need;
    }

    static int idealBooleanArraySize(int i) {
        int need = i;
        int i2 = need;
        return idealByteArraySize(need);
    }

    static int idealShortArraySize(int i) {
        int need = i;
        int i2 = need;
        return idealByteArraySize(need * 2) / 2;
    }

    static int idealCharArraySize(int i) {
        int need = i;
        int i2 = need;
        return idealByteArraySize(need * 2) / 2;
    }

    static int idealIntArraySize(int i) {
        int need = i;
        int i2 = need;
        return idealByteArraySize(need * 4) / 4;
    }

    static int idealFloatArraySize(int i) {
        int need = i;
        int i2 = need;
        return idealByteArraySize(need * 4) / 4;
    }

    static int idealObjectArraySize(int i) {
        int need = i;
        int i2 = need;
        return idealByteArraySize(need * 4) / 4;
    }

    static int idealLongArraySize(int i) {
        int need = i;
        int i2 = need;
        return idealByteArraySize(need * 8) / 8;
    }
}
