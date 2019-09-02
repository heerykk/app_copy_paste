package android.support.p003v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.app.ActionBar.LayoutParams;
import android.support.p003v7.app.ActionBar.OnMenuVisibilityListener;
import android.support.p003v7.app.ActionBar.OnNavigationListener;
import android.support.p003v7.app.ActionBar.Tab;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.WindowCallbackWrapper;
import android.support.p003v7.view.menu.ListMenuPresenter;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuPresenter;
import android.support.p003v7.widget.DecorToolbar;
import android.support.p003v7.widget.Toolbar;
import android.support.p003v7.widget.Toolbar.OnMenuItemClickListener;
import android.support.p003v7.widget.ToolbarWidgetWrapper;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window.Callback;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;

/* renamed from: android.support.v7.app.ToolbarActionBar */
class ToolbarActionBar extends ActionBar {
    DecorToolbar mDecorToolbar;
    private boolean mLastMenuVisibility;
    private ListMenuPresenter mListMenuPresenter;
    private boolean mMenuCallbackSet;
    private final OnMenuItemClickListener mMenuClicker = new OnMenuItemClickListener(this) {
        final /* synthetic */ ToolbarActionBar this$0;

        {
            ToolbarActionBar this$02 = r5;
            ToolbarActionBar toolbarActionBar = this$02;
            this.this$0 = this$02;
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            return this.this$0.mWindowCallback.onMenuItemSelected(0, item);
        }
    };
    private final Runnable mMenuInvalidator = new Runnable(this) {
        final /* synthetic */ ToolbarActionBar this$0;

        {
            ToolbarActionBar this$02 = r5;
            ToolbarActionBar toolbarActionBar = this$02;
            this.this$0 = this$02;
        }

        public void run() {
            this.this$0.populateOptionsMenu();
        }
    };
    private ArrayList<OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList<>();
    boolean mToolbarMenuPrepared;
    Callback mWindowCallback;

    /* renamed from: android.support.v7.app.ToolbarActionBar$ActionMenuPresenterCallback */
    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        private boolean mClosingActionMenu;
        final /* synthetic */ ToolbarActionBar this$0;

        ActionMenuPresenterCallback(ToolbarActionBar toolbarActionBar) {
            this.this$0 = toolbarActionBar;
        }

        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            MenuBuilder subMenu = menuBuilder;
            MenuBuilder menuBuilder2 = subMenu;
            if (this.this$0.mWindowCallback == null) {
                return false;
            }
            boolean onMenuOpened = this.this$0.mWindowCallback.onMenuOpened(108, subMenu);
            return true;
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder menu = menuBuilder;
            MenuBuilder menuBuilder2 = menu;
            boolean z2 = z;
            if (!this.mClosingActionMenu) {
                this.mClosingActionMenu = true;
                this.this$0.mDecorToolbar.dismissPopupMenus();
                if (this.this$0.mWindowCallback != null) {
                    this.this$0.mWindowCallback.onPanelClosed(108, menu);
                }
                this.mClosingActionMenu = false;
            }
        }
    }

    /* renamed from: android.support.v7.app.ToolbarActionBar$MenuBuilderCallback */
    private final class MenuBuilderCallback implements MenuBuilder.Callback {
        final /* synthetic */ ToolbarActionBar this$0;

        MenuBuilderCallback(ToolbarActionBar toolbarActionBar) {
            this.this$0 = toolbarActionBar;
        }

        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            MenuBuilder menuBuilder2 = menuBuilder;
            MenuItem menuItem2 = menuItem;
            return false;
        }

        public void onMenuModeChange(MenuBuilder menuBuilder) {
            MenuBuilder menu = menuBuilder;
            MenuBuilder menuBuilder2 = menu;
            if (this.this$0.mWindowCallback != null) {
                if (this.this$0.mDecorToolbar.isOverflowMenuShowing()) {
                    this.this$0.mWindowCallback.onPanelClosed(108, menu);
                } else if (this.this$0.mWindowCallback.onPreparePanel(0, null, menu)) {
                    boolean onMenuOpened = this.this$0.mWindowCallback.onMenuOpened(108, menu);
                }
            }
        }
    }

    /* renamed from: android.support.v7.app.ToolbarActionBar$PanelMenuPresenterCallback */
    private final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        final /* synthetic */ ToolbarActionBar this$0;

        PanelMenuPresenterCallback(ToolbarActionBar toolbarActionBar) {
            this.this$0 = toolbarActionBar;
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder menu = menuBuilder;
            MenuBuilder menuBuilder2 = menu;
            boolean z2 = z;
            if (this.this$0.mWindowCallback != null) {
                this.this$0.mWindowCallback.onPanelClosed(0, menu);
            }
        }

        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            MenuBuilder subMenu = menuBuilder;
            MenuBuilder menuBuilder2 = subMenu;
            if (subMenu == null && this.this$0.mWindowCallback != null) {
                boolean onMenuOpened = this.this$0.mWindowCallback.onMenuOpened(0, subMenu);
            }
            return true;
        }
    }

    /* renamed from: android.support.v7.app.ToolbarActionBar$ToolbarCallbackWrapper */
    private class ToolbarCallbackWrapper extends WindowCallbackWrapper {
        final /* synthetic */ ToolbarActionBar this$0;

        public ToolbarCallbackWrapper(ToolbarActionBar toolbarActionBar, Callback callback) {
            Callback wrapped = callback;
            Callback callback2 = wrapped;
            this.this$0 = toolbarActionBar;
            super(wrapped);
        }

        public boolean onPreparePanel(int i, View view, Menu menu) {
            int featureId = i;
            View view2 = view;
            Menu menu2 = menu;
            int i2 = featureId;
            View view3 = view2;
            Menu menu3 = menu2;
            boolean onPreparePanel = super.onPreparePanel(featureId, view2, menu2);
            boolean result = onPreparePanel;
            if (onPreparePanel && !this.this$0.mToolbarMenuPrepared) {
                this.this$0.mDecorToolbar.setMenuPrepared();
                this.this$0.mToolbarMenuPrepared = true;
            }
            return result;
        }

        public View onCreatePanelView(int i) {
            int featureId = i;
            int i2 = featureId;
            switch (featureId) {
                case 0:
                    Menu menu = this.this$0.mDecorToolbar.getMenu();
                    if (onPreparePanel(featureId, null, menu) && onMenuOpened(featureId, menu)) {
                        return this.this$0.getListMenuView(menu);
                    }
            }
            return super.onCreatePanelView(featureId);
        }
    }

    public ToolbarActionBar(Toolbar toolbar, CharSequence charSequence, Callback callback) {
        Toolbar toolbar2 = toolbar;
        CharSequence title = charSequence;
        Callback callback2 = callback;
        Toolbar toolbar3 = toolbar2;
        CharSequence charSequence2 = title;
        Callback callback3 = callback2;
        this.mDecorToolbar = new ToolbarWidgetWrapper(toolbar2, false);
        this.mWindowCallback = new ToolbarCallbackWrapper(this, callback2);
        this.mDecorToolbar.setWindowCallback(this.mWindowCallback);
        toolbar2.setOnMenuItemClickListener(this.mMenuClicker);
        this.mDecorToolbar.setWindowTitle(title);
    }

    public Callback getWrappedWindowCallback() {
        return this.mWindowCallback;
    }

    public void setCustomView(View view) {
        View view2 = view;
        View view3 = view2;
        setCustomView(view2, new LayoutParams(-2, -2));
    }

    public void setCustomView(View view, LayoutParams layoutParams) {
        View view2 = view;
        LayoutParams layoutParams2 = layoutParams;
        View view3 = view2;
        LayoutParams layoutParams3 = layoutParams2;
        if (view2 != null) {
            view2.setLayoutParams(layoutParams2);
        }
        this.mDecorToolbar.setCustomView(view2);
    }

    public void setCustomView(int i) {
        int resId = i;
        int i2 = resId;
        setCustomView(LayoutInflater.from(this.mDecorToolbar.getContext()).inflate(resId, this.mDecorToolbar.getViewGroup(), false));
    }

    public void setIcon(int i) {
        int resId = i;
        int i2 = resId;
        this.mDecorToolbar.setIcon(resId);
    }

    public void setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        this.mDecorToolbar.setIcon(icon);
    }

    public void setLogo(int i) {
        int resId = i;
        int i2 = resId;
        this.mDecorToolbar.setLogo(resId);
    }

    public void setLogo(Drawable drawable) {
        Drawable logo = drawable;
        Drawable drawable2 = logo;
        this.mDecorToolbar.setLogo(logo);
    }

    public void setStackedBackgroundDrawable(Drawable drawable) {
        Drawable drawable2 = drawable;
    }

    public void setSplitBackgroundDrawable(Drawable drawable) {
        Drawable drawable2 = drawable;
    }

    public void setHomeButtonEnabled(boolean z) {
        boolean z2 = z;
    }

    public void setElevation(float f) {
        float elevation = f;
        float f2 = elevation;
        ViewCompat.setElevation(this.mDecorToolbar.getViewGroup(), elevation);
    }

    public float getElevation() {
        return ViewCompat.getElevation(this.mDecorToolbar.getViewGroup());
    }

    public Context getThemedContext() {
        return this.mDecorToolbar.getContext();
    }

    public boolean isTitleTruncated() {
        return super.isTitleTruncated();
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        Drawable indicator = drawable;
        Drawable drawable2 = indicator;
        this.mDecorToolbar.setNavigationIcon(indicator);
    }

    public void setHomeAsUpIndicator(int i) {
        int resId = i;
        int i2 = resId;
        this.mDecorToolbar.setNavigationIcon(resId);
    }

    public void setHomeActionContentDescription(CharSequence charSequence) {
        CharSequence description = charSequence;
        CharSequence charSequence2 = description;
        this.mDecorToolbar.setNavigationContentDescription(description);
    }

    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
        boolean z2 = z;
    }

    public void setHomeActionContentDescription(int i) {
        int resId = i;
        int i2 = resId;
        this.mDecorToolbar.setNavigationContentDescription(resId);
    }

    public void setShowHideAnimationEnabled(boolean z) {
        boolean z2 = z;
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration config = configuration;
        Configuration configuration2 = config;
        super.onConfigurationChanged(config);
    }

    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, OnNavigationListener onNavigationListener) {
        SpinnerAdapter adapter = spinnerAdapter;
        OnNavigationListener callback = onNavigationListener;
        SpinnerAdapter spinnerAdapter2 = adapter;
        OnNavigationListener onNavigationListener2 = callback;
        this.mDecorToolbar.setDropdownParams(adapter, new NavItemSelectedListener(callback));
    }

    public void setSelectedNavigationItem(int i) {
        int position = i;
        int i2 = position;
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                this.mDecorToolbar.setDropdownSelectedPosition(position);
                return;
            default:
                throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
    }

    public int getSelectedNavigationIndex() {
        return -1;
    }

    public int getNavigationItemCount() {
        return 0;
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mDecorToolbar.setTitle(title);
    }

    public void setTitle(int i) {
        int resId = i;
        int i2 = resId;
        this.mDecorToolbar.setTitle(resId == 0 ? null : this.mDecorToolbar.getContext().getText(resId));
    }

    public void setWindowTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mDecorToolbar.setWindowTitle(title);
    }

    public boolean requestFocus() {
        ViewGroup viewGroup = this.mDecorToolbar.getViewGroup();
        ViewGroup viewGroup2 = viewGroup;
        if (viewGroup == null || viewGroup2.hasFocus()) {
            return false;
        }
        boolean requestFocus = viewGroup2.requestFocus();
        return true;
    }

    public void setSubtitle(CharSequence charSequence) {
        CharSequence subtitle = charSequence;
        CharSequence charSequence2 = subtitle;
        this.mDecorToolbar.setSubtitle(subtitle);
    }

    public void setSubtitle(int i) {
        int resId = i;
        int i2 = resId;
        this.mDecorToolbar.setSubtitle(resId == 0 ? null : this.mDecorToolbar.getContext().getText(resId));
    }

    public void setDisplayOptions(int i) {
        int options = i;
        int i2 = options;
        setDisplayOptions(options, -1);
    }

    public void setDisplayOptions(int i, int i2) {
        int options = i;
        int mask = i2;
        int i3 = options;
        int i4 = mask;
        int displayOptions = this.mDecorToolbar.getDisplayOptions();
        int i5 = displayOptions;
        this.mDecorToolbar.setDisplayOptions((options & mask) | (displayOptions & (mask ^ -1)));
    }

    public void setDisplayUseLogoEnabled(boolean z) {
        setDisplayOptions(!z ? 0 : 1, 1);
    }

    public void setDisplayShowHomeEnabled(boolean z) {
        setDisplayOptions(!z ? 0 : 2, 2);
    }

    public void setDisplayHomeAsUpEnabled(boolean z) {
        setDisplayOptions(!z ? 0 : 4, 4);
    }

    public void setDisplayShowTitleEnabled(boolean z) {
        setDisplayOptions(!z ? 0 : 8, 8);
    }

    public void setDisplayShowCustomEnabled(boolean z) {
        setDisplayOptions(!z ? 0 : 16, 16);
    }

    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        this.mDecorToolbar.setBackgroundDrawable(d);
    }

    public View getCustomView() {
        return this.mDecorToolbar.getCustomView();
    }

    public CharSequence getTitle() {
        return this.mDecorToolbar.getTitle();
    }

    public CharSequence getSubtitle() {
        return this.mDecorToolbar.getSubtitle();
    }

    public int getNavigationMode() {
        return 0;
    }

    public void setNavigationMode(int i) {
        int mode = i;
        int i2 = mode;
        if (mode != 2) {
            this.mDecorToolbar.setNavigationMode(mode);
            return;
        }
        throw new IllegalArgumentException("Tabs not supported in this configuration");
    }

    public int getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions();
    }

    public Tab newTab() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab) {
        Tab tab2 = tab;
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab, boolean z) {
        Tab tab2 = tab;
        boolean z2 = z;
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab, int i) {
        Tab tab2 = tab;
        int i2 = i;
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab, int i, boolean z) {
        Tab tab2 = tab;
        int i2 = i;
        boolean z2 = z;
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void removeTab(Tab tab) {
        Tab tab2 = tab;
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void removeTabAt(int i) {
        int i2 = i;
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void removeAllTabs() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void selectTab(Tab tab) {
        Tab tab2 = tab;
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public Tab getSelectedTab() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public Tab getTabAt(int i) {
        int i2 = i;
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public int getTabCount() {
        return 0;
    }

    public int getHeight() {
        return this.mDecorToolbar.getHeight();
    }

    public void show() {
        this.mDecorToolbar.setVisibility(0);
    }

    public void hide() {
        this.mDecorToolbar.setVisibility(8);
    }

    public boolean isShowing() {
        return this.mDecorToolbar.getVisibility() == 0;
    }

    public boolean openOptionsMenu() {
        return this.mDecorToolbar.showOverflowMenu();
    }

    public boolean invalidateOptionsMenu() {
        boolean removeCallbacks = this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
        ViewCompat.postOnAnimation(this.mDecorToolbar.getViewGroup(), this.mMenuInvalidator);
        return true;
    }

    public boolean collapseActionView() {
        if (!this.mDecorToolbar.hasExpandedActionView()) {
            return false;
        }
        this.mDecorToolbar.collapseActionView();
        return true;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0044, code lost:
        if (r1.mWindowCallback.onPreparePanel(0, null, r4) != false) goto L_0x0026;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void populateOptionsMenu() {
        /*
            r19 = this;
            r1 = r19
            r2 = r1
            android.view.Menu r3 = r1.getMenu()
            r4 = r3
            boolean r5 = r3 instanceof android.support.p003v7.view.menu.MenuBuilder
            r6 = r5
            r7 = r6
            r8 = 0
            if (r7 != r8) goto L_0x002a
            r3 = 0
        L_0x0010:
            r9 = r3
            r10 = 0
            if (r9 != r10) goto L_0x002f
        L_0x0014:
            r4.clear()     // Catch:{ all -> 0x004b }
            android.view.Window$Callback r3 = r1.mWindowCallback     // Catch:{ all -> 0x004b }
            r11 = 0
            r12 = r4
            boolean r13 = r3.onCreatePanelMenu(r11, r12)     // Catch:{ all -> 0x004b }
            r7 = r13
            r8 = 0
            if (r7 != r8) goto L_0x0033
        L_0x0023:
            r4.clear()     // Catch:{ all -> 0x004b }
        L_0x0026:
            r10 = 0
            if (r9 != r10) goto L_0x0047
        L_0x0029:
            return
        L_0x002a:
            r0 = r4
            android.support.v7.view.menu.MenuBuilder r0 = (android.support.p003v7.view.menu.MenuBuilder) r0
            r3 = r0
            goto L_0x0010
        L_0x002f:
            r9.stopDispatchingItemsChanged()
            goto L_0x0014
        L_0x0033:
            android.view.Window$Callback r3 = r1.mWindowCallback     // Catch:{ all -> 0x004b }
            r11 = 0
            r14 = 0
            r10 = 0
            r15 = r10
            r16 = r4
            r0 = r16
            boolean r17 = r3.onPreparePanel(r11, r15, r0)     // Catch:{ all -> 0x004b }
            r7 = r17
            r8 = 0
            if (r7 == r8) goto L_0x0023
            goto L_0x0026
        L_0x0047:
            r9.startDispatchingItemsChanged()
            goto L_0x0029
        L_0x004b:
            r18 = move-exception
            r10 = 0
            if (r9 != r10) goto L_0x0050
        L_0x004f:
            throw r18
        L_0x0050:
            r9.startDispatchingItemsChanged()
            goto L_0x004f
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.app.ToolbarActionBar.populateOptionsMenu():void");
    }

    public boolean onMenuKeyEvent(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        if (event.getAction() == 1) {
            boolean openOptionsMenu = openOptionsMenu();
        }
        return true;
    }

    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent ev = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = ev;
        Menu menu = getMenu();
        Menu menu2 = menu;
        if (menu != null) {
            menu2.setQwertyMode(KeyCharacterMap.load(ev == null ? -1 : ev.getDeviceId()).getKeyboardType() != 1);
            boolean performShortcut = menu2.performShortcut(keyCode, ev, 0);
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void onDestroy() {
        boolean removeCallbacks = this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
    }

    public void addOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener) {
        OnMenuVisibilityListener listener = onMenuVisibilityListener;
        OnMenuVisibilityListener onMenuVisibilityListener2 = listener;
        boolean add = this.mMenuVisibilityListeners.add(listener);
    }

    public void removeOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener) {
        OnMenuVisibilityListener listener = onMenuVisibilityListener;
        OnMenuVisibilityListener onMenuVisibilityListener2 = listener;
        boolean remove = this.mMenuVisibilityListeners.remove(listener);
    }

    public void dispatchMenuVisibilityChanged(boolean z) {
        boolean isVisible = z;
        if (isVisible != this.mLastMenuVisibility) {
            this.mLastMenuVisibility = isVisible;
            int count = this.mMenuVisibilityListeners.size();
            for (int i = 0; i < count; i++) {
                ((OnMenuVisibilityListener) this.mMenuVisibilityListeners.get(i)).onMenuVisibilityChanged(isVisible);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public View getListMenuView(Menu menu) {
        Menu menu2 = menu;
        Menu menu3 = menu2;
        ensureListMenuPresenter(menu2);
        if (menu2 == null || this.mListMenuPresenter == null) {
            return null;
        }
        if (this.mListMenuPresenter.getAdapter().getCount() <= 0) {
            return null;
        }
        return (View) this.mListMenuPresenter.getMenuView(this.mDecorToolbar.getViewGroup());
    }

    private void ensureListMenuPresenter(Menu menu) {
        Menu menu2 = menu;
        Menu menu3 = menu2;
        if (this.mListMenuPresenter == null && (menu2 instanceof MenuBuilder)) {
            MenuBuilder mb = (MenuBuilder) menu2;
            Context context = this.mDecorToolbar.getContext();
            TypedValue outValue = new TypedValue();
            Theme newTheme = context.getResources().newTheme();
            Theme widgetTheme = newTheme;
            newTheme.setTo(context.getTheme());
            boolean resolveAttribute = widgetTheme.resolveAttribute(C0268R.attr.actionBarPopupTheme, outValue, true);
            if (outValue.resourceId != 0) {
                widgetTheme.applyStyle(outValue.resourceId, true);
            }
            boolean resolveAttribute2 = widgetTheme.resolveAttribute(C0268R.attr.panelMenuListTheme, outValue, true);
            if (outValue.resourceId == 0) {
                widgetTheme.applyStyle(C0268R.style.Theme_AppCompat_CompactMenu, true);
            } else {
                widgetTheme.applyStyle(outValue.resourceId, true);
            }
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 0);
            ContextThemeWrapper contextThemeWrapper2 = contextThemeWrapper;
            contextThemeWrapper.getTheme().setTo(widgetTheme);
            ListMenuPresenter listMenuPresenter = new ListMenuPresenter((Context) contextThemeWrapper2, C0268R.layout.abc_list_menu_item_layout);
            this.mListMenuPresenter = listMenuPresenter;
            ListMenuPresenter listMenuPresenter2 = this.mListMenuPresenter;
            PanelMenuPresenterCallback panelMenuPresenterCallback = new PanelMenuPresenterCallback(this);
            listMenuPresenter2.setCallback(panelMenuPresenterCallback);
            mb.addMenuPresenter(this.mListMenuPresenter);
        }
    }

    private Menu getMenu() {
        if (!this.mMenuCallbackSet) {
            this.mDecorToolbar.setMenuCallbacks(new ActionMenuPresenterCallback(this), new MenuBuilderCallback(this));
            this.mMenuCallbackSet = true;
        }
        return this.mDecorToolbar.getMenu();
    }
}
