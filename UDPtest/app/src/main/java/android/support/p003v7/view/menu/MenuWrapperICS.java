package android.support.p003v7.view.menu;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresApi;
import android.support.p000v4.internal.view.SupportMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v7.view.menu.MenuWrapperICS */
class MenuWrapperICS extends BaseMenuWrapper<SupportMenu> implements Menu {
    MenuWrapperICS(Context context, SupportMenu supportMenu) {
        Context context2 = context;
        SupportMenu object = supportMenu;
        Context context3 = context2;
        SupportMenu supportMenu2 = object;
        super(context2, object);
    }

    public MenuItem add(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add(title));
    }

    public MenuItem add(int i) {
        int titleRes = i;
        int i2 = titleRes;
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add(titleRes));
    }

    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        int groupId = i;
        int itemId = i2;
        int order = i3;
        CharSequence title = charSequence;
        int i4 = groupId;
        int i5 = itemId;
        int i6 = order;
        CharSequence charSequence2 = title;
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add(groupId, itemId, order, title));
    }

    public MenuItem add(int i, int i2, int i3, int i4) {
        int groupId = i;
        int itemId = i2;
        int order = i3;
        int titleRes = i4;
        int i5 = groupId;
        int i6 = itemId;
        int i7 = order;
        int i8 = titleRes;
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add(groupId, itemId, order, titleRes));
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu(title));
    }

    public SubMenu addSubMenu(int i) {
        int titleRes = i;
        int i2 = titleRes;
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu(titleRes));
    }

    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        int groupId = i;
        int itemId = i2;
        int order = i3;
        CharSequence title = charSequence;
        int i4 = groupId;
        int i5 = itemId;
        int i6 = order;
        CharSequence charSequence2 = title;
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu(groupId, itemId, order, title));
    }

    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        int groupId = i;
        int itemId = i2;
        int order = i3;
        int titleRes = i4;
        int i5 = groupId;
        int i6 = itemId;
        int i7 = order;
        int i8 = titleRes;
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu(groupId, itemId, order, titleRes));
    }

    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        int groupId = i;
        int itemId = i2;
        int order = i3;
        ComponentName caller = componentName;
        Intent[] specifics = intentArr;
        Intent intent2 = intent;
        int flags = i4;
        MenuItem[] outSpecificItems = menuItemArr;
        int i5 = groupId;
        int i6 = itemId;
        int i7 = order;
        ComponentName componentName2 = caller;
        Intent[] intentArr2 = specifics;
        Intent intent3 = intent2;
        int i8 = flags;
        MenuItem[] menuItemArr2 = outSpecificItems;
        MenuItem[] items = null;
        if (outSpecificItems != null) {
            items = new MenuItem[outSpecificItems.length];
        }
        int result = ((SupportMenu) this.mWrappedObject).addIntentOptions(groupId, itemId, order, caller, specifics, intent2, flags, items);
        if (items != null) {
            int z = items.length;
            for (int i9 = 0; i9 < z; i9++) {
                outSpecificItems[i9] = getMenuItemWrapper(items[i9]);
            }
        }
        return result;
    }

    public void removeItem(int i) {
        int id = i;
        int i2 = id;
        internalRemoveItem(id);
        ((SupportMenu) this.mWrappedObject).removeItem(id);
    }

    public void removeGroup(int i) {
        int groupId = i;
        int i2 = groupId;
        internalRemoveGroup(groupId);
        ((SupportMenu) this.mWrappedObject).removeGroup(groupId);
    }

    public void clear() {
        internalClear();
        ((SupportMenu) this.mWrappedObject).clear();
    }

    public void setGroupCheckable(int i, boolean z, boolean z2) {
        int group = i;
        int i2 = group;
        ((SupportMenu) this.mWrappedObject).setGroupCheckable(group, z, z2);
    }

    public void setGroupVisible(int i, boolean z) {
        int group = i;
        int i2 = group;
        ((SupportMenu) this.mWrappedObject).setGroupVisible(group, z);
    }

    public void setGroupEnabled(int i, boolean z) {
        int group = i;
        int i2 = group;
        ((SupportMenu) this.mWrappedObject).setGroupEnabled(group, z);
    }

    public boolean hasVisibleItems() {
        return ((SupportMenu) this.mWrappedObject).hasVisibleItems();
    }

    public MenuItem findItem(int i) {
        int id = i;
        int i2 = id;
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).findItem(id));
    }

    public int size() {
        return ((SupportMenu) this.mWrappedObject).size();
    }

    public MenuItem getItem(int i) {
        int index = i;
        int i2 = index;
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).getItem(index));
    }

    public void close() {
        ((SupportMenu) this.mWrappedObject).close();
    }

    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        int flags = i2;
        int i3 = keyCode;
        KeyEvent keyEvent2 = event;
        int i4 = flags;
        return ((SupportMenu) this.mWrappedObject).performShortcut(keyCode, event, flags);
    }

    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        return ((SupportMenu) this.mWrappedObject).isShortcutKey(keyCode, event);
    }

    public boolean performIdentifierAction(int i, int i2) {
        int id = i;
        int flags = i2;
        int i3 = id;
        int i4 = flags;
        return ((SupportMenu) this.mWrappedObject).performIdentifierAction(id, flags);
    }

    public void setQwertyMode(boolean z) {
        ((SupportMenu) this.mWrappedObject).setQwertyMode(z);
    }
}
