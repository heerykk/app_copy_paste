package android.support.p000v4.widget;

import android.os.Build.VERSION;
import android.view.View.OnTouchListener;

/* renamed from: android.support.v4.widget.PopupMenuCompat */
public final class PopupMenuCompat {
    static final PopupMenuImpl IMPL;

    /* renamed from: android.support.v4.widget.PopupMenuCompat$BasePopupMenuImpl */
    static class BasePopupMenuImpl implements PopupMenuImpl {
        BasePopupMenuImpl() {
        }

        public OnTouchListener getDragToOpenListener(Object obj) {
            Object obj2 = obj;
            return null;
        }
    }

    /* renamed from: android.support.v4.widget.PopupMenuCompat$KitKatPopupMenuImpl */
    static class KitKatPopupMenuImpl extends BasePopupMenuImpl {
        KitKatPopupMenuImpl() {
        }

        public OnTouchListener getDragToOpenListener(Object obj) {
            Object popupMenu = obj;
            Object obj2 = popupMenu;
            return PopupMenuCompatKitKat.getDragToOpenListener(popupMenu);
        }
    }

    /* renamed from: android.support.v4.widget.PopupMenuCompat$PopupMenuImpl */
    interface PopupMenuImpl {
        OnTouchListener getDragToOpenListener(Object obj);
    }

    static {
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 19) {
            IMPL = new BasePopupMenuImpl();
        } else {
            IMPL = new KitKatPopupMenuImpl();
        }
    }

    private PopupMenuCompat() {
    }

    public static OnTouchListener getDragToOpenListener(Object obj) {
        Object popupMenu = obj;
        Object obj2 = popupMenu;
        return IMPL.getDragToOpenListener(popupMenu);
    }
}
