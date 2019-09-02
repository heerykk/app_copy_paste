package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.view.AccessibilityDelegateCompatIcs */
class AccessibilityDelegateCompatIcs {

    /* renamed from: android.support.v4.view.AccessibilityDelegateCompatIcs$AccessibilityDelegateBridge */
    public interface AccessibilityDelegateBridge {
        boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        void onInitializeAccessibilityNodeInfo(View view, Object obj);

        void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

        void sendAccessibilityEvent(View view, int i);

        void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent);
    }

    AccessibilityDelegateCompatIcs() {
    }

    public static Object newAccessibilityDelegateDefaultImpl() {
        return new AccessibilityDelegate();
    }

    public static Object newAccessibilityDelegateBridge(AccessibilityDelegateBridge accessibilityDelegateBridge) {
        AccessibilityDelegateBridge bridge = accessibilityDelegateBridge;
        AccessibilityDelegateBridge accessibilityDelegateBridge2 = bridge;
        final AccessibilityDelegateBridge accessibilityDelegateBridge3 = bridge;
        return new AccessibilityDelegate() {
            public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                View host = view;
                AccessibilityEvent event = accessibilityEvent;
                View view2 = host;
                AccessibilityEvent accessibilityEvent2 = event;
                return accessibilityDelegateBridge3.dispatchPopulateAccessibilityEvent(host, event);
            }

            public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                View host = view;
                AccessibilityEvent event = accessibilityEvent;
                View view2 = host;
                AccessibilityEvent accessibilityEvent2 = event;
                accessibilityDelegateBridge3.onInitializeAccessibilityEvent(host, event);
            }

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                View host = view;
                AccessibilityNodeInfo info = accessibilityNodeInfo;
                View view2 = host;
                AccessibilityNodeInfo accessibilityNodeInfo2 = info;
                accessibilityDelegateBridge3.onInitializeAccessibilityNodeInfo(host, info);
            }

            public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                View host = view;
                AccessibilityEvent event = accessibilityEvent;
                View view2 = host;
                AccessibilityEvent accessibilityEvent2 = event;
                accessibilityDelegateBridge3.onPopulateAccessibilityEvent(host, event);
            }

            public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                ViewGroup host = viewGroup;
                View child = view;
                AccessibilityEvent event = accessibilityEvent;
                ViewGroup viewGroup2 = host;
                View view2 = child;
                AccessibilityEvent accessibilityEvent2 = event;
                return accessibilityDelegateBridge3.onRequestSendAccessibilityEvent(host, child, event);
            }

            public void sendAccessibilityEvent(View view, int i) {
                View host = view;
                int eventType = i;
                View view2 = host;
                int i2 = eventType;
                accessibilityDelegateBridge3.sendAccessibilityEvent(host, eventType);
            }

            public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
                View host = view;
                AccessibilityEvent event = accessibilityEvent;
                View view2 = host;
                AccessibilityEvent accessibilityEvent2 = event;
                accessibilityDelegateBridge3.sendAccessibilityEventUnchecked(host, event);
            }
        };
    }

    public static boolean dispatchPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) {
        Object delegate = obj;
        View host = view;
        AccessibilityEvent event = accessibilityEvent;
        Object obj2 = delegate;
        View view2 = host;
        AccessibilityEvent accessibilityEvent2 = event;
        return ((AccessibilityDelegate) delegate).dispatchPopulateAccessibilityEvent(host, event);
    }

    public static void onInitializeAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) {
        Object delegate = obj;
        View host = view;
        AccessibilityEvent event = accessibilityEvent;
        Object obj2 = delegate;
        View view2 = host;
        AccessibilityEvent accessibilityEvent2 = event;
        ((AccessibilityDelegate) delegate).onInitializeAccessibilityEvent(host, event);
    }

    public static void onInitializeAccessibilityNodeInfo(Object obj, View view, Object obj2) {
        Object delegate = obj;
        View host = view;
        Object info = obj2;
        Object obj3 = delegate;
        View view2 = host;
        Object obj4 = info;
        ((AccessibilityDelegate) delegate).onInitializeAccessibilityNodeInfo(host, (AccessibilityNodeInfo) info);
    }

    public static void onPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) {
        Object delegate = obj;
        View host = view;
        AccessibilityEvent event = accessibilityEvent;
        Object obj2 = delegate;
        View view2 = host;
        AccessibilityEvent accessibilityEvent2 = event;
        ((AccessibilityDelegate) delegate).onPopulateAccessibilityEvent(host, event);
    }

    public static boolean onRequestSendAccessibilityEvent(Object obj, ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        Object delegate = obj;
        ViewGroup host = viewGroup;
        View child = view;
        AccessibilityEvent event = accessibilityEvent;
        Object obj2 = delegate;
        ViewGroup viewGroup2 = host;
        View view2 = child;
        AccessibilityEvent accessibilityEvent2 = event;
        return ((AccessibilityDelegate) delegate).onRequestSendAccessibilityEvent(host, child, event);
    }

    public static void sendAccessibilityEvent(Object obj, View view, int i) {
        Object delegate = obj;
        View host = view;
        int eventType = i;
        Object obj2 = delegate;
        View view2 = host;
        int i2 = eventType;
        ((AccessibilityDelegate) delegate).sendAccessibilityEvent(host, eventType);
    }

    public static void sendAccessibilityEventUnchecked(Object obj, View view, AccessibilityEvent accessibilityEvent) {
        Object delegate = obj;
        View host = view;
        AccessibilityEvent event = accessibilityEvent;
        Object obj2 = delegate;
        View view2 = host;
        AccessibilityEvent accessibilityEvent2 = event;
        ((AccessibilityDelegate) delegate).sendAccessibilityEventUnchecked(host, event);
    }
}
