package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.p000v4.view.AccessibilityDelegateCompatIcs.AccessibilityDelegateBridge;
import android.support.p000v4.view.AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

/* renamed from: android.support.v4.view.AccessibilityDelegateCompat */
public class AccessibilityDelegateCompat {
    private static final Object DEFAULT_DELEGATE = IMPL.newAccessiblityDelegateDefaultImpl();
    private static final AccessibilityDelegateImpl IMPL;
    final Object mBridge = IMPL.newAccessiblityDelegateBridge(this);

    /* renamed from: android.support.v4.view.AccessibilityDelegateCompat$AccessibilityDelegateIcsImpl */
    static class AccessibilityDelegateIcsImpl extends AccessibilityDelegateStubImpl {
        AccessibilityDelegateIcsImpl() {
        }

        public Object newAccessiblityDelegateDefaultImpl() {
            return AccessibilityDelegateCompatIcs.newAccessibilityDelegateDefaultImpl();
        }

        public Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat accessibilityDelegateCompat) {
            AccessibilityDelegateCompat compat = accessibilityDelegateCompat;
            AccessibilityDelegateCompat accessibilityDelegateCompat2 = compat;
            final AccessibilityDelegateCompat accessibilityDelegateCompat3 = compat;
            return AccessibilityDelegateCompatIcs.newAccessibilityDelegateBridge(new AccessibilityDelegateBridge(this) {
                final /* synthetic */ AccessibilityDelegateIcsImpl this$0;

                {
                    AccessibilityDelegateIcsImpl this$02 = r6;
                    AccessibilityDelegateCompat accessibilityDelegateCompat = r7;
                    AccessibilityDelegateIcsImpl accessibilityDelegateIcsImpl = this$02;
                    this.this$0 = this$02;
                }

                public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                    View host = view;
                    AccessibilityEvent event = accessibilityEvent;
                    View view2 = host;
                    AccessibilityEvent accessibilityEvent2 = event;
                    return accessibilityDelegateCompat3.dispatchPopulateAccessibilityEvent(host, event);
                }

                public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                    View host = view;
                    AccessibilityEvent event = accessibilityEvent;
                    View view2 = host;
                    AccessibilityEvent accessibilityEvent2 = event;
                    accessibilityDelegateCompat3.onInitializeAccessibilityEvent(host, event);
                }

                public void onInitializeAccessibilityNodeInfo(View view, Object obj) {
                    View host = view;
                    Object info = obj;
                    View view2 = host;
                    Object obj2 = info;
                    accessibilityDelegateCompat3.onInitializeAccessibilityNodeInfo(host, new AccessibilityNodeInfoCompat(info));
                }

                public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                    View host = view;
                    AccessibilityEvent event = accessibilityEvent;
                    View view2 = host;
                    AccessibilityEvent accessibilityEvent2 = event;
                    accessibilityDelegateCompat3.onPopulateAccessibilityEvent(host, event);
                }

                public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                    ViewGroup host = viewGroup;
                    View child = view;
                    AccessibilityEvent event = accessibilityEvent;
                    ViewGroup viewGroup2 = host;
                    View view2 = child;
                    AccessibilityEvent accessibilityEvent2 = event;
                    return accessibilityDelegateCompat3.onRequestSendAccessibilityEvent(host, child, event);
                }

                public void sendAccessibilityEvent(View view, int i) {
                    View host = view;
                    int eventType = i;
                    View view2 = host;
                    int i2 = eventType;
                    accessibilityDelegateCompat3.sendAccessibilityEvent(host, eventType);
                }

                public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
                    View host = view;
                    AccessibilityEvent event = accessibilityEvent;
                    View view2 = host;
                    AccessibilityEvent accessibilityEvent2 = event;
                    accessibilityDelegateCompat3.sendAccessibilityEventUnchecked(host, event);
                }
            });
        }

        public boolean dispatchPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            Object delegate = obj;
            View host = view;
            AccessibilityEvent event = accessibilityEvent;
            Object obj2 = delegate;
            View view2 = host;
            AccessibilityEvent accessibilityEvent2 = event;
            return AccessibilityDelegateCompatIcs.dispatchPopulateAccessibilityEvent(delegate, host, event);
        }

        public void onInitializeAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            Object delegate = obj;
            View host = view;
            AccessibilityEvent event = accessibilityEvent;
            Object obj2 = delegate;
            View view2 = host;
            AccessibilityEvent accessibilityEvent2 = event;
            AccessibilityDelegateCompatIcs.onInitializeAccessibilityEvent(delegate, host, event);
        }

        public void onInitializeAccessibilityNodeInfo(Object obj, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            Object delegate = obj;
            View host = view;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            Object obj2 = delegate;
            View view2 = host;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            AccessibilityDelegateCompatIcs.onInitializeAccessibilityNodeInfo(delegate, host, info.getInfo());
        }

        public void onPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            Object delegate = obj;
            View host = view;
            AccessibilityEvent event = accessibilityEvent;
            Object obj2 = delegate;
            View view2 = host;
            AccessibilityEvent accessibilityEvent2 = event;
            AccessibilityDelegateCompatIcs.onPopulateAccessibilityEvent(delegate, host, event);
        }

        public boolean onRequestSendAccessibilityEvent(Object obj, ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            Object delegate = obj;
            ViewGroup host = viewGroup;
            View child = view;
            AccessibilityEvent event = accessibilityEvent;
            Object obj2 = delegate;
            ViewGroup viewGroup2 = host;
            View view2 = child;
            AccessibilityEvent accessibilityEvent2 = event;
            return AccessibilityDelegateCompatIcs.onRequestSendAccessibilityEvent(delegate, host, child, event);
        }

        public void sendAccessibilityEvent(Object obj, View view, int i) {
            Object delegate = obj;
            View host = view;
            int eventType = i;
            Object obj2 = delegate;
            View view2 = host;
            int i2 = eventType;
            AccessibilityDelegateCompatIcs.sendAccessibilityEvent(delegate, host, eventType);
        }

        public void sendAccessibilityEventUnchecked(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            Object delegate = obj;
            View host = view;
            AccessibilityEvent event = accessibilityEvent;
            Object obj2 = delegate;
            View view2 = host;
            AccessibilityEvent accessibilityEvent2 = event;
            AccessibilityDelegateCompatIcs.sendAccessibilityEventUnchecked(delegate, host, event);
        }
    }

    /* renamed from: android.support.v4.view.AccessibilityDelegateCompat$AccessibilityDelegateImpl */
    interface AccessibilityDelegateImpl {
        boolean dispatchPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent);

        AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object obj, View view);

        Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat accessibilityDelegateCompat);

        Object newAccessiblityDelegateDefaultImpl();

        void onInitializeAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent);

        void onInitializeAccessibilityNodeInfo(Object obj, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

        void onPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent);

        boolean onRequestSendAccessibilityEvent(Object obj, ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

        boolean performAccessibilityAction(Object obj, View view, int i, Bundle bundle);

        void sendAccessibilityEvent(Object obj, View view, int i);

        void sendAccessibilityEventUnchecked(Object obj, View view, AccessibilityEvent accessibilityEvent);
    }

    /* renamed from: android.support.v4.view.AccessibilityDelegateCompat$AccessibilityDelegateJellyBeanImpl */
    static class AccessibilityDelegateJellyBeanImpl extends AccessibilityDelegateIcsImpl {
        AccessibilityDelegateJellyBeanImpl() {
        }

        public Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat accessibilityDelegateCompat) {
            AccessibilityDelegateCompat compat = accessibilityDelegateCompat;
            AccessibilityDelegateCompat accessibilityDelegateCompat2 = compat;
            final AccessibilityDelegateCompat accessibilityDelegateCompat3 = compat;
            return AccessibilityDelegateCompatJellyBean.newAccessibilityDelegateBridge(new AccessibilityDelegateBridgeJellyBean(this) {
                final /* synthetic */ AccessibilityDelegateJellyBeanImpl this$0;

                {
                    AccessibilityDelegateJellyBeanImpl this$02 = r6;
                    AccessibilityDelegateCompat accessibilityDelegateCompat = r7;
                    AccessibilityDelegateJellyBeanImpl accessibilityDelegateJellyBeanImpl = this$02;
                    this.this$0 = this$02;
                }

                public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                    View host = view;
                    AccessibilityEvent event = accessibilityEvent;
                    View view2 = host;
                    AccessibilityEvent accessibilityEvent2 = event;
                    return accessibilityDelegateCompat3.dispatchPopulateAccessibilityEvent(host, event);
                }

                public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                    View host = view;
                    AccessibilityEvent event = accessibilityEvent;
                    View view2 = host;
                    AccessibilityEvent accessibilityEvent2 = event;
                    accessibilityDelegateCompat3.onInitializeAccessibilityEvent(host, event);
                }

                public void onInitializeAccessibilityNodeInfo(View view, Object obj) {
                    View host = view;
                    Object info = obj;
                    View view2 = host;
                    Object obj2 = info;
                    accessibilityDelegateCompat3.onInitializeAccessibilityNodeInfo(host, new AccessibilityNodeInfoCompat(info));
                }

                public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                    View host = view;
                    AccessibilityEvent event = accessibilityEvent;
                    View view2 = host;
                    AccessibilityEvent accessibilityEvent2 = event;
                    accessibilityDelegateCompat3.onPopulateAccessibilityEvent(host, event);
                }

                public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                    ViewGroup host = viewGroup;
                    View child = view;
                    AccessibilityEvent event = accessibilityEvent;
                    ViewGroup viewGroup2 = host;
                    View view2 = child;
                    AccessibilityEvent accessibilityEvent2 = event;
                    return accessibilityDelegateCompat3.onRequestSendAccessibilityEvent(host, child, event);
                }

                public void sendAccessibilityEvent(View view, int i) {
                    View host = view;
                    int eventType = i;
                    View view2 = host;
                    int i2 = eventType;
                    accessibilityDelegateCompat3.sendAccessibilityEvent(host, eventType);
                }

                public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
                    View host = view;
                    AccessibilityEvent event = accessibilityEvent;
                    View view2 = host;
                    AccessibilityEvent accessibilityEvent2 = event;
                    accessibilityDelegateCompat3.sendAccessibilityEventUnchecked(host, event);
                }

                public Object getAccessibilityNodeProvider(View view) {
                    View host = view;
                    View view2 = host;
                    AccessibilityNodeProviderCompat accessibilityNodeProvider = accessibilityDelegateCompat3.getAccessibilityNodeProvider(host);
                    AccessibilityNodeProviderCompat provider = accessibilityNodeProvider;
                    if (accessibilityNodeProvider == null) {
                        return null;
                    }
                    return provider.getProvider();
                }

                public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                    View host = view;
                    int action = i;
                    Bundle args = bundle;
                    View view2 = host;
                    int i2 = action;
                    Bundle bundle2 = args;
                    return accessibilityDelegateCompat3.performAccessibilityAction(host, action, args);
                }
            });
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object obj, View view) {
            Object delegate = obj;
            View host = view;
            Object obj2 = delegate;
            View view2 = host;
            Object accessibilityNodeProvider = AccessibilityDelegateCompatJellyBean.getAccessibilityNodeProvider(delegate, host);
            Object provider = accessibilityNodeProvider;
            if (accessibilityNodeProvider == null) {
                return null;
            }
            return new AccessibilityNodeProviderCompat(provider);
        }

        public boolean performAccessibilityAction(Object obj, View view, int i, Bundle bundle) {
            Object delegate = obj;
            View host = view;
            int action = i;
            Bundle args = bundle;
            Object obj2 = delegate;
            View view2 = host;
            int i2 = action;
            Bundle bundle2 = args;
            return AccessibilityDelegateCompatJellyBean.performAccessibilityAction(delegate, host, action, args);
        }
    }

    /* renamed from: android.support.v4.view.AccessibilityDelegateCompat$AccessibilityDelegateStubImpl */
    static class AccessibilityDelegateStubImpl implements AccessibilityDelegateImpl {
        AccessibilityDelegateStubImpl() {
        }

        public Object newAccessiblityDelegateDefaultImpl() {
            return null;
        }

        public Object newAccessiblityDelegateBridge(AccessibilityDelegateCompat accessibilityDelegateCompat) {
            AccessibilityDelegateCompat accessibilityDelegateCompat2 = accessibilityDelegateCompat;
            return null;
        }

        public boolean dispatchPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            Object obj2 = obj;
            View view2 = view;
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            return false;
        }

        public void onInitializeAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            Object obj2 = obj;
            View view2 = view;
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
        }

        public void onInitializeAccessibilityNodeInfo(Object obj, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            Object obj2 = obj;
            View view2 = view;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = accessibilityNodeInfoCompat;
        }

        public void onPopulateAccessibilityEvent(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            Object obj2 = obj;
            View view2 = view;
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
        }

        public boolean onRequestSendAccessibilityEvent(Object obj, ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            Object obj2 = obj;
            ViewGroup viewGroup2 = viewGroup;
            View view2 = view;
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            return true;
        }

        public void sendAccessibilityEvent(Object obj, View view, int i) {
            Object obj2 = obj;
            View view2 = view;
            int i2 = i;
        }

        public void sendAccessibilityEventUnchecked(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            Object obj2 = obj;
            View view2 = view;
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object obj, View view) {
            Object obj2 = obj;
            View view2 = view;
            return null;
        }

        public boolean performAccessibilityAction(Object obj, View view, int i, Bundle bundle) {
            Object obj2 = obj;
            View view2 = view;
            int i2 = i;
            Bundle bundle2 = bundle;
            return false;
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityDelegateJellyBeanImpl();
        } else if (VERSION.SDK_INT < 14) {
            IMPL = new AccessibilityDelegateStubImpl();
        } else {
            IMPL = new AccessibilityDelegateIcsImpl();
        }
    }

    public AccessibilityDelegateCompat() {
    }

    /* access modifiers changed from: 0000 */
    public Object getBridge() {
        return this.mBridge;
    }

    public void sendAccessibilityEvent(View view, int i) {
        View host = view;
        int eventType = i;
        View view2 = host;
        int i2 = eventType;
        IMPL.sendAccessibilityEvent(DEFAULT_DELEGATE, host, eventType);
    }

    public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
        View host = view;
        AccessibilityEvent event = accessibilityEvent;
        View view2 = host;
        AccessibilityEvent accessibilityEvent2 = event;
        IMPL.sendAccessibilityEventUnchecked(DEFAULT_DELEGATE, host, event);
    }

    public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        View host = view;
        AccessibilityEvent event = accessibilityEvent;
        View view2 = host;
        AccessibilityEvent accessibilityEvent2 = event;
        return IMPL.dispatchPopulateAccessibilityEvent(DEFAULT_DELEGATE, host, event);
    }

    public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        View host = view;
        AccessibilityEvent event = accessibilityEvent;
        View view2 = host;
        AccessibilityEvent accessibilityEvent2 = event;
        IMPL.onPopulateAccessibilityEvent(DEFAULT_DELEGATE, host, event);
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        View host = view;
        AccessibilityEvent event = accessibilityEvent;
        View view2 = host;
        AccessibilityEvent accessibilityEvent2 = event;
        IMPL.onInitializeAccessibilityEvent(DEFAULT_DELEGATE, host, event);
    }

    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        View host = view;
        AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
        View view2 = host;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
        IMPL.onInitializeAccessibilityNodeInfo(DEFAULT_DELEGATE, host, info);
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        ViewGroup host = viewGroup;
        View child = view;
        AccessibilityEvent event = accessibilityEvent;
        ViewGroup viewGroup2 = host;
        View view2 = child;
        AccessibilityEvent accessibilityEvent2 = event;
        return IMPL.onRequestSendAccessibilityEvent(DEFAULT_DELEGATE, host, child, event);
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        View host = view;
        View view2 = host;
        return IMPL.getAccessibilityNodeProvider(DEFAULT_DELEGATE, host);
    }

    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        View host = view;
        int action = i;
        Bundle args = bundle;
        View view2 = host;
        int i2 = action;
        Bundle bundle2 = args;
        return IMPL.performAccessibilityAction(DEFAULT_DELEGATE, host, action, args);
    }
}
