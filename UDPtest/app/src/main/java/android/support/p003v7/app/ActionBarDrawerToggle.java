package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.widget.DrawerLayout;
import android.support.p000v4.widget.DrawerLayout.DrawerListener;
import android.support.p003v7.graphics.drawable.DrawerArrowDrawable;
import android.support.p003v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

/* renamed from: android.support.v7.app.ActionBarDrawerToggle */
public class ActionBarDrawerToggle implements DrawerListener {
    private final Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    boolean mDrawerIndicatorEnabled;
    private final DrawerLayout mDrawerLayout;
    private boolean mHasCustomUpIndicator;
    private Drawable mHomeAsUpIndicator;
    private final int mOpenDrawerContentDescRes;
    private DrawerArrowDrawable mSlider;
    OnClickListener mToolbarNavigationClickListener;
    private boolean mWarnedForDisplayHomeAsUp;

    /* renamed from: android.support.v7.app.ActionBarDrawerToggle$Delegate */
    public interface Delegate {
        Context getActionBarThemedContext();

        Drawable getThemeUpIndicator();

        boolean isNavigationVisible();

        void setActionBarDescription(@StringRes int i);

        void setActionBarUpIndicator(Drawable drawable, @StringRes int i);
    }

    /* renamed from: android.support.v7.app.ActionBarDrawerToggle$DelegateProvider */
    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate();
    }

    /* renamed from: android.support.v7.app.ActionBarDrawerToggle$DummyDelegate */
    static class DummyDelegate implements Delegate {
        final Activity mActivity;

        DummyDelegate(Activity activity) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            this.mActivity = activity2;
        }

        public void setActionBarUpIndicator(Drawable drawable, @StringRes int i) {
            Drawable drawable2 = drawable;
            int i2 = i;
        }

        public void setActionBarDescription(@StringRes int i) {
            int i2 = i;
        }

        public Drawable getThemeUpIndicator() {
            return null;
        }

        public Context getActionBarThemedContext() {
            return this.mActivity;
        }

        public boolean isNavigationVisible() {
            return true;
        }
    }

    @RequiresApi(11)
    @TargetApi(11)
    /* renamed from: android.support.v7.app.ActionBarDrawerToggle$HoneycombDelegate */
    private static class HoneycombDelegate implements Delegate {
        final Activity mActivity;
        SetIndicatorInfo mSetIndicatorInfo;

        HoneycombDelegate(Activity activity) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            this.mActivity = activity2;
        }

        public Drawable getThemeUpIndicator() {
            return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator(this.mActivity);
        }

        public Context getActionBarThemedContext() {
            return this.mActivity;
        }

        public boolean isNavigationVisible() {
            ActionBar actionBar = this.mActivity.getActionBar();
            return (actionBar == null || (actionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        public void setActionBarUpIndicator(Drawable drawable, int i) {
            Drawable themeImage = drawable;
            int contentDescRes = i;
            Drawable drawable2 = themeImage;
            int i2 = contentDescRes;
            ActionBar actionBar = this.mActivity.getActionBar();
            ActionBar actionBar2 = actionBar;
            if (actionBar != null) {
                actionBar2.setDisplayShowHomeEnabled(true);
                this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator(this.mSetIndicatorInfo, this.mActivity, themeImage, contentDescRes);
                actionBar2.setDisplayShowHomeEnabled(false);
            }
        }

        public void setActionBarDescription(int i) {
            int contentDescRes = i;
            int i2 = contentDescRes;
            this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, contentDescRes);
        }
    }

    @RequiresApi(14)
    @TargetApi(14)
    /* renamed from: android.support.v7.app.ActionBarDrawerToggle$IcsDelegate */
    private static class IcsDelegate extends HoneycombDelegate {
        IcsDelegate(Activity activity) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            super(activity2);
        }

        public Context getActionBarThemedContext() {
            Context context;
            ActionBar actionBar = this.mActivity.getActionBar();
            ActionBar actionBar2 = actionBar;
            if (actionBar == null) {
                context = this.mActivity;
            } else {
                context = actionBar2.getThemedContext();
            }
            return context;
        }
    }

    @RequiresApi(18)
    @TargetApi(18)
    /* renamed from: android.support.v7.app.ActionBarDrawerToggle$JellybeanMr2Delegate */
    private static class JellybeanMr2Delegate implements Delegate {
        final Activity mActivity;

        JellybeanMr2Delegate(Activity activity) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            this.mActivity = activity2;
        }

        public Drawable getThemeUpIndicator() {
            TypedArray obtainStyledAttributes = getActionBarThemedContext().obtainStyledAttributes(null, new int[]{16843531}, 16843470, 0);
            TypedArray a = obtainStyledAttributes;
            Drawable result = obtainStyledAttributes.getDrawable(0);
            a.recycle();
            return result;
        }

        public Context getActionBarThemedContext() {
            Context context;
            ActionBar actionBar = this.mActivity.getActionBar();
            ActionBar actionBar2 = actionBar;
            if (actionBar == null) {
                context = this.mActivity;
            } else {
                context = actionBar2.getThemedContext();
            }
            return context;
        }

        public boolean isNavigationVisible() {
            ActionBar actionBar = this.mActivity.getActionBar();
            return (actionBar == null || (actionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        public void setActionBarUpIndicator(Drawable drawable, int i) {
            Drawable drawable2 = drawable;
            int contentDescRes = i;
            Drawable drawable3 = drawable2;
            int i2 = contentDescRes;
            ActionBar actionBar = this.mActivity.getActionBar();
            ActionBar actionBar2 = actionBar;
            if (actionBar != null) {
                actionBar2.setHomeAsUpIndicator(drawable2);
                actionBar2.setHomeActionContentDescription(contentDescRes);
            }
        }

        public void setActionBarDescription(int i) {
            int contentDescRes = i;
            int i2 = contentDescRes;
            ActionBar actionBar = this.mActivity.getActionBar();
            ActionBar actionBar2 = actionBar;
            if (actionBar != null) {
                actionBar2.setHomeActionContentDescription(contentDescRes);
            }
        }
    }

    /* renamed from: android.support.v7.app.ActionBarDrawerToggle$ToolbarCompatDelegate */
    static class ToolbarCompatDelegate implements Delegate {
        final CharSequence mDefaultContentDescription;
        final Drawable mDefaultUpIndicator;
        final Toolbar mToolbar;

        ToolbarCompatDelegate(Toolbar toolbar) {
            Toolbar toolbar2 = toolbar;
            Toolbar toolbar3 = toolbar2;
            this.mToolbar = toolbar2;
            this.mDefaultUpIndicator = toolbar2.getNavigationIcon();
            this.mDefaultContentDescription = toolbar2.getNavigationContentDescription();
        }

        public void setActionBarUpIndicator(Drawable drawable, @StringRes int i) {
            Drawable upDrawable = drawable;
            int contentDescRes = i;
            Drawable drawable2 = upDrawable;
            int i2 = contentDescRes;
            this.mToolbar.setNavigationIcon(upDrawable);
            setActionBarDescription(contentDescRes);
        }

        public void setActionBarDescription(@StringRes int i) {
            int contentDescRes = i;
            int i2 = contentDescRes;
            if (contentDescRes != 0) {
                this.mToolbar.setNavigationContentDescription(contentDescRes);
            } else {
                this.mToolbar.setNavigationContentDescription(this.mDefaultContentDescription);
            }
        }

        public Drawable getThemeUpIndicator() {
            return this.mDefaultUpIndicator;
        }

        public Context getActionBarThemedContext() {
            return this.mToolbar.getContext();
        }

        public boolean isNavigationVisible() {
            return true;
        }
    }

    ActionBarDrawerToggle(Activity activity, Toolbar toolbar, DrawerLayout drawerLayout, DrawerArrowDrawable drawerArrowDrawable, @StringRes int i, @StringRes int i2) {
        Activity activity2 = activity;
        Toolbar toolbar2 = toolbar;
        DrawerLayout drawerLayout2 = drawerLayout;
        DrawerArrowDrawable slider = drawerArrowDrawable;
        int openDrawerContentDescRes = i;
        int closeDrawerContentDescRes = i2;
        Activity activity3 = activity2;
        Toolbar toolbar3 = toolbar2;
        DrawerLayout drawerLayout3 = drawerLayout2;
        DrawerArrowDrawable drawerArrowDrawable2 = slider;
        int i3 = openDrawerContentDescRes;
        int i4 = closeDrawerContentDescRes;
        this.mDrawerIndicatorEnabled = true;
        this.mWarnedForDisplayHomeAsUp = false;
        if (toolbar2 != null) {
            this.mActivityImpl = new ToolbarCompatDelegate(toolbar2);
            C02411 r0 = new OnClickListener(this) {
                final /* synthetic */ ActionBarDrawerToggle this$0;

                {
                    ActionBarDrawerToggle this$02 = r5;
                    ActionBarDrawerToggle actionBarDrawerToggle = this$02;
                    this.this$0 = this$02;
                }

                public void onClick(View view) {
                    View v = view;
                    View view2 = v;
                    if (this.this$0.mDrawerIndicatorEnabled) {
                        this.this$0.toggle();
                    } else if (this.this$0.mToolbarNavigationClickListener != null) {
                        this.this$0.mToolbarNavigationClickListener.onClick(v);
                    }
                }
            };
            toolbar2.setNavigationOnClickListener(r0);
        } else if (activity2 instanceof DelegateProvider) {
            this.mActivityImpl = ((DelegateProvider) activity2).getDrawerToggleDelegate();
        } else if (VERSION.SDK_INT >= 18) {
            JellybeanMr2Delegate jellybeanMr2Delegate = new JellybeanMr2Delegate(activity2);
            this.mActivityImpl = jellybeanMr2Delegate;
        } else if (VERSION.SDK_INT >= 14) {
            IcsDelegate icsDelegate = new IcsDelegate(activity2);
            this.mActivityImpl = icsDelegate;
        } else if (VERSION.SDK_INT < 11) {
            DummyDelegate dummyDelegate = new DummyDelegate(activity2);
            this.mActivityImpl = dummyDelegate;
        } else {
            HoneycombDelegate honeycombDelegate = new HoneycombDelegate(activity2);
            this.mActivityImpl = honeycombDelegate;
        }
        this.mDrawerLayout = drawerLayout2;
        this.mOpenDrawerContentDescRes = openDrawerContentDescRes;
        this.mCloseDrawerContentDescRes = closeDrawerContentDescRes;
        if (slider != null) {
            this.mSlider = slider;
        } else {
            DrawerArrowDrawable drawerArrowDrawable3 = new DrawerArrowDrawable(this.mActivityImpl.getActionBarThemedContext());
            this.mSlider = drawerArrowDrawable3;
        }
        this.mHomeAsUpIndicator = getThemeUpIndicator();
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, @StringRes int i, @StringRes int i2) {
        Activity activity2 = activity;
        DrawerLayout drawerLayout2 = drawerLayout;
        int openDrawerContentDescRes = i;
        int closeDrawerContentDescRes = i2;
        Activity activity3 = activity2;
        DrawerLayout drawerLayout3 = drawerLayout2;
        int i3 = openDrawerContentDescRes;
        int i4 = closeDrawerContentDescRes;
        this(activity2, null, drawerLayout2, null, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, @StringRes int i, @StringRes int i2) {
        Activity activity2 = activity;
        DrawerLayout drawerLayout2 = drawerLayout;
        Toolbar toolbar2 = toolbar;
        int openDrawerContentDescRes = i;
        int closeDrawerContentDescRes = i2;
        Activity activity3 = activity2;
        DrawerLayout drawerLayout3 = drawerLayout2;
        Toolbar toolbar3 = toolbar2;
        int i3 = openDrawerContentDescRes;
        int i4 = closeDrawerContentDescRes;
        this(activity2, toolbar2, drawerLayout2, null, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    public void syncState() {
        if (!this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            setPosition(0.0f);
        } else {
            setPosition(1.0f);
        }
        if (this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mSlider, !this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START) ? this.mOpenDrawerContentDescRes : this.mCloseDrawerContentDescRes);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration configuration2 = configuration;
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
        }
        syncState();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        if (item == null || item.getItemId() != 16908332 || !this.mDrawerIndicatorEnabled) {
            return false;
        }
        toggle();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void toggle() {
        int drawerLockMode = this.mDrawerLayout.getDrawerLockMode((int) GravityCompat.START);
        if (this.mDrawerLayout.isDrawerVisible((int) GravityCompat.START) && drawerLockMode != 2) {
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
        } else if (drawerLockMode != 1) {
            this.mDrawerLayout.openDrawer((int) GravityCompat.START);
        }
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        Drawable indicator = drawable;
        Drawable drawable2 = indicator;
        if (indicator != null) {
            this.mHomeAsUpIndicator = indicator;
            this.mHasCustomUpIndicator = true;
        } else {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
            this.mHasCustomUpIndicator = false;
        }
        if (!this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
        }
    }

    public void setHomeAsUpIndicator(int i) {
        int resId = i;
        int i2 = resId;
        Drawable indicator = null;
        if (resId != 0) {
            indicator = this.mDrawerLayout.getResources().getDrawable(resId);
        }
        setHomeAsUpIndicator(indicator);
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled;
    }

    public void setDrawerIndicatorEnabled(boolean z) {
        boolean enable = z;
        if (enable != this.mDrawerIndicatorEnabled) {
            if (!enable) {
                setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
            } else {
                setActionBarUpIndicator(this.mSlider, !this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START) ? this.mOpenDrawerContentDescRes : this.mCloseDrawerContentDescRes);
            }
            this.mDrawerIndicatorEnabled = enable;
        }
    }

    @NonNull
    public DrawerArrowDrawable getDrawerArrowDrawable() {
        return this.mSlider;
    }

    public void setDrawerArrowDrawable(@NonNull DrawerArrowDrawable drawerArrowDrawable) {
        DrawerArrowDrawable drawable = drawerArrowDrawable;
        DrawerArrowDrawable drawerArrowDrawable2 = drawable;
        this.mSlider = drawable;
        syncState();
    }

    public void onDrawerSlide(View view, float f) {
        float slideOffset = f;
        View view2 = view;
        float f2 = slideOffset;
        setPosition(Math.min(1.0f, Math.max(0.0f, slideOffset)));
    }

    public void onDrawerOpened(View view) {
        View view2 = view;
        setPosition(1.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mCloseDrawerContentDescRes);
        }
    }

    public void onDrawerClosed(View view) {
        View view2 = view;
        setPosition(0.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mOpenDrawerContentDescRes);
        }
    }

    public void onDrawerStateChanged(int i) {
        int i2 = i;
    }

    public OnClickListener getToolbarNavigationClickListener() {
        return this.mToolbarNavigationClickListener;
    }

    public void setToolbarNavigationClickListener(OnClickListener onClickListener) {
        OnClickListener onToolbarNavigationClickListener = onClickListener;
        OnClickListener onClickListener2 = onToolbarNavigationClickListener;
        this.mToolbarNavigationClickListener = onToolbarNavigationClickListener;
    }

    /* access modifiers changed from: 0000 */
    public void setActionBarUpIndicator(Drawable drawable, int i) {
        Drawable upDrawable = drawable;
        int contentDescRes = i;
        Drawable drawable2 = upDrawable;
        int i2 = contentDescRes;
        if (!this.mWarnedForDisplayHomeAsUp && !this.mActivityImpl.isNavigationVisible()) {
            int w = Log.w("ActionBarDrawerToggle", "DrawerToggle may not show up because NavigationIcon is not visible. You may need to call actionbar.setDisplayHomeAsUpEnabled(true);");
            this.mWarnedForDisplayHomeAsUp = true;
        }
        this.mActivityImpl.setActionBarUpIndicator(upDrawable, contentDescRes);
    }

    /* access modifiers changed from: 0000 */
    public void setActionBarDescription(int i) {
        int contentDescRes = i;
        int i2 = contentDescRes;
        this.mActivityImpl.setActionBarDescription(contentDescRes);
    }

    /* access modifiers changed from: 0000 */
    public Drawable getThemeUpIndicator() {
        return this.mActivityImpl.getThemeUpIndicator();
    }

    private void setPosition(float f) {
        float position = f;
        float f2 = position;
        if (position == 1.0f) {
            this.mSlider.setVerticalMirror(true);
        } else if (position == 0.0f) {
            this.mSlider.setVerticalMirror(false);
        }
        this.mSlider.setProgress(position);
    }
}
