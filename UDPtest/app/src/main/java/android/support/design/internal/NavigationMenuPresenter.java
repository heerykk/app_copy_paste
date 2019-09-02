package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.design.C0001R;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.WindowInsetsCompat;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.support.p003v7.view.menu.MenuPresenter;
import android.support.p003v7.view.menu.MenuPresenter.Callback;
import android.support.p003v7.view.menu.MenuView;
import android.support.p003v7.view.menu.SubMenuBuilder;
import android.support.p003v7.widget.RecyclerView.Adapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;

@RestrictTo({Scope.LIBRARY_GROUP})
public class NavigationMenuPresenter implements MenuPresenter {
    private static final String STATE_ADAPTER = "android:menu:adapter";
    private static final String STATE_HEADER = "android:menu:header";
    private static final String STATE_HIERARCHY = "android:menu:list";
    NavigationMenuAdapter mAdapter;
    private Callback mCallback;
    LinearLayout mHeaderLayout;
    ColorStateList mIconTintList;
    private int mId;
    Drawable mItemBackground;
    LayoutInflater mLayoutInflater;
    MenuBuilder mMenu;
    private NavigationMenuView mMenuView;
    final OnClickListener mOnClickListener = new OnClickListener(this) {
        final /* synthetic */ NavigationMenuPresenter this$0;

        {
            NavigationMenuPresenter this$02 = r5;
            NavigationMenuPresenter navigationMenuPresenter = this$02;
            this.this$0 = this$02;
        }

        public void onClick(View view) {
            View v = view;
            View view2 = v;
            NavigationMenuItemView itemView = (NavigationMenuItemView) v;
            this.this$0.setUpdateSuspended(true);
            MenuItemImpl item = itemView.getItemData();
            boolean result = this.this$0.mMenu.performItemAction(item, this.this$0, 0);
            if (item != null && item.isCheckable() && result) {
                this.this$0.mAdapter.setCheckedItem(item);
            }
            this.this$0.setUpdateSuspended(false);
            this.this$0.updateMenuView(false);
        }
    };
    int mPaddingSeparator;
    private int mPaddingTopDefault;
    int mTextAppearance;
    boolean mTextAppearanceSet;
    ColorStateList mTextColor;

    private static class HeaderViewHolder extends ViewHolder {
        public HeaderViewHolder(View view) {
            View itemView = view;
            View view2 = itemView;
            super(itemView);
        }
    }

    private class NavigationMenuAdapter extends Adapter<ViewHolder> {
        private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
        private static final String STATE_CHECKED_ITEM = "android:menu:checked";
        private static final int VIEW_TYPE_HEADER = 3;
        private static final int VIEW_TYPE_NORMAL = 0;
        private static final int VIEW_TYPE_SEPARATOR = 2;
        private static final int VIEW_TYPE_SUBHEADER = 1;
        private MenuItemImpl mCheckedItem;
        private final ArrayList<NavigationMenuItem> mItems = new ArrayList<>();
        private boolean mUpdateSuspended;
        final /* synthetic */ NavigationMenuPresenter this$0;

        public /* bridge */ /* synthetic */ void onBindViewHolder(android.support.p003v7.widget.RecyclerView.ViewHolder viewHolder, int i) {
            onBindViewHolder((ViewHolder) viewHolder, i);
        }

        public /* bridge */ /* synthetic */ void onViewRecycled(android.support.p003v7.widget.RecyclerView.ViewHolder viewHolder) {
            onViewRecycled((ViewHolder) viewHolder);
        }

        NavigationMenuAdapter(NavigationMenuPresenter navigationMenuPresenter) {
            this.this$0 = navigationMenuPresenter;
            prepareMenuItems();
        }

        public long getItemId(int i) {
            int position = i;
            int i2 = position;
            return (long) position;
        }

        public int getItemCount() {
            return this.mItems.size();
        }

        public int getItemViewType(int i) {
            int position = i;
            int i2 = position;
            NavigationMenuItem navigationMenuItem = (NavigationMenuItem) this.mItems.get(position);
            NavigationMenuItem item = navigationMenuItem;
            if (navigationMenuItem instanceof NavigationMenuSeparatorItem) {
                return 2;
            }
            if (item instanceof NavigationMenuHeaderItem) {
                return 3;
            }
            if (!(item instanceof NavigationMenuTextItem)) {
                throw new RuntimeException("Unknown item type.");
            }
            NavigationMenuTextItem navigationMenuTextItem = (NavigationMenuTextItem) item;
            NavigationMenuTextItem navigationMenuTextItem2 = navigationMenuTextItem;
            if (!navigationMenuTextItem.getMenuItem().hasSubMenu()) {
                return 0;
            }
            return 1;
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            ViewGroup parent = viewGroup;
            int viewType = i;
            ViewGroup viewGroup2 = parent;
            int i2 = viewType;
            switch (viewType) {
                case 0:
                    return new NormalViewHolder(this.this$0.mLayoutInflater, parent, this.this$0.mOnClickListener);
                case 1:
                    return new SubheaderViewHolder(this.this$0.mLayoutInflater, parent);
                case 2:
                    return new SeparatorViewHolder(this.this$0.mLayoutInflater, parent);
                case 3:
                    return new HeaderViewHolder(this.this$0.mHeaderLayout);
                default:
                    return null;
            }
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Drawable newDrawable;
            ViewHolder holder = viewHolder;
            int position = i;
            ViewHolder viewHolder2 = holder;
            int i2 = position;
            switch (getItemViewType(position)) {
                case 0:
                    NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) holder.itemView;
                    NavigationMenuItemView itemView = navigationMenuItemView;
                    navigationMenuItemView.setIconTintList(this.this$0.mIconTintList);
                    if (this.this$0.mTextAppearanceSet) {
                        itemView.setTextAppearance(this.this$0.mTextAppearance);
                    }
                    if (this.this$0.mTextColor != null) {
                        itemView.setTextColor(this.this$0.mTextColor);
                    }
                    if (this.this$0.mItemBackground == null) {
                        newDrawable = null;
                    } else {
                        newDrawable = this.this$0.mItemBackground.getConstantState().newDrawable();
                    }
                    ViewCompat.setBackground(itemView, newDrawable);
                    NavigationMenuTextItem item = (NavigationMenuTextItem) this.mItems.get(position);
                    itemView.setNeedsEmptyIcon(item.needsEmptyIcon);
                    itemView.initialize(item.getMenuItem(), 0);
                    return;
                case 1:
                    ((TextView) holder.itemView).setText(((NavigationMenuTextItem) this.mItems.get(position)).getMenuItem().getTitle());
                    return;
                case 2:
                    NavigationMenuSeparatorItem item2 = (NavigationMenuSeparatorItem) this.mItems.get(position);
                    holder.itemView.setPadding(0, item2.getPaddingTop(), 0, item2.getPaddingBottom());
                    return;
                default:
                    return;
            }
        }

        public void onViewRecycled(ViewHolder viewHolder) {
            ViewHolder holder = viewHolder;
            ViewHolder viewHolder2 = holder;
            if (holder instanceof NormalViewHolder) {
                ((NavigationMenuItemView) holder.itemView).recycle();
            }
        }

        public void update() {
            prepareMenuItems();
            notifyDataSetChanged();
        }

        private void prepareMenuItems() {
            if (!this.mUpdateSuspended) {
                this.mUpdateSuspended = true;
                this.mItems.clear();
                boolean add = this.mItems.add(new NavigationMenuHeaderItem());
                int currentGroupId = -1;
                int currentGroupStart = 0;
                boolean currentGroupHasIcon = false;
                int totalSize = this.this$0.mMenu.getVisibleItems().size();
                for (int i = 0; i < totalSize; i++) {
                    MenuItemImpl menuItemImpl = (MenuItemImpl) this.this$0.mMenu.getVisibleItems().get(i);
                    MenuItemImpl item = menuItemImpl;
                    if (menuItemImpl.isChecked()) {
                        setCheckedItem(item);
                    }
                    if (item.isCheckable()) {
                        item.setExclusiveCheckable(false);
                    }
                    if (!item.hasSubMenu()) {
                        int groupId = item.getGroupId();
                        int groupId2 = groupId;
                        if (groupId != currentGroupId) {
                            currentGroupStart = this.mItems.size();
                            currentGroupHasIcon = item.getIcon() != null;
                            if (i != 0) {
                                currentGroupStart++;
                                boolean add2 = this.mItems.add(new NavigationMenuSeparatorItem(this.this$0.mPaddingSeparator, this.this$0.mPaddingSeparator));
                            }
                        } else if (!currentGroupHasIcon && item.getIcon() != null) {
                            currentGroupHasIcon = true;
                            appendTransparentIconIfMissing(currentGroupStart, this.mItems.size());
                        }
                        NavigationMenuTextItem navigationMenuTextItem = new NavigationMenuTextItem(item);
                        NavigationMenuTextItem textItem = navigationMenuTextItem;
                        navigationMenuTextItem.needsEmptyIcon = currentGroupHasIcon;
                        boolean add3 = this.mItems.add(textItem);
                        currentGroupId = groupId2;
                    } else {
                        SubMenu subMenu = item.getSubMenu();
                        SubMenu subMenu2 = subMenu;
                        if (subMenu.hasVisibleItems()) {
                            if (i != 0) {
                                boolean add4 = this.mItems.add(new NavigationMenuSeparatorItem(this.this$0.mPaddingSeparator, 0));
                            }
                            boolean add5 = this.mItems.add(new NavigationMenuTextItem(item));
                            boolean subMenuHasIcon = false;
                            int subMenuStart = this.mItems.size();
                            int size = subMenu2.size();
                            for (int j = 0; j < size; j++) {
                                MenuItemImpl menuItemImpl2 = (MenuItemImpl) subMenu2.getItem(j);
                                MenuItemImpl subMenuItem = menuItemImpl2;
                                if (menuItemImpl2.isVisible()) {
                                    if (!subMenuHasIcon && subMenuItem.getIcon() != null) {
                                        subMenuHasIcon = true;
                                    }
                                    if (subMenuItem.isCheckable()) {
                                        subMenuItem.setExclusiveCheckable(false);
                                    }
                                    if (item.isChecked()) {
                                        setCheckedItem(item);
                                    }
                                    boolean add6 = this.mItems.add(new NavigationMenuTextItem(subMenuItem));
                                }
                            }
                            if (subMenuHasIcon) {
                                appendTransparentIconIfMissing(subMenuStart, this.mItems.size());
                            }
                        }
                    }
                }
                this.mUpdateSuspended = false;
            }
        }

        private void appendTransparentIconIfMissing(int i, int i2) {
            int startIndex = i;
            int endIndex = i2;
            int i3 = startIndex;
            int i4 = endIndex;
            for (int i5 = startIndex; i5 < endIndex; i5++) {
                NavigationMenuTextItem navigationMenuTextItem = (NavigationMenuTextItem) this.mItems.get(i5);
                NavigationMenuTextItem navigationMenuTextItem2 = navigationMenuTextItem;
                navigationMenuTextItem.needsEmptyIcon = true;
            }
        }

        public void setCheckedItem(MenuItemImpl menuItemImpl) {
            MenuItemImpl checkedItem = menuItemImpl;
            MenuItemImpl menuItemImpl2 = checkedItem;
            if (this.mCheckedItem != checkedItem && checkedItem.isCheckable()) {
                if (this.mCheckedItem != null) {
                    MenuItem checked = this.mCheckedItem.setChecked(false);
                }
                this.mCheckedItem = checkedItem;
                MenuItem checked2 = checkedItem.setChecked(true);
            }
        }

        public Bundle createInstanceState() {
            Bundle state = new Bundle();
            if (this.mCheckedItem != null) {
                state.putInt(STATE_CHECKED_ITEM, this.mCheckedItem.getItemId());
            }
            SparseArray sparseArray = new SparseArray();
            Iterator it = this.mItems.iterator();
            while (it.hasNext()) {
                NavigationMenuItem navigationMenuItem = (NavigationMenuItem) it.next();
                NavigationMenuItem navigationMenuItem2 = navigationMenuItem;
                if (navigationMenuItem instanceof NavigationMenuTextItem) {
                    MenuItemImpl menuItem = ((NavigationMenuTextItem) navigationMenuItem2).getMenuItem();
                    MenuItemImpl item = menuItem;
                    View actionView = menuItem == null ? null : item.getActionView();
                    if (actionView != null) {
                        ParcelableSparseArray container = new ParcelableSparseArray();
                        actionView.saveHierarchyState(container);
                        sparseArray.put(item.getItemId(), container);
                    }
                }
            }
            state.putSparseParcelableArray(STATE_ACTION_VIEWS, sparseArray);
            return state;
        }

        public void restoreInstanceState(Bundle bundle) {
            Bundle state = bundle;
            Bundle bundle2 = state;
            int i = state.getInt(STATE_CHECKED_ITEM, 0);
            int checkedItem = i;
            if (i != 0) {
                this.mUpdateSuspended = true;
                Iterator it = this.mItems.iterator();
                while (true) {
                    if (it.hasNext()) {
                        NavigationMenuItem navigationMenuItem = (NavigationMenuItem) it.next();
                        NavigationMenuItem item = navigationMenuItem;
                        if (navigationMenuItem instanceof NavigationMenuTextItem) {
                            MenuItemImpl menuItem = ((NavigationMenuTextItem) item).getMenuItem();
                            MenuItemImpl menuItem2 = menuItem;
                            if (menuItem != null && menuItem2.getItemId() == checkedItem) {
                                setCheckedItem(menuItem2);
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }
                this.mUpdateSuspended = false;
                prepareMenuItems();
            }
            SparseArray sparseParcelableArray = state.getSparseParcelableArray(STATE_ACTION_VIEWS);
            Iterator it2 = this.mItems.iterator();
            while (it2.hasNext()) {
                NavigationMenuItem navigationMenuItem2 = (NavigationMenuItem) it2.next();
                NavigationMenuItem navigationMenuItem3 = navigationMenuItem2;
                if (navigationMenuItem2 instanceof NavigationMenuTextItem) {
                    MenuItemImpl menuItem3 = ((NavigationMenuTextItem) navigationMenuItem3).getMenuItem();
                    MenuItemImpl item2 = menuItem3;
                    View actionView = menuItem3 == null ? null : item2.getActionView();
                    if (actionView != null) {
                        actionView.restoreHierarchyState((SparseArray) sparseParcelableArray.get(item2.getItemId()));
                    }
                }
            }
        }

        public void setUpdateSuspended(boolean z) {
            this.mUpdateSuspended = z;
        }
    }

    private static class NavigationMenuHeaderItem implements NavigationMenuItem {
        NavigationMenuHeaderItem() {
        }
    }

    private interface NavigationMenuItem {
    }

    private static class NavigationMenuSeparatorItem implements NavigationMenuItem {
        private final int mPaddingBottom;
        private final int mPaddingTop;

        public NavigationMenuSeparatorItem(int i, int i2) {
            int paddingTop = i;
            int paddingBottom = i2;
            int i3 = paddingTop;
            int i4 = paddingBottom;
            this.mPaddingTop = paddingTop;
            this.mPaddingBottom = paddingBottom;
        }

        public int getPaddingTop() {
            return this.mPaddingTop;
        }

        public int getPaddingBottom() {
            return this.mPaddingBottom;
        }
    }

    private static class NavigationMenuTextItem implements NavigationMenuItem {
        private final MenuItemImpl mMenuItem;
        boolean needsEmptyIcon;

        NavigationMenuTextItem(MenuItemImpl menuItemImpl) {
            MenuItemImpl item = menuItemImpl;
            MenuItemImpl menuItemImpl2 = item;
            this.mMenuItem = item;
        }

        public MenuItemImpl getMenuItem() {
            return this.mMenuItem;
        }
    }

    private static class NormalViewHolder extends ViewHolder {
        public NormalViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, OnClickListener onClickListener) {
            LayoutInflater inflater = layoutInflater;
            ViewGroup parent = viewGroup;
            OnClickListener listener = onClickListener;
            LayoutInflater layoutInflater2 = inflater;
            ViewGroup viewGroup2 = parent;
            OnClickListener onClickListener2 = listener;
            super(inflater.inflate(C0001R.layout.design_navigation_item, parent, false));
            this.itemView.setOnClickListener(listener);
        }
    }

    private static class SeparatorViewHolder extends ViewHolder {
        public SeparatorViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            LayoutInflater inflater = layoutInflater;
            ViewGroup parent = viewGroup;
            LayoutInflater layoutInflater2 = inflater;
            ViewGroup viewGroup2 = parent;
            super(inflater.inflate(C0001R.layout.design_navigation_item_separator, parent, false));
        }
    }

    private static class SubheaderViewHolder extends ViewHolder {
        public SubheaderViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            LayoutInflater inflater = layoutInflater;
            ViewGroup parent = viewGroup;
            LayoutInflater layoutInflater2 = inflater;
            ViewGroup viewGroup2 = parent;
            super(inflater.inflate(C0001R.layout.design_navigation_item_subheader, parent, false));
        }
    }

    private static abstract class ViewHolder extends android.support.p003v7.widget.RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            View itemView = view;
            View view2 = itemView;
            super(itemView);
        }
    }

    public NavigationMenuPresenter() {
    }

    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        Context context2 = context;
        MenuBuilder menu = menuBuilder;
        Context context3 = context2;
        MenuBuilder menuBuilder2 = menu;
        this.mLayoutInflater = LayoutInflater.from(context2);
        this.mMenu = menu;
        this.mPaddingSeparator = context2.getResources().getDimensionPixelOffset(C0001R.dimen.design_navigation_separator_vertical_padding);
    }

    public MenuView getMenuView(ViewGroup viewGroup) {
        ViewGroup root = viewGroup;
        ViewGroup viewGroup2 = root;
        if (this.mMenuView == null) {
            this.mMenuView = (NavigationMenuView) this.mLayoutInflater.inflate(C0001R.layout.design_navigation_menu, root, false);
            if (this.mAdapter == null) {
                this.mAdapter = new NavigationMenuAdapter(this);
            }
            this.mHeaderLayout = (LinearLayout) this.mLayoutInflater.inflate(C0001R.layout.design_navigation_item_header, this.mMenuView, false);
            this.mMenuView.setAdapter(this.mAdapter);
        }
        return this.mMenuView;
    }

    public void updateMenuView(boolean z) {
        boolean z2 = z;
        if (this.mAdapter != null) {
            this.mAdapter.update();
        }
    }

    public void setCallback(Callback callback) {
        Callback cb = callback;
        Callback callback2 = cb;
        this.mCallback = cb;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
        return false;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        boolean allMenusAreClosing = z;
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menu, allMenusAreClosing);
        }
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

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        int id = i;
        int i2 = id;
        this.mId = id;
    }

    public Parcelable onSaveInstanceState() {
        if (VERSION.SDK_INT < 11) {
            return null;
        }
        Bundle state = new Bundle();
        if (this.mMenuView != null) {
            SparseArray sparseArray = new SparseArray();
            this.mMenuView.saveHierarchyState(sparseArray);
            state.putSparseParcelableArray("android:menu:list", sparseArray);
        }
        if (this.mAdapter != null) {
            state.putBundle(STATE_ADAPTER, this.mAdapter.createInstanceState());
        }
        if (this.mHeaderLayout != null) {
            SparseArray sparseArray2 = new SparseArray();
            this.mHeaderLayout.saveHierarchyState(sparseArray2);
            state.putSparseParcelableArray(STATE_HEADER, sparseArray2);
        }
        return state;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2 = parcelable;
        Parcelable parcelable3 = parcelable2;
        if (parcelable2 instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable2;
            Bundle state = bundle;
            SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:list");
            SparseArray sparseArray = sparseParcelableArray;
            if (sparseParcelableArray != null) {
                this.mMenuView.restoreHierarchyState(sparseArray);
            }
            Bundle bundle2 = state.getBundle(STATE_ADAPTER);
            Bundle adapterState = bundle2;
            if (bundle2 != null) {
                this.mAdapter.restoreInstanceState(adapterState);
            }
            SparseArray sparseParcelableArray2 = state.getSparseParcelableArray(STATE_HEADER);
            SparseArray sparseArray2 = sparseParcelableArray2;
            if (sparseParcelableArray2 != null) {
                this.mHeaderLayout.restoreHierarchyState(sparseArray2);
            }
        }
    }

    public void setCheckedItem(MenuItemImpl menuItemImpl) {
        MenuItemImpl item = menuItemImpl;
        MenuItemImpl menuItemImpl2 = item;
        this.mAdapter.setCheckedItem(item);
    }

    public View inflateHeaderView(@LayoutRes int i) {
        int res = i;
        int i2 = res;
        View view = this.mLayoutInflater.inflate(res, this.mHeaderLayout, false);
        addHeaderView(view);
        return view;
    }

    public void addHeaderView(@NonNull View view) {
        View view2 = view;
        View view3 = view2;
        this.mHeaderLayout.addView(view2);
        this.mMenuView.setPadding(0, 0, 0, this.mMenuView.getPaddingBottom());
    }

    public void removeHeaderView(@NonNull View view) {
        View view2 = view;
        View view3 = view2;
        this.mHeaderLayout.removeView(view2);
        if (this.mHeaderLayout.getChildCount() == 0) {
            this.mMenuView.setPadding(0, this.mPaddingTopDefault, 0, this.mMenuView.getPaddingBottom());
        }
    }

    public int getHeaderCount() {
        return this.mHeaderLayout.getChildCount();
    }

    public View getHeaderView(int i) {
        int index = i;
        int i2 = index;
        return this.mHeaderLayout.getChildAt(index);
    }

    @Nullable
    public ColorStateList getItemTintList() {
        return this.mIconTintList;
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        this.mIconTintList = tint;
        updateMenuView(false);
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.mTextColor;
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        ColorStateList textColor = colorStateList;
        ColorStateList colorStateList2 = textColor;
        this.mTextColor = textColor;
        updateMenuView(false);
    }

    public void setItemTextAppearance(@StyleRes int i) {
        int resId = i;
        int i2 = resId;
        this.mTextAppearance = resId;
        this.mTextAppearanceSet = true;
        updateMenuView(false);
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.mItemBackground;
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        Drawable itemBackground = drawable;
        Drawable drawable2 = itemBackground;
        this.mItemBackground = itemBackground;
        updateMenuView(false);
    }

    public void setUpdateSuspended(boolean z) {
        boolean updateSuspended = z;
        if (this.mAdapter != null) {
            this.mAdapter.setUpdateSuspended(updateSuspended);
        }
    }

    public void dispatchApplyWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat insets = windowInsetsCompat;
        WindowInsetsCompat windowInsetsCompat2 = insets;
        int top = insets.getSystemWindowInsetTop();
        if (this.mPaddingTopDefault != top) {
            this.mPaddingTopDefault = top;
            if (this.mHeaderLayout.getChildCount() == 0) {
                this.mMenuView.setPadding(0, this.mPaddingTopDefault, 0, this.mMenuView.getPaddingBottom());
            }
        }
        WindowInsetsCompat dispatchApplyWindowInsets = ViewCompat.dispatchApplyWindowInsets(this.mHeaderLayout, insets);
    }
}
