package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View.OnTouchListener;
import android.widget.PopupMenu;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.widget.PopupMenuCompatKitKat */
class PopupMenuCompatKitKat {
    PopupMenuCompatKitKat() {
    }

    public static OnTouchListener getDragToOpenListener(Object obj) {
        Object popupMenu = obj;
        Object obj2 = popupMenu;
        return ((PopupMenu) popupMenu).getDragToOpenListener();
    }
}
