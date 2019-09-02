package android.support.p003v7.widget;

import android.support.p003v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v7.widget.ChildHelper */
class ChildHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "ChildrenHelper";
    final Bucket mBucket = new Bucket();
    final Callback mCallback;
    final List<View> mHiddenViews = new ArrayList();

    /* renamed from: android.support.v7.widget.ChildHelper$Bucket */
    static class Bucket {
        static final int BITS_PER_WORD = 64;
        static final long LAST_BIT = Long.MIN_VALUE;
        long mData = 0;
        Bucket next;

        Bucket() {
        }

        /* access modifiers changed from: 0000 */
        public void set(int i) {
            int index = i;
            int i2 = index;
            if (index < 64) {
                this.mData |= 1 << index;
                return;
            }
            ensureNext();
            this.next.set(index - 64);
        }

        private void ensureNext() {
            if (this.next == null) {
                this.next = new Bucket();
            }
        }

        /* access modifiers changed from: 0000 */
        public void clear(int i) {
            int index = i;
            int i2 = index;
            if (index < 64) {
                this.mData &= (1 << index) ^ -1;
            } else if (this.next != null) {
                this.next.clear(index - 64);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean get(int i) {
            int index = i;
            int i2 = index;
            if (index < 64) {
                return (this.mData & (1 << index)) != 0;
            }
            ensureNext();
            return this.next.get(index - 64);
        }

        /* access modifiers changed from: 0000 */
        public void reset() {
            this.mData = 0;
            if (this.next != null) {
                this.next.reset();
            }
        }

        /* access modifiers changed from: 0000 */
        public void insert(int i, boolean z) {
            int index = i;
            int i2 = index;
            boolean value = z;
            if (index < 64) {
                boolean lastBit = (this.mData & LAST_BIT) != 0;
                long mask = (1 << index) - 1;
                long j = (this.mData & (mask ^ -1)) << 1;
                long j2 = j;
                this.mData = (this.mData & mask) | j;
                if (!value) {
                    clear(index);
                } else {
                    set(index);
                }
                if (lastBit || this.next != null) {
                    ensureNext();
                    this.next.insert(0, lastBit);
                    return;
                }
                return;
            }
            ensureNext();
            this.next.insert(index - 64, value);
        }

        /* access modifiers changed from: 0000 */
        public boolean remove(int i) {
            int index = i;
            int i2 = index;
            if (index < 64) {
                long mask = 1 << index;
                boolean value = (this.mData & mask) != 0;
                this.mData &= mask ^ -1;
                long mask2 = mask - 1;
                long before = this.mData & mask2;
                long rotateRight = Long.rotateRight(this.mData & (mask2 ^ -1), 1);
                long j = rotateRight;
                this.mData = before | rotateRight;
                if (this.next != null) {
                    if (this.next.get(0)) {
                        set(63);
                    }
                    boolean remove = this.next.remove(0);
                }
                return value;
            }
            ensureNext();
            return this.next.remove(index - 64);
        }

        /* access modifiers changed from: 0000 */
        public int countOnesBefore(int i) {
            int index = i;
            int i2 = index;
            if (this.next != null) {
                if (index >= 64) {
                    return this.next.countOnesBefore(index - 64) + Long.bitCount(this.mData);
                }
                return Long.bitCount(this.mData & ((1 << index) - 1));
            } else if (index < 64) {
                return Long.bitCount(this.mData & ((1 << index) - 1));
            } else {
                return Long.bitCount(this.mData);
            }
        }

        public String toString() {
            String binaryString;
            if (this.next != null) {
                binaryString = this.next.toString() + "xx" + Long.toBinaryString(this.mData);
            } else {
                binaryString = Long.toBinaryString(this.mData);
            }
            return binaryString;
        }
    }

    /* renamed from: android.support.v7.widget.ChildHelper$Callback */
    interface Callback {
        void addView(View view, int i);

        void attachViewToParent(View view, int i, LayoutParams layoutParams);

        void detachViewFromParent(int i);

        View getChildAt(int i);

        int getChildCount();

        ViewHolder getChildViewHolder(View view);

        int indexOfChild(View view);

        void onEnteredHiddenState(View view);

        void onLeftHiddenState(View view);

        void removeAllViews();

        void removeViewAt(int i);
    }

    ChildHelper(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        this.mCallback = callback2;
    }

    private void hideViewInternal(View view) {
        View child = view;
        View view2 = child;
        boolean add = this.mHiddenViews.add(child);
        this.mCallback.onEnteredHiddenState(child);
    }

    private boolean unhideViewInternal(View view) {
        View child = view;
        View view2 = child;
        if (!this.mHiddenViews.remove(child)) {
            return false;
        }
        this.mCallback.onLeftHiddenState(child);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void addView(View view, boolean z) {
        View child = view;
        View view2 = child;
        addView(child, -1, z);
    }

    /* access modifiers changed from: 0000 */
    public void addView(View view, int i, boolean z) {
        int offset;
        View child = view;
        int index = i;
        View view2 = child;
        int i2 = index;
        boolean hidden = z;
        if (index >= 0) {
            offset = getOffset(index);
        } else {
            offset = this.mCallback.getChildCount();
        }
        this.mBucket.insert(offset, hidden);
        if (hidden) {
            hideViewInternal(child);
        }
        this.mCallback.addView(child, offset);
    }

    private int getOffset(int i) {
        int index = i;
        int i2 = index;
        if (index < 0) {
            return -1;
        }
        int limit = this.mCallback.getChildCount();
        int offset = index;
        while (offset < limit) {
            int countOnesBefore = index - (offset - this.mBucket.countOnesBefore(offset));
            int diff = countOnesBefore;
            if (countOnesBefore != 0) {
                offset += diff;
            } else {
                while (this.mBucket.get(offset)) {
                    offset++;
                }
                return offset;
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public void removeView(View view) {
        View view2 = view;
        View view3 = view2;
        int indexOfChild = this.mCallback.indexOfChild(view2);
        int index = indexOfChild;
        if (indexOfChild >= 0) {
            if (this.mBucket.remove(index)) {
                boolean unhideViewInternal = unhideViewInternal(view2);
            }
            this.mCallback.removeViewAt(index);
        }
    }

    /* access modifiers changed from: 0000 */
    public void removeViewAt(int i) {
        int index = i;
        int i2 = index;
        int offset = getOffset(index);
        int offset2 = offset;
        View childAt = this.mCallback.getChildAt(offset);
        View view = childAt;
        if (childAt != null) {
            if (this.mBucket.remove(offset)) {
                boolean unhideViewInternal = unhideViewInternal(view);
            }
            this.mCallback.removeViewAt(offset2);
        }
    }

    /* access modifiers changed from: 0000 */
    public View getChildAt(int i) {
        int index = i;
        int i2 = index;
        int offset = getOffset(index);
        int i3 = offset;
        return this.mCallback.getChildAt(offset);
    }

    /* access modifiers changed from: 0000 */
    public void removeAllViewsUnfiltered() {
        this.mBucket.reset();
        for (int i = this.mHiddenViews.size() - 1; i >= 0; i--) {
            this.mCallback.onLeftHiddenState((View) this.mHiddenViews.get(i));
            Object remove = this.mHiddenViews.remove(i);
        }
        this.mCallback.removeAllViews();
    }

    /* access modifiers changed from: 0000 */
    public View findHiddenNonRemovedView(int i) {
        int position = i;
        int i2 = position;
        int count = this.mHiddenViews.size();
        for (int i3 = 0; i3 < count; i3++) {
            View view = (View) this.mHiddenViews.get(i3);
            ViewHolder childViewHolder = this.mCallback.getChildViewHolder(view);
            ViewHolder holder = childViewHolder;
            if (childViewHolder.getLayoutPosition() == position && !holder.isInvalid() && !holder.isRemoved()) {
                return view;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void attachViewToParent(View view, int i, LayoutParams layoutParams, boolean z) {
        int offset;
        View child = view;
        int index = i;
        LayoutParams layoutParams2 = layoutParams;
        View view2 = child;
        int i2 = index;
        LayoutParams layoutParams3 = layoutParams2;
        boolean hidden = z;
        if (index >= 0) {
            offset = getOffset(index);
        } else {
            offset = this.mCallback.getChildCount();
        }
        this.mBucket.insert(offset, hidden);
        if (hidden) {
            hideViewInternal(child);
        }
        this.mCallback.attachViewToParent(child, offset, layoutParams2);
    }

    /* access modifiers changed from: 0000 */
    public int getChildCount() {
        return this.mCallback.getChildCount() - this.mHiddenViews.size();
    }

    /* access modifiers changed from: 0000 */
    public int getUnfilteredChildCount() {
        return this.mCallback.getChildCount();
    }

    /* access modifiers changed from: 0000 */
    public View getUnfilteredChildAt(int i) {
        int index = i;
        int i2 = index;
        return this.mCallback.getChildAt(index);
    }

    /* access modifiers changed from: 0000 */
    public void detachViewFromParent(int i) {
        int index = i;
        int i2 = index;
        int offset = getOffset(index);
        int offset2 = offset;
        boolean remove = this.mBucket.remove(offset);
        this.mCallback.detachViewFromParent(offset2);
    }

    /* access modifiers changed from: 0000 */
    public int indexOfChild(View view) {
        View child = view;
        View view2 = child;
        int indexOfChild = this.mCallback.indexOfChild(child);
        int index = indexOfChild;
        if (indexOfChild == -1) {
            return -1;
        }
        if (!this.mBucket.get(index)) {
            return index - this.mBucket.countOnesBefore(index);
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public boolean isHidden(View view) {
        View view2 = view;
        View view3 = view2;
        return this.mHiddenViews.contains(view2);
    }

    /* access modifiers changed from: 0000 */
    public void hide(View view) {
        View view2 = view;
        View view3 = view2;
        int indexOfChild = this.mCallback.indexOfChild(view2);
        int offset = indexOfChild;
        if (indexOfChild >= 0) {
            this.mBucket.set(offset);
            hideViewInternal(view2);
            return;
        }
        throw new IllegalArgumentException("view is not a child, cannot hide " + view2);
    }

    /* access modifiers changed from: 0000 */
    public void unhide(View view) {
        View view2 = view;
        View view3 = view2;
        int indexOfChild = this.mCallback.indexOfChild(view2);
        int offset = indexOfChild;
        if (indexOfChild < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view2);
        } else if (this.mBucket.get(offset)) {
            this.mBucket.clear(offset);
            boolean unhideViewInternal = unhideViewInternal(view2);
        } else {
            throw new RuntimeException("trying to unhide a view that was not hidden" + view2);
        }
    }

    public String toString() {
        return this.mBucket.toString() + ", hidden list:" + this.mHiddenViews.size();
    }

    /* access modifiers changed from: 0000 */
    public boolean removeViewIfHidden(View view) {
        View view2 = view;
        View view3 = view2;
        int indexOfChild = this.mCallback.indexOfChild(view2);
        int index = indexOfChild;
        if (indexOfChild == -1) {
            if (!unhideViewInternal(view2)) {
            }
            return true;
        } else if (!this.mBucket.get(index)) {
            return false;
        } else {
            boolean remove = this.mBucket.remove(index);
            if (unhideViewInternal(view2)) {
            }
            this.mCallback.removeViewAt(index);
            return true;
        }
    }
}
