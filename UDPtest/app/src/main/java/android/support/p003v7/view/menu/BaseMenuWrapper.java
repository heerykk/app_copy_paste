package android.support.p003v7.view.menu;

import android.content.Context;
import android.support.p000v4.internal.view.SupportMenuItem;
import android.support.p000v4.internal.view.SupportSubMenu;
import android.support.p000v4.util.ArrayMap;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Iterator;
import java.util.Map;

/* renamed from: android.support.v7.view.menu.BaseMenuWrapper */
abstract class BaseMenuWrapper<T> extends BaseWrapper<T> {
    final Context mContext;
    private Map<SupportMenuItem, MenuItem> mMenuItems;
    private Map<SupportSubMenu, SubMenu> mSubMenus;

    BaseMenuWrapper(Context context, T t) {
        Context context2 = context;
        T object = t;
        Context context3 = context2;
        T t2 = object;
        super(object);
        this.mContext = context2;
    }

    /* access modifiers changed from: 0000 */
    public final MenuItem getMenuItemWrapper(MenuItem menuItem) {
        MenuItem menuItem2 = menuItem;
        MenuItem menuItem3 = menuItem2;
        if (!(menuItem2 instanceof SupportMenuItem)) {
            return menuItem2;
        }
        SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem2;
        if (this.mMenuItems == null) {
            this.mMenuItems = new ArrayMap();
        }
        MenuItem wrappedItem = (MenuItem) this.mMenuItems.get(menuItem2);
        if (null == wrappedItem) {
            wrappedItem = MenuWrapperFactory.wrapSupportMenuItem(this.mContext, supportMenuItem);
            Object put = this.mMenuItems.put(supportMenuItem, wrappedItem);
        }
        return wrappedItem;
    }

    /* access modifiers changed from: 0000 */
    public final SubMenu getSubMenuWrapper(SubMenu subMenu) {
        SubMenu subMenu2 = subMenu;
        SubMenu subMenu3 = subMenu2;
        if (!(subMenu2 instanceof SupportSubMenu)) {
            return subMenu2;
        }
        SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu2;
        if (this.mSubMenus == null) {
            this.mSubMenus = new ArrayMap();
        }
        SubMenu wrappedMenu = (SubMenu) this.mSubMenus.get(supportSubMenu);
        if (null == wrappedMenu) {
            wrappedMenu = MenuWrapperFactory.wrapSupportSubMenu(this.mContext, supportSubMenu);
            Object put = this.mSubMenus.put(supportSubMenu, wrappedMenu);
        }
        return wrappedMenu;
    }

    /* access modifiers changed from: 0000 */
    public final void internalClear() {
        if (this.mMenuItems != null) {
            this.mMenuItems.clear();
        }
        if (this.mSubMenus != null) {
            this.mSubMenus.clear();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void internalRemoveGroup(int i) {
        int groupId = i;
        int i2 = groupId;
        if (this.mMenuItems != null) {
            Iterator it = this.mMenuItems.keySet().iterator();
            while (it.hasNext()) {
                MenuItem menuItem = (MenuItem) it.next();
                MenuItem menuItem2 = menuItem;
                if (groupId == menuItem.getGroupId()) {
                    it.remove();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void internalRemoveItem(int i) {
        int id = i;
        int i2 = id;
        if (this.mMenuItems != null) {
            Iterator it = this.mMenuItems.keySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    MenuItem menuItem = (MenuItem) it.next();
                    MenuItem menuItem2 = menuItem;
                    if (id == menuItem.getItemId()) {
                        it.remove();
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }
}
