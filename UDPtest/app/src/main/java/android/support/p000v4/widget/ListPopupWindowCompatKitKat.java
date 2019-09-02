package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListPopupWindow;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.widget.ListPopupWindowCompatKitKat */
class ListPopupWindowCompatKitKat {
    ListPopupWindowCompatKitKat() {
    }

    public static OnTouchListener createDragToOpenListener(Object obj, View view) {
        Object listPopupWindow = obj;
        View src = view;
        Object obj2 = listPopupWindow;
        View view2 = src;
        return ((ListPopupWindow) listPopupWindow).createDragToOpenListener(src);
    }
}
