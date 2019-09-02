package android.support.transition;

import android.animation.Animator;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

public class ChangeBounds extends Transition {
    public ChangeBounds() {
        super(true);
        if (VERSION.SDK_INT >= 19) {
            this.mImpl = new ChangeBoundsKitKat(this);
        } else {
            this.mImpl = new ChangeBoundsIcs(this);
        }
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

    public void setResizeClip(boolean z) {
        ((ChangeBoundsInterface) this.mImpl).setResizeClip(z);
    }
}
