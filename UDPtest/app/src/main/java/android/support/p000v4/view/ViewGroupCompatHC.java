package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.view.ViewGroupCompatHC */
class ViewGroupCompatHC {
    private ViewGroupCompatHC() {
    }

    public static void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean z) {
        ViewGroup group = viewGroup;
        ViewGroup viewGroup2 = group;
        group.setMotionEventSplittingEnabled(z);
    }
}
