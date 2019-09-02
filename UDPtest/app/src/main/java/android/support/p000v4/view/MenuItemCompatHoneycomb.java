package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.MenuItem;
import android.view.View;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.view.MenuItemCompatHoneycomb */
class MenuItemCompatHoneycomb {
    MenuItemCompatHoneycomb() {
    }

    public static void setShowAsAction(MenuItem menuItem, int i) {
        MenuItem item = menuItem;
        int actionEnum = i;
        MenuItem menuItem2 = item;
        int i2 = actionEnum;
        item.setShowAsAction(actionEnum);
    }

    public static MenuItem setActionView(MenuItem menuItem, View view) {
        MenuItem item = menuItem;
        View view2 = view;
        MenuItem menuItem2 = item;
        View view3 = view2;
        return item.setActionView(view2);
    }

    public static MenuItem setActionView(MenuItem menuItem, int i) {
        MenuItem item = menuItem;
        int resId = i;
        MenuItem menuItem2 = item;
        int i2 = resId;
        return item.setActionView(resId);
    }

    public static View getActionView(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        return item.getActionView();
    }
}
