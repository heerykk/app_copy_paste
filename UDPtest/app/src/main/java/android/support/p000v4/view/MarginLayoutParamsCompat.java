package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.view.ViewGroup.MarginLayoutParams;

/* renamed from: android.support.v4.view.MarginLayoutParamsCompat */
public final class MarginLayoutParamsCompat {
    static final MarginLayoutParamsCompatImpl IMPL;

    /* renamed from: android.support.v4.view.MarginLayoutParamsCompat$MarginLayoutParamsCompatImpl */
    interface MarginLayoutParamsCompatImpl {
        int getLayoutDirection(MarginLayoutParams marginLayoutParams);

        int getMarginEnd(MarginLayoutParams marginLayoutParams);

        int getMarginStart(MarginLayoutParams marginLayoutParams);

        boolean isMarginRelative(MarginLayoutParams marginLayoutParams);

        void resolveLayoutDirection(MarginLayoutParams marginLayoutParams, int i);

        void setLayoutDirection(MarginLayoutParams marginLayoutParams, int i);

        void setMarginEnd(MarginLayoutParams marginLayoutParams, int i);

        void setMarginStart(MarginLayoutParams marginLayoutParams, int i);
    }

    /* renamed from: android.support.v4.view.MarginLayoutParamsCompat$MarginLayoutParamsCompatImplBase */
    static class MarginLayoutParamsCompatImplBase implements MarginLayoutParamsCompatImpl {
        MarginLayoutParamsCompatImplBase() {
        }

        public int getMarginStart(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams lp = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = lp;
            return lp.leftMargin;
        }

        public int getMarginEnd(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams lp = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = lp;
            return lp.rightMargin;
        }

        public void setMarginStart(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParams lp = marginLayoutParams;
            int marginStart = i;
            MarginLayoutParams marginLayoutParams2 = lp;
            int i2 = marginStart;
            lp.leftMargin = marginStart;
        }

        public void setMarginEnd(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParams lp = marginLayoutParams;
            int marginEnd = i;
            MarginLayoutParams marginLayoutParams2 = lp;
            int i2 = marginEnd;
            lp.rightMargin = marginEnd;
        }

        public boolean isMarginRelative(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams marginLayoutParams2 = marginLayoutParams;
            return false;
        }

        public int getLayoutDirection(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams marginLayoutParams2 = marginLayoutParams;
            return 0;
        }

        public void setLayoutDirection(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParams marginLayoutParams2 = marginLayoutParams;
            int i2 = i;
        }

        public void resolveLayoutDirection(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParams marginLayoutParams2 = marginLayoutParams;
            int i2 = i;
        }
    }

    /* renamed from: android.support.v4.view.MarginLayoutParamsCompat$MarginLayoutParamsCompatImplJbMr1 */
    static class MarginLayoutParamsCompatImplJbMr1 implements MarginLayoutParamsCompatImpl {
        MarginLayoutParamsCompatImplJbMr1() {
        }

        public int getMarginStart(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams lp = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = lp;
            return MarginLayoutParamsCompatJellybeanMr1.getMarginStart(lp);
        }

        public int getMarginEnd(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams lp = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = lp;
            return MarginLayoutParamsCompatJellybeanMr1.getMarginEnd(lp);
        }

        public void setMarginStart(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParams lp = marginLayoutParams;
            int marginStart = i;
            MarginLayoutParams marginLayoutParams2 = lp;
            int i2 = marginStart;
            MarginLayoutParamsCompatJellybeanMr1.setMarginStart(lp, marginStart);
        }

        public void setMarginEnd(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParams lp = marginLayoutParams;
            int marginEnd = i;
            MarginLayoutParams marginLayoutParams2 = lp;
            int i2 = marginEnd;
            MarginLayoutParamsCompatJellybeanMr1.setMarginEnd(lp, marginEnd);
        }

        public boolean isMarginRelative(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams lp = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = lp;
            return MarginLayoutParamsCompatJellybeanMr1.isMarginRelative(lp);
        }

        public int getLayoutDirection(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams lp = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = lp;
            return MarginLayoutParamsCompatJellybeanMr1.getLayoutDirection(lp);
        }

        public void setLayoutDirection(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParams lp = marginLayoutParams;
            int layoutDirection = i;
            MarginLayoutParams marginLayoutParams2 = lp;
            int i2 = layoutDirection;
            MarginLayoutParamsCompatJellybeanMr1.setLayoutDirection(lp, layoutDirection);
        }

        public void resolveLayoutDirection(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParams lp = marginLayoutParams;
            int layoutDirection = i;
            MarginLayoutParams marginLayoutParams2 = lp;
            int i2 = layoutDirection;
            MarginLayoutParamsCompatJellybeanMr1.resolveLayoutDirection(lp, layoutDirection);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 17) {
            IMPL = new MarginLayoutParamsCompatImplBase();
        } else {
            IMPL = new MarginLayoutParamsCompatImplJbMr1();
        }
    }

    public static int getMarginStart(MarginLayoutParams marginLayoutParams) {
        MarginLayoutParams lp = marginLayoutParams;
        MarginLayoutParams marginLayoutParams2 = lp;
        return IMPL.getMarginStart(lp);
    }

    public static int getMarginEnd(MarginLayoutParams marginLayoutParams) {
        MarginLayoutParams lp = marginLayoutParams;
        MarginLayoutParams marginLayoutParams2 = lp;
        return IMPL.getMarginEnd(lp);
    }

    public static void setMarginStart(MarginLayoutParams marginLayoutParams, int i) {
        MarginLayoutParams lp = marginLayoutParams;
        int marginStart = i;
        MarginLayoutParams marginLayoutParams2 = lp;
        int i2 = marginStart;
        IMPL.setMarginStart(lp, marginStart);
    }

    public static void setMarginEnd(MarginLayoutParams marginLayoutParams, int i) {
        MarginLayoutParams lp = marginLayoutParams;
        int marginEnd = i;
        MarginLayoutParams marginLayoutParams2 = lp;
        int i2 = marginEnd;
        IMPL.setMarginEnd(lp, marginEnd);
    }

    public static boolean isMarginRelative(MarginLayoutParams marginLayoutParams) {
        MarginLayoutParams lp = marginLayoutParams;
        MarginLayoutParams marginLayoutParams2 = lp;
        return IMPL.isMarginRelative(lp);
    }

    public static int getLayoutDirection(MarginLayoutParams marginLayoutParams) {
        MarginLayoutParams lp = marginLayoutParams;
        MarginLayoutParams marginLayoutParams2 = lp;
        int layoutDirection = IMPL.getLayoutDirection(lp);
        int result = layoutDirection;
        if (!(layoutDirection == 0 || result == 1)) {
            result = 0;
        }
        return result;
    }

    public static void setLayoutDirection(MarginLayoutParams marginLayoutParams, int i) {
        MarginLayoutParams lp = marginLayoutParams;
        int layoutDirection = i;
        MarginLayoutParams marginLayoutParams2 = lp;
        int i2 = layoutDirection;
        IMPL.setLayoutDirection(lp, layoutDirection);
    }

    public static void resolveLayoutDirection(MarginLayoutParams marginLayoutParams, int i) {
        MarginLayoutParams lp = marginLayoutParams;
        int layoutDirection = i;
        MarginLayoutParams marginLayoutParams2 = lp;
        int i2 = layoutDirection;
        IMPL.resolveLayoutDirection(lp, layoutDirection);
    }

    private MarginLayoutParamsCompat() {
    }
}
