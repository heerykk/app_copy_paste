package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.C0001R;
import android.support.p000v4.util.Pools.Pool;
import android.support.p000v4.util.Pools.SynchronizedPool;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.support.p003v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

@RestrictTo({Scope.LIBRARY_GROUP})
public class BottomNavigationMenuView extends ViewGroup implements MenuView {
    private static final Pool<BottomNavigationItemView> sItemPool = new SynchronizedPool(5);
    private int mActiveButton;
    private final int mActiveItemMaxWidth;
    private final BottomNavigationAnimationHelperBase mAnimationHelper;
    private BottomNavigationItemView[] mButtons;
    private final int mInactiveItemMaxWidth;
    private final int mInactiveItemMinWidth;
    private int mItemBackgroundRes;
    private final int mItemHeight;
    private ColorStateList mItemIconTint;
    private ColorStateList mItemTextColor;
    private MenuBuilder mMenu;
    private final OnClickListener mOnClickListener;
    private BottomNavigationPresenter mPresenter;
    private boolean mShiftingMode;
    private int[] mTempChildWidths;

    static /* synthetic */ BottomNavigationPresenter access$000(BottomNavigationMenuView bottomNavigationMenuView) {
        BottomNavigationMenuView x0 = bottomNavigationMenuView;
        BottomNavigationMenuView bottomNavigationMenuView2 = x0;
        return x0.mPresenter;
    }

    static /* synthetic */ MenuBuilder access$100(BottomNavigationMenuView bottomNavigationMenuView) {
        BottomNavigationMenuView x0 = bottomNavigationMenuView;
        BottomNavigationMenuView bottomNavigationMenuView2 = x0;
        return x0.mMenu;
    }

    static /* synthetic */ void access$200(BottomNavigationMenuView bottomNavigationMenuView, int i) {
        BottomNavigationMenuView x0 = bottomNavigationMenuView;
        int x1 = i;
        BottomNavigationMenuView bottomNavigationMenuView2 = x0;
        int i2 = x1;
        x0.activateNewButton(x1);
    }

    public BottomNavigationMenuView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        this.mShiftingMode = true;
        this.mActiveButton = 0;
        Resources res = getResources();
        this.mInactiveItemMaxWidth = res.getDimensionPixelSize(C0001R.dimen.design_bottom_navigation_item_max_width);
        this.mInactiveItemMinWidth = res.getDimensionPixelSize(C0001R.dimen.design_bottom_navigation_item_min_width);
        this.mActiveItemMaxWidth = res.getDimensionPixelSize(C0001R.dimen.design_bottom_navigation_active_item_max_width);
        this.mItemHeight = res.getDimensionPixelSize(C0001R.dimen.design_bottom_navigation_height);
        if (VERSION.SDK_INT < 14) {
            this.mAnimationHelper = new BottomNavigationAnimationHelperBase();
        } else {
            this.mAnimationHelper = new BottomNavigationAnimationHelperIcs();
        }
        this.mOnClickListener = new OnClickListener(this) {
            final /* synthetic */ BottomNavigationMenuView this$0;

            {
                BottomNavigationMenuView this$02 = r5;
                BottomNavigationMenuView bottomNavigationMenuView = this$02;
                this.this$0 = this$02;
            }

            public void onClick(View view) {
                View v = view;
                View view2 = v;
                BottomNavigationItemView bottomNavigationItemView = (BottomNavigationItemView) v;
                BottomNavigationItemView itemView = bottomNavigationItemView;
                int itemPosition = bottomNavigationItemView.getItemPosition();
                if (!BottomNavigationMenuView.access$100(this.this$0).performItemAction(itemView.getItemData(), BottomNavigationMenuView.access$000(this.this$0), 0)) {
                    BottomNavigationMenuView.access$200(this.this$0, itemPosition);
                }
            }
        };
        this.mTempChildWidths = new int[5];
    }

    public BottomNavigationMenuView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public void initialize(MenuBuilder menuBuilder) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        this.mMenu = menu;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int i3 = widthMeasureSpec;
        int i4 = i2;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int count = getChildCount();
        int heightSpec = MeasureSpec.makeMeasureSpec(this.mItemHeight, 1073741824);
        if (!this.mShiftingMode) {
            int i5 = width / (count != 0 ? count : 1);
            int i6 = i5;
            int childWidth = Math.min(i5, this.mActiveItemMaxWidth);
            int extra = width - (childWidth * count);
            for (int i7 = 0; i7 < count; i7++) {
                this.mTempChildWidths[i7] = childWidth;
                if (extra > 0) {
                    int[] iArr = this.mTempChildWidths;
                    iArr[i7] = iArr[i7] + 1;
                    extra--;
                }
            }
        } else {
            int inactiveCount = count - 1;
            int i8 = width - (inactiveCount * this.mInactiveItemMinWidth);
            int i9 = i8;
            int activeWidth = Math.min(i8, this.mActiveItemMaxWidth);
            int i10 = (width - activeWidth) / inactiveCount;
            int i11 = i10;
            int inactiveWidth = Math.min(i10, this.mInactiveItemMaxWidth);
            int extra2 = (width - activeWidth) - (inactiveWidth * inactiveCount);
            int i12 = 0;
            while (i12 < count) {
                this.mTempChildWidths[i12] = i12 != this.mActiveButton ? inactiveWidth : activeWidth;
                if (extra2 > 0) {
                    int[] iArr2 = this.mTempChildWidths;
                    iArr2[i12] = iArr2[i12] + 1;
                    extra2--;
                }
                i12++;
            }
        }
        int totalWidth = 0;
        for (int i13 = 0; i13 < count; i13++) {
            View childAt = getChildAt(i13);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                child.measure(MeasureSpec.makeMeasureSpec(this.mTempChildWidths[i13], 1073741824), heightSpec);
                LayoutParams layoutParams = child.getLayoutParams();
                LayoutParams layoutParams2 = layoutParams;
                layoutParams.width = child.getMeasuredWidth();
                totalWidth += child.getMeasuredWidth();
            }
        }
        setMeasuredDimension(ViewCompat.resolveSizeAndState(totalWidth, MeasureSpec.makeMeasureSpec(totalWidth, 1073741824), 0), ViewCompat.resolveSizeAndState(this.mItemHeight, heightSpec, 0));
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
        int width = right - left;
        int height = bottom - top;
        int used = 0;
        for (int i9 = 0; i9 < count; i9++) {
            View childAt = getChildAt(i9);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                if (ViewCompat.getLayoutDirection(this) != 1) {
                    child.layout(used, 0, child.getMeasuredWidth() + used, height);
                } else {
                    child.layout((width - used) - child.getMeasuredWidth(), 0, width - used, height);
                }
                used += child.getMeasuredWidth();
            }
        }
    }

    public int getWindowAnimations() {
        return 0;
    }

    public void setIconTintList(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        this.mItemIconTint = tint;
        if (this.mButtons != null) {
            BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
            BottomNavigationItemView[] bottomNavigationItemViewArr2 = bottomNavigationItemViewArr;
            int length = bottomNavigationItemViewArr.length;
            for (int i = 0; i < length; i++) {
                BottomNavigationItemView bottomNavigationItemView = bottomNavigationItemViewArr2[i];
                BottomNavigationItemView bottomNavigationItemView2 = bottomNavigationItemView;
                bottomNavigationItemView.setIconTintList(tint);
            }
        }
    }

    @Nullable
    public ColorStateList getIconTintList() {
        return this.mItemIconTint;
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        ColorStateList color = colorStateList;
        ColorStateList colorStateList2 = color;
        this.mItemTextColor = color;
        if (this.mButtons != null) {
            BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
            BottomNavigationItemView[] bottomNavigationItemViewArr2 = bottomNavigationItemViewArr;
            int length = bottomNavigationItemViewArr.length;
            for (int i = 0; i < length; i++) {
                BottomNavigationItemView bottomNavigationItemView = bottomNavigationItemViewArr2[i];
                BottomNavigationItemView bottomNavigationItemView2 = bottomNavigationItemView;
                bottomNavigationItemView.setTextColor(color);
            }
        }
    }

    public ColorStateList getItemTextColor() {
        return this.mItemTextColor;
    }

    public void setItemBackgroundRes(int i) {
        int background = i;
        int i2 = background;
        this.mItemBackgroundRes = background;
        if (this.mButtons != null) {
            BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
            BottomNavigationItemView[] bottomNavigationItemViewArr2 = bottomNavigationItemViewArr;
            int length = bottomNavigationItemViewArr.length;
            for (int i3 = 0; i3 < length; i3++) {
                BottomNavigationItemView bottomNavigationItemView = bottomNavigationItemViewArr2[i3];
                BottomNavigationItemView bottomNavigationItemView2 = bottomNavigationItemView;
                bottomNavigationItemView.setItemBackground(background);
            }
        }
    }

    public int getItemBackgroundRes() {
        return this.mItemBackgroundRes;
    }

    public void setPresenter(BottomNavigationPresenter bottomNavigationPresenter) {
        BottomNavigationPresenter presenter = bottomNavigationPresenter;
        BottomNavigationPresenter bottomNavigationPresenter2 = presenter;
        this.mPresenter = presenter;
    }

    public void buildMenuView() {
        if (this.mButtons != null) {
            BottomNavigationItemView[] bottomNavigationItemViewArr = this.mButtons;
            BottomNavigationItemView[] bottomNavigationItemViewArr2 = bottomNavigationItemViewArr;
            int length = bottomNavigationItemViewArr.length;
            for (int i = 0; i < length; i++) {
                boolean release = sItemPool.release(bottomNavigationItemViewArr2[i]);
            }
        }
        removeAllViews();
        if (this.mMenu.size() != 0) {
            this.mButtons = new BottomNavigationItemView[this.mMenu.size()];
            this.mShiftingMode = this.mMenu.size() > 3;
            for (int i2 = 0; i2 < this.mMenu.size(); i2++) {
                this.mPresenter.setUpdateSuspended(true);
                MenuItem checkable = this.mMenu.getItem(i2).setCheckable(true);
                this.mPresenter.setUpdateSuspended(false);
                BottomNavigationItemView child = getNewItem();
                this.mButtons[i2] = child;
                child.setIconTintList(this.mItemIconTint);
                child.setTextColor(this.mItemTextColor);
                child.setItemBackground(this.mItemBackgroundRes);
                child.setShiftingMode(this.mShiftingMode);
                child.initialize((MenuItemImpl) this.mMenu.getItem(i2), 0);
                child.setItemPosition(i2);
                child.setOnClickListener(this.mOnClickListener);
                addView(child);
            }
            this.mActiveButton = Math.min(this.mMenu.size() - 1, this.mActiveButton);
            MenuItem checked = this.mMenu.getItem(this.mActiveButton).setChecked(true);
            return;
        }
        this.mButtons = null;
    }

    public void updateMenuView() {
        int size = this.mMenu.size();
        int menuSize = size;
        if (size == this.mButtons.length) {
            for (int i = 0; i < menuSize; i++) {
                this.mPresenter.setUpdateSuspended(true);
                if (this.mMenu.getItem(i).isChecked()) {
                    this.mActiveButton = i;
                }
                this.mButtons[i].initialize((MenuItemImpl) this.mMenu.getItem(i), 0);
                this.mPresenter.setUpdateSuspended(false);
            }
            return;
        }
        buildMenuView();
    }

    private void activateNewButton(int i) {
        int newButton = i;
        int i2 = newButton;
        if (this.mActiveButton != newButton) {
            this.mAnimationHelper.beginDelayedTransition(this);
            MenuItem checked = this.mMenu.getItem(newButton).setChecked(true);
            this.mActiveButton = newButton;
        }
    }

    private BottomNavigationItemView getNewItem() {
        BottomNavigationItemView bottomNavigationItemView = (BottomNavigationItemView) sItemPool.acquire();
        BottomNavigationItemView item = bottomNavigationItemView;
        if (bottomNavigationItemView == null) {
            item = new BottomNavigationItemView(getContext());
        }
        return item;
    }
}
