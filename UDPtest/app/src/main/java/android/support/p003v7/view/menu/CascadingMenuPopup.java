package android.support.p003v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.MenuPresenter.Callback;
import android.support.p003v7.widget.MenuItemHoverListener;
import android.support.p003v7.widget.MenuPopupWindow;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* renamed from: android.support.v7.view.menu.CascadingMenuPopup */
final class CascadingMenuPopup extends MenuPopup implements MenuPresenter, OnKeyListener, OnDismissListener {
    static final int HORIZ_POSITION_LEFT = 0;
    static final int HORIZ_POSITION_RIGHT = 1;
    static final int SUBMENU_TIMEOUT_MS = 200;
    private View mAnchorView;
    private final Context mContext;
    private int mDropDownGravity = 0;
    private boolean mForceShowIcon;
    private final OnGlobalLayoutListener mGlobalLayoutListener = new OnGlobalLayoutListener(this) {
        final /* synthetic */ CascadingMenuPopup this$0;

        {
            CascadingMenuPopup this$02 = r5;
            CascadingMenuPopup cascadingMenuPopup = this$02;
            this.this$0 = this$02;
        }

        public void onGlobalLayout() {
            if (this.this$0.isShowing() && this.this$0.mShowingMenus.size() > 0 && !((CascadingMenuInfo) this.this$0.mShowingMenus.get(0)).window.isModal()) {
                View view = this.this$0.mShownAnchorView;
                View anchor = view;
                if (view != null && anchor.isShown()) {
                    for (CascadingMenuInfo cascadingMenuInfo : this.this$0.mShowingMenus) {
                        CascadingMenuInfo cascadingMenuInfo2 = cascadingMenuInfo;
                        cascadingMenuInfo.window.show();
                    }
                    return;
                }
                this.this$0.dismiss();
            }
        }
    };
    private boolean mHasXOffset;
    private boolean mHasYOffset;
    private int mLastPosition;
    private final MenuItemHoverListener mMenuItemHoverListener = new MenuItemHoverListener(this) {
        final /* synthetic */ CascadingMenuPopup this$0;

        {
            CascadingMenuPopup this$02 = r5;
            CascadingMenuPopup cascadingMenuPopup = this$02;
            this.this$0 = this$02;
        }

        public void onItemHoverExit(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            MenuBuilder menu = menuBuilder;
            MenuBuilder menuBuilder2 = menu;
            MenuItem menuItem2 = menuItem;
            this.this$0.mSubMenuHoverHandler.removeCallbacksAndMessages(menu);
        }

        public void onItemHoverEnter(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            CascadingMenuInfo nextInfo;
            MenuBuilder menu = menuBuilder;
            MenuItem item = menuItem;
            MenuBuilder menuBuilder2 = menu;
            MenuItem menuItem2 = item;
            this.this$0.mSubMenuHoverHandler.removeCallbacksAndMessages(null);
            int menuIndex = -1;
            int i = 0;
            int count = this.this$0.mShowingMenus.size();
            while (true) {
                if (i < count) {
                    if (menu == ((CascadingMenuInfo) this.this$0.mShowingMenus.get(i)).menu) {
                        menuIndex = i;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            if (menuIndex != -1) {
                int i2 = menuIndex + 1;
                int count2 = i2;
                if (i2 >= this.this$0.mShowingMenus.size()) {
                    nextInfo = null;
                } else {
                    nextInfo = (CascadingMenuInfo) this.this$0.mShowingMenus.get(count2);
                }
                final CascadingMenuInfo cascadingMenuInfo = nextInfo;
                final MenuItem menuItem3 = item;
                final MenuBuilder menuBuilder3 = menu;
                long uptimeMillis = SystemClock.uptimeMillis() + 200;
                long j = uptimeMillis;
                boolean postAtTime = this.this$0.mSubMenuHoverHandler.postAtTime(new Runnable(this) {
                    final /* synthetic */ C02832 this$1;

                    {
                        C02832 this$12 = r8;
                        CascadingMenuInfo cascadingMenuInfo = r9;
                        MenuItem menuItem = r10;
                        MenuBuilder menuBuilder = r11;
                        C02832 r6 = this$12;
                        this.this$1 = this$12;
                    }

                    public void run() {
                        if (cascadingMenuInfo != null) {
                            this.this$1.this$0.mShouldCloseImmediately = true;
                            cascadingMenuInfo.menu.close(false);
                            this.this$1.this$0.mShouldCloseImmediately = false;
                        }
                        if (menuItem3.isEnabled() && menuItem3.hasSubMenu()) {
                            boolean performItemAction = menuBuilder3.performItemAction(menuItem3, 0);
                        }
                    }
                }, menu, uptimeMillis);
            }
        }
    };
    private final int mMenuMaxWidth;
    private OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private final List<MenuBuilder> mPendingMenus = new LinkedList();
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private Callback mPresenterCallback;
    private int mRawDropDownGravity = 0;
    boolean mShouldCloseImmediately;
    private boolean mShowTitle;
    final List<CascadingMenuInfo> mShowingMenus = new ArrayList();
    View mShownAnchorView;
    final Handler mSubMenuHoverHandler;
    private ViewTreeObserver mTreeObserver;
    private int mXOffset;
    private int mYOffset;

    /* renamed from: android.support.v7.view.menu.CascadingMenuPopup$CascadingMenuInfo */
    private static class CascadingMenuInfo {
        public final MenuBuilder menu;
        public final int position;
        public final MenuPopupWindow window;

        public CascadingMenuInfo(@NonNull MenuPopupWindow menuPopupWindow, @NonNull MenuBuilder menuBuilder, int i) {
            MenuPopupWindow window2 = menuPopupWindow;
            MenuBuilder menu2 = menuBuilder;
            int position2 = i;
            MenuPopupWindow menuPopupWindow2 = window2;
            MenuBuilder menuBuilder2 = menu2;
            int i2 = position2;
            this.window = window2;
            this.menu = menu2;
            this.position = position2;
        }

        public ListView getListView() {
            return this.window.getListView();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v7.view.menu.CascadingMenuPopup$HorizPosition */
    public @interface HorizPosition {
    }

    public CascadingMenuPopup(@NonNull Context context, @NonNull View view, @AttrRes int i, @StyleRes int i2, boolean z) {
        Context context2 = context;
        View anchor = view;
        int popupStyleAttr = i;
        int popupStyleRes = i2;
        Context context3 = context2;
        View view2 = anchor;
        int i3 = popupStyleAttr;
        int i4 = popupStyleRes;
        boolean overflowOnly = z;
        this.mContext = context2;
        this.mAnchorView = anchor;
        this.mPopupStyleAttr = popupStyleAttr;
        this.mPopupStyleRes = popupStyleRes;
        this.mOverflowOnly = overflowOnly;
        this.mForceShowIcon = false;
        this.mLastPosition = getInitialMenuPosition();
        Resources res = context2.getResources();
        this.mMenuMaxWidth = Math.max(res.getDisplayMetrics().widthPixels / 2, res.getDimensionPixelSize(C0268R.dimen.abc_config_prefDialogWidth));
        this.mSubMenuHoverHandler = new Handler();
    }

    public void setForceShowIcon(boolean z) {
        this.mForceShowIcon = z;
    }

    private MenuPopupWindow createPopupWindow() {
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        MenuPopupWindow popupWindow = menuPopupWindow;
        menuPopupWindow.setHoverListener(this.mMenuItemHoverListener);
        popupWindow.setOnItemClickListener(this);
        popupWindow.setOnDismissListener(this);
        popupWindow.setAnchorView(this.mAnchorView);
        popupWindow.setDropDownGravity(this.mDropDownGravity);
        popupWindow.setModal(true);
        return popupWindow;
    }

    public void show() {
        if (!isShowing()) {
            for (MenuBuilder showMenu : this.mPendingMenus) {
                showMenu(showMenu);
            }
            this.mPendingMenus.clear();
            this.mShownAnchorView = this.mAnchorView;
            if (this.mShownAnchorView != null) {
                boolean addGlobalListener = this.mTreeObserver == null;
                this.mTreeObserver = this.mShownAnchorView.getViewTreeObserver();
                if (addGlobalListener) {
                    this.mTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
                }
            }
        }
    }

    public void dismiss() {
        int size = this.mShowingMenus.size();
        int length = size;
        if (size > 0) {
            CascadingMenuInfo[] addedMenus = (CascadingMenuInfo[]) this.mShowingMenus.toArray(new CascadingMenuInfo[length]);
            for (int i = length - 1; i >= 0; i--) {
                CascadingMenuInfo cascadingMenuInfo = addedMenus[i];
                CascadingMenuInfo info = cascadingMenuInfo;
                if (cascadingMenuInfo.window.isShowing()) {
                    info.window.dismiss();
                }
            }
        }
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        View view2 = view;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        if (event.getAction() != 1 || keyCode != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    private int getInitialMenuPosition() {
        int layoutDirection = ViewCompat.getLayoutDirection(this.mAnchorView);
        int i = layoutDirection;
        return layoutDirection != 1 ? 1 : 0;
    }

    private int getNextMenuPosition(int i) {
        int nextMenuWidth = i;
        int i2 = nextMenuWidth;
        ListView lastListView = ((CascadingMenuInfo) this.mShowingMenus.get(this.mShowingMenus.size() - 1)).getListView();
        int[] screenLocation = new int[2];
        lastListView.getLocationOnScreen(screenLocation);
        Rect displayFrame = new Rect();
        this.mShownAnchorView.getWindowVisibleDisplayFrame(displayFrame);
        if (this.mLastPosition != 1) {
            int i3 = screenLocation[0] - nextMenuWidth;
            int i4 = i3;
            if (i3 >= 0) {
                return 0;
            }
            return 1;
        }
        int width = screenLocation[0] + lastListView.getWidth() + nextMenuWidth;
        int i5 = width;
        if (width <= displayFrame.right) {
            return 1;
        }
        return 0;
    }

    public void addMenu(MenuBuilder menuBuilder) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        menu.addMenuPresenter(this, this.mContext);
        if (!isShowing()) {
            boolean add = this.mPendingMenus.add(menu);
        } else {
            showMenu(menu);
        }
    }

    private void showMenu(@NonNull MenuBuilder menuBuilder) {
        CascadingMenuInfo parentInfo;
        View parentView;
        int x;
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        MenuAdapter adapter = new MenuAdapter(menu, inflater, this.mOverflowOnly);
        if (!isShowing() && this.mForceShowIcon) {
            adapter.setForceShowIcon(true);
        } else if (isShowing()) {
            adapter.setForceShowIcon(MenuPopup.shouldPreserveIconSpacing(menu));
        }
        int measureIndividualMenuWidth = measureIndividualMenuWidth(adapter, null, this.mContext, this.mMenuMaxWidth);
        int menuWidth = measureIndividualMenuWidth;
        MenuPopupWindow createPopupWindow = createPopupWindow();
        MenuPopupWindow popupWindow = createPopupWindow;
        createPopupWindow.setAdapter(adapter);
        popupWindow.setContentWidth(measureIndividualMenuWidth);
        popupWindow.setDropDownGravity(this.mDropDownGravity);
        if (this.mShowingMenus.size() <= 0) {
            parentInfo = null;
            parentView = null;
        } else {
            parentInfo = (CascadingMenuInfo) this.mShowingMenus.get(this.mShowingMenus.size() - 1);
            parentView = findParentViewForSubmenu(parentInfo, menu);
        }
        if (parentView == null) {
            if (this.mHasXOffset) {
                popupWindow.setHorizontalOffset(this.mXOffset);
            }
            if (this.mHasYOffset) {
                popupWindow.setVerticalOffset(this.mYOffset);
            }
            popupWindow.setEpicenterBounds(getEpicenterBounds());
        } else {
            popupWindow.setTouchModal(false);
            popupWindow.setEnterTransition(null);
            int nextMenuPosition = getNextMenuPosition(menuWidth);
            boolean showOnRight = nextMenuPosition == 1;
            this.mLastPosition = nextMenuPosition;
            int[] tempLocation = new int[2];
            parentView.getLocationInWindow(tempLocation);
            int parentOffsetLeft = parentInfo.window.getHorizontalOffset() + tempLocation[0];
            int parentOffsetTop = parentInfo.window.getVerticalOffset() + tempLocation[1];
            if ((this.mDropDownGravity & 5) != 5) {
                if (!showOnRight) {
                    x = parentOffsetLeft - menuWidth;
                } else {
                    x = parentOffsetLeft + parentView.getWidth();
                }
            } else if (!showOnRight) {
                x = parentOffsetLeft - parentView.getWidth();
            } else {
                x = parentOffsetLeft + menuWidth;
            }
            popupWindow.setHorizontalOffset(x);
            int i = parentOffsetTop;
            popupWindow.setVerticalOffset(parentOffsetTop);
        }
        boolean add = this.mShowingMenus.add(new CascadingMenuInfo(popupWindow, menu, this.mLastPosition));
        popupWindow.show();
        if (parentInfo == null && this.mShowTitle && menu.getHeaderTitle() != null) {
            ListView listView = popupWindow.getListView();
            FrameLayout frameLayout = (FrameLayout) inflater.inflate(C0268R.layout.abc_popup_menu_header_item_layout, listView, false);
            FrameLayout titleItemView = frameLayout;
            TextView titleView = (TextView) frameLayout.findViewById(16908310);
            titleItemView.setEnabled(false);
            titleView.setText(menu.getHeaderTitle());
            listView.addHeaderView(titleItemView, null, false);
            popupWindow.show();
        }
    }

    private MenuItem findMenuItemForSubmenu(@NonNull MenuBuilder menuBuilder, @NonNull MenuBuilder menuBuilder2) {
        MenuBuilder parent = menuBuilder;
        MenuBuilder submenu = menuBuilder2;
        MenuBuilder menuBuilder3 = parent;
        MenuBuilder menuBuilder4 = submenu;
        int count = parent.size();
        for (int i = 0; i < count; i++) {
            MenuItem item = parent.getItem(i);
            MenuItem item2 = item;
            if (item.hasSubMenu() && submenu == item2.getSubMenu()) {
                return item2;
            }
        }
        return null;
    }

    @Nullable
    private View findParentViewForSubmenu(@NonNull CascadingMenuInfo cascadingMenuInfo, @NonNull MenuBuilder menuBuilder) {
        int headersCount;
        MenuAdapter menuAdapter;
        CascadingMenuInfo parentInfo = cascadingMenuInfo;
        MenuBuilder submenu = menuBuilder;
        CascadingMenuInfo cascadingMenuInfo2 = parentInfo;
        MenuBuilder menuBuilder2 = submenu;
        MenuItem findMenuItemForSubmenu = findMenuItemForSubmenu(parentInfo.menu, submenu);
        MenuItem owner = findMenuItemForSubmenu;
        if (findMenuItemForSubmenu == null) {
            return null;
        }
        ListView listView = parentInfo.getListView();
        ListView listView2 = listView;
        ListAdapter adapter = listView.getAdapter();
        ListAdapter listAdapter = adapter;
        if (!(adapter instanceof HeaderViewListAdapter)) {
            headersCount = 0;
            menuAdapter = (MenuAdapter) listAdapter;
        } else {
            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) listAdapter;
            HeaderViewListAdapter headerAdapter = headerViewListAdapter;
            headersCount = headerViewListAdapter.getHeadersCount();
            menuAdapter = (MenuAdapter) headerAdapter.getWrappedAdapter();
        }
        int ownerPosition = -1;
        int i = 0;
        int count = menuAdapter.getCount();
        while (true) {
            if (i < count) {
                if (owner == menuAdapter.getItem(i)) {
                    ownerPosition = i;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        if (ownerPosition == -1) {
            return null;
        }
        int i2 = ownerPosition + headersCount;
        int ownerPosition2 = i2;
        int firstVisiblePosition = i2 - listView2.getFirstVisiblePosition();
        int ownerViewPosition = firstVisiblePosition;
        if (firstVisiblePosition >= 0 && ownerViewPosition < listView2.getChildCount()) {
            return listView2.getChildAt(ownerViewPosition);
        }
        return null;
    }

    public boolean isShowing() {
        return this.mShowingMenus.size() > 0 && ((CascadingMenuInfo) this.mShowingMenus.get(0)).window.isShowing();
    }

    public void onDismiss() {
        CascadingMenuInfo dismissedInfo = null;
        int i = 0;
        int count = this.mShowingMenus.size();
        while (true) {
            if (i < count) {
                CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) this.mShowingMenus.get(i);
                CascadingMenuInfo info = cascadingMenuInfo;
                if (!cascadingMenuInfo.window.isShowing()) {
                    dismissedInfo = info;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        if (dismissedInfo != null) {
            dismissedInfo.menu.close(false);
        }
    }

    public void updateMenuView(boolean z) {
        boolean z2 = z;
        for (CascadingMenuInfo cascadingMenuInfo : this.mShowingMenus) {
            CascadingMenuInfo cascadingMenuInfo2 = cascadingMenuInfo;
            toMenuAdapter(cascadingMenuInfo.getListView().getAdapter()).notifyDataSetChanged();
        }
    }

    public void setCallback(Callback callback) {
        Callback cb = callback;
        Callback callback2 = cb;
        this.mPresenterCallback = cb;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        SubMenuBuilder subMenu = subMenuBuilder;
        SubMenuBuilder subMenuBuilder2 = subMenu;
        for (CascadingMenuInfo info : this.mShowingMenus) {
            if (subMenu == info.menu) {
                boolean requestFocus = info.getListView().requestFocus();
                return true;
            }
        }
        if (!subMenu.hasVisibleItems()) {
            return false;
        }
        addMenu(subMenu);
        if (this.mPresenterCallback != null) {
            boolean onOpenSubMenu = this.mPresenterCallback.onOpenSubMenu(subMenu);
        }
        return true;
    }

    private int findIndexOfAddedMenu(@NonNull MenuBuilder menuBuilder) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        int count = this.mShowingMenus.size();
        for (int i = 0; i < count; i++) {
            if (menu == ((CascadingMenuInfo) this.mShowingMenus.get(i)).menu) {
                return i;
            }
        }
        return -1;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        boolean allMenusAreClosing = z;
        int findIndexOfAddedMenu = findIndexOfAddedMenu(menu);
        int menuIndex = findIndexOfAddedMenu;
        if (findIndexOfAddedMenu >= 0) {
            int i = menuIndex + 1;
            int nextMenuIndex = i;
            if (i < this.mShowingMenus.size()) {
                CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) this.mShowingMenus.get(nextMenuIndex);
                CascadingMenuInfo cascadingMenuInfo2 = cascadingMenuInfo;
                cascadingMenuInfo.menu.close(false);
            }
            CascadingMenuInfo cascadingMenuInfo3 = (CascadingMenuInfo) this.mShowingMenus.remove(menuIndex);
            CascadingMenuInfo info = cascadingMenuInfo3;
            cascadingMenuInfo3.menu.removeMenuPresenter(this);
            if (this.mShouldCloseImmediately) {
                info.window.setExitTransition(null);
                info.window.setAnimationStyle(0);
            }
            info.window.dismiss();
            int size = this.mShowingMenus.size();
            int count = size;
            if (size <= 0) {
                this.mLastPosition = getInitialMenuPosition();
            } else {
                this.mLastPosition = ((CascadingMenuInfo) this.mShowingMenus.get(count - 1)).position;
            }
            if (count == 0) {
                dismiss();
                if (this.mPresenterCallback != null) {
                    this.mPresenterCallback.onCloseMenu(menu, true);
                }
                if (this.mTreeObserver != null) {
                    if (this.mTreeObserver.isAlive()) {
                        this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
                    }
                    this.mTreeObserver = null;
                }
                this.mOnDismissListener.onDismiss();
            } else if (allMenusAreClosing) {
                CascadingMenuInfo cascadingMenuInfo4 = (CascadingMenuInfo) this.mShowingMenus.get(0);
                CascadingMenuInfo cascadingMenuInfo5 = cascadingMenuInfo4;
                cascadingMenuInfo4.menu.close(false);
            }
        }
    }

    public boolean flagActionItems() {
        return false;
    }

    public Parcelable onSaveInstanceState() {
        return null;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2 = parcelable;
    }

    public void setGravity(int i) {
        int dropDownGravity = i;
        int i2 = dropDownGravity;
        if (this.mRawDropDownGravity != dropDownGravity) {
            this.mRawDropDownGravity = dropDownGravity;
            this.mDropDownGravity = GravityCompat.getAbsoluteGravity(dropDownGravity, ViewCompat.getLayoutDirection(this.mAnchorView));
        }
    }

    public void setAnchorView(@NonNull View view) {
        View anchor = view;
        View view2 = anchor;
        if (this.mAnchorView != anchor) {
            this.mAnchorView = anchor;
            this.mDropDownGravity = GravityCompat.getAbsoluteGravity(this.mRawDropDownGravity, ViewCompat.getLayoutDirection(this.mAnchorView));
        }
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        OnDismissListener listener = onDismissListener;
        OnDismissListener onDismissListener2 = listener;
        this.mOnDismissListener = listener;
    }

    public ListView getListView() {
        ListView listView;
        if (!this.mShowingMenus.isEmpty()) {
            listView = ((CascadingMenuInfo) this.mShowingMenus.get(this.mShowingMenus.size() - 1)).getListView();
        } else {
            listView = null;
        }
        return listView;
    }

    public void setHorizontalOffset(int i) {
        int x = i;
        int i2 = x;
        this.mHasXOffset = true;
        this.mXOffset = x;
    }

    public void setVerticalOffset(int i) {
        int y = i;
        int i2 = y;
        this.mHasYOffset = true;
        this.mYOffset = y;
    }

    public void setShowTitle(boolean z) {
        this.mShowTitle = z;
    }

    /* access modifiers changed from: protected */
    public boolean closeMenuOnSubMenuOpened() {
        return false;
    }
}
