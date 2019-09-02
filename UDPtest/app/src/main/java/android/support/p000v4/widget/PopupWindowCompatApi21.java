package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.PopupWindow;
import java.lang.reflect.Field;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.widget.PopupWindowCompatApi21 */
class PopupWindowCompatApi21 {
    private static final String TAG = "PopupWindowCompatApi21";
    private static Field sOverlapAnchorField;

    PopupWindowCompatApi21() {
    }

    static {
        try {
            sOverlapAnchorField = PopupWindow.class.getDeclaredField("mOverlapAnchor");
            sOverlapAnchorField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            NoSuchFieldException noSuchFieldException = e;
            int i = Log.i(TAG, "Could not fetch mOverlapAnchor field from PopupWindow", e);
        }
    }

    static void setOverlapAnchor(PopupWindow popupWindow, boolean z) {
        PopupWindow popupWindow2 = popupWindow;
        PopupWindow popupWindow3 = popupWindow2;
        boolean overlapAnchor = z;
        if (sOverlapAnchorField != null) {
            try {
                sOverlapAnchorField.set(popupWindow2, Boolean.valueOf(overlapAnchor));
            } catch (IllegalAccessException e) {
                IllegalAccessException illegalAccessException = e;
                int i = Log.i(TAG, "Could not set overlap anchor field in PopupWindow", e);
            }
        }
    }

    static boolean getOverlapAnchor(PopupWindow popupWindow) {
        PopupWindow popupWindow2 = popupWindow;
        PopupWindow popupWindow3 = popupWindow2;
        if (sOverlapAnchorField != null) {
            try {
                return ((Boolean) sOverlapAnchorField.get(popupWindow2)).booleanValue();
            } catch (IllegalAccessException e) {
                IllegalAccessException illegalAccessException = e;
                int i = Log.i(TAG, "Could not get overlap anchor field in PopupWindow", e);
            }
        }
        return false;
    }
}
