package android.support.p000v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.RequiresApi;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompatJellyBean */
class AccessibilityServiceInfoCompatJellyBean {
    AccessibilityServiceInfoCompatJellyBean() {
    }

    public static String loadDescription(AccessibilityServiceInfo accessibilityServiceInfo, PackageManager packageManager) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        PackageManager pm = packageManager;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        PackageManager packageManager2 = pm;
        return info.loadDescription(pm);
    }
}
