package android.support.p003v7.view.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.internal.view.SupportMenuItem;
import android.support.p000v4.view.ActionProvider;
import android.support.p000v4.view.MenuItemCompat;
import android.support.p003v7.view.CollapsibleActionView;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import java.lang.reflect.Method;

@RequiresApi(14)
@RestrictTo({Scope.LIBRARY_GROUP})
@TargetApi(14)
/* renamed from: android.support.v7.view.menu.MenuItemWrapperICS */
public class MenuItemWrapperICS extends BaseMenuWrapper<SupportMenuItem> implements MenuItem {
    static final String LOG_TAG = "MenuItemWrapper";
    private Method mSetExclusiveCheckableMethod;

    /* renamed from: android.support.v7.view.menu.MenuItemWrapperICS$ActionProviderWrapper */
    class ActionProviderWrapper extends ActionProvider {
        final android.view.ActionProvider mInner;
        final /* synthetic */ MenuItemWrapperICS this$0;

        public ActionProviderWrapper(MenuItemWrapperICS menuItemWrapperICS, Context context, android.view.ActionProvider actionProvider) {
            MenuItemWrapperICS this$02 = menuItemWrapperICS;
            Context context2 = context;
            android.view.ActionProvider inner = actionProvider;
            MenuItemWrapperICS menuItemWrapperICS2 = this$02;
            Context context3 = context2;
            android.view.ActionProvider actionProvider2 = inner;
            this.this$0 = this$02;
            super(context2);
            this.mInner = inner;
        }

        public View onCreateActionView() {
            return this.mInner.onCreateActionView();
        }

        public boolean onPerformDefaultAction() {
            return this.mInner.onPerformDefaultAction();
        }

        public boolean hasSubMenu() {
            return this.mInner.hasSubMenu();
        }

        public void onPrepareSubMenu(SubMenu subMenu) {
            SubMenu subMenu2 = subMenu;
            SubMenu subMenu3 = subMenu2;
            this.mInner.onPrepareSubMenu(this.this$0.getSubMenuWrapper(subMenu2));
        }
    }

    /* renamed from: android.support.v7.view.menu.MenuItemWrapperICS$CollapsibleActionViewWrapper */
    static class CollapsibleActionViewWrapper extends FrameLayout implements CollapsibleActionView {
        final android.view.CollapsibleActionView mWrappedView;

        CollapsibleActionViewWrapper(View view) {
            View actionView = view;
            View view2 = actionView;
            super(actionView.getContext());
            this.mWrappedView = (android.view.CollapsibleActionView) actionView;
            addView(actionView);
        }

        public void onActionViewExpanded() {
            this.mWrappedView.onActionViewExpanded();
        }

        public void onActionViewCollapsed() {
            this.mWrappedView.onActionViewCollapsed();
        }

        /* access modifiers changed from: 0000 */
        public View getWrappedView() {
            return (View) this.mWrappedView;
        }
    }

    /* renamed from: android.support.v7.view.menu.MenuItemWrapperICS$OnActionExpandListenerWrapper */
    private class OnActionExpandListenerWrapper extends BaseWrapper<OnActionExpandListener> implements MenuItemCompat.OnActionExpandListener {
        final /* synthetic */ MenuItemWrapperICS this$0;

        OnActionExpandListenerWrapper(MenuItemWrapperICS menuItemWrapperICS, OnActionExpandListener onActionExpandListener) {
            OnActionExpandListener object = onActionExpandListener;
            OnActionExpandListener onActionExpandListener2 = object;
            this.this$0 = menuItemWrapperICS;
            super(object);
        }

        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            return ((OnActionExpandListener) this.mWrappedObject).onMenuItemActionExpand(this.this$0.getMenuItemWrapper(item));
        }

        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            return ((OnActionExpandListener) this.mWrappedObject).onMenuItemActionCollapse(this.this$0.getMenuItemWrapper(item));
        }
    }

    /* renamed from: android.support.v7.view.menu.MenuItemWrapperICS$OnMenuItemClickListenerWrapper */
    private class OnMenuItemClickListenerWrapper extends BaseWrapper<OnMenuItemClickListener> implements OnMenuItemClickListener {
        final /* synthetic */ MenuItemWrapperICS this$0;

        OnMenuItemClickListenerWrapper(MenuItemWrapperICS menuItemWrapperICS, OnMenuItemClickListener onMenuItemClickListener) {
            OnMenuItemClickListener object = onMenuItemClickListener;
            OnMenuItemClickListener onMenuItemClickListener2 = object;
            this.this$0 = menuItemWrapperICS;
            super(object);
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuItem menuItem2 = item;
            return ((OnMenuItemClickListener) this.mWrappedObject).onMenuItemClick(this.this$0.getMenuItemWrapper(item));
        }
    }

    MenuItemWrapperICS(Context context, SupportMenuItem supportMenuItem) {
        Context context2 = context;
        SupportMenuItem object = supportMenuItem;
        Context context3 = context2;
        SupportMenuItem supportMenuItem2 = object;
        super(context2, object);
    }

    public int getItemId() {
        return ((SupportMenuItem) this.mWrappedObject).getItemId();
    }

    public int getGroupId() {
        return ((SupportMenuItem) this.mWrappedObject).getGroupId();
    }

    public int getOrder() {
        return ((SupportMenuItem) this.mWrappedObject).getOrder();
    }

    public MenuItem setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        MenuItem title2 = ((SupportMenuItem) this.mWrappedObject).setTitle(title);
        return this;
    }

    public MenuItem setTitle(int i) {
        int title = i;
        int i2 = title;
        MenuItem title2 = ((SupportMenuItem) this.mWrappedObject).setTitle(title);
        return this;
    }

    public CharSequence getTitle() {
        return ((SupportMenuItem) this.mWrappedObject).getTitle();
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        MenuItem titleCondensed = ((SupportMenuItem) this.mWrappedObject).setTitleCondensed(title);
        return this;
    }

    public CharSequence getTitleCondensed() {
        return ((SupportMenuItem) this.mWrappedObject).getTitleCondensed();
    }

    public MenuItem setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        MenuItem icon2 = ((SupportMenuItem) this.mWrappedObject).setIcon(icon);
        return this;
    }

    public MenuItem setIcon(int i) {
        int iconRes = i;
        int i2 = iconRes;
        MenuItem icon = ((SupportMenuItem) this.mWrappedObject).setIcon(iconRes);
        return this;
    }

    public Drawable getIcon() {
        return ((SupportMenuItem) this.mWrappedObject).getIcon();
    }

    public MenuItem setIntent(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        MenuItem intent4 = ((SupportMenuItem) this.mWrappedObject).setIntent(intent2);
        return this;
    }

    public Intent getIntent() {
        return ((SupportMenuItem) this.mWrappedObject).getIntent();
    }

    public MenuItem setShortcut(char c, char c2) {
        MenuItem shortcut = ((SupportMenuItem) this.mWrappedObject).setShortcut(c, c2);
        return this;
    }

    public MenuItem setNumericShortcut(char c) {
        MenuItem numericShortcut = ((SupportMenuItem) this.mWrappedObject).setNumericShortcut(c);
        return this;
    }

    public char getNumericShortcut() {
        return ((SupportMenuItem) this.mWrappedObject).getNumericShortcut();
    }

    public MenuItem setAlphabeticShortcut(char c) {
        MenuItem alphabeticShortcut = ((SupportMenuItem) this.mWrappedObject).setAlphabeticShortcut(c);
        return this;
    }

    public char getAlphabeticShortcut() {
        return ((SupportMenuItem) this.mWrappedObject).getAlphabeticShortcut();
    }

    public MenuItem setCheckable(boolean z) {
        MenuItem checkable = ((SupportMenuItem) this.mWrappedObject).setCheckable(z);
        return this;
    }

    public boolean isCheckable() {
        return ((SupportMenuItem) this.mWrappedObject).isCheckable();
    }

    public MenuItem setChecked(boolean z) {
        MenuItem checked = ((SupportMenuItem) this.mWrappedObject).setChecked(z);
        return this;
    }

    public boolean isChecked() {
        return ((SupportMenuItem) this.mWrappedObject).isChecked();
    }

    public MenuItem setVisible(boolean z) {
        return ((SupportMenuItem) this.mWrappedObject).setVisible(z);
    }

    public boolean isVisible() {
        return ((SupportMenuItem) this.mWrappedObject).isVisible();
    }

    public MenuItem setEnabled(boolean z) {
        MenuItem enabled = ((SupportMenuItem) this.mWrappedObject).setEnabled(z);
        return this;
    }

    public boolean isEnabled() {
        return ((SupportMenuItem) this.mWrappedObject).isEnabled();
    }

    public boolean hasSubMenu() {
        return ((SupportMenuItem) this.mWrappedObject).hasSubMenu();
    }

    public SubMenu getSubMenu() {
        return getSubMenuWrapper(((SupportMenuItem) this.mWrappedObject).getSubMenu());
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        OnMenuItemClickListener menuItemClickListener = onMenuItemClickListener;
        OnMenuItemClickListener onMenuItemClickListener2 = menuItemClickListener;
        MenuItem onMenuItemClickListener3 = ((SupportMenuItem) this.mWrappedObject).setOnMenuItemClickListener(menuItemClickListener == null ? null : new OnMenuItemClickListenerWrapper(this, menuItemClickListener));
        return this;
    }

    public ContextMenuInfo getMenuInfo() {
        return ((SupportMenuItem) this.mWrappedObject).getMenuInfo();
    }

    public void setShowAsAction(int i) {
        int actionEnum = i;
        int i2 = actionEnum;
        ((SupportMenuItem) this.mWrappedObject).setShowAsAction(actionEnum);
    }

    public MenuItem setShowAsActionFlags(int i) {
        int actionEnum = i;
        int i2 = actionEnum;
        MenuItem showAsActionFlags = ((SupportMenuItem) this.mWrappedObject).setShowAsActionFlags(actionEnum);
        return this;
    }

    public MenuItem setActionView(View view) {
        View view2 = view;
        View view3 = view2;
        if (view2 instanceof android.view.CollapsibleActionView) {
            view3 = new CollapsibleActionViewWrapper(view2);
        }
        MenuItem actionView = ((SupportMenuItem) this.mWrappedObject).setActionView(view3);
        return this;
    }

    public MenuItem setActionView(int i) {
        int resId = i;
        int i2 = resId;
        MenuItem actionView = ((SupportMenuItem) this.mWrappedObject).setActionView(resId);
        View actionView2 = ((SupportMenuItem) this.mWrappedObject).getActionView();
        View actionView3 = actionView2;
        if (actionView2 instanceof android.view.CollapsibleActionView) {
            MenuItem actionView4 = ((SupportMenuItem) this.mWrappedObject).setActionView((View) new CollapsibleActionViewWrapper(actionView3));
        }
        return this;
    }

    public View getActionView() {
        View actionView = ((SupportMenuItem) this.mWrappedObject).getActionView();
        View actionView2 = actionView;
        if (!(actionView instanceof CollapsibleActionViewWrapper)) {
            return actionView2;
        }
        return ((CollapsibleActionViewWrapper) actionView2).getWrappedView();
    }

    public MenuItem setActionProvider(android.view.ActionProvider actionProvider) {
        android.view.ActionProvider provider = actionProvider;
        android.view.ActionProvider actionProvider2 = provider;
        SupportMenuItem supportActionProvider = ((SupportMenuItem) this.mWrappedObject).setSupportActionProvider(provider == null ? null : createActionProviderWrapper(provider));
        return this;
    }

    public android.view.ActionProvider getActionProvider() {
        ActionProvider supportActionProvider = ((SupportMenuItem) this.mWrappedObject).getSupportActionProvider();
        ActionProvider provider = supportActionProvider;
        if (!(supportActionProvider instanceof ActionProviderWrapper)) {
            return null;
        }
        return ((ActionProviderWrapper) provider).mInner;
    }

    public boolean expandActionView() {
        return ((SupportMenuItem) this.mWrappedObject).expandActionView();
    }

    public boolean collapseActionView() {
        return ((SupportMenuItem) this.mWrappedObject).collapseActionView();
    }

    public boolean isActionViewExpanded() {
        return ((SupportMenuItem) this.mWrappedObject).isActionViewExpanded();
    }

    public MenuItem setOnActionExpandListener(OnActionExpandListener onActionExpandListener) {
        OnActionExpandListener listener = onActionExpandListener;
        OnActionExpandListener onActionExpandListener2 = listener;
        SupportMenuItem supportOnActionExpandListener = ((SupportMenuItem) this.mWrappedObject).setSupportOnActionExpandListener(listener == null ? null : new OnActionExpandListenerWrapper(this, listener));
        return this;
    }

    public void setExclusiveCheckable(boolean z) {
        boolean checkable = z;
        try {
            if (this.mSetExclusiveCheckableMethod == null) {
                Class cls = ((SupportMenuItem) this.mWrappedObject).getClass();
                Class[] clsArr = new Class[1];
                clsArr[0] = Boolean.TYPE;
                this.mSetExclusiveCheckableMethod = cls.getDeclaredMethod("setExclusiveCheckable", clsArr);
            }
            Method method = this.mSetExclusiveCheckableMethod;
            Object obj = this.mWrappedObject;
            Object[] objArr = new Object[1];
            objArr[0] = Boolean.valueOf(checkable);
            Object invoke = method.invoke(obj, objArr);
        } catch (Exception e) {
            Exception exc = e;
            int w = Log.w(LOG_TAG, "Error while calling setExclusiveCheckable", e);
        }
    }

    /* access modifiers changed from: 0000 */
    public ActionProviderWrapper createActionProviderWrapper(android.view.ActionProvider actionProvider) {
        android.view.ActionProvider provider = actionProvider;
        android.view.ActionProvider actionProvider2 = provider;
        return new ActionProviderWrapper(this, this.mContext, provider);
    }
}
