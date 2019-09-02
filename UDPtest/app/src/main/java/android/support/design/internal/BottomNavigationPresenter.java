package android.support.design.internal;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.support.p003v7.view.menu.MenuPresenter;
import android.support.p003v7.view.menu.MenuPresenter.Callback;
import android.support.p003v7.view.menu.MenuView;
import android.support.p003v7.view.menu.SubMenuBuilder;
import android.view.ViewGroup;

@RestrictTo({Scope.LIBRARY_GROUP})
public class BottomNavigationPresenter implements MenuPresenter {
    private MenuBuilder mMenu;
    private BottomNavigationMenuView mMenuView;
    private boolean mUpdateSuspended = false;

    public BottomNavigationPresenter() {
    }

    public void setBottomNavigationMenuView(BottomNavigationMenuView bottomNavigationMenuView) {
        BottomNavigationMenuView menuView = bottomNavigationMenuView;
        BottomNavigationMenuView bottomNavigationMenuView2 = menuView;
        this.mMenuView = menuView;
    }

    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        MenuBuilder menu = menuBuilder;
        Context context2 = context;
        MenuBuilder menuBuilder2 = menu;
        this.mMenuView.initialize(this.mMenu);
        this.mMenu = menu;
    }

    public MenuView getMenuView(ViewGroup viewGroup) {
        ViewGroup viewGroup2 = viewGroup;
        return this.mMenuView;
    }

    public void updateMenuView(boolean z) {
        boolean cleared = z;
        if (!this.mUpdateSuspended) {
            if (!cleared) {
                this.mMenuView.updateMenuView();
            } else {
                this.mMenuView.buildMenuView();
            }
        }
    }

    public void setCallback(Callback callback) {
        Callback callback2 = callback;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
        return false;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuBuilder menuBuilder2 = menuBuilder;
        boolean z2 = z;
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
        return -1;
    }

    public Parcelable onSaveInstanceState() {
        return null;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2 = parcelable;
    }

    public void setUpdateSuspended(boolean z) {
        this.mUpdateSuspended = z;
    }
}
