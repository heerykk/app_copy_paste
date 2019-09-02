package android.support.p000v4.view.accessibility;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat */
public class AccessibilityNodeProviderCompat {
    public static final int HOST_VIEW_ID = -1;
    private static final AccessibilityNodeProviderImpl IMPL;
    private final Object mProvider;

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat$AccessibilityNodeProviderImpl */
    interface AccessibilityNodeProviderImpl {
        Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat);
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat$AccessibilityNodeProviderJellyBeanImpl */
    private static class AccessibilityNodeProviderJellyBeanImpl extends AccessibilityNodeProviderStubImpl {
        AccessibilityNodeProviderJellyBeanImpl() {
        }

        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            AccessibilityNodeProviderCompat compat = accessibilityNodeProviderCompat;
            AccessibilityNodeProviderCompat accessibilityNodeProviderCompat2 = compat;
            final AccessibilityNodeProviderCompat accessibilityNodeProviderCompat3 = compat;
            return AccessibilityNodeProviderCompatJellyBean.newAccessibilityNodeProviderBridge(new AccessibilityNodeInfoBridge(this) {
                final /* synthetic */ AccessibilityNodeProviderJellyBeanImpl this$0;

                {
                    AccessibilityNodeProviderJellyBeanImpl this$02 = r6;
                    AccessibilityNodeProviderCompat accessibilityNodeProviderCompat = r7;
                    AccessibilityNodeProviderJellyBeanImpl accessibilityNodeProviderJellyBeanImpl = this$02;
                    this.this$0 = this$02;
                }

                public boolean performAction(int i, int i2, Bundle bundle) {
                    int virtualViewId = i;
                    int action = i2;
                    Bundle arguments = bundle;
                    int i3 = virtualViewId;
                    int i4 = action;
                    Bundle bundle2 = arguments;
                    return accessibilityNodeProviderCompat3.performAction(virtualViewId, action, arguments);
                }

                public List<Object> findAccessibilityNodeInfosByText(String str, int i) {
                    String text = str;
                    int virtualViewId = i;
                    String str2 = text;
                    int i2 = virtualViewId;
                    List findAccessibilityNodeInfosByText = accessibilityNodeProviderCompat3.findAccessibilityNodeInfosByText(text, virtualViewId);
                    List list = findAccessibilityNodeInfosByText;
                    if (findAccessibilityNodeInfosByText == null) {
                        return null;
                    }
                    ArrayList arrayList = new ArrayList();
                    int infoCount = list.size();
                    for (int i3 = 0; i3 < infoCount; i3++) {
                        boolean add = arrayList.add(((AccessibilityNodeInfoCompat) list.get(i3)).getInfo());
                    }
                    return arrayList;
                }

                public Object createAccessibilityNodeInfo(int i) {
                    int virtualViewId = i;
                    int i2 = virtualViewId;
                    AccessibilityNodeInfoCompat createAccessibilityNodeInfo = accessibilityNodeProviderCompat3.createAccessibilityNodeInfo(virtualViewId);
                    AccessibilityNodeInfoCompat compatInfo = createAccessibilityNodeInfo;
                    if (createAccessibilityNodeInfo != null) {
                        return compatInfo.getInfo();
                    }
                    return null;
                }
            });
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat$AccessibilityNodeProviderKitKatImpl */
    private static class AccessibilityNodeProviderKitKatImpl extends AccessibilityNodeProviderStubImpl {
        AccessibilityNodeProviderKitKatImpl() {
        }

        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            AccessibilityNodeProviderCompat compat = accessibilityNodeProviderCompat;
            AccessibilityNodeProviderCompat accessibilityNodeProviderCompat2 = compat;
            final AccessibilityNodeProviderCompat accessibilityNodeProviderCompat3 = compat;
            return AccessibilityNodeProviderCompatKitKat.newAccessibilityNodeProviderBridge(new AccessibilityNodeInfoBridge(this) {
                final /* synthetic */ AccessibilityNodeProviderKitKatImpl this$0;

                {
                    AccessibilityNodeProviderKitKatImpl this$02 = r6;
                    AccessibilityNodeProviderCompat accessibilityNodeProviderCompat = r7;
                    AccessibilityNodeProviderKitKatImpl accessibilityNodeProviderKitKatImpl = this$02;
                    this.this$0 = this$02;
                }

                public boolean performAction(int i, int i2, Bundle bundle) {
                    int virtualViewId = i;
                    int action = i2;
                    Bundle arguments = bundle;
                    int i3 = virtualViewId;
                    int i4 = action;
                    Bundle bundle2 = arguments;
                    return accessibilityNodeProviderCompat3.performAction(virtualViewId, action, arguments);
                }

                public List<Object> findAccessibilityNodeInfosByText(String str, int i) {
                    String text = str;
                    int virtualViewId = i;
                    String str2 = text;
                    int i2 = virtualViewId;
                    List findAccessibilityNodeInfosByText = accessibilityNodeProviderCompat3.findAccessibilityNodeInfosByText(text, virtualViewId);
                    List list = findAccessibilityNodeInfosByText;
                    if (findAccessibilityNodeInfosByText == null) {
                        return null;
                    }
                    ArrayList arrayList = new ArrayList();
                    int infoCount = list.size();
                    for (int i3 = 0; i3 < infoCount; i3++) {
                        boolean add = arrayList.add(((AccessibilityNodeInfoCompat) list.get(i3)).getInfo());
                    }
                    return arrayList;
                }

                public Object createAccessibilityNodeInfo(int i) {
                    int virtualViewId = i;
                    int i2 = virtualViewId;
                    AccessibilityNodeInfoCompat createAccessibilityNodeInfo = accessibilityNodeProviderCompat3.createAccessibilityNodeInfo(virtualViewId);
                    AccessibilityNodeInfoCompat compatInfo = createAccessibilityNodeInfo;
                    if (createAccessibilityNodeInfo != null) {
                        return compatInfo.getInfo();
                    }
                    return null;
                }

                public Object findFocus(int i) {
                    int focus = i;
                    int i2 = focus;
                    AccessibilityNodeInfoCompat findFocus = accessibilityNodeProviderCompat3.findFocus(focus);
                    AccessibilityNodeInfoCompat compatInfo = findFocus;
                    if (findFocus != null) {
                        return compatInfo.getInfo();
                    }
                    return null;
                }
            });
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat$AccessibilityNodeProviderStubImpl */
    static class AccessibilityNodeProviderStubImpl implements AccessibilityNodeProviderImpl {
        AccessibilityNodeProviderStubImpl() {
        }

        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            AccessibilityNodeProviderCompat accessibilityNodeProviderCompat2 = accessibilityNodeProviderCompat;
            return null;
        }
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityNodeProviderKitKatImpl();
        } else if (VERSION.SDK_INT < 16) {
            IMPL = new AccessibilityNodeProviderStubImpl();
        } else {
            IMPL = new AccessibilityNodeProviderJellyBeanImpl();
        }
    }

    public AccessibilityNodeProviderCompat() {
        this.mProvider = IMPL.newAccessibilityNodeProviderBridge(this);
    }

    public AccessibilityNodeProviderCompat(Object obj) {
        Object provider = obj;
        Object obj2 = provider;
        this.mProvider = provider;
    }

    public Object getProvider() {
        return this.mProvider;
    }

    @Nullable
    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int i) {
        int i2 = i;
        return null;
    }

    public boolean performAction(int i, int i2, Bundle bundle) {
        int i3 = i;
        int i4 = i2;
        Bundle bundle2 = bundle;
        return false;
    }

    @Nullable
    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String str, int i) {
        String str2 = str;
        int i2 = i;
        return null;
    }

    @Nullable
    public AccessibilityNodeInfoCompat findFocus(int i) {
        int i2 = i;
        return null;
    }
}
