package android.support.design.widget;

class MathUtils {
    MathUtils() {
    }

    static int constrain(int i, int i2, int i3) {
        int amount = i;
        int low = i2;
        int high = i3;
        int i4 = amount;
        int i5 = low;
        int i6 = high;
        int i7 = amount >= low ? amount <= high ? amount : high : low;
        return i7;
    }

    static float constrain(float f, float f2, float f3) {
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
}
