package android.support.p000v4.net;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresApi;

@TargetApi(13)
@RequiresApi(13)
/* renamed from: android.support.v4.net.ConnectivityManagerCompatHoneycombMR2 */
class ConnectivityManagerCompatHoneycombMR2 {
    ConnectivityManagerCompatHoneycombMR2() {
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager connectivityManager) {
        ConnectivityManager cm = connectivityManager;
        ConnectivityManager connectivityManager2 = cm;
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        NetworkInfo info = activeNetworkInfo;
        if (activeNetworkInfo == null) {
            return true;
        }
        int type = info.getType();
        int i = type;
        switch (type) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            case 1:
            case 7:
            case 9:
                return false;
            default:
                return true;
        }
    }
}
