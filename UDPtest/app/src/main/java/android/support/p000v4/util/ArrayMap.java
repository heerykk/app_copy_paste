package android.support.p000v4.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: android.support.v4.util.ArrayMap */
public class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {
    MapCollections<K, V> mCollections;

    public ArrayMap() {
    }

    public ArrayMap(int i) {
        int capacity = i;
        int i2 = capacity;
        super(capacity);
    }

    public ArrayMap(SimpleArrayMap simpleArrayMap) {
        SimpleArrayMap map = simpleArrayMap;
        SimpleArrayMap simpleArrayMap2 = map;
        super(map);
    }

    private MapCollections<K, V> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<K, V>(this) {
                final /* synthetic */ ArrayMap this$0;

                {
                    ArrayMap this$02 = r5;
                    ArrayMap arrayMap = this$02;
                    this.this$0 = this$02;
                }

                /* access modifiers changed from: protected */
                public int colGetSize() {
                    return this.this$0.mSize;
                }

                /* access modifiers changed from: protected */
                public Object colGetEntry(int i, int i2) {
                    int index = i;
                    int offset = i2;
                    int i3 = index;
                    int i4 = offset;
                    return this.this$0.mArray[(index << 1) + offset];
                }

                /* access modifiers changed from: protected */
                public int colIndexOfKey(Object obj) {
                    Object key = obj;
                    Object obj2 = key;
                    return this.this$0.indexOfKey(key);
                }

                /* access modifiers changed from: protected */
                public int colIndexOfValue(Object obj) {
                    Object value = obj;
                    Object obj2 = value;
                    return this.this$0.indexOfValue(value);
                }

                /* access modifiers changed from: protected */
                public Map<K, V> colGetMap() {
                    return this.this$0;
                }

                /* access modifiers changed from: protected */
                public void colPut(K k, V v) {
                    K key = k;
                    V value = v;
                    K k2 = key;
                    V v2 = value;
                    Object put = this.this$0.put(key, value);
                }

                /* access modifiers changed from: protected */
                public V colSetValue(int i, V v) {
                    int index = i;
                    V value = v;
                    int i2 = index;
                    V v2 = value;
                    return this.this$0.setValueAt(index, value);
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

    public boolean containsAll(Collection<?> collection) {
        Collection<?> collection2 = collection;
        Collection<?> collection3 = collection2;
        return MapCollections.containsAllHelper(this, collection2);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        Map<? extends K, ? extends V> map2 = map;
        Map<? extends K, ? extends V> map3 = map2;
        ensureCapacity(this.mSize + map2.size());
        for (Entry entry : map2.entrySet()) {
            Object put = put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(Collection<?> collection) {
        Collection<?> collection2 = collection;
        Collection<?> collection3 = collection2;
        return MapCollections.removeAllHelper(this, collection2);
    }

    public boolean retainAll(Collection<?> collection) {
        Collection<?> collection2 = collection;
        Collection<?> collection3 = collection2;
        return MapCollections.retainAllHelper(this, collection2);
    }

    public Set<Entry<K, V>> entrySet() {
        return getCollection().getEntrySet();
    }

    public Set<K> keySet() {
        return getCollection().getKeySet();
    }

    public Collection<V> values() {
        return getCollection().getValues();
    }
}
