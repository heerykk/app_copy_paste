package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.design.C0001R;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.util.Pools.Pool;
import android.support.p000v4.util.Pools.SynchronizedPool;
import android.support.p000v4.view.AbsSavedState;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.NestedScrollingParent;
import android.support.p000v4.view.NestedScrollingParentHelper;
import android.support.p000v4.view.OnApplyWindowInsetsListener;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.WindowInsetsCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout extends ViewGroup implements NestedScrollingParent {
    static final Class<?>[] CONSTRUCTOR_PARAMS;
    static final int EVENT_NESTED_SCROLL = 1;
    static final int EVENT_PRE_DRAW = 0;
    static final int EVENT_VIEW_REMOVED = 2;
    static final String TAG = "CoordinatorLayout";
    static final Comparator<View> TOP_SORTED_CHILDREN_COMPARATOR;
    private static final int TYPE_ON_INTERCEPT = 0;
    private static final int TYPE_ON_TOUCH = 1;
    static final String WIDGET_PACKAGE_NAME;
    static final ThreadLocal<Map<String, Constructor<Behavior>>> sConstructors = new ThreadLocal<>();
    private static final Pool<Rect> sRectPool = new SynchronizedPool(12);
    private OnApplyWindowInsetsListener mApplyWindowInsetsListener;
    private View mBehaviorTouchView;
    private final DirectedAcyclicGraph<View> mChildDag;
    private final List<View> mDependencySortedChildren;
    private boolean mDisallowInterceptReset;
    private boolean mDrawStatusBarBackground;
    private boolean mIsAttachedToWindow;
    private int[] mKeylines;
    private WindowInsetsCompat mLastInsets;
    private boolean mNeedsPreDrawListener;
    private View mNestedScrollingDirectChild;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private View mNestedScrollingTarget;
    OnHierarchyChangeListener mOnHierarchyChangeListener;
    private OnPreDrawListener mOnPreDrawListener;
    private Paint mScrimPaint;
    private Drawable mStatusBarBackground;
    private final List<View> mTempDependenciesList;
    private final int[] mTempIntPair;
    private final List<View> mTempList1;

    public static abstract class Behavior<V extends View> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            Context context2 = context;
            AttributeSet attributeSet2 = attributeSet;
        }

        public void onAttachedToLayoutParams(@NonNull LayoutParams layoutParams) {
            LayoutParams layoutParams2 = layoutParams;
        }

        public void onDetachedFromLayoutParams() {
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            MotionEvent motionEvent2 = motionEvent;
            return false;
        }

        public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            MotionEvent motionEvent2 = motionEvent;
            return false;
        }

        @ColorInt
        public int getScrimColor(CoordinatorLayout coordinatorLayout, V v) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            return ViewCompat.MEASURED_STATE_MASK;
        }

        @FloatRange(from = 0.0d, mo5to = 1.0d)
        public float getScrimOpacity(CoordinatorLayout coordinatorLayout, V v) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            return 0.0f;
        }

        public boolean blocksInteractionBelow(CoordinatorLayout coordinatorLayout, V v) {
            CoordinatorLayout parent = coordinatorLayout;
            V child = v;
            CoordinatorLayout coordinatorLayout2 = parent;
            V v2 = child;
            return getScrimOpacity(parent, child) > 0.0f;
        }

        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, V v, View view) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            View view2 = view;
            return false;
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, V v, View view) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            View view2 = view;
            return false;
        }

        public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, V v, View view) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            View view2 = view;
        }

        @Deprecated
        public boolean isDirty(CoordinatorLayout coordinatorLayout, V v) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            return false;
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3, int i4) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            int i5 = i;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
            return false;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            int i2 = i;
            return false;
        }

        public static void setTag(View view, Object obj) {
            View child = view;
            Object tag = obj;
            View view2 = child;
            Object obj2 = tag;
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            layoutParams.mBehaviorTag = tag;
        }

        public static Object getTag(View view) {
            View child = view;
            View view2 = child;
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            return layoutParams.mBehaviorTag;
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            View view3 = view;
            View view4 = view2;
            int i2 = i;
            return false;
        }

        public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            View view3 = view;
            View view4 = view2;
            int i2 = i;
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            View view2 = view;
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int i3, int i4) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            View view2 = view;
            int i5 = i;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            View view2 = view;
            int i3 = i;
            int i4 = i2;
            int[] iArr2 = iArr;
        }

        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2, boolean z) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            View view2 = view;
            float f3 = f;
            float f4 = f2;
            boolean z2 = z;
            return false;
        }

        public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            View view2 = view;
            float f3 = f;
            float f4 = f2;
            return false;
        }

        @NonNull
        public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, V v, WindowInsetsCompat windowInsetsCompat) {
            WindowInsetsCompat insets = windowInsetsCompat;
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            WindowInsetsCompat windowInsetsCompat2 = insets;
            return insets;
        }

        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, V v, Rect rect, boolean z) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            Rect rect2 = rect;
            boolean z2 = z;
            return false;
        }

        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            Parcelable parcelable2 = parcelable;
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            return BaseSavedState.EMPTY_STATE;
        }

        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull Rect rect) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            V v2 = v;
            Rect rect2 = rect;
            return false;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface DefaultBehavior {
        Class<? extends Behavior> value();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DispatchChangeEvent {
    }

    private class HierarchyChangeListener implements OnHierarchyChangeListener {
        final /* synthetic */ CoordinatorLayout this$0;

        HierarchyChangeListener(CoordinatorLayout coordinatorLayout) {
            this.this$0 = coordinatorLayout;
        }

        public void onChildViewAdded(View view, View view2) {
            View parent = view;
            View child = view2;
            View view3 = parent;
            View view4 = child;
            if (this.this$0.mOnHierarchyChangeListener != null) {
                this.this$0.mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        public void onChildViewRemoved(View view, View view2) {
            View parent = view;
            View child = view2;
            View view3 = parent;
            View view4 = child;
            this.this$0.onChildViewsChanged(2);
            if (this.this$0.mOnHierarchyChangeListener != null) {
                this.this$0.mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        public int anchorGravity = 0;
        public int dodgeInsetEdges = 0;
        public int gravity = 0;
        public int insetEdge = 0;
        public int keyline = -1;
        View mAnchorDirectChild;
        int mAnchorId = -1;
        View mAnchorView;
        Behavior mBehavior;
        boolean mBehaviorResolved = false;
        Object mBehaviorTag;
        private boolean mDidAcceptNestedScroll;
        private boolean mDidBlockInteraction;
        private boolean mDidChangeAfterNestedScroll;
        int mInsetOffsetX;
        int mInsetOffsetY;
        final Rect mLastChildRect = new Rect();

        public LayoutParams(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            super(width, height);
        }

        LayoutParams(Context context, AttributeSet attributeSet) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            super(context2, attrs);
            TypedArray a = context2.obtainStyledAttributes(attrs, C0001R.styleable.CoordinatorLayout_Layout);
            this.gravity = a.getInteger(C0001R.styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
            this.mAnchorId = a.getResourceId(C0001R.styleable.CoordinatorLayout_Layout_layout_anchor, -1);
            this.anchorGravity = a.getInteger(C0001R.styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
            this.keyline = a.getInteger(C0001R.styleable.CoordinatorLayout_Layout_layout_keyline, -1);
            this.insetEdge = a.getInt(C0001R.styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
            this.dodgeInsetEdges = a.getInt(C0001R.styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
            this.mBehaviorResolved = a.hasValue(C0001R.styleable.CoordinatorLayout_Layout_layout_behavior);
            if (this.mBehaviorResolved) {
                this.mBehavior = CoordinatorLayout.parseBehavior(context2, attrs, a.getString(C0001R.styleable.CoordinatorLayout_Layout_layout_behavior));
            }
            a.recycle();
            if (this.mBehavior != null) {
                this.mBehavior.onAttachedToLayoutParams(this);
            }
        }

        public LayoutParams(LayoutParams layoutParams) {
            LayoutParams p = layoutParams;
            LayoutParams layoutParams2 = p;
            super(p);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.view.ViewGroup.LayoutParams p = layoutParams;
            android.view.ViewGroup.LayoutParams layoutParams2 = p;
            super(p);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams p = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = p;
            super(p);
        }

        @IdRes
        public int getAnchorId() {
            return this.mAnchorId;
        }

        public void setAnchorId(@IdRes int i) {
            int id = i;
            int i2 = id;
            invalidateAnchor();
            this.mAnchorId = id;
        }

        @Nullable
        public Behavior getBehavior() {
            return this.mBehavior;
        }

        public void setBehavior(@Nullable Behavior behavior) {
            Behavior behavior2 = behavior;
            Behavior behavior3 = behavior2;
            if (this.mBehavior != behavior2) {
                if (this.mBehavior != null) {
                    this.mBehavior.onDetachedFromLayoutParams();
                }
                this.mBehavior = behavior2;
                this.mBehaviorTag = null;
                this.mBehaviorResolved = true;
                if (behavior2 != null) {
                    behavior2.onAttachedToLayoutParams(this);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void setLastChildRect(Rect rect) {
            Rect r = rect;
            Rect rect2 = r;
            this.mLastChildRect.set(r);
        }

        /* access modifiers changed from: 0000 */
        public Rect getLastChildRect() {
            return this.mLastChildRect;
        }

        /* access modifiers changed from: 0000 */
        public boolean checkAnchorChanged() {
            return this.mAnchorView == null && this.mAnchorId != -1;
        }

        /* access modifiers changed from: 0000 */
        public boolean didBlockInteraction() {
            if (this.mBehavior == null) {
                this.mDidBlockInteraction = false;
            }
            return this.mDidBlockInteraction;
        }

        /* access modifiers changed from: 0000 */
        public boolean isBlockingInteractionBelow(CoordinatorLayout coordinatorLayout, View view) {
            CoordinatorLayout parent = coordinatorLayout;
            View child = view;
            CoordinatorLayout coordinatorLayout2 = parent;
            View view2 = child;
            if (this.mDidBlockInteraction) {
                return true;
            }
            boolean blocksInteractionBelow = this.mDidBlockInteraction | (this.mBehavior == null ? false : this.mBehavior.blocksInteractionBelow(parent, child));
            this.mDidBlockInteraction = blocksInteractionBelow;
            return blocksInteractionBelow;
        }

        /* access modifiers changed from: 0000 */
        public void resetTouchBehaviorTracking() {
            this.mDidBlockInteraction = false;
        }

        /* access modifiers changed from: 0000 */
        public void resetNestedScroll() {
            this.mDidAcceptNestedScroll = false;
        }

        /* access modifiers changed from: 0000 */
        public void acceptNestedScroll(boolean z) {
            this.mDidAcceptNestedScroll = z;
        }

        /* access modifiers changed from: 0000 */
        public boolean isNestedScrollAccepted() {
            return this.mDidAcceptNestedScroll;
        }

        /* access modifiers changed from: 0000 */
        public boolean getChangedAfterNestedScroll() {
            return this.mDidChangeAfterNestedScroll;
        }

        /* access modifiers changed from: 0000 */
        public void setChangedAfterNestedScroll(boolean z) {
            this.mDidChangeAfterNestedScroll = z;
        }

        /* access modifiers changed from: 0000 */
        public void resetChangedAfterNestedScroll() {
            this.mDidChangeAfterNestedScroll = false;
        }

        /* access modifiers changed from: 0000 */
        public boolean dependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            boolean z;
            CoordinatorLayout parent = coordinatorLayout;
            View child = view;
            View dependency = view2;
            CoordinatorLayout coordinatorLayout2 = parent;
            View view3 = child;
            View view4 = dependency;
            if (dependency != this.mAnchorDirectChild) {
                if (!shouldDodge(dependency, ViewCompat.getLayoutDirection(parent)) && (this.mBehavior == null || !this.mBehavior.layoutDependsOn(parent, child, dependency))) {
                    z = false;
                    return z;
                }
            }
            z = true;
            return z;
        }

        /* access modifiers changed from: 0000 */
        public void invalidateAnchor() {
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
        }

        /* access modifiers changed from: 0000 */
        public View findAnchorView(CoordinatorLayout coordinatorLayout, View view) {
            CoordinatorLayout parent = coordinatorLayout;
            View forChild = view;
            CoordinatorLayout coordinatorLayout2 = parent;
            View view2 = forChild;
            if (this.mAnchorId != -1) {
                if (this.mAnchorView == null || !verifyAnchorView(forChild, parent)) {
                    resolveAnchorView(forChild, parent);
                }
                return this.mAnchorView;
            }
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
            return null;
        }

        private void resolveAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            View forChild = view;
            CoordinatorLayout parent = coordinatorLayout;
            View view2 = forChild;
            CoordinatorLayout coordinatorLayout2 = parent;
            this.mAnchorView = parent.findViewById(this.mAnchorId);
            if (this.mAnchorView == null) {
                if (!parent.isInEditMode()) {
                    throw new IllegalStateException("Could not find CoordinatorLayout descendant view with id " + parent.getResources().getResourceName(this.mAnchorId) + " to anchor view " + forChild);
                }
                this.mAnchorDirectChild = null;
                this.mAnchorView = null;
            } else if (this.mAnchorView != parent) {
                View directChild = this.mAnchorView;
                ViewParent parent2 = this.mAnchorView.getParent();
                while (true) {
                    ViewParent p = parent2;
                    if (!(p == parent || p == null)) {
                        if (p != forChild) {
                            if (p instanceof View) {
                                directChild = (View) p;
                            }
                            parent2 = p.getParent();
                        } else if (!parent.isInEditMode()) {
                            throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                        } else {
                            this.mAnchorDirectChild = null;
                            this.mAnchorView = null;
                            return;
                        }
                    }
                }
                this.mAnchorDirectChild = directChild;
            } else if (!parent.isInEditMode()) {
                throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
            } else {
                this.mAnchorDirectChild = null;
                this.mAnchorView = null;
            }
        }

        private boolean verifyAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            View forChild = view;
            CoordinatorLayout parent = coordinatorLayout;
            View view2 = forChild;
            CoordinatorLayout coordinatorLayout2 = parent;
            if (this.mAnchorView.getId() != this.mAnchorId) {
                return false;
            }
            View directChild = this.mAnchorView;
            for (ViewParent p = this.mAnchorView.getParent(); p != parent; p = p.getParent()) {
                if (p == null || p == forChild) {
                    this.mAnchorDirectChild = null;
                    this.mAnchorView = null;
                    return false;
                }
                if (p instanceof View) {
                    directChild = (View) p;
                }
            }
            this.mAnchorDirectChild = directChild;
            return true;
        }

        private boolean shouldDodge(View view, int i) {
            View other = view;
            int layoutDirection = i;
            View view2 = other;
            int i2 = layoutDirection;
            LayoutParams layoutParams = (LayoutParams) other.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            int absoluteGravity = GravityCompat.getAbsoluteGravity(layoutParams.insetEdge, layoutDirection);
            int absInset = absoluteGravity;
            return absoluteGravity != 0 && (absInset & GravityCompat.getAbsoluteGravity(this.dodgeInsetEdges, layoutDirection)) == absInset;
        }
    }

    class OnPreDrawListener implements android.view.ViewTreeObserver.OnPreDrawListener {
        final /* synthetic */ CoordinatorLayout this$0;

        OnPreDrawListener(CoordinatorLayout coordinatorLayout) {
            CoordinatorLayout this$02 = coordinatorLayout;
            CoordinatorLayout coordinatorLayout2 = this$02;
            this.this$0 = this$02;
        }

        public boolean onPreDraw() {
            this.this$0.onChildViewsChanged(0);
            return true;
        }
    }

    protected static class SavedState extends AbsSavedState {
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
        SparseArray<Parcelable> behaviorStates;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            Parcel source = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = source;
            ClassLoader classLoader2 = loader;
            super(source, loader);
            int readInt = source.readInt();
            int size = readInt;
            int[] ids = new int[readInt];
            source.readIntArray(ids);
            Parcelable[] states = source.readParcelableArray(loader);
            SparseArray sparseArray = new SparseArray(size);
            this.behaviorStates = sparseArray;
            for (int i = 0; i < size; i++) {
                this.behaviorStates.append(ids[i], states[i]);
            }
        }

        public SavedState(Parcelable parcelable) {
            Parcelable superState = parcelable;
            Parcelable parcelable2 = superState;
            super(superState);
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            int flags = i;
            Parcel parcel2 = dest;
            int i2 = flags;
            super.writeToParcel(dest, flags);
            int size = this.behaviorStates == null ? 0 : this.behaviorStates.size();
            dest.writeInt(size);
            int[] ids = new int[size];
            Parcelable[] states = new Parcelable[size];
            for (int i3 = 0; i3 < size; i3++) {
                ids[i3] = this.behaviorStates.keyAt(i3);
                states[i3] = (Parcelable) this.behaviorStates.valueAt(i3);
            }
            dest.writeIntArray(ids);
            dest.writeParcelableArray(states, flags);
        }
    }

    static class ViewElevationComparator implements Comparator<View> {
        ViewElevationComparator() {
        }

        public /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            return compare((View) obj, (View) obj2);
        }

        public int compare(View view, View view2) {
            View lhs = view;
            View rhs = view2;
            View view3 = lhs;
            View view4 = rhs;
            float lz = ViewCompat.getZ(lhs);
            float rz = ViewCompat.getZ(rhs);
            if (lz > rz) {
                return -1;
            }
            if (lz < rz) {
                return 1;
            }
            return 0;
        }
    }

    static {
        Package packageR = CoordinatorLayout.class.getPackage();
        WIDGET_PACKAGE_NAME = packageR == null ? null : packageR.getName();
        if (VERSION.SDK_INT < 21) {
            TOP_SORTED_CHILDREN_COMPARATOR = null;
        } else {
            TOP_SORTED_CHILDREN_COMPARATOR = new ViewElevationComparator();
        }
        Class[] clsArr = new Class[2];
        clsArr[0] = Context.class;
        clsArr[1] = AttributeSet.class;
        CONSTRUCTOR_PARAMS = clsArr;
    }

    @NonNull
    private static Rect acquireTempRect() {
        Rect rect = (Rect) sRectPool.acquire();
        Rect rect2 = rect;
        if (rect == null) {
            rect2 = new Rect();
        }
        return rect2;
    }

    private static void releaseTempRect(@NonNull Rect rect) {
        Rect rect2 = rect;
        Rect rect3 = rect2;
        rect2.setEmpty();
        boolean release = sRectPool.release(rect2);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mDependencySortedChildren = new ArrayList();
        this.mChildDag = new DirectedAcyclicGraph<>();
        this.mTempList1 = new ArrayList();
        this.mTempDependenciesList = new ArrayList();
        this.mTempIntPair = new int[2];
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        ThemeUtils.checkAppCompatTheme(context2);
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attrs, C0001R.styleable.CoordinatorLayout, defStyleAttr, C0001R.style.Widget_Design_CoordinatorLayout);
        TypedArray a = obtainStyledAttributes;
        int resourceId = obtainStyledAttributes.getResourceId(C0001R.styleable.CoordinatorLayout_keylines, 0);
        int keylineArrayRes = resourceId;
        if (resourceId != 0) {
            Resources res = context2.getResources();
            this.mKeylines = res.getIntArray(keylineArrayRes);
            float f = res.getDisplayMetrics().density;
            float f2 = f;
            int count = this.mKeylines.length;
            for (int i3 = 0; i3 < count; i3++) {
                int[] iArr = this.mKeylines;
                iArr[i3] = (int) (((float) iArr[i3]) * f);
            }
        }
        this.mStatusBarBackground = a.getDrawable(C0001R.styleable.CoordinatorLayout_statusBarBackground);
        a.recycle();
        setupForInsets();
        super.setOnHierarchyChangeListener(new HierarchyChangeListener(this));
    }

    public CoordinatorLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void setOnHierarchyChangeListener(OnHierarchyChangeListener onHierarchyChangeListener) {
        OnHierarchyChangeListener onHierarchyChangeListener2 = onHierarchyChangeListener;
        OnHierarchyChangeListener onHierarchyChangeListener3 = onHierarchyChangeListener2;
        this.mOnHierarchyChangeListener = onHierarchyChangeListener2;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        resetTouchBehaviors();
        if (this.mNeedsPreDrawListener) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener(this);
            }
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            ViewTreeObserver viewTreeObserver2 = viewTreeObserver;
            viewTreeObserver.addOnPreDrawListener(this.mOnPreDrawListener);
        }
        if (this.mLastInsets == null && ViewCompat.getFitsSystemWindows(this)) {
            ViewCompat.requestApplyInsets(this);
        }
        this.mIsAttachedToWindow = true;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetTouchBehaviors();
        if (this.mNeedsPreDrawListener && this.mOnPreDrawListener != null) {
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            ViewTreeObserver viewTreeObserver2 = viewTreeObserver;
            viewTreeObserver.removeOnPreDrawListener(this.mOnPreDrawListener);
        }
        if (this.mNestedScrollingTarget != null) {
            onStopNestedScroll(this.mNestedScrollingTarget);
        }
        this.mIsAttachedToWindow = false;
    }

    public void setStatusBarBackground(@Nullable Drawable drawable) {
        Drawable bg = drawable;
        Drawable drawable2 = bg;
        if (this.mStatusBarBackground != bg) {
            if (this.mStatusBarBackground != null) {
                this.mStatusBarBackground.setCallback(null);
            }
            this.mStatusBarBackground = bg == null ? null : bg.mutate();
            if (this.mStatusBarBackground != null) {
                if (this.mStatusBarBackground.isStateful()) {
                    boolean state = this.mStatusBarBackground.setState(getDrawableState());
                }
                boolean layoutDirection = DrawableCompat.setLayoutDirection(this.mStatusBarBackground, ViewCompat.getLayoutDirection(this));
                boolean visible = this.mStatusBarBackground.setVisible(getVisibility() == 0, false);
                this.mStatusBarBackground.setCallback(this);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Nullable
    public Drawable getStatusBarBackground() {
        return this.mStatusBarBackground;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] state = getDrawableState();
        boolean changed = false;
        Drawable drawable = this.mStatusBarBackground;
        Drawable d = drawable;
        if (drawable != null && d.isStateful()) {
            changed = false | d.setState(state);
        }
        if (changed) {
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        Drawable who = drawable;
        Drawable drawable2 = who;
        return super.verifyDrawable(who) || who == this.mStatusBarBackground;
    }

    public void setVisibility(int i) {
        int visibility = i;
        int i2 = visibility;
        super.setVisibility(visibility);
        boolean visible = visibility == 0;
        if (this.mStatusBarBackground != null && this.mStatusBarBackground.isVisible() != visible) {
            boolean visible2 = this.mStatusBarBackground.setVisible(visible, false);
        }
    }

    public void setStatusBarBackgroundResource(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setStatusBarBackground(resId == 0 ? null : ContextCompat.getDrawable(getContext(), resId));
    }

    public void setStatusBarBackgroundColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        setStatusBarBackground(new ColorDrawable(color));
    }

    /* access modifiers changed from: 0000 */
    public final WindowInsetsCompat setWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat insets = windowInsetsCompat;
        WindowInsetsCompat insets2 = insets;
        if (!ViewUtils.objectEquals(this.mLastInsets, insets)) {
            this.mLastInsets = insets;
            this.mDrawStatusBarBackground = insets != null && insets.getSystemWindowInsetTop() > 0;
            setWillNotDraw(!this.mDrawStatusBarBackground && getBackground() == null);
            insets2 = dispatchApplyWindowInsetsToBehaviors(insets);
            requestLayout();
        }
        return insets2;
    }

    /* access modifiers changed from: 0000 */
    public final WindowInsetsCompat getLastWindowInsets() {
        return this.mLastInsets;
    }

    private void resetTouchBehaviors() {
        if (this.mBehaviorTouchView != null) {
            Behavior behavior = ((LayoutParams) this.mBehaviorTouchView.getLayoutParams()).getBehavior();
            Behavior b = behavior;
            if (behavior != null) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent cancelEvent = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                boolean onTouchEvent = b.onTouchEvent(this, this.mBehaviorTouchView, cancelEvent);
                cancelEvent.recycle();
            }
            this.mBehaviorTouchView = null;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            View view = childAt;
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            layoutParams.resetTouchBehaviorTracking();
        }
        this.mDisallowInterceptReset = false;
    }

    private void getTopSortedChildren(List<View> list) {
        List<View> out = list;
        List<View> list2 = out;
        out.clear();
        boolean useCustomOrder = isChildrenDrawingOrderEnabled();
        int childCount = getChildCount();
        int childCount2 = childCount;
        int i = childCount - 1;
        while (i >= 0) {
            boolean add = out.add(getChildAt(!useCustomOrder ? i : getChildDrawingOrder(childCount2, i)));
            i--;
        }
        if (TOP_SORTED_CHILDREN_COMPARATOR != null) {
            Collections.sort(out, TOP_SORTED_CHILDREN_COMPARATOR);
        }
    }

    private boolean performIntercept(MotionEvent motionEvent, int i) {
        MotionEvent ev = motionEvent;
        int type = i;
        MotionEvent motionEvent2 = ev;
        int i2 = type;
        boolean intercepted = false;
        boolean newBlock = false;
        MotionEvent cancelEvent = null;
        int action = MotionEventCompat.getActionMasked(ev);
        List<View> topmostChildList = this.mTempList1;
        getTopSortedChildren(topmostChildList);
        int childCount = topmostChildList.size();
        for (int i3 = 0; i3 < childCount; i3++) {
            View view = (View) topmostChildList.get(i3);
            View child = view;
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            LayoutParams lp = layoutParams;
            Behavior behavior = layoutParams.getBehavior();
            Behavior b = behavior;
            if ((!intercepted && !newBlock) || action == 0) {
                if (!intercepted && behavior != null) {
                    switch (type) {
                        case 0:
                            intercepted = b.onInterceptTouchEvent(this, child, ev);
                            break;
                        case 1:
                            intercepted = b.onTouchEvent(this, child, ev);
                            break;
                    }
                    if (intercepted) {
                        this.mBehaviorTouchView = child;
                    }
                }
                boolean wasBlocking = lp.didBlockInteraction();
                boolean isBlockingInteractionBelow = lp.isBlockingInteractionBelow(this, child);
                boolean isBlocking = isBlockingInteractionBelow;
                newBlock = isBlockingInteractionBelow && !wasBlocking;
                if (isBlocking && !newBlock) {
                    topmostChildList.clear();
                    return intercepted;
                }
            } else if (behavior != null) {
                if (cancelEvent == null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    cancelEvent = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                }
                switch (type) {
                    case 0:
                        boolean onInterceptTouchEvent = b.onInterceptTouchEvent(this, child, cancelEvent);
                        break;
                    case 1:
                        boolean onTouchEvent = b.onTouchEvent(this, child, cancelEvent);
                        break;
                }
            }
        }
        topmostChildList.clear();
        return intercepted;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        int action = actionMasked;
        if (actionMasked == 0) {
            resetTouchBehaviors();
        }
        boolean intercepted = performIntercept(ev, 0);
        if (action == 1 || action == 3) {
            resetTouchBehaviors();
        }
        return intercepted;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0071, code lost:
        if (r18 == false) goto L_0x0031;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r48) {
        /*
            r47 = this;
            r9 = r47
            r10 = r48
            r11 = r9
            r12 = r10
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = r10
            int r18 = android.support.p000v4.view.MotionEventCompat.getActionMasked(r17)
            r19 = r18
            android.view.View r15 = r9.mBehaviorTouchView
            r20 = 0
            r0 = r20
            if (r15 == r0) goto L_0x005b
        L_0x001b:
            android.view.View r15 = r9.mBehaviorTouchView
            android.view.ViewGroup$LayoutParams r15 = r15.getLayoutParams()
            android.support.design.widget.CoordinatorLayout$LayoutParams r15 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r15
            r25 = r15
            android.support.design.widget.CoordinatorLayout$Behavior r15 = r15.getBehavior()
            r26 = r15
            r20 = 0
            r0 = r20
            if (r15 != r0) goto L_0x0074
        L_0x0031:
            android.view.View r15 = r9.mBehaviorTouchView
            r20 = 0
            r0 = r20
            if (r15 == r0) goto L_0x008f
            r24 = 0
            r0 = r24
            if (r14 != r0) goto L_0x00a0
        L_0x003f:
            r24 = 0
            r0 = r24
            if (r13 == r0) goto L_0x00cb
        L_0x0045:
            r20 = 0
            r0 = r16
            r1 = r20
            if (r0 != r1) goto L_0x00d5
        L_0x004d:
            r24 = 1
            r0 = r19
            r1 = r24
            if (r0 != r1) goto L_0x00da
        L_0x0055:
            r9.resetTouchBehaviors()
        L_0x0058:
            r46 = r13
            return r46
        L_0x005b:
            r21 = 1
            r22 = r10
            r0 = r22
            r1 = r21
            boolean r23 = r9.performIntercept(r0, r1)
            r18 = r23
            r14 = r18
            r24 = 0
            r0 = r18
            r1 = r24
            if (r0 != r1) goto L_0x001b
            goto L_0x0031
        L_0x0074:
            android.view.View r0 = r9.mBehaviorTouchView
            r27 = r0
            r28 = r9
            r29 = r27
            r30 = r10
            r0 = r26
            r1 = r28
            r2 = r29
            r3 = r30
            boolean r31 = r0.onTouchEvent(r1, r2, r3)
            r18 = r31
            r13 = r18
            goto L_0x0031
        L_0x008f:
            r18 = r13
            r32 = r9
            r33 = r10
            boolean r34 = super.onTouchEvent(r33)
            r35 = r34
            r18 = r18 | r35
            r13 = r18
            goto L_0x003f
        L_0x00a0:
            r15 = 0
            long r36 = android.os.SystemClock.uptimeMillis()
            r38 = r36
            r21 = 3
            r40 = 0
            r41 = 0
            r42 = 0
            r0 = r36
            r2 = r38
            r4 = r21
            r5 = r40
            r6 = r41
            r7 = r42
            android.view.MotionEvent r15 = android.view.MotionEvent.obtain(r0, r2, r4, r5, r6, r7)
            r16 = r15
            r43 = r9
            r44 = r16
            boolean r45 = super.onTouchEvent(r44)
            goto L_0x003f
        L_0x00cb:
            r24 = 0
            r0 = r19
            r1 = r24
            if (r0 == r1) goto L_0x0045
            goto L_0x0045
        L_0x00d5:
            r16.recycle()
            goto L_0x004d
        L_0x00da:
            r24 = 3
            r0 = r19
            r1 = r24
            if (r0 == r1) goto L_0x0055
            goto L_0x0058
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        boolean disallowIntercept = z;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        if (disallowIntercept && !this.mDisallowInterceptReset) {
            resetTouchBehaviors();
            this.mDisallowInterceptReset = true;
        }
    }

    private int getKeyline(int i) {
        int index = i;
        int i2 = index;
        if (this.mKeylines == null) {
            int e = Log.e(TAG, "No keylines defined for " + this + " - attempted index lookup " + index);
            return 0;
        } else if (index >= 0 && index < this.mKeylines.length) {
            return this.mKeylines[index];
        } else {
            int e2 = Log.e(TAG, "Keyline index " + index + " out of range for " + this);
            return 0;
        }
    }

    static Behavior parseBehavior(Context context, AttributeSet attributeSet, String str) {
        String fullName;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        String name = str;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        String str2 = name;
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        if (name.startsWith(".")) {
            fullName = context2.getPackageName() + name;
        } else if (name.indexOf(46) < 0) {
            fullName = TextUtils.isEmpty(WIDGET_PACKAGE_NAME) ? name : WIDGET_PACKAGE_NAME + '.' + name;
        } else {
            fullName = name;
        }
        try {
            Map map = (Map) sConstructors.get();
            Map map2 = map;
            if (map == null) {
                map2 = new HashMap();
                sConstructors.set(map2);
            }
            Constructor constructor = (Constructor) map2.get(fullName);
            Constructor constructor2 = constructor;
            if (constructor == null) {
                Class cls = Class.forName(fullName, true, context2.getClassLoader());
                Class cls2 = cls;
                Constructor constructor3 = cls.getConstructor(CONSTRUCTOR_PARAMS);
                constructor2 = constructor3;
                constructor3.setAccessible(true);
                Object put = map2.put(fullName, constructor2);
            }
            return (Behavior) constructor2.newInstance(new Object[]{context2, attrs});
        } catch (Exception e) {
            Exception exc = e;
            throw new RuntimeException("Could not inflate Behavior subclass " + fullName, e);
        }
    }

    /* access modifiers changed from: 0000 */
    public LayoutParams getResolvedLayoutParams(View view) {
        View child = view;
        View view2 = child;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        LayoutParams result = layoutParams;
        if (!layoutParams.mBehaviorResolved) {
            DefaultBehavior defaultBehavior = null;
            for (Class cls = child.getClass(); cls != null; cls = cls.getSuperclass()) {
                DefaultBehavior defaultBehavior2 = (DefaultBehavior) cls.getAnnotation(DefaultBehavior.class);
                defaultBehavior = defaultBehavior2;
                if (defaultBehavior2 != null) {
                    break;
                }
            }
            if (defaultBehavior != null) {
                try {
                    result.setBehavior((Behavior) defaultBehavior.value().newInstance());
                } catch (Exception e) {
                    Exception exc = e;
                    int e2 = Log.e(TAG, "Default behavior class " + defaultBehavior.value().getName() + " could not be instantiated. Did you forget a default constructor?", e);
                }
            }
            result.mBehaviorResolved = true;
        }
        return result;
    }

    private void prepareChildren() {
        this.mDependencySortedChildren.clear();
        this.mChildDag.clear();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            LayoutParams resolvedLayoutParams = getResolvedLayoutParams(view);
            LayoutParams layoutParams = resolvedLayoutParams;
            View findAnchorView = resolvedLayoutParams.findAnchorView(this, view);
            this.mChildDag.addNode(view);
            for (int j = 0; j < count; j++) {
                if (j != i) {
                    View other = getChildAt(j);
                    LayoutParams resolvedLayoutParams2 = getResolvedLayoutParams(other);
                    LayoutParams layoutParams2 = resolvedLayoutParams2;
                    if (resolvedLayoutParams2.dependsOn(this, other, view)) {
                        if (!this.mChildDag.contains(other)) {
                            this.mChildDag.addNode(other);
                        }
                        this.mChildDag.addEdge(view, other);
                    }
                }
            }
        }
        boolean addAll = this.mDependencySortedChildren.addAll(this.mChildDag.getSortedList());
        Collections.reverse(this.mDependencySortedChildren);
    }

    /* access modifiers changed from: 0000 */
    public void getDescendantRect(View view, Rect rect) {
        View descendant = view;
        Rect out = rect;
        View view2 = descendant;
        Rect rect2 = out;
        ViewGroupUtils.getDescendantRect(this, descendant, out);
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingLeft() + getPaddingRight());
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingTop() + getPaddingBottom());
    }

    public void onMeasureChild(View view, int i, int i2, int i3, int i4) {
        View child = view;
        int parentWidthMeasureSpec = i;
        int widthUsed = i2;
        int parentHeightMeasureSpec = i3;
        int heightUsed = i4;
        View view2 = child;
        int i5 = parentWidthMeasureSpec;
        int i6 = widthUsed;
        int i7 = parentHeightMeasureSpec;
        int i8 = heightUsed;
        measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x025c, code lost:
        if (r49.onMeasureChild(r7, r38, r42, r40, r43, 0) != false) goto L_0x0112;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r60, int r61) {
        /*
            r59 = this;
            r7 = r59
            r8 = r60
            r9 = r61
            r10 = r7
            r11 = r8
            r12 = r9
            r7.prepareChildren()
            r7.ensurePreDrawListener()
            int r13 = r7.getPaddingLeft()
            r14 = r13
            int r13 = r7.getPaddingTop()
            r15 = r13
            int r13 = r7.getPaddingRight()
            r16 = r13
            int r13 = r7.getPaddingBottom()
            r17 = r13
            r18 = r7
            int r13 = android.support.p000v4.view.ViewCompat.getLayoutDirection(r18)
            r19 = r13
            r20 = 1
            r0 = r20
            if (r13 == r0) goto L_0x00a5
            r13 = 0
        L_0x0034:
            r21 = r13
            int r13 = android.view.View.MeasureSpec.getMode(r8)
            r22 = r13
            int r13 = android.view.View.MeasureSpec.getSize(r8)
            r23 = r13
            int r13 = android.view.View.MeasureSpec.getMode(r9)
            r24 = r13
            int r13 = android.view.View.MeasureSpec.getSize(r9)
            r25 = r13
            int r13 = r14 + r16
            r26 = r13
            int r13 = r15 + r17
            r27 = r13
            int r13 = r7.getSuggestedMinimumWidth()
            r28 = r13
            int r13 = r7.getSuggestedMinimumHeight()
            r29 = r13
            r30 = 0
            android.support.v4.view.WindowInsetsCompat r0 = r7.mLastInsets
            r31 = r0
            r32 = 0
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x00a7
        L_0x0070:
            r13 = 0
        L_0x0071:
            r35 = r13
            java.util.List<android.view.View> r0 = r7.mDependencySortedChildren
            r31 = r0
            int r13 = r31.size()
            r36 = r13
            r37 = 0
        L_0x007f:
            r0 = r37
            r1 = r36
            if (r0 < r1) goto L_0x00b8
            r20 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r56 = r30 & r20
            r0 = r28
            r1 = r56
            int r13 = android.support.p000v4.view.ViewCompat.resolveSizeAndState(r0, r8, r1)
            r37 = r13
            int r56 = r30 << 16
            r0 = r29
            r1 = r56
            int r13 = android.support.p000v4.view.ViewCompat.resolveSizeAndState(r0, r9, r1)
            r58 = r13
            r0 = r37
            r7.setMeasuredDimension(r0, r13)
            return
        L_0x00a5:
            r13 = 1
            goto L_0x0034
        L_0x00a7:
            r33 = r7
            boolean r34 = android.support.p000v4.view.ViewCompat.getFitsSystemWindows(r33)
            r13 = r34
            r20 = 0
            r0 = r20
            if (r13 != r0) goto L_0x00b6
            goto L_0x0070
        L_0x00b6:
            r13 = 1
            goto L_0x0071
        L_0x00b8:
            java.util.List<android.view.View> r0 = r7.mDependencySortedChildren
            r31 = r0
            r0 = r31
            r1 = r37
            java.lang.Object r31 = r0.get(r1)
            android.view.View r31 = (android.view.View) r31
            r38 = r31
            int r13 = r31.getVisibility()
            r20 = 8
            r0 = r20
            if (r13 == r0) goto L_0x0166
            android.view.ViewGroup$LayoutParams r31 = r38.getLayoutParams()
            android.support.design.widget.CoordinatorLayout$LayoutParams r31 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r31
            r39 = r31
            r40 = 0
            r0 = r31
            int r13 = r0.keyline
            r20 = 0
            r0 = r20
            if (r13 >= r0) goto L_0x0167
        L_0x00e6:
            r42 = r8
            r43 = r9
            r20 = 0
            r0 = r35
            r1 = r20
            if (r0 != r1) goto L_0x01f0
        L_0x00f2:
            android.support.design.widget.CoordinatorLayout$Behavior r31 = r39.getBehavior()
            r49 = r31
            r32 = 0
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x023e
        L_0x0100:
            r54 = 0
            r55 = r38
            r0 = r7
            r1 = r55
            r2 = r42
            r3 = r40
            r4 = r43
            r5 = r54
            r0.onMeasureChild(r1, r2, r3, r4, r5)
        L_0x0112:
            r13 = r28
            int r56 = r38.getMeasuredWidth()
            int r41 = r26 + r56
            r0 = r39
            int r0 = r0.leftMargin
            r56 = r0
            int r41 = r41 + r56
            r0 = r39
            int r0 = r0.rightMargin
            r56 = r0
            int r41 = r41 + r56
            r0 = r41
            int r13 = java.lang.Math.max(r13, r0)
            r28 = r13
            r13 = r29
            int r56 = r38.getMeasuredHeight()
            int r41 = r27 + r56
            r0 = r39
            int r0 = r0.topMargin
            r56 = r0
            int r41 = r41 + r56
            r0 = r39
            int r0 = r0.bottomMargin
            r56 = r0
            int r41 = r41 + r56
            r0 = r41
            int r13 = java.lang.Math.max(r13, r0)
            r29 = r13
            r13 = r30
            r57 = r38
            int r41 = android.support.p000v4.view.ViewCompat.getMeasuredState(r57)
            r0 = r41
            int r13 = android.support.p000v4.view.ViewCompat.combineMeasuredStates(r13, r0)
            r30 = r13
        L_0x0162:
            int r37 = r37 + 1
            goto L_0x007f
        L_0x0166:
            goto L_0x0162
        L_0x0167:
            r20 = 0
            r0 = r22
            r1 = r20
            if (r0 != r1) goto L_0x0171
            goto L_0x00e6
        L_0x0171:
            r0 = r39
            int r0 = r0.keyline
            r41 = r0
            r0 = r41
            int r13 = r7.getKeyline(r0)
            r42 = r13
            r0 = r39
            int r13 = r0.gravity
            int r13 = resolveKeylineGravity(r13)
            r0 = r19
            int r13 = android.support.p000v4.view.GravityCompat.getAbsoluteGravity(r13, r0)
            r13 = r13 & 7
            r43 = r13
            r20 = 3
            r0 = r20
            if (r13 == r0) goto L_0x01b1
        L_0x0197:
            r20 = 5
            r0 = r43
            r1 = r20
            if (r0 == r1) goto L_0x01c8
        L_0x019f:
            r20 = 5
            r0 = r43
            r1 = r20
            if (r0 == r1) goto L_0x01d1
        L_0x01a7:
            r20 = 3
            r0 = r43
            r1 = r20
            if (r0 == r1) goto L_0x01e6
            goto L_0x00e6
        L_0x01b1:
            r20 = 0
            r0 = r21
            r1 = r20
            if (r0 != r1) goto L_0x0197
        L_0x01b9:
            r13 = 0
            int r41 = r23 - r16
            int r41 = r41 - r42
            r0 = r41
            int r13 = java.lang.Math.max(r13, r0)
            r40 = r13
            goto L_0x00e6
        L_0x01c8:
            r20 = 0
            r0 = r21
            r1 = r20
            if (r0 != r1) goto L_0x01b9
            goto L_0x019f
        L_0x01d1:
            r20 = 0
            r0 = r21
            r1 = r20
            if (r0 != r1) goto L_0x01a7
        L_0x01d9:
            r13 = 0
            int r41 = r42 - r14
            r0 = r41
            int r13 = java.lang.Math.max(r13, r0)
            r40 = r13
            goto L_0x00e6
        L_0x01e6:
            r20 = 0
            r0 = r21
            r1 = r20
            if (r0 != r1) goto L_0x01d9
            goto L_0x00e6
        L_0x01f0:
            r44 = r38
            boolean r45 = android.support.p000v4.view.ViewCompat.getFitsSystemWindows(r44)
            r13 = r45
            r20 = 0
            r0 = r20
            if (r13 == r0) goto L_0x0200
            goto L_0x00f2
        L_0x0200:
            android.support.v4.view.WindowInsetsCompat r0 = r7.mLastInsets
            r31 = r0
            int r13 = r31.getSystemWindowInsetLeft()
            android.support.v4.view.WindowInsetsCompat r0 = r7.mLastInsets
            r46 = r0
            int r41 = r46.getSystemWindowInsetRight()
            int r13 = r13 + r41
            r47 = r13
            android.support.v4.view.WindowInsetsCompat r0 = r7.mLastInsets
            r31 = r0
            int r13 = r31.getSystemWindowInsetTop()
            android.support.v4.view.WindowInsetsCompat r0 = r7.mLastInsets
            r46 = r0
            int r41 = r46.getSystemWindowInsetBottom()
            int r13 = r13 + r41
            r48 = r13
            int r13 = r23 - r47
            r0 = r22
            int r13 = android.view.View.MeasureSpec.makeMeasureSpec(r13, r0)
            r42 = r13
            int r13 = r25 - r48
            r0 = r24
            int r13 = android.view.View.MeasureSpec.makeMeasureSpec(r13, r0)
            r43 = r13
            goto L_0x00f2
        L_0x023e:
            r50 = 0
            r51 = r7
            r52 = r38
            r0 = r49
            r1 = r51
            r2 = r52
            r3 = r42
            r4 = r40
            r5 = r43
            r6 = r50
            boolean r53 = r0.onMeasureChild(r1, r2, r3, r4, r5, r6)
            r13 = r53
            r20 = 0
            r0 = r20
            if (r13 == r0) goto L_0x0100
            goto L_0x0112
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.onMeasure(int, int):void");
    }

    private WindowInsetsCompat dispatchApplyWindowInsetsToBehaviors(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat insets = windowInsetsCompat;
        WindowInsetsCompat insets2 = insets;
        if (insets.isConsumed()) {
            return insets;
        }
        int z = getChildCount();
        for (int i = 0; i < z; i++) {
            View childAt = getChildAt(i);
            View child = childAt;
            if (ViewCompat.getFitsSystemWindows(childAt)) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams layoutParams2 = layoutParams;
                Behavior behavior = layoutParams.getBehavior();
                Behavior b = behavior;
                if (behavior != null) {
                    WindowInsetsCompat onApplyWindowInsets = b.onApplyWindowInsets(this, child, insets2);
                    insets2 = onApplyWindowInsets;
                    if (onApplyWindowInsets.isConsumed()) {
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        return insets2;
    }

    public void onLayoutChild(View view, int i) {
        View child = view;
        int layoutDirection = i;
        View view2 = child;
        int i2 = layoutDirection;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        LayoutParams lp = layoutParams;
        if (layoutParams.checkAnchorChanged()) {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        } else if (lp.mAnchorView != null) {
            layoutChildWithAnchor(child, lp.mAnchorView, layoutDirection);
        } else if (lp.keyline < 0) {
            layoutChild(child, layoutDirection);
        } else {
            layoutChildWithKeyline(child, lp.keyline, layoutDirection);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = z;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        int i8 = i4;
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int childCount = this.mDependencySortedChildren.size();
        for (int i9 = 0; i9 < childCount; i9++) {
            View view = (View) this.mDependencySortedChildren.get(i9);
            View child = view;
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams layoutParams2 = layoutParams;
                Behavior behavior = layoutParams.getBehavior();
                Behavior behavior2 = behavior;
                if (behavior != null) {
                    if (behavior2.onLayoutChild(this, child, layoutDirection)) {
                    }
                }
                onLayoutChild(child, layoutDirection);
            }
        }
    }

    public void onDraw(Canvas canvas) {
        Canvas c = canvas;
        Canvas canvas2 = c;
        super.onDraw(c);
        if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
            int inset = this.mLastInsets == null ? 0 : this.mLastInsets.getSystemWindowInsetTop();
            if (inset > 0) {
                this.mStatusBarBackground.setBounds(0, 0, getWidth(), inset);
                this.mStatusBarBackground.draw(c);
            }
        }
    }

    public void setFitsSystemWindows(boolean z) {
        super.setFitsSystemWindows(z);
        setupForInsets();
    }

    /* access modifiers changed from: 0000 */
    public void recordLastChildRect(View view, Rect rect) {
        View child = view;
        Rect r = rect;
        View view2 = child;
        Rect rect2 = r;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        LayoutParams layoutParams2 = layoutParams;
        layoutParams.setLastChildRect(r);
    }

    /* access modifiers changed from: 0000 */
    public void getLastChildRect(View view, Rect rect) {
        View child = view;
        Rect out = rect;
        View view2 = child;
        Rect rect2 = out;
        out.set(((LayoutParams) child.getLayoutParams()).getLastChildRect());
    }

    /* access modifiers changed from: 0000 */
    public void getChildRect(View view, boolean z, Rect rect) {
        View child = view;
        Rect out = rect;
        View view2 = child;
        boolean transform = z;
        Rect rect2 = out;
        if (!child.isLayoutRequested() && child.getVisibility() != 8) {
            if (!transform) {
                out.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
            } else {
                getDescendantRect(child, out);
            }
            return;
        }
        out.setEmpty();
    }

    private void getDesiredAnchoredChildRectWithoutConstraints(View view, int i, Rect rect, Rect rect2, LayoutParams layoutParams, int i2, int i3) {
        int left;
        int top;
        int layoutDirection = i;
        Rect anchorRect = rect;
        Rect out = rect2;
        LayoutParams lp = layoutParams;
        int childWidth = i2;
        int childHeight = i3;
        View view2 = view;
        int i4 = layoutDirection;
        Rect rect3 = anchorRect;
        Rect rect4 = out;
        LayoutParams layoutParams2 = lp;
        int i5 = childWidth;
        int i6 = childHeight;
        int absGravity = GravityCompat.getAbsoluteGravity(resolveAnchoredChildGravity(lp.gravity), layoutDirection);
        int absAnchorGravity = GravityCompat.getAbsoluteGravity(resolveGravity(lp.anchorGravity), layoutDirection);
        int hgrav = absGravity & 7;
        int vgrav = absGravity & 112;
        int anchorVgrav = absAnchorGravity & 112;
        switch (absAnchorGravity & 7) {
            case 1:
                left = anchorRect.left + (anchorRect.width() / 2);
                break;
            case 5:
                left = anchorRect.right;
                break;
            default:
                left = anchorRect.left;
                break;
        }
        switch (anchorVgrav) {
            case 16:
                top = anchorRect.top + (anchorRect.height() / 2);
                break;
            case 80:
                top = anchorRect.bottom;
                break;
            default:
                top = anchorRect.top;
                break;
        }
        switch (hgrav) {
            case 1:
                left -= childWidth / 2;
                break;
            case 5:
                break;
            default:
                left -= childWidth;
                break;
        }
        switch (vgrav) {
            case 16:
                top -= childHeight / 2;
                break;
            case 80:
                break;
            default:
                top -= childHeight;
                break;
        }
        out.set(left, top, left + childWidth, top + childHeight);
    }

    private void constrainChildRect(LayoutParams layoutParams, Rect rect, int i, int i2) {
        LayoutParams lp = layoutParams;
        Rect out = rect;
        int childWidth = i;
        int childHeight = i2;
        LayoutParams layoutParams2 = lp;
        Rect rect2 = out;
        int i3 = childWidth;
        int i4 = childHeight;
        int width = getWidth();
        int height = getHeight();
        int left = Math.max(getPaddingLeft() + lp.leftMargin, Math.min(out.left, ((width - getPaddingRight()) - childWidth) - lp.rightMargin));
        int max = Math.max(getPaddingTop() + lp.topMargin, Math.min(out.top, ((height - getPaddingBottom()) - childHeight) - lp.bottomMargin));
        int i5 = max;
        out.set(left, max, left + childWidth, max + childHeight);
    }

    /* access modifiers changed from: 0000 */
    public void getDesiredAnchoredChildRect(View view, int i, Rect rect, Rect rect2) {
        View child = view;
        int layoutDirection = i;
        Rect anchorRect = rect;
        Rect out = rect2;
        View view2 = child;
        int i2 = layoutDirection;
        Rect rect3 = anchorRect;
        Rect rect4 = out;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childWidth = child.getMeasuredWidth();
        int measuredHeight = child.getMeasuredHeight();
        int i3 = measuredHeight;
        getDesiredAnchoredChildRectWithoutConstraints(child, layoutDirection, anchorRect, out, lp, childWidth, measuredHeight);
        constrainChildRect(lp, out, childWidth, measuredHeight);
    }

    private void layoutChildWithAnchor(View view, View view2, int i) {
        View child = view;
        View anchor = view2;
        int layoutDirection = i;
        View view3 = child;
        View view4 = anchor;
        int i2 = layoutDirection;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        Rect anchorRect = acquireTempRect();
        Rect childRect = acquireTempRect();
        try {
            getDescendantRect(anchor, anchorRect);
            getDesiredAnchoredChildRect(child, layoutDirection, anchorRect, childRect);
            child.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
        } finally {
            releaseTempRect(anchorRect);
            releaseTempRect(childRect);
        }
    }

    private void layoutChildWithKeyline(View view, int i, int i2) {
        View child = view;
        int keyline = i;
        int layoutDirection = i2;
        View view2 = child;
        int keyline2 = keyline;
        int i3 = layoutDirection;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        LayoutParams lp = layoutParams;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(resolveKeylineGravity(layoutParams.gravity), layoutDirection);
        int hgrav = absoluteGravity & 7;
        int vgrav = absoluteGravity & 112;
        int width = getWidth();
        int height = getHeight();
        int childWidth = child.getMeasuredWidth();
        int childHeight = child.getMeasuredHeight();
        if (layoutDirection == 1) {
            keyline2 = width - keyline;
        }
        int left = getKeyline(keyline2) - childWidth;
        int top = 0;
        switch (hgrav) {
            case 1:
                left += childWidth / 2;
                break;
            case 5:
                left += childWidth;
                break;
        }
        switch (vgrav) {
            case 16:
                top = 0 + (childHeight / 2);
                break;
            case 80:
                top = 0 + childHeight;
                break;
        }
        int left2 = Math.max(getPaddingLeft() + lp.leftMargin, Math.min(left, ((width - getPaddingRight()) - childWidth) - lp.rightMargin));
        int max = Math.max(getPaddingTop() + lp.topMargin, Math.min(top, ((height - getPaddingBottom()) - childHeight) - lp.bottomMargin));
        int top2 = max;
        child.layout(left2, max, left2 + childWidth, max + childHeight);
    }

    private void layoutChild(View view, int i) {
        View child = view;
        int layoutDirection = i;
        View view2 = child;
        int i2 = layoutDirection;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        Rect acquireTempRect = acquireTempRect();
        Rect parent = acquireTempRect;
        acquireTempRect.set(getPaddingLeft() + lp.leftMargin, getPaddingTop() + lp.topMargin, (getWidth() - getPaddingRight()) - lp.rightMargin, (getHeight() - getPaddingBottom()) - lp.bottomMargin);
        if (this.mLastInsets != null && ViewCompat.getFitsSystemWindows(this) && !ViewCompat.getFitsSystemWindows(child)) {
            parent.left += this.mLastInsets.getSystemWindowInsetLeft();
            parent.top += this.mLastInsets.getSystemWindowInsetTop();
            parent.right -= this.mLastInsets.getSystemWindowInsetRight();
            parent.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
        }
        Rect out = acquireTempRect();
        GravityCompat.apply(resolveGravity(lp.gravity), child.getMeasuredWidth(), child.getMeasuredHeight(), parent, out, layoutDirection);
        child.layout(out.left, out.top, out.right, out.bottom);
        releaseTempRect(parent);
        releaseTempRect(out);
    }

    private static int resolveGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        return gravity != 0 ? gravity : 8388659;
    }

    private static int resolveKeylineGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        return gravity != 0 ? gravity : 8388661;
    }

    private static int resolveAnchoredChildGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        return gravity != 0 ? gravity : 17;
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        Canvas canvas2 = canvas;
        View child = view;
        long drawingTime = j;
        Canvas canvas3 = canvas2;
        View view2 = child;
        long j2 = drawingTime;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        LayoutParams lp = layoutParams;
        if (layoutParams.mBehavior != null) {
            float scrimOpacity = lp.mBehavior.getScrimOpacity(this, child);
            float scrimAlpha = scrimOpacity;
            if (scrimOpacity > 0.0f) {
                if (this.mScrimPaint == null) {
                    this.mScrimPaint = new Paint();
                }
                this.mScrimPaint.setColor(lp.mBehavior.getScrimColor(this, child));
                this.mScrimPaint.setAlpha(MathUtils.constrain(Math.round(255.0f * scrimAlpha), 0, 255));
                int saved = canvas2.save();
                if (child.isOpaque()) {
                    boolean clipRect = canvas2.clipRect((float) child.getLeft(), (float) child.getTop(), (float) child.getRight(), (float) child.getBottom(), Op.DIFFERENCE);
                }
                canvas2.drawRect((float) getPaddingLeft(), (float) getPaddingTop(), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - getPaddingBottom()), this.mScrimPaint);
                canvas2.restoreToCount(saved);
            }
        }
        return super.drawChild(canvas2, child, drawingTime);
    }

    /* access modifiers changed from: 0000 */
    public final void onChildViewsChanged(int i) {
        boolean handled;
        int type = i;
        int i2 = type;
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int childCount = this.mDependencySortedChildren.size();
        Rect inset = acquireTempRect();
        Rect drawRect = acquireTempRect();
        Rect lastDrawRect = acquireTempRect();
        for (int i3 = 0; i3 < childCount; i3++) {
            View view = (View) this.mDependencySortedChildren.get(i3);
            View child = view;
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            if (type != 0 || child.getVisibility() != 8) {
                for (int j = 0; j < i3; j++) {
                    if (lp.mAnchorDirectChild == ((View) this.mDependencySortedChildren.get(j))) {
                        offsetChildToAnchor(child, layoutDirection);
                    }
                }
                getChildRect(child, true, drawRect);
                if (lp.insetEdge != 0 && !drawRect.isEmpty()) {
                    int absoluteGravity = GravityCompat.getAbsoluteGravity(lp.insetEdge, layoutDirection);
                    int j2 = absoluteGravity;
                    switch (absoluteGravity & 112) {
                        case 48:
                            inset.top = Math.max(inset.top, drawRect.bottom);
                            break;
                        case 80:
                            inset.bottom = Math.max(inset.bottom, getHeight() - drawRect.top);
                            break;
                    }
                    switch (j2 & 7) {
                        case 3:
                            inset.left = Math.max(inset.left, drawRect.right);
                            break;
                        case 5:
                            inset.right = Math.max(inset.right, getWidth() - drawRect.left);
                            break;
                    }
                }
                if (lp.dodgeInsetEdges != 0 && child.getVisibility() == 0) {
                    offsetChildByInset(child, inset, layoutDirection);
                }
                if (type == 0) {
                    getLastChildRect(child, lastDrawRect);
                    if (!lastDrawRect.equals(drawRect)) {
                        recordLastChildRect(child, drawRect);
                    }
                }
                for (int j3 = i3 + 1; j3 < childCount; j3++) {
                    View view2 = (View) this.mDependencySortedChildren.get(j3);
                    View checkChild = view2;
                    LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
                    LayoutParams checkLp = layoutParams;
                    Behavior behavior = layoutParams.getBehavior();
                    Behavior b = behavior;
                    if (behavior != null) {
                        if (b.layoutDependsOn(this, checkChild, child)) {
                            if (type == 0 && checkLp.getChangedAfterNestedScroll()) {
                                checkLp.resetChangedAfterNestedScroll();
                            } else {
                                switch (type) {
                                    case 2:
                                        b.onDependentViewRemoved(this, checkChild, child);
                                        handled = true;
                                        break;
                                    default:
                                        handled = b.onDependentViewChanged(this, checkChild, child);
                                        break;
                                }
                                if (type == 1) {
                                    checkLp.setChangedAfterNestedScroll(handled);
                                }
                            }
                        }
                    }
                }
            }
        }
        releaseTempRect(inset);
        releaseTempRect(drawRect);
        releaseTempRect(lastDrawRect);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0147  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void offsetChildByInset(android.view.View r56, android.graphics.Rect r57, int r58) {
        /*
            r55 = this;
            r5 = r55
            r6 = r56
            r7 = r57
            r8 = r58
            r9 = r5
            r10 = r6
            r11 = r7
            r12 = r8
            r13 = r6
            boolean r14 = android.support.p000v4.view.ViewCompat.isLaidOut(r13)
            r15 = r14
            r16 = 0
            r0 = r16
            if (r15 == r0) goto L_0x0023
            int r15 = r6.getWidth()
            r16 = 0
            r0 = r16
            if (r15 > r0) goto L_0x0024
        L_0x0022:
            return
        L_0x0023:
            return
        L_0x0024:
            int r15 = r6.getHeight()
            r16 = 0
            r0 = r16
            if (r15 <= r0) goto L_0x0022
            android.view.ViewGroup$LayoutParams r17 = r6.getLayoutParams()
            android.support.design.widget.CoordinatorLayout$LayoutParams r17 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r17
            r18 = r17
            android.support.design.widget.CoordinatorLayout$Behavior r17 = r17.getBehavior()
            r19 = r17
            android.graphics.Rect r17 = acquireTempRect()
            r20 = r17
            android.graphics.Rect r17 = acquireTempRect()
            r21 = r17
            r17 = r17
            int r22 = r6.getLeft()
            int r23 = r6.getTop()
            int r24 = r6.getRight()
            int r25 = r6.getBottom()
            r0 = r17
            r1 = r22
            r2 = r23
            r3 = r24
            r4 = r25
            r0.set(r1, r2, r3, r4)
            r26 = 0
            r0 = r19
            r1 = r26
            if (r0 != r1) goto L_0x00cd
        L_0x006f:
            r40 = r21
            r0 = r20
            r1 = r40
            r0.set(r1)
        L_0x0078:
            r41 = r21
            releaseTempRect(r41)
            boolean r42 = r20.isEmpty()
            r15 = r42
            r16 = 0
            r0 = r16
            if (r15 != r0) goto L_0x0147
            r0 = r18
            int r15 = r0.dodgeInsetEdges
            int r15 = android.support.p000v4.view.GravityCompat.getAbsoluteGravity(r15, r8)
            r44 = r15
            r45 = 0
            r15 = r44 & 48
            r16 = 48
            r0 = r16
            if (r15 == r0) goto L_0x014d
        L_0x009d:
            r15 = r44 & 80
            r16 = 80
            r0 = r16
            if (r15 == r0) goto L_0x0181
        L_0x00a5:
            r16 = 0
            r0 = r45
            r1 = r16
            if (r0 == r1) goto L_0x01bd
        L_0x00ad:
            r46 = 0
            r15 = r44 & 3
            r16 = 3
            r0 = r16
            if (r15 == r0) goto L_0x01ca
        L_0x00b7:
            r15 = r44 & 5
            r16 = 5
            r0 = r16
            if (r15 == r0) goto L_0x01fe
        L_0x00bf:
            r16 = 0
            r0 = r46
            r1 = r16
            if (r0 == r1) goto L_0x023a
        L_0x00c7:
            r54 = r20
            releaseTempRect(r54)
            return
        L_0x00cd:
            r27 = r5
            r28 = r6
            r29 = r20
            r0 = r19
            r1 = r27
            r2 = r28
            r3 = r29
            boolean r30 = r0.getInsetDodgeRect(r1, r2, r3)
            r15 = r30
            r16 = 0
            r0 = r16
            if (r15 != r0) goto L_0x00e8
            goto L_0x006f
        L_0x00e8:
            r31 = r20
            r0 = r21
            r1 = r31
            boolean r32 = r0.contains(r1)
            r15 = r32
            r16 = 0
            r0 = r16
            if (r15 == r0) goto L_0x00fc
            goto L_0x0078
        L_0x00fc:
            java.lang.IllegalArgumentException r17 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r33 = new java.lang.StringBuilder
            r33.<init>()
            java.lang.String r34 = "Rect should be within the child's bounds. Rect:"
            r35 = r34
            r0 = r33
            r1 = r35
            java.lang.StringBuilder r33 = r0.append(r1)
            java.lang.String r34 = r20.toShortString()
            r36 = r34
            r0 = r33
            r1 = r36
            java.lang.StringBuilder r33 = r0.append(r1)
            java.lang.String r34 = " | Bounds:"
            r37 = r34
            r0 = r33
            r1 = r37
            java.lang.StringBuilder r33 = r0.append(r1)
            java.lang.String r34 = r21.toShortString()
            r38 = r34
            r0 = r33
            r1 = r38
            java.lang.StringBuilder r33 = r0.append(r1)
            java.lang.String r33 = r33.toString()
            r39 = r33
            r0 = r17
            r1 = r39
            r0.<init>(r1)
            throw r17
        L_0x0147:
            r43 = r20
            releaseTempRect(r43)
            return
        L_0x014d:
            r0 = r20
            int r15 = r0.top
            r0 = r18
            int r0 = r0.topMargin
            r22 = r0
            int r15 = r15 - r22
            r0 = r18
            int r0 = r0.mInsetOffsetY
            r22 = r0
            int r15 = r15 - r22
            r46 = r15
            r15 = r15
            int r0 = r7.top
            r22 = r0
            r0 = r22
            if (r15 < r0) goto L_0x016e
            goto L_0x009d
        L_0x016e:
            int r0 = r7.top
            r23 = r0
            int r23 = r23 - r46
            r47 = r6
            r0 = r47
            r1 = r23
            r5.setInsetOffsetY(r0, r1)
            r45 = 1
            goto L_0x009d
        L_0x0181:
            int r15 = r5.getHeight()
            r0 = r20
            int r0 = r0.bottom
            r22 = r0
            int r15 = r15 - r22
            r0 = r18
            int r0 = r0.bottomMargin
            r22 = r0
            int r15 = r15 - r22
            r0 = r18
            int r0 = r0.mInsetOffsetY
            r22 = r0
            int r15 = r15 + r22
            r46 = r15
            r15 = r15
            int r0 = r7.bottom
            r22 = r0
            r0 = r22
            if (r15 < r0) goto L_0x01aa
            goto L_0x00a5
        L_0x01aa:
            int r0 = r7.bottom
            r24 = r0
            int r23 = r46 - r24
            r48 = r6
            r0 = r48
            r1 = r23
            r5.setInsetOffsetY(r0, r1)
            r45 = 1
            goto L_0x00a5
        L_0x01bd:
            r23 = 0
            r49 = r6
            r0 = r49
            r1 = r23
            r5.setInsetOffsetY(r0, r1)
            goto L_0x00ad
        L_0x01ca:
            r0 = r20
            int r15 = r0.left
            r0 = r18
            int r0 = r0.leftMargin
            r22 = r0
            int r15 = r15 - r22
            r0 = r18
            int r0 = r0.mInsetOffsetX
            r22 = r0
            int r15 = r15 - r22
            r50 = r15
            r15 = r15
            int r0 = r7.left
            r22 = r0
            r0 = r22
            if (r15 < r0) goto L_0x01eb
            goto L_0x00b7
        L_0x01eb:
            int r0 = r7.left
            r23 = r0
            int r23 = r23 - r50
            r51 = r6
            r0 = r51
            r1 = r23
            r5.setInsetOffsetX(r0, r1)
            r46 = 1
            goto L_0x00b7
        L_0x01fe:
            int r15 = r5.getWidth()
            r0 = r20
            int r0 = r0.right
            r22 = r0
            int r15 = r15 - r22
            r0 = r18
            int r0 = r0.rightMargin
            r22 = r0
            int r15 = r15 - r22
            r0 = r18
            int r0 = r0.mInsetOffsetX
            r22 = r0
            int r15 = r15 + r22
            r50 = r15
            r15 = r15
            int r0 = r7.right
            r22 = r0
            r0 = r22
            if (r15 < r0) goto L_0x0227
            goto L_0x00bf
        L_0x0227:
            int r0 = r7.right
            r24 = r0
            int r23 = r50 - r24
            r52 = r6
            r0 = r52
            r1 = r23
            r5.setInsetOffsetX(r0, r1)
            r46 = 1
            goto L_0x00bf
        L_0x023a:
            r23 = 0
            r53 = r6
            r0 = r53
            r1 = r23
            r5.setInsetOffsetX(r0, r1)
            goto L_0x00c7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.offsetChildByInset(android.view.View, android.graphics.Rect, int):void");
    }

    private void setInsetOffsetX(View view, int i) {
        View child = view;
        int offsetX = i;
        View view2 = child;
        int i2 = offsetX;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        LayoutParams lp = layoutParams;
        if (layoutParams.mInsetOffsetX != offsetX) {
            int i3 = offsetX - lp.mInsetOffsetX;
            int i4 = i3;
            ViewCompat.offsetLeftAndRight(child, i3);
            lp.mInsetOffsetX = offsetX;
        }
    }

    private void setInsetOffsetY(View view, int i) {
        View child = view;
        int offsetY = i;
        View view2 = child;
        int i2 = offsetY;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        LayoutParams lp = layoutParams;
        if (layoutParams.mInsetOffsetY != offsetY) {
            int i3 = offsetY - lp.mInsetOffsetY;
            int i4 = i3;
            ViewCompat.offsetTopAndBottom(child, i3);
            lp.mInsetOffsetY = offsetY;
        }
    }

    public void dispatchDependentViewsChanged(View view) {
        View view2 = view;
        View view3 = view2;
        List incomingEdges = this.mChildDag.getIncomingEdges(view2);
        List list = incomingEdges;
        if (incomingEdges != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                View view4 = (View) list.get(i);
                View child = view4;
                LayoutParams layoutParams = (LayoutParams) view4.getLayoutParams();
                LayoutParams layoutParams2 = layoutParams;
                Behavior behavior = layoutParams.getBehavior();
                Behavior b = behavior;
                if (behavior != null) {
                    boolean onDependentViewChanged = b.onDependentViewChanged(this, child, view2);
                }
            }
        }
    }

    @NonNull
    public List<View> getDependencies(@NonNull View view) {
        View child = view;
        View view2 = child;
        List outgoingEdges = this.mChildDag.getOutgoingEdges(child);
        this.mTempDependenciesList.clear();
        if (outgoingEdges != null) {
            boolean addAll = this.mTempDependenciesList.addAll(outgoingEdges);
        }
        return this.mTempDependenciesList;
    }

    @NonNull
    public List<View> getDependents(@NonNull View view) {
        View child = view;
        View view2 = child;
        List incomingEdges = this.mChildDag.getIncomingEdges(child);
        this.mTempDependenciesList.clear();
        if (incomingEdges != null) {
            boolean addAll = this.mTempDependenciesList.addAll(incomingEdges);
        }
        return this.mTempDependenciesList;
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public final List<View> getDependencySortedChildren() {
        prepareChildren();
        return Collections.unmodifiableList(this.mDependencySortedChildren);
    }

    /* access modifiers changed from: 0000 */
    public void ensurePreDrawListener() {
        boolean hasDependencies = false;
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            if (i < childCount) {
                if (hasDependencies(getChildAt(i))) {
                    hasDependencies = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        if (hasDependencies != this.mNeedsPreDrawListener) {
            if (!hasDependencies) {
                removePreDrawListener();
            } else {
                addPreDrawListener();
            }
        }
    }

    private boolean hasDependencies(View view) {
        View child = view;
        View view2 = child;
        return this.mChildDag.hasOutgoingEdges(child);
    }

    /* access modifiers changed from: 0000 */
    public void addPreDrawListener() {
        if (this.mIsAttachedToWindow) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener(this);
            }
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            ViewTreeObserver viewTreeObserver2 = viewTreeObserver;
            viewTreeObserver.addOnPreDrawListener(this.mOnPreDrawListener);
        }
        this.mNeedsPreDrawListener = true;
    }

    /* access modifiers changed from: 0000 */
    public void removePreDrawListener() {
        if (this.mIsAttachedToWindow && this.mOnPreDrawListener != null) {
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            ViewTreeObserver viewTreeObserver2 = viewTreeObserver;
            viewTreeObserver.removeOnPreDrawListener(this.mOnPreDrawListener);
        }
        this.mNeedsPreDrawListener = false;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x00dc, code lost:
        r26 = r19.top;
        r33 = r18.top;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void offsetChildToAnchor(android.view.View r52, int r53) {
        /*
            r51 = this;
            r8 = r51
            r9 = r52
            r10 = r53
            r11 = r8
            r12 = r9
            r13 = r10
            android.view.ViewGroup$LayoutParams r14 = r9.getLayoutParams()
            android.support.design.widget.CoordinatorLayout$LayoutParams r14 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r14
            r15 = r14
            android.view.View r14 = r14.mAnchorView
            r16 = 0
            r0 = r16
            if (r14 != r0) goto L_0x0019
        L_0x0018:
            return
        L_0x0019:
            android.graphics.Rect r14 = acquireTempRect()
            r17 = r14
            android.graphics.Rect r14 = acquireTempRect()
            r18 = r14
            android.graphics.Rect r14 = acquireTempRect()
            r19 = r14
            android.view.View r0 = r15.mAnchorView
            r20 = r0
            r21 = r20
            r22 = r17
            r0 = r21
            r1 = r22
            r8.getDescendantRect(r0, r1)
            r23 = r9
            r24 = 0
            r25 = r18
            r0 = r23
            r1 = r24
            r2 = r25
            r8.getChildRect(r0, r1, r2)
            int r26 = r9.getMeasuredWidth()
            r27 = r26
            int r26 = r9.getMeasuredHeight()
            r28 = r26
            r29 = r9
            r30 = r17
            r31 = r19
            r32 = r15
            r0 = r8
            r1 = r29
            r2 = r10
            r3 = r30
            r4 = r31
            r5 = r32
            r6 = r27
            r7 = r26
            r0.getDesiredAnchoredChildRectWithoutConstraints(r1, r2, r3, r4, r5, r6, r7)
            r0 = r19
            int r0 = r0.left
            r26 = r0
            r0 = r18
            int r0 = r0.left
            r33 = r0
            r0 = r26
            r1 = r33
            if (r0 == r1) goto L_0x00dc
        L_0x0080:
            r26 = 1
        L_0x0082:
            r34 = r26
            r35 = r15
            r36 = r19
            r0 = r35
            r1 = r36
            r2 = r27
            r3 = r28
            r8.constrainChildRect(r0, r1, r2, r3)
            r0 = r19
            int r0 = r0.left
            r26 = r0
            r0 = r18
            int r0 = r0.left
            r33 = r0
            int r26 = r26 - r33
            r37 = r26
            r0 = r19
            int r0 = r0.top
            r26 = r0
            r0 = r18
            int r0 = r0.top
            r33 = r0
            int r26 = r26 - r33
            r38 = r26
            r39 = 0
            r0 = r37
            r1 = r39
            if (r0 != r1) goto L_0x00f1
        L_0x00bb:
            r39 = 0
            r0 = r38
            r1 = r39
            if (r0 != r1) goto L_0x00fb
        L_0x00c3:
            r39 = 0
            r0 = r34
            r1 = r39
            if (r0 != r1) goto L_0x0105
        L_0x00cb:
            r48 = r17
            releaseTempRect(r48)
            r49 = r18
            releaseTempRect(r49)
            r50 = r19
            releaseTempRect(r50)
            goto L_0x0018
        L_0x00dc:
            r0 = r19
            int r0 = r0.top
            r26 = r0
            r0 = r18
            int r0 = r0.top
            r33 = r0
            r0 = r26
            r1 = r33
            if (r0 != r1) goto L_0x0080
            r26 = 0
            goto L_0x0082
        L_0x00f1:
            r40 = r9
            r0 = r40
            r1 = r37
            android.support.p000v4.view.ViewCompat.offsetLeftAndRight(r0, r1)
            goto L_0x00bb
        L_0x00fb:
            r41 = r9
            r0 = r41
            r1 = r38
            android.support.p000v4.view.ViewCompat.offsetTopAndBottom(r0, r1)
            goto L_0x00c3
        L_0x0105:
            android.support.design.widget.CoordinatorLayout$Behavior r14 = r15.getBehavior()
            r42 = r14
            r16 = 0
            r0 = r16
            if (r14 != r0) goto L_0x0112
            goto L_0x00cb
        L_0x0112:
            android.view.View r0 = r15.mAnchorView
            r43 = r0
            r44 = r8
            r45 = r9
            r46 = r43
            r0 = r42
            r1 = r44
            r2 = r45
            r3 = r46
            boolean r47 = r0.onDependentViewChanged(r1, r2, r3)
            goto L_0x00cb
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.offsetChildToAnchor(android.view.View, int):void");
    }

    /* JADX INFO: finally extract failed */
    public boolean isPointInChildBounds(View view, int i, int i2) {
        View child = view;
        int x = i;
        int y = i2;
        View view2 = child;
        int i3 = x;
        int i4 = y;
        Rect r = acquireTempRect();
        getDescendantRect(child, r);
        try {
            boolean contains = r.contains(x, y);
            releaseTempRect(r);
            return contains;
        } catch (Throwable th) {
            releaseTempRect(r);
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public boolean doViewsOverlap(View view, View view2) {
        boolean z;
        View first = view;
        View second = view2;
        View view3 = first;
        View view4 = second;
        if (first.getVisibility() != 0 || second.getVisibility() != 0) {
            return false;
        }
        Rect firstRect = acquireTempRect();
        getChildRect(first, first.getParent() != this, firstRect);
        Rect secondRect = acquireTempRect();
        getChildRect(second, second.getParent() != this, secondRect);
        try {
            if (firstRect.left <= secondRect.right) {
                if (firstRect.top <= secondRect.bottom) {
                    if (firstRect.right >= secondRect.left) {
                        if (firstRect.bottom >= secondRect.top) {
                            z = true;
                            boolean z2 = z;
                            releaseTempRect(firstRect);
                            releaseTempRect(secondRect);
                            return z2;
                        }
                    }
                }
            }
            z = false;
            boolean z22 = z;
            releaseTempRect(firstRect);
            releaseTempRect(secondRect);
            return z22;
        } catch (Throwable th) {
            releaseTempRect(firstRect);
            releaseTempRect(secondRect);
            throw th;
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AttributeSet attrs = attributeSet;
        AttributeSet attributeSet2 = attrs;
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        if (p instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) p);
        }
        if (!(p instanceof MarginLayoutParams)) {
            return new LayoutParams(p);
        }
        return new LayoutParams((MarginLayoutParams) p);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return (p instanceof LayoutParams) && super.checkLayoutParams(p);
    }

    public boolean onStartNestedScroll(View view, View view2, int i) {
        View child = view;
        View target = view2;
        int nestedScrollAxes = i;
        View view3 = child;
        View view4 = target;
        int i2 = nestedScrollAxes;
        boolean handled = false;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            View view5 = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) view5.getLayoutParams();
                LayoutParams lp = layoutParams;
                Behavior behavior = layoutParams.getBehavior();
                Behavior viewBehavior = behavior;
                if (behavior == null) {
                    lp.acceptNestedScroll(false);
                } else {
                    boolean accepted = viewBehavior.onStartNestedScroll(this, view5, child, target, nestedScrollAxes);
                    handled |= accepted;
                    lp.acceptNestedScroll(accepted);
                }
            }
        }
        return handled;
    }

    public void onNestedScrollAccepted(View view, View view2, int i) {
        View child = view;
        View target = view2;
        int nestedScrollAxes = i;
        View view3 = child;
        View view4 = target;
        int i2 = nestedScrollAxes;
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
        this.mNestedScrollingDirectChild = child;
        this.mNestedScrollingTarget = target;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            View view5 = childAt;
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            LayoutParams lp = layoutParams;
            if (layoutParams.isNestedScrollAccepted()) {
                Behavior behavior = lp.getBehavior();
                Behavior viewBehavior = behavior;
                if (behavior != null) {
                    viewBehavior.onNestedScrollAccepted(this, view5, child, target, nestedScrollAxes);
                }
            }
        }
    }

    public void onStopNestedScroll(View view) {
        View target = view;
        View view2 = target;
        this.mNestedScrollingParentHelper.onStopNestedScroll(target);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            View view3 = childAt;
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            LayoutParams lp = layoutParams;
            if (layoutParams.isNestedScrollAccepted()) {
                Behavior behavior = lp.getBehavior();
                Behavior viewBehavior = behavior;
                if (behavior != null) {
                    viewBehavior.onStopNestedScroll(this, view3, target);
                }
                lp.resetNestedScroll();
                lp.resetChangedAfterNestedScroll();
            }
        }
        this.mNestedScrollingDirectChild = null;
        this.mNestedScrollingTarget = null;
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        View target = view;
        int dxConsumed = i;
        int dyConsumed = i2;
        int dxUnconsumed = i3;
        int dyUnconsumed = i4;
        View view2 = target;
        int i5 = dxConsumed;
        int i6 = dyConsumed;
        int i7 = dxUnconsumed;
        int i8 = dyUnconsumed;
        int childCount = getChildCount();
        boolean accepted = false;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            View view3 = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.isNestedScrollAccepted()) {
                    Behavior behavior = lp.getBehavior();
                    Behavior viewBehavior = behavior;
                    if (behavior != null) {
                        viewBehavior.onNestedScroll(this, view3, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
                        accepted = true;
                    }
                }
            }
        }
        if (accepted) {
            onChildViewsChanged(1);
        }
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        int max;
        int max2;
        View target = view;
        int dx = i;
        int dy = i2;
        int[] consumed = iArr;
        View view2 = target;
        int i3 = dx;
        int i4 = dy;
        int[] iArr2 = consumed;
        int xConsumed = 0;
        int yConsumed = 0;
        boolean accepted = false;
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            View view3 = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.isNestedScrollAccepted()) {
                    Behavior behavior = lp.getBehavior();
                    Behavior viewBehavior = behavior;
                    if (behavior != null) {
                        int[] iArr3 = this.mTempIntPair;
                        this.mTempIntPair[1] = 0;
                        iArr3[0] = 0;
                        viewBehavior.onNestedPreScroll(this, view3, target, dx, dy, this.mTempIntPair);
                        if (dx <= 0) {
                            max = Math.min(xConsumed, this.mTempIntPair[0]);
                        } else {
                            max = Math.max(xConsumed, this.mTempIntPair[0]);
                        }
                        xConsumed = max;
                        if (dy <= 0) {
                            max2 = Math.min(yConsumed, this.mTempIntPair[1]);
                        } else {
                            max2 = Math.max(yConsumed, this.mTempIntPair[1]);
                        }
                        yConsumed = max2;
                        accepted = true;
                    }
                }
            }
        }
        consumed[0] = xConsumed;
        consumed[1] = yConsumed;
        if (accepted) {
            onChildViewsChanged(1);
        }
    }

    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        View target = view;
        float velocityX = f;
        float velocityY = f2;
        View view2 = target;
        float f3 = velocityX;
        float f4 = velocityY;
        boolean consumed = z;
        boolean handled = false;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            View view3 = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.isNestedScrollAccepted()) {
                    Behavior behavior = lp.getBehavior();
                    Behavior viewBehavior = behavior;
                    if (behavior != null) {
                        handled |= viewBehavior.onNestedFling(this, view3, target, velocityX, velocityY, consumed);
                    }
                }
            }
        }
        if (handled) {
            onChildViewsChanged(1);
        }
        return handled;
    }

    public boolean onNestedPreFling(View view, float f, float f2) {
        View target = view;
        float velocityX = f;
        float velocityY = f2;
        View view2 = target;
        float f3 = velocityX;
        float f4 = velocityY;
        boolean handled = false;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            View view3 = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.isNestedScrollAccepted()) {
                    Behavior behavior = lp.getBehavior();
                    Behavior viewBehavior = behavior;
                    if (behavior != null) {
                        handled |= viewBehavior.onNestedPreFling(this, view3, target, velocityX, velocityY);
                    }
                }
            }
        }
        return handled;
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            SparseArray<Parcelable> behaviorStates = ss.behaviorStates;
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View childAt = getChildAt(i);
                View child = childAt;
                int id = childAt.getId();
                int childId = id;
                LayoutParams resolvedLayoutParams = getResolvedLayoutParams(child);
                LayoutParams layoutParams = resolvedLayoutParams;
                Behavior behavior = resolvedLayoutParams.getBehavior();
                Behavior b = behavior;
                if (!(id == -1 || behavior == null)) {
                    Parcelable parcelable3 = (Parcelable) behaviorStates.get(childId);
                    Parcelable savedState = parcelable3;
                    if (parcelable3 != null) {
                        b.onRestoreInstanceState(this, child, savedState);
                    }
                }
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        SparseArray sparseArray = new SparseArray();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childAt = getChildAt(i);
            View child = childAt;
            int id = childAt.getId();
            int childId = id;
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            Behavior behavior = layoutParams.getBehavior();
            Behavior b = behavior;
            if (!(id == -1 || behavior == null)) {
                Parcelable onSaveInstanceState = b.onSaveInstanceState(this, child);
                Parcelable state = onSaveInstanceState;
                if (onSaveInstanceState != null) {
                    sparseArray.append(childId, state);
                }
            }
        }
        ss.behaviorStates = sparseArray;
        return ss;
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        View child = view;
        Rect rectangle = rect;
        View view2 = child;
        Rect rect2 = rectangle;
        boolean immediate = z;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        LayoutParams layoutParams2 = layoutParams;
        Behavior behavior = layoutParams.getBehavior();
        Behavior behavior2 = behavior;
        if (behavior != null && behavior2.onRequestChildRectangleOnScreen(this, child, rectangle, immediate)) {
            return true;
        }
        return super.requestChildRectangleOnScreen(child, rectangle, immediate);
    }

    private void setupForInsets() {
        if (VERSION.SDK_INT >= 21) {
            if (!ViewCompat.getFitsSystemWindows(this)) {
                ViewCompat.setOnApplyWindowInsetsListener(this, null);
            } else {
                if (this.mApplyWindowInsetsListener == null) {
                    this.mApplyWindowInsetsListener = new OnApplyWindowInsetsListener(this) {
                        final /* synthetic */ CoordinatorLayout this$0;

                        {
                            CoordinatorLayout this$02 = r5;
                            CoordinatorLayout coordinatorLayout = this$02;
                            this.this$0 = this$02;
                        }

                        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                            WindowInsetsCompat insets = windowInsetsCompat;
                            View view2 = view;
                            WindowInsetsCompat windowInsetsCompat2 = insets;
                            return this.this$0.setWindowInsets(insets);
                        }
                    };
                }
                ViewCompat.setOnApplyWindowInsetsListener(this, this.mApplyWindowInsetsListener);
                setSystemUiVisibility(1280);
            }
        }
    }
}
