package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.view.ViewCompatMarshmallow */
class ViewCompatMarshmallow {
    ViewCompatMarshmallow() {
    }

    public static void setScrollIndicators(View view, int i) {
        View view2 = view;
        int indicators = i;
        View view3 = view2;
        int i2 = indicators;
        view2.setScrollIndicators(indicators);
    }

    public static void setScrollIndicators(View view, int i, int i2) {
        View view2 = view;
        int indicators = i;
        int mask = i2;
        View view3 = view2;
        int i3 = indicators;
        int i4 = mask;
        view2.setScrollIndicators(indicators, mask);
    }

    public static int getScrollIndicators(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getScrollIndicators();
    }

    static void offsetTopAndBottom(View view, int i) {
        View view2 = view;
        int offset = i;
        View view3 = view2;
        int i2 = offset;
        view2.offsetTopAndBottom(offset);
    }

    static void offsetLeftAndRight(View view, int i) {
        View view2 = view;
        int offset = i;
        View view3 = view2;
        int i2 = offset;
        view2.offsetLeftAndRight(offset);
    }
}
