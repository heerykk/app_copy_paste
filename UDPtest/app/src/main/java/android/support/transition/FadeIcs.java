package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class FadeIcs extends TransitionIcs implements VisibilityImpl {
    public FadeIcs(TransitionInterface transitionInterface) {
        TransitionInterface transition = transitionInterface;
        TransitionInterface transitionInterface2 = transition;
        init(transition, new FadePort());
    }

    public FadeIcs(TransitionInterface transitionInterface, int i) {
        TransitionInterface transition = transitionInterface;
        int fadingMode = i;
        TransitionInterface transitionInterface2 = transition;
        int i2 = fadingMode;
        init(transition, new FadePort(fadingMode));
    }

    public boolean isVisible(TransitionValues transitionValues) {
        TransitionValues values = transitionValues;
        TransitionValues transitionValues2 = values;
        return ((FadePort) this.mTransition).isVisible(values);
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
        return ((FadePort) this.mTransition).onAppear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
    }

    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        ViewGroup sceneRoot = viewGroup;
        TransitionValues startValues = transitionValues;
        int startVisibility = i;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionValues transitionValues3 = startValues;
        int i3 = startVisibility;
        TransitionValues transitionValues4 = transitionValues2;
        int i4 = i2;
        return ((FadePort) this.mTransition).onDisappear(sceneRoot, startValues, startVisibility, startValues, startVisibility);
    }
}
