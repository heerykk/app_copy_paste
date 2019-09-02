package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

/* renamed from: android.support.v4.view.ViewGroupCompat */
public final class ViewGroupCompat {
    static final ViewGroupCompatImpl IMPL;
    public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
    public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;

    /* renamed from: android.support.v4.view.ViewGroupCompat$ViewGroupCompatHCImpl */
    static class ViewGroupCompatHCImpl extends ViewGroupCompatStubImpl {
        ViewGroupCompatHCImpl() {
        }

        public void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean z) {
            ViewGroup group = viewGroup;
            ViewGroup viewGroup2 = group;
            ViewGroupCompatHC.setMotionEventSplittingEnabled(group, z);
        }
    }

    /* renamed from: android.support.v4.view.ViewGroupCompat$ViewGroupCompatIcsImpl */
    static class ViewGroupCompatIcsImpl extends ViewGroupCompatHCImpl {
        ViewGroupCompatIcsImpl() {
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            ViewGroup group = viewGroup;
            View child = view;
            AccessibilityEvent event = accessibilityEvent;
            ViewGroup viewGroup2 = group;
            View view2 = child;
            AccessibilityEvent accessibilityEvent2 = event;
            return ViewGroupCompatIcs.onRequestSendAccessibilityEvent(group, child, event);
        }
    }

    /* renamed from: android.support.v4.view.ViewGroupCompat$ViewGroupCompatImpl */
    interface ViewGroupCompatImpl {
        int getLayoutMode(ViewGroup viewGroup);

        int getNestedScrollAxes(ViewGroup viewGroup);

        boolean isTransitionGroup(ViewGroup viewGroup);

        boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

        void setLayoutMode(ViewGroup viewGroup, int i);

        void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean z);

        void setTransitionGroup(ViewGroup viewGroup, boolean z);
    }

    /* renamed from: android.support.v4.view.ViewGroupCompat$ViewGroupCompatJellybeanMR2Impl */
    static class ViewGroupCompatJellybeanMR2Impl extends ViewGroupCompatIcsImpl {
        ViewGroupCompatJellybeanMR2Impl() {
        }

        public int getLayoutMode(ViewGroup viewGroup) {
            ViewGroup group = viewGroup;
            ViewGroup viewGroup2 = group;
            return ViewGroupCompatJellybeanMR2.getLayoutMode(group);
        }

        public void setLayoutMode(ViewGroup viewGroup, int i) {
            ViewGroup group = viewGroup;
            int mode = i;
            ViewGroup viewGroup2 = group;
            int i2 = mode;
            ViewGroupCompatJellybeanMR2.setLayoutMode(group, mode);
        }
    }

    /* renamed from: android.support.v4.view.ViewGroupCompat$ViewGroupCompatLollipopImpl */
    static class ViewGroupCompatLollipopImpl extends ViewGroupCompatJellybeanMR2Impl {
        ViewGroupCompatLollipopImpl() {
        }

        public void setTransitionGroup(ViewGroup viewGroup, boolean z) {
            ViewGroup group = viewGroup;
            ViewGroup viewGroup2 = group;
            ViewGroupCompatLollipop.setTransitionGroup(group, z);
        }

        public boolean isTransitionGroup(ViewGroup viewGroup) {
            ViewGroup group = viewGroup;
            ViewGroup viewGroup2 = group;
            return ViewGroupCompatLollipop.isTransitionGroup(group);
        }

        public int getNestedScrollAxes(ViewGroup viewGroup) {
            ViewGroup group = viewGroup;
            ViewGroup viewGroup2 = group;
            return ViewGroupCompatLollipop.getNestedScrollAxes(group);
        }
    }

    /* renamed from: android.support.v4.view.ViewGroupCompat$ViewGroupCompatStubImpl */
    static class ViewGroupCompatStubImpl implements ViewGroupCompatImpl {
        ViewGroupCompatStubImpl() {
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            ViewGroup viewGroup2 = viewGroup;
            View view2 = view;
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
            return true;
        }

        public void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean z) {
            ViewGroup viewGroup2 = viewGroup;
            boolean z2 = z;
        }

        public int getLayoutMode(ViewGroup viewGroup) {
            ViewGroup viewGroup2 = viewGroup;
            return 0;
        }

        public void setLayoutMode(ViewGroup viewGroup, int i) {
            ViewGroup viewGroup2 = viewGroup;
            int i2 = i;
        }

        public void setTransitionGroup(ViewGroup viewGroup, boolean z) {
            ViewGroup viewGroup2 = viewGroup;
            boolean z2 = z;
        }

        public boolean isTransitionGroup(ViewGroup viewGroup) {
            ViewGroup viewGroup2 = viewGroup;
            return false;
        }

        public int getNestedScrollAxes(ViewGroup viewGroup) {
            ViewGroup group = viewGroup;
            ViewGroup viewGroup2 = group;
            if (!(group instanceof NestedScrollingParent)) {
                return 0;
            }
            return ((NestedScrollingParent) group).getNestedScrollAxes();
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 21) {
            IMPL = new ViewGroupCompatLollipopImpl();
        } else if (version >= 18) {
            IMPL = new ViewGroupCompatJellybeanMR2Impl();
        } else if (version >= 14) {
            IMPL = new ViewGroupCompatIcsImpl();
        } else if (version < 11) {
            IMPL = new ViewGroupCompatStubImpl();
        } else {
            IMPL = new ViewGroupCompatHCImpl();
        }
    }

    private ViewGroupCompat() {
    }

    public static boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        ViewGroup group = viewGroup;
        View child = view;
        AccessibilityEvent event = accessibilityEvent;
        ViewGroup viewGroup2 = group;
        View view2 = child;
        AccessibilityEvent accessibilityEvent2 = event;
        return IMPL.onRequestSendAccessibilityEvent(group, child, event);
    }

    public static void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean z) {
        ViewGroup group = viewGroup;
        ViewGroup viewGroup2 = group;
        IMPL.setMotionEventSplittingEnabled(group, z);
    }

    public static int getLayoutMode(ViewGroup viewGroup) {
        ViewGroup group = viewGroup;
        ViewGroup viewGroup2 = group;
        return IMPL.getLayoutMode(group);
    }

    public static void setLayoutMode(ViewGroup viewGroup, int i) {
        ViewGroup group = viewGroup;
        int mode = i;
        ViewGroup viewGroup2 = group;
        int i2 = mode;
        IMPL.setLayoutMode(group, mode);
    }

    public static void setTransitionGroup(ViewGroup viewGroup, boolean z) {
        ViewGroup group = viewGroup;
        ViewGroup viewGroup2 = group;
        IMPL.setTransitionGroup(group, z);
    }

    public static boolean isTransitionGroup(ViewGroup viewGroup) {
        ViewGroup group = viewGroup;
        ViewGroup viewGroup2 = group;
        return IMPL.isTransitionGroup(group);
    }

    public static int getNestedScrollAxes(ViewGroup viewGroup) {
        ViewGroup group = viewGroup;
        ViewGroup viewGroup2 = group;
        return IMPL.getNestedScrollAxes(group);
    }
}
