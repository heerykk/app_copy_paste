package android.support.design.internal;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.view.SubMenu;

@RestrictTo({Scope.LIBRARY_GROUP})
public class NavigationMenu extends MenuBuilder {
    public NavigationMenu(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
    }

    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        int group = i;
        int id = i2;
        int categoryOrder = i3;
        CharSequence title = charSequence;
        int i4 = group;
        int i5 = id;
        int i6 = categoryOrder;
        CharSequence charSequence2 = title;
        MenuItemImpl item = (MenuItemImpl) addInternal(group, id, categoryOrder, title);
        NavigationSubMenu navigationSubMenu = new NavigationSubMenu(getContext(), this, item);
        item.setSubMenu(navigationSubMenu);
        return navigationSubMenu;
    }
}
