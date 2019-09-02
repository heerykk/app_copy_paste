package android.support.p003v7.view;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuBuilder.Callback;
import android.support.p003v7.view.menu.MenuPopupHelper;
import android.support.p003v7.view.menu.SubMenuBuilder;
import android.support.p003v7.widget.ActionBarContextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.lang.ref.WeakReference;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.StandaloneActionMode */
public class StandaloneActionMode extends ActionMode implements Callback {
    private ActionMode.Callback mCallback;
    private Context mContext;
    private ActionBarContextView mContextView;
    private WeakReference<View> mCustomView;
    private boolean mFinished;
    private boolean mFocusable;
    private MenuBuilder mMenu;

    public StandaloneActionMode(Context context, ActionBarContextView actionBarContextView, ActionMode.Callback callback, boolean z) {
        Context context2 = context;
        ActionBarContextView view = actionBarContextView;
        ActionMode.Callback callback2 = callback;
        Context context3 = context2;
        ActionBarContextView actionBarContextView2 = view;
        ActionMode.Callback callback3 = callback2;
        boolean isFocusable = z;
        this.mContext = context2;
        this.mContextView = view;
        this.mCallback = callback2;
        this.mMenu = new MenuBuilder(view.getContext()).setDefaultShowAsAction(1);
        this.mMenu.setCallback(this);
        this.mFocusable = isFocusable;
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mContextView.setTitle(title);
    }

    public void setSubtitle(CharSequence charSequence) {
        CharSequence subtitle = charSequence;
        CharSequence charSequence2 = subtitle;
        this.mContextView.setSubtitle(subtitle);
    }

    public void setTitle(int i) {
        int resId = i;
        int i2 = resId;
        setTitle((CharSequence) this.mContext.getString(resId));
    }

    public void setSubtitle(int i) {
        int resId = i;
        int i2 = resId;
        setSubtitle((CharSequence) this.mContext.getString(resId));
    }

    public void setTitleOptionalHint(boolean z) {
        boolean titleOptional = z;
        super.setTitleOptionalHint(titleOptional);
        this.mContextView.setTitleOptional(titleOptional);
    }

    public boolean isTitleOptional() {
        return this.mContextView.isTitleOptional();
    }

    public void setCustomView(View view) {
        View view2 = view;
        View view3 = view2;
        this.mContextView.setCustomView(view2);
        this.mCustomView = view2 == null ? null : new WeakReference<>(view2);
    }

    public void invalidate() {
        boolean onPrepareActionMode = this.mCallback.onPrepareActionMode(this, this.mMenu);
    }

    public void finish() {
        if (!this.mFinished) {
            this.mFinished = true;
            this.mContextView.sendAccessibilityEvent(32);
            this.mCallback.onDestroyActionMode(this);
        }
    }

    public Menu getMenu() {
        return this.mMenu;
    }

    public CharSequence getTitle() {
        return this.mContextView.getTitle();
    }

    public CharSequence getSubtitle() {
        return this.mContextView.getSubtitle();
    }

    public View getCustomView() {
        return this.mCustomView == null ? null : (View) this.mCustomView.get();
    }

    public MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.mContextView.getContext());
    }

    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuBuilder menuBuilder2 = menuBuilder;
        MenuItem menuItem2 = item;
        return this.mCallback.onActionItemClicked(this, item);
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuBuilder menuBuilder2 = menuBuilder;
        boolean z2 = z;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        SubMenuBuilder subMenu = subMenuBuilder;
        SubMenuBuilder subMenuBuilder2 = subMenu;
        if (!subMenu.hasVisibleItems()) {
            return true;
        }
        new MenuPopupHelper(this.mContextView.getContext(), subMenu).show();
        return true;
    }

    public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
        SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
    }

    public void onMenuModeChange(MenuBuilder menuBuilder) {
        MenuBuilder menuBuilder2 = menuBuilder;
        invalidate();
        boolean showOverflowMenu = this.mContextView.showOverflowMenu();
    }

    public boolean isUiFocusable() {
        return this.mFocusable;
    }
}
