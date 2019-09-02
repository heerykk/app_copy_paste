package android.support.transition;

import android.animation.Animator;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public abstract class Visibility extends Transition implements VisibilityInterface {
    public Visibility() {
        this(false);
    }

    Visibility(boolean z) {
        boolean deferred = z;
        super(true);
        if (!deferred) {
            if (VERSION.SDK_INT < 19) {
                this.mImpl = new VisibilityIcs();
            } else {
                this.mImpl = new VisibilityKitKat();
            }
            this.mImpl.init(this);
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

    public boolean isVisible(TransitionValues transitionValues) {
        TransitionValues values = transitionValues;
        TransitionValues transitionValues2 = values;
        return ((VisibilityImpl) this.mImpl).isVisible(values);
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
        return ((VisibilityImpl) this.mImpl).onAppear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
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
        return ((VisibilityImpl) this.mImpl).onDisappear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
    }
}
