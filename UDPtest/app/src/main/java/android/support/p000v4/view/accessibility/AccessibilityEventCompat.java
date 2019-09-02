package android.support.p000v4.view.accessibility;

import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityEvent;

/* renamed from: android.support.v4.view.accessibility.AccessibilityEventCompat */
public final class AccessibilityEventCompat {
    public static final int CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION = 4;
    public static final int CONTENT_CHANGE_TYPE_SUBTREE = 1;
    public static final int CONTENT_CHANGE_TYPE_TEXT = 2;
    public static final int CONTENT_CHANGE_TYPE_UNDEFINED = 0;
    private static final AccessibilityEventVersionImpl IMPL;
    public static final int TYPES_ALL_MASK = -1;
    public static final int TYPE_ANNOUNCEMENT = 16384;
    public static final int TYPE_ASSIST_READING_CONTEXT = 16777216;
    public static final int TYPE_GESTURE_DETECTION_END = 524288;
    public static final int TYPE_GESTURE_DETECTION_START = 262144;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_END = 1024;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_START = 512;
    public static final int TYPE_TOUCH_INTERACTION_END = 2097152;
    public static final int TYPE_TOUCH_INTERACTION_START = 1048576;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUSED = 32768;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED = 65536;
    public static final int TYPE_VIEW_CONTEXT_CLICKED = 8388608;
    public static final int TYPE_VIEW_HOVER_ENTER = 128;
    public static final int TYPE_VIEW_HOVER_EXIT = 256;
    public static final int TYPE_VIEW_SCROLLED = 4096;
    public static final int TYPE_VIEW_TEXT_SELECTION_CHANGED = 8192;
    public static final int TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY = 131072;
    public static final int TYPE_WINDOWS_CHANGED = 4194304;
    public static final int TYPE_WINDOW_CONTENT_CHANGED = 2048;

    /* renamed from: android.support.v4.view.accessibility.AccessibilityEventCompat$AccessibilityEventIcsImpl */
    static class AccessibilityEventIcsImpl extends AccessibilityEventStubImpl {
        AccessibilityEventIcsImpl() {
        }

        public void appendRecord(AccessibilityEvent accessibilityEvent, Object obj) {
            AccessibilityEvent event = accessibilityEvent;
            Object record = obj;
            AccessibilityEvent accessibilityEvent2 = event;
            Object obj2 = record;
            AccessibilityEventCompatIcs.appendRecord(event, record);
        }

        public Object getRecord(AccessibilityEvent accessibilityEvent, int i) {
            AccessibilityEvent event = accessibilityEvent;
            int index = i;
            AccessibilityEvent accessibilityEvent2 = event;
            int i2 = index;
            return AccessibilityEventCompatIcs.getRecord(event, index);
        }

        public int getRecordCount(AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent event = accessibilityEvent;
            AccessibilityEvent accessibilityEvent2 = event;
            return AccessibilityEventCompatIcs.getRecordCount(event);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityEventCompat$AccessibilityEventJellyBeanImpl */
    static class AccessibilityEventJellyBeanImpl extends AccessibilityEventIcsImpl {
        AccessibilityEventJellyBeanImpl() {
        }

        public void setMovementGranularity(AccessibilityEvent accessibilityEvent, int i) {
            AccessibilityEvent event = accessibilityEvent;
            int granularity = i;
            AccessibilityEvent accessibilityEvent2 = event;
            int i2 = granularity;
            AccessibilityEventCompatJellyBean.setMovementGranularity(event, granularity);
        }

        public int getMovementGranularity(AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent event = accessibilityEvent;
            AccessibilityEvent accessibilityEvent2 = event;
            return AccessibilityEventCompatJellyBean.getMovementGranularity(event);
        }

        public void setAction(AccessibilityEvent accessibilityEvent, int i) {
            AccessibilityEvent event = accessibilityEvent;
            int action = i;
            AccessibilityEvent accessibilityEvent2 = event;
            int i2 = action;
            AccessibilityEventCompatJellyBean.setAction(event, action);
        }

        public int getAction(AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent event = accessibilityEvent;
            AccessibilityEvent accessibilityEvent2 = event;
            return AccessibilityEventCompatJellyBean.getAction(event);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityEventCompat$AccessibilityEventKitKatImpl */
    static class AccessibilityEventKitKatImpl extends AccessibilityEventJellyBeanImpl {
        AccessibilityEventKitKatImpl() {
        }

        public void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int i) {
            AccessibilityEvent event = accessibilityEvent;
            int types = i;
            AccessibilityEvent accessibilityEvent2 = event;
            int i2 = types;
            AccessibilityEventCompatKitKat.setContentChangeTypes(event, types);
        }

        public int getContentChangeTypes(AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent event = accessibilityEvent;
            AccessibilityEvent accessibilityEvent2 = event;
            return AccessibilityEventCompatKitKat.getContentChangeTypes(event);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityEventCompat$AccessibilityEventStubImpl */
    static class AccessibilityEventStubImpl implements AccessibilityEventVersionImpl {
        AccessibilityEventStubImpl() {
        }

        public void appendRecord(AccessibilityEvent accessibilityEvent, Object obj) {
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            Object obj2 = obj;
        }

        public Object getRecord(AccessibilityEvent accessibilityEvent, int i) {
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            int i2 = i;
            return null;
        }

        public void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int i) {
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            int i2 = i;
        }

        public int getRecordCount(AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            return 0;
        }

        public int getContentChangeTypes(AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            return 0;
        }

        public void setMovementGranularity(AccessibilityEvent accessibilityEvent, int i) {
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            int i2 = i;
        }

        public int getMovementGranularity(AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            return 0;
        }

        public void setAction(AccessibilityEvent accessibilityEvent, int i) {
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            int i2 = i;
        }

        public int getAction(AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            return 0;
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityEventCompat$AccessibilityEventVersionImpl */
    interface AccessibilityEventVersionImpl {
        void appendRecord(AccessibilityEvent accessibilityEvent, Object obj);

        int getAction(AccessibilityEvent accessibilityEvent);

        int getContentChangeTypes(AccessibilityEvent accessibilityEvent);

        int getMovementGranularity(AccessibilityEvent accessibilityEvent);

        Object getRecord(AccessibilityEvent accessibilityEvent, int i);

        int getRecordCount(AccessibilityEvent accessibilityEvent);

        void setAction(AccessibilityEvent accessibilityEvent, int i);

        void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int i);

        void setMovementGranularity(AccessibilityEvent accessibilityEvent, int i);
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityEventKitKatImpl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityEventJellyBeanImpl();
        } else if (VERSION.SDK_INT < 14) {
            IMPL = new AccessibilityEventStubImpl();
        } else {
            IMPL = new AccessibilityEventIcsImpl();
        }
    }

    private AccessibilityEventCompat() {
    }

    public static int getRecordCount(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        return IMPL.getRecordCount(event);
    }

    public static void appendRecord(AccessibilityEvent accessibilityEvent, AccessibilityRecordCompat accessibilityRecordCompat) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityRecordCompat record = accessibilityRecordCompat;
        AccessibilityEvent accessibilityEvent2 = event;
        AccessibilityRecordCompat accessibilityRecordCompat2 = record;
        IMPL.appendRecord(event, record.getImpl());
    }

    public static AccessibilityRecordCompat getRecord(AccessibilityEvent accessibilityEvent, int i) {
        AccessibilityEvent event = accessibilityEvent;
        int index = i;
        AccessibilityEvent accessibilityEvent2 = event;
        int i2 = index;
        return new AccessibilityRecordCompat(IMPL.getRecord(event, index));
    }

    public static AccessibilityRecordCompat asRecord(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        return new AccessibilityRecordCompat(event);
    }

    public static void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int i) {
        AccessibilityEvent event = accessibilityEvent;
        int changeTypes = i;
        AccessibilityEvent accessibilityEvent2 = event;
        int i2 = changeTypes;
        IMPL.setContentChangeTypes(event, changeTypes);
    }

    public static int getContentChangeTypes(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        return IMPL.getContentChangeTypes(event);
    }

    public void setMovementGranularity(AccessibilityEvent accessibilityEvent, int i) {
        AccessibilityEvent event = accessibilityEvent;
        int granularity = i;
        AccessibilityEvent accessibilityEvent2 = event;
        int i2 = granularity;
        IMPL.setMovementGranularity(event, granularity);
    }

    public int getMovementGranularity(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        return IMPL.getMovementGranularity(event);
    }

    public void setAction(AccessibilityEvent accessibilityEvent, int i) {
        AccessibilityEvent event = accessibilityEvent;
        int action = i;
        AccessibilityEvent accessibilityEvent2 = event;
        int i2 = action;
        IMPL.setAction(event, action);
    }

    public int getAction(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        return IMPL.getAction(event);
    }
}
