package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;

@TargetApi(12)
@RequiresApi(12)
/* renamed from: android.support.v4.view.MotionEventCompatHoneycombMr1 */
class MotionEventCompatHoneycombMr1 {
    MotionEventCompatHoneycombMr1() {
    }

    static float getAxisValue(MotionEvent motionEvent, int i) {
        MotionEvent event = motionEvent;
        int axis = i;
        MotionEvent motionEvent2 = event;
        int i2 = axis;
        return event.getAxisValue(axis);
    }

    static float getAxisValue(MotionEvent motionEvent, int i, int i2) {
        MotionEvent event = motionEvent;
        int axis = i;
        int pointerIndex = i2;
        MotionEvent motionEvent2 = event;
        int i3 = axis;
        int i4 = pointerIndex;
        return event.getAxisValue(axis, pointerIndex);
    }
}
