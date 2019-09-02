package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.view.ActionProvider;
import android.support.p000v4.view.ActionProvider.SubUiVisibilityListener;
import android.support.p000v4.view.GravityCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.transition.ActionBarTransition;
import android.support.p003v7.view.ActionBarPolicy;
import android.support.p003v7.view.menu.ActionMenuItemView;
import android.support.p003v7.view.menu.ActionMenuItemView.PopupCallback;
import android.support.p003v7.view.menu.BaseMenuPresenter;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.support.p003v7.view.menu.MenuPopupHelper;
import android.support.p003v7.view.menu.MenuPresenter.Callback;
import android.support.p003v7.view.menu.MenuView;
import android.support.p003v7.view.menu.MenuView.ItemView;
import android.support.p003v7.view.menu.ShowableListMenu;
import android.support.p003v7.view.menu.SubMenuBuilder;
import android.support.p003v7.widget.ActionMenuView.ActionMenuChildView;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;

/* renamed from: android.support.v7.widget.ActionMenuPresenter */
class ActionMenuPresenter extends BaseMenuPresenter implements SubUiVisibilityListener {
    private static final String TAG = "ActionMenuPresenter";
    private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
    ActionButtonSubmenu mActionButtonPopup;
    private int mActionItemWidthLimit;
    private boolean mExpandedActionViewsExclusive;
    private int mMaxItems;
    private boolean mMaxItemsSet;
    private int mMinCellSize;
    int mOpenSubMenuId;
    OverflowMenuButton mOverflowButton;
    OverflowPopup mOverflowPopup;
    private Drawable mPendingOverflowIcon;
    private boolean mPendingOverflowIconSet;
    private ActionMenuPopupCallback mPopupCallback;
    final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback(this);
    OpenOverflowRunnable mPostedOpenRunnable;
    private boolean mReserveOverflow;
    private boolean mReserveOverflowSet;
    private View mScrapActionButtonView;
    private boolean mStrictWidthLimit;
    private int mWidthLimit;
    private boolean mWidthLimitSet;

    /* renamed from: android.support.v7.widget.ActionMenuPresenter$ActionButtonSubmenu */
    private class ActionButtonSubmenu extends MenuPopupHelper {
        final /* synthetic */ ActionMenuPresenter this$0;

        public ActionButtonSubmenu(ActionMenuPresenter actionMenuPresenter, Context context, SubMenuBuilder subMenuBuilder, View view) {
            ActionMenuPresenter actionMenuPresenter2 = actionMenuPresenter;
            Context context2 = context;
            SubMenuBuilder subMenu = subMenuBuilder;
            View anchorView = view;
            Context context3 = context2;
            SubMenuBuilder subMenuBuilder2 = subMenu;
            View view2 = anchorView;
            this.this$0 = actionMenuPresenter2;
            super(context2, subMenu, anchorView, false, C0268R.attr.actionOverflowMenuStyle);
            MenuItemImpl menuItemImpl = (MenuItemImpl) subMenu.getItem();
            MenuItemImpl menuItemImpl2 = menuItemImpl;
            if (!menuItemImpl.isActionButton()) {
                setAnchorView(actionMenuPresenter2.mOverflowButton != null ? actionMenuPresenter2.mOverflowButton : (View) ActionMenuPresenter.access$200(actionMenuPresenter2));
            }
            setPresenterCallback(actionMenuPresenter2.mPopupPresenterCallback);
        }

        /* access modifiers changed from: protected */
        public void onDismiss() {
            this.this$0.mActionButtonPopup = null;
            this.this$0.mOpenSubMenuId = 0;
            super.onDismiss();
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuPresenter$ActionMenuPopupCallback */
    private class ActionMenuPopupCallback extends PopupCallback {
        final /* synthetic */ ActionMenuPresenter this$0;

        ActionMenuPopupCallback(ActionMenuPresenter actionMenuPresenter) {
            this.this$0 = actionMenuPresenter;
        }

        public ShowableListMenu getPopup() {
            return this.this$0.mActionButtonPopup == null ? null : this.this$0.mActionButtonPopup.getPopup();
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuPresenter$OpenOverflowRunnable */
    private class OpenOverflowRunnable implements Runnable {
        private OverflowPopup mPopup;
        final /* synthetic */ ActionMenuPresenter this$0;

        public OpenOverflowRunnable(ActionMenuPresenter actionMenuPresenter, OverflowPopup overflowPopup) {
            OverflowPopup popup = overflowPopup;
            OverflowPopup overflowPopup2 = popup;
            this.this$0 = actionMenuPresenter;
            this.mPopup = popup;
        }

        public void run() {
            if (ActionMenuPresenter.access$300(this.this$0) != null) {
                ActionMenuPresenter.access$400(this.this$0).changeMenuMode();
            }
            View view = (View) ActionMenuPresenter.access$500(this.this$0);
            View menuView = view;
            if (!(view == null || menuView.getWindowToken() == null || !this.mPopup.tryShow())) {
                this.this$0.mOverflowPopup = this.mPopup;
            }
            this.this$0.mPostedOpenRunnable = null;
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuPresenter$OverflowMenuButton */
    private class OverflowMenuButton extends AppCompatImageView implements ActionMenuChildView {
        private final float[] mTempPts = new float[2];
        final /* synthetic */ ActionMenuPresenter this$0;

        public OverflowMenuButton(ActionMenuPresenter actionMenuPresenter, Context context) {
            ActionMenuPresenter actionMenuPresenter2 = actionMenuPresenter;
            Context context2 = context;
            Context context3 = context2;
            this.this$0 = actionMenuPresenter2;
            super(context2, null, C0268R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            setOnTouchListener(new ForwardingListener(this, this, actionMenuPresenter2) {
                final /* synthetic */ OverflowMenuButton this$1;
                final /* synthetic */ ActionMenuPresenter val$this$0;

                {
                    OverflowMenuButton this$12 = r9;
                    View src = r10;
                    ActionMenuPresenter actionMenuPresenter = r11;
                    OverflowMenuButton overflowMenuButton = this$12;
                    View view = src;
                    this.this$1 = this$12;
                    this.val$this$0 = actionMenuPresenter;
                }

                public ShowableListMenu getPopup() {
                    if (this.this$1.this$0.mOverflowPopup != null) {
                        return this.this$1.this$0.mOverflowPopup.getPopup();
                    }
                    return null;
                }

                public boolean onForwardingStarted() {
                    boolean showOverflowMenu = this.this$1.this$0.showOverflowMenu();
                    return true;
                }

                public boolean onForwardingStopped() {
                    if (this.this$1.this$0.mPostedOpenRunnable != null) {
                        return false;
                    }
                    boolean hideOverflowMenu = this.this$1.this$0.hideOverflowMenu();
                    return true;
                }
            });
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            boolean showOverflowMenu = this.this$0.showOverflowMenu();
            return true;
        }

        public boolean needsDividerBefore() {
            return false;
        }

        public boolean needsDividerAfter() {
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean setFrame(int i, int i2, int i3, int i4) {
            int l = i;
            int t = i2;
            int r = i3;
            int b = i4;
            int i5 = l;
            int i6 = t;
            int i7 = r;
            int i8 = b;
            boolean changed = super.setFrame(l, t, r, b);
            Drawable d = getDrawable();
            Drawable bg = getBackground();
            if (!(d == null || bg == null)) {
                int width = getWidth();
                int height = getHeight();
                int halfEdge = Math.max(width, height) / 2;
                int centerX = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                int i9 = paddingTop;
                DrawableCompat.setHotspotBounds(bg, centerX - halfEdge, paddingTop - halfEdge, centerX + halfEdge, paddingTop + halfEdge);
            }
            return changed;
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuPresenter$OverflowPopup */
    private class OverflowPopup extends MenuPopupHelper {
        final /* synthetic */ ActionMenuPresenter this$0;

        public OverflowPopup(ActionMenuPresenter actionMenuPresenter, Context context, MenuBuilder menuBuilder, View view, boolean z) {
            ActionMenuPresenter actionMenuPresenter2 = actionMenuPresenter;
            Context context2 = context;
            MenuBuilder menu = menuBuilder;
            View anchorView = view;
            Context context3 = context2;
            MenuBuilder menuBuilder2 = menu;
            View view2 = anchorView;
            boolean overflowOnly = z;
            this.this$0 = actionMenuPresenter2;
            super(context2, menu, anchorView, overflowOnly, C0268R.attr.actionOverflowMenuStyle);
            setGravity(GravityCompat.END);
            setPresenterCallback(actionMenuPresenter2.mPopupPresenterCallback);
        }

        /* access modifiers changed from: protected */
        public void onDismiss() {
            if (ActionMenuPresenter.access$000(this.this$0) != null) {
                ActionMenuPresenter.access$100(this.this$0).close();
            }
            this.this$0.mOverflowPopup = null;
            super.onDismiss();
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuPresenter$PopupPresenterCallback */
    private class PopupPresenterCallback implements Callback {
        final /* synthetic */ ActionMenuPresenter this$0;

        PopupPresenterCallback(ActionMenuPresenter actionMenuPresenter) {
            this.this$0 = actionMenuPresenter;
        }

        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            MenuBuilder subMenu = menuBuilder;
            MenuBuilder menuBuilder2 = subMenu;
            if (subMenu == null) {
                return false;
            }
            this.this$0.mOpenSubMenuId = ((SubMenuBuilder) subMenu).getItem().getItemId();
            Callback callback = this.this$0.getCallback();
            return callback == null ? false : callback.onOpenSubMenu(subMenu);
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder menu = menuBuilder;
            MenuBuilder menuBuilder2 = menu;
            boolean allMenusAreClosing = z;
            if (menu instanceof SubMenuBuilder) {
                menu.getRootMenu().close(false);
            }
            Callback callback = this.this$0.getCallback();
            Callback cb = callback;
            if (callback != null) {
                cb.onCloseMenu(menu, allMenusAreClosing);
            }
        }
    }

    /* renamed from: android.support.v7.widget.ActionMenuPresenter$SavedState */
    private static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                Parcel in = parcel;
                Parcel parcel2 = in;
                return new SavedState(in);
            }

            public SavedState[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new SavedState[size];
            }
        };
        public int openSubMenuId;

        SavedState() {
        }

        SavedState(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            this.openSubMenuId = in.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            Parcel parcel2 = dest;
            int i2 = i;
            dest.writeInt(this.openSubMenuId);
        }
    }

    static /* synthetic */ MenuBuilder access$000(ActionMenuPresenter actionMenuPresenter) {
        ActionMenuPresenter x0 = actionMenuPresenter;
        ActionMenuPresenter actionMenuPresenter2 = x0;
        return x0.mMenu;
    }

    static /* synthetic */ MenuBuilder access$100(ActionMenuPresenter actionMenuPresenter) {
        ActionMenuPresenter x0 = actionMenuPresenter;
        ActionMenuPresenter actionMenuPresenter2 = x0;
        return x0.mMenu;
    }

    static /* synthetic */ MenuView access$200(ActionMenuPresenter actionMenuPresenter) {
        ActionMenuPresenter x0 = actionMenuPresenter;
        ActionMenuPresenter actionMenuPresenter2 = x0;
        return x0.mMenuView;
    }

    static /* synthetic */ MenuBuilder access$300(ActionMenuPresenter actionMenuPresenter) {
        ActionMenuPresenter x0 = actionMenuPresenter;
        ActionMenuPresenter actionMenuPresenter2 = x0;
        return x0.mMenu;
    }

    static /* synthetic */ MenuBuilder access$400(ActionMenuPresenter actionMenuPresenter) {
        ActionMenuPresenter x0 = actionMenuPresenter;
        ActionMenuPresenter actionMenuPresenter2 = x0;
        return x0.mMenu;
    }

    static /* synthetic */ MenuView access$500(ActionMenuPresenter actionMenuPresenter) {
        ActionMenuPresenter x0 = actionMenuPresenter;
        ActionMenuPresenter actionMenuPresenter2 = x0;
        return x0.mMenuView;
    }

    public ActionMenuPresenter(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2, C0268R.layout.abc_action_menu_layout, C0268R.layout.abc_action_menu_item_layout);
    }

    public void initForMenu(@NonNull Context context, @Nullable MenuBuilder menuBuilder) {
        Context context2 = context;
        MenuBuilder menu = menuBuilder;
        Context context3 = context2;
        MenuBuilder menuBuilder2 = menu;
        super.initForMenu(context2, menu);
        Resources res = context2.getResources();
        ActionBarPolicy abp = ActionBarPolicy.get(context2);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = abp.showsOverflowMenuButton();
        }
        if (!this.mWidthLimitSet) {
            this.mWidthLimit = abp.getEmbeddedMenuWidthLimit();
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = abp.getMaxActionButtons();
        }
        int width = this.mWidthLimit;
        if (!this.mReserveOverflow) {
            this.mOverflowButton = null;
        } else {
            if (this.mOverflowButton == null) {
                OverflowMenuButton overflowMenuButton = new OverflowMenuButton(this, this.mSystemContext);
                this.mOverflowButton = overflowMenuButton;
                if (this.mPendingOverflowIconSet) {
                    this.mOverflowButton.setImageDrawable(this.mPendingOverflowIcon);
                    this.mPendingOverflowIcon = null;
                    this.mPendingOverflowIconSet = false;
                }
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
                int i = makeMeasureSpec;
                this.mOverflowButton.measure(makeMeasureSpec, makeMeasureSpec);
            }
            width -= this.mOverflowButton.getMeasuredWidth();
        }
        this.mActionItemWidthLimit = width;
        this.mMinCellSize = (int) (56.0f * res.getDisplayMetrics().density);
        this.mScrapActionButtonView = null;
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration configuration2 = configuration;
        if (!this.mMaxItemsSet) {
            this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons();
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true);
        }
    }

    public void setWidthLimit(int i, boolean z) {
        int width = i;
        int i2 = width;
        boolean strict = z;
        this.mWidthLimit = width;
        this.mStrictWidthLimit = strict;
        this.mWidthLimitSet = true;
    }

    public void setReserveOverflow(boolean z) {
        this.mReserveOverflow = z;
        this.mReserveOverflowSet = true;
    }

    public void setItemLimit(int i) {
        int itemCount = i;
        int i2 = itemCount;
        this.mMaxItems = itemCount;
        this.mMaxItemsSet = true;
    }

    public void setExpandedActionViewsExclusive(boolean z) {
        this.mExpandedActionViewsExclusive = z;
    }

    public void setOverflowIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        if (this.mOverflowButton == null) {
            this.mPendingOverflowIconSet = true;
            this.mPendingOverflowIcon = icon;
            return;
        }
        this.mOverflowButton.setImageDrawable(icon);
    }

    public Drawable getOverflowIcon() {
        if (this.mOverflowButton != null) {
            return this.mOverflowButton.getDrawable();
        }
        if (!this.mPendingOverflowIconSet) {
            return null;
        }
        return this.mPendingOverflowIcon;
    }

    public MenuView getMenuView(ViewGroup viewGroup) {
        ViewGroup root = viewGroup;
        ViewGroup viewGroup2 = root;
        MenuView oldMenuView = this.mMenuView;
        MenuView result = super.getMenuView(root);
        if (oldMenuView != result) {
            ((ActionMenuView) result).setPresenter(this);
        }
        return result;
    }

    public View getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        MenuItemImpl item = menuItemImpl;
        View convertView = view;
        ViewGroup parent = viewGroup;
        MenuItemImpl menuItemImpl2 = item;
        View view2 = convertView;
        ViewGroup viewGroup2 = parent;
        View actionView = item.getActionView();
        View actionView2 = actionView;
        if (actionView == null || item.hasCollapsibleActionView()) {
            actionView2 = super.getItemView(item, convertView, parent);
        }
        actionView2.setVisibility(!item.isActionViewExpanded() ? 0 : 8);
        ActionMenuView menuParent = (ActionMenuView) parent;
        LayoutParams lp = actionView2.getLayoutParams();
        if (!menuParent.checkLayoutParams(lp)) {
            actionView2.setLayoutParams(menuParent.generateLayoutParams(lp));
        }
        return actionView2;
    }

    public void bindItemView(MenuItemImpl menuItemImpl, ItemView itemView) {
        MenuItemImpl item = menuItemImpl;
        ItemView itemView2 = itemView;
        MenuItemImpl menuItemImpl2 = item;
        ItemView itemView3 = itemView2;
        itemView2.initialize(item, 0);
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) itemView2;
        ActionMenuItemView actionItemView = actionMenuItemView;
        actionMenuItemView.setItemInvoker((ActionMenuView) this.mMenuView);
        if (this.mPopupCallback == null) {
            this.mPopupCallback = new ActionMenuPopupCallback(this);
        }
        actionItemView.setPopupCallback(this.mPopupCallback);
    }

    public boolean shouldIncludeItem(int i, MenuItemImpl menuItemImpl) {
        MenuItemImpl item = menuItemImpl;
        int i2 = i;
        MenuItemImpl menuItemImpl2 = item;
        return item.isActionButton();
    }

    public void updateMenuView(boolean z) {
        boolean cleared = z;
        ViewGroup viewGroup = (ViewGroup) ((View) this.mMenuView).getParent();
        ViewGroup menuViewParent = viewGroup;
        if (viewGroup != null) {
            ActionBarTransition.beginDelayedTransition(menuViewParent);
        }
        super.updateMenuView(cleared);
        ((View) this.mMenuView).requestLayout();
        if (this.mMenu != null) {
            ArrayList actionItems = this.mMenu.getActionItems();
            ArrayList arrayList = actionItems;
            int count = actionItems.size();
            for (int i = 0; i < count; i++) {
                ActionProvider supportActionProvider = ((MenuItemImpl) arrayList.get(i)).getSupportActionProvider();
                ActionProvider provider = supportActionProvider;
                if (supportActionProvider != null) {
                    provider.setSubUiVisibilityListener(this);
                }
            }
        }
        ArrayList nonActionItems = this.mMenu == null ? null : this.mMenu.getNonActionItems();
        boolean hasOverflow = false;
        if (this.mReserveOverflow && nonActionItems != null) {
            int size = nonActionItems.size();
            int count2 = size;
            if (size != 1) {
                hasOverflow = count2 > 0;
            } else {
                hasOverflow = !((MenuItemImpl) nonActionItems.get(0)).isActionViewExpanded();
            }
        }
        if (hasOverflow) {
            if (this.mOverflowButton == null) {
                OverflowMenuButton overflowMenuButton = new OverflowMenuButton(this, this.mSystemContext);
                this.mOverflowButton = overflowMenuButton;
            }
            ViewGroup viewGroup2 = (ViewGroup) this.mOverflowButton.getParent();
            ViewGroup parent = viewGroup2;
            if (viewGroup2 != this.mMenuView) {
                if (parent != null) {
                    parent.removeView(this.mOverflowButton);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.mMenuView;
                actionMenuView.addView(this.mOverflowButton, actionMenuView.generateOverflowButtonLayoutParams());
            }
        } else if (this.mOverflowButton != null && this.mOverflowButton.getParent() == this.mMenuView) {
            ((ViewGroup) this.mMenuView).removeView(this.mOverflowButton);
        }
        ((ActionMenuView) this.mMenuView).setOverflowReserved(this.mReserveOverflow);
    }

    public boolean filterLeftoverView(ViewGroup viewGroup, int i) {
        ViewGroup parent = viewGroup;
        int childIndex = i;
        ViewGroup viewGroup2 = parent;
        int i2 = childIndex;
        if (parent.getChildAt(childIndex) != this.mOverflowButton) {
            return super.filterLeftoverView(parent, childIndex);
        }
        return false;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        SubMenuBuilder subMenu = subMenuBuilder;
        SubMenuBuilder subMenuBuilder2 = subMenu;
        if (!subMenu.hasVisibleItems()) {
            return false;
        }
        SubMenuBuilder topSubMenu = subMenu;
        while (topSubMenu.getParentMenu() != this.mMenu) {
            topSubMenu = (SubMenuBuilder) topSubMenu.getParentMenu();
        }
        View findViewForItem = findViewForItem(topSubMenu.getItem());
        View anchor = findViewForItem;
        if (findViewForItem == null) {
            return false;
        }
        this.mOpenSubMenuId = subMenu.getItem().getItemId();
        boolean preserveIconSpacing = false;
        int count = subMenu.size();
        int i = 0;
        while (true) {
            if (i >= count) {
                break;
            }
            MenuItem item = subMenu.getItem(i);
            MenuItem childItem = item;
            if (item.isVisible() && childItem.getIcon() != null) {
                preserveIconSpacing = true;
                break;
            }
            i++;
        }
        this.mActionButtonPopup = new ActionButtonSubmenu(this, this.mContext, subMenu, anchor);
        this.mActionButtonPopup.setForceShowIcon(preserveIconSpacing);
        this.mActionButtonPopup.show();
        boolean onSubMenuSelected = super.onSubMenuSelected(subMenu);
        return true;
    }

    private View findViewForItem(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        ViewGroup viewGroup = (ViewGroup) this.mMenuView;
        ViewGroup parent = viewGroup;
        if (viewGroup == null) {
            return null;
        }
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View childAt = parent.getChildAt(i);
            View child = childAt;
            if ((childAt instanceof ItemView) && ((ItemView) child).getItemData() == item) {
                return child;
            }
        }
        return null;
    }

    public boolean showOverflowMenu() {
        if (!this.mReserveOverflow || isOverflowMenuShowing() || this.mMenu == null || this.mMenuView == null || this.mPostedOpenRunnable != null || this.mMenu.getNonActionItems().isEmpty()) {
            return false;
        }
        OverflowPopup overflowPopup = new OverflowPopup(this, this.mContext, this.mMenu, this.mOverflowButton, true);
        this.mPostedOpenRunnable = new OpenOverflowRunnable(this, overflowPopup);
        boolean post = ((View) this.mMenuView).post(this.mPostedOpenRunnable);
        boolean onSubMenuSelected = super.onSubMenuSelected(null);
        return true;
    }

    public boolean hideOverflowMenu() {
        if (this.mPostedOpenRunnable == null || this.mMenuView == null) {
            OverflowPopup overflowPopup = this.mOverflowPopup;
            OverflowPopup overflowPopup2 = overflowPopup;
            if (overflowPopup == null) {
                return false;
            }
            overflowPopup2.dismiss();
            return true;
        }
        boolean removeCallbacks = ((View) this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
        this.mPostedOpenRunnable = null;
        return true;
    }

    public boolean dismissPopupMenus() {
        boolean hideOverflowMenu = hideOverflowMenu();
        boolean z = hideOverflowMenu;
        boolean hideSubMenus = hideOverflowMenu | hideSubMenus();
        boolean result = hideSubMenus;
        return hideSubMenus;
    }

    public boolean hideSubMenus() {
        if (this.mActionButtonPopup == null) {
            return false;
        }
        this.mActionButtonPopup.dismiss();
        return true;
    }

    public boolean isOverflowMenuShowing() {
        return this.mOverflowPopup != null && this.mOverflowPopup.isShowing();
    }

    public boolean isOverflowMenuShowPending() {
        return this.mPostedOpenRunnable != null || isOverflowMenuShowing();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public boolean flagActionItems() {
        ArrayList arrayList;
        int itemsSize;
        boolean z;
        if (this.mMenu == null) {
            arrayList = null;
            itemsSize = 0;
        } else {
            ArrayList visibleItems = this.mMenu.getVisibleItems();
            arrayList = visibleItems;
            itemsSize = visibleItems.size();
        }
        int maxActions = this.mMaxItems;
        int widthLimit = this.mActionItemWidthLimit;
        int querySpec = MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup parent = (ViewGroup) this.mMenuView;
        int requiredItems = 0;
        int requestedItems = 0;
        int firstActionWidth = 0;
        boolean hasOverflow = false;
        for (int i = 0; i < itemsSize; i++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) arrayList.get(i);
            MenuItemImpl item = menuItemImpl;
            if (menuItemImpl.requiresActionButton()) {
                requiredItems++;
            } else if (!item.requestsActionButton()) {
                hasOverflow = true;
            } else {
                requestedItems++;
            }
            if (this.mExpandedActionViewsExclusive && item.isActionViewExpanded()) {
                maxActions = 0;
            }
        }
        if (this.mReserveOverflow && (hasOverflow || requiredItems + requestedItems > maxActions)) {
            maxActions--;
        }
        int maxActions2 = maxActions - requiredItems;
        SparseBooleanArray sparseBooleanArray = this.mActionButtonGroups;
        SparseBooleanArray seenGroups = sparseBooleanArray;
        sparseBooleanArray.clear();
        int cellSize = 0;
        int cellsRemaining = 0;
        if (this.mStrictWidthLimit) {
            cellsRemaining = widthLimit / this.mMinCellSize;
            cellSize = this.mMinCellSize + ((widthLimit % this.mMinCellSize) / cellsRemaining);
        }
        for (int i2 = 0; i2 < itemsSize; i2++) {
            MenuItemImpl menuItemImpl2 = (MenuItemImpl) arrayList.get(i2);
            MenuItemImpl item2 = menuItemImpl2;
            if (menuItemImpl2.requiresActionButton()) {
                View v = getItemView(item2, this.mScrapActionButtonView, parent);
                if (this.mScrapActionButtonView == null) {
                    this.mScrapActionButtonView = v;
                }
                if (!this.mStrictWidthLimit) {
                    v.measure(querySpec, querySpec);
                } else {
                    cellsRemaining -= ActionMenuView.measureChildForCells(v, cellSize, cellsRemaining, querySpec, 0);
                }
                int measuredWidth = v.getMeasuredWidth();
                widthLimit -= measuredWidth;
                if (firstActionWidth == 0) {
                    firstActionWidth = measuredWidth;
                }
                int groupId = item2.getGroupId();
                int groupId2 = groupId;
                if (groupId != 0) {
                    seenGroups.put(groupId2, true);
                }
                item2.setIsActionButton(true);
            } else if (!item2.requestsActionButton()) {
                item2.setIsActionButton(false);
            } else {
                int groupId3 = item2.getGroupId();
                int groupId4 = groupId3;
                boolean inGroup = seenGroups.get(groupId3);
                boolean isAction = (maxActions2 > 0 || inGroup) && widthLimit > 0 && (!this.mStrictWidthLimit || cellsRemaining > 0);
                if (isAction) {
                    View v2 = getItemView(item2, this.mScrapActionButtonView, parent);
                    if (this.mScrapActionButtonView == null) {
                        this.mScrapActionButtonView = v2;
                    }
                    if (!this.mStrictWidthLimit) {
                        v2.measure(querySpec, querySpec);
                    } else {
                        int cells = ActionMenuView.measureChildForCells(v2, cellSize, cellsRemaining, querySpec, 0);
                        cellsRemaining -= cells;
                        if (cells == 0) {
                            isAction = false;
                        }
                    }
                    int measuredWidth2 = v2.getMeasuredWidth();
                    widthLimit -= measuredWidth2;
                    if (firstActionWidth == 0) {
                        firstActionWidth = measuredWidth2;
                    }
                    if (!this.mStrictWidthLimit) {
                        boolean z2 = isAction;
                        if (widthLimit + firstActionWidth <= 0) {
                            z = false;
                        } else {
                            z = true;
                        }
                        isAction = z2 & z;
                    } else {
                        isAction &= widthLimit >= 0;
                    }
                }
                if (isAction && groupId4 != 0) {
                    seenGroups.put(groupId4, true);
                } else if (inGroup) {
                    seenGroups.put(groupId4, false);
                    for (int j = 0; j < i2; j++) {
                        MenuItemImpl menuItemImpl3 = (MenuItemImpl) arrayList.get(j);
                        MenuItemImpl areYouMyGroupie = menuItemImpl3;
                        if (menuItemImpl3.getGroupId() == groupId4) {
                            if (areYouMyGroupie.isActionButton()) {
                                maxActions2++;
                            }
                            areYouMyGroupie.setIsActionButton(false);
                        }
                    }
                }
                if (isAction) {
                    maxActions2--;
                }
                item2.setIsActionButton(isAction);
            }
        }
        return true;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        boolean allMenusAreClosing = z;
        boolean dismissPopupMenus = dismissPopupMenus();
        super.onCloseMenu(menu, allMenusAreClosing);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        SavedState state = savedState;
        savedState.openSubMenuId = this.mOpenSubMenuId;
        return state;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            SavedState saved = savedState;
            if (savedState.openSubMenuId > 0) {
                MenuItem findItem = this.mMenu.findItem(saved.openSubMenuId);
                MenuItem item = findItem;
                if (findItem != null) {
                    boolean onSubMenuSelected = onSubMenuSelected((SubMenuBuilder) item.getSubMenu());
                }
            }
        }
    }

    public void onSubUiVisibilityChanged(boolean z) {
        if (z) {
            boolean onSubMenuSelected = super.onSubMenuSelected(null);
        } else if (this.mMenu != null) {
            this.mMenu.close(false);
        }
    }

    public void setMenuView(ActionMenuView actionMenuView) {
        ActionMenuView menuView = actionMenuView;
        ActionMenuView actionMenuView2 = menuView;
        this.mMenuView = menuView;
        menuView.initialize(this.mMenu);
    }
}
