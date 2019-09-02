package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.view.MenuItemCompatIcs */
class MenuItemCompatIcs {

    /* renamed from: android.support.v4.view.MenuItemCompatIcs$OnActionExpandListenerWrapper */
    static class OnActionExpandListenerWrapper implements OnActionExpandListener {
        private SupportActionExpandProxy mWrapped;

        public OnActionExpandListenerWrapper(SupportActionExpandProxy supportActionExpandProxy) {
            SupportActionExpandProxy wrapped = supportActionExpandProxy;
            SupportActionExpandProxy supportActionExpandProxy2 = wrapped;
            this.mWrapped = wrapped;
        }

        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            return this.mWrapped.onMenuItemActionExpand(item);
        }

        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            return this.mWrapped.onMenuItemActionCollapse(item);
        }
    }

    /* renamed from: android.support.v4.view.MenuItemCompatIcs$SupportActionExpandProxy */
    interface SupportActionExpandProxy {
        boolean onMenuItemActionCollapse(MenuItem menuItem);

        boolean onMenuItemActionExpand(MenuItem menuItem);
    }

    MenuItemCompatIcs() {
    }

    public static boolean expandActionView(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        return item.expandActionView();
    }

    public static boolean collapseActionView(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        return item.collapseActionView();
    }

    public static boolean isActionViewExpanded(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        return item.isActionViewExpanded();
    }

    public static MenuItem setOnActionExpandListener(MenuItem menuItem, SupportActionExpandProxy supportActionExpandProxy) {
        MenuItem item = menuItem;
        SupportActionExpandProxy listener = supportActionExpandProxy;
        MenuItem menuItem2 = item;
        SupportActionExpandProxy supportActionExpandProxy2 = listener;
        return item.setOnActionExpandListener(new OnActionExpandListenerWrapper(listener));
    }
}
