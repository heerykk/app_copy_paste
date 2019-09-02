package android.support.p003v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.app.FragmentActivity;
import android.support.p000v4.app.FragmentTransaction;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewPropertyAnimatorCompat;
import android.support.p000v4.view.ViewPropertyAnimatorListener;
import android.support.p000v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.p000v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.p003v7.app.ActionBar.LayoutParams;
import android.support.p003v7.app.ActionBar.OnMenuVisibilityListener;
import android.support.p003v7.app.ActionBar.OnNavigationListener;
import android.support.p003v7.app.ActionBar.Tab;
import android.support.p003v7.app.ActionBar.TabListener;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.view.ActionBarPolicy;
import android.support.p003v7.view.ActionMode;
import android.support.p003v7.view.ActionMode.Callback;
import android.support.p003v7.view.SupportMenuInflater;
import android.support.p003v7.view.ViewPropertyAnimatorCompatSet;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuPopupHelper;
import android.support.p003v7.view.menu.SubMenuBuilder;
import android.support.p003v7.widget.ActionBarContainer;
import android.support.p003v7.widget.ActionBarContextView;
import android.support.p003v7.widget.ActionBarOverlayLayout;
import android.support.p003v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback;
import android.support.p003v7.widget.DecorToolbar;
import android.support.p003v7.widget.ScrollingTabContainerView;
import android.support.p003v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.SpinnerAdapter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.app.WindowDecorActionBar */
public class WindowDecorActionBar extends ActionBar implements ActionBarVisibilityCallback {
    static final /* synthetic */ boolean $assertionsDisabled = (!WindowDecorActionBar.class.desiredAssertionStatus());
    private static final boolean ALLOW_SHOW_HIDE_ANIMATIONS;
    private static final long FADE_IN_DURATION_MS = 200;
    private static final long FADE_OUT_DURATION_MS = 100;
    private static final int INVALID_POSITION = -1;
    private static final String TAG = "WindowDecorActionBar";
    private static final Interpolator sHideInterpolator = new AccelerateInterpolator();
    private static final Interpolator sShowInterpolator = new DecelerateInterpolator();
    ActionModeImpl mActionMode;
    private Activity mActivity;
    ActionBarContainer mContainerView;
    boolean mContentAnimations = true;
    View mContentView;
    Context mContext;
    ActionBarContextView mContextView;
    private int mCurWindowVisibility = 0;
    ViewPropertyAnimatorCompatSet mCurrentShowAnim;
    DecorToolbar mDecorToolbar;
    ActionMode mDeferredDestroyActionMode;
    Callback mDeferredModeDestroyCallback;
    private Dialog mDialog;
    private boolean mDisplayHomeAsUpSet;
    private boolean mHasEmbeddedTabs;
    boolean mHiddenByApp;
    boolean mHiddenBySystem;
    final ViewPropertyAnimatorListener mHideListener = new ViewPropertyAnimatorListenerAdapter(this) {
        final /* synthetic */ WindowDecorActionBar this$0;

        {
            WindowDecorActionBar this$02 = r5;
            WindowDecorActionBar windowDecorActionBar = this$02;
            this.this$0 = this$02;
        }

        public void onAnimationEnd(View view) {
            View view2 = view;
            if (this.this$0.mContentAnimations && this.this$0.mContentView != null) {
                ViewCompat.setTranslationY(this.this$0.mContentView, 0.0f);
                ViewCompat.setTranslationY(this.this$0.mContainerView, 0.0f);
            }
            this.this$0.mContainerView.setVisibility(8);
            this.this$0.mContainerView.setTransitioning(false);
            this.this$0.mCurrentShowAnim = null;
            this.this$0.completeDeferredDestroyActionMode();
            if (this.this$0.mOverlayLayout != null) {
                ViewCompat.requestApplyInsets(this.this$0.mOverlayLayout);
            }
        }
    };
    boolean mHideOnContentScroll;
    private boolean mLastMenuVisibility;
    private ArrayList<OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList<>();
    private boolean mNowShowing = true;
    ActionBarOverlayLayout mOverlayLayout;
    private int mSavedTabPosition = -1;
    private TabImpl mSelectedTab;
    private boolean mShowHideAnimationEnabled;
    final ViewPropertyAnimatorListener mShowListener = new ViewPropertyAnimatorListenerAdapter(this) {
        final /* synthetic */ WindowDecorActionBar this$0;

        {
            WindowDecorActionBar this$02 = r5;
            WindowDecorActionBar windowDecorActionBar = this$02;
            this.this$0 = this$02;
        }

        public void onAnimationEnd(View view) {
            View view2 = view;
            this.this$0.mCurrentShowAnim = null;
            this.this$0.mContainerView.requestLayout();
        }
    };
    private boolean mShowingForMode;
    ScrollingTabContainerView mTabScrollView;
    private ArrayList<TabImpl> mTabs = new ArrayList<>();
    private Context mThemedContext;
    final ViewPropertyAnimatorUpdateListener mUpdateListener = new ViewPropertyAnimatorUpdateListener(this) {
        final /* synthetic */ WindowDecorActionBar this$0;

        {
            WindowDecorActionBar this$02 = r5;
            WindowDecorActionBar windowDecorActionBar = this$02;
            this.this$0 = this$02;
        }

        public void onAnimationUpdate(View view) {
            View view2 = view;
            ViewParent parent = this.this$0.mContainerView.getParent();
            ViewParent viewParent = parent;
            ((View) parent).invalidate();
        }
    };

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.app.WindowDecorActionBar$ActionModeImpl */
    public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
        private final Context mActionModeContext;
        private Callback mCallback;
        private WeakReference<View> mCustomView;
        private final MenuBuilder mMenu;
        final /* synthetic */ WindowDecorActionBar this$0;

        public ActionModeImpl(WindowDecorActionBar windowDecorActionBar, Context context, Callback callback) {
            WindowDecorActionBar this$02 = windowDecorActionBar;
            Context context2 = context;
            Callback callback2 = callback;
            WindowDecorActionBar windowDecorActionBar2 = this$02;
            Context context3 = context2;
            Callback callback3 = callback2;
            this.this$0 = this$02;
            this.mActionModeContext = context2;
            this.mCallback = callback2;
            this.mMenu = new MenuBuilder(context2).setDefaultShowAsAction(1);
            this.mMenu.setCallback(this);
        }

        public MenuInflater getMenuInflater() {
            return new SupportMenuInflater(this.mActionModeContext);
        }

        public Menu getMenu() {
            return this.mMenu;
        }

        public void finish() {
            if (this.this$0.mActionMode == this) {
                if (WindowDecorActionBar.checkShowingFlags(this.this$0.mHiddenByApp, this.this$0.mHiddenBySystem, false)) {
                    this.mCallback.onDestroyActionMode(this);
                } else {
                    this.this$0.mDeferredDestroyActionMode = this;
                    this.this$0.mDeferredModeDestroyCallback = this.mCallback;
                }
                this.mCallback = null;
                this.this$0.animateToMode(false);
                this.this$0.mContextView.closeMode();
                this.this$0.mDecorToolbar.getViewGroup().sendAccessibilityEvent(32);
                this.this$0.mOverlayLayout.setHideOnContentScrollEnabled(this.this$0.mHideOnContentScroll);
                this.this$0.mActionMode = null;
            }
        }

        public void invalidate() {
            if (this.this$0.mActionMode == this) {
                this.mMenu.stopDispatchingItemsChanged();
                try {
                    boolean onPrepareActionMode = this.mCallback.onPrepareActionMode(this, this.mMenu);
                } finally {
                    this.mMenu.startDispatchingItemsChanged();
                }
            }
        }

        /* JADX INFO: finally extract failed */
        public boolean dispatchOnCreate() {
            this.mMenu.stopDispatchingItemsChanged();
            try {
                boolean onCreateActionMode = this.mCallback.onCreateActionMode(this, this.mMenu);
                this.mMenu.startDispatchingItemsChanged();
                return onCreateActionMode;
            } catch (Throwable th) {
                this.mMenu.startDispatchingItemsChanged();
                throw th;
            }
        }

        public void setCustomView(View view) {
            View view2 = view;
            View view3 = view2;
            this.this$0.mContextView.setCustomView(view2);
            this.mCustomView = new WeakReference<>(view2);
        }

        public void setSubtitle(CharSequence charSequence) {
            CharSequence subtitle = charSequence;
            CharSequence charSequence2 = subtitle;
            this.this$0.mContextView.setSubtitle(subtitle);
        }

        public void setTitle(CharSequence charSequence) {
            CharSequence title = charSequence;
            CharSequence charSequence2 = title;
            this.this$0.mContextView.setTitle(title);
        }

        public void setTitle(int i) {
            int resId = i;
            int i2 = resId;
            setTitle((CharSequence) this.this$0.mContext.getResources().getString(resId));
        }

        public void setSubtitle(int i) {
            int resId = i;
            int i2 = resId;
            setSubtitle((CharSequence) this.this$0.mContext.getResources().getString(resId));
        }

        public CharSequence getTitle() {
            return this.this$0.mContextView.getTitle();
        }

        public CharSequence getSubtitle() {
            return this.this$0.mContextView.getSubtitle();
        }

        public void setTitleOptionalHint(boolean z) {
            boolean titleOptional = z;
            super.setTitleOptionalHint(titleOptional);
            this.this$0.mContextView.setTitleOptional(titleOptional);
        }

        public boolean isTitleOptional() {
            return this.this$0.mContextView.isTitleOptional();
        }

        public View getCustomView() {
            return this.mCustomView == null ? null : (View) this.mCustomView.get();
        }

        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            MenuItem item = menuItem;
            MenuBuilder menuBuilder2 = menuBuilder;
            MenuItem menuItem2 = item;
            if (this.mCallback == null) {
                return false;
            }
            return this.mCallback.onActionItemClicked(this, item);
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder menuBuilder2 = menuBuilder;
            boolean z2 = z;
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            SubMenuBuilder subMenu = subMenuBuilder;
            SubMenuBuilder subMenuBuilder2 = subMenu;
            if (this.mCallback == null) {
                return false;
            }
            if (!subMenu.hasVisibleItems()) {
                return true;
            }
            new MenuPopupHelper(this.this$0.getThemedContext(), subMenu).show();
            return true;
        }

        public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
            SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
        }

        public void onMenuModeChange(MenuBuilder menuBuilder) {
            MenuBuilder menuBuilder2 = menuBuilder;
            if (this.mCallback != null) {
                invalidate();
                boolean showOverflowMenu = this.this$0.mContextView.showOverflowMenu();
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v7.app.WindowDecorActionBar$TabImpl */
    public class TabImpl extends Tab {
        private TabListener mCallback;
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        private int mPosition = -1;
        private Object mTag;
        private CharSequence mText;
        final /* synthetic */ WindowDecorActionBar this$0;

        public TabImpl(WindowDecorActionBar windowDecorActionBar) {
            WindowDecorActionBar this$02 = windowDecorActionBar;
            WindowDecorActionBar windowDecorActionBar2 = this$02;
            this.this$0 = this$02;
        }

        public Object getTag() {
            return this.mTag;
        }

        public Tab setTag(Object obj) {
            Object tag = obj;
            Object obj2 = tag;
            this.mTag = tag;
            return this;
        }

        public TabListener getCallback() {
            return this.mCallback;
        }

        public Tab setTabListener(TabListener tabListener) {
            TabListener callback = tabListener;
            TabListener tabListener2 = callback;
            this.mCallback = callback;
            return this;
        }

        public View getCustomView() {
            return this.mCustomView;
        }

        public Tab setCustomView(View view) {
            View view2 = view;
            View view3 = view2;
            this.mCustomView = view2;
            if (this.mPosition >= 0) {
                this.this$0.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        public Tab setCustomView(int i) {
            int layoutResId = i;
            int i2 = layoutResId;
            return setCustomView(LayoutInflater.from(this.this$0.getThemedContext()).inflate(layoutResId, null));
        }

        public Drawable getIcon() {
            return this.mIcon;
        }

        public int getPosition() {
            return this.mPosition;
        }

        public void setPosition(int i) {
            int position = i;
            int i2 = position;
            this.mPosition = position;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public Tab setIcon(Drawable drawable) {
            Drawable icon = drawable;
            Drawable drawable2 = icon;
            this.mIcon = icon;
            if (this.mPosition >= 0) {
                this.this$0.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        public Tab setIcon(int i) {
            int resId = i;
            int i2 = resId;
            return setIcon(AppCompatResources.getDrawable(this.this$0.mContext, resId));
        }

        public Tab setText(CharSequence charSequence) {
            CharSequence text = charSequence;
            CharSequence charSequence2 = text;
            this.mText = text;
            if (this.mPosition >= 0) {
                this.this$0.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        public Tab setText(int i) {
            int resId = i;
            int i2 = resId;
            return setText(this.this$0.mContext.getResources().getText(resId));
        }

        public void select() {
            this.this$0.selectTab(this);
        }

        public Tab setContentDescription(int i) {
            int resId = i;
            int i2 = resId;
            return setContentDescription(this.this$0.mContext.getResources().getText(resId));
        }

        public Tab setContentDescription(CharSequence charSequence) {
            CharSequence contentDesc = charSequence;
            CharSequence charSequence2 = contentDesc;
            this.mContentDesc = contentDesc;
            if (this.mPosition >= 0) {
                this.this$0.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }
    }

    static {
        boolean z;
        if (VERSION.SDK_INT < 14) {
            z = false;
        } else {
            z = true;
        }
        ALLOW_SHOW_HIDE_ANIMATIONS = z;
    }

    public WindowDecorActionBar(Activity activity, boolean z) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        boolean overlayMode = z;
        this.mActivity = activity2;
        Window window = activity2.getWindow();
        Window window2 = window;
        View decor = window.getDecorView();
        init(decor);
        if (!overlayMode) {
            this.mContentView = decor.findViewById(16908290);
        }
    }

    public WindowDecorActionBar(Dialog dialog) {
        Dialog dialog2 = dialog;
        Dialog dialog3 = dialog2;
        this.mDialog = dialog2;
        init(dialog2.getWindow().getDecorView());
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public WindowDecorActionBar(View view) {
        View layout = view;
        View view2 = layout;
        if (!$assertionsDisabled && !layout.isInEditMode()) {
            throw new AssertionError();
        }
        init(layout);
    }

    private void init(View view) {
        View decor = view;
        View view2 = decor;
        this.mOverlayLayout = (ActionBarOverlayLayout) decor.findViewById(C0268R.C0270id.decor_content_parent);
        if (this.mOverlayLayout != null) {
            this.mOverlayLayout.setActionBarVisibilityCallback(this);
        }
        this.mDecorToolbar = getDecorToolbar(decor.findViewById(C0268R.C0270id.action_bar));
        this.mContextView = (ActionBarContextView) decor.findViewById(C0268R.C0270id.action_context_bar);
        this.mContainerView = (ActionBarContainer) decor.findViewById(C0268R.C0270id.action_bar_container);
        if (this.mDecorToolbar == null || this.mContextView == null || this.mContainerView == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used " + "with a compatible window decor layout");
        }
        this.mContext = this.mDecorToolbar.getContext();
        int displayOptions = this.mDecorToolbar.getDisplayOptions();
        int i = displayOptions;
        boolean homeAsUp = (displayOptions & 4) != 0;
        if (homeAsUp) {
            this.mDisplayHomeAsUpSet = true;
        }
        ActionBarPolicy abp = ActionBarPolicy.get(this.mContext);
        setHomeButtonEnabled(abp.enableHomeButtonByDefault() || homeAsUp);
        setHasEmbeddedTabs(abp.hasEmbeddedTabs());
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, C0268R.styleable.ActionBar, C0268R.attr.actionBarStyle, 0);
        TypedArray a = obtainStyledAttributes;
        if (obtainStyledAttributes.getBoolean(C0268R.styleable.ActionBar_hideOnContentScroll, false)) {
            setHideOnContentScrollEnabled(true);
        }
        int dimensionPixelSize = a.getDimensionPixelSize(C0268R.styleable.ActionBar_elevation, 0);
        int elevation = dimensionPixelSize;
        if (dimensionPixelSize != 0) {
            setElevation((float) elevation);
        }
        a.recycle();
    }

    private DecorToolbar getDecorToolbar(View view) {
        View view2 = view;
        View view3 = view2;
        if (view2 instanceof DecorToolbar) {
            return (DecorToolbar) view2;
        }
        if (view2 instanceof Toolbar) {
            return ((Toolbar) view2).getWrapper();
        }
        throw new IllegalStateException(new StringBuilder().append("Can't make a decor toolbar out of ").append(view2).toString() == null ? "null" : view2.getClass().getSimpleName());
    }

    public void setElevation(float f) {
        float elevation = f;
        float f2 = elevation;
        ViewCompat.setElevation(this.mContainerView, elevation);
    }

    public float getElevation() {
        return ViewCompat.getElevation(this.mContainerView);
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration configuration2 = configuration;
        setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
    }

    private void setHasEmbeddedTabs(boolean z) {
        this.mHasEmbeddedTabs = z;
        if (this.mHasEmbeddedTabs) {
            this.mContainerView.setTabContainer(null);
            this.mDecorToolbar.setEmbeddedTabView(this.mTabScrollView);
        } else {
            this.mDecorToolbar.setEmbeddedTabView(null);
            this.mContainerView.setTabContainer(this.mTabScrollView);
        }
        boolean isInTabMode = getNavigationMode() == 2;
        if (this.mTabScrollView != null) {
            if (!isInTabMode) {
                this.mTabScrollView.setVisibility(8);
            } else {
                this.mTabScrollView.setVisibility(0);
                if (this.mOverlayLayout != null) {
                    ViewCompat.requestApplyInsets(this.mOverlayLayout);
                }
            }
        }
        this.mDecorToolbar.setCollapsible(!this.mHasEmbeddedTabs && isInTabMode);
        this.mOverlayLayout.setHasNonEmbeddedTabs(!this.mHasEmbeddedTabs && isInTabMode);
    }

    private void ensureTabsExist() {
        if (this.mTabScrollView == null) {
            ScrollingTabContainerView tabScroller = new ScrollingTabContainerView(this.mContext);
            if (!this.mHasEmbeddedTabs) {
                if (getNavigationMode() != 2) {
                    tabScroller.setVisibility(8);
                } else {
                    tabScroller.setVisibility(0);
                    if (this.mOverlayLayout != null) {
                        ViewCompat.requestApplyInsets(this.mOverlayLayout);
                    }
                }
                this.mContainerView.setTabContainer(tabScroller);
            } else {
                tabScroller.setVisibility(0);
                this.mDecorToolbar.setEmbeddedTabView(tabScroller);
            }
            this.mTabScrollView = tabScroller;
        }
    }

    /* access modifiers changed from: 0000 */
    public void completeDeferredDestroyActionMode() {
        if (this.mDeferredModeDestroyCallback != null) {
            this.mDeferredModeDestroyCallback.onDestroyActionMode(this.mDeferredDestroyActionMode);
            this.mDeferredDestroyActionMode = null;
            this.mDeferredModeDestroyCallback = null;
        }
    }

    public void onWindowVisibilityChanged(int i) {
        int visibility = i;
        int i2 = visibility;
        this.mCurWindowVisibility = visibility;
    }

    public void setShowHideAnimationEnabled(boolean z) {
        boolean enabled = z;
        this.mShowHideAnimationEnabled = enabled;
        if (!enabled && this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
    }

    public void addOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener) {
        OnMenuVisibilityListener listener = onMenuVisibilityListener;
        OnMenuVisibilityListener onMenuVisibilityListener2 = listener;
        boolean add = this.mMenuVisibilityListeners.add(listener);
    }

    public void removeOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener) {
        OnMenuVisibilityListener listener = onMenuVisibilityListener;
        OnMenuVisibilityListener onMenuVisibilityListener2 = listener;
        boolean remove = this.mMenuVisibilityListeners.remove(listener);
    }

    public void dispatchMenuVisibilityChanged(boolean z) {
        boolean isVisible = z;
        if (isVisible != this.mLastMenuVisibility) {
            this.mLastMenuVisibility = isVisible;
            int count = this.mMenuVisibilityListeners.size();
            for (int i = 0; i < count; i++) {
                ((OnMenuVisibilityListener) this.mMenuVisibilityListeners.get(i)).onMenuVisibilityChanged(isVisible);
            }
        }
    }

    public void setCustomView(int i) {
        int resId = i;
        int i2 = resId;
        setCustomView(LayoutInflater.from(getThemedContext()).inflate(resId, this.mDecorToolbar.getViewGroup(), false));
    }

    public void setDisplayUseLogoEnabled(boolean z) {
        setDisplayOptions(!z ? 0 : 1, 1);
    }

    public void setDisplayShowHomeEnabled(boolean z) {
        setDisplayOptions(!z ? 0 : 2, 2);
    }

    public void setDisplayHomeAsUpEnabled(boolean z) {
        setDisplayOptions(!z ? 0 : 4, 4);
    }

    public void setDisplayShowTitleEnabled(boolean z) {
        setDisplayOptions(!z ? 0 : 8, 8);
    }

    public void setDisplayShowCustomEnabled(boolean z) {
        setDisplayOptions(!z ? 0 : 16, 16);
    }

    public void setHomeButtonEnabled(boolean z) {
        this.mDecorToolbar.setHomeButtonEnabled(z);
    }

    public void setTitle(int i) {
        int resId = i;
        int i2 = resId;
        setTitle((CharSequence) this.mContext.getString(resId));
    }

    public void setSubtitle(int i) {
        int resId = i;
        int i2 = resId;
        setSubtitle((CharSequence) this.mContext.getString(resId));
    }

    public void setSelectedNavigationItem(int i) {
        int position = i;
        int i2 = position;
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                this.mDecorToolbar.setDropdownSelectedPosition(position);
                return;
            case 2:
                selectTab((Tab) this.mTabs.get(position));
                return;
            default:
                throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
    }

    public void removeAllTabs() {
        cleanupTabs();
    }

    private void cleanupTabs() {
        if (this.mSelectedTab != null) {
            selectTab(null);
        }
        this.mTabs.clear();
        if (this.mTabScrollView != null) {
            this.mTabScrollView.removeAllTabs();
        }
        this.mSavedTabPosition = -1;
    }

    public void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mDecorToolbar.setTitle(title);
    }

    public void setWindowTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mDecorToolbar.setWindowTitle(title);
    }

    public boolean requestFocus() {
        ViewGroup viewGroup = this.mDecorToolbar.getViewGroup();
        ViewGroup viewGroup2 = viewGroup;
        if (viewGroup == null || viewGroup2.hasFocus()) {
            return false;
        }
        boolean requestFocus = viewGroup2.requestFocus();
        return true;
    }

    public void setSubtitle(CharSequence charSequence) {
        CharSequence subtitle = charSequence;
        CharSequence charSequence2 = subtitle;
        this.mDecorToolbar.setSubtitle(subtitle);
    }

    public void setDisplayOptions(int i) {
        int options = i;
        int i2 = options;
        if ((options & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions(options);
    }

    public void setDisplayOptions(int i, int i2) {
        int options = i;
        int mask = i2;
        int i3 = options;
        int i4 = mask;
        int current = this.mDecorToolbar.getDisplayOptions();
        if ((mask & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions((options & mask) | (current & (mask ^ -1)));
    }

    public void setBackgroundDrawable(Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        this.mContainerView.setPrimaryBackground(d);
    }

    public void setStackedBackgroundDrawable(Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        this.mContainerView.setStackedBackground(d);
    }

    public void setSplitBackgroundDrawable(Drawable drawable) {
        Drawable drawable2 = drawable;
    }

    public View getCustomView() {
        return this.mDecorToolbar.getCustomView();
    }

    public CharSequence getTitle() {
        return this.mDecorToolbar.getTitle();
    }

    public CharSequence getSubtitle() {
        return this.mDecorToolbar.getSubtitle();
    }

    public int getNavigationMode() {
        return this.mDecorToolbar.getNavigationMode();
    }

    public int getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions();
    }

    public ActionMode startActionMode(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        if (this.mActionMode != null) {
            this.mActionMode.finish();
        }
        this.mOverlayLayout.setHideOnContentScrollEnabled(false);
        this.mContextView.killMode();
        ActionModeImpl actionModeImpl = new ActionModeImpl(this, this.mContextView.getContext(), callback2);
        ActionModeImpl mode = actionModeImpl;
        if (!actionModeImpl.dispatchOnCreate()) {
            return null;
        }
        this.mActionMode = mode;
        mode.invalidate();
        this.mContextView.initForMode(mode);
        animateToMode(true);
        this.mContextView.sendAccessibilityEvent(32);
        return mode;
    }

    private void configureTab(Tab tab, int i) {
        Tab tab2 = tab;
        int position = i;
        Tab tab3 = tab2;
        int i2 = position;
        TabImpl tabImpl = (TabImpl) tab2;
        TabImpl tabi = tabImpl;
        TabListener callback = tabImpl.getCallback();
        TabListener tabListener = callback;
        if (callback != null) {
            tabi.setPosition(position);
            this.mTabs.add(position, tabi);
            int count = this.mTabs.size();
            for (int i3 = position + 1; i3 < count; i3++) {
                ((TabImpl) this.mTabs.get(i3)).setPosition(i3);
            }
            return;
        }
        throw new IllegalStateException("Action Bar Tab must have a Callback");
    }

    public void addTab(Tab tab) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        addTab(tab2, this.mTabs.isEmpty());
    }

    public void addTab(Tab tab, int i) {
        Tab tab2 = tab;
        int position = i;
        Tab tab3 = tab2;
        int i2 = position;
        addTab(tab2, position, this.mTabs.isEmpty());
    }

    public void addTab(Tab tab, boolean z) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        boolean setSelected = z;
        ensureTabsExist();
        this.mTabScrollView.addTab(tab2, setSelected);
        configureTab(tab2, this.mTabs.size());
        if (setSelected) {
            selectTab(tab2);
        }
    }

    public void addTab(Tab tab, int i, boolean z) {
        Tab tab2 = tab;
        int position = i;
        Tab tab3 = tab2;
        int i2 = position;
        boolean setSelected = z;
        ensureTabsExist();
        this.mTabScrollView.addTab(tab2, position, setSelected);
        configureTab(tab2, position);
        if (setSelected) {
            selectTab(tab2);
        }
    }

    public Tab newTab() {
        return new TabImpl(this);
    }

    public void removeTab(Tab tab) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        removeTabAt(tab2.getPosition());
    }

    public void removeTabAt(int i) {
        int position = i;
        int i2 = position;
        if (this.mTabScrollView != null) {
            int selectedTabPosition = this.mSelectedTab == null ? this.mSavedTabPosition : this.mSelectedTab.getPosition();
            this.mTabScrollView.removeTabAt(position);
            TabImpl tabImpl = (TabImpl) this.mTabs.remove(position);
            TabImpl removedTab = tabImpl;
            if (tabImpl != null) {
                removedTab.setPosition(-1);
            }
            int newTabCount = this.mTabs.size();
            for (int i3 = position; i3 < newTabCount; i3++) {
                ((TabImpl) this.mTabs.get(i3)).setPosition(i3);
            }
            if (selectedTabPosition == position) {
                selectTab(!this.mTabs.isEmpty() ? (TabImpl) this.mTabs.get(Math.max(0, position - 1)) : null);
            }
        }
    }

    public void selectTab(Tab tab) {
        FragmentTransaction trans;
        Tab tab2 = tab;
        Tab tab3 = tab2;
        if (getNavigationMode() == 2) {
            if ((this.mActivity instanceof FragmentActivity) && !this.mDecorToolbar.getViewGroup().isInEditMode()) {
                trans = ((FragmentActivity) this.mActivity).getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
            } else {
                trans = null;
            }
            if (this.mSelectedTab != tab2) {
                this.mTabScrollView.setTabSelected(tab2 == null ? -1 : tab2.getPosition());
                if (this.mSelectedTab != null) {
                    this.mSelectedTab.getCallback().onTabUnselected(this.mSelectedTab, trans);
                }
                this.mSelectedTab = (TabImpl) tab2;
                if (this.mSelectedTab != null) {
                    this.mSelectedTab.getCallback().onTabSelected(this.mSelectedTab, trans);
                }
            } else if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabReselected(this.mSelectedTab, trans);
                this.mTabScrollView.animateToTab(tab2.getPosition());
            }
            if (trans != null && !trans.isEmpty()) {
                int commit = trans.commit();
            }
            return;
        }
        this.mSavedTabPosition = tab2 == null ? -1 : tab2.getPosition();
    }

    public Tab getSelectedTab() {
        return this.mSelectedTab;
    }

    public int getHeight() {
        return this.mContainerView.getHeight();
    }

    public void enableContentAnimations(boolean z) {
        this.mContentAnimations = z;
    }

    public void show() {
        if (this.mHiddenByApp) {
            this.mHiddenByApp = false;
            updateVisibility(false);
        }
    }

    private void showForActionMode() {
        if (!this.mShowingForMode) {
            this.mShowingForMode = true;
            if (this.mOverlayLayout != null) {
                this.mOverlayLayout.setShowingForActionMode(true);
            }
            updateVisibility(false);
        }
    }

    public void showForSystem() {
        if (this.mHiddenBySystem) {
            this.mHiddenBySystem = false;
            updateVisibility(true);
        }
    }

    public void hide() {
        if (!this.mHiddenByApp) {
            this.mHiddenByApp = true;
            updateVisibility(false);
        }
    }

    private void hideForActionMode() {
        if (this.mShowingForMode) {
            this.mShowingForMode = false;
            if (this.mOverlayLayout != null) {
                this.mOverlayLayout.setShowingForActionMode(false);
            }
            updateVisibility(false);
        }
    }

    public void hideForSystem() {
        if (!this.mHiddenBySystem) {
            this.mHiddenBySystem = true;
            updateVisibility(true);
        }
    }

    public void setHideOnContentScrollEnabled(boolean z) {
        boolean hideOnContentScroll = z;
        if (hideOnContentScroll && !this.mOverlayLayout.isInOverlayMode()) {
            throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
        }
        this.mHideOnContentScroll = hideOnContentScroll;
        this.mOverlayLayout.setHideOnContentScrollEnabled(hideOnContentScroll);
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.mOverlayLayout.isHideOnContentScrollEnabled();
    }

    public int getHideOffset() {
        return this.mOverlayLayout.getActionBarHideOffset();
    }

    public void setHideOffset(int i) {
        int offset = i;
        int i2 = offset;
        if (offset != 0 && !this.mOverlayLayout.isInOverlayMode()) {
            throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
        }
        this.mOverlayLayout.setActionBarHideOffset(offset);
    }

    static boolean checkShowingFlags(boolean z, boolean z2, boolean z3) {
        boolean hiddenByApp = z;
        boolean hiddenBySystem = z2;
        if (z3) {
            return true;
        }
        if (!hiddenByApp && !hiddenBySystem) {
            return true;
        }
        return false;
    }

    private void updateVisibility(boolean z) {
        boolean fromSystem = z;
        boolean checkShowingFlags = checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode);
        boolean z2 = checkShowingFlags;
        if (!checkShowingFlags) {
            if (this.mNowShowing) {
                this.mNowShowing = false;
                doHide(fromSystem);
            }
        } else if (!this.mNowShowing) {
            this.mNowShowing = true;
            doShow(fromSystem);
        }
    }

    public void doShow(boolean z) {
        boolean fromSystem = z;
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
        this.mContainerView.setVisibility(0);
        if (this.mCurWindowVisibility == 0 && ALLOW_SHOW_HIDE_ANIMATIONS && (this.mShowHideAnimationEnabled || fromSystem)) {
            ViewCompat.setTranslationY(this.mContainerView, 0.0f);
            float f = (float) (-this.mContainerView.getHeight());
            float startingY = f;
            if (fromSystem) {
                int[] iArr = new int[2];
                iArr[0] = 0;
                iArr[1] = 0;
                int[] topLeft = iArr;
                this.mContainerView.getLocationInWindow(topLeft);
                startingY = f - ((float) topLeft[1]);
            }
            ViewCompat.setTranslationY(this.mContainerView, startingY);
            ViewPropertyAnimatorCompatSet anim = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompat translationY = ViewCompat.animate(this.mContainerView).translationY(0.0f);
            ViewPropertyAnimatorCompat a = translationY;
            ViewPropertyAnimatorCompat updateListener = translationY.setUpdateListener(this.mUpdateListener);
            ViewPropertyAnimatorCompatSet play = anim.play(a);
            if (this.mContentAnimations && this.mContentView != null) {
                ViewCompat.setTranslationY(this.mContentView, startingY);
                ViewPropertyAnimatorCompatSet play2 = anim.play(ViewCompat.animate(this.mContentView).translationY(0.0f));
            }
            ViewPropertyAnimatorCompatSet interpolator = anim.setInterpolator(sShowInterpolator);
            ViewPropertyAnimatorCompatSet duration = anim.setDuration(250);
            ViewPropertyAnimatorCompatSet listener = anim.setListener(this.mShowListener);
            this.mCurrentShowAnim = anim;
            anim.start();
        } else {
            ViewCompat.setAlpha(this.mContainerView, 1.0f);
            ViewCompat.setTranslationY(this.mContainerView, 0.0f);
            if (this.mContentAnimations && this.mContentView != null) {
                ViewCompat.setTranslationY(this.mContentView, 0.0f);
            }
            this.mShowListener.onAnimationEnd(null);
        }
        if (this.mOverlayLayout != null) {
            ViewCompat.requestApplyInsets(this.mOverlayLayout);
        }
    }

    public void doHide(boolean z) {
        boolean fromSystem = z;
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
        if (this.mCurWindowVisibility == 0 && ALLOW_SHOW_HIDE_ANIMATIONS && (this.mShowHideAnimationEnabled || fromSystem)) {
            ViewCompat.setAlpha(this.mContainerView, 1.0f);
            this.mContainerView.setTransitioning(true);
            ViewPropertyAnimatorCompatSet anim = new ViewPropertyAnimatorCompatSet();
            float f = (float) (-this.mContainerView.getHeight());
            float endingY = f;
            if (fromSystem) {
                int[] iArr = new int[2];
                iArr[0] = 0;
                iArr[1] = 0;
                int[] topLeft = iArr;
                this.mContainerView.getLocationInWindow(topLeft);
                endingY = f - ((float) topLeft[1]);
            }
            ViewPropertyAnimatorCompat translationY = ViewCompat.animate(this.mContainerView).translationY(endingY);
            ViewPropertyAnimatorCompat a = translationY;
            ViewPropertyAnimatorCompat updateListener = translationY.setUpdateListener(this.mUpdateListener);
            ViewPropertyAnimatorCompatSet play = anim.play(a);
            if (this.mContentAnimations && this.mContentView != null) {
                ViewPropertyAnimatorCompatSet play2 = anim.play(ViewCompat.animate(this.mContentView).translationY(endingY));
            }
            ViewPropertyAnimatorCompatSet interpolator = anim.setInterpolator(sHideInterpolator);
            ViewPropertyAnimatorCompatSet duration = anim.setDuration(250);
            ViewPropertyAnimatorCompatSet listener = anim.setListener(this.mHideListener);
            this.mCurrentShowAnim = anim;
            anim.start();
            return;
        }
        this.mHideListener.onAnimationEnd(null);
    }

    public boolean isShowing() {
        int height = getHeight();
        return this.mNowShowing && (height == 0 || getHideOffset() < height);
    }

    public void animateToMode(boolean z) {
        ViewPropertyAnimatorCompat fadeOut;
        ViewPropertyAnimatorCompat fadeIn;
        boolean toActionMode = z;
        if (!toActionMode) {
            hideForActionMode();
        } else {
            showForActionMode();
        }
        if (shouldAnimateContextView()) {
            if (!toActionMode) {
                fadeIn = this.mDecorToolbar.setupAnimatorToVisibility(0, FADE_IN_DURATION_MS);
                fadeOut = this.mContextView.setupAnimatorToVisibility(8, FADE_OUT_DURATION_MS);
            } else {
                fadeOut = this.mDecorToolbar.setupAnimatorToVisibility(4, FADE_OUT_DURATION_MS);
                fadeIn = this.mContextView.setupAnimatorToVisibility(0, FADE_IN_DURATION_MS);
            }
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompatSet set = viewPropertyAnimatorCompatSet;
            ViewPropertyAnimatorCompatSet playSequentially = viewPropertyAnimatorCompatSet.playSequentially(fadeOut, fadeIn);
            set.start();
        } else if (!toActionMode) {
            this.mDecorToolbar.setVisibility(0);
            this.mContextView.setVisibility(8);
        } else {
            this.mDecorToolbar.setVisibility(4);
            this.mContextView.setVisibility(0);
        }
    }

    private boolean shouldAnimateContextView() {
        return ViewCompat.isLaidOut(this.mContainerView);
    }

    public Context getThemedContext() {
        if (this.mThemedContext == null) {
            TypedValue outValue = new TypedValue();
            Theme theme = this.mContext.getTheme();
            Theme theme2 = theme;
            boolean resolveAttribute = theme.resolveAttribute(C0268R.attr.actionBarWidgetTheme, outValue, true);
            int i = outValue.resourceId;
            int targetThemeRes = i;
            if (i == 0) {
                this.mThemedContext = this.mContext;
            } else {
                this.mThemedContext = new ContextThemeWrapper(this.mContext, targetThemeRes);
            }
        }
        return this.mThemedContext;
    }

    public boolean isTitleTruncated() {
        return this.mDecorToolbar != null && this.mDecorToolbar.isTitleTruncated();
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        Drawable indicator = drawable;
        Drawable drawable2 = indicator;
        this.mDecorToolbar.setNavigationIcon(indicator);
    }

    public void setHomeAsUpIndicator(int i) {
        int resId = i;
        int i2 = resId;
        this.mDecorToolbar.setNavigationIcon(resId);
    }

    public void setHomeActionContentDescription(CharSequence charSequence) {
        CharSequence description = charSequence;
        CharSequence charSequence2 = description;
        this.mDecorToolbar.setNavigationContentDescription(description);
    }

    public void setHomeActionContentDescription(int i) {
        int resId = i;
        int i2 = resId;
        this.mDecorToolbar.setNavigationContentDescription(resId);
    }

    public void onContentScrollStarted() {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
            this.mCurrentShowAnim = null;
        }
    }

    public void onContentScrollStopped() {
    }

    public boolean collapseActionView() {
        if (this.mDecorToolbar == null || !this.mDecorToolbar.hasExpandedActionView()) {
            return false;
        }
        this.mDecorToolbar.collapseActionView();
        return true;
    }

    public void setCustomView(View view) {
        View view2 = view;
        View view3 = view2;
        this.mDecorToolbar.setCustomView(view2);
    }

    public void setCustomView(View view, LayoutParams layoutParams) {
        View view2 = view;
        LayoutParams layoutParams2 = layoutParams;
        View view3 = view2;
        LayoutParams layoutParams3 = layoutParams2;
        view2.setLayoutParams(layoutParams2);
        this.mDecorToolbar.setCustomView(view2);
    }

    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, OnNavigationListener onNavigationListener) {
        SpinnerAdapter adapter = spinnerAdapter;
        OnNavigationListener callback = onNavigationListener;
        SpinnerAdapter spinnerAdapter2 = adapter;
        OnNavigationListener onNavigationListener2 = callback;
        this.mDecorToolbar.setDropdownParams(adapter, new NavItemSelectedListener(callback));
    }

    public int getSelectedNavigationIndex() {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                return this.mDecorToolbar.getDropdownSelectedPosition();
            case 2:
                return this.mSelectedTab == null ? -1 : this.mSelectedTab.getPosition();
            default:
                return -1;
        }
    }

    public int getNavigationItemCount() {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                return this.mDecorToolbar.getDropdownItemCount();
            case 2:
                return this.mTabs.size();
            default:
                return 0;
        }
    }

    public int getTabCount() {
        return this.mTabs.size();
    }

    public void setNavigationMode(int i) {
        boolean z;
        int mode = i;
        int i2 = mode;
        int navigationMode = this.mDecorToolbar.getNavigationMode();
        int oldMode = navigationMode;
        switch (navigationMode) {
            case 2:
                this.mSavedTabPosition = getSelectedNavigationIndex();
                selectTab(null);
                this.mTabScrollView.setVisibility(8);
                break;
        }
        if (!(oldMode == mode || this.mHasEmbeddedTabs || this.mOverlayLayout == null)) {
            ViewCompat.requestApplyInsets(this.mOverlayLayout);
        }
        this.mDecorToolbar.setNavigationMode(mode);
        switch (mode) {
            case 2:
                ensureTabsExist();
                this.mTabScrollView.setVisibility(0);
                if (this.mSavedTabPosition != -1) {
                    setSelectedNavigationItem(this.mSavedTabPosition);
                    this.mSavedTabPosition = -1;
                    break;
                }
                break;
        }
        this.mDecorToolbar.setCollapsible(mode == 2 && !this.mHasEmbeddedTabs);
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (mode == 2 && !this.mHasEmbeddedTabs) {
            z = true;
        } else {
            z = false;
        }
        actionBarOverlayLayout.setHasNonEmbeddedTabs(z);
    }

    public Tab getTabAt(int i) {
        int index = i;
        int i2 = index;
        return (Tab) this.mTabs.get(index);
    }

    public void setIcon(int i) {
        int resId = i;
        int i2 = resId;
        this.mDecorToolbar.setIcon(resId);
    }

    public void setIcon(Drawable drawable) {
        Drawable icon = drawable;
        Drawable drawable2 = icon;
        this.mDecorToolbar.setIcon(icon);
    }

    public boolean hasIcon() {
        return this.mDecorToolbar.hasIcon();
    }

    public void setLogo(int i) {
        int resId = i;
        int i2 = resId;
        this.mDecorToolbar.setLogo(resId);
    }

    public void setLogo(Drawable drawable) {
        Drawable logo = drawable;
        Drawable drawable2 = logo;
        this.mDecorToolbar.setLogo(logo);
    }

    public boolean hasLogo() {
        return this.mDecorToolbar.hasLogo();
    }

    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
        boolean enable = z;
        if (!this.mDisplayHomeAsUpSet) {
            setDisplayHomeAsUpEnabled(enable);
        }
    }
}
