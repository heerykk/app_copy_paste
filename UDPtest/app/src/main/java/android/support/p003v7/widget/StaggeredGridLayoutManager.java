package android.support.p003v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.media.TransportMediator;
import android.support.p000v4.view.accessibility.AccessibilityEventCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityRecordCompat;
import android.support.p003v7.widget.RecyclerView.LayoutManager;
import android.support.p003v7.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry;
import android.support.p003v7.widget.RecyclerView.LayoutManager.Properties;
import android.support.p003v7.widget.RecyclerView.Recycler;
import android.support.p003v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.support.p003v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/* renamed from: android.support.v7.widget.StaggeredGridLayoutManager */
public class StaggeredGridLayoutManager extends LayoutManager implements ScrollVectorProvider {
    static final boolean DEBUG = false;
    @Deprecated
    public static final int GAP_HANDLING_LAZY = 1;
    public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
    public static final int GAP_HANDLING_NONE = 0;
    public static final int HORIZONTAL = 0;
    static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "StaggeredGridLayoutManager";
    public static final int VERTICAL = 1;
    private final AnchorInfo mAnchorInfo = new AnchorInfo(this);
    private final Runnable mCheckForGapsRunnable = new Runnable(this) {
        final /* synthetic */ StaggeredGridLayoutManager this$0;

        {
            StaggeredGridLayoutManager this$02 = r5;
            StaggeredGridLayoutManager staggeredGridLayoutManager = this$02;
            this.this$0 = this$02;
        }

        public void run() {
            boolean checkForGaps = this.this$0.checkForGaps();
        }
    };
    private int mFullSizeSpec;
    private int mGapStrategy = 2;
    private boolean mLaidOutInvalidFullSpan = false;
    private boolean mLastLayoutFromEnd;
    private boolean mLastLayoutRTL;
    @NonNull
    private final LayoutState mLayoutState;
    LazySpanLookup mLazySpanLookup = new LazySpanLookup();
    private int mOrientation;
    private SavedState mPendingSavedState;
    int mPendingScrollPosition = -1;
    int mPendingScrollPositionOffset = Integer.MIN_VALUE;
    private int[] mPrefetchDistances;
    @NonNull
    OrientationHelper mPrimaryOrientation;
    private BitSet mRemainingSpans;
    boolean mReverseLayout = false;
    @NonNull
    OrientationHelper mSecondaryOrientation;
    boolean mShouldReverseLayout = false;
    private int mSizePerSpan;
    private boolean mSmoothScrollbarEnabled = true;
    private int mSpanCount = -1;
    Span[] mSpans;
    private final Rect mTmpRect = new Rect();

    /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$AnchorInfo */
    class AnchorInfo {
        boolean mInvalidateOffsets;
        boolean mLayoutFromEnd;
        int mOffset;
        int mPosition;
        int[] mSpanReferenceLines;
        boolean mValid;
        final /* synthetic */ StaggeredGridLayoutManager this$0;

        public AnchorInfo(StaggeredGridLayoutManager staggeredGridLayoutManager) {
            StaggeredGridLayoutManager this$02 = staggeredGridLayoutManager;
            StaggeredGridLayoutManager staggeredGridLayoutManager2 = this$02;
            this.this$0 = this$02;
            reset();
        }

        /* access modifiers changed from: 0000 */
        public void reset() {
            this.mPosition = -1;
            this.mOffset = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mInvalidateOffsets = false;
            this.mValid = false;
            if (this.mSpanReferenceLines != null) {
                Arrays.fill(this.mSpanReferenceLines, -1);
            }
        }

        /* access modifiers changed from: 0000 */
        public void saveSpanReferenceLines(Span[] spanArr) {
            Span[] spans = spanArr;
            Span[] spanArr2 = spans;
            int spanCount = spans.length;
            if (this.mSpanReferenceLines == null || this.mSpanReferenceLines.length < spanCount) {
                this.mSpanReferenceLines = new int[this.this$0.mSpans.length];
            }
            for (int i = 0; i < spanCount; i++) {
                this.mSpanReferenceLines[i] = spans[i].getStartLine(Integer.MIN_VALUE);
            }
        }

        /* access modifiers changed from: 0000 */
        public void assignCoordinateFromPadding() {
            int endAfterPadding;
            if (!this.mLayoutFromEnd) {
                endAfterPadding = this.this$0.mPrimaryOrientation.getStartAfterPadding();
            } else {
                endAfterPadding = this.this$0.mPrimaryOrientation.getEndAfterPadding();
            }
            this.mOffset = endAfterPadding;
        }

        /* access modifiers changed from: 0000 */
        public void assignCoordinateFromPadding(int i) {
            int addedDistance = i;
            int i2 = addedDistance;
            if (!this.mLayoutFromEnd) {
                this.mOffset = this.this$0.mPrimaryOrientation.getStartAfterPadding() + addedDistance;
            } else {
                this.mOffset = this.this$0.mPrimaryOrientation.getEndAfterPadding() - addedDistance;
            }
        }
    }

    /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$LayoutParams */
    public static class LayoutParams extends android.support.p003v7.widget.RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        boolean mFullSpan;
        Span mSpan;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            Context c = context;
            AttributeSet attrs = attributeSet;
            Context context2 = c;
            AttributeSet attributeSet2 = attrs;
            super(c, attrs);
        }

        public LayoutParams(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams source = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = source;
            super(source);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.view.ViewGroup.LayoutParams source = layoutParams;
            android.view.ViewGroup.LayoutParams layoutParams2 = source;
            super(source);
        }

        public LayoutParams(android.support.p003v7.widget.RecyclerView.LayoutParams layoutParams) {
            android.support.p003v7.widget.RecyclerView.LayoutParams source = layoutParams;
            android.support.p003v7.widget.RecyclerView.LayoutParams layoutParams2 = source;
            super(source);
        }

        public void setFullSpan(boolean z) {
            this.mFullSpan = z;
        }

        public boolean isFullSpan() {
            return this.mFullSpan;
        }

        public final int getSpanIndex() {
            if (this.mSpan != null) {
                return this.mSpan.mIndex;
            }
            return -1;
        }
    }

    /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup */
    static class LazySpanLookup {
        private static final int MIN_SIZE = 10;
        int[] mData;
        List<FullSpanItem> mFullSpanItems;

        /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem */
        static class FullSpanItem implements Parcelable {
            public static final Creator<FullSpanItem> CREATOR = new Creator<FullSpanItem>() {
                public FullSpanItem createFromParcel(Parcel parcel) {
                    Parcel in = parcel;
                    Parcel parcel2 = in;
                    return new FullSpanItem(in);
                }

                public FullSpanItem[] newArray(int i) {
                    int size = i;
                    int i2 = size;
                    return new FullSpanItem[size];
                }
            };
            int mGapDir;
            int[] mGapPerSpan;
            boolean mHasUnwantedGapAfter;
            int mPosition;

            public FullSpanItem(Parcel parcel) {
                Parcel in = parcel;
                Parcel parcel2 = in;
                this.mPosition = in.readInt();
                this.mGapDir = in.readInt();
                this.mHasUnwantedGapAfter = in.readInt() == 1;
                int readInt = in.readInt();
                int spanCount = readInt;
                if (readInt > 0) {
                    this.mGapPerSpan = new int[spanCount];
                    in.readIntArray(this.mGapPerSpan);
                }
            }

            public FullSpanItem() {
            }

            /* access modifiers changed from: 0000 */
            public int getGapForSpan(int i) {
                int spanIndex = i;
                int i2 = spanIndex;
                return this.mGapPerSpan != null ? this.mGapPerSpan[spanIndex] : 0;
            }

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel parcel, int i) {
                Parcel dest = parcel;
                Parcel parcel2 = dest;
                int i2 = i;
                dest.writeInt(this.mPosition);
                dest.writeInt(this.mGapDir);
                dest.writeInt(!this.mHasUnwantedGapAfter ? 0 : 1);
                if (this.mGapPerSpan != null && this.mGapPerSpan.length > 0) {
                    dest.writeInt(this.mGapPerSpan.length);
                    dest.writeIntArray(this.mGapPerSpan);
                    return;
                }
                dest.writeInt(0);
            }

            public String toString() {
                return "FullSpanItem{mPosition=" + this.mPosition + ", mGapDir=" + this.mGapDir + ", mHasUnwantedGapAfter=" + this.mHasUnwantedGapAfter + ", mGapPerSpan=" + Arrays.toString(this.mGapPerSpan) + '}';
            }
        }

        LazySpanLookup() {
        }

        /* access modifiers changed from: 0000 */
        public int forceInvalidateAfter(int i) {
            int position = i;
            int i2 = position;
            if (this.mFullSpanItems != null) {
                for (int i3 = this.mFullSpanItems.size() - 1; i3 >= 0; i3--) {
                    FullSpanItem fullSpanItem = (FullSpanItem) this.mFullSpanItems.get(i3);
                    FullSpanItem fullSpanItem2 = fullSpanItem;
                    if (fullSpanItem.mPosition >= position) {
                        Object remove = this.mFullSpanItems.remove(i3);
                    }
                }
            }
            return invalidateAfter(position);
        }

        /* access modifiers changed from: 0000 */
        public int invalidateAfter(int i) {
            int position = i;
            int i2 = position;
            if (this.mData == null) {
                return -1;
            }
            if (position >= this.mData.length) {
                return -1;
            }
            int invalidateFullSpansAfter = invalidateFullSpansAfter(position);
            int endPosition = invalidateFullSpansAfter;
            if (invalidateFullSpansAfter != -1) {
                Arrays.fill(this.mData, position, endPosition + 1, -1);
                return endPosition + 1;
            }
            Arrays.fill(this.mData, position, this.mData.length, -1);
            return this.mData.length;
        }

        /* access modifiers changed from: 0000 */
        public int getSpan(int i) {
            int position = i;
            int i2 = position;
            if (this.mData != null && position < this.mData.length) {
                return this.mData[position];
            }
            return -1;
        }

        /* access modifiers changed from: 0000 */
        public void setSpan(int i, Span span) {
            int position = i;
            Span span2 = span;
            int i2 = position;
            Span span3 = span2;
            ensureSize(position);
            this.mData[position] = span2.mIndex;
        }

        /* access modifiers changed from: 0000 */
        public int sizeForPosition(int i) {
            int position = i;
            int i2 = position;
            int len = this.mData.length;
            while (len <= position) {
                len *= 2;
            }
            return len;
        }

        /* access modifiers changed from: 0000 */
        public void ensureSize(int i) {
            int position = i;
            int i2 = position;
            if (this.mData == null) {
                this.mData = new int[(Math.max(position, 10) + 1)];
                Arrays.fill(this.mData, -1);
            } else if (position >= this.mData.length) {
                int[] old = this.mData;
                this.mData = new int[sizeForPosition(position)];
                System.arraycopy(old, 0, this.mData, 0, old.length);
                Arrays.fill(this.mData, old.length, this.mData.length, -1);
            }
        }

        /* access modifiers changed from: 0000 */
        public void clear() {
            if (this.mData != null) {
                Arrays.fill(this.mData, -1);
            }
            this.mFullSpanItems = null;
        }

        /* access modifiers changed from: 0000 */
        public void offsetForRemoval(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            if (this.mData != null && positionStart < this.mData.length) {
                ensureSize(positionStart + itemCount);
                System.arraycopy(this.mData, positionStart + itemCount, this.mData, positionStart, (this.mData.length - positionStart) - itemCount);
                Arrays.fill(this.mData, this.mData.length - itemCount, this.mData.length, -1);
                offsetFullSpansForRemoval(positionStart, itemCount);
            }
        }

        private void offsetFullSpansForRemoval(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            if (this.mFullSpanItems != null) {
                int end = positionStart + itemCount;
                for (int i5 = this.mFullSpanItems.size() - 1; i5 >= 0; i5--) {
                    FullSpanItem fullSpanItem = (FullSpanItem) this.mFullSpanItems.get(i5);
                    FullSpanItem fsi = fullSpanItem;
                    if (fullSpanItem.mPosition >= positionStart) {
                        if (fsi.mPosition >= end) {
                            fsi.mPosition -= itemCount;
                        } else {
                            Object remove = this.mFullSpanItems.remove(i5);
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void offsetForAddition(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            if (this.mData != null && positionStart < this.mData.length) {
                ensureSize(positionStart + itemCount);
                System.arraycopy(this.mData, positionStart, this.mData, positionStart + itemCount, (this.mData.length - positionStart) - itemCount);
                Arrays.fill(this.mData, positionStart, positionStart + itemCount, -1);
                offsetFullSpansForAddition(positionStart, itemCount);
            }
        }

        private void offsetFullSpansForAddition(int i, int i2) {
            int positionStart = i;
            int itemCount = i2;
            int i3 = positionStart;
            int i4 = itemCount;
            if (this.mFullSpanItems != null) {
                for (int i5 = this.mFullSpanItems.size() - 1; i5 >= 0; i5--) {
                    FullSpanItem fullSpanItem = (FullSpanItem) this.mFullSpanItems.get(i5);
                    FullSpanItem fsi = fullSpanItem;
                    if (fullSpanItem.mPosition >= positionStart) {
                        fsi.mPosition += itemCount;
                    }
                }
            }
        }

        private int invalidateFullSpansAfter(int i) {
            int position = i;
            int i2 = position;
            if (this.mFullSpanItems == null) {
                return -1;
            }
            FullSpanItem fullSpanItem = getFullSpanItem(position);
            FullSpanItem item = fullSpanItem;
            if (fullSpanItem != null) {
                boolean remove = this.mFullSpanItems.remove(item);
            }
            int nextFsiIndex = -1;
            int count = this.mFullSpanItems.size();
            int i3 = 0;
            while (true) {
                if (i3 < count) {
                    FullSpanItem fullSpanItem2 = (FullSpanItem) this.mFullSpanItems.get(i3);
                    FullSpanItem fullSpanItem3 = fullSpanItem2;
                    if (fullSpanItem2.mPosition >= position) {
                        nextFsiIndex = i3;
                        break;
                    }
                    i3++;
                } else {
                    break;
                }
            }
            if (nextFsiIndex == -1) {
                return -1;
            }
            FullSpanItem fsi = (FullSpanItem) this.mFullSpanItems.get(nextFsiIndex);
            Object remove2 = this.mFullSpanItems.remove(nextFsiIndex);
            return fsi.mPosition;
        }

        public void addFullSpanItem(FullSpanItem fullSpanItem) {
            FullSpanItem fullSpanItem2 = fullSpanItem;
            FullSpanItem fullSpanItem3 = fullSpanItem2;
            if (this.mFullSpanItems == null) {
                this.mFullSpanItems = new ArrayList();
            }
            int size = this.mFullSpanItems.size();
            int i = 0;
            while (i < size) {
                FullSpanItem fullSpanItem4 = (FullSpanItem) this.mFullSpanItems.get(i);
                FullSpanItem other = fullSpanItem4;
                if (fullSpanItem4.mPosition == fullSpanItem2.mPosition) {
                    Object remove = this.mFullSpanItems.remove(i);
                }
                if (other.mPosition < fullSpanItem2.mPosition) {
                    i++;
                } else {
                    this.mFullSpanItems.add(i, fullSpanItem2);
                    return;
                }
            }
            boolean add = this.mFullSpanItems.add(fullSpanItem2);
        }

        public FullSpanItem getFullSpanItem(int i) {
            int position = i;
            int i2 = position;
            if (this.mFullSpanItems == null) {
                return null;
            }
            for (int i3 = this.mFullSpanItems.size() - 1; i3 >= 0; i3--) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.mFullSpanItems.get(i3);
                FullSpanItem fsi = fullSpanItem;
                if (fullSpanItem.mPosition == position) {
                    return fsi;
                }
            }
            return null;
        }

        public FullSpanItem getFirstFullSpanItemInRange(int i, int i2, int i3, boolean z) {
            int minPos = i;
            int maxPos = i2;
            int gapDir = i3;
            int i4 = minPos;
            int i5 = maxPos;
            int i6 = gapDir;
            boolean hasUnwantedGapAfter = z;
            if (this.mFullSpanItems == null) {
                return null;
            }
            int limit = this.mFullSpanItems.size();
            for (int i7 = 0; i7 < limit; i7++) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.mFullSpanItems.get(i7);
                FullSpanItem fsi = fullSpanItem;
                if (fullSpanItem.mPosition >= maxPos) {
                    return null;
                }
                if (fsi.mPosition >= minPos && (gapDir == 0 || fsi.mGapDir == gapDir || (hasUnwantedGapAfter && fsi.mHasUnwantedGapAfter))) {
                    return fsi;
                }
            }
            return null;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$SavedState */
    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                Parcel in = parcel;
                Parcel parcel2 = in;
                return new SavedState(in);
            }

            public SavedState[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new SavedState[size];
            }
        };
        boolean mAnchorLayoutFromEnd;
        int mAnchorPosition;
        List<FullSpanItem> mFullSpanItems;
        boolean mLastLayoutRTL;
        boolean mReverseLayout;
        int[] mSpanLookup;
        int mSpanLookupSize;
        int[] mSpanOffsets;
        int mSpanOffsetsSize;
        int mVisibleAnchorPosition;

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            this.mAnchorPosition = in.readInt();
            this.mVisibleAnchorPosition = in.readInt();
            this.mSpanOffsetsSize = in.readInt();
            if (this.mSpanOffsetsSize > 0) {
                this.mSpanOffsets = new int[this.mSpanOffsetsSize];
                in.readIntArray(this.mSpanOffsets);
            }
            this.mSpanLookupSize = in.readInt();
            if (this.mSpanLookupSize > 0) {
                this.mSpanLookup = new int[this.mSpanLookupSize];
                in.readIntArray(this.mSpanLookup);
            }
            this.mReverseLayout = in.readInt() == 1;
            this.mAnchorLayoutFromEnd = in.readInt() == 1;
            this.mLastLayoutRTL = in.readInt() == 1;
            this.mFullSpanItems = in.readArrayList(FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            SavedState other = savedState;
            SavedState savedState2 = other;
            this.mSpanOffsetsSize = other.mSpanOffsetsSize;
            this.mAnchorPosition = other.mAnchorPosition;
            this.mVisibleAnchorPosition = other.mVisibleAnchorPosition;
            this.mSpanOffsets = other.mSpanOffsets;
            this.mSpanLookupSize = other.mSpanLookupSize;
            this.mSpanLookup = other.mSpanLookup;
            this.mReverseLayout = other.mReverseLayout;
            this.mAnchorLayoutFromEnd = other.mAnchorLayoutFromEnd;
            this.mLastLayoutRTL = other.mLastLayoutRTL;
            this.mFullSpanItems = other.mFullSpanItems;
        }

        /* access modifiers changed from: 0000 */
        public void invalidateSpanInfo() {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mSpanLookupSize = 0;
            this.mSpanLookup = null;
            this.mFullSpanItems = null;
        }

        /* access modifiers changed from: 0000 */
        public void invalidateAnchorPositionInfo() {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mAnchorPosition = -1;
            this.mVisibleAnchorPosition = -1;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            Parcel parcel2 = dest;
            int i2 = i;
            dest.writeInt(this.mAnchorPosition);
            dest.writeInt(this.mVisibleAnchorPosition);
            dest.writeInt(this.mSpanOffsetsSize);
            if (this.mSpanOffsetsSize > 0) {
                dest.writeIntArray(this.mSpanOffsets);
            }
            dest.writeInt(this.mSpanLookupSize);
            if (this.mSpanLookupSize > 0) {
                dest.writeIntArray(this.mSpanLookup);
            }
            dest.writeInt(!this.mReverseLayout ? 0 : 1);
            dest.writeInt(!this.mAnchorLayoutFromEnd ? 0 : 1);
            dest.writeInt(!this.mLastLayoutRTL ? 0 : 1);
            dest.writeList(this.mFullSpanItems);
        }
    }

    /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$Span */
    class Span {
        static final int INVALID_LINE = Integer.MIN_VALUE;
        int mCachedEnd = Integer.MIN_VALUE;
        int mCachedStart = Integer.MIN_VALUE;
        int mDeletedSize = 0;
        final int mIndex;
        ArrayList<View> mViews = new ArrayList<>();
        final /* synthetic */ StaggeredGridLayoutManager this$0;

        Span(StaggeredGridLayoutManager staggeredGridLayoutManager, int i) {
            StaggeredGridLayoutManager this$02 = staggeredGridLayoutManager;
            int index = i;
            StaggeredGridLayoutManager staggeredGridLayoutManager2 = this$02;
            int i2 = index;
            this.this$0 = this$02;
            this.mIndex = index;
        }

        /* access modifiers changed from: 0000 */
        public int getStartLine(int i) {
            int def = i;
            int i2 = def;
            if (this.mCachedStart != Integer.MIN_VALUE) {
                return this.mCachedStart;
            }
            if (this.mViews.size() == 0) {
                return def;
            }
            calculateCachedStart();
            return this.mCachedStart;
        }

        /* access modifiers changed from: 0000 */
        public void calculateCachedStart() {
            View startView = (View) this.mViews.get(0);
            LayoutParams lp = getLayoutParams(startView);
            this.mCachedStart = this.this$0.mPrimaryOrientation.getDecoratedStart(startView);
            if (lp.mFullSpan) {
                FullSpanItem fullSpanItem = this.this$0.mLazySpanLookup.getFullSpanItem(lp.getViewLayoutPosition());
                FullSpanItem fsi = fullSpanItem;
                if (fullSpanItem != null && fsi.mGapDir == -1) {
                    this.mCachedStart -= fsi.getGapForSpan(this.mIndex);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public int getStartLine() {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                return this.mCachedStart;
            }
            calculateCachedStart();
            return this.mCachedStart;
        }

        /* access modifiers changed from: 0000 */
        public int getEndLine(int i) {
            int def = i;
            int i2 = def;
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                return this.mCachedEnd;
            }
            int size = this.mViews.size();
            int i3 = size;
            if (size == 0) {
                return def;
            }
            calculateCachedEnd();
            return this.mCachedEnd;
        }

        /* access modifiers changed from: 0000 */
        public void calculateCachedEnd() {
            View endView = (View) this.mViews.get(this.mViews.size() - 1);
            LayoutParams lp = getLayoutParams(endView);
            this.mCachedEnd = this.this$0.mPrimaryOrientation.getDecoratedEnd(endView);
            if (lp.mFullSpan) {
                FullSpanItem fullSpanItem = this.this$0.mLazySpanLookup.getFullSpanItem(lp.getViewLayoutPosition());
                FullSpanItem fsi = fullSpanItem;
                if (fullSpanItem != null && fsi.mGapDir == 1) {
                    this.mCachedEnd += fsi.getGapForSpan(this.mIndex);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public int getEndLine() {
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                return this.mCachedEnd;
            }
            calculateCachedEnd();
            return this.mCachedEnd;
        }

        /* access modifiers changed from: 0000 */
        public void prependToSpan(View view) {
            View view2 = view;
            View view3 = view2;
            LayoutParams layoutParams = getLayoutParams(view2);
            LayoutParams lp = layoutParams;
            layoutParams.mSpan = this;
            this.mViews.add(0, view2);
            this.mCachedStart = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if (lp.isItemRemoved() || lp.isItemChanged()) {
                this.mDeletedSize += this.this$0.mPrimaryOrientation.getDecoratedMeasurement(view2);
            }
        }

        /* access modifiers changed from: 0000 */
        public void appendToSpan(View view) {
            View view2 = view;
            View view3 = view2;
            LayoutParams layoutParams = getLayoutParams(view2);
            LayoutParams lp = layoutParams;
            layoutParams.mSpan = this;
            boolean add = this.mViews.add(view2);
            this.mCachedEnd = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            if (lp.isItemRemoved() || lp.isItemChanged()) {
                this.mDeletedSize += this.this$0.mPrimaryOrientation.getDecoratedMeasurement(view2);
            }
        }

        /* access modifiers changed from: 0000 */
        public void cacheReferenceLineAndClear(boolean z, int i) {
            int reference;
            int offset = i;
            boolean reverseLayout = z;
            int i2 = offset;
            if (!reverseLayout) {
                reference = getStartLine(Integer.MIN_VALUE);
            } else {
                reference = getEndLine(Integer.MIN_VALUE);
            }
            clear();
            if (reference == Integer.MIN_VALUE) {
                return;
            }
            if ((!reverseLayout || reference >= this.this$0.mPrimaryOrientation.getEndAfterPadding()) && (reverseLayout || reference <= this.this$0.mPrimaryOrientation.getStartAfterPadding())) {
                if (offset != Integer.MIN_VALUE) {
                    reference += offset;
                }
                int i3 = reference;
                this.mCachedEnd = reference;
                this.mCachedStart = i3;
            }
        }

        /* access modifiers changed from: 0000 */
        public void clear() {
            this.mViews.clear();
            invalidateCache();
            this.mDeletedSize = 0;
        }

        /* access modifiers changed from: 0000 */
        public void invalidateCache() {
            this.mCachedStart = Integer.MIN_VALUE;
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        /* access modifiers changed from: 0000 */
        public void setLine(int i) {
            int line = i;
            int i2 = line;
            this.mCachedStart = line;
            this.mCachedEnd = line;
        }

        /* access modifiers changed from: 0000 */
        public void popEnd() {
            int size = this.mViews.size();
            int size2 = size;
            View end = (View) this.mViews.remove(size - 1);
            LayoutParams layoutParams = getLayoutParams(end);
            LayoutParams lp = layoutParams;
            layoutParams.mSpan = null;
            if (lp.isItemRemoved() || lp.isItemChanged()) {
                this.mDeletedSize -= this.this$0.mPrimaryOrientation.getDecoratedMeasurement(end);
            }
            if (size2 == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        /* access modifiers changed from: 0000 */
        public void popStart() {
            View start = (View) this.mViews.remove(0);
            LayoutParams layoutParams = getLayoutParams(start);
            LayoutParams lp = layoutParams;
            layoutParams.mSpan = null;
            if (this.mViews.size() == 0) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if (lp.isItemRemoved() || lp.isItemChanged()) {
                this.mDeletedSize -= this.this$0.mPrimaryOrientation.getDecoratedMeasurement(start);
            }
            this.mCachedStart = Integer.MIN_VALUE;
        }

        public int getDeletedSize() {
            return this.mDeletedSize;
        }

        /* access modifiers changed from: 0000 */
        public LayoutParams getLayoutParams(View view) {
            View view2 = view;
            View view3 = view2;
            return (LayoutParams) view2.getLayoutParams();
        }

        /* access modifiers changed from: 0000 */
        public void onOffset(int i) {
            int dt = i;
            int i2 = dt;
            if (this.mCachedStart != Integer.MIN_VALUE) {
                this.mCachedStart += dt;
            }
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                this.mCachedEnd += dt;
            }
        }

        public int findFirstVisibleItemPosition() {
            int findOneVisibleChild;
            if (!this.this$0.mReverseLayout) {
                findOneVisibleChild = findOneVisibleChild(0, this.mViews.size(), false);
            } else {
                findOneVisibleChild = findOneVisibleChild(this.mViews.size() - 1, -1, false);
            }
            return findOneVisibleChild;
        }

        public int findFirstCompletelyVisibleItemPosition() {
            int findOneVisibleChild;
            if (!this.this$0.mReverseLayout) {
                findOneVisibleChild = findOneVisibleChild(0, this.mViews.size(), true);
            } else {
                findOneVisibleChild = findOneVisibleChild(this.mViews.size() - 1, -1, true);
            }
            return findOneVisibleChild;
        }

        public int findLastVisibleItemPosition() {
            int findOneVisibleChild;
            if (!this.this$0.mReverseLayout) {
                findOneVisibleChild = findOneVisibleChild(this.mViews.size() - 1, -1, false);
            } else {
                findOneVisibleChild = findOneVisibleChild(0, this.mViews.size(), false);
            }
            return findOneVisibleChild;
        }

        public int findLastCompletelyVisibleItemPosition() {
            int findOneVisibleChild;
            if (!this.this$0.mReverseLayout) {
                findOneVisibleChild = findOneVisibleChild(this.mViews.size() - 1, -1, true);
            } else {
                findOneVisibleChild = findOneVisibleChild(0, this.mViews.size(), true);
            }
            return findOneVisibleChild;
        }

        /* access modifiers changed from: 0000 */
        public int findOneVisibleChild(int i, int i2, boolean z) {
            int fromIndex = i;
            int toIndex = i2;
            int i3 = fromIndex;
            int i4 = toIndex;
            boolean completelyVisible = z;
            int start = this.this$0.mPrimaryOrientation.getStartAfterPadding();
            int end = this.this$0.mPrimaryOrientation.getEndAfterPadding();
            int next = toIndex <= fromIndex ? -1 : 1;
            for (int i5 = fromIndex; i5 != toIndex; i5 += next) {
                View child = (View) this.mViews.get(i5);
                int childStart = this.this$0.mPrimaryOrientation.getDecoratedStart(child);
                int childEnd = this.this$0.mPrimaryOrientation.getDecoratedEnd(child);
                if (childStart < end && childEnd > start) {
                    if (!completelyVisible) {
                        return this.this$0.getPosition(child);
                    }
                    if (childStart >= start && childEnd <= end) {
                        return this.this$0.getPosition(child);
                    }
                }
            }
            return -1;
        }

        public View getFocusableViewAfter(int i, int i2) {
            int referenceChildPosition = i;
            int layoutDir = i2;
            int i3 = referenceChildPosition;
            int i4 = layoutDir;
            View candidate = null;
            if (layoutDir == -1) {
                int limit = this.mViews.size();
                for (int i5 = 0; i5 < limit; i5++) {
                    View view = (View) this.mViews.get(i5);
                    View view2 = view;
                    if (!view.isFocusable()) {
                        break;
                    }
                    if ((this.this$0.getPosition(view2) > referenceChildPosition) != this.this$0.mReverseLayout) {
                        break;
                    }
                    candidate = view2;
                }
            } else {
                for (int i6 = this.mViews.size() - 1; i6 >= 0; i6--) {
                    View view3 = (View) this.mViews.get(i6);
                    View view4 = view3;
                    if (!view3.isFocusable()) {
                        break;
                    }
                    if ((this.this$0.getPosition(view4) > referenceChildPosition) != (!this.this$0.mReverseLayout)) {
                        break;
                    }
                    candidate = view4;
                }
            }
            return candidate;
        }
    }

    public StaggeredGridLayoutManager(int i, int i2) {
        int spanCount = i;
        int orientation = i2;
        int i3 = spanCount;
        int i4 = orientation;
        this.mOrientation = orientation;
        setSpanCount(spanCount);
        setAutoMeasureEnabled(this.mGapStrategy != 0);
        this.mLayoutState = new LayoutState();
        createOrientationHelpers();
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        int defStyleRes = i2;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i3 = defStyleAttr;
        int i4 = defStyleRes;
        Properties properties = getProperties(context2, attrs, defStyleAttr, defStyleRes);
        setOrientation(properties.orientation);
        setSpanCount(properties.spanCount);
        setReverseLayout(properties.reverseLayout);
        setAutoMeasureEnabled(this.mGapStrategy != 0);
        this.mLayoutState = new LayoutState();
        createOrientationHelpers();
    }

    private void createOrientationHelpers() {
        this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
    }

    /* access modifiers changed from: 0000 */
    public boolean checkForGaps() {
        int minPos;
        int maxPos;
        if (getChildCount() == 0 || this.mGapStrategy == 0 || !isAttachedToWindow()) {
            return false;
        }
        if (!this.mShouldReverseLayout) {
            minPos = getFirstChildPosition();
            maxPos = getLastChildPosition();
        } else {
            minPos = getLastChildPosition();
            maxPos = getFirstChildPosition();
        }
        if (minPos == 0) {
            View hasGapsToFix = hasGapsToFix();
            View view = hasGapsToFix;
            if (hasGapsToFix != null) {
                this.mLazySpanLookup.clear();
                requestSimpleAnimationsInNextLayout();
                requestLayout();
                return true;
            }
        }
        if (!this.mLaidOutInvalidFullSpan) {
            return false;
        }
        int invalidGapDir = !this.mShouldReverseLayout ? 1 : -1;
        FullSpanItem firstFullSpanItemInRange = this.mLazySpanLookup.getFirstFullSpanItemInRange(minPos, maxPos + 1, invalidGapDir, true);
        FullSpanItem invalidFsi = firstFullSpanItemInRange;
        if (firstFullSpanItemInRange != null) {
            FullSpanItem firstFullSpanItemInRange2 = this.mLazySpanLookup.getFirstFullSpanItemInRange(minPos, invalidFsi.mPosition, invalidGapDir * -1, true);
            FullSpanItem validFsi = firstFullSpanItemInRange2;
            if (firstFullSpanItemInRange2 != null) {
                int forceInvalidateAfter = this.mLazySpanLookup.forceInvalidateAfter(validFsi.mPosition + 1);
            } else {
                int forceInvalidateAfter2 = this.mLazySpanLookup.forceInvalidateAfter(invalidFsi.mPosition);
            }
            requestSimpleAnimationsInNextLayout();
            requestLayout();
            return true;
        }
        this.mLaidOutInvalidFullSpan = false;
        int forceInvalidateAfter3 = this.mLazySpanLookup.forceInvalidateAfter(maxPos + 1);
        return false;
    }

    public void onScrollStateChanged(int i) {
        int state = i;
        int i2 = state;
        if (state == 0) {
            boolean checkForGaps = checkForGaps();
        }
    }

    public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
        RecyclerView view = recyclerView;
        RecyclerView recyclerView2 = view;
        Recycler recycler2 = recycler;
        boolean removeCallbacks = removeCallbacks(this.mCheckForGapsRunnable);
        for (int i = 0; i < this.mSpanCount; i++) {
            this.mSpans[i].clear();
        }
        view.requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public View hasGapsToFix() {
        int firstChildIndex;
        int childLimit;
        int endChildIndex = getChildCount() - 1;
        BitSet bitSet = new BitSet(this.mSpanCount);
        BitSet mSpansToCheck = bitSet;
        bitSet.set(0, this.mSpanCount, true);
        int preferredSpanDir = (this.mOrientation == 1 && isLayoutRTL()) ? (char) 1 : 65535;
        if (!this.mShouldReverseLayout) {
            firstChildIndex = 0;
            childLimit = endChildIndex + 1;
        } else {
            firstChildIndex = endChildIndex;
            childLimit = -1;
        }
        int nextChildDiff = firstChildIndex >= childLimit ? -1 : 1;
        for (int i = firstChildIndex; i != childLimit; i += nextChildDiff) {
            View childAt = getChildAt(i);
            View child = childAt;
            LayoutParams lp = (LayoutParams) childAt.getLayoutParams();
            if (mSpansToCheck.get(lp.mSpan.mIndex)) {
                if (checkSpanForGap(lp.mSpan)) {
                    return child;
                }
                mSpansToCheck.clear(lp.mSpan.mIndex);
            }
            if (!lp.mFullSpan && i + nextChildDiff != childLimit) {
                View nextChild = getChildAt(i + nextChildDiff);
                boolean compareSpans = false;
                if (!this.mShouldReverseLayout) {
                    int myStart = this.mPrimaryOrientation.getDecoratedStart(child);
                    int nextStart = this.mPrimaryOrientation.getDecoratedStart(nextChild);
                    if (myStart > nextStart) {
                        return child;
                    }
                    if (myStart == nextStart) {
                        compareSpans = true;
                    }
                } else {
                    int myEnd = this.mPrimaryOrientation.getDecoratedEnd(child);
                    int nextEnd = this.mPrimaryOrientation.getDecoratedEnd(nextChild);
                    if (myEnd < nextEnd) {
                        return child;
                    }
                    if (myEnd == nextEnd) {
                        compareSpans = true;
                    }
                }
                if (!compareSpans) {
                    continue;
                } else {
                    if ((lp.mSpan.mIndex - ((LayoutParams) nextChild.getLayoutParams()).mSpan.mIndex < 0) != (preferredSpanDir < 0)) {
                        return child;
                    }
                }
            }
        }
        return null;
    }

    private boolean checkSpanForGap(Span span) {
        Span span2 = span;
        Span span3 = span2;
        if (!this.mShouldReverseLayout) {
            if (span2.getStartLine() > this.mPrimaryOrientation.getStartAfterPadding()) {
                LayoutParams layoutParams = span2.getLayoutParams((View) span2.mViews.get(0));
                LayoutParams layoutParams2 = layoutParams;
                return !layoutParams.mFullSpan;
            }
        } else if (span2.getEndLine() < this.mPrimaryOrientation.getEndAfterPadding()) {
            LayoutParams layoutParams3 = span2.getLayoutParams((View) span2.mViews.get(span2.mViews.size() - 1));
            LayoutParams layoutParams4 = layoutParams3;
            return !layoutParams3.mFullSpan;
        }
        return false;
    }

    public void setSpanCount(int i) {
        int spanCount = i;
        int i2 = spanCount;
        assertNotInLayoutOrScroll(null);
        if (spanCount != this.mSpanCount) {
            invalidateSpanAssignments();
            this.mSpanCount = spanCount;
            this.mRemainingSpans = new BitSet(this.mSpanCount);
            this.mSpans = new Span[this.mSpanCount];
            for (int i3 = 0; i3 < this.mSpanCount; i3++) {
                this.mSpans[i3] = new Span(this, i3);
            }
            requestLayout();
        }
    }

    public void setOrientation(int i) {
        int orientation = i;
        int i2 = orientation;
        if (orientation == 0 || orientation == 1) {
            assertNotInLayoutOrScroll(null);
            if (orientation != this.mOrientation) {
                this.mOrientation = orientation;
                OrientationHelper tmp = this.mPrimaryOrientation;
                this.mPrimaryOrientation = this.mSecondaryOrientation;
                this.mSecondaryOrientation = tmp;
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation.");
    }

    public void setReverseLayout(boolean z) {
        boolean reverseLayout = z;
        assertNotInLayoutOrScroll(null);
        if (!(this.mPendingSavedState == null || this.mPendingSavedState.mReverseLayout == reverseLayout)) {
            this.mPendingSavedState.mReverseLayout = reverseLayout;
        }
        this.mReverseLayout = reverseLayout;
        requestLayout();
    }

    public int getGapStrategy() {
        return this.mGapStrategy;
    }

    public void setGapStrategy(int i) {
        int gapStrategy = i;
        int i2 = gapStrategy;
        assertNotInLayoutOrScroll(null);
        if (gapStrategy == this.mGapStrategy) {
            return;
        }
        if (gapStrategy == 0 || gapStrategy == 2) {
            this.mGapStrategy = gapStrategy;
            setAutoMeasureEnabled(this.mGapStrategy != 0);
            requestLayout();
            return;
        }
        throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
    }

    public void assertNotInLayoutOrScroll(String str) {
        String message = str;
        String str2 = message;
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(message);
        }
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public void invalidateSpanAssignments() {
        this.mLazySpanLookup.clear();
        requestLayout();
    }

    private void resolveShouldLayoutReverse() {
        if (this.mOrientation != 1 && isLayoutRTL()) {
            this.mShouldReverseLayout = !this.mReverseLayout;
        } else {
            this.mShouldReverseLayout = this.mReverseLayout;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
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
        int horizontalPadding = getPaddingLeft() + getPaddingRight();
        int verticalPadding = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation != 1) {
            width = chooseSize(wSpec, childrenBounds.width() + horizontalPadding, getMinimumWidth());
            height = chooseSize(hSpec, (this.mSizePerSpan * this.mSpanCount) + verticalPadding, getMinimumHeight());
        } else {
            height = chooseSize(hSpec, childrenBounds.height() + verticalPadding, getMinimumHeight());
            width = chooseSize(wSpec, (this.mSizePerSpan * this.mSpanCount) + horizontalPadding, getMinimumWidth());
        }
        setMeasuredDimension(width, height);
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        onLayoutChildren(recycler2, state2, true);
    }

    private void onLayoutChildren(Recycler recycler, State state, boolean z) {
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        boolean shouldCheckForGaps = z;
        AnchorInfo anchorInfo = this.mAnchorInfo;
        if ((this.mPendingSavedState == null && this.mPendingScrollPosition == -1) || state2.getItemCount() != 0) {
            boolean recalculateAnchor = (anchorInfo.mValid && this.mPendingScrollPosition == -1 && this.mPendingSavedState == null) ? false : true;
            if (recalculateAnchor) {
                anchorInfo.reset();
                if (this.mPendingSavedState == null) {
                    resolveShouldLayoutReverse();
                    anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
                } else {
                    applyPendingSavedState(anchorInfo);
                }
                updateAnchorInfoForLayout(state2, anchorInfo);
                anchorInfo.mValid = true;
            }
            if (this.mPendingSavedState == null && this.mPendingScrollPosition == -1 && !(anchorInfo.mLayoutFromEnd == this.mLastLayoutFromEnd && isLayoutRTL() == this.mLastLayoutRTL)) {
                this.mLazySpanLookup.clear();
                anchorInfo.mInvalidateOffsets = true;
            }
            if (getChildCount() > 0 && (this.mPendingSavedState == null || this.mPendingSavedState.mSpanOffsetsSize < 1)) {
                if (anchorInfo.mInvalidateOffsets) {
                    for (int i = 0; i < this.mSpanCount; i++) {
                        this.mSpans[i].clear();
                        if (anchorInfo.mOffset != Integer.MIN_VALUE) {
                            this.mSpans[i].setLine(anchorInfo.mOffset);
                        }
                    }
                } else if (!recalculateAnchor && this.mAnchorInfo.mSpanReferenceLines != null) {
                    for (int i2 = 0; i2 < this.mSpanCount; i2++) {
                        Span span = this.mSpans[i2];
                        Span span2 = span;
                        span.clear();
                        span2.setLine(this.mAnchorInfo.mSpanReferenceLines[i2]);
                    }
                } else {
                    for (int i3 = 0; i3 < this.mSpanCount; i3++) {
                        this.mSpans[i3].cacheReferenceLineAndClear(this.mShouldReverseLayout, anchorInfo.mOffset);
                    }
                    this.mAnchorInfo.saveSpanReferenceLines(this.mSpans);
                }
            }
            detachAndScrapAttachedViews(recycler2);
            this.mLayoutState.mRecycle = false;
            this.mLaidOutInvalidFullSpan = false;
            updateMeasureSpecs(this.mSecondaryOrientation.getTotalSpace());
            updateLayoutState(anchorInfo.mPosition, state2);
            if (!anchorInfo.mLayoutFromEnd) {
                setLayoutStateDirection(1);
                int fill = fill(recycler2, this.mLayoutState, state2);
                setLayoutStateDirection(-1);
                this.mLayoutState.mCurrentPosition = anchorInfo.mPosition + this.mLayoutState.mItemDirection;
                int fill2 = fill(recycler2, this.mLayoutState, state2);
            } else {
                setLayoutStateDirection(-1);
                int fill3 = fill(recycler2, this.mLayoutState, state2);
                setLayoutStateDirection(1);
                this.mLayoutState.mCurrentPosition = anchorInfo.mPosition + this.mLayoutState.mItemDirection;
                int fill4 = fill(recycler2, this.mLayoutState, state2);
            }
            repositionToWrapContentIfNecessary();
            if (getChildCount() > 0) {
                if (!this.mShouldReverseLayout) {
                    fixStartGap(recycler2, state2, true);
                    fixEndGap(recycler2, state2, false);
                } else {
                    fixEndGap(recycler2, state2, true);
                    fixStartGap(recycler2, state2, false);
                }
            }
            boolean hasGaps = false;
            if (shouldCheckForGaps && !state2.isPreLayout()) {
                if (this.mGapStrategy != 0 && getChildCount() > 0 && (this.mLaidOutInvalidFullSpan || hasGapsToFix() != null)) {
                    boolean removeCallbacks = removeCallbacks(this.mCheckForGapsRunnable);
                    if (checkForGaps()) {
                        hasGaps = true;
                    }
                }
            }
            if (state2.isPreLayout()) {
                this.mAnchorInfo.reset();
            }
            this.mLastLayoutFromEnd = anchorInfo.mLayoutFromEnd;
            this.mLastLayoutRTL = isLayoutRTL();
            if (hasGaps) {
                this.mAnchorInfo.reset();
                onLayoutChildren(recycler2, state2, false);
            }
            return;
        }
        removeAndRecycleAllViews(recycler2);
        anchorInfo.reset();
    }

    public void onLayoutCompleted(State state) {
        State state2 = state;
        State state3 = state2;
        super.onLayoutCompleted(state2);
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo.reset();
    }

    private void repositionToWrapContentIfNecessary() {
        if (this.mSecondaryOrientation.getMode() != 1073741824) {
            float maxSize = 0.0f;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                float decoratedMeasurement = (float) this.mSecondaryOrientation.getDecoratedMeasurement(child);
                float size = decoratedMeasurement;
                if (decoratedMeasurement >= maxSize) {
                    LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                    LayoutParams layoutParams2 = layoutParams;
                    if (layoutParams.isFullSpan()) {
                        size = (1.0f * size) / ((float) this.mSpanCount);
                    }
                    maxSize = Math.max(maxSize, size);
                }
            }
            int before = this.mSizePerSpan;
            int desired = Math.round(maxSize * ((float) this.mSpanCount));
            if (this.mSecondaryOrientation.getMode() == Integer.MIN_VALUE) {
                desired = Math.min(desired, this.mSecondaryOrientation.getTotalSpace());
            }
            updateMeasureSpecs(desired);
            if (this.mSizePerSpan != before) {
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = getChildAt(i2);
                    View child2 = childAt;
                    LayoutParams layoutParams3 = (LayoutParams) childAt.getLayoutParams();
                    LayoutParams lp = layoutParams3;
                    if (!layoutParams3.mFullSpan) {
                        if (isLayoutRTL() && this.mOrientation == 1) {
                            int i3 = (-((this.mSpanCount - 1) - lp.mSpan.mIndex)) * before;
                            int i4 = i3;
                            child2.offsetLeftAndRight(((-((this.mSpanCount - 1) - lp.mSpan.mIndex)) * this.mSizePerSpan) - i3);
                        } else {
                            int newOffset = lp.mSpan.mIndex * this.mSizePerSpan;
                            int prevOffset = lp.mSpan.mIndex * before;
                            if (this.mOrientation != 1) {
                                child2.offsetTopAndBottom(newOffset - prevOffset);
                            } else {
                                child2.offsetLeftAndRight(newOffset - prevOffset);
                            }
                        }
                    }
                }
            }
        }
    }

    private void applyPendingSavedState(AnchorInfo anchorInfo) {
        AnchorInfo anchorInfo2 = anchorInfo;
        AnchorInfo anchorInfo3 = anchorInfo2;
        if (this.mPendingSavedState.mSpanOffsetsSize > 0) {
            if (this.mPendingSavedState.mSpanOffsetsSize != this.mSpanCount) {
                this.mPendingSavedState.invalidateSpanInfo();
                this.mPendingSavedState.mAnchorPosition = this.mPendingSavedState.mVisibleAnchorPosition;
            } else {
                for (int i = 0; i < this.mSpanCount; i++) {
                    this.mSpans[i].clear();
                    int i2 = this.mPendingSavedState.mSpanOffsets[i];
                    int line = i2;
                    if (i2 != Integer.MIN_VALUE) {
                        if (!this.mPendingSavedState.mAnchorLayoutFromEnd) {
                            line += this.mPrimaryOrientation.getStartAfterPadding();
                        } else {
                            line += this.mPrimaryOrientation.getEndAfterPadding();
                        }
                    }
                    this.mSpans[i].setLine(line);
                }
            }
        }
        this.mLastLayoutRTL = this.mPendingSavedState.mLastLayoutRTL;
        setReverseLayout(this.mPendingSavedState.mReverseLayout);
        resolveShouldLayoutReverse();
        if (this.mPendingSavedState.mAnchorPosition == -1) {
            anchorInfo2.mLayoutFromEnd = this.mShouldReverseLayout;
        } else {
            this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
            anchorInfo2.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
        }
        if (this.mPendingSavedState.mSpanLookupSize > 1) {
            this.mLazySpanLookup.mData = this.mPendingSavedState.mSpanLookup;
            this.mLazySpanLookup.mFullSpanItems = this.mPendingSavedState.mFullSpanItems;
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateAnchorInfoForLayout(State state, AnchorInfo anchorInfo) {
        State state2 = state;
        AnchorInfo anchorInfo2 = anchorInfo;
        State state3 = state2;
        AnchorInfo anchorInfo3 = anchorInfo2;
        if (!updateAnchorFromPendingData(state2, anchorInfo2) && !updateAnchorFromChildren(state2, anchorInfo2)) {
            anchorInfo2.assignCoordinateFromPadding();
            anchorInfo2.mPosition = 0;
        }
    }

    private boolean updateAnchorFromChildren(State state, AnchorInfo anchorInfo) {
        int findLastReferenceChildPosition;
        State state2 = state;
        AnchorInfo anchorInfo2 = anchorInfo;
        State state3 = state2;
        AnchorInfo anchorInfo3 = anchorInfo2;
        if (!this.mLastLayoutFromEnd) {
            findLastReferenceChildPosition = findFirstReferenceChildPosition(state2.getItemCount());
        } else {
            findLastReferenceChildPosition = findLastReferenceChildPosition(state2.getItemCount());
        }
        anchorInfo2.mPosition = findLastReferenceChildPosition;
        anchorInfo2.mOffset = Integer.MIN_VALUE;
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean updateAnchorFromPendingData(State state, AnchorInfo anchorInfo) {
        int lastChildPosition;
        int endAfterPadding;
        State state2 = state;
        AnchorInfo anchorInfo2 = anchorInfo;
        State state3 = state2;
        AnchorInfo anchorInfo3 = anchorInfo2;
        if (state2.isPreLayout() || this.mPendingScrollPosition == -1) {
            return false;
        }
        if (this.mPendingScrollPosition >= 0 && this.mPendingScrollPosition < state2.getItemCount()) {
            if (this.mPendingSavedState == null || this.mPendingSavedState.mAnchorPosition == -1 || this.mPendingSavedState.mSpanOffsetsSize < 1) {
                View findViewByPosition = findViewByPosition(this.mPendingScrollPosition);
                View child = findViewByPosition;
                if (findViewByPosition == null) {
                    anchorInfo2.mPosition = this.mPendingScrollPosition;
                    if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                        anchorInfo2.assignCoordinateFromPadding(this.mPendingScrollPositionOffset);
                    } else {
                        int calculateScrollDirectionForPosition = calculateScrollDirectionForPosition(anchorInfo2.mPosition);
                        int i = calculateScrollDirectionForPosition;
                        anchorInfo2.mLayoutFromEnd = calculateScrollDirectionForPosition == 1;
                        anchorInfo2.assignCoordinateFromPadding();
                    }
                    anchorInfo2.mInvalidateOffsets = true;
                } else {
                    if (!this.mShouldReverseLayout) {
                        lastChildPosition = getFirstChildPosition();
                    } else {
                        lastChildPosition = getLastChildPosition();
                    }
                    anchorInfo2.mPosition = lastChildPosition;
                    if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
                        int decoratedMeasurement = this.mPrimaryOrientation.getDecoratedMeasurement(child);
                        int i2 = decoratedMeasurement;
                        if (decoratedMeasurement <= this.mPrimaryOrientation.getTotalSpace()) {
                            int decoratedStart = this.mPrimaryOrientation.getDecoratedStart(child) - this.mPrimaryOrientation.getStartAfterPadding();
                            int startGap = decoratedStart;
                            if (decoratedStart >= 0) {
                                int endAfterPadding2 = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd(child);
                                int endGap = endAfterPadding2;
                                if (endAfterPadding2 >= 0) {
                                    anchorInfo2.mOffset = Integer.MIN_VALUE;
                                } else {
                                    anchorInfo2.mOffset = endGap;
                                    return true;
                                }
                            } else {
                                anchorInfo2.mOffset = -startGap;
                                return true;
                            }
                        } else {
                            if (!anchorInfo2.mLayoutFromEnd) {
                                endAfterPadding = this.mPrimaryOrientation.getStartAfterPadding();
                            } else {
                                endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding();
                            }
                            anchorInfo2.mOffset = endAfterPadding;
                            return true;
                        }
                    } else {
                        if (!anchorInfo2.mLayoutFromEnd) {
                            int startAfterPadding = this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset;
                            int i3 = startAfterPadding;
                            anchorInfo2.mOffset = startAfterPadding - this.mPrimaryOrientation.getDecoratedStart(child);
                        } else {
                            int endAfterPadding3 = this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset;
                            int i4 = endAfterPadding3;
                            anchorInfo2.mOffset = endAfterPadding3 - this.mPrimaryOrientation.getDecoratedEnd(child);
                        }
                        return true;
                    }
                }
            } else {
                anchorInfo2.mOffset = Integer.MIN_VALUE;
                anchorInfo2.mPosition = this.mPendingScrollPosition;
            }
            return true;
        }
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void updateMeasureSpecs(int i) {
        int totalSpace = i;
        int i2 = totalSpace;
        this.mSizePerSpan = totalSpace / this.mSpanCount;
        this.mFullSizeSpec = MeasureSpec.makeMeasureSpec(totalSpace, this.mSecondaryOrientation.getMode());
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null;
    }

    public int[] findFirstVisibleItemPositions(int[] iArr) {
        int[] into = iArr;
        int[] into2 = into;
        if (into == null) {
            into2 = new int[this.mSpanCount];
        } else if (into.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + into.length);
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            into2[i] = this.mSpans[i].findFirstVisibleItemPosition();
        }
        return into2;
    }

    public int[] findFirstCompletelyVisibleItemPositions(int[] iArr) {
        int[] into = iArr;
        int[] into2 = into;
        if (into == null) {
            into2 = new int[this.mSpanCount];
        } else if (into.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + into.length);
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            into2[i] = this.mSpans[i].findFirstCompletelyVisibleItemPosition();
        }
        return into2;
    }

    public int[] findLastVisibleItemPositions(int[] iArr) {
        int[] into = iArr;
        int[] into2 = into;
        if (into == null) {
            into2 = new int[this.mSpanCount];
        } else if (into.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + into.length);
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            into2[i] = this.mSpans[i].findLastVisibleItemPosition();
        }
        return into2;
    }

    public int[] findLastCompletelyVisibleItemPositions(int[] iArr) {
        int[] into = iArr;
        int[] into2 = into;
        if (into == null) {
            into2 = new int[this.mSpanCount];
        } else if (into.length < this.mSpanCount) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + into.length);
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            into2[i] = this.mSpans[i].findLastCompletelyVisibleItemPosition();
        }
        return into2;
    }

    public int computeHorizontalScrollOffset(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollOffset(state2);
    }

    private int computeScrollOffset(State state) {
        State state2 = state;
        State state3 = state2;
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollOffset(state2, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled), findFirstVisibleItemClosestToEnd(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    public int computeVerticalScrollOffset(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollOffset(state2);
    }

    public int computeHorizontalScrollExtent(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollExtent(state2);
    }

    private int computeScrollExtent(State state) {
        State state2 = state;
        State state3 = state2;
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollExtent(state2, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled), findFirstVisibleItemClosestToEnd(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled);
    }

    public int computeVerticalScrollExtent(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollExtent(state2);
    }

    public int computeHorizontalScrollRange(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollRange(state2);
    }

    private int computeScrollRange(State state) {
        State state2 = state;
        State state3 = state2;
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollRange(state2, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled), findFirstVisibleItemClosestToEnd(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled);
    }

    public int computeVerticalScrollRange(State state) {
        State state2 = state;
        State state3 = state2;
        return computeScrollRange(state2);
    }

    private void measureChildWithDecorationsAndMargin(View view, LayoutParams layoutParams, boolean z) {
        View child = view;
        LayoutParams lp = layoutParams;
        View view2 = child;
        LayoutParams layoutParams2 = lp;
        boolean alreadyMeasured = z;
        if (!lp.mFullSpan) {
            if (this.mOrientation != 1) {
                measureChildWithDecorationsAndMargin(child, getChildMeasureSpec(getWidth(), getWidthMode(), 0, lp.width, true), getChildMeasureSpec(this.mSizePerSpan, getHeightMode(), 0, lp.height, false), alreadyMeasured);
                return;
            }
            measureChildWithDecorationsAndMargin(child, getChildMeasureSpec(this.mSizePerSpan, getWidthMode(), 0, lp.width, false), getChildMeasureSpec(getHeight(), getHeightMode(), 0, lp.height, true), alreadyMeasured);
        } else if (this.mOrientation != 1) {
            measureChildWithDecorationsAndMargin(child, getChildMeasureSpec(getWidth(), getWidthMode(), 0, lp.width, true), this.mFullSizeSpec, alreadyMeasured);
        } else {
            measureChildWithDecorationsAndMargin(child, this.mFullSizeSpec, getChildMeasureSpec(getHeight(), getHeightMode(), 0, lp.height, true), alreadyMeasured);
        }
    }

    private void measureChildWithDecorationsAndMargin(View view, int i, int i2, boolean z) {
        boolean shouldReMeasureChild;
        View child = view;
        int widthSpec = i;
        int heightSpec = i2;
        View view2 = child;
        int i3 = widthSpec;
        int i4 = heightSpec;
        boolean alreadyMeasured = z;
        calculateItemDecorationsForChild(child, this.mTmpRect);
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int widthSpec2 = updateSpecWithExtra(widthSpec, lp.leftMargin + this.mTmpRect.left, lp.rightMargin + this.mTmpRect.right);
        int heightSpec2 = updateSpecWithExtra(heightSpec, lp.topMargin + this.mTmpRect.top, lp.bottomMargin + this.mTmpRect.bottom);
        if (!alreadyMeasured) {
            shouldReMeasureChild = shouldMeasureChild(child, widthSpec2, heightSpec2, lp);
        } else {
            shouldReMeasureChild = shouldReMeasureChild(child, widthSpec2, heightSpec2, lp);
        }
        if (shouldReMeasureChild) {
            child.measure(widthSpec2, heightSpec2);
        }
    }

    private int updateSpecWithExtra(int i, int i2, int i3) {
        int spec = i;
        int startInset = i2;
        int endInset = i3;
        int i4 = spec;
        int i5 = startInset;
        int i6 = endInset;
        if (startInset == 0 && endInset == 0) {
            return spec;
        }
        int mode = MeasureSpec.getMode(spec);
        int mode2 = mode;
        if (mode == Integer.MIN_VALUE || mode2 == 1073741824) {
            return MeasureSpec.makeMeasureSpec(Math.max(0, (MeasureSpec.getSize(spec) - startInset) - endInset), mode2);
        }
        return spec;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            this.mPendingSavedState = (SavedState) state;
            requestLayout();
        }
    }

    public Parcelable onSaveInstanceState() {
        int lastChildPosition;
        int line;
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState savedState = new SavedState();
        SavedState state = savedState;
        savedState.mReverseLayout = this.mReverseLayout;
        state.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
        state.mLastLayoutRTL = this.mLastLayoutRTL;
        if (this.mLazySpanLookup == null || this.mLazySpanLookup.mData == null) {
            state.mSpanLookupSize = 0;
        } else {
            state.mSpanLookup = this.mLazySpanLookup.mData;
            state.mSpanLookupSize = state.mSpanLookup.length;
            state.mFullSpanItems = this.mLazySpanLookup.mFullSpanItems;
        }
        if (getChildCount() <= 0) {
            state.mAnchorPosition = -1;
            state.mVisibleAnchorPosition = -1;
            state.mSpanOffsetsSize = 0;
        } else {
            if (!this.mLastLayoutFromEnd) {
                lastChildPosition = getFirstChildPosition();
            } else {
                lastChildPosition = getLastChildPosition();
            }
            state.mAnchorPosition = lastChildPosition;
            state.mVisibleAnchorPosition = findFirstVisibleItemPositionInt();
            state.mSpanOffsetsSize = this.mSpanCount;
            state.mSpanOffsets = new int[this.mSpanCount];
            for (int i = 0; i < this.mSpanCount; i++) {
                if (!this.mLastLayoutFromEnd) {
                    int startLine = this.mSpans[i].getStartLine(Integer.MIN_VALUE);
                    line = startLine;
                    if (startLine != Integer.MIN_VALUE) {
                        line -= this.mPrimaryOrientation.getStartAfterPadding();
                    }
                } else {
                    int endLine = this.mSpans[i].getEndLine(Integer.MIN_VALUE);
                    line = endLine;
                    if (endLine != Integer.MIN_VALUE) {
                        line -= this.mPrimaryOrientation.getEndAfterPadding();
                    }
                }
                state.mSpanOffsets[i] = line;
            }
        }
        return state;
    }

    public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        View host = view;
        AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
        Recycler recycler2 = recycler;
        State state2 = state;
        View view2 = host;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
        android.view.ViewGroup.LayoutParams layoutParams = host.getLayoutParams();
        android.view.ViewGroup.LayoutParams lp = layoutParams;
        if (layoutParams instanceof LayoutParams) {
            LayoutParams sglp = (LayoutParams) lp;
            if (this.mOrientation != 0) {
                info.setCollectionItemInfo(CollectionItemInfoCompat.obtain(-1, -1, sglp.getSpanIndex(), !sglp.mFullSpan ? 1 : this.mSpanCount, sglp.mFullSpan, false));
            } else {
                info.setCollectionItemInfo(CollectionItemInfoCompat.obtain(sglp.getSpanIndex(), !sglp.mFullSpan ? 1 : this.mSpanCount, -1, -1, sglp.mFullSpan, false));
            }
            return;
        }
        super.onInitializeAccessibilityNodeInfoForItem(host, info);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        super.onInitializeAccessibilityEvent(event);
        if (getChildCount() > 0) {
            AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(event);
            View start = findFirstVisibleItemClosestToStart(false);
            View end = findFirstVisibleItemClosestToEnd(false);
            if (start != null && end != null) {
                int startPos = getPosition(start);
                int endPos = getPosition(end);
                if (startPos >= endPos) {
                    record.setFromIndex(endPos);
                    record.setToIndex(startPos);
                } else {
                    record.setFromIndex(startPos);
                    record.setToIndex(endPos);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int findFirstVisibleItemPositionInt() {
        View findFirstVisibleItemClosestToEnd;
        if (!this.mShouldReverseLayout) {
            findFirstVisibleItemClosestToEnd = findFirstVisibleItemClosestToStart(true);
        } else {
            findFirstVisibleItemClosestToEnd = findFirstVisibleItemClosestToEnd(true);
        }
        View first = findFirstVisibleItemClosestToEnd;
        return first != null ? getPosition(first) : -1;
    }

    public int getRowCountForAccessibility(Recycler recycler, State state) {
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (this.mOrientation != 0) {
            return super.getRowCountForAccessibility(recycler2, state2);
        }
        return this.mSpanCount;
    }

    public int getColumnCountForAccessibility(Recycler recycler, State state) {
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (this.mOrientation != 1) {
            return super.getColumnCountForAccessibility(recycler2, state2);
        }
        return this.mSpanCount;
    }

    /* access modifiers changed from: 0000 */
    public View findFirstVisibleItemClosestToStart(boolean z) {
        boolean fullyVisible = z;
        int boundsStart = this.mPrimaryOrientation.getStartAfterPadding();
        int boundsEnd = this.mPrimaryOrientation.getEndAfterPadding();
        int limit = getChildCount();
        View partiallyVisible = null;
        for (int i = 0; i < limit; i++) {
            View child = getChildAt(i);
            int childStart = this.mPrimaryOrientation.getDecoratedStart(child);
            int decoratedEnd = this.mPrimaryOrientation.getDecoratedEnd(child);
            int i2 = decoratedEnd;
            if (decoratedEnd > boundsStart && childStart < boundsEnd) {
                if (childStart >= boundsStart || !fullyVisible) {
                    return child;
                }
                if (partiallyVisible == null) {
                    partiallyVisible = child;
                }
            }
        }
        return partiallyVisible;
    }

    /* access modifiers changed from: 0000 */
    public View findFirstVisibleItemClosestToEnd(boolean z) {
        boolean fullyVisible = z;
        int boundsStart = this.mPrimaryOrientation.getStartAfterPadding();
        int boundsEnd = this.mPrimaryOrientation.getEndAfterPadding();
        View partiallyVisible = null;
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            int childStart = this.mPrimaryOrientation.getDecoratedStart(child);
            int decoratedEnd = this.mPrimaryOrientation.getDecoratedEnd(child);
            int childEnd = decoratedEnd;
            if (decoratedEnd > boundsStart && childStart < boundsEnd) {
                if (childEnd <= boundsEnd || !fullyVisible) {
                    return child;
                }
                if (partiallyVisible == null) {
                    partiallyVisible = child;
                }
            }
        }
        return partiallyVisible;
    }

    private void fixEndGap(Recycler recycler, State state, boolean z) {
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        boolean canOffsetChildren = z;
        int maxEnd = getMaxEnd(Integer.MIN_VALUE);
        int maxEndLine = maxEnd;
        if (maxEnd != Integer.MIN_VALUE) {
            int endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding() - maxEndLine;
            int gap = endAfterPadding;
            if (endAfterPadding > 0) {
                int gap2 = gap - (-scrollBy(-gap, recycler2, state2));
                if (canOffsetChildren && gap2 > 0) {
                    this.mPrimaryOrientation.offsetChildren(gap2);
                }
            }
        }
    }

    private void fixStartGap(Recycler recycler, State state, boolean z) {
        Recycler recycler2 = recycler;
        State state2 = state;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        boolean canOffsetChildren = z;
        int minStart = getMinStart(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        int minStartLine = minStart;
        if (minStart != Integer.MAX_VALUE) {
            int startAfterPadding = minStartLine - this.mPrimaryOrientation.getStartAfterPadding();
            int gap = startAfterPadding;
            if (startAfterPadding > 0) {
                int gap2 = gap - scrollBy(gap, recycler2, state2);
                if (canOffsetChildren && gap2 > 0) {
                    this.mPrimaryOrientation.offsetChildren(-gap2);
                }
            }
        }
    }

    private void updateLayoutState(int i, State state) {
        int anchorPosition = i;
        State state2 = state;
        int i2 = anchorPosition;
        State state3 = state2;
        this.mLayoutState.mAvailable = 0;
        this.mLayoutState.mCurrentPosition = anchorPosition;
        int startExtra = 0;
        int endExtra = 0;
        if (isSmoothScrolling()) {
            int targetScrollPosition = state2.getTargetScrollPosition();
            int targetPos = targetScrollPosition;
            if (targetScrollPosition != -1) {
                if (this.mShouldReverseLayout != (targetPos < anchorPosition)) {
                    startExtra = this.mPrimaryOrientation.getTotalSpace();
                } else {
                    endExtra = this.mPrimaryOrientation.getTotalSpace();
                }
            }
        }
        boolean clipToPadding = getClipToPadding();
        boolean z = clipToPadding;
        if (!clipToPadding) {
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEnd() + endExtra;
            this.mLayoutState.mStartLine = -startExtra;
        } else {
            this.mLayoutState.mStartLine = this.mPrimaryOrientation.getStartAfterPadding() - startExtra;
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEndAfterPadding() + endExtra;
        }
        this.mLayoutState.mStopInFocusable = false;
        this.mLayoutState.mRecycle = true;
        this.mLayoutState.mInfinite = this.mPrimaryOrientation.getMode() == 0 && this.mPrimaryOrientation.getEnd() == 0;
    }

    private void setLayoutStateDirection(int i) {
        int direction = i;
        int i2 = direction;
        this.mLayoutState.mLayoutDirection = direction;
        this.mLayoutState.mItemDirection = this.mShouldReverseLayout != (direction == -1) ? -1 : 1;
    }

    public void offsetChildrenHorizontal(int i) {
        int dx = i;
        int i2 = dx;
        super.offsetChildrenHorizontal(dx);
        for (int i3 = 0; i3 < this.mSpanCount; i3++) {
            this.mSpans[i3].onOffset(dx);
        }
    }

    public void offsetChildrenVertical(int i) {
        int dy = i;
        int i2 = dy;
        super.offsetChildrenVertical(dy);
        for (int i3 = 0; i3 < this.mSpanCount; i3++) {
            this.mSpans[i3].onOffset(dy);
        }
    }

    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        int positionStart = i;
        int itemCount = i2;
        RecyclerView recyclerView2 = recyclerView;
        int i3 = positionStart;
        int i4 = itemCount;
        handleUpdate(positionStart, itemCount, 2);
    }

    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        int positionStart = i;
        int itemCount = i2;
        RecyclerView recyclerView2 = recyclerView;
        int i3 = positionStart;
        int i4 = itemCount;
        handleUpdate(positionStart, itemCount, 1);
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = recyclerView;
        this.mLazySpanLookup.clear();
        requestLayout();
    }

    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        int from = i;
        int to = i2;
        RecyclerView recyclerView2 = recyclerView;
        int i4 = from;
        int i5 = to;
        int i6 = i3;
        handleUpdate(from, to, 8);
    }

    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        int positionStart = i;
        int itemCount = i2;
        RecyclerView recyclerView2 = recyclerView;
        int i3 = positionStart;
        int i4 = itemCount;
        Object obj2 = obj;
        handleUpdate(positionStart, itemCount, 4);
    }

    private void handleUpdate(int i, int i2, int i3) {
        int affectedRangeEnd;
        int affectedRangeStart;
        int positionStart = i;
        int itemCountOrToPosition = i2;
        int cmd = i3;
        int i4 = positionStart;
        int i5 = itemCountOrToPosition;
        int i6 = cmd;
        int minPosition = !this.mShouldReverseLayout ? getFirstChildPosition() : getLastChildPosition();
        if (cmd != 8) {
            affectedRangeStart = positionStart;
            affectedRangeEnd = positionStart + itemCountOrToPosition;
        } else if (positionStart >= itemCountOrToPosition) {
            affectedRangeEnd = positionStart + 1;
            affectedRangeStart = itemCountOrToPosition;
        } else {
            affectedRangeEnd = itemCountOrToPosition + 1;
            affectedRangeStart = positionStart;
        }
        int invalidateAfter = this.mLazySpanLookup.invalidateAfter(affectedRangeStart);
        switch (cmd) {
            case 1:
                this.mLazySpanLookup.offsetForAddition(positionStart, itemCountOrToPosition);
                break;
            case 2:
                this.mLazySpanLookup.offsetForRemoval(positionStart, itemCountOrToPosition);
                break;
            case 8:
                this.mLazySpanLookup.offsetForRemoval(positionStart, 1);
                this.mLazySpanLookup.offsetForAddition(itemCountOrToPosition, 1);
                break;
        }
        if (affectedRangeEnd > minPosition) {
            if (affectedRangeStart <= (!this.mShouldReverseLayout ? getLastChildPosition() : getFirstChildPosition())) {
                requestLayout();
            }
        }
    }

    private int fill(Recycler recycler, LayoutState layoutState, State state) {
        int targetLine;
        int endAfterPadding;
        Span span;
        Span currentSpan;
        int maxEnd;
        int start;
        int end;
        boolean hasInvalidGap;
        int endAfterPadding2;
        int otherEnd;
        int otherStart;
        int startAfterPadding;
        int minStart;
        int diff;
        int min;
        Recycler recycler2 = recycler;
        LayoutState layoutState2 = layoutState;
        State state2 = state;
        Recycler recycler3 = recycler2;
        LayoutState layoutState3 = layoutState2;
        State state3 = state2;
        this.mRemainingSpans.set(0, this.mSpanCount, true);
        if (!this.mLayoutState.mInfinite) {
            if (layoutState2.mLayoutDirection != 1) {
                targetLine = layoutState2.mStartLine - layoutState2.mAvailable;
            } else {
                targetLine = layoutState2.mEndLine + layoutState2.mAvailable;
            }
        } else if (layoutState2.mLayoutDirection != 1) {
            targetLine = Integer.MIN_VALUE;
        } else {
            targetLine = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
        updateAllRemainingSpans(layoutState2.mLayoutDirection, targetLine);
        if (!this.mShouldReverseLayout) {
            endAfterPadding = this.mPrimaryOrientation.getStartAfterPadding();
        } else {
            endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding();
        }
        int defaultNewViewLine = endAfterPadding;
        boolean added = false;
        while (layoutState2.hasMore(state2) && (this.mLayoutState.mInfinite || !this.mRemainingSpans.isEmpty())) {
            View next = layoutState2.next(recycler2);
            View view = next;
            LayoutParams layoutParams = (LayoutParams) next.getLayoutParams();
            LayoutParams lp = layoutParams;
            int viewLayoutPosition = layoutParams.getViewLayoutPosition();
            int position = viewLayoutPosition;
            int span2 = this.mLazySpanLookup.getSpan(viewLayoutPosition);
            int spanIndex = span2;
            boolean assignSpan = span2 == -1;
            if (!assignSpan) {
                currentSpan = this.mSpans[spanIndex];
            } else {
                if (!lp.mFullSpan) {
                    span = getNextSpan(layoutState2);
                } else {
                    span = this.mSpans[0];
                }
                currentSpan = span;
                this.mLazySpanLookup.setSpan(position, currentSpan);
            }
            lp.mSpan = currentSpan;
            if (layoutState2.mLayoutDirection != 1) {
                addView(view, 0);
            } else {
                addView(view);
            }
            measureChildWithDecorationsAndMargin(view, lp, false);
            if (layoutState2.mLayoutDirection != 1) {
                if (!lp.mFullSpan) {
                    minStart = currentSpan.getStartLine(defaultNewViewLine);
                } else {
                    minStart = getMinStart(defaultNewViewLine);
                }
                end = minStart;
                start = end - this.mPrimaryOrientation.getDecoratedMeasurement(view);
                if (assignSpan && lp.mFullSpan) {
                    FullSpanItem createFullSpanItemFromStart = createFullSpanItemFromStart(end);
                    FullSpanItem fullSpanItem = createFullSpanItemFromStart;
                    createFullSpanItemFromStart.mGapDir = 1;
                    fullSpanItem.mPosition = position;
                    this.mLazySpanLookup.addFullSpanItem(fullSpanItem);
                }
            } else {
                if (!lp.mFullSpan) {
                    maxEnd = currentSpan.getEndLine(defaultNewViewLine);
                } else {
                    maxEnd = getMaxEnd(defaultNewViewLine);
                }
                start = maxEnd;
                end = start + this.mPrimaryOrientation.getDecoratedMeasurement(view);
                if (assignSpan && lp.mFullSpan) {
                    FullSpanItem createFullSpanItemFromEnd = createFullSpanItemFromEnd(start);
                    FullSpanItem fullSpanItem2 = createFullSpanItemFromEnd;
                    createFullSpanItemFromEnd.mGapDir = -1;
                    fullSpanItem2.mPosition = position;
                    this.mLazySpanLookup.addFullSpanItem(fullSpanItem2);
                }
            }
            if (lp.mFullSpan && layoutState2.mItemDirection == -1) {
                if (!assignSpan) {
                    if (layoutState2.mLayoutDirection != 1) {
                        hasInvalidGap = !areAllStartsEqual();
                    } else {
                        hasInvalidGap = !areAllEndsEqual();
                    }
                    if (hasInvalidGap) {
                        FullSpanItem fullSpanItem3 = this.mLazySpanLookup.getFullSpanItem(position);
                        FullSpanItem fullSpanItem4 = fullSpanItem3;
                        if (fullSpanItem3 != null) {
                            fullSpanItem4.mHasUnwantedGapAfter = true;
                        }
                        this.mLaidOutInvalidFullSpan = true;
                    }
                } else {
                    this.mLaidOutInvalidFullSpan = true;
                }
            }
            attachViewToSpans(view, lp, layoutState2);
            if (isLayoutRTL() && this.mOrientation == 1) {
                if (!lp.mFullSpan) {
                    endAfterPadding2 = this.mSecondaryOrientation.getEndAfterPadding() - (((this.mSpanCount - 1) - currentSpan.mIndex) * this.mSizePerSpan);
                } else {
                    endAfterPadding2 = this.mSecondaryOrientation.getEndAfterPadding();
                }
                otherEnd = endAfterPadding2;
                otherStart = otherEnd - this.mSecondaryOrientation.getDecoratedMeasurement(view);
            } else {
                if (!lp.mFullSpan) {
                    startAfterPadding = (currentSpan.mIndex * this.mSizePerSpan) + this.mSecondaryOrientation.getStartAfterPadding();
                } else {
                    startAfterPadding = this.mSecondaryOrientation.getStartAfterPadding();
                }
                otherStart = startAfterPadding;
                otherEnd = otherStart + this.mSecondaryOrientation.getDecoratedMeasurement(view);
            }
            if (this.mOrientation != 1) {
                layoutDecoratedWithMargins(view, start, otherStart, end, otherEnd);
            } else {
                layoutDecoratedWithMargins(view, otherStart, start, otherEnd, end);
            }
            if (!lp.mFullSpan) {
                updateRemainingSpans(currentSpan, this.mLayoutState.mLayoutDirection, targetLine);
            } else {
                updateAllRemainingSpans(this.mLayoutState.mLayoutDirection, targetLine);
            }
            recycle(recycler2, this.mLayoutState);
            if (this.mLayoutState.mStopInFocusable && view.isFocusable()) {
                if (!lp.mFullSpan) {
                    this.mRemainingSpans.set(currentSpan.mIndex, false);
                } else {
                    this.mRemainingSpans.clear();
                }
            }
            added = true;
        }
        if (!added) {
            recycle(recycler2, this.mLayoutState);
        }
        if (this.mLayoutState.mLayoutDirection != -1) {
            int maxEnd2 = getMaxEnd(this.mPrimaryOrientation.getEndAfterPadding());
            int i = maxEnd2;
            diff = maxEnd2 - this.mPrimaryOrientation.getEndAfterPadding();
        } else {
            diff = this.mPrimaryOrientation.getStartAfterPadding() - getMinStart(this.mPrimaryOrientation.getStartAfterPadding());
        }
        if (diff <= 0) {
            min = 0;
        } else {
            min = Math.min(layoutState2.mAvailable, diff);
        }
        return min;
    }

    private FullSpanItem createFullSpanItemFromEnd(int i) {
        int newItemTop = i;
        int i2 = newItemTop;
        FullSpanItem fullSpanItem = new FullSpanItem();
        FullSpanItem fsi = fullSpanItem;
        fullSpanItem.mGapPerSpan = new int[this.mSpanCount];
        for (int i3 = 0; i3 < this.mSpanCount; i3++) {
            fsi.mGapPerSpan[i3] = newItemTop - this.mSpans[i3].getEndLine(newItemTop);
        }
        return fsi;
    }

    private FullSpanItem createFullSpanItemFromStart(int i) {
        int newItemBottom = i;
        int i2 = newItemBottom;
        FullSpanItem fullSpanItem = new FullSpanItem();
        FullSpanItem fsi = fullSpanItem;
        fullSpanItem.mGapPerSpan = new int[this.mSpanCount];
        for (int i3 = 0; i3 < this.mSpanCount; i3++) {
            fsi.mGapPerSpan[i3] = this.mSpans[i3].getStartLine(newItemBottom) - newItemBottom;
        }
        return fsi;
    }

    private void attachViewToSpans(View view, LayoutParams layoutParams, LayoutState layoutState) {
        View view2 = view;
        LayoutParams lp = layoutParams;
        LayoutState layoutState2 = layoutState;
        View view3 = view2;
        LayoutParams layoutParams2 = lp;
        LayoutState layoutState3 = layoutState2;
        if (layoutState2.mLayoutDirection != 1) {
            if (!lp.mFullSpan) {
                lp.mSpan.prependToSpan(view2);
            } else {
                prependViewToAllSpans(view2);
            }
        } else if (!lp.mFullSpan) {
            lp.mSpan.appendToSpan(view2);
        } else {
            appendViewToAllSpans(view2);
        }
    }

    private void recycle(Recycler recycler, LayoutState layoutState) {
        int line;
        int line2;
        Recycler recycler2 = recycler;
        LayoutState layoutState2 = layoutState;
        Recycler recycler3 = recycler2;
        LayoutState layoutState3 = layoutState2;
        if (layoutState2.mRecycle && !layoutState2.mInfinite) {
            if (layoutState2.mAvailable != 0) {
                if (layoutState2.mLayoutDirection != -1) {
                    int minEnd = getMinEnd(layoutState2.mEndLine) - layoutState2.mEndLine;
                    int scrolled = minEnd;
                    if (minEnd >= 0) {
                        line2 = layoutState2.mStartLine + Math.min(scrolled, layoutState2.mAvailable);
                    } else {
                        line2 = layoutState2.mStartLine;
                    }
                    recycleFromStart(recycler2, line2);
                } else {
                    int maxStart = layoutState2.mStartLine - getMaxStart(layoutState2.mStartLine);
                    int scrolled2 = maxStart;
                    if (maxStart >= 0) {
                        line = layoutState2.mEndLine - Math.min(scrolled2, layoutState2.mAvailable);
                    } else {
                        line = layoutState2.mEndLine;
                    }
                    recycleFromEnd(recycler2, line);
                }
            } else if (layoutState2.mLayoutDirection != -1) {
                recycleFromStart(recycler2, layoutState2.mStartLine);
            } else {
                recycleFromEnd(recycler2, layoutState2.mEndLine);
            }
        }
    }

    private void appendViewToAllSpans(View view) {
        View view2 = view;
        View view3 = view2;
        for (int i = this.mSpanCount - 1; i >= 0; i--) {
            this.mSpans[i].appendToSpan(view2);
        }
    }

    private void prependViewToAllSpans(View view) {
        View view2 = view;
        View view3 = view2;
        for (int i = this.mSpanCount - 1; i >= 0; i--) {
            this.mSpans[i].prependToSpan(view2);
        }
    }

    private void updateAllRemainingSpans(int i, int i2) {
        int layoutDir = i;
        int targetLine = i2;
        int i3 = layoutDir;
        int i4 = targetLine;
        for (int i5 = 0; i5 < this.mSpanCount; i5++) {
            if (!this.mSpans[i5].mViews.isEmpty()) {
                updateRemainingSpans(this.mSpans[i5], layoutDir, targetLine);
            }
        }
    }

    private void updateRemainingSpans(Span span, int i, int i2) {
        Span span2 = span;
        int layoutDir = i;
        int targetLine = i2;
        Span span3 = span2;
        int i3 = layoutDir;
        int i4 = targetLine;
        int deletedSize = span2.getDeletedSize();
        if (layoutDir != -1) {
            int endLine = span2.getEndLine();
            int i5 = endLine;
            if (endLine - deletedSize >= targetLine) {
                this.mRemainingSpans.set(span2.mIndex, false);
                return;
            }
            return;
        }
        int startLine = span2.getStartLine();
        int i6 = startLine;
        if (startLine + deletedSize <= targetLine) {
            this.mRemainingSpans.set(span2.mIndex, false);
        }
    }

    private int getMaxStart(int i) {
        int def = i;
        int i2 = def;
        int maxStart = this.mSpans[0].getStartLine(def);
        for (int i3 = 1; i3 < this.mSpanCount; i3++) {
            int startLine = this.mSpans[i3].getStartLine(def);
            int spanStart = startLine;
            if (startLine > maxStart) {
                maxStart = spanStart;
            }
        }
        return maxStart;
    }

    private int getMinStart(int i) {
        int def = i;
        int i2 = def;
        int minStart = this.mSpans[0].getStartLine(def);
        for (int i3 = 1; i3 < this.mSpanCount; i3++) {
            int startLine = this.mSpans[i3].getStartLine(def);
            int spanStart = startLine;
            if (startLine < minStart) {
                minStart = spanStart;
            }
        }
        return minStart;
    }

    /* access modifiers changed from: 0000 */
    public boolean areAllEndsEqual() {
        int end = this.mSpans[0].getEndLine(Integer.MIN_VALUE);
        for (int i = 1; i < this.mSpanCount; i++) {
            if (this.mSpans[i].getEndLine(Integer.MIN_VALUE) != end) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean areAllStartsEqual() {
        int start = this.mSpans[0].getStartLine(Integer.MIN_VALUE);
        for (int i = 1; i < this.mSpanCount; i++) {
            if (this.mSpans[i].getStartLine(Integer.MIN_VALUE) != start) {
                return false;
            }
        }
        return true;
    }

    private int getMaxEnd(int i) {
        int def = i;
        int i2 = def;
        int maxEnd = this.mSpans[0].getEndLine(def);
        for (int i3 = 1; i3 < this.mSpanCount; i3++) {
            int endLine = this.mSpans[i3].getEndLine(def);
            int spanEnd = endLine;
            if (endLine > maxEnd) {
                maxEnd = spanEnd;
            }
        }
        return maxEnd;
    }

    private int getMinEnd(int i) {
        int def = i;
        int i2 = def;
        int minEnd = this.mSpans[0].getEndLine(def);
        for (int i3 = 1; i3 < this.mSpanCount; i3++) {
            int endLine = this.mSpans[i3].getEndLine(def);
            int spanEnd = endLine;
            if (endLine < minEnd) {
                minEnd = spanEnd;
            }
        }
        return minEnd;
    }

    private void recycleFromStart(Recycler recycler, int i) {
        Recycler recycler2 = recycler;
        int line = i;
        Recycler recycler3 = recycler2;
        int i2 = line;
        while (getChildCount() > 0) {
            View child = getChildAt(0);
            if (this.mPrimaryOrientation.getDecoratedEnd(child) <= line && this.mPrimaryOrientation.getTransformedEndWithDecoration(child) <= line) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.mFullSpan) {
                    int j = 0;
                    while (j < this.mSpanCount) {
                        if (this.mSpans[j].mViews.size() != 1) {
                            j++;
                        } else {
                            return;
                        }
                    }
                    for (int j2 = 0; j2 < this.mSpanCount; j2++) {
                        this.mSpans[j2].popStart();
                    }
                } else if (lp.mSpan.mViews.size() != 1) {
                    lp.mSpan.popStart();
                } else {
                    return;
                }
                removeAndRecycleView(child, recycler2);
            } else {
                return;
            }
        }
    }

    private void recycleFromEnd(Recycler recycler, int i) {
        Recycler recycler2 = recycler;
        int line = i;
        Recycler recycler3 = recycler2;
        int i2 = line;
        int childCount = getChildCount();
        int i3 = childCount;
        int i4 = childCount - 1;
        while (i4 >= 0) {
            View child = getChildAt(i4);
            if (this.mPrimaryOrientation.getDecoratedStart(child) >= line && this.mPrimaryOrientation.getTransformedStartWithDecoration(child) >= line) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.mFullSpan) {
                    int j = 0;
                    while (j < this.mSpanCount) {
                        if (this.mSpans[j].mViews.size() != 1) {
                            j++;
                        } else {
                            return;
                        }
                    }
                    for (int j2 = 0; j2 < this.mSpanCount; j2++) {
                        this.mSpans[j2].popEnd();
                    }
                } else if (lp.mSpan.mViews.size() != 1) {
                    lp.mSpan.popEnd();
                } else {
                    return;
                }
                removeAndRecycleView(child, recycler2);
                i4--;
            } else {
                return;
            }
        }
    }

    private boolean preferLastSpan(int i) {
        int layoutDir = i;
        int i2 = layoutDir;
        if (this.mOrientation != 0) {
            return ((layoutDir == -1) == this.mShouldReverseLayout) == isLayoutRTL();
        }
        return (layoutDir == -1) != this.mShouldReverseLayout;
    }

    private Span getNextSpan(LayoutState layoutState) {
        int startIndex;
        int endIndex;
        int diff;
        LayoutState layoutState2 = layoutState;
        LayoutState layoutState3 = layoutState2;
        boolean preferLastSpan = preferLastSpan(layoutState2.mLayoutDirection);
        boolean z = preferLastSpan;
        if (!preferLastSpan) {
            startIndex = 0;
            endIndex = this.mSpanCount;
            diff = 1;
        } else {
            startIndex = this.mSpanCount - 1;
            endIndex = -1;
            diff = -1;
        }
        if (layoutState2.mLayoutDirection != 1) {
            Span max = null;
            int maxLine = Integer.MIN_VALUE;
            int defaultLine = this.mPrimaryOrientation.getEndAfterPadding();
            for (int i = startIndex; i != endIndex; i += diff) {
                Span span = this.mSpans[i];
                Span other = span;
                int startLine = span.getStartLine(defaultLine);
                int otherLine = startLine;
                if (startLine > maxLine) {
                    max = other;
                    maxLine = otherLine;
                }
            }
            return max;
        }
        Span min = null;
        int minLine = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int defaultLine2 = this.mPrimaryOrientation.getStartAfterPadding();
        for (int i2 = startIndex; i2 != endIndex; i2 += diff) {
            Span span2 = this.mSpans[i2];
            Span other2 = span2;
            int endLine = span2.getEndLine(defaultLine2);
            int otherLine2 = endLine;
            if (endLine < minLine) {
                min = other2;
                minLine = otherLine2;
            }
        }
        return min;
    }

    public boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    public boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
        int dx = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        int i2 = dx;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        return scrollBy(dx, recycler2, state2);
    }

    public int scrollVerticallyBy(int i, Recycler recycler, State state) {
        int dy = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        int i2 = dy;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        return scrollBy(dy, recycler2, state2);
    }

    private int calculateScrollDirectionForPosition(int i) {
        int position = i;
        int i2 = position;
        if (getChildCount() != 0) {
            return (position < getFirstChildPosition()) == this.mShouldReverseLayout ? 1 : -1;
        }
        return !this.mShouldReverseLayout ? -1 : 1;
    }

    public PointF computeScrollVectorForPosition(int i) {
        int targetPosition = i;
        int i2 = targetPosition;
        int calculateScrollDirectionForPosition = calculateScrollDirectionForPosition(targetPosition);
        int direction = calculateScrollDirectionForPosition;
        PointF outVector = new PointF();
        if (calculateScrollDirectionForPosition == 0) {
            return null;
        }
        if (this.mOrientation != 0) {
            outVector.x = 0.0f;
            outVector.y = (float) direction;
        } else {
            outVector.x = (float) direction;
            outVector.y = 0.0f;
        }
        return outVector;
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
        RecyclerView recyclerView2 = recyclerView;
        int position = i;
        RecyclerView recyclerView3 = recyclerView2;
        State state2 = state;
        int i2 = position;
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView2.getContext());
        LinearSmoothScroller scroller = linearSmoothScroller;
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(scroller);
    }

    public void scrollToPosition(int i) {
        int position = i;
        int i2 = position;
        if (!(this.mPendingSavedState == null || this.mPendingSavedState.mAnchorPosition == position)) {
            this.mPendingSavedState.invalidateAnchorPositionInfo();
        }
        this.mPendingScrollPosition = position;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i, int i2) {
        int position = i;
        int offset = i2;
        int i3 = position;
        int i4 = offset;
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchorPositionInfo();
        }
        this.mPendingScrollPosition = position;
        this.mPendingScrollPositionOffset = offset;
        requestLayout();
    }

    public void collectAdjacentPrefetchPositions(int i, int i2, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int startLine;
        int dx = i;
        int dy = i2;
        State state2 = state;
        LayoutPrefetchRegistry layoutPrefetchRegistry2 = layoutPrefetchRegistry;
        int i3 = dx;
        int i4 = dy;
        State state3 = state2;
        LayoutPrefetchRegistry layoutPrefetchRegistry3 = layoutPrefetchRegistry2;
        int delta = this.mOrientation != 0 ? dy : dx;
        if (getChildCount() != 0 && delta != 0) {
            prepareLayoutStateForDelta(delta, state2);
            if (this.mPrefetchDistances == null || this.mPrefetchDistances.length < this.mSpanCount) {
                this.mPrefetchDistances = new int[this.mSpanCount];
            }
            int itemPrefetchCount = 0;
            for (int i5 = 0; i5 < this.mSpanCount; i5++) {
                if (this.mLayoutState.mItemDirection != -1) {
                    startLine = this.mSpans[i5].getEndLine(this.mLayoutState.mEndLine) - this.mLayoutState.mEndLine;
                } else {
                    startLine = this.mLayoutState.mStartLine - this.mSpans[i5].getStartLine(this.mLayoutState.mStartLine);
                }
                int distance = startLine;
                if (distance >= 0) {
                    this.mPrefetchDistances[itemPrefetchCount] = distance;
                    itemPrefetchCount++;
                }
            }
            Arrays.sort(this.mPrefetchDistances, 0, itemPrefetchCount);
            for (int i6 = 0; i6 < itemPrefetchCount && this.mLayoutState.hasMore(state2); i6++) {
                layoutPrefetchRegistry2.addPosition(this.mLayoutState.mCurrentPosition, this.mPrefetchDistances[i6]);
                this.mLayoutState.mCurrentPosition += this.mLayoutState.mItemDirection;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void prepareLayoutStateForDelta(int i, State state) {
        int layoutDir;
        int referenceChildPosition;
        int delta = i;
        State state2 = state;
        int i2 = delta;
        State state3 = state2;
        if (delta <= 0) {
            layoutDir = -1;
            referenceChildPosition = getFirstChildPosition();
        } else {
            layoutDir = 1;
            referenceChildPosition = getLastChildPosition();
        }
        this.mLayoutState.mRecycle = true;
        updateLayoutState(referenceChildPosition, state2);
        setLayoutStateDirection(layoutDir);
        this.mLayoutState.mCurrentPosition = referenceChildPosition + this.mLayoutState.mItemDirection;
        this.mLayoutState.mAvailable = Math.abs(delta);
    }

    /* access modifiers changed from: 0000 */
    public int scrollBy(int i, Recycler recycler, State state) {
        int totalScroll;
        int dt = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        int i2 = dt;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (getChildCount() == 0 || dt == 0) {
            return 0;
        }
        prepareLayoutStateForDelta(dt, state2);
        int consumed = fill(recycler2, this.mLayoutState, state2);
        int i3 = this.mLayoutState.mAvailable;
        int i4 = i3;
        if (i3 < consumed) {
            totalScroll = dt;
        } else if (dt >= 0) {
            totalScroll = consumed;
        } else {
            totalScroll = -consumed;
        }
        this.mPrimaryOrientation.offsetChildren(-totalScroll);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        this.mLayoutState.mAvailable = 0;
        recycle(recycler2, this.mLayoutState);
        return totalScroll;
    }

    /* access modifiers changed from: 0000 */
    public int getLastChildPosition() {
        int childCount = getChildCount();
        return childCount != 0 ? getPosition(getChildAt(childCount - 1)) : 0;
    }

    /* access modifiers changed from: 0000 */
    public int getFirstChildPosition() {
        int childCount = getChildCount();
        int i = childCount;
        return childCount != 0 ? getPosition(getChildAt(0)) : 0;
    }

    private int findFirstReferenceChildPosition(int i) {
        int itemCount = i;
        int i2 = itemCount;
        int limit = getChildCount();
        for (int i3 = 0; i3 < limit; i3++) {
            int position = getPosition(getChildAt(i3));
            int position2 = position;
            if (position >= 0 && position2 < itemCount) {
                return position2;
            }
        }
        return 0;
    }

    private int findLastReferenceChildPosition(int i) {
        int itemCount = i;
        int i2 = itemCount;
        for (int i3 = getChildCount() - 1; i3 >= 0; i3--) {
            int position = getPosition(getChildAt(i3));
            int position2 = position;
            if (position >= 0 && position2 < itemCount) {
                return position2;
            }
        }
        return 0;
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

    public int getOrientation() {
        return this.mOrientation;
    }

    @Nullable
    public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
        int referenceChildPosition;
        View focused = view;
        int direction = i;
        Recycler recycler2 = recycler;
        State state2 = state;
        View view2 = focused;
        int i2 = direction;
        Recycler recycler3 = recycler2;
        State state3 = state2;
        if (getChildCount() == 0) {
            return null;
        }
        View findContainingItemView = findContainingItemView(focused);
        View directChild = findContainingItemView;
        if (findContainingItemView == null) {
            return null;
        }
        resolveShouldLayoutReverse();
        int convertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(direction);
        int layoutDir = convertFocusDirectionToLayoutDirection;
        if (convertFocusDirectionToLayoutDirection == Integer.MIN_VALUE) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) directChild.getLayoutParams();
        LayoutParams prevFocusLayoutParams = layoutParams;
        boolean prevFocusFullSpan = layoutParams.mFullSpan;
        Span prevFocusSpan = prevFocusLayoutParams.mSpan;
        if (layoutDir != 1) {
            referenceChildPosition = getFirstChildPosition();
        } else {
            referenceChildPosition = getLastChildPosition();
        }
        updateLayoutState(referenceChildPosition, state2);
        setLayoutStateDirection(layoutDir);
        this.mLayoutState.mCurrentPosition = referenceChildPosition + this.mLayoutState.mItemDirection;
        this.mLayoutState.mAvailable = (int) (MAX_SCROLL_FACTOR * ((float) this.mPrimaryOrientation.getTotalSpace()));
        this.mLayoutState.mStopInFocusable = true;
        this.mLayoutState.mRecycle = false;
        int fill = fill(recycler2, this.mLayoutState, state2);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        if (!prevFocusFullSpan) {
            View focusableViewAfter = prevFocusSpan.getFocusableViewAfter(referenceChildPosition, layoutDir);
            View view3 = focusableViewAfter;
            if (!(focusableViewAfter == null || view3 == directChild)) {
                return view3;
            }
        }
        if (!preferLastSpan(layoutDir)) {
            for (int i3 = 0; i3 < this.mSpanCount; i3++) {
                View focusableViewAfter2 = this.mSpans[i3].getFocusableViewAfter(referenceChildPosition, layoutDir);
                View view4 = focusableViewAfter2;
                if (focusableViewAfter2 != null && view4 != directChild) {
                    return view4;
                }
            }
        } else {
            for (int i4 = this.mSpanCount - 1; i4 >= 0; i4--) {
                View focusableViewAfter3 = this.mSpans[i4].getFocusableViewAfter(referenceChildPosition, layoutDir);
                View view5 = focusableViewAfter3;
                if (focusableViewAfter3 != null && view5 != directChild) {
                    return view5;
                }
            }
        }
        return null;
    }

    private int convertFocusDirectionToLayoutDirection(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int focusDirection = i;
        int i6 = focusDirection;
        switch (focusDirection) {
            case 1:
                if (this.mOrientation == 1) {
                    return -1;
                }
                if (!isLayoutRTL()) {
                    return -1;
                }
                return 1;
            case 2:
                if (this.mOrientation == 1) {
                    return 1;
                }
                if (!isLayoutRTL()) {
                    return 1;
                }
                return -1;
            case 17:
                if (this.mOrientation != 0) {
                    i5 = Integer.MIN_VALUE;
                } else {
                    i5 = -1;
                }
                return i5;
            case 33:
                if (this.mOrientation != 1) {
                    i4 = Integer.MIN_VALUE;
                } else {
                    i4 = -1;
                }
                return i4;
            case 66:
                if (this.mOrientation != 0) {
                    i3 = Integer.MIN_VALUE;
                } else {
                    i3 = 1;
                }
                return i3;
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                if (this.mOrientation != 1) {
                    i2 = Integer.MIN_VALUE;
                } else {
                    i2 = 1;
                }
                return i2;
            default:
                return Integer.MIN_VALUE;
        }
    }
}
