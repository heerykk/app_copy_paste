package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.C0001R;
import android.support.p000v4.content.res.ResourcesCompat;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.view.AccessibilityDelegateCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.widget.TextViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.support.p003v7.view.menu.MenuView.ItemView;
import android.support.p003v7.widget.LinearLayoutCompat.LayoutParams;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;

@RestrictTo({Scope.LIBRARY_GROUP})
public class NavigationMenuItemView extends ForegroundLinearLayout implements ItemView {
    private static final int[] CHECKED_STATE_SET;
    private final AccessibilityDelegateCompat mAccessibilityDelegate;
    private FrameLayout mActionArea;
    boolean mCheckable;
    private Drawable mEmptyDrawable;
    private boolean mHasIconTintList;
    private final int mIconSize;
    private ColorStateList mIconTintList;
    private MenuItemImpl mItemData;
    private boolean mNeedsEmptyIcon;
    private final CheckedTextView mTextView;

    static {
        int[] iArr = new int[1];
        iArr[0] = 16842912;
        CHECKED_STATE_SET = iArr;
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mAccessibilityDelegate = new AccessibilityDelegateCompat(this) {
            final /* synthetic */ NavigationMenuItemView this$0;

            {
                NavigationMenuItemView this$02 = r5;
                NavigationMenuItemView navigationMenuItemView = this$02;
                this.this$0 = this$02;
            }

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                View host = view;
                AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
                View view2 = host;
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setCheckable(this.this$0.mCheckable);
            }
        };
        setOrientation(0);
        LayoutInflater from = LayoutInflater.from(context2);
        View inflate = from.inflate(C0001R.layout.design_navigation_menu_item, this, true);
        this.mIconSize = context2.getResources().getDimensionPixelSize(C0001R.dimen.design_navigation_icon_size);
        this.mTextView = (CheckedTextView) findViewById(C0001R.C0003id.design_menu_item_text);
        this.mTextView.setDuplicateParentStateEnabled(true);
        ViewCompat.setAccessibilityDelegate(this.mTextView, this.mAccessibilityDelegate);
    }

    public NavigationMenuItemView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void initialize(MenuItemImpl menuItemImpl, int i) {
        MenuItemImpl itemData = menuItemImpl;
        MenuItemImpl menuItemImpl2 = itemData;
        int i2 = i;
        this.mItemData = itemData;
        setVisibility(!itemData.isVisible() ? 8 : 0);
        if (getBackground() == null) {
            ViewCompat.setBackground(this, createDefaultBackground());
        }
        setCheckable(itemData.isCheckable());
        setChecked(itemData.isChecked());
        setEnabled(itemData.isEnabled());
        setTitle(itemData.getTitle());
        setIcon(itemData.getIcon());
        setActionView(itemData.getActionView());
        adjustAppearance();
    }

    private boolean shouldExpandActionArea() {
        return this.mItemData.getTitle() == null && this.mItemData.getIcon() == null && this.mItemData.getActionView() != null;
    }

    private void adjustAppearance() {
        if (!shouldExpandActionArea()) {
            this.mTextView.setVisibility(0);
            if (this.mActionArea != null) {
                LayoutParams layoutParams = (LayoutParams) this.mActionArea.getLayoutParams();
                LayoutParams params = layoutParams;
                layoutParams.width = -2;
                this.mActionArea.setLayoutParams(params);
                return;
            }
            return;
        }
        this.mTextView.setVisibility(8);
        if (this.mActionArea != null) {
            LayoutParams layoutParams2 = (LayoutParams) this.mActionArea.getLayoutParams();
            LayoutParams params2 = layoutParams2;
            layoutParams2.width = -1;
            this.mActionArea.setLayoutParams(params2);
        }
    }

    public void recycle() {
        if (this.mActionArea != null) {
            this.mActionArea.removeAllViews();
        }
        this.mTextView.setCompoundDrawables(null, null, null, null);
    }

    private void setActionView(View view) {
        View actionView = view;
        View view2 = actionView;
        if (actionView != null) {
            if (this.mActionArea == null) {
                this.mActionArea = (FrameLayout) ((ViewStub) findViewById(C0001R.C0003id.design_menu_item_action_area_stub)).inflate();
            }
            this.mActionArea.removeAllViews();
            this.mActionArea.addView(actionView);
        }
    }

    private StateListDrawable createDefaultBackground() {
        TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(C0268R.attr.colorControlHighlight, value, true)) {
            return null;
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        StateListDrawable drawable = stateListDrawable;
        stateListDrawable.addState(CHECKED_STATE_SET, new ColorDrawable(value.data));
        drawable.addState(EMPTY_STATE_SET, new ColorDrawable(0));
        return drawable;
    }

    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mTextView.setText(title);
    }

    public void setCheckable(boolean z) {
        boolean checkable = z;
        refreshDrawableState();
        if (this.mCheckable != checkable) {
            this.mCheckable = checkable;
            this.mAccessibilityDelegate.sendAccessibilityEvent(this.mTextView, 2048);
        }
    }

    public void setChecked(boolean z) {
        boolean checked = z;
        refreshDrawableState();
        this.mTextView.setChecked(checked);
    }

    public void setShortcut(boolean z, char c) {
        boolean z2 = z;
        char c2 = c;
    }

    public void setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable icon2 = icon;
        if (icon != null) {
            if (this.mHasIconTintList) {
                ConstantState constantState = icon.getConstantState();
                Drawable mutate = DrawableCompat.wrap(constantState != null ? constantState.newDrawable() : icon).mutate();
                icon2 = mutate;
                DrawableCompat.setTintList(mutate, this.mIconTintList);
            }
            icon2.setBounds(0, 0, this.mIconSize, this.mIconSize);
        } else if (this.mNeedsEmptyIcon) {
            if (this.mEmptyDrawable == null) {
                this.mEmptyDrawable = ResourcesCompat.getDrawable(getResources(), C0001R.C0002drawable.navigation_empty_icon, getContext().getTheme());
                if (this.mEmptyDrawable != null) {
                    this.mEmptyDrawable.setBounds(0, 0, this.mIconSize, this.mIconSize);
                }
            }
            icon2 = this.mEmptyDrawable;
        }
        TextViewCompat.setCompoundDrawablesRelative(this.mTextView, icon2, null, null, null);
    }

    public boolean prefersCondensedTitle() {
        return false;
    }

    public boolean showsIcon() {
        return true;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        int extraSpace = i;
        int i2 = extraSpace;
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (this.mItemData != null && this.mItemData.isCheckable() && this.mItemData.isChecked()) {
            int[] mergeDrawableStates = mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    /* access modifiers changed from: 0000 */
    public void setIconTintList(ColorStateList colorStateList) {
        ColorStateList tintList = colorStateList;
        ColorStateList colorStateList2 = tintList;
        this.mIconTintList = tintList;
        this.mHasIconTintList = this.mIconTintList != null;
        if (this.mItemData != null) {
            setIcon(this.mItemData.getIcon());
        }
    }

    public void setTextAppearance(int i) {
        int textAppearance = i;
        int i2 = textAppearance;
        TextViewCompat.setTextAppearance(this.mTextView, textAppearance);
    }

    public void setTextColor(ColorStateList colorStateList) {
        ColorStateList colors = colorStateList;
        ColorStateList colorStateList2 = colors;
        this.mTextView.setTextColor(colors);
    }

    public void setNeedsEmptyIcon(boolean z) {
        this.mNeedsEmptyIcon = z;
    }
}
