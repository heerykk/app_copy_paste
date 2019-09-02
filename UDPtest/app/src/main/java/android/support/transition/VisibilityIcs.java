package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class VisibilityIcs extends TransitionIcs implements VisibilityImpl {

    private static class VisibilityWrapper extends VisibilityPort {
        private VisibilityInterface mVisibility;

        VisibilityWrapper(VisibilityInterface visibilityInterface) {
            VisibilityInterface visibility = visibilityInterface;
            VisibilityInterface visibilityInterface2 = visibility;
            this.mVisibility = visibility;
        }

        public void captureStartValues(TransitionValues transitionValues) {
            TransitionValues transitionValues2 = transitionValues;
            TransitionValues transitionValues3 = transitionValues2;
            this.mVisibility.captureStartValues(transitionValues2);
        }

        public void captureEndValues(TransitionValues transitionValues) {
            TransitionValues transitionValues2 = transitionValues;
            TransitionValues transitionValues3 = transitionValues2;
            this.mVisibility.captureEndValues(transitionValues2);
        }

        public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            ViewGroup sceneRoot = viewGroup;
            TransitionValues startValues = transitionValues;
            TransitionValues endValues = transitionValues2;
            ViewGroup viewGroup2 = sceneRoot;
            TransitionValues transitionValues3 = startValues;
            TransitionValues transitionValues4 = endValues;
            return this.mVisibility.createAnimator(sceneRoot, startValues, endValues);
        }

        public boolean isVisible(TransitionValues transitionValues) {
            TransitionValues values = transitionValues;
            TransitionValues transitionValues2 = values;
            return this.mVisibility.isVisible(values);
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
            return this.mVisibility.onAppear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
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
            return this.mVisibility.onDisappear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
        }
    }

    VisibilityIcs() {
    }

    public void init(TransitionInterface transitionInterface, Object obj) {
        TransitionInterface external = transitionInterface;
        Object internal = obj;
        TransitionInterface transitionInterface2 = external;
        Object obj2 = internal;
        this.mExternalTransition = external;
        if (internal != null) {
            this.mTransition = (VisibilityPort) internal;
        } else {
            this.mTransition = new VisibilityWrapper((VisibilityInterface) external);
        }
    }

    public boolean isVisible(TransitionValues transitionValues) {
        TransitionValues values = transitionValues;
        TransitionValues transitionValues2 = values;
        return ((VisibilityPort) this.mTransition).isVisible(values);
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
        return ((VisibilityPort) this.mTransition).onAppear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
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
        return ((VisibilityPort) this.mTransition).onDisappear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
    }
}
