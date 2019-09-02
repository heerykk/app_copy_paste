package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.View;

@TargetApi(18)
@RequiresApi(18)
/* renamed from: android.support.v4.view.ViewCompatJellybeanMr2 */
class ViewCompatJellybeanMr2 {
    ViewCompatJellybeanMr2() {
    }

    public static Rect getClipBounds(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getClipBounds();
    }

    public static void setClipBounds(View view, Rect rect) {
        View view2 = view;
        Rect clipBounds = rect;
        View view3 = view2;
        Rect rect2 = clipBounds;
        view2.setClipBounds(clipBounds);
    }

    public static boolean isInLayout(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.isInLayout();
    }
}
