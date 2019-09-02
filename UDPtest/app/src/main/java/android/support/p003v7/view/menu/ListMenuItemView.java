package android.support.p003v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.menu.MenuView.ItemView;
import android.support.p003v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.ListMenuItemView */
public class ListMenuItemView extends LinearLayout implements ItemView {
    private static final String TAG = "ListMenuItemView";
    private Drawable mBackground;
    private CheckBox mCheckBox;
    private boolean mForceShowIcon;
    private ImageView mIconView;
    private LayoutInflater mInflater;
    private MenuItemImpl mItemData;
    private int mMenuType;
    private boolean mPreserveIconSpacing;
    private RadioButton mRadioButton;
    private TextView mShortcutView;
    private Drawable mSubMenuArrow;
    private ImageView mSubMenuArrowView;
    private int mTextAppearance;
    private Context mTextAppearanceContext;
    private TextView mTitleView;

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, C0268R.attr.listMenuViewStyle);
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, C0268R.styleable.MenuView, defStyleAttr, 0);
        this.mBackground = a.getDrawable(C0268R.styleable.MenuView_android_itemBackground);
        this.mTextAppearance = a.getResourceId(C0268R.styleable.MenuView_android_itemTextAppearance, -1);
        this.mPreserveIconSpacing = a.getBoolean(C0268R.styleable.MenuView_preserveIconSpacing, false);
        this.mTextAppearanceContext = context2;
        this.mSubMenuArrow = a.getDrawable(C0268R.styleable.MenuView_subMenuArrow);
        a.recycle();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        ViewCompat.setBackground(this, this.mBackground);
        this.mTitleView = (TextView) findViewById(C0268R.C0270id.title);
        if (this.mTextAppearance != -1) {
            this.mTitleView.setTextAppearance(this.mTextAppearanceContext, this.mTextAppearance);
        }
        this.mShortcutView = (TextView) findViewById(C0268R.C0270id.shortcut);
        this.mSubMenuArrowView = (ImageView) findViewById(C0268R.C0270id.submenuarrow);
        if (this.mSubMenuArrowView != null) {
            this.mSubMenuArrowView.setImageDrawable(this.mSubMenuArrow);
        }
    }

    public void initialize(MenuItemImpl menuItemImpl, int i) {
        MenuItemImpl itemData = menuItemImpl;
        int menuType = i;
        MenuItemImpl menuItemImpl2 = itemData;
        int i2 = menuType;
        this.mItemData = itemData;
        this.mMenuType = menuType;
        setVisibility(!itemData.isVisible() ? 8 : 0);
        setTitle(itemData.getTitleForItemView(this));
        setCheckable(itemData.isCheckable());
        setShortcut(itemData.shouldShowShortcut(), itemData.getShortcut());
        setIcon(itemData.getIcon());
        setEnabled(itemData.isEnabled());
        setSubMenuArrowVisible(itemData.hasSubMenu());
    }

    public void setForceShowIcon(boolean z) {
        boolean forceShow = z;
        this.mForceShowIcon = forceShow;
        this.mPreserveIconSpacing = forceShow;
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        if (title != null) {
            this.mTitleView.setText(title);
            if (this.mTitleView.getVisibility() != 0) {
                this.mTitleView.setVisibility(0);
            }
        } else if (this.mTitleView.getVisibility() != 8) {
            this.mTitleView.setVisibility(8);
        }
    }

    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public void setCheckable(boolean z) {
        CompoundButton compoundButton;
        CompoundButton otherCompoundButton;
        boolean checkable = z;
        if (checkable || this.mRadioButton != null || this.mCheckBox != null) {
            if (!this.mItemData.isExclusiveCheckable()) {
                if (this.mCheckBox == null) {
                    insertCheckBox();
                }
                compoundButton = this.mCheckBox;
                otherCompoundButton = this.mRadioButton;
            } else {
                if (this.mRadioButton == null) {
                    insertRadioButton();
                }
                compoundButton = this.mRadioButton;
                otherCompoundButton = this.mCheckBox;
            }
            if (!checkable) {
                if (this.mCheckBox != null) {
                    this.mCheckBox.setVisibility(8);
                }
                if (this.mRadioButton != null) {
                    this.mRadioButton.setVisibility(8);
                }
            } else {
                compoundButton.setChecked(this.mItemData.isChecked());
                int newVisibility = !checkable ? 8 : 0;
                if (compoundButton.getVisibility() != newVisibility) {
                    compoundButton.setVisibility(newVisibility);
                }
                if (!(otherCompoundButton == null || otherCompoundButton.getVisibility() == 8)) {
                    otherCompoundButton.setVisibility(8);
                }
            }
        }
    }

    public void setChecked(boolean z) {
        CompoundButton compoundButton;
        boolean checked = z;
        if (!this.mItemData.isExclusiveCheckable()) {
            if (this.mCheckBox == null) {
                insertCheckBox();
            }
            compoundButton = this.mCheckBox;
        } else {
            if (this.mRadioButton == null) {
                insertRadioButton();
            }
            compoundButton = this.mRadioButton;
        }
        compoundButton.setChecked(checked);
    }

    private void setSubMenuArrowVisible(boolean z) {
        boolean hasSubmenu = z;
        if (this.mSubMenuArrowView != null) {
            this.mSubMenuArrowView.setVisibility(!hasSubmenu ? 8 : 0);
        }
    }

    public void setShortcut(boolean z, char c) {
        char c2 = c;
        int newVisibility = (z && this.mItemData.shouldShowShortcut()) ? 0 : 8;
        if (newVisibility == 0) {
            this.mShortcutView.setText(this.mItemData.getShortcutLabel());
        }
        if (this.mShortcutView.getVisibility() != newVisibility) {
            this.mShortcutView.setVisibility(newVisibility);
        }
    }

    public void setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        boolean showIcon = this.mItemData.shouldShowIcon() || this.mForceShowIcon;
        if (showIcon || this.mPreserveIconSpacing) {
            if (this.mIconView != null || icon != null || this.mPreserveIconSpacing) {
                if (this.mIconView == null) {
                    insertIconView();
                }
                if (icon == null && !this.mPreserveIconSpacing) {
                    this.mIconView.setVisibility(8);
                } else {
                    this.mIconView.setImageDrawable(!showIcon ? null : icon);
                    if (this.mIconView.getVisibility() != 0) {
                        this.mIconView.setVisibility(0);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        if (this.mIconView != null && this.mPreserveIconSpacing) {
            LayoutParams lp = getLayoutParams();
            LinearLayout.LayoutParams iconLp = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
            if (lp.height > 0 && iconLp.width <= 0) {
                iconLp.width = lp.height;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void insertIconView() {
        this.mIconView = (ImageView) getInflater().inflate(C0268R.layout.abc_list_menu_item_icon, this, false);
        addView(this.mIconView, 0);
    }

    private void insertRadioButton() {
        this.mRadioButton = (RadioButton) getInflater().inflate(C0268R.layout.abc_list_menu_item_radio, this, false);
        addView(this.mRadioButton);
    }

    private void insertCheckBox() {
        this.mCheckBox = (CheckBox) getInflater().inflate(C0268R.layout.abc_list_menu_item_checkbox, this, false);
        addView(this.mCheckBox);
    }

    public boolean prefersCondensedTitle() {
        return false;
    }

    public boolean showsIcon() {
        return this.mForceShowIcon;
    }

    private LayoutInflater getInflater() {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getContext());
        }
        return this.mInflater;
    }
}
