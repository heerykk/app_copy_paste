package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.design.C0001R;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.view.AbsSavedState;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.WindowInsetsCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.view.SupportMenuInflater;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuBuilder.Callback;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.support.p003v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;

public class NavigationView extends ScrimInsetsFrameLayout {
    private static final int[] CHECKED_STATE_SET;
    private static final int[] DISABLED_STATE_SET;
    private static final int PRESENTER_NAVIGATION_VIEW_ID = 1;
    OnNavigationItemSelectedListener mListener;
    private int mMaxWidth;
    private final NavigationMenu mMenu;
    private MenuInflater mMenuInflater;
    private final NavigationMenuPresenter mPresenter;

    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
    }

    public static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                Parcel parcel2 = parcel;
                ClassLoader loader = classLoader;
                Parcel parcel3 = parcel2;
                ClassLoader classLoader2 = loader;
                return new SavedState(parcel2, loader);
            }

            public SavedState[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new SavedState[size];
            }
        });
        public Bundle menuState;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            Parcel in = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = in;
            ClassLoader classLoader2 = loader;
            super(in, loader);
            this.menuState = in.readBundle(loader);
        }

        public SavedState(Parcelable parcelable) {
            Parcelable superState = parcelable;
            Parcelable parcelable2 = superState;
            super(superState);
        }

        public void writeToParcel(@NonNull Parcel parcel, int i) {
            Parcel dest = parcel;
            int flags = i;
            Parcel parcel2 = dest;
            int i2 = flags;
            super.writeToParcel(dest, flags);
            dest.writeBundle(this.menuState);
        }
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 16842912;
        CHECKED_STATE_SET = iArr;
        int[] iArr2 = new int[1];
        iArr2[0] = -16842910;
        DISABLED_STATE_SET = iArr2;
    }

    public NavigationView(Context context, AttributeSet attributeSet, int i) {
        ColorStateList itemIconTint;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mPresenter = new NavigationMenuPresenter();
        ThemeUtils.checkAppCompatTheme(context2);
        this.mMenu = new NavigationMenu(context2);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context2, attrs, C0001R.styleable.NavigationView, defStyleAttr, C0001R.style.Widget_Design_NavigationView);
        ViewCompat.setBackground(this, a.getDrawable(C0001R.styleable.NavigationView_android_background));
        if (a.hasValue(C0001R.styleable.NavigationView_elevation)) {
            ViewCompat.setElevation(this, (float) a.getDimensionPixelSize(C0001R.styleable.NavigationView_elevation, 0));
        }
        ViewCompat.setFitsSystemWindows(this, a.getBoolean(C0001R.styleable.NavigationView_android_fitsSystemWindows, false));
        this.mMaxWidth = a.getDimensionPixelSize(C0001R.styleable.NavigationView_android_maxWidth, 0);
        if (!a.hasValue(C0001R.styleable.NavigationView_itemIconTint)) {
            itemIconTint = createDefaultColorStateList(16842808);
        } else {
            itemIconTint = a.getColorStateList(C0001R.styleable.NavigationView_itemIconTint);
        }
        boolean textAppearanceSet = false;
        int textAppearance = 0;
        if (a.hasValue(C0001R.styleable.NavigationView_itemTextAppearance)) {
            textAppearance = a.getResourceId(C0001R.styleable.NavigationView_itemTextAppearance, 0);
            textAppearanceSet = true;
        }
        ColorStateList itemTextColor = null;
        if (a.hasValue(C0001R.styleable.NavigationView_itemTextColor)) {
            itemTextColor = a.getColorStateList(C0001R.styleable.NavigationView_itemTextColor);
        }
        if (!textAppearanceSet && itemTextColor == null) {
            itemTextColor = createDefaultColorStateList(16842806);
        }
        Drawable itemBackground = a.getDrawable(C0001R.styleable.NavigationView_itemBackground);
        this.mMenu.setCallback(new Callback(this) {
            final /* synthetic */ NavigationView this$0;

            {
                NavigationView this$02 = r5;
                NavigationView navigationView = this$02;
                this.this$0 = this$02;
            }

            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                MenuItem item = menuItem;
                MenuBuilder menuBuilder2 = menuBuilder;
                MenuItem menuItem2 = item;
                return this.this$0.mListener != null && this.this$0.mListener.onNavigationItemSelected(item);
            }

            public void onMenuModeChange(MenuBuilder menuBuilder) {
                MenuBuilder menuBuilder2 = menuBuilder;
            }
        });
        this.mPresenter.setId(1);
        this.mPresenter.initForMenu(context2, this.mMenu);
        this.mPresenter.setItemIconTintList(itemIconTint);
        if (textAppearanceSet) {
            this.mPresenter.setItemTextAppearance(textAppearance);
        }
        this.mPresenter.setItemTextColor(itemTextColor);
        this.mPresenter.setItemBackground(itemBackground);
        this.mMenu.addMenuPresenter(this.mPresenter);
        addView((View) this.mPresenter.getMenuView(this));
        if (a.hasValue(C0001R.styleable.NavigationView_menu)) {
            inflateMenu(a.getResourceId(C0001R.styleable.NavigationView_menu, 0));
        }
        if (a.hasValue(C0001R.styleable.NavigationView_headerLayout)) {
            View inflateHeaderView = inflateHeaderView(a.getResourceId(C0001R.styleable.NavigationView_headerLayout, 0));
        }
        a.recycle();
    }

    public NavigationView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public NavigationView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState state = savedState;
        savedState.menuState = new Bundle();
        this.mMenu.savePresenterStates(state.menuState);
        return state;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable savedState = parcelable;
        Parcelable parcelable2 = savedState;
        if (savedState instanceof SavedState) {
            SavedState state = (SavedState) savedState;
            super.onRestoreInstanceState(state.getSuperState());
            this.mMenu.restorePresenterStates(state.menuState);
            return;
        }
        super.onRestoreInstanceState(savedState);
    }

    public void setNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        OnNavigationItemSelectedListener listener = onNavigationItemSelectedListener;
        OnNavigationItemSelectedListener onNavigationItemSelectedListener2 = listener;
        this.mListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthSpec = i;
        int heightSpec = i2;
        int widthSpec2 = widthSpec;
        int i3 = heightSpec;
        switch (MeasureSpec.getMode(widthSpec)) {
            case Integer.MIN_VALUE:
                widthSpec2 = MeasureSpec.makeMeasureSpec(Math.min(MeasureSpec.getSize(widthSpec), this.mMaxWidth), 1073741824);
                break;
            case 0:
                widthSpec2 = MeasureSpec.makeMeasureSpec(this.mMaxWidth, 1073741824);
                break;
        }
        super.onMeasure(widthSpec2, heightSpec);
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void onInsetsChanged(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat insets = windowInsetsCompat;
        WindowInsetsCompat windowInsetsCompat2 = insets;
        this.mPresenter.dispatchApplyWindowInsets(insets);
    }

    public void inflateMenu(int i) {
        int resId = i;
        int i2 = resId;
        this.mPresenter.setUpdateSuspended(true);
        getMenuInflater().inflate(resId, this.mMenu);
        this.mPresenter.setUpdateSuspended(false);
        this.mPresenter.updateMenuView(false);
    }

    public Menu getMenu() {
        return this.mMenu;
    }

    public View inflateHeaderView(@LayoutRes int i) {
        int res = i;
        int i2 = res;
        return this.mPresenter.inflateHeaderView(res);
    }

    public void addHeaderView(@NonNull View view) {
        View view2 = view;
        View view3 = view2;
        this.mPresenter.addHeaderView(view2);
    }

    public void removeHeaderView(@NonNull View view) {
        View view2 = view;
        View view3 = view2;
        this.mPresenter.removeHeaderView(view2);
    }

    public int getHeaderCount() {
        return this.mPresenter.getHeaderCount();
    }

    public View getHeaderView(int i) {
        int index = i;
        int i2 = index;
        return this.mPresenter.getHeaderView(index);
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return this.mPresenter.getItemTintList();
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        this.mPresenter.setItemIconTintList(tint);
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.mPresenter.getItemTextColor();
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        ColorStateList textColor = colorStateList;
        ColorStateList colorStateList2 = textColor;
        this.mPresenter.setItemTextColor(textColor);
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.mPresenter.getItemBackground();
    }

    public void setItemBackgroundResource(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setItemBackground(ContextCompat.getDrawable(getContext(), resId));
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        Drawable itemBackground = drawable;
        Drawable drawable2 = itemBackground;
        this.mPresenter.setItemBackground(itemBackground);
    }

    public void setCheckedItem(@IdRes int i) {
        int id = i;
        int i2 = id;
        MenuItem findItem = this.mMenu.findItem(id);
        MenuItem item = findItem;
        if (findItem != null) {
            this.mPresenter.setCheckedItem((MenuItemImpl) item);
        }
    }

    public void setItemTextAppearance(@StyleRes int i) {
        int resId = i;
        int i2 = resId;
        this.mPresenter.setItemTextAppearance(resId);
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
