package android.support.p000v4.view.animation;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.support.annotation.RequiresApi;
import android.view.animation.Interpolator;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.view.animation.PathInterpolatorCompatBase */
class PathInterpolatorCompatBase {
    private PathInterpolatorCompatBase() {
    }

    public static Interpolator create(Path path) {
        Path path2 = path;
        Path path3 = path2;
        return new PathInterpolatorGingerbread(path2);
    }

    public static Interpolator create(float f, float f2) {
        float controlX = f;
        float controlY = f2;
        float f3 = controlX;
        float f4 = controlY;
        return new PathInterpolatorGingerbread(controlX, controlY);
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
        return new PathInterpolatorGingerbread(controlX1, controlY1, controlX2, controlY2);
    }
}
