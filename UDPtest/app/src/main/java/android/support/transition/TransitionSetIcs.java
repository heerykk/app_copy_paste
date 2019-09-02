package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@TargetApi(14)
@RequiresApi(14)
class TransitionSetIcs extends TransitionIcs implements TransitionSetImpl {
    private TransitionSetPort mTransitionSet = new TransitionSetPort();

    public TransitionSetIcs(TransitionInterface transitionInterface) {
        TransitionInterface transition = transitionInterface;
        TransitionInterface transitionInterface2 = transition;
        init(transition, this.mTransitionSet);
    }

    public int getOrdering() {
        return this.mTransitionSet.getOrdering();
    }

    public TransitionSetIcs setOrdering(int i) {
        int ordering = i;
        int i2 = ordering;
        TransitionSetPort ordering2 = this.mTransitionSet.setOrdering(ordering);
        return this;
    }

    public TransitionSetIcs addTransition(TransitionImpl transitionImpl) {
        TransitionImpl transition = transitionImpl;
        TransitionImpl transitionImpl2 = transition;
        TransitionSetPort addTransition = this.mTransitionSet.addTransition(((TransitionIcs) transition).mTransition);
        return this;
    }

    public TransitionSetIcs removeTransition(TransitionImpl transitionImpl) {
        TransitionImpl transition = transitionImpl;
        TransitionImpl transitionImpl2 = transition;
        TransitionSetPort removeTransition = this.mTransitionSet.removeTransition(((TransitionIcs) transition).mTransition);
        return this;
    }
}
