package android.support.p000v4.graphics;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.p000v4.view.ViewCompat;

/* renamed from: android.support.v4.graphics.ColorUtils */
public final class ColorUtils {
    private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
    private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
    private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal<>();
    private static final double XYZ_EPSILON = 0.008856d;
    private static final double XYZ_KAPPA = 903.3d;
    private static final double XYZ_WHITE_REFERENCE_X = 95.047d;
    private static final double XYZ_WHITE_REFERENCE_Y = 100.0d;
    private static final double XYZ_WHITE_REFERENCE_Z = 108.883d;

    private ColorUtils() {
    }

    public static int compositeColors(@ColorInt int i, @ColorInt int i2) {
        int foreground = i;
        int background = i2;
        int i3 = foreground;
        int i4 = background;
        int bgAlpha = Color.alpha(background);
        int alpha = Color.alpha(foreground);
        int fgAlpha = alpha;
        int a = compositeAlpha(alpha, bgAlpha);
        return Color.argb(a, compositeComponent(Color.red(foreground), fgAlpha, Color.red(background), bgAlpha, a), compositeComponent(Color.green(foreground), fgAlpha, Color.green(background), bgAlpha, a), compositeComponent(Color.blue(foreground), fgAlpha, Color.blue(background), bgAlpha, a));
    }

    private static int compositeAlpha(int i, int i2) {
        int foregroundAlpha = i;
        int backgroundAlpha = i2;
        int i3 = foregroundAlpha;
        int i4 = backgroundAlpha;
        return 255 - (((255 - backgroundAlpha) * (255 - foregroundAlpha)) / 255);
    }

    private static int compositeComponent(int i, int i2, int i3, int i4, int i5) {
        int fgC = i;
        int fgA = i2;
        int bgC = i3;
        int bgA = i4;
        int a = i5;
        int i6 = fgC;
        int i7 = fgA;
        int i8 = bgC;
        int i9 = bgA;
        int i10 = a;
        if (a != 0) {
            return (((255 * fgC) * fgA) + ((bgC * bgA) * (255 - fgA))) / (a * 255);
        }
        return 0;
    }

    @FloatRange(from = 0.0d, mo5to = 1.0d)
    public static double calculateLuminance(@ColorInt int i) {
        int color = i;
        int i2 = color;
        double[] tempDouble3Array = getTempDouble3Array();
        double[] dArr = tempDouble3Array;
        colorToXYZ(color, tempDouble3Array);
        return tempDouble3Array[1] / XYZ_WHITE_REFERENCE_Y;
    }

    public static double calculateContrast(@ColorInt int i, @ColorInt int i2) {
        int foreground = i;
        int background = i2;
        int foreground2 = foreground;
        int i3 = background;
        if (Color.alpha(background) == 255) {
            if (Color.alpha(foreground) < 255) {
                foreground2 = compositeColors(foreground, background);
            }
            double luminance1 = calculateLuminance(foreground2) + 0.05d;
            double luminance2 = calculateLuminance(background) + 0.05d;
            return Math.max(luminance1, luminance2) / Math.min(luminance1, luminance2);
        }
        throw new IllegalArgumentException("background can not be translucent: #" + Integer.toHexString(background));
    }

    public static int calculateMinimumAlpha(@ColorInt int i, @ColorInt int i2, float f) {
        int foreground = i;
        int background = i2;
        float minContrastRatio = f;
        int i3 = foreground;
        int i4 = background;
        float f2 = minContrastRatio;
        if (Color.alpha(background) == 255) {
            int alphaComponent = setAlphaComponent(foreground, 255);
            int i5 = alphaComponent;
            double calculateContrast = calculateContrast(alphaComponent, background);
            double d = calculateContrast;
            if (calculateContrast < ((double) minContrastRatio)) {
                return -1;
            }
            int minAlpha = 0;
            int maxAlpha = 255;
            for (int numIterations = 0; numIterations <= 10 && maxAlpha - minAlpha > 1; numIterations++) {
                int testAlpha = (minAlpha + maxAlpha) / 2;
                int alphaComponent2 = setAlphaComponent(foreground, testAlpha);
                int testForeground = alphaComponent2;
                double calculateContrast2 = calculateContrast(alphaComponent2, background);
                double testRatio = calculateContrast2;
                if (calculateContrast2 < ((double) minContrastRatio)) {
                    minAlpha = testAlpha;
                } else {
                    maxAlpha = testAlpha;
                }
            }
            return maxAlpha;
        }
        throw new IllegalArgumentException("background can not be translucent: #" + Integer.toHexString(background));
    }

    public static void RGBToHSL(@IntRange(from = 0, mo10to = 255) int i, @IntRange(from = 0, mo10to = 255) int i2, @IntRange(from = 0, mo10to = 255) int i3, @NonNull float[] fArr) {
        float h;
        float s;
        int r = i;
        int g = i2;
        int b = i3;
        float[] outHsl = fArr;
        int i4 = r;
        int i5 = g;
        int i6 = b;
        float[] fArr2 = outHsl;
        float rf = ((float) r) / 255.0f;
        float gf = ((float) g) / 255.0f;
        float bf = ((float) b) / 255.0f;
        float max = Math.max(rf, Math.max(gf, bf));
        float min = Math.min(rf, Math.min(gf, bf));
        float deltaMaxMin = max - min;
        float l = (max + min) / 2.0f;
        if (max == min) {
            s = 0.0f;
            h = 0.0f;
        } else {
            if (max == rf) {
                h = ((gf - bf) / deltaMaxMin) % 6.0f;
            } else if (max == gf) {
                h = ((bf - rf) / deltaMaxMin) + 2.0f;
            } else {
                h = ((rf - gf) / deltaMaxMin) + 4.0f;
            }
            s = deltaMaxMin / (1.0f - Math.abs((2.0f * l) - 1.0f));
        }
        float f = (h * 60.0f) % 360.0f;
        float h2 = f;
        if (f < 0.0f) {
            h2 += 360.0f;
        }
        outHsl[0] = constrain(h2, 0.0f, 360.0f);
        outHsl[1] = constrain(s, 0.0f, 1.0f);
        outHsl[2] = constrain(l, 0.0f, 1.0f);
    }

    public static void colorToHSL(@ColorInt int i, @NonNull float[] fArr) {
        int color = i;
        float[] outHsl = fArr;
        int i2 = color;
        float[] fArr2 = outHsl;
        RGBToHSL(Color.red(color), Color.green(color), Color.blue(color), outHsl);
    }

    @ColorInt
    public static int HSLToColor(@NonNull float[] fArr) {
        float[] hsl = fArr;
        float[] fArr2 = hsl;
        float h = hsl[0];
        float s = hsl[1];
        float l = hsl[2];
        float c = (1.0f - Math.abs((2.0f * l) - 1.0f)) * s;
        float m = l - (0.5f * c);
        float x = c * (1.0f - Math.abs(((h / 60.0f) % 2.0f) - 1.0f));
        int r = 0;
        int g = 0;
        int b = 0;
        switch (((int) h) / 60) {
            case 0:
                r = Math.round(255.0f * (c + m));
                g = Math.round(255.0f * (x + m));
                b = Math.round(255.0f * m);
                break;
            case 1:
                r = Math.round(255.0f * (x + m));
                g = Math.round(255.0f * (c + m));
                b = Math.round(255.0f * m);
                break;
            case 2:
                r = Math.round(255.0f * m);
                g = Math.round(255.0f * (c + m));
                b = Math.round(255.0f * (x + m));
                break;
            case 3:
                r = Math.round(255.0f * m);
                g = Math.round(255.0f * (x + m));
                b = Math.round(255.0f * (c + m));
                break;
            case 4:
                r = Math.round(255.0f * (x + m));
                g = Math.round(255.0f * m);
                b = Math.round(255.0f * (c + m));
                break;
            case 5:
            case 6:
                r = Math.round(255.0f * (c + m));
                g = Math.round(255.0f * m);
                b = Math.round(255.0f * (x + m));
                break;
        }
        return Color.rgb(constrain(r, 0, 255), constrain(g, 0, 255), constrain(b, 0, 255));
    }

    @ColorInt
    public static int setAlphaComponent(@ColorInt int i, @IntRange(from = 0, mo10to = 255) int i2) {
        int color = i;
        int alpha = i2;
        int i3 = color;
        int i4 = alpha;
        if (alpha >= 0 && alpha <= 255) {
            return (color & ViewCompat.MEASURED_SIZE_MASK) | (alpha << 24);
        }
        throw new IllegalArgumentException("alpha must be between 0 and 255.");
    }

    public static void colorToLAB(@ColorInt int i, @NonNull double[] dArr) {
        int color = i;
        double[] outLab = dArr;
        int i2 = color;
        double[] dArr2 = outLab;
        RGBToLAB(Color.red(color), Color.green(color), Color.blue(color), outLab);
    }

    public static void RGBToLAB(@IntRange(from = 0, mo10to = 255) int i, @IntRange(from = 0, mo10to = 255) int i2, @IntRange(from = 0, mo10to = 255) int i3, @NonNull double[] dArr) {
        int r = i;
        int g = i2;
        int b = i3;
        double[] outLab = dArr;
        int i4 = r;
        int i5 = g;
        int i6 = b;
        double[] dArr2 = outLab;
        RGBToXYZ(r, g, b, outLab);
        XYZToLAB(outLab[0], outLab[1], outLab[2], outLab);
    }

    public static void colorToXYZ(@ColorInt int i, @NonNull double[] dArr) {
        int color = i;
        double[] outXyz = dArr;
        int i2 = color;
        double[] dArr2 = outXyz;
        RGBToXYZ(Color.red(color), Color.green(color), Color.blue(color), outXyz);
    }

    public static void RGBToXYZ(@IntRange(from = 0, mo10to = 255) int i, @IntRange(from = 0, mo10to = 255) int i2, @IntRange(from = 0, mo10to = 255) int i3, @NonNull double[] dArr) {
        int r = i;
        int g = i2;
        int b = i3;
        double[] outXyz = dArr;
        int i4 = r;
        int i5 = g;
        int i6 = b;
        double[] dArr2 = outXyz;
        if (outXyz.length == 3) {
            double d = ((double) r) / 255.0d;
            double sr = d;
            double sr2 = d < 0.04045d ? sr / 12.92d : Math.pow((sr + 0.055d) / 1.055d, 2.4d);
            double d2 = ((double) g) / 255.0d;
            double sg = d2;
            double sg2 = d2 < 0.04045d ? sg / 12.92d : Math.pow((sg + 0.055d) / 1.055d, 2.4d);
            double d3 = ((double) b) / 255.0d;
            double sb = d3;
            double sb2 = d3 < 0.04045d ? sb / 12.92d : Math.pow((sb + 0.055d) / 1.055d, 2.4d);
            outXyz[0] = XYZ_WHITE_REFERENCE_Y * ((sr2 * 0.4124d) + (sg2 * 0.3576d) + (sb2 * 0.1805d));
            outXyz[1] = XYZ_WHITE_REFERENCE_Y * ((sr2 * 0.2126d) + (sg2 * 0.7152d) + (sb2 * 0.0722d));
            outXyz[2] = XYZ_WHITE_REFERENCE_Y * ((sr2 * 0.0193d) + (sg2 * 0.1192d) + (sb2 * 0.9505d));
            return;
        }
        throw new IllegalArgumentException("outXyz must have a length of 3.");
    }

    public static void XYZToLAB(@FloatRange(from = 0.0d, mo5to = 95.047d) double d, @FloatRange(from = 0.0d, mo5to = 100.0d) double d2, @FloatRange(from = 0.0d, mo5to = 108.883d) double d3, @NonNull double[] dArr) {
        double x = d;
        double y = d2;
        double z = d3;
        double[] outLab = dArr;
        double d4 = x;
        double d5 = y;
        double d6 = z;
        double[] dArr2 = outLab;
        if (outLab.length == 3) {
            double x2 = pivotXyzComponent(x / XYZ_WHITE_REFERENCE_X);
            double y2 = pivotXyzComponent(y / XYZ_WHITE_REFERENCE_Y);
            double pivotXyzComponent = pivotXyzComponent(z / XYZ_WHITE_REFERENCE_Z);
            double z2 = pivotXyzComponent;
            outLab[0] = Math.max(0.0d, (116.0d * y2) - 16.0d);
            outLab[1] = 500.0d * (x2 - y2);
            outLab[2] = 200.0d * (y2 - pivotXyzComponent);
            return;
        }
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("outLab must have a length of 3.");
        throw illegalArgumentException;
    }

    public static void LABToXYZ(@FloatRange(from = 0.0d, mo5to = 100.0d) double d, @FloatRange(from = -128.0d, mo5to = 127.0d) double d2, @FloatRange(from = -128.0d, mo5to = 127.0d) double d3, @NonNull double[] dArr) {
        double l = d;
        double a = d2;
        double b = d3;
        double[] outXyz = dArr;
        double d4 = l;
        double d5 = a;
        double d6 = b;
        double[] dArr2 = outXyz;
        double fy = (l + 16.0d) / 116.0d;
        double fx = (a / 500.0d) + fy;
        double fz = fy - (b / 200.0d);
        double pow = Math.pow(fx, 3.0d);
        double xr = pow > XYZ_EPSILON ? pow : ((116.0d * fx) - 16.0d) / XYZ_KAPPA;
        double yr = l > 7.9996247999999985d ? Math.pow(fy, 3.0d) : l / XYZ_KAPPA;
        double pow2 = Math.pow(fz, 3.0d);
        double zr = pow2 > XYZ_EPSILON ? pow2 : ((116.0d * fz) - 16.0d) / XYZ_KAPPA;
        outXyz[0] = xr * XYZ_WHITE_REFERENCE_X;
        outXyz[1] = yr * XYZ_WHITE_REFERENCE_Y;
        outXyz[2] = zr * XYZ_WHITE_REFERENCE_Z;
    }

    @ColorInt
    public static int XYZToColor(@FloatRange(from = 0.0d, mo5to = 95.047d) double d, @FloatRange(from = 0.0d, mo5to = 100.0d) double d2, @FloatRange(from = 0.0d, mo5to = 108.883d) double d3) {
        double x = d;
        double y = d2;
        double z = d3;
        double d4 = x;
        double d5 = y;
        double d6 = z;
        double r = (((x * 3.2406d) + (y * -1.5372d)) + (z * -0.4986d)) / XYZ_WHITE_REFERENCE_Y;
        double g = (((x * -0.9689d) + (y * 1.8758d)) + (z * 0.0415d)) / XYZ_WHITE_REFERENCE_Y;
        double b = (((x * 0.0557d) + (y * -0.204d)) + (z * 1.057d)) / XYZ_WHITE_REFERENCE_Y;
        return Color.rgb(constrain((int) Math.round((r > 0.0031308d ? (1.055d * Math.pow(r, 0.4166666666666667d)) - 0.055d : 12.92d * r) * 255.0d), 0, 255), constrain((int) Math.round((g > 0.0031308d ? (1.055d * Math.pow(g, 0.4166666666666667d)) - 0.055d : 12.92d * g) * 255.0d), 0, 255), constrain((int) Math.round((b > 0.0031308d ? (1.055d * Math.pow(b, 0.4166666666666667d)) - 0.055d : 12.92d * b) * 255.0d), 0, 255));
    }

    @ColorInt
    public static int LABToColor(@FloatRange(from = 0.0d, mo5to = 100.0d) double d, @FloatRange(from = -128.0d, mo5to = 127.0d) double d2, @FloatRange(from = -128.0d, mo5to = 127.0d) double d3) {
        double l = d;
        double a = d2;
        double b = d3;
        double d4 = l;
        double d5 = a;
        double d6 = b;
        double[] tempDouble3Array = getTempDouble3Array();
        double[] result = tempDouble3Array;
        LABToXYZ(l, a, b, tempDouble3Array);
        return XYZToColor(tempDouble3Array[0], result[1], result[2]);
    }

    public static double distanceEuclidean(@NonNull double[] dArr, @NonNull double[] dArr2) {
        double[] labX = dArr;
        double[] labY = dArr2;
        double[] dArr3 = labX;
        double[] dArr4 = labY;
        return Math.sqrt(Math.pow(labX[0] - labY[0], 2.0d) + Math.pow(labX[1] - labY[1], 2.0d) + Math.pow(labX[2] - labY[2], 2.0d));
    }

    private static float constrain(float f, float f2, float f3) {
        float amount = f;
        float low = f2;
        float high = f3;
        float f4 = amount;
        float f5 = low;
        float f6 = high;
        if (amount < low) {
            return low;
        }
        return amount > high ? high : amount;
    }

    private static int constrain(int i, int i2, int i3) {
        int amount = i;
        int low = i2;
        int high = i3;
        int i4 = amount;
        int i5 = low;
        int i6 = high;
        int i7 = amount >= low ? amount <= high ? amount : high : low;
        return i7;
    }

    private static double pivotXyzComponent(double d) {
        double component = d;
        double d2 = component;
        return component > XYZ_EPSILON ? Math.pow(component, 0.3333333333333333d) : ((XYZ_KAPPA * component) + 16.0d) / 116.0d;
    }

    @ColorInt
    public static int blendARGB(@ColorInt int i, @ColorInt int i2, @FloatRange(from = 0.0d, mo5to = 1.0d) float f) {
        int color1 = i;
        int color2 = i2;
        float ratio = f;
        int i3 = color1;
        int i4 = color2;
        float f2 = ratio;
        float inverseRatio = 1.0f - ratio;
        return Color.argb((int) ((((float) Color.alpha(color1)) * inverseRatio) + (((float) Color.alpha(color2)) * ratio)), (int) ((((float) Color.red(color1)) * inverseRatio) + (((float) Color.red(color2)) * ratio)), (int) ((((float) Color.green(color1)) * inverseRatio) + (((float) Color.green(color2)) * ratio)), (int) ((((float) Color.blue(color1)) * inverseRatio) + (((float) Color.blue(color2)) * ratio)));
    }

    public static void blendHSL(@NonNull float[] fArr, @NonNull float[] fArr2, @FloatRange(from = 0.0d, mo5to = 1.0d) float f, @NonNull float[] fArr3) {
        float[] hsl1 = fArr;
        float[] hsl2 = fArr2;
        float ratio = f;
        float[] outResult = fArr3;
        float[] fArr4 = hsl1;
        float[] fArr5 = hsl2;
        float f2 = ratio;
        float[] fArr6 = outResult;
        if (outResult.length == 3) {
            float f3 = 1.0f - ratio;
            float f4 = f3;
            float[] fArr7 = outResult;
            fArr7[0] = circularInterpolate(hsl1[0], hsl2[0], ratio);
            outResult[1] = (hsl1[1] * f3) + (hsl2[1] * ratio);
            outResult[2] = (hsl1[2] * f3) + (hsl2[2] * ratio);
            return;
        }
        throw new IllegalArgumentException("result must have a length of 3.");
    }

    public static void blendLAB(@NonNull double[] dArr, @NonNull double[] dArr2, @FloatRange(from = 0.0d, mo5to = 1.0d) double d, @NonNull double[] dArr3) {
        double[] lab1 = dArr;
        double[] lab2 = dArr2;
        double ratio = d;
        double[] outResult = dArr3;
        double[] dArr4 = lab1;
        double[] dArr5 = lab2;
        double d2 = ratio;
        double[] dArr6 = outResult;
        if (outResult.length == 3) {
            double d3 = 1.0d - ratio;
            double d4 = d3;
            outResult[0] = (lab1[0] * d3) + (lab2[0] * ratio);
            outResult[1] = (lab1[1] * d3) + (lab2[1] * ratio);
            outResult[2] = (lab1[2] * d3) + (lab2[2] * ratio);
            return;
        }
        throw new IllegalArgumentException("outResult must have a length of 3.");
    }

    @VisibleForTesting
    static float circularInterpolate(float f, float f2, float f3) {
        float a = f;
        float b = f2;
        float f4 = f3;
        float a2 = a;
        float b2 = b;
        float f5 = f4;
        if (Math.abs(b - a) > 180.0f) {
            if (b > a) {
                a2 = a + 360.0f;
            } else {
                b2 = b + 360.0f;
            }
        }
        return (a2 + ((b2 - a2) * f4)) % 360.0f;
    }

    private static double[] getTempDouble3Array() {
        double[] dArr = (double[]) TEMP_ARRAY.get();
        double[] result = dArr;
        if (dArr == null) {
            result = new double[3];
            TEMP_ARRAY.set(result);
        }
        return result;
    }
}
