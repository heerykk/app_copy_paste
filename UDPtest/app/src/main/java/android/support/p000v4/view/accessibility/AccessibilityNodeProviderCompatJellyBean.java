package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompatJellyBean */
class AccessibilityNodeProviderCompatJellyBean {

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompatJellyBean$AccessibilityNodeInfoBridge */
    interface AccessibilityNodeInfoBridge {
        Object createAccessibilityNodeInfo(int i);

        List<Object> findAccessibilityNodeInfosByText(String str, int i);

        boolean performAction(int i, int i2, Bundle bundle);
    }

    AccessibilityNodeProviderCompatJellyBean() {
    }

    public static Object newAccessibilityNodeProviderBridge(AccessibilityNodeInfoBridge accessibilityNodeInfoBridge) {
        AccessibilityNodeInfoBridge bridge = accessibilityNodeInfoBridge;
        AccessibilityNodeInfoBridge accessibilityNodeInfoBridge2 = bridge;
        final AccessibilityNodeInfoBridge accessibilityNodeInfoBridge3 = bridge;
        return new AccessibilityNodeProvider() {
            public AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
                int virtualViewId = i;
                int i2 = virtualViewId;
                return (AccessibilityNodeInfo) accessibilityNodeInfoBridge3.createAccessibilityNodeInfo(virtualViewId);
            }

            public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String str, int i) {
                String text = str;
                int virtualViewId = i;
                String str2 = text;
                int i2 = virtualViewId;
                return accessibilityNodeInfoBridge3.findAccessibilityNodeInfosByText(text, virtualViewId);
            }

            public boolean performAction(int i, int i2, Bundle bundle) {
                int virtualViewId = i;
                int action = i2;
                Bundle arguments = bundle;
                int i3 = virtualViewId;
                int i4 = action;
                Bundle bundle2 = arguments;
                return accessibilityNodeInfoBridge3.performAction(virtualViewId, action, arguments);
            }
        };
    }
}
