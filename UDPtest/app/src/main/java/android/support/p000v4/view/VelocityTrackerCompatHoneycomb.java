package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.VelocityTracker;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.view.VelocityTrackerCompatHoneycomb */
class VelocityTrackerCompatHoneycomb {
    VelocityTrackerCompatHoneycomb() {
    }

    public static float getXVelocity(VelocityTracker velocityTracker, int i) {
        VelocityTracker tracker = velocityTracker;
        int pointerId = i;
        VelocityTracker velocityTracker2 = tracker;
        int i2 = pointerId;
        return tracker.getXVelocity(pointerId);
    }

    public static float getYVelocity(VelocityTracker velocityTracker, int i) {
        VelocityTracker tracker = velocityTracker;
        int pointerId = i;
        VelocityTracker velocityTracker2 = tracker;
        int i2 = pointerId;
        return tracker.getYVelocity(pointerId);
    }
}
