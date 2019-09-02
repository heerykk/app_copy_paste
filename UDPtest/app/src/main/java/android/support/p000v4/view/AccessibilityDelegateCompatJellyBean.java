package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.view.AccessibilityDelegateCompatJellyBean */
class AccessibilityDelegateCompatJellyBean {

    /* renamed from: android.support.v4.view.AccessibilityDelegateCompatJellyBean$AccessibilityDelegateBridgeJellyBean */
    public interface AccessibilityDelegateBridgeJellyBean {
        boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        Object getAccessibilityNodeProvider(View view);

        void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        void onInitializeAccessibilityNodeInfo(View view, Object obj);

        void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

        boolean performAccessibilityAction(View view, int i, Bundle bundle);

        void sendAccessibilityEvent(View view, int i);

        void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent);
    }

    AccessibilityDelegateCompatJellyBean() {
    }

    public static Object newAccessibilityDelegateBridge(AccessibilityDelegateBridgeJellyBean accessibilityDelegateBridgeJellyBean) {
        AccessibilityDelegateBridgeJellyBean bridge = accessibilityDelegateBridgeJellyBean;
        AccessibilityDelegateBridgeJellyBean accessibilityDelegateBridgeJellyBean2 = bridge;
        final AccessibilityDelegateBridgeJellyBean accessibilityDelegateBridgeJellyBean3 = bridge;
        return new AccessibilityDelegate() {
            public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                View host = view;
                AccessibilityEvent event = accessibilityEvent;
                View view2 = host;
                AccessibilityEvent accessibilityEvent2 = event;
                return accessibilityDelegateBridgeJellyBean3.dispatchPopulateAccessibilityEvent(host, event);
            }

            public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                View host = view;
                AccessibilityEvent event = accessibilityEvent;
                View view2 = host;
                AccessibilityEvent accessibilityEvent2 = event;
                accessibilityDelegateBridgeJellyBean3.onInitializeAccessibilityEvent(host, event);
            }

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                View host = view;
                AccessibilityNodeInfo info = accessibilityNodeInfo;
                View view2 = host;
                AccessibilityNodeInfo accessibilityNodeInfo2 = info;
                accessibilityDelegateBridgeJellyBean3.onInitializeAccessibilityNodeInfo(host, info);
            }

            public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                View host = view;
                AccessibilityEvent event = accessibilityEvent;
                View view2 = host;
                AccessibilityEvent accessibilityEvent2 = event;
                accessibilityDelegateBridgeJellyBean3.onPopulateAccessibilityEvent(host, event);
            }

            public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                ViewGroup host = viewGroup;
                View child = view;
                AccessibilityEvent event = accessibilityEvent;
                ViewGroup viewGroup2 = host;
                View view2 = child;
                AccessibilityEvent accessibilityEvent2 = event;
                return accessibilityDelegateBridgeJellyBean3.onRequestSendAccessibilityEvent(host, child, event);
            }

            public void sendAccessibilityEvent(View view, int i) {
                View host = view;
                int eventType = i;
                View view2 = host;
                int i2 = eventType;
                accessibilityDelegateBridgeJellyBean3.sendAccessibilityEvent(host, eventType);
            }

            public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
                View host = view;
                AccessibilityEvent event = accessibilityEvent;
                View view2 = host;
                AccessibilityEvent accessibilityEvent2 = event;
                accessibilityDelegateBridgeJellyBean3.sendAccessibilityEventUnchecked(host, event);
            }

            public AccessibilityNodeProvider getAccessibilityNodeProvider(View view) {
                View host = view;
                View view2 = host;
                return (AccessibilityNodeProvider) accessibilityDelegateBridgeJellyBean3.getAccessibilityNodeProvider(host);
            }

            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                View host = view;
                int action = i;
                Bundle args = bundle;
                View view2 = host;
                int i2 = action;
                Bundle bundle2 = args;
                return accessibilityDelegateBridgeJellyBean3.performAccessibilityAction(host, action, args);
            }
        };
    }

    public static Object getAccessibilityNodeProvider(Object obj, View view) {
        Object delegate = obj;
        View host = view;
        Object obj2 = delegate;
        View view2 = host;
        return ((AccessibilityDelegate) delegate).getAccessibilityNodeProvider(host);
    }

    public static boolean performAccessibilityAction(Object obj, View view, int i, Bundle bundle) {
        Object delegate = obj;
        View host = view;
        int action = i;
        Bundle args = bundle;
        Object obj2 = delegate;
        View view2 = host;
        int i2 = action;
        Bundle bundle2 = args;
        return ((AccessibilityDelegate) delegate).performAccessibilityAction(host, action, args);
    }
}
