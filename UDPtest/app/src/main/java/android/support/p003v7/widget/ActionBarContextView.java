package android.support.p003v7.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewPropertyAnimatorCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.ActionMode;
import android.support.p003v7.view.menu.MenuBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ActionBarContextView */
public class ActionBarContextView extends AbsActionBarView {
    private static final String TAG = "ActionBarContextView";
    private View mClose;
    private int mCloseItemLayout;
    private View mCustomView;
    private CharSequence mSubtitle;
    private int mSubtitleStyleRes;
    private TextView mSubtitleView;
    private CharSequence mTitle;
    private LinearLayout mTitleLayout;
    private boolean mTitleOptional;
    private int mTitleStyleRes;
    private TextView mTitleView;

    public /* bridge */ /* synthetic */ void animateToVisibility(int i) {
        super.animateToVisibility(i);
    }

    public /* bridge */ /* synthetic */ boolean canShowOverflowMenu() {
        return super.canShowOverflowMenu();
    }

    public /* bridge */ /* synthetic */ void dismissPopupMenus() {
        super.dismissPopupMenus();
    }

    public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
        return super.getAnimatedVisibility();
    }

    public /* bridge */ /* synthetic */ int getContentHeight() {
        return super.getContentHeight();
    }

    public /* bridge */ /* synthetic */ boolean isOverflowMenuShowPending() {
        return super.isOverflowMenuShowPending();
    }

    public /* bridge */ /* synthetic */ boolean isOverflowReserved() {
        return super.isOverflowReserved();
    }

    public /* bridge */ /* synthetic */ boolean onHoverEvent(MotionEvent motionEvent) {
        return super.onHoverEvent(motionEvent);
    }

    public /* bridge */ /* synthetic */ boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    public /* bridge */ /* synthetic */ void postShowOverflowMenu() {
        super.postShowOverflowMenu();
    }

    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    public /* bridge */ /* synthetic */ ViewPropertyAnimatorCompat setupAnimatorToVisibility(int i, long j) {
        return super.setupAnimatorToVisibility(i, j);
    }

    public ActionBarContextView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, C0268R.attr.actionModeStyle);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyle = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyle;
        super(context2, attrs, defStyle);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context2, attrs, C0268R.styleable.ActionMode, defStyle, 0);
        ViewCompat.setBackground(this, a.getDrawable(C0268R.styleable.ActionMode_background));
        this.mTitleStyleRes = a.getResourceId(C0268R.styleable.ActionMode_titleTextStyle, 0);
        this.mSubtitleStyleRes = a.getResourceId(C0268R.styleable.ActionMode_subtitleTextStyle, 0);
        this.mContentHeight = a.getLayoutDimension(C0268R.styleable.ActionMode_height, 0);
        this.mCloseItemLayout = a.getResourceId(C0268R.styleable.ActionMode_closeItemLayout, C0268R.layout.abc_action_mode_close_item_material);
        a.recycle();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mActionMenuPresenter != null) {
            boolean hideOverflowMenu = this.mActionMenuPresenter.hideOverflowMenu();
            boolean hideSubMenus = this.mActionMenuPresenter.hideSubMenus();
        }
    }

    public void setContentHeight(int i) {
        int height = i;
        int i2 = height;
        this.mContentHeight = height;
    }

    public void setCustomView(View view) {
        View view2 = view;
        View view3 = view2;
        if (this.mCustomView != null) {
            removeView(this.mCustomView);
        }
        this.mCustomView = view2;
        if (!(view2 == null || this.mTitleLayout == null)) {
            removeView(this.mTitleLayout);
            this.mTitleLayout = null;
        }
        if (view2 != null) {
            addView(view2);
        }
        requestLayout();
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mTitle = title;
        initTitle();
    }

    public void setSubtitle(CharSequence charSequence) {
        CharSequence subtitle = charSequence;
        CharSequence charSequence2 = subtitle;
        this.mSubtitle = subtitle;
        initTitle();
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    private void initTitle() {
        if (this.mTitleLayout == null) {
            LayoutInflater from = LayoutInflater.from(getContext());
            LayoutInflater layoutInflater = from;
            View inflate = from.inflate(C0268R.layout.abc_action_bar_title_item, this);
            this.mTitleLayout = (LinearLayout) getChildAt(getChildCount() - 1);
            this.mTitleView = (TextView) this.mTitleLayout.findViewById(C0268R.C0270id.action_bar_title);
            this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(C0268R.C0270id.action_bar_subtitle);
            if (this.mTitleStyleRes != 0) {
                this.mTitleView.setTextAppearance(getContext(), this.mTitleStyleRes);
            }
            if (this.mSubtitleStyleRes != 0) {
                this.mSubtitleView.setTextAppearance(getContext(), this.mSubtitleStyleRes);
            }
        }
        this.mTitleView.setText(this.mTitle);
        this.mSubtitleView.setText(this.mSubtitle);
        boolean hasTitle = !TextUtils.isEmpty(this.mTitle);
        boolean hasSubtitle = !TextUtils.isEmpty(this.mSubtitle);
        this.mSubtitleView.setVisibility(!hasSubtitle ? 8 : 0);
        this.mTitleLayout.setVisibility((!hasTitle && !hasSubtitle) ? 8 : 0);
        if (this.mTitleLayout.getParent() == null) {
            addView(this.mTitleLayout);
        }
    }

    public void initForMode(ActionMode actionMode) {
        ActionMode mode = actionMode;
        ActionMode actionMode2 = mode;
        if (this.mClose == null) {
            this.mClose = LayoutInflater.from(getContext()).inflate(this.mCloseItemLayout, this, false);
            addView(this.mClose);
        } else if (this.mClose.getParent() == null) {
            addView(this.mClose);
        }
        View findViewById = this.mClose.findViewById(C0268R.C0270id.action_mode_close_button);
        View view = findViewById;
        final ActionMode actionMode3 = mode;
        findViewById.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ActionBarContextView this$0;

            {
                ActionBarContextView this$02 = r6;
                ActionMode actionMode = r7;
                ActionBarContextView actionBarContextView = this$02;
                this.this$0 = this$02;
            }

            public void onClick(View view) {
                View view2 = view;
                actionMode3.finish();
            }
        });
        MenuBuilder menu = (MenuBuilder) mode.getMenu();
        if (this.mActionMenuPresenter != null) {
            boolean dismissPopupMenus = this.mActionMenuPresenter.dismissPopupMenus();
        }
        this.mActionMenuPresenter = new ActionMenuPresenter(getContext());
        this.mActionMenuPresenter.setReserveOverflow(true);
        LayoutParams layoutParams = new LayoutParams(-2, -1);
        menu.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
        this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
        ViewCompat.setBackground(this.mMenuView, null);
        addView(this.mMenuView, layoutParams);
    }

    public void closeMode() {
        if (this.mClose == null) {
            killMode();
        }
    }

    public void killMode() {
        removeAllViews();
        this.mCustomView = null;
        this.mMenuView = null;
    }

    public boolean showOverflowMenu() {
        if (this.mActionMenuPresenter == null) {
            return false;
        }
        return this.mActionMenuPresenter.showOverflowMenu();
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

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(-1, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AttributeSet attrs = attributeSet;
        AttributeSet attributeSet2 = attrs;
        return new MarginLayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i4 = widthMeasureSpec;
        int i5 = heightMeasureSpec;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int i6 = mode;
        if (mode == 1073741824) {
            int mode2 = MeasureSpec.getMode(heightMeasureSpec);
            int i7 = mode2;
            if (mode2 != 0) {
                int contentWidth = MeasureSpec.getSize(widthMeasureSpec);
                if (this.mContentHeight <= 0) {
                    i3 = MeasureSpec.getSize(heightMeasureSpec);
                } else {
                    i3 = this.mContentHeight;
                }
                int maxHeight = i3;
                int verticalPadding = getPaddingTop() + getPaddingBottom();
                int availableWidth = (contentWidth - getPaddingLeft()) - getPaddingRight();
                int i8 = maxHeight - verticalPadding;
                int height = i8;
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(i8, Integer.MIN_VALUE);
                int childSpecHeight = makeMeasureSpec;
                if (this.mClose != null) {
                    int measureChildView = measureChildView(this.mClose, availableWidth, makeMeasureSpec, 0);
                    int availableWidth2 = measureChildView;
                    MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mClose.getLayoutParams();
                    MarginLayoutParams marginLayoutParams2 = marginLayoutParams;
                    availableWidth = measureChildView - (marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
                }
                if (this.mMenuView != null && this.mMenuView.getParent() == this) {
                    availableWidth = measureChildView(this.mMenuView, availableWidth, childSpecHeight, 0);
                }
                if (this.mTitleLayout != null && this.mCustomView == null) {
                    if (!this.mTitleOptional) {
                        availableWidth = measureChildView(this.mTitleLayout, availableWidth, childSpecHeight, 0);
                    } else {
                        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(0, 0);
                        int i9 = makeMeasureSpec2;
                        this.mTitleLayout.measure(makeMeasureSpec2, childSpecHeight);
                        int measuredWidth = this.mTitleLayout.getMeasuredWidth();
                        int titleWidth = measuredWidth;
                        boolean titleFits = measuredWidth <= availableWidth;
                        if (titleFits) {
                            availableWidth -= titleWidth;
                        }
                        this.mTitleLayout.setVisibility(!titleFits ? 8 : 0);
                    }
                }
                if (this.mCustomView != null) {
                    LayoutParams layoutParams = this.mCustomView.getLayoutParams();
                    LayoutParams lp = layoutParams;
                    int customWidthMode = layoutParams.width == -2 ? Integer.MIN_VALUE : 1073741824;
                    this.mCustomView.measure(MeasureSpec.makeMeasureSpec(lp.width < 0 ? availableWidth : Math.min(lp.width, availableWidth), customWidthMode), MeasureSpec.makeMeasureSpec(lp.height < 0 ? height : Math.min(lp.height, height), lp.height == -2 ? Integer.MIN_VALUE : 1073741824));
                }
                if (this.mContentHeight > 0) {
                    setMeasuredDimension(contentWidth, maxHeight);
                    return;
                }
                int measuredHeight = 0;
                int count = getChildCount();
                for (int i10 = 0; i10 < count; i10++) {
                    View childAt = getChildAt(i10);
                    View view = childAt;
                    int measuredHeight2 = childAt.getMeasuredHeight() + verticalPadding;
                    int paddedViewHeight = measuredHeight2;
                    if (measuredHeight2 > measuredHeight) {
                        measuredHeight = paddedViewHeight;
                    }
                }
                setMeasuredDimension(contentWidth, measuredHeight);
                return;
            }
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used " + "with android:layout_height=\"wrap_content\"");
        }
        throw new IllegalStateException(getClass().getSimpleName() + " can only be used " + "with android:layout_width=\"match_parent\" (or fill_parent)");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int l = i;
        int t = i2;
        int r = i3;
        int b = i4;
        boolean z2 = z;
        int i5 = l;
        int i6 = t;
        int i7 = r;
        int i8 = b;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        boolean isLayoutRtl2 = isLayoutRtl;
        int x = !isLayoutRtl ? getPaddingLeft() : (r - l) - getPaddingRight();
        int y = getPaddingTop();
        int contentHeight = ((b - t) - getPaddingTop()) - getPaddingBottom();
        if (!(this.mClose == null || this.mClose.getVisibility() == 8)) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mClose.getLayoutParams();
            MarginLayoutParams lp = marginLayoutParams;
            int endMargin = !isLayoutRtl2 ? lp.rightMargin : lp.leftMargin;
            int next = next(x, !isLayoutRtl2 ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin, isLayoutRtl2);
            int positionChild = next + positionChild(this.mClose, next, y, contentHeight, isLayoutRtl2);
            int x2 = positionChild;
            x = next(positionChild, endMargin, isLayoutRtl2);
        }
        if (!(this.mTitleLayout == null || this.mCustomView != null || this.mTitleLayout.getVisibility() == 8)) {
            x += positionChild(this.mTitleLayout, x, y, contentHeight, isLayoutRtl2);
        }
        if (this.mCustomView != null) {
            int x3 = x + positionChild(this.mCustomView, x, y, contentHeight, isLayoutRtl2);
        }
        int x4 = !isLayoutRtl2 ? (r - l) - getPaddingRight() : getPaddingLeft();
        if (this.mMenuView != null) {
            int x5 = x4 + positionChild(this.mMenuView, x4, y, contentHeight, !isLayoutRtl2);
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        if (VERSION.SDK_INT >= 14) {
            if (event.getEventType() != 32) {
                super.onInitializeAccessibilityEvent(event);
                return;
            }
            event.setSource(this);
            event.setClassName(getClass().getName());
            event.setPackageName(getContext().getPackageName());
            event.setContentDescription(this.mTitle);
        }
    }

    public void setTitleOptional(boolean z) {
        boolean titleOptional = z;
        if (titleOptional != this.mTitleOptional) {
            requestLayout();
        }
        this.mTitleOptional = titleOptional;
    }

    public boolean isTitleOptional() {
        return this.mTitleOptional;
    }
}
