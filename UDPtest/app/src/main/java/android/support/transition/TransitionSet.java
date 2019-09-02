package android.support.transition;

import android.animation.Animator;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

public class TransitionSet extends Transition {
    public static final int ORDERING_SEQUENTIAL = 1;
    public static final int ORDERING_TOGETHER = 0;

    public TransitionSet() {
        super(true);
        if (VERSION.SDK_INT >= 19) {
            this.mImpl = new TransitionSetKitKat(this);
        } else {
            this.mImpl = new TransitionSetIcs(this);
        }
    }

    public int getOrdering() {
        return ((TransitionSetImpl) this.mImpl).getOrdering();
    }

    @NonNull
    public TransitionSet setOrdering(int i) {
        int ordering = i;
        int i2 = ordering;
        TransitionSetImpl ordering2 = ((TransitionSetImpl) this.mImpl).setOrdering(ordering);
        return this;
    }

    @NonNull
    public TransitionSet addTransition(@NonNull Transition transition) {
        Transition transition2 = transition;
        Transition transition3 = transition2;
        TransitionSetImpl addTransition = ((TransitionSetImpl) this.mImpl).addTransition(transition2.mImpl);
        return this;
    }

    @NonNull
    public TransitionSet removeTransition(@NonNull Transition transition) {
        Transition transition2 = transition;
        Transition transition3 = transition2;
        TransitionSetImpl removeTransition = ((TransitionSetImpl) this.mImpl).removeTransition(transition2.mImpl);
        return this;
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
