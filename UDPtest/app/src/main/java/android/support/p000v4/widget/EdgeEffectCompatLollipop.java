package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.widget.EdgeEffect;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.widget.EdgeEffectCompatLollipop */
class EdgeEffectCompatLollipop {
    EdgeEffectCompatLollipop() {
    }

    public static boolean onPull(Object obj, float f, float f2) {
        Object edgeEffect = obj;
        float deltaDistance = f;
        float displacement = f2;
        Object obj2 = edgeEffect;
        float f3 = deltaDistance;
        float f4 = displacement;
        ((EdgeEffect) edgeEffect).onPull(deltaDistance, displacement);
        return true;
    }
}
