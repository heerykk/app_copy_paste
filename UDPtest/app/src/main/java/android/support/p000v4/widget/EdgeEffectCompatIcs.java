package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.RequiresApi;
import android.widget.EdgeEffect;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.widget.EdgeEffectCompatIcs */
class EdgeEffectCompatIcs {
    EdgeEffectCompatIcs() {
    }

    public static Object newEdgeEffect(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return new EdgeEffect(context2);
    }

    public static void setSize(Object obj, int i, int i2) {
        Object edgeEffect = obj;
        int width = i;
        int height = i2;
        Object obj2 = edgeEffect;
        int i3 = width;
        int i4 = height;
        ((EdgeEffect) edgeEffect).setSize(width, height);
    }

    public static boolean isFinished(Object obj) {
        Object edgeEffect = obj;
        Object obj2 = edgeEffect;
        return ((EdgeEffect) edgeEffect).isFinished();
    }

    public static void finish(Object obj) {
        Object edgeEffect = obj;
        Object obj2 = edgeEffect;
        ((EdgeEffect) edgeEffect).finish();
    }

    public static boolean onPull(Object obj, float f) {
        Object edgeEffect = obj;
        float deltaDistance = f;
        Object obj2 = edgeEffect;
        float f2 = deltaDistance;
        ((EdgeEffect) edgeEffect).onPull(deltaDistance);
        return true;
    }

    public static boolean onRelease(Object obj) {
        Object edgeEffect = obj;
        Object obj2 = edgeEffect;
        EdgeEffect edgeEffect2 = (EdgeEffect) edgeEffect;
        EdgeEffect eff = edgeEffect2;
        edgeEffect2.onRelease();
        return eff.isFinished();
    }

    public static boolean onAbsorb(Object obj, int i) {
        Object edgeEffect = obj;
        int velocity = i;
        Object obj2 = edgeEffect;
        int i2 = velocity;
        ((EdgeEffect) edgeEffect).onAbsorb(velocity);
        return true;
    }

    public static boolean draw(Object obj, Canvas canvas) {
        Object edgeEffect = obj;
        Canvas canvas2 = canvas;
        Object obj2 = edgeEffect;
        Canvas canvas3 = canvas2;
        return ((EdgeEffect) edgeEffect).draw(canvas2);
    }
}
