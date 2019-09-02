package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

@TargetApi(18)
@RequiresApi(18)
/* renamed from: android.support.v4.app.BundleCompatJellybeanMR2 */
class BundleCompatJellybeanMR2 {
    BundleCompatJellybeanMR2() {
    }

    public static IBinder getBinder(Bundle bundle, String str) {
        Bundle bundle2 = bundle;
        String key = str;
        Bundle bundle3 = bundle2;
        String str2 = key;
        return bundle2.getBinder(key);
    }

    public static void putBinder(Bundle bundle, String str, IBinder iBinder) {
        Bundle bundle2 = bundle;
        String key = str;
        IBinder binder = iBinder;
        Bundle bundle3 = bundle2;
        String str2 = key;
        IBinder iBinder2 = binder;
        bundle2.putBinder(key, binder);
    }
}
