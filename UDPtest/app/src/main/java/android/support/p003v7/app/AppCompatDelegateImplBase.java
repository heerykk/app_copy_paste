package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.p003v7.app.ActionBarDrawerToggle.Delegate;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.view.ActionMode;
import android.support.p003v7.view.SupportMenuInflater;
import android.support.p003v7.view.WindowCallbackWrapper;
import android.support.p003v7.view.menu.MenuBuilder;
import android.support.p003v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;
import java.lang.Thread.UncaughtExceptionHandler;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v7.app.AppCompatDelegateImplBase */
abstract class AppCompatDelegateImplBase extends AppCompatDelegate {
    static final boolean DEBUG = false;
    static final String EXCEPTION_HANDLER_MESSAGE_SUFFIX = ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.";
    private static final boolean SHOULD_INSTALL_EXCEPTION_HANDLER = (VERSION.SDK_INT < 21);
    private static boolean sInstalledExceptionHandler = true;
    private static final int[] sWindowBackgroundStyleable;
    ActionBar mActionBar;
    final AppCompatCallback mAppCompatCallback;
    final Callback mAppCompatWindowCallback;
    final Context mContext;
    boolean mHasActionBar;
    private boolean mIsDestroyed;
    boolean mIsFloating;
    private boolean mIsStarted;
    MenuInflater mMenuInflater;
    final Callback mOriginalWindowCallback = this.mWindow.getCallback();
    boolean mOverlayActionBar;
    boolean mOverlayActionMode;
    private CharSequence mTitle;
    final Window mWindow;
    boolean mWindowNoTitle;

    /* renamed from: android.support.v7.app.AppCompatDelegateImplBase$ActionBarDrawableToggleImpl */
    private class ActionBarDrawableToggleImpl implements Delegate {
        final /* synthetic */ AppCompatDelegateImplBase this$0;

        ActionBarDrawableToggleImpl(AppCompatDelegateImplBase appCompatDelegateImplBase) {
            this.this$0 = appCompatDelegateImplBase;
        }

        public Drawable getThemeUpIndicator() {
            Context actionBarThemedContext = getActionBarThemedContext();
            int[] iArr = new int[1];
            iArr[0] = C0268R.attr.homeAsUpIndicator;
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(actionBarThemedContext, (AttributeSet) null, iArr);
            TintTypedArray a = obtainStyledAttributes;
            Drawable result = obtainStyledAttributes.getDrawable(0);
            a.recycle();
            return result;
        }

        public Context getActionBarThemedContext() {
            return this.this$0.getActionBarThemedContext();
        }

        public boolean isNavigationVisible() {
            ActionBar supportActionBar = this.this$0.getSupportActionBar();
            return (supportActionBar == null || (supportActionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        public void setActionBarUpIndicator(Drawable drawable, int i) {
            Drawable upDrawable = drawable;
            int contentDescRes = i;
            Drawable drawable2 = upDrawable;
            int i2 = contentDescRes;
            ActionBar supportActionBar = this.this$0.getSupportActionBar();
            ActionBar ab = supportActionBar;
            if (supportActionBar != null) {
                ab.setHomeAsUpIndicator(upDrawable);
                ab.setHomeActionContentDescription(contentDescRes);
            }
        }

        public void setActionBarDescription(int i) {
            int contentDescRes = i;
            int i2 = contentDescRes;
            ActionBar supportActionBar = this.this$0.getSupportActionBar();
            ActionBar ab = supportActionBar;
            if (supportActionBar != null) {
                ab.setHomeActionContentDescription(contentDescRes);
            }
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImplBase$AppCompatWindowCallbackBase */
    class AppCompatWindowCallbackBase extends WindowCallbackWrapper {
        final /* synthetic */ AppCompatDelegateImplBase this$0;

        AppCompatWindowCallbackBase(AppCompatDelegateImplBase appCompatDelegateImplBase, Callback callback) {
            AppCompatDelegateImplBase this$02 = appCompatDelegateImplBase;
            Callback callback2 = callback;
            AppCompatDelegateImplBase appCompatDelegateImplBase2 = this$02;
            Callback callback3 = callback2;
            this.this$0 = this$02;
            super(callback2);
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            KeyEvent event = keyEvent;
            KeyEvent keyEvent2 = event;
            return this.this$0.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
        }

        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            KeyEvent event = keyEvent;
            KeyEvent keyEvent2 = event;
            return super.dispatchKeyShortcutEvent(event) || this.this$0.onKeyShortcut(event.getKeyCode(), event);
        }

        public boolean onCreatePanelMenu(int i, Menu menu) {
            int featureId = i;
            Menu menu2 = menu;
            int i2 = featureId;
            Menu menu3 = menu2;
            if (featureId == 0 && !(menu2 instanceof MenuBuilder)) {
                return false;
            }
            return super.onCreatePanelMenu(featureId, menu2);
        }

        public void onContentChanged() {
        }

        public boolean onPreparePanel(int i, View view, Menu menu) {
            int featureId = i;
            View view2 = view;
            Menu menu2 = menu;
            int i2 = featureId;
            View view3 = view2;
            Menu menu3 = menu2;
            MenuBuilder mb = !(menu2 instanceof MenuBuilder) ? null : (MenuBuilder) menu2;
            if (featureId == 0 && mb == null) {
                return false;
            }
            if (mb != null) {
                mb.setOverrideVisibleItems(true);
            }
            boolean onPreparePanel = super.onPreparePanel(featureId, view2, menu2);
            boolean z = onPreparePanel;
            if (mb != null) {
                mb.setOverrideVisibleItems(false);
            }
            return onPreparePanel;
        }

        public boolean onMenuOpened(int i, Menu menu) {
            int featureId = i;
            Menu menu2 = menu;
            int i2 = featureId;
            Menu menu3 = menu2;
            boolean onMenuOpened = super.onMenuOpened(featureId, menu2);
            boolean onMenuOpened2 = this.this$0.onMenuOpened(featureId, menu2);
            return true;
        }

        public void onPanelClosed(int i, Menu menu) {
            int featureId = i;
            Menu menu2 = menu;
            int i2 = featureId;
            Menu menu3 = menu2;
            super.onPanelClosed(featureId, menu2);
            this.this$0.onPanelClosed(featureId, menu2);
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract boolean dispatchKeyEvent(KeyEvent keyEvent);

    /* access modifiers changed from: 0000 */
    public abstract void initWindowDecorActionBar();

    /* access modifiers changed from: 0000 */
    public abstract boolean onKeyShortcut(int i, KeyEvent keyEvent);

    /* access modifiers changed from: 0000 */
    public abstract boolean onMenuOpened(int i, Menu menu);

    /* access modifiers changed from: 0000 */
    public abstract void onPanelClosed(int i, Menu menu);

    /* access modifiers changed from: 0000 */
    public abstract void onTitleChanged(CharSequence charSequence);

    /* access modifiers changed from: 0000 */
    public abstract ActionMode startSupportActionModeFromWindow(ActionMode.Callback callback);

    static {
        if (SHOULD_INSTALL_EXCEPTION_HANDLER && !sInstalledExceptionHandler) {
            final UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                public void uncaughtException(Thread thread, Throwable th) {
                    Thread thread2 = thread;
                    Throwable thowable = th;
                    Thread thread3 = thread2;
                    Throwable th2 = thowable;
                    if (!shouldWrapException(thowable)) {
                        defaultUncaughtExceptionHandler.uncaughtException(thread2, thowable);
                        return;
                    }
                    NotFoundException notFoundException = new NotFoundException(thowable.getMessage() + AppCompatDelegateImplBase.EXCEPTION_HANDLER_MESSAGE_SUFFIX);
                    NotFoundException notFoundException2 = notFoundException;
                    Throwable initCause = notFoundException.initCause(thowable.getCause());
                    notFoundException2.setStackTrace(thowable.getStackTrace());
                    defaultUncaughtExceptionHandler.uncaughtException(thread2, notFoundException2);
                }

                private boolean shouldWrapException(Throwable th) {
                    Throwable throwable = th;
                    Throwable th2 = throwable;
                    if (!(throwable instanceof NotFoundException)) {
                        return false;
                    }
                    String message = throwable.getMessage();
                    String message2 = message;
                    return message != null && (message2.contains("drawable") || message2.contains("Drawable"));
                }
            });
        }
        int[] iArr = new int[1];
        iArr[0] = 16842836;
        sWindowBackgroundStyleable = iArr;
    }

    AppCompatDelegateImplBase(Context context, Window window, AppCompatCallback appCompatCallback) {
        Context context2 = context;
        Window window2 = window;
        AppCompatCallback callback = appCompatCallback;
        Context context3 = context2;
        Window window3 = window2;
        AppCompatCallback appCompatCallback2 = callback;
        this.mContext = context2;
        this.mWindow = window2;
        this.mAppCompatCallback = callback;
        if (!(this.mOriginalWindowCallback instanceof AppCompatWindowCallbackBase)) {
            this.mAppCompatWindowCallback = wrapWindowCallback(this.mOriginalWindowCallback);
            this.mWindow.setCallback(this.mAppCompatWindowCallback);
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, (AttributeSet) null, sWindowBackgroundStyleable);
            TintTypedArray a = obtainStyledAttributes;
            Drawable drawableIfKnown = obtainStyledAttributes.getDrawableIfKnown(0);
            Drawable winBg = drawableIfKnown;
            if (drawableIfKnown != null) {
                this.mWindow.setBackgroundDrawable(winBg);
            }
            a.recycle();
            return;
        }
        throw new IllegalStateException("AppCompat has already installed itself into the Window");
    }

    /* access modifiers changed from: 0000 */
    public Callback wrapWindowCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return new AppCompatWindowCallbackBase(this, callback2);
    }

    public ActionBar getSupportActionBar() {
        initWindowDecorActionBar();
        return this.mActionBar;
    }

    /* access modifiers changed from: 0000 */
    public final ActionBar peekSupportActionBar() {
        return this.mActionBar;
    }

    public MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            initWindowDecorActionBar();
            this.mMenuInflater = new SupportMenuInflater(this.mActionBar == null ? this.mContext : this.mActionBar.getThemedContext());
        }
        return this.mMenuInflater;
    }

    public void setLocalNightMode(int i) {
        int i2 = i;
    }

    public final Delegate getDrawerToggleDelegate() {
        return new ActionBarDrawableToggleImpl(this);
    }

    /* access modifiers changed from: 0000 */
    public final Context getActionBarThemedContext() {
        Context context = null;
        ActionBar supportActionBar = getSupportActionBar();
        ActionBar ab = supportActionBar;
        if (supportActionBar != null) {
            context = ab.getThemedContext();
        }
        if (context == null) {
            context = this.mContext;
        }
        return context;
    }

    public void onStart() {
        this.mIsStarted = true;
    }

    public void onStop() {
        this.mIsStarted = false;
    }

    public void onDestroy() {
        this.mIsDestroyed = true;
    }

    public void setHandleNativeActionModesEnabled(boolean z) {
        boolean z2 = z;
    }

    public boolean isHandleNativeActionModesEnabled() {
        return false;
    }

    public boolean applyDayNight() {
        return false;
    }

    /* access modifiers changed from: 0000 */
    public final boolean isDestroyed() {
        return this.mIsDestroyed;
    }

    /* access modifiers changed from: 0000 */
    public final boolean isStarted() {
        return this.mIsStarted;
    }

    /* access modifiers changed from: 0000 */
    public final Callback getWindowCallback() {
        return this.mWindow.getCallback();
    }

    public final void setTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mTitle = title;
        onTitleChanged(title);
    }

    public void onSaveInstanceState(Bundle bundle) {
        Bundle bundle2 = bundle;
    }

    /* access modifiers changed from: 0000 */
    public final CharSequence getTitle() {
        if (!(this.mOriginalWindowCallback instanceof Activity)) {
            return this.mTitle;
        }
        return ((Activity) this.mOriginalWindowCallback).getTitle();
    }
}
