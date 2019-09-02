package android.support.p003v7.content.res;

import java.lang.reflect.Array;

/* renamed from: android.support.v7.content.res.GrowingArrayUtils */
final class GrowingArrayUtils {
    static final /* synthetic */ boolean $assertionsDisabled = (!GrowingArrayUtils.class.desiredAssertionStatus());

    public static <T> T[] append(T[] tArr, int i, T t) {
        T[] array = tArr;
        int currentSize = i;
        T element = t;
        T[] array2 = array;
        int i2 = currentSize;
        T t2 = element;
        if (!$assertionsDisabled && currentSize > array.length) {
            throw new AssertionError();
        }
        if (currentSize + 1 > array.length) {
            T[] newArray = (Object[]) Array.newInstance(array.getClass().getComponentType(), growSize(currentSize));
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array2 = newArray;
        }
        array2[currentSize] = element;
        return array2;
    }

    public static int[] append(int[] iArr, int i, int i2) {
        int[] array = iArr;
        int currentSize = i;
        int element = i2;
        int[] array2 = array;
        int i3 = currentSize;
        int i4 = element;
        if (!$assertionsDisabled && currentSize > array.length) {
            throw new AssertionError();
        }
        if (currentSize + 1 > array.length) {
            int[] newArray = new int[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array2 = newArray;
        }
        array2[currentSize] = element;
        return array2;
    }

    public static long[] append(long[] jArr, int i, long j) {
        long[] array = jArr;
        int currentSize = i;
        long element = j;
        long[] array2 = array;
        int i2 = currentSize;
        long j2 = element;
        if (!$assertionsDisabled && currentSize > array.length) {
            throw new AssertionError();
        }
        if (currentSize + 1 > array.length) {
            long[] newArray = new long[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array2 = newArray;
        }
        array2[currentSize] = element;
        return array2;
    }

    public static boolean[] append(boolean[] zArr, int i, boolean z) {
        boolean[] array = zArr;
        int currentSize = i;
        boolean[] array2 = array;
        int i2 = currentSize;
        boolean element = z;
        if (!$assertionsDisabled && currentSize > array.length) {
            throw new AssertionError();
        }
        if (currentSize + 1 > array.length) {
            boolean[] newArray = new boolean[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array2 = newArray;
        }
        array2[currentSize] = element;
        return array2;
    }

    public static <T> T[] insert(T[] tArr, int i, int i2, T t) {
        T[] array = tArr;
        int currentSize = i;
        int index = i2;
        T element = t;
        T[] tArr2 = array;
        int i3 = currentSize;
        int i4 = index;
        T t2 = element;
        if (!$assertionsDisabled && currentSize > array.length) {
            throw new AssertionError();
        } else if (currentSize + 1 > array.length) {
            Object[] objArr = (Object[]) Array.newInstance(array.getClass().getComponentType(), growSize(currentSize));
            System.arraycopy(array, 0, objArr, 0, index);
            objArr[index] = element;
            System.arraycopy(array, index, objArr, index + 1, array.length - index);
            return objArr;
        } else {
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        }
    }

    public static int[] insert(int[] iArr, int i, int i2, int i3) {
        int[] array = iArr;
        int currentSize = i;
        int index = i2;
        int element = i3;
        int[] iArr2 = array;
        int i4 = currentSize;
        int i5 = index;
        int i6 = element;
        if (!$assertionsDisabled && currentSize > array.length) {
            throw new AssertionError();
        } else if (currentSize + 1 > array.length) {
            int[] newArray = new int[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, index);
            newArray[index] = element;
            System.arraycopy(array, index, newArray, index + 1, array.length - index);
            return newArray;
        } else {
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        }
    }

    public static long[] insert(long[] jArr, int i, int i2, long j) {
        long[] array = jArr;
        int currentSize = i;
        int index = i2;
        long element = j;
        long[] jArr2 = array;
        int i3 = currentSize;
        int i4 = index;
        long j2 = element;
        if (!$assertionsDisabled && currentSize > array.length) {
            throw new AssertionError();
        } else if (currentSize + 1 > array.length) {
            long[] newArray = new long[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, index);
            newArray[index] = element;
            System.arraycopy(array, index, newArray, index + 1, array.length - index);
            return newArray;
        } else {
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        }
    }

    public static boolean[] insert(boolean[] zArr, int i, int i2, boolean z) {
        boolean[] array = zArr;
        int currentSize = i;
        int index = i2;
        boolean[] zArr2 = array;
        int i3 = currentSize;
        int i4 = index;
        boolean element = z;
        if (!$assertionsDisabled && currentSize > array.length) {
            throw new AssertionError();
        } else if (currentSize + 1 > array.length) {
            boolean[] newArray = new boolean[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, index);
            newArray[index] = element;
            System.arraycopy(array, index, newArray, index + 1, array.length - index);
            return newArray;
        } else {
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        }
    }

    public static int growSize(int i) {
        int currentSize = i;
        int i2 = currentSize;
        return currentSize > 4 ? currentSize * 2 : 8;
    }

    private GrowingArrayUtils() {
    }
}
