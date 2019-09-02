package android.support.p003v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.p003v7.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry;
import android.support.p003v7.widget.RecyclerView.Recycler;
import android.support.p003v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.Arrays;

/* renamed from: android.support.v7.widget.GridLayoutManager */
public class GridLayoutManager extends LinearLayoutManager {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_SPAN_COUNT = -1;
    private static final String TAG = "GridLayoutManager";
    int[] mCachedBorders;
    final Rect mDecorInsets = new Rect();
    boolean mPendingSpanCountChange = false;
    final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
    final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
    View[] mSet;
    int mSpanCount = -1;
    SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();

    /* renamed from: android.support.v7.widget.GridLayoutManager$DefaultSpanSizeLookup */
    public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
        public DefaultSpanSizeLookup() {
        }

        public int getSpanSize(int i) {
            int i2 = i;
            return 1;
        }

        public int getSpanIndex(int i, int i2) {
            int position = i;
            int spanCount = i2;
            int i3 = position;
            int i4 = spanCount;
            return position % spanCount;
        }
    }

    /* renamed from: android.support.v7.widget.GridLayoutManager$LayoutParams */
    public static class LayoutParams extends android.support.p003v7.widget.RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        int mSpanIndex = -1;
        int mSpanSize = 0;

        public LayoutParams(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            super(width, height);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            Context c = context;
            AttributeSet attrs = attributeSet;
            Context context2 = c;
            AttributeSet attributeSet2 = attrs;
            super(c, attrs);
        }

        public LayoutParams(android.support.p003v7.widget.RecyclerView.LayoutParams layoutParams) {
            android.support.p003v7.widget.RecyclerView.LayoutParams source = layoutParams;
            android.support.p003v7.widget.RecyclerView.LayoutParams layoutParams2 = source;
            super(source);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.view.ViewGroup.LayoutParams source = layoutParams;
            android.view.ViewGroup.LayoutParams layoutParams2 = source;
            super(source);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams source = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = source;
            super(source);
        }

        public int getSpanIndex() {
            return this.mSpanIndex;
        }

        public int getSpanSize() {
            return this.mSpanSize;
        }
    }

    /* renamed from: android.support.v7.widget.GridLayoutManager$SpanSizeLookup */
    public static abstract class SpanSizeLookup {
        private boolean mCacheSpanIndices = false;
        final SparseIntArray mSpanIndexCache = new SparseIntArray();

        public abstract int getSpanSize(int i);

        public SpanSizeLookup() {
        }

        public void setSpanIndexCacheEnabled(boolean z) {
            this.mCacheSpanIndices = z;
        }

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }

        public boolean isSpanIndexCacheEnabled() {
            return this.mCacheSpanIndices;
        }

        /* access modifiers changed from: 0000 */
        public int getCachedSpanIndex(int i, int i2) {
            int position = i;
            int spanCount = i2;
            int i3 = position;
            int i4 = spanCount;
            if (!this.mCacheSpanIndices) {
                return getSpanIndex(position, spanCount);
            }
            int i5 = this.mSpanIndexCache.get(position, -1);
            int existing = i5;
            if (i5 != -1) {
                return existing;
            }
            int spanIndex = getSpanIndex(position, spanCount);
            int i6 = spanIndex;
            this.mSpanIndexCache.put(position, spanIndex);
            return spanIndex;
        }

        public int getSpanIndex(int i, int i2) {
            int span;
            int position = i;
            int spanCount = i2;
            int i3 = position;
            int i4 = spanCount;
            int spanSize = getSpanSize(position);
            int positionSpanSize = spanSize;
            if (spanSize == spanCount) {
                return 0;
            }
            int span2 = 0;
            int startPos = 0;
            if (this.mCacheSpanIndices && this.mSpanIndexCache.size() > 0) {
                int findReferenceIndexFromCache = findReferenceIndexFromCache(position);
                int prevKey = findReferenceIndexFromCache;
                if (findReferenceIndexFromCache >= 0) {
                    span2 = this.mSpanIndexCache.get(prevKey) + getSpanSize(prevKey);
                    startPos = prevKey + 1;
                }
            }
            for (int i5 = startPos; i5 < position; i5++) {
                int size = getSpanSize(i5);
                int i6 = span + size;
                span = i6;
                if (i6 == spanCount) {
                    span = 0;
                } else if (span > spanCount) {
                    span = size;
                }
            }
            if (span + positionSpanSize > spanCount) {
                return 0;
            }
            return span;
        }

        /* access modifiers changed from: 0000 */
        public int findReferenceIndexFromCache(int i) {
            int position = i;
            int i2 = position;
            int lo = 0;
            int hi = this.mSpanIndexCache.size() - 1;
            while (lo <= hi) {
                int i3 = (lo + hi) >>> 1;
                int mid = i3;
                int keyAt = this.mSpanIndexCache.keyAt(i3);
                int i4 = keyAt;
                if (keyAt >= position) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            int i5 = lo - 1;
            int index = i5;
            if (i5 >= 0 && index < this.mSpanIndexCache.size()) {
                return this.mSpanIndexCache.keyAt(index);
            }
            return -1;
        }

        public int getSpanGroupIndex(int i, int i2) {
            int adapterPosition = i;
            int spanCount = i2;
            int i3 = adapterPosition;
            int i4 = spanCount;
            int span = 0;
            int group = 0;
            int positionSpanSize = getSpanSize(adapterPosition);
            for (int i5 = 0; i5 < adapterPosition; i5++) {
                int size = getSpanSize(i5);
                int i6 = span + size;
                span = i6;
                if (i6 == spanCount) {
                    span = 0;
                    group++;
                } else if (span > spanCount) {
                    span = size;
                    group++;
                }
            }
            if (span + positionSpanSize > spanCount) {
                group++;
            }
            return group;
        }
    }

    public GridLayoutManager(Context context, int i) {
        Context context2 = context;
        int spanCount = i;
        Context context3 = context2;
        int i2 = spanCount;
        super(context2);
        setSpanCount(spanCount);
    }

    public GridLayoutManager(Context context, int i, int i2, boolean z) {
        Context context2 = context;
        int spanCount = i;
        int orientation = i2;
        Context context3 = context2;
        int i3 = spanCount;
        int i4 = orientation;
        super(context2, orientation, z);
        setSpanCount(spanCount);
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        int defStyleRes = i2;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i3 = defStyleAttr;
        int i4 = defStyleRes;
        super(context2, attrs, defStyleAttr, defStyleRes);
        setSpanCount(getProperties(context2, attrs, defStyleAttr, defStyleRes).spanCount);
    }

    public void setStackFromEnd(boolean z) {
        if (!z) {
            super.setStackFromEnd(false);
            return;
        }
        throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
    }

    public int getRowCountForAccessibility(Recycler recycler, State state) {
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        if (state2.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(recycler2, state2, state2.getItemCount() - 1) + 1;
    }

    public int getColumnCountForAccessibility(Recycler recycler, State state) {
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        if (state2.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(recycler2, state2, state2.getItemCount() - 1) + 1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00cf, code lost:
        r33 = r25.getSpanSize();
        r34 = r6.mSpanCount;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onInitializeAccessibilityNodeInfoForItem(android.support.p003v7.widget.RecyclerView.Recycler r41, android.support.p003v7.widget.RecyclerView.State r42, android.view.View r43, android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat r44) {
        /*
            r40 = this;
            r6 = r40
            r7 = r41
            r8 = r42
            r9 = r43
            r10 = r44
            r11 = r6
            r12 = r7
            r13 = r8
            r14 = r9
            r15 = r10
            android.view.ViewGroup$LayoutParams r16 = r9.getLayoutParams()
            r17 = r16
            r0 = r16
            boolean r0 = r0 instanceof android.support.p003v7.widget.GridLayoutManager.LayoutParams
            r18 = r0
            r19 = r18
            r20 = r19
            r21 = 0
            r0 = r20
            r1 = r21
            if (r0 == r1) goto L_0x0081
            r0 = r17
            android.support.v7.widget.GridLayoutManager$LayoutParams r0 = (android.support.p003v7.widget.GridLayoutManager.LayoutParams) r0
            r16 = r0
            r25 = r16
            int r26 = r25.getViewLayoutPosition()
            r27 = r7
            r28 = r8
            r0 = r27
            r1 = r28
            r2 = r26
            int r20 = r6.getSpanGroupIndex(r0, r1, r2)
            r29 = r20
            int r0 = r6.mOrientation
            r20 = r0
            r21 = 0
            r0 = r20
            r1 = r21
            if (r0 == r1) goto L_0x008b
            r31 = 1
            int r26 = r25.getSpanIndex()
            int r32 = r25.getSpanSize()
            int r0 = r6.mSpanCount
            r33 = r0
            r21 = 1
            r0 = r33
            r1 = r21
            if (r0 > r1) goto L_0x00cf
        L_0x0065:
            r33 = 0
        L_0x0067:
            r38 = r33
            r39 = 0
            r0 = r29
            r1 = r31
            r2 = r26
            r3 = r32
            r4 = r38
            r5 = r39
            android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$CollectionItemInfoCompat r37 = android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(r0, r1, r2, r3, r4, r5)
            r0 = r37
            r10.setCollectionItemInfo(r0)
        L_0x0080:
            return
        L_0x0081:
            r22 = r6
            r23 = r9
            r24 = r10
            super.onInitializeAccessibilityNodeInfoForItem(r23, r24)
            return
        L_0x008b:
            int r30 = r25.getSpanIndex()
            int r31 = r25.getSpanSize()
            r32 = 1
            int r0 = r6.mSpanCount
            r33 = r0
            r21 = 1
            r0 = r33
            r1 = r21
            if (r0 > r1) goto L_0x00bd
        L_0x00a1:
            r33 = 0
        L_0x00a3:
            r35 = r33
            r36 = 0
            r0 = r30
            r1 = r31
            r2 = r29
            r3 = r32
            r4 = r35
            r5 = r36
            android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$CollectionItemInfoCompat r37 = android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(r0, r1, r2, r3, r4, r5)
            r0 = r37
            r10.setCollectionItemInfo(r0)
            goto L_0x0080
        L_0x00bd:
            int r33 = r25.getSpanSize()
            int r0 = r6.mSpanCount
            r34 = r0
            r0 = r33
            r1 = r34
            if (r0 == r1) goto L_0x00cc
            goto L_0x00a1
        L_0x00cc:
            r33 = 1
            goto L_0x00a3
        L_0x00cf:
            int r33 = r25.getSpanSize()
            int r0 = r6.mSpanCount
            r34 = r0
            r0 = r33
            r1 = r34
            if (r0 == r1) goto L_0x00de
            goto L_0x0065
        L_0x00de:
            r33 = 1
            goto L_0x0067
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.widget.GridLayoutManager.onInitializeAccessibilityNodeInfoForItem(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State, android.view.View, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat):void");
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (state2.isPreLayout()) {
            cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler2, state2);
        clearPreLayoutSpanMappingCache();
    }

    public void onLayoutCompleted(State state) {
        State state2 = state;
        State state3 = state2;
        super.onLayoutCompleted(state2);
        this.mPendingSpanCountChange = false;
    }

    private void clearPreLayoutSpanMappingCache() {
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    private void cachePreLayoutSpanMapping() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            LayoutParams lp = layoutParams;
            int viewLayoutPosition = layoutParams.getViewLayoutPosition();
            int i2 = viewLayoutPosition;
            this.mPreLayoutSpanSizeCache.put(viewLayoutPosition, lp.getSpanSize());
            this.mPreLayoutSpanIndexCache.put(viewLayoutPosition, lp.getSpanIndex());
        }
    }

    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        RecyclerView recyclerView2 = recyclerView;
        int i3 = i;
        int i4 = i2;
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = recyclerView;
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        RecyclerView recyclerView2 = recyclerView;
        int i3 = i;
        int i4 = i2;
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        RecyclerView recyclerView2 = recyclerView;
        int i3 = i;
        int i4 = i2;
        Object obj2 = obj;
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        RecyclerView recyclerView2 = recyclerView;
        int i4 = i;
        int i5 = i2;
        int i6 = i3;
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public android.support.p003v7.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation != 0) {
            return new LayoutParams(-1, -2);
        }
        return new LayoutParams(-2, -1);
    }

    public android.support.p003v7.widget.RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        Context c = context;
        AttributeSet attrs = attributeSet;
        Context context2 = c;
        AttributeSet attributeSet2 = attrs;
        return new LayoutParams(c, attrs);
    }

    public android.support.p003v7.widget.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams lp = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = lp;
        if (!(lp instanceof MarginLayoutParams)) {
            return new LayoutParams(lp);
        }
        return new LayoutParams((MarginLayoutParams) lp);
    }

    public boolean checkLayoutParams(android.support.p003v7.widget.RecyclerView.LayoutParams layoutParams) {
        android.support.p003v7.widget.RecyclerView.LayoutParams lp = layoutParams;
        android.support.p003v7.widget.RecyclerView.LayoutParams layoutParams2 = lp;
        return lp instanceof LayoutParams;
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        SpanSizeLookup spanSizeLookup2 = spanSizeLookup;
        SpanSizeLookup spanSizeLookup3 = spanSizeLookup2;
        this.mSpanSizeLookup = spanSizeLookup2;
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return this.mSpanSizeLookup;
    }

    private void updateMeasurements() {
        int totalSpace;
        if (getOrientation() != 1) {
            totalSpace = (getHeight() - getPaddingBottom()) - getPaddingTop();
        } else {
            totalSpace = (getWidth() - getPaddingRight()) - getPaddingLeft();
        }
        calculateItemBorders(totalSpace);
    }

    public void setMeasuredDimension(Rect rect, int i, int i2) {
        int height;
        int width;
        Rect childrenBounds = rect;
        int wSpec = i;
        int hSpec = i2;
        Rect rect2 = childrenBounds;
        int i3 = wSpec;
        int i4 = hSpec;
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension(childrenBounds, wSpec, hSpec);
        }
        int horizontalPadding = getPaddingLeft() + getPaddingRight();
        int verticalPadding = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation != 1) {
            width = chooseSize(wSpec, childrenBounds.width() + horizontalPadding, getMinimumWidth());
            height = chooseSize(hSpec, this.mCachedBorders[this.mCachedBorders.length - 1] + verticalPadding, getMinimumHeight());
        } else {
            height = chooseSize(hSpec, childrenBounds.height() + verticalPadding, getMinimumHeight());
            width = chooseSize(wSpec, this.mCachedBorders[this.mCachedBorders.length - 1] + horizontalPadding, getMinimumWidth());
        }
        setMeasuredDimension(width, height);
    }

    private void calculateItemBorders(int i) {
        int totalSpace = i;
        int i2 = totalSpace;
        this.mCachedBorders = calculateItemBorders(this.mCachedBorders, this.mSpanCount, totalSpace);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0035, code lost:
        if (r1[r1.length - 1] == r3) goto L_0x0011;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int[] calculateItemBorders(int[] r22, int r23, int r24) {
        /*
            r1 = r22
            r2 = r23
            r3 = r24
            r4 = r1
            r5 = r2
            r6 = r3
            r7 = 0
            if (r1 != r7) goto L_0x0029
        L_0x000c:
            int r8 = r2 + 1
            int[] r11 = new int[r8]
            r4 = r11
        L_0x0011:
            r9 = 0
            r12 = r4
            r13 = 0
            r12[r9] = r13
            int r8 = r3 / r2
            r14 = r8
            int r8 = r3 % r2
            r15 = r8
            r16 = 0
            r17 = 0
            r18 = 1
        L_0x0022:
            r0 = r18
            if (r0 <= r2) goto L_0x0038
            r21 = r4
            return r21
        L_0x0029:
            int r8 = r1.length
            int r9 = r2 + 1
            if (r8 == r9) goto L_0x002f
            goto L_0x000c
        L_0x002f:
            int r9 = r1.length
            int r9 = r9 + -1
            r10 = r1
            r8 = r10[r9]
            if (r8 != r3) goto L_0x000c
            goto L_0x0011
        L_0x0038:
            r19 = r14
            int r8 = r17 + r15
            r17 = r8
            r13 = 0
            if (r8 > r13) goto L_0x004c
        L_0x0041:
            int r8 = r16 + r19
            r16 = r8
            r20 = r4
            r20[r18] = r8
            int r18 = r18 + 1
            goto L_0x0022
        L_0x004c:
            int r8 = r2 - r17
            if (r8 < r15) goto L_0x0051
            goto L_0x0041
        L_0x0051:
            int r19 = r14 + 1
            int r8 = r17 - r2
            r17 = r8
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.widget.GridLayoutManager.calculateItemBorders(int[], int, int):int[]");
    }

    /* access modifiers changed from: 0000 */
    public int getSpaceForSpanRange(int i, int i2) {
        int startSpan = i;
        int spanSize = i2;
        int i3 = startSpan;
        int i4 = spanSize;
        if (this.mOrientation == 1 && isLayoutRTL()) {
            return this.mCachedBorders[this.mSpanCount - startSpan] - this.mCachedBorders[(this.mSpanCount - startSpan) - spanSize];
        }
        return this.mCachedBorders[startSpan + spanSize] - this.mCachedBorders[startSpan];
    }

    /* access modifiers changed from: 0000 */
    public void onAnchorReady(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
        Recycler recycler2 = recycler;
        State state2 = state;
        AnchorInfo anchorInfo2 = anchorInfo;
        int itemDirection = i;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        AnchorInfo anchorInfo3 = anchorInfo2;
        int i2 = itemDirection;
        super.onAnchorReady(recycler2, state2, anchorInfo2, itemDirection);
        updateMeasurements();
        if (state2.getItemCount() > 0 && !state2.isPreLayout()) {
            ensureAnchorIsInCorrectSpan(recycler2, state2, anchorInfo2, itemDirection);
        }
        ensureViewSet();
    }

    private void ensureViewSet() {
        if (this.mSet == null || this.mSet.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
        int dx = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        int i2 = dx;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        updateMeasurements();
        ensureViewSet();
        return super.scrollHorizontallyBy(dx, recycler2, state2);
    }

    public int scrollVerticallyBy(int i, Recycler recycler, State state) {
        int dy = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        int i2 = dy;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        updateMeasurements();
        ensureViewSet();
        return super.scrollVerticallyBy(dy, recycler2, state2);
    }

    private void ensureAnchorIsInCorrectSpan(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
        Recycler recycler2 = recycler;
        State state2 = state;
        AnchorInfo anchorInfo2 = anchorInfo;
        int itemDirection = i;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        AnchorInfo anchorInfo3 = anchorInfo2;
        int i2 = itemDirection;
        boolean layingOutInPrimaryDirection = itemDirection == 1;
        int span = getSpanIndex(recycler2, state2, anchorInfo2.mPosition);
        if (!layingOutInPrimaryDirection) {
            int indexLimit = state2.getItemCount() - 1;
            int pos = anchorInfo2.mPosition;
            int bestSpan = span;
            while (pos < indexLimit) {
                int spanIndex = getSpanIndex(recycler2, state2, pos + 1);
                int next = spanIndex;
                if (spanIndex <= bestSpan) {
                    break;
                }
                pos++;
                bestSpan = next;
            }
            anchorInfo2.mPosition = pos;
            return;
        }
        while (span > 0 && anchorInfo2.mPosition > 0) {
            anchorInfo2.mPosition--;
            span = getSpanIndex(recycler2, state2, anchorInfo2.mPosition);
        }
    }

    /* access modifiers changed from: 0000 */
    public View findReferenceChild(Recycler recycler, State state, int i, int i2, int i3) {
        View view;
        Recycler recycler2 = recycler;
        State state2 = state;
        int start = i;
        int end = i2;
        int itemCount = i3;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        int i4 = start;
        int i5 = end;
        int i6 = itemCount;
        ensureLayoutState();
        View invalidMatch = null;
        View outOfBoundsMatch = null;
        int boundsStart = this.mOrientationHelper.getStartAfterPadding();
        int boundsEnd = this.mOrientationHelper.getEndAfterPadding();
        int diff = end <= start ? -1 : 1;
        for (int i7 = start; i7 != end; i7 += diff) {
            View view2 = getChildAt(i7);
            int position = getPosition(view2);
            int position2 = position;
            if (position >= 0 && position2 < itemCount) {
                int spanIndex = getSpanIndex(recycler2, state2, position2);
                int i8 = spanIndex;
                if (spanIndex != 0) {
                    continue;
                } else if (!((android.support.p003v7.widget.RecyclerView.LayoutParams) view2.getLayoutParams()).isItemRemoved()) {
                    if (this.mOrientationHelper.getDecoratedStart(view2) < boundsEnd && this.mOrientationHelper.getDecoratedEnd(view2) >= boundsStart) {
                        return view2;
                    }
                    if (outOfBoundsMatch == null) {
                        outOfBoundsMatch = view2;
                    }
                } else if (invalidMatch == null) {
                    invalidMatch = view2;
                }
            }
        }
        if (outOfBoundsMatch == null) {
            view = invalidMatch;
        } else {
            view = outOfBoundsMatch;
        }
        return view;
    }

    private int getSpanGroupIndex(Recycler recycler, State state, int i) {
        Recycler recycler2 = recycler;
        State state2 = state;
        int viewPosition = i;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        int i2 = viewPosition;
        if (!state2.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanGroupIndex(viewPosition, this.mSpanCount);
        }
        int convertPreLayoutPositionToPostLayout = recycler2.convertPreLayoutPositionToPostLayout(viewPosition);
        int adapterPosition = convertPreLayoutPositionToPostLayout;
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.mSpanSizeLookup.getSpanGroupIndex(adapterPosition, this.mSpanCount);
        }
        int w = Log.w(TAG, "Cannot find span size for pre layout position. " + viewPosition);
        return 0;
    }

    private int getSpanIndex(Recycler recycler, State state, int i) {
        Recycler recycler2 = recycler;
        State state2 = state;
        int pos = i;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        int i2 = pos;
        if (!state2.isPreLayout()) {
            return this.mSpanSizeLookup.getCachedSpanIndex(pos, this.mSpanCount);
        }
        int i3 = this.mPreLayoutSpanIndexCache.get(pos, -1);
        int cached = i3;
        if (i3 != -1) {
            return cached;
        }
        int convertPreLayoutPositionToPostLayout = recycler2.convertPreLayoutPositionToPostLayout(pos);
        int adapterPosition = convertPreLayoutPositionToPostLayout;
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.mSpanSizeLookup.getCachedSpanIndex(adapterPosition, this.mSpanCount);
        }
        int w = Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + pos);
        return 0;
    }

    private int getSpanSize(Recycler recycler, State state, int i) {
        Recycler recycler2 = recycler;
        State state2 = state;
        int pos = i;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        int i2 = pos;
        if (!state2.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanSize(pos);
        }
        int i3 = this.mPreLayoutSpanSizeCache.get(pos, -1);
        int cached = i3;
        if (i3 != -1) {
            return cached;
        }
        int convertPreLayoutPositionToPostLayout = recycler2.convertPreLayoutPositionToPostLayout(pos);
        int adapterPosition = convertPreLayoutPositionToPostLayout;
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.mSpanSizeLookup.getSpanSize(adapterPosition);
        }
        int w = Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + pos);
        return 1;
    }

    /* access modifiers changed from: 0000 */
    public void collectPrefetchPositionsForLayoutState(State state, LayoutState layoutState, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        State state2 = state;
        LayoutState layoutState2 = layoutState;
        LayoutPrefetchRegistry layoutPrefetchRegistry2 = layoutPrefetchRegistry;
        State state3 = state2;
        LayoutState layoutState3 = layoutState2;
        LayoutPrefetchRegistry layoutPrefetchRegistry3 = layoutPrefetchRegistry2;
        int remainingSpan = this.mSpanCount;
        for (int count = 0; count < this.mSpanCount && layoutState2.hasMore(state2) && remainingSpan > 0; count++) {
            int i = layoutState2.mCurrentPosition;
            int i2 = i;
            layoutPrefetchRegistry2.addPosition(i, layoutState2.mScrollingOffset);
            remainingSpan -= this.mSpanSizeLookup.getSpanSize(i);
            layoutState2.mCurrentPosition += layoutState2.mItemDirection;
        }
    }

    /* access modifiers changed from: 0000 */
    public void layoutChunk(Recycler recycler, State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int wSpec;
        int hSpec;
        Recycler recycler2 = recycler;
        State state2 = state;
        LayoutState layoutState2 = layoutState;
        LayoutChunkResult result = layoutChunkResult;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        LayoutState layoutState3 = layoutState2;
        LayoutChunkResult layoutChunkResult2 = result;
        int modeInOther = this.mOrientationHelper.getModeInOther();
        int otherDirSpecMode = modeInOther;
        boolean flexibleInOtherDir = modeInOther != 1073741824;
        int currentOtherDirSize = getChildCount() <= 0 ? 0 : this.mCachedBorders[this.mSpanCount];
        if (flexibleInOtherDir) {
            updateMeasurements();
        }
        boolean layingOutInPrimaryDirection = layoutState2.mItemDirection == 1;
        int count = 0;
        int consumedSpanCount = 0;
        int remainingSpan = this.mSpanCount;
        if (!layingOutInPrimaryDirection) {
            remainingSpan = getSpanIndex(recycler2, state2, layoutState2.mCurrentPosition) + getSpanSize(recycler2, state2, layoutState2.mCurrentPosition);
        }
        while (count < this.mSpanCount && layoutState2.hasMore(state2) && remainingSpan > 0) {
            int i = layoutState2.mCurrentPosition;
            int pos = i;
            int spanSize = getSpanSize(recycler2, state2, i);
            int spanSize2 = spanSize;
            if (spanSize <= this.mSpanCount) {
                int i2 = remainingSpan - spanSize2;
                remainingSpan = i2;
                if (i2 < 0) {
                    break;
                }
                View next = layoutState2.next(recycler2);
                View view = next;
                if (next == null) {
                    break;
                }
                consumedSpanCount += spanSize2;
                this.mSet[count] = view;
                count++;
            } else {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Item at position " + pos + " requires " + spanSize2 + " spans but GridLayoutManager has only " + this.mSpanCount + " spans.");
                throw illegalArgumentException;
            }
        }
        if (count != 0) {
            int maxSize = 0;
            float maxSizeInOther = 0.0f;
            assignSpans(recycler2, state2, count, consumedSpanCount, layingOutInPrimaryDirection);
            for (int i3 = 0; i3 < count; i3++) {
                View view2 = this.mSet[i3];
                if (layoutState2.mScrapList != null) {
                    if (!layingOutInPrimaryDirection) {
                        addDisappearingView(view2, 0);
                    } else {
                        addDisappearingView(view2);
                    }
                } else if (!layingOutInPrimaryDirection) {
                    addView(view2, 0);
                } else {
                    addView(view2);
                }
                calculateItemDecorationsForChild(view2, this.mDecorInsets);
                measureChild(view2, otherDirSpecMode, false);
                int decoratedMeasurement = this.mOrientationHelper.getDecoratedMeasurement(view2);
                int size = decoratedMeasurement;
                if (decoratedMeasurement > maxSize) {
                    maxSize = size;
                }
                LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
                LayoutParams layoutParams2 = layoutParams;
                float decoratedMeasurementInOther = (1.0f * ((float) this.mOrientationHelper.getDecoratedMeasurementInOther(view2))) / ((float) layoutParams.mSpanSize);
                float otherSize = decoratedMeasurementInOther;
                if (decoratedMeasurementInOther > maxSizeInOther) {
                    maxSizeInOther = otherSize;
                }
            }
            if (flexibleInOtherDir) {
                guessMeasurement(maxSizeInOther, currentOtherDirSize);
                maxSize = 0;
                for (int i4 = 0; i4 < count; i4++) {
                    View view3 = this.mSet[i4];
                    measureChild(view3, 1073741824, true);
                    int decoratedMeasurement2 = this.mOrientationHelper.getDecoratedMeasurement(view3);
                    int size2 = decoratedMeasurement2;
                    if (decoratedMeasurement2 > maxSize) {
                        maxSize = size2;
                    }
                }
            }
            for (int i5 = 0; i5 < count; i5++) {
                View view4 = this.mSet[i5];
                if (this.mOrientationHelper.getDecoratedMeasurement(view4) != maxSize) {
                    LayoutParams layoutParams3 = (LayoutParams) view4.getLayoutParams();
                    LayoutParams lp = layoutParams3;
                    Rect rect = layoutParams3.mDecorInsets;
                    Rect decorInsets = rect;
                    int verticalInsets = rect.top + decorInsets.bottom + lp.topMargin + lp.bottomMargin;
                    int horizontalInsets = decorInsets.left + decorInsets.right + lp.leftMargin + lp.rightMargin;
                    int totalSpaceInOther = getSpaceForSpanRange(lp.mSpanIndex, lp.mSpanSize);
                    if (this.mOrientation != 1) {
                        wSpec = MeasureSpec.makeMeasureSpec(maxSize - horizontalInsets, 1073741824);
                        hSpec = getChildMeasureSpec(totalSpaceInOther, 1073741824, verticalInsets, lp.height, false);
                    } else {
                        wSpec = getChildMeasureSpec(totalSpaceInOther, 1073741824, horizontalInsets, lp.width, false);
                        hSpec = MeasureSpec.makeMeasureSpec(maxSize - verticalInsets, 1073741824);
                    }
                    measureChildWithDecorationsAndMargin(view4, wSpec, hSpec, true);
                }
            }
            result.mConsumed = maxSize;
            int left = 0;
            int right = 0;
            int top = 0;
            int bottom = 0;
            if (this.mOrientation != 1) {
                if (layoutState2.mLayoutDirection != -1) {
                    int i6 = layoutState2.mOffset;
                    left = i6;
                    right = i6 + maxSize;
                } else {
                    int i7 = layoutState2.mOffset;
                    right = i7;
                    left = i7 - maxSize;
                }
            } else if (layoutState2.mLayoutDirection != -1) {
                int i8 = layoutState2.mOffset;
                top = i8;
                bottom = i8 + maxSize;
            } else {
                int i9 = layoutState2.mOffset;
                bottom = i9;
                top = i9 - maxSize;
            }
            for (int i10 = 0; i10 < count; i10++) {
                View view5 = this.mSet[i10];
                View view6 = view5;
                LayoutParams params = (LayoutParams) view5.getLayoutParams();
                if (this.mOrientation != 1) {
                    int paddingTop = getPaddingTop() + this.mCachedBorders[params.mSpanIndex];
                    top = paddingTop;
                    bottom = paddingTop + this.mOrientationHelper.getDecoratedMeasurementInOther(view6);
                } else if (!isLayoutRTL()) {
                    int paddingLeft = getPaddingLeft() + this.mCachedBorders[params.mSpanIndex];
                    left = paddingLeft;
                    right = paddingLeft + this.mOrientationHelper.getDecoratedMeasurementInOther(view6);
                } else {
                    int paddingLeft2 = getPaddingLeft() + this.mCachedBorders[this.mSpanCount - params.mSpanIndex];
                    right = paddingLeft2;
                    left = paddingLeft2 - this.mOrientationHelper.getDecoratedMeasurementInOther(view6);
                }
                layoutDecoratedWithMargins(view6, left, top, right, bottom);
                if (params.isItemRemoved() || params.isItemChanged()) {
                    result.mIgnoreConsumed = true;
                }
                result.mFocusable |= view6.isFocusable();
            }
            Arrays.fill(this.mSet, null);
            return;
        }
        result.mFinished = true;
    }

    private void measureChild(View view, int i, boolean z) {
        int wSpec;
        int hSpec;
        View view2 = view;
        int otherDirParentSpecMode = i;
        View view3 = view2;
        int i2 = otherDirParentSpecMode;
        boolean alreadyMeasured = z;
        LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
        LayoutParams lp = layoutParams;
        Rect rect = layoutParams.mDecorInsets;
        Rect decorInsets = rect;
        int verticalInsets = rect.top + decorInsets.bottom + lp.topMargin + lp.bottomMargin;
        int horizontalInsets = decorInsets.left + decorInsets.right + lp.leftMargin + lp.rightMargin;
        int availableSpaceInOther = getSpaceForSpanRange(lp.mSpanIndex, lp.mSpanSize);
        if (this.mOrientation != 1) {
            hSpec = getChildMeasureSpec(availableSpaceInOther, otherDirParentSpecMode, verticalInsets, lp.height, false);
            wSpec = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getWidthMode(), horizontalInsets, lp.width, true);
        } else {
            wSpec = getChildMeasureSpec(availableSpaceInOther, otherDirParentSpecMode, horizontalInsets, lp.width, false);
            hSpec = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getHeightMode(), verticalInsets, lp.height, true);
        }
        measureChildWithDecorationsAndMargin(view2, wSpec, hSpec, alreadyMeasured);
    }

    private void guessMeasurement(float f, int i) {
        float maxSizeInOther = f;
        int currentOtherDirSize = i;
        float f2 = maxSizeInOther;
        int i2 = currentOtherDirSize;
        int round = Math.round(maxSizeInOther * ((float) this.mSpanCount));
        int i3 = round;
        calculateItemBorders(Math.max(round, currentOtherDirSize));
    }

    private void measureChildWithDecorationsAndMargin(View view, int i, int i2, boolean z) {
        boolean measure;
        View child = view;
        int widthSpec = i;
        int heightSpec = i2;
        View view2 = child;
        int i3 = widthSpec;
        int i4 = heightSpec;
        android.support.p003v7.widget.RecyclerView.LayoutParams lp = (android.support.p003v7.widget.RecyclerView.LayoutParams) child.getLayoutParams();
        if (!z) {
            measure = shouldMeasureChild(child, widthSpec, heightSpec, lp);
        } else {
            measure = shouldReMeasureChild(child, widthSpec, heightSpec, lp);
        }
        if (measure) {
            child.measure(widthSpec, heightSpec);
        }
    }

    private void assignSpans(Recycler recycler, State state, int i, int i2, boolean z) {
        int start;
        int end;
        int diff;
        Recycler recycler2 = recycler;
        State state2 = state;
        int count = i;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        int i3 = count;
        int i4 = i2;
        if (!z) {
            start = count - 1;
            end = -1;
            diff = -1;
        } else {
            start = 0;
            end = count;
            diff = 1;
        }
        int span = 0;
        for (int i5 = start; i5 != end; i5 += diff) {
            View view = this.mSet[i5];
            View view2 = view;
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            LayoutParams params = layoutParams;
            layoutParams.mSpanSize = getSpanSize(recycler2, state2, getPosition(view2));
            params.mSpanIndex = span;
            span += params.mSpanSize;
        }
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public void setSpanCount(int i) {
        int spanCount = i;
        int i2 = spanCount;
        if (spanCount != this.mSpanCount) {
            this.mPendingSpanCountChange = true;
            if (spanCount >= 1) {
                this.mSpanCount = spanCount;
                this.mSpanSizeLookup.invalidateSpanIndexCache();
                requestLayout();
                return;
            }
            throw new IllegalArgumentException("Span count should be at least 1. Provided " + spanCount);
        }
    }

    public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
        int start;
        int inc;
        int limit;
        View focused = view;
        int focusDirection = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        View view2 = focused;
        int i2 = focusDirection;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        View findContainingItemView = findContainingItemView(focused);
        View prevFocusedChild = findContainingItemView;
        if (findContainingItemView == null) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) prevFocusedChild.getLayoutParams();
        LayoutParams lp = layoutParams;
        int prevSpanStart = layoutParams.mSpanIndex;
        int prevSpanEnd = lp.mSpanIndex + lp.mSpanSize;
        View onFocusSearchFailed = super.onFocusSearchFailed(focused, focusDirection, recycler2, state2);
        View view3 = onFocusSearchFailed;
        if (onFocusSearchFailed == null) {
            return null;
        }
        int convertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(focusDirection);
        int i3 = convertFocusDirectionToLayoutDirection;
        if (!((convertFocusDirectionToLayoutDirection == 1) != this.mShouldReverseLayout)) {
            start = 0;
            inc = 1;
            limit = getChildCount();
        } else {
            start = getChildCount() - 1;
            inc = -1;
            limit = -1;
        }
        boolean preferLastSpan = this.mOrientation == 1 && isLayoutRTL();
        View weakCandidate = null;
        int weakCandidateSpanIndex = -1;
        int weakCandidateOverlap = 0;
        for (int i4 = start; i4 != limit; i4 += inc) {
            View childAt = getChildAt(i4);
            View candidate = childAt;
            if (childAt == prevFocusedChild) {
                break;
            }
            if (candidate.isFocusable()) {
                LayoutParams layoutParams2 = (LayoutParams) candidate.getLayoutParams();
                LayoutParams candidateLp = layoutParams2;
                int candidateStart = layoutParams2.mSpanIndex;
                int candidateEnd = candidateLp.mSpanIndex + candidateLp.mSpanSize;
                if (candidateStart == prevSpanStart && candidateEnd == prevSpanEnd) {
                    return candidate;
                }
                boolean assignAsWeek = false;
                if (weakCandidate != null) {
                    int maxStart = Math.max(candidateStart, prevSpanStart);
                    int min = Math.min(candidateEnd, prevSpanEnd);
                    int i5 = min;
                    int i6 = min - maxStart;
                    int overlap = i6;
                    if (i6 > weakCandidateOverlap) {
                        assignAsWeek = true;
                    } else if (overlap == weakCandidateOverlap) {
                        if (preferLastSpan == (candidateStart > weakCandidateSpanIndex)) {
                            assignAsWeek = true;
                        }
                    }
                } else {
                    assignAsWeek = true;
                }
                if (assignAsWeek) {
                    weakCandidate = candidate;
                    weakCandidateSpanIndex = candidateLp.mSpanIndex;
                    weakCandidateOverlap = Math.min(candidateEnd, prevSpanEnd) - Math.max(candidateStart, prevSpanStart);
                }
            }
        }
        return weakCandidate;
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && !this.mPendingSpanCountChange;
    }
}
