package android.support.p003v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.MenuPresenter.Callback;
import android.support.p003v7.widget.MenuPopupWindow;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

/* renamed from: android.support.v7.view.menu.StandardMenuPopup */
final class StandardMenuPopup extends MenuPopup implements OnDismissListener, OnItemClickListener, MenuPresenter, OnKeyListener {
    private final MenuAdapter mAdapter;
    private View mAnchorView;
    private int mContentWidth;
    private final Context mContext;
    private int mDropDownGravity = 0;
    private final OnGlobalLayoutListener mGlobalLayoutListener = new OnGlobalLayoutListener(this) {
        final /* synthetic */ StandardMenuPopup this$0;

        {
            StandardMenuPopup this$02 = r5;
            StandardMenuPopup standardMenuPopup = this$02;
            this.this$0 = this$02;
        }

        public void onGlobalLayout() {
            if (this.this$0.isShowing() && !this.this$0.mPopup.isModal()) {
                View view = this.this$0.mShownAnchorView;
                View anchor = view;
                if (view != null && anchor.isShown()) {
                    this.this$0.mPopup.show();
                } else {
                    this.this$0.dismiss();
                }
            }
        }
    };
    private boolean mHasContentWidth;
    private final MenuBuilder mMenu;
    private OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    final MenuPopupWindow mPopup;
    private final int mPopupMaxWidth;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private Callback mPresenterCallback;
    private boolean mShowTitle;
    View mShownAnchorView;
    private ViewTreeObserver mTreeObserver;
    private boolean mWasDismissed;

    public StandardMenuPopup(Context context, MenuBuilder menuBuilder, View view, int i, int i2, boolean z) {
        Context context2 = context;
        MenuBuilder menu = menuBuilder;
        View anchorView = view;
        int popupStyleAttr = i;
        int popupStyleRes = i2;
        Context context3 = context2;
        MenuBuilder menuBuilder2 = menu;
        View view2 = anchorView;
        int i3 = popupStyleAttr;
        int i4 = popupStyleRes;
        boolean overflowOnly = z;
        this.mContext = context2;
        this.mMenu = menu;
        this.mOverflowOnly = overflowOnly;
        MenuAdapter menuAdapter = new MenuAdapter(menu, LayoutInflater.from(context2), this.mOverflowOnly);
        this.mAdapter = menuAdapter;
        this.mPopupStyleAttr = popupStyleAttr;
        this.mPopupStyleRes = popupStyleRes;
        Resources res = context2.getResources();
        this.mPopupMaxWidth = Math.max(res.getDisplayMetrics().widthPixels / 2, res.getDimensionPixelSize(C0268R.dimen.abc_config_prefDialogWidth));
        this.mAnchorView = anchorView;
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        this.mPopup = menuPopupWindow;
        menu.addMenuPresenter(this, context2);
    }

    public void setForceShowIcon(boolean z) {
        this.mAdapter.setForceShowIcon(z);
    }

    public void setGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        this.mDropDownGravity = gravity;
    }

    private boolean tryShow() {
        if (isShowing()) {
            return true;
        }
        if (this.mWasDismissed || this.mAnchorView == null) {
            return false;
        }
        this.mShownAnchorView = this.mAnchorView;
        this.mPopup.setOnDismissListener(this);
        this.mPopup.setOnItemClickListener(this);
        this.mPopup.setModal(true);
        View anchor = this.mShownAnchorView;
        boolean addGlobalListener = this.mTreeObserver == null;
        this.mTreeObserver = anchor.getViewTreeObserver();
        if (addGlobalListener) {
            this.mTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
        this.mPopup.setAnchorView(anchor);
        this.mPopup.setDropDownGravity(this.mDropDownGravity);
        if (!this.mHasContentWidth) {
            this.mContentWidth = measureIndividualMenuWidth(this.mAdapter, null, this.mContext, this.mPopupMaxWidth);
            this.mHasContentWidth = true;
        }
        this.mPopup.setContentWidth(this.mContentWidth);
        this.mPopup.setInputMethodMode(2);
        this.mPopup.setEpicenterBounds(getEpicenterBounds());
        this.mPopup.show();
        ListView listView = this.mPopup.getListView();
        ListView listView2 = listView;
        listView.setOnKeyListener(this);
        if (this.mShowTitle && this.mMenu.getHeaderTitle() != null) {
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.mContext).inflate(C0268R.layout.abc_popup_menu_header_item_layout, listView2, false);
            FrameLayout titleItemView = frameLayout;
            TextView textView = (TextView) frameLayout.findViewById(16908310);
            TextView titleView = textView;
            if (textView != null) {
                titleView.setText(this.mMenu.getHeaderTitle());
            }
            titleItemView.setEnabled(false);
            listView2.addHeaderView(titleItemView, null, false);
        }
        this.mPopup.setAdapter(this.mAdapter);
        this.mPopup.show();
        return true;
    }

    public void show() {
        if (!tryShow()) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    public void dismiss() {
        if (isShowing()) {
            this.mPopup.dismiss();
        }
    }

    public void addMenu(MenuBuilder menuBuilder) {
        MenuBuilder menuBuilder2 = menuBuilder;
    }

    public boolean isShowing() {
        return !this.mWasDismissed && this.mPopup.isShowing();
    }

    public void onDismiss() {
        this.mWasDismissed = true;
        this.mMenu.close();
        if (this.mTreeObserver != null) {
            if (!this.mTreeObserver.isAlive()) {
                this.mTreeObserver = this.mShownAnchorView.getViewTreeObserver();
            }
            this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
            this.mTreeObserver = null;
        }
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.onDismiss();
        }
    }

    public void updateMenuView(boolean z) {
        boolean z2 = z;
        this.mHasContentWidth = false;
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
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
        if (subMenu.hasVisibleItems()) {
            MenuPopupHelper menuPopupHelper = new MenuPopupHelper(this.mContext, subMenu, this.mShownAnchorView, this.mOverflowOnly, this.mPopupStyleAttr, this.mPopupStyleRes);
            MenuPopupHelper subPopup = menuPopupHelper;
            menuPopupHelper.setPresenterCallback(this.mPresenterCallback);
            subPopup.setForceShowIcon(MenuPopup.shouldPreserveIconSpacing(subMenu));
            subPopup.setOnDismissListener(this.mOnDismissListener);
            this.mOnDismissListener = null;
            this.mMenu.close(false);
            int horizontalOffset = this.mPopup.getHorizontalOffset();
            int verticalOffset = this.mPopup.getVerticalOffset();
            int i = verticalOffset;
            if (subPopup.tryShow(horizontalOffset, verticalOffset)) {
                if (this.mPresenterCallback != null) {
                    boolean onOpenSubMenu = this.mPresenterCallback.onOpenSubMenu(subMenu);
                }
                return true;
            }
        }
        return false;
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        boolean allMenusAreClosing = z;
        if (menu == this.mMenu) {
            dismiss();
            if (this.mPresenterCallback != null) {
                this.mPresenterCallback.onCloseMenu(menu, allMenusAreClosing);
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

    public void setAnchorView(View view) {
        View anchor = view;
        View view2 = anchor;
        this.mAnchorView = anchor;
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

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        OnDismissListener listener = onDismissListener;
        OnDismissListener onDismissListener2 = listener;
        this.mOnDismissListener = listener;
    }

    public ListView getListView() {
        return this.mPopup.getListView();
    }

    public void setHorizontalOffset(int i) {
        int x = i;
        int i2 = x;
        this.mPopup.setHorizontalOffset(x);
    }

    public void setVerticalOffset(int i) {
        int y = i;
        int i2 = y;
        this.mPopup.setVerticalOffset(y);
    }

    public void setShowTitle(boolean z) {
        this.mShowTitle = z;
    }
}
