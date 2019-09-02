package android.support.p003v7.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.content.ContextCompat;
import android.support.p003v7.widget.AppCompatDrawableManager;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import java.util.WeakHashMap;

/* renamed from: android.support.v7.content.res.AppCompatResources */
public final class AppCompatResources {
    private static final String LOG_TAG = "AppCompatResources";
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();
    private static final Object sColorStateCacheLock = new Object();
    private static final WeakHashMap<Context, SparseArray<ColorStateListCacheEntry>> sColorStateCaches = new WeakHashMap<>(0);

    /* renamed from: android.support.v7.content.res.AppCompatResources$ColorStateListCacheEntry */
    private static class ColorStateListCacheEntry {
        final Configuration configuration;
        final ColorStateList value;

        ColorStateListCacheEntry(@NonNull ColorStateList colorStateList, @NonNull Configuration configuration2) {
            ColorStateList value2 = colorStateList;
            Configuration configuration3 = configuration2;
            ColorStateList colorStateList2 = value2;
            Configuration configuration4 = configuration3;
            this.value = value2;
            this.configuration = configuration3;
        }
    }

    private AppCompatResources() {
    }

    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        if (VERSION.SDK_INT >= 23) {
            return context2.getColorStateList(resId);
        }
        ColorStateList cachedColorStateList = getCachedColorStateList(context2, resId);
        ColorStateList csl = cachedColorStateList;
        if (cachedColorStateList != null) {
            return csl;
        }
        ColorStateList inflateColorStateList = inflateColorStateList(context2, resId);
        ColorStateList csl2 = inflateColorStateList;
        if (inflateColorStateList == null) {
            return ContextCompat.getColorStateList(context2, resId);
        }
        addColorStateListToCache(context2, resId, csl2);
        return csl2;
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        return AppCompatDrawableManager.get().getDrawable(context2, resId);
    }

    @Nullable
    private static ColorStateList inflateColorStateList(Context context, int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        if (isColorInt(context2, resId)) {
            return null;
        }
        Resources resources = context2.getResources();
        try {
            return AppCompatColorStateListInflater.createFromXml(resources, resources.getXml(resId), context2.getTheme());
        } catch (Exception e) {
            Exception exc = e;
            int e2 = Log.e(LOG_TAG, "Failed to inflate ColorStateList, leaving it to the framework", e);
            return null;
        }
    }

    @Nullable
    private static ColorStateList getCachedColorStateList(@NonNull Context context, @ColorRes int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        Object obj = sColorStateCacheLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                SparseArray sparseArray = (SparseArray) sColorStateCaches.get(context2);
                SparseArray sparseArray2 = sparseArray;
                if (sparseArray != null) {
                    if (sparseArray2.size() > 0) {
                        ColorStateListCacheEntry colorStateListCacheEntry = (ColorStateListCacheEntry) sparseArray2.get(resId);
                        ColorStateListCacheEntry entry = colorStateListCacheEntry;
                        if (colorStateListCacheEntry != null) {
                            if (!entry.configuration.equals(context2.getResources().getConfiguration())) {
                                sparseArray2.remove(resId);
                            } else {
                                ColorStateList colorStateList = entry.value;
                                return colorStateList;
                            }
                        }
                    }
                }
                return null;
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    private static void addColorStateListToCache(@NonNull Context context, @ColorRes int i, @NonNull ColorStateList colorStateList) {
        Context context2 = context;
        int resId = i;
        ColorStateList value = colorStateList;
        Context context3 = context2;
        int i2 = resId;
        ColorStateList colorStateList2 = value;
        Object obj = sColorStateCacheLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                SparseArray sparseArray = (SparseArray) sColorStateCaches.get(context2);
                SparseArray sparseArray2 = sparseArray;
                if (sparseArray == null) {
                    sparseArray2 = new SparseArray();
                    Object put = sColorStateCaches.put(context2, sparseArray2);
                }
                sparseArray2.append(resId, new ColorStateListCacheEntry(value, context2.getResources().getConfiguration()));
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    private static boolean isColorInt(@NonNull Context context, @ColorRes int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        Resources r = context2.getResources();
        TypedValue value = getTypedValue();
        r.getValue(resId, value, true);
        return value.type >= 28 && value.type <= 31;
    }

    @NonNull
    private static TypedValue getTypedValue() {
        TypedValue typedValue = (TypedValue) TL_TYPED_VALUE.get();
        TypedValue tv = typedValue;
        if (typedValue == null) {
            tv = new TypedValue();
            TL_TYPED_VALUE.set(tv);
        }
        return tv;
    }
}
