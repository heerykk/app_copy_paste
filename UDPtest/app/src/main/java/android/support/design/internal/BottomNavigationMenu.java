package android.support.design.internal;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.view.MenuItem;
import android.view.SubMenu;

@RestrictTo({Scope.LIBRARY_GROUP})
public final class BottomNavigationMenu extends MenuBuilder {
    public static final int MAX_ITEM_COUNT = 5;

    public BottomNavigationMenu(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
    }

    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        int i4 = i;
        int i5 = i2;
        int i6 = i3;
        CharSequence charSequence2 = charSequence;
        throw new UnsupportedOperationException("BottomNavigationView does not support submenus");
    }

    /* access modifiers changed from: protected */
    public MenuItem addInternal(int i, int i2, int i3, CharSequence charSequence) {
        int group = i;
        int id = i2;
        int categoryOrder = i3;
        CharSequence title = charSequence;
        int i4 = group;
        int i5 = id;
        int i6 = categoryOrder;
        CharSequence charSequence2 = title;
        if (size() + 1 <= 5) {
            stopDispatchingItemsChanged();
            MenuItem addInternal = super.addInternal(group, id, categoryOrder, title);
            MenuItem item = addInternal;
            if (addInternal instanceof MenuItemImpl) {
                ((MenuItemImpl) item).setExclusiveCheckable(true);
            }
            startDispatchingItemsChanged();
            return item;
        }
        throw new IllegalArgumentException("Maximum number of items supported by BottomNavigationView is 5. Limit can be checked with BottomNavigationView#getMaxItemCount()");
    }
}
