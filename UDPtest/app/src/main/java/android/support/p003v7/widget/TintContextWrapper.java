package android.support.p003v7.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.TintContextWrapper */
public class TintContextWrapper extends ContextWrapper {
    private static final Object CACHE_LOCK = new Object();
    private static ArrayList<WeakReference<TintContextWrapper>> sCache;
    private final Resources mResources;
    private final Theme mTheme;

    public static Context wrap(@NonNull Context context) {
        int i;
        int i2;
        Context context2 = context;
        Context context3 = context2;
        if (!shouldWrap(context2)) {
            return context2;
        }
        Object obj = CACHE_LOCK;
        int i3 = obj;
        synchronized (obj) {
            try {
                if (sCache != null) {
                    i3 = sCache.size() - 1;
                    while (i2 >= 0) {
                        WeakReference weakReference = (WeakReference) sCache.get(i2);
                        WeakReference weakReference2 = weakReference;
                        if (weakReference == null || weakReference2.get() == null) {
                            Object remove = sCache.remove(i2);
                        }
                        i2--;
                    }
                    for (int i4 = sCache.size() - 1; i4 >= 0; i4--) {
                        WeakReference weakReference3 = (WeakReference) sCache.get(i4);
                        TintContextWrapper wrapper = weakReference3 == null ? null : (TintContextWrapper) weakReference3.get();
                        if (wrapper != null) {
                            if (wrapper.getBaseContext() == context2) {
                                return wrapper;
                            }
                        }
                    }
                } else {
                    sCache = new ArrayList<>();
                }
                TintContextWrapper wrapper2 = new TintContextWrapper(context2);
                boolean add = sCache.add(new WeakReference(wrapper2));
                return wrapper2;
            } finally {
                i = i3;
            }
        }
    }

    private static boolean shouldWrap(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        if ((context2 instanceof TintContextWrapper) || (context2.getResources() instanceof TintResources) || (context2.getResources() instanceof VectorEnabledTintResources)) {
            return false;
        }
        return VERSION.SDK_INT < 21 || VectorEnabledTintResources.shouldBeUsed();
    }

    private TintContextWrapper(@NonNull Context context) {
        Context base = context;
        Context context2 = base;
        super(base);
        if (!VectorEnabledTintResources.shouldBeUsed()) {
            this.mResources = new TintResources(this, base.getResources());
            this.mTheme = null;
            return;
        }
        this.mResources = new VectorEnabledTintResources(this, base.getResources());
        this.mTheme = this.mResources.newTheme();
        this.mTheme.setTo(base.getTheme());
    }

    public Theme getTheme() {
        return this.mTheme != null ? this.mTheme : super.getTheme();
    }

    public void setTheme(int i) {
        int resid = i;
        int i2 = resid;
        if (this.mTheme != null) {
            this.mTheme.applyStyle(resid, true);
        } else {
            super.setTheme(resid);
        }
    }

    public Resources getResources() {
        return this.mResources;
    }

    public AssetManager getAssets() {
        return this.mResources.getAssets();
    }
}
