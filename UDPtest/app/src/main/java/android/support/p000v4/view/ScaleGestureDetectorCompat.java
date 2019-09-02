package android.support.p000v4.view;

import android.os.Build.VERSION;

/* renamed from: android.support.v4.view.ScaleGestureDetectorCompat */
public final class ScaleGestureDetectorCompat {
    static final ScaleGestureDetectorImpl IMPL;

    /* renamed from: android.support.v4.view.ScaleGestureDetectorCompat$BaseScaleGestureDetectorImpl */
    private static class BaseScaleGestureDetectorImpl implements ScaleGestureDetectorImpl {
        BaseScaleGestureDetectorImpl() {
        }

        public void setQuickScaleEnabled(Object obj, boolean z) {
            Object obj2 = obj;
            boolean z2 = z;
        }

        public boolean isQuickScaleEnabled(Object obj) {
            Object obj2 = obj;
            return false;
        }
    }

    /* renamed from: android.support.v4.view.ScaleGestureDetectorCompat$ScaleGestureDetectorCompatKitKatImpl */
    private static class ScaleGestureDetectorCompatKitKatImpl implements ScaleGestureDetectorImpl {
        ScaleGestureDetectorCompatKitKatImpl() {
        }

        public void setQuickScaleEnabled(Object obj, boolean z) {
            Object o = obj;
            Object obj2 = o;
            ScaleGestureDetectorCompatKitKat.setQuickScaleEnabled(o, z);
        }

        public boolean isQuickScaleEnabled(Object obj) {
            Object o = obj;
            Object obj2 = o;
            return ScaleGestureDetectorCompatKitKat.isQuickScaleEnabled(o);
        }
    }

    /* renamed from: android.support.v4.view.ScaleGestureDetectorCompat$ScaleGestureDetectorImpl */
    interface ScaleGestureDetectorImpl {
        boolean isQuickScaleEnabled(Object obj);

        void setQuickScaleEnabled(Object obj, boolean z);
    }

    static {
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 19) {
            IMPL = new BaseScaleGestureDetectorImpl();
        } else {
            IMPL = new ScaleGestureDetectorCompatKitKatImpl();
        }
    }

    private ScaleGestureDetectorCompat() {
    }

    public static void setQuickScaleEnabled(Object obj, boolean z) {
        Object scaleGestureDetector = obj;
        Object obj2 = scaleGestureDetector;
        IMPL.setQuickScaleEnabled(scaleGestureDetector, z);
    }

    public static boolean isQuickScaleEnabled(Object obj) {
        Object scaleGestureDetector = obj;
        Object obj2 = scaleGestureDetector;
        return IMPL.isQuickScaleEnabled(scaleGestureDetector);
    }
}
