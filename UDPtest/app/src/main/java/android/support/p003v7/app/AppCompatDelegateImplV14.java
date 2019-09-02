package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.p003v7.view.SupportActionModeWrapper.CallbackWrapper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Window;
import android.view.Window.Callback;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v7.app.AppCompatDelegateImplV14 */
class AppCompatDelegateImplV14 extends AppCompatDelegateImplV11 {
    private static final String KEY_LOCAL_NIGHT_MODE = "appcompat:local_night_mode";
    private boolean mApplyDayNightCalled;
    private AutoNightModeManager mAutoNightModeManager;
    private boolean mHandleNativeActionModes = true;
    private int mLocalNightMode = -100;

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV14$AppCompatWindowCallbackV14 */
    class AppCompatWindowCallbackV14 extends AppCompatWindowCallbackBase {
        final /* synthetic */ AppCompatDelegateImplV14 this$0;

        AppCompatWindowCallbackV14(AppCompatDelegateImplV14 appCompatDelegateImplV14, Callback callback) {
            AppCompatDelegateImplV14 this$02 = appCompatDelegateImplV14;
            Callback callback2 = callback;
            AppCompatDelegateImplV14 appCompatDelegateImplV142 = this$02;
            Callback callback3 = callback2;
            this.this$0 = this$02;
            super(this$02, callback2);
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            ActionMode.Callback callback2 = callback;
            ActionMode.Callback callback3 = callback2;
            if (!this.this$0.isHandleNativeActionModesEnabled()) {
                return super.onWindowStartingActionMode(callback2);
            }
            return startAsSupportActionMode(callback2);
        }

        /* access modifiers changed from: 0000 */
        public final ActionMode startAsSupportActionMode(ActionMode.Callback callback) {
            ActionMode.Callback callback2 = callback;
            ActionMode.Callback callback3 = callback2;
            CallbackWrapper callbackWrapper = new CallbackWrapper(this.this$0.mContext, callback2);
            android.support.p003v7.view.ActionMode startSupportActionMode = this.this$0.startSupportActionMode(callbackWrapper);
            android.support.p003v7.view.ActionMode supportActionMode = startSupportActionMode;
            if (startSupportActionMode == null) {
                return null;
            }
            return callbackWrapper.getActionModeWrapper(supportActionMode);
        }
    }

    @VisibleForTesting
    /* renamed from: android.support.v7.app.AppCompatDelegateImplV14$AutoNightModeManager */
    final class AutoNightModeManager {
        private BroadcastReceiver mAutoTimeChangeReceiver;
        private IntentFilter mAutoTimeChangeReceiverFilter;
        private boolean mIsNight;
        private TwilightManager mTwilightManager;
        final /* synthetic */ AppCompatDelegateImplV14 this$0;

        AutoNightModeManager(AppCompatDelegateImplV14 appCompatDelegateImplV14, @NonNull TwilightManager twilightManager) {
            AppCompatDelegateImplV14 this$02 = appCompatDelegateImplV14;
            TwilightManager twilightManager2 = twilightManager;
            AppCompatDelegateImplV14 appCompatDelegateImplV142 = this$02;
            TwilightManager twilightManager3 = twilightManager2;
            this.this$0 = this$02;
            this.mTwilightManager = twilightManager2;
            this.mIsNight = twilightManager2.isNight();
        }

        /* access modifiers changed from: 0000 */
        public final int getApplyableNightMode() {
            this.mIsNight = this.mTwilightManager.isNight();
            return !this.mIsNight ? 1 : 2;
        }

        /* access modifiers changed from: 0000 */
        public final void dispatchTimeChanged() {
            boolean isNight = this.mTwilightManager.isNight();
            boolean isNight2 = isNight;
            if (isNight != this.mIsNight) {
                this.mIsNight = isNight2;
                boolean applyDayNight = this.this$0.applyDayNight();
            }
        }

        /* access modifiers changed from: 0000 */
        public final void setup() {
            cleanup();
            if (this.mAutoTimeChangeReceiver == null) {
                this.mAutoTimeChangeReceiver = new BroadcastReceiver(this) {
                    final /* synthetic */ AutoNightModeManager this$1;

                    {
                        AutoNightModeManager this$12 = r5;
                        AutoNightModeManager autoNightModeManager = this$12;
                        this.this$1 = this$12;
                    }

                    public void onReceive(Context context, Intent intent) {
                        Context context2 = context;
                        Intent intent2 = intent;
                        this.this$1.dispatchTimeChanged();
                    }
                };
            }
            if (this.mAutoTimeChangeReceiverFilter == null) {
                this.mAutoTimeChangeReceiverFilter = new IntentFilter();
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_SET");
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_TICK");
            }
            Intent registerReceiver = this.this$0.mContext.registerReceiver(this.mAutoTimeChangeReceiver, this.mAutoTimeChangeReceiverFilter);
        }

        /* access modifiers changed from: 0000 */
        public final void cleanup() {
            if (this.mAutoTimeChangeReceiver != null) {
                this.this$0.mContext.unregisterReceiver(this.mAutoTimeChangeReceiver);
                this.mAutoTimeChangeReceiver = null;
            }
        }
    }

    AppCompatDelegateImplV14(Context context, Window window, AppCompatCallback appCompatCallback) {
        Context context2 = context;
        Window window2 = window;
        AppCompatCallback callback = appCompatCallback;
        Context context3 = context2;
        Window window3 = window2;
        AppCompatCallback appCompatCallback2 = callback;
        super(context2, window2, callback);
    }

    public void onCreate(Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && this.mLocalNightMode == -100) {
            this.mLocalNightMode = savedInstanceState.getInt(KEY_LOCAL_NIGHT_MODE, -100);
        }
    }

    /* access modifiers changed from: 0000 */
    public Callback wrapWindowCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return new AppCompatWindowCallbackV14(this, callback2);
    }

    public void setHandleNativeActionModesEnabled(boolean z) {
        this.mHandleNativeActionModes = z;
    }

    public boolean isHandleNativeActionModesEnabled() {
        return this.mHandleNativeActionModes;
    }

    public boolean applyDayNight() {
        boolean applied = false;
        int nightMode = getNightMode();
        int nightMode2 = nightMode;
        int mapNightMode = mapNightMode(nightMode);
        int modeToApply = mapNightMode;
        if (mapNightMode != -1) {
            applied = updateForNightMode(modeToApply);
        }
        if (nightMode2 == 0) {
            ensureAutoNightModeManager();
            this.mAutoNightModeManager.setup();
        }
        this.mApplyDayNightCalled = true;
        return applied;
    }

    public void onStart() {
        super.onStart();
        boolean applyDayNight = applyDayNight();
    }

    public void onStop() {
        super.onStop();
        if (this.mAutoNightModeManager != null) {
            this.mAutoNightModeManager.cleanup();
        }
    }

    public void setLocalNightMode(int i) {
        int mode = i;
        int i2 = mode;
        switch (mode) {
            case -1:
            case 0:
            case 1:
            case 2:
                if (this.mLocalNightMode != mode) {
                    this.mLocalNightMode = mode;
                    if (this.mApplyDayNightCalled) {
                        boolean applyDayNight = applyDayNight();
                        return;
                    }
                    return;
                }
                return;
            default:
                int i3 = Log.i("AppCompatDelegate", "setLocalNightMode() called with an unknown mode");
                return;
        }
    }

    /* access modifiers changed from: 0000 */
    public int mapNightMode(int i) {
        int mode = i;
        int i2 = mode;
        switch (mode) {
            case -100:
                return -1;
            case 0:
                ensureAutoNightModeManager();
                return this.mAutoNightModeManager.getApplyableNightMode();
            default:
                return mode;
        }
    }

    private int getNightMode() {
        return this.mLocalNightMode == -100 ? getDefaultNightMode() : this.mLocalNightMode;
    }

    public void onSaveInstanceState(Bundle bundle) {
        Bundle outState = bundle;
        Bundle bundle2 = outState;
        super.onSaveInstanceState(outState);
        if (this.mLocalNightMode != -100) {
            outState.putInt(KEY_LOCAL_NIGHT_MODE, this.mLocalNightMode);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mAutoNightModeManager != null) {
            this.mAutoNightModeManager.cleanup();
        }
    }

    private boolean updateForNightMode(int i) {
        int mode = i;
        int i2 = mode;
        Resources resources = this.mContext.getResources();
        Resources res = resources;
        Configuration configuration = resources.getConfiguration();
        Configuration conf = configuration;
        int currentNightMode = configuration.uiMode & 48;
        int newNightMode = mode != 2 ? 16 : 32;
        if (currentNightMode == newNightMode) {
            return false;
        }
        if (!shouldRecreateOnNightModeChange()) {
            Configuration config = new Configuration(conf);
            DisplayMetrics metrics = res.getDisplayMetrics();
            config.uiMode = newNightMode | (config.uiMode & -49);
            res.updateConfiguration(config, metrics);
            boolean flush = ResourcesFlusher.flush(res);
        } else {
            Activity activity = (Activity) this.mContext;
            Activity activity2 = activity;
            activity.recreate();
        }
        return true;
    }

    private void ensureAutoNightModeManager() {
        if (this.mAutoNightModeManager == null) {
            this.mAutoNightModeManager = new AutoNightModeManager(this, TwilightManager.getInstance(this.mContext));
        }
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public final AutoNightModeManager getAutoNightModeManager() {
        ensureAutoNightModeManager();
        return this.mAutoNightModeManager;
    }

    private boolean shouldRecreateOnNightModeChange() {
        if (!this.mApplyDayNightCalled || !(this.mContext instanceof Activity)) {
            return false;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        PackageManager packageManager2 = packageManager;
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo(new ComponentName(this.mContext, this.mContext.getClass()), 0);
            ActivityInfo activityInfo2 = activityInfo;
            return (activityInfo.configChanges & 512) == 0;
        } catch (NameNotFoundException e) {
            NameNotFoundException nameNotFoundException = e;
            int d = Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e);
            return true;
        }
    }
}
