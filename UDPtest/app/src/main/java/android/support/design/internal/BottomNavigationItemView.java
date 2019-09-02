package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.C0001R;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.view.PointerIconCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.support.p003v7.view.menu.MenuView.ItemView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

@RestrictTo({Scope.LIBRARY_GROUP})
public class BottomNavigationItemView extends FrameLayout implements ItemView {
    private static final int[] CHECKED_STATE_SET;
    public static final int INVALID_ITEM_POSITION = -1;
    private final int mDefaultMargin;
    private ImageView mIcon;
    private ColorStateList mIconTint;
    private MenuItemImpl mItemData;
    private int mItemPosition;
    private final TextView mLargeLabel;
    private final float mScaleDownFactor;
    private final float mScaleUpFactor;
    private final int mShiftAmount;
    private boolean mShiftingMode;
    private final TextView mSmallLabel;

    static {
        int[] iArr = new int[1];
        iArr[0] = 16842912;
        CHECKED_STATE_SET = iArr;
    }

    public BottomNavigationItemView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mItemPosition = -1;
        Resources resources = getResources();
        Resources res = resources;
        int inactiveLabelSize = resources.getDimensionPixelSize(C0001R.dimen.design_bottom_navigation_text_size);
        int dimensionPixelSize = res.getDimensionPixelSize(C0001R.dimen.design_bottom_navigation_active_text_size);
        int i3 = dimensionPixelSize;
        this.mDefaultMargin = res.getDimensionPixelSize(C0001R.dimen.design_bottom_navigation_margin);
        this.mShiftAmount = inactiveLabelSize - dimensionPixelSize;
        this.mScaleUpFactor = (1.0f * ((float) dimensionPixelSize)) / ((float) inactiveLabelSize);
        this.mScaleDownFactor = (1.0f * ((float) inactiveLabelSize)) / ((float) dimensionPixelSize);
        View inflate = LayoutInflater.from(context2).inflate(C0001R.layout.design_bottom_navigation_item, this, true);
        setBackgroundResource(C0001R.C0002drawable.design_bottom_navigation_item_background);
        this.mIcon = (ImageView) findViewById(C0001R.C0003id.icon);
        this.mSmallLabel = (TextView) findViewById(C0001R.C0003id.smallLabel);
        this.mLargeLabel = (TextView) findViewById(C0001R.C0003id.largeLabel);
    }

    public BottomNavigationItemView(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public BottomNavigationItemView(@NonNull Context context, AttributeSet attributeSet) {
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
        setCheckable(itemData.isCheckable());
        setChecked(itemData.isChecked());
        setEnabled(itemData.isEnabled());
        setIcon(itemData.getIcon());
        setTitle(itemData.getTitle());
        setId(itemData.getItemId());
    }

    public void setItemPosition(int i) {
        int position = i;
        int i2 = position;
        this.mItemPosition = position;
    }

    public int getItemPosition() {
        return this.mItemPosition;
    }

    public void setShiftingMode(boolean z) {
        this.mShiftingMode = z;
    }

    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mSmallLabel.setText(title);
        this.mLargeLabel.setText(title);
    }

    public void setCheckable(boolean z) {
        boolean z2 = z;
        refreshDrawableState();
    }

    public void setChecked(boolean z) {
        boolean checked = z;
        ViewCompat.setPivotX(this.mLargeLabel, (float) (this.mLargeLabel.getWidth() / 2));
        ViewCompat.setPivotY(this.mLargeLabel, (float) this.mLargeLabel.getBaseline());
        ViewCompat.setPivotX(this.mSmallLabel, (float) (this.mSmallLabel.getWidth() / 2));
        ViewCompat.setPivotY(this.mSmallLabel, (float) this.mSmallLabel.getBaseline());
        if (this.mShiftingMode) {
            if (!checked) {
                LayoutParams layoutParams = (LayoutParams) this.mIcon.getLayoutParams();
                LayoutParams iconParams = layoutParams;
                layoutParams.gravity = 17;
                iconParams.topMargin = this.mDefaultMargin;
                this.mIcon.setLayoutParams(iconParams);
                this.mLargeLabel.setVisibility(4);
                ViewCompat.setScaleX(this.mLargeLabel, 0.5f);
                ViewCompat.setScaleY(this.mLargeLabel, 0.5f);
            } else {
                LayoutParams layoutParams2 = (LayoutParams) this.mIcon.getLayoutParams();
                LayoutParams iconParams2 = layoutParams2;
                layoutParams2.gravity = 49;
                iconParams2.topMargin = this.mDefaultMargin;
                this.mIcon.setLayoutParams(iconParams2);
                this.mLargeLabel.setVisibility(0);
                ViewCompat.setScaleX(this.mLargeLabel, 1.0f);
                ViewCompat.setScaleY(this.mLargeLabel, 1.0f);
            }
            this.mSmallLabel.setVisibility(4);
        } else if (!checked) {
            LayoutParams layoutParams3 = (LayoutParams) this.mIcon.getLayoutParams();
            LayoutParams iconParams3 = layoutParams3;
            layoutParams3.gravity = 49;
            iconParams3.topMargin = this.mDefaultMargin;
            this.mIcon.setLayoutParams(iconParams3);
            this.mLargeLabel.setVisibility(4);
            this.mSmallLabel.setVisibility(0);
            ViewCompat.setScaleX(this.mLargeLabel, this.mScaleDownFactor);
            ViewCompat.setScaleY(this.mLargeLabel, this.mScaleDownFactor);
            ViewCompat.setScaleX(this.mSmallLabel, 1.0f);
            ViewCompat.setScaleY(this.mSmallLabel, 1.0f);
        } else {
            LayoutParams layoutParams4 = (LayoutParams) this.mIcon.getLayoutParams();
            LayoutParams iconParams4 = layoutParams4;
            layoutParams4.gravity = 49;
            iconParams4.topMargin = this.mDefaultMargin + this.mShiftAmount;
            this.mIcon.setLayoutParams(iconParams4);
            this.mLargeLabel.setVisibility(0);
            this.mSmallLabel.setVisibility(4);
            ViewCompat.setScaleX(this.mLargeLabel, 1.0f);
            ViewCompat.setScaleY(this.mLargeLabel, 1.0f);
            ViewCompat.setScaleX(this.mSmallLabel, this.mScaleUpFactor);
            ViewCompat.setScaleY(this.mSmallLabel, this.mScaleUpFactor);
        }
        refreshDrawableState();
    }

    public void setEnabled(boolean z) {
        boolean enabled = z;
        super.setEnabled(enabled);
        this.mSmallLabel.setEnabled(enabled);
        this.mLargeLabel.setEnabled(enabled);
        this.mIcon.setEnabled(enabled);
        if (!enabled) {
            ViewCompat.setPointerIcon(this, null);
        } else {
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND));
        }
    }

    public int[] onCreateDrawableState(int i) {
        int extraSpace = i;
        int i2 = extraSpace;
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (this.mItemData != null && this.mItemData.isCheckable() && this.mItemData.isChecked()) {
            int[] mergeDrawableStates = mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    public void setShortcut(boolean z, char c) {
        boolean z2 = z;
        char c2 = c;
    }

    public void setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable icon2 = icon;
        if (icon != null) {
            ConstantState constantState = icon.getConstantState();
            Drawable mutate = DrawableCompat.wrap(constantState != null ? constantState.newDrawable() : icon).mutate();
            icon2 = mutate;
            DrawableCompat.setTintList(mutate, this.mIconTint);
        }
        this.mIcon.setImageDrawable(icon2);
    }

    public boolean prefersCondensedTitle() {
        return false;
    }

    public boolean showsIcon() {
        return true;
    }

    public void setIconTintList(ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        this.mIconTint = tint;
        if (this.mItemData != null) {
            setIcon(this.mItemData.getIcon());
        }
    }

    public void setTextColor(ColorStateList colorStateList) {
        ColorStateList color = colorStateList;
        ColorStateList colorStateList2 = color;
        this.mSmallLabel.setTextColor(color);
        this.mLargeLabel.setTextColor(color);
    }

    public void setItemBackground(int i) {
        Drawable drawable;
        int background = i;
        int i2 = background;
        if (background != 0) {
            drawable = ContextCompat.getDrawable(getContext(), background);
        } else {
            drawable = null;
        }
        ViewCompat.setBackground(this, drawable);
    }
}
