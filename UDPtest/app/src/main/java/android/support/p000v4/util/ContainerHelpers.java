package android.support.p000v4.util;

/* renamed from: android.support.v4.util.ContainerHelpers */
class ContainerHelpers {
    static final int[] EMPTY_INTS = new int[0];
    static final long[] EMPTY_LONGS = new long[0];
    static final Object[] EMPTY_OBJECTS = new Object[0];

    ContainerHelpers() {
    }

    public static int idealIntArraySize(int i) {
        int need = i;
        int i2 = need;
        return idealByteArraySize(need * 4) / 4;
    }

    public static int idealLongArraySize(int i) {
        int need = i;
        int i2 = need;
        return idealByteArraySize(need * 8) / 8;
    }

    public static int idealByteArraySize(int i) {
        int need = i;
        int i2 = need;
        for (int i3 = 4; i3 < 32; i3++) {
            if (need <= (1 << i3) - 12) {
                return (1 << i3) - 12;
            }
        }
        return need;
    }

    public static boolean equal(Object obj, Object obj2) {
        Object a = obj;
        Object b = obj2;
        Object obj3 = a;
        Object obj4 = b;
        return a == b || (a != null && a.equals(b));
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

    static int binarySearch(long[] jArr, int i, long j) {
        long[] array = jArr;
        int size = i;
        long value = j;
        long[] jArr2 = array;
        int i2 = size;
        long j2 = value;
        int lo = 0;
        int hi = size - 1;
        while (lo <= hi) {
            int i3 = (lo + hi) >>> 1;
            int i4 = i3;
            long j3 = array[i3];
            long midVal = j3;
            if (!(j3 >= value)) {
                lo = i3 + 1;
            } else {
                if (midVal <= value) {
                    return i3;
                }
                hi = i3 - 1;
            }
        }
        return lo ^ -1;
    }
}
