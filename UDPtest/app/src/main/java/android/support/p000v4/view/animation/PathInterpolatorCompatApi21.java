package android.support.p000v4.view.animation;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.support.annotation.RequiresApi;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.view.animation.PathInterpolatorCompatApi21 */
class PathInterpolatorCompatApi21 {
    private PathInterpolatorCompatApi21() {
    }

    public static Interpolator create(Path path) {
        Path path2 = path;
        Path path3 = path2;
        return new PathInterpolator(path2);
    }

    public static Interpolator create(float f, float f2) {
        float controlX = f;
        float controlY = f2;
        float f3 = controlX;
        float f4 = controlY;
        return new PathInterpolator(controlX, controlY);
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
        return new PathInterpolator(controlX1, controlY1, controlX2, controlY2);
    }
}
