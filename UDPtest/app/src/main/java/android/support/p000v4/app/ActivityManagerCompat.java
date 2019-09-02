package android.support.p000v4.app;

import android.app.ActivityManager;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.app.ActivityManagerCompat */
public final class ActivityManagerCompat {
    private ActivityManagerCompat() {
    }

    public static boolean isLowRamDevice(@NonNull ActivityManager activityManager) {
        ActivityManager am = activityManager;
        ActivityManager activityManager2 = am;
        if (VERSION.SDK_INT < 19) {
            return false;
        }
        return ActivityManagerCompatKitKat.isLowRamDevice(am);
    }
}
