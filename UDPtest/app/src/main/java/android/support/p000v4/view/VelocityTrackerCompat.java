package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.view.VelocityTracker;

/* renamed from: android.support.v4.view.VelocityTrackerCompat */
public final class VelocityTrackerCompat {
    static final VelocityTrackerVersionImpl IMPL;

    /* renamed from: android.support.v4.view.VelocityTrackerCompat$BaseVelocityTrackerVersionImpl */
    static class BaseVelocityTrackerVersionImpl implements VelocityTrackerVersionImpl {
        BaseVelocityTrackerVersionImpl() {
        }

        public float getXVelocity(VelocityTracker velocityTracker, int i) {
            VelocityTracker tracker = velocityTracker;
            VelocityTracker velocityTracker2 = tracker;
            int i2 = i;
            return tracker.getXVelocity();
        }

        public float getYVelocity(VelocityTracker velocityTracker, int i) {
            VelocityTracker tracker = velocityTracker;
            VelocityTracker velocityTracker2 = tracker;
            int i2 = i;
            return tracker.getYVelocity();
        }
    }

    /* renamed from: android.support.v4.view.VelocityTrackerCompat$HoneycombVelocityTrackerVersionImpl */
    static class HoneycombVelocityTrackerVersionImpl implements VelocityTrackerVersionImpl {
        HoneycombVelocityTrackerVersionImpl() {
        }

        public float getXVelocity(VelocityTracker velocityTracker, int i) {
            VelocityTracker tracker = velocityTracker;
            int pointerId = i;
            VelocityTracker velocityTracker2 = tracker;
            int i2 = pointerId;
            return VelocityTrackerCompatHoneycomb.getXVelocity(tracker, pointerId);
        }

        public float getYVelocity(VelocityTracker velocityTracker, int i) {
            VelocityTracker tracker = velocityTracker;
            int pointerId = i;
            VelocityTracker velocityTracker2 = tracker;
            int i2 = pointerId;
            return VelocityTrackerCompatHoneycomb.getYVelocity(tracker, pointerId);
        }
    }

    /* renamed from: android.support.v4.view.VelocityTrackerCompat$VelocityTrackerVersionImpl */
    interface VelocityTrackerVersionImpl {
        float getXVelocity(VelocityTracker velocityTracker, int i);

        float getYVelocity(VelocityTracker velocityTracker, int i);
    }

    static {
        if (VERSION.SDK_INT < 11) {
            IMPL = new BaseVelocityTrackerVersionImpl();
        } else {
            IMPL = new HoneycombVelocityTrackerVersionImpl();
        }
    }

    public static float getXVelocity(VelocityTracker velocityTracker, int i) {
        VelocityTracker tracker = velocityTracker;
        int pointerId = i;
        VelocityTracker velocityTracker2 = tracker;
        int i2 = pointerId;
        return IMPL.getXVelocity(tracker, pointerId);
    }

    public static float getYVelocity(VelocityTracker velocityTracker, int i) {
        VelocityTracker tracker = velocityTracker;
        int pointerId = i;
        VelocityTracker velocityTracker2 = tracker;
        int i2 = pointerId;
        return IMPL.getYVelocity(tracker, pointerId);
    }

    private VelocityTrackerCompat() {
    }
}
