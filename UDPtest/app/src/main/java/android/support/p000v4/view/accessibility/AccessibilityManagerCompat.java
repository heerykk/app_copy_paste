package android.support.p000v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build.VERSION;
import android.support.p000v4.view.accessibility.AccessibilityManagerCompatIcs.AccessibilityStateChangeListenerWrapper;
import android.support.p000v4.view.accessibility.AccessibilityManagerCompatKitKat.TouchExplorationStateChangeListenerWrapper;
import android.view.accessibility.AccessibilityManager;
import java.util.Collections;
import java.util.List;

/* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat */
public final class AccessibilityManagerCompat {
    private static final AccessibilityManagerVersionImpl IMPL;

    /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat$AccessibilityManagerIcsImpl */
    static class AccessibilityManagerIcsImpl extends AccessibilityManagerStubImpl {
        AccessibilityManagerIcsImpl() {
        }

        public AccessibilityStateChangeListenerWrapper newAccessibilityStateChangeListener(AccessibilityStateChangeListener accessibilityStateChangeListener) {
            AccessibilityStateChangeListener listener = accessibilityStateChangeListener;
            AccessibilityStateChangeListener accessibilityStateChangeListener2 = listener;
            final AccessibilityStateChangeListener accessibilityStateChangeListener3 = listener;
            return new AccessibilityStateChangeListenerWrapper(listener, new AccessibilityStateChangeListenerBridge(this) {
                final /* synthetic */ AccessibilityManagerIcsImpl this$0;

                {
                    AccessibilityManagerIcsImpl this$02 = r6;
                    AccessibilityStateChangeListener accessibilityStateChangeListener = r7;
                    AccessibilityManagerIcsImpl accessibilityManagerIcsImpl = this$02;
                    this.this$0 = this$02;
                }

                public void onAccessibilityStateChanged(boolean z) {
                    accessibilityStateChangeListener3.onAccessibilityStateChanged(z);
                }
            });
        }

        public boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener) {
            AccessibilityManager manager = accessibilityManager;
            AccessibilityStateChangeListener listener = accessibilityStateChangeListener;
            AccessibilityManager accessibilityManager2 = manager;
            AccessibilityStateChangeListener accessibilityStateChangeListener2 = listener;
            return AccessibilityManagerCompatIcs.addAccessibilityStateChangeListener(manager, newAccessibilityStateChangeListener(listener));
        }

        public boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener) {
            AccessibilityManager manager = accessibilityManager;
            AccessibilityStateChangeListener listener = accessibilityStateChangeListener;
            AccessibilityManager accessibilityManager2 = manager;
            AccessibilityStateChangeListener accessibilityStateChangeListener2 = listener;
            return AccessibilityManagerCompatIcs.removeAccessibilityStateChangeListener(manager, newAccessibilityStateChangeListener(listener));
        }

        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager accessibilityManager, int i) {
            AccessibilityManager manager = accessibilityManager;
            int feedbackTypeFlags = i;
            AccessibilityManager accessibilityManager2 = manager;
            int i2 = feedbackTypeFlags;
            return AccessibilityManagerCompatIcs.getEnabledAccessibilityServiceList(manager, feedbackTypeFlags);
        }

        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager accessibilityManager) {
            AccessibilityManager manager = accessibilityManager;
            AccessibilityManager accessibilityManager2 = manager;
            return AccessibilityManagerCompatIcs.getInstalledAccessibilityServiceList(manager);
        }

        public boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager) {
            AccessibilityManager manager = accessibilityManager;
            AccessibilityManager accessibilityManager2 = manager;
            return AccessibilityManagerCompatIcs.isTouchExplorationEnabled(manager);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat$AccessibilityManagerKitKatImpl */
    static class AccessibilityManagerKitKatImpl extends AccessibilityManagerIcsImpl {
        AccessibilityManagerKitKatImpl() {
        }

        public TouchExplorationStateChangeListenerWrapper newTouchExplorationStateChangeListener(TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
            TouchExplorationStateChangeListener listener = touchExplorationStateChangeListener;
            TouchExplorationStateChangeListener touchExplorationStateChangeListener2 = listener;
            final TouchExplorationStateChangeListener touchExplorationStateChangeListener3 = listener;
            return new TouchExplorationStateChangeListenerWrapper(listener, new TouchExplorationStateChangeListenerBridge(this) {
                final /* synthetic */ AccessibilityManagerKitKatImpl this$0;

                {
                    AccessibilityManagerKitKatImpl this$02 = r6;
                    TouchExplorationStateChangeListener touchExplorationStateChangeListener = r7;
                    AccessibilityManagerKitKatImpl accessibilityManagerKitKatImpl = this$02;
                    this.this$0 = this$02;
                }

                public void onTouchExplorationStateChanged(boolean z) {
                    touchExplorationStateChangeListener3.onTouchExplorationStateChanged(z);
                }
            });
        }

        public boolean addTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
            AccessibilityManager manager = accessibilityManager;
            TouchExplorationStateChangeListener listener = touchExplorationStateChangeListener;
            AccessibilityManager accessibilityManager2 = manager;
            TouchExplorationStateChangeListener touchExplorationStateChangeListener2 = listener;
            return AccessibilityManagerCompatKitKat.addTouchExplorationStateChangeListener(manager, newTouchExplorationStateChangeListener(listener));
        }

        public boolean removeTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
            AccessibilityManager manager = accessibilityManager;
            TouchExplorationStateChangeListener listener = touchExplorationStateChangeListener;
            AccessibilityManager accessibilityManager2 = manager;
            TouchExplorationStateChangeListener touchExplorationStateChangeListener2 = listener;
            return AccessibilityManagerCompatKitKat.removeTouchExplorationStateChangeListener(manager, newTouchExplorationStateChangeListener(listener));
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat$AccessibilityManagerStubImpl */
    static class AccessibilityManagerStubImpl implements AccessibilityManagerVersionImpl {
        AccessibilityManagerStubImpl() {
        }

        public AccessibilityStateChangeListenerWrapper newAccessibilityStateChangeListener(AccessibilityStateChangeListener accessibilityStateChangeListener) {
            AccessibilityStateChangeListener accessibilityStateChangeListener2 = accessibilityStateChangeListener;
            return null;
        }

        public boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener) {
            AccessibilityManager accessibilityManager2 = accessibilityManager;
            AccessibilityStateChangeListener accessibilityStateChangeListener2 = accessibilityStateChangeListener;
            return false;
        }

        public boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener) {
            AccessibilityManager accessibilityManager2 = accessibilityManager;
            AccessibilityStateChangeListener accessibilityStateChangeListener2 = accessibilityStateChangeListener;
            return false;
        }

        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager accessibilityManager, int i) {
            AccessibilityManager accessibilityManager2 = accessibilityManager;
            int i2 = i;
            return Collections.emptyList();
        }

        public List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager accessibilityManager) {
            AccessibilityManager accessibilityManager2 = accessibilityManager;
            return Collections.emptyList();
        }

        public boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager) {
            AccessibilityManager accessibilityManager2 = accessibilityManager;
            return false;
        }

        public TouchExplorationStateChangeListenerWrapper newTouchExplorationStateChangeListener(TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
            TouchExplorationStateChangeListener touchExplorationStateChangeListener2 = touchExplorationStateChangeListener;
            return null;
        }

        public boolean addTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
            AccessibilityManager accessibilityManager2 = accessibilityManager;
            TouchExplorationStateChangeListener touchExplorationStateChangeListener2 = touchExplorationStateChangeListener;
            return false;
        }

        public boolean removeTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
            AccessibilityManager accessibilityManager2 = accessibilityManager;
            TouchExplorationStateChangeListener touchExplorationStateChangeListener2 = touchExplorationStateChangeListener;
            return false;
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat$AccessibilityManagerVersionImpl */
    interface AccessibilityManagerVersionImpl {
        boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener);

        boolean addTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener);

        List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager accessibilityManager, int i);

        List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager accessibilityManager);

        boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager);

        AccessibilityStateChangeListenerWrapper newAccessibilityStateChangeListener(AccessibilityStateChangeListener accessibilityStateChangeListener);

        TouchExplorationStateChangeListenerWrapper newTouchExplorationStateChangeListener(TouchExplorationStateChangeListener touchExplorationStateChangeListener);

        boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener);

        boolean removeTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener);
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat$AccessibilityStateChangeListener */
    public interface AccessibilityStateChangeListener {
        void onAccessibilityStateChanged(boolean z);
    }

    @Deprecated
    /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat$AccessibilityStateChangeListenerCompat */
    public static abstract class AccessibilityStateChangeListenerCompat implements AccessibilityStateChangeListener {
        public AccessibilityStateChangeListenerCompat() {
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListener */
    public interface TouchExplorationStateChangeListener {
        void onTouchExplorationStateChanged(boolean z);
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityManagerKitKatImpl();
        } else if (VERSION.SDK_INT < 14) {
            IMPL = new AccessibilityManagerStubImpl();
        } else {
            IMPL = new AccessibilityManagerIcsImpl();
        }
    }

    public static boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener) {
        AccessibilityManager manager = accessibilityManager;
        AccessibilityStateChangeListener listener = accessibilityStateChangeListener;
        AccessibilityManager accessibilityManager2 = manager;
        AccessibilityStateChangeListener accessibilityStateChangeListener2 = listener;
        return IMPL.addAccessibilityStateChangeListener(manager, listener);
    }

    public static boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListener accessibilityStateChangeListener) {
        AccessibilityManager manager = accessibilityManager;
        AccessibilityStateChangeListener listener = accessibilityStateChangeListener;
        AccessibilityManager accessibilityManager2 = manager;
        AccessibilityStateChangeListener accessibilityStateChangeListener2 = listener;
        return IMPL.removeAccessibilityStateChangeListener(manager, listener);
    }

    public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager accessibilityManager) {
        AccessibilityManager manager = accessibilityManager;
        AccessibilityManager accessibilityManager2 = manager;
        return IMPL.getInstalledAccessibilityServiceList(manager);
    }

    public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager accessibilityManager, int i) {
        AccessibilityManager manager = accessibilityManager;
        int feedbackTypeFlags = i;
        AccessibilityManager accessibilityManager2 = manager;
        int i2 = feedbackTypeFlags;
        return IMPL.getEnabledAccessibilityServiceList(manager, feedbackTypeFlags);
    }

    public static boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager) {
        AccessibilityManager manager = accessibilityManager;
        AccessibilityManager accessibilityManager2 = manager;
        return IMPL.isTouchExplorationEnabled(manager);
    }

    public static boolean addTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        AccessibilityManager manager = accessibilityManager;
        TouchExplorationStateChangeListener listener = touchExplorationStateChangeListener;
        AccessibilityManager accessibilityManager2 = manager;
        TouchExplorationStateChangeListener touchExplorationStateChangeListener2 = listener;
        return IMPL.addTouchExplorationStateChangeListener(manager, listener);
    }

    public static boolean removeTouchExplorationStateChangeListener(AccessibilityManager accessibilityManager, TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        AccessibilityManager manager = accessibilityManager;
        TouchExplorationStateChangeListener listener = touchExplorationStateChangeListener;
        AccessibilityManager accessibilityManager2 = manager;
        TouchExplorationStateChangeListener touchExplorationStateChangeListener2 = listener;
        return IMPL.removeTouchExplorationStateChangeListener(manager, listener);
    }

    private AccessibilityManagerCompat() {
    }
}
