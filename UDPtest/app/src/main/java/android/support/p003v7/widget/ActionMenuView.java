package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.p003v7.view.menu.ActionMenuItemView;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuBuilder.ItemInvoker;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.support.p003v7.view.menu.MenuPresenter.Callback;
import android.support.p003v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.ExportedProperty;
import android.view.accessibility.AccessibilityEvent;

/* renamed from: android.support.v7.widget.ActionMenuView */
public class ActionMenuView extends LinearLayoutCompat implements ItemInvoker, MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.widget.ActionMenuView$ActionMenuChildView */
    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$ActionMenuPresenterCallback */
    private class ActionMenuPresenterCallback implements Callback {
        final /* synthetic */ ActionMenuView this$0;

        ActionMenuPresenterCallback(ActionMenuView actionMenuView) {
            this.this$0 = actionMenuView;
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder menuBuilder2 = menuBuilder;
            boolean z2 = z;
        }

        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            MenuBuilder menuBuilder2 = menuBuilder;
            return false;
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$LayoutParams */
    public static class LayoutParams extends android.support.p003v7.widget.LinearLayoutCompat.LayoutParams {
        @ExportedProperty
        public int cellsUsed;
        @ExportedProperty
        public boolean expandable;
        boolean expanded;
        @ExportedProperty
        public int extraPixels;
        @ExportedProperty
        public boolean isOverflowButton;
        @ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            Context c = context;
            AttributeSet attrs = attributeSet;
            Context context2 = c;
            AttributeSet attributeSet2 = attrs;
            super(c, attrs);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.view.ViewGroup.LayoutParams other = layoutParams;
            android.view.ViewGroup.LayoutParams layoutParams2 = other;
            super(other);
        }

        public LayoutParams(LayoutParams layoutParams) {
            LayoutParams other = layoutParams;
            LayoutParams layoutParams2 = other;
            super((android.view.ViewGroup.LayoutParams) other);
            this.isOverflowButton = other.isOverflowButton;
        }

        public LayoutParams(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            super(width, height);
            this.isOverflowButton = false;
        }

        LayoutParams(int i, int i2, boolean z) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            boolean isOverflowButton2 = z;
            super(width, height);
            this.isOverflowButton = isOverflowButton2;
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$MenuBuilderCallback */
    private class MenuBuilderCallback implements MenuBuilder.Callback {
        final /* synthetic */ ActionMenuView this$0;

        MenuBuilderCallback(ActionMenuView actionMenuView) {
            this.this$0 = actionMenuView;
        }

        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuBuilder menuBuilder2 = menuBuilder;
            MenuItem menuItem2 = item;
            return this.this$0.mOnMenuItemClickListener != null && this.this$0.mOnMenuItemClickListener.onMenuItemClick(item);
        }

        public void onMenuModeChange(MenuBuilder menuBuilder) {
            MenuBuilder menu = menuBuilder;
            MenuBuilder menuBuilder2 = menu;
            if (this.this$0.mMenuBuilderCallback != null) {
                this.this$0.mMenuBuilderCallback.onMenuModeChange(menu);
            }
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuView$OnMenuItemClickListener */
    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public ActionMenuView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        setBaselineAligned(false);
        float f = context2.getResources().getDisplayMetrics().density;
        float f2 = f;
        this.mMinCellSize = (int) (56.0f * f);
        this.mGeneratedItemPadding = (int) (4.0f * f);
        this.mPopupContext = context2;
        this.mPopupTheme = 0;
    }

    public void setPopupTheme(@StyleRes int i) {
        int resId = i;
        int i2 = resId;
        if (this.mPopupTheme != resId) {
            this.mPopupTheme = resId;
            if (resId != 0) {
                this.mPopupContext = new ContextThemeWrapper(getContext(), resId);
            } else {
                this.mPopupContext = getContext();
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        ActionMenuPresenter presenter = actionMenuPresenter;
        ActionMenuPresenter actionMenuPresenter2 = presenter;
        this.mPresenter = presenter;
        this.mPresenter.setMenuView(this);
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        Configuration configuration2 = newConfig;
        super.onConfigurationChanged(newConfig);
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                boolean hideOverflowMenu = this.mPresenter.hideOverflowMenu();
                boolean showOverflowMenu = this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        OnMenuItemClickListener listener = onMenuItemClickListener;
        OnMenuItemClickListener onMenuItemClickListener2 = listener;
        this.mOnMenuItemClickListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        boolean z = this.mFormatItems;
        boolean z2 = z;
        this.mFormatItems = MeasureSpec.getMode(widthMeasureSpec) == 1073741824;
        if (z != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (!(!this.mFormatItems || this.mMenu == null || widthSize == this.mFormatItemsWidth)) {
            this.mFormatItemsWidth = widthSize;
            this.mMenu.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (this.mFormatItems && childCount > 0) {
            onMeasureExactFormat(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            View view = childAt;
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            LayoutParams lp = layoutParams;
            LayoutParams layoutParams2 = layoutParams;
            lp.rightMargin = 0;
            layoutParams2.leftMargin = 0;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void onMeasureExactFormat(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthPadding = getPaddingLeft() + getPaddingRight();
        int heightPadding = getPaddingTop() + getPaddingBottom();
        int itemHeightSpec = getChildMeasureSpec(heightMeasureSpec, heightPadding, -2);
        int i5 = widthSize - widthPadding;
        int widthSize2 = i5;
        int cellCount = i5 / this.mMinCellSize;
        int cellSizeRemaining = widthSize2 % this.mMinCellSize;
        if (cellCount != 0) {
            int cellSize = this.mMinCellSize + (cellSizeRemaining / cellCount);
            int cellsRemaining = cellCount;
            int maxChildHeight = 0;
            int maxCellsUsed = 0;
            int expandableItemCount = 0;
            int visibleItemCount = 0;
            boolean hasOverflow = false;
            long smallestItemsAt = 0;
            int childCount = getChildCount();
            for (int i6 = 0; i6 < childCount; i6++) {
                View childAt = getChildAt(i6);
                View child = childAt;
                if (childAt.getVisibility() != 8) {
                    boolean z = child instanceof ActionMenuItemView;
                    boolean isGeneratedItem = z;
                    visibleItemCount++;
                    if (z) {
                        child.setPadding(this.mGeneratedItemPadding, 0, this.mGeneratedItemPadding, 0);
                    }
                    LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                    LayoutParams lp = layoutParams;
                    layoutParams.expanded = false;
                    lp.extraPixels = 0;
                    lp.cellsUsed = 0;
                    lp.expandable = false;
                    lp.leftMargin = 0;
                    lp.rightMargin = 0;
                    lp.preventEdgeOffset = isGeneratedItem && ((ActionMenuItemView) child).hasText();
                    int cellsUsed = measureChildForCells(child, cellSize, !lp.isOverflowButton ? cellsRemaining : 1, itemHeightSpec, heightPadding);
                    maxCellsUsed = Math.max(maxCellsUsed, cellsUsed);
                    if (lp.expandable) {
                        expandableItemCount++;
                    }
                    if (lp.isOverflowButton) {
                        hasOverflow = true;
                    }
                    cellsRemaining -= cellsUsed;
                    maxChildHeight = Math.max(maxChildHeight, child.getMeasuredHeight());
                    if (cellsUsed == 1) {
                        smallestItemsAt |= (long) (1 << i6);
                    }
                }
            }
            boolean centerSingleExpandedItem = hasOverflow && visibleItemCount == 2;
            boolean needsExpansion = false;
            while (expandableItemCount > 0 && cellsRemaining > 0) {
                int minCells = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                long minCellsAt = 0;
                int minCellsItemCount = 0;
                for (int i7 = 0; i7 < childCount; i7++) {
                    View childAt2 = getChildAt(i7);
                    View view = childAt2;
                    LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                    LayoutParams lp2 = layoutParams2;
                    if (layoutParams2.expandable) {
                        if (lp2.cellsUsed < minCells) {
                            minCells = lp2.cellsUsed;
                            minCellsAt = (long) (1 << i7);
                            minCellsItemCount = 1;
                        } else if (lp2.cellsUsed == minCells) {
                            minCellsAt |= (long) (1 << i7);
                            minCellsItemCount++;
                        }
                    }
                }
                smallestItemsAt |= minCellsAt;
                if (minCellsItemCount > cellsRemaining) {
                    break;
                }
                int minCells2 = minCells + 1;
                for (int i8 = 0; i8 < childCount; i8++) {
                    View childAt3 = getChildAt(i8);
                    View child2 = childAt3;
                    LayoutParams layoutParams3 = (LayoutParams) childAt3.getLayoutParams();
                    LayoutParams lp3 = layoutParams3;
                    if ((minCellsAt & ((long) (1 << i8))) != 0) {
                        if (centerSingleExpandedItem && layoutParams3.preventEdgeOffset && cellsRemaining == 1) {
                            child2.setPadding(this.mGeneratedItemPadding + cellSize, 0, this.mGeneratedItemPadding, 0);
                        }
                        lp3.cellsUsed++;
                        lp3.expanded = true;
                        cellsRemaining--;
                    } else if (layoutParams3.cellsUsed == minCells2) {
                        smallestItemsAt |= (long) (1 << i8);
                    }
                }
                needsExpansion = true;
            }
            boolean singleItem = !hasOverflow && visibleItemCount == 1;
            if (cellsRemaining > 0 && smallestItemsAt != 0 && (cellsRemaining < visibleItemCount - 1 || singleItem || maxCellsUsed > 1)) {
                float bitCount = (float) Long.bitCount(smallestItemsAt);
                float expandCount = bitCount;
                if (!singleItem) {
                    if ((smallestItemsAt & 1) != 0) {
                        LayoutParams layoutParams4 = (LayoutParams) getChildAt(0).getLayoutParams();
                        LayoutParams layoutParams5 = layoutParams4;
                        if (!layoutParams4.preventEdgeOffset) {
                            expandCount = bitCount - 0.5f;
                        }
                    }
                    if ((smallestItemsAt & ((long) (1 << (childCount - 1)))) != 0) {
                        LayoutParams layoutParams6 = (LayoutParams) getChildAt(childCount - 1).getLayoutParams();
                        LayoutParams layoutParams7 = layoutParams6;
                        if (!layoutParams6.preventEdgeOffset) {
                            expandCount -= 0.5f;
                        }
                    }
                }
                int extraPixels = expandCount > 0.0f ? (int) (((float) (cellsRemaining * cellSize)) / expandCount) : 0;
                for (int i9 = 0; i9 < childCount; i9++) {
                    if ((smallestItemsAt & ((long) (1 << i9))) != 0) {
                        View childAt4 = getChildAt(i9);
                        LayoutParams lp4 = (LayoutParams) childAt4.getLayoutParams();
                        if (childAt4 instanceof ActionMenuItemView) {
                            lp4.extraPixels = extraPixels;
                            lp4.expanded = true;
                            if (i9 == 0 && !lp4.preventEdgeOffset) {
                                lp4.leftMargin = (-extraPixels) / 2;
                            }
                            needsExpansion = true;
                        } else if (!lp4.isOverflowButton) {
                            if (i9 != 0) {
                                lp4.leftMargin = extraPixels / 2;
                            }
                            if (i9 != childCount - 1) {
                                lp4.rightMargin = extraPixels / 2;
                            }
                        } else {
                            lp4.extraPixels = extraPixels;
                            lp4.expanded = true;
                            lp4.rightMargin = (-extraPixels) / 2;
                            needsExpansion = true;
                        }
                    }
                }
            }
            if (needsExpansion) {
                for (int i10 = 0; i10 < childCount; i10++) {
                    View childAt5 = getChildAt(i10);
                    View child3 = childAt5;
                    LayoutParams layoutParams8 = (LayoutParams) childAt5.getLayoutParams();
                    LayoutParams lp5 = layoutParams8;
                    if (layoutParams8.expanded) {
                        int i11 = (lp5.cellsUsed * cellSize) + lp5.extraPixels;
                        int i12 = i11;
                        child3.measure(MeasureSpec.makeMeasureSpec(i11, 1073741824), itemHeightSpec);
                    }
                }
            }
            if (heightMode != 1073741824) {
                heightSize = maxChildHeight;
            }
            setMeasuredDimension(widthSize2, heightSize);
            return;
        }
        setMeasuredDimension(widthSize2, 0);
    }

    static int measureChildForCells(View view, int i, int i2, int i3, int i4) {
        View child = view;
        int cellSize = i;
        int cellsRemaining = i2;
        int parentHeightMeasureSpec = i3;
        int parentHeightPadding = i4;
        View view2 = child;
        int i5 = cellSize;
        int i6 = cellsRemaining;
        int i7 = parentHeightMeasureSpec;
        int i8 = parentHeightPadding;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childHeightSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(parentHeightMeasureSpec) - parentHeightPadding, MeasureSpec.getMode(parentHeightMeasureSpec));
        ActionMenuItemView itemView = !(child instanceof ActionMenuItemView) ? null : (ActionMenuItemView) child;
        boolean hasText = itemView != null && itemView.hasText();
        int cellsUsed = 0;
        if (cellsRemaining > 0 && (!hasText || cellsRemaining >= 2)) {
            int makeMeasureSpec = MeasureSpec.makeMeasureSpec(cellSize * cellsRemaining, Integer.MIN_VALUE);
            int i9 = makeMeasureSpec;
            child.measure(makeMeasureSpec, childHeightSpec);
            int measuredWidth = child.getMeasuredWidth();
            cellsUsed = measuredWidth / cellSize;
            if (measuredWidth % cellSize != 0) {
                cellsUsed++;
            }
            if (hasText && cellsUsed < 2) {
                cellsUsed = 2;
            }
        }
        lp.expandable = !lp.isOverflowButton && hasText;
        lp.cellsUsed = cellsUsed;
        int i10 = cellsUsed * cellSize;
        int i11 = i10;
        child.measure(MeasureSpec.makeMeasureSpec(i10, 1073741824), childHeightSpec);
        return cellsUsed;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int l;
        int r;
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        boolean changed = z;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        if (this.mFormatItems) {
            int childCount = getChildCount();
            int midVertical = (bottom - top) / 2;
            int dividerWidth = getDividerWidth();
            int nonOverflowWidth = 0;
            int nonOverflowCount = 0;
            int widthRemaining = ((right - left) - getPaddingRight()) - getPaddingLeft();
            boolean hasOverflow = false;
            boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
            for (int i9 = 0; i9 < childCount; i9++) {
                View childAt = getChildAt(i9);
                View v = childAt;
                if (childAt.getVisibility() != 8) {
                    LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
                    LayoutParams p = layoutParams;
                    if (!layoutParams.isOverflowButton) {
                        int size = v.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                        nonOverflowWidth += size;
                        widthRemaining -= size;
                        if (hasSupportDividerBeforeChildAt(i9)) {
                            nonOverflowWidth += dividerWidth;
                        }
                        nonOverflowCount++;
                    } else {
                        int overflowWidth = v.getMeasuredWidth();
                        if (hasSupportDividerBeforeChildAt(i9)) {
                            overflowWidth += dividerWidth;
                        }
                        int height = v.getMeasuredHeight();
                        if (!isLayoutRtl) {
                            int width = (getWidth() - getPaddingRight()) - p.rightMargin;
                            r = width;
                            l = width - overflowWidth;
                        } else {
                            int paddingLeft = getPaddingLeft() + p.leftMargin;
                            l = paddingLeft;
                            r = paddingLeft + overflowWidth;
                        }
                        int i10 = midVertical - (height / 2);
                        int t = i10;
                        int i11 = i10 + height;
                        int i12 = i11;
                        v.layout(l, t, r, i11);
                        widthRemaining -= overflowWidth;
                        hasOverflow = true;
                    }
                }
            }
            if (childCount == 1 && !hasOverflow) {
                View childAt2 = getChildAt(0);
                View v2 = childAt2;
                int width2 = childAt2.getMeasuredWidth();
                int height2 = v2.getMeasuredHeight();
                int i13 = (right - left) / 2;
                int i14 = i13;
                int l2 = i13 - (width2 / 2);
                int i15 = midVertical - (height2 / 2);
                int i16 = i15;
                v2.layout(l2, i15, l2 + width2, i15 + height2);
                return;
            }
            int i17 = nonOverflowCount - (!hasOverflow ? 1 : 0);
            int spacerSize = Math.max(0, i17 <= 0 ? 0 : widthRemaining / i17);
            if (!isLayoutRtl) {
                int startLeft = getPaddingLeft();
                for (int i18 = 0; i18 < childCount; i18++) {
                    View childAt3 = getChildAt(i18);
                    View v3 = childAt3;
                    LayoutParams lp = (LayoutParams) childAt3.getLayoutParams();
                    if (v3.getVisibility() != 8 && !lp.isOverflowButton) {
                        int startLeft2 = startLeft + lp.leftMargin;
                        int width3 = v3.getMeasuredWidth();
                        int height3 = v3.getMeasuredHeight();
                        int i19 = midVertical - (height3 / 2);
                        int i20 = i19;
                        v3.layout(startLeft2, i19, startLeft2 + width3, i19 + height3);
                        startLeft = startLeft2 + width3 + lp.rightMargin + spacerSize;
                    }
                }
            } else {
                int startRight = getWidth() - getPaddingRight();
                for (int i21 = 0; i21 < childCount; i21++) {
                    View childAt4 = getChildAt(i21);
                    View v4 = childAt4;
                    LayoutParams lp2 = (LayoutParams) childAt4.getLayoutParams();
                    if (v4.getVisibility() != 8 && !lp2.isOverflowButton) {
                        int startRight2 = startRight - lp2.rightMargin;
                        int width4 = v4.getMeasuredWidth();
                        int height4 = v4.getMeasuredHeight();
                        int i22 = midVertical - (height4 / 2);
                        int i23 = i22;
                        v4.layout(startRight2 - width4, i22, startRight2, i22 + height4);
                        startRight = startRight2 - ((width4 + lp2.leftMargin) + spacerSize);
                    }
                }
            }
            return;
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public void setOverflowIcon(@Nullable Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        Menu menu = getMenu();
        this.mPresenter.setOverflowIcon(icon);
    }

    @Nullable
    public Drawable getOverflowIcon() {
        Menu menu = getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setOverflowReserved(boolean z) {
        this.mReserveOverflow = z;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        LayoutParams params = layoutParams;
        layoutParams.gravity = 16;
        return params;
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
        if (p == null) {
            return generateDefaultLayoutParams();
        }
        LayoutParams result = !(p instanceof LayoutParams) ? new LayoutParams(p) : new LayoutParams((LayoutParams) p);
        if (result.gravity <= 0) {
            result.gravity = 16;
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return p != null && (p instanceof LayoutParams);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
        LayoutParams result = generateDefaultLayoutParams;
        generateDefaultLayoutParams.isOverflowButton = true;
        return result;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        MenuItemImpl item = menuItemImpl;
        MenuItemImpl menuItemImpl2 = item;
        return this.mMenu.performItemAction(item, 0);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int getWindowAnimations() {
        return 0;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void initialize(MenuBuilder menuBuilder) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        this.mMenu = menu;
    }

    public Menu getMenu() {
        if (this.mMenu == null) {
            Context context = getContext();
            this.mMenu = new MenuBuilder(context);
            this.mMenu.setCallback(new MenuBuilderCallback(this));
            this.mPresenter = new ActionMenuPresenter(context);
            this.mPresenter.setReserveOverflow(true);
            this.mPresenter.setCallback(this.mActionMenuPresenterCallback == null ? new ActionMenuPresenterCallback(this) : this.mActionMenuPresenterCallback);
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setMenuCallbacks(Callback callback, MenuBuilder.Callback callback2) {
        Callback pcb = callback;
        MenuBuilder.Callback mcb = callback2;
        Callback callback3 = pcb;
        MenuBuilder.Callback callback4 = mcb;
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public boolean showOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowing();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isOverflowMenuShowPending() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending();
    }

    public void dismissPopupMenus() {
        if (this.mPresenter != null) {
            boolean dismissPopupMenus = this.mPresenter.dismissPopupMenus();
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean hasSupportDividerBeforeChildAt(int i) {
        int childIndex = i;
        int i2 = childIndex;
        if (childIndex == 0) {
            return false;
        }
        View childBefore = getChildAt(childIndex - 1);
        View child = getChildAt(childIndex);
        boolean result = false;
        if (childIndex < getChildCount() && (childBefore instanceof ActionMenuChildView)) {
            result = false | ((ActionMenuChildView) childBefore).needsDividerAfter();
        }
        if (childIndex > 0 && (child instanceof ActionMenuChildView)) {
            result |= ((ActionMenuChildView) child).needsDividerBefore();
        }
        return result;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
        return false;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setExpandedActionViewsExclusive(boolean z) {
        this.mPresenter.setExpandedActionViewsExclusive(z);
    }
}
