package android.support.p000v4.net;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.support.annotation.RequiresApi;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.net.ConnectivityManagerCompatApi24 */
class ConnectivityManagerCompatApi24 {
    ConnectivityManagerCompatApi24() {
    }

    public static int getRestrictBackgroundStatus(ConnectivityManager connectivityManager) {
        ConnectivityManager cm = connectivityManager;
        ConnectivityManager connectivityManager2 = cm;
        return cm.getRestrictBackgroundStatus();
    }
}
