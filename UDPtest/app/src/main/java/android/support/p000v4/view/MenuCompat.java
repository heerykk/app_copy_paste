package android.support.p000v4.view;

import android.view.MenuItem;

/* renamed from: android.support.v4.view.MenuCompat */
public final class MenuCompat {
    @Deprecated
    public static void setShowAsAction(MenuItem menuItem, int i) {
        MenuItem item = menuItem;
        int actionEnum = i;
        MenuItem menuItem2 = item;
        int i2 = actionEnum;
        MenuItemCompat.setShowAsAction(item, actionEnum);
    }

    private MenuCompat() {
    }
}
