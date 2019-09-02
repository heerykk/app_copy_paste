package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.C0001R;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.internal.BottomNavigationPresenter;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.view.SupportMenuInflater;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuBuilder.Callback;
import android.support.p003v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class BottomNavigationView extends FrameLayout {
    private static final int[] CHECKED_STATE_SET;
    private static final int[] DISABLED_STATE_SET;
    private OnNavigationItemSelectedListener mListener;
    private final MenuBuilder mMenu;
    private MenuInflater mMenuInflater;
    private final BottomNavigationMenuView mMenuView;
    private final BottomNavigationPresenter mPresenter;

    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
    }

    static /* synthetic */ OnNavigationItemSelectedListener access$000(BottomNavigationView bottomNavigationView) {
        BottomNavigationView x0 = bottomNavigationView;
        BottomNavigationView bottomNavigationView2 = x0;
        return x0.mListener;
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 16842912;
        CHECKED_STATE_SET = iArr;
        int[] iArr2 = new int[1];
        iArr2[0] = -16842910;
        DISABLED_STATE_SET = iArr2;
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mPresenter = new BottomNavigationPresenter();
        ThemeUtils.checkAppCompatTheme(context2);
        this.mMenu = new BottomNavigationMenu(context2);
        this.mMenuView = new BottomNavigationMenuView(context2);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        LayoutParams params = layoutParams;
        layoutParams.gravity = 17;
        this.mMenuView.setLayoutParams(params);
        this.mPresenter.setBottomNavigationMenuView(this.mMenuView);
        this.mMenuView.setPresenter(this.mPresenter);
        this.mMenu.addMenuPresenter(this.mPresenter);
        this.mPresenter.initForMenu(getContext(), this.mMenu);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, attrs, C0001R.styleable.BottomNavigationView, defStyleAttr, C0001R.style.Widget_Design_BottomNavigationView);
        TintTypedArray a = obtainStyledAttributes;
        if (!obtainStyledAttributes.hasValue(C0001R.styleable.BottomNavigationView_itemIconTint)) {
            this.mMenuView.setIconTintList(createDefaultColorStateList(16842808));
        } else {
            this.mMenuView.setIconTintList(a.getColorStateList(C0001R.styleable.BottomNavigationView_itemIconTint));
        }
        if (!a.hasValue(C0001R.styleable.BottomNavigationView_itemTextColor)) {
            this.mMenuView.setItemTextColor(createDefaultColorStateList(16842808));
        } else {
            this.mMenuView.setItemTextColor(a.getColorStateList(C0001R.styleable.BottomNavigationView_itemTextColor));
        }
        if (a.hasValue(C0001R.styleable.BottomNavigationView_elevation)) {
            ViewCompat.setElevation(this, (float) a.getDimensionPixelSize(C0001R.styleable.BottomNavigationView_elevation, 0));
        }
        int resourceId = a.getResourceId(C0001R.styleable.BottomNavigationView_itemBackground, 0);
        int i3 = resourceId;
        this.mMenuView.setItemBackgroundRes(resourceId);
        if (a.hasValue(C0001R.styleable.BottomNavigationView_menu)) {
            inflateMenu(a.getResourceId(C0001R.styleable.BottomNavigationView_menu, 0));
        }
        a.recycle();
        addView(this.mMenuView, params);
        if (VERSION.SDK_INT < 21) {
            addCompatibilityTopDivider(context2);
        }
        this.mMenu.setCallback(new Callback(this) {
            final /* synthetic */ BottomNavigationView this$0;

            {
                BottomNavigationView this$02 = r5;
                BottomNavigationView bottomNavigationView = this$02;
                this.this$0 = this$02;
            }

            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                MenuItem item = menuItem;
                MenuBuilder menuBuilder2 = menuBuilder;
                MenuItem menuItem2 = item;
                return BottomNavigationView.access$000(this.this$0) != null && !BottomNavigationView.access$000(this.this$0).onNavigationItemSelected(item);
            }

            public void onMenuModeChange(MenuBuilder menuBuilder) {
                MenuBuilder menuBuilder2 = menuBuilder;
            }
        });
    }

    public BottomNavigationView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void setOnNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        OnNavigationItemSelectedListener listener = onNavigationItemSelectedListener;
        OnNavigationItemSelectedListener onNavigationItemSelectedListener2 = listener;
        this.mListener = listener;
    }

    @NonNull
    public Menu getMenu() {
        return this.mMenu;
    }

    public void inflateMenu(int i) {
        int resId = i;
        int i2 = resId;
        this.mPresenter.setUpdateSuspended(true);
        getMenuInflater().inflate(resId, this.mMenu);
        this.mPresenter.setUpdateSuspended(false);
        this.mPresenter.updateMenuView(true);
    }

    public int getMaxItemCount() {
        return 5;
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return this.mMenuView.getIconTintList();
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        this.mMenuView.setIconTintList(tint);
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.mMenuView.getItemTextColor();
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        ColorStateList textColor = colorStateList;
        ColorStateList colorStateList2 = textColor;
        this.mMenuView.setItemTextColor(textColor);
    }

    @DrawableRes
    public int getItemBackgroundResource() {
        return this.mMenuView.getItemBackgroundRes();
    }

    public void setItemBackgroundResource(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        this.mMenuView.setItemBackgroundRes(resId);
    }

    private void addCompatibilityTopDivider(Context context) {
        Context context2 = context;
        Context context3 = context2;
        View view = new View(context2);
        View divider = view;
        view.setBackgroundColor(ContextCompat.getColor(context2, C0001R.color.design_bottom_navigation_shadow_color));
        divider.setLayoutParams(new LayoutParams(-1, getResources().getDimensionPixelSize(C0001R.dimen.design_bottom_navigation_shadow_height)));
        addView(divider);
    }

    private MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            this.mMenuInflater = new SupportMenuInflater(getContext());
        }
        return this.mMenuInflater;
    }

    private ColorStateList createDefaultColorStateList(int i) {
        int baseColorThemeAttr = i;
        int i2 = baseColorThemeAttr;
        TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            return null;
        }
        ColorStateList baseColor = AppCompatResources.getColorStateList(getContext(), value.resourceId);
        if (!getContext().getTheme().resolveAttribute(C0268R.attr.colorPrimary, value, true)) {
            return null;
        }
        int colorPrimary = value.data;
        int defaultColor = baseColor.getDefaultColor();
        int i3 = defaultColor;
        int[][] iArr = new int[3][];
        iArr[0] = DISABLED_STATE_SET;
        iArr[1] = CHECKED_STATE_SET;
        iArr[2] = EMPTY_STATE_SET;
        int[] iArr2 = new int[3];
        iArr2[0] = baseColor.getColorForState(DISABLED_STATE_SET, defaultColor);
        iArr2[1] = colorPrimary;
        iArr2[2] = defaultColor;
        return new ColorStateList(iArr, iArr2);
    }
}
