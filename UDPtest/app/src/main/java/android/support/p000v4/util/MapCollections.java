package android.support.p000v4.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: android.support.v4.util.MapCollections */
abstract class MapCollections<K, V> {
    EntrySet mEntrySet;
    KeySet mKeySet;
    ValuesCollection mValues;

    /* renamed from: android.support.v4.util.MapCollections$ArrayIterator */
    final class ArrayIterator<T> implements Iterator<T> {
        boolean mCanRemove = false;
        int mIndex;
        final int mOffset;
        int mSize;
        final /* synthetic */ MapCollections this$0;

        ArrayIterator(MapCollections mapCollections, int i) {
            MapCollections this$02 = mapCollections;
            int offset = i;
            MapCollections mapCollections2 = this$02;
            int i2 = offset;
            this.this$0 = this$02;
            this.mOffset = offset;
            this.mSize = this$02.colGetSize();
        }

        public boolean hasNext() {
            return this.mIndex < this.mSize;
        }

        public T next() {
            Object res = this.this$0.colGetEntry(this.mIndex, this.mOffset);
            this.mIndex++;
            this.mCanRemove = true;
            return res;
        }

        public void remove() {
            if (this.mCanRemove) {
                this.mIndex--;
                this.mSize--;
                this.mCanRemove = false;
                this.this$0.colRemoveAt(this.mIndex);
                return;
            }
            throw new IllegalStateException();
        }
    }

    /* renamed from: android.support.v4.util.MapCollections$EntrySet */
    final class EntrySet implements Set<Entry<K, V>> {
        final /* synthetic */ MapCollections this$0;

        EntrySet(MapCollections mapCollections) {
            MapCollections this$02 = mapCollections;
            MapCollections mapCollections2 = this$02;
            this.this$0 = this$02;
        }

        public /* bridge */ /* synthetic */ boolean add(Object obj) {
            return add((Entry) obj);
        }

        public boolean add(Entry<K, V> entry) {
            Entry<K, V> entry2 = entry;
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends Entry<K, V>> collection) {
            Collection<? extends Entry<K, V>> collection2 = collection;
            Collection<? extends Entry<K, V>> collection3 = collection2;
            int oldSize = this.this$0.colGetSize();
            for (Entry entry : collection2) {
                this.this$0.colPut(entry.getKey(), entry.getValue());
            }
            return oldSize != this.this$0.colGetSize();
        }

        public void clear() {
            this.this$0.colClear();
        }

        public boolean contains(Object obj) {
            Object o = obj;
            Object obj2 = o;
            if (!(o instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) o;
            int colIndexOfKey = this.this$0.colIndexOfKey(entry.getKey());
            int index = colIndexOfKey;
            if (colIndexOfKey < 0) {
                return false;
            }
            Object colGetEntry = this.this$0.colGetEntry(index, 1);
            Object obj3 = colGetEntry;
            return ContainerHelpers.equal(colGetEntry, entry.getValue());
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

        public boolean isEmpty() {
            return this.this$0.colGetSize() == 0;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new MapIterator(this.this$0);
        }

        public boolean remove(Object obj) {
            Object obj2 = obj;
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> collection) {
            Collection<?> collection2 = collection;
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> collection) {
            Collection<?> collection2 = collection;
            throw new UnsupportedOperationException();
        }

        public int size() {
            return this.this$0.colGetSize();
        }

        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        public <T> T[] toArray(T[] tArr) {
            T[] tArr2 = tArr;
            throw new UnsupportedOperationException();
        }

        public boolean equals(Object obj) {
            Object object = obj;
            Object obj2 = object;
            return MapCollections.equalsSetHelper(this, object);
        }

        public int hashCode() {
            int i;
            int result = 0;
            for (int i2 = this.this$0.colGetSize() - 1; i2 >= 0; i2--) {
                Object key = this.this$0.colGetEntry(i2, 0);
                Object colGetEntry = this.this$0.colGetEntry(i2, 1);
                Object obj = colGetEntry;
                int i3 = result;
                int i4 = key != null ? key.hashCode() : 0;
                if (colGetEntry != null) {
                    i = colGetEntry.hashCode();
                } else {
                    i = 0;
                }
                result = i3 + (i4 ^ i);
            }
            return result;
        }
    }

    /* renamed from: android.support.v4.util.MapCollections$KeySet */
    final class KeySet implements Set<K> {
        final /* synthetic */ MapCollections this$0;

        KeySet(MapCollections mapCollections) {
            MapCollections this$02 = mapCollections;
            MapCollections mapCollections2 = this$02;
            this.this$0 = this$02;
        }

        public boolean add(K k) {
            K k2 = k;
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends K> collection) {
            Collection<? extends K> collection2 = collection;
            throw new UnsupportedOperationException();
        }

        public void clear() {
            this.this$0.colClear();
        }

        public boolean contains(Object obj) {
            Object object = obj;
            Object obj2 = object;
            return this.this$0.colIndexOfKey(object) >= 0;
        }

        public boolean containsAll(Collection<?> collection) {
            Collection<?> collection2 = collection;
            Collection<?> collection3 = collection2;
            return MapCollections.containsAllHelper(this.this$0.colGetMap(), collection2);
        }

        public boolean isEmpty() {
            return this.this$0.colGetSize() == 0;
        }

        public Iterator<K> iterator() {
            return new ArrayIterator(this.this$0, 0);
        }

        public boolean remove(Object obj) {
            Object object = obj;
            Object obj2 = object;
            int colIndexOfKey = this.this$0.colIndexOfKey(object);
            int index = colIndexOfKey;
            if (colIndexOfKey < 0) {
                return false;
            }
            this.this$0.colRemoveAt(index);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            Collection<?> collection2 = collection;
            Collection<?> collection3 = collection2;
            return MapCollections.removeAllHelper(this.this$0.colGetMap(), collection2);
        }

        public boolean retainAll(Collection<?> collection) {
            Collection<?> collection2 = collection;
            Collection<?> collection3 = collection2;
            return MapCollections.retainAllHelper(this.this$0.colGetMap(), collection2);
        }

        public int size() {
            return this.this$0.colGetSize();
        }

        public Object[] toArray() {
            return this.this$0.toArrayHelper(0);
        }

        public <T> T[] toArray(T[] tArr) {
            T[] array = tArr;
            T[] tArr2 = array;
            return this.this$0.toArrayHelper(array, 0);
        }

        public boolean equals(Object obj) {
            Object object = obj;
            Object obj2 = object;
            return MapCollections.equalsSetHelper(this, object);
        }

        public int hashCode() {
            int result = 0;
            for (int i = this.this$0.colGetSize() - 1; i >= 0; i--) {
                Object colGetEntry = this.this$0.colGetEntry(i, 0);
                Object obj = colGetEntry;
                result += colGetEntry != null ? colGetEntry.hashCode() : 0;
            }
            return result;
        }
    }

    /* renamed from: android.support.v4.util.MapCollections$MapIterator */
    final class MapIterator implements Iterator<Entry<K, V>>, Entry<K, V> {
        int mEnd;
        boolean mEntryValid = false;
        int mIndex;
        final /* synthetic */ MapCollections this$0;

        MapIterator(MapCollections mapCollections) {
            MapCollections this$02 = mapCollections;
            MapCollections mapCollections2 = this$02;
            this.this$0 = this$02;
            this.mEnd = this$02.colGetSize() - 1;
            this.mIndex = -1;
        }

        public boolean hasNext() {
            return this.mIndex < this.mEnd;
        }

        public Entry<K, V> next() {
            this.mIndex++;
            this.mEntryValid = true;
            return this;
        }

        public void remove() {
            if (this.mEntryValid) {
                this.this$0.colRemoveAt(this.mIndex);
                this.mIndex--;
                this.mEnd--;
                this.mEntryValid = false;
                return;
            }
            throw new IllegalStateException();
        }

        public K getKey() {
            if (this.mEntryValid) {
                return this.this$0.colGetEntry(this.mIndex, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public V getValue() {
            if (this.mEntryValid) {
                return this.this$0.colGetEntry(this.mIndex, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public V setValue(V v) {
            V object = v;
            V v2 = object;
            if (this.mEntryValid) {
                return this.this$0.colSetValue(this.mIndex, object);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final boolean equals(Object obj) {
            Object o = obj;
            Object obj2 = o;
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            } else if (!(o instanceof Entry)) {
                return false;
            } else {
                Entry entry = (Entry) o;
                return ContainerHelpers.equal(entry.getKey(), this.this$0.colGetEntry(this.mIndex, 0)) && ContainerHelpers.equal(entry.getValue(), this.this$0.colGetEntry(this.mIndex, 1));
            }
        }

        public final int hashCode() {
            int i;
            if (this.mEntryValid) {
                Object key = this.this$0.colGetEntry(this.mIndex, 0);
                Object value = this.this$0.colGetEntry(this.mIndex, 1);
                int i2 = key != null ? key.hashCode() : 0;
                if (value != null) {
                    i = value.hashCode();
                } else {
                    i = 0;
                }
                return i2 ^ i;
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /* renamed from: android.support.v4.util.MapCollections$ValuesCollection */
    final class ValuesCollection implements Collection<V> {
        final /* synthetic */ MapCollections this$0;

        ValuesCollection(MapCollections mapCollections) {
            MapCollections this$02 = mapCollections;
            MapCollections mapCollections2 = this$02;
            this.this$0 = this$02;
        }

        public boolean add(V v) {
            V v2 = v;
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends V> collection) {
            Collection<? extends V> collection2 = collection;
            throw new UnsupportedOperationException();
        }

        public void clear() {
            this.this$0.colClear();
        }

        public boolean contains(Object obj) {
            Object object = obj;
            Object obj2 = object;
            return this.this$0.colIndexOfValue(object) >= 0;
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

        public boolean isEmpty() {
            return this.this$0.colGetSize() == 0;
        }

        public Iterator<V> iterator() {
            return new ArrayIterator(this.this$0, 1);
        }

        public boolean remove(Object obj) {
            Object object = obj;
            Object obj2 = object;
            int colIndexOfValue = this.this$0.colIndexOfValue(object);
            int index = colIndexOfValue;
            if (colIndexOfValue < 0) {
                return false;
            }
            this.this$0.colRemoveAt(index);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            Collection<?> collection2 = collection;
            Collection<?> collection3 = collection2;
            int N = this.this$0.colGetSize();
            boolean changed = false;
            int i = 0;
            while (i < N) {
                if (collection2.contains(this.this$0.colGetEntry(i, 1))) {
                    this.this$0.colRemoveAt(i);
                    i--;
                    N--;
                    changed = true;
                }
                i++;
            }
            return changed;
        }

        public boolean retainAll(Collection<?> collection) {
            Collection<?> collection2 = collection;
            Collection<?> collection3 = collection2;
            int N = this.this$0.colGetSize();
            boolean changed = false;
            int i = 0;
            while (i < N) {
                if (!collection2.contains(this.this$0.colGetEntry(i, 1))) {
                    this.this$0.colRemoveAt(i);
                    i--;
                    N--;
                    changed = true;
                }
                i++;
            }
            return changed;
        }

        public int size() {
            return this.this$0.colGetSize();
        }

        public Object[] toArray() {
            return this.this$0.toArrayHelper(1);
        }

        public <T> T[] toArray(T[] tArr) {
            T[] array = tArr;
            T[] tArr2 = array;
            return this.this$0.toArrayHelper(array, 1);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void colClear();

    /* access modifiers changed from: protected */
    public abstract Object colGetEntry(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract Map<K, V> colGetMap();

    /* access modifiers changed from: protected */
    public abstract int colGetSize();

    /* access modifiers changed from: protected */
    public abstract int colIndexOfKey(Object obj);

    /* access modifiers changed from: protected */
    public abstract int colIndexOfValue(Object obj);

    /* access modifiers changed from: protected */
    public abstract void colPut(K k, V v);

    /* access modifiers changed from: protected */
    public abstract void colRemoveAt(int i);

    /* access modifiers changed from: protected */
    public abstract V colSetValue(int i, V v);

    MapCollections() {
    }

    public static <K, V> boolean containsAllHelper(Map<K, V> map, Collection<?> collection) {
        Map<K, V> map2 = map;
        Collection<?> collection2 = collection;
        Map<K, V> map3 = map2;
        Collection<?> collection3 = collection2;
        for (Object containsKey : collection2) {
            if (!map2.containsKey(containsKey)) {
                return false;
            }
        }
        return true;
    }

    public static <K, V> boolean removeAllHelper(Map<K, V> map, Collection<?> collection) {
        Map<K, V> map2 = map;
        Collection<?> collection2 = collection;
        Map<K, V> map3 = map2;
        Collection<?> collection3 = collection2;
        int oldSize = map2.size();
        for (Object remove : collection2) {
            Object remove2 = map2.remove(remove);
        }
        return oldSize != map2.size();
    }

    public static <K, V> boolean retainAllHelper(Map<K, V> map, Collection<?> collection) {
        Map<K, V> map2 = map;
        Collection<?> collection2 = collection;
        Map<K, V> map3 = map2;
        Collection<?> collection3 = collection2;
        int oldSize = map2.size();
        Iterator it = map2.keySet().iterator();
        while (it.hasNext()) {
            if (!collection2.contains(it.next())) {
                it.remove();
            }
        }
        return oldSize != map2.size();
    }

    public Object[] toArrayHelper(int i) {
        int offset = i;
        int i2 = offset;
        int colGetSize = colGetSize();
        int N = colGetSize;
        Object[] result = new Object[colGetSize];
        for (int i3 = 0; i3 < N; i3++) {
            result[i3] = colGetEntry(i3, offset);
        }
        return result;
    }

    public <T> T[] toArrayHelper(T[] tArr, int i) {
        T[] array = tArr;
        int offset = i;
        T[] array2 = array;
        int i2 = offset;
        int N = colGetSize();
        if (array.length < N) {
            T[] tArr2 = (Object[]) Array.newInstance(array.getClass().getComponentType(), N);
            T[] tArr3 = tArr2;
            array2 = tArr2;
        }
        for (int i3 = 0; i3 < N; i3++) {
            array2[i3] = colGetEntry(i3, offset);
        }
        if (array2.length > N) {
            array2[N] = null;
        }
        return array2;
    }

    public static <T> boolean equalsSetHelper(Set<T> set, Object obj) {
        Set<T> set2 = set;
        Object object = obj;
        Set<T> set3 = set2;
        Object obj2 = object;
        if (set2 == object) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        Set set4 = (Set) object;
        try {
            return set2.size() == set4.size() && set2.containsAll(set4);
        } catch (NullPointerException e) {
            NullPointerException nullPointerException = e;
            return false;
        } catch (ClassCastException e2) {
            ClassCastException classCastException = e2;
            return false;
        }
    }

    public Set<Entry<K, V>> getEntrySet() {
        if (this.mEntrySet == null) {
            this.mEntrySet = new EntrySet<>(this);
        }
        return this.mEntrySet;
    }

    public Set<K> getKeySet() {
        if (this.mKeySet == null) {
            this.mKeySet = new KeySet<>(this);
        }
        return this.mKeySet;
    }

    public Collection<V> getValues() {
        if (this.mValues == null) {
            this.mValues = new ValuesCollection<>(this);
        }
        return this.mValues;
    }
}
