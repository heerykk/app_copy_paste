package android.support.p000v4.hardware.display;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.support.annotation.RequiresApi;
import android.view.Display;

@TargetApi(17)
@RequiresApi(17)
/* renamed from: android.support.v4.hardware.display.DisplayManagerJellybeanMr1 */
final class DisplayManagerJellybeanMr1 {
    DisplayManagerJellybeanMr1() {
    }

    public static Object getDisplayManager(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.getSystemService("display");
    }

    public static Display getDisplay(Object obj, int i) {
        Object displayManagerObj = obj;
        int displayId = i;
        Object obj2 = displayManagerObj;
        int i2 = displayId;
        return ((DisplayManager) displayManagerObj).getDisplay(displayId);
    }

    public static Display[] getDisplays(Object obj) {
        Object displayManagerObj = obj;
        Object obj2 = displayManagerObj;
        return ((DisplayManager) displayManagerObj).getDisplays();
    }

    public static Display[] getDisplays(Object obj, String str) {
        Object displayManagerObj = obj;
        String category = str;
        Object obj2 = displayManagerObj;
        String str2 = category;
        return ((DisplayManager) displayManagerObj).getDisplays(category);
    }
}
