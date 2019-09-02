package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
class VisibilityKitKat extends TransitionKitKat implements VisibilityImpl {

    private static class VisibilityWrapper extends Visibility {
        private final VisibilityInterface mVisibility;

        VisibilityWrapper(VisibilityInterface visibilityInterface) {
            VisibilityInterface visibility = visibilityInterface;
            VisibilityInterface visibilityInterface2 = visibility;
            this.mVisibility = visibility;
        }

        public void captureStartValues(TransitionValues transitionValues) {
            TransitionValues transitionValues2 = transitionValues;
            TransitionValues transitionValues3 = transitionValues2;
            TransitionKitKat.wrapCaptureStartValues(this.mVisibility, transitionValues2);
        }

        public void captureEndValues(TransitionValues transitionValues) {
            TransitionValues transitionValues2 = transitionValues;
            TransitionValues transitionValues3 = transitionValues2;
            TransitionKitKat.wrapCaptureEndValues(this.mVisibility, transitionValues2);
        }

        public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            ViewGroup sceneRoot = viewGroup;
            TransitionValues startValues = transitionValues;
            TransitionValues endValues = transitionValues2;
            ViewGroup viewGroup2 = sceneRoot;
            TransitionValues transitionValues3 = startValues;
            TransitionValues transitionValues4 = endValues;
            return this.mVisibility.createAnimator(sceneRoot, TransitionKitKat.convertToSupport(startValues), TransitionKitKat.convertToSupport(endValues));
        }

        public boolean isVisible(TransitionValues transitionValues) {
            TransitionValues values = transitionValues;
            TransitionValues transitionValues2 = values;
            if (values == null) {
                return false;
            }
            TransitionValues externalValues = new TransitionValues();
            TransitionKitKat.copyValues(values, externalValues);
            return this.mVisibility.isVisible(externalValues);
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
            return this.mVisibility.onAppear(sceneRoot, TransitionKitKat.convertToSupport(startValues), startVisibility, TransitionKitKat.convertToSupport(endValues), endVisibility);
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
            return this.mVisibility.onDisappear(sceneRoot, TransitionKitKat.convertToSupport(startValues), startVisibility, TransitionKitKat.convertToSupport(endValues), endVisibility);
        }
    }

    VisibilityKitKat() {
    }

    public void init(TransitionInterface transitionInterface, Object obj) {
        TransitionInterface external = transitionInterface;
        Object internal = obj;
        TransitionInterface transitionInterface2 = external;
        Object obj2 = internal;
        this.mExternalTransition = external;
        if (internal != null) {
            this.mTransition = (Visibility) internal;
        } else {
            this.mTransition = new VisibilityWrapper((VisibilityInterface) external);
        }
    }

    public boolean isVisible(TransitionValues transitionValues) {
        TransitionValues values = transitionValues;
        TransitionValues transitionValues2 = values;
        return ((Visibility) this.mTransition).isVisible(convertToPlatform(values));
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
        return ((Visibility) this.mTransition).onAppear(sceneRoot, convertToPlatform(startValues), startVisibility, convertToPlatform(endValues), endVisibility);
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
        return ((Visibility) this.mTransition).onDisappear(sceneRoot, convertToPlatform(startValues), startVisibility, convertToPlatform(endValues), endVisibility);
    }
}
