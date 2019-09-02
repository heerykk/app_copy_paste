package android.support.p003v7.view.menu;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.IBinder;
import android.support.p000v4.view.PointerIconCompat;
import android.support.p003v7.app.AlertDialog;
import android.support.p003v7.app.AlertDialog.Builder;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.MenuPresenter.Callback;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

/* renamed from: android.support.v7.view.menu.MenuDialogHelper */
class MenuDialogHelper implements OnKeyListener, OnClickListener, OnDismissListener, Callback {
    private AlertDialog mDialog;
    private MenuBuilder mMenu;
    ListMenuPresenter mPresenter;
    private Callback mPresenterCallback;

    public MenuDialogHelper(MenuBuilder menuBuilder) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        this.mMenu = menu;
    }

    public void show(IBinder iBinder) {
        IBinder windowToken = iBinder;
        IBinder iBinder2 = windowToken;
        MenuBuilder menu = this.mMenu;
        Builder builder = new Builder(menu.getContext());
        this.mPresenter = new ListMenuPresenter(builder.getContext(), C0268R.layout.abc_list_menu_item_layout);
        this.mPresenter.setCallback(this);
        this.mMenu.addMenuPresenter(this.mPresenter);
        Builder adapter = builder.setAdapter(this.mPresenter.getAdapter(), this);
        View headerView = menu.getHeaderView();
        View headerView2 = headerView;
        if (headerView == null) {
            Builder title = builder.setIcon(menu.getHeaderIcon()).setTitle(menu.getHeaderTitle());
        } else {
            Builder customTitle = builder.setCustomTitle(headerView2);
        }
        Builder onKeyListener = builder.setOnKeyListener(this);
        this.mDialog = builder.create();
        this.mDialog.setOnDismissListener(this);
        LayoutParams attributes = this.mDialog.getWindow().getAttributes();
        LayoutParams lp = attributes;
        attributes.type = PointerIconCompat.TYPE_HELP;
        if (windowToken != null) {
            lp.token = windowToken;
        }
        lp.flags |= 131072;
        this.mDialog.show();
    }

    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        DialogInterface dialog = dialogInterface;
        int keyCode = i;
        KeyEvent event = keyEvent;
        DialogInterface dialogInterface2 = dialog;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        if (keyCode == 82 || keyCode == 4) {
            if (event.getAction() == 0 && event.getRepeatCount() == 0) {
                Window window = this.mDialog.getWindow();
                Window win = window;
                if (window != null) {
                    View decorView = win.getDecorView();
                    View decor = decorView;
                    if (decorView != null) {
                        DispatcherState keyDispatcherState = decor.getKeyDispatcherState();
                        DispatcherState ds = keyDispatcherState;
                        if (keyDispatcherState != null) {
                            ds.startTracking(event, this);
                            return true;
                        }
                    }
                }
            } else if (event.getAction() == 1 && !event.isCanceled()) {
                Window window2 = this.mDialog.getWindow();
                Window win2 = window2;
                if (window2 != null) {
                    View decorView2 = win2.getDecorView();
                    View decor2 = decorView2;
                    if (decorView2 != null) {
                        DispatcherState keyDispatcherState2 = decor2.getKeyDispatcherState();
                        DispatcherState ds2 = keyDispatcherState2;
                        if (keyDispatcherState2 != null && ds2.isTracking(event)) {
                            this.mMenu.close(true);
                            dialog.dismiss();
                            return true;
                        }
                    }
                }
            }
        }
        return this.mMenu.performShortcut(keyCode, event, 0);
    }

    public void setPresenterCallback(Callback callback) {
        Callback cb = callback;
        Callback callback2 = cb;
        this.mPresenterCallback = cb;
    }

    public void dismiss() {
        if (this.mDialog != null) {
            this.mDialog.dismiss();
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        DialogInterface dialogInterface2 = dialogInterface;
        this.mPresenter.onCloseMenu(this.mMenu, true);
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        boolean allMenusAreClosing = z;
        if (allMenusAreClosing || menu == this.mMenu) {
            dismiss();
        }
        if (this.mPresenterCallback != null) {
            this.mPresenterCallback.onCloseMenu(menu, allMenusAreClosing);
        }
    }

    public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        MenuBuilder subMenu = menuBuilder;
        MenuBuilder menuBuilder2 = subMenu;
        if (this.mPresenterCallback == null) {
            return false;
        }
        return this.mPresenterCallback.onOpenSubMenu(subMenu);
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        int which = i;
        DialogInterface dialogInterface2 = dialogInterface;
        int i2 = which;
        boolean performItemAction = this.mMenu.performItemAction((MenuItemImpl) this.mPresenter.getAdapter().getItem(which), 0);
    }
}
