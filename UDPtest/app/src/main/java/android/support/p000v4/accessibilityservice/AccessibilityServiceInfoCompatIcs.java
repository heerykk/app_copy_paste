package android.support.p000v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.pm.ResolveInfo;
import android.support.annotation.RequiresApi;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompatIcs */
class AccessibilityServiceInfoCompatIcs {
    AccessibilityServiceInfoCompatIcs() {
    }

    public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return info.getCanRetrieveWindowContent();
    }

    public static String getDescription(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return info.getDescription();
    }

    public static String getId(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return info.getId();
    }

    public static ResolveInfo getResolveInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return info.getResolveInfo();
    }

    public static String getSettingsActivityName(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return info.getSettingsActivityName();
    }
}
