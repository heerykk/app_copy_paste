package android.support.p000v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompatKitKat */
class AccessibilityManagerCompatKitKat {

    /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompatKitKat$TouchExplorationStateChangeListenerBridge */
    interface TouchExplorationStateChangeListenerBridge {
        void onTouchExplorationStateChanged(boolean z);
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompatKitKat$TouchExplorationStateChangeListenerWrapper */
    public static class TouchExplorationStateChangeListenerWrapper implements TouchExplorationStateChangeListener {
        final Object mListener;
        final TouchExplorationStateChangeListenerBridge mListenerBridge;

        public TouchExplorationStateChangeListenerWrapper(Object obj, TouchExplorationStateChangeListenerBridge touchExplorationStateChangeListenerBridge) {
            Object listener = obj;
            TouchExplorationStateChangeListenerBridge listenerBridge = touchExplorationStateChangeListenerBridge;
            Object obj2 = listener;
            TouchExplorationStateChangeListenerBridge touchExplorationStateChangeListenerBridge2 = listenerBridge;
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
            TouchExplorationStateChangeListenerWrapper other = (TouchExplorationStateChangeListenerWrapper) o;
            boolean z = this.mListener != null ? this.mListener.equals(other.mListener) : other.mListener == null;
            return z;
        }

        public void onTouchExplorationStateChanged(boolean z) {
            this.mListenerBridge.onTouchExplorationStateChanged(z);
        }
    }

    AccessibilityManagerCompatKitKat() {
    }

    public static Object newTouchExplorationStateChangeListener(TouchExplorationStateChangeListenerBridge touchExplorationStateChangeListenerBridge) {
        TouchExplorationStateChangeListenerBridge bridge = touchExplorationStateChangeListenerBridge;
        TouchExplorationStateChangeListenerBridge touchExplorationStateChangeListenerBridge2 = bridge;
        final TouchExplorationStateChangeListenerBridge touchExplorationStateChangeListenerBridge3 = bridge;
        return new TouchExplorationStateChangeListener() {
            public void onTouchExplorationStateChanged(boolean z) {
                touchExplorationStateChangeListenerBridge3.onTouchExplorationStateChanged(z);
            }
        };
    }

    public static boolean addTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, Object obj) {
        AccessibilityManager manager = accessibilityManager;
        Object listener = obj;
        AccessibilityManager accessibilityManager2 = manager;
        Object obj2 = listener;
        return manager.addTouchExplorationStateChangeListener((TouchExplorationStateChangeListener) listener);
    }

    public static boolean removeTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, Object obj) {
        AccessibilityManager manager = accessibilityManager;
        Object listener = obj;
        AccessibilityManager accessibilityManager2 = manager;
        Object obj2 = listener;
        return manager.removeTouchExplorationStateChangeListener((TouchExplorationStateChangeListener) listener);
    }
}
