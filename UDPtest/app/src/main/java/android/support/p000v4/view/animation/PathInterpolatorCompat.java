package android.support.p000v4.view.animation;

import android.graphics.Path;
import android.os.Build.VERSION;
import android.view.animation.Interpolator;

/* renamed from: android.support.v4.view.animation.PathInterpolatorCompat */
public final class PathInterpolatorCompat {
    private PathInterpolatorCompat() {
    }

    public static Interpolator create(Path path) {
        Path path2 = path;
        Path path3 = path2;
        if (VERSION.SDK_INT < 21) {
            return PathInterpolatorCompatBase.create(path2);
        }
        return PathInterpolatorCompatApi21.create(path2);
    }

    public static Interpolator create(float f, float f2) {
        float controlX = f;
        float controlY = f2;
        float f3 = controlX;
        float f4 = controlY;
        if (VERSION.SDK_INT < 21) {
            return PathInterpolatorCompatBase.create(controlX, controlY);
        }
        return PathInterpolatorCompatApi21.create(controlX, controlY);
    }

    public static Interpolator create(float f, float f2, float f3, float f4) {
        float controlX1 = f;
        float controlY1 = f2;
        float controlX2 = f3;
        float controlY2 = f4;
        float f5 = controlX1;
        float f6 = controlY1;
        float f7 = controlX2;
        float f8 = controlY2;
        if (VERSION.SDK_INT < 21) {
            return PathInterpolatorCompatBase.create(controlX1, controlY1, controlX2, controlY2);
        }
        return PathInterpolatorCompatApi21.create(controlX1, controlY1, controlX2, controlY2);
    }
}
