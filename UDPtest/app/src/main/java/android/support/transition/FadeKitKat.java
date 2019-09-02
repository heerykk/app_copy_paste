package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Fade;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
class FadeKitKat extends TransitionKitKat implements VisibilityImpl {
    public FadeKitKat(TransitionInterface transitionInterface) {
        TransitionInterface transition = transitionInterface;
        TransitionInterface transitionInterface2 = transition;
        init(transition, new Fade());
    }

    public FadeKitKat(TransitionInterface transitionInterface, int i) {
        TransitionInterface transition = transitionInterface;
        int fadingMode = i;
        TransitionInterface transitionInterface2 = transition;
        int i2 = fadingMode;
        init(transition, new Fade(fadingMode));
    }

    public boolean isVisible(TransitionValues transitionValues) {
        TransitionValues values = transitionValues;
        TransitionValues transitionValues2 = values;
        return ((Fade) this.mTransition).isVisible(convertToPlatform(values));
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        ViewGroup sceneRoot = viewGroup;
        TransitionValues startValues = transitionValues;
        int startVisibility = i;
        TransitionValues endValues = transitionValues2;
        int endVisibility = i2;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionValues transitionValues3 = startValues;
        int i3 = startVisibility;
        TransitionValues transitionValues4 = endValues;
        int i4 = endVisibility;
        return ((Fade) this.mTransition).onAppear(sceneRoot, convertToPlatform(startValues), startVisibility, convertToPlatform(endValues), endVisibility);
    }

    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        ViewGroup sceneRoot = viewGroup;
        TransitionValues startValues = transitionValues;
        int startVisibility = i;
        TransitionValues endValues = transitionValues2;
        int endVisibility = i2;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionValues transitionValues3 = startValues;
        int i3 = startVisibility;
        TransitionValues transitionValues4 = endValues;
        int i4 = endVisibility;
        return ((Fade) this.mTransition).onDisappear(sceneRoot, convertToPlatform(startValues), startVisibility, convertToPlatform(endValues), endVisibility);
    }
}
