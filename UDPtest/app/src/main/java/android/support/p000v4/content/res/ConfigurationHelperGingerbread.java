package android.support.p000v4.content.res;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.content.res.ConfigurationHelperGingerbread */
class ConfigurationHelperGingerbread {
    ConfigurationHelperGingerbread() {
    }

    static int getScreenHeightDp(@NonNull Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        DisplayMetrics displayMetrics = resources2.getDisplayMetrics();
        return (int) (((float) displayMetrics.heightPixels) / displayMetrics.density);
    }

    static int getScreenWidthDp(@NonNull Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        DisplayMetrics displayMetrics = resources2.getDisplayMetrics();
        return (int) (((float) displayMetrics.widthPixels) / displayMetrics.density);
    }

    static int getSmallestScreenWidthDp(@NonNull Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        return Math.min(getScreenWidthDp(resources2), getScreenHeightDp(resources2));
    }

    static int getDensityDpi(@NonNull Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        return resources2.getDisplayMetrics().densityDpi;
    }
}
