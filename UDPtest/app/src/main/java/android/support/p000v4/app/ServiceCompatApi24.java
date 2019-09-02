package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Service;
import android.support.annotation.RequiresApi;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.app.ServiceCompatApi24 */
class ServiceCompatApi24 {
    ServiceCompatApi24() {
    }

    public static void stopForeground(Service service, int i) {
        Service service2 = service;
        int flags = i;
        Service service3 = service2;
        int i2 = flags;
        service2.stopForeground(flags);
    }
}
