package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.view.menu.ListMenuItemView;
import android.support.p003v7.view.menu.MenuAdapter;
import android.support.p003v7.view.menu.MenuBuilder;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.MenuPopupWindow */
public class MenuPopupWindow extends ListPopupWindow implements MenuItemHoverListener {
    private static final String TAG = "MenuPopupWindow";
    private static Method sSetTouchModalMethod;
    private MenuItemHoverListener mHoverListener;

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.widget.MenuPopupWindow$MenuDropDownListView */
    public static class MenuDropDownListView extends DropDownListView {
        final int mAdvanceKey;
        private MenuItemHoverListener mHoverListener;
        private MenuItem mHoveredMenuItem;
        final int mRetreatKey;

        public /* bridge */ /* synthetic */ boolean hasFocus() {
            return super.hasFocus();
        }

        public /* bridge */ /* synthetic */ boolean hasWindowFocus() {
            return super.hasWindowFocus();
        }

        public /* bridge */ /* synthetic */ boolean isFocused() {
            return super.isFocused();
        }

        public /* bridge */ /* synthetic */ boolean isInTouchMode() {
            return super.isInTouchMode();
        }

        public /* bridge */ /* synthetic */ boolean onForwardedEvent(MotionEvent motionEvent, int i) {
            return super.onForwardedEvent(motionEvent, i);
        }

        public MenuDropDownListView(Context context, boolean z) {
            Context context2 = context;
            Context context3 = context2;
            super(context2, z);
            Resources resources = context2.getResources();
            Resources resources2 = resources;
            Configuration configuration = resources.getConfiguration();
            Configuration configuration2 = configuration;
            if (VERSION.SDK_INT >= 17 && 1 == configuration.getLayoutDirection()) {
                this.mAdvanceKey = 21;
                this.mRetreatKey = 22;
                return;
            }
            this.mAdvanceKey = 22;
            this.mRetreatKey = 21;
        }

        public void setHoverListener(MenuItemHoverListener menuItemHoverListener) {
            MenuItemHoverListener hoverListener = menuItemHoverListener;
            MenuItemHoverListener menuItemHoverListener2 = hoverListener;
            this.mHoverListener = hoverListener;
        }

        public void clearSelection() {
            setSelection(-1);
        }

        public boolean onKeyDown(int i, KeyEvent keyEvent) {
            int keyCode = i;
            KeyEvent event = keyEvent;
            int i2 = keyCode;
            KeyEvent keyEvent2 = event;
            ListMenuItemView listMenuItemView = (ListMenuItemView) getSelectedView();
            ListMenuItemView selectedItem = listMenuItemView;
            if (listMenuItemView != null && keyCode == this.mAdvanceKey) {
                if (selectedItem.isEnabled() && selectedItem.getItemData().hasSubMenu()) {
                    boolean performItemClick = performItemClick(selectedItem, getSelectedItemPosition(), getSelectedItemId());
                }
                return true;
            } else if (selectedItem != null && keyCode == this.mRetreatKey) {
                setSelection(-1);
                ((MenuAdapter) getAdapter()).getAdapterMenu().close(false);
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }

        public boolean onHoverEvent(MotionEvent motionEvent) {
            int headersCount;
            MenuAdapter menuAdapter;
            MotionEvent ev = motionEvent;
            MotionEvent motionEvent2 = ev;
            if (this.mHoverListener != null) {
                ListAdapter adapter = getAdapter();
                ListAdapter adapter2 = adapter;
                if (!(adapter instanceof HeaderViewListAdapter)) {
                    headersCount = 0;
                    menuAdapter = (MenuAdapter) adapter2;
                } else {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter2;
                    HeaderViewListAdapter headerAdapter = headerViewListAdapter;
                    headersCount = headerViewListAdapter.getHeadersCount();
                    menuAdapter = (MenuAdapter) headerAdapter.getWrappedAdapter();
                }
                MenuItem menuItem = null;
                if (ev.getAction() != 10) {
                    int pointToPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
                    int position = pointToPosition;
                    if (pointToPosition != -1) {
                        int i = position - headersCount;
                        int itemPosition = i;
                        if (i >= 0 && itemPosition < menuAdapter.getCount()) {
                            menuItem = menuAdapter.getItem(itemPosition);
                        }
                    }
                }
                MenuItem menuItem2 = this.mHoveredMenuItem;
                MenuItem oldMenuItem = menuItem2;
                if (menuItem2 != menuItem) {
                    MenuBuilder menu = menuAdapter.getAdapterMenu();
                    if (oldMenuItem != null) {
                        this.mHoverListener.onItemHoverExit(menu, oldMenuItem);
                    }
                    this.mHoveredMenuItem = menuItem;
                    if (menuItem != null) {
                        this.mHoverListener.onItemHoverEnter(menu, menuItem);
                    }
                }
            }
            return super.onHoverEvent(ev);
        }
    }

    static {
        Class<PopupWindow> cls = PopupWindow.class;
        String str = "setTouchModal";
        try {
            Class[] clsArr = new Class[1];
            clsArr[0] = Boolean.TYPE;
            sSetTouchModalMethod = cls.getDeclaredMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            NoSuchMethodException noSuchMethodException = e;
            int i = Log.i(TAG, "Could not find method setTouchModal() on PopupWindow. Oh well.");
        }
    }

    public MenuPopupWindow(Context context, AttributeSet attributeSet, int i, int i2) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        int defStyleRes = i2;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i3 = defStyleAttr;
        int i4 = defStyleRes;
        super(context2, attrs, defStyleAttr, defStyleRes);
    }

    /* access modifiers changed from: 0000 */
    public DropDownListView createDropDownListView(Context context, boolean z) {
        Context context2 = context;
        Context context3 = context2;
        MenuDropDownListView menuDropDownListView = new MenuDropDownListView(context2, z);
        MenuDropDownListView view = menuDropDownListView;
        menuDropDownListView.setHoverListener(this);
        return view;
    }

    public void setEnterTransition(Object obj) {
        Object enterTransition = obj;
        Object obj2 = enterTransition;
        if (VERSION.SDK_INT >= 23) {
            this.mPopup.setEnterTransition((Transition) enterTransition);
        }
    }

    public void setExitTransition(Object obj) {
        Object exitTransition = obj;
        Object obj2 = exitTransition;
        if (VERSION.SDK_INT >= 23) {
            this.mPopup.setExitTransition((Transition) exitTransition);
        }
    }

    public void setHoverListener(MenuItemHoverListener menuItemHoverListener) {
        MenuItemHoverListener hoverListener = menuItemHoverListener;
        MenuItemHoverListener menuItemHoverListener2 = hoverListener;
        this.mHoverListener = hoverListener;
    }

    public void setTouchModal(boolean z) {
        boolean touchModal = z;
        if (sSetTouchModalMethod != null) {
            try {
                Method method = sSetTouchModalMethod;
                PopupWindow popupWindow = this.mPopup;
                Object[] objArr = new Object[1];
                objArr[0] = Boolean.valueOf(touchModal);
                Object invoke = method.invoke(popupWindow, objArr);
            } catch (Exception e) {
                Exception exc = e;
                int i = Log.i(TAG, "Could not invoke setTouchModal() on PopupWindow. Oh well.");
            }
        }
    }

    public void onItemHoverEnter(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
        MenuBuilder menu = menuBuilder;
        MenuItem item = menuItem;
        MenuBuilder menuBuilder2 = menu;
        MenuItem menuItem2 = item;
        if (this.mHoverListener != null) {
            this.mHoverListener.onItemHoverEnter(menu, item);
        }
    }

    public void onItemHoverExit(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
        MenuBuilder menu = menuBuilder;
        MenuItem item = menuItem;
        MenuBuilder menuBuilder2 = menu;
        MenuItem menuItem2 = item;
        if (this.mHoverListener != null) {
            this.mHoverListener.onItemHoverExit(menu, item);
        }
    }
}
