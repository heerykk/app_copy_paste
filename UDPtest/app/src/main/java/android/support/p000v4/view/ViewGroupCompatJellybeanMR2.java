package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(18)
@RequiresApi(18)
/* renamed from: android.support.v4.view.ViewGroupCompatJellybeanMR2 */
class ViewGroupCompatJellybeanMR2 {
    ViewGroupCompatJellybeanMR2() {
    }

    public static int getLayoutMode(ViewGroup viewGroup) {
        ViewGroup group = viewGroup;
        ViewGroup viewGroup2 = group;
        return group.getLayoutMode();
    }

    public static void setLayoutMode(ViewGroup viewGroup, int i) {
        ViewGroup group = viewGroup;
        int mode = i;
        ViewGroup viewGroup2 = group;
        int i2 = mode;
        group.setLayoutMode(mode);
    }
}
