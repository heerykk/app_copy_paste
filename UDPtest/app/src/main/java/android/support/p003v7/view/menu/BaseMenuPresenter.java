package android.support.p003v7.view.menu;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.view.menu.MenuPresenter.Callback;
import android.support.p003v7.view.menu.MenuView.ItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.BaseMenuPresenter */
public abstract class BaseMenuPresenter implements MenuPresenter {
    private Callback mCallback;
    protected Context mContext;
    private int mId;
    protected LayoutInflater mInflater;
    private int mItemLayoutRes;
    protected MenuBuilder mMenu;
    private int mMenuLayoutRes;
    protected MenuView mMenuView;
    protected Context mSystemContext;
    protected LayoutInflater mSystemInflater;

    public abstract void bindItemView(MenuItemImpl menuItemImpl, ItemView itemView);

    public BaseMenuPresenter(Context context, int i, int i2) {
        Context context2 = context;
        int menuLayoutRes = i;
        int itemLayoutRes = i2;
        Context context3 = context2;
        int i3 = menuLayoutRes;
        int i4 = itemLayoutRes;
        this.mSystemContext = context2;
        this.mSystemInflater = LayoutInflater.from(context2);
        this.mMenuLayoutRes = menuLayoutRes;
        this.mItemLayoutRes = itemLayoutRes;
    }

    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        Context context2 = context;
        MenuBuilder menu = menuBuilder;
        Context context3 = context2;
        MenuBuilder menuBuilder2 = menu;
        this.mContext = context2;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mMenu = menu;
    }

    public MenuView getMenuView(ViewGroup viewGroup) {
        ViewGroup root = viewGroup;
        ViewGroup viewGroup2 = root;
        if (this.mMenuView == null) {
            this.mMenuView = (MenuView) this.mSystemInflater.inflate(this.mMenuLayoutRes, root, false);
            this.mMenuView.initialize(this.mMenu);
            updateMenuView(true);
        }
        return this.mMenuView;
    }

    public void updateMenuView(boolean z) {
        boolean z2 = z;
        ViewGroup viewGroup = (ViewGroup) this.mMenuView;
        ViewGroup parent = viewGroup;
        if (viewGroup != null) {
            int childIndex = 0;
            if (this.mMenu != null) {
                this.mMenu.flagActionItems();
                ArrayList visibleItems = this.mMenu.getVisibleItems();
                ArrayList arrayList = visibleItems;
                int itemCount = visibleItems.size();
                for (int i = 0; i < itemCount; i++) {
                    MenuItemImpl item = (MenuItemImpl) arrayList.get(i);
                    if (shouldIncludeItem(childIndex, item)) {
                        View childAt = parent.getChildAt(childIndex);
                        View convertView = childAt;
                        MenuItemImpl oldItem = !(childAt instanceof ItemView) ? null : ((ItemView) convertView).getItemData();
                        View itemView = getItemView(item, convertView, parent);
                        if (item != oldItem) {
                            itemView.setPressed(false);
                            ViewCompat.jumpDrawablesToCurrentState(itemView);
                        }
                        if (itemView != convertView) {
                            addItemView(itemView, childIndex);
                        }
                        childIndex++;
                    }
                }
            }
            while (childIndex < parent.getChildCount()) {
                if (!filterLeftoverView(parent, childIndex)) {
                    childIndex++;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void addItemView(View view, int i) {
        View itemView = view;
        int childIndex = i;
        View view2 = itemView;
        int i2 = childIndex;
        ViewGroup viewGroup = (ViewGroup) itemView.getParent();
        ViewGroup currentParent = viewGroup;
        if (viewGroup != null) {
            currentParent.removeView(itemView);
        }
        ((ViewGroup) this.mMenuView).addView(itemView, childIndex);
    }

    /* access modifiers changed from: protected */
    public boolean filterLeftoverView(ViewGroup viewGroup, int i) {
        ViewGroup parent = viewGroup;
        int childIndex = i;
        ViewGroup viewGroup2 = parent;
        int i2 = childIndex;
        parent.removeViewAt(childIndex);
        return true;
    }

    public void setCallback(Callback callback) {
        Callback cb = callback;
        Callback callback2 = cb;
        this.mCallback = cb;
    }

    public Callback getCallback() {
        return this.mCallback;
    }

    public ItemView createItemView(ViewGroup viewGroup) {
        ViewGroup parent = viewGroup;
        ViewGroup viewGroup2 = parent;
        return (ItemView) this.mSystemInflater.inflate(this.mItemLayoutRes, parent, false);
    }

    public View getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        ItemView itemView;
        MenuItemImpl item = menuItemImpl;
        View convertView = view;
        ViewGroup parent = viewGroup;
        MenuItemImpl menuItemImpl2 = item;
        View view2 = convertView;
        ViewGroup viewGroup2 = parent;
        if (!(convertView instanceof ItemView)) {
            itemView = createItemView(parent);
        } else {
            itemView = (ItemView) convertView;
        }
        bindItemView(item, itemView);
        return (View) itemView;
    }

    public boolean shouldIncludeItem(int i, MenuItemImpl menuItemImpl) {
        int i2 = i;
        MenuItemImpl menuItemImpl2 = menuItemImpl;
        return true;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        boolean allMenusAreClosing = z;
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menu, allMenusAreClosing);
        }
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        SubMenuBuilder menu = subMenuBuilder;
        SubMenuBuilder subMenuBuilder2 = menu;
        if (this.mCallback == null) {
            return false;
        }
        return this.mCallback.onOpenSubMenu(menu);
    }

    public boolean flagActionItems() {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        MenuBuilder menuBuilder2 = menuBuilder;
        MenuItemImpl menuItemImpl2 = menuItemImpl;
        return false;
    }

    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        MenuBuilder menuBuilder2 = menuBuilder;
        MenuItemImpl menuItemImpl2 = menuItemImpl;
        return false;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        int id = i;
        int i2 = id;
        this.mId = id;
    }
}
