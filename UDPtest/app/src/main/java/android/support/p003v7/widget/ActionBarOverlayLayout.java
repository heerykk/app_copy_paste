package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.NestedScrollingParent;
import android.support.p000v4.view.NestedScrollingParentHelper;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewPropertyAnimatorCompat;
import android.support.p000v4.view.ViewPropertyAnimatorListener;
import android.support.p000v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.p000v4.widget.ScrollerCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.MenuPresenter;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window.Callback;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ActionBarOverlayLayout */
public class ActionBarOverlayLayout extends ViewGroup implements DecorContentParent, NestedScrollingParent {
    static final int[] ATTRS;
    private static final String TAG = "ActionBarOverlayLayout";
    private final int ACTION_BAR_ANIMATE_DELAY;
    private int mActionBarHeight;
    ActionBarContainer mActionBarTop;
    private ActionBarVisibilityCallback mActionBarVisibilityCallback;
    private final Runnable mAddActionBarHideOffset;
    boolean mAnimatingForFling;
    private final Rect mBaseContentInsets;
    private final Rect mBaseInnerInsets;
    private ContentFrameLayout mContent;
    private final Rect mContentInsets;
    ViewPropertyAnimatorCompat mCurrentActionBarTopAnimator;
    private DecorToolbar mDecorToolbar;
    private ScrollerCompat mFlingEstimator;
    private boolean mHasNonEmbeddedTabs;
    private boolean mHideOnContentScroll;
    private int mHideOnContentScrollReference;
    private boolean mIgnoreWindowContentOverlay;
    private final Rect mInnerInsets;
    private final Rect mLastBaseContentInsets;
    private final Rect mLastInnerInsets;
    private int mLastSystemUiVisibility;
    private boolean mOverlayMode;
    private final NestedScrollingParentHelper mParentHelper;
    private final Runnable mRemoveActionBarHideOffset;
    final ViewPropertyAnimatorListener mTopAnimatorListener;
    private Drawable mWindowContentOverlay;
    private int mWindowVisibility;

    /* renamed from: android.support.v7.widget.ActionBarOverlayLayout$ActionBarVisibilityCallback */
    public interface ActionBarVisibilityCallback {
        void enableContentAnimations(boolean z);

        void hideForSystem();

        void onContentScrollStarted();

        void onContentScrollStopped();

        void onWindowVisibilityChanged(int i);

        void showForSystem();
    }

    /* renamed from: android.support.v7.widget.ActionBarOverlayLayout$LayoutParams */
    public static class LayoutParams extends MarginLayoutParams {
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
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        this.mWindowVisibility = 0;
        this.mBaseContentInsets = new Rect();
        this.mLastBaseContentInsets = new Rect();
        this.mContentInsets = new Rect();
        this.mBaseInnerInsets = new Rect();
        this.mInnerInsets = new Rect();
        this.mLastInnerInsets = new Rect();
        this.ACTION_BAR_ANIMATE_DELAY = 600;
        this.mTopAnimatorListener = new ViewPropertyAnimatorListenerAdapter(this) {
            final /* synthetic */ ActionBarOverlayLayout this$0;

            {
                ActionBarOverlayLayout this$02 = r5;
                ActionBarOverlayLayout actionBarOverlayLayout = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationEnd(View view) {
                View view2 = view;
                this.this$0.mCurrentActionBarTopAnimator = null;
                this.this$0.mAnimatingForFling = false;
            }

            public void onAnimationCancel(View view) {
                View view2 = view;
                this.this$0.mCurrentActionBarTopAnimator = null;
                this.this$0.mAnimatingForFling = false;
            }
        };
        this.mRemoveActionBarHideOffset = new Runnable(this) {
            final /* synthetic */ ActionBarOverlayLayout this$0;

            {
                ActionBarOverlayLayout this$02 = r5;
                ActionBarOverlayLayout actionBarOverlayLayout = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                this.this$0.haltActionBarHideOffsetAnimations();
                this.this$0.mCurrentActionBarTopAnimator = ViewCompat.animate(this.this$0.mActionBarTop).translationY(0.0f).setListener(this.this$0.mTopAnimatorListener);
            }
        };
        this.mAddActionBarHideOffset = new Runnable(this) {
            final /* synthetic */ ActionBarOverlayLayout this$0;

            {
                ActionBarOverlayLayout this$02 = r5;
                ActionBarOverlayLayout actionBarOverlayLayout = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                this.this$0.haltActionBarHideOffsetAnimations();
                this.this$0.mCurrentActionBarTopAnimator = ViewCompat.animate(this.this$0.mActionBarTop).translationY((float) (-this.this$0.mActionBarTop.getHeight())).setListener(this.this$0.mTopAnimatorListener);
            }
        };
        init(context2);
        this.mParentHelper = new NestedScrollingParentHelper(this);
    }

    static {
        int[] iArr = new int[2];
        iArr[0] = C0268R.attr.actionBarSize;
        iArr[1] = 16842841;
        ATTRS = iArr;
    }

    public ActionBarOverlayLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    private void init(Context context) {
        Context context2 = context;
        Context context3 = context2;
        TypedArray ta = getContext().getTheme().obtainStyledAttributes(ATTRS);
        this.mActionBarHeight = ta.getDimensionPixelSize(0, 0);
        this.mWindowContentOverlay = ta.getDrawable(1);
        setWillNotDraw(this.mWindowContentOverlay == null);
        ta.recycle();
        this.mIgnoreWindowContentOverlay = context2.getApplicationInfo().targetSdkVersion < 19;
        this.mFlingEstimator = ScrollerCompat.create(context2);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        haltActionBarHideOffsetAnimations();
    }

    public void setActionBarVisibilityCallback(ActionBarVisibilityCallback actionBarVisibilityCallback) {
        ActionBarVisibilityCallback cb = actionBarVisibilityCallback;
        ActionBarVisibilityCallback actionBarVisibilityCallback2 = cb;
        this.mActionBarVisibilityCallback = cb;
        if (getWindowToken() != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility);
            if (this.mLastSystemUiVisibility != 0) {
                int i = this.mLastSystemUiVisibility;
                int i2 = i;
                onWindowSystemUiVisibilityChanged(i);
                ViewCompat.requestApplyInsets(this);
            }
        }
    }

    public void setOverlayMode(boolean z) {
        boolean overlayMode = z;
        this.mOverlayMode = overlayMode;
        this.mIgnoreWindowContentOverlay = overlayMode && getContext().getApplicationInfo().targetSdkVersion < 19;
    }

    public boolean isInOverlayMode() {
        return this.mOverlayMode;
    }

    public void setHasNonEmbeddedTabs(boolean z) {
        this.mHasNonEmbeddedTabs = z;
    }

    public void setShowingForActionMode(boolean z) {
        boolean z2 = z;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        Configuration configuration2 = newConfig;
        super.onConfigurationChanged(newConfig);
        init(getContext());
        ViewCompat.requestApplyInsets(this);
    }

    public void onWindowSystemUiVisibilityChanged(int i) {
        int visible = i;
        int i2 = visible;
        if (VERSION.SDK_INT >= 16) {
            super.onWindowSystemUiVisibilityChanged(visible);
        }
        pullChildren();
        int diff = this.mLastSystemUiVisibility ^ visible;
        this.mLastSystemUiVisibility = visible;
        boolean barVisible = (visible & 4) == 0;
        boolean stable = (visible & 256) != 0;
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.enableContentAnimations(!stable);
            if (!barVisible && stable) {
                this.mActionBarVisibilityCallback.hideForSystem();
            } else {
                this.mActionBarVisibilityCallback.showForSystem();
            }
        }
        if ((diff & 256) != 0 && this.mActionBarVisibilityCallback != null) {
            ViewCompat.requestApplyInsets(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int i) {
        int visibility = i;
        int i2 = visibility;
        super.onWindowVisibilityChanged(visibility);
        this.mWindowVisibility = visibility;
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(visibility);
        }
    }

    private boolean applyInsets(View view, Rect rect, boolean z, boolean z2, boolean z3, boolean z4) {
        View view2 = view;
        Rect insets = rect;
        View view3 = view2;
        Rect rect2 = insets;
        boolean top = z2;
        boolean bottom = z3;
        boolean right = z4;
        boolean changed = false;
        LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
        LayoutParams lp = layoutParams;
        if (z && layoutParams.leftMargin != insets.left) {
            changed = true;
            lp.leftMargin = insets.left;
        }
        if (top && lp.topMargin != insets.top) {
            changed = true;
            lp.topMargin = insets.top;
        }
        if (right && lp.rightMargin != insets.right) {
            changed = true;
            lp.rightMargin = insets.right;
        }
        if (bottom && lp.bottomMargin != insets.bottom) {
            changed = true;
            lp.bottomMargin = insets.bottom;
        }
        return changed;
    }

    /* access modifiers changed from: protected */
    public boolean fitSystemWindows(Rect rect) {
        Rect insets = rect;
        Rect rect2 = insets;
        pullChildren();
        int windowSystemUiVisibility = ViewCompat.getWindowSystemUiVisibility(this);
        int i = windowSystemUiVisibility;
        boolean z = (windowSystemUiVisibility & 256) != 0;
        Rect rect3 = insets;
        boolean changed = applyInsets(this.mActionBarTop, insets, true, true, false, true);
        this.mBaseInnerInsets.set(insets);
        ViewUtils.computeFitSystemWindows(this, this.mBaseInnerInsets, this.mBaseContentInsets);
        if (!this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
            changed = true;
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
        }
        if (changed) {
            requestLayout();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AttributeSet attrs = attributeSet;
        AttributeSet attributeSet2 = attrs;
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return new LayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return p instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        pullChildren();
        int topInset = 0;
        measureChildWithMargins(this.mActionBarTop, widthMeasureSpec, 0, heightMeasureSpec, 0);
        LayoutParams layoutParams = (LayoutParams) this.mActionBarTop.getLayoutParams();
        LayoutParams layoutParams2 = layoutParams;
        int maxWidth = Math.max(0, this.mActionBarTop.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
        int maxHeight = Math.max(0, this.mActionBarTop.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        int childState = ViewUtils.combineMeasuredStates(0, ViewCompat.getMeasuredState(this.mActionBarTop));
        int windowSystemUiVisibility = ViewCompat.getWindowSystemUiVisibility(this);
        int i5 = windowSystemUiVisibility;
        boolean stable = (windowSystemUiVisibility & 256) != 0;
        if (stable) {
            topInset = this.mActionBarHeight;
            if (this.mHasNonEmbeddedTabs) {
                View tabContainer = this.mActionBarTop.getTabContainer();
                View view = tabContainer;
                if (tabContainer != null) {
                    topInset += this.mActionBarHeight;
                }
            }
        } else if (this.mActionBarTop.getVisibility() != 8) {
            topInset = this.mActionBarTop.getMeasuredHeight();
        }
        this.mContentInsets.set(this.mBaseContentInsets);
        this.mInnerInsets.set(this.mBaseInnerInsets);
        if (!this.mOverlayMode && !stable) {
            this.mContentInsets.top += topInset;
            this.mContentInsets.bottom += 0;
        } else {
            this.mInnerInsets.top += topInset;
            this.mInnerInsets.bottom += 0;
        }
        boolean applyInsets = applyInsets(this.mContent, this.mContentInsets, true, true, true, true);
        if (!this.mLastInnerInsets.equals(this.mInnerInsets)) {
            this.mLastInnerInsets.set(this.mInnerInsets);
            this.mContent.dispatchFitSystemWindows(this.mInnerInsets);
        }
        measureChildWithMargins(this.mContent, widthMeasureSpec, 0, heightMeasureSpec, 0);
        LayoutParams layoutParams3 = (LayoutParams) this.mContent.getLayoutParams();
        LayoutParams lp = layoutParams3;
        int maxWidth2 = Math.max(maxWidth, this.mContent.getMeasuredWidth() + layoutParams3.leftMargin + layoutParams3.rightMargin);
        int maxHeight2 = Math.max(maxHeight, this.mContent.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin);
        int childState2 = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(this.mContent));
        int maxWidth3 = maxWidth2 + getPaddingLeft() + getPaddingRight();
        int paddingTop = maxHeight2 + getPaddingTop() + getPaddingBottom();
        int maxHeight3 = paddingTop;
        int maxHeight4 = Math.max(paddingTop, getSuggestedMinimumHeight());
        int max = Math.max(maxWidth3, getSuggestedMinimumWidth());
        int maxWidth4 = max;
        setMeasuredDimension(ViewCompat.resolveSizeAndState(max, widthMeasureSpec, childState2), ViewCompat.resolveSizeAndState(maxHeight4, heightMeasureSpec, childState2 << 16));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        boolean z2 = z;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        int count = getChildCount();
        int parentLeft = getPaddingLeft();
        int paddingRight = (right - left) - getPaddingRight();
        int parentTop = getPaddingTop();
        int paddingBottom = (bottom - top) - getPaddingBottom();
        for (int i9 = 0; i9 < count; i9++) {
            View childAt = getChildAt(i9);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int childLeft = parentLeft + lp.leftMargin;
                int i10 = parentTop + lp.topMargin;
                int i11 = i10;
                child.layout(childLeft, i10, childLeft + child.getMeasuredWidth(), i10 + child.getMeasuredHeight());
            }
        }
    }

    public void draw(Canvas canvas) {
        Canvas c = canvas;
        Canvas canvas2 = c;
        super.draw(c);
        if (this.mWindowContentOverlay != null && !this.mIgnoreWindowContentOverlay) {
            int top = this.mActionBarTop.getVisibility() != 0 ? 0 : (int) (((float) this.mActionBarTop.getBottom()) + ViewCompat.getTranslationY(this.mActionBarTop) + 0.5f);
            this.mWindowContentOverlay.setBounds(0, top, getWidth(), top + this.mWindowContentOverlay.getIntrinsicHeight());
            this.mWindowContentOverlay.draw(c);
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public boolean onStartNestedScroll(View view, View view2, int i) {
        int axes = i;
        View view3 = view;
        View view4 = view2;
        int i2 = axes;
        if ((axes & 2) != 0 && this.mActionBarTop.getVisibility() == 0) {
            return this.mHideOnContentScroll;
        }
        return false;
    }

    public void onNestedScrollAccepted(View view, View view2, int i) {
        View child = view;
        View target = view2;
        int axes = i;
        View view3 = child;
        View view4 = target;
        int i2 = axes;
        this.mParentHelper.onNestedScrollAccepted(child, target, axes);
        this.mHideOnContentScrollReference = getActionBarHideOffset();
        haltActionBarHideOffsetAnimations();
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStarted();
        }
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        int dyConsumed = i2;
        View view2 = view;
        int i5 = i;
        int i6 = dyConsumed;
        int i7 = i3;
        int i8 = i4;
        this.mHideOnContentScrollReference += dyConsumed;
        setActionBarHideOffset(this.mHideOnContentScrollReference);
    }

    public void onStopNestedScroll(View view) {
        View view2 = view;
        if (this.mHideOnContentScroll && !this.mAnimatingForFling) {
            if (this.mHideOnContentScrollReference > this.mActionBarTop.getHeight()) {
                postAddActionBarHideOffset();
            } else {
                postRemoveActionBarHideOffset();
            }
        }
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStopped();
        }
    }

    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        float velocityX = f;
        float velocityY = f2;
        View view2 = view;
        float f3 = velocityX;
        float f4 = velocityY;
        boolean consumed = z;
        if (!this.mHideOnContentScroll || !consumed) {
            return false;
        }
        if (!shouldHideActionBarOnFling(velocityX, velocityY)) {
            removeActionBarHideOffset();
        } else {
            addActionBarHideOffset();
        }
        this.mAnimatingForFling = true;
        return true;
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        View view2 = view;
        int i3 = i;
        int i4 = i2;
        int[] iArr2 = iArr;
    }

    public boolean onNestedPreFling(View view, float f, float f2) {
        View view2 = view;
        float f3 = f;
        float f4 = f2;
        return false;
    }

    public int getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes();
    }

    /* access modifiers changed from: 0000 */
    public void pullChildren() {
        if (this.mContent == null) {
            this.mContent = (ContentFrameLayout) findViewById(C0268R.C0270id.action_bar_activity_content);
            this.mActionBarTop = (ActionBarContainer) findViewById(C0268R.C0270id.action_bar_container);
            this.mDecorToolbar = getDecorToolbar(findViewById(C0268R.C0270id.action_bar));
        }
    }

    private DecorToolbar getDecorToolbar(View view) {
        View view2 = view;
        View view3 = view2;
        if (view2 instanceof DecorToolbar) {
            return (DecorToolbar) view2;
        }
        if (view2 instanceof Toolbar) {
            return ((Toolbar) view2).getWrapper();
        }
        throw new IllegalStateException("Can't make a decor toolbar out of " + view2.getClass().getSimpleName());
    }

    public void setHideOnContentScrollEnabled(boolean z) {
        boolean hideOnContentScroll = z;
        if (hideOnContentScroll != this.mHideOnContentScroll) {
            this.mHideOnContentScroll = hideOnContentScroll;
            if (!hideOnContentScroll) {
                haltActionBarHideOffsetAnimations();
                setActionBarHideOffset(0);
            }
        }
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.mHideOnContentScroll;
    }

    public int getActionBarHideOffset() {
        return this.mActionBarTop == null ? 0 : -((int) ViewCompat.getTranslationY(this.mActionBarTop));
    }

    public void setActionBarHideOffset(int i) {
        int offset = i;
        int i2 = offset;
        haltActionBarHideOffsetAnimations();
        int max = Math.max(0, Math.min(offset, this.mActionBarTop.getHeight()));
        int offset2 = max;
        ViewCompat.setTranslationY(this.mActionBarTop, (float) (-max));
    }

    /* access modifiers changed from: 0000 */
    public void haltActionBarHideOffsetAnimations() {
        boolean removeCallbacks = removeCallbacks(this.mRemoveActionBarHideOffset);
        boolean removeCallbacks2 = removeCallbacks(this.mAddActionBarHideOffset);
        if (this.mCurrentActionBarTopAnimator != null) {
            this.mCurrentActionBarTopAnimator.cancel();
        }
    }

    private void postRemoveActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        boolean postDelayed = postDelayed(this.mRemoveActionBarHideOffset, 600);
    }

    private void postAddActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        boolean postDelayed = postDelayed(this.mAddActionBarHideOffset, 600);
    }

    private void removeActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        this.mRemoveActionBarHideOffset.run();
    }

    private void addActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        this.mAddActionBarHideOffset.run();
    }

    private boolean shouldHideActionBarOnFling(float f, float f2) {
        float velocityY = f2;
        float f3 = f;
        float f4 = velocityY;
        this.mFlingEstimator.fling(0, 0, 0, (int) velocityY, 0, 0, Integer.MIN_VALUE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        int finalY = this.mFlingEstimator.getFinalY();
        int i = finalY;
        return finalY > this.mActionBarTop.getHeight();
    }

    public void setWindowCallback(Callback callback) {
        Callback cb = callback;
        Callback callback2 = cb;
        pullChildren();
        this.mDecorToolbar.setWindowCallback(cb);
    }

    public void setWindowTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        pullChildren();
        this.mDecorToolbar.setWindowTitle(title);
    }

    public CharSequence getTitle() {
        pullChildren();
        return this.mDecorToolbar.getTitle();
    }

    public void initFeature(int i) {
        int windowFeature = i;
        int i2 = windowFeature;
        pullChildren();
        switch (windowFeature) {
            case 2:
                this.mDecorToolbar.initProgress();
                return;
            case 5:
                this.mDecorToolbar.initIndeterminateProgress();
                return;
            case 109:
                setOverlayMode(true);
                return;
            default:
                return;
        }
    }

    public void setUiOptions(int i) {
        int i2 = i;
    }

    public boolean hasIcon() {
        pullChildren();
        return this.mDecorToolbar.hasIcon();
    }

    public boolean hasLogo() {
        pullChildren();
        return this.mDecorToolbar.hasLogo();
    }

    public void setIcon(int i) {
        int resId = i;
        int i2 = resId;
        pullChildren();
        this.mDecorToolbar.setIcon(resId);
    }

    public void setIcon(Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        pullChildren();
        this.mDecorToolbar.setIcon(d);
    }

    public void setLogo(int i) {
        int resId = i;
        int i2 = resId;
        pullChildren();
        this.mDecorToolbar.setLogo(resId);
    }

    public boolean canShowOverflowMenu() {
        pullChildren();
        return this.mDecorToolbar.canShowOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() {
        pullChildren();
        return this.mDecorToolbar.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        pullChildren();
        return this.mDecorToolbar.hideOverflowMenu();
    }

    public void setMenuPrepared() {
        pullChildren();
        this.mDecorToolbar.setMenuPrepared();
    }

    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        Menu menu2 = menu;
        MenuPresenter.Callback cb = callback;
        Menu menu3 = menu2;
        MenuPresenter.Callback callback2 = cb;
        pullChildren();
        this.mDecorToolbar.setMenu(menu2, cb);
    }

    public void saveToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> toolbarStates = sparseArray;
        SparseArray<Parcelable> sparseArray2 = toolbarStates;
        pullChildren();
        this.mDecorToolbar.saveHierarchyState(toolbarStates);
    }

    public void restoreToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> toolbarStates = sparseArray;
        SparseArray<Parcelable> sparseArray2 = toolbarStates;
        pullChildren();
        this.mDecorToolbar.restoreHierarchyState(toolbarStates);
    }

    public void dismissPopups() {
        pullChildren();
        this.mDecorToolbar.dismissPopupMenus();
    }
}
