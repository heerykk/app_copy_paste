package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.support.annotation.RequiresApi;

@TargetApi(22)
@RequiresApi(22)
/* renamed from: android.support.v4.app.ActivityCompatApi22 */
class ActivityCompatApi22 {
    ActivityCompatApi22() {
    }

    public static Uri getReferrer(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        return activity2.getReferrer();
    }
}
