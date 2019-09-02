package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.view.LayoutInflater;

/* renamed from: android.support.v4.view.LayoutInflaterCompat */
public final class LayoutInflaterCompat {
    static final LayoutInflaterCompatImpl IMPL;

    /* renamed from: android.support.v4.view.LayoutInflaterCompat$LayoutInflaterCompatImpl */
    interface LayoutInflaterCompatImpl {
        LayoutInflaterFactory getFactory(LayoutInflater layoutInflater);

        void setFactory(LayoutInflater layoutInflater, LayoutInflaterFactory layoutInflaterFactory);
    }

    /* renamed from: android.support.v4.view.LayoutInflaterCompat$LayoutInflaterCompatImplBase */
    static class LayoutInflaterCompatImplBase implements LayoutInflaterCompatImpl {
        LayoutInflaterCompatImplBase() {
        }

        public void setFactory(LayoutInflater layoutInflater, LayoutInflaterFactory layoutInflaterFactory) {
            LayoutInflater layoutInflater2 = layoutInflater;
            LayoutInflaterFactory factory = layoutInflaterFactory;
            LayoutInflater layoutInflater3 = layoutInflater2;
            LayoutInflaterFactory layoutInflaterFactory2 = factory;
            LayoutInflaterCompatBase.setFactory(layoutInflater2, factory);
        }

        public LayoutInflaterFactory getFactory(LayoutInflater layoutInflater) {
            LayoutInflater layoutInflater2 = layoutInflater;
            LayoutInflater layoutInflater3 = layoutInflater2;
            return LayoutInflaterCompatBase.getFactory(layoutInflater2);
        }
    }

    /* renamed from: android.support.v4.view.LayoutInflaterCompat$LayoutInflaterCompatImplV11 */
    static class LayoutInflaterCompatImplV11 extends LayoutInflaterCompatImplBase {
        LayoutInflaterCompatImplV11() {
        }

        public void setFactory(LayoutInflater layoutInflater, LayoutInflaterFactory layoutInflaterFactory) {
            LayoutInflater layoutInflater2 = layoutInflater;
            LayoutInflaterFactory factory = layoutInflaterFactory;
            LayoutInflater layoutInflater3 = layoutInflater2;
            LayoutInflaterFactory layoutInflaterFactory2 = factory;
            LayoutInflaterCompatHC.setFactory(layoutInflater2, factory);
        }
    }

    /* renamed from: android.support.v4.view.LayoutInflaterCompat$LayoutInflaterCompatImplV21 */
    static class LayoutInflaterCompatImplV21 extends LayoutInflaterCompatImplV11 {
        LayoutInflaterCompatImplV21() {
        }

        public void setFactory(LayoutInflater layoutInflater, LayoutInflaterFactory layoutInflaterFactory) {
            LayoutInflater layoutInflater2 = layoutInflater;
            LayoutInflaterFactory factory = layoutInflaterFactory;
            LayoutInflater layoutInflater3 = layoutInflater2;
            LayoutInflaterFactory layoutInflaterFactory2 = factory;
            LayoutInflaterCompatLollipop.setFactory(layoutInflater2, factory);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 21) {
            IMPL = new LayoutInflaterCompatImplV21();
        } else if (version < 11) {
            IMPL = new LayoutInflaterCompatImplBase();
        } else {
            IMPL = new LayoutInflaterCompatImplV11();
        }
    }

    private LayoutInflaterCompat() {
    }

    public static void setFactory(LayoutInflater layoutInflater, LayoutInflaterFactory layoutInflaterFactory) {
        LayoutInflater inflater = layoutInflater;
        LayoutInflaterFactory factory = layoutInflaterFactory;
        LayoutInflater layoutInflater2 = inflater;
        LayoutInflaterFactory layoutInflaterFactory2 = factory;
        IMPL.setFactory(inflater, factory);
    }

    public static LayoutInflaterFactory getFactory(LayoutInflater layoutInflater) {
        LayoutInflater inflater = layoutInflater;
        LayoutInflater layoutInflater2 = inflater;
        return IMPL.getFactory(inflater);
    }
}
