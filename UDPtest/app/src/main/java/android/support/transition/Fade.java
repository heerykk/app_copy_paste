package android.support.transition;

import android.animation.Animator;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

public class Fade extends Visibility {

    /* renamed from: IN */
    public static final int f4IN = 1;
    public static final int OUT = 2;

    public Fade(int i) {
        int fadingMode = i;
        int i2 = fadingMode;
        super(true);
        if (VERSION.SDK_INT < 19) {
            if (fadingMode <= 0) {
                this.mImpl = new FadeIcs(this);
            } else {
                this.mImpl = new FadeIcs(this, fadingMode);
            }
        } else if (fadingMode <= 0) {
            this.mImpl = new FadeKitKat(this);
        } else {
            this.mImpl = new FadeKitKat(this, fadingMode);
        }
    }

    public Fade() {
        this(-1);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        this.mImpl.captureEndValues(transitionValues2);
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        this.mImpl.captureStartValues(transitionValues2);
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @NonNull TransitionValues transitionValues, @NonNull TransitionValues transitionValues2) {
        ViewGroup sceneRoot = viewGroup;
        TransitionValues startValues = transitionValues;
        TransitionValues endValues = transitionValues2;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionValues transitionValues3 = startValues;
        TransitionValues transitionValues4 = endValues;
        return this.mImpl.createAnimator(sceneRoot, startValues, endValues);
    }
}
