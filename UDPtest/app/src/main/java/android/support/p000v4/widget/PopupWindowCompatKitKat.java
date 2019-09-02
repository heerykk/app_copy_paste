package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.PopupWindow;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.widget.PopupWindowCompatKitKat */
class PopupWindowCompatKitKat {
    PopupWindowCompatKitKat() {
    }

    public static void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3) {
        PopupWindow popup = popupWindow;
        View anchor = view;
        int xoff = i;
        int yoff = i2;
        int gravity = i3;
        PopupWindow popupWindow2 = popup;
        View view2 = anchor;
        int i4 = xoff;
        int i5 = yoff;
        int i6 = gravity;
        popup.showAsDropDown(anchor, xoff, yoff, gravity);
    }
}
