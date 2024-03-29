package android.support.p000v4.widget;

import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnTouchListener;

/* renamed from: android.support.v4.widget.ListPopupWindowCompat */
public final class ListPopupWindowCompat {
    static final ListPopupWindowImpl IMPL;

    /* renamed from: android.support.v4.widget.ListPopupWindowCompat$BaseListPopupWindowImpl */
    static class BaseListPopupWindowImpl implements ListPopupWindowImpl {
        BaseListPopupWindowImpl() {
        }

        public OnTouchListener createDragToOpenListener(Object obj, View view) {
            Object obj2 = obj;
            View view2 = view;
            return null;
        }
    }

    /* renamed from: android.support.v4.widget.ListPopupWindowCompat$KitKatListPopupWindowImpl */
    static class KitKatListPopupWindowImpl extends BaseListPopupWindowImpl {
        KitKatListPopupWindowImpl() {
        }

        public OnTouchListener createDragToOpenListener(Object obj, View view) {
            Object listPopupWindow = obj;
            View src = view;
            Object obj2 = listPopupWindow;
            View view2 = src;
            return ListPopupWindowCompatKitKat.createDragToOpenListener(listPopupWindow, src);
        }
    }

    /* renamed from: android.support.v4.widget.ListPopupWindowCompat$ListPopupWindowImpl */
    interface ListPopupWindowImpl {
        OnTouchListener createDragToOpenListener(Object obj, View view);
    }

    static {
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 19) {
            IMPL = new BaseListPopupWindowImpl();
        } else {
            IMPL = new KitKatListPopupWindowImpl();
        }
    }

    private ListPopupWindowCompat() {
    }

    public static OnTouchListener createDragToOpenListener(Object obj, View view) {
        Object listPopupWindow = obj;
        View src = view;
        Object obj2 = listPopupWindow;
        View view2 = src;
        return IMPL.createDragToOpenListener(listPopupWindow, src);
    }
}
