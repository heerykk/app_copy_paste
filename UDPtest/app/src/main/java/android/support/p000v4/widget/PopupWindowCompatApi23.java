package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.widget.PopupWindow;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.widget.PopupWindowCompatApi23 */
class PopupWindowCompatApi23 {
    PopupWindowCompatApi23() {
    }

    static void setOverlapAnchor(PopupWindow popupWindow, boolean z) {
        PopupWindow popupWindow2 = popupWindow;
        PopupWindow popupWindow3 = popupWindow2;
        popupWindow2.setOverlapAnchor(z);
    }

    static boolean getOverlapAnchor(PopupWindow popupWindow) {
        PopupWindow popupWindow2 = popupWindow;
        PopupWindow popupWindow3 = popupWindow2;
        return popupWindow2.getOverlapAnchor();
    }

    static void setWindowLayoutType(PopupWindow popupWindow, int i) {
        PopupWindow popupWindow2 = popupWindow;
        int layoutType = i;
        PopupWindow popupWindow3 = popupWindow2;
        int i2 = layoutType;
        popupWindow2.setWindowLayoutType(layoutType);
    }

    static int getWindowLayoutType(PopupWindow popupWindow) {
        PopupWindow popupWindow2 = popupWindow;
        PopupWindow popupWindow3 = popupWindow2;
        return popupWindow2.getWindowLayoutType();
    }
}
