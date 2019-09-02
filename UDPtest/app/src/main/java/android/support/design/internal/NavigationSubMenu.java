package android.support.design.internal;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.support.p003v7.view.menu.SubMenuBuilder;

@RestrictTo({Scope.LIBRARY_GROUP})
public class NavigationSubMenu extends SubMenuBuilder {
    public NavigationSubMenu(Context context, NavigationMenu navigationMenu, MenuItemImpl menuItemImpl) {
        Context context2 = context;
        NavigationMenu menu = navigationMenu;
        MenuItemImpl item = menuItemImpl;
        Context context3 = context2;
        NavigationMenu navigationMenu2 = menu;
        MenuItemImpl menuItemImpl2 = item;
        super(context2, menu, item);
    }

    public void onItemsChanged(boolean z) {
        boolean structureChanged = z;
        super.onItemsChanged(structureChanged);
        ((MenuBuilder) getParentMenu()).onItemsChanged(structureChanged);
    }
}
