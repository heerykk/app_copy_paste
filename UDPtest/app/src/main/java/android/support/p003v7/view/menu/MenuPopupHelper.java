package android.support.p003v7.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.MenuPresenter.Callback;
import android.view.View;
import android.widget.PopupWindow.OnDismissListener;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.MenuPopupHelper */
public class MenuPopupHelper implements MenuHelper {
    private static final int TOUCH_EPICENTER_SIZE_DP = 48;
    private View mAnchorView;
    private final Context mContext;
    private int mDropDownGravity;
    private boolean mForceShowIcon;
    private final OnDismissListener mInternalOnDismissListener;
    private final MenuBuilder mMenu;
    private OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private MenuPopup mPopup;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private Callback mPresenterCallback;

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view, boolean z, @AttrRes int i, @StyleRes int i2) {
        Context context2 = context;
        MenuBuilder menu = menuBuilder;
        View anchorView = view;
        int popupStyleAttr = i;
        int popupStyleRes = i2;
        Context context3 = context2;
        MenuBuilder menuBuilder2 = menu;
        View view2 = anchorView;
        boolean overflowOnly = z;
        int i3 = popupStyleAttr;
        int i4 = popupStyleRes;
        this.mDropDownGravity = GravityCompat.START;
        this.mInternalOnDismissListener = new OnDismissListener(this) {
            final /* synthetic */ MenuPopupHelper this$0;

            {
                MenuPopupHelper this$02 = r5;
                MenuPopupHelper menuPopupHelper = this$02;
                this.this$0 = this$02;
            }

            public void onDismiss() {
                this.this$0.onDismiss();
            }
        };
        this.mContext = context2;
        this.mMenu = menu;
        this.mAnchorView = anchorView;
        this.mOverflowOnly = overflowOnly;
        this.mPopupStyleAttr = popupStyleAttr;
        this.mPopupStyleRes = popupStyleRes;
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder) {
        Context context2 = context;
        MenuBuilder menu = menuBuilder;
        Context context3 = context2;
        MenuBuilder menuBuilder2 = menu;
        this(context2, menu, null, false, C0268R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view) {
        Context context2 = context;
        MenuBuilder menu = menuBuilder;
        View anchorView = view;
        Context context3 = context2;
        MenuBuilder menuBuilder2 = menu;
        View view2 = anchorView;
        this(context2, menu, anchorView, false, C0268R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view, boolean z, @AttrRes int i) {
        Context context2 = context;
        MenuBuilder menu = menuBuilder;
        View anchorView = view;
        int popupStyleAttr = i;
        Context context3 = context2;
        MenuBuilder menuBuilder2 = menu;
        View view2 = anchorView;
        int i2 = popupStyleAttr;
        this(context2, menu, anchorView, z, popupStyleAttr, 0);
    }

    public void setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        OnDismissListener listener = onDismissListener;
        OnDismissListener onDismissListener2 = listener;
        this.mOnDismissListener = listener;
    }

    public void setAnchorView(@NonNull View view) {
        View anchor = view;
        View view2 = anchor;
        this.mAnchorView = anchor;
    }

    public void setForceShowIcon(boolean z) {
        boolean forceShowIcon = z;
        this.mForceShowIcon = forceShowIcon;
        if (this.mPopup != null) {
            this.mPopup.setForceShowIcon(forceShowIcon);
        }
    }

    public void setGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        this.mDropDownGravity = gravity;
    }

    public int getGravity() {
        return this.mDropDownGravity;
    }

    public void show() {
        if (!tryShow()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public void show(int i, int i2) {
        int x = i;
        int y = i2;
        int i3 = x;
        int i4 = y;
        if (!tryShow(x, y)) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    @NonNull
    public MenuPopup getPopup() {
        if (this.mPopup == null) {
            this.mPopup = createPopup();
        }
        return this.mPopup;
    }

    public boolean tryShow() {
        if (isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        showPopup(0, 0, false, false);
        return true;
    }

    public boolean tryShow(int i, int i2) {
        int x = i;
        int y = i2;
        int i3 = x;
        int i4 = y;
        if (isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        showPopup(x, y, true, true);
        return true;
    }

    /* JADX WARNING: type inference failed for: r33v0 */
    /* JADX WARNING: type inference failed for: r33v1 */
    /* JADX WARNING: type inference failed for: r9v8 */
    /* JADX WARNING: type inference failed for: r41v0, types: [android.support.v7.view.menu.MenuPopup] */
    /* JADX WARNING: type inference failed for: r9v9 */
    /* JADX WARNING: type inference failed for: r43v0, types: [android.support.v7.view.menu.MenuPopup] */
    /* JADX WARNING: type inference failed for: r9v10 */
    /* JADX WARNING: type inference failed for: r45v0, types: [android.support.v7.view.menu.MenuPopup] */
    /* JADX WARNING: type inference failed for: r9v11 */
    /* JADX WARNING: type inference failed for: r47v0, types: [android.support.v7.view.menu.MenuPopup] */
    /* JADX WARNING: type inference failed for: r9v12 */
    /* JADX WARNING: type inference failed for: r50v0, types: [android.support.v7.view.menu.MenuPopup] */
    /* JADX WARNING: type inference failed for: r9v13 */
    /* JADX WARNING: type inference failed for: r52v0 */
    /* JADX WARNING: type inference failed for: r0v15, types: [android.support.v7.view.menu.MenuPopup] */
    /* JADX WARNING: type inference failed for: r53v0, types: [android.support.v7.view.menu.MenuPopup] */
    /* JADX WARNING: type inference failed for: r33v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 15 */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.support.p003v7.view.menu.MenuPopup createPopup() {
        /*
            r54 = this;
            r7 = r54
            r8 = r7
            android.content.Context r9 = r7.mContext
            java.lang.String r10 = "window"
            r11 = r10
            java.lang.Object r9 = r9.getSystemService(r11)
            android.view.WindowManager r9 = (android.view.WindowManager) r9
            r12 = r9
            android.view.Display r9 = r9.getDefaultDisplay()
            r13 = r9
            android.graphics.Point r9 = new android.graphics.Point
            r9.<init>()
            r14 = r9
            int r15 = android.os.Build.VERSION.SDK_INT
            r16 = 17
            r0 = r16
            if (r15 >= r0) goto L_0x00ed
            int r15 = android.os.Build.VERSION.SDK_INT
            r16 = 13
            r0 = r16
            if (r15 >= r0) goto L_0x00f6
            r9 = r9
            int r19 = r13.getWidth()
            int r20 = r13.getHeight()
            r0 = r19
            r1 = r20
            r9.set(r0, r1)
        L_0x003b:
            int r15 = r14.x
            int r0 = r14.y
            r19 = r0
            r0 = r19
            int r15 = java.lang.Math.min(r15, r0)
            r21 = r15
            android.content.Context r9 = r7.mContext
            android.content.res.Resources r9 = r9.getResources()
            int r19 = android.support.p003v7.appcompat.C0268R.dimen.abc_cascading_menus_min_smallest_width
            r0 = r19
            int r15 = r9.getDimensionPixelSize(r0)
            r22 = r15
            r0 = r21
            r1 = r22
            if (r0 >= r1) goto L_0x00ff
            r15 = 0
        L_0x0060:
            r23 = r15
            r16 = 0
            r0 = r23
            r1 = r16
            if (r0 != r1) goto L_0x0102
            android.support.v7.view.menu.StandardMenuPopup r9 = new android.support.v7.view.menu.StandardMenuPopup
            android.content.Context r0 = r7.mContext
            r24 = r0
            android.support.v7.view.menu.MenuBuilder r0 = r7.mMenu
            r25 = r0
            android.view.View r0 = r7.mAnchorView
            r34 = r0
            int r0 = r7.mPopupStyleAttr
            r27 = r0
            int r0 = r7.mPopupStyleRes
            r29 = r0
            boolean r0 = r7.mOverflowOnly
            r35 = r0
            r36 = r35
            r37 = r24
            r38 = r25
            r39 = r34
            r40 = r36
            r0 = r9
            r1 = r37
            r2 = r38
            r3 = r39
            r4 = r27
            r5 = r29
            r6 = r40
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r33 = r9
        L_0x00a0:
            r9 = r33
            android.support.v7.view.menu.MenuBuilder r10 = r7.mMenu
            r41 = r9
            r42 = r10
            r41.addMenu(r42)
            r9 = r33
            android.widget.PopupWindow$OnDismissListener r10 = r7.mInternalOnDismissListener
            r43 = r9
            r44 = r10
            r43.setOnDismissListener(r44)
            r9 = r33
            android.view.View r10 = r7.mAnchorView
            r45 = r9
            r46 = r10
            r45.setAnchorView(r46)
            r9 = r33
            android.support.v7.view.menu.MenuPresenter$Callback r10 = r7.mPresenterCallback
            r47 = r9
            r48 = r10
            r47.setCallback(r48)
            r9 = r33
            boolean r0 = r7.mForceShowIcon
            r49 = r0
            r19 = r49
            r50 = r9
            r51 = r19
            r50.setForceShowIcon(r51)
            r9 = r33
            int r0 = r7.mDropDownGravity
            r19 = r0
            r52 = r9
            r0 = r52
            r1 = r19
            r0.setGravity(r1)
            r53 = r33
            return r53
        L_0x00ed:
            r17 = r14
            r0 = r17
            r13.getRealSize(r0)
            goto L_0x003b
        L_0x00f6:
            r18 = r14
            r0 = r18
            r13.getSize(r0)
            goto L_0x003b
        L_0x00ff:
            r15 = 1
            goto L_0x0060
        L_0x0102:
            android.support.v7.view.menu.CascadingMenuPopup r9 = new android.support.v7.view.menu.CascadingMenuPopup
            android.content.Context r0 = r7.mContext
            r24 = r0
            android.view.View r0 = r7.mAnchorView
            r25 = r0
            int r0 = r7.mPopupStyleAttr
            r26 = r0
            int r0 = r7.mPopupStyleRes
            r27 = r0
            boolean r0 = r7.mOverflowOnly
            r28 = r0
            r29 = r28
            r30 = r24
            r31 = r25
            r32 = r29
            r0 = r9
            r1 = r30
            r2 = r31
            r3 = r26
            r4 = r27
            r5 = r32
            r0.<init>(r1, r2, r3, r4, r5)
            r33 = r9
            goto L_0x00a0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.view.menu.MenuPopupHelper.createPopup():android.support.v7.view.menu.MenuPopup");
    }

    private void showPopup(int i, int i2, boolean z, boolean z2) {
        int xOffset = i;
        int yOffset = i2;
        int xOffset2 = xOffset;
        int i3 = yOffset;
        boolean useOffsets = z;
        boolean showTitle = z2;
        MenuPopup popup = getPopup();
        MenuPopup popup2 = popup;
        popup.setShowTitle(showTitle);
        if (useOffsets) {
            int absoluteGravity = GravityCompat.getAbsoluteGravity(this.mDropDownGravity, ViewCompat.getLayoutDirection(this.mAnchorView)) & 7;
            int i4 = absoluteGravity;
            if (absoluteGravity == 5) {
                xOffset2 = xOffset - this.mAnchorView.getWidth();
            }
            popup2.setHorizontalOffset(xOffset2);
            popup2.setVerticalOffset(yOffset);
            int i5 = (int) ((48.0f * this.mContext.getResources().getDisplayMetrics().density) / 2.0f);
            int i6 = i5;
            popup2.setEpicenterBounds(new Rect(xOffset2 - i5, yOffset - i5, xOffset2 + i5, yOffset + i5));
        }
        popup2.show();
    }

    public void dismiss() {
        if (isShowing()) {
            this.mPopup.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onDismiss() {
        this.mPopup = null;
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.onDismiss();
        }
    }

    public boolean isShowing() {
        return this.mPopup != null && this.mPopup.isShowing();
    }

    public void setPresenterCallback(@Nullable Callback callback) {
        Callback cb = callback;
        Callback callback2 = cb;
        this.mPresenterCallback = cb;
        if (this.mPopup != null) {
            this.mPopup.setCallback(cb);
        }
    }
}
