package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewPropertyAnimatorCompat;
import android.support.p000v4.view.ViewPropertyAnimatorListener;
import android.support.p003v7.appcompat.C0268R;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

/* renamed from: android.support.v7.widget.AbsActionBarView */
abstract class AbsActionBarView extends ViewGroup {
    private static final int FADE_DURATION = 200;
    protected ActionMenuPresenter mActionMenuPresenter;
    protected int mContentHeight;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    protected ActionMenuView mMenuView;
    protected final Context mPopupContext;
    protected final VisibilityAnimListener mVisAnimListener;
    protected ViewPropertyAnimatorCompat mVisibilityAnim;

    /* renamed from: android.support.v7.widget.AbsActionBarView$VisibilityAnimListener */
    protected class VisibilityAnimListener implements ViewPropertyAnimatorListener {
        private boolean mCanceled = false;
        int mFinalVisibility;
        final /* synthetic */ AbsActionBarView this$0;

        protected VisibilityAnimListener(AbsActionBarView absActionBarView) {
            AbsActionBarView this$02 = absActionBarView;
            AbsActionBarView absActionBarView2 = this$02;
            this.this$0 = this$02;
        }

        public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, int i) {
            ViewPropertyAnimatorCompat animation = viewPropertyAnimatorCompat;
            int visibility = i;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = animation;
            int i2 = visibility;
            this.this$0.mVisibilityAnim = animation;
            this.mFinalVisibility = visibility;
            return this;
        }

        public void onAnimationStart(View view) {
            View view2 = view;
            AbsActionBarView.access$001(this.this$0, 0);
            this.mCanceled = false;
        }

        public void onAnimationEnd(View view) {
            View view2 = view;
            if (!this.mCanceled) {
                this.this$0.mVisibilityAnim = null;
                AbsActionBarView.access$101(this.this$0, this.mFinalVisibility);
            }
        }

        public void onAnimationCancel(View view) {
            View view2 = view;
            this.mCanceled = true;
        }
    }

    static /* synthetic */ void access$001(AbsActionBarView absActionBarView, int i) {
        AbsActionBarView x0 = absActionBarView;
        int x1 = i;
        AbsActionBarView absActionBarView2 = x0;
        int i2 = x1;
        super.setVisibility(x1);
    }

    static /* synthetic */ void access$101(AbsActionBarView absActionBarView, int i) {
        AbsActionBarView x0 = absActionBarView;
        int x1 = i;
        AbsActionBarView absActionBarView2 = x0;
        int i2 = x1;
        super.setVisibility(x1);
    }

    AbsActionBarView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyle = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyle;
        super(context2, attrs, defStyle);
        this.mVisAnimListener = new VisibilityAnimListener(this);
        TypedValue tv = new TypedValue();
        if (context2.getTheme().resolveAttribute(C0268R.attr.actionBarPopupTheme, tv, true) && tv.resourceId != 0) {
            this.mPopupContext = new ContextThemeWrapper(context2, tv.resourceId);
            return;
        }
        this.mPopupContext = context2;
    }

    AbsActionBarView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    AbsActionBarView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        Configuration configuration2 = newConfig;
        super.onConfigurationChanged(newConfig);
        TypedArray a = getContext().obtainStyledAttributes(null, C0268R.styleable.ActionBar, C0268R.attr.actionBarStyle, 0);
        setContentHeight(a.getLayoutDimension(C0268R.styleable.ActionBar_height, 0));
        a.recycle();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.onConfigurationChanged(newConfig);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        int action = actionMasked;
        if (actionMasked == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean handled = super.onTouchEvent(ev);
            if (action == 0 && !handled) {
                this.mEatingTouch = true;
            }
        }
        if (action == 1 || action == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        int action = actionMasked;
        if (actionMasked == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean handled = super.onHoverEvent(ev);
            if (action == 9 && !handled) {
                this.mEatingHover = true;
            }
        }
        if (action == 10 || action == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    public void setContentHeight(int i) {
        int height = i;
        int i2 = height;
        this.mContentHeight = height;
        requestLayout();
    }

    public int getContentHeight() {
        return this.mContentHeight;
    }

    public int getAnimatedVisibility() {
        if (this.mVisibilityAnim == null) {
            return getVisibility();
        }
        return this.mVisAnimListener.mFinalVisibility;
    }

    public ViewPropertyAnimatorCompat setupAnimatorToVisibility(int i, long j) {
        int visibility = i;
        long duration = j;
        int i2 = visibility;
        long j2 = duration;
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel();
        }
        if (visibility != 0) {
            ViewPropertyAnimatorCompat alpha = ViewCompat.animate(this).alpha(0.0f);
            ViewPropertyAnimatorCompat anim = alpha;
            ViewPropertyAnimatorCompat duration2 = alpha.setDuration(duration);
            ViewPropertyAnimatorCompat listener = anim.setListener(this.mVisAnimListener.withFinalVisibility(anim, visibility));
            return anim;
        }
        if (getVisibility() != 0) {
            ViewCompat.setAlpha(this, 0.0f);
        }
        ViewPropertyAnimatorCompat alpha2 = ViewCompat.animate(this).alpha(1.0f);
        ViewPropertyAnimatorCompat anim2 = alpha2;
        ViewPropertyAnimatorCompat duration3 = alpha2.setDuration(duration);
        ViewPropertyAnimatorCompat listener2 = anim2.setListener(this.mVisAnimListener.withFinalVisibility(anim2, visibility));
        return anim2;
    }

    public void animateToVisibility(int i) {
        int visibility = i;
        int i2 = visibility;
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = setupAnimatorToVisibility(visibility, 200);
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
        viewPropertyAnimatorCompat.start();
    }

    public void setVisibility(int i) {
        int visibility = i;
        int i2 = visibility;
        if (visibility != getVisibility()) {
            if (this.mVisibilityAnim != null) {
                this.mVisibilityAnim.cancel();
            }
            super.setVisibility(visibility);
        }
    }

    public boolean showOverflowMenu() {
        if (this.mActionMenuPresenter == null) {
            return false;
        }
        return this.mActionMenuPresenter.showOverflowMenu();
    }

    public void postShowOverflowMenu() {
        boolean post = post(new Runnable(this) {
            final /* synthetic */ AbsActionBarView this$0;

            {
                AbsActionBarView this$02 = r5;
                AbsActionBarView absActionBarView = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                boolean showOverflowMenu = this.this$0.showOverflowMenu();
            }
        });
    }

    public boolean hideOverflowMenu() {
        if (this.mActionMenuPresenter == null) {
            return false;
        }
        return this.mActionMenuPresenter.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        if (this.mActionMenuPresenter == null) {
            return false;
        }
        return this.mActionMenuPresenter.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        if (this.mActionMenuPresenter == null) {
            return false;
        }
        return this.mActionMenuPresenter.isOverflowMenuShowPending();
    }

    public boolean isOverflowReserved() {
        return this.mActionMenuPresenter != null && this.mActionMenuPresenter.isOverflowReserved();
    }

    public boolean canShowOverflowMenu() {
        return isOverflowReserved() && getVisibility() == 0;
    }

    public void dismissPopupMenus() {
        if (this.mActionMenuPresenter != null) {
            boolean dismissPopupMenus = this.mActionMenuPresenter.dismissPopupMenus();
        }
    }

    /* access modifiers changed from: protected */
    public int measureChildView(View view, int i, int i2, int i3) {
        View child = view;
        int availableWidth = i;
        int childSpecHeight = i2;
        int spacing = i3;
        View view2 = child;
        int i4 = availableWidth;
        int i5 = childSpecHeight;
        int i6 = spacing;
        child.measure(MeasureSpec.makeMeasureSpec(availableWidth, Integer.MIN_VALUE), childSpecHeight);
        int measuredWidth = availableWidth - child.getMeasuredWidth();
        int availableWidth2 = measuredWidth;
        return Math.max(0, measuredWidth - spacing);
    }

    protected static int next(int i, int i2, boolean z) {
        int x = i;
        int val = i2;
        int i3 = x;
        int i4 = val;
        return !z ? x + val : x - val;
    }

    /* access modifiers changed from: protected */
    public int positionChild(View view, int i, int i2, int i3, boolean z) {
        int i4;
        View child = view;
        int x = i;
        int y = i2;
        int contentHeight = i3;
        View view2 = child;
        int i5 = x;
        int i6 = y;
        int i7 = contentHeight;
        boolean reverse = z;
        int childWidth = child.getMeasuredWidth();
        int childHeight = child.getMeasuredHeight();
        int childTop = y + ((contentHeight - childHeight) / 2);
        if (!reverse) {
            child.layout(x, childTop, x + childWidth, childTop + childHeight);
        } else {
            child.layout(x - childWidth, childTop, x, childTop + childHeight);
        }
        if (!reverse) {
            i4 = childWidth;
        } else {
            i4 = -childWidth;
        }
        return i4;
    }
}
