package android.support.p003v7.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow.OnDismissListener;

/* renamed from: android.support.v7.view.menu.MenuPopup */
abstract class MenuPopup implements ShowableListMenu, MenuPresenter, OnItemClickListener {
    private Rect mEpicenterBounds;

    public abstract void addMenu(MenuBuilder menuBuilder);

    public abstract void setAnchorView(View view);

    public abstract void setForceShowIcon(boolean z);

    public abstract void setGravity(int i);

    public abstract void setHorizontalOffset(int i);

    public abstract void setOnDismissListener(OnDismissListener onDismissListener);

    public abstract void setShowTitle(boolean z);

    public abstract void setVerticalOffset(int i);

    MenuPopup() {
    }

    public void setEpicenterBounds(Rect rect) {
        Rect bounds = rect;
        Rect rect2 = bounds;
        this.mEpicenterBounds = bounds;
    }

    public Rect getEpicenterBounds() {
        return this.mEpicenterBounds;
    }

    public void initForMenu(@NonNull Context context, @Nullable MenuBuilder menuBuilder) {
        Context context2 = context;
        MenuBuilder menuBuilder2 = menuBuilder;
    }

    public MenuView getMenuView(ViewGroup viewGroup) {
        ViewGroup viewGroup2 = viewGroup;
        throw new UnsupportedOperationException("MenuPopups manage their own views");
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

    public int getId() {
        return 0;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        AdapterView<?> parent = adapterView;
        int position = i;
        AdapterView<?> adapterView2 = parent;
        View view2 = view;
        int i2 = position;
        long j2 = j;
        ListAdapter listAdapter = (ListAdapter) parent.getAdapter();
        ListAdapter outerAdapter = listAdapter;
        MenuAdapter menuAdapter = toMenuAdapter(listAdapter);
        MenuAdapter menuAdapter2 = menuAdapter;
        boolean performItemAction = menuAdapter.mAdapterMenu.performItemAction((MenuItem) outerAdapter.getItem(position), this, !closeMenuOnSubMenuOpened() ? 4 : 0);
    }

    protected static int measureIndividualMenuWidth(ListAdapter listAdapter, ViewGroup viewGroup, Context context, int i) {
        ListAdapter adapter = listAdapter;
        Context context2 = context;
        int maxAllowedWidth = i;
        ListAdapter listAdapter2 = adapter;
        ViewGroup parent = viewGroup;
        Context context3 = context2;
        int i2 = maxAllowedWidth;
        int maxWidth = 0;
        View itemView = null;
        int itemType = 0;
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        int count = adapter.getCount();
        for (int i3 = 0; i3 < count; i3++) {
            int itemViewType = adapter.getItemViewType(i3);
            int positionType = itemViewType;
            if (itemViewType != itemType) {
                itemType = positionType;
                itemView = null;
            }
            if (parent == null) {
                parent = new FrameLayout(context2);
            }
            View view = adapter.getView(i3, itemView, parent);
            itemView = view;
            view.measure(widthMeasureSpec, heightMeasureSpec);
            int measuredWidth = itemView.getMeasuredWidth();
            int itemWidth = measuredWidth;
            if (measuredWidth >= maxAllowedWidth) {
                return maxAllowedWidth;
            }
            if (itemWidth > maxWidth) {
                maxWidth = itemWidth;
            }
        }
        return maxWidth;
    }

    protected static MenuAdapter toMenuAdapter(ListAdapter listAdapter) {
        ListAdapter adapter = listAdapter;
        ListAdapter listAdapter2 = adapter;
        if (!(adapter instanceof HeaderViewListAdapter)) {
            return (MenuAdapter) adapter;
        }
        return (MenuAdapter) ((HeaderViewListAdapter) adapter).getWrappedAdapter();
    }

    protected static boolean shouldPreserveIconSpacing(MenuBuilder menuBuilder) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        boolean preserveIconSpacing = false;
        int count = menu.size();
        int i = 0;
        while (true) {
            if (i >= count) {
                break;
            }
            MenuItem item = menu.getItem(i);
            MenuItem childItem = item;
            if (item.isVisible() && childItem.getIcon() != null) {
                preserveIconSpacing = true;
                break;
            }
            i++;
        }
        return preserveIconSpacing;
    }

    /* access modifiers changed from: protected */
    public boolean closeMenuOnSubMenuOpened() {
        return true;
    }
}
