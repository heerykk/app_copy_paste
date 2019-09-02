package android.support.p000v4.util;

/* renamed from: android.support.v4.util.CircularArray */
public final class CircularArray<E> {
    private int mCapacityBitmask;
    private E[] mElements;
    private int mHead;
    private int mTail;

    private void doubleCapacity() {
        int length = this.mElements.length;
        int n = length;
        int r = length - this.mHead;
        int i = n << 1;
        int newCapacity = i;
        if (i >= 0) {
            Object[] a = new Object[newCapacity];
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

    public CircularArray() {
        this(8);
    }

    public CircularArray(int i) {
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
            this.mElements = (Object[]) new Object[arrayCapacity];
        } else {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        }
    }

    public void addFirst(E e) {
        E e2 = e;
        E e3 = e2;
        this.mHead = (this.mHead - 1) & this.mCapacityBitmask;
        this.mElements[this.mHead] = e2;
        if (this.mHead == this.mTail) {
            doubleCapacity();
        }
    }

    public void addLast(E e) {
        E e2 = e;
        E e3 = e2;
        this.mElements[this.mTail] = e2;
        this.mTail = (this.mTail + 1) & this.mCapacityBitmask;
        if (this.mTail == this.mHead) {
            doubleCapacity();
        }
    }

    public E popFirst() {
        if (this.mHead != this.mTail) {
            E result = this.mElements[this.mHead];
            this.mElements[this.mHead] = null;
            this.mHead = (this.mHead + 1) & this.mCapacityBitmask;
            return result;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E popLast() {
        if (this.mHead != this.mTail) {
            int i = (this.mTail - 1) & this.mCapacityBitmask;
            int i2 = i;
            E result = this.mElements[i];
            this.mElements[i] = null;
            this.mTail = i;
            return result;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void clear() {
        removeFromStart(size());
    }

    public void removeFromStart(int i) {
        int numOfElements = i;
        int i2 = numOfElements;
        if (numOfElements <= 0) {
            return;
        }
        if (numOfElements <= size()) {
            int end = this.mElements.length;
            if (numOfElements < end - this.mHead) {
                end = this.mHead + numOfElements;
            }
            for (int i3 = this.mHead; i3 < end; i3++) {
                this.mElements[i3] = null;
            }
            int i4 = end - this.mHead;
            int i5 = numOfElements - i4;
            int numOfElements2 = i5;
            this.mHead = (this.mHead + i4) & this.mCapacityBitmask;
            if (i5 > 0) {
                for (int i6 = 0; i6 < numOfElements2; i6++) {
                    this.mElements[i6] = null;
                }
                this.mHead = numOfElements2;
            }
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
            int start = 0;
            if (numOfElements < this.mTail) {
                start = this.mTail - numOfElements;
            }
            for (int i3 = start; i3 < this.mTail; i3++) {
                this.mElements[i3] = null;
            }
            int i4 = this.mTail - start;
            int i5 = numOfElements - i4;
            int numOfElements2 = i5;
            this.mTail -= i4;
            if (i5 > 0) {
                this.mTail = this.mElements.length;
                int i6 = this.mTail - numOfElements2;
                int newTail = i6;
                for (int i7 = i6; i7 < this.mTail; i7++) {
                    this.mElements[i7] = null;
                }
                this.mTail = newTail;
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E getFirst() {
        if (this.mHead != this.mTail) {
            return this.mElements[this.mHead];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E getLast() {
        if (this.mHead != this.mTail) {
            return this.mElements[(this.mTail - 1) & this.mCapacityBitmask];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E get(int i) {
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
