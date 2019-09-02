package android.support.p003v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.view.menu.MenuBuilder.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.SubMenuBuilder */
public class SubMenuBuilder extends MenuBuilder implements SubMenu {
    private MenuItemImpl mItem;
    private MenuBuilder mParentMenu;

    public SubMenuBuilder(Context context, MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        Context context2 = context;
        MenuBuilder parentMenu = menuBuilder;
        MenuItemImpl item = menuItemImpl;
        Context context3 = context2;
        MenuBuilder menuBuilder2 = parentMenu;
        MenuItemImpl menuItemImpl2 = item;
        super(context2);
        this.mParentMenu = parentMenu;
        this.mItem = item;
    }

    public void setQwertyMode(boolean z) {
        this.mParentMenu.setQwertyMode(z);
    }

    public boolean isQwertyMode() {
        return this.mParentMenu.isQwertyMode();
    }

    public void setShortcutsVisible(boolean z) {
        this.mParentMenu.setShortcutsVisible(z);
    }

    public boolean isShortcutsVisible() {
        return this.mParentMenu.isShortcutsVisible();
    }

    public Menu getParentMenu() {
        return this.mParentMenu;
    }

    public MenuItem getItem() {
        return this.mItem;
    }

    public void setCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        this.mParentMenu.setCallback(callback2);
    }

    public MenuBuilder getRootMenu() {
        return this.mParentMenu.getRootMenu();
    }

    /* access modifiers changed from: 0000 */
    public boolean dispatchMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        MenuBuilder menu = menuBuilder;
        MenuItem item = menuItem;
        MenuBuilder menuBuilder2 = menu;
        MenuItem menuItem2 = item;
        return super.dispatchMenuItemSelected(menu, item) || this.mParentMenu.dispatchMenuItemSelected(menu, item);
    }

    public SubMenu setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        MenuItem icon2 = this.mItem.setIcon(icon);
        return this;
    }

    public SubMenu setIcon(int i) {
        int iconRes = i;
        int i2 = iconRes;
        MenuItem icon = this.mItem.setIcon(iconRes);
        return this;
    }

    public SubMenu setHeaderIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        return (SubMenu) super.setHeaderIconInt(icon);
    }

    public SubMenu setHeaderIcon(int i) {
        int iconRes = i;
        int i2 = iconRes;
        return (SubMenu) super.setHeaderIconInt(iconRes);
    }

    public SubMenu setHeaderTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        return (SubMenu) super.setHeaderTitleInt(title);
    }

    public SubMenu setHeaderTitle(int i) {
        int titleRes = i;
        int i2 = titleRes;
        return (SubMenu) super.setHeaderTitleInt(titleRes);
    }

    public SubMenu setHeaderView(View view) {
        View view2 = view;
        View view3 = view2;
        return (SubMenu) super.setHeaderViewInt(view2);
    }

    public boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        MenuItemImpl item = menuItemImpl;
        MenuItemImpl menuItemImpl2 = item;
        return this.mParentMenu.expandItemActionView(item);
    }

    public boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        MenuItemImpl item = menuItemImpl;
        MenuItemImpl menuItemImpl2 = item;
        return this.mParentMenu.collapseItemActionView(item);
    }

    public String getActionViewStatesKey() {
        int itemId = this.mItem == null ? 0 : this.mItem.getItemId();
        if (itemId != 0) {
            return super.getActionViewStatesKey() + ":" + itemId;
        }
        return null;
    }
}
