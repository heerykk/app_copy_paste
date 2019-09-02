package android.support.p000v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat */
public final class AccessibilityServiceInfoCompat {
    public static final int CAPABILITY_CAN_FILTER_KEY_EVENTS = 8;
    public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
    public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
    public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
    public static final int DEFAULT = 1;
    public static final int FEEDBACK_ALL_MASK = -1;
    public static final int FEEDBACK_BRAILLE = 32;
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
    public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
    public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;
    private static final AccessibilityServiceInfoVersionImpl IMPL;

    /* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat$AccessibilityServiceInfoIcsImpl */
    static class AccessibilityServiceInfoIcsImpl extends AccessibilityServiceInfoStubImpl {
        AccessibilityServiceInfoIcsImpl() {
        }

        public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo info = accessibilityServiceInfo;
            AccessibilityServiceInfo accessibilityServiceInfo2 = info;
            return AccessibilityServiceInfoCompatIcs.getCanRetrieveWindowContent(info);
        }

        public String getDescription(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo info = accessibilityServiceInfo;
            AccessibilityServiceInfo accessibilityServiceInfo2 = info;
            return AccessibilityServiceInfoCompatIcs.getDescription(info);
        }

        public String getId(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo info = accessibilityServiceInfo;
            AccessibilityServiceInfo accessibilityServiceInfo2 = info;
            return AccessibilityServiceInfoCompatIcs.getId(info);
        }

        public ResolveInfo getResolveInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo info = accessibilityServiceInfo;
            AccessibilityServiceInfo accessibilityServiceInfo2 = info;
            return AccessibilityServiceInfoCompatIcs.getResolveInfo(info);
        }

        public String getSettingsActivityName(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo info = accessibilityServiceInfo;
            AccessibilityServiceInfo accessibilityServiceInfo2 = info;
            return AccessibilityServiceInfoCompatIcs.getSettingsActivityName(info);
        }

        public int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo info = accessibilityServiceInfo;
            AccessibilityServiceInfo accessibilityServiceInfo2 = info;
            if (!getCanRetrieveWindowContent(info)) {
                return 0;
            }
            return 1;
        }
    }

    /* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat$AccessibilityServiceInfoJellyBeanImpl */
    static class AccessibilityServiceInfoJellyBeanImpl extends AccessibilityServiceInfoIcsImpl {
        AccessibilityServiceInfoJellyBeanImpl() {
        }

        public String loadDescription(AccessibilityServiceInfo accessibilityServiceInfo, PackageManager packageManager) {
            AccessibilityServiceInfo info = accessibilityServiceInfo;
            PackageManager pm = packageManager;
            AccessibilityServiceInfo accessibilityServiceInfo2 = info;
            PackageManager packageManager2 = pm;
            return AccessibilityServiceInfoCompatJellyBean.loadDescription(info, pm);
        }
    }

    /* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat$AccessibilityServiceInfoJellyBeanMr2Impl */
    static class AccessibilityServiceInfoJellyBeanMr2Impl extends AccessibilityServiceInfoJellyBeanImpl {
        AccessibilityServiceInfoJellyBeanMr2Impl() {
        }

        public int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo info = accessibilityServiceInfo;
            AccessibilityServiceInfo accessibilityServiceInfo2 = info;
            return AccessibilityServiceInfoCompatJellyBeanMr2.getCapabilities(info);
        }
    }

    /* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat$AccessibilityServiceInfoStubImpl */
    static class AccessibilityServiceInfoStubImpl implements AccessibilityServiceInfoVersionImpl {
        AccessibilityServiceInfoStubImpl() {
        }

        public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo accessibilityServiceInfo2 = accessibilityServiceInfo;
            return false;
        }

        public String getDescription(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo accessibilityServiceInfo2 = accessibilityServiceInfo;
            return null;
        }

        public String getId(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo accessibilityServiceInfo2 = accessibilityServiceInfo;
            return null;
        }

        public ResolveInfo getResolveInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo accessibilityServiceInfo2 = accessibilityServiceInfo;
            return null;
        }

        public String getSettingsActivityName(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo accessibilityServiceInfo2 = accessibilityServiceInfo;
            return null;
        }

        public int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo) {
            AccessibilityServiceInfo accessibilityServiceInfo2 = accessibilityServiceInfo;
            return 0;
        }

        public String loadDescription(AccessibilityServiceInfo accessibilityServiceInfo, PackageManager packageManager) {
            AccessibilityServiceInfo accessibilityServiceInfo2 = accessibilityServiceInfo;
            PackageManager packageManager2 = packageManager;
            return null;
        }
    }

    /* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat$AccessibilityServiceInfoVersionImpl */
    interface AccessibilityServiceInfoVersionImpl {
        boolean getCanRetrieveWindowContent(AccessibilityServiceInfo accessibilityServiceInfo);

        int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo);

        String getDescription(AccessibilityServiceInfo accessibilityServiceInfo);

        String getId(AccessibilityServiceInfo accessibilityServiceInfo);

        ResolveInfo getResolveInfo(AccessibilityServiceInfo accessibilityServiceInfo);

        String getSettingsActivityName(AccessibilityServiceInfo accessibilityServiceInfo);

        String loadDescription(AccessibilityServiceInfo accessibilityServiceInfo, PackageManager packageManager);
    }

    static {
        if (VERSION.SDK_INT >= 18) {
            IMPL = new AccessibilityServiceInfoJellyBeanMr2Impl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityServiceInfoJellyBeanImpl();
        } else if (VERSION.SDK_INT < 14) {
            IMPL = new AccessibilityServiceInfoStubImpl();
        } else {
            IMPL = new AccessibilityServiceInfoIcsImpl();
        }
    }

    private AccessibilityServiceInfoCompat() {
    }

    public static String getId(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return IMPL.getId(info);
    }

    public static ResolveInfo getResolveInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return IMPL.getResolveInfo(info);
    }

    public static String getSettingsActivityName(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return IMPL.getSettingsActivityName(info);
    }

    public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return IMPL.getCanRetrieveWindowContent(info);
    }

    public static String getDescription(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return IMPL.getDescription(info);
    }

    public static String loadDescription(AccessibilityServiceInfo accessibilityServiceInfo, PackageManager packageManager) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        PackageManager packageManager2 = packageManager;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        PackageManager packageManager3 = packageManager2;
        return IMPL.loadDescription(info, packageManager2);
    }

    public static String feedbackTypeToString(int i) {
        int feedbackType = i;
        StringBuilder sb = new StringBuilder();
        StringBuilder builder = sb;
        StringBuilder append = sb.append("[");
        while (feedbackType > 0) {
            int feedbackTypeFlag = 1 << Integer.numberOfTrailingZeros(feedbackType);
            feedbackType &= feedbackTypeFlag ^ -1;
            if (builder.length() > 1) {
                StringBuilder append2 = builder.append(", ");
            }
            switch (feedbackTypeFlag) {
                case 1:
                    StringBuilder append3 = builder.append("FEEDBACK_SPOKEN");
                    break;
                case 2:
                    StringBuilder append4 = builder.append("FEEDBACK_HAPTIC");
                    break;
                case 4:
                    StringBuilder append5 = builder.append("FEEDBACK_AUDIBLE");
                    break;
                case 8:
                    StringBuilder append6 = builder.append("FEEDBACK_VISUAL");
                    break;
                case 16:
                    StringBuilder append7 = builder.append("FEEDBACK_GENERIC");
                    break;
            }
        }
        StringBuilder append8 = builder.append("]");
        return builder.toString();
    }

    public static String flagToString(int i) {
        int flag = i;
        int i2 = flag;
        switch (flag) {
            case 1:
                return "DEFAULT";
            case 2:
                return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
            case 4:
                return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
            case 8:
                return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
            case 16:
                return "FLAG_REPORT_VIEW_IDS";
            case 32:
                return "FLAG_REQUEST_FILTER_KEY_EVENTS";
            default:
                return null;
        }
    }

    public static int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo) {
        AccessibilityServiceInfo info = accessibilityServiceInfo;
        AccessibilityServiceInfo accessibilityServiceInfo2 = info;
        return IMPL.getCapabilities(info);
    }

    public static String capabilityToString(int i) {
        int capability = i;
        int i2 = capability;
        switch (capability) {
            case 1:
                return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
            case 2:
                return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
            case 4:
                return "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
            case 8:
                return "CAPABILITY_CAN_FILTER_KEY_EVENTS";
            default:
                return "UNKNOWN";
        }
    }
}
