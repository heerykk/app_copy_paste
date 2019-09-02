package android.support.p000v4.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* renamed from: android.support.v4.util.ArraySet */
public final class ArraySet<E> implements Collection<E>, Set<E> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final int[] INT = new int[0];
    private static final Object[] OBJECT = new Object[0];
    private static final String TAG = "ArraySet";
    static Object[] sBaseCache;
    static int sBaseCacheSize;
    static Object[] sTwiceBaseCache;
    static int sTwiceBaseCacheSize;
    Object[] mArray;
    MapCollections<E, E> mCollections;
    int[] mHashes;
    final boolean mIdentityHashCode;
    int mSize;

    private int indexOf(Object obj, int i) {
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
        if (key.equals(this.mArray[index])) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == hash) {
            if (key.equals(this.mArray[end])) {
                return end;
            }
            end++;
        }
        int i4 = index - 1;
        while (i4 >= 0 && this.mHashes[i4] == hash) {
            if (key.equals(this.mArray[i4])) {
                return i4;
            }
            i4--;
        }
        return end ^ -1;
    }

    private int indexOfNull() {
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
        if (null == this.mArray[index]) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == 0) {
            if (null == this.mArray[end]) {
                return end;
            }
            end++;
        }
        int i2 = index - 1;
        while (i2 >= 0 && this.mHashes[i2] == 0) {
            if (null == this.mArray[i2]) {
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
            Class<ArraySet> cls = ArraySet.class;
            Class<ArraySet> cls2 = cls;
            synchronized (cls) {
                try {
                    if (sTwiceBaseCache == null) {
                    } else {
                        Object[] array = sTwiceBaseCache;
                        this.mArray = array;
                        sTwiceBaseCache = (Object[]) array[0];
                        this.mHashes = (int[]) array[1];
                        array[1] = null;
                        array[0] = null;
                        sTwiceBaseCacheSize--;
                        return;
                    }
                } finally {
                    Class<ArraySet> cls3 = cls2;
                }
            }
        } else if (size == 4) {
            Class<ArraySet> cls4 = ArraySet.class;
            Class<ArraySet> cls5 = cls4;
            synchronized (cls4) {
                try {
                    if (sBaseCache == null) {
                    } else {
                        Object[] array2 = sBaseCache;
                        this.mArray = array2;
                        sBaseCache = (Object[]) array2[0];
                        this.mHashes = (int[]) array2[1];
                        array2[1] = null;
                        array2[0] = null;
                        sBaseCacheSize--;
                        return;
                    }
                } finally {
                    Class<ArraySet> cls6 = cls5;
                }
            }
        }
        this.mHashes = new int[size];
        this.mArray = new Object[size];
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i) {
        int[] hashes = iArr;
        Object[] array = objArr;
        int size = i;
        int[] iArr2 = hashes;
        Object obj = array;
        int i2 = size;
        if (hashes.length == 8) {
            Class<ArraySet> cls = ArraySet.class;
            Class<ArraySet> cls2 = cls;
            synchronized (cls) {
                try {
                    if (sTwiceBaseCacheSize < 10) {
                        array[0] = sTwiceBaseCache;
                        array[1] = hashes;
                        for (int i3 = size - 1; i3 >= 2; i3--) {
                            array[i3] = 0;
                        }
                        sTwiceBaseCache = array;
                        sTwiceBaseCacheSize++;
                    }
                } finally {
                    Class<ArraySet> cls3 = cls2;
                }
            }
        } else if (hashes.length == 4) {
            Class<ArraySet> cls4 = ArraySet.class;
            Class<ArraySet> cls5 = cls4;
            synchronized (cls4) {
                try {
                    if (sBaseCacheSize < 10) {
                        array[0] = sBaseCache;
                        array[1] = hashes;
                        for (int i4 = size - 1; i4 >= 2; i4--) {
                            array[i4] = 0;
                        }
                        sBaseCache = array;
                        sBaseCacheSize++;
                    }
                } finally {
                    Class<ArraySet> cls6 = cls5;
                }
            }
        }
    }

    public ArraySet() {
        this(0, false);
    }

    public ArraySet(int i) {
        int capacity = i;
        int i2 = capacity;
        this(capacity, false);
    }

    public ArraySet(int i, boolean z) {
        int capacity = i;
        int i2 = capacity;
        this.mIdentityHashCode = z;
        if (capacity != 0) {
            allocArrays(capacity);
        } else {
            this.mHashes = INT;
            this.mArray = OBJECT;
        }
        this.mSize = 0;
    }

    public ArraySet(ArraySet<E> arraySet) {
        ArraySet<E> set = arraySet;
        ArraySet<E> arraySet2 = set;
        this();
        if (set != null) {
            addAll(set);
        }
    }

    public ArraySet(Collection<E> collection) {
        Collection<E> set = collection;
        Collection<E> collection2 = set;
        this();
        if (set != null) {
            boolean addAll = addAll(set);
        }
    }

    public void clear() {
        if (this.mSize != 0) {
            int[] iArr = this.mHashes;
            freeArrays(iArr, this.mArray, this.mSize);
            this.mHashes = INT;
            this.mArray = OBJECT;
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
                System.arraycopy(oarray, 0, this.mArray, 0, this.mSize);
            }
            freeArrays(ohashes, oarray, this.mSize);
        }
    }

    public boolean contains(Object obj) {
        Object key = obj;
        Object obj2 = key;
        return indexOf(key) >= 0;
    }

    public int indexOf(Object obj) {
        int indexOfNull;
        Object key = obj;
        Object obj2 = key;
        if (key != null) {
            indexOfNull = indexOf(key, !this.mIdentityHashCode ? key.hashCode() : System.identityHashCode(key));
        } else {
            indexOfNull = indexOfNull();
        }
        return indexOfNull;
    }

    public E valueAt(int i) {
        int index = i;
        int i2 = index;
        return this.mArray[index];
    }

    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    public boolean add(E e) {
        int hash;
        int index;
        E value = e;
        E e2 = value;
        if (value != null) {
            hash = !this.mIdentityHashCode ? value.hashCode() : System.identityHashCode(value);
            index = indexOf(value, hash);
        } else {
            hash = 0;
            index = indexOfNull();
        }
        if (index >= 0) {
            return false;
        }
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
            System.arraycopy(this.mArray, index2, this.mArray, index2 + 1, this.mSize - index2);
        }
        this.mHashes[index2] = hash;
        this.mArray[index2] = value;
        this.mSize++;
        return true;
    }

    public void append(E e) {
        E value = e;
        E e2 = value;
        int index = this.mSize;
        int i = value != null ? !this.mIdentityHashCode ? value.hashCode() : System.identityHashCode(value) : 0;
        int hash = i;
        if (index < this.mHashes.length) {
            if (index > 0) {
                if (this.mHashes[index - 1] > hash) {
                    boolean add = add(value);
                    return;
                }
            }
            this.mSize = index + 1;
            this.mHashes[index] = hash;
            this.mArray[index] = value;
            return;
        }
        throw new IllegalStateException("Array is full");
    }

    public void addAll(ArraySet<? extends E> arraySet) {
        ArraySet<? extends E> array = arraySet;
        ArraySet<? extends E> arraySet2 = array;
        int i = array.mSize;
        int N = i;
        ensureCapacity(this.mSize + i);
        if (this.mSize != 0) {
            for (int i2 = 0; i2 < N; i2++) {
                boolean add = add(array.valueAt(i2));
            }
        } else if (N > 0) {
            System.arraycopy(array.mHashes, 0, this.mHashes, 0, N);
            System.arraycopy(array.mArray, 0, this.mArray, 0, N);
            this.mSize = N;
        }
    }

    public boolean remove(Object obj) {
        Object object = obj;
        Object obj2 = object;
        int indexOf = indexOf(object);
        int index = indexOf;
        if (indexOf < 0) {
            return false;
        }
        Object removeAt = removeAt(index);
        return true;
    }

    public E removeAt(int i) {
        int i2;
        int index = i;
        int i3 = index;
        Object old = this.mArray[index];
        if (this.mSize <= 1) {
            int[] iArr = this.mHashes;
            freeArrays(iArr, this.mArray, this.mSize);
            this.mHashes = INT;
            this.mArray = OBJECT;
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
                System.arraycopy(oarray, 0, this.mArray, 0, index);
            }
            if (index < this.mSize) {
                System.arraycopy(ohashes, index + 1, this.mHashes, index, this.mSize - index);
                System.arraycopy(oarray, index + 1, this.mArray, index, this.mSize - index);
            }
        } else {
            this.mSize--;
            if (index < this.mSize) {
                System.arraycopy(this.mHashes, index + 1, this.mHashes, index, this.mSize - index);
                System.arraycopy(this.mArray, index + 1, this.mArray, index, this.mSize - index);
            }
            this.mArray[this.mSize] = null;
        }
        return old;
    }

    public boolean removeAll(ArraySet<? extends E> arraySet) {
        ArraySet<? extends E> array = arraySet;
        ArraySet<? extends E> arraySet2 = array;
        int N = array.mSize;
        int originalSize = this.mSize;
        for (int i = 0; i < N; i++) {
            boolean remove = remove(array.valueAt(i));
        }
        return originalSize != this.mSize;
    }

    public int size() {
        return this.mSize;
    }

    public Object[] toArray() {
        Object[] result = new Object[this.mSize];
        System.arraycopy(this.mArray, 0, result, 0, this.mSize);
        return result;
    }

    public <T> T[] toArray(T[] tArr) {
        T[] array = tArr;
        T[] array2 = array;
        if (array.length < this.mSize) {
            T[] tArr2 = (Object[]) Array.newInstance(array.getClass().getComponentType(), this.mSize);
            T[] tArr3 = tArr2;
            array2 = tArr2;
        }
        System.arraycopy(this.mArray, 0, array2, 0, this.mSize);
        if (array2.length > this.mSize) {
            array2[this.mSize] = null;
        }
        return array2;
    }

    public boolean equals(Object obj) {
        Object object = obj;
        Object obj2 = object;
        if (this == object) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        Set set = (Set) object;
        if (size() != set.size()) {
            return false;
        }
        int i = 0;
        while (i < this.mSize) {
            try {
                if (!set.contains(valueAt(i))) {
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
    }

    public int hashCode() {
        int[] hashes = this.mHashes;
        int result = 0;
        for (int i = 0; i < this.mSize; i++) {
            result += hashes[i];
        }
        return result;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 14);
        StringBuilder buffer = sb;
        StringBuilder append = sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                StringBuilder append2 = buffer.append(", ");
            }
            Object valueAt = valueAt(i);
            Object value = valueAt;
            if (valueAt == this) {
                StringBuilder append3 = buffer.append("(this Set)");
            } else {
                StringBuilder append4 = buffer.append(value);
            }
        }
        StringBuilder append5 = buffer.append('}');
        return buffer.toString();
    }

    private MapCollections<E, E> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<E, E>(this) {
                final /* synthetic */ ArraySet this$0;

                {
                    ArraySet this$02 = r5;
                    ArraySet arraySet = this$02;
                    this.this$0 = this$02;
                }

                /* access modifiers changed from: protected */
                public int colGetSize() {
                    return this.this$0.mSize;
                }

                /* access modifiers changed from: protected */
                public Object colGetEntry(int i, int i2) {
                    int index = i;
                    int i3 = index;
                    int i4 = i2;
                    return this.this$0.mArray[index];
                }

                /* access modifiers changed from: protected */
                public int colIndexOfKey(Object obj) {
                    Object key = obj;
                    Object obj2 = key;
                    return this.this$0.indexOf(key);
                }

                /* access modifiers changed from: protected */
                public int colIndexOfValue(Object obj) {
                    Object value = obj;
                    Object obj2 = value;
                    return this.this$0.indexOf(value);
                }

                /* access modifiers changed from: protected */
                public Map<E, E> colGetMap() {
                    throw new UnsupportedOperationException("not a map");
                }

                /* access modifiers changed from: protected */
                public void colPut(E e, E e2) {
                    E key = e;
                    E e3 = key;
                    E e4 = e2;
                    boolean add = this.this$0.add(key);
                }

                /* access modifiers changed from: protected */
                public E colSetValue(int i, E e) {
                    int i2 = i;
                    E e2 = e;
                    throw new UnsupportedOperationException("not a map");
                }

                /* access modifiers changed from: protected */
                public void colRemoveAt(int i) {
                    int index = i;
                    int i2 = index;
                    Object removeAt = this.this$0.removeAt(index);
                }

                /* access modifiers changed from: protected */
                public void colClear() {
                    this.this$0.clear();
                }
            };
        }
        return this.mCollections;
    }

    public Iterator<E> iterator() {
        return getCollection().getKeySet().iterator();
    }

    public boolean containsAll(Collection<?> collection) {
        Collection<?> collection2 = collection;
        Collection<?> collection3 = collection2;
        for (Object contains : collection2) {
            if (!contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(Collection<? extends E> collection) {
        Collection<? extends E> collection2 = collection;
        Collection<? extends E> collection3 = collection2;
        ensureCapacity(this.mSize + collection2.size());
        boolean added = false;
        for (Object next : collection2) {
            Object obj = next;
            added |= add(next);
        }
        return added;
    }

    public boolean removeAll(Collection<?> collection) {
        Collection<?> collection2 = collection;
        Collection<?> collection3 = collection2;
        boolean removed = false;
        for (Object next : collection2) {
            Object obj = next;
            removed |= remove(next);
        }
        return removed;
    }

    public boolean retainAll(Collection<?> collection) {
        Collection<?> collection2 = collection;
        Collection<?> collection3 = collection2;
        boolean removed = false;
        for (int i = this.mSize - 1; i >= 0; i--) {
            if (!collection2.contains(this.mArray[i])) {
                Object removeAt = removeAt(i);
                removed = true;
            }
        }
        return removed;
    }
}
