package android.support.p003v7.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.content.res.ConfigurationHelper;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.MenuBuilder.ItemInvoker;
import android.support.p003v7.view.menu.MenuView.ItemView;
import android.support.p003v7.widget.ActionMenuView.ActionMenuChildView;
import android.support.p003v7.widget.AppCompatTextView;
import android.support.p003v7.widget.ForwardingListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Toast;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.ActionMenuItemView */
public class ActionMenuItemView extends AppCompatTextView implements ItemView, OnClickListener, OnLongClickListener, ActionMenuChildView {
    private static final int MAX_ICON_SIZE = 32;
    private static final String TAG = "ActionMenuItemView";
    private boolean mAllowTextWithIcon;
    private boolean mExpandedFormat;
    private ForwardingListener mForwardingListener;
    private Drawable mIcon;
    MenuItemImpl mItemData;
    ItemInvoker mItemInvoker;
    private int mMaxIconSize;
    private int mMinWidth;
    PopupCallback mPopupCallback;
    private int mSavedPaddingLeft;
    private CharSequence mTitle;

    /* renamed from: android.support.v7.view.menu.ActionMenuItemView$ActionMenuItemForwardingListener */
    private class ActionMenuItemForwardingListener extends ForwardingListener {
        final /* synthetic */ ActionMenuItemView this$0;

        public ActionMenuItemForwardingListener(ActionMenuItemView actionMenuItemView) {
            ActionMenuItemView actionMenuItemView2 = actionMenuItemView;
            this.this$0 = actionMenuItemView2;
            super(actionMenuItemView2);
        }

        public ShowableListMenu getPopup() {
            if (this.this$0.mPopupCallback == null) {
                return null;
            }
            return this.this$0.mPopupCallback.getPopup();
        }

        /* access modifiers changed from: protected */
        public boolean onForwardingStarted() {
            if (this.this$0.mItemInvoker == null || !this.this$0.mItemInvoker.invokeItem(this.this$0.mItemData)) {
                return false;
            }
            ShowableListMenu popup = getPopup();
            return popup != null && popup.isShowing();
        }
    }

    /* renamed from: android.support.v7.view.menu.ActionMenuItemView$PopupCallback */
    public static abstract class PopupCallback {
        public abstract ShowableListMenu getPopup();

        public PopupCallback() {
        }
    }

    public ActionMenuItemView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyle = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyle;
        super(context2, attrs, defStyle);
        Resources res = context2.getResources();
        this.mAllowTextWithIcon = shouldAllowTextWithIcon();
        TypedArray a = context2.obtainStyledAttributes(attrs, C0268R.styleable.ActionMenuItemView, defStyle, 0);
        this.mMinWidth = a.getDimensionPixelSize(C0268R.styleable.ActionMenuItemView_android_minWidth, 0);
        a.recycle();
        float f = res.getDisplayMetrics().density;
        float f2 = f;
        this.mMaxIconSize = (int) ((32.0f * f) + 0.5f);
        setOnClickListener(this);
        setOnLongClickListener(this);
        this.mSavedPaddingLeft = -1;
        setSaveEnabled(false);
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        Configuration configuration2 = newConfig;
        super.onConfigurationChanged(newConfig);
        this.mAllowTextWithIcon = shouldAllowTextWithIcon();
        updateTextButtonVisibility();
    }

    private boolean shouldAllowTextWithIcon() {
        Configuration config = getContext().getResources().getConfiguration();
        int widthDp = ConfigurationHelper.getScreenWidthDp(getResources());
        return widthDp >= 480 || (widthDp >= 640 && ConfigurationHelper.getScreenHeightDp(getResources()) >= 480) || config.orientation == 2;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        int l = i;
        int t = i2;
        int r = i3;
        int b = i4;
        int i5 = l;
        int i6 = t;
        int i7 = r;
        int i8 = b;
        this.mSavedPaddingLeft = l;
        super.setPadding(l, t, r, b);
    }

    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public void initialize(MenuItemImpl menuItemImpl, int i) {
        MenuItemImpl itemData = menuItemImpl;
        MenuItemImpl menuItemImpl2 = itemData;
        int i2 = i;
        this.mItemData = itemData;
        setIcon(itemData.getIcon());
        setTitle(itemData.getTitleForItemView(this));
        setId(itemData.getItemId());
        setVisibility(!itemData.isVisible() ? 8 : 0);
        setEnabled(itemData.isEnabled());
        if (itemData.hasSubMenu() && this.mForwardingListener == null) {
            this.mForwardingListener = new ActionMenuItemForwardingListener(this);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent e = motionEvent;
        MotionEvent motionEvent2 = e;
        if (this.mItemData.hasSubMenu() && this.mForwardingListener != null && this.mForwardingListener.onTouch(this, e)) {
            return true;
        }
        return super.onTouchEvent(e);
    }

    public void onClick(View view) {
        View view2 = view;
        if (this.mItemInvoker != null) {
            boolean invokeItem = this.mItemInvoker.invokeItem(this.mItemData);
        }
    }

    public void setItemInvoker(ItemInvoker itemInvoker) {
        ItemInvoker invoker = itemInvoker;
        ItemInvoker itemInvoker2 = invoker;
        this.mItemInvoker = invoker;
    }

    public void setPopupCallback(PopupCallback popupCallback) {
        PopupCallback popupCallback2 = popupCallback;
        PopupCallback popupCallback3 = popupCallback2;
        this.mPopupCallback = popupCallback2;
    }

    public boolean prefersCondensedTitle() {
        return true;
    }

    public void setCheckable(boolean z) {
        boolean z2 = z;
    }

    public void setChecked(boolean z) {
        boolean z2 = z;
    }

    public void setExpandedFormat(boolean z) {
        boolean expandedFormat = z;
        if (this.mExpandedFormat != expandedFormat) {
            this.mExpandedFormat = expandedFormat;
            if (this.mItemData != null) {
                this.mItemData.actionFormatChanged();
            }
        }
    }

    private void updateTextButtonVisibility() {
        boolean z = (!TextUtils.isEmpty(this.mTitle)) & (this.mIcon == null || (this.mItemData.showsTextAsAction() && (this.mAllowTextWithIcon || this.mExpandedFormat)));
        boolean visible = z;
        setText(!z ? null : this.mTitle);
    }

    public void setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        this.mIcon = icon;
        if (icon != null) {
            int width = icon.getIntrinsicWidth();
            int height = icon.getIntrinsicHeight();
            if (width > this.mMaxIconSize) {
                float scale = ((float) this.mMaxIconSize) / ((float) width);
                width = this.mMaxIconSize;
                height = (int) (((float) height) * scale);
            }
            if (height > this.mMaxIconSize) {
                float scale2 = ((float) this.mMaxIconSize) / ((float) height);
                height = this.mMaxIconSize;
                width = (int) (((float) width) * scale2);
            }
            icon.setBounds(0, 0, width, height);
        }
        setCompoundDrawables(icon, null, null, null);
        updateTextButtonVisibility();
    }

    public boolean hasText() {
        return !TextUtils.isEmpty(getText());
    }

    public void setShortcut(boolean z, char c) {
        boolean z2 = z;
        char c2 = c;
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mTitle = title;
        setContentDescription(this.mTitle);
        updateTextButtonVisibility();
    }

    public boolean showsIcon() {
        return true;
    }

    public boolean needsDividerBefore() {
        return hasText() && this.mItemData.getIcon() == null;
    }

    public boolean needsDividerAfter() {
        return hasText();
    }

    public boolean onLongClick(View view) {
        View v = view;
        View view2 = v;
        if (hasText()) {
            return false;
        }
        int[] screenPos = new int[2];
        Rect displayFrame = new Rect();
        getLocationOnScreen(screenPos);
        getWindowVisibleDisplayFrame(displayFrame);
        Context context = getContext();
        int width = getWidth();
        int height = getHeight();
        int midy = screenPos[1] + (height / 2);
        int referenceX = screenPos[0] + (width / 2);
        if (ViewCompat.getLayoutDirection(v) == 0) {
            int i = context.getResources().getDisplayMetrics().widthPixels;
            int i2 = i;
            referenceX = i - referenceX;
        }
        Toast makeText = Toast.makeText(context, this.mItemData.getTitle(), 0);
        Toast cheatSheet = makeText;
        if (midy >= displayFrame.height()) {
            makeText.setGravity(81, 0, height);
        } else {
            makeText.setGravity(8388661, referenceX, (screenPos[1] + height) - displayFrame.top);
        }
        cheatSheet.show();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int min;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        boolean hasText = hasText();
        boolean textVisible = hasText;
        if (hasText && this.mSavedPaddingLeft >= 0) {
            super.setPadding(this.mSavedPaddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int oldMeasuredWidth = getMeasuredWidth();
        if (widthMode != Integer.MIN_VALUE) {
            min = this.mMinWidth;
        } else {
            min = Math.min(widthSize, this.mMinWidth);
        }
        int targetWidth = min;
        if (widthMode != 1073741824 && this.mMinWidth > 0 && oldMeasuredWidth < targetWidth) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(targetWidth, 1073741824), heightMeasureSpec);
        }
        if (!textVisible && this.mIcon != null) {
            int w = getMeasuredWidth();
            int width = this.mIcon.getBounds().width();
            int i5 = width;
            super.setPadding((w - width) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2 = parcelable;
        super.onRestoreInstanceState(null);
    }
}
