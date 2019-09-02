package android.support.design.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Map;

@TargetApi(14)
@RequiresApi(14)
public class TextScale extends Transition {
    private static final String PROPNAME_SCALE = "android:textscale:scale";

    public TextScale() {
    }

    public void captureStartValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        captureValues(transitionValues2);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        captureValues(transitionValues2);
    }

    private void captureValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        if (transitionValues2.view instanceof TextView) {
            Object put = transitionValues2.values.put(PROPNAME_SCALE, Float.valueOf(((TextView) transitionValues2.view).getScaleX()));
        }
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        TransitionValues startValues = transitionValues;
        TransitionValues endValues = transitionValues2;
        ViewGroup viewGroup2 = viewGroup;
        TransitionValues transitionValues3 = startValues;
        TransitionValues transitionValues4 = endValues;
        if (startValues == null || endValues == null || !(startValues.view instanceof TextView) || !(endValues.view instanceof TextView)) {
            return null;
        }
        TextView view = (TextView) endValues.view;
        Map<String, Object> startVals = startValues.values;
        Map<String, Object> endVals = endValues.values;
        float startSize = startVals.get(PROPNAME_SCALE) == null ? 1.0f : ((Float) startVals.get(PROPNAME_SCALE)).floatValue();
        float endSize = endVals.get(PROPNAME_SCALE) == null ? 1.0f : ((Float) endVals.get(PROPNAME_SCALE)).floatValue();
        if (startSize == endSize) {
            return null;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{startSize, endSize});
        ValueAnimator animator = ofFloat;
        ValueAnimator valueAnimator = ofFloat;
        final TextView textView = view;
        C00091 r0 = new AnimatorUpdateListener(this) {
            final /* synthetic */ TextScale this$0;

            {
                TextScale this$02 = r6;
                TextView textView = r7;
                TextScale textScale = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ValueAnimator valueAnimator2 = valueAnimator;
                ValueAnimator valueAnimator3 = valueAnimator2;
                float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                float f = floatValue;
                textView.setScaleX(floatValue);
                textView.setScaleY(floatValue);
            }
        };
        valueAnimator.addUpdateListener(r0);
        return animator;
    }
}
