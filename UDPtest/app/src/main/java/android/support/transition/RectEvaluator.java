package android.support.transition;

import android.animation.TypeEvaluator;
import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;

@TargetApi(14)
@RequiresApi(14)
class RectEvaluator implements TypeEvaluator<Rect> {
    private Rect mRect;

    public /* bridge */ /* synthetic */ Object evaluate(float f, Object obj, Object obj2) {
        return evaluate(f, (Rect) obj, (Rect) obj2);
    }

    public RectEvaluator() {
    }

    public RectEvaluator(Rect rect) {
        Rect reuseRect = rect;
        Rect rect2 = reuseRect;
        this.mRect = reuseRect;
    }

    public Rect evaluate(float f, Rect rect, Rect rect2) {
        float fraction = f;
        Rect startValue = rect;
        Rect endValue = rect2;
        float f2 = fraction;
        Rect rect3 = startValue;
        Rect rect4 = endValue;
        int left = startValue.left + ((int) (((float) (endValue.left - startValue.left)) * fraction));
        int top = startValue.top + ((int) (((float) (endValue.top - startValue.top)) * fraction));
        int right = startValue.right + ((int) (((float) (endValue.right - startValue.right)) * fraction));
        int i = startValue.bottom + ((int) (((float) (endValue.bottom - startValue.bottom)) * fraction));
        int i2 = i;
        if (this.mRect != null) {
            this.mRect.set(left, top, right, i);
            return this.mRect;
        }
        Rect rect5 = new Rect(left, top, right, i);
        return rect5;
    }
}
