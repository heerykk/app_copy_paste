package android.support.p000v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@TargetApi(18)
@RequiresApi(18)
/* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompatJellyBeanMr2 */
class AccessibilityServiceInfoCompatJellyBeanMr2 {
    AccessibilityServiceInfoCompatJellyBeanMr2() {
    }

    public static int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return info.getCapabilities();
    }
}
