package android.support.p000v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener;
import java.util.List;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompatIcs */
class AccessibilityManagerCompatIcs {

    /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompatIcs$AccessibilityStateChangeListenerBridge */
    interface AccessibilityStateChangeListenerBridge {
        void onAccessibilityStateChanged(boolean z);
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompatIcs$AccessibilityStateChangeListenerWrapper */
    public static class AccessibilityStateChangeListenerWrapper implements AccessibilityStateChangeListener {
        Object mListener;
        AccessibilityStateChangeListenerBridge mListenerBridge;

        public AccessibilityStateChangeListenerWrapper(Object obj, AccessibilityStateChangeListenerBridge accessibilityStateChangeListenerBridge) {
            Object listener = obj;
            AccessibilityStateChangeListenerBridge listenerBridge = accessibilityStateChangeListenerBridge;
            Object obj2 = listener;
            AccessibilityStateChangeListenerBridge accessibilityStateChangeListenerBridge2 = listenerBridge;
            this.mListener = listener;
            this.mListenerBridge = listenerBridge;
        }

        public int hashCode() {
            return this.mListener != null ? this.mListener.hashCode() : 0;
        }

        public boolean equals(Object obj) {
            Object o = obj;
            Object obj2 = o;
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            AccessibilityStateChangeListenerWrapper other = (AccessibilityStateChangeListenerWrapper) o;
            boolean z = this.mListener != null ? this.mListener.equals(other.mListener) : other.mListener == null;
            return z;
        }

        public void onAccessibilityStateChanged(boolean z) {
            this.mListenerBridge.onAccessibilityStateChanged(z);
        }
    }

    AccessibilityManagerCompatIcs() {
    }

    public static boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerWrapper accessibilityStateChangeListenerWrapper) {
        AccessibilityManager manager = accessibilityManager;
        AccessibilityStateChangeListenerWrapper listener = accessibilityStateChangeListenerWrapper;
        AccessibilityManager accessibilityManager2 = manager;
        AccessibilityStateChangeListenerWrapper accessibilityStateChangeListenerWrapper2 = listener;
        return manager.addAccessibilityStateChangeListener(listener);
    }

    public static boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerWrapper accessibilityStateChangeListenerWrapper) {
        AccessibilityManager manager = accessibilityManager;
        AccessibilityStateChangeListenerWrapper listener = accessibilityStateChangeListenerWrapper;
        AccessibilityManager accessibilityManager2 = manager;
        AccessibilityStateChangeListenerWrapper accessibilityStateChangeListenerWrapper2 = listener;
        return manager.removeAccessibilityStateChangeListener(listener);
    }

    public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager accessibilityManager, int i) {
        AccessibilityManager manager = accessibilityManager;
        int feedbackTypeFlags = i;
        AccessibilityManager accessibilityManager2 = manager;
        int i2 = feedbackTypeFlags;
        return manager.getEnabledAccessibilityServiceList(feedbackTypeFlags);
    }

    public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager accessibilityManager) {
        AccessibilityManager manager = accessibilityManager;
        AccessibilityManager accessibilityManager2 = manager;
        return manager.getInstalledAccessibilityServiceList();
    }

    public static boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager) {
        AccessibilityManager manager = accessibilityManager;
        AccessibilityManager accessibilityManager2 = manager;
        return manager.isTouchExplorationEnabled();
    }
}
