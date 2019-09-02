package android.support.p000v4.util;

import java.util.Map;

/* renamed from: android.support.v4.util.SimpleArrayMap */
public class SimpleArrayMap<K, V> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayMap";
    static Object[] mBaseCache;
    static int mBaseCacheSize;
    static Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    Object[] mArray;
    int[] mHashes;
    int mSize;

    /* access modifiers changed from: 0000 */
    public int indexOf(Object obj, int i) {
        Object key = obj;
        int hash = i;
        Object obj2 = key;
        int i2 = hash;
        int i3 = this.mSize;
        int N = i3;
        if (i3 == 0) {
            return -1;
        }
        int binarySearch = ContainerHelpers.binarySearch(this.mHashes, N, hash);
        int index = binarySearch;
        if (binarySearch < 0) {
            return index;
        }
        if (key.equals(this.mArray[index << 1])) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == hash) {
            if (key.equals(this.mArray[end << 1])) {
                return end;
            }
            end++;
        }
        int i4 = index - 1;
        while (i4 >= 0 && this.mHashes[i4] == hash) {
            if (key.equals(this.mArray[i4 << 1])) {
                return i4;
            }
            i4--;
        }
        return end ^ -1;
    }

    /* access modifiers changed from: 0000 */
    public int indexOfNull() {
        int i = this.mSize;
        int N = i;
        if (i == 0) {
            return -1;
        }
        int binarySearch = ContainerHelpers.binarySearch(this.mHashes, N, 0);
        int index = binarySearch;
        if (binarySearch < 0) {
            return index;
        }
        if (null == this.mArray[index << 1]) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == 0) {
            if (null == this.mArray[end << 1]) {
                return end;
            }
            end++;
        }
        int i2 = index - 1;
        while (i2 >= 0 && this.mHashes[i2] == 0) {
            if (null == this.mArray[i2 << 1]) {
                return i2;
            }
            i2--;
        }
        return end ^ -1;
    }

    private void allocArrays(int i) {
        int size = i;
        int i2 = size;
        if (size == 8) {
            Class<ArrayMap> cls = ArrayMap.class;
            Class<ArrayMap> cls2 = cls;
            synchronized (cls) {
                try {
                    if (mTwiceBaseCache == null) {
                    } else {
                        Object[] array = mTwiceBaseCache;
                        this.mArray = array;
                        mTwiceBaseCache = (Object[]) array[0];
                        this.mHashes = (int[]) array[1];
                        array[1] = null;
                        array[0] = null;
                        mTwiceBaseCacheSize--;
                        return;
                    }
                } finally {
                    Class<ArrayMap> cls3 = cls2;
                }
            }
        } else if (size == 4) {
            Class<ArrayMap> cls4 = ArrayMap.class;
            Class<ArrayMap> cls5 = cls4;
            synchronized (cls4) {
                try {
                    if (mBaseCache == null) {
                    } else {
                        Object[] array2 = mBaseCache;
                        this.mArray = array2;
                        mBaseCache = (Object[]) array2[0];
                        this.mHashes = (int[]) array2[1];
                        array2[1] = null;
                        array2[0] = null;
                        mBaseCacheSize--;
                        return;
                    }
                } finally {
                    Class<ArrayMap> cls6 = cls5;
                }
            }
        }
        this.mHashes = new int[size];
        this.mArray = new Object[(size << 1)];
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i) {
        int[] hashes = iArr;
        Object[] array = objArr;
        int size = i;
        int[] iArr2 = hashes;
        Object obj = array;
        int i2 = size;
        if (hashes.length == 8) {
            Class<ArrayMap> cls = ArrayMap.class;
            Class<ArrayMap> cls2 = cls;
            synchronized (cls) {
                try {
                    if (mTwiceBaseCacheSize < 10) {
                        array[0] = mTwiceBaseCache;
                        array[1] = hashes;
                        for (int i3 = (size << 1) - 1; i3 >= 2; i3--) {
                            array[i3] = 0;
                        }
                        mTwiceBaseCache = array;
                        mTwiceBaseCacheSize++;
                    }
                } finally {
                    Class<ArrayMap> cls3 = cls2;
                }
            }
        } else if (hashes.length == 4) {
            Class<ArrayMap> cls4 = ArrayMap.class;
            Class<ArrayMap> cls5 = cls4;
            synchronized (cls4) {
                try {
                    if (mBaseCacheSize < 10) {
                        array[0] = mBaseCache;
                        array[1] = hashes;
                        for (int i4 = (size << 1) - 1; i4 >= 2; i4--) {
                            array[i4] = 0;
                        }
                        mBaseCache = array;
                        mBaseCacheSize++;
                    }
                } finally {
                    Class<ArrayMap> cls6 = cls5;
                }
            }
        }
    }

    public SimpleArrayMap() {
        this.mHashes = ContainerHelpers.EMPTY_INTS;
        this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        this.mSize = 0;
    }

    public SimpleArrayMap(int i) {
        int capacity = i;
        int i2 = capacity;
        if (capacity != 0) {
            allocArrays(capacity);
        } else {
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        }
        this.mSize = 0;
    }

    public SimpleArrayMap(SimpleArrayMap simpleArrayMap) {
        SimpleArrayMap map = simpleArrayMap;
        SimpleArrayMap simpleArrayMap2 = map;
        this();
        if (map != null) {
            putAll(map);
        }
    }

    public void clear() {
        if (this.mSize != 0) {
            int[] iArr = this.mHashes;
            freeArrays(iArr, this.mArray, this.mSize);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
        }
    }

    public void ensureCapacity(int i) {
        int minimumCapacity = i;
        int i2 = minimumCapacity;
        if (this.mHashes.length < minimumCapacity) {
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            allocArrays(minimumCapacity);
            if (this.mSize > 0) {
                System.arraycopy(ohashes, 0, this.mHashes, 0, this.mSize);
                System.arraycopy(oarray, 0, this.mArray, 0, this.mSize << 1);
            }
            freeArrays(ohashes, oarray, this.mSize);
        }
    }

    public boolean containsKey(Object obj) {
        Object key = obj;
        Object obj2 = key;
        return indexOfKey(key) >= 0;
    }

    public int indexOfKey(Object obj) {
        Object key = obj;
        Object obj2 = key;
        return key != null ? indexOf(key, key.hashCode()) : indexOfNull();
    }

    /* access modifiers changed from: 0000 */
    public int indexOfValue(Object obj) {
        Object value = obj;
        Object obj2 = value;
        int N = this.mSize * 2;
        Object[] array = this.mArray;
        if (value != null) {
            for (int i = 1; i < N; i += 2) {
                if (value.equals(array[i])) {
                    return i >> 1;
                }
            }
        } else {
            for (int i2 = 1; i2 < N; i2 += 2) {
                if (array[i2] == null) {
                    return i2 >> 1;
                }
            }
        }
        return -1;
    }

    public boolean containsValue(Object obj) {
        Object value = obj;
        Object obj2 = value;
        return indexOfValue(value) >= 0;
    }

    public V get(Object obj) {
        Object key = obj;
        Object obj2 = key;
        int indexOfKey = indexOfKey(key);
        int index = indexOfKey;
        if (indexOfKey < 0) {
            return null;
        }
        return this.mArray[(index << 1) + 1];
    }

    public K keyAt(int i) {
        int index = i;
        int i2 = index;
        return this.mArray[index << 1];
    }

    public V valueAt(int i) {
        int index = i;
        int i2 = index;
        return this.mArray[(index << 1) + 1];
    }

    public V setValueAt(int i, V v) {
        int index = i;
        V value = v;
        int i2 = index;
        V v2 = value;
        int i3 = (index << 1) + 1;
        int index2 = i3;
        Object obj = this.mArray[i3];
        this.mArray[i3] = value;
        return obj;
    }

    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    public V put(K k, V v) {
        int hash;
        int index;
        K key = k;
        V value = v;
        K k2 = key;
        V v2 = value;
        if (key != null) {
            int hashCode = key.hashCode();
            hash = hashCode;
            index = indexOf(key, hashCode);
        } else {
            hash = 0;
            index = indexOfNull();
        }
        if (index < 0) {
            int index2 = index ^ -1;
            if (this.mSize >= this.mHashes.length) {
                int i = this.mSize < 8 ? this.mSize < 4 ? 4 : 8 : this.mSize + (this.mSize >> 1);
                int n = i;
                int[] ohashes = this.mHashes;
                Object[] oarray = this.mArray;
                allocArrays(n);
                if (this.mHashes.length > 0) {
                    System.arraycopy(ohashes, 0, this.mHashes, 0, ohashes.length);
                    System.arraycopy(oarray, 0, this.mArray, 0, oarray.length);
                }
                freeArrays(ohashes, oarray, this.mSize);
            }
            if (index2 < this.mSize) {
                System.arraycopy(this.mHashes, index2, this.mHashes, index2 + 1, this.mSize - index2);
                System.arraycopy(this.mArray, index2 << 1, this.mArray, (index2 + 1) << 1, (this.mSize - index2) << 1);
            }
            this.mHashes[index2] = hash;
            this.mArray[index2 << 1] = key;
            this.mArray[(index2 << 1) + 1] = value;
            this.mSize++;
            return null;
        }
        int i2 = (index << 1) + 1;
        int index3 = i2;
        Object obj = this.mArray[i2];
        this.mArray[i2] = value;
        return obj;
    }

    public void putAll(SimpleArrayMap<? extends K, ? extends V> simpleArrayMap) {
        SimpleArrayMap<? extends K, ? extends V> array = simpleArrayMap;
        SimpleArrayMap<? extends K, ? extends V> simpleArrayMap2 = array;
        int i = array.mSize;
        int N = i;
        ensureCapacity(this.mSize + i);
        if (this.mSize != 0) {
            for (int i2 = 0; i2 < N; i2++) {
                Object put = put(array.keyAt(i2), array.valueAt(i2));
            }
        } else if (N > 0) {
            System.arraycopy(array.mHashes, 0, this.mHashes, 0, N);
            System.arraycopy(array.mArray, 0, this.mArray, 0, N << 1);
            this.mSize = N;
        }
    }

    public V remove(Object obj) {
        Object key = obj;
        Object obj2 = key;
        int indexOfKey = indexOfKey(key);
        int index = indexOfKey;
        if (indexOfKey < 0) {
            return null;
        }
        return removeAt(index);
    }

    public V removeAt(int i) {
        int i2;
        int index = i;
        int i3 = index;
        Object old = this.mArray[(index << 1) + 1];
        if (this.mSize <= 1) {
            int[] iArr = this.mHashes;
            freeArrays(iArr, this.mArray, this.mSize);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
        } else if (this.mHashes.length > 8 && this.mSize < this.mHashes.length / 3) {
            if (this.mSize <= 8) {
                i2 = 8;
            } else {
                i2 = this.mSize + (this.mSize >> 1);
            }
            int n = i2;
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            allocArrays(n);
            this.mSize--;
            if (index > 0) {
                System.arraycopy(ohashes, 0, this.mHashes, 0, index);
                System.arraycopy(oarray, 0, this.mArray, 0, index << 1);
            }
            if (index < this.mSize) {
                System.arraycopy(ohashes, index + 1, this.mHashes, index, this.mSize - index);
                System.arraycopy(oarray, (index + 1) << 1, this.mArray, index << 1, (this.mSize - index) << 1);
            }
        } else {
            this.mSize--;
            if (index < this.mSize) {
                System.arraycopy(this.mHashes, index + 1, this.mHashes, index, this.mSize - index);
                System.arraycopy(this.mArray, (index + 1) << 1, this.mArray, index << 1, (this.mSize - index) << 1);
            }
            this.mArray[this.mSize << 1] = null;
            this.mArray[(this.mSize << 1) + 1] = null;
        }
        return old;
    }

    public int size() {
        return this.mSize;
    }

    public boolean equals(Object obj) {
        Object object = obj;
        Object obj2 = object;
        if (this == object) {
            return true;
        }
        if (object instanceof SimpleArrayMap) {
            SimpleArrayMap simpleArrayMap = (SimpleArrayMap) object;
            if (size() != simpleArrayMap.size()) {
                return false;
            }
            int i = 0;
            while (i < this.mSize) {
                try {
                    Object keyAt = keyAt(i);
                    Object valueAt = valueAt(i);
                    Object theirs = simpleArrayMap.get(keyAt);
                    if (valueAt != null) {
                        if (!valueAt.equals(theirs)) {
                            return false;
                        }
                    } else if (theirs != null || !simpleArrayMap.containsKey(keyAt)) {
                        return false;
                    }
                    i++;
                } catch (NullPointerException e) {
                    NullPointerException nullPointerException = e;
                    return false;
                } catch (ClassCastException e2) {
                    ClassCastException classCastException = e2;
                    return false;
                }
            }
            return true;
        } else if (!(object instanceof Map)) {
            return false;
        } else {
            Map map = (Map) object;
            if (size() != map.size()) {
                return false;
            }
            int i2 = 0;
            while (i2 < this.mSize) {
                try {
                    Object keyAt2 = keyAt(i2);
                    Object valueAt2 = valueAt(i2);
                    Object theirs2 = map.get(keyAt2);
                    if (valueAt2 != null) {
                        if (!valueAt2.equals(theirs2)) {
                            return false;
                        }
                    } else if (theirs2 != null || !map.containsKey(keyAt2)) {
                        return false;
                    }
                    i2++;
                } catch (NullPointerException e3) {
                    NullPointerException nullPointerException2 = e3;
                    return false;
                } catch (ClassCastException e4) {
                    ClassCastException classCastException2 = e4;
                    return false;
                }
            }
            return true;
        }
    }

    public int hashCode() {
        int[] hashes = this.mHashes;
        Object[] array = this.mArray;
        int result = 0;
        int i = 0;
        int v = 1;
        int s = this.mSize;
        while (i < s) {
            Object obj = array[v];
            Object obj2 = obj;
            result += hashes[i] ^ (obj != null ? obj.hashCode() : 0);
            i++;
            v += 2;
        }
        return result;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        StringBuilder buffer = sb;
        StringBuilder append = sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                StringBuilder append2 = buffer.append(", ");
            }
            Object keyAt = keyAt(i);
            Object key = keyAt;
            if (keyAt == this) {
                StringBuilder append3 = buffer.append("(this Map)");
            } else {
                StringBuilder append4 = buffer.append(key);
            }
            StringBuilder append5 = buffer.append('=');
            Object valueAt = valueAt(i);
            Object value = valueAt;
            if (valueAt == this) {
                StringBuilder append6 = buffer.append("(this Map)");
            } else {
                StringBuilder append7 = buffer.append(value);
            }
        }
        StringBuilder append8 = buffer.append('}');
        return buffer.toString();
    }
}
