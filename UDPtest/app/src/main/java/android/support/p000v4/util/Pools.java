package android.support.p000v4.util;

/* renamed from: android.support.v4.util.Pools */
public final class Pools {

    /* renamed from: android.support.v4.util.Pools$Pool */
    public interface Pool<T> {
        T acquire();

        boolean release(T t);
    }

    /* renamed from: android.support.v4.util.Pools$SimplePool */
    public static class SimplePool<T> implements Pool<T> {
        private final Object[] mPool;
        private int mPoolSize;

        public SimplePool(int i) {
            int maxPoolSize = i;
            int i2 = maxPoolSize;
            if (maxPoolSize > 0) {
                this.mPool = new Object[maxPoolSize];
                return;
            }
            throw new IllegalArgumentException("The max pool size must be > 0");
        }

        public T acquire() {
            if (this.mPoolSize <= 0) {
                return null;
            }
            int i = this.mPoolSize - 1;
            int i2 = i;
            Object obj = this.mPool[i];
            this.mPool[i] = null;
            this.mPoolSize--;
            return obj;
        }

        public boolean release(T t) {
            T instance = t;
            T t2 = instance;
            if (isInPool(instance)) {
                throw new IllegalStateException("Already in the pool!");
            } else if (this.mPoolSize >= this.mPool.length) {
                return false;
            } else {
                this.mPool[this.mPoolSize] = instance;
                this.mPoolSize++;
                return true;
            }
        }

        private boolean isInPool(T t) {
            T instance = t;
            T t2 = instance;
            for (int i = 0; i < this.mPoolSize; i++) {
                if (this.mPool[i] == instance) {
                    return true;
                }
            }
            return false;
        }
    }

    /* renamed from: android.support.v4.util.Pools$SynchronizedPool */
    public static class SynchronizedPool<T> extends SimplePool<T> {
        private final Object mLock = new Object();

        public SynchronizedPool(int i) {
            int maxPoolSize = i;
            int i2 = maxPoolSize;
            super(maxPoolSize);
        }

        public T acquire() {
            Object obj = this.mLock;
            Object obj2 = obj;
            synchronized (obj) {
                try {
                    T acquire = super.acquire();
                    return acquire;
                } catch (Throwable th) {
                    Object obj3 = obj2;
                    throw th;
                }
            }
        }

        public boolean release(T t) {
            T element = t;
            T t2 = element;
            Object obj = this.mLock;
            boolean z = obj;
            synchronized (obj) {
                try {
                    z = super.release(element);
                    return z;
                } finally {
                    boolean z2 = z;
                }
            }
        }
    }

    private Pools() {
    }
}
