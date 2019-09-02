package android.support.p003v7.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.internal.view.SupportMenu;
import android.support.p000v4.view.ActionProvider;
import android.support.p000v4.view.MenuItemCompat;
import android.support.p003v7.appcompat.C0268R;
import android.util.SparseArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyCharacterMap.KeyData;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.MenuBuilder */
public class MenuBuilder implements SupportMenu {
    private static final String ACTION_VIEW_STATES_KEY = "android:menu:actionviewstates";
    private static final String EXPANDED_ACTION_VIEW_ID = "android:menu:expandedactionview";
    private static final String PRESENTER_KEY = "android:menu:presenters";
    private static final String TAG = "MenuBuilder";
    private static final int[] sCategoryToOrder;
    private ArrayList<MenuItemImpl> mActionItems;
    private Callback mCallback;
    private final Context mContext;
    private ContextMenuInfo mCurrentMenuInfo;
    private int mDefaultShowAsAction = 0;
    private MenuItemImpl mExpandedItem;
    private SparseArray<Parcelable> mFrozenViewStates;
    Drawable mHeaderIcon;
    CharSequence mHeaderTitle;
    View mHeaderView;
    private boolean mIsActionItemsStale;
    private boolean mIsClosing = false;
    private boolean mIsVisibleItemsStale;
    private ArrayList<MenuItemImpl> mItems;
    private boolean mItemsChangedWhileDispatchPrevented = false;
    private ArrayList<MenuItemImpl> mNonActionItems;
    private boolean mOptionalIconsVisible = false;
    private boolean mOverrideVisibleItems;
    private CopyOnWriteArrayList<WeakReference<MenuPresenter>> mPresenters = new CopyOnWriteArrayList<>();
    private boolean mPreventDispatchingItemsChanged = false;
    private boolean mQwertyMode;
    private final Resources mResources;
    private boolean mShortcutsVisible;
    private boolean mStructureChangedWhileDispatchPrevented = false;
    private ArrayList<MenuItemImpl> mTempShortcutItemList = new ArrayList<>();
    private ArrayList<MenuItemImpl> mVisibleItems;

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.view.menu.MenuBuilder$Callback */
    public interface Callback {
        boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem);

        void onMenuModeChange(MenuBuilder menuBuilder);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.view.menu.MenuBuilder$ItemInvoker */
    public interface ItemInvoker {
        boolean invokeItem(MenuItemImpl menuItemImpl);
    }

    static {
        int[] iArr = new int[6];
        iArr[0] = 1;
        iArr[1] = 4;
        iArr[2] = 5;
        iArr[3] = 3;
        iArr[4] = 2;
        iArr[5] = 0;
        sCategoryToOrder = iArr;
    }

    public MenuBuilder(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this.mContext = context2;
        this.mResources = context2.getResources();
        this.mItems = new ArrayList<>();
        this.mVisibleItems = new ArrayList<>();
        this.mIsVisibleItemsStale = true;
        this.mActionItems = new ArrayList<>();
        this.mNonActionItems = new ArrayList<>();
        this.mIsActionItemsStale = true;
        setShortcutsVisibleInner(true);
    }

    public MenuBuilder setDefaultShowAsAction(int i) {
        int defaultShowAsAction = i;
        int i2 = defaultShowAsAction;
        this.mDefaultShowAsAction = defaultShowAsAction;
        return this;
    }

    public void addMenuPresenter(MenuPresenter menuPresenter) {
        MenuPresenter presenter = menuPresenter;
        MenuPresenter menuPresenter2 = presenter;
        addMenuPresenter(presenter, this.mContext);
    }

    public void addMenuPresenter(MenuPresenter menuPresenter, Context context) {
        MenuPresenter presenter = menuPresenter;
        Context menuContext = context;
        MenuPresenter menuPresenter2 = presenter;
        Context context2 = menuContext;
        boolean add = this.mPresenters.add(new WeakReference(presenter));
        presenter.initForMenu(menuContext, this);
        this.mIsActionItemsStale = true;
    }

    public void removeMenuPresenter(MenuPresenter menuPresenter) {
        MenuPresenter presenter = menuPresenter;
        MenuPresenter menuPresenter2 = presenter;
        Iterator it = this.mPresenters.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            WeakReference weakReference2 = weakReference;
            MenuPresenter menuPresenter3 = (MenuPresenter) weakReference.get();
            MenuPresenter item = menuPresenter3;
            if (menuPresenter3 == null || item == presenter) {
                boolean remove = this.mPresenters.remove(weakReference2);
            }
        }
    }

    private void dispatchPresenterUpdate(boolean z) {
        boolean cleared = z;
        if (!this.mPresenters.isEmpty()) {
            stopDispatchingItemsChanged();
            Iterator it = this.mPresenters.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                WeakReference weakReference2 = weakReference;
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
                MenuPresenter presenter = menuPresenter;
                if (menuPresenter != null) {
                    presenter.updateMenuView(cleared);
                } else {
                    boolean remove = this.mPresenters.remove(weakReference2);
                }
            }
            startDispatchingItemsChanged();
        }
    }

    private boolean dispatchSubMenuSelected(SubMenuBuilder subMenuBuilder, MenuPresenter menuPresenter) {
        SubMenuBuilder subMenu = subMenuBuilder;
        MenuPresenter preferredPresenter = menuPresenter;
        SubMenuBuilder subMenuBuilder2 = subMenu;
        MenuPresenter menuPresenter2 = preferredPresenter;
        if (this.mPresenters.isEmpty()) {
            return false;
        }
        boolean result = false;
        if (preferredPresenter != null) {
            result = preferredPresenter.onSubMenuSelected(subMenu);
        }
        Iterator it = this.mPresenters.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            WeakReference weakReference2 = weakReference;
            MenuPresenter menuPresenter3 = (MenuPresenter) weakReference.get();
            MenuPresenter presenter = menuPresenter3;
            if (menuPresenter3 == null) {
                boolean remove = this.mPresenters.remove(weakReference2);
            } else if (!result) {
                result = presenter.onSubMenuSelected(subMenu);
            }
        }
        return result;
    }

    private void dispatchSaveInstanceState(Bundle bundle) {
        Bundle outState = bundle;
        Bundle bundle2 = outState;
        if (!this.mPresenters.isEmpty()) {
            SparseArray sparseArray = new SparseArray();
            Iterator it = this.mPresenters.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                WeakReference weakReference2 = weakReference;
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
                MenuPresenter presenter = menuPresenter;
                if (menuPresenter != null) {
                    int id = presenter.getId();
                    int id2 = id;
                    if (id > 0) {
                        Parcelable onSaveInstanceState = presenter.onSaveInstanceState();
                        Parcelable state = onSaveInstanceState;
                        if (onSaveInstanceState != null) {
                            sparseArray.put(id2, state);
                        }
                    }
                } else {
                    boolean remove = this.mPresenters.remove(weakReference2);
                }
            }
            outState.putSparseParcelableArray(PRESENTER_KEY, sparseArray);
        }
    }

    private void dispatchRestoreInstanceState(Bundle bundle) {
        Bundle state = bundle;
        Bundle bundle2 = state;
        SparseArray sparseParcelableArray = state.getSparseParcelableArray(PRESENTER_KEY);
        SparseArray sparseArray = sparseParcelableArray;
        if (sparseParcelableArray != null && !this.mPresenters.isEmpty()) {
            Iterator it = this.mPresenters.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                WeakReference weakReference2 = weakReference;
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
                MenuPresenter presenter = menuPresenter;
                if (menuPresenter != null) {
                    int id = presenter.getId();
                    int id2 = id;
                    if (id > 0) {
                        Parcelable parcelable = (Parcelable) sparseArray.get(id2);
                        Parcelable parcel = parcelable;
                        if (parcelable != null) {
                            presenter.onRestoreInstanceState(parcel);
                        }
                    }
                } else {
                    boolean remove = this.mPresenters.remove(weakReference2);
                }
            }
        }
    }

    public void savePresenterStates(Bundle bundle) {
        Bundle outState = bundle;
        Bundle bundle2 = outState;
        dispatchSaveInstanceState(outState);
    }

    public void restorePresenterStates(Bundle bundle) {
        Bundle state = bundle;
        Bundle bundle2 = state;
        dispatchRestoreInstanceState(state);
    }

    public void saveActionViewStates(Bundle bundle) {
        Bundle outStates = bundle;
        Bundle bundle2 = outStates;
        SparseArray sparseArray = null;
        int itemCount = size();
        for (int i = 0; i < itemCount; i++) {
            MenuItem item = getItem(i);
            MenuItem item2 = item;
            View actionView = MenuItemCompat.getActionView(item);
            View v = actionView;
            if (!(actionView == null || v.getId() == -1)) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                }
                v.saveHierarchyState(sparseArray);
                if (MenuItemCompat.isActionViewExpanded(item2)) {
                    outStates.putInt(EXPANDED_ACTION_VIEW_ID, item2.getItemId());
                }
            }
            if (item2.hasSubMenu()) {
                SubMenuBuilder subMenuBuilder = (SubMenuBuilder) item2.getSubMenu();
                SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
                subMenuBuilder.saveActionViewStates(outStates);
            }
        }
        if (sparseArray != null) {
            outStates.putSparseParcelableArray(getActionViewStatesKey(), sparseArray);
        }
    }

    public void restoreActionViewStates(Bundle bundle) {
        Bundle states = bundle;
        Bundle bundle2 = states;
        if (states != null) {
            SparseArray sparseParcelableArray = states.getSparseParcelableArray(getActionViewStatesKey());
            int itemCount = size();
            for (int i = 0; i < itemCount; i++) {
                MenuItem item = getItem(i);
                MenuItem item2 = item;
                View actionView = MenuItemCompat.getActionView(item);
                View v = actionView;
                if (!(actionView == null || v.getId() == -1)) {
                    v.restoreHierarchyState(sparseParcelableArray);
                }
                if (item2.hasSubMenu()) {
                    SubMenuBuilder subMenuBuilder = (SubMenuBuilder) item2.getSubMenu();
                    SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
                    subMenuBuilder.restoreActionViewStates(states);
                }
            }
            int i2 = states.getInt(EXPANDED_ACTION_VIEW_ID);
            int i3 = i2;
            if (i2 > 0) {
                MenuItem findItem = findItem(i3);
                MenuItem itemToExpand = findItem;
                if (findItem != null) {
                    boolean expandActionView = MenuItemCompat.expandActionView(itemToExpand);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public String getActionViewStatesKey() {
        return ACTION_VIEW_STATES_KEY;
    }

    public void setCallback(Callback callback) {
        Callback cb = callback;
        Callback callback2 = cb;
        this.mCallback = cb;
    }

    /* access modifiers changed from: protected */
    public MenuItem addInternal(int i, int i2, int i3, CharSequence charSequence) {
        int group = i;
        int id = i2;
        int categoryOrder = i3;
        CharSequence title = charSequence;
        int i4 = group;
        int i5 = id;
        int i6 = categoryOrder;
        CharSequence charSequence2 = title;
        int ordering = getOrdering(categoryOrder);
        int i7 = ordering;
        MenuItemImpl item = createNewMenuItem(group, id, categoryOrder, ordering, title, this.mDefaultShowAsAction);
        if (this.mCurrentMenuInfo != null) {
            item.setMenuInfo(this.mCurrentMenuInfo);
        }
        this.mItems.add(findInsertIndex(this.mItems, ordering), item);
        onItemsChanged(true);
        return item;
    }

    private MenuItemImpl createNewMenuItem(int i, int i2, int i3, int i4, CharSequence charSequence, int i5) {
        int group = i;
        int id = i2;
        int categoryOrder = i3;
        int ordering = i4;
        CharSequence title = charSequence;
        int defaultShowAsAction = i5;
        int i6 = group;
        int i7 = id;
        int i8 = categoryOrder;
        int i9 = ordering;
        CharSequence charSequence2 = title;
        int i10 = defaultShowAsAction;
        MenuItemImpl menuItemImpl = new MenuItemImpl(this, group, id, categoryOrder, ordering, title, defaultShowAsAction);
        return menuItemImpl;
    }

    public MenuItem add(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        return addInternal(0, 0, 0, title);
    }

    public MenuItem add(int i) {
        int titleRes = i;
        int i2 = titleRes;
        return addInternal(0, 0, 0, this.mResources.getString(titleRes));
    }

    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        int group = i;
        int id = i2;
        int categoryOrder = i3;
        CharSequence title = charSequence;
        int i4 = group;
        int i5 = id;
        int i6 = categoryOrder;
        CharSequence charSequence2 = title;
        return addInternal(group, id, categoryOrder, title);
    }

    public MenuItem add(int i, int i2, int i3, int i4) {
        int group = i;
        int id = i2;
        int categoryOrder = i3;
        int title = i4;
        int i5 = group;
        int i6 = id;
        int i7 = categoryOrder;
        int i8 = title;
        return addInternal(group, id, categoryOrder, this.mResources.getString(title));
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        return addSubMenu(0, 0, 0, title);
    }

    public SubMenu addSubMenu(int i) {
        int titleRes = i;
        int i2 = titleRes;
        return addSubMenu(0, 0, 0, (CharSequence) this.mResources.getString(titleRes));
    }

    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        int group = i;
        int id = i2;
        int categoryOrder = i3;
        CharSequence title = charSequence;
        int i4 = group;
        int i5 = id;
        int i6 = categoryOrder;
        CharSequence charSequence2 = title;
        MenuItemImpl item = (MenuItemImpl) addInternal(group, id, categoryOrder, title);
        SubMenuBuilder subMenu = new SubMenuBuilder(this.mContext, this, item);
        item.setSubMenu(subMenu);
        return subMenu;
    }

    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        int group = i;
        int id = i2;
        int categoryOrder = i3;
        int title = i4;
        int i5 = group;
        int i6 = id;
        int i7 = categoryOrder;
        int i8 = title;
        return addSubMenu(group, id, categoryOrder, (CharSequence) this.mResources.getString(title));
    }

    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        int group = i;
        int id = i2;
        int categoryOrder = i3;
        ComponentName caller = componentName;
        Intent[] specifics = intentArr;
        Intent intent2 = intent;
        int flags = i4;
        MenuItem[] outSpecificItems = menuItemArr;
        int i5 = group;
        int i6 = id;
        int i7 = categoryOrder;
        ComponentName componentName2 = caller;
        Intent[] intentArr2 = specifics;
        Intent intent3 = intent2;
        int i8 = flags;
        MenuItem[] menuItemArr2 = outSpecificItems;
        PackageManager packageManager = this.mContext.getPackageManager();
        PackageManager pm = packageManager;
        List queryIntentActivityOptions = packageManager.queryIntentActivityOptions(caller, specifics, intent2, 0);
        List list = queryIntentActivityOptions;
        int N = queryIntentActivityOptions == null ? 0 : list.size();
        if ((flags & 1) == 0) {
            removeGroup(group);
        }
        for (int i9 = 0; i9 < N; i9++) {
            ResolveInfo ri = (ResolveInfo) list.get(i9);
            Intent intent4 = new Intent(ri.specificIndex >= 0 ? specifics[ri.specificIndex] : intent2);
            Intent rintent = intent4;
            Intent intent5 = intent4;
            ComponentName componentName3 = new ComponentName(ri.activityInfo.applicationInfo.packageName, ri.activityInfo.name);
            Intent component = intent5.setComponent(componentName3);
            MenuItem item = add(group, id, categoryOrder, ri.loadLabel(pm)).setIcon(ri.loadIcon(pm)).setIntent(rintent);
            if (outSpecificItems != null && ri.specificIndex >= 0) {
                outSpecificItems[ri.specificIndex] = item;
            }
        }
        return N;
    }

    public void removeItem(int i) {
        int id = i;
        int i2 = id;
        removeItemAtInt(findItemIndex(id), true);
    }

    public void removeGroup(int i) {
        int group = i;
        int i2 = group;
        int findGroupIndex = findGroupIndex(group);
        int i3 = findGroupIndex;
        if (findGroupIndex >= 0) {
            int maxRemovable = this.mItems.size() - i3;
            int numRemoved = 0;
            while (true) {
                int i4 = numRemoved;
                numRemoved++;
                if (i4 < maxRemovable && ((MenuItemImpl) this.mItems.get(i3)).getGroupId() == group) {
                    removeItemAtInt(i3, false);
                }
            }
            onItemsChanged(true);
        }
    }

    private void removeItemAtInt(int i, boolean z) {
        int index = i;
        int i2 = index;
        boolean updateChildrenOnMenuViews = z;
        if (index >= 0 && index < this.mItems.size()) {
            Object remove = this.mItems.remove(index);
            if (updateChildrenOnMenuViews) {
                onItemsChanged(true);
            }
        }
    }

    public void removeItemAt(int i) {
        int index = i;
        int i2 = index;
        removeItemAtInt(index, true);
    }

    public void clearAll() {
        this.mPreventDispatchingItemsChanged = true;
        clear();
        clearHeader();
        this.mPreventDispatchingItemsChanged = false;
        this.mItemsChangedWhileDispatchPrevented = false;
        this.mStructureChangedWhileDispatchPrevented = false;
        onItemsChanged(true);
    }

    public void clear() {
        if (this.mExpandedItem != null) {
            boolean collapseItemActionView = collapseItemActionView(this.mExpandedItem);
        }
        this.mItems.clear();
        onItemsChanged(true);
    }

    /* access modifiers changed from: 0000 */
    public void setExclusiveItemChecked(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        int group = item.getGroupId();
        int N = this.mItems.size();
        stopDispatchingItemsChanged();
        for (int i = 0; i < N; i++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i);
            MenuItemImpl curItem = menuItemImpl;
            if (menuItemImpl.getGroupId() == group && curItem.isExclusiveCheckable() && curItem.isCheckable()) {
                curItem.setCheckedInt(curItem == item);
            }
        }
        startDispatchingItemsChanged();
    }

    public void setGroupCheckable(int i, boolean z, boolean z2) {
        int group = i;
        int i2 = group;
        boolean checkable = z;
        boolean exclusive = z2;
        int N = this.mItems.size();
        for (int i3 = 0; i3 < N; i3++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i3);
            MenuItemImpl item = menuItemImpl;
            if (menuItemImpl.getGroupId() == group) {
                item.setExclusiveCheckable(exclusive);
                MenuItem checkable2 = item.setCheckable(checkable);
            }
        }
    }

    public void setGroupVisible(int i, boolean z) {
        int group = i;
        int i2 = group;
        boolean visible = z;
        int N = this.mItems.size();
        boolean changedAtLeastOneItem = false;
        for (int i3 = 0; i3 < N; i3++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i3);
            MenuItemImpl item = menuItemImpl;
            if (menuItemImpl.getGroupId() == group && item.setVisibleInt(visible)) {
                changedAtLeastOneItem = true;
            }
        }
        if (changedAtLeastOneItem) {
            onItemsChanged(true);
        }
    }

    public void setGroupEnabled(int i, boolean z) {
        int group = i;
        int i2 = group;
        boolean enabled = z;
        int N = this.mItems.size();
        for (int i3 = 0; i3 < N; i3++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i3);
            MenuItemImpl item = menuItemImpl;
            if (menuItemImpl.getGroupId() == group) {
                MenuItem enabled2 = item.setEnabled(enabled);
            }
        }
    }

    public boolean hasVisibleItems() {
        if (this.mOverrideVisibleItems) {
            return true;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i);
            MenuItemImpl menuItemImpl2 = menuItemImpl;
            if (menuItemImpl.isVisible()) {
                return true;
            }
        }
        return false;
    }

    public MenuItem findItem(int i) {
        int id = i;
        int i2 = id;
        int size = size();
        for (int i3 = 0; i3 < size; i3++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i3);
            MenuItemImpl item = menuItemImpl;
            if (menuItemImpl.getItemId() == id) {
                return item;
            }
            if (item.hasSubMenu()) {
                MenuItem findItem = item.getSubMenu().findItem(id);
                MenuItem possibleItem = findItem;
                if (findItem != null) {
                    return possibleItem;
                }
            }
        }
        return null;
    }

    public int findItemIndex(int i) {
        int id = i;
        int i2 = id;
        int size = size();
        for (int i3 = 0; i3 < size; i3++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i3);
            MenuItemImpl menuItemImpl2 = menuItemImpl;
            if (menuItemImpl.getItemId() == id) {
                return i3;
            }
        }
        return -1;
    }

    public int findGroupIndex(int i) {
        int group = i;
        int i2 = group;
        return findGroupIndex(group, 0);
    }

    public int findGroupIndex(int i, int i2) {
        int group = i;
        int start = i2;
        int i3 = group;
        int start2 = start;
        int size = size();
        if (start < 0) {
            start2 = 0;
        }
        for (int i4 = start2; i4 < size; i4++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i4);
            MenuItemImpl menuItemImpl2 = menuItemImpl;
            if (menuItemImpl.getGroupId() == group) {
                return i4;
            }
        }
        return -1;
    }

    public int size() {
        return this.mItems.size();
    }

    public MenuItem getItem(int i) {
        int index = i;
        int i2 = index;
        return (MenuItem) this.mItems.get(index);
    }

    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        return findItemWithShortcutForKey(keyCode, event) != null;
    }

    public void setQwertyMode(boolean z) {
        this.mQwertyMode = z;
        onItemsChanged(false);
    }

    private static int getOrdering(int i) {
        int categoryOrder = i;
        int i2 = categoryOrder;
        int i3 = (categoryOrder & SupportMenu.CATEGORY_MASK) >> 16;
        int index = i3;
        if (i3 >= 0 && index < sCategoryToOrder.length) {
            return (sCategoryToOrder[index] << 16) | (categoryOrder & SupportMenu.USER_MASK);
        }
        throw new IllegalArgumentException("order does not contain a valid category.");
    }

    /* access modifiers changed from: 0000 */
    public boolean isQwertyMode() {
        return this.mQwertyMode;
    }

    public void setShortcutsVisible(boolean z) {
        boolean shortcutsVisible = z;
        if (this.mShortcutsVisible != shortcutsVisible) {
            setShortcutsVisibleInner(shortcutsVisible);
            onItemsChanged(false);
        }
    }

    private void setShortcutsVisibleInner(boolean z) {
        this.mShortcutsVisible = z && this.mResources.getConfiguration().keyboard != 1 && this.mResources.getBoolean(C0268R.bool.abc_config_showMenuShortcutsWhenKeyboardPresent);
    }

    public boolean isShortcutsVisible() {
        return this.mShortcutsVisible;
    }

    /* access modifiers changed from: 0000 */
    public Resources getResources() {
        return this.mResources;
    }

    public Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: 0000 */
    public boolean dispatchMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        MenuBuilder menu = menuBuilder;
        MenuItem item = menuItem;
        MenuBuilder menuBuilder2 = menu;
        MenuItem menuItem2 = item;
        return this.mCallback != null && this.mCallback.onMenuItemSelected(menu, item);
    }

    public void changeMenuMode() {
        if (this.mCallback != null) {
            this.mCallback.onMenuModeChange(this);
        }
    }

    private static int findInsertIndex(ArrayList<MenuItemImpl> arrayList, int i) {
        ArrayList<MenuItemImpl> items = arrayList;
        int ordering = i;
        ArrayList<MenuItemImpl> arrayList2 = items;
        int i2 = ordering;
        for (int i3 = items.size() - 1; i3 >= 0; i3--) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) items.get(i3);
            MenuItemImpl menuItemImpl2 = menuItemImpl;
            if (menuItemImpl.getOrdering() <= ordering) {
                return i3 + 1;
            }
        }
        return 0;
    }

    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        int flags = i2;
        int i3 = keyCode;
        KeyEvent keyEvent2 = event;
        int i4 = flags;
        MenuItemImpl findItemWithShortcutForKey = findItemWithShortcutForKey(keyCode, event);
        MenuItemImpl item = findItemWithShortcutForKey;
        boolean handled = false;
        if (findItemWithShortcutForKey != null) {
            handled = performItemAction(item, flags);
        }
        if ((flags & 2) != 0) {
            close(true);
        }
        return handled;
    }

    /* access modifiers changed from: 0000 */
    public void findItemsWithShortcutForKey(List<MenuItemImpl> list, int i, KeyEvent keyEvent) {
        List<MenuItemImpl> items = list;
        int keyCode = i;
        KeyEvent event = keyEvent;
        List<MenuItemImpl> list2 = items;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        boolean qwerty = isQwertyMode();
        int metaState = event.getMetaState();
        KeyData possibleChars = new KeyData();
        boolean keyData = event.getKeyData(possibleChars);
        boolean z = keyData;
        if (keyData || keyCode == 67) {
            int N = this.mItems.size();
            for (int i3 = 0; i3 < N; i3++) {
                MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i3);
                MenuItemImpl item = menuItemImpl;
                if (menuItemImpl.hasSubMenu()) {
                    ((MenuBuilder) item.getSubMenu()).findItemsWithShortcutForKey(items, keyCode, event);
                }
                char shortcutChar = !qwerty ? item.getNumericShortcut() : item.getAlphabeticShortcut();
                if ((metaState & 5) == 0 && shortcutChar != 0 && ((shortcutChar == possibleChars.meta[0] || shortcutChar == possibleChars.meta[2] || (qwerty && shortcutChar == 8 && keyCode == 67)) && item.isEnabled())) {
                    boolean add = items.add(item);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public MenuItemImpl findItemWithShortcutForKey(int i, KeyEvent keyEvent) {
        char alphabeticShortcut;
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        ArrayList<MenuItemImpl> arrayList = this.mTempShortcutItemList;
        ArrayList<MenuItemImpl> items = arrayList;
        arrayList.clear();
        findItemsWithShortcutForKey(items, keyCode, event);
        if (items.isEmpty()) {
            return null;
        }
        int metaState = event.getMetaState();
        KeyData possibleChars = new KeyData();
        boolean keyData = event.getKeyData(possibleChars);
        int size = items.size();
        int size2 = size;
        if (size == 1) {
            return (MenuItemImpl) items.get(0);
        }
        boolean qwerty = isQwertyMode();
        for (int i3 = 0; i3 < size2; i3++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) items.get(i3);
            MenuItemImpl item = menuItemImpl;
            if (!qwerty) {
                alphabeticShortcut = menuItemImpl.getNumericShortcut();
            } else {
                alphabeticShortcut = menuItemImpl.getAlphabeticShortcut();
            }
            char shortcutChar = alphabeticShortcut;
            if ((shortcutChar == possibleChars.meta[0] && (metaState & 2) == 0) || ((shortcutChar == possibleChars.meta[2] && (metaState & 2) != 0) || (qwerty && shortcutChar == 8 && keyCode == 67))) {
                return item;
            }
        }
        return null;
    }

    public boolean performIdentifierAction(int i, int i2) {
        int id = i;
        int flags = i2;
        int i3 = id;
        int i4 = flags;
        return performItemAction(findItem(id), flags);
    }

    public boolean performItemAction(MenuItem menuItem, int i) {
        MenuItem item = menuItem;
        int flags = i;
        MenuItem menuItem2 = item;
        int i2 = flags;
        return performItemAction(item, null, flags);
    }

    public boolean performItemAction(MenuItem menuItem, MenuPresenter menuPresenter, int i) {
        MenuItem item = menuItem;
        MenuPresenter preferredPresenter = menuPresenter;
        int flags = i;
        MenuItem menuItem2 = item;
        MenuPresenter menuPresenter2 = preferredPresenter;
        int i2 = flags;
        MenuItemImpl menuItemImpl = (MenuItemImpl) item;
        MenuItemImpl itemImpl = menuItemImpl;
        if (menuItemImpl == null || !itemImpl.isEnabled()) {
            return false;
        }
        boolean invoked = itemImpl.invoke();
        ActionProvider supportActionProvider = itemImpl.getSupportActionProvider();
        ActionProvider provider = supportActionProvider;
        boolean providerHasSubMenu = supportActionProvider != null && provider.hasSubMenu();
        if (itemImpl.hasCollapsibleActionView()) {
            boolean expandActionView = invoked | itemImpl.expandActionView();
            invoked = expandActionView;
            if (expandActionView) {
                close(true);
            }
        } else if (itemImpl.hasSubMenu() || providerHasSubMenu) {
            if ((flags & 4) == 0) {
                close(false);
            }
            if (!itemImpl.hasSubMenu()) {
                SubMenuBuilder subMenuBuilder = new SubMenuBuilder(getContext(), this, itemImpl);
                itemImpl.setSubMenu(subMenuBuilder);
            }
            SubMenuBuilder subMenu = (SubMenuBuilder) itemImpl.getSubMenu();
            if (providerHasSubMenu) {
                provider.onPrepareSubMenu(subMenu);
            }
            boolean dispatchSubMenuSelected = invoked | dispatchSubMenuSelected(subMenu, preferredPresenter);
            invoked = dispatchSubMenuSelected;
            if (!dispatchSubMenuSelected) {
                close(true);
            }
        } else if ((flags & 1) == 0) {
            close(true);
        }
        return invoked;
    }

    public final void close(boolean z) {
        boolean closeAllMenus = z;
        if (!this.mIsClosing) {
            this.mIsClosing = true;
            Iterator it = this.mPresenters.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                WeakReference weakReference2 = weakReference;
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
                MenuPresenter presenter = menuPresenter;
                if (menuPresenter != null) {
                    presenter.onCloseMenu(this, closeAllMenus);
                } else {
                    boolean remove = this.mPresenters.remove(weakReference2);
                }
            }
            this.mIsClosing = false;
        }
    }

    public void close() {
        close(true);
    }

    public void onItemsChanged(boolean z) {
        boolean structureChanged = z;
        if (this.mPreventDispatchingItemsChanged) {
            this.mItemsChangedWhileDispatchPrevented = true;
            if (structureChanged) {
                this.mStructureChangedWhileDispatchPrevented = true;
                return;
            }
            return;
        }
        if (structureChanged) {
            this.mIsVisibleItemsStale = true;
            this.mIsActionItemsStale = true;
        }
        dispatchPresenterUpdate(structureChanged);
    }

    public void stopDispatchingItemsChanged() {
        if (!this.mPreventDispatchingItemsChanged) {
            this.mPreventDispatchingItemsChanged = true;
            this.mItemsChangedWhileDispatchPrevented = false;
            this.mStructureChangedWhileDispatchPrevented = false;
        }
    }

    public void startDispatchingItemsChanged() {
        this.mPreventDispatchingItemsChanged = false;
        if (this.mItemsChangedWhileDispatchPrevented) {
            this.mItemsChangedWhileDispatchPrevented = false;
            onItemsChanged(this.mStructureChangedWhileDispatchPrevented);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onItemVisibleChanged(MenuItemImpl menuItemImpl) {
        MenuItemImpl menuItemImpl2 = menuItemImpl;
        this.mIsVisibleItemsStale = true;
        onItemsChanged(true);
    }

    /* access modifiers changed from: 0000 */
    public void onItemActionRequestChanged(MenuItemImpl menuItemImpl) {
        MenuItemImpl menuItemImpl2 = menuItemImpl;
        this.mIsActionItemsStale = true;
        onItemsChanged(true);
    }

    @NonNull
    public ArrayList<MenuItemImpl> getVisibleItems() {
        if (!this.mIsVisibleItemsStale) {
            return this.mVisibleItems;
        }
        this.mVisibleItems.clear();
        int itemsSize = this.mItems.size();
        for (int i = 0; i < itemsSize; i++) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.mItems.get(i);
            MenuItemImpl item = menuItemImpl;
            if (menuItemImpl.isVisible()) {
                boolean add = this.mVisibleItems.add(item);
            }
        }
        this.mIsVisibleItemsStale = false;
        this.mIsActionItemsStale = true;
        return this.mVisibleItems;
    }

    public void flagActionItems() {
        ArrayList visibleItems = getVisibleItems();
        if (this.mIsActionItemsStale) {
            boolean flagged = false;
            Iterator it = this.mPresenters.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                WeakReference weakReference2 = weakReference;
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
                MenuPresenter presenter = menuPresenter;
                if (menuPresenter != null) {
                    flagged |= presenter.flagActionItems();
                } else {
                    boolean remove = this.mPresenters.remove(weakReference2);
                }
            }
            if (!flagged) {
                this.mActionItems.clear();
                this.mNonActionItems.clear();
                boolean addAll = this.mNonActionItems.addAll(getVisibleItems());
            } else {
                this.mActionItems.clear();
                this.mNonActionItems.clear();
                int itemsSize = visibleItems.size();
                for (int i = 0; i < itemsSize; i++) {
                    MenuItemImpl menuItemImpl = (MenuItemImpl) visibleItems.get(i);
                    MenuItemImpl item = menuItemImpl;
                    if (!menuItemImpl.isActionButton()) {
                        boolean add = this.mNonActionItems.add(item);
                    } else {
                        boolean add2 = this.mActionItems.add(item);
                    }
                }
            }
            this.mIsActionItemsStale = false;
        }
    }

    public ArrayList<MenuItemImpl> getActionItems() {
        flagActionItems();
        return this.mActionItems;
    }

    public ArrayList<MenuItemImpl> getNonActionItems() {
        flagActionItems();
        return this.mNonActionItems;
    }

    public void clearHeader() {
        this.mHeaderIcon = null;
        this.mHeaderTitle = null;
        this.mHeaderView = null;
        onItemsChanged(false);
    }

    private void setHeaderInternal(int i, CharSequence charSequence, int i2, Drawable drawable, View view) {
        int titleRes = i;
        CharSequence title = charSequence;
        int iconRes = i2;
        Drawable icon = drawable;
        View view2 = view;
        int i3 = titleRes;
        CharSequence charSequence2 = title;
        int i4 = iconRes;
        Drawable drawable2 = icon;
        View view3 = view2;
        Resources r = getResources();
        if (view2 == null) {
            if (titleRes > 0) {
                this.mHeaderTitle = r.getText(titleRes);
            } else if (title != null) {
                this.mHeaderTitle = title;
            }
            if (iconRes > 0) {
                this.mHeaderIcon = ContextCompat.getDrawable(getContext(), iconRes);
            } else if (icon != null) {
                this.mHeaderIcon = icon;
            }
            this.mHeaderView = null;
        } else {
            this.mHeaderView = view2;
            this.mHeaderTitle = null;
            this.mHeaderIcon = null;
        }
        onItemsChanged(false);
    }

    /* access modifiers changed from: protected */
    public MenuBuilder setHeaderTitleInt(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        setHeaderInternal(0, title, 0, null, null);
        return this;
    }

    /* access modifiers changed from: protected */
    public MenuBuilder setHeaderTitleInt(int i) {
        int titleRes = i;
        int i2 = titleRes;
        setHeaderInternal(titleRes, null, 0, null, null);
        return this;
    }

    /* access modifiers changed from: protected */
    public MenuBuilder setHeaderIconInt(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        setHeaderInternal(0, null, 0, icon, null);
        return this;
    }

    /* access modifiers changed from: protected */
    public MenuBuilder setHeaderIconInt(int i) {
        int iconRes = i;
        int i2 = iconRes;
        setHeaderInternal(0, null, iconRes, null, null);
        return this;
    }

    /* access modifiers changed from: protected */
    public MenuBuilder setHeaderViewInt(View view) {
        View view2 = view;
        View view3 = view2;
        setHeaderInternal(0, null, 0, null, view2);
        return this;
    }

    public CharSequence getHeaderTitle() {
        return this.mHeaderTitle;
    }

    public Drawable getHeaderIcon() {
        return this.mHeaderIcon;
    }

    public View getHeaderView() {
        return this.mHeaderView;
    }

    public MenuBuilder getRootMenu() {
        return this;
    }

    public void setCurrentMenuInfo(ContextMenuInfo contextMenuInfo) {
        ContextMenuInfo menuInfo = contextMenuInfo;
        ContextMenuInfo contextMenuInfo2 = menuInfo;
        this.mCurrentMenuInfo = menuInfo;
    }

    public void setOptionalIconsVisible(boolean z) {
        this.mOptionalIconsVisible = z;
    }

    /* access modifiers changed from: 0000 */
    public boolean getOptionalIconsVisible() {
        return this.mOptionalIconsVisible;
    }

    public boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        MenuItemImpl item = menuItemImpl;
        MenuItemImpl menuItemImpl2 = item;
        if (this.mPresenters.isEmpty()) {
            return false;
        }
        boolean expanded = false;
        stopDispatchingItemsChanged();
        Iterator it = this.mPresenters.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            WeakReference weakReference2 = weakReference;
            MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
            MenuPresenter presenter = menuPresenter;
            if (menuPresenter != null) {
                boolean expandItemActionView = presenter.expandItemActionView(this, item);
                expanded = expandItemActionView;
                if (expandItemActionView) {
                    break;
                }
            } else {
                boolean remove = this.mPresenters.remove(weakReference2);
            }
        }
        startDispatchingItemsChanged();
        if (expanded) {
            this.mExpandedItem = item;
        }
        return expanded;
    }

    public boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        MenuItemImpl item = menuItemImpl;
        MenuItemImpl menuItemImpl2 = item;
        if (this.mPresenters.isEmpty() || this.mExpandedItem != item) {
            return false;
        }
        boolean collapsed = false;
        stopDispatchingItemsChanged();
        Iterator it = this.mPresenters.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            WeakReference weakReference2 = weakReference;
            MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
            MenuPresenter presenter = menuPresenter;
            if (menuPresenter != null) {
                boolean collapseItemActionView = presenter.collapseItemActionView(this, item);
                collapsed = collapseItemActionView;
                if (collapseItemActionView) {
                    break;
                }
            } else {
                boolean remove = this.mPresenters.remove(weakReference2);
            }
        }
        startDispatchingItemsChanged();
        if (collapsed) {
            this.mExpandedItem = null;
        }
        return collapsed;
    }

    public MenuItemImpl getExpandedItem() {
        return this.mExpandedItem;
    }

    public void setOverrideVisibleItems(boolean z) {
        this.mOverrideVisibleItems = z;
    }
}
