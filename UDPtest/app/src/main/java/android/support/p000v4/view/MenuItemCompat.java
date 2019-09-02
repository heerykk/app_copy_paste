package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.support.p000v4.internal.view.SupportMenuItem;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/* renamed from: android.support.v4.view.MenuItemCompat */
public final class MenuItemCompat {
    static final MenuVersionImpl IMPL;
    public static final int SHOW_AS_ACTION_ALWAYS = 2;
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    public static final int SHOW_AS_ACTION_NEVER = 0;
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
    private static final String TAG = "MenuItemCompat";

    /* renamed from: android.support.v4.view.MenuItemCompat$BaseMenuVersionImpl */
    static class BaseMenuVersionImpl implements MenuVersionImpl {
        BaseMenuVersionImpl() {
        }

        public void setShowAsAction(MenuItem menuItem, int i) {
            MenuItem menuItem2 = menuItem;
            int i2 = i;
        }

        public MenuItem setActionView(MenuItem menuItem, View view) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            View view2 = view;
            return item;
        }

        public MenuItem setActionView(MenuItem menuItem, int i) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            int i2 = i;
            return item;
        }

        public View getActionView(MenuItem menuItem) {
            MenuItem menuItem2 = menuItem;
            return null;
        }

        public boolean expandActionView(MenuItem menuItem) {
            MenuItem menuItem2 = menuItem;
            return false;
        }

        public boolean collapseActionView(MenuItem menuItem) {
            MenuItem menuItem2 = menuItem;
            return false;
        }

        public boolean isActionViewExpanded(MenuItem menuItem) {
            MenuItem menuItem2 = menuItem;
            return false;
        }

        public MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            OnActionExpandListener onActionExpandListener2 = onActionExpandListener;
            return item;
        }
    }

    /* renamed from: android.support.v4.view.MenuItemCompat$HoneycombMenuVersionImpl */
    static class HoneycombMenuVersionImpl implements MenuVersionImpl {
        HoneycombMenuVersionImpl() {
        }

        public void setShowAsAction(MenuItem menuItem, int i) {
            MenuItem item = menuItem;
            int actionEnum = i;
            MenuItem menuItem2 = item;
            int i2 = actionEnum;
            MenuItemCompatHoneycomb.setShowAsAction(item, actionEnum);
        }

        public MenuItem setActionView(MenuItem menuItem, View view) {
            MenuItem item = menuItem;
            View view2 = view;
            MenuItem menuItem2 = item;
            View view3 = view2;
            return MenuItemCompatHoneycomb.setActionView(item, view2);
        }

        public MenuItem setActionView(MenuItem menuItem, int i) {
            MenuItem item = menuItem;
            int resId = i;
            MenuItem menuItem2 = item;
            int i2 = resId;
            return MenuItemCompatHoneycomb.setActionView(item, resId);
        }

        public View getActionView(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            return MenuItemCompatHoneycomb.getActionView(item);
        }

        public boolean expandActionView(MenuItem menuItem) {
            MenuItem menuItem2 = menuItem;
            return false;
        }

        public boolean collapseActionView(MenuItem menuItem) {
            MenuItem menuItem2 = menuItem;
            return false;
        }

        public boolean isActionViewExpanded(MenuItem menuItem) {
            MenuItem menuItem2 = menuItem;
            return false;
        }

        public MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            OnActionExpandListener onActionExpandListener2 = onActionExpandListener;
            return item;
        }
    }

    /* renamed from: android.support.v4.view.MenuItemCompat$IcsMenuVersionImpl */
    static class IcsMenuVersionImpl extends HoneycombMenuVersionImpl {
        IcsMenuVersionImpl() {
        }

        public boolean expandActionView(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            return MenuItemCompatIcs.expandActionView(item);
        }

        public boolean collapseActionView(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            return MenuItemCompatIcs.collapseActionView(item);
        }

        public boolean isActionViewExpanded(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            return MenuItemCompatIcs.isActionViewExpanded(item);
        }

        public MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener) {
            MenuItem item = menuItem;
            OnActionExpandListener listener = onActionExpandListener;
            MenuItem menuItem2 = item;
            OnActionExpandListener onActionExpandListener2 = listener;
            if (listener == null) {
                return MenuItemCompatIcs.setOnActionExpandListener(item, null);
            }
            final OnActionExpandListener onActionExpandListener3 = listener;
            return MenuItemCompatIcs.setOnActionExpandListener(item, new SupportActionExpandProxy(this) {
                final /* synthetic */ IcsMenuVersionImpl this$0;

                {
                    IcsMenuVersionImpl this$02 = r6;
                    OnActionExpandListener onActionExpandListener = r7;
                    IcsMenuVersionImpl icsMenuVersionImpl = this$02;
                    this.this$0 = this$02;
                }

                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    MenuItem item = menuItem;
                    MenuItem menuItem2 = item;
                    return onActionExpandListener3.onMenuItemActionExpand(item);
                }

                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    MenuItem item = menuItem;
                    MenuItem menuItem2 = item;
                    return onActionExpandListener3.onMenuItemActionCollapse(item);
                }
            });
        }
    }

    /* renamed from: android.support.v4.view.MenuItemCompat$MenuVersionImpl */
    interface MenuVersionImpl {
        boolean collapseActionView(MenuItem menuItem);

        boolean expandActionView(MenuItem menuItem);

        View getActionView(MenuItem menuItem);

        boolean isActionViewExpanded(MenuItem menuItem);

        MenuItem setActionView(MenuItem menuItem, int i);

        MenuItem setActionView(MenuItem menuItem, View view);

        MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener);

        void setShowAsAction(MenuItem menuItem, int i);
    }

    /* renamed from: android.support.v4.view.MenuItemCompat$OnActionExpandListener */
    public interface OnActionExpandListener {
        boolean onMenuItemActionCollapse(MenuItem menuItem);

        boolean onMenuItemActionExpand(MenuItem menuItem);
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new IcsMenuVersionImpl();
        } else if (VERSION.SDK_INT < 11) {
            IMPL = new BaseMenuVersionImpl();
        } else {
            IMPL = new HoneycombMenuVersionImpl();
        }
    }

    public static void setShowAsAction(MenuItem menuItem, int i) {
        MenuItem item = menuItem;
        int actionEnum = i;
        MenuItem menuItem2 = item;
        int i2 = actionEnum;
        if (!(item instanceof SupportMenuItem)) {
            IMPL.setShowAsAction(item, actionEnum);
        } else {
            ((SupportMenuItem) item).setShowAsAction(actionEnum);
        }
    }

    public static MenuItem setActionView(MenuItem menuItem, View view) {
        MenuItem item = menuItem;
        View view2 = view;
        MenuItem menuItem2 = item;
        View view3 = view2;
        if (!(item instanceof SupportMenuItem)) {
            return IMPL.setActionView(item, view2);
        }
        return ((SupportMenuItem) item).setActionView(view2);
    }

    public static MenuItem setActionView(MenuItem menuItem, int i) {
        MenuItem item = menuItem;
        int resId = i;
        MenuItem menuItem2 = item;
        int i2 = resId;
        if (!(item instanceof SupportMenuItem)) {
            return IMPL.setActionView(item, resId);
        }
        return ((SupportMenuItem) item).setActionView(resId);
    }

    public static View getActionView(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        if (!(item instanceof SupportMenuItem)) {
            return IMPL.getActionView(item);
        }
        return ((SupportMenuItem) item).getActionView();
    }

    public static MenuItem setActionProvider(MenuItem menuItem, ActionProvider actionProvider) {
        MenuItem item = menuItem;
        ActionProvider provider = actionProvider;
        MenuItem menuItem2 = item;
        ActionProvider actionProvider2 = provider;
        if (item instanceof SupportMenuItem) {
            return ((SupportMenuItem) item).setSupportActionProvider(provider);
        }
        int w = Log.w(TAG, "setActionProvider: item does not implement SupportMenuItem; ignoring");
        return item;
    }

    public static ActionProvider getActionProvider(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        if (item instanceof SupportMenuItem) {
            return ((SupportMenuItem) item).getSupportActionProvider();
        }
        int w = Log.w(TAG, "getActionProvider: item does not implement SupportMenuItem; returning null");
        return null;
    }

    public static boolean expandActionView(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        if (!(item instanceof SupportMenuItem)) {
            return IMPL.expandActionView(item);
        }
        return ((SupportMenuItem) item).expandActionView();
    }

    public static boolean collapseActionView(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        if (!(item instanceof SupportMenuItem)) {
            return IMPL.collapseActionView(item);
        }
        return ((SupportMenuItem) item).collapseActionView();
    }

    public static boolean isActionViewExpanded(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        if (!(item instanceof SupportMenuItem)) {
            return IMPL.isActionViewExpanded(item);
        }
        return ((SupportMenuItem) item).isActionViewExpanded();
    }

    public static MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener) {
        MenuItem item = menuItem;
        OnActionExpandListener listener = onActionExpandListener;
        MenuItem menuItem2 = item;
        OnActionExpandListener onActionExpandListener2 = listener;
        if (!(item instanceof SupportMenuItem)) {
            return IMPL.setOnActionExpandListener(item, listener);
        }
        return ((SupportMenuItem) item).setSupportOnActionExpandListener(listener);
    }

    private MenuItemCompat() {
    }
}
