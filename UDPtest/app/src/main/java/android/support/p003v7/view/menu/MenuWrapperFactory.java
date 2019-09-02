package android.support.p003v7.view.menu;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.internal.view.SupportMenu;
import android.support.p000v4.internal.view.SupportMenuItem;
import android.support.p000v4.internal.view.SupportSubMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.MenuWrapperFactory */
public final class MenuWrapperFactory {
    private MenuWrapperFactory() {
    }

    public static Menu wrapSupportMenu(Context context, SupportMenu supportMenu) {
        Context context2 = context;
        SupportMenu supportMenu2 = supportMenu;
        Context context3 = context2;
        SupportMenu supportMenu3 = supportMenu2;
        if (VERSION.SDK_INT >= 14) {
            return new MenuWrapperICS(context2, supportMenu2);
        }
        throw new UnsupportedOperationException();
    }

    public static MenuItem wrapSupportMenuItem(Context context, SupportMenuItem supportMenuItem) {
        Context context2 = context;
        SupportMenuItem supportMenuItem2 = supportMenuItem;
        Context context3 = context2;
        SupportMenuItem supportMenuItem3 = supportMenuItem2;
        if (VERSION.SDK_INT >= 16) {
            return new MenuItemWrapperJB(context2, supportMenuItem2);
        }
        if (VERSION.SDK_INT >= 14) {
            return new MenuItemWrapperICS(context2, supportMenuItem2);
        }
        throw new UnsupportedOperationException();
    }

    public static SubMenu wrapSupportSubMenu(Context context, SupportSubMenu supportSubMenu) {
        Context context2 = context;
        SupportSubMenu supportSubMenu2 = supportSubMenu;
        Context context3 = context2;
        SupportSubMenu supportSubMenu3 = supportSubMenu2;
        if (VERSION.SDK_INT >= 14) {
            return new SubMenuWrapperICS(context2, supportSubMenu2);
        }
        throw new UnsupportedOperationException();
    }
}
