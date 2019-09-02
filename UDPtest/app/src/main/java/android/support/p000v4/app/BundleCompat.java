package android.support.p000v4.app;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;

/* renamed from: android.support.v4.app.BundleCompat */
public final class BundleCompat {
    private BundleCompat() {
    }

    public static IBinder getBinder(Bundle bundle, String str) {
        Bundle bundle2 = bundle;
        String key = str;
        Bundle bundle3 = bundle2;
        String str2 = key;
        if (VERSION.SDK_INT < 18) {
            return BundleCompatGingerbread.getBinder(bundle2, key);
        }
        return BundleCompatJellybeanMR2.getBinder(bundle2, key);
    }

    public static void putBinder(Bundle bundle, String str, IBinder iBinder) {
        Bundle bundle2 = bundle;
        String key = str;
        IBinder binder = iBinder;
        Bundle bundle3 = bundle2;
        String str2 = key;
        IBinder iBinder2 = binder;
        if (VERSION.SDK_INT < 18) {
            BundleCompatGingerbread.putBinder(bundle2, key, binder);
        } else {
            BundleCompatJellybeanMR2.putBinder(bundle2, key, binder);
        }
    }
}
