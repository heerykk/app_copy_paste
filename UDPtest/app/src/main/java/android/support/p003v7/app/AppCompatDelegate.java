package android.support.p003v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.p002os.BuildCompat;
import android.support.p003v7.app.ActionBarDrawerToggle.Delegate;
import android.support.p003v7.view.ActionMode;
import android.support.p003v7.view.ActionMode.Callback;
import android.support.p003v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: android.support.v7.app.AppCompatDelegate */
public abstract class AppCompatDelegate {
    public static final int FEATURE_ACTION_MODE_OVERLAY = 10;
    public static final int FEATURE_SUPPORT_ACTION_BAR = 108;
    public static final int FEATURE_SUPPORT_ACTION_BAR_OVERLAY = 109;
    public static final int MODE_NIGHT_AUTO = 0;
    public static final int MODE_NIGHT_FOLLOW_SYSTEM = -1;
    public static final int MODE_NIGHT_NO = 1;
    static final int MODE_NIGHT_UNSPECIFIED = -100;
    public static final int MODE_NIGHT_YES = 2;
    static final String TAG = "AppCompatDelegate";
    private static boolean sCompatVectorFromResourcesEnabled = false;
    private static int sDefaultNightMode = -1;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v7.app.AppCompatDelegate$NightMode */
    public @interface NightMode {
    }

    public abstract void addContentView(View view, LayoutParams layoutParams);

    public abstract boolean applyDayNight();

    public abstract View createView(@Nullable View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet);

    @Nullable
    public abstract View findViewById(@IdRes int i);

    @Nullable
    public abstract Delegate getDrawerToggleDelegate();

    public abstract MenuInflater getMenuInflater();

    @Nullable
    public abstract ActionBar getSupportActionBar();

    public abstract boolean hasWindowFeature(int i);

    public abstract void installViewFactory();

    public abstract void invalidateOptionsMenu();

    public abstract boolean isHandleNativeActionModesEnabled();

    public abstract void onConfigurationChanged(Configuration configuration);

    public abstract void onCreate(Bundle bundle);

    public abstract void onDestroy();

    public abstract void onPostCreate(Bundle bundle);

    public abstract void onPostResume();

    public abstract void onSaveInstanceState(Bundle bundle);

    public abstract void onStart();

    public abstract void onStop();

    public abstract boolean requestWindowFeature(int i);

    public abstract void setContentView(@LayoutRes int i);

    public abstract void setContentView(View view);

    public abstract void setContentView(View view, LayoutParams layoutParams);

    public abstract void setHandleNativeActionModesEnabled(boolean z);

    public abstract void setLocalNightMode(int i);

    public abstract void setSupportActionBar(@Nullable Toolbar toolbar);

    public abstract void setTitle(@Nullable CharSequence charSequence);

    @Nullable
    public abstract ActionMode startSupportActionMode(@NonNull Callback callback);

    public static AppCompatDelegate create(Activity activity, AppCompatCallback appCompatCallback) {
        Activity activity2 = activity;
        AppCompatCallback callback = appCompatCallback;
        Activity activity3 = activity2;
        AppCompatCallback appCompatCallback2 = callback;
        return create(activity2, activity2.getWindow(), callback);
    }

    public static AppCompatDelegate create(Dialog dialog, AppCompatCallback appCompatCallback) {
        Dialog dialog2 = dialog;
        AppCompatCallback callback = appCompatCallback;
        Dialog dialog3 = dialog2;
        AppCompatCallback appCompatCallback2 = callback;
        return create(dialog2.getContext(), dialog2.getWindow(), callback);
    }

    private static AppCompatDelegate create(Context context, Window window, AppCompatCallback appCompatCallback) {
        Context context2 = context;
        Window window2 = window;
        AppCompatCallback callback = appCompatCallback;
        Context context3 = context2;
        Window window3 = window2;
        AppCompatCallback appCompatCallback2 = callback;
        int sdk = VERSION.SDK_INT;
        if (BuildCompat.isAtLeastN()) {
            return new AppCompatDelegateImplN(context2, window2, callback);
        }
        if (sdk >= 23) {
            return new AppCompatDelegateImplV23(context2, window2, callback);
        }
        if (sdk >= 14) {
            return new AppCompatDelegateImplV14(context2, window2, callback);
        }
        if (sdk < 11) {
            return new AppCompatDelegateImplV9(context2, window2, callback);
        }
        return new AppCompatDelegateImplV11(context2, window2, callback);
    }

    AppCompatDelegate() {
    }

    public static void setDefaultNightMode(int i) {
        int mode = i;
        int i2 = mode;
        switch (mode) {
            case -1:
            case 0:
            case 1:
            case 2:
                sDefaultNightMode = mode;
                return;
            default:
                int d = Log.d(TAG, "setDefaultNightMode() called with an unknown mode");
                return;
        }
    }

    public static int getDefaultNightMode() {
        return sDefaultNightMode;
    }

    public static void setCompatVectorFromResourcesEnabled(boolean z) {
        sCompatVectorFromResourcesEnabled = z;
    }

    public static boolean isCompatVectorFromResourcesEnabled() {
        return sCompatVectorFromResourcesEnabled;
    }
}
