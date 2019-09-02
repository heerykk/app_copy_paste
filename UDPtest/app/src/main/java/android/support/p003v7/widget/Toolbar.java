package android.support.p003v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.view.AbsSavedState;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.MarginLayoutParamsCompat;
import android.support.p000v4.view.MenuItemCompat;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.view.CollapsibleActionView;
import android.support.p003v7.view.SupportMenuInflater;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuItemImpl;
import android.support.p003v7.view.menu.MenuPresenter;
import android.support.p003v7.view.menu.MenuPresenter.Callback;
import android.support.p003v7.view.menu.MenuView;
import android.support.p003v7.view.menu.SubMenuBuilder;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v7.widget.Toolbar */
public class Toolbar extends ViewGroup {
    private static final String TAG = "Toolbar";
    private Callback mActionMenuPresenterCallback;
    int mButtonGravity;
    ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private int mContentInsetEndWithActions;
    private int mContentInsetStartWithNavigation;
    private RtlSpacingHelper mContentInsets;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity;
    private final ArrayList<View> mHiddenViews;
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final android.support.p003v7.widget.ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener;
    private ImageButton mNavButtonView;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable;
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private int mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins;
    private final ArrayList<View> mTempViews;
    private int mTitleMarginBottom;
    private int mTitleMarginEnd;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    /* renamed from: android.support.v7.widget.Toolbar$ExpandedActionViewMenuPresenter */
    private class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;
        final /* synthetic */ Toolbar this$0;

        ExpandedActionViewMenuPresenter(Toolbar toolbar) {
            this.this$0 = toolbar;
        }

        public void initForMenu(Context context, MenuBuilder menuBuilder) {
            MenuBuilder menu = menuBuilder;
            Context context2 = context;
            MenuBuilder menuBuilder2 = menu;
            if (!(this.mMenu == null || this.mCurrentExpandedItem == null)) {
                boolean collapseItemActionView = this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
            }
            this.mMenu = menu;
        }

        public MenuView getMenuView(ViewGroup viewGroup) {
            ViewGroup viewGroup2 = viewGroup;
            return null;
        }

        public void updateMenuView(boolean z) {
            boolean z2 = z;
            if (this.mCurrentExpandedItem != null) {
                boolean found = false;
                if (this.mMenu != null) {
                    int count = this.mMenu.size();
                    int i = 0;
                    while (true) {
                        if (i < count) {
                            MenuItem item = this.mMenu.getItem(i);
                            MenuItem menuItem = item;
                            if (item == this.mCurrentExpandedItem) {
                                found = true;
                                break;
                            }
                            i++;
                        } else {
                            break;
                        }
                    }
                }
                if (!found) {
                    boolean collapseItemActionView = collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
                }
            }
        }

        public void setCallback(Callback callback) {
            Callback callback2 = callback;
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
            return false;
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder menuBuilder2 = menuBuilder;
            boolean z2 = z;
        }

        public boolean flagActionItems() {
            return false;
        }

        public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            MenuItemImpl item = menuItemImpl;
            MenuBuilder menuBuilder2 = menuBuilder;
            MenuItemImpl menuItemImpl2 = item;
            this.this$0.ensureCollapseButtonView();
            if (this.this$0.mCollapseButtonView.getParent() != this.this$0) {
                this.this$0.addView(this.this$0.mCollapseButtonView);
            }
            this.this$0.mExpandedActionView = item.getActionView();
            this.mCurrentExpandedItem = item;
            if (this.this$0.mExpandedActionView.getParent() != this.this$0) {
                LayoutParams generateDefaultLayoutParams = this.this$0.generateDefaultLayoutParams();
                LayoutParams lp = generateDefaultLayoutParams;
                generateDefaultLayoutParams.gravity = 8388611 | (this.this$0.mButtonGravity & 112);
                lp.mViewType = 2;
                this.this$0.mExpandedActionView.setLayoutParams(lp);
                this.this$0.addView(this.this$0.mExpandedActionView);
            }
            this.this$0.removeChildrenForExpandedActionView();
            this.this$0.requestLayout();
            item.setActionViewExpanded(true);
            if (this.this$0.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) this.this$0.mExpandedActionView).onActionViewExpanded();
            }
            return true;
        }

        public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            MenuItemImpl item = menuItemImpl;
            MenuBuilder menuBuilder2 = menuBuilder;
            MenuItemImpl menuItemImpl2 = item;
            if (this.this$0.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) this.this$0.mExpandedActionView).onActionViewCollapsed();
            }
            this.this$0.removeView(this.this$0.mExpandedActionView);
            this.this$0.removeView(this.this$0.mCollapseButtonView);
            this.this$0.mExpandedActionView = null;
            this.this$0.addChildrenForExpandedActionView();
            this.mCurrentExpandedItem = null;
            this.this$0.requestLayout();
            item.setActionViewExpanded(false);
            return true;
        }

        public int getId() {
            return 0;
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
            Parcelable parcelable2 = parcelable;
        }
    }

    /* renamed from: android.support.v7.widget.Toolbar$LayoutParams */
    public static class LayoutParams extends android.support.p003v7.app.ActionBar.LayoutParams {
        static final int CUSTOM = 0;
        static final int EXPANDED = 2;
        static final int SYSTEM = 1;
        int mViewType;

        public LayoutParams(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            super(width, height);
            this.mViewType = 0;
            this.gravity = 8388627;
        }

        public LayoutParams(int i, int i2, int i3) {
            int width = i;
            int height = i2;
            int gravity = i3;
            int i4 = width;
            int i5 = height;
            int i6 = gravity;
            super(width, height);
            this.mViewType = 0;
            this.gravity = gravity;
        }

        public LayoutParams(@NonNull Context context, AttributeSet attributeSet) {
            Context c = context;
            AttributeSet attrs = attributeSet;
            Context context2 = c;
            AttributeSet attributeSet2 = attrs;
            super(c, attrs);
            this.mViewType = 0;
        }

        public LayoutParams(android.support.p003v7.app.ActionBar.LayoutParams layoutParams) {
            android.support.p003v7.app.ActionBar.LayoutParams source = layoutParams;
            android.support.p003v7.app.ActionBar.LayoutParams layoutParams2 = source;
            super(source);
            this.mViewType = 0;
        }

        public LayoutParams(LayoutParams layoutParams) {
            LayoutParams source = layoutParams;
            LayoutParams layoutParams2 = source;
            super((android.support.p003v7.app.ActionBar.LayoutParams) source);
            this.mViewType = 0;
            this.mViewType = source.mViewType;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.view.ViewGroup.LayoutParams source = layoutParams;
            android.view.ViewGroup.LayoutParams layoutParams2 = source;
            super(source);
            this.mViewType = 0;
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams source = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = source;
            super((android.view.ViewGroup.LayoutParams) source);
            this.mViewType = 0;
            copyMarginsFromCompat(source);
        }

        public LayoutParams(int i) {
            int gravity = i;
            int i2 = gravity;
            this(-2, -1, gravity);
        }

        /* access modifiers changed from: 0000 */
        public void copyMarginsFromCompat(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams source = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = source;
            this.leftMargin = source.leftMargin;
            this.topMargin = source.topMargin;
            this.rightMargin = source.rightMargin;
            this.bottomMargin = source.bottomMargin;
        }
    }

    /* renamed from: android.support.v7.widget.Toolbar$OnMenuItemClickListener */
    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    /* renamed from: android.support.v7.widget.Toolbar$SavedState */
    public static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                Parcel in = parcel;
                ClassLoader loader = classLoader;
                Parcel parcel2 = in;
                ClassLoader classLoader2 = loader;
                return new SavedState(in, loader);
            }

            public SavedState[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new SavedState[size];
            }
        });
        int expandedMenuItemId;
        boolean isOverflowOpen;

        public SavedState(Parcel parcel) {
            Parcel source = parcel;
            Parcel parcel2 = source;
            this(source, null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            Parcel source = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = source;
            ClassLoader classLoader2 = loader;
            super(source, loader);
            this.expandedMenuItemId = source.readInt();
            this.isOverflowOpen = source.readInt() != 0;
        }

        public SavedState(Parcelable parcelable) {
            Parcelable superState = parcelable;
            Parcelable parcelable2 = superState;
            super(superState);
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel out = parcel;
            int flags = i;
            Parcel parcel2 = out;
            int i2 = flags;
            super.writeToParcel(out, flags);
            out.writeInt(this.expandedMenuItemId);
            out.writeInt(!this.isOverflowOpen ? 0 : 1);
        }
    }

    public Toolbar(Context context, @Nullable AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mGravity = 8388627;
        this.mTempViews = new ArrayList<>();
        this.mHiddenViews = new ArrayList<>();
        this.mTempMargins = new int[2];
        this.mMenuViewItemClickListener = new android.support.p003v7.widget.ActionMenuView.OnMenuItemClickListener(this) {
            final /* synthetic */ Toolbar this$0;

            {
                Toolbar this$02 = r5;
                Toolbar toolbar = this$02;
                this.this$0 = this$02;
            }

            public boolean onMenuItemClick(MenuItem menuItem) {
                MenuItem item = menuItem;
                MenuItem menuItem2 = item;
                if (this.this$0.mOnMenuItemClickListener == null) {
                    return false;
                }
                return this.this$0.mOnMenuItemClickListener.onMenuItemClick(item);
            }
        };
        this.mShowOverflowMenuRunnable = new Runnable(this) {
            final /* synthetic */ Toolbar this$0;

            {
                Toolbar this$02 = r5;
                Toolbar toolbar = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                boolean showOverflowMenu = this.this$0.showOverflowMenu();
            }
        };
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, C0268R.styleable.Toolbar, defStyleAttr, 0);
        this.mTitleTextAppearance = a.getResourceId(C0268R.styleable.Toolbar_titleTextAppearance, 0);
        this.mSubtitleTextAppearance = a.getResourceId(C0268R.styleable.Toolbar_subtitleTextAppearance, 0);
        this.mGravity = a.getInteger(C0268R.styleable.Toolbar_android_gravity, this.mGravity);
        this.mButtonGravity = a.getInteger(C0268R.styleable.Toolbar_buttonGravity, 48);
        int titleMargin = a.getDimensionPixelOffset(C0268R.styleable.Toolbar_titleMargin, 0);
        if (a.hasValue(C0268R.styleable.Toolbar_titleMargins)) {
            titleMargin = a.getDimensionPixelOffset(C0268R.styleable.Toolbar_titleMargins, titleMargin);
        }
        int i3 = titleMargin;
        this.mTitleMarginBottom = titleMargin;
        this.mTitleMarginTop = i3;
        int i4 = i3;
        this.mTitleMarginEnd = i3;
        this.mTitleMarginStart = i4;
        int dimensionPixelOffset = a.getDimensionPixelOffset(C0268R.styleable.Toolbar_titleMarginStart, -1);
        int marginStart = dimensionPixelOffset;
        if (dimensionPixelOffset >= 0) {
            this.mTitleMarginStart = marginStart;
        }
        int dimensionPixelOffset2 = a.getDimensionPixelOffset(C0268R.styleable.Toolbar_titleMarginEnd, -1);
        int marginEnd = dimensionPixelOffset2;
        if (dimensionPixelOffset2 >= 0) {
            this.mTitleMarginEnd = marginEnd;
        }
        int dimensionPixelOffset3 = a.getDimensionPixelOffset(C0268R.styleable.Toolbar_titleMarginTop, -1);
        int marginTop = dimensionPixelOffset3;
        if (dimensionPixelOffset3 >= 0) {
            this.mTitleMarginTop = marginTop;
        }
        int dimensionPixelOffset4 = a.getDimensionPixelOffset(C0268R.styleable.Toolbar_titleMarginBottom, -1);
        int marginBottom = dimensionPixelOffset4;
        if (dimensionPixelOffset4 >= 0) {
            this.mTitleMarginBottom = marginBottom;
        }
        this.mMaxButtonHeight = a.getDimensionPixelSize(C0268R.styleable.Toolbar_maxButtonHeight, -1);
        int contentInsetStart = a.getDimensionPixelOffset(C0268R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int contentInsetEnd = a.getDimensionPixelOffset(C0268R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int contentInsetLeft = a.getDimensionPixelSize(C0268R.styleable.Toolbar_contentInsetLeft, 0);
        int dimensionPixelSize = a.getDimensionPixelSize(C0268R.styleable.Toolbar_contentInsetRight, 0);
        int i5 = dimensionPixelSize;
        ensureContentInsets();
        this.mContentInsets.setAbsolute(contentInsetLeft, dimensionPixelSize);
        if (!(contentInsetStart == Integer.MIN_VALUE && contentInsetEnd == Integer.MIN_VALUE)) {
            this.mContentInsets.setRelative(contentInsetStart, contentInsetEnd);
        }
        this.mContentInsetStartWithNavigation = a.getDimensionPixelOffset(C0268R.styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.mContentInsetEndWithActions = a.getDimensionPixelOffset(C0268R.styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.mCollapseIcon = a.getDrawable(C0268R.styleable.Toolbar_collapseIcon);
        this.mCollapseDescription = a.getText(C0268R.styleable.Toolbar_collapseContentDescription);
        CharSequence text = a.getText(C0268R.styleable.Toolbar_title);
        CharSequence title = text;
        if (!TextUtils.isEmpty(text)) {
            setTitle(title);
        }
        CharSequence text2 = a.getText(C0268R.styleable.Toolbar_subtitle);
        CharSequence subtitle = text2;
        if (!TextUtils.isEmpty(text2)) {
            setSubtitle(subtitle);
        }
        this.mPopupContext = getContext();
        setPopupTheme(a.getResourceId(C0268R.styleable.Toolbar_popupTheme, 0));
        Drawable drawable = a.getDrawable(C0268R.styleable.Toolbar_navigationIcon);
        Drawable navIcon = drawable;
        if (drawable != null) {
            setNavigationIcon(navIcon);
        }
        CharSequence text3 = a.getText(C0268R.styleable.Toolbar_navigationContentDescription);
        CharSequence navDesc = text3;
        if (!TextUtils.isEmpty(text3)) {
            setNavigationContentDescription(navDesc);
        }
        Drawable drawable2 = a.getDrawable(C0268R.styleable.Toolbar_logo);
        Drawable logo = drawable2;
        if (drawable2 != null) {
            setLogo(logo);
        }
        CharSequence text4 = a.getText(C0268R.styleable.Toolbar_logoDescription);
        CharSequence logoDesc = text4;
        if (!TextUtils.isEmpty(text4)) {
            setLogoDescription(logoDesc);
        }
        if (a.hasValue(C0268R.styleable.Toolbar_titleTextColor)) {
            setTitleTextColor(a.getColor(C0268R.styleable.Toolbar_titleTextColor, -1));
        }
        if (a.hasValue(C0268R.styleable.Toolbar_subtitleTextColor)) {
            setSubtitleTextColor(a.getColor(C0268R.styleable.Toolbar_subtitleTextColor, -1));
        }
        a.recycle();
    }

    public Toolbar(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public Toolbar(Context context, @Nullable AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, C0268R.attr.toolbarStyle);
    }

    public void setPopupTheme(@StyleRes int i) {
        int resId = i;
        int i2 = resId;
        if (this.mPopupTheme != resId) {
            this.mPopupTheme = resId;
            if (resId != 0) {
                this.mPopupContext = new ContextThemeWrapper(getContext(), resId);
            } else {
                this.mPopupContext = getContext();
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public void setTitleMargin(int i, int i2, int i3, int i4) {
        int start = i;
        int top = i2;
        int end = i3;
        int bottom = i4;
        int i5 = start;
        int i6 = top;
        int i7 = end;
        int i8 = bottom;
        this.mTitleMarginStart = start;
        this.mTitleMarginTop = top;
        this.mTitleMarginEnd = end;
        this.mTitleMarginBottom = bottom;
        requestLayout();
    }

    public int getTitleMarginStart() {
        return this.mTitleMarginStart;
    }

    public void setTitleMarginStart(int i) {
        int margin = i;
        int i2 = margin;
        this.mTitleMarginStart = margin;
        requestLayout();
    }

    public int getTitleMarginTop() {
        return this.mTitleMarginTop;
    }

    public void setTitleMarginTop(int i) {
        int margin = i;
        int i2 = margin;
        this.mTitleMarginTop = margin;
        requestLayout();
    }

    public int getTitleMarginEnd() {
        return this.mTitleMarginEnd;
    }

    public void setTitleMarginEnd(int i) {
        int margin = i;
        int i2 = margin;
        this.mTitleMarginEnd = margin;
        requestLayout();
    }

    public int getTitleMarginBottom() {
        return this.mTitleMarginBottom;
    }

    public void setTitleMarginBottom(int i) {
        int margin = i;
        int i2 = margin;
        this.mTitleMarginBottom = margin;
        requestLayout();
    }

    public void onRtlPropertiesChanged(int i) {
        int layoutDirection = i;
        int i2 = layoutDirection;
        if (VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(layoutDirection);
        }
        ensureContentInsets();
        this.mContentInsets.setDirection(layoutDirection == 1);
    }

    public void setLogo(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setLogo(AppCompatResources.getDrawable(getContext(), resId));
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean canShowOverflowMenu() {
        return getVisibility() == 0 && this.mMenuView != null && this.mMenuView.isOverflowReserved();
    }

    public boolean isOverflowMenuShowing() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowing();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isOverflowMenuShowPending() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.hideOverflowMenu();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setMenu(MenuBuilder menuBuilder, ActionMenuPresenter actionMenuPresenter) {
        MenuBuilder menu = menuBuilder;
        ActionMenuPresenter outerPresenter = actionMenuPresenter;
        MenuBuilder menuBuilder2 = menu;
        ActionMenuPresenter actionMenuPresenter2 = outerPresenter;
        if (menu != null || this.mMenuView != null) {
            ensureMenuView();
            MenuBuilder peekMenu = this.mMenuView.peekMenu();
            MenuBuilder oldMenu = peekMenu;
            if (peekMenu != menu) {
                if (oldMenu != null) {
                    oldMenu.removeMenuPresenter(this.mOuterActionMenuPresenter);
                    oldMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
                }
                if (this.mExpandedMenuPresenter == null) {
                    this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter(this);
                }
                outerPresenter.setExpandedActionViewsExclusive(true);
                if (menu == null) {
                    outerPresenter.initForMenu(this.mPopupContext, null);
                    this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
                    outerPresenter.updateMenuView(true);
                    this.mExpandedMenuPresenter.updateMenuView(true);
                } else {
                    menu.addMenuPresenter(outerPresenter, this.mPopupContext);
                    menu.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
                }
                this.mMenuView.setPopupTheme(this.mPopupTheme);
                this.mMenuView.setPresenter(outerPresenter);
                this.mOuterActionMenuPresenter = outerPresenter;
            }
        }
    }

    public void dismissPopupMenus() {
        if (this.mMenuView != null) {
            this.mMenuView.dismissPopupMenus();
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isTitleTruncated() {
        if (this.mTitleTextView == null) {
            return false;
        }
        Layout layout = this.mTitleTextView.getLayout();
        Layout titleLayout = layout;
        if (layout == null) {
            return false;
        }
        int lineCount = titleLayout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            if (titleLayout.getEllipsisCount(i) > 0) {
                return true;
            }
        }
        return false;
    }

    public void setLogo(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (drawable2 != null) {
            ensureLogoView();
            if (!isChildOrHidden(this.mLogoView)) {
                addSystemView(this.mLogoView, true);
            }
        } else if (this.mLogoView != null && isChildOrHidden(this.mLogoView)) {
            removeView(this.mLogoView);
            boolean remove = this.mHiddenViews.remove(this.mLogoView);
        }
        if (this.mLogoView != null) {
            this.mLogoView.setImageDrawable(drawable2);
        }
    }

    public Drawable getLogo() {
        return this.mLogoView == null ? null : this.mLogoView.getDrawable();
    }

    public void setLogoDescription(@StringRes int i) {
        int resId = i;
        int i2 = resId;
        setLogoDescription(getContext().getText(resId));
    }

    public void setLogoDescription(CharSequence charSequence) {
        CharSequence description = charSequence;
        CharSequence charSequence2 = description;
        if (!TextUtils.isEmpty(description)) {
            ensureLogoView();
        }
        if (this.mLogoView != null) {
            this.mLogoView.setContentDescription(description);
        }
    }

    public CharSequence getLogoDescription() {
        return this.mLogoView == null ? null : this.mLogoView.getContentDescription();
    }

    private void ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = new AppCompatImageView(getContext());
        }
    }

    public boolean hasExpandedActionView() {
        return (this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    public void collapseActionView() {
        MenuItemImpl item = this.mExpandedMenuPresenter != null ? this.mExpandedMenuPresenter.mCurrentExpandedItem : null;
        if (item != null) {
            boolean collapseActionView = item.collapseActionView();
        }
    }

    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public void setTitle(@StringRes int i) {
        int resId = i;
        int i2 = resId;
        setTitle(getContext().getText(resId));
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        if (!TextUtils.isEmpty(title)) {
            if (this.mTitleTextView == null) {
                Context context = getContext();
                this.mTitleTextView = new AppCompatTextView(context);
                this.mTitleTextView.setSingleLine();
                this.mTitleTextView.setEllipsize(TruncateAt.END);
                if (this.mTitleTextAppearance != 0) {
                    this.mTitleTextView.setTextAppearance(context, this.mTitleTextAppearance);
                }
                if (this.mTitleTextColor != 0) {
                    this.mTitleTextView.setTextColor(this.mTitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mTitleTextView)) {
                addSystemView(this.mTitleTextView, true);
            }
        } else if (this.mTitleTextView != null && isChildOrHidden(this.mTitleTextView)) {
            removeView(this.mTitleTextView);
            boolean remove = this.mHiddenViews.remove(this.mTitleTextView);
        }
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText(title);
        }
        this.mTitleText = title;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }

    public void setSubtitle(@StringRes int i) {
        int resId = i;
        int i2 = resId;
        setSubtitle(getContext().getText(resId));
    }

    public void setSubtitle(CharSequence charSequence) {
        CharSequence subtitle = charSequence;
        CharSequence charSequence2 = subtitle;
        if (!TextUtils.isEmpty(subtitle)) {
            if (this.mSubtitleTextView == null) {
                Context context = getContext();
                this.mSubtitleTextView = new AppCompatTextView(context);
                this.mSubtitleTextView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TruncateAt.END);
                if (this.mSubtitleTextAppearance != 0) {
                    this.mSubtitleTextView.setTextAppearance(context, this.mSubtitleTextAppearance);
                }
                if (this.mSubtitleTextColor != 0) {
                    this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mSubtitleTextView)) {
                addSystemView(this.mSubtitleTextView, true);
            }
        } else if (this.mSubtitleTextView != null && isChildOrHidden(this.mSubtitleTextView)) {
            removeView(this.mSubtitleTextView);
            boolean remove = this.mHiddenViews.remove(this.mSubtitleTextView);
        }
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setText(subtitle);
        }
        this.mSubtitleText = subtitle;
    }

    public void setTitleTextAppearance(Context context, @StyleRes int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        this.mTitleTextAppearance = resId;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextAppearance(context2, resId);
        }
    }

    public void setSubtitleTextAppearance(Context context, @StyleRes int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        this.mSubtitleTextAppearance = resId;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextAppearance(context2, resId);
        }
    }

    public void setTitleTextColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        this.mTitleTextColor = color;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextColor(color);
        }
    }

    public void setSubtitleTextColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        this.mSubtitleTextColor = color;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextColor(color);
        }
    }

    @Nullable
    public CharSequence getNavigationContentDescription() {
        return this.mNavButtonView == null ? null : this.mNavButtonView.getContentDescription();
    }

    public void setNavigationContentDescription(@StringRes int i) {
        int resId = i;
        int i2 = resId;
        setNavigationContentDescription(resId == 0 ? null : getContext().getText(resId));
    }

    public void setNavigationContentDescription(@Nullable CharSequence charSequence) {
        CharSequence description = charSequence;
        CharSequence charSequence2 = description;
        if (!TextUtils.isEmpty(description)) {
            ensureNavButtonView();
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setContentDescription(description);
        }
    }

    public void setNavigationIcon(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setNavigationIcon(AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setNavigationIcon(@Nullable Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        if (icon != null) {
            ensureNavButtonView();
            if (!isChildOrHidden(this.mNavButtonView)) {
                addSystemView(this.mNavButtonView, true);
            }
        } else if (this.mNavButtonView != null && isChildOrHidden(this.mNavButtonView)) {
            removeView(this.mNavButtonView);
            boolean remove = this.mHiddenViews.remove(this.mNavButtonView);
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setImageDrawable(icon);
        }
    }

    @Nullable
    public Drawable getNavigationIcon() {
        return this.mNavButtonView == null ? null : this.mNavButtonView.getDrawable();
    }

    public void setNavigationOnClickListener(OnClickListener onClickListener) {
        OnClickListener listener = onClickListener;
        OnClickListener onClickListener2 = listener;
        ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(listener);
    }

    public Menu getMenu() {
        ensureMenu();
        return this.mMenuView.getMenu();
    }

    public void setOverflowIcon(@Nullable Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        ensureMenu();
        this.mMenuView.setOverflowIcon(icon);
    }

    @Nullable
    public Drawable getOverflowIcon() {
        ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    private void ensureMenu() {
        ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder menu = (MenuBuilder) this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter(this);
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            menu.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        }
    }

    private void ensureMenuView() {
        if (this.mMenuView == null) {
            this.mMenuView = new ActionMenuView(getContext());
            this.mMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
            LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
            LayoutParams lp = generateDefaultLayoutParams;
            generateDefaultLayoutParams.gravity = 8388613 | (this.mButtonGravity & 112);
            this.mMenuView.setLayoutParams(lp);
            addSystemView(this.mMenuView, false);
        }
    }

    private MenuInflater getMenuInflater() {
        return new SupportMenuInflater(getContext());
    }

    public void inflateMenu(@MenuRes int i) {
        int resId = i;
        int i2 = resId;
        getMenuInflater().inflate(resId, getMenu());
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        OnMenuItemClickListener listener = onMenuItemClickListener;
        OnMenuItemClickListener onMenuItemClickListener2 = listener;
        this.mOnMenuItemClickListener = listener;
    }

    public void setContentInsetsRelative(int i, int i2) {
        int contentInsetStart = i;
        int contentInsetEnd = i2;
        int i3 = contentInsetStart;
        int i4 = contentInsetEnd;
        ensureContentInsets();
        this.mContentInsets.setRelative(contentInsetStart, contentInsetEnd);
    }

    public int getContentInsetStart() {
        return this.mContentInsets == null ? 0 : this.mContentInsets.getStart();
    }

    public int getContentInsetEnd() {
        return this.mContentInsets == null ? 0 : this.mContentInsets.getEnd();
    }

    public void setContentInsetsAbsolute(int i, int i2) {
        int contentInsetLeft = i;
        int contentInsetRight = i2;
        int i3 = contentInsetLeft;
        int i4 = contentInsetRight;
        ensureContentInsets();
        this.mContentInsets.setAbsolute(contentInsetLeft, contentInsetRight);
    }

    public int getContentInsetLeft() {
        return this.mContentInsets == null ? 0 : this.mContentInsets.getLeft();
    }

    public int getContentInsetRight() {
        return this.mContentInsets == null ? 0 : this.mContentInsets.getRight();
    }

    public int getContentInsetStartWithNavigation() {
        int i;
        if (this.mContentInsetStartWithNavigation == Integer.MIN_VALUE) {
            i = getContentInsetStart();
        } else {
            i = this.mContentInsetStartWithNavigation;
        }
        return i;
    }

    public void setContentInsetStartWithNavigation(int i) {
        int insetStartWithNavigation = i;
        int insetStartWithNavigation2 = insetStartWithNavigation;
        if (insetStartWithNavigation < 0) {
            insetStartWithNavigation2 = Integer.MIN_VALUE;
        }
        if (insetStartWithNavigation2 != this.mContentInsetStartWithNavigation) {
            this.mContentInsetStartWithNavigation = insetStartWithNavigation2;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getContentInsetEndWithActions() {
        int i;
        if (this.mContentInsetEndWithActions == Integer.MIN_VALUE) {
            i = getContentInsetEnd();
        } else {
            i = this.mContentInsetEndWithActions;
        }
        return i;
    }

    public void setContentInsetEndWithActions(int i) {
        int insetEndWithActions = i;
        int insetEndWithActions2 = insetEndWithActions;
        if (insetEndWithActions < 0) {
            insetEndWithActions2 = Integer.MIN_VALUE;
        }
        if (insetEndWithActions2 != this.mContentInsetEndWithActions) {
            this.mContentInsetEndWithActions = insetEndWithActions2;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getCurrentContentInsetStart() {
        int max;
        if (getNavigationIcon() == null) {
            max = getContentInsetStart();
        } else {
            max = Math.max(getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
        }
        return max;
    }

    public int getCurrentContentInsetEnd() {
        int max;
        boolean hasActions = false;
        if (this.mMenuView != null) {
            MenuBuilder peekMenu = this.mMenuView.peekMenu();
            hasActions = peekMenu != null && peekMenu.hasVisibleItems();
        }
        if (!hasActions) {
            max = getContentInsetEnd();
        } else {
            max = Math.max(getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0));
        }
        return max;
    }

    public int getCurrentContentInsetLeft() {
        int currentContentInsetEnd;
        if (ViewCompat.getLayoutDirection(this) != 1) {
            currentContentInsetEnd = getCurrentContentInsetStart();
        } else {
            currentContentInsetEnd = getCurrentContentInsetEnd();
        }
        return currentContentInsetEnd;
    }

    public int getCurrentContentInsetRight() {
        int currentContentInsetStart;
        if (ViewCompat.getLayoutDirection(this) != 1) {
            currentContentInsetStart = getCurrentContentInsetEnd();
        } else {
            currentContentInsetStart = getCurrentContentInsetStart();
        }
        return currentContentInsetStart;
    }

    private void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new AppCompatImageButton(getContext(), null, C0268R.attr.toolbarNavigationButtonStyle);
            LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
            LayoutParams lp = generateDefaultLayoutParams;
            generateDefaultLayoutParams.gravity = 8388611 | (this.mButtonGravity & 112);
            this.mNavButtonView.setLayoutParams(lp);
        }
    }

    /* access modifiers changed from: 0000 */
    public void ensureCollapseButtonView() {
        if (this.mCollapseButtonView == null) {
            this.mCollapseButtonView = new AppCompatImageButton(getContext(), null, C0268R.attr.toolbarNavigationButtonStyle);
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
            LayoutParams lp = generateDefaultLayoutParams;
            generateDefaultLayoutParams.gravity = 8388611 | (this.mButtonGravity & 112);
            lp.mViewType = 2;
            this.mCollapseButtonView.setLayoutParams(lp);
            this.mCollapseButtonView.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ Toolbar this$0;

                {
                    Toolbar this$02 = r5;
                    Toolbar toolbar = this$02;
                    this.this$0 = this$02;
                }

                public void onClick(View view) {
                    View view2 = view;
                    this.this$0.collapseActionView();
                }
            });
        }
    }

    private void addSystemView(View view, boolean z) {
        LayoutParams lp;
        View v = view;
        View view2 = v;
        boolean allowHide = z;
        android.view.ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        android.view.ViewGroup.LayoutParams vlp = layoutParams;
        if (layoutParams == null) {
            lp = generateDefaultLayoutParams();
        } else if (checkLayoutParams(vlp)) {
            lp = (LayoutParams) vlp;
        } else {
            lp = generateLayoutParams(vlp);
        }
        lp.mViewType = 1;
        if (allowHide && this.mExpandedActionView != null) {
            v.setLayoutParams(lp);
            boolean add = this.mHiddenViews.add(v);
            return;
        }
        addView(v, lp);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        if (!(this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null)) {
            state.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        state.isOverflowOpen = isOverflowMenuShowing();
        return state;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            MenuBuilder peekMenu = this.mMenuView == null ? null : this.mMenuView.peekMenu();
            if (!(ss.expandedMenuItemId == 0 || this.mExpandedMenuPresenter == null || peekMenu == null)) {
                MenuItem findItem = peekMenu.findItem(ss.expandedMenuItemId);
                MenuItem item = findItem;
                if (findItem != null) {
                    boolean expandActionView = MenuItemCompat.expandActionView(item);
                }
            }
            if (ss.isOverflowOpen) {
                postShowOverflowMenu();
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    private void postShowOverflowMenu() {
        boolean removeCallbacks = removeCallbacks(this.mShowOverflowMenuRunnable);
        boolean post = post(this.mShowOverflowMenuRunnable);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        boolean removeCallbacks = removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        int action = actionMasked;
        if (actionMasked == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean handled = super.onTouchEvent(ev);
            if (action == 0 && !handled) {
                this.mEatingTouch = true;
            }
        }
        if (action == 1 || action == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        int action = actionMasked;
        if (actionMasked == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean handled = super.onHoverEvent(ev);
            if (action == 9 && !handled) {
                this.mEatingHover = true;
            }
        }
        if (action == 10 || action == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    private void measureChildConstrained(View view, int i, int i2, int i3, int i4, int i5) {
        View child = view;
        int parentWidthSpec = i;
        int widthUsed = i2;
        int parentHeightSpec = i3;
        int heightUsed = i4;
        int heightConstraint = i5;
        View view2 = child;
        int i6 = parentWidthSpec;
        int i7 = widthUsed;
        int i8 = parentHeightSpec;
        int i9 = heightUsed;
        int i10 = heightConstraint;
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
        MarginLayoutParams marginLayoutParams2 = marginLayoutParams;
        int childWidthSpec = getChildMeasureSpec(parentWidthSpec, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + widthUsed, marginLayoutParams.width);
        int childMeasureSpec = getChildMeasureSpec(parentHeightSpec, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + heightUsed, marginLayoutParams.height);
        int childHeightSpec = childMeasureSpec;
        int mode = MeasureSpec.getMode(childMeasureSpec);
        int childHeightMode = mode;
        if (mode != 1073741824 && heightConstraint >= 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(childHeightMode == 0 ? heightConstraint : Math.min(MeasureSpec.getSize(childHeightSpec), heightConstraint), 1073741824);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private int measureChildCollapseMargins(View view, int i, int i2, int i3, int i4, int[] iArr) {
        View child = view;
        int parentWidthMeasureSpec = i;
        int widthUsed = i2;
        int parentHeightMeasureSpec = i3;
        int heightUsed = i4;
        int[] collapsingMargins = iArr;
        View view2 = child;
        int i5 = parentWidthMeasureSpec;
        int i6 = widthUsed;
        int i7 = parentHeightMeasureSpec;
        int i8 = heightUsed;
        int[] iArr2 = collapsingMargins;
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
        MarginLayoutParams lp = marginLayoutParams;
        int leftDiff = marginLayoutParams.leftMargin - collapsingMargins[0];
        int rightDiff = lp.rightMargin - collapsingMargins[1];
        int hMargins = Math.max(0, leftDiff) + Math.max(0, rightDiff);
        collapsingMargins[0] = Math.max(0, -leftDiff);
        collapsingMargins[1] = Math.max(0, -rightDiff);
        int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, getPaddingLeft() + getPaddingRight() + hMargins + widthUsed, lp.width);
        int childMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec, getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin + heightUsed, lp.height);
        int i9 = childMeasureSpec;
        child.measure(childWidthMeasureSpec, childMeasureSpec);
        return child.getMeasuredWidth() + hMargins;
    }

    private boolean shouldCollapse() {
        if (!this.mCollapsible) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (shouldLayout(child) && child.getMeasuredWidth() > 0 && child.getMeasuredHeight() > 0) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        char c;
        char c2;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        int height = 0;
        int childState = 0;
        int[] collapsingMargins = this.mTempMargins;
        if (!ViewUtils.isLayoutRtl(this)) {
            c = 0;
            c2 = 1;
        } else {
            c = 1;
            c2 = 0;
        }
        int navWidth = 0;
        if (shouldLayout(this.mNavButtonView)) {
            measureChildConstrained(this.mNavButtonView, widthMeasureSpec, 0, heightMeasureSpec, 0, this.mMaxButtonHeight);
            navWidth = this.mNavButtonView.getMeasuredWidth() + getHorizontalMargins(this.mNavButtonView);
            height = Math.max(0, this.mNavButtonView.getMeasuredHeight() + getVerticalMargins(this.mNavButtonView));
            childState = ViewUtils.combineMeasuredStates(0, ViewCompat.getMeasuredState(this.mNavButtonView));
        }
        if (shouldLayout(this.mCollapseButtonView)) {
            measureChildConstrained(this.mCollapseButtonView, widthMeasureSpec, 0, heightMeasureSpec, 0, this.mMaxButtonHeight);
            navWidth = this.mCollapseButtonView.getMeasuredWidth() + getHorizontalMargins(this.mCollapseButtonView);
            height = Math.max(height, this.mCollapseButtonView.getMeasuredHeight() + getVerticalMargins(this.mCollapseButtonView));
            childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(this.mCollapseButtonView));
        }
        int contentInsetStart = getCurrentContentInsetStart();
        int width = 0 + Math.max(contentInsetStart, navWidth);
        collapsingMargins[c] = Math.max(0, contentInsetStart - navWidth);
        int menuWidth = 0;
        if (shouldLayout(this.mMenuView)) {
            measureChildConstrained(this.mMenuView, widthMeasureSpec, width, heightMeasureSpec, 0, this.mMaxButtonHeight);
            menuWidth = this.mMenuView.getMeasuredWidth() + getHorizontalMargins(this.mMenuView);
            height = Math.max(height, this.mMenuView.getMeasuredHeight() + getVerticalMargins(this.mMenuView));
            childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(this.mMenuView));
        }
        int contentInsetEnd = getCurrentContentInsetEnd();
        int width2 = width + Math.max(contentInsetEnd, menuWidth);
        collapsingMargins[c2] = Math.max(0, contentInsetEnd - menuWidth);
        if (shouldLayout(this.mExpandedActionView)) {
            width2 += measureChildCollapseMargins(this.mExpandedActionView, widthMeasureSpec, width2, heightMeasureSpec, 0, collapsingMargins);
            height = Math.max(height, this.mExpandedActionView.getMeasuredHeight() + getVerticalMargins(this.mExpandedActionView));
            childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(this.mExpandedActionView));
        }
        if (shouldLayout(this.mLogoView)) {
            width2 += measureChildCollapseMargins(this.mLogoView, widthMeasureSpec, width2, heightMeasureSpec, 0, collapsingMargins);
            height = Math.max(height, this.mLogoView.getMeasuredHeight() + getVerticalMargins(this.mLogoView));
            childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(this.mLogoView));
        }
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            View child = childAt;
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            if (layoutParams.mViewType == 0 && shouldLayout(child)) {
                width2 += measureChildCollapseMargins(child, widthMeasureSpec, width2, heightMeasureSpec, 0, collapsingMargins);
                height = Math.max(height, child.getMeasuredHeight() + getVerticalMargins(child));
                childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(child));
            }
        }
        int titleWidth = 0;
        int titleHeight = 0;
        int titleVertMargins = this.mTitleMarginTop + this.mTitleMarginBottom;
        int titleHorizMargins = this.mTitleMarginStart + this.mTitleMarginEnd;
        if (shouldLayout(this.mTitleTextView)) {
            int titleWidth2 = measureChildCollapseMargins(this.mTitleTextView, widthMeasureSpec, width2 + titleHorizMargins, heightMeasureSpec, titleVertMargins, collapsingMargins);
            titleWidth = this.mTitleTextView.getMeasuredWidth() + getHorizontalMargins(this.mTitleTextView);
            titleHeight = this.mTitleTextView.getMeasuredHeight() + getVerticalMargins(this.mTitleTextView);
            childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(this.mTitleTextView));
        }
        if (shouldLayout(this.mSubtitleTextView)) {
            titleWidth = Math.max(titleWidth, measureChildCollapseMargins(this.mSubtitleTextView, widthMeasureSpec, width2 + titleHorizMargins, heightMeasureSpec, titleHeight + titleVertMargins, collapsingMargins));
            titleHeight += this.mSubtitleTextView.getMeasuredHeight() + getVerticalMargins(this.mSubtitleTextView);
            childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(this.mSubtitleTextView));
        }
        int height2 = Math.max(height, titleHeight) + getPaddingTop() + getPaddingBottom();
        int measuredWidth = ViewCompat.resolveSizeAndState(Math.max(width2 + titleWidth + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), widthMeasureSpec, childState & ViewCompat.MEASURED_STATE_MASK);
        int resolveSizeAndState = ViewCompat.resolveSizeAndState(Math.max(height2, getSuggestedMinimumHeight()), heightMeasureSpec, childState << 16);
        int i6 = resolveSizeAndState;
        setMeasuredDimension(measuredWidth, !shouldCollapse() ? resolveSizeAndState : 0);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int titleTop;
        int t = i2;
        int b = i4;
        boolean z2 = z;
        int i5 = i;
        int i6 = t;
        int i7 = i3;
        int i8 = b;
        boolean isRtl = ViewCompat.getLayoutDirection(this) == 1;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int left = paddingLeft;
        int right = width - paddingRight;
        int[] iArr = this.mTempMargins;
        int[] collapsingMargins = iArr;
        int[] iArr2 = iArr;
        collapsingMargins[1] = 0;
        iArr2[0] = 0;
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        int alignmentHeight = minimumHeight < 0 ? 0 : Math.min(minimumHeight, b - t);
        if (shouldLayout(this.mNavButtonView)) {
            if (!isRtl) {
                left = layoutChildLeft(this.mNavButtonView, paddingLeft, collapsingMargins, alignmentHeight);
            } else {
                right = layoutChildRight(this.mNavButtonView, right, collapsingMargins, alignmentHeight);
            }
        }
        if (shouldLayout(this.mCollapseButtonView)) {
            if (!isRtl) {
                left = layoutChildLeft(this.mCollapseButtonView, left, collapsingMargins, alignmentHeight);
            } else {
                right = layoutChildRight(this.mCollapseButtonView, right, collapsingMargins, alignmentHeight);
            }
        }
        if (shouldLayout(this.mMenuView)) {
            if (!isRtl) {
                right = layoutChildRight(this.mMenuView, right, collapsingMargins, alignmentHeight);
            } else {
                left = layoutChildLeft(this.mMenuView, left, collapsingMargins, alignmentHeight);
            }
        }
        int contentInsetLeft = getCurrentContentInsetLeft();
        int currentContentInsetRight = getCurrentContentInsetRight();
        int contentInsetRight = currentContentInsetRight;
        collapsingMargins[0] = Math.max(0, contentInsetLeft - left);
        collapsingMargins[1] = Math.max(0, currentContentInsetRight - ((width - paddingRight) - right));
        int left2 = Math.max(left, contentInsetLeft);
        int right2 = Math.min(right, (width - paddingRight) - contentInsetRight);
        if (shouldLayout(this.mExpandedActionView)) {
            if (!isRtl) {
                left2 = layoutChildLeft(this.mExpandedActionView, left2, collapsingMargins, alignmentHeight);
            } else {
                right2 = layoutChildRight(this.mExpandedActionView, right2, collapsingMargins, alignmentHeight);
            }
        }
        if (shouldLayout(this.mLogoView)) {
            if (!isRtl) {
                left2 = layoutChildLeft(this.mLogoView, left2, collapsingMargins, alignmentHeight);
            } else {
                right2 = layoutChildRight(this.mLogoView, right2, collapsingMargins, alignmentHeight);
            }
        }
        boolean layoutTitle = shouldLayout(this.mTitleTextView);
        boolean layoutSubtitle = shouldLayout(this.mSubtitleTextView);
        int titleHeight = 0;
        if (layoutTitle) {
            LayoutParams layoutParams = (LayoutParams) this.mTitleTextView.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            titleHeight = 0 + layoutParams.topMargin + this.mTitleTextView.getMeasuredHeight() + layoutParams.bottomMargin;
        }
        if (layoutSubtitle) {
            LayoutParams layoutParams3 = (LayoutParams) this.mSubtitleTextView.getLayoutParams();
            LayoutParams layoutParams4 = layoutParams3;
            titleHeight += layoutParams3.topMargin + this.mSubtitleTextView.getMeasuredHeight() + layoutParams3.bottomMargin;
        }
        if (layoutTitle || layoutSubtitle) {
            LayoutParams toplp = (LayoutParams) (!layoutTitle ? this.mSubtitleTextView : this.mTitleTextView).getLayoutParams();
            LayoutParams bottomlp = (LayoutParams) (!layoutSubtitle ? this.mTitleTextView : this.mSubtitleTextView).getLayoutParams();
            boolean titleHasWidth = (layoutTitle && this.mTitleTextView.getMeasuredWidth() > 0) || (layoutSubtitle && this.mSubtitleTextView.getMeasuredWidth() > 0);
            switch (this.mGravity & 112) {
                case 48:
                    titleTop = getPaddingTop() + toplp.topMargin + this.mTitleMarginTop;
                    break;
                case 80:
                    titleTop = (((height - paddingBottom) - bottomlp.bottomMargin) - this.mTitleMarginBottom) - titleHeight;
                    break;
                default:
                    int i9 = (height - paddingTop) - paddingBottom;
                    int i10 = i9;
                    int i11 = (i9 - titleHeight) / 2;
                    int spaceAbove = i11;
                    if (i11 >= toplp.topMargin + this.mTitleMarginTop) {
                        int i12 = (((height - paddingBottom) - titleHeight) - spaceAbove) - paddingTop;
                        int spaceBelow = i12;
                        if (i12 < toplp.bottomMargin + this.mTitleMarginBottom) {
                            spaceAbove = Math.max(0, spaceAbove - ((bottomlp.bottomMargin + this.mTitleMarginBottom) - spaceBelow));
                        }
                    } else {
                        spaceAbove = toplp.topMargin + this.mTitleMarginTop;
                    }
                    titleTop = paddingTop + spaceAbove;
                    break;
            }
            if (!isRtl) {
                int ld = (!titleHasWidth ? 0 : this.mTitleMarginStart) - collapsingMargins[0];
                int max = left2 + Math.max(0, ld);
                left2 = max;
                collapsingMargins[0] = Math.max(0, -ld);
                int titleLeft = max;
                int subtitleLeft = left2;
                if (layoutTitle) {
                    LayoutParams lp = (LayoutParams) this.mTitleTextView.getLayoutParams();
                    int titleRight = titleLeft + this.mTitleTextView.getMeasuredWidth();
                    int measuredHeight = titleTop + this.mTitleTextView.getMeasuredHeight();
                    int titleBottom = measuredHeight;
                    this.mTitleTextView.layout(titleLeft, titleTop, titleRight, measuredHeight);
                    titleLeft = titleRight + this.mTitleMarginEnd;
                    titleTop = titleBottom + lp.bottomMargin;
                }
                if (layoutSubtitle) {
                    LayoutParams layoutParams5 = (LayoutParams) this.mSubtitleTextView.getLayoutParams();
                    LayoutParams lp2 = layoutParams5;
                    int titleTop2 = titleTop + layoutParams5.topMargin;
                    int subtitleRight = left2 + this.mSubtitleTextView.getMeasuredWidth();
                    int measuredHeight2 = titleTop2 + this.mSubtitleTextView.getMeasuredHeight();
                    int subtitleBottom = measuredHeight2;
                    this.mSubtitleTextView.layout(left2, titleTop2, subtitleRight, measuredHeight2);
                    subtitleLeft = subtitleRight + this.mTitleMarginEnd;
                    int titleTop3 = subtitleBottom + lp2.bottomMargin;
                }
                if (titleHasWidth) {
                    left2 = Math.max(titleLeft, subtitleLeft);
                }
            } else {
                int rd = (!titleHasWidth ? 0 : this.mTitleMarginStart) - collapsingMargins[1];
                int max2 = right2 - Math.max(0, rd);
                right2 = max2;
                collapsingMargins[1] = Math.max(0, -rd);
                int titleRight2 = max2;
                int subtitleRight2 = right2;
                if (layoutTitle) {
                    LayoutParams lp3 = (LayoutParams) this.mTitleTextView.getLayoutParams();
                    int titleLeft2 = titleRight2 - this.mTitleTextView.getMeasuredWidth();
                    int measuredHeight3 = titleTop + this.mTitleTextView.getMeasuredHeight();
                    int titleBottom2 = measuredHeight3;
                    this.mTitleTextView.layout(titleLeft2, titleTop, titleRight2, measuredHeight3);
                    titleRight2 = titleLeft2 - this.mTitleMarginEnd;
                    titleTop = titleBottom2 + lp3.bottomMargin;
                }
                if (layoutSubtitle) {
                    LayoutParams layoutParams6 = (LayoutParams) this.mSubtitleTextView.getLayoutParams();
                    LayoutParams lp4 = layoutParams6;
                    int titleTop4 = titleTop + layoutParams6.topMargin;
                    int measuredHeight4 = titleTop4 + this.mSubtitleTextView.getMeasuredHeight();
                    int subtitleBottom2 = measuredHeight4;
                    this.mSubtitleTextView.layout(right2 - this.mSubtitleTextView.getMeasuredWidth(), titleTop4, right2, measuredHeight4);
                    subtitleRight2 = right2 - this.mTitleMarginEnd;
                    int titleTop5 = subtitleBottom2 + lp4.bottomMargin;
                }
                if (titleHasWidth) {
                    right2 = Math.min(titleRight2, subtitleRight2);
                }
            }
        }
        addCustomViewsWithGravity(this.mTempViews, 3);
        int leftViewsCount = this.mTempViews.size();
        for (int i13 = 0; i13 < leftViewsCount; i13++) {
            left2 = layoutChildLeft((View) this.mTempViews.get(i13), left2, collapsingMargins, alignmentHeight);
        }
        addCustomViewsWithGravity(this.mTempViews, 5);
        int i14 = this.mTempViews.size();
        for (int i15 = 0; i15 < i14; i15++) {
            right2 = layoutChildRight((View) this.mTempViews.get(i15), right2, collapsingMargins, alignmentHeight);
        }
        addCustomViewsWithGravity(this.mTempViews, 1);
        int centerViewsWidth = getViewListMeasuredWidth(this.mTempViews, collapsingMargins);
        int i16 = (paddingLeft + (((width - paddingLeft) - paddingRight) / 2)) - (centerViewsWidth / 2);
        int centerLeft = i16;
        int centerRight = i16 + centerViewsWidth;
        if (centerLeft < left2) {
            centerLeft = left2;
        } else if (centerRight > right2) {
            centerLeft -= centerRight - right2;
        }
        int centerViewsCount = this.mTempViews.size();
        for (int i17 = 0; i17 < centerViewsCount; i17++) {
            centerLeft = layoutChildLeft((View) this.mTempViews.get(i17), centerLeft, collapsingMargins, alignmentHeight);
        }
        this.mTempViews.clear();
    }

    private int getViewListMeasuredWidth(List<View> list, int[] iArr) {
        List<View> views = list;
        int[] collapsingMargins = iArr;
        List<View> list2 = views;
        int[] iArr2 = collapsingMargins;
        int collapseLeft = collapsingMargins[0];
        int collapseRight = collapsingMargins[1];
        int width = 0;
        int count = views.size();
        for (int i = 0; i < count; i++) {
            View view = (View) views.get(i);
            View v = view;
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int l = layoutParams.leftMargin - collapseLeft;
            int r = layoutParams.rightMargin - collapseRight;
            int leftMargin = Math.max(0, l);
            int rightMargin = Math.max(0, r);
            collapseLeft = Math.max(0, -l);
            collapseRight = Math.max(0, -r);
            width += leftMargin + v.getMeasuredWidth() + rightMargin;
        }
        return width;
    }

    private int layoutChildLeft(View view, int i, int[] iArr, int i2) {
        View child = view;
        int left = i;
        int[] collapsingMargins = iArr;
        int alignmentHeight = i2;
        View view2 = child;
        int i3 = left;
        int[] iArr2 = collapsingMargins;
        int i4 = alignmentHeight;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        LayoutParams lp = layoutParams;
        int l = layoutParams.leftMargin - collapsingMargins[0];
        int left2 = left + Math.max(0, l);
        collapsingMargins[0] = Math.max(0, -l);
        int top = getChildTop(child, alignmentHeight);
        int measuredWidth = child.getMeasuredWidth();
        int childWidth = measuredWidth;
        child.layout(left2, top, left2 + measuredWidth, top + child.getMeasuredHeight());
        int i5 = left2 + childWidth + lp.rightMargin;
        int left3 = i5;
        return i5;
    }

    private int layoutChildRight(View view, int i, int[] iArr, int i2) {
        View child = view;
        int right = i;
        int[] collapsingMargins = iArr;
        int alignmentHeight = i2;
        View view2 = child;
        int i3 = right;
        int[] iArr2 = collapsingMargins;
        int i4 = alignmentHeight;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        LayoutParams lp = layoutParams;
        int r = layoutParams.rightMargin - collapsingMargins[1];
        int right2 = right - Math.max(0, r);
        collapsingMargins[1] = Math.max(0, -r);
        int top = getChildTop(child, alignmentHeight);
        int measuredWidth = child.getMeasuredWidth();
        int childWidth = measuredWidth;
        child.layout(right2 - measuredWidth, top, right2, top + child.getMeasuredHeight());
        int i5 = right2 - (childWidth + lp.leftMargin);
        int right3 = i5;
        return i5;
    }

    private int getChildTop(View view, int i) {
        View child = view;
        int alignmentHeight = i;
        View view2 = child;
        int i2 = alignmentHeight;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childHeight = child.getMeasuredHeight();
        int alignmentOffset = alignmentHeight <= 0 ? 0 : (childHeight - alignmentHeight) / 2;
        switch (getChildVerticalGravity(lp.gravity)) {
            case 48:
                return getPaddingTop() - alignmentOffset;
            case 80:
                return (((getHeight() - getPaddingBottom()) - childHeight) - lp.bottomMargin) - alignmentOffset;
            default:
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                int height = getHeight();
                int height2 = height;
                int i3 = (height - paddingTop) - paddingBottom;
                int i4 = i3;
                int i5 = (i3 - childHeight) / 2;
                int spaceAbove = i5;
                if (i5 >= lp.topMargin) {
                    int i6 = (((height2 - paddingBottom) - childHeight) - spaceAbove) - paddingTop;
                    int spaceBelow = i6;
                    if (i6 < lp.bottomMargin) {
                        spaceAbove = Math.max(0, spaceAbove - (lp.bottomMargin - spaceBelow));
                    }
                } else {
                    spaceAbove = lp.topMargin;
                }
                return paddingTop + spaceAbove;
        }
    }

    private int getChildVerticalGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        int i3 = gravity & 112;
        int vgrav = i3;
        switch (i3) {
            case 16:
            case 48:
            case 80:
                return vgrav;
            default:
                return this.mGravity & 112;
        }
    }

    private void addCustomViewsWithGravity(List<View> list, int i) {
        List<View> views = list;
        int gravity = i;
        List<View> list2 = views;
        int i2 = gravity;
        boolean isRtl = ViewCompat.getLayoutDirection(this) == 1;
        int childCount = getChildCount();
        int absGrav = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this));
        views.clear();
        if (!isRtl) {
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                View child = childAt;
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (layoutParams.mViewType == 0 && shouldLayout(child) && getChildHorizontalGravity(lp.gravity) == absGrav) {
                    boolean add = views.add(child);
                }
            }
            return;
        }
        for (int i4 = childCount - 1; i4 >= 0; i4--) {
            View childAt2 = getChildAt(i4);
            View child2 = childAt2;
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
            LayoutParams lp2 = layoutParams2;
            if (layoutParams2.mViewType == 0 && shouldLayout(child2) && getChildHorizontalGravity(lp2.gravity) == absGrav) {
                boolean add2 = views.add(child2);
            }
        }
    }

    private int getChildHorizontalGravity(int i) {
        int i2;
        int gravity = i;
        int i3 = gravity;
        int ld = ViewCompat.getLayoutDirection(this);
        int absoluteGravity = GravityCompat.getAbsoluteGravity(gravity, ld);
        int i4 = absoluteGravity;
        int i5 = absoluteGravity & 7;
        int hGrav = i5;
        switch (i5) {
            case 1:
            case 3:
            case 5:
                return hGrav;
            default:
                if (ld != 1) {
                    i2 = 3;
                } else {
                    i2 = 5;
                }
                return i2;
        }
    }

    private boolean shouldLayout(View view) {
        View view2 = view;
        View view3 = view2;
        return (view2 == null || view2.getParent() != this || view2.getVisibility() == 8) ? false : true;
    }

    private int getHorizontalMargins(View view) {
        View v = view;
        View view2 = v;
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) v.getLayoutParams();
        return MarginLayoutParamsCompat.getMarginStart(marginLayoutParams) + MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams);
    }

    private int getVerticalMargins(View view) {
        View v = view;
        View view2 = v;
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) v.getLayoutParams();
        return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AttributeSet attrs = attributeSet;
        AttributeSet attributeSet2 = attrs;
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        if (p instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) p);
        }
        if (p instanceof android.support.p003v7.app.ActionBar.LayoutParams) {
            return new LayoutParams((android.support.p003v7.app.ActionBar.LayoutParams) p);
        }
        if (!(p instanceof MarginLayoutParams)) {
            return new LayoutParams(p);
        }
        return new LayoutParams((MarginLayoutParams) p);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return super.checkLayoutParams(p) && (p instanceof LayoutParams);
    }

    private static boolean isCustomView(View view) {
        View child = view;
        View view2 = child;
        return ((LayoutParams) child.getLayoutParams()).mViewType == 0;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public DecorToolbar getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }

    /* access modifiers changed from: 0000 */
    public void removeChildrenForExpandedActionView() {
        int childCount = getChildCount();
        int i = childCount;
        for (int i2 = childCount - 1; i2 >= 0; i2--) {
            View childAt = getChildAt(i2);
            View child = childAt;
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            if (!(layoutParams.mViewType == 2 || child == this.mMenuView)) {
                removeViewAt(i2);
                boolean add = this.mHiddenViews.add(child);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void addChildrenForExpandedActionView() {
        int size = this.mHiddenViews.size();
        int i = size;
        for (int i2 = size - 1; i2 >= 0; i2--) {
            addView((View) this.mHiddenViews.get(i2));
        }
        this.mHiddenViews.clear();
    }

    private boolean isChildOrHidden(View view) {
        View child = view;
        View view2 = child;
        return child.getParent() == this || this.mHiddenViews.contains(child);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setCollapsible(boolean z) {
        this.mCollapsible = z;
        requestLayout();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setMenuCallbacks(Callback callback, MenuBuilder.Callback callback2) {
        Callback pcb = callback;
        MenuBuilder.Callback mcb = callback2;
        Callback callback3 = pcb;
        MenuBuilder.Callback callback4 = mcb;
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
        if (this.mMenuView != null) {
            this.mMenuView.setMenuCallbacks(pcb, mcb);
        }
    }

    private void ensureContentInsets() {
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
    }
}
