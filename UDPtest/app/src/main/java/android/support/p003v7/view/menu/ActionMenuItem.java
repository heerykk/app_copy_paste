package android.support.p003v7.view.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.internal.view.SupportMenuItem;
import android.support.p000v4.view.MenuItemCompat;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.ActionMenuItem */
public class ActionMenuItem implements SupportMenuItem {
    private static final int CHECKABLE = 1;
    private static final int CHECKED = 2;
    private static final int ENABLED = 16;
    private static final int EXCLUSIVE = 4;
    private static final int HIDDEN = 8;
    private static final int NO_ICON = 0;
    private final int mCategoryOrder;
    private OnMenuItemClickListener mClickListener;
    private Context mContext;
    private int mFlags = 16;
    private final int mGroup;
    private Drawable mIconDrawable;
    private int mIconResId = 0;
    private final int mId;
    private Intent mIntent;
    private final int mOrdering;
    private char mShortcutAlphabeticChar;
    private char mShortcutNumericChar;
    private CharSequence mTitle;
    private CharSequence mTitleCondensed;

    public ActionMenuItem(Context context, int i, int i2, int i3, int i4, CharSequence charSequence) {
        Context context2 = context;
        int group = i;
        int id = i2;
        int categoryOrder = i3;
        int ordering = i4;
        CharSequence title = charSequence;
        Context context3 = context2;
        int i5 = group;
        int i6 = id;
        int i7 = categoryOrder;
        int i8 = ordering;
        CharSequence charSequence2 = title;
        this.mContext = context2;
        this.mId = id;
        this.mGroup = group;
        this.mCategoryOrder = categoryOrder;
        this.mOrdering = ordering;
        this.mTitle = title;
    }

    public char getAlphabeticShortcut() {
        return this.mShortcutAlphabeticChar;
    }

    public int getGroupId() {
        return this.mGroup;
    }

    public Drawable getIcon() {
        return this.mIconDrawable;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public int getItemId() {
        return this.mId;
    }

    public ContextMenuInfo getMenuInfo() {
        return null;
    }

    public char getNumericShortcut() {
        return this.mShortcutNumericChar;
    }

    public int getOrder() {
        return this.mOrdering;
    }

    public SubMenu getSubMenu() {
        return null;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getTitleCondensed() {
        return this.mTitleCondensed == null ? this.mTitle : this.mTitleCondensed;
    }

    public boolean hasSubMenu() {
        return false;
    }

    public boolean isCheckable() {
        return (this.mFlags & 1) != 0;
    }

    public boolean isChecked() {
        return (this.mFlags & 2) != 0;
    }

    public boolean isEnabled() {
        return (this.mFlags & 16) != 0;
    }

    public boolean isVisible() {
        return (this.mFlags & 8) == 0;
    }

    public MenuItem setAlphabeticShortcut(char c) {
        this.mShortcutAlphabeticChar = (char) c;
        return this;
    }

    public MenuItem setCheckable(boolean z) {
        this.mFlags = (this.mFlags & -2) | (!z ? 0 : 1);
        return this;
    }

    public ActionMenuItem setExclusiveCheckable(boolean z) {
        this.mFlags = (this.mFlags & -5) | (!z ? 0 : 4);
        return this;
    }

    public MenuItem setChecked(boolean z) {
        this.mFlags = (this.mFlags & -3) | (!z ? 0 : 2);
        return this;
    }

    public MenuItem setEnabled(boolean z) {
        this.mFlags = (this.mFlags & -17) | (!z ? 0 : 16);
        return this;
    }

    public MenuItem setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        this.mIconDrawable = icon;
        this.mIconResId = 0;
        return this;
    }

    public MenuItem setIcon(int i) {
        int iconRes = i;
        int i2 = iconRes;
        this.mIconResId = iconRes;
        this.mIconDrawable = ContextCompat.getDrawable(this.mContext, iconRes);
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        this.mIntent = intent2;
        return this;
    }

    public MenuItem setNumericShortcut(char c) {
        this.mShortcutNumericChar = (char) c;
        return this;
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        OnMenuItemClickListener menuItemClickListener = onMenuItemClickListener;
        OnMenuItemClickListener onMenuItemClickListener2 = menuItemClickListener;
        this.mClickListener = menuItemClickListener;
        return this;
    }

    public MenuItem setShortcut(char c, char c2) {
        char alphaChar = c2;
        this.mShortcutNumericChar = (char) c;
        this.mShortcutAlphabeticChar = (char) alphaChar;
        return this;
    }

    public MenuItem setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mTitle = title;
        return this;
    }

    public MenuItem setTitle(int i) {
        int title = i;
        int i2 = title;
        this.mTitle = this.mContext.getResources().getString(title);
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mTitleCondensed = title;
        return this;
    }

    public MenuItem setVisible(boolean z) {
        this.mFlags = (this.mFlags & 8) | (!z ? 8 : 0);
        return this;
    }

    public boolean invoke() {
        if (this.mClickListener != null && this.mClickListener.onMenuItemClick(this)) {
            return true;
        }
        if (this.mIntent == null) {
            return false;
        }
        this.mContext.startActivity(this.mIntent);
        return true;
    }

    public void setShowAsAction(int i) {
        int i2 = i;
    }

    public SupportMenuItem setActionView(View view) {
        View view2 = view;
        throw new UnsupportedOperationException();
    }

    public View getActionView() {
        return null;
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        ActionProvider actionProvider2 = actionProvider;
        throw new UnsupportedOperationException();
    }

    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException();
    }

    public SupportMenuItem setActionView(int i) {
        int i2 = i;
        throw new UnsupportedOperationException();
    }

    public android.support.p000v4.view.ActionProvider getSupportActionProvider() {
        return null;
    }

    public SupportMenuItem setSupportActionProvider(android.support.p000v4.view.ActionProvider actionProvider) {
        android.support.p000v4.view.ActionProvider actionProvider2 = actionProvider;
        throw new UnsupportedOperationException();
    }

    public SupportMenuItem setShowAsActionFlags(int i) {
        int actionEnum = i;
        int i2 = actionEnum;
        setShowAsAction(actionEnum);
        return this;
    }

    public boolean expandActionView() {
        return false;
    }

    public boolean collapseActionView() {
        return false;
    }

    public boolean isActionViewExpanded() {
        return false;
    }

    public MenuItem setOnActionExpandListener(OnActionExpandListener onActionExpandListener) {
        OnActionExpandListener onActionExpandListener2 = onActionExpandListener;
        throw new UnsupportedOperationException();
    }

    public SupportMenuItem setSupportOnActionExpandListener(MenuItemCompat.OnActionExpandListener onActionExpandListener) {
        MenuItemCompat.OnActionExpandListener onActionExpandListener2 = onActionExpandListener;
        return this;
    }
}
