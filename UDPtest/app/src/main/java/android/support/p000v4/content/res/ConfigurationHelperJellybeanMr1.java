package android.support.p000v4.content.res;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

@TargetApi(17)
@RequiresApi(17)
/* renamed from: android.support.v4.content.res.ConfigurationHelperJellybeanMr1 */
class ConfigurationHelperJellybeanMr1 {
    ConfigurationHelperJellybeanMr1() {
    }

    static int getDensityDpi(@NonNull Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        return resources2.getConfiguration().densityDpi;
    }
}
