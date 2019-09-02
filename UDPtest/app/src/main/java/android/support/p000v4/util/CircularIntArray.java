package android.support.p000v4.util;

/* renamed from: android.support.v4.util.CircularIntArray */
public final class CircularIntArray {
    private int mCapacityBitmask;
    private int[] mElements;
    private int mHead;
    private int mTail;

    private void doubleCapacity() {
        int length = this.mElements.length;
        int n = length;
        int r = length - this.mHead;
        int i = n << 1;
        int newCapacity = i;
        if (i >= 0) {
            int[] a = new int[newCapacity];
            System.arraycopy(this.mElements, this.mHead, a, 0, r);
            System.arraycopy(this.mElements, 0, a, r, this.mHead);
            this.mElements = a;
            this.mHead = 0;
            this.mTail = n;
            this.mCapacityBitmask = newCapacity - 1;
            return;
        }
        throw new RuntimeException("Max array capacity exceeded");
    }

    public CircularIntArray() {
        this(8);
    }

    public CircularIntArray(int i) {
        int arrayCapacity;
        int minCapacity = i;
        int i2 = minCapacity;
        if (minCapacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        } else if (minCapacity <= 1073741824) {
            if (Integer.bitCount(minCapacity) == 1) {
                arrayCapacity = minCapacity;
            } else {
                arrayCapacity = Integer.highestOneBit(minCapacity - 1) << 1;
            }
            this.mCapacityBitmask = arrayCapacity - 1;
            this.mElements = new int[arrayCapacity];
        } else {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        }
    }

    public void addFirst(int i) {
        int e = i;
        int i2 = e;
        this.mHead = (this.mHead - 1) & this.mCapacityBitmask;
        this.mElements[this.mHead] = e;
        if (this.mHead == this.mTail) {
            doubleCapacity();
        }
    }

    public void addLast(int i) {
        int e = i;
        int i2 = e;
        this.mElements[this.mTail] = e;
        this.mTail = (this.mTail + 1) & this.mCapacityBitmask;
        if (this.mTail == this.mHead) {
            doubleCapacity();
        }
    }

    public int popFirst() {
        if (this.mHead != this.mTail) {
            int i = this.mElements[this.mHead];
            int i2 = i;
            this.mHead = (this.mHead + 1) & this.mCapacityBitmask;
            return i;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int popLast() {
        if (this.mHead != this.mTail) {
            int i = (this.mTail - 1) & this.mCapacityBitmask;
            int t = i;
            int i2 = this.mElements[i];
            int i3 = i2;
            this.mTail = t;
            return i2;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void clear() {
        this.mTail = this.mHead;
    }

    public void removeFromStart(int i) {
        int numOfElements = i;
        int i2 = numOfElements;
        if (numOfElements <= 0) {
            return;
        }
        if (numOfElements <= size()) {
            this.mHead = (this.mHead + numOfElements) & this.mCapacityBitmask;
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void removeFromEnd(int i) {
        int numOfElements = i;
        int i2 = numOfElements;
        if (numOfElements <= 0) {
            return;
        }
        if (numOfElements <= size()) {
            this.mTail = (this.mTail - numOfElements) & this.mCapacityBitmask;
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getFirst() {
        if (this.mHead != this.mTail) {
            return this.mElements[this.mHead];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getLast() {
        if (this.mHead != this.mTail) {
            return this.mElements[(this.mTail - 1) & this.mCapacityBitmask];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int get(int i) {
        int n = i;
        int i2 = n;
        if (n >= 0 && n < size()) {
            return this.mElements[(this.mHead + n) & this.mCapacityBitmask];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int size() {
        return (this.mTail - this.mHead) & this.mCapacityBitmask;
    }

    public boolean isEmpty() {
        return this.mHead == this.mTail;
    }
}
