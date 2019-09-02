package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.widget.DrawerLayout;
import android.support.p000v4.widget.DrawerLayout.DrawerListener;
import android.view.MenuItem;
import android.view.View;

@Deprecated
/* renamed from: android.support.v4.app.ActionBarDrawerToggle */
public class ActionBarDrawerToggle implements DrawerListener {
    private static final int ID_HOME = 16908332;
    private static final ActionBarDrawerToggleImpl IMPL;
    private static final float TOGGLE_DRAWABLE_OFFSET = 0.33333334f;
    final Activity mActivity;
    private final Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    private Drawable mDrawerImage;
    private final int mDrawerImageResource;
    private boolean mDrawerIndicatorEnabled;
    private final DrawerLayout mDrawerLayout;
    private boolean mHasCustomUpIndicator;
    private Drawable mHomeAsUpIndicator;
    private final int mOpenDrawerContentDescRes;
    private Object mSetIndicatorInfo;
    private SlideDrawable mSlider;

    /* renamed from: android.support.v4.app.ActionBarDrawerToggle$ActionBarDrawerToggleImpl */
    private interface ActionBarDrawerToggleImpl {
        Drawable getThemeUpIndicator(Activity activity);

        Object setActionBarDescription(Object obj, Activity activity, int i);

        Object setActionBarUpIndicator(Object obj, Activity activity, Drawable drawable, int i);
    }

    /* renamed from: android.support.v4.app.ActionBarDrawerToggle$ActionBarDrawerToggleImplBase */
    private static class ActionBarDrawerToggleImplBase implements ActionBarDrawerToggleImpl {
        ActionBarDrawerToggleImplBase() {
        }

        public Drawable getThemeUpIndicator(Activity activity) {
            Activity activity2 = activity;
            return null;
        }

        public Object setActionBarUpIndicator(Object obj, Activity activity, Drawable drawable, int i) {
            Object info = obj;
            Object obj2 = info;
            Activity activity2 = activity;
            Drawable drawable2 = drawable;
            int i2 = i;
            return info;
        }

        public Object setActionBarDescription(Object obj, Activity activity, int i) {
            Object info = obj;
            Object obj2 = info;
            Activity activity2 = activity;
            int i2 = i;
            return info;
        }
    }

    @RequiresApi(11)
    @TargetApi(11)
    /* renamed from: android.support.v4.app.ActionBarDrawerToggle$ActionBarDrawerToggleImplHC */
    private static class ActionBarDrawerToggleImplHC implements ActionBarDrawerToggleImpl {
        ActionBarDrawerToggleImplHC() {
        }

        public Drawable getThemeUpIndicator(Activity activity) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator(activity2);
        }

        public Object setActionBarUpIndicator(Object obj, Activity activity, Drawable drawable, int i) {
            Object info = obj;
            Activity activity2 = activity;
            Drawable themeImage = drawable;
            int contentDescRes = i;
            Object obj2 = info;
            Activity activity3 = activity2;
            Drawable drawable2 = themeImage;
            int i2 = contentDescRes;
            return ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator(info, activity2, themeImage, contentDescRes);
        }

        public Object setActionBarDescription(Object obj, Activity activity, int i) {
            Object info = obj;
            Activity activity2 = activity;
            int contentDescRes = i;
            Object obj2 = info;
            Activity activity3 = activity2;
            int i2 = contentDescRes;
            return ActionBarDrawerToggleHoneycomb.setActionBarDescription(info, activity2, contentDescRes);
        }
    }

    @RequiresApi(18)
    @TargetApi(18)
    /* renamed from: android.support.v4.app.ActionBarDrawerToggle$ActionBarDrawerToggleImplJellybeanMR2 */
    private static class ActionBarDrawerToggleImplJellybeanMR2 implements ActionBarDrawerToggleImpl {
        ActionBarDrawerToggleImplJellybeanMR2() {
        }

        public Drawable getThemeUpIndicator(Activity activity) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            return ActionBarDrawerToggleJellybeanMR2.getThemeUpIndicator(activity2);
        }

        public Object setActionBarUpIndicator(Object obj, Activity activity, Drawable drawable, int i) {
            Object info = obj;
            Activity activity2 = activity;
            Drawable themeImage = drawable;
            int contentDescRes = i;
            Object obj2 = info;
            Activity activity3 = activity2;
            Drawable drawable2 = themeImage;
            int i2 = contentDescRes;
            return ActionBarDrawerToggleJellybeanMR2.setActionBarUpIndicator(info, activity2, themeImage, contentDescRes);
        }

        public Object setActionBarDescription(Object obj, Activity activity, int i) {
            Object info = obj;
            Activity activity2 = activity;
            int contentDescRes = i;
            Object obj2 = info;
            Activity activity3 = activity2;
            int i2 = contentDescRes;
            return ActionBarDrawerToggleJellybeanMR2.setActionBarDescription(info, activity2, contentDescRes);
        }
    }

    /* renamed from: android.support.v4.app.ActionBarDrawerToggle$Delegate */
    public interface Delegate {
        @Nullable
        Drawable getThemeUpIndicator();

        void setActionBarDescription(@StringRes int i);

        void setActionBarUpIndicator(Drawable drawable, @StringRes int i);
    }

    /* renamed from: android.support.v4.app.ActionBarDrawerToggle$DelegateProvider */
    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate();
    }

    /* renamed from: android.support.v4.app.ActionBarDrawerToggle$SlideDrawable */
    private class SlideDrawable extends InsetDrawable implements Callback {
        private final boolean mHasMirroring;
        private float mOffset;
        private float mPosition;
        private final Rect mTmpRect;
        final /* synthetic */ ActionBarDrawerToggle this$0;

        SlideDrawable(ActionBarDrawerToggle actionBarDrawerToggle, Drawable drawable) {
            Drawable wrapped = drawable;
            Drawable drawable2 = wrapped;
            this.this$0 = actionBarDrawerToggle;
            super(wrapped, 0);
            this.mHasMirroring = VERSION.SDK_INT > 18;
            this.mTmpRect = new Rect();
        }

        public void setPosition(float f) {
            float position = f;
            float f2 = position;
            this.mPosition = position;
            invalidateSelf();
        }

        public float getPosition() {
            return this.mPosition;
        }

        public void setOffset(float f) {
            float offset = f;
            float f2 = offset;
            this.mOffset = offset;
            invalidateSelf();
        }

        public void draw(Canvas canvas) {
            Canvas canvas2 = canvas;
            Canvas canvas3 = canvas2;
            copyBounds(this.mTmpRect);
            int save = canvas2.save();
            boolean isLayoutRTL = ViewCompat.getLayoutDirection(this.this$0.mActivity.getWindow().getDecorView()) == 1;
            int flipRtl = !isLayoutRTL ? 1 : -1;
            int width = this.mTmpRect.width();
            int width2 = width;
            canvas2.translate((-this.mOffset) * ((float) width) * this.mPosition * ((float) flipRtl), 0.0f);
            if (isLayoutRTL && !this.mHasMirroring) {
                canvas2.translate((float) width2, 0.0f);
                canvas2.scale(-1.0f, 1.0f);
            }
            super.draw(canvas2);
            canvas2.restore();
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 18) {
            IMPL = new ActionBarDrawerToggleImplJellybeanMR2();
        } else if (version < 11) {
            IMPL = new ActionBarDrawerToggleImplBase();
        } else {
            IMPL = new ActionBarDrawerToggleImplHC();
        }
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, boolean z, @DrawableRes int i, @StringRes int i2, @StringRes int i3) {
        Activity activity2 = activity;
        DrawerLayout drawerLayout2 = drawerLayout;
        int drawerImageRes = i;
        int openDrawerContentDescRes = i2;
        int closeDrawerContentDescRes = i3;
        Activity activity3 = activity2;
        DrawerLayout drawerLayout3 = drawerLayout2;
        boolean animate = z;
        int i4 = drawerImageRes;
        int i5 = openDrawerContentDescRes;
        int i6 = closeDrawerContentDescRes;
        this.mDrawerIndicatorEnabled = true;
        this.mActivity = activity2;
        if (!(activity2 instanceof DelegateProvider)) {
            this.mActivityImpl = null;
        } else {
            this.mActivityImpl = ((DelegateProvider) activity2).getDrawerToggleDelegate();
        }
        this.mDrawerLayout = drawerLayout2;
        this.mDrawerImageResource = drawerImageRes;
        this.mOpenDrawerContentDescRes = openDrawerContentDescRes;
        this.mCloseDrawerContentDescRes = closeDrawerContentDescRes;
        this.mHomeAsUpIndicator = getThemeUpIndicator();
        this.mDrawerImage = ContextCompat.getDrawable(activity2, drawerImageRes);
        SlideDrawable slideDrawable = new SlideDrawable(this, this.mDrawerImage);
        this.mSlider = slideDrawable;
        this.mSlider.setOffset(!animate ? 0.0f : TOGGLE_DRAWABLE_OFFSET);
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, @DrawableRes int i, @StringRes int i2, @StringRes int i3) {
        Activity activity2 = activity;
        DrawerLayout drawerLayout2 = drawerLayout;
        int drawerImageRes = i;
        int openDrawerContentDescRes = i2;
        int closeDrawerContentDescRes = i3;
        Activity activity3 = activity2;
        DrawerLayout drawerLayout3 = drawerLayout2;
        int i4 = drawerImageRes;
        int i5 = openDrawerContentDescRes;
        int i6 = closeDrawerContentDescRes;
        this(activity2, drawerLayout2, !assumeMaterial(activity2), drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    private static boolean assumeMaterial(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.getApplicationInfo().targetSdkVersion >= 21 && VERSION.SDK_INT >= 21;
    }

    public void syncState() {
        if (!this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.mSlider.setPosition(0.0f);
        } else {
            this.mSlider.setPosition(1.0f);
        }
        if (this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mSlider, !this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START) ? this.mOpenDrawerContentDescRes : this.mCloseDrawerContentDescRes);
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
            indicator = ContextCompat.getDrawable(this.mActivity, resId);
        }
        setHomeAsUpIndicator(indicator);
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

    public boolean isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled;
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration configuration2 = configuration;
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
        }
        this.mDrawerImage = ContextCompat.getDrawable(this.mActivity, this.mDrawerImageResource);
        syncState();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        if (item == null || item.getItemId() != ID_HOME || !this.mDrawerIndicatorEnabled) {
            return false;
        }
        if (!this.mDrawerLayout.isDrawerVisible((int) GravityCompat.START)) {
            this.mDrawerLayout.openDrawer((int) GravityCompat.START);
        } else {
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
        }
        return true;
    }

    public void onDrawerSlide(View view, float f) {
        float glyphOffset;
        float slideOffset = f;
        View view2 = view;
        float f2 = slideOffset;
        float glyphOffset2 = this.mSlider.getPosition();
        if (slideOffset > 0.5f) {
            glyphOffset = Math.max(glyphOffset2, Math.max(0.0f, slideOffset - 0.5f) * 2.0f);
        } else {
            glyphOffset = Math.min(glyphOffset2, slideOffset * 2.0f);
        }
        this.mSlider.setPosition(glyphOffset);
    }

    public void onDrawerOpened(View view) {
        View view2 = view;
        this.mSlider.setPosition(1.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mCloseDrawerContentDescRes);
        }
    }

    public void onDrawerClosed(View view) {
        View view2 = view;
        this.mSlider.setPosition(0.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mOpenDrawerContentDescRes);
        }
    }

    public void onDrawerStateChanged(int i) {
        int i2 = i;
    }

    /* access modifiers changed from: 0000 */
    public Drawable getThemeUpIndicator() {
        if (this.mActivityImpl == null) {
            return IMPL.getThemeUpIndicator(this.mActivity);
        }
        return this.mActivityImpl.getThemeUpIndicator();
    }

    /* access modifiers changed from: 0000 */
    public void setActionBarUpIndicator(Drawable drawable, int i) {
        Drawable upDrawable = drawable;
        int contentDescRes = i;
        Drawable drawable2 = upDrawable;
        int i2 = contentDescRes;
        if (this.mActivityImpl == null) {
            this.mSetIndicatorInfo = IMPL.setActionBarUpIndicator(this.mSetIndicatorInfo, this.mActivity, upDrawable, contentDescRes);
        } else {
            this.mActivityImpl.setActionBarUpIndicator(upDrawable, contentDescRes);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setActionBarDescription(int i) {
        int contentDescRes = i;
        int i2 = contentDescRes;
        if (this.mActivityImpl == null) {
            this.mSetIndicatorInfo = IMPL.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, contentDescRes);
        } else {
            this.mActivityImpl.setActionBarDescription(contentDescRes);
        }
    }
}
