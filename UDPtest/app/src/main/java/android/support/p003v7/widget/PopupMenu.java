package android.support.p003v7.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.SupportMenuInflater;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuBuilder.Callback;
import android.support.p003v7.view.menu.MenuPopupHelper;
import android.support.p003v7.view.menu.ShowableListMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;

/* renamed from: android.support.v7.widget.PopupMenu */
public class PopupMenu {
    private final View mAnchor;
    private final Context mContext;
    private OnTouchListener mDragListener;
    private final MenuBuilder mMenu;
    OnMenuItemClickListener mMenuItemClickListener;
    OnDismissListener mOnDismissListener;
    final MenuPopupHelper mPopup;

    /* renamed from: android.support.v7.widget.PopupMenu$OnDismissListener */
    public interface OnDismissListener {
        void onDismiss(PopupMenu popupMenu);
    }

    /* renamed from: android.support.v7.widget.PopupMenu$OnMenuItemClickListener */
    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public PopupMenu(@NonNull Context context, @NonNull View view) {
        Context context2 = context;
        View anchor = view;
        Context context3 = context2;
        View view2 = anchor;
        this(context2, anchor, 0);
    }

    public PopupMenu(@NonNull Context context, @NonNull View view, int i) {
        Context context2 = context;
        View anchor = view;
        int gravity = i;
        Context context3 = context2;
        View view2 = anchor;
        int i2 = gravity;
        this(context2, anchor, gravity, C0268R.attr.popupMenuStyle, 0);
    }

    public PopupMenu(@NonNull Context context, @NonNull View view, int i, @AttrRes int i2, @StyleRes int i3) {
        Context context2 = context;
        View anchor = view;
        int gravity = i;
        int popupStyleAttr = i2;
        int popupStyleRes = i3;
        Context context3 = context2;
        View view2 = anchor;
        int i4 = gravity;
        int i5 = popupStyleAttr;
        int i6 = popupStyleRes;
        this.mContext = context2;
        this.mAnchor = anchor;
        this.mMenu = new MenuBuilder(context2);
        MenuBuilder menuBuilder = this.mMenu;
        C03221 r0 = new Callback(this) {
            final /* synthetic */ PopupMenu this$0;

            {
                PopupMenu this$02 = r5;
                PopupMenu popupMenu = this$02;
                this.this$0 = this$02;
            }

            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                MenuItem item = menuItem;
                MenuBuilder menuBuilder2 = menuBuilder;
                MenuItem menuItem2 = item;
                if (this.this$0.mMenuItemClickListener == null) {
                    return false;
                }
                return this.this$0.mMenuItemClickListener.onMenuItemClick(item);
            }

            public void onMenuModeChange(MenuBuilder menuBuilder) {
                MenuBuilder menuBuilder2 = menuBuilder;
            }
        };
        menuBuilder.setCallback(r0);
        MenuPopupHelper menuPopupHelper = new MenuPopupHelper(context2, this.mMenu, anchor, false, popupStyleAttr, popupStyleRes);
        this.mPopup = menuPopupHelper;
        this.mPopup.setGravity(gravity);
        MenuPopupHelper menuPopupHelper2 = this.mPopup;
        C03232 r02 = new android.widget.PopupWindow.OnDismissListener(this) {
            final /* synthetic */ PopupMenu this$0;

            {
                PopupMenu this$02 = r5;
                PopupMenu popupMenu = this$02;
                this.this$0 = this$02;
            }

            public void onDismiss() {
                if (this.this$0.mOnDismissListener != null) {
                    this.this$0.mOnDismissListener.onDismiss(this.this$0);
                }
            }
        };
        menuPopupHelper2.setOnDismissListener(r02);
    }

    public void setGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        this.mPopup.setGravity(gravity);
    }

    public int getGravity() {
        return this.mPopup.getGravity();
    }

    @NonNull
    public OnTouchListener getDragToOpenListener() {
        if (this.mDragListener == null) {
            this.mDragListener = new ForwardingListener(this, this.mAnchor) {
                final /* synthetic */ PopupMenu this$0;

                {
                    PopupMenu this$02 = r8;
                    View src = r9;
                    PopupMenu popupMenu = this$02;
                    View view = src;
                    this.this$0 = this$02;
                }

                /* access modifiers changed from: protected */
                public boolean onForwardingStarted() {
                    this.this$0.show();
                    return true;
                }

                /* access modifiers changed from: protected */
                public boolean onForwardingStopped() {
                    this.this$0.dismiss();
                    return true;
                }

                public ShowableListMenu getPopup() {
                    return this.this$0.mPopup.getPopup();
                }
            };
        }
        return this.mDragListener;
    }

    @NonNull
    public Menu getMenu() {
        return this.mMenu;
    }

    @NonNull
    public MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.mContext);
    }

    public void inflate(@MenuRes int i) {
        int menuRes = i;
        int i2 = menuRes;
        getMenuInflater().inflate(menuRes, this.mMenu);
    }

    public void show() {
        this.mPopup.show();
    }

    public void dismiss() {
        this.mPopup.dismiss();
    }

    public void setOnMenuItemClickListener(@Nullable OnMenuItemClickListener onMenuItemClickListener) {
        OnMenuItemClickListener listener = onMenuItemClickListener;
        OnMenuItemClickListener onMenuItemClickListener2 = listener;
        this.mMenuItemClickListener = listener;
    }

    public void setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        OnDismissListener listener = onDismissListener;
        OnDismissListener onDismissListener2 = listener;
        this.mOnDismissListener = listener;
    }
}
