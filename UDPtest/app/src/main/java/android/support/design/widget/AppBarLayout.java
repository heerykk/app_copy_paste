package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.design.C0001R;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.view.AbsSavedState;
import android.support.p000v4.view.OnApplyWindowInsetsListener;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@DefaultBehavior(Behavior.class)
public class AppBarLayout extends LinearLayout {
    private static final int INVALID_SCROLL_RANGE = -1;
    static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
    static final int PENDING_ACTION_COLLAPSED = 2;
    static final int PENDING_ACTION_EXPANDED = 1;
    static final int PENDING_ACTION_NONE = 0;
    private boolean mCollapsed;
    private boolean mCollapsible;
    private int mDownPreScrollRange;
    private int mDownScrollRange;
    private boolean mHaveChildWithInterpolator;
    private WindowInsetsCompat mLastInsets;
    private List<OnOffsetChangedListener> mListeners;
    private int mPendingAction;
    private final int[] mTmpStatesArray;
    private int mTotalScrollRange;

    public static class Behavior extends HeaderBehavior<AppBarLayout> {
        private static final int INVALID_POSITION = -1;
        private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
        private WeakReference<View> mLastNestedScrollingChildRef;
        private ValueAnimatorCompat mOffsetAnimator;
        private int mOffsetDelta;
        private int mOffsetToChildIndexOnLayout = -1;
        private boolean mOffsetToChildIndexOnLayoutIsMinHeight;
        private float mOffsetToChildIndexOnLayoutPerc;
        private DragCallback mOnDragCallback;
        private boolean mSkipNestedPreScroll;
        private boolean mWasNestedFlung;

        public static abstract class DragCallback {
            public abstract boolean canDrag(@NonNull AppBarLayout appBarLayout);

            public DragCallback() {
            }
        }

        protected static class SavedState extends AbsSavedState {
            public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    Parcel source = parcel;
                    ClassLoader loader = classLoader;
                    Parcel parcel2 = source;
                    ClassLoader classLoader2 = loader;
                    return new SavedState(source, loader);
                }

                public SavedState[] newArray(int i) {
                    int size = i;
                    int i2 = size;
                    return new SavedState[size];
                }
            });
            boolean firstVisibleChildAtMinimumHeight;
            int firstVisibleChildIndex;
            float firstVisibleChildPercentageShown;

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                Parcel source = parcel;
                ClassLoader loader = classLoader;
                Parcel parcel2 = source;
                ClassLoader classLoader2 = loader;
                super(source, loader);
                this.firstVisibleChildIndex = source.readInt();
                this.firstVisibleChildPercentageShown = source.readFloat();
                this.firstVisibleChildAtMinimumHeight = source.readByte() != 0;
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
                dest.writeInt(this.firstVisibleChildIndex);
                dest.writeFloat(this.firstVisibleChildPercentageShown);
                dest.writeByte((byte) (!this.firstVisibleChildAtMinimumHeight ? 0 : 1));
            }
        }

        static /* synthetic */ int access$000(Behavior behavior) {
            Behavior x0 = behavior;
            Behavior behavior2 = x0;
            return x0.mOffsetDelta;
        }

        /* access modifiers changed from: 0000 */
        public /* bridge */ /* synthetic */ boolean canDragView(View view) {
            return canDragView((AppBarLayout) view);
        }

        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        /* access modifiers changed from: 0000 */
        public /* bridge */ /* synthetic */ int getMaxDragOffset(View view) {
            return getMaxDragOffset((AppBarLayout) view);
        }

        /* access modifiers changed from: 0000 */
        public /* bridge */ /* synthetic */ int getScrollRangeForDragFling(View view) {
            return getScrollRangeForDragFling((AppBarLayout) view);
        }

        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        /* access modifiers changed from: 0000 */
        public /* bridge */ /* synthetic */ void onFlingFinished(CoordinatorLayout coordinatorLayout, View view) {
            onFlingFinished(coordinatorLayout, (AppBarLayout) view);
        }

        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            return onLayoutChild(coordinatorLayout, (AppBarLayout) view, i);
        }

        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
            return onMeasureChild(coordinatorLayout, (AppBarLayout) view, i, i2, i3, i4);
        }

        public /* bridge */ /* synthetic */ boolean onNestedFling(CoordinatorLayout coordinatorLayout, View view, View view2, float f, float f2, boolean z) {
            return onNestedFling(coordinatorLayout, (AppBarLayout) view, view2, f, f2, z);
        }

        public /* bridge */ /* synthetic */ void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int[] iArr) {
            onNestedPreScroll(coordinatorLayout, (AppBarLayout) view, view2, i, i2, iArr);
        }

        public /* bridge */ /* synthetic */ void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4) {
            onNestedScroll(coordinatorLayout, (AppBarLayout) view, view2, i, i2, i3, i4);
        }

        public /* bridge */ /* synthetic */ void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, View view, Parcelable parcelable) {
            onRestoreInstanceState(coordinatorLayout, (AppBarLayout) view, parcelable);
        }

        public /* bridge */ /* synthetic */ Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, View view) {
            return onSaveInstanceState(coordinatorLayout, (AppBarLayout) view);
        }

        public /* bridge */ /* synthetic */ boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i) {
            return onStartNestedScroll(coordinatorLayout, (AppBarLayout) view, view2, view3, i);
        }

        public /* bridge */ /* synthetic */ void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2) {
            onStopNestedScroll(coordinatorLayout, (AppBarLayout) view, view2);
        }

        /* access modifiers changed from: 0000 */
        public /* bridge */ /* synthetic */ int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
            return setHeaderTopBottomOffset(coordinatorLayout, (AppBarLayout) view, i, i2, i3);
        }

        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            super(context2, attrs);
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i) {
            CoordinatorLayout parent = coordinatorLayout;
            AppBarLayout child = appBarLayout;
            View directTargetChild = view;
            int nestedScrollAxes = i;
            CoordinatorLayout coordinatorLayout2 = parent;
            AppBarLayout appBarLayout2 = child;
            View view3 = directTargetChild;
            View view4 = view2;
            int i2 = nestedScrollAxes;
            boolean started = (nestedScrollAxes & 2) != 0 && child.hasScrollableChildren() && parent.getHeight() - directTargetChild.getHeight() <= child.getHeight();
            if (started && this.mOffsetAnimator != null) {
                this.mOffsetAnimator.cancel();
            }
            this.mLastNestedScrollingChildRef = null;
            return started;
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr) {
            int min;
            int max;
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            AppBarLayout child = appBarLayout;
            int dy = i2;
            int[] consumed = iArr;
            CoordinatorLayout coordinatorLayout3 = coordinatorLayout2;
            AppBarLayout appBarLayout2 = child;
            View view2 = view;
            int i3 = i;
            int i4 = dy;
            int[] iArr2 = consumed;
            if (dy != 0 && !this.mSkipNestedPreScroll) {
                if (dy >= 0) {
                    min = -child.getUpNestedPreScrollRange();
                    max = 0;
                } else {
                    int i5 = -child.getTotalScrollRange();
                    min = i5;
                    max = i5 + child.getDownNestedPreScrollRange();
                }
                consumed[1] = scroll(coordinatorLayout2, child, dy, min, max);
            }
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            AppBarLayout child = appBarLayout;
            int dyUnconsumed = i4;
            CoordinatorLayout coordinatorLayout3 = coordinatorLayout2;
            AppBarLayout appBarLayout2 = child;
            View view2 = view;
            int i5 = i;
            int i6 = i2;
            int i7 = i3;
            int i8 = dyUnconsumed;
            if (dyUnconsumed >= 0) {
                this.mSkipNestedPreScroll = false;
                return;
            }
            int scroll = scroll(coordinatorLayout2, child, dyUnconsumed, -child.getDownNestedScrollRange(), 0);
            this.mSkipNestedPreScroll = true;
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            AppBarLayout abl = appBarLayout;
            View target = view;
            CoordinatorLayout coordinatorLayout3 = coordinatorLayout2;
            AppBarLayout appBarLayout2 = abl;
            View view2 = target;
            if (!this.mWasNestedFlung) {
                snapToChildIfNeeded(coordinatorLayout2, abl);
            }
            this.mSkipNestedPreScroll = false;
            this.mWasNestedFlung = false;
            this.mLastNestedScrollingChildRef = new WeakReference<>(target);
        }

        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, float f, float f2, boolean z) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            AppBarLayout child = appBarLayout;
            float velocityY = f2;
            CoordinatorLayout coordinatorLayout3 = coordinatorLayout2;
            AppBarLayout appBarLayout2 = child;
            View view2 = view;
            float f3 = f;
            float f4 = velocityY;
            boolean flung = false;
            if (!z) {
                flung = fling(coordinatorLayout2, child, -child.getTotalScrollRange(), 0, -velocityY);
            } else if (velocityY < 0.0f) {
                int targetScroll = (-child.getTotalScrollRange()) + child.getDownNestedPreScrollRange();
                if (getTopBottomOffsetForScrollingSibling() < targetScroll) {
                    animateOffsetTo(coordinatorLayout2, child, targetScroll, velocityY);
                    flung = true;
                }
            } else {
                int targetScroll2 = -child.getUpNestedPreScrollRange();
                if (getTopBottomOffsetForScrollingSibling() > targetScroll2) {
                    animateOffsetTo(coordinatorLayout2, child, targetScroll2, velocityY);
                    flung = true;
                }
            }
            this.mWasNestedFlung = flung;
            return flung;
        }

        public void setDragCallback(@Nullable DragCallback dragCallback) {
            DragCallback callback = dragCallback;
            DragCallback dragCallback2 = callback;
            this.mOnDragCallback = callback;
        }

        private void animateOffsetTo(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, float f) {
            int duration;
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            AppBarLayout child = appBarLayout;
            int offset = i;
            float velocity = f;
            CoordinatorLayout coordinatorLayout3 = coordinatorLayout2;
            AppBarLayout appBarLayout2 = child;
            int i2 = offset;
            float f2 = velocity;
            int abs = Math.abs(getTopBottomOffsetForScrollingSibling() - offset);
            int distance = abs;
            float abs2 = Math.abs(velocity);
            float velocity2 = abs2;
            if (abs2 > 0.0f) {
                duration = 3 * Math.round(1000.0f * (((float) distance) / velocity2));
            } else {
                float height = ((float) abs) / ((float) child.getHeight());
                float f3 = height;
                duration = (int) ((height + 1.0f) * 150.0f);
            }
            animateOffsetWithDuration(coordinatorLayout2, child, offset, duration);
        }

        private void animateOffsetWithDuration(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            AppBarLayout child = appBarLayout;
            int offset = i;
            int duration = i2;
            CoordinatorLayout coordinatorLayout3 = coordinatorLayout2;
            AppBarLayout appBarLayout2 = child;
            int i3 = offset;
            int i4 = duration;
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
            int currentOffset = topBottomOffsetForScrollingSibling;
            if (topBottomOffsetForScrollingSibling != offset) {
                if (this.mOffsetAnimator != null) {
                    this.mOffsetAnimator.cancel();
                } else {
                    this.mOffsetAnimator = ViewUtils.createAnimator();
                    this.mOffsetAnimator.setInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
                    ValueAnimatorCompat valueAnimatorCompat = this.mOffsetAnimator;
                    final CoordinatorLayout coordinatorLayout4 = coordinatorLayout2;
                    final AppBarLayout appBarLayout3 = child;
                    C00111 r0 = new AnimatorUpdateListener(this) {
                        final /* synthetic */ Behavior this$0;

                        {
                            Behavior this$02 = r7;
                            CoordinatorLayout coordinatorLayout = r8;
                            AppBarLayout appBarLayout = r9;
                            Behavior behavior = this$02;
                            this.this$0 = this$02;
                        }

                        public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                            ValueAnimatorCompat animator = valueAnimatorCompat;
                            ValueAnimatorCompat valueAnimatorCompat2 = animator;
                            Behavior behavior = this.this$0;
                            CoordinatorLayout coordinatorLayout = coordinatorLayout4;
                            int headerTopBottomOffset = behavior.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout3, animator.getAnimatedIntValue());
                        }
                    };
                    valueAnimatorCompat.addUpdateListener(r0);
                }
                this.mOffsetAnimator.setDuration((long) Math.min(duration, MAX_OFFSET_ANIMATION_DURATION));
                this.mOffsetAnimator.setIntValues(currentOffset, offset);
                this.mOffsetAnimator.start();
                return;
            }
            if (this.mOffsetAnimator != null && this.mOffsetAnimator.isRunning()) {
                this.mOffsetAnimator.cancel();
            }
        }

        private int getChildIndexOnOffset(AppBarLayout appBarLayout, int i) {
            AppBarLayout abl = appBarLayout;
            int offset = i;
            AppBarLayout appBarLayout2 = abl;
            int i2 = offset;
            int count = abl.getChildCount();
            for (int i3 = 0; i3 < count; i3++) {
                View childAt = abl.getChildAt(i3);
                View child = childAt;
                if (childAt.getTop() <= (-offset) && child.getBottom() >= (-offset)) {
                    return i3;
                }
            }
            return -1;
        }

        private void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            AppBarLayout abl = appBarLayout;
            CoordinatorLayout coordinatorLayout3 = coordinatorLayout2;
            AppBarLayout appBarLayout2 = abl;
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
            int offset = topBottomOffsetForScrollingSibling;
            int childIndexOnOffset = getChildIndexOnOffset(abl, topBottomOffsetForScrollingSibling);
            int offsetChildIndex = childIndexOnOffset;
            if (childIndexOnOffset >= 0) {
                View childAt = abl.getChildAt(offsetChildIndex);
                View offsetChild = childAt;
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                LayoutParams layoutParams2 = layoutParams;
                int scrollFlags = layoutParams.getScrollFlags();
                int flags = scrollFlags;
                if ((scrollFlags & 17) == 17) {
                    int snapTop = -offsetChild.getTop();
                    int snapBottom = -offsetChild.getBottom();
                    if (offsetChildIndex == abl.getChildCount() - 1) {
                        snapBottom += abl.getTopInset();
                    }
                    if (checkFlag(flags, 2)) {
                        snapBottom += ViewCompat.getMinimumHeight(offsetChild);
                    } else if (checkFlag(flags, 5)) {
                        int seam = snapBottom + ViewCompat.getMinimumHeight(offsetChild);
                        if (offset >= seam) {
                            snapBottom = seam;
                        } else {
                            snapTop = seam;
                        }
                    }
                    animateOffsetTo(coordinatorLayout2, abl, MathUtils.constrain(offset >= (snapBottom + snapTop) / 2 ? snapTop : snapBottom, -abl.getTotalScrollRange(), 0), 0.0f);
                }
            }
        }

        private static boolean checkFlag(int i, int i2) {
            int flags = i;
            int check = i2;
            int i3 = flags;
            int i4 = check;
            return (flags & check) == check;
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3, int i4) {
            CoordinatorLayout parent = coordinatorLayout;
            AppBarLayout child = appBarLayout;
            int parentWidthMeasureSpec = i;
            int widthUsed = i2;
            int parentHeightMeasureSpec = i3;
            int heightUsed = i4;
            CoordinatorLayout coordinatorLayout2 = parent;
            AppBarLayout appBarLayout2 = child;
            int i5 = parentWidthMeasureSpec;
            int i6 = widthUsed;
            int i7 = parentHeightMeasureSpec;
            int i8 = heightUsed;
            android.support.design.widget.CoordinatorLayout.LayoutParams layoutParams = (android.support.design.widget.CoordinatorLayout.LayoutParams) child.getLayoutParams();
            android.support.design.widget.CoordinatorLayout.LayoutParams layoutParams2 = layoutParams;
            if (layoutParams.height != -2) {
                return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
            }
            parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, MeasureSpec.makeMeasureSpec(0, 0), heightUsed);
            return true;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i) {
            int offset;
            CoordinatorLayout parent = coordinatorLayout;
            AppBarLayout abl = appBarLayout;
            int layoutDirection = i;
            CoordinatorLayout coordinatorLayout2 = parent;
            AppBarLayout appBarLayout2 = abl;
            int i2 = layoutDirection;
            boolean handled = super.onLayoutChild(parent, abl, layoutDirection);
            int pendingAction = abl.getPendingAction();
            int pendingAction2 = pendingAction;
            if (pendingAction != 0) {
                boolean animate = (pendingAction2 & 4) != 0;
                if ((pendingAction2 & 2) != 0) {
                    int offset2 = -abl.getUpNestedPreScrollRange();
                    if (!animate) {
                        int headerTopBottomOffset = setHeaderTopBottomOffset(parent, abl, offset2);
                    } else {
                        animateOffsetTo(parent, abl, offset2, 0.0f);
                    }
                } else if ((pendingAction2 & 1) != 0) {
                    if (!animate) {
                        int headerTopBottomOffset2 = setHeaderTopBottomOffset(parent, abl, 0);
                    } else {
                        animateOffsetTo(parent, abl, 0, 0.0f);
                    }
                }
            } else if (this.mOffsetToChildIndexOnLayout >= 0) {
                View childAt = abl.getChildAt(this.mOffsetToChildIndexOnLayout);
                View child = childAt;
                int offset3 = -childAt.getBottom();
                if (!this.mOffsetToChildIndexOnLayoutIsMinHeight) {
                    offset = offset3 + Math.round(((float) child.getHeight()) * this.mOffsetToChildIndexOnLayoutPerc);
                } else {
                    offset = offset3 + ViewCompat.getMinimumHeight(child);
                }
                boolean topAndBottomOffset = setTopAndBottomOffset(offset);
            }
            abl.resetPendingAction();
            this.mOffsetToChildIndexOnLayout = -1;
            boolean topAndBottomOffset2 = setTopAndBottomOffset(MathUtils.constrain(getTopAndBottomOffset(), -abl.getTotalScrollRange(), 0));
            abl.dispatchOffsetUpdates(getTopAndBottomOffset());
            return handled;
        }

        /* access modifiers changed from: 0000 */
        public boolean canDragView(AppBarLayout appBarLayout) {
            AppBarLayout view = appBarLayout;
            AppBarLayout appBarLayout2 = view;
            if (this.mOnDragCallback != null) {
                return this.mOnDragCallback.canDrag(view);
            }
            if (this.mLastNestedScrollingChildRef == null) {
                return true;
            }
            View view2 = (View) this.mLastNestedScrollingChildRef.get();
            View scrollingView = view2;
            return view2 != null && scrollingView.isShown() && !ViewCompat.canScrollVertically(scrollingView, -1);
        }

        /* access modifiers changed from: 0000 */
        public void onFlingFinished(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            CoordinatorLayout parent = coordinatorLayout;
            AppBarLayout layout = appBarLayout;
            CoordinatorLayout coordinatorLayout2 = parent;
            AppBarLayout appBarLayout2 = layout;
            snapToChildIfNeeded(parent, layout);
        }

        /* access modifiers changed from: 0000 */
        public int getMaxDragOffset(AppBarLayout appBarLayout) {
            AppBarLayout view = appBarLayout;
            AppBarLayout appBarLayout2 = view;
            return -view.getDownNestedScrollRange();
        }

        /* access modifiers changed from: 0000 */
        public int getScrollRangeForDragFling(AppBarLayout appBarLayout) {
            AppBarLayout view = appBarLayout;
            AppBarLayout appBarLayout2 = view;
            return view.getTotalScrollRange();
        }

        /* access modifiers changed from: 0000 */
        public int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            AppBarLayout appBarLayout2 = appBarLayout;
            int newOffset = i;
            int minOffset = i2;
            int maxOffset = i3;
            CoordinatorLayout coordinatorLayout3 = coordinatorLayout2;
            AppBarLayout appBarLayout3 = appBarLayout2;
            int i4 = newOffset;
            int i5 = minOffset;
            int i6 = maxOffset;
            int curOffset = getTopBottomOffsetForScrollingSibling();
            int consumed = 0;
            if (minOffset != 0 && curOffset >= minOffset && curOffset <= maxOffset) {
                int newOffset2 = MathUtils.constrain(newOffset, minOffset, maxOffset);
                if (curOffset != newOffset2) {
                    int interpolatedOffset = !appBarLayout2.hasChildWithInterpolator() ? newOffset2 : interpolateOffset(appBarLayout2, newOffset2);
                    boolean offsetChanged = setTopAndBottomOffset(interpolatedOffset);
                    consumed = curOffset - newOffset2;
                    this.mOffsetDelta = newOffset2 - interpolatedOffset;
                    if (!offsetChanged && appBarLayout2.hasChildWithInterpolator()) {
                        coordinatorLayout2.dispatchDependentViewsChanged(appBarLayout2);
                    }
                    appBarLayout2.dispatchOffsetUpdates(getTopAndBottomOffset());
                    updateAppBarLayoutDrawableState(coordinatorLayout2, appBarLayout2, newOffset2, newOffset2 >= curOffset ? 1 : -1);
                }
            } else {
                this.mOffsetDelta = 0;
            }
            return consumed;
        }

        /* access modifiers changed from: 0000 */
        @VisibleForTesting
        public boolean isOffsetAnimatorRunning() {
            return this.mOffsetAnimator != null && this.mOffsetAnimator.isRunning();
        }

        private int interpolateOffset(AppBarLayout appBarLayout, int i) {
            AppBarLayout layout = appBarLayout;
            int offset = i;
            AppBarLayout appBarLayout2 = layout;
            int i2 = offset;
            int absOffset = Math.abs(offset);
            int i3 = 0;
            int z = layout.getChildCount();
            while (true) {
                if (i3 >= z) {
                    break;
                }
                View childAt = layout.getChildAt(i3);
                View child = childAt;
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                LayoutParams childLp = layoutParams;
                Interpolator scrollInterpolator = layoutParams.getScrollInterpolator();
                Interpolator interpolator = scrollInterpolator;
                if (absOffset < child.getTop() || absOffset > child.getBottom()) {
                    i3++;
                } else if (scrollInterpolator != null) {
                    int childScrollableHeight = 0;
                    int scrollFlags = childLp.getScrollFlags();
                    int flags = scrollFlags;
                    if ((scrollFlags & 1) != 0) {
                        childScrollableHeight = 0 + child.getHeight() + childLp.topMargin + childLp.bottomMargin;
                        if ((flags & 2) != 0) {
                            childScrollableHeight -= ViewCompat.getMinimumHeight(child);
                        }
                    }
                    if (ViewCompat.getFitsSystemWindows(child)) {
                        childScrollableHeight -= layout.getTopInset();
                    }
                    if (childScrollableHeight > 0) {
                        return Integer.signum(offset) * (child.getTop() + Math.round(((float) childScrollableHeight) * interpolator.getInterpolation(((float) (absOffset - child.getTop())) / ((float) childScrollableHeight))));
                    }
                }
            }
            return offset;
        }

        private void updateAppBarLayoutDrawableState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2) {
            CoordinatorLayout parent = coordinatorLayout;
            AppBarLayout layout = appBarLayout;
            int offset = i;
            int direction = i2;
            CoordinatorLayout coordinatorLayout2 = parent;
            AppBarLayout appBarLayout2 = layout;
            int i3 = offset;
            int i4 = direction;
            View appBarChildOnOffset = getAppBarChildOnOffset(layout, offset);
            View child = appBarChildOnOffset;
            if (appBarChildOnOffset != null) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams layoutParams2 = layoutParams;
                int flags = layoutParams.getScrollFlags();
                boolean collapsed = false;
                if ((flags & 1) != 0) {
                    int minHeight = ViewCompat.getMinimumHeight(child);
                    if (direction > 0 && (flags & 12) != 0) {
                        collapsed = (-offset) >= (child.getBottom() - minHeight) - layout.getTopInset();
                    } else if ((flags & 2) != 0) {
                        collapsed = (-offset) >= (child.getBottom() - minHeight) - layout.getTopInset();
                    }
                }
                boolean collapsedState = layout.setCollapsedState(collapsed);
                boolean z = collapsedState;
                if (collapsedState && VERSION.SDK_INT >= 11 && shouldJumpElevationState(parent, layout)) {
                    layout.jumpDrawablesToCurrentState();
                }
            }
        }

        private boolean shouldJumpElevationState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            CoordinatorLayout parent = coordinatorLayout;
            AppBarLayout layout = appBarLayout;
            CoordinatorLayout coordinatorLayout2 = parent;
            AppBarLayout appBarLayout2 = layout;
            List dependents = parent.getDependents(layout);
            List list = dependents;
            int i = 0;
            int size = dependents.size();
            while (i < size) {
                View view = (View) list.get(i);
                View view2 = view;
                android.support.design.widget.CoordinatorLayout.LayoutParams layoutParams = (android.support.design.widget.CoordinatorLayout.LayoutParams) view.getLayoutParams();
                android.support.design.widget.CoordinatorLayout.LayoutParams layoutParams2 = layoutParams;
                android.support.design.widget.CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
                android.support.design.widget.CoordinatorLayout.Behavior behavior2 = behavior;
                if (!(behavior instanceof ScrollingViewBehavior)) {
                    i++;
                } else {
                    return ((ScrollingViewBehavior) behavior2).getOverlayTop() != 0;
                }
            }
            return false;
        }

        private static View getAppBarChildOnOffset(AppBarLayout appBarLayout, int i) {
            AppBarLayout layout = appBarLayout;
            int offset = i;
            AppBarLayout appBarLayout2 = layout;
            int i2 = offset;
            int absOffset = Math.abs(offset);
            int z = layout.getChildCount();
            for (int i3 = 0; i3 < z; i3++) {
                View childAt = layout.getChildAt(i3);
                View view = childAt;
                if (absOffset >= childAt.getTop() && absOffset <= childAt.getBottom()) {
                    return childAt;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public int getTopBottomOffsetForScrollingSibling() {
            return getTopAndBottomOffset() + this.mOffsetDelta;
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            CoordinatorLayout parent = coordinatorLayout;
            AppBarLayout abl = appBarLayout;
            CoordinatorLayout coordinatorLayout2 = parent;
            AppBarLayout appBarLayout2 = abl;
            Parcelable superState = super.onSaveInstanceState(parent, abl);
            int offset = getTopAndBottomOffset();
            int count = abl.getChildCount();
            for (int i = 0; i < count; i++) {
                View childAt = abl.getChildAt(i);
                View child = childAt;
                int visBottom = childAt.getBottom() + offset;
                if (child.getTop() + offset <= 0 && visBottom >= 0) {
                    SavedState savedState = new SavedState(superState);
                    SavedState ss = savedState;
                    savedState.firstVisibleChildIndex = i;
                    ss.firstVisibleChildAtMinimumHeight = visBottom == ViewCompat.getMinimumHeight(child) + abl.getTopInset();
                    ss.firstVisibleChildPercentageShown = ((float) visBottom) / ((float) child.getHeight());
                    return ss;
                }
            }
            return superState;
        }

        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable parcelable) {
            CoordinatorLayout parent = coordinatorLayout;
            AppBarLayout appBarLayout2 = appBarLayout;
            Parcelable state = parcelable;
            CoordinatorLayout coordinatorLayout2 = parent;
            AppBarLayout appBarLayout3 = appBarLayout2;
            Parcelable parcelable2 = state;
            if (!(state instanceof SavedState)) {
                super.onRestoreInstanceState(parent, appBarLayout2, state);
                this.mOffsetToChildIndexOnLayout = -1;
                return;
            }
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(parent, appBarLayout2, ss.getSuperState());
            this.mOffsetToChildIndexOnLayout = ss.firstVisibleChildIndex;
            this.mOffsetToChildIndexOnLayoutPerc = ss.firstVisibleChildPercentageShown;
            this.mOffsetToChildIndexOnLayoutIsMinHeight = ss.firstVisibleChildAtMinimumHeight;
        }
    }

    public static class LayoutParams extends android.widget.LinearLayout.LayoutParams {
        static final int COLLAPSIBLE_FLAGS = 10;
        static final int FLAG_QUICK_RETURN = 5;
        static final int FLAG_SNAP = 17;
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        int mScrollFlags = 1;
        Interpolator mScrollInterpolator;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ScrollFlags {
        }

        public LayoutParams(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            super(width, height);
        }

        public LayoutParams(int i, int i2, float f) {
            int width = i;
            int height = i2;
            float weight = f;
            int i3 = width;
            int i4 = height;
            float f2 = weight;
            super(width, height, weight);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            Context c = context;
            AttributeSet attrs = attributeSet;
            Context context2 = c;
            AttributeSet attributeSet2 = attrs;
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, C0001R.styleable.AppBarLayout_Layout);
            this.mScrollFlags = a.getInt(C0001R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
            if (a.hasValue(C0001R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
                int resourceId = a.getResourceId(C0001R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0);
                int i = resourceId;
                this.mScrollInterpolator = AnimationUtils.loadInterpolator(c, resourceId);
            }
            a.recycle();
        }

        @TargetApi(19)
        @RequiresApi(19)
        public LayoutParams(LayoutParams layoutParams) {
            LayoutParams source = layoutParams;
            LayoutParams layoutParams2 = source;
            super(source);
            this.mScrollFlags = source.mScrollFlags;
            this.mScrollInterpolator = source.mScrollInterpolator;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.view.ViewGroup.LayoutParams p = layoutParams;
            android.view.ViewGroup.LayoutParams layoutParams2 = p;
            super(p);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams source = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = source;
            super(source);
        }

        @TargetApi(19)
        @RequiresApi(19)
        public LayoutParams(android.widget.LinearLayout.LayoutParams layoutParams) {
            android.widget.LinearLayout.LayoutParams source = layoutParams;
            android.widget.LinearLayout.LayoutParams layoutParams2 = source;
            super(source);
        }

        public void setScrollFlags(int i) {
            int flags = i;
            int i2 = flags;
            this.mScrollFlags = flags;
        }

        public int getScrollFlags() {
            return this.mScrollFlags;
        }

        public void setScrollInterpolator(Interpolator interpolator) {
            Interpolator interpolator2 = interpolator;
            Interpolator interpolator3 = interpolator2;
            this.mScrollInterpolator = interpolator2;
        }

        public Interpolator getScrollInterpolator() {
            return this.mScrollInterpolator;
        }

        /* access modifiers changed from: 0000 */
        public boolean isCollapsible() {
            return (this.mScrollFlags & 1) == 1 && (this.mScrollFlags & 10) != 0;
        }
    }

    public interface OnOffsetChangedListener {
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            return super.onLayoutChild(coordinatorLayout, view, i);
        }

        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, view, i, i2, i3, i4);
        }

        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            super(context2, attrs);
            TypedArray a = context2.obtainStyledAttributes(attrs, C0001R.styleable.ScrollingViewBehavior_Layout);
            setOverlayTop(a.getDimensionPixelSize(C0001R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            a.recycle();
        }

        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            View dependency = view2;
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            View view3 = view;
            View view4 = dependency;
            return dependency instanceof AppBarLayout;
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            CoordinatorLayout parent = coordinatorLayout;
            View child = view;
            View dependency = view2;
            CoordinatorLayout coordinatorLayout2 = parent;
            View view3 = child;
            View view4 = dependency;
            offsetChildAsNeeded(parent, child, dependency);
            return false;
        }

        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            CoordinatorLayout parent = coordinatorLayout;
            View child = view;
            Rect rectangle = rect;
            CoordinatorLayout coordinatorLayout2 = parent;
            View view2 = child;
            Rect rect2 = rectangle;
            boolean immediate = z;
            AppBarLayout findFirstDependency = findFirstDependency(parent.getDependencies(child));
            AppBarLayout header = findFirstDependency;
            if (findFirstDependency != null) {
                rectangle.offset(child.getLeft(), child.getTop());
                Rect rect3 = this.mTempRect1;
                Rect parentRect = rect3;
                rect3.set(0, 0, parent.getWidth(), parent.getHeight());
                if (!parentRect.contains(rectangle)) {
                    header.setExpanded(false, !immediate);
                    return true;
                }
            }
            return false;
        }

        private void offsetChildAsNeeded(CoordinatorLayout coordinatorLayout, View view, View view2) {
            View child = view;
            View dependency = view2;
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            View view3 = child;
            View view4 = dependency;
            android.support.design.widget.CoordinatorLayout.Behavior behavior = ((android.support.design.widget.CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
            android.support.design.widget.CoordinatorLayout.Behavior behavior2 = behavior;
            if (behavior instanceof Behavior) {
                ViewCompat.offsetTopAndBottom(child, (((dependency.getBottom() - child.getTop()) + Behavior.access$000((Behavior) behavior2)) + getVerticalLayoutGap()) - getOverlapPixelsForOffset(dependency));
            }
        }

        /* access modifiers changed from: 0000 */
        public float getOverlapRatioForOffset(View view) {
            View header = view;
            View view2 = header;
            if (header instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) header;
                AppBarLayout abl = appBarLayout;
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                int preScrollDown = abl.getDownNestedPreScrollRange();
                int offset = getAppBarLayoutOffset(abl);
                if (preScrollDown != 0 && totalScrollRange + offset <= preScrollDown) {
                    return 0.0f;
                }
                int i = totalScrollRange - preScrollDown;
                int availScrollRange = i;
                if (i != 0) {
                    return 1.0f + (((float) offset) / ((float) availScrollRange));
                }
            }
            return 0.0f;
        }

        private static int getAppBarLayoutOffset(AppBarLayout appBarLayout) {
            AppBarLayout abl = appBarLayout;
            AppBarLayout appBarLayout2 = abl;
            android.support.design.widget.CoordinatorLayout.Behavior behavior = ((android.support.design.widget.CoordinatorLayout.LayoutParams) abl.getLayoutParams()).getBehavior();
            android.support.design.widget.CoordinatorLayout.Behavior behavior2 = behavior;
            if (!(behavior instanceof Behavior)) {
                return 0;
            }
            return ((Behavior) behavior2).getTopBottomOffsetForScrollingSibling();
        }

        /* access modifiers changed from: 0000 */
        public AppBarLayout findFirstDependency(List<View> list) {
            List<View> views = list;
            List<View> list2 = views;
            int z = views.size();
            for (int i = 0; i < z; i++) {
                View view = (View) views.get(i);
                View view2 = view;
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view2;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public int getScrollRange(View view) {
            View v = view;
            View view2 = v;
            if (!(v instanceof AppBarLayout)) {
                return super.getScrollRange(v);
            }
            return ((AppBarLayout) v).getTotalScrollRange();
        }
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        this.mTotalScrollRange = -1;
        this.mDownPreScrollRange = -1;
        this.mDownScrollRange = -1;
        this.mPendingAction = 0;
        this.mTmpStatesArray = new int[2];
        setOrientation(1);
        ThemeUtils.checkAppCompatTheme(context2);
        if (VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.setBoundsViewOutlineProvider(this);
            ViewUtilsLollipop.setStateListAnimatorFromAttrs(this, attrs, 0, C0001R.style.Widget_Design_AppBarLayout);
        }
        TypedArray a = context2.obtainStyledAttributes(attrs, C0001R.styleable.AppBarLayout, 0, C0001R.style.Widget_Design_AppBarLayout);
        ViewCompat.setBackground(this, a.getDrawable(C0001R.styleable.AppBarLayout_android_background));
        if (a.hasValue(C0001R.styleable.AppBarLayout_expanded)) {
            setExpanded(a.getBoolean(C0001R.styleable.AppBarLayout_expanded, false));
        }
        if (VERSION.SDK_INT >= 21) {
            if (a.hasValue(C0001R.styleable.AppBarLayout_elevation)) {
                ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, (float) a.getDimensionPixelSize(C0001R.styleable.AppBarLayout_elevation, 0));
            }
        }
        a.recycle();
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener(this) {
            final /* synthetic */ AppBarLayout this$0;

            {
                AppBarLayout this$02 = r5;
                AppBarLayout appBarLayout = this$02;
                this.this$0 = this$02;
            }

            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                WindowInsetsCompat insets = windowInsetsCompat;
                View view2 = view;
                WindowInsetsCompat windowInsetsCompat2 = insets;
                return this.this$0.onWindowInsetChanged(insets);
            }
        });
    }

    public AppBarLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        OnOffsetChangedListener listener = onOffsetChangedListener;
        OnOffsetChangedListener onOffsetChangedListener2 = listener;
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        if (listener != null && !this.mListeners.contains(listener)) {
            boolean add = this.mListeners.add(listener);
        }
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        OnOffsetChangedListener listener = onOffsetChangedListener;
        OnOffsetChangedListener onOffsetChangedListener2 = listener;
        if (this.mListeners != null && listener != null) {
            boolean remove = this.mListeners.remove(listener);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        invalidateScrollRanges();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int l = i;
        int t = i2;
        int r = i3;
        int b = i4;
        int i5 = l;
        int i6 = t;
        int i7 = r;
        int i8 = b;
        super.onLayout(z, l, t, r, b);
        invalidateScrollRanges();
        this.mHaveChildWithInterpolator = false;
        int i9 = 0;
        int z2 = getChildCount();
        while (true) {
            if (i9 < z2) {
                View childAt = getChildAt(i9);
                View view = childAt;
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                LayoutParams layoutParams2 = layoutParams;
                Interpolator scrollInterpolator = layoutParams.getScrollInterpolator();
                Interpolator interpolator = scrollInterpolator;
                if (scrollInterpolator != null) {
                    this.mHaveChildWithInterpolator = true;
                    break;
                }
                i9++;
            } else {
                break;
            }
        }
        updateCollapsible();
    }

    private void updateCollapsible() {
        boolean haveCollapsibleChild = false;
        int i = 0;
        int z = getChildCount();
        while (true) {
            if (i < z) {
                if (((LayoutParams) getChildAt(i).getLayoutParams()).isCollapsible()) {
                    haveCollapsibleChild = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        boolean collapsibleState = setCollapsibleState(haveCollapsibleChild);
    }

    private void invalidateScrollRanges() {
        this.mTotalScrollRange = -1;
        this.mDownPreScrollRange = -1;
        this.mDownScrollRange = -1;
    }

    public void setOrientation(int i) {
        int orientation = i;
        int i2 = orientation;
        if (orientation == 1) {
            super.setOrientation(orientation);
            return;
        }
        throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
    }

    public void setExpanded(boolean z) {
        setExpanded(z, ViewCompat.isLaidOut(this));
    }

    public void setExpanded(boolean z, boolean z2) {
        this.mPendingAction = (!z ? 2 : 1) | (!z2 ? 0 : 4);
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return p instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
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
        if (VERSION.SDK_INT >= 19 && (p instanceof android.widget.LinearLayout.LayoutParams)) {
            return new LayoutParams((android.widget.LinearLayout.LayoutParams) p);
        }
        if (!(p instanceof MarginLayoutParams)) {
            return new LayoutParams(p);
        }
        return new LayoutParams((MarginLayoutParams) p);
    }

    /* access modifiers changed from: 0000 */
    public boolean hasChildWithInterpolator() {
        return this.mHaveChildWithInterpolator;
    }

    public final int getTotalScrollRange() {
        if (this.mTotalScrollRange != -1) {
            return this.mTotalScrollRange;
        }
        int range = 0;
        int i = 0;
        int z = getChildCount();
        while (true) {
            if (i < z) {
                View childAt = getChildAt(i);
                View child = childAt;
                LayoutParams lp = (LayoutParams) childAt.getLayoutParams();
                int childHeight = child.getMeasuredHeight();
                int i2 = lp.mScrollFlags;
                int flags = i2;
                if ((i2 & 1) != 0) {
                    range += childHeight + lp.topMargin + lp.bottomMargin;
                    if ((flags & 2) != 0) {
                        range -= ViewCompat.getMinimumHeight(child);
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        int max = Math.max(0, range - getTopInset());
        this.mTotalScrollRange = max;
        return max;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasScrollableChildren() {
        return getTotalScrollRange() != 0;
    }

    /* access modifiers changed from: 0000 */
    public int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    /* access modifiers changed from: 0000 */
    public int getDownNestedPreScrollRange() {
        if (this.mDownPreScrollRange != -1) {
            return this.mDownPreScrollRange;
        }
        int range = 0;
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View childAt = getChildAt(i);
            View child = childAt;
            LayoutParams lp = (LayoutParams) childAt.getLayoutParams();
            int childHeight = child.getMeasuredHeight();
            int i2 = lp.mScrollFlags;
            int flags = i2;
            if ((i2 & 5) != 5) {
                if (range > 0) {
                    break;
                }
            } else {
                int range2 = range + lp.topMargin + lp.bottomMargin;
                if ((flags & 8) != 0) {
                    range = range2 + ViewCompat.getMinimumHeight(child);
                } else if ((flags & 2) == 0) {
                    range = range2 + childHeight;
                } else {
                    range = range2 + (childHeight - ViewCompat.getMinimumHeight(child));
                }
            }
        }
        int max = Math.max(0, range);
        this.mDownPreScrollRange = max;
        return max;
    }

    /* access modifiers changed from: 0000 */
    public int getDownNestedScrollRange() {
        if (this.mDownScrollRange != -1) {
            return this.mDownScrollRange;
        }
        int range = 0;
        int i = 0;
        int z = getChildCount();
        while (true) {
            if (i < z) {
                View childAt = getChildAt(i);
                View child = childAt;
                LayoutParams lp = (LayoutParams) childAt.getLayoutParams();
                int measuredHeight = child.getMeasuredHeight();
                int i2 = measuredHeight;
                int childHeight = measuredHeight + lp.topMargin + lp.bottomMargin;
                int i3 = lp.mScrollFlags;
                int flags = i3;
                if ((i3 & 1) != 0) {
                    range += childHeight;
                    if ((flags & 2) != 0) {
                        range -= ViewCompat.getMinimumHeight(child) + getTopInset();
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        int max = Math.max(0, range);
        this.mDownScrollRange = max;
        return max;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOffsetUpdates(int i) {
        int offset = i;
        int i2 = offset;
        if (this.mListeners != null) {
            int z = this.mListeners.size();
            for (int i3 = 0; i3 < z; i3++) {
                OnOffsetChangedListener onOffsetChangedListener = (OnOffsetChangedListener) this.mListeners.get(i3);
                OnOffsetChangedListener listener = onOffsetChangedListener;
                if (onOffsetChangedListener != null) {
                    listener.onOffsetChanged(this, offset);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        int minHeight = minimumHeight;
        if (minimumHeight != 0) {
            return (minHeight * 2) + topInset;
        }
        int childCount = getChildCount();
        int lastChildMinHeight = childCount < 1 ? 0 : ViewCompat.getMinimumHeight(getChildAt(childCount - 1));
        if (lastChildMinHeight == 0) {
            return getHeight() / 3;
        }
        return (lastChildMinHeight * 2) + topInset;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        int extraSpace = i;
        int i2 = extraSpace;
        int[] extraStates = this.mTmpStatesArray;
        int[] states = super.onCreateDrawableState(extraSpace + extraStates.length);
        extraStates[0] = !this.mCollapsible ? -C0001R.attr.state_collapsible : C0001R.attr.state_collapsible;
        extraStates[1] = (this.mCollapsible && this.mCollapsed) ? C0001R.attr.state_collapsed : -C0001R.attr.state_collapsed;
        return mergeDrawableStates(states, extraStates);
    }

    private boolean setCollapsibleState(boolean z) {
        boolean collapsible = z;
        if (this.mCollapsible == collapsible) {
            return false;
        }
        this.mCollapsible = collapsible;
        refreshDrawableState();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean setCollapsedState(boolean z) {
        boolean collapsed = z;
        if (this.mCollapsed == collapsed) {
            return false;
        }
        this.mCollapsed = collapsed;
        refreshDrawableState();
        return true;
    }

    @Deprecated
    public void setTargetElevation(float f) {
        float elevation = f;
        float f2 = elevation;
        if (VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, elevation);
        }
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    /* access modifiers changed from: 0000 */
    public int getPendingAction() {
        return this.mPendingAction;
    }

    /* access modifiers changed from: 0000 */
    public void resetPendingAction() {
        this.mPendingAction = 0;
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public final int getTopInset() {
        return this.mLastInsets == null ? 0 : this.mLastInsets.getSystemWindowInsetTop();
    }

    /* access modifiers changed from: 0000 */
    public WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat insets = windowInsetsCompat;
        WindowInsetsCompat windowInsetsCompat2 = insets;
        WindowInsetsCompat newInsets = null;
        if (ViewCompat.getFitsSystemWindows(this)) {
            newInsets = insets;
        }
        if (!ViewUtils.objectEquals(this.mLastInsets, newInsets)) {
            this.mLastInsets = newInsets;
            invalidateScrollRanges();
        }
        return insets;
    }
}
