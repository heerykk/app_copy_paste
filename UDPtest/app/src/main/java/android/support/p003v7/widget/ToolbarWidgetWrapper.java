package android.support.p003v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewPropertyAnimatorCompat;
import android.support.p000v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.view.menu.ActionMenuItem;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuPresenter;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window.Callback;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ToolbarWidgetWrapper */
public class ToolbarWidgetWrapper implements DecorToolbar {
    private static final int AFFECTS_LOGO_MASK = 3;
    private static final long DEFAULT_FADE_DURATION_MS = 200;
    private static final String TAG = "ToolbarWidgetWrapper";
    private ActionMenuPresenter mActionMenuPresenter;
    private View mCustomView;
    private int mDefaultNavigationContentDescription;
    private Drawable mDefaultNavigationIcon;
    private int mDisplayOpts;
    private CharSequence mHomeDescription;
    private Drawable mIcon;
    private Drawable mLogo;
    boolean mMenuPrepared;
    private Drawable mNavIcon;
    private int mNavigationMode;
    private Spinner mSpinner;
    private CharSequence mSubtitle;
    private View mTabView;
    CharSequence mTitle;
    private boolean mTitleSet;
    Toolbar mToolbar;
    Callback mWindowCallback;

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean z, int i, int i2) {
        Toolbar toolbar2 = toolbar;
        int defaultNavigationContentDescription = i;
        Toolbar toolbar3 = toolbar2;
        boolean style = z;
        int i3 = defaultNavigationContentDescription;
        int i4 = i2;
        this.mNavigationMode = 0;
        this.mDefaultNavigationContentDescription = 0;
        this.mToolbar = toolbar2;
        this.mTitle = toolbar2.getTitle();
        this.mSubtitle = toolbar2.getSubtitle();
        this.mTitleSet = this.mTitle != null;
        this.mNavIcon = toolbar2.getNavigationIcon();
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(toolbar2.getContext(), null, C0268R.styleable.ActionBar, C0268R.attr.actionBarStyle, 0);
        this.mDefaultNavigationIcon = a.getDrawable(C0268R.styleable.ActionBar_homeAsUpIndicator);
        if (!style) {
            this.mDisplayOpts = detectDisplayOptions();
        } else {
            CharSequence text = a.getText(C0268R.styleable.ActionBar_title);
            CharSequence title = text;
            if (!TextUtils.isEmpty(text)) {
                setTitle(title);
            }
            CharSequence text2 = a.getText(C0268R.styleable.ActionBar_subtitle);
            CharSequence subtitle = text2;
            if (!TextUtils.isEmpty(text2)) {
                setSubtitle(subtitle);
            }
            Drawable drawable = a.getDrawable(C0268R.styleable.ActionBar_logo);
            Drawable logo = drawable;
            if (drawable != null) {
                setLogo(logo);
            }
            Drawable drawable2 = a.getDrawable(C0268R.styleable.ActionBar_icon);
            Drawable icon = drawable2;
            if (drawable2 != null) {
                setIcon(icon);
            }
            if (this.mNavIcon == null && this.mDefaultNavigationIcon != null) {
                setNavigationIcon(this.mDefaultNavigationIcon);
            }
            setDisplayOptions(a.getInt(C0268R.styleable.ActionBar_displayOptions, 0));
            int resourceId = a.getResourceId(C0268R.styleable.ActionBar_customNavigationLayout, 0);
            int customNavId = resourceId;
            if (resourceId != 0) {
                setCustomView(LayoutInflater.from(this.mToolbar.getContext()).inflate(customNavId, this.mToolbar, false));
                setDisplayOptions(this.mDisplayOpts | 16);
            }
            int layoutDimension = a.getLayoutDimension(C0268R.styleable.ActionBar_height, 0);
            int height = layoutDimension;
            if (layoutDimension > 0) {
                LayoutParams layoutParams = this.mToolbar.getLayoutParams();
                LayoutParams lp = layoutParams;
                layoutParams.height = height;
                this.mToolbar.setLayoutParams(lp);
            }
            int contentInsetStart = a.getDimensionPixelOffset(C0268R.styleable.ActionBar_contentInsetStart, -1);
            int contentInsetEnd = a.getDimensionPixelOffset(C0268R.styleable.ActionBar_contentInsetEnd, -1);
            if (contentInsetStart >= 0 || contentInsetEnd >= 0) {
                this.mToolbar.setContentInsetsRelative(Math.max(contentInsetStart, 0), Math.max(contentInsetEnd, 0));
            }
            int resourceId2 = a.getResourceId(C0268R.styleable.ActionBar_titleTextStyle, 0);
            int titleTextStyle = resourceId2;
            if (resourceId2 != 0) {
                this.mToolbar.setTitleTextAppearance(this.mToolbar.getContext(), titleTextStyle);
            }
            int resourceId3 = a.getResourceId(C0268R.styleable.ActionBar_subtitleTextStyle, 0);
            int subtitleTextStyle = resourceId3;
            if (resourceId3 != 0) {
                this.mToolbar.setSubtitleTextAppearance(this.mToolbar.getContext(), subtitleTextStyle);
            }
            int resourceId4 = a.getResourceId(C0268R.styleable.ActionBar_popupTheme, 0);
            int popupTheme = resourceId4;
            if (resourceId4 != 0) {
                this.mToolbar.setPopupTheme(popupTheme);
            }
        }
        a.recycle();
        setDefaultNavigationContentDescription(defaultNavigationContentDescription);
        this.mHomeDescription = this.mToolbar.getNavigationContentDescription();
        Toolbar toolbar4 = this.mToolbar;
        C03571 r0 = new OnClickListener(this) {
            final ActionMenuItem mNavItem;
            final /* synthetic */ ToolbarWidgetWrapper this$0;

            {
                ToolbarWidgetWrapper this$02 = r21;
                ToolbarWidgetWrapper toolbarWidgetWrapper = this$02;
                this.this$0 = this$02;
                ActionMenuItem actionMenuItem = new ActionMenuItem(this.this$0.mToolbar.getContext(), 0, 16908332, 0, 0, this.this$0.mTitle);
                this.mNavItem = actionMenuItem;
            }

            public void onClick(View view) {
                View view2 = view;
                if (this.this$0.mWindowCallback != null && this.this$0.mMenuPrepared) {
                    boolean onMenuItemSelected = this.this$0.mWindowCallback.onMenuItemSelected(0, this.mNavItem);
                }
            }
        };
        toolbar4.setNavigationOnClickListener(r0);
    }

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean z) {
        Toolbar toolbar2 = toolbar;
        Toolbar toolbar3 = toolbar2;
        this(toolbar2, z, C0268R.string.abc_action_bar_up_description, C0268R.C0269drawable.abc_ic_ab_back_material);
    }

    public void setDefaultNavigationContentDescription(int i) {
        int defaultNavigationContentDescription = i;
        int i2 = defaultNavigationContentDescription;
        if (defaultNavigationContentDescription != this.mDefaultNavigationContentDescription) {
            this.mDefaultNavigationContentDescription = defaultNavigationContentDescription;
            if (TextUtils.isEmpty(this.mToolbar.getNavigationContentDescription())) {
                setNavigationContentDescription(this.mDefaultNavigationContentDescription);
            }
        }
    }

    private int detectDisplayOptions() {
        int opts = 11;
        if (this.mToolbar.getNavigationIcon() != null) {
            opts = 15;
            this.mDefaultNavigationIcon = this.mToolbar.getNavigationIcon();
        }
        return opts;
    }

    public ViewGroup getViewGroup() {
        return this.mToolbar;
    }

    public Context getContext() {
        return this.mToolbar.getContext();
    }

    public boolean hasExpandedActionView() {
        return this.mToolbar.hasExpandedActionView();
    }

    public void collapseActionView() {
        this.mToolbar.collapseActionView();
    }

    public void setWindowCallback(Callback callback) {
        Callback cb = callback;
        Callback callback2 = cb;
        this.mWindowCallback = cb;
    }

    public void setWindowTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        if (!this.mTitleSet) {
            setTitleInt(title);
        }
    }

    public CharSequence getTitle() {
        return this.mToolbar.getTitle();
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mTitleSet = true;
        setTitleInt(title);
    }

    private void setTitleInt(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mTitle = title;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setTitle(title);
        }
    }

    public CharSequence getSubtitle() {
        return this.mToolbar.getSubtitle();
    }

    public void setSubtitle(CharSequence charSequence) {
        CharSequence subtitle = charSequence;
        CharSequence charSequence2 = subtitle;
        this.mSubtitle = subtitle;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setSubtitle(subtitle);
        }
    }

    public void initProgress() {
        int i = Log.i(TAG, "Progress display unsupported");
    }

    public void initIndeterminateProgress() {
        int i = Log.i(TAG, "Progress display unsupported");
    }

    public boolean hasIcon() {
        return this.mIcon != null;
    }

    public boolean hasLogo() {
        return this.mLogo != null;
    }

    public void setIcon(int i) {
        int resId = i;
        int i2 = resId;
        setIcon(resId == 0 ? null : AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setIcon(Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        this.mIcon = d;
        updateToolbarLogo();
    }

    public void setLogo(int i) {
        int resId = i;
        int i2 = resId;
        setLogo(resId == 0 ? null : AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setLogo(Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        this.mLogo = d;
        updateToolbarLogo();
    }

    private void updateToolbarLogo() {
        Drawable logo = null;
        if ((this.mDisplayOpts & 2) != 0) {
            if ((this.mDisplayOpts & 1) == 0) {
                logo = this.mIcon;
            } else {
                logo = this.mLogo == null ? this.mIcon : this.mLogo;
            }
        }
        this.mToolbar.setLogo(logo);
    }

    public boolean canShowOverflowMenu() {
        return this.mToolbar.canShowOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        return this.mToolbar.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        return this.mToolbar.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() {
        return this.mToolbar.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.mToolbar.hideOverflowMenu();
    }

    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        Menu menu2 = menu;
        MenuPresenter.Callback cb = callback;
        Menu menu3 = menu2;
        MenuPresenter.Callback callback2 = cb;
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = new ActionMenuPresenter(this.mToolbar.getContext());
            this.mActionMenuPresenter.setId(C0268R.C0270id.action_menu_presenter);
        }
        this.mActionMenuPresenter.setCallback(cb);
        this.mToolbar.setMenu((MenuBuilder) menu2, this.mActionMenuPresenter);
    }

    public void dismissPopupMenus() {
        this.mToolbar.dismissPopupMenus();
    }

    public int getDisplayOptions() {
        return this.mDisplayOpts;
    }

    public void setDisplayOptions(int i) {
        int newOpts = i;
        int i2 = newOpts;
        int i3 = this.mDisplayOpts;
        int i4 = i3;
        int i5 = i3 ^ newOpts;
        int changed = i5;
        this.mDisplayOpts = newOpts;
        if (i5 != 0) {
            if ((changed & 4) != 0) {
                if ((newOpts & 4) != 0) {
                    updateHomeAccessibility();
                }
                updateNavigationIcon();
            }
            if ((changed & 3) != 0) {
                updateToolbarLogo();
            }
            if ((changed & 8) != 0) {
                if ((newOpts & 8) == 0) {
                    this.mToolbar.setTitle((CharSequence) null);
                    this.mToolbar.setSubtitle((CharSequence) null);
                } else {
                    this.mToolbar.setTitle(this.mTitle);
                    this.mToolbar.setSubtitle(this.mSubtitle);
                }
            }
            if ((changed & 16) != 0 && this.mCustomView != null) {
                if ((newOpts & 16) == 0) {
                    this.mToolbar.removeView(this.mCustomView);
                } else {
                    this.mToolbar.addView(this.mCustomView);
                }
            }
        }
    }

    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView) {
        ScrollingTabContainerView tabView = scrollingTabContainerView;
        ScrollingTabContainerView scrollingTabContainerView2 = tabView;
        if (this.mTabView != null && this.mTabView.getParent() == this.mToolbar) {
            this.mToolbar.removeView(this.mTabView);
        }
        this.mTabView = tabView;
        if (tabView != null && this.mNavigationMode == 2) {
            this.mToolbar.addView(this.mTabView, 0);
            Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) this.mTabView.getLayoutParams();
            Toolbar.LayoutParams lp = layoutParams;
            layoutParams.width = -2;
            lp.height = -2;
            lp.gravity = 8388691;
            tabView.setAllowCollapse(true);
        }
    }

    public boolean hasEmbeddedTabs() {
        return this.mTabView != null;
    }

    public boolean isTitleTruncated() {
        return this.mToolbar.isTitleTruncated();
    }

    public void setCollapsible(boolean z) {
        this.mToolbar.setCollapsible(z);
    }

    public void setHomeButtonEnabled(boolean z) {
        boolean z2 = z;
    }

    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    public void setNavigationMode(int i) {
        int mode = i;
        int i2 = mode;
        int oldMode = this.mNavigationMode;
        if (mode != oldMode) {
            switch (oldMode) {
                case 1:
                    if (this.mSpinner != null && this.mSpinner.getParent() == this.mToolbar) {
                        this.mToolbar.removeView(this.mSpinner);
                        break;
                    }
                case 2:
                    if (this.mTabView != null && this.mTabView.getParent() == this.mToolbar) {
                        this.mToolbar.removeView(this.mTabView);
                        break;
                    }
            }
            this.mNavigationMode = mode;
            switch (mode) {
                case 0:
                    return;
                case 1:
                    ensureSpinner();
                    this.mToolbar.addView(this.mSpinner, 0);
                    return;
                case 2:
                    if (this.mTabView != null) {
                        this.mToolbar.addView(this.mTabView, 0);
                        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) this.mTabView.getLayoutParams();
                        Toolbar.LayoutParams lp = layoutParams;
                        layoutParams.width = -2;
                        lp.height = -2;
                        lp.gravity = 8388691;
                        return;
                    }
                    return;
                default:
                    throw new IllegalArgumentException("Invalid navigation mode " + mode);
            }
        }
    }

    private void ensureSpinner() {
        if (this.mSpinner == null) {
            this.mSpinner = new AppCompatSpinner(getContext(), null, C0268R.attr.actionDropDownStyle);
            this.mSpinner.setLayoutParams(new Toolbar.LayoutParams(-2, -2, 8388627));
        }
    }

    public void setDropdownParams(SpinnerAdapter spinnerAdapter, OnItemSelectedListener onItemSelectedListener) {
        SpinnerAdapter adapter = spinnerAdapter;
        OnItemSelectedListener listener = onItemSelectedListener;
        SpinnerAdapter spinnerAdapter2 = adapter;
        OnItemSelectedListener onItemSelectedListener2 = listener;
        ensureSpinner();
        this.mSpinner.setAdapter(adapter);
        this.mSpinner.setOnItemSelectedListener(listener);
    }

    public void setDropdownSelectedPosition(int i) {
        int position = i;
        int i2 = position;
        if (this.mSpinner != null) {
            this.mSpinner.setSelection(position);
            return;
        }
        throw new IllegalStateException("Can't set dropdown selected position without an adapter");
    }

    public int getDropdownSelectedPosition() {
        return this.mSpinner == null ? 0 : this.mSpinner.getSelectedItemPosition();
    }

    public int getDropdownItemCount() {
        return this.mSpinner == null ? 0 : this.mSpinner.getCount();
    }

    public void setCustomView(View view) {
        View view2 = view;
        View view3 = view2;
        if (!(this.mCustomView == null || (this.mDisplayOpts & 16) == 0)) {
            this.mToolbar.removeView(this.mCustomView);
        }
        this.mCustomView = view2;
        if (view2 != null && (this.mDisplayOpts & 16) != 0) {
            this.mToolbar.addView(this.mCustomView);
        }
    }

    public View getCustomView() {
        return this.mCustomView;
    }

    public void animateToVisibility(int i) {
        int visibility = i;
        int i2 = visibility;
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = setupAnimatorToVisibility(visibility, DEFAULT_FADE_DURATION_MS);
        ViewPropertyAnimatorCompat anim = viewPropertyAnimatorCompat;
        if (viewPropertyAnimatorCompat != null) {
            anim.start();
        }
    }

    public ViewPropertyAnimatorCompat setupAnimatorToVisibility(int i, long j) {
        final int visibility = i;
        long duration = j;
        int i2 = visibility;
        long j2 = duration;
        return ViewCompat.animate(this.mToolbar).alpha(visibility != 0 ? 0.0f : 1.0f).setDuration(duration).setListener(new ViewPropertyAnimatorListenerAdapter(this) {
            private boolean mCanceled = false;
            final /* synthetic */ ToolbarWidgetWrapper this$0;

            {
                ToolbarWidgetWrapper this$02 = r7;
                int i = r8;
                ToolbarWidgetWrapper toolbarWidgetWrapper = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationStart(View view) {
                View view2 = view;
                this.this$0.mToolbar.setVisibility(0);
            }

            public void onAnimationEnd(View view) {
                View view2 = view;
                if (!this.mCanceled) {
                    this.this$0.mToolbar.setVisibility(visibility);
                }
            }

            public void onAnimationCancel(View view) {
                View view2 = view;
                this.mCanceled = true;
            }
        });
    }

    public void setNavigationIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        this.mNavIcon = icon;
        updateNavigationIcon();
    }

    public void setNavigationIcon(int i) {
        int resId = i;
        int i2 = resId;
        setNavigationIcon(resId == 0 ? null : AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setDefaultNavigationIcon(Drawable drawable) {
        Drawable defaultNavigationIcon = drawable;
        Drawable drawable2 = defaultNavigationIcon;
        if (this.mDefaultNavigationIcon != defaultNavigationIcon) {
            this.mDefaultNavigationIcon = defaultNavigationIcon;
            updateNavigationIcon();
        }
    }

    private void updateNavigationIcon() {
        if ((this.mDisplayOpts & 4) == 0) {
            this.mToolbar.setNavigationIcon((Drawable) null);
        } else {
            this.mToolbar.setNavigationIcon(this.mNavIcon == null ? this.mDefaultNavigationIcon : this.mNavIcon);
        }
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        CharSequence description = charSequence;
        CharSequence charSequence2 = description;
        this.mHomeDescription = description;
        updateHomeAccessibility();
    }

    public void setNavigationContentDescription(int i) {
        int resId = i;
        int i2 = resId;
        setNavigationContentDescription((CharSequence) resId != 0 ? getContext().getString(resId) : null);
    }

    private void updateHomeAccessibility() {
        if ((this.mDisplayOpts & 4) != 0) {
            if (!TextUtils.isEmpty(this.mHomeDescription)) {
                this.mToolbar.setNavigationContentDescription(this.mHomeDescription);
            } else {
                this.mToolbar.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
            }
        }
    }

    public void saveHierarchyState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> toolbarStates = sparseArray;
        SparseArray<Parcelable> sparseArray2 = toolbarStates;
        this.mToolbar.saveHierarchyState(toolbarStates);
    }

    public void restoreHierarchyState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> toolbarStates = sparseArray;
        SparseArray<Parcelable> sparseArray2 = toolbarStates;
        this.mToolbar.restoreHierarchyState(toolbarStates);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        ViewCompat.setBackground(this.mToolbar, d);
    }

    public int getHeight() {
        return this.mToolbar.getHeight();
    }

    public void setVisibility(int i) {
        int visible = i;
        int i2 = visible;
        this.mToolbar.setVisibility(visible);
    }

    public int getVisibility() {
        return this.mToolbar.getVisibility();
    }

    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        MenuPresenter.Callback actionMenuPresenterCallback = callback;
        MenuBuilder.Callback menuBuilderCallback = callback2;
        MenuPresenter.Callback callback3 = actionMenuPresenterCallback;
        MenuBuilder.Callback callback4 = menuBuilderCallback;
        this.mToolbar.setMenuCallbacks(actionMenuPresenterCallback, menuBuilderCallback);
    }

    public Menu getMenu() {
        return this.mToolbar.getMenu();
    }
}
