package android.support.p003v7.view.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.MenuPresenter.Callback;
import android.support.p003v7.view.menu.MenuView.ItemView;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import java.util.ArrayList;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.ListMenuPresenter */
public class ListMenuPresenter implements MenuPresenter, OnItemClickListener {
    private static final String TAG = "ListMenuPresenter";
    public static final String VIEWS_TAG = "android:menu:list";
    MenuAdapter mAdapter;
    private Callback mCallback;
    Context mContext;
    private int mId;
    LayoutInflater mInflater;
    int mItemIndexOffset;
    int mItemLayoutRes;
    MenuBuilder mMenu;
    ExpandedMenuView mMenuView;
    int mThemeRes;

    /* renamed from: android.support.v7.view.menu.ListMenuPresenter$MenuAdapter */
    private class MenuAdapter extends BaseAdapter {
        private int mExpandedIndex = -1;
        final /* synthetic */ ListMenuPresenter this$0;

        public MenuAdapter(ListMenuPresenter listMenuPresenter) {
            this.this$0 = listMenuPresenter;
            findExpandedIndex();
        }

        public int getCount() {
            ArrayList nonActionItems = this.this$0.mMenu.getNonActionItems();
            ArrayList arrayList = nonActionItems;
            int count = nonActionItems.size() - this.this$0.mItemIndexOffset;
            if (this.mExpandedIndex >= 0) {
                return count - 1;
            }
            return count;
        }

        public MenuItemImpl getItem(int i) {
            int position = i;
            int i2 = position;
            ArrayList nonActionItems = this.this$0.mMenu.getNonActionItems();
            int position2 = position + this.this$0.mItemIndexOffset;
            if (this.mExpandedIndex >= 0 && position2 >= this.mExpandedIndex) {
                position2++;
            }
            return (MenuItemImpl) nonActionItems.get(position2);
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
                convertView2 = this.this$0.mInflater.inflate(this.this$0.mItemLayoutRes, parent, false);
            }
            ItemView itemView = (ItemView) convertView2;
            ItemView itemView2 = itemView;
            itemView.initialize(getItem(position), 0);
            return convertView2;
        }

        /* access modifiers changed from: 0000 */
        public void findExpandedIndex() {
            MenuItemImpl expandedItem = this.this$0.mMenu.getExpandedItem();
            MenuItemImpl expandedItem2 = expandedItem;
            if (expandedItem != null) {
                ArrayList nonActionItems = this.this$0.mMenu.getNonActionItems();
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

    public ListMenuPresenter(Context context, int i) {
        Context context2 = context;
        int itemLayoutRes = i;
        Context context3 = context2;
        int i2 = itemLayoutRes;
        this(itemLayoutRes, 0);
        this.mContext = context2;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public ListMenuPresenter(int i, int i2) {
        int itemLayoutRes = i;
        int themeRes = i2;
        int i3 = itemLayoutRes;
        int i4 = themeRes;
        this.mItemLayoutRes = itemLayoutRes;
        this.mThemeRes = themeRes;
    }

    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        Context context2 = context;
        MenuBuilder menu = menuBuilder;
        Context context3 = context2;
        MenuBuilder menuBuilder2 = menu;
        if (this.mThemeRes != 0) {
            this.mContext = new ContextThemeWrapper(context2, this.mThemeRes);
            this.mInflater = LayoutInflater.from(this.mContext);
        } else if (this.mContext != null) {
            this.mContext = context2;
            if (this.mInflater == null) {
                this.mInflater = LayoutInflater.from(this.mContext);
            }
        }
        this.mMenu = menu;
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public MenuView getMenuView(ViewGroup viewGroup) {
        ViewGroup root = viewGroup;
        ViewGroup viewGroup2 = root;
        if (this.mMenuView == null) {
            this.mMenuView = (ExpandedMenuView) this.mInflater.inflate(C0268R.layout.abc_expanded_menu_layout, root, false);
            if (this.mAdapter == null) {
                this.mAdapter = new MenuAdapter(this);
            }
            this.mMenuView.setAdapter(this.mAdapter);
            this.mMenuView.setOnItemClickListener(this);
        }
        return this.mMenuView;
    }

    public ListAdapter getAdapter() {
        if (this.mAdapter == null) {
            this.mAdapter = new MenuAdapter(this);
        }
        return this.mAdapter;
    }

    public void updateMenuView(boolean z) {
        boolean z2 = z;
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public void setCallback(Callback callback) {
        Callback cb = callback;
        Callback callback2 = cb;
        this.mCallback = cb;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        SubMenuBuilder subMenu = subMenuBuilder;
        SubMenuBuilder subMenuBuilder2 = subMenu;
        if (!subMenu.hasVisibleItems()) {
            return false;
        }
        new MenuDialogHelper(subMenu).show(null);
        if (this.mCallback != null) {
            boolean onOpenSubMenu = this.mCallback.onOpenSubMenu(subMenu);
        }
        return true;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        boolean allMenusAreClosing = z;
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menu, allMenusAreClosing);
        }
    }

    /* access modifiers changed from: 0000 */
    public int getItemIndexOffset() {
        return this.mItemIndexOffset;
    }

    public void setItemIndexOffset(int i) {
        int offset = i;
        int i2 = offset;
        this.mItemIndexOffset = offset;
        if (this.mMenuView != null) {
            updateMenuView(false);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int position = i;
        AdapterView<?> adapterView2 = adapterView;
        View view2 = view;
        int i2 = position;
        long j2 = j;
        boolean performItemAction = this.mMenu.performItemAction(this.mAdapter.getItem(position), this, 0);
    }

    public boolean flagActionItems() {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        MenuBuilder menuBuilder2 = menuBuilder;
        MenuItemImpl menuItemImpl2 = menuItemImpl;
        return false;
    }

    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        MenuBuilder menuBuilder2 = menuBuilder;
        MenuItemImpl menuItemImpl2 = menuItemImpl;
        return false;
    }

    public void saveHierarchyState(Bundle bundle) {
        Bundle outState = bundle;
        Bundle bundle2 = outState;
        SparseArray sparseArray = new SparseArray();
        if (this.mMenuView != null) {
            this.mMenuView.saveHierarchyState(sparseArray);
        }
        outState.putSparseParcelableArray(VIEWS_TAG, sparseArray);
    }

    public void restoreHierarchyState(Bundle bundle) {
        Bundle inState = bundle;
        Bundle bundle2 = inState;
        SparseArray sparseParcelableArray = inState.getSparseParcelableArray(VIEWS_TAG);
        SparseArray sparseArray = sparseParcelableArray;
        if (sparseParcelableArray != null) {
            this.mMenuView.restoreHierarchyState(sparseArray);
        }
    }

    public void setId(int i) {
        int id = i;
        int i2 = id;
        this.mId = id;
    }

    public int getId() {
        return this.mId;
    }

    public Parcelable onSaveInstanceState() {
        if (this.mMenuView == null) {
            return null;
        }
        Bundle state = new Bundle();
        saveHierarchyState(state);
        return state;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        restoreHierarchyState((Bundle) state);
    }
}
