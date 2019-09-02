package android.support.p003v7.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.internal.view.SupportMenuItem;
import android.support.p000v4.view.ActionProvider;
import android.support.p000v4.view.ActionProvider.VisibilityListener;
import android.support.p000v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.view.menu.MenuView.ItemView;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.LinearLayout;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.MenuItemImpl */
public final class MenuItemImpl implements SupportMenuItem {
    private static final int CHECKABLE = 1;
    private static final int CHECKED = 2;
    private static final int ENABLED = 16;
    private static final int EXCLUSIVE = 4;
    private static final int HIDDEN = 8;
    private static final int IS_ACTION = 32;
    static final int NO_ICON = 0;
    private static final int SHOW_AS_ACTION_MASK = 3;
    private static final String TAG = "MenuItemImpl";
    private static String sDeleteShortcutLabel;
    private static String sEnterShortcutLabel;
    private static String sPrependShortcutLabel;
    private static String sSpaceShortcutLabel;
    private ActionProvider mActionProvider;
    private View mActionView;
    private final int mCategoryOrder;
    private OnMenuItemClickListener mClickListener;
    private int mFlags = 16;
    private final int mGroup;
    private Drawable mIconDrawable;
    private int mIconResId = 0;
    private final int mId;
    private Intent mIntent;
    private boolean mIsActionViewExpanded = false;
    private Runnable mItemCallback;
    MenuBuilder mMenu;
    private ContextMenuInfo mMenuInfo;
    private OnActionExpandListener mOnActionExpandListener;
    private final int mOrdering;
    private char mShortcutAlphabeticChar;
    private char mShortcutNumericChar;
    private int mShowAsAction = 0;
    private SubMenuBuilder mSubMenu;
    private CharSequence mTitle;
    private CharSequence mTitleCondensed;

    MenuItemImpl(MenuBuilder menuBuilder, int i, int i2, int i3, int i4, CharSequence charSequence, int i5) {
        MenuBuilder menu = menuBuilder;
        int group = i;
        int id = i2;
        int categoryOrder = i3;
        int ordering = i4;
        CharSequence title = charSequence;
        int showAsAction = i5;
        MenuBuilder menuBuilder2 = menu;
        int i6 = group;
        int i7 = id;
        int i8 = categoryOrder;
        int i9 = ordering;
        CharSequence charSequence2 = title;
        int i10 = showAsAction;
        this.mMenu = menu;
        this.mId = id;
        this.mGroup = group;
        this.mCategoryOrder = categoryOrder;
        this.mOrdering = ordering;
        this.mTitle = title;
        this.mShowAsAction = showAsAction;
    }

    public boolean invoke() {
        if (this.mClickListener != null && this.mClickListener.onMenuItemClick(this)) {
            return true;
        }
        if (this.mMenu.dispatchMenuItemSelected(this.mMenu.getRootMenu(), this)) {
            return true;
        }
        if (this.mItemCallback == null) {
            if (this.mIntent != null) {
                try {
                    this.mMenu.getContext().startActivity(this.mIntent);
                    return true;
                } catch (ActivityNotFoundException e) {
                    ActivityNotFoundException activityNotFoundException = e;
                    int e2 = Log.e(TAG, "Can't find activity to handle intent; ignoring", e);
                }
            }
            if (this.mActionProvider != null && this.mActionProvider.onPerformDefaultAction()) {
                return true;
            }
            return false;
        }
        this.mItemCallback.run();
        return true;
    }

    public boolean isEnabled() {
        return (this.mFlags & 16) != 0;
    }

    public MenuItem setEnabled(boolean z) {
        if (!z) {
            this.mFlags &= -17;
        } else {
            this.mFlags |= 16;
        }
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public int getGroupId() {
        return this.mGroup;
    }

    @CapturedViewProperty
    public int getItemId() {
        return this.mId;
    }

    public int getOrder() {
        return this.mCategoryOrder;
    }

    public int getOrdering() {
        return this.mOrdering;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public MenuItem setIntent(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        this.mIntent = intent2;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Runnable getCallback() {
        return this.mItemCallback;
    }

    public MenuItem setCallback(Runnable runnable) {
        Runnable callback = runnable;
        Runnable runnable2 = callback;
        this.mItemCallback = callback;
        return this;
    }

    public char getAlphabeticShortcut() {
        return this.mShortcutAlphabeticChar;
    }

    public MenuItem setAlphabeticShortcut(char c) {
        char alphaChar = c;
        if (this.mShortcutAlphabeticChar == alphaChar) {
            return this;
        }
        this.mShortcutAlphabeticChar = (char) Character.toLowerCase(alphaChar);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public char getNumericShortcut() {
        return this.mShortcutNumericChar;
    }

    public MenuItem setNumericShortcut(char c) {
        char numericChar = c;
        if (this.mShortcutNumericChar == numericChar) {
            return this;
        }
        this.mShortcutNumericChar = (char) numericChar;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setShortcut(char c, char c2) {
        char alphaChar = c2;
        this.mShortcutNumericChar = (char) c;
        this.mShortcutAlphabeticChar = (char) Character.toLowerCase(alphaChar);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public char getShortcut() {
        return !this.mMenu.isQwertyMode() ? this.mShortcutNumericChar : this.mShortcutAlphabeticChar;
    }

    /* access modifiers changed from: 0000 */
    public String getShortcutLabel() {
        char shortcut = getShortcut();
        char shortcut2 = shortcut;
        if (shortcut == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(sPrependShortcutLabel);
        StringBuilder sb2 = sb;
        switch (shortcut2) {
            case 8:
                StringBuilder append = sb.append(sDeleteShortcutLabel);
                break;
            case 10:
                StringBuilder append2 = sb.append(sEnterShortcutLabel);
                break;
            case ' ':
                StringBuilder append3 = sb.append(sSpaceShortcutLabel);
                break;
            default:
                StringBuilder append4 = sb.append(shortcut2);
                break;
        }
        return sb2.toString();
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldShowShortcut() {
        return this.mMenu.isShortcutsVisible() && getShortcut() != 0;
    }

    public SubMenu getSubMenu() {
        return this.mSubMenu;
    }

    public boolean hasSubMenu() {
        return this.mSubMenu != null;
    }

    public void setSubMenu(SubMenuBuilder subMenuBuilder) {
        SubMenuBuilder subMenu = subMenuBuilder;
        SubMenuBuilder subMenuBuilder2 = subMenu;
        this.mSubMenu = subMenu;
        SubMenu headerTitle = subMenu.setHeaderTitle(getTitle());
    }

    @CapturedViewProperty
    public CharSequence getTitle() {
        return this.mTitle;
    }

    /* access modifiers changed from: 0000 */
    public CharSequence getTitleForItemView(ItemView itemView) {
        CharSequence titleCondensed;
        ItemView itemView2 = itemView;
        ItemView itemView3 = itemView2;
        if (itemView2 != null && itemView2.prefersCondensedTitle()) {
            titleCondensed = getTitleCondensed();
        } else {
            titleCondensed = getTitle();
        }
        return titleCondensed;
    }

    public MenuItem setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mTitle = title;
        this.mMenu.onItemsChanged(false);
        if (this.mSubMenu != null) {
            SubMenu headerTitle = this.mSubMenu.setHeaderTitle(title);
        }
        return this;
    }

    public MenuItem setTitle(int i) {
        int title = i;
        int i2 = title;
        return setTitle((CharSequence) this.mMenu.getContext().getString(title));
    }

    public CharSequence getTitleCondensed() {
        CharSequence ctitle = this.mTitleCondensed == null ? this.mTitle : this.mTitleCondensed;
        if (VERSION.SDK_INT < 18 && ctitle != null && !(ctitle instanceof String)) {
            return ctitle.toString();
        }
        return ctitle;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mTitleCondensed = title;
        if (title == null) {
            CharSequence title2 = this.mTitle;
        }
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public Drawable getIcon() {
        if (this.mIconDrawable != null) {
            return this.mIconDrawable;
        }
        if (this.mIconResId == 0) {
            return null;
        }
        Drawable icon = AppCompatResources.getDrawable(this.mMenu.getContext(), this.mIconResId);
        this.mIconResId = 0;
        this.mIconDrawable = icon;
        return icon;
    }

    public MenuItem setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        this.mIconResId = 0;
        this.mIconDrawable = icon;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setIcon(int i) {
        int iconResId = i;
        int i2 = iconResId;
        this.mIconDrawable = null;
        this.mIconResId = iconResId;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public boolean isCheckable() {
        return (this.mFlags & 1) == 1;
    }

    public MenuItem setCheckable(boolean z) {
        boolean checkable = z;
        int i = this.mFlags;
        int i2 = i;
        this.mFlags = (this.mFlags & -2) | (!checkable ? 0 : 1);
        if (i != this.mFlags) {
            this.mMenu.onItemsChanged(false);
        }
        return this;
    }

    public void setExclusiveCheckable(boolean z) {
        this.mFlags = (this.mFlags & -5) | (!z ? 0 : 4);
    }

    public boolean isExclusiveCheckable() {
        return (this.mFlags & 4) != 0;
    }

    public boolean isChecked() {
        return (this.mFlags & 2) == 2;
    }

    public MenuItem setChecked(boolean z) {
        boolean checked = z;
        if ((this.mFlags & 4) == 0) {
            setCheckedInt(checked);
        } else {
            this.mMenu.setExclusiveItemChecked(this);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void setCheckedInt(boolean z) {
        boolean checked = z;
        int i = this.mFlags;
        int i2 = i;
        this.mFlags = (this.mFlags & -3) | (!checked ? 0 : 2);
        if (i != this.mFlags) {
            this.mMenu.onItemsChanged(false);
        }
    }

    public boolean isVisible() {
        if (this.mActionProvider != null && this.mActionProvider.overridesItemVisibility()) {
            return (this.mFlags & 8) == 0 && this.mActionProvider.isVisible();
        }
        return (this.mFlags & 8) == 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean setVisibleInt(boolean z) {
        boolean shown = z;
        int i = this.mFlags;
        int i2 = i;
        this.mFlags = (this.mFlags & -9) | (!shown ? 8 : 0);
        return i != this.mFlags;
    }

    public MenuItem setVisible(boolean z) {
        if (setVisibleInt(z)) {
            this.mMenu.onItemVisibleChanged(this);
        }
        return this;
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        OnMenuItemClickListener clickListener = onMenuItemClickListener;
        OnMenuItemClickListener onMenuItemClickListener2 = clickListener;
        this.mClickListener = clickListener;
        return this;
    }

    public String toString() {
        return this.mTitle == null ? null : this.mTitle.toString();
    }

    /* access modifiers changed from: 0000 */
    public void setMenuInfo(ContextMenuInfo contextMenuInfo) {
        ContextMenuInfo menuInfo = contextMenuInfo;
        ContextMenuInfo contextMenuInfo2 = menuInfo;
        this.mMenuInfo = menuInfo;
    }

    public ContextMenuInfo getMenuInfo() {
        return this.mMenuInfo;
    }

    public void actionFormatChanged() {
        this.mMenu.onItemActionRequestChanged(this);
    }

    public boolean shouldShowIcon() {
        return this.mMenu.getOptionalIconsVisible();
    }

    public boolean isActionButton() {
        return (this.mFlags & 32) == 32;
    }

    public boolean requestsActionButton() {
        return (this.mShowAsAction & 1) == 1;
    }

    public boolean requiresActionButton() {
        return (this.mShowAsAction & 2) == 2;
    }

    public void setIsActionButton(boolean z) {
        if (!z) {
            this.mFlags &= -33;
        } else {
            this.mFlags |= 32;
        }
    }

    public boolean showsTextAsAction() {
        return (this.mShowAsAction & 4) == 4;
    }

    public void setShowAsAction(int i) {
        int actionEnum = i;
        int i2 = actionEnum;
        switch (actionEnum & 3) {
            case 0:
            case 1:
            case 2:
                this.mShowAsAction = actionEnum;
                this.mMenu.onItemActionRequestChanged(this);
                return;
            default:
                throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
        }
    }

    public SupportMenuItem setActionView(View view) {
        View view2 = view;
        View view3 = view2;
        this.mActionView = view2;
        this.mActionProvider = null;
        if (view2 != null && view2.getId() == -1 && this.mId > 0) {
            view2.setId(this.mId);
        }
        this.mMenu.onItemActionRequestChanged(this);
        return this;
    }

    public SupportMenuItem setActionView(int i) {
        int resId = i;
        int i2 = resId;
        Context context = this.mMenu.getContext();
        SupportMenuItem actionView = setActionView(LayoutInflater.from(context).inflate(resId, new LinearLayout(context), false));
        return this;
    }

    public View getActionView() {
        if (this.mActionView != null) {
            return this.mActionView;
        }
        if (this.mActionProvider == null) {
            return null;
        }
        this.mActionView = this.mActionProvider.onCreateActionView(this);
        return this.mActionView;
    }

    public MenuItem setActionProvider(android.view.ActionProvider actionProvider) {
        android.view.ActionProvider actionProvider2 = actionProvider;
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    public android.view.ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    public ActionProvider getSupportActionProvider() {
        return this.mActionProvider;
    }

    public SupportMenuItem setSupportActionProvider(ActionProvider actionProvider) {
        ActionProvider actionProvider2 = actionProvider;
        ActionProvider actionProvider3 = actionProvider2;
        if (this.mActionProvider != null) {
            this.mActionProvider.reset();
        }
        this.mActionView = null;
        this.mActionProvider = actionProvider2;
        this.mMenu.onItemsChanged(true);
        if (this.mActionProvider != null) {
            this.mActionProvider.setVisibilityListener(new VisibilityListener(this) {
                final /* synthetic */ MenuItemImpl this$0;

                {
                    MenuItemImpl this$02 = r5;
                    MenuItemImpl menuItemImpl = this$02;
                    this.this$0 = this$02;
                }

                public void onActionProviderVisibilityChanged(boolean z) {
                    boolean z2 = z;
                    this.this$0.mMenu.onItemVisibleChanged(this.this$0);
                }
            });
        }
        return this;
    }

    public SupportMenuItem setShowAsActionFlags(int i) {
        int actionEnum = i;
        int i2 = actionEnum;
        setShowAsAction(actionEnum);
        return this;
    }

    public boolean expandActionView() {
        if (!hasCollapsibleActionView()) {
            return false;
        }
        if (this.mOnActionExpandListener != null && !this.mOnActionExpandListener.onMenuItemActionExpand(this)) {
            return false;
        }
        return this.mMenu.expandItemActionView(this);
    }

    public boolean collapseActionView() {
        if ((this.mShowAsAction & 8) == 0) {
            return false;
        }
        if (this.mActionView == null) {
            return true;
        }
        if (this.mOnActionExpandListener != null && !this.mOnActionExpandListener.onMenuItemActionCollapse(this)) {
            return false;
        }
        return this.mMenu.collapseItemActionView(this);
    }

    public SupportMenuItem setSupportOnActionExpandListener(OnActionExpandListener onActionExpandListener) {
        OnActionExpandListener listener = onActionExpandListener;
        OnActionExpandListener onActionExpandListener2 = listener;
        this.mOnActionExpandListener = listener;
        return this;
    }

    public boolean hasCollapsibleActionView() {
        if ((this.mShowAsAction & 8) == 0) {
            return false;
        }
        if (this.mActionView == null && this.mActionProvider != null) {
            this.mActionView = this.mActionProvider.onCreateActionView(this);
        }
        return this.mActionView != null;
    }

    public void setActionViewExpanded(boolean z) {
        this.mIsActionViewExpanded = z;
        this.mMenu.onItemsChanged(false);
    }

    public boolean isActionViewExpanded() {
        return this.mIsActionViewExpanded;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        MenuItem.OnActionExpandListener onActionExpandListener2 = onActionExpandListener;
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setOnActionExpandListener()");
    }
}
