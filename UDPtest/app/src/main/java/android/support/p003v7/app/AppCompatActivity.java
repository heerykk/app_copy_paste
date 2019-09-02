package android.support.p003v7.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.p000v4.app.ActivityCompat;
import android.support.p000v4.app.FragmentActivity;
import android.support.p000v4.app.NavUtils;
import android.support.p000v4.app.TaskStackBuilder;
import android.support.p000v4.app.TaskStackBuilder.SupportParentable;
import android.support.p000v4.view.KeyEventCompat;
import android.support.p003v7.app.ActionBarDrawerToggle.Delegate;
import android.support.p003v7.app.ActionBarDrawerToggle.DelegateProvider;
import android.support.p003v7.view.ActionMode;
import android.support.p003v7.view.ActionMode.Callback;
import android.support.p003v7.widget.Toolbar;
import android.support.p003v7.widget.VectorEnabledTintResources;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/* renamed from: android.support.v7.app.AppCompatActivity */
public class AppCompatActivity extends FragmentActivity implements AppCompatCallback, SupportParentable, DelegateProvider {
    private AppCompatDelegate mDelegate;
    private boolean mEatKeyUpEvent;
    private Resources mResources;
    private int mThemeId = 0;

    public AppCompatActivity() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        AppCompatDelegate delegate = getDelegate();
        AppCompatDelegate delegate2 = delegate;
        delegate.installViewFactory();
        delegate2.onCreate(savedInstanceState);
        if (delegate2.applyDayNight() && this.mThemeId != 0) {
            if (VERSION.SDK_INT < 23) {
                setTheme(this.mThemeId);
            } else {
                onApplyThemeResource(getTheme(), this.mThemeId, false);
            }
        }
        super.onCreate(savedInstanceState);
    }

    public void setTheme(@StyleRes int i) {
        int resid = i;
        int i2 = resid;
        super.setTheme(resid);
        this.mThemeId = resid;
    }

    /* access modifiers changed from: protected */
    public void onPostCreate(@Nullable Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    @Nullable
    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        Toolbar toolbar2 = toolbar;
        Toolbar toolbar3 = toolbar2;
        getDelegate().setSupportActionBar(toolbar2);
    }

    public MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    public void setContentView(@LayoutRes int i) {
        int layoutResID = i;
        int i2 = layoutResID;
        getDelegate().setContentView(layoutResID);
    }

    public void setContentView(View view) {
        View view2 = view;
        View view3 = view2;
        getDelegate().setContentView(view2);
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        View view2 = view;
        LayoutParams params = layoutParams;
        View view3 = view2;
        LayoutParams layoutParams2 = params;
        getDelegate().setContentView(view2, params);
    }

    public void addContentView(View view, LayoutParams layoutParams) {
        View view2 = view;
        LayoutParams params = layoutParams;
        View view3 = view2;
        LayoutParams layoutParams2 = params;
        getDelegate().addContentView(view2, params);
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        Configuration configuration2 = newConfig;
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
        if (this.mResources != null) {
            this.mResources.updateConfiguration(newConfig, super.getResources().getDisplayMetrics());
        }
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        getDelegate().onStart();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    public View findViewById(@IdRes int i) {
        int id = i;
        int i2 = id;
        return getDelegate().findViewById(id);
    }

    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        int featureId = i;
        MenuItem item = menuItem;
        int i2 = featureId;
        MenuItem menuItem2 = item;
        if (super.onMenuItemSelected(featureId, item)) {
            return true;
        }
        ActionBar ab = getSupportActionBar();
        if (item.getItemId() != 16908332 || ab == null || (ab.getDisplayOptions() & 4) == 0) {
            return false;
        }
        return onSupportNavigateUp();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onTitleChanged(CharSequence charSequence, int i) {
        CharSequence title = charSequence;
        int color = i;
        CharSequence charSequence2 = title;
        int i2 = color;
        super.onTitleChanged(title, color);
        getDelegate().setTitle(title);
    }

    public boolean supportRequestWindowFeature(int i) {
        int featureId = i;
        int i2 = featureId;
        return getDelegate().requestWindowFeature(featureId);
    }

    public void supportInvalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    @CallSuper
    public void onSupportActionModeStarted(@NonNull ActionMode actionMode) {
        ActionMode actionMode2 = actionMode;
    }

    @CallSuper
    public void onSupportActionModeFinished(@NonNull ActionMode actionMode) {
        ActionMode actionMode2 = actionMode;
    }

    @Nullable
    public ActionMode onWindowStartingSupportActionMode(@NonNull Callback callback) {
        Callback callback2 = callback;
        return null;
    }

    @Nullable
    public ActionMode startSupportActionMode(@NonNull Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return getDelegate().startSupportActionMode(callback2);
    }

    @Deprecated
    public void setSupportProgressBarVisibility(boolean z) {
        boolean z2 = z;
    }

    @Deprecated
    public void setSupportProgressBarIndeterminateVisibility(boolean z) {
        boolean z2 = z;
    }

    @Deprecated
    public void setSupportProgressBarIndeterminate(boolean z) {
        boolean z2 = z;
    }

    @Deprecated
    public void setSupportProgress(int i) {
        int i2 = i;
    }

    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder taskStackBuilder) {
        TaskStackBuilder builder = taskStackBuilder;
        TaskStackBuilder taskStackBuilder2 = builder;
        TaskStackBuilder addParentStack = builder.addParentStack((Activity) this);
    }

    public void onPrepareSupportNavigateUpTaskStack(@NonNull TaskStackBuilder taskStackBuilder) {
        TaskStackBuilder taskStackBuilder2 = taskStackBuilder;
    }

    public boolean onSupportNavigateUp() {
        Intent supportParentActivityIntent = getSupportParentActivityIntent();
        Intent upIntent = supportParentActivityIntent;
        if (supportParentActivityIntent == null) {
            return false;
        }
        if (!supportShouldUpRecreateTask(upIntent)) {
            supportNavigateUpTo(upIntent);
        } else {
            TaskStackBuilder b = TaskStackBuilder.create(this);
            onCreateSupportNavigateUpTaskStack(b);
            onPrepareSupportNavigateUpTaskStack(b);
            b.startActivities();
            try {
                ActivityCompat.finishAffinity(this);
            } catch (IllegalStateException e) {
                IllegalStateException illegalStateException = e;
                finish();
            }
        }
        return true;
    }

    @Nullable
    public Intent getSupportParentActivityIntent() {
        return NavUtils.getParentActivityIntent(this);
    }

    public boolean supportShouldUpRecreateTask(@NonNull Intent intent) {
        Intent targetIntent = intent;
        Intent intent2 = targetIntent;
        return NavUtils.shouldUpRecreateTask(this, targetIntent);
    }

    public void supportNavigateUpTo(@NonNull Intent intent) {
        Intent upIntent = intent;
        Intent intent2 = upIntent;
        NavUtils.navigateUpTo(this, upIntent);
    }

    public void onContentChanged() {
        onSupportContentChanged();
    }

    @Deprecated
    public void onSupportContentChanged() {
    }

    @Nullable
    public Delegate getDrawerToggleDelegate() {
        return getDelegate().getDrawerToggleDelegate();
    }

    public boolean onMenuOpened(int i, Menu menu) {
        int featureId = i;
        Menu menu2 = menu;
        int i2 = featureId;
        Menu menu3 = menu2;
        return super.onMenuOpened(featureId, menu2);
    }

    public void onPanelClosed(int i, Menu menu) {
        int featureId = i;
        Menu menu2 = menu;
        int i2 = featureId;
        Menu menu3 = menu2;
        super.onPanelClosed(featureId, menu2);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        Bundle outState = bundle;
        Bundle bundle2 = outState;
        super.onSaveInstanceState(outState);
        getDelegate().onSaveInstanceState(outState);
    }

    @NonNull
    public AppCompatDelegate getDelegate() {
        if (this.mDelegate == null) {
            this.mDelegate = AppCompatDelegate.create((Activity) this, (AppCompatCallback) this);
        }
        return this.mDelegate;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        if (KeyEventCompat.isCtrlPressed(event) && event.getUnicodeChar(event.getMetaState() & -28673) == 60) {
            int action = event.getAction();
            int action2 = action;
            if (action == 0) {
                ActionBar supportActionBar = getSupportActionBar();
                ActionBar actionBar = supportActionBar;
                if (supportActionBar != null && actionBar.isShowing() && actionBar.requestFocus()) {
                    this.mEatKeyUpEvent = true;
                    return true;
                }
            } else if (action2 == 1 && this.mEatKeyUpEvent) {
                this.mEatKeyUpEvent = false;
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public Resources getResources() {
        if (this.mResources == null && VectorEnabledTintResources.shouldBeUsed()) {
            this.mResources = new VectorEnabledTintResources(this, super.getResources());
        }
        return this.mResources != null ? this.mResources : super.getResources();
    }
}
