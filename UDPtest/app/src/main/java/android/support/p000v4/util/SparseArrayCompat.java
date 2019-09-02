package android.support.p000v4.util;

/* renamed from: android.support.v4.util.SparseArrayCompat */
public class SparseArrayCompat<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    public SparseArrayCompat(int i) {
        int initialCapacity = i;
        int i2 = initialCapacity;
        this.mGarbage = false;
        if (initialCapacity != 0) {
            int idealIntArraySize = ContainerHelpers.idealIntArraySize(initialCapacity);
            int initialCapacity2 = idealIntArraySize;
            this.mKeys = new int[idealIntArraySize];
            this.mValues = new Object[idealIntArraySize];
        } else {
            this.mKeys = ContainerHelpers.EMPTY_INTS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
        }
        this.mSize = 0;
    }

    public SparseArrayCompat() {
        this(10);
    }

    public SparseArrayCompat<E> clone() {
        SparseArrayCompat sparseArrayCompat = null;
        try {
            SparseArrayCompat sparseArrayCompat2 = (SparseArrayCompat) super.clone();
            sparseArrayCompat = sparseArrayCompat2;
            sparseArrayCompat2.mKeys = (int[]) this.mKeys.clone();
            sparseArrayCompat.mValues = (Object[]) this.mValues.clone();
        } catch (CloneNotSupportedException e) {
        }
        return sparseArrayCompat;
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

    /* renamed from: gc */
    private void m13gc() {
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
                m13gc();
                i5 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key) ^ -1;
            }
            if (this.mSize >= this.mKeys.length) {
                int idealIntArraySize = ContainerHelpers.idealIntArraySize(this.mSize + 1);
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
            m13gc();
        }
        return this.mSize;
    }

    public int keyAt(int i) {
        int index = i;
        int i2 = index;
        if (this.mGarbage) {
            m13gc();
        }
        return this.mKeys[index];
    }

    public E valueAt(int i) {
        int index = i;
        int i2 = index;
        if (this.mGarbage) {
            m13gc();
        }
        return this.mValues[index];
    }

    public void setValueAt(int i, E e) {
        int index = i;
        E value = e;
        int i2 = index;
        E e2 = value;
        if (this.mGarbage) {
            m13gc();
        }
        this.mValues[index] = value;
    }

    public int indexOfKey(int i) {
        int key = i;
        int i2 = key;
        if (this.mGarbage) {
            m13gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
    }

    public int indexOfValue(E e) {
        E value = e;
        E e2 = value;
        if (this.mGarbage) {
            m13gc();
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
            m13gc();
        }
        int i3 = this.mSize;
        int pos = i3;
        if (i3 >= this.mKeys.length) {
            int idealIntArraySize = ContainerHelpers.idealIntArraySize(pos + 1);
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
}
