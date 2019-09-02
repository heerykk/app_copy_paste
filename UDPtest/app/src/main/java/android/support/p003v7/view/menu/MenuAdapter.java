package android.support.p003v7.view.menu;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.MenuView.ItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.MenuAdapter */
public class MenuAdapter extends BaseAdapter {
    static final int ITEM_LAYOUT = C0268R.layout.abc_popup_menu_item_layout;
    MenuBuilder mAdapterMenu;
    private int mExpandedIndex = -1;
    private boolean mForceShowIcon;
    private final LayoutInflater mInflater;
    private final boolean mOverflowOnly;

    public MenuAdapter(MenuBuilder menuBuilder, LayoutInflater layoutInflater, boolean z) {
        MenuBuilder menu = menuBuilder;
        LayoutInflater inflater = layoutInflater;
        MenuBuilder menuBuilder2 = menu;
        LayoutInflater layoutInflater2 = inflater;
        boolean overflowOnly = z;
        this.mOverflowOnly = overflowOnly;
        this.mInflater = inflater;
        this.mAdapterMenu = menu;
        findExpandedIndex();
    }

    public boolean getForceShowIcon() {
        return this.mForceShowIcon;
    }

    public void setForceShowIcon(boolean z) {
        this.mForceShowIcon = z;
    }

    public int getCount() {
        ArrayList visibleItems = !this.mOverflowOnly ? this.mAdapterMenu.getVisibleItems() : this.mAdapterMenu.getNonActionItems();
        if (this.mExpandedIndex >= 0) {
            return visibleItems.size() - 1;
        }
        return visibleItems.size();
    }

    public MenuBuilder getAdapterMenu() {
        return this.mAdapterMenu;
    }

    public MenuItemImpl getItem(int i) {
        int position = i;
        int position2 = position;
        ArrayList visibleItems = !this.mOverflowOnly ? this.mAdapterMenu.getVisibleItems() : this.mAdapterMenu.getNonActionItems();
        if (this.mExpandedIndex >= 0 && position >= this.mExpandedIndex) {
            position2 = position + 1;
        }
        return (MenuItemImpl) visibleItems.get(position2);
    }

    public long getItemId(int i) {
        int position = i;
        int i2 = position;
        return (long) position;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        int position = i;
        View convertView = view;
        ViewGroup parent = viewGroup;
        int i2 = position;
        View convertView2 = convertView;
        ViewGroup viewGroup2 = parent;
        if (convertView == null) {
            convertView2 = this.mInflater.inflate(ITEM_LAYOUT, parent, false);
        }
        ItemView itemView = (ItemView) convertView2;
        if (this.mForceShowIcon) {
            ((ListMenuItemView) convertView2).setForceShowIcon(true);
        }
        itemView.initialize(getItem(position), 0);
        return convertView2;
    }

    /* access modifiers changed from: 0000 */
    public void findExpandedIndex() {
        MenuItemImpl expandedItem = this.mAdapterMenu.getExpandedItem();
        MenuItemImpl expandedItem2 = expandedItem;
        if (expandedItem != null) {
            ArrayList nonActionItems = this.mAdapterMenu.getNonActionItems();
            ArrayList arrayList = nonActionItems;
            int count = nonActionItems.size();
            int i = 0;
            while (i < count) {
                MenuItemImpl menuItemImpl = (MenuItemImpl) arrayList.get(i);
                MenuItemImpl menuItemImpl2 = menuItemImpl;
                if (menuItemImpl != expandedItem2) {
                    i++;
                } else {
                    this.mExpandedIndex = i;
                    return;
                }
            }
        }
        this.mExpandedIndex = -1;
    }

    public void notifyDataSetChanged() {
        findExpandedIndex();
        super.notifyDataSetChanged();
    }
}
