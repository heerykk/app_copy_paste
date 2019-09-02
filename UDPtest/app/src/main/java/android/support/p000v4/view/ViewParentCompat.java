package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

/* renamed from: android.support.v4.view.ViewParentCompat */
public final class ViewParentCompat {
    static final ViewParentCompatImpl IMPL;

    /* renamed from: android.support.v4.view.ViewParentCompat$ViewParentCompatICSImpl */
    static class ViewParentCompatICSImpl extends ViewParentCompatStubImpl {
        ViewParentCompatICSImpl() {
        }

        public boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
            ViewParent parent = viewParent;
            View child = view;
            AccessibilityEvent event = accessibilityEvent;
            ViewParent viewParent2 = parent;
            View view2 = child;
            AccessibilityEvent accessibilityEvent2 = event;
            return ViewParentCompatICS.requestSendAccessibilityEvent(parent, child, event);
        }
    }

    /* renamed from: android.support.v4.view.ViewParentCompat$ViewParentCompatImpl */
    interface ViewParentCompatImpl {
        void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int i);

        boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean z);

        boolean onNestedPreFling(ViewParent viewParent, View view, float f, float f2);

        void onNestedPreScroll(ViewParent viewParent, View view, int i, int i2, int[] iArr);

        void onNestedScroll(ViewParent viewParent, View view, int i, int i2, int i3, int i4);

        void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int i);

        boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int i);

        void onStopNestedScroll(ViewParent viewParent, View view);

        boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent);
    }

    /* renamed from: android.support.v4.view.ViewParentCompat$ViewParentCompatKitKatImpl */
    static class ViewParentCompatKitKatImpl extends ViewParentCompatICSImpl {
        ViewParentCompatKitKatImpl() {
        }

        public void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int i) {
            ViewParent parent = viewParent;
            View child = view;
            View source = view2;
            int changeType = i;
            ViewParent viewParent2 = parent;
            View view3 = child;
            View view4 = source;
            int i2 = changeType;
            ViewParentCompatKitKat.notifySubtreeAccessibilityStateChanged(parent, child, source, changeType);
        }
    }

    /* renamed from: android.support.v4.view.ViewParentCompat$ViewParentCompatLollipopImpl */
    static class ViewParentCompatLollipopImpl extends ViewParentCompatKitKatImpl {
        ViewParentCompatLollipopImpl() {
        }

        public boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int i) {
            ViewParent parent = viewParent;
            View child = view;
            View target = view2;
            int nestedScrollAxes = i;
            ViewParent viewParent2 = parent;
            View view3 = child;
            View view4 = target;
            int i2 = nestedScrollAxes;
            return ViewParentCompatLollipop.onStartNestedScroll(parent, child, target, nestedScrollAxes);
        }

        public void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int i) {
            ViewParent parent = viewParent;
            View child = view;
            View target = view2;
            int nestedScrollAxes = i;
            ViewParent viewParent2 = parent;
            View view3 = child;
            View view4 = target;
            int i2 = nestedScrollAxes;
            ViewParentCompatLollipop.onNestedScrollAccepted(parent, child, target, nestedScrollAxes);
        }

        public void onStopNestedScroll(ViewParent viewParent, View view) {
            ViewParent parent = viewParent;
            View target = view;
            ViewParent viewParent2 = parent;
            View view2 = target;
            ViewParentCompatLollipop.onStopNestedScroll(parent, target);
        }

        public void onNestedScroll(ViewParent viewParent, View view, int i, int i2, int i3, int i4) {
            ViewParent parent = viewParent;
            View target = view;
            int dxConsumed = i;
            int dyConsumed = i2;
            int dxUnconsumed = i3;
            int dyUnconsumed = i4;
            ViewParent viewParent2 = parent;
            View view2 = target;
            int i5 = dxConsumed;
            int i6 = dyConsumed;
            int i7 = dxUnconsumed;
            int i8 = dyUnconsumed;
            ViewParentCompatLollipop.onNestedScroll(parent, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        }

        public void onNestedPreScroll(ViewParent viewParent, View view, int i, int i2, int[] iArr) {
            ViewParent parent = viewParent;
            View target = view;
            int dx = i;
            int dy = i2;
            int[] consumed = iArr;
            ViewParent viewParent2 = parent;
            View view2 = target;
            int i3 = dx;
            int i4 = dy;
            int[] iArr2 = consumed;
            ViewParentCompatLollipop.onNestedPreScroll(parent, target, dx, dy, consumed);
        }

        public boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean z) {
            ViewParent parent = viewParent;
            View target = view;
            float velocityX = f;
            float velocityY = f2;
            ViewParent viewParent2 = parent;
            View view2 = target;
            float f3 = velocityX;
            float f4 = velocityY;
            return ViewParentCompatLollipop.onNestedFling(parent, target, velocityX, velocityY, z);
        }

        public boolean onNestedPreFling(ViewParent viewParent, View view, float f, float f2) {
            ViewParent parent = viewParent;
            View target = view;
            float velocityX = f;
            float velocityY = f2;
            ViewParent viewParent2 = parent;
            View view2 = target;
            float f3 = velocityX;
            float f4 = velocityY;
            return ViewParentCompatLollipop.onNestedPreFling(parent, target, velocityX, velocityY);
        }
    }

    /* renamed from: android.support.v4.view.ViewParentCompat$ViewParentCompatStubImpl */
    static class ViewParentCompatStubImpl implements ViewParentCompatImpl {
        ViewParentCompatStubImpl() {
        }

        public boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
            View child = view;
            AccessibilityEvent event = accessibilityEvent;
            ViewParent viewParent2 = viewParent;
            View view2 = child;
            AccessibilityEvent accessibilityEvent2 = event;
            if (child == null) {
                return false;
            }
            AccessibilityManager accessibilityManager = (AccessibilityManager) child.getContext().getSystemService("accessibility");
            AccessibilityManager accessibilityManager2 = accessibilityManager;
            accessibilityManager.sendAccessibilityEvent(event);
            return true;
        }

        public boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int i) {
            ViewParent parent = viewParent;
            View child = view;
            View target = view2;
            int nestedScrollAxes = i;
            ViewParent viewParent2 = parent;
            View view3 = child;
            View view4 = target;
            int i2 = nestedScrollAxes;
            if (!(parent instanceof NestedScrollingParent)) {
                return false;
            }
            return ((NestedScrollingParent) parent).onStartNestedScroll(child, target, nestedScrollAxes);
        }

        public void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int i) {
            ViewParent parent = viewParent;
            View child = view;
            View target = view2;
            int nestedScrollAxes = i;
            ViewParent viewParent2 = parent;
            View view3 = child;
            View view4 = target;
            int i2 = nestedScrollAxes;
            if (parent instanceof NestedScrollingParent) {
                ((NestedScrollingParent) parent).onNestedScrollAccepted(child, target, nestedScrollAxes);
            }
        }

        public void onStopNestedScroll(ViewParent viewParent, View view) {
            ViewParent parent = viewParent;
            View target = view;
            ViewParent viewParent2 = parent;
            View view2 = target;
            if (parent instanceof NestedScrollingParent) {
                ((NestedScrollingParent) parent).onStopNestedScroll(target);
            }
        }

        public void onNestedScroll(ViewParent viewParent, View view, int i, int i2, int i3, int i4) {
            ViewParent parent = viewParent;
            View target = view;
            int dxConsumed = i;
            int dyConsumed = i2;
            int dxUnconsumed = i3;
            int dyUnconsumed = i4;
            ViewParent viewParent2 = parent;
            View view2 = target;
            int i5 = dxConsumed;
            int i6 = dyConsumed;
            int i7 = dxUnconsumed;
            int i8 = dyUnconsumed;
            if (parent instanceof NestedScrollingParent) {
                ((NestedScrollingParent) parent).onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
            }
        }

        public void onNestedPreScroll(ViewParent viewParent, View view, int i, int i2, int[] iArr) {
            ViewParent parent = viewParent;
            View target = view;
            int dx = i;
            int dy = i2;
            int[] consumed = iArr;
            ViewParent viewParent2 = parent;
            View view2 = target;
            int i3 = dx;
            int i4 = dy;
            int[] iArr2 = consumed;
            if (parent instanceof NestedScrollingParent) {
                ((NestedScrollingParent) parent).onNestedPreScroll(target, dx, dy, consumed);
            }
        }

        public boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean z) {
            ViewParent parent = viewParent;
            View target = view;
            float velocityX = f;
            float velocityY = f2;
            ViewParent viewParent2 = parent;
            View view2 = target;
            float f3 = velocityX;
            float f4 = velocityY;
            boolean consumed = z;
            if (!(parent instanceof NestedScrollingParent)) {
                return false;
            }
            return ((NestedScrollingParent) parent).onNestedFling(target, velocityX, velocityY, consumed);
        }

        public boolean onNestedPreFling(ViewParent viewParent, View view, float f, float f2) {
            ViewParent parent = viewParent;
            View target = view;
            float velocityX = f;
            float velocityY = f2;
            ViewParent viewParent2 = parent;
            View view2 = target;
            float f3 = velocityX;
            float f4 = velocityY;
            if (!(parent instanceof NestedScrollingParent)) {
                return false;
            }
            return ((NestedScrollingParent) parent).onNestedPreFling(target, velocityX, velocityY);
        }

        public void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int i) {
            ViewParent viewParent2 = viewParent;
            View view3 = view;
            View view4 = view2;
            int i2 = i;
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 21) {
            IMPL = new ViewParentCompatLollipopImpl();
        } else if (version >= 19) {
            IMPL = new ViewParentCompatKitKatImpl();
        } else if (version < 14) {
            IMPL = new ViewParentCompatStubImpl();
        } else {
            IMPL = new ViewParentCompatICSImpl();
        }
    }

    private ViewParentCompat() {
    }

    public static boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
        ViewParent parent = viewParent;
        View child = view;
        AccessibilityEvent event = accessibilityEvent;
        ViewParent viewParent2 = parent;
        View view2 = child;
        AccessibilityEvent accessibilityEvent2 = event;
        return IMPL.requestSendAccessibilityEvent(parent, child, event);
    }

    public static boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int i) {
        ViewParent parent = viewParent;
        View child = view;
        View target = view2;
        int nestedScrollAxes = i;
        ViewParent viewParent2 = parent;
        View view3 = child;
        View view4 = target;
        int i2 = nestedScrollAxes;
        return IMPL.onStartNestedScroll(parent, child, target, nestedScrollAxes);
    }

    public static void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int i) {
        ViewParent parent = viewParent;
        View child = view;
        View target = view2;
        int nestedScrollAxes = i;
        ViewParent viewParent2 = parent;
        View view3 = child;
        View view4 = target;
        int i2 = nestedScrollAxes;
        IMPL.onNestedScrollAccepted(parent, child, target, nestedScrollAxes);
    }

    public static void onStopNestedScroll(ViewParent viewParent, View view) {
        ViewParent parent = viewParent;
        View target = view;
        ViewParent viewParent2 = parent;
        View view2 = target;
        IMPL.onStopNestedScroll(parent, target);
    }

    public static void onNestedScroll(ViewParent viewParent, View view, int i, int i2, int i3, int i4) {
        ViewParent parent = viewParent;
        View target = view;
        int dxConsumed = i;
        int dyConsumed = i2;
        int dxUnconsumed = i3;
        int dyUnconsumed = i4;
        ViewParent viewParent2 = parent;
        View view2 = target;
        int i5 = dxConsumed;
        int i6 = dyConsumed;
        int i7 = dxUnconsumed;
        int i8 = dyUnconsumed;
        IMPL.onNestedScroll(parent, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    public static void onNestedPreScroll(ViewParent viewParent, View view, int i, int i2, int[] iArr) {
        ViewParent parent = viewParent;
        View target = view;
        int dx = i;
        int dy = i2;
        int[] consumed = iArr;
        ViewParent viewParent2 = parent;
        View view2 = target;
        int i3 = dx;
        int i4 = dy;
        int[] iArr2 = consumed;
        IMPL.onNestedPreScroll(parent, target, dx, dy, consumed);
    }

    public static boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean z) {
        ViewParent parent = viewParent;
        View target = view;
        float velocityX = f;
        float velocityY = f2;
        ViewParent viewParent2 = parent;
        View view2 = target;
        float f3 = velocityX;
        float f4 = velocityY;
        return IMPL.onNestedFling(parent, target, velocityX, velocityY, z);
    }

    public static boolean onNestedPreFling(ViewParent viewParent, View view, float f, float f2) {
        ViewParent parent = viewParent;
        View target = view;
        float velocityX = f;
        float velocityY = f2;
        ViewParent viewParent2 = parent;
        View view2 = target;
        float f3 = velocityX;
        float f4 = velocityY;
        return IMPL.onNestedPreFling(parent, target, velocityX, velocityY);
    }

    public static void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int i) {
        ViewParent parent = viewParent;
        View child = view;
        View source = view2;
        int changeType = i;
        ViewParent viewParent2 = parent;
        View view3 = child;
        View view4 = source;
        int i2 = changeType;
        IMPL.notifySubtreeAccessibilityStateChanged(parent, child, source, changeType);
    }
}
