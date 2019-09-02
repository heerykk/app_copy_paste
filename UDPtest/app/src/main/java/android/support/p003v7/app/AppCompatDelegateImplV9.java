package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.NavUtils;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.view.LayoutInflaterCompat;
import android.support.p000v4.view.LayoutInflaterFactory;
import android.support.p000v4.view.OnApplyWindowInsetsListener;
import android.support.p000v4.view.PointerIconCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewConfigurationCompat;
import android.support.p000v4.view.ViewPropertyAnimatorCompat;
import android.support.p000v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.p000v4.view.WindowInsetsCompat;
import android.support.p000v4.widget.PopupWindowCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.view.ActionMode;
import android.support.p003v7.view.ContextThemeWrapper;
import android.support.p003v7.view.StandaloneActionMode;
import android.support.p003v7.view.menu.ListMenuPresenter;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.view.menu.MenuBuilder.Callback;
import android.support.p003v7.view.menu.MenuPresenter;
import android.support.p003v7.view.menu.MenuView;
import android.support.p003v7.widget.ActionBarContextView;
import android.support.p003v7.widget.AppCompatDrawableManager;
import android.support.p003v7.widget.ContentFrameLayout;
import android.support.p003v7.widget.ContentFrameLayout.OnAttachListener;
import android.support.p003v7.widget.DecorContentParent;
import android.support.p003v7.widget.FitWindowsViewGroup;
import android.support.p003v7.widget.FitWindowsViewGroup.OnFitSystemWindowsListener;
import android.support.p003v7.widget.Toolbar;
import android.support.p003v7.widget.VectorEnabledTintResources;
import android.support.p003v7.widget.ViewStubCompat;
import android.support.p003v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v7.app.AppCompatDelegateImplV9 */
class AppCompatDelegateImplV9 extends AppCompatDelegateImplBase implements Callback, LayoutInflaterFactory {
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    ActionMode mActionMode;
    PopupWindow mActionModePopup;
    ActionBarContextView mActionModeView;
    private AppCompatViewInflater mAppCompatViewInflater;
    private boolean mClosingActionMenu;
    private DecorContentParent mDecorContentParent;
    private boolean mEnableDefaultActionBarUp;
    ViewPropertyAnimatorCompat mFadeAnim = null;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    int mInvalidatePanelMenuFeatures;
    boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable = new Runnable(this) {
        final /* synthetic */ AppCompatDelegateImplV9 this$0;

        {
            AppCompatDelegateImplV9 this$02 = r5;
            AppCompatDelegateImplV9 appCompatDelegateImplV9 = this$02;
            this.this$0 = this$02;
        }

        public void run() {
            if ((this.this$0.mInvalidatePanelMenuFeatures & 1) != 0) {
                this.this$0.doInvalidatePanelMenu(0);
            }
            if ((this.this$0.mInvalidatePanelMenuFeatures & 4096) != 0) {
                this.this$0.doInvalidatePanelMenu(108);
            }
            this.this$0.mInvalidatePanelMenuPosted = false;
            this.this$0.mInvalidatePanelMenuFeatures = 0;
        }
    };
    private boolean mLongPressBackDown;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    private PanelFeatureState mPreparedPanel;
    Runnable mShowActionModePopup;
    private View mStatusGuard;
    private ViewGroup mSubDecor;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private TextView mTitleView;

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV9$ActionMenuPresenterCallback */
    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        final /* synthetic */ AppCompatDelegateImplV9 this$0;

        ActionMenuPresenterCallback(AppCompatDelegateImplV9 appCompatDelegateImplV9) {
            this.this$0 = appCompatDelegateImplV9;
        }

        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            MenuBuilder subMenu = menuBuilder;
            MenuBuilder menuBuilder2 = subMenu;
            Window.Callback windowCallback = this.this$0.getWindowCallback();
            Window.Callback cb = windowCallback;
            if (windowCallback != null) {
                boolean onMenuOpened = cb.onMenuOpened(108, subMenu);
            }
            return true;
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder menu = menuBuilder;
            MenuBuilder menuBuilder2 = menu;
            boolean z2 = z;
            this.this$0.checkCloseActionMenu(menu);
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV9$ActionModeCallbackWrapperV9 */
    class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;
        final /* synthetic */ AppCompatDelegateImplV9 this$0;

        public ActionModeCallbackWrapperV9(AppCompatDelegateImplV9 appCompatDelegateImplV9, ActionMode.Callback callback) {
            AppCompatDelegateImplV9 this$02 = appCompatDelegateImplV9;
            ActionMode.Callback wrapped = callback;
            AppCompatDelegateImplV9 appCompatDelegateImplV92 = this$02;
            ActionMode.Callback callback2 = wrapped;
            this.this$0 = this$02;
            this.mWrapped = wrapped;
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            ActionMode mode = actionMode;
            Menu menu2 = menu;
            ActionMode actionMode2 = mode;
            Menu menu3 = menu2;
            return this.mWrapped.onCreateActionMode(mode, menu2);
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            ActionMode mode = actionMode;
            Menu menu2 = menu;
            ActionMode actionMode2 = mode;
            Menu menu3 = menu2;
            return this.mWrapped.onPrepareActionMode(mode, menu2);
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            ActionMode mode = actionMode;
            MenuItem item = menuItem;
            ActionMode actionMode2 = mode;
            MenuItem menuItem2 = item;
            return this.mWrapped.onActionItemClicked(mode, item);
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            ActionMode mode = actionMode;
            ActionMode actionMode2 = mode;
            this.mWrapped.onDestroyActionMode(mode);
            if (this.this$0.mActionModePopup != null) {
                boolean removeCallbacks = this.this$0.mWindow.getDecorView().removeCallbacks(this.this$0.mShowActionModePopup);
            }
            if (this.this$0.mActionModeView != null) {
                this.this$0.endOnGoingFadeAnimation();
                this.this$0.mFadeAnim = ViewCompat.animate(this.this$0.mActionModeView).alpha(0.0f);
                ViewPropertyAnimatorCompat listener = this.this$0.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(this) {
                    final /* synthetic */ ActionModeCallbackWrapperV9 this$1;

                    {
                        ActionModeCallbackWrapperV9 this$12 = r5;
                        ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = this$12;
                        this.this$1 = this$12;
                    }

                    public void onAnimationEnd(View view) {
                        View view2 = view;
                        this.this$1.this$0.mActionModeView.setVisibility(8);
                        if (this.this$1.this$0.mActionModePopup != null) {
                            this.this$1.this$0.mActionModePopup.dismiss();
                        } else if (this.this$1.this$0.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) this.this$1.this$0.mActionModeView.getParent());
                        }
                        this.this$1.this$0.mActionModeView.removeAllViews();
                        ViewPropertyAnimatorCompat listener = this.this$1.this$0.mFadeAnim.setListener(null);
                        this.this$1.this$0.mFadeAnim = null;
                    }
                });
            }
            if (this.this$0.mAppCompatCallback != null) {
                this.this$0.mAppCompatCallback.onSupportActionModeFinished(this.this$0.mActionMode);
            }
            this.this$0.mActionMode = null;
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV9$ListMenuDecorView */
    private class ListMenuDecorView extends ContentFrameLayout {
        final /* synthetic */ AppCompatDelegateImplV9 this$0;

        public ListMenuDecorView(AppCompatDelegateImplV9 appCompatDelegateImplV9, Context context) {
            Context context2 = context;
            Context context3 = context2;
            this.this$0 = appCompatDelegateImplV9;
            super(context2);
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            KeyEvent event = keyEvent;
            KeyEvent keyEvent2 = event;
            return this.this$0.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            MotionEvent event = motionEvent;
            MotionEvent motionEvent2 = event;
            int action = event.getAction();
            int i = action;
            if (action == 0) {
                int y = (int) event.getY();
                int i2 = y;
                if (isOutOfBounds((int) event.getX(), y)) {
                    this.this$0.closePanel(0);
                    return true;
                }
            }
            return super.onInterceptTouchEvent(event);
        }

        public void setBackgroundResource(int i) {
            int resid = i;
            int i2 = resid;
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), resid));
        }

        private boolean isOutOfBounds(int i, int i2) {
            int x = i;
            int y = i2;
            int i3 = x;
            int i4 = y;
            return x < -5 || y < -5 || x > getWidth() + 5 || y > getHeight() + 5;
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV9$PanelFeatureState */
    protected static final class PanelFeatureState {
        int background;
        View createdPanelView;
        ViewGroup decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int gravity;
        boolean isHandled;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        Context listPresenterContext;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView = false;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastOpen;
        int windowAnimations;

        /* renamed from: x */
        int f17x;

        /* renamed from: y */
        int f18y;

        /* renamed from: android.support.v7.app.AppCompatDelegateImplV9$PanelFeatureState$SavedState */
        private static class SavedState implements Parcelable {
            public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    Parcel in = parcel;
                    ClassLoader loader = classLoader;
                    Parcel parcel2 = in;
                    ClassLoader classLoader2 = loader;
                    return SavedState.readFromParcel(in, loader);
                }

                public SavedState[] newArray(int i) {
                    int size = i;
                    int i2 = size;
                    return new SavedState[size];
                }
            });
            int featureId;
            boolean isOpen;
            Bundle menuState;

            SavedState() {
            }

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel parcel, int i) {
                Parcel dest = parcel;
                Parcel parcel2 = dest;
                int i2 = i;
                dest.writeInt(this.featureId);
                dest.writeInt(!this.isOpen ? 0 : 1);
                if (this.isOpen) {
                    dest.writeBundle(this.menuState);
                }
            }

            static SavedState readFromParcel(Parcel parcel, ClassLoader classLoader) {
                Parcel source = parcel;
                ClassLoader loader = classLoader;
                Parcel parcel2 = source;
                ClassLoader classLoader2 = loader;
                SavedState savedState = new SavedState();
                SavedState savedState2 = savedState;
                savedState.featureId = source.readInt();
                savedState2.isOpen = source.readInt() == 1;
                if (savedState2.isOpen) {
                    savedState2.menuState = source.readBundle(loader);
                }
                return savedState2;
            }
        }

        PanelFeatureState(int i) {
            int featureId2 = i;
            int i2 = featureId2;
            this.featureId = featureId2;
        }

        public boolean hasPanelItems() {
            if (this.shownPanelView == null) {
                return false;
            }
            if (this.createdPanelView != null) {
                return true;
            }
            return this.listMenuPresenter.getAdapter().getCount() > 0;
        }

        public void clearMenuPresenters() {
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.listMenuPresenter = null;
        }

        /* access modifiers changed from: 0000 */
        public void setStyle(Context context) {
            Context context2 = context;
            Context context3 = context2;
            TypedValue outValue = new TypedValue();
            Theme newTheme = context2.getResources().newTheme();
            Theme widgetTheme = newTheme;
            newTheme.setTo(context2.getTheme());
            boolean resolveAttribute = widgetTheme.resolveAttribute(C0268R.attr.actionBarPopupTheme, outValue, true);
            if (outValue.resourceId != 0) {
                widgetTheme.applyStyle(outValue.resourceId, true);
            }
            boolean resolveAttribute2 = widgetTheme.resolveAttribute(C0268R.attr.panelMenuListTheme, outValue, true);
            if (outValue.resourceId == 0) {
                widgetTheme.applyStyle(C0268R.style.Theme_AppCompat_CompactMenu, true);
            } else {
                widgetTheme.applyStyle(outValue.resourceId, true);
            }
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context2, 0);
            ContextThemeWrapper contextThemeWrapper2 = contextThemeWrapper;
            contextThemeWrapper.getTheme().setTo(widgetTheme);
            this.listPresenterContext = contextThemeWrapper2;
            TypedArray a = contextThemeWrapper2.obtainStyledAttributes(C0268R.styleable.AppCompatTheme);
            this.background = a.getResourceId(C0268R.styleable.AppCompatTheme_panelBackground, 0);
            this.windowAnimations = a.getResourceId(C0268R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            a.recycle();
        }

        /* access modifiers changed from: 0000 */
        public void setMenu(MenuBuilder menuBuilder) {
            MenuBuilder menu2 = menuBuilder;
            MenuBuilder menuBuilder2 = menu2;
            if (menu2 != this.menu) {
                if (this.menu != null) {
                    this.menu.removeMenuPresenter(this.listMenuPresenter);
                }
                this.menu = menu2;
                if (!(menu2 == null || this.listMenuPresenter == null)) {
                    menu2.addMenuPresenter(this.listMenuPresenter);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public MenuView getListMenuView(MenuPresenter.Callback callback) {
            MenuPresenter.Callback cb = callback;
            MenuPresenter.Callback callback2 = cb;
            if (this.menu == null) {
                return null;
            }
            if (this.listMenuPresenter == null) {
                this.listMenuPresenter = new ListMenuPresenter(this.listPresenterContext, C0268R.layout.abc_list_menu_item_layout);
                this.listMenuPresenter.setCallback(cb);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            MenuView menuView = this.listMenuPresenter.getMenuView(this.decorView);
            MenuView menuView2 = menuView;
            return menuView;
        }

        /* access modifiers changed from: 0000 */
        public Parcelable onSaveInstanceState() {
            SavedState savedState = new SavedState();
            SavedState savedState2 = savedState;
            savedState.featureId = this.featureId;
            savedState2.isOpen = this.isOpen;
            if (this.menu != null) {
                savedState2.menuState = new Bundle();
                this.menu.savePresenterStates(savedState2.menuState);
            }
            return savedState2;
        }

        /* access modifiers changed from: 0000 */
        public void onRestoreInstanceState(Parcelable parcelable) {
            Parcelable state = parcelable;
            Parcelable parcelable2 = state;
            SavedState savedState = (SavedState) state;
            this.featureId = savedState.featureId;
            this.wasLastOpen = savedState.isOpen;
            this.frozenMenuState = savedState.menuState;
            this.shownPanelView = null;
            this.decorView = null;
        }

        /* access modifiers changed from: 0000 */
        public void applyFrozenState() {
            if (this.menu != null && this.frozenMenuState != null) {
                this.menu.restorePresenterStates(this.frozenMenuState);
                this.frozenMenuState = null;
            }
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV9$PanelMenuPresenterCallback */
    private final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        final /* synthetic */ AppCompatDelegateImplV9 this$0;

        PanelMenuPresenterCallback(AppCompatDelegateImplV9 appCompatDelegateImplV9) {
            this.this$0 = appCompatDelegateImplV9;
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder menu = menuBuilder;
            MenuBuilder menuBuilder2 = menu;
            boolean allMenusAreClosing = z;
            MenuBuilder rootMenu = menu.getRootMenu();
            MenuBuilder menuBuilder3 = rootMenu;
            boolean isSubMenu = rootMenu != menu;
            PanelFeatureState findMenuPanel = this.this$0.findMenuPanel(!isSubMenu ? menu : menuBuilder3);
            PanelFeatureState panel = findMenuPanel;
            if (findMenuPanel != null) {
                if (!isSubMenu) {
                    this.this$0.closePanel(panel, allMenusAreClosing);
                    return;
                }
                this.this$0.callOnPanelClosed(panel.featureId, panel, menuBuilder3);
                this.this$0.closePanel(panel, true);
            }
        }

        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            MenuBuilder subMenu = menuBuilder;
            MenuBuilder menuBuilder2 = subMenu;
            if (subMenu == null && this.this$0.mHasActionBar) {
                Window.Callback windowCallback = this.this$0.getWindowCallback();
                Window.Callback cb = windowCallback;
                if (windowCallback != null && !this.this$0.isDestroyed()) {
                    boolean onMenuOpened = cb.onMenuOpened(108, subMenu);
                }
            }
            return true;
        }
    }

    AppCompatDelegateImplV9(Context context, Window window, AppCompatCallback appCompatCallback) {
        Context context2 = context;
        Window window2 = window;
        AppCompatCallback callback = appCompatCallback;
        Context context3 = context2;
        Window window3 = window2;
        AppCompatCallback appCompatCallback2 = callback;
        super(context2, window2, callback);
    }

    public void onCreate(Bundle bundle) {
        Bundle bundle2 = bundle;
        if ((this.mOriginalWindowCallback instanceof Activity) && NavUtils.getParentActivityName((Activity) this.mOriginalWindowCallback) != null) {
            ActionBar peekSupportActionBar = peekSupportActionBar();
            ActionBar ab = peekSupportActionBar;
            if (peekSupportActionBar != null) {
                ab.setDefaultDisplayHomeAsUpEnabled(true);
            } else {
                this.mEnableDefaultActionBarUp = true;
            }
        }
    }

    public void onPostCreate(Bundle bundle) {
        Bundle bundle2 = bundle;
        ensureSubDecor();
    }

    public void initWindowDecorActionBar() {
        ensureSubDecor();
        if (this.mHasActionBar && this.mActionBar == null) {
            if (this.mOriginalWindowCallback instanceof Activity) {
                this.mActionBar = new WindowDecorActionBar((Activity) this.mOriginalWindowCallback, this.mOverlayActionBar);
            } else if (this.mOriginalWindowCallback instanceof Dialog) {
                this.mActionBar = new WindowDecorActionBar((Dialog) this.mOriginalWindowCallback);
            }
            if (this.mActionBar != null) {
                this.mActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
            }
        }
    }

    public void setSupportActionBar(Toolbar toolbar) {
        Toolbar toolbar2 = toolbar;
        Toolbar toolbar3 = toolbar2;
        if (this.mOriginalWindowCallback instanceof Activity) {
            ActionBar supportActionBar = getSupportActionBar();
            ActionBar ab = supportActionBar;
            if (!(supportActionBar instanceof WindowDecorActionBar)) {
                this.mMenuInflater = null;
                if (ab != null) {
                    ab.onDestroy();
                }
                if (toolbar2 == null) {
                    this.mActionBar = null;
                    this.mWindow.setCallback(this.mAppCompatWindowCallback);
                } else {
                    ToolbarActionBar tbab = new ToolbarActionBar(toolbar2, ((Activity) this.mOriginalWindowCallback).getTitle(), this.mAppCompatWindowCallback);
                    this.mActionBar = tbab;
                    this.mWindow.setCallback(tbab.getWrappedWindowCallback());
                }
                invalidateOptionsMenu();
                return;
            }
            throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
        }
    }

    @Nullable
    public View findViewById(@IdRes int i) {
        int id = i;
        int i2 = id;
        ensureSubDecor();
        return this.mWindow.findViewById(id);
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        Configuration configuration2 = newConfig;
        if (this.mHasActionBar && this.mSubDecorInstalled) {
            ActionBar supportActionBar = getSupportActionBar();
            ActionBar ab = supportActionBar;
            if (supportActionBar != null) {
                ab.onConfigurationChanged(newConfig);
            }
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.mContext);
        boolean applyDayNight = applyDayNight();
    }

    public void onStop() {
        ActionBar supportActionBar = getSupportActionBar();
        ActionBar ab = supportActionBar;
        if (supportActionBar != null) {
            ab.setShowHideAnimationEnabled(false);
        }
    }

    public void onPostResume() {
        ActionBar supportActionBar = getSupportActionBar();
        ActionBar ab = supportActionBar;
        if (supportActionBar != null) {
            ab.setShowHideAnimationEnabled(true);
        }
    }

    public void setContentView(View view) {
        View v = view;
        View view2 = v;
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(16908290);
        ViewGroup contentParent = viewGroup;
        viewGroup.removeAllViews();
        contentParent.addView(v);
        this.mOriginalWindowCallback.onContentChanged();
    }

    public void setContentView(int i) {
        int resId = i;
        int i2 = resId;
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(16908290);
        ViewGroup contentParent = viewGroup;
        viewGroup.removeAllViews();
        View inflate = LayoutInflater.from(this.mContext).inflate(resId, contentParent);
        this.mOriginalWindowCallback.onContentChanged();
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        View v = view;
        LayoutParams lp = layoutParams;
        View view2 = v;
        LayoutParams layoutParams2 = lp;
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(16908290);
        ViewGroup contentParent = viewGroup;
        viewGroup.removeAllViews();
        contentParent.addView(v, lp);
        this.mOriginalWindowCallback.onContentChanged();
    }

    public void addContentView(View view, LayoutParams layoutParams) {
        View v = view;
        LayoutParams lp = layoutParams;
        View view2 = v;
        LayoutParams layoutParams2 = lp;
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(16908290);
        ViewGroup viewGroup2 = viewGroup;
        viewGroup.addView(v, lp);
        this.mOriginalWindowCallback.onContentChanged();
    }

    public void onDestroy() {
        if (this.mInvalidatePanelMenuPosted) {
            boolean removeCallbacks = this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
        }
        super.onDestroy();
        if (this.mActionBar != null) {
            this.mActionBar.onDestroy();
        }
    }

    private void ensureSubDecor() {
        if (!this.mSubDecorInstalled) {
            this.mSubDecor = createSubDecor();
            CharSequence title = getTitle();
            CharSequence title2 = title;
            if (!TextUtils.isEmpty(title)) {
                onTitleChanged(title2);
            }
            applyFixedSizeWindow();
            onSubDecorInstalled(this.mSubDecor);
            this.mSubDecorInstalled = true;
            PanelFeatureState st = getPanelState(0, false);
            if (!isDestroyed()) {
                if (st == null || st.menu == null) {
                    invalidatePanelMenu(108);
                }
            }
        }
    }

    private ViewGroup createSubDecor() {
        Context themedContext;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(C0268R.styleable.AppCompatTheme);
        TypedArray a = obtainStyledAttributes;
        if (obtainStyledAttributes.hasValue(C0268R.styleable.AppCompatTheme_windowActionBar)) {
            if (a.getBoolean(C0268R.styleable.AppCompatTheme_windowNoTitle, false)) {
                boolean requestWindowFeature = requestWindowFeature(1);
            } else if (a.getBoolean(C0268R.styleable.AppCompatTheme_windowActionBar, false)) {
                boolean requestWindowFeature2 = requestWindowFeature(108);
            }
            if (a.getBoolean(C0268R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
                boolean requestWindowFeature3 = requestWindowFeature(109);
            }
            if (a.getBoolean(C0268R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
                boolean requestWindowFeature4 = requestWindowFeature(10);
            }
            this.mIsFloating = a.getBoolean(C0268R.styleable.AppCompatTheme_android_windowIsFloating, false);
            a.recycle();
            View decorView = this.mWindow.getDecorView();
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            ViewGroup subDecor = null;
            if (this.mWindowNoTitle) {
                if (!this.mOverlayActionMode) {
                    subDecor = (ViewGroup) inflater.inflate(C0268R.layout.abc_screen_simple, null);
                } else {
                    subDecor = (ViewGroup) inflater.inflate(C0268R.layout.abc_screen_simple_overlay_action_mode, null);
                }
                if (VERSION.SDK_INT < 21) {
                    ((FitWindowsViewGroup) subDecor).setOnFitSystemWindowsListener(new OnFitSystemWindowsListener(this) {
                        final /* synthetic */ AppCompatDelegateImplV9 this$0;

                        {
                            AppCompatDelegateImplV9 this$02 = r5;
                            AppCompatDelegateImplV9 appCompatDelegateImplV9 = this$02;
                            this.this$0 = this$02;
                        }

                        public void onFitSystemWindows(Rect rect) {
                            Rect insets = rect;
                            Rect rect2 = insets;
                            insets.top = this.this$0.updateStatusGuard(insets.top);
                        }
                    });
                } else {
                    ViewCompat.setOnApplyWindowInsetsListener(subDecor, new OnApplyWindowInsetsListener(this) {
                        final /* synthetic */ AppCompatDelegateImplV9 this$0;

                        {
                            AppCompatDelegateImplV9 this$02 = r5;
                            AppCompatDelegateImplV9 appCompatDelegateImplV9 = this$02;
                            this.this$0 = this$02;
                        }

                        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                            View v = view;
                            WindowInsetsCompat insets = windowInsetsCompat;
                            View view2 = v;
                            WindowInsetsCompat insets2 = insets;
                            int systemWindowInsetTop = insets.getSystemWindowInsetTop();
                            int top = systemWindowInsetTop;
                            int newTop = this.this$0.updateStatusGuard(systemWindowInsetTop);
                            if (top != newTop) {
                                insets2 = insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), newTop, insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
                            }
                            return ViewCompat.onApplyWindowInsets(v, insets2);
                        }
                    });
                }
            } else if (this.mIsFloating) {
                subDecor = (ViewGroup) inflater.inflate(C0268R.layout.abc_dialog_title_material, null);
                this.mOverlayActionBar = false;
                this.mHasActionBar = false;
            } else if (this.mHasActionBar) {
                TypedValue outValue = new TypedValue();
                boolean resolveAttribute = this.mContext.getTheme().resolveAttribute(C0268R.attr.actionBarTheme, outValue, true);
                if (outValue.resourceId == 0) {
                    themedContext = this.mContext;
                } else {
                    themedContext = new ContextThemeWrapper(this.mContext, outValue.resourceId);
                }
                subDecor = (ViewGroup) LayoutInflater.from(themedContext).inflate(C0268R.layout.abc_screen_toolbar, null);
                this.mDecorContentParent = (DecorContentParent) subDecor.findViewById(C0268R.C0270id.decor_content_parent);
                this.mDecorContentParent.setWindowCallback(getWindowCallback());
                if (this.mOverlayActionBar) {
                    this.mDecorContentParent.initFeature(109);
                }
                if (this.mFeatureProgress) {
                    this.mDecorContentParent.initFeature(2);
                }
                if (this.mFeatureIndeterminateProgress) {
                    this.mDecorContentParent.initFeature(5);
                }
            }
            if (subDecor != null) {
                if (this.mDecorContentParent == null) {
                    this.mTitleView = (TextView) subDecor.findViewById(C0268R.C0270id.title);
                }
                ViewUtils.makeOptionalFitsSystemWindows(subDecor);
                ContentFrameLayout contentView = (ContentFrameLayout) subDecor.findViewById(C0268R.C0270id.action_bar_activity_content);
                ViewGroup viewGroup = (ViewGroup) this.mWindow.findViewById(16908290);
                ViewGroup windowContentView = viewGroup;
                if (viewGroup != null) {
                    while (windowContentView.getChildCount() > 0) {
                        View child = windowContentView.getChildAt(0);
                        windowContentView.removeViewAt(0);
                        contentView.addView(child);
                    }
                    windowContentView.setId(-1);
                    contentView.setId(16908290);
                    if (windowContentView instanceof FrameLayout) {
                        ((FrameLayout) windowContentView).setForeground(null);
                    }
                }
                this.mWindow.setContentView(subDecor);
                contentView.setAttachListener(new OnAttachListener(this) {
                    final /* synthetic */ AppCompatDelegateImplV9 this$0;

                    {
                        AppCompatDelegateImplV9 this$02 = r5;
                        AppCompatDelegateImplV9 appCompatDelegateImplV9 = this$02;
                        this.this$0 = this$02;
                    }

                    public void onAttachedFromWindow() {
                    }

                    public void onDetachedFromWindow() {
                        this.this$0.dismissPopups();
                    }
                });
                return subDecor;
            }
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.mHasActionBar + ", windowActionBarOverlay: " + this.mOverlayActionBar + ", android:windowIsFloating: " + this.mIsFloating + ", windowActionModeOverlay: " + this.mOverlayActionMode + ", windowNoTitle: " + this.mWindowNoTitle + " }");
        }
        a.recycle();
        throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
    }

    /* access modifiers changed from: 0000 */
    public void onSubDecorInstalled(ViewGroup viewGroup) {
        ViewGroup viewGroup2 = viewGroup;
    }

    private void applyFixedSizeWindow() {
        ContentFrameLayout cfl = (ContentFrameLayout) this.mSubDecor.findViewById(16908290);
        View windowDecor = this.mWindow.getDecorView();
        cfl.setDecorPadding(windowDecor.getPaddingLeft(), windowDecor.getPaddingTop(), windowDecor.getPaddingRight(), windowDecor.getPaddingBottom());
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(C0268R.styleable.AppCompatTheme);
        TypedArray a = obtainStyledAttributes;
        boolean value = obtainStyledAttributes.getValue(C0268R.styleable.AppCompatTheme_windowMinWidthMajor, cfl.getMinWidthMajor());
        boolean value2 = a.getValue(C0268R.styleable.AppCompatTheme_windowMinWidthMinor, cfl.getMinWidthMinor());
        if (a.hasValue(C0268R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            boolean value3 = a.getValue(C0268R.styleable.AppCompatTheme_windowFixedWidthMajor, cfl.getFixedWidthMajor());
        }
        if (a.hasValue(C0268R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            boolean value4 = a.getValue(C0268R.styleable.AppCompatTheme_windowFixedWidthMinor, cfl.getFixedWidthMinor());
        }
        if (a.hasValue(C0268R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            boolean value5 = a.getValue(C0268R.styleable.AppCompatTheme_windowFixedHeightMajor, cfl.getFixedHeightMajor());
        }
        if (a.hasValue(C0268R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            boolean value6 = a.getValue(C0268R.styleable.AppCompatTheme_windowFixedHeightMinor, cfl.getFixedHeightMinor());
        }
        a.recycle();
        cfl.requestLayout();
    }

    public boolean requestWindowFeature(int i) {
        int featureId = i;
        int i2 = featureId;
        int featureId2 = sanitizeWindowFeatureId(featureId);
        if (this.mWindowNoTitle && featureId2 == 108) {
            return false;
        }
        if (this.mHasActionBar && featureId2 == 1) {
            this.mHasActionBar = false;
        }
        switch (featureId2) {
            case 1:
                throwFeatureRequestIfSubDecorInstalled();
                this.mWindowNoTitle = true;
                return true;
            case 2:
                throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureProgress = true;
                return true;
            case 5:
                throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureIndeterminateProgress = true;
                return true;
            case 10:
                throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionMode = true;
                return true;
            case 108:
                throwFeatureRequestIfSubDecorInstalled();
                this.mHasActionBar = true;
                return true;
            case 109:
                throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionBar = true;
                return true;
            default:
                return this.mWindow.requestFeature(featureId2);
        }
    }

    public boolean hasWindowFeature(int i) {
        int featureId = i;
        int i2 = featureId;
        int sanitizeWindowFeatureId = sanitizeWindowFeatureId(featureId);
        int featureId2 = sanitizeWindowFeatureId;
        switch (sanitizeWindowFeatureId) {
            case 1:
                return this.mWindowNoTitle;
            case 2:
                return this.mFeatureProgress;
            case 5:
                return this.mFeatureIndeterminateProgress;
            case 10:
                return this.mOverlayActionMode;
            case 108:
                return this.mHasActionBar;
            case 109:
                return this.mOverlayActionBar;
            default:
                return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void onTitleChanged(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setWindowTitle(title);
        } else if (peekSupportActionBar() != null) {
            peekSupportActionBar().setWindowTitle(title);
        } else if (this.mTitleView != null) {
            this.mTitleView.setText(title);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onPanelClosed(int i, Menu menu) {
        int featureId = i;
        int i2 = featureId;
        Menu menu2 = menu;
        if (featureId == 108) {
            ActionBar supportActionBar = getSupportActionBar();
            ActionBar ab = supportActionBar;
            if (supportActionBar != null) {
                ab.dispatchMenuVisibilityChanged(false);
            }
        } else if (featureId == 0) {
            PanelFeatureState panelState = getPanelState(featureId, true);
            PanelFeatureState st = panelState;
            if (panelState.isOpen) {
                closePanel(st, false);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean onMenuOpened(int i, Menu menu) {
        int featureId = i;
        int i2 = featureId;
        Menu menu2 = menu;
        if (featureId != 108) {
            return false;
        }
        ActionBar supportActionBar = getSupportActionBar();
        ActionBar ab = supportActionBar;
        if (supportActionBar != null) {
            ab.dispatchMenuVisibilityChanged(true);
        }
        return true;
    }

    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        MenuBuilder menu = menuBuilder;
        MenuItem item = menuItem;
        MenuBuilder menuBuilder2 = menu;
        MenuItem menuItem2 = item;
        Window.Callback windowCallback = getWindowCallback();
        Window.Callback cb = windowCallback;
        if (windowCallback != null && !isDestroyed()) {
            PanelFeatureState findMenuPanel = findMenuPanel(menu.getRootMenu());
            PanelFeatureState panel = findMenuPanel;
            if (findMenuPanel != null) {
                return cb.onMenuItemSelected(panel.featureId, item);
            }
        }
        return false;
    }

    public void onMenuModeChange(MenuBuilder menuBuilder) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        reopenMenu(menu, true);
    }

    public ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        ActionMode.Callback callback2 = callback;
        ActionMode.Callback callback3 = callback2;
        if (callback2 != null) {
            if (this.mActionMode != null) {
                this.mActionMode.finish();
            }
            ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = new ActionModeCallbackWrapperV9(this, callback2);
            ActionBar supportActionBar = getSupportActionBar();
            ActionBar ab = supportActionBar;
            if (supportActionBar != null) {
                this.mActionMode = ab.startActionMode(actionModeCallbackWrapperV9);
                if (!(this.mActionMode == null || this.mAppCompatCallback == null)) {
                    this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode);
                }
            }
            if (this.mActionMode == null) {
                this.mActionMode = startSupportActionModeFromWindow(actionModeCallbackWrapperV9);
            }
            return this.mActionMode;
        }
        throw new IllegalArgumentException("ActionMode callback can not be null.");
    }

    public void invalidateOptionsMenu() {
        ActionBar supportActionBar = getSupportActionBar();
        ActionBar ab = supportActionBar;
        if (supportActionBar == null || !ab.invalidateOptionsMenu()) {
            invalidatePanelMenu(0);
        }
    }

    /* access modifiers changed from: 0000 */
    public ActionMode startSupportActionModeFromWindow(@NonNull ActionMode.Callback callback) {
        Context actionBarContext;
        ActionMode.Callback callback2 = callback;
        ActionMode.Callback callback3 = callback2;
        endOnGoingFadeAnimation();
        if (this.mActionMode != null) {
            this.mActionMode.finish();
        }
        if (!(callback2 instanceof ActionModeCallbackWrapperV9)) {
            callback3 = new ActionModeCallbackWrapperV9(this, callback2);
        }
        ActionMode mode = null;
        if (this.mAppCompatCallback != null && !isDestroyed()) {
            try {
                mode = this.mAppCompatCallback.onWindowStartingSupportActionMode(callback3);
            } catch (AbstractMethodError e) {
            }
        }
        if (mode == null) {
            if (this.mActionModeView == null) {
                if (!this.mIsFloating) {
                    ViewStubCompat viewStubCompat = (ViewStubCompat) this.mSubDecor.findViewById(C0268R.C0270id.action_mode_bar_stub);
                    ViewStubCompat stub = viewStubCompat;
                    if (viewStubCompat != null) {
                        stub.setLayoutInflater(LayoutInflater.from(getActionBarThemedContext()));
                        this.mActionModeView = (ActionBarContextView) stub.inflate();
                    }
                } else {
                    TypedValue outValue = new TypedValue();
                    Theme theme = this.mContext.getTheme();
                    Theme baseTheme = theme;
                    boolean resolveAttribute = theme.resolveAttribute(C0268R.attr.actionBarTheme, outValue, true);
                    if (outValue.resourceId == 0) {
                        actionBarContext = this.mContext;
                    } else {
                        Theme newTheme = this.mContext.getResources().newTheme();
                        Theme actionBarTheme = newTheme;
                        newTheme.setTo(baseTheme);
                        actionBarTheme.applyStyle(outValue.resourceId, true);
                        Context contextThemeWrapper = new ContextThemeWrapper(this.mContext, 0);
                        actionBarContext = contextThemeWrapper;
                        contextThemeWrapper.getTheme().setTo(actionBarTheme);
                    }
                    this.mActionModeView = new ActionBarContextView(actionBarContext);
                    PopupWindow popupWindow = new PopupWindow(actionBarContext, null, C0268R.attr.actionModePopupWindowStyle);
                    this.mActionModePopup = popupWindow;
                    PopupWindowCompat.setWindowLayoutType(this.mActionModePopup, 2);
                    this.mActionModePopup.setContentView(this.mActionModeView);
                    this.mActionModePopup.setWidth(-1);
                    boolean resolveAttribute2 = actionBarContext.getTheme().resolveAttribute(C0268R.attr.actionBarSize, outValue, true);
                    int complexToDimensionPixelSize = TypedValue.complexToDimensionPixelSize(outValue.data, actionBarContext.getResources().getDisplayMetrics());
                    int i = complexToDimensionPixelSize;
                    this.mActionModeView.setContentHeight(complexToDimensionPixelSize);
                    this.mActionModePopup.setHeight(-2);
                    C02575 r0 = new Runnable(this) {
                        final /* synthetic */ AppCompatDelegateImplV9 this$0;

                        {
                            AppCompatDelegateImplV9 this$02 = r5;
                            AppCompatDelegateImplV9 appCompatDelegateImplV9 = this$02;
                            this.this$0 = this$02;
                        }

                        public void run() {
                            this.this$0.mActionModePopup.showAtLocation(this.this$0.mActionModeView, 55, 0, 0);
                            this.this$0.endOnGoingFadeAnimation();
                            if (!this.this$0.shouldAnimateActionModeView()) {
                                ViewCompat.setAlpha(this.this$0.mActionModeView, 1.0f);
                                this.this$0.mActionModeView.setVisibility(0);
                                return;
                            }
                            ViewCompat.setAlpha(this.this$0.mActionModeView, 0.0f);
                            this.this$0.mFadeAnim = ViewCompat.animate(this.this$0.mActionModeView).alpha(1.0f);
                            ViewPropertyAnimatorCompat listener = this.this$0.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(this) {
                                final /* synthetic */ C02575 this$1;

                                {
                                    C02575 this$12 = r5;
                                    C02575 r3 = this$12;
                                    this.this$1 = this$12;
                                }

                                public void onAnimationStart(View view) {
                                    View view2 = view;
                                    this.this$1.this$0.mActionModeView.setVisibility(0);
                                }

                                public void onAnimationEnd(View view) {
                                    View view2 = view;
                                    ViewCompat.setAlpha(this.this$1.this$0.mActionModeView, 1.0f);
                                    ViewPropertyAnimatorCompat listener = this.this$1.this$0.mFadeAnim.setListener(null);
                                    this.this$1.this$0.mFadeAnim = null;
                                }
                            });
                        }
                    };
                    this.mShowActionModePopup = r0;
                }
            }
            if (this.mActionModeView != null) {
                endOnGoingFadeAnimation();
                this.mActionModeView.killMode();
                StandaloneActionMode standaloneActionMode = new StandaloneActionMode(this.mActionModeView.getContext(), this.mActionModeView, callback3, this.mActionModePopup == null);
                if (!callback3.onCreateActionMode(standaloneActionMode, standaloneActionMode.getMenu())) {
                    this.mActionMode = null;
                } else {
                    standaloneActionMode.invalidate();
                    this.mActionModeView.initForMode(standaloneActionMode);
                    this.mActionMode = standaloneActionMode;
                    if (!shouldAnimateActionModeView()) {
                        ViewCompat.setAlpha(this.mActionModeView, 1.0f);
                        this.mActionModeView.setVisibility(0);
                        this.mActionModeView.sendAccessibilityEvent(32);
                        if (this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) this.mActionModeView.getParent());
                        }
                    } else {
                        ViewCompat.setAlpha(this.mActionModeView, 0.0f);
                        this.mFadeAnim = ViewCompat.animate(this.mActionModeView).alpha(1.0f);
                        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mFadeAnim;
                        C02596 r02 = new ViewPropertyAnimatorListenerAdapter(this) {
                            final /* synthetic */ AppCompatDelegateImplV9 this$0;

                            {
                                AppCompatDelegateImplV9 this$02 = r5;
                                AppCompatDelegateImplV9 appCompatDelegateImplV9 = this$02;
                                this.this$0 = this$02;
                            }

                            public void onAnimationStart(View view) {
                                View view2 = view;
                                this.this$0.mActionModeView.setVisibility(0);
                                this.this$0.mActionModeView.sendAccessibilityEvent(32);
                                if (this.this$0.mActionModeView.getParent() instanceof View) {
                                    ViewCompat.requestApplyInsets((View) this.this$0.mActionModeView.getParent());
                                }
                            }

                            public void onAnimationEnd(View view) {
                                View view2 = view;
                                ViewCompat.setAlpha(this.this$0.mActionModeView, 1.0f);
                                ViewPropertyAnimatorCompat listener = this.this$0.mFadeAnim.setListener(null);
                                this.this$0.mFadeAnim = null;
                            }
                        };
                        ViewPropertyAnimatorCompat listener = viewPropertyAnimatorCompat.setListener(r02);
                    }
                    if (this.mActionModePopup != null) {
                        boolean post = this.mWindow.getDecorView().post(this.mShowActionModePopup);
                    }
                }
            }
        } else {
            this.mActionMode = mode;
        }
        if (!(this.mActionMode == null || this.mAppCompatCallback == null)) {
            this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode);
        }
        return this.mActionMode;
    }

    /* access modifiers changed from: 0000 */
    public final boolean shouldAnimateActionModeView() {
        return this.mSubDecorInstalled && this.mSubDecor != null && ViewCompat.isLaidOut(this.mSubDecor);
    }

    /* access modifiers changed from: 0000 */
    public void endOnGoingFadeAnimation() {
        if (this.mFadeAnim != null) {
            this.mFadeAnim.cancel();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean onBackPressed() {
        if (this.mActionMode == null) {
            ActionBar supportActionBar = getSupportActionBar();
            ActionBar ab = supportActionBar;
            if (supportActionBar != null && ab.collapseActionView()) {
                return true;
            }
            return false;
        }
        this.mActionMode.finish();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent ev = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = ev;
        ActionBar supportActionBar = getSupportActionBar();
        ActionBar ab = supportActionBar;
        if (supportActionBar != null && ab.onKeyShortcut(keyCode, ev)) {
            return true;
        }
        if (this.mPreparedPanel != null) {
            boolean performPanelShortcut = performPanelShortcut(this.mPreparedPanel, ev.getKeyCode(), ev, 1);
            boolean z = performPanelShortcut;
            if (performPanelShortcut) {
                if (this.mPreparedPanel != null) {
                    this.mPreparedPanel.isHandled = true;
                }
                return true;
            }
        }
        if (this.mPreparedPanel == null) {
            PanelFeatureState st = getPanelState(0, true);
            boolean preparePanel = preparePanel(st, ev);
            boolean performPanelShortcut2 = performPanelShortcut(st, ev.getKeyCode(), ev, 1);
            boolean z2 = performPanelShortcut2;
            st.isPrepared = false;
            if (performPanelShortcut2) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean onKeyDown;
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        if (event.getKeyCode() == 82 && this.mOriginalWindowCallback.dispatchKeyEvent(event)) {
            return true;
        }
        int keyCode = event.getKeyCode();
        int action = event.getAction();
        int i = action;
        if (!(action == 0)) {
            onKeyDown = onKeyUp(keyCode, event);
        } else {
            onKeyDown = onKeyDown(keyCode, event);
        }
        return onKeyDown;
    }

    /* access modifiers changed from: 0000 */
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        switch (keyCode) {
            case 4:
                boolean wasLongPressBackDown = this.mLongPressBackDown;
                this.mLongPressBackDown = false;
                PanelFeatureState panelState = getPanelState(0, false);
                PanelFeatureState st = panelState;
                if (panelState != null && st.isOpen) {
                    if (!wasLongPressBackDown) {
                        closePanel(st, true);
                    }
                    return true;
                } else if (onBackPressed()) {
                    return true;
                }
                break;
            case 82:
                boolean onKeyUpPanel = onKeyUpPanel(0, event);
                return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z;
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        switch (keyCode) {
            case 4:
                if ((event.getFlags() & 128) == 0) {
                    z = false;
                } else {
                    z = true;
                }
                this.mLongPressBackDown = z;
                break;
            case 82:
                boolean onKeyDownPanel = onKeyDownPanel(0, event);
                return true;
        }
        if (VERSION.SDK_INT < 11) {
            boolean onKeyShortcut = onKeyShortcut(keyCode, event);
        }
        return false;
    }

    public View createView(View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        View parent = view;
        String name = str;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        View view2 = parent;
        String str2 = name;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        boolean isPre21 = VERSION.SDK_INT < 21;
        if (this.mAppCompatViewInflater == null) {
            this.mAppCompatViewInflater = new AppCompatViewInflater();
        }
        return this.mAppCompatViewInflater.createView(parent, name, context2, attrs, isPre21 && shouldInheritContext((ViewParent) parent), isPre21, true, VectorEnabledTintResources.shouldBeUsed());
    }

    private boolean shouldInheritContext(ViewParent viewParent) {
        ViewParent parent = viewParent;
        if (parent == null) {
            return false;
        }
        View windowDecor = this.mWindow.getDecorView();
        for (ViewParent parent2 = parent; parent2 != null; parent2 = parent2.getParent()) {
            if (parent2 == windowDecor || !(parent2 instanceof View) || ViewCompat.isAttachedToWindow((View) parent2)) {
                return false;
            }
        }
        return true;
    }

    public void installViewFactory() {
        LayoutInflater from = LayoutInflater.from(this.mContext);
        LayoutInflater layoutInflater = from;
        if (from.getFactory() == null) {
            LayoutInflaterCompat.setFactory(layoutInflater, this);
        } else if (!(LayoutInflaterCompat.getFactory(layoutInflater) instanceof AppCompatDelegateImplV9)) {
            int i = Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View parent = view;
        String name = str;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        View view2 = parent;
        String str2 = name;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        View callActivityOnCreateView = callActivityOnCreateView(parent, name, context2, attrs);
        View view3 = callActivityOnCreateView;
        if (callActivityOnCreateView == null) {
            return createView(parent, name, context2, attrs);
        }
        return view3;
    }

    /* access modifiers changed from: 0000 */
    public View callActivityOnCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        String name = str;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        View view2 = view;
        String str2 = name;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        if (this.mOriginalWindowCallback instanceof Factory) {
            View onCreateView = ((Factory) this.mOriginalWindowCallback).onCreateView(name, context2, attrs);
            View result = onCreateView;
            if (onCreateView != null) {
                return result;
            }
        }
        return null;
    }

    private void openPanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        PanelFeatureState st = panelFeatureState;
        KeyEvent event = keyEvent;
        PanelFeatureState panelFeatureState2 = st;
        KeyEvent keyEvent2 = event;
        if (!st.isOpen && !isDestroyed()) {
            if (st.featureId == 0) {
                Context context = this.mContext;
                Context context2 = context;
                Configuration configuration = context.getResources().getConfiguration();
                Configuration configuration2 = configuration;
                boolean isXLarge = (configuration.screenLayout & 15) == 4;
                boolean isHoneycombApp = context2.getApplicationInfo().targetSdkVersion >= 11;
                if (isXLarge && isHoneycombApp) {
                    return;
                }
            }
            Window.Callback windowCallback = getWindowCallback();
            Window.Callback cb = windowCallback;
            if (windowCallback != null) {
                if (!cb.onMenuOpened(st.featureId, st.menu)) {
                    closePanel(st, true);
                    return;
                }
            }
            WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
            WindowManager wm = windowManager;
            if (windowManager != null && preparePanel(st, event)) {
                int width = -2;
                if (st.decorView == null || st.refreshDecorView) {
                    if (st.decorView != null) {
                        if (st.refreshDecorView && st.decorView.getChildCount() > 0) {
                            st.decorView.removeAllViews();
                        }
                    } else if (!initializePanelDecor(st) || st.decorView == null) {
                        return;
                    }
                    if (initializePanelContent(st) && st.hasPanelItems()) {
                        LayoutParams layoutParams = st.shownPanelView.getLayoutParams();
                        LayoutParams lp = layoutParams;
                        if (layoutParams == null) {
                            LayoutParams layoutParams2 = new LayoutParams(-2, -2);
                            lp = layoutParams2;
                        }
                        int i = st.background;
                        int i2 = i;
                        st.decorView.setBackgroundResource(i);
                        ViewParent parent = st.shownPanelView.getParent();
                        ViewParent shownPanelParent = parent;
                        if (parent != null && (shownPanelParent instanceof ViewGroup)) {
                            ((ViewGroup) shownPanelParent).removeView(st.shownPanelView);
                        }
                        st.decorView.addView(st.shownPanelView, lp);
                        if (!st.shownPanelView.hasFocus()) {
                            boolean requestFocus = st.shownPanelView.requestFocus();
                        }
                    } else {
                        return;
                    }
                } else if (st.createdPanelView != null) {
                    LayoutParams layoutParams3 = st.createdPanelView.getLayoutParams();
                    LayoutParams lp2 = layoutParams3;
                    if (layoutParams3 != null && lp2.width == -1) {
                        width = -1;
                    }
                }
                st.isHandled = false;
                WindowManager.LayoutParams layoutParams4 = new WindowManager.LayoutParams(width, -2, st.f17x, st.f18y, PointerIconCompat.TYPE_HAND, 8519680, -3);
                WindowManager.LayoutParams lp3 = layoutParams4;
                layoutParams4.gravity = st.gravity;
                lp3.windowAnimations = st.windowAnimations;
                wm.addView(st.decorView, lp3);
                st.isOpen = true;
            }
        }
    }

    private boolean initializePanelDecor(PanelFeatureState panelFeatureState) {
        PanelFeatureState st = panelFeatureState;
        PanelFeatureState panelFeatureState2 = st;
        st.setStyle(getActionBarThemedContext());
        st.decorView = new ListMenuDecorView(this, st.listPresenterContext);
        st.gravity = 81;
        return true;
    }

    private void reopenMenu(MenuBuilder menuBuilder, boolean z) {
        MenuBuilder menuBuilder2 = menuBuilder;
        boolean toggleMenuMode = z;
        if (this.mDecorContentParent != null && this.mDecorContentParent.canShowOverflowMenu() && (!ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(this.mContext)) || this.mDecorContentParent.isOverflowMenuShowPending())) {
            Window.Callback cb = getWindowCallback();
            if (this.mDecorContentParent.isOverflowMenuShowing() && toggleMenuMode) {
                boolean hideOverflowMenu = this.mDecorContentParent.hideOverflowMenu();
                if (!isDestroyed()) {
                    cb.onPanelClosed(108, getPanelState(0, true).menu);
                }
            } else if (cb != null && !isDestroyed()) {
                if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 1) != 0) {
                    boolean removeCallbacks = this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
                    this.mInvalidatePanelMenuRunnable.run();
                }
                PanelFeatureState panelState = getPanelState(0, true);
                PanelFeatureState st = panelState;
                if (panelState.menu != null && !st.refreshMenuContent) {
                    if (cb.onPreparePanel(0, st.createdPanelView, st.menu)) {
                        boolean onMenuOpened = cb.onMenuOpened(108, st.menu);
                        boolean showOverflowMenu = this.mDecorContentParent.showOverflowMenu();
                    }
                }
            }
            return;
        }
        PanelFeatureState panelState2 = getPanelState(0, true);
        PanelFeatureState st2 = panelState2;
        panelState2.refreshDecorView = true;
        closePanel(st2, false);
        openPanel(st2, null);
    }

    private boolean initializePanelMenu(PanelFeatureState panelFeatureState) {
        PanelFeatureState st = panelFeatureState;
        PanelFeatureState panelFeatureState2 = st;
        Context context = this.mContext;
        if ((st.featureId == 0 || st.featureId == 108) && this.mDecorContentParent != null) {
            TypedValue outValue = new TypedValue();
            Theme theme = context.getTheme();
            Theme baseTheme = theme;
            boolean resolveAttribute = theme.resolveAttribute(C0268R.attr.actionBarTheme, outValue, true);
            Theme widgetTheme = null;
            if (outValue.resourceId == 0) {
                boolean resolveAttribute2 = baseTheme.resolveAttribute(C0268R.attr.actionBarWidgetTheme, outValue, true);
            } else {
                Theme newTheme = context.getResources().newTheme();
                widgetTheme = newTheme;
                newTheme.setTo(baseTheme);
                widgetTheme.applyStyle(outValue.resourceId, true);
                boolean resolveAttribute3 = widgetTheme.resolveAttribute(C0268R.attr.actionBarWidgetTheme, outValue, true);
            }
            if (outValue.resourceId != 0) {
                if (widgetTheme == null) {
                    Theme newTheme2 = context.getResources().newTheme();
                    widgetTheme = newTheme2;
                    newTheme2.setTo(baseTheme);
                }
                widgetTheme.applyStyle(outValue.resourceId, true);
            }
            if (widgetTheme != null) {
                ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 0);
                context = contextThemeWrapper;
                contextThemeWrapper.getTheme().setTo(widgetTheme);
            }
        }
        MenuBuilder menuBuilder = new MenuBuilder(context);
        MenuBuilder menu = menuBuilder;
        menuBuilder.setCallback(this);
        st.setMenu(menu);
        return true;
    }

    private boolean initializePanelContent(PanelFeatureState panelFeatureState) {
        boolean z;
        PanelFeatureState st = panelFeatureState;
        PanelFeatureState panelFeatureState2 = st;
        if (st.createdPanelView != null) {
            st.shownPanelView = st.createdPanelView;
            return true;
        } else if (st.menu == null) {
            return false;
        } else {
            if (this.mPanelMenuPresenterCallback == null) {
                this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback(this);
            }
            st.shownPanelView = (View) st.getListMenuView(this.mPanelMenuPresenterCallback);
            if (st.shownPanelView == null) {
                z = false;
            } else {
                z = true;
            }
            return z;
        }
    }

    private boolean preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        PanelFeatureState st = panelFeatureState;
        KeyEvent event = keyEvent;
        PanelFeatureState panelFeatureState2 = st;
        KeyEvent keyEvent2 = event;
        if (isDestroyed()) {
            return false;
        }
        if (st.isPrepared) {
            return true;
        }
        if (!(this.mPreparedPanel == null || this.mPreparedPanel == st)) {
            closePanel(this.mPreparedPanel, false);
        }
        Window.Callback windowCallback = getWindowCallback();
        Window.Callback cb = windowCallback;
        if (windowCallback != null) {
            st.createdPanelView = cb.onCreatePanelView(st.featureId);
        }
        boolean isActionBarMenu = st.featureId == 0 || st.featureId == 108;
        if (isActionBarMenu && this.mDecorContentParent != null) {
            this.mDecorContentParent.setMenuPrepared();
        }
        if (st.createdPanelView == null && (!isActionBarMenu || !(peekSupportActionBar() instanceof ToolbarActionBar))) {
            if (st.menu == null || st.refreshMenuContent) {
                if (st.menu == null && (!initializePanelMenu(st) || st.menu == null)) {
                    return false;
                }
                if (isActionBarMenu && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        ActionMenuPresenterCallback actionMenuPresenterCallback = new ActionMenuPresenterCallback(this);
                        this.mActionMenuPresenterCallback = actionMenuPresenterCallback;
                    }
                    this.mDecorContentParent.setMenu(st.menu, this.mActionMenuPresenterCallback);
                }
                st.menu.stopDispatchingItemsChanged();
                if (cb.onCreatePanelMenu(st.featureId, st.menu)) {
                    st.refreshMenuContent = false;
                } else {
                    st.setMenu(null);
                    if (isActionBarMenu && this.mDecorContentParent != null) {
                        this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
            }
            st.menu.stopDispatchingItemsChanged();
            if (st.frozenActionViewState != null) {
                st.menu.restoreActionViewStates(st.frozenActionViewState);
                st.frozenActionViewState = null;
            }
            if (cb.onPreparePanel(0, st.createdPanelView, st.menu)) {
                st.qwertyMode = KeyCharacterMap.load(event == null ? -1 : event.getDeviceId()).getKeyboardType() != 1;
                st.menu.setQwertyMode(st.qwertyMode);
                st.menu.startDispatchingItemsChanged();
            } else {
                if (isActionBarMenu && this.mDecorContentParent != null) {
                    this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                }
                st.menu.startDispatchingItemsChanged();
                return false;
            }
        }
        st.isPrepared = true;
        st.isHandled = false;
        this.mPreparedPanel = st;
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void checkCloseActionMenu(MenuBuilder menuBuilder) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        if (!this.mClosingActionMenu) {
            this.mClosingActionMenu = true;
            this.mDecorContentParent.dismissPopups();
            Window.Callback windowCallback = getWindowCallback();
            Window.Callback cb = windowCallback;
            if (windowCallback != null && !isDestroyed()) {
                cb.onPanelClosed(108, menu);
            }
            this.mClosingActionMenu = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void closePanel(int i) {
        int featureId = i;
        int i2 = featureId;
        closePanel(getPanelState(featureId, true), true);
    }

    /* access modifiers changed from: 0000 */
    public void closePanel(PanelFeatureState panelFeatureState, boolean z) {
        PanelFeatureState st = panelFeatureState;
        PanelFeatureState panelFeatureState2 = st;
        boolean doCallback = z;
        if (doCallback && st.featureId == 0 && this.mDecorContentParent != null && this.mDecorContentParent.isOverflowMenuShowing()) {
            checkCloseActionMenu(st.menu);
            return;
        }
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        WindowManager wm = windowManager;
        if (!(windowManager == null || !st.isOpen || st.decorView == null)) {
            wm.removeView(st.decorView);
            if (doCallback) {
                callOnPanelClosed(st.featureId, st, null);
            }
        }
        st.isPrepared = false;
        st.isHandled = false;
        st.isOpen = false;
        st.shownPanelView = null;
        st.refreshDecorView = true;
        if (this.mPreparedPanel == st) {
            this.mPreparedPanel = null;
        }
    }

    private boolean onKeyDownPanel(int i, KeyEvent keyEvent) {
        int featureId = i;
        KeyEvent event = keyEvent;
        int i2 = featureId;
        KeyEvent keyEvent2 = event;
        if (event.getRepeatCount() == 0) {
            PanelFeatureState panelState = getPanelState(featureId, true);
            PanelFeatureState st = panelState;
            if (!panelState.isOpen) {
                return preparePanel(st, event);
            }
        }
        return false;
    }

    private boolean onKeyUpPanel(int i, KeyEvent keyEvent) {
        int featureId = i;
        KeyEvent event = keyEvent;
        int i2 = featureId;
        KeyEvent keyEvent2 = event;
        if (this.mActionMode != null) {
            return false;
        }
        boolean handled = false;
        PanelFeatureState st = getPanelState(featureId, true);
        if (featureId == 0 && this.mDecorContentParent != null && this.mDecorContentParent.canShowOverflowMenu() && !ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(this.mContext))) {
            if (this.mDecorContentParent.isOverflowMenuShowing()) {
                handled = this.mDecorContentParent.hideOverflowMenu();
            } else if (!isDestroyed() && preparePanel(st, event)) {
                handled = this.mDecorContentParent.showOverflowMenu();
            }
        } else if (st.isOpen || st.isHandled) {
            handled = st.isOpen;
            closePanel(st, true);
        } else if (st.isPrepared) {
            boolean show = true;
            if (st.refreshMenuContent) {
                st.isPrepared = false;
                show = preparePanel(st, event);
            }
            if (show) {
                openPanel(st, event);
                handled = true;
            }
        }
        if (handled) {
            AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
            AudioManager audioManager2 = audioManager;
            if (audioManager == null) {
                int w = Log.w("AppCompatDelegate", "Couldn't get audio manager");
            } else {
                audioManager2.playSoundEffect(0);
            }
        }
        return handled;
    }

    /* access modifiers changed from: 0000 */
    public void callOnPanelClosed(int i, PanelFeatureState panelFeatureState, Menu menu) {
        int featureId = i;
        PanelFeatureState panel = panelFeatureState;
        Menu menu2 = menu;
        int i2 = featureId;
        PanelFeatureState panel2 = panel;
        Menu menu3 = menu2;
        if (menu2 == null) {
            if (panel == null && featureId >= 0 && featureId < this.mPanels.length) {
                panel2 = this.mPanels[featureId];
            }
            if (panel2 != null) {
                menu3 = panel2.menu;
            }
        }
        if ((panel2 == null || panel2.isOpen) && !isDestroyed()) {
            this.mOriginalWindowCallback.onPanelClosed(featureId, menu3);
        }
    }

    /* access modifiers changed from: 0000 */
    public PanelFeatureState findMenuPanel(Menu menu) {
        Menu menu2 = menu;
        Menu menu3 = menu2;
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        PanelFeatureState[] panels = panelFeatureStateArr;
        int N = panelFeatureStateArr == null ? 0 : panels.length;
        for (int i = 0; i < N; i++) {
            PanelFeatureState panelFeatureState = panels[i];
            PanelFeatureState panel = panelFeatureState;
            if (panelFeatureState != null && panel.menu == menu2) {
                return panel;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public PanelFeatureState getPanelState(int i, boolean z) {
        int featureId = i;
        int i2 = featureId;
        boolean z2 = z;
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        PanelFeatureState[] ar = panelFeatureStateArr;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= featureId) {
            PanelFeatureState[] nar = new PanelFeatureState[(featureId + 1)];
            if (ar != null) {
                System.arraycopy(ar, 0, nar, 0, ar.length);
            }
            ar = nar;
            this.mPanels = nar;
        }
        PanelFeatureState panelFeatureState = ar[featureId];
        PanelFeatureState st = panelFeatureState;
        if (panelFeatureState == null) {
            PanelFeatureState[] panelFeatureStateArr2 = ar;
            PanelFeatureState panelFeatureState2 = new PanelFeatureState(featureId);
            st = panelFeatureState2;
            panelFeatureStateArr2[featureId] = panelFeatureState2;
        }
        return st;
    }

    private boolean performPanelShortcut(PanelFeatureState panelFeatureState, int i, KeyEvent keyEvent, int i2) {
        PanelFeatureState st = panelFeatureState;
        int keyCode = i;
        KeyEvent event = keyEvent;
        int flags = i2;
        PanelFeatureState panelFeatureState2 = st;
        int i3 = keyCode;
        KeyEvent keyEvent2 = event;
        int i4 = flags;
        if (event.isSystem()) {
            return false;
        }
        boolean handled = false;
        if ((st.isPrepared || preparePanel(st, event)) && st.menu != null) {
            handled = st.menu.performShortcut(keyCode, event, flags);
        }
        if (handled && (flags & 1) == 0 && this.mDecorContentParent == null) {
            closePanel(st, true);
        }
        return handled;
    }

    private void invalidatePanelMenu(int i) {
        int featureId = i;
        int i2 = featureId;
        this.mInvalidatePanelMenuFeatures |= 1 << featureId;
        if (!this.mInvalidatePanelMenuPosted) {
            ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public void doInvalidatePanelMenu(int i) {
        int featureId = i;
        int i2 = featureId;
        PanelFeatureState st = getPanelState(featureId, true);
        if (st.menu != null) {
            Bundle savedActionViewStates = new Bundle();
            st.menu.saveActionViewStates(savedActionViewStates);
            if (savedActionViewStates.size() > 0) {
                st.frozenActionViewState = savedActionViewStates;
            }
            st.menu.stopDispatchingItemsChanged();
            st.menu.clear();
        }
        st.refreshMenuContent = true;
        st.refreshDecorView = true;
        if ((featureId == 108 || featureId == 0) && this.mDecorContentParent != null) {
            PanelFeatureState panelState = getPanelState(0, false);
            PanelFeatureState st2 = panelState;
            if (panelState != null) {
                st2.isPrepared = false;
                boolean preparePanel = preparePanel(st2, null);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int updateStatusGuard(int i) {
        int insetTop = i;
        int insetTop2 = insetTop;
        boolean showStatusGuard = false;
        if (this.mActionModeView != null && (this.mActionModeView.getLayoutParams() instanceof MarginLayoutParams)) {
            MarginLayoutParams mlp = (MarginLayoutParams) this.mActionModeView.getLayoutParams();
            boolean mlpChanged = false;
            if (this.mActionModeView.isShown()) {
                if (this.mTempRect1 == null) {
                    this.mTempRect1 = new Rect();
                    this.mTempRect2 = new Rect();
                }
                Rect insets = this.mTempRect1;
                Rect localInsets = this.mTempRect2;
                insets.set(0, insetTop, 0, 0);
                ViewUtils.computeFitSystemWindows(this.mSubDecor, insets, localInsets);
                if (mlp.topMargin != (localInsets.top != 0 ? 0 : insetTop)) {
                    mlpChanged = true;
                    mlp.topMargin = insetTop;
                    if (this.mStatusGuard != null) {
                        LayoutParams layoutParams = this.mStatusGuard.getLayoutParams();
                        LayoutParams lp = layoutParams;
                        if (layoutParams.height != insetTop) {
                            lp.height = insetTop;
                            this.mStatusGuard.setLayoutParams(lp);
                        }
                    } else {
                        View view = new View(this.mContext);
                        this.mStatusGuard = view;
                        this.mStatusGuard.setBackgroundColor(this.mContext.getResources().getColor(C0268R.color.abc_input_method_navigation_guard));
                        ViewGroup viewGroup = this.mSubDecor;
                        View view2 = this.mStatusGuard;
                        LayoutParams layoutParams2 = new LayoutParams(-1, insetTop);
                        viewGroup.addView(view2, -1, layoutParams2);
                    }
                }
                showStatusGuard = this.mStatusGuard != null;
                if (!this.mOverlayActionMode && showStatusGuard) {
                    insetTop2 = 0;
                }
            } else if (mlp.topMargin != 0) {
                mlpChanged = true;
                mlp.topMargin = 0;
            }
            if (mlpChanged) {
                this.mActionModeView.setLayoutParams(mlp);
            }
        }
        if (this.mStatusGuard != null) {
            this.mStatusGuard.setVisibility(!showStatusGuard ? 8 : 0);
        }
        return insetTop2;
    }

    private void throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    private int sanitizeWindowFeatureId(int i) {
        int featureId = i;
        int i2 = featureId;
        if (featureId == 8) {
            int i3 = Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        } else if (featureId != 9) {
            return featureId;
        } else {
            int i4 = Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
    }

    /* access modifiers changed from: 0000 */
    public ViewGroup getSubDecor() {
        return this.mSubDecor;
    }

    /* access modifiers changed from: 0000 */
    public void dismissPopups() {
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.dismissPopups();
        }
        if (this.mActionModePopup != null) {
            boolean removeCallbacks = this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
            if (this.mActionModePopup.isShowing()) {
                try {
                    this.mActionModePopup.dismiss();
                } catch (IllegalArgumentException e) {
                }
            }
            this.mActionModePopup = null;
        }
        endOnGoingFadeAnimation();
        PanelFeatureState panelState = getPanelState(0, false);
        PanelFeatureState st = panelState;
        if (panelState != null && st.menu != null) {
            st.menu.close();
        }
    }
}
