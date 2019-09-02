package android.support.p000v4.widget;

import android.os.Build.VERSION;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.ViewCompat;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

/* renamed from: android.support.v4.widget.PopupWindowCompat */
public final class PopupWindowCompat {
    static final PopupWindowImpl IMPL;

    /* renamed from: android.support.v4.widget.PopupWindowCompat$Api21PopupWindowImpl */
    static class Api21PopupWindowImpl extends KitKatPopupWindowImpl {
        Api21PopupWindowImpl() {
        }

        public void setOverlapAnchor(PopupWindow popupWindow, boolean z) {
            PopupWindow popupWindow2 = popupWindow;
            PopupWindow popupWindow3 = popupWindow2;
            PopupWindowCompatApi21.setOverlapAnchor(popupWindow2, z);
        }

        public boolean getOverlapAnchor(PopupWindow popupWindow) {
            PopupWindow popupWindow2 = popupWindow;
            PopupWindow popupWindow3 = popupWindow2;
            return PopupWindowCompatApi21.getOverlapAnchor(popupWindow2);
        }
    }

    /* renamed from: android.support.v4.widget.PopupWindowCompat$Api23PopupWindowImpl */
    static class Api23PopupWindowImpl extends Api21PopupWindowImpl {
        Api23PopupWindowImpl() {
        }

        public void setOverlapAnchor(PopupWindow popupWindow, boolean z) {
            PopupWindow popupWindow2 = popupWindow;
            PopupWindow popupWindow3 = popupWindow2;
            PopupWindowCompatApi23.setOverlapAnchor(popupWindow2, z);
        }

        public boolean getOverlapAnchor(PopupWindow popupWindow) {
            PopupWindow popupWindow2 = popupWindow;
            PopupWindow popupWindow3 = popupWindow2;
            return PopupWindowCompatApi23.getOverlapAnchor(popupWindow2);
        }

        public void setWindowLayoutType(PopupWindow popupWindow, int i) {
            PopupWindow popupWindow2 = popupWindow;
            int layoutType = i;
            PopupWindow popupWindow3 = popupWindow2;
            int i2 = layoutType;
            PopupWindowCompatApi23.setWindowLayoutType(popupWindow2, layoutType);
        }

        public int getWindowLayoutType(PopupWindow popupWindow) {
            PopupWindow popupWindow2 = popupWindow;
            PopupWindow popupWindow3 = popupWindow2;
            return PopupWindowCompatApi23.getWindowLayoutType(popupWindow2);
        }
    }

    /* renamed from: android.support.v4.widget.PopupWindowCompat$BasePopupWindowImpl */
    static class BasePopupWindowImpl implements PopupWindowImpl {
        private static Method sGetWindowLayoutTypeMethod;
        private static boolean sGetWindowLayoutTypeMethodAttempted;
        private static Method sSetWindowLayoutTypeMethod;
        private static boolean sSetWindowLayoutTypeMethodAttempted;

        BasePopupWindowImpl() {
        }

        public void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3) {
            PopupWindow popup = popupWindow;
            View anchor = view;
            int xoff = i;
            int yoff = i2;
            int gravity = i3;
            PopupWindow popupWindow2 = popup;
            View view2 = anchor;
            int xoff2 = xoff;
            int i4 = yoff;
            int i5 = gravity;
            int absoluteGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(anchor)) & 7;
            int i6 = absoluteGravity;
            if (absoluteGravity == 5) {
                xoff2 = xoff - (popup.getWidth() - anchor.getWidth());
            }
            popup.showAsDropDown(anchor, xoff2, yoff);
        }

        public void setOverlapAnchor(PopupWindow popupWindow, boolean z) {
            PopupWindow popupWindow2 = popupWindow;
            boolean z2 = z;
        }

        public boolean getOverlapAnchor(PopupWindow popupWindow) {
            PopupWindow popupWindow2 = popupWindow;
            return false;
        }

        public void setWindowLayoutType(PopupWindow popupWindow, int i) {
            PopupWindow popupWindow2 = popupWindow;
            int layoutType = i;
            PopupWindow popupWindow3 = popupWindow2;
            int i2 = layoutType;
            if (!sSetWindowLayoutTypeMethodAttempted) {
                Class<PopupWindow> cls = PopupWindow.class;
                String str = "setWindowLayoutType";
                try {
                    Class[] clsArr = new Class[1];
                    clsArr[0] = Integer.TYPE;
                    sSetWindowLayoutTypeMethod = cls.getDeclaredMethod(str, clsArr);
                    sSetWindowLayoutTypeMethod.setAccessible(true);
                } catch (Exception e) {
                }
                sSetWindowLayoutTypeMethodAttempted = true;
            }
            if (sSetWindowLayoutTypeMethod != null) {
                try {
                    Method method = sSetWindowLayoutTypeMethod;
                    Object[] objArr = new Object[1];
                    objArr[0] = Integer.valueOf(layoutType);
                    Object invoke = method.invoke(popupWindow2, objArr);
                } catch (Exception e2) {
                }
            }
        }

        public int getWindowLayoutType(PopupWindow popupWindow) {
            PopupWindow popupWindow2 = popupWindow;
            PopupWindow popupWindow3 = popupWindow2;
            if (!sGetWindowLayoutTypeMethodAttempted) {
                try {
                    sGetWindowLayoutTypeMethod = PopupWindow.class.getDeclaredMethod("getWindowLayoutType", new Class[0]);
                    sGetWindowLayoutTypeMethod.setAccessible(true);
                } catch (Exception e) {
                }
                sGetWindowLayoutTypeMethodAttempted = true;
            }
            if (sGetWindowLayoutTypeMethod != null) {
                try {
                    return ((Integer) sGetWindowLayoutTypeMethod.invoke(popupWindow2, new Object[0])).intValue();
                } catch (Exception e2) {
                }
            }
            return 0;
        }
    }

    /* renamed from: android.support.v4.widget.PopupWindowCompat$KitKatPopupWindowImpl */
    static class KitKatPopupWindowImpl extends BasePopupWindowImpl {
        KitKatPopupWindowImpl() {
        }

        public void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3) {
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
            PopupWindowCompatKitKat.showAsDropDown(popup, anchor, xoff, yoff, gravity);
        }
    }

    /* renamed from: android.support.v4.widget.PopupWindowCompat$PopupWindowImpl */
    interface PopupWindowImpl {
        boolean getOverlapAnchor(PopupWindow popupWindow);

        int getWindowLayoutType(PopupWindow popupWindow);

        void setOverlapAnchor(PopupWindow popupWindow, boolean z);

        void setWindowLayoutType(PopupWindow popupWindow, int i);

        void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3);
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 23) {
            IMPL = new Api23PopupWindowImpl();
        } else if (version >= 21) {
            IMPL = new Api21PopupWindowImpl();
        } else if (version < 19) {
            IMPL = new BasePopupWindowImpl();
        } else {
            IMPL = new KitKatPopupWindowImpl();
        }
    }

    private PopupWindowCompat() {
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
        IMPL.showAsDropDown(popup, anchor, xoff, yoff, gravity);
    }

    public static void setOverlapAnchor(PopupWindow popupWindow, boolean z) {
        PopupWindow popupWindow2 = popupWindow;
        PopupWindow popupWindow3 = popupWindow2;
        IMPL.setOverlapAnchor(popupWindow2, z);
    }

    public static boolean getOverlapAnchor(PopupWindow popupWindow) {
        PopupWindow popupWindow2 = popupWindow;
        PopupWindow popupWindow3 = popupWindow2;
        return IMPL.getOverlapAnchor(popupWindow2);
    }

    public static void setWindowLayoutType(PopupWindow popupWindow, int i) {
        PopupWindow popupWindow2 = popupWindow;
        int layoutType = i;
        PopupWindow popupWindow3 = popupWindow2;
        int i2 = layoutType;
        IMPL.setWindowLayoutType(popupWindow2, layoutType);
    }

    public static int getWindowLayoutType(PopupWindow popupWindow) {
        PopupWindow popupWindow2 = popupWindow;
        PopupWindow popupWindow3 = popupWindow2;
        return IMPL.getWindowLayoutType(popupWindow2);
    }
}
