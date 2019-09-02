package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.PointerIcon;
import android.view.View;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.view.ViewCompatApi24 */
class ViewCompatApi24 {
    ViewCompatApi24() {
    }

    public static void setPointerIcon(View view, Object obj) {
        View view2 = view;
        Object pointerIcon = obj;
        View view3 = view2;
        Object obj2 = pointerIcon;
        view2.setPointerIcon((PointerIcon) pointerIcon);
    }
}
