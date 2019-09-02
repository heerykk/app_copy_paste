package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup.MarginLayoutParams;

@TargetApi(17)
@RequiresApi(17)
/* renamed from: android.support.v4.view.MarginLayoutParamsCompatJellybeanMr1 */
class MarginLayoutParamsCompatJellybeanMr1 {
    MarginLayoutParamsCompatJellybeanMr1() {
    }

    public static int getMarginStart(MarginLayoutParams marginLayoutParams) {
        MarginLayoutParams lp = marginLayoutParams;
        MarginLayoutParams marginLayoutParams2 = lp;
        return lp.getMarginStart();
    }

    public static int getMarginEnd(MarginLayoutParams marginLayoutParams) {
        MarginLayoutParams lp = marginLayoutParams;
        MarginLayoutParams marginLayoutParams2 = lp;
        return lp.getMarginEnd();
    }

    public static void setMarginStart(MarginLayoutParams marginLayoutParams, int i) {
        MarginLayoutParams lp = marginLayoutParams;
        int marginStart = i;
        MarginLayoutParams marginLayoutParams2 = lp;
        int i2 = marginStart;
        lp.setMarginStart(marginStart);
    }

    public static void setMarginEnd(MarginLayoutParams marginLayoutParams, int i) {
        MarginLayoutParams lp = marginLayoutParams;
        int marginEnd = i;
        MarginLayoutParams marginLayoutParams2 = lp;
        int i2 = marginEnd;
        lp.setMarginEnd(marginEnd);
    }

    public static boolean isMarginRelative(MarginLayoutParams marginLayoutParams) {
        MarginLayoutParams lp = marginLayoutParams;
        MarginLayoutParams marginLayoutParams2 = lp;
        return lp.isMarginRelative();
    }

    public static int getLayoutDirection(MarginLayoutParams marginLayoutParams) {
        MarginLayoutParams lp = marginLayoutParams;
        MarginLayoutParams marginLayoutParams2 = lp;
        return lp.getLayoutDirection();
    }

    public static void setLayoutDirection(MarginLayoutParams marginLayoutParams, int i) {
        MarginLayoutParams lp = marginLayoutParams;
        int layoutDirection = i;
        MarginLayoutParams marginLayoutParams2 = lp;
        int i2 = layoutDirection;
        lp.setLayoutDirection(layoutDirection);
    }

    public static void resolveLayoutDirection(MarginLayoutParams marginLayoutParams, int i) {
        MarginLayoutParams lp = marginLayoutParams;
        int layoutDirection = i;
        MarginLayoutParams marginLayoutParams2 = lp;
        int i2 = layoutDirection;
        lp.resolveLayoutDirection(layoutDirection);
    }
}
