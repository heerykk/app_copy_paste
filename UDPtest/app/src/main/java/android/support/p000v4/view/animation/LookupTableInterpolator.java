package android.support.p000v4.view.animation;

import android.view.animation.Interpolator;

/* renamed from: android.support.v4.view.animation.LookupTableInterpolator */
abstract class LookupTableInterpolator implements Interpolator {
    private final float mStepSize = (1.0f / ((float) (this.mValues.length - 1)));
    private final float[] mValues;

    public LookupTableInterpolator(float[] fArr) {
        float[] values = fArr;
        float[] fArr2 = values;
        this.mValues = values;
    }

    public float getInterpolation(float f) {
        float input = f;
        float f2 = input;
        if (input >= 1.0f) {
            return 1.0f;
        }
        if (input <= 0.0f) {
            return 0.0f;
        }
        int min = Math.min((int) (input * ((float) (this.mValues.length - 1))), this.mValues.length - 2);
        int position = min;
        float f3 = input - (((float) min) * this.mStepSize);
        float f4 = f3;
        float weight = f3 / this.mStepSize;
        return this.mValues[position] + (weight * (this.mValues[position + 1] - this.mValues[position]));
    }
}
