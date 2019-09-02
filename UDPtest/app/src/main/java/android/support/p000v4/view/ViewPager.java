package android.support.p000v4.view;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.view.accessibility.AccessibilityEventCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityRecordCompat;
import android.support.p000v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* renamed from: android.support.v4.view.ViewPager */
public class ViewPager extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<ItemInfo> COMPARATOR = new Comparator<ItemInfo>() {
        public /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            return compare((ItemInfo) obj, (ItemInfo) obj2);
        }

        public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
            ItemInfo lhs = itemInfo;
            ItemInfo rhs = itemInfo2;
            ItemInfo itemInfo3 = lhs;
            ItemInfo itemInfo4 = rhs;
            return lhs.position - rhs.position;
        }
    };
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    static final int[] LAYOUT_ATTRS;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float f) {
            float t = f;
            float f2 = t;
            float f3 = t - 1.0f;
            float t2 = f3;
            return (f3 * t2 * t2 * t2 * t2) + 1.0f;
        }
    };
    private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();
    private int mActivePointerId = -1;
    PagerAdapter mAdapter;
    private List<OnAdapterChangeListener> mAdapterChangeListeners;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable = new Runnable(this) {
        final /* synthetic */ ViewPager this$0;

        {
            ViewPager this$02 = r5;
            ViewPager viewPager = this$02;
            this.this$0 = this$02;
        }

        public void run() {
            this.this$0.setScrollState(0);
            this.this$0.populate();
        }
    };
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout = true;
    private float mFirstOffset = -3.4028235E38f;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsScrollStarted;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems = new ArrayList<>();
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset = Float.MAX_VALUE;
    private EdgeEffectCompat mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets = false;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit = 1;
    private OnPageChangeListener mOnPageChangeListener;
    private List<OnPageChangeListener> mOnPageChangeListeners;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private int mPageTransformerLayerType;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mRestoredCurItem = -1;
    private EdgeEffectCompat mRightEdge;
    private int mScrollState = 0;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private Method mSetChildrenDrawingOrderEnabled;
    private final ItemInfo mTempItem = new ItemInfo();
    private final Rect mTempRect = new Rect();
    private int mTopPageBounds;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    @Inherited
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    /* renamed from: android.support.v4.view.ViewPager$DecorView */
    public @interface DecorView {
    }

    /* renamed from: android.support.v4.view.ViewPager$ItemInfo */
    static class ItemInfo {
        Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;

        ItemInfo() {
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$LayoutParams */
    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            super(context2, attrs);
            TypedArray a = context2.obtainStyledAttributes(attrs, ViewPager.LAYOUT_ATTRS);
            this.gravity = a.getInteger(0, 48);
            a.recycle();
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$MyAccessibilityDelegate */
    class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        final /* synthetic */ ViewPager this$0;

        MyAccessibilityDelegate(ViewPager viewPager) {
            ViewPager this$02 = viewPager;
            ViewPager viewPager2 = this$02;
            this.this$0 = this$02;
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            View host = view;
            AccessibilityEvent event = accessibilityEvent;
            View view2 = host;
            AccessibilityEvent accessibilityEvent2 = event;
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(ViewPager.class.getName());
            AccessibilityRecordCompat asRecord = AccessibilityEventCompat.asRecord(event);
            AccessibilityRecordCompat recordCompat = asRecord;
            asRecord.setScrollable(canScroll());
            if (event.getEventType() == 4096 && this.this$0.mAdapter != null) {
                recordCompat.setItemCount(this.this$0.mAdapter.getCount());
                recordCompat.setFromIndex(this.this$0.mCurItem);
                recordCompat.setToIndex(this.this$0.mCurItem);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View host = view;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            View view2 = host;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setClassName(ViewPager.class.getName());
            info.setScrollable(canScroll());
            if (this.this$0.canScrollHorizontally(1)) {
                info.addAction(4096);
            }
            if (this.this$0.canScrollHorizontally(-1)) {
                info.addAction(8192);
            }
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            View host = view;
            int action = i;
            Bundle args = bundle;
            View view2 = host;
            int i2 = action;
            Bundle bundle2 = args;
            if (super.performAccessibilityAction(host, action, args)) {
                return true;
            }
            switch (action) {
                case 4096:
                    if (!this.this$0.canScrollHorizontally(1)) {
                        return false;
                    }
                    this.this$0.setCurrentItem(this.this$0.mCurItem + 1);
                    return true;
                case 8192:
                    if (!this.this$0.canScrollHorizontally(-1)) {
                        return false;
                    }
                    this.this$0.setCurrentItem(this.this$0.mCurItem - 1);
                    return true;
                default:
                    return false;
            }
        }

        private boolean canScroll() {
            return this.this$0.mAdapter != null && this.this$0.mAdapter.getCount() > 1;
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$OnAdapterChangeListener */
    public interface OnAdapterChangeListener {
        void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2);
    }

    /* renamed from: android.support.v4.view.ViewPager$OnPageChangeListener */
    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);

        void onPageSelected(int i);
    }

    /* renamed from: android.support.v4.view.ViewPager$PageTransformer */
    public interface PageTransformer {
        void transformPage(View view, float f);
    }

    /* renamed from: android.support.v4.view.ViewPager$PagerObserver */
    private class PagerObserver extends DataSetObserver {
        final /* synthetic */ ViewPager this$0;

        PagerObserver(ViewPager viewPager) {
            this.this$0 = viewPager;
        }

        public void onChanged() {
            this.this$0.dataSetChanged();
        }

        public void onInvalidated() {
            this.this$0.dataSetChanged();
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$SavedState */
    public static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                Parcel in = parcel;
                ClassLoader loader = classLoader;
                Parcel parcel2 = in;
                ClassLoader classLoader2 = loader;
                return new SavedState(in, loader);
            }

            public SavedState[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new SavedState[size];
            }
        });
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        public SavedState(Parcelable parcelable) {
            Parcelable superState = parcelable;
            Parcelable parcelable2 = superState;
            super(superState);
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel out = parcel;
            int flags = i;
            Parcel parcel2 = out;
            int i2 = flags;
            super.writeToParcel(out, flags);
            out.writeInt(this.position);
            out.writeParcelable(this.adapterState, flags);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            Parcel in = parcel;
            ClassLoader loader2 = classLoader;
            Parcel parcel2 = in;
            ClassLoader loader3 = loader2;
            super(in, loader2);
            if (loader2 == null) {
                loader3 = getClass().getClassLoader();
            }
            this.position = in.readInt();
            this.adapterState = in.readParcelable(loader3);
            this.loader = loader3;
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$SimpleOnPageChangeListener */
    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
        public SimpleOnPageChangeListener() {
        }

        public void onPageScrolled(int i, float f, int i2) {
            int i3 = i;
            float f2 = f;
            int i4 = i2;
        }

        public void onPageSelected(int i) {
            int i2 = i;
        }

        public void onPageScrollStateChanged(int i) {
            int i2 = i;
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$ViewPositionComparator */
    static class ViewPositionComparator implements Comparator<View> {
        ViewPositionComparator() {
        }

        public /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            return compare((View) obj, (View) obj2);
        }

        public int compare(View view, View view2) {
            View lhs = view;
            View rhs = view2;
            View view3 = lhs;
            View view4 = rhs;
            LayoutParams llp = (LayoutParams) lhs.getLayoutParams();
            LayoutParams rlp = (LayoutParams) rhs.getLayoutParams();
            if (llp.isDecor == rlp.isDecor) {
                return llp.position - rlp.position;
            }
            return !llp.isDecor ? -1 : 1;
        }
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 16842931;
        LAYOUT_ATTRS = iArr;
    }

    public ViewPager(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
        initViewPager();
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        initViewPager();
    }

    /* access modifiers changed from: 0000 */
    public void initViewPager() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        float f = context.getResources().getDisplayMetrics().density;
        float f2 = f;
        this.mTouchSlop = configuration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int) (400.0f * f);
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffectCompat(context);
        this.mRightEdge = new EdgeEffectCompat(context);
        this.mFlingDistance = (int) (25.0f * f);
        this.mCloseEnough = (int) (2.0f * f);
        this.mDefaultGutterSize = (int) (16.0f * f);
        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate(this));
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener(this) {
            private final Rect mTempRect = new Rect();
            final /* synthetic */ ViewPager this$0;

            {
                ViewPager this$02 = r6;
                ViewPager viewPager = this$02;
                this.this$0 = this$02;
            }

            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                View v = view;
                WindowInsetsCompat originalInsets = windowInsetsCompat;
                View view2 = v;
                WindowInsetsCompat windowInsetsCompat2 = originalInsets;
                WindowInsetsCompat onApplyWindowInsets = ViewCompat.onApplyWindowInsets(v, originalInsets);
                WindowInsetsCompat applied = onApplyWindowInsets;
                if (onApplyWindowInsets.isConsumed()) {
                    return applied;
                }
                Rect rect = this.mTempRect;
                Rect res = rect;
                rect.left = applied.getSystemWindowInsetLeft();
                res.top = applied.getSystemWindowInsetTop();
                res.right = applied.getSystemWindowInsetRight();
                res.bottom = applied.getSystemWindowInsetBottom();
                int count = this.this$0.getChildCount();
                for (int i = 0; i < count; i++) {
                    WindowInsetsCompat childInsets = ViewCompat.dispatchApplyWindowInsets(this.this$0.getChildAt(i), applied);
                    res.left = Math.min(childInsets.getSystemWindowInsetLeft(), res.left);
                    res.top = Math.min(childInsets.getSystemWindowInsetTop(), res.top);
                    res.right = Math.min(childInsets.getSystemWindowInsetRight(), res.right);
                    res.bottom = Math.min(childInsets.getSystemWindowInsetBottom(), res.bottom);
                }
                return applied.replaceSystemWindowInsets(res.left, res.top, res.right, res.bottom);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        boolean removeCallbacks = removeCallbacks(this.mEndScrollRunnable);
        if (this.mScroller != null && !this.mScroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: 0000 */
    public void setScrollState(int i) {
        int newState = i;
        int i2 = newState;
        if (this.mScrollState != newState) {
            this.mScrollState = newState;
            if (this.mPageTransformer != null) {
                enableLayers(newState != 0);
            }
            dispatchOnScrollStateChanged(newState);
        }
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        PagerAdapter adapter = pagerAdapter;
        PagerAdapter pagerAdapter2 = adapter;
        if (this.mAdapter != null) {
            this.mAdapter.setViewPagerObserver(null);
            this.mAdapter.startUpdate((ViewGroup) this);
            for (int i = 0; i < this.mItems.size(); i++) {
                ItemInfo ii = (ItemInfo) this.mItems.get(i);
                this.mAdapter.destroyItem((ViewGroup) this, ii.position, ii.object);
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            this.mItems.clear();
            removeNonDecorViews();
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        PagerAdapter oldAdapter = this.mAdapter;
        this.mAdapter = adapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver(this);
            }
            this.mAdapter.setViewPagerObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean wasFirstLayout = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (wasFirstLayout) {
                requestLayout();
            } else {
                populate();
            }
        }
        if (this.mAdapterChangeListeners != null && !this.mAdapterChangeListeners.isEmpty()) {
            int count = this.mAdapterChangeListeners.size();
            for (int i2 = 0; i2 < count; i2++) {
                ((OnAdapterChangeListener) this.mAdapterChangeListeners.get(i2)).onAdapterChanged(this, oldAdapter, adapter);
            }
        }
    }

    private void removeNonDecorViews() {
        int i = 0;
        while (i < getChildCount()) {
            View childAt = getChildAt(i);
            View view = childAt;
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            if (!layoutParams.isDecor) {
                removeViewAt(i);
                i--;
            }
            i++;
        }
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    public void addOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        OnAdapterChangeListener listener = onAdapterChangeListener;
        OnAdapterChangeListener onAdapterChangeListener2 = listener;
        if (this.mAdapterChangeListeners == null) {
            this.mAdapterChangeListeners = new ArrayList();
        }
        boolean add = this.mAdapterChangeListeners.add(listener);
    }

    public void removeOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        OnAdapterChangeListener listener = onAdapterChangeListener;
        OnAdapterChangeListener onAdapterChangeListener2 = listener;
        if (this.mAdapterChangeListeners != null) {
            boolean remove = this.mAdapterChangeListeners.remove(listener);
        }
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public void setCurrentItem(int i) {
        int item = i;
        int i2 = item;
        this.mPopulatePending = false;
        setCurrentItemInternal(item, !this.mFirstLayout, false);
    }

    public void setCurrentItem(int i, boolean z) {
        int item = i;
        int i2 = item;
        boolean smoothScroll = z;
        this.mPopulatePending = false;
        setCurrentItemInternal(item, smoothScroll, false);
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    /* access modifiers changed from: 0000 */
    public void setCurrentItemInternal(int i, boolean z, boolean z2) {
        int item = i;
        int i2 = item;
        setCurrentItemInternal(item, z, z2, 0);
    }

    /* access modifiers changed from: 0000 */
    public void setCurrentItemInternal(int i, boolean z, boolean z2, int i2) {
        int item = i;
        int velocity = i2;
        int item2 = item;
        boolean smoothScroll = z;
        boolean always = z2;
        int i3 = velocity;
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (!always && this.mCurItem == item && this.mItems.size() != 0) {
            setScrollingCacheEnabled(false);
        } else {
            if (item < 0) {
                item2 = 0;
            } else if (item >= this.mAdapter.getCount()) {
                item2 = this.mAdapter.getCount() - 1;
            }
            int pageLimit = this.mOffscreenPageLimit;
            if (item2 > this.mCurItem + pageLimit || item2 < this.mCurItem - pageLimit) {
                for (int i4 = 0; i4 < this.mItems.size(); i4++) {
                    ((ItemInfo) this.mItems.get(i4)).scrolling = true;
                }
            }
            boolean dispatchSelected = this.mCurItem != item2;
            if (!this.mFirstLayout) {
                populate(item2);
                scrollToItem(item2, smoothScroll, velocity, dispatchSelected);
            } else {
                this.mCurItem = item2;
                if (dispatchSelected) {
                    dispatchOnPageSelected(item2);
                }
                requestLayout();
            }
        }
    }

    private void scrollToItem(int i, boolean z, int i2, boolean z2) {
        int item = i;
        int velocity = i2;
        int i3 = item;
        boolean smoothScroll = z;
        int i4 = velocity;
        boolean dispatchSelected = z2;
        ItemInfo infoForPosition = infoForPosition(item);
        ItemInfo curInfo = infoForPosition;
        int destX = 0;
        if (infoForPosition != null) {
            int clientWidth = getClientWidth();
            int i5 = clientWidth;
            destX = (int) (((float) clientWidth) * Math.max(this.mFirstOffset, Math.min(curInfo.offset, this.mLastOffset)));
        }
        if (!smoothScroll) {
            if (dispatchSelected) {
                dispatchOnPageSelected(item);
            }
            completeScroll(false);
            scrollTo(destX, 0);
            boolean pageScrolled = pageScrolled(destX);
            return;
        }
        smoothScrollTo(destX, 0, velocity);
        if (dispatchSelected) {
            dispatchOnPageSelected(item);
        }
    }

    @Deprecated
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener listener = onPageChangeListener;
        OnPageChangeListener onPageChangeListener2 = listener;
        this.mOnPageChangeListener = listener;
    }

    public void addOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener listener = onPageChangeListener;
        OnPageChangeListener onPageChangeListener2 = listener;
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = new ArrayList();
        }
        boolean add = this.mOnPageChangeListeners.add(listener);
    }

    public void removeOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener listener = onPageChangeListener;
        OnPageChangeListener onPageChangeListener2 = listener;
        if (this.mOnPageChangeListeners != null) {
            boolean remove = this.mOnPageChangeListeners.remove(listener);
        }
    }

    public void clearOnPageChangeListeners() {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.clear();
        }
    }

    public void setPageTransformer(boolean z, PageTransformer pageTransformer) {
        PageTransformer transformer = pageTransformer;
        PageTransformer pageTransformer2 = transformer;
        setPageTransformer(z, transformer, 2);
    }

    public void setPageTransformer(boolean z, PageTransformer pageTransformer, int i) {
        PageTransformer transformer = pageTransformer;
        int pageLayerType = i;
        boolean reverseDrawingOrder = z;
        PageTransformer pageTransformer2 = transformer;
        int i2 = pageLayerType;
        if (VERSION.SDK_INT >= 11) {
            boolean hasTransformer = transformer != null;
            boolean needsPopulate = hasTransformer != (this.mPageTransformer != null);
            this.mPageTransformer = transformer;
            setChildrenDrawingOrderEnabledCompat(hasTransformer);
            if (!hasTransformer) {
                this.mDrawingOrder = 0;
            } else {
                this.mDrawingOrder = !reverseDrawingOrder ? 1 : 2;
                this.mPageTransformerLayerType = pageLayerType;
            }
            if (needsPopulate) {
                populate();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void setChildrenDrawingOrderEnabledCompat(boolean z) {
        boolean enable = z;
        if (VERSION.SDK_INT >= 7) {
            if (this.mSetChildrenDrawingOrderEnabled == null) {
                Class<ViewGroup> cls = ViewGroup.class;
                String str = "setChildrenDrawingOrderEnabled";
                try {
                    Class[] clsArr = new Class[1];
                    clsArr[0] = Boolean.TYPE;
                    this.mSetChildrenDrawingOrderEnabled = cls.getDeclaredMethod(str, clsArr);
                } catch (NoSuchMethodException e) {
                    NoSuchMethodException noSuchMethodException = e;
                    int e2 = Log.e(TAG, "Can't find setChildrenDrawingOrderEnabled", e);
                }
            }
            try {
                Method method = this.mSetChildrenDrawingOrderEnabled;
                Object[] objArr = new Object[1];
                objArr[0] = Boolean.valueOf(enable);
                Object invoke = method.invoke(this, objArr);
            } catch (Exception e3) {
                Exception exc = e3;
                int e4 = Log.e(TAG, "Error changing children drawing order", e3);
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i, int i2) {
        int childCount = i;
        int i3 = i2;
        int i4 = childCount;
        int i5 = i3;
        int i6 = ((LayoutParams) ((View) this.mDrawingOrderedChildren.get(this.mDrawingOrder != 2 ? i3 : (childCount - 1) - i3)).getLayoutParams()).childIndex;
        int i7 = i6;
        return i6;
    }

    /* access modifiers changed from: 0000 */
    public OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener listener = onPageChangeListener;
        OnPageChangeListener onPageChangeListener2 = listener;
        OnPageChangeListener oldListener = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = listener;
        return oldListener;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public void setOffscreenPageLimit(int i) {
        int limit = i;
        int limit2 = limit;
        if (limit < 1) {
            int w = Log.w(TAG, "Requested offscreen page limit " + limit + " too small; defaulting to " + 1);
            limit2 = 1;
        }
        if (limit2 != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = limit2;
            populate();
        }
    }

    public void setPageMargin(int i) {
        int marginPixels = i;
        int i2 = marginPixels;
        int oldMargin = this.mPageMargin;
        this.mPageMargin = marginPixels;
        int width = getWidth();
        int i3 = width;
        recomputeScrollPosition(width, width, marginPixels, oldMargin);
        requestLayout();
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    public void setPageMarginDrawable(Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        this.mMarginDrawable = d;
        if (d != null) {
            refreshDrawableState();
        }
        setWillNotDraw(d == null);
        invalidate();
    }

    public void setPageMarginDrawable(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setPageMarginDrawable(ContextCompat.getDrawable(getContext(), resId));
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        Drawable who = drawable;
        Drawable drawable2 = who;
        return super.verifyDrawable(who) || who == this.mMarginDrawable;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mMarginDrawable;
        Drawable d = drawable;
        if (drawable != null && d.isStateful()) {
            boolean state = d.setState(getDrawableState());
        }
    }

    /* access modifiers changed from: 0000 */
    public float distanceInfluenceForSnapDuration(float f) {
        float f2 = f;
        float f3 = f2;
        float f4 = f2 - 0.5f;
        float f5 = f4;
        float f6 = (float) (((double) f4) * 0.4712389167638204d);
        float f7 = f6;
        return (float) Math.sin((double) f6);
    }

    /* access modifiers changed from: 0000 */
    public void smoothScrollTo(int i, int i2) {
        int x = i;
        int y = i2;
        int i3 = x;
        int i4 = y;
        smoothScrollTo(x, y, 0);
    }

    /* access modifiers changed from: 0000 */
    public void smoothScrollTo(int i, int i2, int i3) {
        int sx;
        int duration;
        int x = i;
        int y = i2;
        int velocity = i3;
        int i4 = x;
        int i5 = y;
        int i6 = velocity;
        if (getChildCount() != 0) {
            if (!(this.mScroller != null && !this.mScroller.isFinished())) {
                sx = getScrollX();
            } else {
                sx = !this.mIsScrollStarted ? this.mScroller.getStartX() : this.mScroller.getCurrX();
                this.mScroller.abortAnimation();
                setScrollingCacheEnabled(false);
            }
            int sy = getScrollY();
            int dx = x - sx;
            int dy = y - sy;
            if (dx == 0 && dy == 0) {
                completeScroll(false);
                populate();
                setScrollState(0);
                return;
            }
            setScrollingCacheEnabled(true);
            setScrollState(2);
            int clientWidth = getClientWidth();
            int width = clientWidth;
            int i7 = clientWidth / 2;
            float distanceInfluenceForSnapDuration = ((float) i7) + (((float) i7) * distanceInfluenceForSnapDuration(Math.min(1.0f, (1.0f * ((float) Math.abs(dx))) / ((float) width))));
            float f = distanceInfluenceForSnapDuration;
            int abs = Math.abs(velocity);
            int velocity2 = abs;
            if (abs <= 0) {
                float abs2 = ((float) Math.abs(dx)) / ((((float) width) * this.mAdapter.getPageWidth(this.mCurItem)) + ((float) this.mPageMargin));
                float f2 = abs2;
                duration = (int) ((abs2 + 1.0f) * 100.0f);
            } else {
                duration = 4 * Math.round(1000.0f * Math.abs(distanceInfluenceForSnapDuration / ((float) velocity2)));
            }
            int min = Math.min(duration, MAX_SETTLE_DURATION);
            int duration2 = min;
            this.mIsScrollStarted = false;
            this.mScroller.startScroll(sx, sy, dx, dy, min);
            ViewCompat.postInvalidateOnAnimation(this);
            return;
        }
        setScrollingCacheEnabled(false);
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo addNewItem(int i, int i2) {
        int position = i;
        int index = i2;
        int i3 = position;
        int i4 = index;
        ItemInfo itemInfo = new ItemInfo();
        ItemInfo ii = itemInfo;
        itemInfo.position = position;
        ii.object = this.mAdapter.instantiateItem((ViewGroup) this, position);
        ii.widthFactor = this.mAdapter.getPageWidth(position);
        if (index >= 0 && index < this.mItems.size()) {
            this.mItems.add(index, ii);
        } else {
            boolean add = this.mItems.add(ii);
        }
        return ii;
    }

    /* access modifiers changed from: 0000 */
    public void dataSetChanged() {
        int count = this.mAdapter.getCount();
        int adapterCount = count;
        this.mExpectedAdapterCount = count;
        boolean needPopulate = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < adapterCount;
        int newCurrItem = this.mCurItem;
        boolean isUpdating = false;
        int i = 0;
        while (i < this.mItems.size()) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            int itemPosition = this.mAdapter.getItemPosition(ii.object);
            int newPos = itemPosition;
            if (itemPosition != -1) {
                if (newPos == -2) {
                    Object remove = this.mItems.remove(i);
                    i--;
                    if (!isUpdating) {
                        this.mAdapter.startUpdate((ViewGroup) this);
                        isUpdating = true;
                    }
                    this.mAdapter.destroyItem((ViewGroup) this, ii.position, ii.object);
                    needPopulate = true;
                    if (this.mCurItem == ii.position) {
                        newCurrItem = Math.max(0, Math.min(this.mCurItem, adapterCount - 1));
                        needPopulate = true;
                    }
                } else if (ii.position != newPos) {
                    if (ii.position == this.mCurItem) {
                        newCurrItem = newPos;
                    }
                    ii.position = newPos;
                    needPopulate = true;
                }
            }
            i++;
        }
        if (isUpdating) {
            this.mAdapter.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (needPopulate) {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                View view = childAt;
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (!layoutParams.isDecor) {
                    lp.widthFactor = 0.0f;
                }
            }
            setCurrentItemInternal(newCurrItem, false, true);
            requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void populate() {
        populate(this.mCurItem);
    }

    /* access modifiers changed from: 0000 */
    public void populate(int i) {
        String resName;
        float f;
        ItemInfo itemInfo;
        float f2;
        ItemInfo itemInfo2;
        int newCurrentItem = i;
        int i2 = newCurrentItem;
        ItemInfo oldCurInfo = null;
        if (this.mCurItem != newCurrentItem) {
            oldCurInfo = infoForPosition(this.mCurItem);
            this.mCurItem = newCurrentItem;
        }
        if (this.mAdapter == null) {
            sortChildDrawingOrder();
        } else if (this.mPopulatePending) {
            sortChildDrawingOrder();
        } else if (getWindowToken() != null) {
            this.mAdapter.startUpdate((ViewGroup) this);
            int pageLimit = this.mOffscreenPageLimit;
            int startPos = Math.max(0, this.mCurItem - pageLimit);
            int count = this.mAdapter.getCount();
            int N = count;
            int endPos = Math.min(count - 1, this.mCurItem + pageLimit);
            if (N == this.mExpectedAdapterCount) {
                ItemInfo curItem = null;
                int curIndex = 0;
                while (true) {
                    if (curIndex >= this.mItems.size()) {
                        break;
                    }
                    ItemInfo itemInfo3 = (ItemInfo) this.mItems.get(curIndex);
                    ItemInfo ii = itemInfo3;
                    if (itemInfo3.position < this.mCurItem) {
                        curIndex++;
                    } else if (ii.position == this.mCurItem) {
                        curItem = ii;
                    }
                }
                if (curItem == null && N > 0) {
                    curItem = addNewItem(this.mCurItem, curIndex);
                }
                if (curItem != null) {
                    float extraWidthLeft = 0.0f;
                    int i3 = curIndex - 1;
                    int itemIndex = i3;
                    ItemInfo ii2 = i3 < 0 ? null : (ItemInfo) this.mItems.get(itemIndex);
                    int clientWidth = getClientWidth();
                    int clientWidth2 = clientWidth;
                    if (clientWidth > 0) {
                        f = (2.0f - curItem.widthFactor) + (((float) getPaddingLeft()) / ((float) clientWidth2));
                    } else {
                        f = 0.0f;
                    }
                    float leftWidthNeeded = f;
                    for (int pos = this.mCurItem - 1; pos >= 0; pos--) {
                        if (extraWidthLeft >= leftWidthNeeded && pos < startPos) {
                            if (ii2 == null) {
                                break;
                            } else if (pos == ii2.position && !ii2.scrolling) {
                                Object remove = this.mItems.remove(itemIndex);
                                this.mAdapter.destroyItem((ViewGroup) this, pos, ii2.object);
                                itemIndex--;
                                curIndex--;
                                ii2 = itemIndex < 0 ? null : (ItemInfo) this.mItems.get(itemIndex);
                            }
                        } else if (ii2 != null && pos == ii2.position) {
                            extraWidthLeft += ii2.widthFactor;
                            itemIndex--;
                            ii2 = itemIndex < 0 ? null : (ItemInfo) this.mItems.get(itemIndex);
                        } else {
                            ItemInfo addNewItem = addNewItem(pos, itemIndex + 1);
                            ItemInfo ii3 = addNewItem;
                            extraWidthLeft += addNewItem.widthFactor;
                            curIndex++;
                            if (itemIndex < 0) {
                                itemInfo = null;
                            } else {
                                itemInfo = (ItemInfo) this.mItems.get(itemIndex);
                            }
                            ii2 = itemInfo;
                        }
                    }
                    float f3 = curItem.widthFactor;
                    float extraWidthRight = f3;
                    int i4 = curIndex + 1;
                    int itemIndex2 = i4;
                    if (f3 < 2.0f) {
                        ItemInfo ii4 = i4 >= this.mItems.size() ? null : (ItemInfo) this.mItems.get(itemIndex2);
                        if (clientWidth2 > 0) {
                            f2 = (((float) getPaddingRight()) / ((float) clientWidth2)) + 2.0f;
                        } else {
                            f2 = 0.0f;
                        }
                        float rightWidthNeeded = f2;
                        for (int pos2 = this.mCurItem + 1; pos2 < N; pos2++) {
                            if (extraWidthRight >= rightWidthNeeded && pos2 > endPos) {
                                if (ii4 == null) {
                                    break;
                                } else if (pos2 == ii4.position && !ii4.scrolling) {
                                    Object remove2 = this.mItems.remove(itemIndex2);
                                    this.mAdapter.destroyItem((ViewGroup) this, pos2, ii4.object);
                                    ii4 = itemIndex2 >= this.mItems.size() ? null : (ItemInfo) this.mItems.get(itemIndex2);
                                }
                            } else if (ii4 != null && pos2 == ii4.position) {
                                extraWidthRight += ii4.widthFactor;
                                itemIndex2++;
                                ii4 = itemIndex2 >= this.mItems.size() ? null : (ItemInfo) this.mItems.get(itemIndex2);
                            } else {
                                ItemInfo addNewItem2 = addNewItem(pos2, itemIndex2);
                                ItemInfo ii5 = addNewItem2;
                                itemIndex2++;
                                extraWidthRight += addNewItem2.widthFactor;
                                if (itemIndex2 >= this.mItems.size()) {
                                    itemInfo2 = null;
                                } else {
                                    itemInfo2 = (ItemInfo) this.mItems.get(itemIndex2);
                                }
                                ii4 = itemInfo2;
                            }
                        }
                    }
                    calculatePageOffsets(curItem, curIndex, oldCurInfo);
                }
                this.mAdapter.setPrimaryItem((ViewGroup) this, this.mCurItem, curItem == null ? null : curItem.object);
                this.mAdapter.finishUpdate((ViewGroup) this);
                int childCount = getChildCount();
                for (int i5 = 0; i5 < childCount; i5++) {
                    View childAt = getChildAt(i5);
                    View child = childAt;
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    LayoutParams lp = layoutParams;
                    layoutParams.childIndex = i5;
                    if (!lp.isDecor && lp.widthFactor == 0.0f) {
                        ItemInfo infoForChild = infoForChild(child);
                        ItemInfo ii6 = infoForChild;
                        if (infoForChild != null) {
                            lp.widthFactor = ii6.widthFactor;
                            lp.position = ii6.position;
                        }
                    }
                }
                sortChildDrawingOrder();
                if (hasFocus()) {
                    View findFocus = findFocus();
                    ItemInfo ii7 = findFocus == null ? null : infoForAnyChild(findFocus);
                    if (ii7 == null || ii7.position != this.mCurItem) {
                        for (int i6 = 0; i6 < getChildCount(); i6++) {
                            View child2 = getChildAt(i6);
                            ItemInfo infoForChild2 = infoForChild(child2);
                            ItemInfo ii8 = infoForChild2;
                            if (infoForChild2 != null && ii8.position == this.mCurItem && child2.requestFocus(2)) {
                                break;
                            }
                        }
                    }
                }
                return;
            }
            try {
                resName = getResources().getResourceName(getId());
            } catch (NotFoundException e) {
                NotFoundException notFoundException = e;
                resName = Integer.toHexString(getId());
            }
            throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + N + " Pager id: " + resName + " Pager class: " + getClass() + " Problematic adapter: " + this.mAdapter.getClass());
        }
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren != null) {
                this.mDrawingOrderedChildren.clear();
            } else {
                this.mDrawingOrderedChildren = new ArrayList<>();
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                boolean add = this.mDrawingOrderedChildren.add(getChildAt(i));
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    private void calculatePageOffsets(ItemInfo itemInfo, int i, ItemInfo itemInfo2) {
        ItemInfo ii;
        ItemInfo ii2;
        ItemInfo curItem = itemInfo;
        int curIndex = i;
        ItemInfo oldCurInfo = itemInfo2;
        ItemInfo itemInfo3 = curItem;
        int i2 = curIndex;
        ItemInfo itemInfo4 = oldCurInfo;
        int N = this.mAdapter.getCount();
        int clientWidth = getClientWidth();
        float marginOffset = clientWidth <= 0 ? 0.0f : ((float) this.mPageMargin) / ((float) clientWidth);
        if (oldCurInfo != null) {
            int i3 = oldCurInfo.position;
            int oldCurPosition = i3;
            if (i3 >= curItem.position) {
                if (oldCurPosition > curItem.position) {
                    int itemIndex = this.mItems.size() - 1;
                    float offset = oldCurInfo.offset;
                    int pos = oldCurPosition - 1;
                    while (pos >= curItem.position && itemIndex >= 0) {
                        Object obj = this.mItems.get(itemIndex);
                        while (true) {
                            ii2 = (ItemInfo) obj;
                            if (pos < ii2.position && itemIndex > 0) {
                                itemIndex--;
                                obj = this.mItems.get(itemIndex);
                            }
                        }
                        while (pos > ii2.position) {
                            offset -= this.mAdapter.getPageWidth(pos) + marginOffset;
                            pos--;
                        }
                        float f = offset - (ii2.widthFactor + marginOffset);
                        offset = f;
                        ii2.offset = f;
                        pos--;
                    }
                }
            } else {
                int itemIndex2 = 0;
                float offset2 = oldCurInfo.offset + oldCurInfo.widthFactor + marginOffset;
                int pos2 = oldCurPosition + 1;
                while (pos2 <= curItem.position && itemIndex2 < this.mItems.size()) {
                    Object obj2 = this.mItems.get(itemIndex2);
                    while (true) {
                        ii = (ItemInfo) obj2;
                        if (pos2 > ii.position && itemIndex2 < this.mItems.size() - 1) {
                            itemIndex2++;
                            obj2 = this.mItems.get(itemIndex2);
                        }
                    }
                    while (pos2 < ii.position) {
                        offset2 += this.mAdapter.getPageWidth(pos2) + marginOffset;
                        pos2++;
                    }
                    ii.offset = offset2;
                    offset2 += ii.widthFactor + marginOffset;
                    pos2++;
                }
            }
        }
        int itemCount = this.mItems.size();
        float offset3 = curItem.offset;
        int pos3 = curItem.position - 1;
        this.mFirstOffset = curItem.position != 0 ? -3.4028235E38f : curItem.offset;
        this.mLastOffset = curItem.position != N + -1 ? Float.MAX_VALUE : (curItem.offset + curItem.widthFactor) - 1.0f;
        int i4 = curIndex - 1;
        while (i4 >= 0) {
            ItemInfo itemInfo5 = (ItemInfo) this.mItems.get(i4);
            ItemInfo ii3 = itemInfo5;
            while (pos3 > itemInfo5.position) {
                int i5 = pos3;
                pos3--;
                offset3 -= this.mAdapter.getPageWidth(i5) + marginOffset;
            }
            float f2 = offset3 - (itemInfo5.widthFactor + marginOffset);
            offset3 = f2;
            itemInfo5.offset = f2;
            if (ii3.position == 0) {
                this.mFirstOffset = f2;
            }
            i4--;
            pos3--;
        }
        float offset4 = curItem.offset + curItem.widthFactor + marginOffset;
        int pos4 = curItem.position + 1;
        int i6 = curIndex + 1;
        while (i6 < itemCount) {
            ItemInfo itemInfo6 = (ItemInfo) this.mItems.get(i6);
            ItemInfo ii4 = itemInfo6;
            while (pos4 < itemInfo6.position) {
                int i7 = pos4;
                pos4++;
                offset4 += this.mAdapter.getPageWidth(i7) + marginOffset;
            }
            if (itemInfo6.position == N - 1) {
                this.mLastOffset = (offset4 + ii4.widthFactor) - 1.0f;
            }
            ii4.offset = offset4;
            offset4 += ii4.widthFactor + marginOffset;
            i6++;
            pos4++;
        }
        this.mNeedCalculatePageOffsets = false;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState ss = savedState;
        savedState.position = this.mCurItem;
        if (this.mAdapter != null) {
            ss.adapterState = this.mAdapter.saveState();
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            if (this.mAdapter == null) {
                this.mRestoredCurItem = ss.position;
                this.mRestoredAdapterState = ss.adapterState;
                this.mRestoredClassLoader = ss.loader;
            } else {
                this.mAdapter.restoreState(ss.adapterState, ss.loader);
                setCurrentItemInternal(ss.position, false, true);
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void addView(View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        View child = view;
        int index = i;
        android.view.ViewGroup.LayoutParams params = layoutParams;
        View view2 = child;
        int i2 = index;
        android.view.ViewGroup.LayoutParams params2 = params;
        if (!checkLayoutParams(params)) {
            params2 = generateLayoutParams(params);
        }
        LayoutParams layoutParams2 = (LayoutParams) params2;
        LayoutParams lp = layoutParams2;
        LayoutParams layoutParams3 = layoutParams2;
        layoutParams3.isDecor |= isDecorView(child);
        if (!this.mInLayout) {
            super.addView(child, index, params2);
        } else if (lp != null && lp.isDecor) {
            IllegalStateException illegalStateException = new IllegalStateException("Cannot add pager decor view during layout");
            throw illegalStateException;
        } else {
            lp.needsMeasure = true;
            boolean addViewInLayout = addViewInLayout(child, index, params2);
        }
    }

    private static boolean isDecorView(@NonNull View view) {
        View view2 = view;
        View view3 = view2;
        Class cls = view2.getClass();
        Class cls2 = cls;
        return cls.getAnnotation(DecorView.class) != null;
    }

    public void removeView(View view) {
        View view2 = view;
        View view3 = view2;
        if (!this.mInLayout) {
            super.removeView(view2);
        } else {
            removeViewInLayout(view2);
        }
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo infoForChild(View view) {
        View child = view;
        View view2 = child;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(child, ii.object)) {
                return ii;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo infoForAnyChild(View view) {
        View child = view;
        while (true) {
            ViewParent parent = child.getParent();
            ViewParent parent2 = parent;
            if (parent == this) {
                return infoForChild(child);
            }
            if (parent != null && (parent2 instanceof View)) {
                child = (View) parent2;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo infoForPosition(int i) {
        int position = i;
        int i2 = position;
        for (int i3 = 0; i3 < this.mItems.size(); i3++) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i3);
            ItemInfo ii = itemInfo;
            if (itemInfo.position == position) {
                return ii;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int measuredWidth = getMeasuredWidth();
        int measuredWidth2 = measuredWidth;
        int i5 = measuredWidth / 10;
        int i6 = i5;
        this.mGutterSize = Math.min(i5, this.mDefaultGutterSize);
        int childWidthSize = (measuredWidth2 - getPaddingLeft()) - getPaddingRight();
        int childHeightSize = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int size = getChildCount();
        for (int i7 = 0; i7 < size; i7++) {
            View childAt = getChildAt(i7);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams != null && lp.isDecor) {
                    int hgrav = lp.gravity & 7;
                    int vgrav = lp.gravity & 112;
                    int widthMode = Integer.MIN_VALUE;
                    int heightMode = Integer.MIN_VALUE;
                    boolean consumeVertical = vgrav == 48 || vgrav == 80;
                    boolean consumeHorizontal = hgrav == 3 || hgrav == 5;
                    if (consumeVertical) {
                        widthMode = 1073741824;
                    } else if (consumeHorizontal) {
                        heightMode = 1073741824;
                    }
                    int widthSize = childWidthSize;
                    int heightSize = childHeightSize;
                    if (lp.width != -2) {
                        widthMode = 1073741824;
                        if (lp.width != -1) {
                            widthSize = lp.width;
                        }
                    }
                    if (lp.height != -2) {
                        heightMode = 1073741824;
                        if (lp.height != -1) {
                            heightSize = lp.height;
                        }
                    }
                    int widthSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);
                    int makeMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
                    int i8 = makeMeasureSpec;
                    child.measure(widthSpec, makeMeasureSpec);
                    if (consumeVertical) {
                        childHeightSize -= child.getMeasuredHeight();
                    } else if (consumeHorizontal) {
                        childWidthSize -= child.getMeasuredWidth();
                    }
                }
            }
        }
        this.mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, 1073741824);
        this.mChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int size2 = getChildCount();
        for (int i9 = 0; i9 < size2; i9++) {
            View childAt2 = getChildAt(i9);
            View child2 = childAt2;
            if (childAt2.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) child2.getLayoutParams();
                LayoutParams lp2 = layoutParams2;
                if (layoutParams2 == null || !lp2.isDecor) {
                    int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec((int) (((float) childWidthSize) * lp2.widthFactor), 1073741824);
                    int i10 = makeMeasureSpec2;
                    child2.measure(makeMeasureSpec2, this.mChildHeightMeasureSpec);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        int w = i;
        int h = i2;
        int oldw = i3;
        int oldh = i4;
        int i5 = w;
        int i6 = h;
        int i7 = oldw;
        int i8 = oldh;
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw) {
            recomputeScrollPosition(w, oldw, this.mPageMargin, this.mPageMargin);
        }
    }

    private void recomputeScrollPosition(int i, int i2, int i3, int i4) {
        int width = i;
        int oldWidth = i2;
        int margin = i3;
        int oldMargin = i4;
        int i5 = width;
        int i6 = oldWidth;
        int i7 = margin;
        int i8 = oldMargin;
        if (oldWidth <= 0 || this.mItems.isEmpty()) {
            ItemInfo infoForPosition = infoForPosition(this.mCurItem);
            int min = (int) ((infoForPosition == null ? 0.0f : Math.min(infoForPosition.offset, this.mLastOffset)) * ((float) ((width - getPaddingLeft()) - getPaddingRight())));
            int scrollPos = min;
            if (min != getScrollX()) {
                completeScroll(false);
                scrollTo(scrollPos, getScrollY());
            }
        } else if (this.mScroller.isFinished()) {
            int widthWithMargin = ((width - getPaddingLeft()) - getPaddingRight()) + margin;
            int oldWidthWithMargin = ((oldWidth - getPaddingLeft()) - getPaddingRight()) + oldMargin;
            int scrollX = getScrollX();
            int i9 = scrollX;
            float f = ((float) scrollX) / ((float) oldWidthWithMargin);
            float f2 = f;
            int i10 = (int) (f * ((float) widthWithMargin));
            int i11 = i10;
            scrollTo(i10, getScrollY());
        } else {
            this.mScroller.setFinalX(getCurrentItem() * getClientWidth());
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childLeft;
        int childTop;
        int l = i;
        int t = i2;
        int r = i3;
        int b = i4;
        boolean z2 = z;
        int i5 = l;
        int i6 = t;
        int i7 = r;
        int i8 = b;
        int count = getChildCount();
        int width = r - l;
        int height = b - t;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int decorCount = 0;
        for (int i9 = 0; i9 < count; i9++) {
            View childAt = getChildAt(i9);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.isDecor) {
                    int vgrav = lp.gravity & 112;
                    switch (lp.gravity & 7) {
                        case 1:
                            childLeft = Math.max((width - child.getMeasuredWidth()) / 2, paddingLeft);
                            break;
                        case 3:
                            childLeft = paddingLeft;
                            paddingLeft += child.getMeasuredWidth();
                            break;
                        case 5:
                            childLeft = (width - paddingRight) - child.getMeasuredWidth();
                            paddingRight += child.getMeasuredWidth();
                            break;
                        default:
                            childLeft = paddingLeft;
                            break;
                    }
                    switch (vgrav) {
                        case 16:
                            childTop = Math.max((height - child.getMeasuredHeight()) / 2, paddingTop);
                            break;
                        case 48:
                            childTop = paddingTop;
                            paddingTop += child.getMeasuredHeight();
                            break;
                        case 80:
                            childTop = (height - paddingBottom) - child.getMeasuredHeight();
                            paddingBottom += child.getMeasuredHeight();
                            break;
                        default:
                            childTop = paddingTop;
                            break;
                    }
                    int i10 = childLeft + scrollX;
                    int childLeft2 = i10;
                    child.layout(i10, childTop, i10 + child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
                    decorCount++;
                }
            }
        }
        int childWidth = (width - paddingLeft) - paddingRight;
        for (int i11 = 0; i11 < count; i11++) {
            View childAt2 = getChildAt(i11);
            View child2 = childAt2;
            if (childAt2.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) child2.getLayoutParams();
                LayoutParams lp2 = layoutParams2;
                if (!layoutParams2.isDecor) {
                    ItemInfo infoForChild = infoForChild(child2);
                    ItemInfo itemInfo = infoForChild;
                    if (infoForChild != null) {
                        int childLeft3 = paddingLeft + ((int) (((float) childWidth) * infoForChild.offset));
                        int childTop2 = paddingTop;
                        if (lp2.needsMeasure) {
                            lp2.needsMeasure = false;
                            int widthSpec = MeasureSpec.makeMeasureSpec((int) (((float) childWidth) * lp2.widthFactor), 1073741824);
                            int makeMeasureSpec = MeasureSpec.makeMeasureSpec((height - paddingTop) - paddingBottom, 1073741824);
                            int i12 = makeMeasureSpec;
                            child2.measure(widthSpec, makeMeasureSpec);
                        }
                        child2.layout(childLeft3, childTop2, childLeft3 + child2.getMeasuredWidth(), childTop2 + child2.getMeasuredHeight());
                    }
                }
            }
        }
        this.mTopPageBounds = paddingTop;
        this.mBottomPageBounds = height - paddingBottom;
        this.mDecorChildCount = decorCount;
        if (this.mFirstLayout) {
            scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    public void computeScroll() {
        this.mIsScrollStarted = true;
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = this.mScroller.getCurrX();
            int y = this.mScroller.getCurrY();
            if (!(oldX == x && oldY == y)) {
                scrollTo(x, y);
                if (!pageScrolled(x)) {
                    this.mScroller.abortAnimation();
                    scrollTo(0, y);
                }
            }
            ViewCompat.postInvalidateOnAnimation(this);
            return;
        }
        completeScroll(true);
    }

    private boolean pageScrolled(int i) {
        int xpos = i;
        int i2 = xpos;
        if (this.mItems.size() != 0) {
            ItemInfo ii = infoForCurrentScrollPosition();
            int clientWidth = getClientWidth();
            int width = clientWidth;
            int widthWithMargin = clientWidth + this.mPageMargin;
            float marginOffset = ((float) this.mPageMargin) / ((float) width);
            int currentPage = ii.position;
            float f = ((((float) xpos) / ((float) width)) - ii.offset) / (ii.widthFactor + marginOffset);
            float pageOffset = f;
            int i3 = (int) (f * ((float) widthWithMargin));
            int i4 = i3;
            this.mCalledSuper = false;
            onPageScrolled(currentPage, pageOffset, i3);
            if (this.mCalledSuper) {
                return true;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        } else if (this.mFirstLayout) {
            return false;
        } else {
            this.mCalledSuper = false;
            onPageScrolled(0, 0.0f, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onPageScrolled(int i, float f, int i2) {
        int childLeft;
        int position = i;
        float offset = f;
        int offsetPixels = i2;
        int i3 = position;
        float f2 = offset;
        int i4 = offsetPixels;
        if (this.mDecorChildCount > 0) {
            int scrollX = getScrollX();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width = getWidth();
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                View child = childAt;
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.isDecor) {
                    switch (lp.gravity & 7) {
                        case 1:
                            childLeft = Math.max((width - child.getMeasuredWidth()) / 2, paddingLeft);
                            break;
                        case 3:
                            childLeft = paddingLeft;
                            paddingLeft += child.getWidth();
                            break;
                        case 5:
                            childLeft = (width - paddingRight) - child.getMeasuredWidth();
                            paddingRight += child.getMeasuredWidth();
                            break;
                        default:
                            childLeft = paddingLeft;
                            break;
                    }
                    int i6 = childLeft + scrollX;
                    int childLeft2 = i6;
                    int left = i6 - child.getLeft();
                    int childOffset = left;
                    if (left != 0) {
                        child.offsetLeftAndRight(childOffset);
                    }
                }
            }
        }
        dispatchOnPageScrolled(position, offset, offsetPixels);
        if (this.mPageTransformer != null) {
            int scrollX2 = getScrollX();
            int childCount2 = getChildCount();
            for (int i7 = 0; i7 < childCount2; i7++) {
                View childAt2 = getChildAt(i7);
                View child2 = childAt2;
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                LayoutParams layoutParams3 = layoutParams2;
                if (!layoutParams2.isDecor) {
                    float left2 = ((float) (child2.getLeft() - scrollX2)) / ((float) getClientWidth());
                    float f3 = left2;
                    this.mPageTransformer.transformPage(child2, left2);
                }
            }
        }
        this.mCalledSuper = true;
    }

    private void dispatchOnPageScrolled(int i, float f, int i2) {
        int position = i;
        float offset = f;
        int offsetPixels = i2;
        int i3 = position;
        float f2 = offset;
        int i4 = offsetPixels;
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(position, offset, offsetPixels);
        }
        if (this.mOnPageChangeListeners != null) {
            int z = this.mOnPageChangeListeners.size();
            for (int i5 = 0; i5 < z; i5++) {
                OnPageChangeListener onPageChangeListener = (OnPageChangeListener) this.mOnPageChangeListeners.get(i5);
                OnPageChangeListener listener = onPageChangeListener;
                if (onPageChangeListener != null) {
                    listener.onPageScrolled(position, offset, offsetPixels);
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(position, offset, offsetPixels);
        }
    }

    private void dispatchOnPageSelected(int i) {
        int position = i;
        int i2 = position;
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(position);
        }
        if (this.mOnPageChangeListeners != null) {
            int z = this.mOnPageChangeListeners.size();
            for (int i3 = 0; i3 < z; i3++) {
                OnPageChangeListener onPageChangeListener = (OnPageChangeListener) this.mOnPageChangeListeners.get(i3);
                OnPageChangeListener listener = onPageChangeListener;
                if (onPageChangeListener != null) {
                    listener.onPageSelected(position);
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(position);
        }
    }

    private void dispatchOnScrollStateChanged(int i) {
        int state = i;
        int i2 = state;
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        if (this.mOnPageChangeListeners != null) {
            int z = this.mOnPageChangeListeners.size();
            for (int i3 = 0; i3 < z; i3++) {
                OnPageChangeListener onPageChangeListener = (OnPageChangeListener) this.mOnPageChangeListeners.get(i3);
                OnPageChangeListener listener = onPageChangeListener;
                if (onPageChangeListener != null) {
                    listener.onPageScrollStateChanged(state);
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private void completeScroll(boolean z) {
        boolean postEvents = z;
        boolean needPopulate = this.mScrollState == 2;
        if (needPopulate) {
            setScrollingCacheEnabled(false);
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                int oldX = getScrollX();
                int oldY = getScrollY();
                int x = this.mScroller.getCurrX();
                int y = this.mScroller.getCurrY();
                if (!(oldX == x && oldY == y)) {
                    scrollTo(x, y);
                    if (x != oldX) {
                        boolean pageScrolled = pageScrolled(x);
                    }
                }
            }
        }
        this.mPopulatePending = false;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i);
            ItemInfo ii = itemInfo;
            if (itemInfo.scrolling) {
                needPopulate = true;
                ii.scrolling = false;
            }
        }
        if (needPopulate) {
            if (!postEvents) {
                this.mEndScrollRunnable.run();
            } else {
                ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0016, code lost:
        if ((r2 > 0.0f) == false) goto L_0x0018;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isGutterDrag(float r18, float r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r0
            r4 = r1
            r5 = r2
            int r6 = r0.mGutterSize
            float r7 = (float) r6
            int r8 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r8 >= 0) goto L_0x002d
            r9 = 0
            int r10 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r10 <= 0) goto L_0x002e
            r11 = 1
        L_0x0016:
            if (r11 != 0) goto L_0x0030
        L_0x0018:
            int r6 = r0.getWidth()
            int r12 = r0.mGutterSize
            int r6 = r6 - r12
            float r7 = (float) r6
            int r13 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r13 <= 0) goto L_0x0031
            r9 = 0
            int r14 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r14 >= 0) goto L_0x0033
        L_0x0029:
            r15 = 1
        L_0x002a:
            r16 = r15
            return r16
        L_0x002d:
            goto L_0x0018
        L_0x002e:
            r11 = 0
            goto L_0x0016
        L_0x0030:
            goto L_0x0029
        L_0x0031:
            r15 = 0
            goto L_0x002a
        L_0x0033:
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.view.ViewPager.isGutterDrag(float, float):boolean");
    }

    private void enableLayers(boolean z) {
        boolean enable = z;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewCompat.setLayerType(getChildAt(i), !enable ? 0 : this.mPageTransformerLayerType, null);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int action = ev.getAction() & 255;
        int action2 = action;
        if (action == 3 || action2 == 1) {
            boolean resetTouch = resetTouch();
            return false;
        }
        if (action2 != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        switch (action2) {
            case 0:
                float x = ev.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                float y = ev.getY();
                this.mInitialMotionY = y;
                this.mLastMotionY = y;
                this.mActivePointerId = ev.getPointerId(0);
                this.mIsUnableToDrag = false;
                this.mIsScrollStarted = true;
                boolean computeScrollOffset = this.mScroller.computeScrollOffset();
                if (this.mScrollState != 2 || Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) <= this.mCloseEnough) {
                    completeScroll(false);
                    this.mIsBeingDragged = false;
                    break;
                } else {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    populate();
                    this.mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                    break;
                }
                break;
            case 2:
                int i = this.mActivePointerId;
                int activePointerId = i;
                if (i != -1) {
                    int findPointerIndex = ev.findPointerIndex(activePointerId);
                    int i2 = findPointerIndex;
                    float x2 = ev.getX(findPointerIndex);
                    float x3 = x2;
                    float f = x2 - this.mLastMotionX;
                    float dx = f;
                    float xDiff = Math.abs(f);
                    float y2 = ev.getY(findPointerIndex);
                    float y3 = y2;
                    float yDiff = Math.abs(y2 - this.mInitialMotionY);
                    if (dx != 0.0f && !isGutterDrag(this.mLastMotionX, dx)) {
                        if (canScroll(this, false, (int) dx, (int) x3, (int) y3)) {
                            this.mLastMotionX = x3;
                            this.mLastMotionY = y3;
                            this.mIsUnableToDrag = true;
                            return false;
                        }
                    }
                    if (xDiff > ((float) this.mTouchSlop) && xDiff * 0.5f > yDiff) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        setScrollState(1);
                        this.mLastMotionX = dx > 0.0f ? this.mInitialMotionX + ((float) this.mTouchSlop) : this.mInitialMotionX - ((float) this.mTouchSlop);
                        this.mLastMotionY = y3;
                        setScrollingCacheEnabled(true);
                    } else if (yDiff > ((float) this.mTouchSlop)) {
                        this.mIsUnableToDrag = true;
                    }
                    if (this.mIsBeingDragged && performDrag(x3)) {
                        ViewCompat.postInvalidateOnAnimation(this);
                        break;
                    }
                }
                break;
            case 6:
                onSecondaryPointerUp(ev);
                break;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        if (this.mFakeDragging) {
            return true;
        }
        if (ev.getAction() == 0 && ev.getEdgeFlags() != 0) {
            return false;
        }
        if (this.mAdapter == null || this.mAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        boolean needsInvalidate = false;
        switch (ev.getAction() & 255) {
            case 0:
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                float x = ev.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                float y = ev.getY();
                this.mInitialMotionY = y;
                this.mLastMotionY = y;
                this.mActivePointerId = ev.getPointerId(0);
                break;
            case 1:
                if (this.mIsBeingDragged) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    VelocityTracker velocityTracker2 = velocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker2, this.mActivePointerId);
                    this.mPopulatePending = true;
                    int width = getClientWidth();
                    int scrollX = getScrollX();
                    ItemInfo ii = infoForCurrentScrollPosition();
                    float marginOffset = ((float) this.mPageMargin) / ((float) width);
                    int currentPage = ii.position;
                    float pageOffset = ((((float) scrollX) / ((float) width)) - ii.offset) / (ii.widthFactor + marginOffset);
                    int findPointerIndex = ev.findPointerIndex(this.mActivePointerId);
                    int i = findPointerIndex;
                    float x2 = ev.getX(findPointerIndex);
                    float f = x2;
                    int i2 = (int) (x2 - this.mInitialMotionX);
                    int i3 = i2;
                    int determineTargetPage = determineTargetPage(currentPage, pageOffset, initialVelocity, i2);
                    int i4 = determineTargetPage;
                    setCurrentItemInternal(determineTargetPage, true, true, initialVelocity);
                    needsInvalidate = resetTouch();
                    break;
                }
                break;
            case 2:
                if (!this.mIsBeingDragged) {
                    int findPointerIndex2 = ev.findPointerIndex(this.mActivePointerId);
                    int pointerIndex = findPointerIndex2;
                    if (findPointerIndex2 == -1) {
                        needsInvalidate = resetTouch();
                        break;
                    } else {
                        float x3 = ev.getX(pointerIndex);
                        float x4 = x3;
                        float xDiff = Math.abs(x3 - this.mLastMotionX);
                        float y2 = ev.getY(pointerIndex);
                        float y3 = y2;
                        float yDiff = Math.abs(y2 - this.mLastMotionY);
                        if (xDiff > ((float) this.mTouchSlop) && xDiff > yDiff) {
                            this.mIsBeingDragged = true;
                            requestParentDisallowInterceptTouchEvent(true);
                            this.mLastMotionX = x4 - this.mInitialMotionX > 0.0f ? this.mInitialMotionX + ((float) this.mTouchSlop) : this.mInitialMotionX - ((float) this.mTouchSlop);
                            this.mLastMotionY = y3;
                            setScrollState(1);
                            setScrollingCacheEnabled(true);
                            ViewParent parent = getParent();
                            ViewParent parent2 = parent;
                            if (parent != null) {
                                parent2.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    }
                }
                if (this.mIsBeingDragged) {
                    int findPointerIndex3 = ev.findPointerIndex(this.mActivePointerId);
                    int i5 = findPointerIndex3;
                    float x5 = ev.getX(findPointerIndex3);
                    float f2 = x5;
                    needsInvalidate = false | performDrag(x5);
                    break;
                }
                break;
            case 3:
                if (this.mIsBeingDragged) {
                    scrollToItem(this.mCurItem, true, 0, false);
                    needsInvalidate = resetTouch();
                    break;
                }
                break;
            case 5:
                int actionIndex = MotionEventCompat.getActionIndex(ev);
                int i6 = actionIndex;
                float x6 = ev.getX(actionIndex);
                float f3 = x6;
                this.mLastMotionX = x6;
                this.mActivePointerId = ev.getPointerId(actionIndex);
                break;
            case 6:
                onSecondaryPointerUp(ev);
                this.mLastMotionX = ev.getX(ev.findPointerIndex(this.mActivePointerId));
                break;
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        return true;
    }

    private boolean resetTouch() {
        this.mActivePointerId = -1;
        endDrag();
        boolean onRelease = this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
        boolean z = onRelease;
        return onRelease;
    }

    private void requestParentDisallowInterceptTouchEvent(boolean z) {
        boolean disallowIntercept = z;
        ViewParent parent = getParent();
        ViewParent parent2 = parent;
        if (parent != null) {
            parent2.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    private boolean performDrag(float f) {
        float x = f;
        float f2 = x;
        boolean needsInvalidate = false;
        float deltaX = this.mLastMotionX - x;
        this.mLastMotionX = x;
        float scrollX = (float) getScrollX();
        float f3 = scrollX;
        float scrollX2 = scrollX + deltaX;
        int clientWidth = getClientWidth();
        int width = clientWidth;
        float leftBound = ((float) clientWidth) * this.mFirstOffset;
        float rightBound = ((float) width) * this.mLastOffset;
        boolean leftAbsolute = true;
        boolean rightAbsolute = true;
        ItemInfo firstItem = (ItemInfo) this.mItems.get(0);
        ItemInfo lastItem = (ItemInfo) this.mItems.get(this.mItems.size() - 1);
        if (firstItem.position != 0) {
            leftAbsolute = false;
            leftBound = firstItem.offset * ((float) width);
        }
        if (lastItem.position != this.mAdapter.getCount() - 1) {
            rightAbsolute = false;
            rightBound = lastItem.offset * ((float) width);
        }
        if (scrollX2 < leftBound) {
            if (leftAbsolute) {
                float f4 = leftBound - scrollX2;
                float f5 = f4;
                needsInvalidate = this.mLeftEdge.onPull(Math.abs(f4) / ((float) width));
            }
            scrollX2 = leftBound;
        } else if (scrollX2 > rightBound) {
            if (rightAbsolute) {
                float f6 = scrollX2 - rightBound;
                float f7 = f6;
                needsInvalidate = this.mRightEdge.onPull(Math.abs(f6) / ((float) width));
            }
            scrollX2 = rightBound;
        }
        this.mLastMotionX += scrollX2 - ((float) ((int) scrollX2));
        scrollTo((int) scrollX2, getScrollY());
        boolean pageScrolled = pageScrolled((int) scrollX2);
        return needsInvalidate;
    }

    private ItemInfo infoForCurrentScrollPosition() {
        int clientWidth = getClientWidth();
        int width = clientWidth;
        float scrollOffset = clientWidth <= 0 ? 0.0f : ((float) getScrollX()) / ((float) width);
        float marginOffset = width <= 0 ? 0.0f : ((float) this.mPageMargin) / ((float) width);
        int lastPos = -1;
        float lastOffset = 0.0f;
        float lastWidth = 0.0f;
        boolean first = true;
        ItemInfo lastItem = null;
        int i = 0;
        while (i < this.mItems.size()) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i);
            ItemInfo ii = itemInfo;
            if (!first && itemInfo.position != lastPos + 1) {
                ItemInfo itemInfo2 = this.mTempItem;
                ii = itemInfo2;
                itemInfo2.offset = lastOffset + lastWidth + marginOffset;
                ii.position = lastPos + 1;
                ii.widthFactor = this.mAdapter.getPageWidth(ii.position);
                i--;
            }
            float f = ii.offset;
            float offset = f;
            float leftBound = f;
            float rightBound = offset + ii.widthFactor + marginOffset;
            if (!first && scrollOffset < leftBound) {
                return lastItem;
            }
            if ((scrollOffset < rightBound) || i == this.mItems.size() - 1) {
                return ii;
            }
            first = false;
            lastPos = ii.position;
            lastOffset = offset;
            lastWidth = ii.widthFactor;
            lastItem = ii;
            i++;
        }
        return lastItem;
    }

    private int determineTargetPage(int i, float f, int i2, int i3) {
        int targetPage;
        int currentPage = i;
        float pageOffset = f;
        int velocity = i2;
        int deltaX = i3;
        int i4 = currentPage;
        float f2 = pageOffset;
        int i5 = velocity;
        int i6 = deltaX;
        if (Math.abs(deltaX) > this.mFlingDistance && Math.abs(velocity) > this.mMinimumVelocity) {
            targetPage = velocity <= 0 ? currentPage + 1 : currentPage;
        } else {
            targetPage = currentPage + ((int) (pageOffset + (currentPage < this.mCurItem ? 0.6f : 0.4f)));
        }
        if (this.mItems.size() > 0) {
            targetPage = Math.max(((ItemInfo) this.mItems.get(0)).position, Math.min(targetPage, ((ItemInfo) this.mItems.get(this.mItems.size() - 1)).position));
        }
        return targetPage;
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        super.draw(canvas2);
        boolean needsInvalidate = false;
        int overScrollMode = getOverScrollMode();
        int overScrollMode2 = overScrollMode;
        if (overScrollMode != 0 && (overScrollMode2 != 1 || this.mAdapter == null || this.mAdapter.getCount() <= 1)) {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        } else {
            if (!this.mLeftEdge.isFinished()) {
                int restoreCount = canvas2.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                int i = width;
                canvas2.rotate(270.0f);
                canvas2.translate((float) ((-height) + getPaddingTop()), this.mFirstOffset * ((float) width));
                this.mLeftEdge.setSize(height, width);
                needsInvalidate = false | this.mLeftEdge.draw(canvas2);
                canvas2.restoreToCount(restoreCount);
            }
            if (!this.mRightEdge.isFinished()) {
                int restoreCount2 = canvas2.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int i2 = height2;
                canvas2.rotate(90.0f);
                canvas2.translate((float) (-getPaddingTop()), (-(this.mLastOffset + 1.0f)) * ((float) width2));
                this.mRightEdge.setSize(height2, width2);
                needsInvalidate |= this.mRightEdge.draw(canvas2);
                canvas2.restoreToCount(restoreCount2);
            }
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float drawAt;
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        super.onDraw(canvas2);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int scrollX = getScrollX();
            int width = getWidth();
            float marginOffset = ((float) this.mPageMargin) / ((float) width);
            int itemIndex = 0;
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(0);
            ItemInfo ii = itemInfo;
            float offset = itemInfo.offset;
            int itemCount = this.mItems.size();
            int firstPos = ii.position;
            int lastPos = ((ItemInfo) this.mItems.get(itemCount - 1)).position;
            int pos = firstPos;
            while (pos < lastPos) {
                while (pos > ii.position && itemIndex < itemCount) {
                    itemIndex++;
                    ii = (ItemInfo) this.mItems.get(itemIndex);
                }
                if (pos != ii.position) {
                    float widthFactor = this.mAdapter.getPageWidth(pos);
                    drawAt = (offset + widthFactor) * ((float) width);
                    offset += widthFactor + marginOffset;
                } else {
                    drawAt = (ii.offset + ii.widthFactor) * ((float) width);
                    offset = ii.offset + ii.widthFactor + marginOffset;
                }
                if (drawAt + ((float) this.mPageMargin) > ((float) scrollX)) {
                    this.mMarginDrawable.setBounds(Math.round(drawAt), this.mTopPageBounds, Math.round(drawAt + ((float) this.mPageMargin)), this.mBottomPageBounds);
                    this.mMarginDrawable.draw(canvas2);
                }
                if (drawAt <= ((float) (scrollX + width))) {
                    pos++;
                } else {
                    return;
                }
            }
        }
    }

    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        setScrollState(1);
        this.mLastMotionX = 0.0f;
        this.mInitialMotionX = 0.0f;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        } else {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        long time = uptimeMillis;
        MotionEvent ev = MotionEvent.obtain(uptimeMillis, time, 0, 0.0f, 0.0f, 0);
        this.mVelocityTracker.addMovement(ev);
        ev.recycle();
        this.mFakeDragBeginTime = time;
        return true;
    }

    public void endFakeDrag() {
        if (this.mFakeDragging) {
            if (this.mAdapter != null) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                VelocityTracker velocityTracker2 = velocityTracker;
                velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker2, this.mActivePointerId);
                this.mPopulatePending = true;
                int width = getClientWidth();
                int scrollX = getScrollX();
                ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
                ItemInfo ii = infoForCurrentScrollPosition;
                int i = (int) (this.mLastMotionX - this.mInitialMotionX);
                int i2 = i;
                int determineTargetPage = determineTargetPage(infoForCurrentScrollPosition.position, ((((float) scrollX) / ((float) width)) - ii.offset) / ii.widthFactor, initialVelocity, i);
                int i3 = determineTargetPage;
                setCurrentItemInternal(determineTargetPage, true, true, initialVelocity);
            }
            endDrag();
            this.mFakeDragging = false;
            return;
        }
        throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
    }

    public void fakeDragBy(float f) {
        float xOffset = f;
        float f2 = xOffset;
        if (!this.mFakeDragging) {
            IllegalStateException illegalStateException = new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
            throw illegalStateException;
        } else if (this.mAdapter != null) {
            this.mLastMotionX += xOffset;
            float scrollX = (float) getScrollX();
            float f3 = scrollX;
            float scrollX2 = scrollX - xOffset;
            int clientWidth = getClientWidth();
            int width = clientWidth;
            float leftBound = ((float) clientWidth) * this.mFirstOffset;
            float rightBound = ((float) width) * this.mLastOffset;
            ItemInfo firstItem = (ItemInfo) this.mItems.get(0);
            ItemInfo lastItem = (ItemInfo) this.mItems.get(this.mItems.size() - 1);
            if (firstItem.position != 0) {
                leftBound = firstItem.offset * ((float) width);
            }
            if (lastItem.position != this.mAdapter.getCount() - 1) {
                rightBound = lastItem.offset * ((float) width);
            }
            if (scrollX2 < leftBound) {
                scrollX2 = leftBound;
            } else if (scrollX2 > rightBound) {
                scrollX2 = rightBound;
            }
            this.mLastMotionX += scrollX2 - ((float) ((int) scrollX2));
            scrollTo((int) scrollX2, getScrollY());
            boolean pageScrolled = pageScrolled((int) scrollX2);
            long time = SystemClock.uptimeMillis();
            MotionEvent ev = MotionEvent.obtain(this.mFakeDragBeginTime, time, 2, this.mLastMotionX, 0.0f, 0);
            this.mVelocityTracker.addMovement(ev);
            ev.recycle();
        }
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int actionIndex = MotionEventCompat.getActionIndex(ev);
        int pointerIndex = actionIndex;
        int pointerId = ev.getPointerId(actionIndex);
        int i = pointerId;
        if (pointerId == this.mActivePointerId) {
            int newPointerIndex = pointerIndex != 0 ? 0 : 1;
            this.mLastMotionX = ev.getX(newPointerIndex);
            this.mActivePointerId = ev.getPointerId(newPointerIndex);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void setScrollingCacheEnabled(boolean z) {
        boolean enabled = z;
        if (this.mScrollingCacheEnabled != enabled) {
            this.mScrollingCacheEnabled = enabled;
        }
    }

    public boolean canScrollHorizontally(int i) {
        int direction = i;
        int i2 = direction;
        if (this.mAdapter == null) {
            return false;
        }
        int width = getClientWidth();
        int scrollX = getScrollX();
        if (direction < 0) {
            return scrollX > ((int) (((float) width) * this.mFirstOffset));
        } else if (direction <= 0) {
            return false;
        } else {
            return scrollX < ((int) (((float) width) * this.mLastOffset));
        }
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        boolean z2;
        View v = view;
        int dx = i;
        int x = i2;
        int y = i3;
        View view2 = v;
        boolean checkV = z;
        int i4 = dx;
        int i5 = x;
        int i6 = y;
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            int childCount = group.getChildCount();
            int i7 = childCount;
            for (int i8 = childCount - 1; i8 >= 0; i8--) {
                View childAt = group.getChildAt(i8);
                View child = childAt;
                if (x + scrollX >= childAt.getLeft() && x + scrollX < childAt.getRight() && y + scrollY >= childAt.getTop() && y + scrollY < childAt.getBottom()) {
                    if (canScroll(child, true, dx, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
                        return true;
                    }
                }
            }
        }
        if (checkV) {
            if (ViewCompat.canScrollHorizontally(v, -dx)) {
                z2 = true;
                return z2;
            }
        }
        z2 = false;
        return z2;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        return super.dispatchKeyEvent(event) || executeKeyEvent(event);
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        boolean handled = false;
        if (event.getAction() == 0) {
            switch (event.getKeyCode()) {
                case 21:
                    handled = arrowScroll(17);
                    break;
                case 22:
                    handled = arrowScroll(66);
                    break;
                case 61:
                    if (VERSION.SDK_INT >= 11) {
                        if (!KeyEventCompat.hasNoModifiers(event)) {
                            if (KeyEventCompat.hasModifiers(event, 1)) {
                                handled = arrowScroll(1);
                                break;
                            }
                        } else {
                            handled = arrowScroll(2);
                            break;
                        }
                    }
                    break;
            }
        }
        return handled;
    }

    public boolean arrowScroll(int i) {
        int direction = i;
        int i2 = direction;
        View findFocus = findFocus();
        View currentFocused = findFocus;
        if (findFocus == this) {
            currentFocused = null;
        } else if (currentFocused != null) {
            boolean isChild = false;
            ViewParent parent = currentFocused.getParent();
            while (true) {
                ViewParent parent2 = parent;
                if (parent2 instanceof ViewGroup) {
                    if (parent2 == this) {
                        isChild = true;
                        break;
                    }
                    parent = parent2.getParent();
                } else {
                    break;
                }
            }
            if (!isChild) {
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = sb;
                StringBuilder append = sb.append(currentFocused.getClass().getSimpleName());
                ViewParent parent3 = currentFocused.getParent();
                while (true) {
                    ViewParent parent4 = parent3;
                    if (!(parent4 instanceof ViewGroup)) {
                        break;
                    }
                    StringBuilder append2 = sb2.append(" => ").append(parent4.getClass().getSimpleName());
                    parent3 = parent4.getParent();
                }
                int e = Log.e(TAG, "arrowScroll tried to find focus based on non-child current focused view " + sb2.toString());
                currentFocused = null;
            }
        }
        boolean handled = false;
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
        View nextFocused = findNextFocus;
        if (findNextFocus == null || nextFocused == currentFocused) {
            if (direction == 17 || direction == 1) {
                handled = pageLeft();
            } else if (direction == 66 || direction == 2) {
                handled = pageRight();
            }
        } else if (direction == 17) {
            handled = (currentFocused != null && getChildRectInPagerCoordinates(this.mTempRect, nextFocused).left >= getChildRectInPagerCoordinates(this.mTempRect, currentFocused).left) ? pageLeft() : nextFocused.requestFocus();
        } else if (direction == 66) {
            handled = (currentFocused != null && getChildRectInPagerCoordinates(this.mTempRect, nextFocused).left <= getChildRectInPagerCoordinates(this.mTempRect, currentFocused).left) ? pageRight() : nextFocused.requestFocus();
        }
        if (handled) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
        }
        return handled;
    }

    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        Rect outRect = rect;
        View child = view;
        Rect outRect2 = outRect;
        View view2 = child;
        if (outRect == null) {
            outRect2 = new Rect();
        }
        if (child != null) {
            outRect2.left = child.getLeft();
            outRect2.right = child.getRight();
            outRect2.top = child.getTop();
            outRect2.bottom = child.getBottom();
            ViewParent parent = child.getParent();
            while (true) {
                ViewParent parent2 = parent;
                if ((parent2 instanceof ViewGroup) && parent2 != this) {
                    ViewGroup group = (ViewGroup) parent2;
                    outRect2.left += group.getLeft();
                    outRect2.right += group.getRight();
                    outRect2.top += group.getTop();
                    outRect2.bottom += group.getBottom();
                    parent = group.getParent();
                }
            }
            return outRect2;
        }
        outRect2.set(0, 0, 0, 0);
        return outRect2;
    }

    /* access modifiers changed from: 0000 */
    public boolean pageLeft() {
        if (this.mCurItem <= 0) {
            return false;
        }
        setCurrentItem(this.mCurItem - 1, true);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean pageRight() {
        if (this.mAdapter == null || this.mCurItem >= this.mAdapter.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        ArrayList<View> views = arrayList;
        int direction = i;
        int focusableMode = i2;
        ArrayList<View> arrayList2 = views;
        int i3 = direction;
        int i4 = focusableMode;
        int focusableCount = views.size();
        int descendantFocusability = getDescendantFocusability();
        int descendantFocusability2 = descendantFocusability;
        if (descendantFocusability != 393216) {
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                View childAt = getChildAt(i5);
                View child = childAt;
                if (childAt.getVisibility() == 0) {
                    ItemInfo infoForChild = infoForChild(child);
                    ItemInfo ii = infoForChild;
                    if (infoForChild != null && ii.position == this.mCurItem) {
                        child.addFocusables(views, direction, focusableMode);
                    }
                }
            }
        }
        if ((descendantFocusability2 != 262144 || focusableCount == views.size()) && isFocusable()) {
            if (((focusableMode & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && views != null) {
                boolean add = views.add(this);
            }
        }
    }

    public void addTouchables(ArrayList<View> arrayList) {
        ArrayList<View> views = arrayList;
        ArrayList<View> arrayList2 = views;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            View child = childAt;
            if (childAt.getVisibility() == 0) {
                ItemInfo infoForChild = infoForChild(child);
                ItemInfo ii = infoForChild;
                if (infoForChild != null && ii.position == this.mCurItem) {
                    child.addTouchables(views);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        int index;
        int increment;
        int end;
        int direction = i;
        Rect previouslyFocusedRect = rect;
        int i2 = direction;
        Rect rect2 = previouslyFocusedRect;
        int count = getChildCount();
        if ((direction & 2) == 0) {
            index = count - 1;
            increment = -1;
            end = -1;
        } else {
            index = 0;
            increment = 1;
            end = count;
        }
        for (int i3 = index; i3 != end; i3 += increment) {
            View childAt = getChildAt(i3);
            View child = childAt;
            if (childAt.getVisibility() == 0) {
                ItemInfo infoForChild = infoForChild(child);
                ItemInfo ii = infoForChild;
                if (infoForChild != null && ii.position == this.mCurItem && child.requestFocus(direction, previouslyFocusedRect)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        if (event.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(event);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            View child = childAt;
            if (childAt.getVisibility() == 0) {
                ItemInfo infoForChild = infoForChild(child);
                ItemInfo ii = infoForChild;
                if (infoForChild != null && ii.position == this.mCurItem && child.dispatchPopulateAccessibilityEvent(event)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams layoutParams2 = layoutParams;
        return generateDefaultLayoutParams();
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return (p instanceof LayoutParams) && super.checkLayoutParams(p);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AttributeSet attrs = attributeSet;
        AttributeSet attributeSet2 = attrs;
        return new LayoutParams(getContext(), attrs);
    }
}
