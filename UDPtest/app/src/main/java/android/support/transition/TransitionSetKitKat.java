package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.TransitionSet;

@TargetApi(19)
@RequiresApi(19)
class TransitionSetKitKat extends TransitionKitKat implements TransitionSetImpl {
    private TransitionSet mTransitionSet = new TransitionSet();

    public TransitionSetKitKat(TransitionInterface transitionInterface) {
        TransitionInterface transition = transitionInterface;
        TransitionInterface transitionInterface2 = transition;
        init(transition, this.mTransitionSet);
    }

    public int getOrdering() {
        return this.mTransitionSet.getOrdering();
    }

    public TransitionSetKitKat setOrdering(int i) {
        int ordering = i;
        int i2 = ordering;
        TransitionSet ordering2 = this.mTransitionSet.setOrdering(ordering);
        return this;
    }

    public TransitionSetKitKat addTransition(TransitionImpl transitionImpl) {
        TransitionImpl transition = transitionImpl;
        TransitionImpl transitionImpl2 = transition;
        TransitionSet addTransition = this.mTransitionSet.addTransition(((TransitionKitKat) transition).mTransition);
        return this;
    }

    public TransitionSetKitKat removeTransition(TransitionImpl transitionImpl) {
        TransitionImpl transition = transitionImpl;
        TransitionImpl transitionImpl2 = transition;
        TransitionSet removeTransition = this.mTransitionSet.removeTransition(((TransitionKitKat) transition).mTransition);
        return this;
    }
}
