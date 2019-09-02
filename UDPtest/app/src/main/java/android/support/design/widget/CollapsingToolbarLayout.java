package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.design.C0001R;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.view.OnApplyWindowInsetsListener;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.WindowInsetsCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.widget.FrameLayout;

public class CollapsingToolbarLayout extends FrameLayout {
    private static final int DEFAULT_SCRIM_ANIMATION_DURATION = 600;
    final CollapsingTextHelper mCollapsingTextHelper;
    private boolean mCollapsingTitleEnabled;
    private Drawable mContentScrim;
    int mCurrentOffset;
    private boolean mDrawCollapsingTitle;
    private View mDummyView;
    private int mExpandedMarginBottom;
    private int mExpandedMarginEnd;
    private int mExpandedMarginStart;
    private int mExpandedMarginTop;
    WindowInsetsCompat mLastInsets;
    private OnOffsetChangedListener mOnOffsetChangedListener;
    private boolean mRefreshToolbar;
    private int mScrimAlpha;
    private long mScrimAnimationDuration;
    private ValueAnimatorCompat mScrimAnimator;
    private int mScrimVisibleHeightTrigger;
    private boolean mScrimsAreShown;
    Drawable mStatusBarScrim;
    private final Rect mTmpRect;
    private Toolbar mToolbar;
    private View mToolbarDirectChild;
    private int mToolbarId;

    public static class LayoutParams extends android.widget.FrameLayout.LayoutParams {
        public static final int COLLAPSE_MODE_OFF = 0;
        public static final int COLLAPSE_MODE_PARALLAX = 2;
        public static final int COLLAPSE_MODE_PIN = 1;
        private static final float DEFAULT_PARALLAX_MULTIPLIER = 0.5f;
        int mCollapseMode = 0;
        float mParallaxMult = DEFAULT_PARALLAX_MULTIPLIER;

        public LayoutParams(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            super(width, height);
        }

        public LayoutParams(int i, int i2, int i3) {
            int width = i;
            int height = i2;
            int gravity = i3;
            int i4 = width;
            int i5 = height;
            int i6 = gravity;
            super(width, height, gravity);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            Context c = context;
            AttributeSet attrs = attributeSet;
            Context context2 = c;
            AttributeSet attributeSet2 = attrs;
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, C0001R.styleable.CollapsingToolbarLayout_Layout);
            this.mCollapseMode = a.getInt(C0001R.styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
            setParallaxMultiplier(a.getFloat(C0001R.styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, DEFAULT_PARALLAX_MULTIPLIER));
            a.recycle();
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
        public LayoutParams(android.widget.FrameLayout.LayoutParams layoutParams) {
            android.widget.FrameLayout.LayoutParams source = layoutParams;
            android.widget.FrameLayout.LayoutParams layoutParams2 = source;
            super(source);
        }

        public void setCollapseMode(int i) {
            int collapseMode = i;
            int i2 = collapseMode;
            this.mCollapseMode = collapseMode;
        }

        public int getCollapseMode() {
            return this.mCollapseMode;
        }

        public void setParallaxMultiplier(float f) {
            float multiplier = f;
            float f2 = multiplier;
            this.mParallaxMult = multiplier;
        }

        public float getParallaxMultiplier() {
            return this.mParallaxMult;
        }
    }

    private class OffsetUpdateListener implements OnOffsetChangedListener {
        final /* synthetic */ CollapsingToolbarLayout this$0;

        OffsetUpdateListener(CollapsingToolbarLayout collapsingToolbarLayout) {
            this.this$0 = collapsingToolbarLayout;
        }

        public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            int verticalOffset = i;
            AppBarLayout appBarLayout2 = appBarLayout;
            int i2 = verticalOffset;
            this.this$0.mCurrentOffset = verticalOffset;
            int insetTop = this.this$0.mLastInsets == null ? 0 : this.this$0.mLastInsets.getSystemWindowInsetTop();
            int z = this.this$0.getChildCount();
            for (int i3 = 0; i3 < z; i3++) {
                View childAt = this.this$0.getChildAt(i3);
                View child = childAt;
                LayoutParams lp = (LayoutParams) childAt.getLayoutParams();
                ViewOffsetHelper offsetHelper = CollapsingToolbarLayout.getViewOffsetHelper(child);
                switch (lp.mCollapseMode) {
                    case 1:
                        boolean topAndBottomOffset = offsetHelper.setTopAndBottomOffset(MathUtils.constrain(-verticalOffset, 0, this.this$0.getMaxOffsetForPinChild(child)));
                        break;
                    case 2:
                        boolean topAndBottomOffset2 = offsetHelper.setTopAndBottomOffset(Math.round(((float) (-verticalOffset)) * lp.mParallaxMult));
                        break;
                }
            }
            this.this$0.updateScrimVisibility();
            if (this.this$0.mStatusBarScrim != null && insetTop > 0) {
                ViewCompat.postInvalidateOnAnimation(this.this$0);
            }
            int height = (this.this$0.getHeight() - ViewCompat.getMinimumHeight(this.this$0)) - insetTop;
            int i4 = height;
            this.this$0.mCollapsingTextHelper.setExpansionFraction(((float) Math.abs(verticalOffset)) / ((float) height));
        }
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mRefreshToolbar = true;
        this.mTmpRect = new Rect();
        this.mScrimVisibleHeightTrigger = -1;
        ThemeUtils.checkAppCompatTheme(context2);
        this.mCollapsingTextHelper = new CollapsingTextHelper(this);
        this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
        TypedArray a = context2.obtainStyledAttributes(attrs, C0001R.styleable.CollapsingToolbarLayout, defStyleAttr, C0001R.style.Widget_Design_CollapsingToolbar);
        this.mCollapsingTextHelper.setExpandedTextGravity(a.getInt(C0001R.styleable.CollapsingToolbarLayout_expandedTitleGravity, 8388691));
        this.mCollapsingTextHelper.setCollapsedTextGravity(a.getInt(C0001R.styleable.CollapsingToolbarLayout_collapsedTitleGravity, 8388627));
        int dimensionPixelSize = a.getDimensionPixelSize(C0001R.styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
        this.mExpandedMarginBottom = dimensionPixelSize;
        int i3 = dimensionPixelSize;
        this.mExpandedMarginEnd = dimensionPixelSize;
        this.mExpandedMarginTop = i3;
        this.mExpandedMarginStart = i3;
        if (a.hasValue(C0001R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
            this.mExpandedMarginStart = a.getDimensionPixelSize(C0001R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
        }
        if (a.hasValue(C0001R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
            this.mExpandedMarginEnd = a.getDimensionPixelSize(C0001R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
        }
        if (a.hasValue(C0001R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
            this.mExpandedMarginTop = a.getDimensionPixelSize(C0001R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
        }
        if (a.hasValue(C0001R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
            this.mExpandedMarginBottom = a.getDimensionPixelSize(C0001R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
        }
        this.mCollapsingTitleEnabled = a.getBoolean(C0001R.styleable.CollapsingToolbarLayout_titleEnabled, true);
        setTitle(a.getText(C0001R.styleable.CollapsingToolbarLayout_title));
        this.mCollapsingTextHelper.setExpandedTextAppearance(C0001R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
        this.mCollapsingTextHelper.setCollapsedTextAppearance(C0268R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
        if (a.hasValue(C0001R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
            this.mCollapsingTextHelper.setExpandedTextAppearance(a.getResourceId(C0001R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
        }
        if (a.hasValue(C0001R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
            this.mCollapsingTextHelper.setCollapsedTextAppearance(a.getResourceId(C0001R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
        }
        this.mScrimVisibleHeightTrigger = a.getDimensionPixelSize(C0001R.styleable.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
        this.mScrimAnimationDuration = (long) a.getInt(C0001R.styleable.CollapsingToolbarLayout_scrimAnimationDuration, DEFAULT_SCRIM_ANIMATION_DURATION);
        setContentScrim(a.getDrawable(C0001R.styleable.CollapsingToolbarLayout_contentScrim));
        setStatusBarScrim(a.getDrawable(C0001R.styleable.CollapsingToolbarLayout_statusBarScrim));
        this.mToolbarId = a.getResourceId(C0001R.styleable.CollapsingToolbarLayout_toolbarId, -1);
        a.recycle();
        setWillNotDraw(false);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener(this) {
            final /* synthetic */ CollapsingToolbarLayout this$0;

            {
                CollapsingToolbarLayout this$02 = r5;
                CollapsingToolbarLayout collapsingToolbarLayout = this$02;
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

    public CollapsingToolbarLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        ViewParent parent2 = parent;
        if (parent instanceof AppBarLayout) {
            ViewCompat.setFitsSystemWindows(this, ViewCompat.getFitsSystemWindows((View) parent2));
            if (this.mOnOffsetChangedListener == null) {
                this.mOnOffsetChangedListener = new OffsetUpdateListener(this);
            }
            ((AppBarLayout) parent2).addOnOffsetChangedListener(this.mOnOffsetChangedListener);
            ViewCompat.requestApplyInsets(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        ViewParent parent = getParent();
        if (this.mOnOffsetChangedListener != null && (parent instanceof AppBarLayout)) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(this.mOnOffsetChangedListener);
        }
        super.onDetachedFromWindow();
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
            requestLayout();
        }
        return insets.consumeSystemWindowInsets();
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        super.draw(canvas2);
        ensureToolbar();
        if (this.mToolbar == null && this.mContentScrim != null && this.mScrimAlpha > 0) {
            this.mContentScrim.mutate().setAlpha(this.mScrimAlpha);
            this.mContentScrim.draw(canvas2);
        }
        if (this.mCollapsingTitleEnabled && this.mDrawCollapsingTitle) {
            this.mCollapsingTextHelper.draw(canvas2);
        }
        if (this.mStatusBarScrim != null && this.mScrimAlpha > 0) {
            int topInset = this.mLastInsets == null ? 0 : this.mLastInsets.getSystemWindowInsetTop();
            if (topInset > 0) {
                this.mStatusBarScrim.setBounds(0, -this.mCurrentOffset, getWidth(), topInset - this.mCurrentOffset);
                this.mStatusBarScrim.mutate().setAlpha(this.mScrimAlpha);
                this.mStatusBarScrim.draw(canvas2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        boolean z;
        Canvas canvas2 = canvas;
        View child = view;
        long drawingTime = j;
        Canvas canvas3 = canvas2;
        View view2 = child;
        long j2 = drawingTime;
        boolean invalidated = false;
        if (this.mContentScrim != null && this.mScrimAlpha > 0 && isToolbarChild(child)) {
            this.mContentScrim.mutate().setAlpha(this.mScrimAlpha);
            this.mContentScrim.draw(canvas2);
            invalidated = true;
        }
        if (!super.drawChild(canvas2, child, drawingTime) && !invalidated) {
            z = false;
        } else {
            z = true;
        }
        return z;
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
        if (this.mContentScrim != null) {
            this.mContentScrim.setBounds(0, 0, w, h);
        }
    }

    private void ensureToolbar() {
        if (this.mRefreshToolbar) {
            this.mToolbar = null;
            this.mToolbarDirectChild = null;
            if (this.mToolbarId != -1) {
                this.mToolbar = (Toolbar) findViewById(this.mToolbarId);
                if (this.mToolbar != null) {
                    this.mToolbarDirectChild = findDirectChild(this.mToolbar);
                }
            }
            if (this.mToolbar == null) {
                Toolbar toolbar = null;
                int i = 0;
                int count = getChildCount();
                while (true) {
                    if (i < count) {
                        View childAt = getChildAt(i);
                        View child = childAt;
                        if (childAt instanceof Toolbar) {
                            toolbar = (Toolbar) child;
                            break;
                        }
                        i++;
                    } else {
                        break;
                    }
                }
                this.mToolbar = toolbar;
            }
            updateDummyView();
            this.mRefreshToolbar = false;
        }
    }

    private boolean isToolbarChild(View view) {
        View child = view;
        View view2 = child;
        boolean z = (this.mToolbarDirectChild == null || this.mToolbarDirectChild == this) ? child == this.mToolbar : child == this.mToolbarDirectChild;
        return z;
    }

    private View findDirectChild(View view) {
        View descendant = view;
        View view2 = descendant;
        View directChild = descendant;
        ViewParent p = descendant.getParent();
        while (p != this && p != null) {
            if (p instanceof View) {
                directChild = (View) p;
            }
            p = p.getParent();
        }
        return directChild;
    }

    private void updateDummyView() {
        if (!this.mCollapsingTitleEnabled && this.mDummyView != null) {
            ViewParent parent = this.mDummyView.getParent();
            ViewParent parent2 = parent;
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent2).removeView(this.mDummyView);
            }
        }
        if (this.mCollapsingTitleEnabled && this.mToolbar != null) {
            if (this.mDummyView == null) {
                this.mDummyView = new View(getContext());
            }
            if (this.mDummyView.getParent() == null) {
                this.mToolbar.addView(this.mDummyView, -1, -1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        ensureToolbar();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int titleMarginEnd;
        int titleMarginStart;
        int i5;
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        int i6 = left;
        int i7 = top;
        int i8 = right;
        int i9 = bottom;
        super.onLayout(z, left, top, right, bottom);
        if (this.mLastInsets != null) {
            int insetTop = this.mLastInsets.getSystemWindowInsetTop();
            int z2 = getChildCount();
            for (int i10 = 0; i10 < z2; i10++) {
                View childAt = getChildAt(i10);
                View child = childAt;
                if (!ViewCompat.getFitsSystemWindows(childAt) && child.getTop() < insetTop) {
                    ViewCompat.offsetTopAndBottom(child, insetTop);
                }
            }
        }
        if (this.mCollapsingTitleEnabled && this.mDummyView != null) {
            this.mDrawCollapsingTitle = ViewCompat.isAttachedToWindow(this.mDummyView) && this.mDummyView.getVisibility() == 0;
            if (this.mDrawCollapsingTitle) {
                boolean isRtl = ViewCompat.getLayoutDirection(this) == 1;
                int maxOffsetForPinChild = getMaxOffsetForPinChild(this.mToolbarDirectChild == null ? this.mToolbar : this.mToolbarDirectChild);
                int i11 = maxOffsetForPinChild;
                ViewGroupUtils.getDescendantRect(this, this.mDummyView, this.mTmpRect);
                CollapsingTextHelper collapsingTextHelper = this.mCollapsingTextHelper;
                int i12 = this.mTmpRect.left;
                if (!isRtl) {
                    titleMarginEnd = this.mToolbar.getTitleMarginStart();
                } else {
                    titleMarginEnd = this.mToolbar.getTitleMarginEnd();
                }
                int i13 = i12 + titleMarginEnd;
                int titleMarginTop = this.mTmpRect.top + maxOffsetForPinChild + this.mToolbar.getTitleMarginTop();
                int i14 = this.mTmpRect.right;
                if (!isRtl) {
                    titleMarginStart = this.mToolbar.getTitleMarginEnd();
                } else {
                    titleMarginStart = this.mToolbar.getTitleMarginStart();
                }
                collapsingTextHelper.setCollapsedBounds(i13, titleMarginTop, i14 + titleMarginStart, (this.mTmpRect.bottom + maxOffsetForPinChild) - this.mToolbar.getTitleMarginBottom());
                CollapsingTextHelper collapsingTextHelper2 = this.mCollapsingTextHelper;
                int i15 = !isRtl ? this.mExpandedMarginStart : this.mExpandedMarginEnd;
                int i16 = this.mTmpRect.top + this.mExpandedMarginTop;
                int i17 = right - left;
                if (!isRtl) {
                    i5 = this.mExpandedMarginEnd;
                } else {
                    i5 = this.mExpandedMarginStart;
                }
                collapsingTextHelper2.setExpandedBounds(i15, i16, i17 - i5, (bottom - top) - this.mExpandedMarginBottom);
                this.mCollapsingTextHelper.recalculate();
            }
        }
        int z3 = getChildCount();
        for (int i18 = 0; i18 < z3; i18++) {
            getViewOffsetHelper(getChildAt(i18)).onViewLayout();
        }
        if (this.mToolbar != null) {
            if (this.mCollapsingTitleEnabled && TextUtils.isEmpty(this.mCollapsingTextHelper.getText())) {
                this.mCollapsingTextHelper.setText(this.mToolbar.getTitle());
            }
            if (this.mToolbarDirectChild == null || this.mToolbarDirectChild == this) {
                setMinimumHeight(getHeightWithMargins(this.mToolbar));
            } else {
                setMinimumHeight(getHeightWithMargins(this.mToolbarDirectChild));
            }
        }
        updateScrimVisibility();
    }

    private static int getHeightWithMargins(@NonNull View view) {
        View view2 = view;
        View view3 = view2;
        android.view.ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
        android.view.ViewGroup.LayoutParams lp = layoutParams;
        if (!(layoutParams instanceof MarginLayoutParams)) {
            return view2.getHeight();
        }
        MarginLayoutParams mlp = (MarginLayoutParams) lp;
        return view2.getHeight() + mlp.topMargin + mlp.bottomMargin;
    }

    static ViewOffsetHelper getViewOffsetHelper(View view) {
        View view2 = view;
        View view3 = view2;
        ViewOffsetHelper viewOffsetHelper = (ViewOffsetHelper) view2.getTag(C0001R.C0003id.view_offset_helper);
        ViewOffsetHelper offsetHelper = viewOffsetHelper;
        if (viewOffsetHelper == null) {
            offsetHelper = new ViewOffsetHelper(view2);
            view2.setTag(C0001R.C0003id.view_offset_helper, offsetHelper);
        }
        return offsetHelper;
    }

    public void setTitle(@Nullable CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mCollapsingTextHelper.setText(title);
    }

    @Nullable
    public CharSequence getTitle() {
        return !this.mCollapsingTitleEnabled ? null : this.mCollapsingTextHelper.getText();
    }

    public void setTitleEnabled(boolean z) {
        boolean enabled = z;
        if (enabled != this.mCollapsingTitleEnabled) {
            this.mCollapsingTitleEnabled = enabled;
            updateDummyView();
            requestLayout();
        }
    }

    public boolean isTitleEnabled() {
        return this.mCollapsingTitleEnabled;
    }

    public void setScrimsShown(boolean z) {
        setScrimsShown(z, ViewCompat.isLaidOut(this) && !isInEditMode());
    }

    public void setScrimsShown(boolean z, boolean z2) {
        boolean shown = z;
        boolean animate = z2;
        if (this.mScrimsAreShown != shown) {
            if (!animate) {
                setScrimAlpha(!shown ? 0 : 255);
            } else {
                animateScrim(!shown ? 0 : 255);
            }
            this.mScrimsAreShown = shown;
        }
    }

    private void animateScrim(int i) {
        int targetAlpha = i;
        int i2 = targetAlpha;
        ensureToolbar();
        if (this.mScrimAnimator == null) {
            this.mScrimAnimator = ViewUtils.createAnimator();
            this.mScrimAnimator.setDuration(this.mScrimAnimationDuration);
            this.mScrimAnimator.setInterpolator(targetAlpha <= this.mScrimAlpha ? AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR : AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
            this.mScrimAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
                final /* synthetic */ CollapsingToolbarLayout this$0;

                {
                    CollapsingToolbarLayout this$02 = r5;
                    CollapsingToolbarLayout collapsingToolbarLayout = this$02;
                    this.this$0 = this$02;
                }

                public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                    ValueAnimatorCompat animator = valueAnimatorCompat;
                    ValueAnimatorCompat valueAnimatorCompat2 = animator;
                    this.this$0.setScrimAlpha(animator.getAnimatedIntValue());
                }
            });
        } else if (this.mScrimAnimator.isRunning()) {
            this.mScrimAnimator.cancel();
        }
        this.mScrimAnimator.setIntValues(this.mScrimAlpha, targetAlpha);
        this.mScrimAnimator.start();
    }

    /* access modifiers changed from: 0000 */
    public void setScrimAlpha(int i) {
        int alpha = i;
        int i2 = alpha;
        if (alpha != this.mScrimAlpha) {
            Drawable drawable = this.mContentScrim;
            Drawable drawable2 = drawable;
            if (!(drawable == null || this.mToolbar == null)) {
                ViewCompat.postInvalidateOnAnimation(this.mToolbar);
            }
            this.mScrimAlpha = alpha;
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public int getScrimAlpha() {
        return this.mScrimAlpha;
    }

    public void setContentScrim(@Nullable Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (this.mContentScrim != drawable2) {
            if (this.mContentScrim != null) {
                this.mContentScrim.setCallback(null);
            }
            this.mContentScrim = drawable2 == null ? null : drawable2.mutate();
            if (this.mContentScrim != null) {
                this.mContentScrim.setBounds(0, 0, getWidth(), getHeight());
                this.mContentScrim.setCallback(this);
                this.mContentScrim.setAlpha(this.mScrimAlpha);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setContentScrimColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        setContentScrim(new ColorDrawable(color));
    }

    public void setContentScrimResource(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setContentScrim(ContextCompat.getDrawable(getContext(), resId));
    }

    @Nullable
    public Drawable getContentScrim() {
        return this.mContentScrim;
    }

    public void setStatusBarScrim(@Nullable Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (this.mStatusBarScrim != drawable2) {
            if (this.mStatusBarScrim != null) {
                this.mStatusBarScrim.setCallback(null);
            }
            this.mStatusBarScrim = drawable2 == null ? null : drawable2.mutate();
            if (this.mStatusBarScrim != null) {
                if (this.mStatusBarScrim.isStateful()) {
                    boolean state = this.mStatusBarScrim.setState(getDrawableState());
                }
                boolean layoutDirection = DrawableCompat.setLayoutDirection(this.mStatusBarScrim, ViewCompat.getLayoutDirection(this));
                boolean visible = this.mStatusBarScrim.setVisible(getVisibility() == 0, false);
                this.mStatusBarScrim.setCallback(this);
                this.mStatusBarScrim.setAlpha(this.mScrimAlpha);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] state = getDrawableState();
        boolean changed = false;
        Drawable drawable = this.mStatusBarScrim;
        Drawable d = drawable;
        if (drawable != null && d.isStateful()) {
            changed = false | d.setState(state);
        }
        Drawable drawable2 = this.mContentScrim;
        Drawable d2 = drawable2;
        if (drawable2 != null && d2.isStateful()) {
            changed |= d2.setState(state);
        }
        if (this.mCollapsingTextHelper != null) {
            changed |= this.mCollapsingTextHelper.setState(state);
        }
        if (changed) {
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        Drawable who = drawable;
        Drawable drawable2 = who;
        return super.verifyDrawable(who) || who == this.mContentScrim || who == this.mStatusBarScrim;
    }

    public void setVisibility(int i) {
        int visibility = i;
        int i2 = visibility;
        super.setVisibility(visibility);
        boolean visible = visibility == 0;
        if (!(this.mStatusBarScrim == null || this.mStatusBarScrim.isVisible() == visible)) {
            boolean visible2 = this.mStatusBarScrim.setVisible(visible, false);
        }
        if (this.mContentScrim != null && this.mContentScrim.isVisible() != visible) {
            boolean visible3 = this.mContentScrim.setVisible(visible, false);
        }
    }

    public void setStatusBarScrimColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        setStatusBarScrim(new ColorDrawable(color));
    }

    public void setStatusBarScrimResource(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setStatusBarScrim(ContextCompat.getDrawable(getContext(), resId));
    }

    @Nullable
    public Drawable getStatusBarScrim() {
        return this.mStatusBarScrim;
    }

    public void setCollapsedTitleTextAppearance(@StyleRes int i) {
        int resId = i;
        int i2 = resId;
        this.mCollapsingTextHelper.setCollapsedTextAppearance(resId);
    }

    public void setCollapsedTitleTextColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        setCollapsedTitleTextColor(ColorStateList.valueOf(color));
    }

    public void setCollapsedTitleTextColor(@NonNull ColorStateList colorStateList) {
        ColorStateList colors = colorStateList;
        ColorStateList colorStateList2 = colors;
        this.mCollapsingTextHelper.setCollapsedTextColor(colors);
    }

    public void setCollapsedTitleGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        this.mCollapsingTextHelper.setCollapsedTextGravity(gravity);
    }

    public int getCollapsedTitleGravity() {
        return this.mCollapsingTextHelper.getCollapsedTextGravity();
    }

    public void setExpandedTitleTextAppearance(@StyleRes int i) {
        int resId = i;
        int i2 = resId;
        this.mCollapsingTextHelper.setExpandedTextAppearance(resId);
    }

    public void setExpandedTitleColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        setExpandedTitleTextColor(ColorStateList.valueOf(color));
    }

    public void setExpandedTitleTextColor(@NonNull ColorStateList colorStateList) {
        ColorStateList colors = colorStateList;
        ColorStateList colorStateList2 = colors;
        this.mCollapsingTextHelper.setExpandedTextColor(colors);
    }

    public void setExpandedTitleGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        this.mCollapsingTextHelper.setExpandedTextGravity(gravity);
    }

    public int getExpandedTitleGravity() {
        return this.mCollapsingTextHelper.getExpandedTextGravity();
    }

    public void setCollapsedTitleTypeface(@Nullable Typeface typeface) {
        Typeface typeface2 = typeface;
        Typeface typeface3 = typeface2;
        this.mCollapsingTextHelper.setCollapsedTypeface(typeface2);
    }

    @NonNull
    public Typeface getCollapsedTitleTypeface() {
        return this.mCollapsingTextHelper.getCollapsedTypeface();
    }

    public void setExpandedTitleTypeface(@Nullable Typeface typeface) {
        Typeface typeface2 = typeface;
        Typeface typeface3 = typeface2;
        this.mCollapsingTextHelper.setExpandedTypeface(typeface2);
    }

    @NonNull
    public Typeface getExpandedTitleTypeface() {
        return this.mCollapsingTextHelper.getExpandedTypeface();
    }

    public void setExpandedTitleMargin(int i, int i2, int i3, int i4) {
        int start = i;
        int top = i2;
        int end = i3;
        int bottom = i4;
        int i5 = start;
        int i6 = top;
        int i7 = end;
        int i8 = bottom;
        this.mExpandedMarginStart = start;
        this.mExpandedMarginTop = top;
        this.mExpandedMarginEnd = end;
        this.mExpandedMarginBottom = bottom;
        requestLayout();
    }

    public int getExpandedTitleMarginStart() {
        return this.mExpandedMarginStart;
    }

    public void setExpandedTitleMarginStart(int i) {
        int margin = i;
        int i2 = margin;
        this.mExpandedMarginStart = margin;
        requestLayout();
    }

    public int getExpandedTitleMarginTop() {
        return this.mExpandedMarginTop;
    }

    public void setExpandedTitleMarginTop(int i) {
        int margin = i;
        int i2 = margin;
        this.mExpandedMarginTop = margin;
        requestLayout();
    }

    public int getExpandedTitleMarginEnd() {
        return this.mExpandedMarginEnd;
    }

    public void setExpandedTitleMarginEnd(int i) {
        int margin = i;
        int i2 = margin;
        this.mExpandedMarginEnd = margin;
        requestLayout();
    }

    public int getExpandedTitleMarginBottom() {
        return this.mExpandedMarginBottom;
    }

    public void setExpandedTitleMarginBottom(int i) {
        int margin = i;
        int i2 = margin;
        this.mExpandedMarginBottom = margin;
        requestLayout();
    }

    public void setScrimVisibleHeightTrigger(@IntRange(from = 0) int i) {
        int height = i;
        int i2 = height;
        if (this.mScrimVisibleHeightTrigger != height) {
            this.mScrimVisibleHeightTrigger = height;
            updateScrimVisibility();
        }
    }

    public int getScrimVisibleHeightTrigger() {
        if (this.mScrimVisibleHeightTrigger >= 0) {
            return this.mScrimVisibleHeightTrigger;
        }
        int insetTop = this.mLastInsets == null ? 0 : this.mLastInsets.getSystemWindowInsetTop();
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        int minHeight = minimumHeight;
        if (minimumHeight <= 0) {
            return getHeight() / 3;
        }
        return Math.min((minHeight * 2) + insetTop, getHeight());
    }

    public void setScrimAnimationDuration(@IntRange(from = 0) long j) {
        long duration = j;
        long j2 = duration;
        this.mScrimAnimationDuration = duration;
    }

    public long getScrimAnimationDuration() {
        return this.mScrimAnimationDuration;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return p instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public android.widget.FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AttributeSet attrs = attributeSet;
        AttributeSet attributeSet2 = attrs;
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public android.widget.FrameLayout.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return new LayoutParams(p);
    }

    /* access modifiers changed from: 0000 */
    public final void updateScrimVisibility() {
        if (this.mContentScrim != null || this.mStatusBarScrim != null) {
            setScrimsShown(getHeight() + this.mCurrentOffset < getScrimVisibleHeightTrigger());
        }
    }

    /* access modifiers changed from: 0000 */
    public final int getMaxOffsetForPinChild(View view) {
        View child = view;
        View view2 = child;
        return ((getHeight() - getViewOffsetHelper(child).getLayoutTop()) - child.getHeight()) - ((LayoutParams) child.getLayoutParams()).bottomMargin;
    }
}
