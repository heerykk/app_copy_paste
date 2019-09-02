package android.support.p003v7.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources.Theme;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.p003v7.appcompat.C0268R;
import android.view.LayoutInflater;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.ContextThemeWrapper */
public class ContextThemeWrapper extends ContextWrapper {
    private LayoutInflater mInflater;
    private Theme mTheme;
    private int mThemeResource;

    public ContextThemeWrapper(Context context, @StyleRes int i) {
        Context base = context;
        int themeResId = i;
        Context context2 = base;
        int i2 = themeResId;
        super(base);
        this.mThemeResource = themeResId;
    }

    public ContextThemeWrapper(Context context, Theme theme) {
        Context base = context;
        Theme theme2 = theme;
        Context context2 = base;
        Theme theme3 = theme2;
        super(base);
        this.mTheme = theme2;
    }

    public void setTheme(int i) {
        int resid = i;
        int i2 = resid;
        if (this.mThemeResource != resid) {
            this.mThemeResource = resid;
            initializeTheme();
        }
    }

    public int getThemeResId() {
        return this.mThemeResource;
    }

    public Theme getTheme() {
        if (this.mTheme != null) {
            return this.mTheme;
        }
        if (this.mThemeResource == 0) {
            this.mThemeResource = C0268R.style.Theme_AppCompat_Light;
        }
        initializeTheme();
        return this.mTheme;
    }

    public Object getSystemService(String str) {
        String name = str;
        String str2 = name;
        if (!"layout_inflater".equals(name)) {
            return getBaseContext().getSystemService(name);
        }
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.mInflater;
    }

    /* access modifiers changed from: protected */
    public void onApplyThemeResource(Theme theme, int i, boolean z) {
        Theme theme2 = theme;
        int resid = i;
        Theme theme3 = theme2;
        int i2 = resid;
        boolean z2 = z;
        theme2.applyStyle(resid, true);
    }

    private void initializeTheme() {
        boolean first = this.mTheme == null;
        if (first) {
            this.mTheme = getResources().newTheme();
            Theme theme = getBaseContext().getTheme();
            Theme theme2 = theme;
            if (theme != null) {
                this.mTheme.setTo(theme2);
            }
        }
        onApplyThemeResource(this.mTheme, this.mThemeResource, first);
    }

    public AssetManager getAssets() {
        return getResources().getAssets();
    }
}
