package android.support.p000v4.content.res;

import android.content.res.Resources;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.content.res.ConfigurationHelper */
public final class ConfigurationHelper {
    private static final ConfigurationHelperImpl IMPL;

    /* renamed from: android.support.v4.content.res.ConfigurationHelper$ConfigurationHelperImpl */
    private interface ConfigurationHelperImpl {
        int getDensityDpi(@NonNull Resources resources);

        int getScreenHeightDp(@NonNull Resources resources);

        int getScreenWidthDp(@NonNull Resources resources);

        int getSmallestScreenWidthDp(@NonNull Resources resources);
    }

    /* renamed from: android.support.v4.content.res.ConfigurationHelper$GingerbreadImpl */
    private static class GingerbreadImpl implements ConfigurationHelperImpl {
        GingerbreadImpl() {
        }

        public int getScreenHeightDp(@NonNull Resources resources) {
            Resources resources2 = resources;
            Resources resources3 = resources2;
            return ConfigurationHelperGingerbread.getScreenHeightDp(resources2);
        }

        public int getScreenWidthDp(@NonNull Resources resources) {
            Resources resources2 = resources;
            Resources resources3 = resources2;
            return ConfigurationHelperGingerbread.getScreenWidthDp(resources2);
        }

        public int getSmallestScreenWidthDp(@NonNull Resources resources) {
            Resources resources2 = resources;
            Resources resources3 = resources2;
            return ConfigurationHelperGingerbread.getSmallestScreenWidthDp(resources2);
        }

        public int getDensityDpi(@NonNull Resources resources) {
            Resources resources2 = resources;
            Resources resources3 = resources2;
            return ConfigurationHelperGingerbread.getDensityDpi(resources2);
        }
    }

    /* renamed from: android.support.v4.content.res.ConfigurationHelper$HoneycombMr2Impl */
    private static class HoneycombMr2Impl extends GingerbreadImpl {
        HoneycombMr2Impl() {
        }

        public int getScreenHeightDp(@NonNull Resources resources) {
            Resources resources2 = resources;
            Resources resources3 = resources2;
            return ConfigurationHelperHoneycombMr2.getScreenHeightDp(resources2);
        }

        public int getScreenWidthDp(@NonNull Resources resources) {
            Resources resources2 = resources;
            Resources resources3 = resources2;
            return ConfigurationHelperHoneycombMr2.getScreenWidthDp(resources2);
        }

        public int getSmallestScreenWidthDp(@NonNull Resources resources) {
            Resources resources2 = resources;
            Resources resources3 = resources2;
            return ConfigurationHelperHoneycombMr2.getSmallestScreenWidthDp(resources2);
        }
    }

    /* renamed from: android.support.v4.content.res.ConfigurationHelper$JellybeanMr1Impl */
    private static class JellybeanMr1Impl extends HoneycombMr2Impl {
        JellybeanMr1Impl() {
        }

        public int getDensityDpi(@NonNull Resources resources) {
            Resources resources2 = resources;
            Resources resources3 = resources2;
            return ConfigurationHelperJellybeanMr1.getDensityDpi(resources2);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int sdk = i;
        if (i >= 17) {
            IMPL = new JellybeanMr1Impl();
        } else if (sdk < 13) {
            IMPL = new GingerbreadImpl();
        } else {
            IMPL = new HoneycombMr2Impl();
        }
    }

    private ConfigurationHelper() {
    }

    public static int getScreenHeightDp(@NonNull Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        return IMPL.getScreenHeightDp(resources2);
    }

    public static int getScreenWidthDp(@NonNull Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        return IMPL.getScreenWidthDp(resources2);
    }

    public static int getSmallestScreenWidthDp(@NonNull Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        return IMPL.getSmallestScreenWidthDp(resources2);
    }

    public static int getDensityDpi(@NonNull Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        return IMPL.getDensityDpi(resources2);
    }
}
