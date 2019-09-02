package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ActionBarContainer */
public class ActionBarContainer extends FrameLayout {
    private View mActionBarView;
    Drawable mBackground;
    private View mContextView;
    private int mHeight;
    boolean mIsSplit;
    boolean mIsStacked;
    private boolean mIsTransitioning;
    Drawable mSplitBackground;
    Drawable mStackedBackground;
    private View mTabContainer;

    public ActionBarContainer(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public ActionBarContainer(Context context, AttributeSet attributeSet) {
        Drawable actionBarBackgroundDrawableV21;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        if (VERSION.SDK_INT < 21) {
            actionBarBackgroundDrawableV21 = new ActionBarBackgroundDrawable(this);
        } else {
            actionBarBackgroundDrawableV21 = new ActionBarBackgroundDrawableV21(this);
        }
        ViewCompat.setBackground(this, actionBarBackgroundDrawableV21);
        TypedArray a = context2.obtainStyledAttributes(attrs, C0268R.styleable.ActionBar);
        this.mBackground = a.getDrawable(C0268R.styleable.ActionBar_background);
        this.mStackedBackground = a.getDrawable(C0268R.styleable.ActionBar_backgroundStacked);
        this.mHeight = a.getDimensionPixelSize(C0268R.styleable.ActionBar_height, -1);
        if (getId() == C0268R.C0270id.split_action_bar) {
            this.mIsSplit = true;
            this.mSplitBackground = a.getDrawable(C0268R.styleable.ActionBar_backgroundSplit);
        }
        a.recycle();
        boolean z = !this.mIsSplit ? this.mBackground == null && this.mStackedBackground == null : this.mSplitBackground == null;
        setWillNotDraw(z);
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.mActionBarView = findViewById(C0268R.C0270id.action_bar);
        this.mContextView = findViewById(C0268R.C0270id.action_context_bar);
    }

    public void setPrimaryBackground(Drawable drawable) {
        Drawable bg = drawable;
        Drawable drawable2 = bg;
        if (this.mBackground != null) {
            this.mBackground.setCallback(null);
            unscheduleDrawable(this.mBackground);
        }
        this.mBackground = bg;
        if (bg != null) {
            bg.setCallback(this);
            if (this.mActionBarView != null) {
                this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
            }
        }
        boolean z = !this.mIsSplit ? this.mBackground == null && this.mStackedBackground == null : this.mSplitBackground == null;
        setWillNotDraw(z);
        invalidate();
    }

    public void setStackedBackground(Drawable drawable) {
        Drawable bg = drawable;
        Drawable drawable2 = bg;
        if (this.mStackedBackground != null) {
            this.mStackedBackground.setCallback(null);
            unscheduleDrawable(this.mStackedBackground);
        }
        this.mStackedBackground = bg;
        if (bg != null) {
            bg.setCallback(this);
            if (this.mIsStacked && this.mStackedBackground != null) {
                this.mStackedBackground.setBounds(this.mTabContainer.getLeft(), this.mTabContainer.getTop(), this.mTabContainer.getRight(), this.mTabContainer.getBottom());
            }
        }
        boolean z = !this.mIsSplit ? this.mBackground == null && this.mStackedBackground == null : this.mSplitBackground == null;
        setWillNotDraw(z);
        invalidate();
    }

    public void setSplitBackground(Drawable drawable) {
        Drawable bg = drawable;
        Drawable drawable2 = bg;
        if (this.mSplitBackground != null) {
            this.mSplitBackground.setCallback(null);
            unscheduleDrawable(this.mSplitBackground);
        }
        this.mSplitBackground = bg;
        if (bg != null) {
            bg.setCallback(this);
            if (this.mIsSplit && this.mSplitBackground != null) {
                this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        }
        boolean z = !this.mIsSplit ? this.mBackground == null && this.mStackedBackground == null : this.mSplitBackground == null;
        setWillNotDraw(z);
        invalidate();
    }

    public void setVisibility(int i) {
        int visibility = i;
        int i2 = visibility;
        super.setVisibility(visibility);
        boolean isVisible = visibility == 0;
        if (this.mBackground != null) {
            boolean visible = this.mBackground.setVisible(isVisible, false);
        }
        if (this.mStackedBackground != null) {
            boolean visible2 = this.mStackedBackground.setVisible(isVisible, false);
        }
        if (this.mSplitBackground != null) {
            boolean visible3 = this.mSplitBackground.setVisible(isVisible, false);
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        Drawable who = drawable;
        Drawable drawable2 = who;
        return (who == this.mBackground && !this.mIsSplit) || (who == this.mStackedBackground && this.mIsStacked) || ((who == this.mSplitBackground && this.mIsSplit) || super.verifyDrawable(who));
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackground != null && this.mBackground.isStateful()) {
            boolean state = this.mBackground.setState(getDrawableState());
        }
        if (this.mStackedBackground != null && this.mStackedBackground.isStateful()) {
            boolean state2 = this.mStackedBackground.setState(getDrawableState());
        }
        if (this.mSplitBackground != null && this.mSplitBackground.isStateful()) {
            boolean state3 = this.mSplitBackground.setState(getDrawableState());
        }
    }

    public void jumpDrawablesToCurrentState() {
        if (VERSION.SDK_INT >= 11) {
            super.jumpDrawablesToCurrentState();
            if (this.mBackground != null) {
                this.mBackground.jumpToCurrentState();
            }
            if (this.mStackedBackground != null) {
                this.mStackedBackground.jumpToCurrentState();
            }
            if (this.mSplitBackground != null) {
                this.mSplitBackground.jumpToCurrentState();
            }
        }
    }

    public void setTransitioning(boolean z) {
        boolean isTransitioning = z;
        this.mIsTransitioning = isTransitioning;
        setDescendantFocusability(!isTransitioning ? 262144 : 393216);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        return this.mIsTransitioning || super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        boolean onTouchEvent = super.onTouchEvent(ev);
        return true;
    }

    public void setTabContainer(ScrollingTabContainerView scrollingTabContainerView) {
        ScrollingTabContainerView tabView = scrollingTabContainerView;
        ScrollingTabContainerView scrollingTabContainerView2 = tabView;
        if (this.mTabContainer != null) {
            removeView(this.mTabContainer);
        }
        this.mTabContainer = tabView;
        if (tabView != null) {
            addView(tabView);
            LayoutParams layoutParams = tabView.getLayoutParams();
            LayoutParams lp = layoutParams;
            layoutParams.width = -1;
            lp.height = -2;
            tabView.setAllowCollapse(false);
        }
    }

    public View getTabContainer() {
        return this.mTabContainer;
    }

    public ActionMode startActionModeForChild(View view, Callback callback) {
        View view2 = view;
        Callback callback2 = callback;
        return null;
    }

    public ActionMode startActionModeForChild(View view, Callback callback, int i) {
        View child = view;
        Callback callback2 = callback;
        int type = i;
        View view2 = child;
        Callback callback3 = callback2;
        int i2 = type;
        if (type == 0) {
            return null;
        }
        return super.startActionModeForChild(child, callback2, type);
    }

    private boolean isCollapsed(View view) {
        View view2 = view;
        View view3 = view2;
        return view2 == null || view2.getVisibility() == 8 || view2.getMeasuredHeight() == 0;
    }

    private int getMeasuredHeightWithMargins(View view) {
        View view2 = view;
        View view3 = view2;
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view2.getLayoutParams();
        return view2.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
    }

    public void onMeasure(int i, int i2) {
        int topMarginForTabs;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int heightMeasureSpec2 = heightMeasureSpec;
        if (this.mActionBarView == null && MeasureSpec.getMode(heightMeasureSpec) == Integer.MIN_VALUE && this.mHeight >= 0) {
            heightMeasureSpec2 = MeasureSpec.makeMeasureSpec(Math.min(this.mHeight, MeasureSpec.getSize(heightMeasureSpec)), Integer.MIN_VALUE);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec2);
        if (this.mActionBarView != null) {
            int mode = MeasureSpec.getMode(heightMeasureSpec2);
            if (!(this.mTabContainer == null || this.mTabContainer.getVisibility() == 8 || mode == 1073741824)) {
                if (!isCollapsed(this.mActionBarView)) {
                    topMarginForTabs = getMeasuredHeightWithMargins(this.mActionBarView);
                } else if (isCollapsed(this.mContextView)) {
                    topMarginForTabs = 0;
                } else {
                    topMarginForTabs = getMeasuredHeightWithMargins(this.mContextView);
                }
                setMeasuredDimension(getMeasuredWidth(), Math.min(topMarginForTabs + getMeasuredHeightWithMargins(this.mTabContainer), mode != Integer.MIN_VALUE ? ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : MeasureSpec.getSize(heightMeasureSpec2)));
            }
        }
    }

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
        View view = this.mTabContainer;
        View tabContainer = view;
        boolean hasTabs = (view == null || tabContainer.getVisibility() == 8) ? false : true;
        if (!(tabContainer == null || tabContainer.getVisibility() == 8)) {
            int containerHeight = getMeasuredHeight();
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) tabContainer.getLayoutParams();
            int measuredHeight = tabContainer.getMeasuredHeight();
            int i9 = measuredHeight;
            tabContainer.layout(l, (containerHeight - measuredHeight) - lp.bottomMargin, r, containerHeight - lp.bottomMargin);
        }
        boolean needsInvalidate = false;
        if (!this.mIsSplit) {
            if (this.mBackground != null) {
                if (this.mActionBarView.getVisibility() == 0) {
                    this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
                } else if (this.mContextView != null && this.mContextView.getVisibility() == 0) {
                    this.mBackground.setBounds(this.mContextView.getLeft(), this.mContextView.getTop(), this.mContextView.getRight(), this.mContextView.getBottom());
                } else {
                    this.mBackground.setBounds(0, 0, 0, 0);
                }
                needsInvalidate = true;
            }
            this.mIsStacked = hasTabs;
            if (hasTabs && this.mStackedBackground != null) {
                this.mStackedBackground.setBounds(tabContainer.getLeft(), tabContainer.getTop(), tabContainer.getRight(), tabContainer.getBottom());
                needsInvalidate = true;
            }
        } else if (this.mSplitBackground != null) {
            this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            needsInvalidate = true;
        }
        if (needsInvalidate) {
            invalidate();
        }
    }
}
