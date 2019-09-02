package android.support.p000v4.util;

import java.util.LinkedHashMap;
import java.util.Map;

/* renamed from: android.support.v4.util.LruCache */
public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int i) {
        int maxSize2 = i;
        int i2 = maxSize2;
        if (maxSize2 > 0) {
            this.maxSize = maxSize2;
            this.map = new LinkedHashMap<>(0, 0.75f, true);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    public void resize(int i) {
        int maxSize2 = i;
        int i2 = maxSize2;
        if (maxSize2 > 0) {
            synchronized (this) {
                try {
                    this.maxSize = maxSize2;
                } catch (Throwable th) {
                    while (true) {
                        throw th;
                    }
                }
            }
            trimToSize(maxSize2);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0023, code lost:
        if (r6 == null) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0026, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r1.createCount++;
        r6 = r1.map.put(r2, r13);
        r9 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        if (r6 != null) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0037, code lost:
        r1.size += safeSizeOf(r2, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0043, code lost:
        if (r9 != null) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0045, code lost:
        trimToSize(r1.maxSize);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004a, code lost:
        return r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0064, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r6 = r1.map.put(r2, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x006c, code lost:
        r16 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006d, code lost:
        r6 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x006f, code lost:
        throw r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0070, code lost:
        entryRemoved(false, r2, r13, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0077, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001d, code lost:
        r6 = create(r2);
        r13 = r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V get(K r19) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r3 = r1
            r4 = r2
            r5 = 0
            if (r2 == r5) goto L_0x004b
            r6 = r1
            monitor-enter(r6)
            java.util.LinkedHashMap<K, V> r6 = r1.map     // Catch:{ all -> 0x005e }
            java.lang.Object r6 = r6.get(r2)     // Catch:{ all -> 0x005e }
            r9 = r6
            r5 = 0
            if (r6 != r5) goto L_0x0055
            int r10 = r1.missCount     // Catch:{ all -> 0x005e }
            int r10 = r10 + 1
            r1.missCount = r10     // Catch:{ all -> 0x005e }
            r6 = r1
            monitor-exit(r6)     // Catch:{ all -> 0x005e }
            java.lang.Object r6 = r1.create(r2)
            r13 = r6
            r5 = 0
            if (r6 == r5) goto L_0x0062
            r6 = r1
            monitor-enter(r6)
            int r10 = r1.createCount     // Catch:{ all -> 0x006c }
            int r10 = r10 + 1
            r1.createCount = r10     // Catch:{ all -> 0x006c }
            java.util.LinkedHashMap<K, V> r6 = r1.map     // Catch:{ all -> 0x006c }
            java.lang.Object r6 = r6.put(r2, r13)     // Catch:{ all -> 0x006c }
            r9 = r6
            r5 = 0
            if (r6 != r5) goto L_0x0065
            int r10 = r1.size     // Catch:{ all -> 0x006c }
            int r15 = r1.safeSizeOf(r2, r13)     // Catch:{ all -> 0x006c }
            int r10 = r10 + r15
            r1.size = r10     // Catch:{ all -> 0x006c }
        L_0x0040:
            r6 = r1
            monitor-exit(r6)     // Catch:{ all -> 0x006c }
            r5 = 0
            if (r9 != r5) goto L_0x0070
            int r10 = r1.maxSize
            r1.trimToSize(r10)
            return r13
        L_0x004b:
            java.lang.NullPointerException r6 = new java.lang.NullPointerException
            java.lang.String r7 = "key == null"
            r8 = r7
            r6.<init>(r8)
            throw r6
        L_0x0055:
            int r10 = r1.hitCount     // Catch:{ all -> 0x005e }
            int r10 = r10 + 1
            r1.hitCount = r10     // Catch:{ all -> 0x005e }
            r11 = r1
            monitor-exit(r11)     // Catch:{ all -> 0x005e }
            return r9
        L_0x005e:
            r12 = move-exception
            r6 = r1
            monitor-exit(r6)     // Catch:{ all -> 0x005e }
            throw r12
        L_0x0062:
            r6 = 0
            r14 = 0
            return r14
        L_0x0065:
            java.util.LinkedHashMap<K, V> r6 = r1.map     // Catch:{ all -> 0x006c }
            java.lang.Object r6 = r6.put(r2, r9)     // Catch:{ all -> 0x006c }
            goto L_0x0040
        L_0x006c:
            r16 = move-exception
            r6 = r1
            monitor-exit(r6)     // Catch:{ all -> 0x006c }
            throw r16
        L_0x0070:
            r17 = 0
            r0 = r17
            r1.entryRemoved(r0, r2, r13, r9)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.util.LruCache.get(java.lang.Object):java.lang.Object");
    }

    public final V put(K k, V v) {
        Object obj;
        K key = k;
        V value = v;
        K k2 = key;
        V v2 = value;
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            try {
                this.putCount++;
                this.size += safeSizeOf(key, value);
                Object put = this.map.put(key, value);
                obj = put;
                if (put != null) {
                    this.size -= safeSizeOf(key, obj);
                }
            } finally {
            }
        }
        if (obj != null) {
            entryRemoved(false, key, obj, value);
        }
        trimToSize(this.maxSize);
        return obj;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0034, code lost:
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void trimToSize(int r28) {
        /*
            r27 = this;
            r4 = r27
            r5 = r28
            r6 = r4
            r7 = r5
        L_0x0006:
            r8 = r4
            monitor-enter(r8)
            int r9 = r4.size     // Catch:{ all -> 0x0035 }
            r10 = 0
            if (r9 >= r10) goto L_0x0039
        L_0x000d:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0035 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0035 }
            r12.<init>()     // Catch:{ all -> 0x0035 }
            java.lang.Class r13 = r4.getClass()     // Catch:{ all -> 0x0035 }
            java.lang.String r13 = r13.getName()     // Catch:{ all -> 0x0035 }
            r14 = r13
            java.lang.StringBuilder r12 = r12.append(r14)     // Catch:{ all -> 0x0035 }
            java.lang.String r13 = ".sizeOf() is reporting inconsistent results!"
            r15 = r13
            java.lang.StringBuilder r12 = r12.append(r15)     // Catch:{ all -> 0x0035 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0035 }
            r16 = r12
            r0 = r16
            r8.<init>(r0)     // Catch:{ all -> 0x0035 }
            throw r8     // Catch:{ all -> 0x0035 }
        L_0x0035:
            r23 = move-exception
            r8 = r4
            monitor-exit(r8)     // Catch:{ all -> 0x0035 }
            throw r23
        L_0x0039:
            java.util.LinkedHashMap<K, V> r8 = r4.map     // Catch:{ all -> 0x0035 }
            boolean r11 = r8.isEmpty()     // Catch:{ all -> 0x0035 }
            r9 = r11
            r10 = 0
            if (r9 != r10) goto L_0x004a
        L_0x0043:
            int r9 = r4.size     // Catch:{ all -> 0x0035 }
            if (r9 > r5) goto L_0x0050
        L_0x0047:
            r8 = r4
            monitor-exit(r8)     // Catch:{ all -> 0x0035 }
            return
        L_0x004a:
            int r9 = r4.size     // Catch:{ all -> 0x0035 }
            r10 = 0
            if (r9 != r10) goto L_0x000d
            goto L_0x0043
        L_0x0050:
            java.util.LinkedHashMap<K, V> r8 = r4.map     // Catch:{ all -> 0x0035 }
            boolean r17 = r8.isEmpty()     // Catch:{ all -> 0x0035 }
            r9 = r17
            r10 = 0
            if (r9 != r10) goto L_0x0047
            java.util.LinkedHashMap<K, V> r8 = r4.map     // Catch:{ all -> 0x0035 }
            java.util.Set r8 = r8.entrySet()     // Catch:{ all -> 0x0035 }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ all -> 0x0035 }
            java.lang.Object r8 = r8.next()     // Catch:{ all -> 0x0035 }
            java.util.Map$Entry r8 = (java.util.Map.Entry) r8     // Catch:{ all -> 0x0035 }
            r18 = r8
            java.lang.Object r8 = r8.getKey()     // Catch:{ all -> 0x0035 }
            r19 = r8
            java.lang.Object r8 = r18.getValue()     // Catch:{ all -> 0x0035 }
            r20 = r8
            java.util.LinkedHashMap<K, V> r8 = r4.map     // Catch:{ all -> 0x0035 }
            r0 = r19
            java.lang.Object r8 = r8.remove(r0)     // Catch:{ all -> 0x0035 }
            int r0 = r4.size     // Catch:{ all -> 0x0035 }
            r21 = r0
            r0 = r19
            r1 = r20
            int r22 = r4.safeSizeOf(r0, r1)     // Catch:{ all -> 0x0035 }
            int r21 = r21 - r22
            r0 = r21
            r4.size = r0     // Catch:{ all -> 0x0035 }
            int r0 = r4.evictionCount     // Catch:{ all -> 0x0035 }
            r21 = r0
            int r21 = r21 + 1
            r0 = r21
            r4.evictionCount = r0     // Catch:{ all -> 0x0035 }
            r8 = r4
            monitor-exit(r8)     // Catch:{ all -> 0x0035 }
            r24 = 0
            r25 = 1
            r26 = 0
            r0 = r25
            r1 = r19
            r2 = r20
            r3 = r26
            r4.entryRemoved(r0, r1, r2, r3)
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.util.LruCache.trimToSize(int):void");
    }

    public final V remove(K k) {
        Object obj;
        K key = k;
        K k2 = key;
        if (key != null) {
            synchronized (this) {
                try {
                    Object remove = this.map.remove(key);
                    obj = remove;
                    if (remove != null) {
                        this.size -= safeSizeOf(key, obj);
                    }
                } finally {
                }
            }
            if (obj != null) {
                entryRemoved(false, key, obj, null);
            }
            return obj;
        }
        throw new NullPointerException("key == null");
    }

    /* access modifiers changed from: protected */
    public void entryRemoved(boolean z, K k, V v, V v2) {
        boolean z2 = z;
        K k2 = k;
        V v3 = v;
        V v4 = v2;
    }

    /* access modifiers changed from: protected */
    public V create(K k) {
        K k2 = k;
        return null;
    }

    private int safeSizeOf(K k, V v) {
        K key = k;
        V value = v;
        K k2 = key;
        V v2 = value;
        int sizeOf = sizeOf(key, value);
        int result = sizeOf;
        if (sizeOf >= 0) {
            return result;
        }
        throw new IllegalStateException("Negative size: " + key + "=" + value);
    }

    /* access modifiers changed from: protected */
    public int sizeOf(K k, V v) {
        K k2 = k;
        V v2 = v;
        return 1;
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        int i;
        synchronized (this) {
            i = this.size;
        }
        return i;
    }

    public final synchronized int maxSize() {
        int i;
        synchronized (this) {
            i = this.maxSize;
        }
        return i;
    }

    public final synchronized int hitCount() {
        int i;
        synchronized (this) {
            i = this.hitCount;
        }
        return i;
    }

    public final synchronized int missCount() {
        int i;
        synchronized (this) {
            i = this.missCount;
        }
        return i;
    }

    public final synchronized int createCount() {
        int i;
        synchronized (this) {
            i = this.createCount;
        }
        return i;
    }

    public final synchronized int putCount() {
        int i;
        synchronized (this) {
            i = this.putCount;
        }
        return i;
    }

    public final synchronized int evictionCount() {
        int i;
        synchronized (this) {
            i = this.evictionCount;
        }
        return i;
    }

    public final synchronized Map<K, V> snapshot() {
        LinkedHashMap linkedHashMap;
        synchronized (this) {
            linkedHashMap = new LinkedHashMap(this.map);
        }
        return linkedHashMap;
    }

    public final synchronized String toString() {
        String format;
        synchronized (this) {
            int i = this.hitCount + this.missCount;
            int hitPercent = i == 0 ? 0 : (100 * this.hitCount) / i;
            Object[] objArr = new Object[4];
            objArr[0] = Integer.valueOf(this.maxSize);
            objArr[1] = Integer.valueOf(this.hitCount);
            objArr[2] = Integer.valueOf(this.missCount);
            objArr[3] = Integer.valueOf(hitPercent);
            format = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", objArr);
        }
        return format;
    }
}
