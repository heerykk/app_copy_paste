package android.support.p000v4.view.animation;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.RequiresApi;
import android.view.animation.Interpolator;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.view.animation.PathInterpolatorGingerbread */
class PathInterpolatorGingerbread implements Interpolator {
    private static final float PRECISION = 0.002f;

    /* renamed from: mX */
    private final float[] f14mX;

    /* renamed from: mY */
    private final float[] f15mY;

    public PathInterpolatorGingerbread(Path path) {
        Path path2 = path;
        Path path3 = path2;
        PathMeasure pathMeasure = new PathMeasure(path2, false);
        PathMeasure pathMeasure2 = pathMeasure;
        float length = pathMeasure.getLength();
        float pathLength = length;
        int i = ((int) (length / PRECISION)) + 1;
        int numPoints = i;
        this.f14mX = new float[i];
        this.f15mY = new float[i];
        float[] position = new float[2];
        for (int i2 = 0; i2 < numPoints; i2++) {
            float f = (((float) i2) * pathLength) / ((float) (numPoints - 1));
            float f2 = f;
            boolean posTan = pathMeasure2.getPosTan(f, position, null);
            this.f14mX[i2] = position[0];
            this.f15mY[i2] = position[1];
        }
    }

    public PathInterpolatorGingerbread(float f, float f2) {
        float controlX = f;
        float controlY = f2;
        float f3 = controlX;
        float f4 = controlY;
        this(createQuad(controlX, controlY));
    }

    public PathInterpolatorGingerbread(float f, float f2, float f3, float f4) {
        float controlX1 = f;
        float controlY1 = f2;
        float controlX2 = f3;
        float controlY2 = f4;
        float f5 = controlX1;
        float f6 = controlY1;
        float f7 = controlX2;
        float f8 = controlY2;
        this(createCubic(controlX1, controlY1, controlX2, controlY2));
    }

    public float getInterpolation(float f) {
        float t = f;
        float f2 = t;
        if (t <= 0.0f) {
            return 0.0f;
        }
        if (t >= 1.0f) {
            return 1.0f;
        }
        int startIndex = 0;
        int endIndex = this.f14mX.length - 1;
        while (endIndex - startIndex > 1) {
            int i = (startIndex + endIndex) / 2;
            int i2 = i;
            if (t < this.f14mX[i]) {
                endIndex = i;
            } else {
                startIndex = i;
            }
        }
        float f3 = this.f14mX[endIndex] - this.f14mX[startIndex];
        float xRange = f3;
        if (f3 == 0.0f) {
            return this.f15mY[startIndex];
        }
        float f4 = t - this.f14mX[startIndex];
        float f5 = f4;
        float fraction = f4 / xRange;
        float startY = this.f15mY[startIndex];
        return startY + (fraction * (this.f15mY[endIndex] - startY));
    }

    private static Path createQuad(float f, float f2) {
        float controlX = f;
        float controlY = f2;
        float f3 = controlX;
        float f4 = controlY;
        Path path = new Path();
        Path path2 = path;
        path.moveTo(0.0f, 0.0f);
        path2.quadTo(controlX, controlY, 1.0f, 1.0f);
        return path2;
    }

    private static Path createCubic(float f, float f2, float f3, float f4) {
        float controlX1 = f;
        float controlY1 = f2;
        float controlX2 = f3;
        float controlY2 = f4;
        float f5 = controlX1;
        float f6 = controlY1;
        float f7 = controlX2;
        float f8 = controlY2;
        Path path = new Path();
        Path path2 = path;
        path.moveTo(0.0f, 0.0f);
        path2.cubicTo(controlX1, controlY1, controlX2, controlY2, 1.0f, 1.0f);
        return path2;
    }
}
