package android.support.p003v7.view.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.internal.view.SupportSubMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

@TargetApi(14)
@RequiresApi(14)
@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.SubMenuWrapperICS */
class SubMenuWrapperICS extends MenuWrapperICS implements SubMenu {
    SubMenuWrapperICS(Context context, SupportSubMenu supportSubMenu) {
        Context context2 = context;
        SupportSubMenu subMenu = supportSubMenu;
        Context context3 = context2;
        SupportSubMenu supportSubMenu2 = subMenu;
        super(context2, subMenu);
    }

    public SupportSubMenu getWrappedObject() {
        return (SupportSubMenu) this.mWrappedObject;
    }

    public SubMenu setHeaderTitle(int i) {
        int titleRes = i;
        int i2 = titleRes;
        SubMenu headerTitle = getWrappedObject().setHeaderTitle(titleRes);
        return this;
    }

    public SubMenu setHeaderTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        SubMenu headerTitle = getWrappedObject().setHeaderTitle(title);
        return this;
    }

    public SubMenu setHeaderIcon(int i) {
        int iconRes = i;
        int i2 = iconRes;
        SubMenu headerIcon = getWrappedObject().setHeaderIcon(iconRes);
        return this;
    }

    public SubMenu setHeaderIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        SubMenu headerIcon = getWrappedObject().setHeaderIcon(icon);
        return this;
    }

    public SubMenu setHeaderView(View view) {
        View view2 = view;
        View view3 = view2;
        SubMenu headerView = getWrappedObject().setHeaderView(view2);
        return this;
    }

    public void clearHeader() {
        getWrappedObject().clearHeader();
    }

    public SubMenu setIcon(int i) {
        int iconRes = i;
        int i2 = iconRes;
        SubMenu icon = getWrappedObject().setIcon(iconRes);
        return this;
    }

    public SubMenu setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        SubMenu icon2 = getWrappedObject().setIcon(icon);
        return this;
    }

    public MenuItem getItem() {
        return getMenuItemWrapper(getWrappedObject().getItem());
    }
}
