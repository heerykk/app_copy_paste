package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.view.MotionEvent;

/* renamed from: android.support.v4.view.MotionEventCompat */
public final class MotionEventCompat {
    public static final int ACTION_HOVER_ENTER = 9;
    public static final int ACTION_HOVER_EXIT = 10;
    public static final int ACTION_HOVER_MOVE = 7;
    public static final int ACTION_MASK = 255;
    public static final int ACTION_POINTER_DOWN = 5;
    public static final int ACTION_POINTER_INDEX_MASK = 65280;
    public static final int ACTION_POINTER_INDEX_SHIFT = 8;
    public static final int ACTION_POINTER_UP = 6;
    public static final int ACTION_SCROLL = 8;
    public static final int AXIS_BRAKE = 23;
    public static final int AXIS_DISTANCE = 24;
    public static final int AXIS_GAS = 22;
    public static final int AXIS_GENERIC_1 = 32;
    public static final int AXIS_GENERIC_10 = 41;
    public static final int AXIS_GENERIC_11 = 42;
    public static final int AXIS_GENERIC_12 = 43;
    public static final int AXIS_GENERIC_13 = 44;
    public static final int AXIS_GENERIC_14 = 45;
    public static final int AXIS_GENERIC_15 = 46;
    public static final int AXIS_GENERIC_16 = 47;
    public static final int AXIS_GENERIC_2 = 33;
    public static final int AXIS_GENERIC_3 = 34;
    public static final int AXIS_GENERIC_4 = 35;
    public static final int AXIS_GENERIC_5 = 36;
    public static final int AXIS_GENERIC_6 = 37;
    public static final int AXIS_GENERIC_7 = 38;
    public static final int AXIS_GENERIC_8 = 39;
    public static final int AXIS_GENERIC_9 = 40;
    public static final int AXIS_HAT_X = 15;
    public static final int AXIS_HAT_Y = 16;
    public static final int AXIS_HSCROLL = 10;
    public static final int AXIS_LTRIGGER = 17;
    public static final int AXIS_ORIENTATION = 8;
    public static final int AXIS_PRESSURE = 2;
    public static final int AXIS_RELATIVE_X = 27;
    public static final int AXIS_RELATIVE_Y = 28;
    public static final int AXIS_RTRIGGER = 18;
    public static final int AXIS_RUDDER = 20;
    public static final int AXIS_RX = 12;
    public static final int AXIS_RY = 13;
    public static final int AXIS_RZ = 14;
    public static final int AXIS_SIZE = 3;
    public static final int AXIS_THROTTLE = 19;
    public static final int AXIS_TILT = 25;
    public static final int AXIS_TOOL_MAJOR = 6;
    public static final int AXIS_TOOL_MINOR = 7;
    public static final int AXIS_TOUCH_MAJOR = 4;
    public static final int AXIS_TOUCH_MINOR = 5;
    public static final int AXIS_VSCROLL = 9;
    public static final int AXIS_WHEEL = 21;
    public static final int AXIS_X = 0;
    public static final int AXIS_Y = 1;
    public static final int AXIS_Z = 11;
    public static final int BUTTON_PRIMARY = 1;
    static final MotionEventVersionImpl IMPL;

    /* renamed from: android.support.v4.view.MotionEventCompat$BaseMotionEventVersionImpl */
    static class BaseMotionEventVersionImpl implements MotionEventVersionImpl {
        BaseMotionEventVersionImpl() {
        }

        public float getAxisValue(MotionEvent motionEvent, int i) {
            MotionEvent motionEvent2 = motionEvent;
            int i2 = i;
            return 0.0f;
        }

        public float getAxisValue(MotionEvent motionEvent, int i, int i2) {
            MotionEvent motionEvent2 = motionEvent;
            int i3 = i;
            int i4 = i2;
            return 0.0f;
        }

        public int getButtonState(MotionEvent motionEvent) {
            MotionEvent motionEvent2 = motionEvent;
            return 0;
        }
    }

    /* renamed from: android.support.v4.view.MotionEventCompat$HoneycombMr1MotionEventVersionImpl */
    static class HoneycombMr1MotionEventVersionImpl extends BaseMotionEventVersionImpl {
        HoneycombMr1MotionEventVersionImpl() {
        }

        public float getAxisValue(MotionEvent motionEvent, int i) {
            MotionEvent event = motionEvent;
            int axis = i;
            MotionEvent motionEvent2 = event;
            int i2 = axis;
            return MotionEventCompatHoneycombMr1.getAxisValue(event, axis);
        }

        public float getAxisValue(MotionEvent motionEvent, int i, int i2) {
            MotionEvent event = motionEvent;
            int axis = i;
            int pointerIndex = i2;
            MotionEvent motionEvent2 = event;
            int i3 = axis;
            int i4 = pointerIndex;
            return MotionEventCompatHoneycombMr1.getAxisValue(event, axis, pointerIndex);
        }
    }

    /* renamed from: android.support.v4.view.MotionEventCompat$ICSMotionEventVersionImpl */
    private static class ICSMotionEventVersionImpl extends HoneycombMr1MotionEventVersionImpl {
        ICSMotionEventVersionImpl() {
        }

        public int getButtonState(MotionEvent motionEvent) {
            MotionEvent event = motionEvent;
            MotionEvent motionEvent2 = event;
            return MotionEventCompatICS.getButtonState(event);
        }
    }

    /* renamed from: android.support.v4.view.MotionEventCompat$MotionEventVersionImpl */
    interface MotionEventVersionImpl {
        float getAxisValue(MotionEvent motionEvent, int i);

        float getAxisValue(MotionEvent motionEvent, int i, int i2);

        int getButtonState(MotionEvent motionEvent);
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new ICSMotionEventVersionImpl();
        } else if (VERSION.SDK_INT < 12) {
            IMPL = new BaseMotionEventVersionImpl();
        } else {
            IMPL = new HoneycombMr1MotionEventVersionImpl();
        }
    }

    public static int getActionMasked(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        return event.getAction() & 255;
    }

    public static int getActionIndex(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        return (event.getAction() & ACTION_POINTER_INDEX_MASK) >> 8;
    }

    @Deprecated
    public static int findPointerIndex(MotionEvent motionEvent, int i) {
        MotionEvent event = motionEvent;
        int pointerId = i;
        MotionEvent motionEvent2 = event;
        int i2 = pointerId;
        return event.findPointerIndex(pointerId);
    }

    @Deprecated
    public static int getPointerId(MotionEvent motionEvent, int i) {
        MotionEvent event = motionEvent;
        int pointerIndex = i;
        MotionEvent motionEvent2 = event;
        int i2 = pointerIndex;
        return event.getPointerId(pointerIndex);
    }

    @Deprecated
    public static float getX(MotionEvent motionEvent, int i) {
        MotionEvent event = motionEvent;
        int pointerIndex = i;
        MotionEvent motionEvent2 = event;
        int i2 = pointerIndex;
        return event.getX(pointerIndex);
    }

    @Deprecated
    public static float getY(MotionEvent motionEvent, int i) {
        MotionEvent event = motionEvent;
        int pointerIndex = i;
        MotionEvent motionEvent2 = event;
        int i2 = pointerIndex;
        return event.getY(pointerIndex);
    }

    @Deprecated
    public static int getPointerCount(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        return event.getPointerCount();
    }

    @Deprecated
    public static int getSource(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        return event.getSource();
    }

    public static boolean isFromSource(MotionEvent motionEvent, int i) {
        MotionEvent event = motionEvent;
        int source = i;
        MotionEvent motionEvent2 = event;
        int i2 = source;
        return (event.getSource() & source) == source;
    }

    public static float getAxisValue(MotionEvent motionEvent, int i) {
        MotionEvent event = motionEvent;
        int axis = i;
        MotionEvent motionEvent2 = event;
        int i2 = axis;
        return IMPL.getAxisValue(event, axis);
    }

    public static float getAxisValue(MotionEvent motionEvent, int i, int i2) {
        MotionEvent event = motionEvent;
        int axis = i;
        int pointerIndex = i2;
        MotionEvent motionEvent2 = event;
        int i3 = axis;
        int i4 = pointerIndex;
        return IMPL.getAxisValue(event, axis, pointerIndex);
    }

    public static int getButtonState(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        return IMPL.getButtonState(event);
    }

    private MotionEventCompat() {
    }
}
