package android.support.p003v7.app;

import android.content.res.Resources;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LongSparseArray;
import java.lang.reflect.Field;
import java.util.Map;

/* renamed from: android.support.v7.app.ResourcesFlusher */
class ResourcesFlusher {
    private static final String TAG = "ResourcesFlusher";
    private static Field sDrawableCacheField;
    private static boolean sDrawableCacheFieldFetched;
    private static Field sResourcesImplField;
    private static boolean sResourcesImplFieldFetched;
    private static Class sThemedResourceCacheClazz;
    private static boolean sThemedResourceCacheClazzFetched;
    private static Field sThemedResourceCache_mUnthemedEntriesField;
    private static boolean sThemedResourceCache_mUnthemedEntriesFieldFetched;

    ResourcesFlusher() {
    }

    static boolean flush(@NonNull Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        int i = VERSION.SDK_INT;
        int sdk = i;
        if (i >= 24) {
            return flushNougats(resources2);
        }
        if (sdk >= 23) {
            return flushMarshmallows(resources2);
        }
        if (sdk < 21) {
            return false;
        }
        return flushLollipops(resources2);
    }

    private static boolean flushLollipops(@NonNull Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = Resources.class.getDeclaredField("mDrawableCache");
                sDrawableCacheField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                NoSuchFieldException noSuchFieldException = e;
                int e2 = Log.e(TAG, "Could not retrieve Resources#mDrawableCache field", e);
            }
            sDrawableCacheFieldFetched = true;
        }
        if (sDrawableCacheField != null) {
            Map drawableCache = null;
            try {
                drawableCache = (Map) sDrawableCacheField.get(resources2);
            } catch (IllegalAccessException e3) {
                IllegalAccessException illegalAccessException = e3;
                int e4 = Log.e(TAG, "Could not retrieve value from Resources#mDrawableCache", e3);
            }
            if (drawableCache != null) {
                drawableCache.clear();
                return true;
            }
        }
        return false;
    }

    private static boolean flushMarshmallows(@NonNull Resources resources) {
        boolean z;
        Resources resources2 = resources;
        Resources resources3 = resources2;
        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = Resources.class.getDeclaredField("mDrawableCache");
                sDrawableCacheField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                NoSuchFieldException noSuchFieldException = e;
                int e2 = Log.e(TAG, "Could not retrieve Resources#mDrawableCache field", e);
            }
            sDrawableCacheFieldFetched = true;
        }
        Object drawableCache = null;
        if (sDrawableCacheField != null) {
            try {
                drawableCache = sDrawableCacheField.get(resources2);
            } catch (IllegalAccessException e3) {
                IllegalAccessException illegalAccessException = e3;
                int e4 = Log.e(TAG, "Could not retrieve value from Resources#mDrawableCache", e3);
            }
        }
        if (drawableCache == null) {
            return false;
        }
        if (drawableCache != null && flushThemedResourcesCache(drawableCache)) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private static boolean flushNougats(@NonNull Resources resources) {
        boolean z;
        Resources resources2 = resources;
        Resources resources3 = resources2;
        if (!sResourcesImplFieldFetched) {
            try {
                sResourcesImplField = Resources.class.getDeclaredField("mResourcesImpl");
                sResourcesImplField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                NoSuchFieldException noSuchFieldException = e;
                int e2 = Log.e(TAG, "Could not retrieve Resources#mResourcesImpl field", e);
            }
            sResourcesImplFieldFetched = true;
        }
        if (sResourcesImplField == null) {
            return false;
        }
        Object resourcesImpl = null;
        try {
            resourcesImpl = sResourcesImplField.get(resources2);
        } catch (IllegalAccessException e3) {
            IllegalAccessException illegalAccessException = e3;
            int e4 = Log.e(TAG, "Could not retrieve value from Resources#mResourcesImpl", e3);
        }
        if (resourcesImpl == null) {
            return false;
        }
        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = resourcesImpl.getClass().getDeclaredField("mDrawableCache");
                sDrawableCacheField.setAccessible(true);
            } catch (NoSuchFieldException e5) {
                NoSuchFieldException noSuchFieldException2 = e5;
                int e6 = Log.e(TAG, "Could not retrieve ResourcesImpl#mDrawableCache field", e5);
            }
            sDrawableCacheFieldFetched = true;
        }
        Object drawableCache = null;
        if (sDrawableCacheField != null) {
            try {
                drawableCache = sDrawableCacheField.get(resourcesImpl);
            } catch (IllegalAccessException e7) {
                IllegalAccessException illegalAccessException2 = e7;
                int e8 = Log.e(TAG, "Could not retrieve value from ResourcesImpl#mDrawableCache", e7);
            }
        }
        if (drawableCache != null && flushThemedResourcesCache(drawableCache)) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private static boolean flushThemedResourcesCache(@NonNull Object obj) {
        Object cache = obj;
        Object obj2 = cache;
        if (!sThemedResourceCacheClazzFetched) {
            try {
                sThemedResourceCacheClazz = Class.forName("android.content.res.ThemedResourceCache");
            } catch (ClassNotFoundException e) {
                ClassNotFoundException classNotFoundException = e;
                int e2 = Log.e(TAG, "Could not find ThemedResourceCache class", e);
            }
            sThemedResourceCacheClazzFetched = true;
        }
        if (sThemedResourceCacheClazz == null) {
            return false;
        }
        if (!sThemedResourceCache_mUnthemedEntriesFieldFetched) {
            try {
                sThemedResourceCache_mUnthemedEntriesField = sThemedResourceCacheClazz.getDeclaredField("mUnthemedEntries");
                sThemedResourceCache_mUnthemedEntriesField.setAccessible(true);
            } catch (NoSuchFieldException e3) {
                NoSuchFieldException noSuchFieldException = e3;
                int e4 = Log.e(TAG, "Could not retrieve ThemedResourceCache#mUnthemedEntries field", e3);
            }
            sThemedResourceCache_mUnthemedEntriesFieldFetched = true;
        }
        if (sThemedResourceCache_mUnthemedEntriesField == null) {
            return false;
        }
        LongSparseArray unthemedEntries = null;
        try {
            unthemedEntries = (LongSparseArray) sThemedResourceCache_mUnthemedEntriesField.get(cache);
        } catch (IllegalAccessException e5) {
            IllegalAccessException illegalAccessException = e5;
            int e6 = Log.e(TAG, "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", e5);
        }
        if (unthemedEntries == null) {
            return false;
        }
        unthemedEntries.clear();
        return true;
    }
}
